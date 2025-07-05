package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.AppFeedback;
import com.easyjob.entity.query.AppFeedbackQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface AppFeedbackService{

	List<AppFeedback> findListByParam(AppFeedbackQuery query);
	Integer findCntByParam(AppFeedbackQuery query);
	PaginationResultVo<AppFeedback> findListByPage(AppFeedbackQuery query);

	Integer add(AppFeedback bean);
	Integer addBatch(List<AppFeedback> bean);
	Integer addOrUpdateBatch(List<AppFeedback> bean);

	Integer updateByParam(AppFeedback bean, AppFeedbackQuery query);
	Integer deleteByParam(AppFeedbackQuery query);

	AppFeedback getAppFeedbackByFeedbackId(Integer feedbackId);
	Integer updateAppFeedbackByFeedbackId(AppFeedback bean, Integer feedbackId);
	Integer deleteAppFeedbackByFeedbackId(Integer feedbackId);

	AppFeedback getAppFeedbackByFeedbackIdAndUserId(Integer feedbackId, String userId);
	Integer updateAppFeedbackByFeedbackIdAndUserId(AppFeedback bean, Integer feedbackId, String userId);
	Integer deleteAppFeedbackByFeedbackIdAndUserId(Integer feedbackId, String userId);

	void replyFeedback(AppFeedback bean);

}