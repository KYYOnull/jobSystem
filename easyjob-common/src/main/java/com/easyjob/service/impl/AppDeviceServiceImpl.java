package com.easyjob.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.AppDevice;
import com.easyjob.entity.query.AppDeviceQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.AppDeviceService;
import com.easyjob.mappers.AppDeviceMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("appDeviceService")
public class AppDeviceServiceImpl implements AppDeviceService{

	@Resource
	private AppDeviceMappers<AppDevice, AppDeviceQuery> appDeviceMappers;

	@Override
	public List<AppDevice> findListByParam(AppDeviceQuery query){

		return this.appDeviceMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(AppDeviceQuery query){

		return this.appDeviceMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<AppDevice> findListByPage(AppDeviceQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<AppDevice> list = this.findListByParam(query);
		PaginationResultVo<AppDevice> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}
	@Override
	public Integer add(AppDevice bean){

		return this.appDeviceMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<AppDevice> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.appDeviceMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<AppDevice> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.appDeviceMappers.insertOrUpdateBatch(listBean);
	}
	@Override
	public Integer updateByParam(AppDevice bean, AppDeviceQuery query) {
		StringTools.checkParam(query);
		return this.appDeviceMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(AppDeviceQuery query) {
		StringTools.checkParam(query);
		return this.appDeviceMappers.deleteByParam(query);
	}

	/**
	 *  根据DeviceId查询
	 */
	@Override
	public AppDevice getAppDeviceByDeviceId(String deviceId){

		return this.appDeviceMappers.selectAppDeviceByDeviceId(deviceId);
	}

	/**
	 *  根据DeviceId更新
	 */
	@Override
	public Integer updateAppDeviceByDeviceId(AppDevice bean, String deviceId){

		return this.appDeviceMappers.updateAppDeviceByDeviceId(bean, deviceId);
	}

	/**
	 *  根据DeviceId删除
	 */
	@Override
	public Integer deleteAppDeviceByDeviceId(String deviceId){

		return this.appDeviceMappers.deleteAppDeviceByDeviceId(deviceId);
	}
}