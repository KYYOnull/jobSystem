package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface AppFeedbackMappers<T, P> extends BaseMapper<T, P> {

	T selectAppFeedbackByFeedbackId(@Param("feedbackId") Integer feedbackId);

	Integer updateAppFeedbackByFeedbackId(@Param("bean") T t, @Param("feedbackId") Integer feedbackId);

	Integer deleteAppFeedbackByFeedbackId(@Param("feedbackId") Integer feedbackId);

	T selectAppFeedbackByFeedbackIdAndUserId(@Param("feedbackId") Integer feedbackId, @Param("userId") String userId);

	Integer updateAppFeedbackByFeedbackIdAndUserId(@Param("bean") T t, @Param("feedbackId") Integer feedbackId, @Param("userId") String userId);

	Integer deleteAppFeedbackByFeedbackIdAndUserId(@Param("feedbackId") Integer feedbackId, @Param("userId") String userId);

}
