package com.easyjob.entity.query;


public class MenuQuery extends BaseQuery{

	private Integer menuId;

	private String menuName;

	private String menuNameFuzzy;

	private Integer menuType;

	private String menuUrl;

	private String menuUrlFuzzy;

	private Integer pId;

	/**
	 *  菜单排序
	 */
	private Integer sort;

	private String permissionCode;

	private String permissionCodeFuzzy;

	private String icon;

	private String iconFuzzy;

	private Boolean format2Tree; // 区分菜单结构

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

	public void setMenuNameFuzzy(String menuNameFuzzy) {this.menuNameFuzzy = menuNameFuzzy;}

	public String getMenuNameFuzzy() {return this.menuNameFuzzy;}

	public void setMenuUrlFuzzy(String menuUrlFuzzy) {this.menuUrlFuzzy = menuUrlFuzzy;}

	public String getMenuUrlFuzzy() {return this.menuUrlFuzzy;}

	public void setPermissionCodeFuzzy(String permissionCodeFuzzy) {this.permissionCodeFuzzy = permissionCodeFuzzy;}

	public String getPermissionCodeFuzzy() {return this.permissionCodeFuzzy;}

	public void setIconFuzzy(String iconFuzzy) {this.iconFuzzy = iconFuzzy;}

	public String getIconFuzzy() {return this.iconFuzzy;}

	public Boolean isFormat2Tree() {
		return format2Tree;
	}

	public void setFormat2Tree(boolean format2Tree) {
		this.format2Tree = format2Tree;
	}
}