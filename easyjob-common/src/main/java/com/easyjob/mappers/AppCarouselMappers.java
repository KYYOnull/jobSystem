package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface AppCarouselMappers<T, P> extends BaseMapper<T, P> {

	T selectAppCarouselByCarouselId(@Param("carouselId") Integer carouselId);

	Integer updateAppCarouselByCarouselId(@Param("bean") T t, @Param("carouselId") Integer carouselId);

	Integer deleteAppCarouselByCarouselId(@Param("carouselId") Integer carouselId);

}
