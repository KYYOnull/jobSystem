package com.easyjob.entity.query;


public class CategoryQuery extends BaseQuery{

	private Integer categoryId;

	private String categoryName;

	private String categoryNameFuzzy;

	private Integer sort;

	private String iconPath;

	private String iconPathFuzzy;

	private String bgColor;

	private String bgColorFuzzy;

	/**
	 *  0: 问题分类, 1: 考题分类, 2: 问题分类和考题分类
	 */
	private Integer type;
	private Integer[] types;

	public void setCategoryId(Integer categoryId) {this.categoryId = categoryId;}
	public Integer getCategoryId() {return this.categoryId;}

	public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
	public String getCategoryName() {return this.categoryName;}

	public void setSort(Integer sort) {this.sort = sort;}
	public Integer getSort() {return this.sort;}

	public void setIconPath(String iconPath) {this.iconPath = iconPath;}
	public String getIconPath() {return this.iconPath;}

	public void setBgColor(String bgColor) {this.bgColor = bgColor;}
	public String getBgColor() {return this.bgColor;}

	public void setType(Integer type) {this.type = type;}
	public Integer getType() {return this.type;}

	public void setCategoryNameFuzzy(String categoryNameFuzzy) {this.categoryNameFuzzy = categoryNameFuzzy;}
	public String getCategoryNameFuzzy() {return this.categoryNameFuzzy;}

	public void setIconPathFuzzy(String iconPathFuzzy) {this.iconPathFuzzy = iconPathFuzzy;}
	public String getIconPathFuzzy() {return this.iconPathFuzzy;}

	public void setBgColorFuzzy(String bgColorFuzzy) {this.bgColorFuzzy = bgColorFuzzy;}
	public String getBgColorFuzzy() {return this.bgColorFuzzy;}

	public Integer[] getTypes() {
		return types;
	}
	public void setTypes(Integer[] types) {
		this.types = types;
	}
}