package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.AppCarousel;
import com.easyjob.entity.query.AppCarouselQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface AppCarouselService{

	List<AppCarousel> findListByParam(AppCarouselQuery query);
	Integer findCntByParam(AppCarouselQuery query);
	PaginationResultVo<AppCarousel> findListByPage(AppCarouselQuery query);

	Integer add(AppCarousel bean);
	Integer addBatch(List<AppCarousel> bean);
	Integer addOrUpdateBatch(List<AppCarousel> bean);

	Integer updateByParam(AppCarousel bean, AppCarouselQuery query);
	Integer deleteByParam(AppCarouselQuery query);

	AppCarousel getAppCarouselByCarouselId(Integer carouselId);
	Integer updateAppCarouselByCarouselId(AppCarousel bean, Integer carouselId);
	Integer deleteAppCarouselByCarouselId(Integer carouselId);

	void saveCarousel(AppCarousel bean);
	void changeSort(String carouselIds);

}