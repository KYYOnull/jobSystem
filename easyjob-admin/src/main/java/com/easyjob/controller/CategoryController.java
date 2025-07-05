package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.enums.CategoryTypeEnum;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.Category;
import com.easyjob.entity.query.CategoryQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.CategoryService;

@RestController("categoryController")
@RequestMapping("/category")
public class CategoryController extends BaseController{

	@Resource
	private CategoryService cateSvc;

	@RequestMapping("loadAllCategory")
	@GlobalInterceptor() // permissionCode=
	public ResponseVo<List<Category>> loadAllCategory(CategoryQuery query){

		query.setOrderBy("sort asc"); // 排序
		return getSuccessResponseVo(cateSvc.findListByParam(query));
	}

	@RequestMapping("saveCategory") // 新增分类
	@GlobalInterceptor() // permissionCode=
	public ResponseVo<Void> saveCategory(Category cate){

		cateSvc.saveCategory(cate);
		return getSuccessResponseVo(null);
	}

	@RequestMapping("delCategory")
	@GlobalInterceptor() // permissionCode=
	public ResponseVo<Void> delCategory(@VerifyParam(required = true) Integer cateId){

		cateSvc.deleteCategoryByCategoryId(cateId);
		return getSuccessResponseVo(null);
	}

	@RequestMapping("changeSort") // 上下移
	@GlobalInterceptor() // permissionCode=
	public ResponseVo<Void> changeSort(@VerifyParam(required = true) String cateIds){

		cateSvc.changeSort(cateIds);
		return getSuccessResponseVo(null);
	}

	@RequestMapping("loadAllCategory4Select") // 根据类型 筛选分类
	@GlobalInterceptor() // permissionCode=
	public ResponseVo<List<Category>> loadAllCategory4Select(@VerifyParam(required = true) Integer type){

		List<Category> cateLst = cateSvc.loadAllCategoryByType(type);
		return getSuccessResponseVo(cateLst);
	}

}