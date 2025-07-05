package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamQuestionItemMappers<T, P> extends BaseMapper<T, P> {

	T selectExamQuestionItemByItemId(@Param("itemId") Integer itemId);

	Integer updateExamQuestionItemByItemId(@Param("bean") T t, @Param("itemId") Integer itemId);

	Integer delExQsItByItemId(@Param("itemId") Integer itemId);

	void delExQsItBatchByItIds(@Param("itemIdList")List<Integer> itemIdList);

	void delExQsItBatchByQsIds(
			@Param("qsIdList")String[] qsIdList,
			@Param("status")Integer status,
			@Param("userId")Integer userId
	); // 细粒度删除

}
