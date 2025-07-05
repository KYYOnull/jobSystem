package com.easyjob.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.ExamQuestionItem;
import com.easyjob.entity.query.ExamQuestionItemQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.ExamQuestionItemService;
import com.easyjob.mappers.ExamQuestionItemMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("examQuestionItemService")
public class ExamQuestionItemServiceImpl implements ExamQuestionItemService{

	@Resource
	private ExamQuestionItemMappers<ExamQuestionItem, ExamQuestionItemQuery> examQuestionItemMappers;

	@Override
	public List<ExamQuestionItem> findListByParam(ExamQuestionItemQuery query){

		return this.examQuestionItemMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(ExamQuestionItemQuery query){

		return this.examQuestionItemMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<ExamQuestionItem> findListByPage(ExamQuestionItemQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<ExamQuestionItem> list = this.findListByParam(query);
		PaginationResultVo<ExamQuestionItem> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(ExamQuestionItem bean){

		return this.examQuestionItemMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<ExamQuestionItem> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.examQuestionItemMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<ExamQuestionItem> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.examQuestionItemMappers.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(ExamQuestionItem bean, ExamQuestionItemQuery query) {
		StringTools.checkParam(query);
		return this.examQuestionItemMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(ExamQuestionItemQuery query) {
		StringTools.checkParam(query);
		return this.examQuestionItemMappers.deleteByParam(query);
	}

	@Override
	public ExamQuestionItem getExamQuestionItemByItemId(Integer itemId){

		return this.examQuestionItemMappers.selectExamQuestionItemByItemId(itemId);
	}
	@Override
	public Integer updateExamQuestionItemByItemId(ExamQuestionItem bean, Integer itemId){

		return this.examQuestionItemMappers.updateExamQuestionItemByItemId(bean, itemId);
	}
	@Override
	public Integer delExQsItByItemId(Integer itemId){

		return this.examQuestionItemMappers.delExQsItByItemId(itemId);
	}
}