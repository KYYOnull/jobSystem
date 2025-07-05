package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface MenuMappers<T, P> extends BaseMapper<T, P> {

	T selectMenuByMenuId(@Param("menuId") Integer menuId);

	Integer updateMenuByMenuId(@Param("bean") T t, @Param("menuId") Integer menuId);

	Integer deleteMenuByMenuId(@Param("menuId") Integer menuId);

}
