package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface AppUpdateMappers<T, P> extends BaseMapper<T, P> {

	T selectAppUpdateById(@Param("id") Integer id);

	Integer updateAppUpdateById(@Param("bean") T t, @Param("id") Integer id);

	Integer deleteAppUpdateById(@Param("id") Integer id);

}
