package com.easyjob.service.impl;

import java.util.List;

import com.easyjob.enums.CategoryTypeEnum;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.Category;
import com.easyjob.entity.query.CategoryQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.CategoryService;
import com.easyjob.mappers.CategoryMappers;
import com.easyjob.entity.query.SimplePage;import com.easyjob.enums.PageSize;
import org.springframework.transaction.annotation.Transactional;
import com.easyjob.utils.StringTools;


@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{

	@Resource
	private CategoryMappers<Category, CategoryQuery> cateMapper;

	@Override
	public List<Category> findListByParam(CategoryQuery query){

		return this.cateMapper.selectList(query);
	}
	@Override
	public Integer findCntByParam(CategoryQuery query){

		return this.cateMapper.selectCount(query);
	}
	@Override
	public PaginationResultVo<Category> findListByPage(CategoryQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<Category> list = this.findListByParam(query);

		return new PaginationResultVo<>(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
	}

	@Override
	public Integer add(Category bean){

		return this.cateMapper.insert(bean);
	}
	@Override
	public Integer addBatch(List<Category> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.cateMapper.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<Category> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.cateMapper.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(Category bean, CategoryQuery query) {
		StringTools.checkParam(query);
		return this.cateMapper.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(CategoryQuery query) {
		StringTools.checkParam(query);
		return this.cateMapper.deleteByParam(query);
	}

	@Override
	public Category getCategoryByCategoryId(Integer categoryId){

		return this.cateMapper.selectCategoryByCategoryId(categoryId);
	}
	@Override
	public Integer updateCategoryByCategoryId(Category bean, Integer categoryId){

		return this.cateMapper.updateCategoryByCategoryId(bean, categoryId);
	}
	@Override
	public Integer deleteCategoryByCategoryId(Integer categoryId){

		return this.cateMapper.deleteCategoryByCategoryId(categoryId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveCategory(Category cate) {

		if(null== cate.getCategoryId()){
			CategoryQuery cateQry = new CategoryQuery();
			Integer cnt = this.cateMapper.selectCount(cateQry);
			cate.setSort(cnt+1);
			this.cateMapper.insert(cate);
		}else {
			this.cateMapper.updateCategoryByCategoryId(cate, cate.getCategoryId());
		}

		CategoryQuery cateQry = new CategoryQuery();
		cateQry.setCategoryName(cate.getCategoryName());
		cateQry.setType(cate.getType());
		if(this.cateMapper.selectCount(cateQry)>1) throw new BusinessException("分类重复");
		if(null == cate.getCategoryName()) return;

		// 可分类 插入后id同步回来
		Category dbCate = cateMapper.selectCategoryByCategoryId(cate.getCategoryId());
		if(!dbCate.getCategoryName().equals(cate.getCategoryName())){
			cateMapper.updateCategoryName(
					cate.getCategoryName(), cate.getCategoryId()
			);

		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeSort(String cateIds) {

		String[] cateIdArr= cateIds.split(",");
		for(int i=0;i<cateIdArr.length;i++){

			int cateId = Integer.parseInt(cateIdArr[i]);
			Category cate = new Category();
			cate.setSort(i+1); // 重置先后
			cateMapper.updateCategoryByCategoryId(cate, cateId);
		}
	}

	@Override
	public List<Category> loadAllCategoryByType(Integer type) {

		CategoryTypeEnum typeEnum = CategoryTypeEnum.getByType(type); // check type
		if(typeEnum == null) throw new BusinessException(ResponseCodeEnum.CODE_600);

		CategoryQuery cateQry = new CategoryQuery();
		cateQry.setOrderBy("sort asc");
		cateQry.setTypes(new Integer[]{
				typeEnum.getType(),
				CategoryTypeEnum.QUESTION_EXAM.getType()
		}); // A , A+B

		return this.cateMapper.selectList(cateQry);

	}
}