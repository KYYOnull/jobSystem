package com.easyjob.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.dto.ImportErrorItem;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.enums.PostStatusEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.QuestionInfo;
import com.easyjob.entity.query.QuestionInfoQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.QuestionInfoService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("questionInfoController")
@RequestMapping("/questionInfo")
public class QuestionInfoController extends BaseController{

	@Resource
	private QuestionInfoService qsInfoSvc;

	// 查询列表 查询数量 分页查询 都用一个
	@RequestMapping("loadDataList")
	@GlobalInterceptor
	public ResponseVo<PaginationResultVo<QuestionInfo>> loadDataList(QuestionInfoQuery query){

		query.setOrderBy("question_id desc");
		query.setQryTxtContent(true);

		return getSuccessResponseVo(qsInfoSvc.findListByPage(query));
	}

	@RequestMapping("saveQuestionInfo")
	@GlobalInterceptor
	public ResponseVo<Void> saveQuestionInfo(HttpSession ses, QuestionInfo bean){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);
		bean.setCreateUserId(String.valueOf(userAdminDto.getUserId()));
		bean.setCreateUserName(userAdminDto.getUserName());

		qsInfoSvc.saveQuestion(bean, userAdminDto.getSuperAdmin());

		return getSuccessResponseVo(null);
	}

	@RequestMapping("delQuestion")
	@GlobalInterceptor
	public ResponseVo<Void> delQuestion(HttpSession ses, @VerifyParam(required = true) Integer qsId){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);
		qsInfoSvc.delQuestionBatch(
				String.valueOf(qsId), // ses 使得无法伪造 userId
				userAdminDto.getSuperAdmin()? null: userAdminDto.getUserId()
		);

		return getSuccessResponseVo(null);
	}
	@RequestMapping("delQuestionBatch") // 超管
	@GlobalInterceptor
	public ResponseVo<Void> delQuestionBatch(@VerifyParam(required = true) String qsIds){

		qsInfoSvc.delQuestionBatch(qsIds, null);
		return getSuccessResponseVo(null);
	}


	@RequestMapping("postQuestion") // 未拆分批量发布接口
	@GlobalInterceptor
	public ResponseVo<Void> postQuestion(@VerifyParam(required = true) String qsIds){

		updateStatus(qsIds, PostStatusEnum.POST.getStatus()); // 改为已发布
		return getSuccessResponseVo(null);
	}
	@RequestMapping("cancelPost") // 未拆分批量发布接口
	@GlobalInterceptor
	public ResponseVo<Void> cancelPost(@VerifyParam(required = true) String qsIds){

		updateStatus(qsIds, PostStatusEnum.NO_POST.getStatus()); // 改为已发布
		return getSuccessResponseVo(null);
	} // 状态参数不要走页面 直接限制操作本接口的权限

	private void updateStatus(String qsIds, Integer stat){

		QuestionInfoQuery qsQry = new QuestionInfoQuery();
		qsQry.setQsIds(qsIds.split(","));
		QuestionInfo qsInfo = new QuestionInfo();
		qsInfo.setStatus(stat);
		qsInfoSvc.updateByParam(qsInfo, qsQry);
	}

	@RequestMapping("/importQuestion")
	@GlobalInterceptor
	public ResponseVo<List<ImportErrorItem>> importQuestion(HttpSession ses, MultipartFile file){

		SessionUserAdminDto dto = getUserAdminFromSession(ses);
		List<ImportErrorItem> errorLst = qsInfoSvc.importQuestion(file, dto);
		return getSuccessResponseVo(errorLst);
	}

	@RequestMapping("/showQuestionDetailNext") // 上一条下一条
	@GlobalInterceptor
	public ResponseVo<QuestionInfo> showQuestionDetailNext(
			QuestionInfoQuery qsQry,
			@VerifyParam(required = true)Integer curId,
			Integer nxtType
	){

		// 管理端查看detail不更新双流
		QuestionInfo qsInfo = qsInfoSvc.showDetailNext(qsQry, curId, nxtType, false);
		return getSuccessResponseVo(qsInfo);
	}


}