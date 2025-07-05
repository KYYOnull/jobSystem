package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;

public interface Role2MenuMappers<T, P> extends BaseMapper<T, P> {


	T selectRole2MenuByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

	Integer updateRole2MenuByRoleIdAndMenuId(@Param("bean") T t, @Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

	Integer deleteRole2MenuByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

}
