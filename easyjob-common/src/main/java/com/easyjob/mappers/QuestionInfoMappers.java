package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;


public interface QuestionInfoMappers<T, P> extends BaseMapper<T, P> {

	T selectQuestionInfoByQuestionId(@Param("questionId") Integer questionId);

	Integer updateQuestionInfoByQuestionId(@Param("bean") T t, @Param("questionId") Integer questionId);

	Integer deleteQuestionInfoByQuestionId(@Param("questionId") Integer questionId);

	// 批量删除 xml中实现新删除sql
	void delBatchByQsId(
			@Param("questionIdsArr") String[] questionIdsArr,
			@Param("status") Integer status,
			@Param("userId") Integer userId
	);

	T showDetailNext(@Param("query") P p);

}
