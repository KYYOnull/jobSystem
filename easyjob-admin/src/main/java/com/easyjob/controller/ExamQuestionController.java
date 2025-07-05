package com.easyjob.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.dto.ImportErrorItem;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.ExamQuestionItem;
import com.easyjob.entity.query.ExamQuestionItemQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.enums.PostStatusEnum;
import com.easyjob.enums.QuestionTypeEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.service.ExamQuestionItemService;
import com.easyjob.utils.JsonUtils;
import com.easyjob.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.ExamQuestion;
import com.easyjob.entity.query.ExamQuestionQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.ExamQuestionService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController("examQuestionController")
@RequestMapping("/examQuestion")
public class ExamQuestionController extends BaseController{

	@Resource
	private ExamQuestionService exQsSvc;

	@Resource
	private ExamQuestionItemService exQsItSvc;

	// 查询列表 查询数量 分页查询 都用一个
	@RequestMapping("/loadDataList")
	@GlobalInterceptor()
	public ResponseVo<PaginationResultVo<ExamQuestion>> loadDataList(ExamQuestionQuery exQsQry){

		exQsQry.setOrderBy("question_id desc");
		exQsQry.setShowAnswer(true); // admin

		return getSuccessResponseVo(exQsSvc.findListByPage(exQsQry));
	}

	@RequestMapping("/saveExamQuestion")
	@GlobalInterceptor()
	public ResponseVo<Void> saveExamQuestion(
			HttpSession ses,
			@VerifyParam(required = true) ExamQuestion examQs,
			String qsItemLstJson
	){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);
		examQs.setCreateUserId(String.valueOf(userAdminDto.getUserId()));
		examQs.setCreateUserName(userAdminDto.getUserName());
		List<ExamQuestionItem> exQsItLst= new ArrayList<>();

		if(QuestionTypeEnum.TRUE_FALSE.getType().equals(examQs.getQuestionType())){ // 是判断
			if(StringTools.isEmpty(qsItemLstJson)){
				throw new BusinessException("当前为判断题，必须提供选项");
			}

			exQsItLst = JsonUtils.jsonArr2ObjList(qsItemLstJson, ExamQuestionItem.class);

		}

		exQsSvc.saveExamQuestion(examQs, exQsItLst, userAdminDto.getSuperAdmin());

		return getSuccessResponseVo(null);
	}

	@RequestMapping("/loadQuestionItem") // 加载考题选项
	@GlobalInterceptor()
	public ResponseVo<List<ExamQuestionItem>> loadQuestionItem(@VerifyParam(required = true)Integer qsId){

		ExamQuestionItemQuery exQsItQry = new ExamQuestionItemQuery();
		exQsItQry.setQuestionId(qsId); // 指定考题 查询其选项
		exQsItQry.setOrderBy("sort asc"); // 升序

		return getSuccessResponseVo(exQsItSvc.findListByParam(exQsItQry));
	}

	@RequestMapping("/delExamQuestion")
	@GlobalInterceptor()
	public ResponseVo<Void> delExamQuestion(
			HttpSession ses,
			@VerifyParam(required = true)Integer qsId
	){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);

		exQsSvc.delExamQuestionBatch(
				String.valueOf(qsId),
				userAdminDto.getSuperAdmin()?null:userAdminDto.getUserId()
		);

		return getSuccessResponseVo(null);
	}

	@RequestMapping("/delExamQuestionBatch")
	@GlobalInterceptor()
	public ResponseVo<Void> delExamQuestionBatch(
			HttpSession ses,
			@VerifyParam(required = true)String qsIds
	){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);
		exQsSvc.delExamQuestionBatch(qsIds, null);

		return getSuccessResponseVo(null);
	}

	@RequestMapping("/postExamQuestion")
	@GlobalInterceptor()
	public ResponseVo<Void> postExamQuestion(@VerifyParam(required = true)String qsIds){

		updateStatus(qsIds, PostStatusEnum.POST.getStatus()); // 发布

		return getSuccessResponseVo(null);
	}

	@RequestMapping("/cancelPostExamQuestion")
	@GlobalInterceptor()
	public ResponseVo<Void> cancelPostExamQuestion(@VerifyParam(required = true)String qsIds){

		updateStatus(qsIds, PostStatusEnum.NO_POST.getStatus()); // 发布

		return getSuccessResponseVo(null);
	}

	private void updateStatus(String qsIds, Integer status){

		ExamQuestionQuery exQsQry = new ExamQuestionQuery();
		exQsQry.setQsIds(qsIds.split(",")); // where ids source

		ExamQuestion exQs = new ExamQuestion();
		exQs.setStatus(status); // update value

		exQsSvc.updateByParam(exQs, exQsQry);

	}

	@RequestMapping("/showExamQuestionDetailNext")
	@GlobalInterceptor()
	public ResponseVo<ExamQuestion> showExamQuestionDetailNext(
			ExamQuestionQuery exQsQry, // mvc 会拦截并检索对象中的字段titleFuzzy, categoryNameFuzzy等 对其绑定
			@VerifyParam(required = true)Integer curId,
			Integer nxtType
	){
		// URL 查询参数、表单参数、或 application/x-www-form-urlencoded 请求体
		ExamQuestion exQsNxt = exQsSvc.showExQsDetailNext(exQsQry, curId, nxtType);

		return getSuccessResponseVo(exQsNxt);
	}

	@RequestMapping("/importExamQuestion")
	@GlobalInterceptor()
	public ResponseVo<List<ImportErrorItem>> importExamQuestion(HttpSession ses, MultipartFile file){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);

		List<ImportErrorItem> errItLst = exQsSvc.importExamQuestion(userAdminDto, file);

		return getSuccessResponseVo(errItLst);
	}

}