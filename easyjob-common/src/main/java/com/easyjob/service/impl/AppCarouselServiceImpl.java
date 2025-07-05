package com.easyjob.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.AppCarousel;
import com.easyjob.entity.query.AppCarouselQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.AppCarouselService;
import com.easyjob.mappers.AppCarouselMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("appCarouselService")
public class AppCarouselServiceImpl implements AppCarouselService{

	@Resource
	private AppCarouselMappers<AppCarousel, AppCarouselQuery> appCarouselMappers;

	@Override
	public List<AppCarousel> findListByParam(AppCarouselQuery query){

		return this.appCarouselMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(AppCarouselQuery query){

		return this.appCarouselMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<AppCarousel> findListByPage(AppCarouselQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<AppCarousel> list = this.findListByParam(query);
		PaginationResultVo<AppCarousel> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}
	@Override
	public Integer add(AppCarousel bean){

		return this.appCarouselMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<AppCarousel> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.appCarouselMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<AppCarousel> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.appCarouselMappers.insertOrUpdateBatch(listBean);
	}
	@Override
	public Integer updateByParam(AppCarousel bean, AppCarouselQuery query) {
		StringTools.checkParam(query);
		return this.appCarouselMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(AppCarouselQuery query) {
		StringTools.checkParam(query);
		return this.appCarouselMappers.deleteByParam(query);
	}


	@Override
	public AppCarousel getAppCarouselByCarouselId(Integer carouselId){

		return this.appCarouselMappers.selectAppCarouselByCarouselId(carouselId);
	}
	@Override
	public Integer updateAppCarouselByCarouselId(AppCarousel bean, Integer carouselId){

		return this.appCarouselMappers.updateAppCarouselByCarouselId(bean, carouselId);
	}
	@Override
	public Integer deleteAppCarouselByCarouselId(Integer carouselId){

		return this.appCarouselMappers.deleteAppCarouselByCarouselId(carouselId);
	}

	@Override
	public void saveCarousel(AppCarousel bean) {

		if(bean.getCarouselId() == null){ // insert
			AppCarouselQuery carouselQry = new AppCarouselQuery(); // where
			Integer cnt = this.appCarouselMappers.selectCount(carouselQry); // select * from tb no where
			bean.setSort(cnt + 1); // 新轮播顺序 可能重复 如共4个，删除id=3再加入新的，那么sort=4就有两个
			this.appCarouselMappers.insert(bean);
		}else {
			this.appCarouselMappers.updateAppCarouselByCarouselId(bean, bean.getCarouselId());
		}
	}

	@Override
	public void changeSort(String caroIds) {

		String[] caroIdArr= caroIds.split(",");
		Integer idx = 1;
		for(String caroIdStr : caroIdArr){
			Integer caroId = Integer.parseInt(caroIdStr);
			AppCarousel bean = new AppCarousel();
			bean.setSort(idx);
			this.appCarouselMappers.updateAppCarouselByCarouselId(bean, caroId);
			idx++;
		} // 按数组顺序为其编号
	}
}