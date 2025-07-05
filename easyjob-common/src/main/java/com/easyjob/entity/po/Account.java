package com.easyjob.entity.po;

import java.io.Serializable;
import java.util.Date;

import com.easyjob.annotation.VerifyParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easyjob.enums.DateTimePatternEnum;
import com.easyjob.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

// Spring MVC 会根据参数名，自动将请求参数（URL 中的 phone 和 password）映射到 POJO 对象中的对应字段
// 要求字段名与请求参数名一致 getter/setter
// 参数没有使用 @RequestBody （否则会走 JSON 解析）


public class Account implements Serializable {

	private Integer userId;

	@VerifyParam(required=true)
	private String phone;

	private String userName;

	@JsonIgnore
	@VerifyParam(required = true)
	private String password;

	private String position;

	private Integer status;

	private String roles;

	/**
	 *  创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public void setUserId(Integer userId) {this.userId = userId;}

	public Integer getUserId() {return this.userId;}

	public void setPhone(String phone) {this.phone = phone;}

	public String getPhone() {return this.phone;}

	public void setUserName(String userName) {this.userName = userName;}

	public String getUserName() {return this.userName;}

	public void setPassword(String password) {this.password = password;}

	public String getPassword() {return this.password;}

	public void setPosition(String position) {this.position = position;}

	public String getPosition() {return this.position;}

	public void setStatus(Integer status) {this.status = status;}

	public Integer getStatus() {return this.status;}

	public void setRoles(String roles) {this.roles = roles;}

	public String getRoles() {return this.roles;}

	public void setCreateTime(Date createTime) {this.createTime = createTime;}

	public Date getCreateTime() {return this.createTime;}

	@Override
	public String toString(){return "用户ID: " + (userId == null?"空": userId) + ", 手机号: " + (phone == null?"空": phone) + ", 用户名: " + (userName == null?"空": userName) + ", 密码: " + (password == null?"空": password) + ", 位: " + (position == null?"空": position) + ", 状态 0:禁用 1:启用: " + (status == null?"空": status) + ", 用户拥有的角色 多个用逗号隔开: " + (roles == null?"空": roles) + ", 创建时间: " + (createTime == null?"空": DateUtils.format(createTime, DateTimePatternEnum._YYYY_MM_DD_HH_MM_SS.getPattern())) ;}
}