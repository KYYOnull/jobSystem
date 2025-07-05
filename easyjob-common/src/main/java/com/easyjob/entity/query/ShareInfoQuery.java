package com.easyjob.entity.query;

import java.util.Date;


public class ShareInfoQuery extends BaseQuery{

	private Integer shareId;

	private String title;
	private String titleFuzzy;

	private Integer coverType;
	private String coverPath;
	private String coverPathFuzzy;


	private String content;
	private String contentFuzzy;

	private Date createTime;
	private String createTimeStart;
	private String createTimeEnd;


	private Integer status;


	private String createUserId;
	private String createUserIdFuzzy;
	private String createUserName;
	private String createUserNameFuzzy;


	private Integer readCount;
	private Integer collectCount;
	private Integer postUserType;

	private Boolean qryTxtCont; // 控制返回
	private String[] shareIds;
	private Integer nxtType;
	private Integer curId;

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

	public void setTitleFuzzy(String titleFuzzy) {this.titleFuzzy = titleFuzzy;}

	public String getTitleFuzzy() {return this.titleFuzzy;}

	public void setCoverPathFuzzy(String coverPathFuzzy) {this.coverPathFuzzy = coverPathFuzzy;}

	public String getCoverPathFuzzy() {return this.coverPathFuzzy;}

	public void setContentFuzzy(String contentFuzzy) {this.contentFuzzy = contentFuzzy;}

	public String getContentFuzzy() {return this.contentFuzzy;}

	public void setCreateTimeStart(String createTimeStart) {this.createTimeStart = createTimeStart;}

	public String getCreateTimeStart() {return this.createTimeStart;}

	public void setCreateTimeEnd(String createTimeEnd) {this.createTimeEnd = createTimeEnd;}

	public String getCreateTimeEnd() {return this.createTimeEnd;}

	public void setCreateUserIdFuzzy(String createUserIdFuzzy) {this.createUserIdFuzzy = createUserIdFuzzy;}

	public String getCreateUserIdFuzzy() {return this.createUserIdFuzzy;}

	public void setCreateUserNameFuzzy(String createUserNameFuzzy) {this.createUserNameFuzzy = createUserNameFuzzy;}

	public String getCreateUserNameFuzzy() {return this.createUserNameFuzzy;}

	public Boolean getQryTxtCont() {
		return qryTxtCont;
	}

	public void setQryTxtCont(Boolean qryTxtCont) {
		this.qryTxtCont = qryTxtCont;
	}

	public String[] getShareIds() {
		return shareIds;
	}

	public void setShareIds(String[] shareIds) {
		this.shareIds = shareIds;
	}

	public Integer getNxtType() {
		return nxtType;
	}

	public void setNxtType(Integer nxtType) {
		this.nxtType = nxtType;
	}

	public Integer getCurId() {
		return curId;
	}

	public void setCurId(Integer curId) {
		this.curId = curId;
	}
}