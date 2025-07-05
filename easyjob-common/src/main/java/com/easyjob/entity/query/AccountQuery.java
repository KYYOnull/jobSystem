package com.easyjob.entity.query;

import java.util.Date;

public class AccountQuery extends BaseQuery{

	private Integer userId;

	private String phone;

	private String phoneFuzzy;

	private String userName;

	private String userNameFuzzy;

	private String password;
	private String passwordFuzzy;


	private String position;
	private String positionFuzzy;

	private Integer status;

	private String roles;
	private String rolesFuzzy;

	private Date createTime;
	private String createTimeStart;
	private String createTimeEnd;

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

	public void setPhoneFuzzy(String phoneFuzzy) {this.phoneFuzzy = phoneFuzzy;}

	public String getPhoneFuzzy() {return this.phoneFuzzy;}

	public void setUserNameFuzzy(String userNameFuzzy) {this.userNameFuzzy = userNameFuzzy;}

	public String getUserNameFuzzy() {return this.userNameFuzzy;}

	public void setPasswordFuzzy(String passwordFuzzy) {this.passwordFuzzy = passwordFuzzy;}

	public String getPasswordFuzzy() {return this.passwordFuzzy;}

	public void setPositionFuzzy(String positionFuzzy) {this.positionFuzzy = positionFuzzy;}

	public String getPositionFuzzy() {return this.positionFuzzy;}

	public void setRolesFuzzy(String rolesFuzzy) {this.rolesFuzzy = rolesFuzzy;}

	public String getRolesFuzzy() {return this.rolesFuzzy;}

	public void setCreateTimeStart(String createTimeStart) {this.createTimeStart = createTimeStart;}

	public String getCreateTimeStart() {return this.createTimeStart;}

	public void setCreateTimeEnd(String createTimeEnd) {this.createTimeEnd = createTimeEnd;}

	public String getCreateTimeEnd() {return this.createTimeEnd;}

}