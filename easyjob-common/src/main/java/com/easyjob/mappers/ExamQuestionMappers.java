package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface ExamQuestionMappers<T, P> extends BaseMapper<T, P> {

	T selectExamQuestionByQuestionId(@Param("questionId") Integer questionId);

	Integer updateExamQuestionByQuestionId(@Param("bean") T t, @Param("questionId") Integer questionId);

	Integer deleteExamQuestionByQuestionId(@Param("questionId") Integer questionId);

	T showExQsDetailNext(@Param("query") P p);

}
