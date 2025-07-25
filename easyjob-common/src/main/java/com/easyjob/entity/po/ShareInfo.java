package com.easyjob.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easyjob.enums.DateTimePatternEnum;
import com.easyjob.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class ShareInfo implements Serializable {

	private Integer shareId;

	private String title;

	private Integer coverType;
	private String coverPath;

	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JsonIgnore
	private Integer status;

	private String createUserId;
	private String createUserName;

	private Integer readCount;
	private Integer collectCount;

	private Integer postUserType;

	public void setShareId(Integer shareId) {this.shareId = shareId;}

	public Integer getShareId() {return this.shareId;}

	public void setTitle(String title) {this.title = title;}

	public String getTitle() {return this.title;}

	public void setCoverType(Integer coverType) {this.coverType = coverType;}

	public Integer getCoverType() {return this.coverType;}

	public void setCoverPath(String coverPath) {this.coverPath = coverPath;}

	public String getCoverPath() {return this.coverPath;}

	public void setContent(String content) {this.content = content;}

	public String getContent() {return this.content;}

	public void setCreateTime(Date createTime) {this.createTime = createTime;}

	public Date getCreateTime() {return this.createTime;}

	public void setStatus(Integer status) {this.status = status;}

	public Integer getStatus() {return this.status;}

	public void setCreateUserId(String createUserId) {this.createUserId = createUserId;}

	public String getCreateUserId() {return this.createUserId;}

	public void setCreateUserName(String createUserName) {this.createUserName = createUserName;}

	public String getCreateUserName() {return this.createUserName;}

	public void setReadCount(Integer readCount) {this.readCount = readCount;}

	public Integer getReadCount() {return this.readCount;}

	public void setCollectCount(Integer collectCount) {this.collectCount = collectCount;}

	public Integer getCollectCount() {return this.collectCount;}

	public void setPostUserType(Integer postUserType) {this.postUserType = postUserType;}

	public Integer getPostUserType() {return this.postUserType;}

	@Override
	public String toString(){return "自增ID: " + (shareId == null?"空": shareId) + ", 标题: " + (title == null?"空": title) + ", 封面类型 0: 无封面, 1: 横幅, 2: 小图标: " + (coverType == null?"空": coverType) + ", 封面路径: " + (coverPath == null?"空": coverPath) + ", 内容: " + (content == null?"空": content) + ", 创建时间: " + (createTime == null?"空": DateUtils.format(createTime, DateTimePatternEnum._YYYY_MM_DD_HH_MM_SS.getPattern())) + ", 0: 未发布, 1: 已发布: " + (status == null?"空": status) + ", 用户ID: " + (createUserId == null?"空": createUserId) + ", 姓名: " + (createUserName == null?"空": createUserName) + ", 阅读数量: " + (readCount == null?"空": readCount) + ", 收藏数: " + (collectCount == null?"空": collectCount) + ", 0: 内部, 1: 外部投稿: " + (postUserType == null?"空": postUserType) ;}
}