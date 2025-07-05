package com.easyjob.service;

import java.util.List;

import com.easyjob.entity.dto.ImportErrorItem;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.QuestionInfo;
import com.easyjob.entity.query.QuestionInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionInfoService{

	List<QuestionInfo> findListByParam(QuestionInfoQuery query);
	Integer findCntByParam(QuestionInfoQuery query);
	PaginationResultVo<QuestionInfo> findListByPage(QuestionInfoQuery query);

	Integer add(QuestionInfo bean);
	Integer addBatch(List<QuestionInfo> bean);
	Integer addOrUpdateBatch(List<QuestionInfo> bean);

	Integer updateByParam(QuestionInfo bean, QuestionInfoQuery query);
	Integer deleteByParam(QuestionInfoQuery query);

	QuestionInfo getQuestionInfoByQuestionId(Integer questionId);
	Integer updateQuestionInfoByQuestionId(QuestionInfo bean, Integer questionId);
	Integer deleteQuestionInfoByQuestionId(Integer questionId);

	void saveQuestion(QuestionInfo bean, Boolean superAdmin);
	void delQuestionBatch(String qsIds, Integer userId);
	List<ImportErrorItem> importQuestion(MultipartFile file, SessionUserAdminDto dto); // controller 提交 file
	QuestionInfo showDetailNext(QuestionInfoQuery qsQry, Integer curId, Integer nxtType, Boolean updateReadCnt);

}