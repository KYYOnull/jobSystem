package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.Category;
import com.easyjob.entity.query.CategoryQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.enums.CategoryTypeEnum;

public interface CategoryService{

	List<Category> findListByParam(CategoryQuery query);
	Integer findCntByParam(CategoryQuery query);
	PaginationResultVo<Category> findListByPage(CategoryQuery query);

	Integer add(Category bean);
	Integer addBatch(List<Category> bean);
	Integer addOrUpdateBatch(List<Category> bean);

	Integer updateByParam(Category bean, CategoryQuery query);
	Integer deleteByParam(CategoryQuery query);

	Category getCategoryByCategoryId(Integer categoryId);
	Integer updateCategoryByCategoryId(Category bean, Integer categoryId);
	Integer deleteCategoryByCategoryId(Integer categoryId);

	void saveCategory(Category cate);
	void changeSort(String cateIds);
	List<Category> loadAllCategoryByType(Integer type);

}