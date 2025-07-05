package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;


public interface CategoryMappers<T, P> extends BaseMapper<T, P> {

	T selectCategoryByCategoryId(@Param("categoryId") Integer categoryId);

	Integer updateCategoryByCategoryId(@Param("bean") T t, @Param("categoryId") Integer categoryId);

	Integer deleteCategoryByCategoryId(@Param("categoryId") Integer categoryId);

	//	exam_question 和 question_info 中的 category_name 也一并更新
	void updateCategoryName(
			@Param("categoryName") String categoryName,
			@Param("categoryId") Integer categoryId
	);
}
