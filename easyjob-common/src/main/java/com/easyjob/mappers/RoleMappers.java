package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;


public interface RoleMappers<T, P> extends BaseMapper<T, P> {

	T selectRoleByRoleId(@Param("roleId") Integer roleId);

	Integer updateRoleByRoleId(@Param("bean") T t, @Param("roleId") Integer roleId);

	Integer deleteRoleByRoleId(@Param("roleId") Integer roleId);

}
