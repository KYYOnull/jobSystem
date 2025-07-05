package com.easyjob.entity.query;



/**
 * @description: 角色对应的菜单权限表查询对象
 * @author: kyyo
 * @date: 2025-06-15 16:27:06
 */
public class Role2MenuQuery extends BaseQuery{
	/**
	 *  角色ID
	 */
	private Integer roleId;

	/**
	 *  菜单ID
	 */
	private Integer menuId;

	/**
	 *  0:半选 1：全选
	 */
	private Integer checkType;

	public void setRoleId(Integer roleId) {this.roleId = roleId;}

	public Integer getRoleId() {return this.roleId;}

	public void setMenuId(Integer menuId) {this.menuId = menuId;}

	public Integer getMenuId() {return this.menuId;}

	public void setCheckType(Integer checkType) {this.checkType = checkType;}

	public Integer getCheckType() {return this.checkType;}

}