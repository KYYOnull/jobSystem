package com.easyjob.entity.po;

import com.easyjob.annotation.VerifyParam;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	private Integer menuId;

	@VerifyParam(required = true, max = 32) // 提交新菜单时 需要校验的字段
	private String menuName;

	@VerifyParam(required = true)
	private Integer menuType;

	private String menuUrl;

	@VerifyParam(required = true)
	private Integer pId;

	@VerifyParam(required = true)
	private Integer sort;

	@VerifyParam(required = true, max = 50)
	private String permissionCode;

	@VerifyParam(max = 50) // 非必填
	private String icon;

	private List<Menu> children; // 菜单实体的子节点

	public void setMenuId(Integer menuId) {this.menuId = menuId;}

	public Integer getMenuId() {return this.menuId;}

	public void setMenuName(String menuName) {this.menuName = menuName;}

	public String getMenuName() {return this.menuName;}

	public void setMenuType(Integer menuType) {this.menuType = menuType;}

	public Integer getMenuType() {return this.menuType;}

	public void setMenuUrl(String menuUrl) {this.menuUrl = menuUrl;}

	public String getMenuUrl() {return this.menuUrl;}

	public void setPId(Integer pId) {this.pId = pId;}

	public Integer getPId() {return this.pId;}

	public void setSort(Integer sort) {this.sort = sort;}

	public Integer getSort() {return this.sort;}

	public void setPermissionCode(String permissionCode) {this.permissionCode = permissionCode;}

	public String getPermissionCode() {return this.permissionCode;}

	public void setIcon(String icon) {this.icon = icon;}

	public String getIcon() {return this.icon;}

	@Override
	public String toString(){return "menu_id,自增主键: " + (menuId == null?"空": menuId) + ", 菜单名: " + (menuName == null?"空": menuName) + ", 菜单类型 0:菜单 1：按钮: " + (menuType == null?"空": menuType) + ", 菜单跳转到的地址: " + (menuUrl == null?"空": menuUrl) + ", 上级菜单ID: " + (pId == null?"空": pId) + ", 菜单排序: " + (sort == null?"空": sort) + ", 权限编码: " + (permissionCode == null?"空": permissionCode) + ", 图标: " + (icon == null?"空": icon) ;}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}