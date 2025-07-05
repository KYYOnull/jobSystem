package com.easyjob.mappers;


import org.apache.ibatis.annotations.Param;

public interface ShareInfoMappers<T, P> extends BaseMapper<T, P> {

	T selectShareInfoByShareId(@Param("shareId") Integer shareId);

	Integer updateShareInfoByShareId(@Param("bean") T t, @Param("shareId") Integer shareId);

	Integer deleteShareInfoByShareId(@Param("shareId") Integer shareId);

	T showDetailNext(@Param("query") P p);

}
