package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.Role2Menu;
import com.easyjob.entity.query.Role2MenuQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface Role2MenuService{

	List<Role2Menu> findListByParam(Role2MenuQuery query);
	Integer findCntByParam(Role2MenuQuery query);
	PaginationResultVo<Role2Menu> findListByPage(Role2MenuQuery query);

	Integer add(Role2Menu bean);
	Integer addBatch(List<Role2Menu> bean);
	Integer addOrUpdateBatch(List<Role2Menu> bean);

	Integer updateByParam(Role2Menu bean, Role2MenuQuery query);
	Integer deleteByParam(Role2MenuQuery query);

	Role2Menu getRole2MenuByRoleIdAndMenuId(Integer roleId, Integer menuId);
	Integer updateRole2MenuByRoleIdAndMenuId(Role2Menu bean, Integer roleId, Integer menuId);
	Integer deleteRole2MenuByRoleIdAndMenuId(Integer roleId, Integer menuId);

}