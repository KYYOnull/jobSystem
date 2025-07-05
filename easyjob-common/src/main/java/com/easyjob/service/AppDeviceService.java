package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.AppDevice;
import com.easyjob.entity.query.AppDeviceQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface AppDeviceService{

	List<AppDevice> findListByParam(AppDeviceQuery query);
	Integer findCntByParam(AppDeviceQuery query);
	PaginationResultVo<AppDevice> findListByPage(AppDeviceQuery query);

	Integer add(AppDevice bean);
	Integer addBatch(List<AppDevice> bean);
	Integer addOrUpdateBatch(List<AppDevice> bean);

	Integer updateByParam(AppDevice bean, AppDeviceQuery query);
	Integer deleteByParam(AppDeviceQuery query);

	AppDevice getAppDeviceByDeviceId(String deviceId);
	Integer updateAppDeviceByDeviceId(AppDevice bean, String deviceId);
	Integer deleteAppDeviceByDeviceId(String deviceId);

}