package com.easyjob.service.impl;

import java.util.List;
import java.util.Date;

import com.easyjob.enums.FeedbackSendTypeEnum;
import com.easyjob.enums.FeedbackStatusEnum;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.AppFeedback;
import com.easyjob.entity.query.AppFeedbackQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.AppFeedbackService;
import com.easyjob.mappers.AppFeedbackMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;

@Service("appFeedbackService")
public class AppFeedbackServiceImpl implements AppFeedbackService{

	@Resource
	private AppFeedbackMappers<AppFeedback, AppFeedbackQuery> appFeedbackMappers;

	@Override
	public List<AppFeedback> findListByParam(AppFeedbackQuery query){

		return this.appFeedbackMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(AppFeedbackQuery query){

		return this.appFeedbackMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<AppFeedback> findListByPage(AppFeedbackQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<AppFeedback> list = this.findListByParam(query);
		PaginationResultVo<AppFeedback> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}
	@Override
	public Integer add(AppFeedback bean){

		return this.appFeedbackMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<AppFeedback> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.appFeedbackMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<AppFeedback> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.appFeedbackMappers.insertOrUpdateBatch(listBean);
	}
	@Override
	public Integer updateByParam(AppFeedback bean, AppFeedbackQuery query) {
		StringTools.checkParam(query);
		return this.appFeedbackMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(AppFeedbackQuery query) {
		StringTools.checkParam(query);
		return this.appFeedbackMappers.deleteByParam(query);
	}

	/**
	 *  根据FeedbackId查询
	 */
	@Override
	public AppFeedback getAppFeedbackByFeedbackId(Integer feedbackId){

		return this.appFeedbackMappers.selectAppFeedbackByFeedbackId(feedbackId);
	}
	@Override
	public Integer updateAppFeedbackByFeedbackId(AppFeedback bean, Integer feedbackId){

		return this.appFeedbackMappers.updateAppFeedbackByFeedbackId(bean, feedbackId);
	}
	@Override
	public Integer deleteAppFeedbackByFeedbackId(Integer feedbackId){

		return this.appFeedbackMappers.deleteAppFeedbackByFeedbackId(feedbackId);
	}

	/**
	 *  根据FeedbackIdAndUserId查询
	 */
	@Override
	public AppFeedback getAppFeedbackByFeedbackIdAndUserId(Integer feedbackId, String userId){

		return this.appFeedbackMappers.selectAppFeedbackByFeedbackIdAndUserId(feedbackId, userId);
	}
	@Override
	public Integer updateAppFeedbackByFeedbackIdAndUserId(AppFeedback bean, Integer feedbackId, String userId){

		return this.appFeedbackMappers.updateAppFeedbackByFeedbackIdAndUserId(bean, feedbackId, userId);
	}
	@Override
	public Integer deleteAppFeedbackByFeedbackIdAndUserId(Integer feedbackId, String userId){

		return this.appFeedbackMappers.deleteAppFeedbackByFeedbackIdAndUserId(feedbackId, userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void replyFeedback(AppFeedback replyBean) { // bean 是一条新reply记录

		if(null == this.appFeedbackMappers.selectAppFeedbackByFeedbackId(replyBean.getPFeedbackId())){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		} // 必须有上一级

		replyBean.setCreateTime(new Date());
		replyBean.setSendType(FeedbackSendTypeEnum.ADMIN.getStatus()); // reply from admin
		replyBean.setStatus(FeedbackStatusEnum.NOT_REPLY.getStatus());
		this.appFeedbackMappers.insert(replyBean);

		AppFeedback pFeedbackUpdate = new AppFeedback();
		pFeedbackUpdate.setStatus(FeedbackStatusEnum.REPLY.getStatus());
		this.appFeedbackMappers.updateAppFeedbackByFeedbackId(pFeedbackUpdate, replyBean.getPFeedbackId());
	} // insert 成功 update 失败，会导致状态不一致
}