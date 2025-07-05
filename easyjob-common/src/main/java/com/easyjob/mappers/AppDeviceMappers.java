package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface AppDeviceMappers<T, P> extends BaseMapper<T, P> {

	T selectAppDeviceByDeviceId(@Param("deviceId") String deviceId);

	Integer updateAppDeviceByDeviceId(@Param("bean") T t, @Param("deviceId") String deviceId);

	Integer deleteAppDeviceByDeviceId(@Param("deviceId") String deviceId);

}
