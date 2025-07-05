package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.vo.PaginationResultVo;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.AppFeedback;
import com.easyjob.entity.query.AppFeedbackQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.AppFeedbackService;
import com.easyjob.controller.BaseController;
import com.easyjob.controller.GlobalExceptionHandlerController;

@RestController("appFeedbackController")
@RequestMapping("/app")
public class AppFeedbackController extends BaseController{

	@Resource
	private AppFeedbackService appFeedbackService;


	@RequestMapping("/loadFeedback")
	@GlobalInterceptor()
	public ResponseVo<PaginationResultVo<AppFeedback>> loadDataList(AppFeedbackQuery query){

		query.setOrderBy("feedback_id desc");
		query.setFeedbackId(0);
		return getSuccessResponseVo(appFeedbackService.findListByPage(query));
	} // 分页查所有疑问

	@RequestMapping("/loadFeedbackReply")
	public ResponseVo<List<AppFeedback>> loadFeedbackReply(Integer pFeedbackId){

		AppFeedbackQuery feedbackQry = new AppFeedbackQuery();
		feedbackQry.setOrderBy("feedback_id asc"); // 聊天结果按时间
		feedbackQry.setFeedbackId(pFeedbackId);
		return getSuccessResponseVo(appFeedbackService.findListByParam(feedbackQry));
	} // 查某一根疑问下的所有解答

	@RequestMapping("/replyFeedback") // reply to pFeedbackId
	public ResponseVo<Void> replyFeedback(
			@VerifyParam(required = true) Integer pFeedbackId,
			@VerifyParam(required = true, max = 500) String content){

		AppFeedback reply = new AppFeedback(); // new a reply
		reply.setPFeedbackId(pFeedbackId);
		reply.setContent(content);

		this.appFeedbackService.replyFeedback(reply);

		return getSuccessResponseVo(null);
	} // 解答
}