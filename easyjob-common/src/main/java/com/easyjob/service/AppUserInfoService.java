package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.AppUserInfo;
import com.easyjob.entity.query.AppUserInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface AppUserInfoService{

	List<AppUserInfo> findListByParam(AppUserInfoQuery query);
	Integer findCntByParam(AppUserInfoQuery query);
	PaginationResultVo<AppUserInfo> findListByPage(AppUserInfoQuery query);

	Integer add(AppUserInfo bean);
	Integer addBatch(List<AppUserInfo> bean);
	Integer addOrUpdateBatch(List<AppUserInfo> bean);

	Integer updateByParam(AppUserInfo bean, AppUserInfoQuery query);
	Integer deleteByParam(AppUserInfoQuery query);

	AppUserInfo getAppUserInfoByUserId(String userId);
	Integer updateAppUserInfoByUserId(AppUserInfo bean, String userId);
	Integer deleteAppUserInfoByUserId(String userId);

	AppUserInfo getAppUserInfoByEmail(String email);
	Integer updateAppUserInfoByEmail(AppUserInfo bean, String email);
	Integer deleteAppUserInfoByEmail(String email);

}