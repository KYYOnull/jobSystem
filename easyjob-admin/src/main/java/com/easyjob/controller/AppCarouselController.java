package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.vo.PaginationResultVo;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.AppCarousel;
import com.easyjob.entity.query.AppCarouselQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.AppCarouselService;
import com.easyjob.controller.BaseController;
import com.easyjob.controller.GlobalExceptionHandlerController;



@RestController("appCarouselController")
@RequestMapping("/appCarousel")
public class AppCarouselController extends BaseController{

	@Resource
	private AppCarouselService appCarouselService;

	@RequestMapping("loadDataList")
	@GlobalInterceptor()
	public ResponseVo<PaginationResultVo<AppCarousel>> loadDataList(AppCarouselQuery query){

		query.setOrderBy("sort asc");
		return getSuccessResponseVo(appCarouselService.findListByPage(query));
	}

	@RequestMapping("saveCarousel") // 加入新轮播图
	@GlobalInterceptor()
	public ResponseVo<Void> saveCarousel(AppCarousel bean){

		appCarouselService.saveCarousel(bean);
		return getSuccessResponseVo(null);
	}

	@RequestMapping("delCarousel") // 加入新轮播图
	@GlobalInterceptor()
	public ResponseVo<Void> delCarousel(@VerifyParam(required = true) Integer carouselId){

		appCarouselService.deleteAppCarouselByCarouselId(carouselId);
		return getSuccessResponseVo(null);
	}

	@RequestMapping("changeSort") // 加入新轮播图
	@GlobalInterceptor()
	public ResponseVo<Void> changeSort(@VerifyParam(required = true) String carouselIds){

		appCarouselService.changeSort(carouselIds);
		return getSuccessResponseVo(null);
	}
}