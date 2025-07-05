package com.easyjob.entity.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.easyjob.annotation.VerifyParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easyjob.enums.DateTimePatternEnum;
import com.easyjob.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class ExamQuestion implements Serializable {


	private Integer questionId;

	@VerifyParam(required = true)
	private String title;

	@VerifyParam(required = true)
	private Integer categoryId;

	private String categoryName;

	@VerifyParam(required = true)
	private Integer difficultyLevel;

	@VerifyParam(required = true)
	private Integer questionType; // 传入的问题类型 0 1 2

	private String question;

	@VerifyParam(required = true)
	private String questionAnswer;

	@VerifyParam(required = true)
	private String answerAnalysis;


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JsonIgnore
	private Integer status;

	private String createUserId;
	private String createUserName;

	/**
	 *  0: 内部, 1: 外部投稿
	 */
	private Integer postUserType;

	private List<ExamQuestionItem> exQsItLst; // 其下选项

	public void setQuestionId(Integer questionId) {this.questionId = questionId;}

	public Integer getQuestionId() {return this.questionId;}

	public void setTitle(String title) {this.title = title;}

	public String getTitle() {return this.title;}

	public void setCategoryId(Integer categoryId) {this.categoryId = categoryId;}

	public Integer getCategoryId() {return this.categoryId;}

	public void setCategoryName(String categoryName) {this.categoryName = categoryName;}

	public String getCategoryName() {return this.categoryName;}

	public void setDifficultyLevel(Integer difficultyLevel) {this.difficultyLevel = difficultyLevel;}

	public Integer getDifficultyLevel() {return this.difficultyLevel;}

	public void setQuestionType(Integer questionType) {this.questionType = questionType;}

	public Integer getQuestionType() {return this.questionType;}

	public void setQuestion(String question) {this.question = question;}

	public String getQuestion() {return this.question;}

	public void setQuestionAnswer(String questionAnswer) {this.questionAnswer = questionAnswer;}

	public String getQuestionAnswer() {return this.questionAnswer;}

	public void setAnswerAnalysis(String answerAnalysis) {this.answerAnalysis = answerAnalysis;}

	public String getAnswerAnalysis() {return this.answerAnalysis;}

	public void setCreateTime(Date createTime) {this.createTime = createTime;}

	public Date getCreateTime() {return this.createTime;}

	public void setStatus(Integer status) {this.status = status;}

	public Integer getStatus() {return this.status;}

	public void setCreateUserId(String createUserId) {this.createUserId = createUserId;}

	public String getCreateUserId() {return this.createUserId;}

	public void setCreateUserName(String createUserName) {this.createUserName = createUserName;}

	public String getCreateUserName() {return this.createUserName;}

	public void setPostUserType(Integer postUserType) {this.postUserType = postUserType;}

	public Integer getPostUserType() {return this.postUserType;}

	@Override
	public String toString(){return "问题ID: " + (questionId == null?"空": questionId) + ", 标题: " + (title == null?"空": title) + ", 分类ID: " + (categoryId == null?"空": categoryId) + ", 分类名称: " + (categoryName == null?"空": categoryName) + ", 难度: " + (difficultyLevel == null?"空": difficultyLevel) + ", 题型 0: 判断题, 1: 单选题, 2: 多选题: " + (questionType == null?"空": questionType) + ", 问题描述: " + (question == null?"空": question) + ", 答案: " + (questionAnswer == null?"空": questionAnswer) + ", 回答解释: " + (answerAnalysis == null?"空": answerAnalysis) + ", 创建时间: " + (createTime == null?"空": DateUtils.format(createTime, DateTimePatternEnum._YYYY_MM_DD_HH_MM_SS.getPattern())) + ", 0: 未发布, 1: 已发布: " + (status == null?"空": status) + ", 创建用户ID: " + (createUserId == null?"空": createUserId) + ", 姓名: " + (createUserName == null?"空": createUserName) + ", 0: 内部, 1: 外部投稿: " + (postUserType == null?"空": postUserType) ;}

	public List<ExamQuestionItem> getExQsItLst() {
		return exQsItLst;
	}

	public void setExQsItLst(List<ExamQuestionItem> exQsItLst) {
		this.exQsItLst = exQsItLst;
	}
}