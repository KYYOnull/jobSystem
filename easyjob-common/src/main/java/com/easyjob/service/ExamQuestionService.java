package com.easyjob.service;

import java.util.List;

import com.easyjob.entity.dto.ImportErrorItem;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.ExamQuestion;
import com.easyjob.entity.po.ExamQuestionItem;
import com.easyjob.entity.query.ExamQuestionQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import org.springframework.web.multipart.MultipartFile;

public interface ExamQuestionService{


	List<ExamQuestion> findListByParam(ExamQuestionQuery query);
	Integer findCntByParam(ExamQuestionQuery query);
	PaginationResultVo<ExamQuestion> findListByPage(ExamQuestionQuery query);

	Integer add(ExamQuestion bean);
	Integer addBatch(List<ExamQuestion> bean);
	Integer addOrUpdateBatch(List<ExamQuestion> bean);

	Integer updateByParam(ExamQuestion bean, ExamQuestionQuery query);
	Integer deleteByParam(ExamQuestionQuery query);

	ExamQuestion getExamQuestionByQuestionId(Integer questionId);
	Integer updateExamQuestionByQuestionId(ExamQuestion bean, Integer questionId);
	Integer deleteExamQuestionByQuestionId(Integer questionId);
	void saveExamQuestion(ExamQuestion exQs, List<ExamQuestionItem> exQsItLst, Boolean isSuper);
	void delExamQuestionBatch(String qsIds, Integer userId);
	ExamQuestion showExQsDetailNext(ExamQuestionQuery exQsQry, Integer curId, Integer nxtType);
	List<ImportErrorItem> importExamQuestion(SessionUserAdminDto userAdminDto, MultipartFile file);

}