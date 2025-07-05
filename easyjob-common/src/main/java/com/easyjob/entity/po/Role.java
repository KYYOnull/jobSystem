package com.easyjob.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easyjob.enums.DateTimePatternEnum;
import com.easyjob.utils.DateUtils;




/**
 * @description: 系统角色表
 * @author: kyyo
 * @date: 2025-06-15 16:27:06
 */
public class Role implements Serializable {
	/**
	 *  角色ID
	 */
	private Integer roleId;

	/**
	 *  角色名称
	 */
	private String roleName;

	/**
	 *  角色描述
	 */
	private String roleDesc;

	/**
	 *  创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 *  最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

	public void setRoleId(Integer roleId) {this.roleId = roleId;}

	public Integer getRoleId() {return this.roleId;}

	public void setRoleName(String roleName) {this.roleName = roleName;}

	public String getRoleName() {return this.roleName;}

	public void setRoleDesc(String roleDesc) {this.roleDesc = roleDesc;}

	public String getRoleDesc() {return this.roleDesc;}

	public void setCreateTime(Date createTime) {this.createTime = createTime;}

	public Date getCreateTime() {return this.createTime;}

	public void setLastUpdateTime(Date lastUpdateTime) {this.lastUpdateTime = lastUpdateTime;}

	public Date getLastUpdateTime() {return this.lastUpdateTime;}

	@Override
	public String toString(){return "角色ID: " + (roleId == null?"空": roleId) + ", 角色名称: " + (roleName == null?"空": roleName) + ", 角色描述: " + (roleDesc == null?"空": roleDesc) + ", 创建时间: " + (createTime == null?"空": DateUtils.format(createTime, DateTimePatternEnum._YYYY_MM_DD_HH_MM_SS.getPattern())) + ", 最后更新时间: " + (lastUpdateTime == null?"空": DateUtils.format(lastUpdateTime, DateTimePatternEnum._YYYY_MM_DD_HH_MM_SS.getPattern())) ;}
}