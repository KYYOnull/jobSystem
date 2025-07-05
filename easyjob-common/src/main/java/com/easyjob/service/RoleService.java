package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.Role;
import com.easyjob.entity.query.RoleQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface RoleService{

	List<Role> findListByParam(RoleQuery query);
	Integer findCntByParam(RoleQuery query);
	PaginationResultVo<Role> findListByPage(RoleQuery query);

	Integer add(Role bean);
	Integer addBatch(List<Role> bean);
	Integer addOrUpdateBatch(List<Role> bean);

	Integer updateByParam(Role bean, RoleQuery query);
	Integer deleteByParam(RoleQuery query);

	Role getRoleByRoleId(Integer roleId);
	Integer updateRoleByRoleId(Role bean, Integer roleId);
	Integer deleteRoleByRoleId(Integer roleId);

	void saveRole(Role ro, String menuIds, String halfMenuIds);

}