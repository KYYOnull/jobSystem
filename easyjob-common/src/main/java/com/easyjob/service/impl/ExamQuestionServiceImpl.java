package com.easyjob.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.dto.ImportErrorItem;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.Category;
import com.easyjob.entity.po.ExamQuestionItem;
import com.easyjob.entity.po.QuestionInfo;
import com.easyjob.entity.query.ExamQuestionItemQuery;
import com.easyjob.enums.*;
import com.easyjob.exception.BusinessException;
import com.easyjob.mappers.ExamQuestionItemMappers;
import com.easyjob.service.CategoryService;
import com.easyjob.utils.ExcelUtils;
import com.easyjob.utils.VerifyUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.ExamQuestion;
import com.easyjob.entity.query.ExamQuestionQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.ExamQuestionService;
import com.easyjob.mappers.ExamQuestionMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("examQuestionService")
public class ExamQuestionServiceImpl implements ExamQuestionService{

	@Resource
	private ExamQuestionMappers<ExamQuestion, ExamQuestionQuery> exQsMapper;

	@Resource
	private CategoryService cateSvc;

	@Resource
	private ExamQuestionItemMappers<ExamQuestionItem, ExamQuestionItemQuery> exQsItMapper;

	@Override
	public List<ExamQuestion> findListByParam(ExamQuestionQuery query){

		return this.exQsMapper.selectList(query);
	}
	@Override
	public Integer findCntByParam(ExamQuestionQuery query){

		return this.exQsMapper.selectCount(query);
	}
	@Override
	public PaginationResultVo<ExamQuestion> findListByPage(ExamQuestionQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<ExamQuestion> list = this.findListByParam(query);
		PaginationResultVo<ExamQuestion> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(ExamQuestion bean){

		return this.exQsMapper.insert(bean);
	}
	@Override
	public Integer addBatch(List<ExamQuestion> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.exQsMapper.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<ExamQuestion> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.exQsMapper.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(ExamQuestion bean, ExamQuestionQuery query) {
		StringTools.checkParam(query);
		return this.exQsMapper.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(ExamQuestionQuery query) {
		StringTools.checkParam(query);
		return this.exQsMapper.deleteByParam(query);
	}

	@Override
	public ExamQuestion getExamQuestionByQuestionId(Integer questionId){

		return this.exQsMapper.selectExamQuestionByQuestionId(questionId);
	}
	@Override
	public Integer updateExamQuestionByQuestionId(ExamQuestion bean, Integer questionId){

		return this.exQsMapper.updateExamQuestionByQuestionId(bean, questionId);
	}
	@Override
	public Integer deleteExamQuestionByQuestionId(Integer questionId){

		return this.exQsMapper.deleteExamQuestionByQuestionId(questionId);
	}

	@Override // 新增 修改
	@Transactional(rollbackFor = Exception.class)
	public void saveExamQuestion(ExamQuestion exQsNeo, List<ExamQuestionItem> exQsItLst, Boolean isSuper) {

		// 关联增改题目exQsNeo 和其选项exQsItLst
		Category cateDb = cateSvc.getCategoryByCategoryId(exQsNeo.getCategoryId()); // 前端勾选了题目类型 查分类名称
		if(cateDb==null) throw new BusinessException(ResponseCodeEnum.CODE_600);
		exQsNeo.setCategoryName(cateDb.getCategoryName());

		Integer qsId = exQsNeo.getQuestionId(); // 增改题目
		if(null == qsId){ // 新增

			exQsNeo.setCreateTime(new Date());
			this.exQsMapper.insert(exQsNeo); // 新增后 exQsNeo得到新id
		}else { // 修改

			exQsNeo.setQuestionType(null); // 禁止修改题型 因为前端不支持

			ExamQuestion exQsDb = this.exQsMapper.selectExamQuestionByQuestionId(qsId); // 旧信息
			if(!exQsDb.getCreateUserId().equals(exQsNeo.getCreateUserId()) && !isSuper){
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			} // 能否修改
			exQsNeo.setCreateUserId(null);
			exQsNeo.setCreateUserName(null);
			exQsNeo.setCreateTime(null); // 禁止修改 擦除

			this.exQsMapper.updateExamQuestionByQuestionId(exQsNeo, qsId);
		} // 增改题目

		exQsItLst.forEach(it -> it.setQuestionId(exQsNeo.getQuestionId()));  // it关联qs

		List<ExamQuestionItem> updateItLst = exQsItLst.stream()
				.filter(it -> it.getItemId() != null)
				.collect(Collectors.toList()); // 带id的
		List<ExamQuestionItem> insertItLst = exQsItLst.stream()
				.filter(it -> it.getItemId() == null)
				.collect(Collectors.toList());

		Map<Integer, ExamQuestionItem> updateItMap = updateItLst.stream().collect(Collectors.toMap(
				ExamQuestionItem::getItemId,
				Function.identity(), // 自身 value
				(d1, d2)-> d2 // id冲突则覆盖
		)); // id : it

		ExamQuestionItemQuery exQsItQry = new ExamQuestionItemQuery();
		exQsItQry.setQuestionId(qsId); // 查找 问题下的 所有旧选项
		List<ExamQuestionItem> exQsItLstDb = exQsItMapper.selectList(exQsItQry);

		List<Integer> delLst = new ArrayList<>();
		if(!updateItMap.isEmpty()){
			for(ExamQuestionItem itDb: exQsItLstDb){ // 库中的 若不在更新提交中 则说明希望将其删除
				if(updateItMap.get(itDb.getItemId()) == null){
					delLst.add(itDb.getItemId()); // 旧选项中的待删除项
				}
			}
		}

		if(!insertItLst.isEmpty()){
			exQsItMapper.insertBatch(insertItLst);
		}

//		if(!updateItLst.isEmpty()){
//			exQsItMapper.insertOrUpdateBatch(updateItLst); 无唯一键 无法当做updatebatch使用
//		}

		for(ExamQuestionItem it: updateItLst){
			exQsItMapper.updateExamQuestionItemByItemId(it, it.getItemId());
		}

		if(!delLst.isEmpty()){
			exQsItMapper.delExQsItBatchByItIds(delLst);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delExamQuestionBatch(String qsIds, Integer userId) {

		String[] qsIdArr = qsIds.split(","); // 单个id多个ids 通用
		if(userId != null){

			// 删前检查
			ExamQuestionQuery exQsQry = new ExamQuestionQuery();
			exQsQry.setQsIds(qsIdArr);
			List<ExamQuestion> exQsLstDb = this.exQsMapper.selectList(exQsQry);

			List<ExamQuestion> curUserExQsLst = exQsLstDb.stream().filter(
					it -> !it.getCreateUserId().equals(String.valueOf(userId))
			).collect(Collectors.toList()); // 与全局qs比对

			if(!curUserExQsLst.isEmpty()){ // 删别人的
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
		} // 非管理员

		// 关联 ExamQuestion 的 ExQsIt  根据 QsIds 删除其关联的 items
		// sql 做了更严格条件检查
		exQsItMapper.delExQsItBatchByQsIds(qsIdArr, PostStatusEnum.NO_POST.getStatus(), userId);

		// 最普通的删除 塞值查询删
		ExamQuestionQuery exQsQry = new ExamQuestionQuery();
		exQsQry.setQsIds(qsIdArr); // qsIds expected to delete
		exQsQry.setStatus(PostStatusEnum.NO_POST.getStatus());
		exQsMapper.deleteByParam(exQsQry);
	}

	@Override
	public ExamQuestion showExQsDetailNext(ExamQuestionQuery exQsQry, Integer curId, Integer nxtType) {

		if(nxtType == null){
			exQsQry.setQuestionId(curId); // 直接问题进来
		}else { // 左右滑动进来
			exQsQry.setNxtType(nxtType);
			exQsQry.setCurId(curId);
		}

		ExamQuestion exQs = exQsMapper.showExQsDetailNext(exQsQry); // 取前一或后一

		if(exQs == null ){
			if(nxtType == null){
				throw new BusinessException("当前 考题内容 不存在");
			}else if (nxtType == -1) {
				throw new BusinessException("已经是第一个考题"); // 没有上一个
			}else if (nxtType == 1) {
				throw new BusinessException("已经是最后一个考题"); // 没有下一个
			}
		}else { // 有题目 则关联选项

			ExamQuestionItemQuery exQsItQry = new ExamQuestionItemQuery();
			exQsItQry.setQuestionId(exQs.getQuestionId());
			exQsItQry.setOrderBy("sort asc");
			List<ExamQuestionItem> exQsItLst = exQsItMapper.selectList(exQsItQry);
			exQs.setExQsItLst(exQsItLst);

		}

		return exQs;
	}

	@Override
	public List<ImportErrorItem> importExamQuestion(SessionUserAdminDto userAdminDto, MultipartFile file) {

		List<Category> cateLst= cateSvc.loadAllCategoryByType(CategoryTypeEnum.EXAM.getType()); // 考试相关分类
		Map<String, Category> cateMap = cateLst.stream().collect(Collectors.toMap(
				Category::getCategoryName,
				Function.identity(),
				(d1, d2) -> d2)
		); // 用于检查excel的分类

		// 读excel （row, cell) List<List<String>>
		List<List<String>> datExcelLst = ExcelUtils.readExcel(file, Constants.EXCEL_TITLE_EXAM_QUESTION, 1);

		// 解析excel 写库
		List<ImportErrorItem> errRowLst = new ArrayList<>(); // 所有出错行
		List<ExamQuestion> exQsLst = new ArrayList<>();
		Integer viewRowIdx= 2; // 标题行
		for(List<String> r: datExcelLst) {

			if (errRowLst.size() > Constants.LEN_50) throw new BusinessException("错误数量超过" + Constants.LEN_50 + "行,可能模板错误");

			viewRowIdx++; // content row
			List<String> errItLst = new ArrayList<>(); // curRow err record

			Integer celId = 0;
			String title= r.get(celId++);
			if(StringTools.isEmpty(title) || title.length()> Constants.LEN_150) errItLst.add("标题不能空，且长度不能超过"+ Constants.LEN_150);

			String cateName= r.get(celId++);
			Category cate = cateMap.get(cateName);
			if(null == cate) errItLst.add("分类不存在");

			String difDeg= r.get(celId++);
			Integer difDegInt= null;
			if(VerifyUtils.verify(VerifyRegexEnum.POSITIVE_INTEGER, difDeg)){
				difDegInt= Integer.parseInt(difDeg);
				if(difDegInt > 5) errItLst.add("难度只能是1-5数字");
			}else {
				errItLst.add("难度只能是正整数");
			}

			String qsType= r.get(celId++);
			QuestionTypeEnum qsTypeEnum= QuestionTypeEnum.getByDesc(qsType);
			if(qsTypeEnum == null) errItLst.add("问题类型不存在");

			String qsItems= r.get(celId++);
			if(qsTypeEnum != null &&
					qsTypeEnum != QuestionTypeEnum.TRUE_FALSE &&
					StringTools.isEmpty(qsItems)){
				errItLst.add("问题选项不能空"); // 判断题无选项
			}

			List<String> exQsItStrLst = new ArrayList<>();
			if(!StringTools.isEmpty(qsItems)){
				exQsItStrLst= Arrays.asList(qsItems.split("\n")); // 分割单元格

				exQsItStrLst = exQsItStrLst.stream()
						.filter(it -> !StringTools.isEmpty(it.trim()))
						.collect(Collectors.toList());
			} // 选项

			String answer= r.get(celId++);
			if(!StringTools.isEmpty(answer) && qsTypeEnum != null){
				switch (qsTypeEnum){
					case TRUE_FALSE:
						if(Constants.TRUE_STR.equals(answer)){

							answer= Constants.ONE_STR;
						}else if(Constants.FALSE_STR.equals(answer)){

							answer= Constants.ZERO_STR;
						}else {

							errItLst.add("判断题的答案只能是正确或错误");
						}
						break;
					case RADIO:
						Integer radioAnswerIdx= Arrays.binarySearch(Constants.LETTERS, answer);
						if(radioAnswerIdx >= 0){

							answer= String.valueOf(radioAnswerIdx); // 存第几个正确
						}else {

							errItLst.add("单选答案设置范围错误");
						}
						break;
					case CHECK_BOX:
						StringBuilder answerBuilder= new StringBuilder();
						answer= answer.replace("\n", "");
						String[] answerArr = answer.split("、");
						boolean answerOk= true;
						for(String it: answerArr){
							it= it.trim();
							int checkBoxAnswerIdx= Arrays.binarySearch(Constants.LETTERS, it);
							if(checkBoxAnswerIdx >= 0){
								answerBuilder.append(it).append(","); // to db
							}else {
								answerOk= false;
								break;
							}
						}
						if(answerOk){

							answer= answerBuilder.toString();
							answer= answer.substring(0, answer.lastIndexOf(","));
						}else {

							errItLst.add("多选选项范围错误");
						}
						break;
				}
			}else if(StringTools.isEmpty(answer)){

				errItLst.add("答案不能为空");
			}

			String desc= r.get(celId++);
			String answerAnalysis= r.get(celId++);
			if(StringTools.isEmpty(answerAnalysis)) errItLst.add("答案解析不能空");
			if(!errItLst.isEmpty()){
				ImportErrorItem errIt = new ImportErrorItem();
				errIt.setRowNum(viewRowIdx);
				errIt.setErrorItemLst(errItLst); // 本行存在的错误
				errRowLst.add(errIt);
				continue;
            }

			// 导入的问题写库
			ExamQuestion exQs = new ExamQuestion();
			exQs.setTitle(title);
			exQs.setCategoryName(cateName);
			exQs.setCategoryId(cate.getCategoryId());
			exQs.setDifficultyLevel(difDegInt);
			exQs.setQuestionType(qsTypeEnum.getType());
			exQs.setQuestionAnswer(answer);
			exQs.setQuestion(desc);
			exQs.setCreateTime(new Date());
			exQs.setStatus(PostStatusEnum.NO_POST.getStatus());
			exQs.setAnswerAnalysis(answerAnalysis);
			exQs.setCreateUserId(String.valueOf(userAdminDto.getUserId()));
			exQs.setCreateUserName(userAdminDto.getUserName());

			Collections.sort(exQsItStrLst); // 入库前 对选项排序
			List<ExamQuestionItem> itLst= new ArrayList<>(); // 问题下的所有选项
			int sort = 0;
			for(String it: exQsItStrLst){
				ExamQuestionItem exQsIt = new ExamQuestionItem();
				it= it.substring(it.indexOf("、") + 1);
				exQsIt.setTitle(it);
				exQsIt.setSort(++sort);
				itLst.add(exQsIt);
			} // 去掉 ABCD

			exQs.setExQsItLst(itLst); // 问题下的所有选项
			exQsLst.add(exQs); // 本行结束
		}

		// 数据关系维护完毕 入库

		if(exQsLst.isEmpty()) return errRowLst; // 无待插入

		this.exQsMapper.insertBatch(exQsLst); // tab1

		List<ExamQuestionItem> allExQsItLst= new ArrayList<>(); // tab2
		for(ExamQuestion exQs: exQsLst){
			for(ExamQuestionItem it: exQs.getExQsItLst()){
				it.setQuestionId(exQs.getQuestionId()); // exQs insert后 有了id
			}
			allExQsItLst.addAll((exQs.getExQsItLst())); // 插入后返回 it 的 id
		}
		if(!allExQsItLst.isEmpty()){ // excel中全部item插入
			this.exQsItMapper.insertBatch(allExQsItLst);
		}

		return errRowLst;
	}
}