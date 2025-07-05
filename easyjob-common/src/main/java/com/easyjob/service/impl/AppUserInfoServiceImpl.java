package com.easyjob.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.AppUserInfo;
import com.easyjob.entity.query.AppUserInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.AppUserInfoService;
import com.easyjob.mappers.AppUserInfoMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("appUserInfoService")
public class AppUserInfoServiceImpl implements AppUserInfoService{

	@Resource
	private AppUserInfoMappers<AppUserInfo, AppUserInfoQuery> appUserInfoMappers;

	@Override
	public List<AppUserInfo> findListByParam(AppUserInfoQuery query){

		return this.appUserInfoMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(AppUserInfoQuery query){

		return this.appUserInfoMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<AppUserInfo> findListByPage(AppUserInfoQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<AppUserInfo> list = this.findListByParam(query);
		PaginationResultVo<AppUserInfo> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}
	@Override
	public Integer add(AppUserInfo bean){

		return this.appUserInfoMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<AppUserInfo> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.appUserInfoMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<AppUserInfo> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.appUserInfoMappers.insertOrUpdateBatch(listBean);
	}
	@Override
	public Integer updateByParam(AppUserInfo bean, AppUserInfoQuery query) {
		StringTools.checkParam(query);
		return this.appUserInfoMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(AppUserInfoQuery query) {
		StringTools.checkParam(query);
		return this.appUserInfoMappers.deleteByParam(query);
	}

	/**
	 *  根据UserId查询
	 */
	@Override
	public AppUserInfo getAppUserInfoByUserId(String userId){

		return this.appUserInfoMappers.selectAppUserInfoByUserId(userId);
	}

	/**
	 *  根据UserId更新
	 */
	@Override
	public Integer updateAppUserInfoByUserId(AppUserInfo bean, String userId){

		return this.appUserInfoMappers.updateAppUserInfoByUserId(bean, userId);
	}

	/**
	 *  根据UserId删除
	 */
	@Override
	public Integer deleteAppUserInfoByUserId(String userId){

		return this.appUserInfoMappers.deleteAppUserInfoByUserId(userId);
	}

	/**
	 *  根据Email查询
	 */
	@Override
	public AppUserInfo getAppUserInfoByEmail(String email){

		return this.appUserInfoMappers.selectAppUserInfoByEmail(email);
	}

	/**
	 *  根据Email更新
	 */
	@Override
	public Integer updateAppUserInfoByEmail(AppUserInfo bean, String email){

		return this.appUserInfoMappers.updateAppUserInfoByEmail(bean, email);
	}

	/**
	 *  根据Email删除
	 */
	@Override
	public Integer deleteAppUserInfoByEmail(String email){

		return this.appUserInfoMappers.deleteAppUserInfoByEmail(email);
	}
}