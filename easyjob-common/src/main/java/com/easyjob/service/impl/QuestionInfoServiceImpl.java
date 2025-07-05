package com.easyjob.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.dto.ImportErrorItem;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.Category;
import com.easyjob.entity.po.ExamQuestion;
import com.easyjob.enums.*;
import com.easyjob.exception.BusinessException;
import com.easyjob.mappers.CommonMapper;
import com.easyjob.service.CategoryService;
import com.easyjob.utils.ExcelUtils;
import com.easyjob.utils.VerifyUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.QuestionInfo;
import com.easyjob.entity.query.QuestionInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.QuestionInfoService;
import com.easyjob.mappers.QuestionInfoMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.utils.StringTools;
import org.springframework.web.multipart.MultipartFile;


@Service("questionInfoService")
public class QuestionInfoServiceImpl implements QuestionInfoService{

	@Resource
	private QuestionInfoMappers<QuestionInfo, QuestionInfoQuery> qsInfoMapper;

	@Resource
	private CategoryService cateSvc;

	@Resource
	private CommonMapper commonMapper; // update read and collect

	@Override
	public List<QuestionInfo> findListByParam(QuestionInfoQuery query){

		return this.qsInfoMapper.selectList(query);
	}
	@Override
	public Integer findCntByParam(QuestionInfoQuery query){

		return this.qsInfoMapper.selectCount(query);
	}
	@Override
	public PaginationResultVo<QuestionInfo> findListByPage(QuestionInfoQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<QuestionInfo> list = this.findListByParam(query);
		PaginationResultVo<QuestionInfo> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(QuestionInfo bean){

		return this.qsInfoMapper.insert(bean);
	}
	@Override
	public Integer addBatch(List<QuestionInfo> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.qsInfoMapper.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<QuestionInfo> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.qsInfoMapper.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(QuestionInfo bean, QuestionInfoQuery query) {
		StringTools.checkParam(query);
		return this.qsInfoMapper.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(QuestionInfoQuery query) {
		StringTools.checkParam(query);
		return this.qsInfoMapper.deleteByParam(query);
	}

	@Override
	public QuestionInfo getQuestionInfoByQuestionId(Integer questionId){

		return this.qsInfoMapper.selectQuestionInfoByQuestionId(questionId);
	}
	@Override
	public Integer updateQuestionInfoByQuestionId(QuestionInfo bean, Integer questionId){

		return this.qsInfoMapper.updateQuestionInfoByQuestionId(bean, questionId);
	}
	@Override
	public Integer deleteQuestionInfoByQuestionId(Integer questionId){

		return this.qsInfoMapper.deleteQuestionInfoByQuestionId(questionId);
	}

	@Override
	public void saveQuestion(QuestionInfo bean, Boolean superAdmin) {

		Category dbCate = cateSvc.getCategoryByCategoryId(bean.getCategoryId()); // qs(cateId) -> cate
		bean.setCategoryName(dbCate.getCategoryName()); // qs(cateName)
		if(null == bean.getQuestionId()){ // 创建
			bean.setCreateTime(new Date());
			this.qsInfoMapper.insert(bean);
		}else { // 修改 db qsInfo
			QuestionInfo dbQsInfo = this.qsInfoMapper.selectQuestionInfoByQuestionId(bean.getQuestionId());
			if(dbQsInfo.getCreateUserId().equals(bean.getCreateUserId()) && !superAdmin){
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			bean.setCreateUserId(null); // 不影响这些字段的值
			bean.setCreateUserName(null);
			bean.setCreateTime(null);
			this.qsInfoMapper.updateQuestionInfoByQuestionId(bean, bean.getQuestionId());
		}
	}

	@Override
	public void delQuestionBatch(String qsIds, Integer userId) {

		String[] qsIdArr = qsIds.split(",");
		if(null != userId){ // 不是超管
			QuestionInfoQuery qsQry = new QuestionInfoQuery();
			qsQry.setQsIds(qsIdArr);
			List<QuestionInfo> qsLst = this.qsInfoMapper.selectList(qsQry);

			// qsIds 中 不由 userId 发布的部分
			List<QuestionInfo> qsIdsFromUserId = qsLst.stream().filter(
					it -> !it.getCreateUserId().equals(String.valueOf(userId))
			).collect(Collectors.toList());
			if(!qsIdsFromUserId.isEmpty()){ // 不能删别人的
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
		}

		qsInfoMapper.delBatchByQsId(qsIdArr, PostStatusEnum.NO_POST.getStatus(), userId);
	}

	@Override
	public List<ImportErrorItem> importQuestion(MultipartFile file, SessionUserAdminDto dto) {

		List<Category> cateLst= cateSvc.loadAllCategoryByType(CategoryTypeEnum.QUESTION.getType());
		Map<String, Category> cateMap = cateLst.stream().collect(Collectors.toMap(
				Category::getCategoryName,
				Function.identity(),
				(d1, d2) -> d2)
		); // 用于检查excel的分类

		// 读excel （row, cell) List<List<String>>
		List<List<String>> datExcelLst = ExcelUtils.readExcel(file, Constants.EXCEL_TITLE_QUESTION, 1);

		// 解析 写库
		List<ImportErrorItem> errRowLst = new ArrayList<>(); // 所有出错行
		List<QuestionInfo> qsInfoLst = new ArrayList<>();
		Integer viewRowIdx= 2; // 标题行
		for(List<String> r: datExcelLst){

			if(errRowLst.size()> Constants.LEN_50) throw new BusinessException("错误数量超过"+ Constants.LEN_50+ "行,可能模板错误");

			viewRowIdx++;
			List<String> errItLst = new ArrayList<>();
			Integer id=0;
			String title= r.get(id++); // 0 1..
			if(StringTools.isEmpty(title) || title.length()> Constants.LEN_150){
				errItLst.add("标题不能空，且长度不能超过"+ Constants.LEN_150);
			}
			String cateName= r.get(id++);
			Category cate = cateMap.get(cateName);
			if(null == cate){
				errItLst.add("分类不存在");
			}
			String difDeg= r.get(id++);
			Integer difDegInt= null;
			if(VerifyUtils.verify(VerifyRegexEnum.POSITIVE_INTEGER, difDeg)){
				difDegInt= Integer.parseInt(difDeg);
				if(difDegInt > 5){
					errItLst.add("难度只能是1-5数字");
				}
			}else {
				errItLst.add("难度只能是正整数");
			}
			String qstDesc= r.get(id++);
			String aswAnal= r.get(id++);
			if(StringTools.isEmpty(aswAnal)){
				errItLst.add("答案解析不能为空");
			}
			if(!errItLst.isEmpty() || !errRowLst.isEmpty()){
				ImportErrorItem errRow = new ImportErrorItem();
				errRow.setRowNum(viewRowIdx); // 当前行出错
				errRow.setErrorItemLst(errItLst); // 当前行所有问题
				errRowLst.add(errRow);
				continue;
			}
			// 本行无错 则构建
			QuestionInfo qsInfo = new QuestionInfo();
			qsInfo.setTitle(title);
			qsInfo.setCategoryId(cate.getCategoryId());
			qsInfo.setCategoryName(cate.getCategoryName());
			qsInfo.setDifficultyLevel(difDegInt);
			qsInfo.setAnswerAnalysis(aswAnal);
			qsInfo.setCreateTime(new Date());
			qsInfo.setStatus(PostStatusEnum.NO_POST.getStatus()); // 仅导入未发布
			qsInfo.setCreateUserId(String.valueOf(dto.getUserId()));
			qsInfo.setCreateUserName(dto.getUserName()); // 当前登录人即提交人
			qsInfoLst.add(qsInfo); // 构建好一个QuestionInfo
		}

		if(qsInfoLst.isEmpty()) return errRowLst; // 全部行都错误

		// 存在部分行成功
		this.qsInfoMapper.insertBatch(qsInfoLst);
		return errRowLst;
	}

	@Override // 有两种 show detail 动作：直接点进来 或者 左右滑动  第一种的nxtType=null
	public QuestionInfo showDetailNext(QuestionInfoQuery qsQry, Integer curId, Integer nxtType, Boolean updateReadCnt) {

		if(null == nxtType){
			qsQry.setQuestionId(curId); // 依据字段的普通查询
		}else {
			qsQry.setNxtType(nxtType); // 额外升降序查询前后记录
			qsQry.setCurId(curId);
		}

		QuestionInfo qsInfo = qsInfoMapper.showDetailNext(qsQry); // 取前一或后一
		if(qsInfo == null ){
			if(nxtType == null){
				throw new BusinessException("当前 问题内容 不存在");
			}else if (nxtType == -1) {
				throw new BusinessException("已经是第一个问题"); // 没有上一个
			}else if (nxtType == 1) {
				throw new BusinessException("已经是最后一个问题"); // 没有下一个
			}
		}else if(updateReadCnt){ // qsInfo != null 需要更新阅读量
			// 加载XML结构 发生在 sb启动
			commonMapper.updateCount(Constants.TABLE_NAME_QUESTION_INFO, 1, null, curId);
			// MyBatis 必须等到 Mapper 方法执行
			// 此时处理动态sql：替换${} 并 #{} 改为 ? 绑定。预编译
//			UPDATE question_info
//			SET read_count = read_count + ?
//			WHERE question_id = ?
			// MB 使用 JDBC 发送到mysql 预编译
			// 执行sql 填充?

			qsInfo.setReadCount(qsInfo.getReadCount() + 1); // 给前端
		}

		return qsInfo;
	}
}