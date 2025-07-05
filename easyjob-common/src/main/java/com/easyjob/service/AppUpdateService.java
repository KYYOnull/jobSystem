package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.AppUpdate;
import com.easyjob.entity.query.AppUpdateQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import org.springframework.web.multipart.MultipartFile;

public interface AppUpdateService{

	List<AppUpdate> findListByParam(AppUpdateQuery query);
	Integer findCntByParam(AppUpdateQuery query);
	PaginationResultVo<AppUpdate> findListByPage(AppUpdateQuery query);

	Integer add(AppUpdate bean);
	Integer addBatch(List<AppUpdate> bean);
	Integer addOrUpdateBatch(List<AppUpdate> bean);

	Integer updateByParam(AppUpdate bean, AppUpdateQuery query);
	Integer deleteByParam(AppUpdateQuery query);

	AppUpdate getAppUpdateById(Integer id);
	Integer updateAppUpdateById(AppUpdate bean, Integer id);
	Integer deleteAppUpdateById(Integer id);

	void saveUpdate(AppUpdate bean, MultipartFile file);
	void postUpdate(Integer id, Integer status, String grayscaleDevice);

}