package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.ExamQuestionItem;
import com.easyjob.entity.query.ExamQuestionItemQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface ExamQuestionItemService{


	List<ExamQuestionItem> findListByParam(ExamQuestionItemQuery query);
	Integer findCntByParam(ExamQuestionItemQuery query);
	PaginationResultVo<ExamQuestionItem> findListByPage(ExamQuestionItemQuery query);

	Integer add(ExamQuestionItem bean);
	Integer addBatch(List<ExamQuestionItem> bean);
	Integer addOrUpdateBatch(List<ExamQuestionItem> bean);

	Integer updateByParam(ExamQuestionItem bean, ExamQuestionItemQuery query);
	Integer deleteByParam(ExamQuestionItemQuery query);

	ExamQuestionItem getExamQuestionItemByItemId(Integer itemId);
	Integer updateExamQuestionItemByItemId(ExamQuestionItem bean, Integer itemId);
	Integer delExQsItByItemId(Integer itemId);

}