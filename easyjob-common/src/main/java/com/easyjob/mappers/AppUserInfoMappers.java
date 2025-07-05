package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface AppUserInfoMappers<T, P> extends BaseMapper<T, P> {

	T selectAppUserInfoByUserId(@Param("userId") String userId);

	Integer updateAppUserInfoByUserId(@Param("bean") T t, @Param("userId") String userId);

	Integer deleteAppUserInfoByUserId(@Param("userId") String userId);

	T selectAppUserInfoByEmail(@Param("email") String email);

	Integer updateAppUserInfoByEmail(@Param("bean") T t, @Param("email") String email);

	Integer deleteAppUserInfoByEmail(@Param("email") String email);

}
