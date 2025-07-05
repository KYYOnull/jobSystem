package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.Role;
import com.easyjob.entity.query.RoleQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.RoleService;

@RestController("roleController")
@RequestMapping("/settings")
public class RoleController extends BaseController{

	@Resource
	private RoleService roleService;

	@RequestMapping("/loadRoles")
	@GlobalInterceptor
	public ResponseVo loadRoles(RoleQuery query){

		query.setOrderBy("create_time desc"); // 最新创建
		return getSuccessResponseVo(roleService.findListByPage(query));
	}

	@RequestMapping("/saveRole")
	@GlobalInterceptor
	public ResponseVo saveRole(
			@VerifyParam Role bean,
			@VerifyParam(required = true) String menuIds, // 相关菜单
			String halfMenuIds // 记录是 全选 半选  无子菜单则不会出现半选
	){
		// 新增角色时 为其分配菜单
		this.roleService.saveRole(bean, menuIds, halfMenuIds);
		return getSuccessResponseVo(null);
	}

}