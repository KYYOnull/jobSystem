package com.easyjob.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.easyjob.entity.config.AppConfig;
import com.easyjob.entity.constants.Constants;
import com.easyjob.enums.AppUpdateStatusEnum;
import com.easyjob.enums.AppUpdateTypeEnum;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.AppUpdate;
import com.easyjob.entity.query.AppUpdateQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.AppUpdateService;
import com.easyjob.mappers.AppUpdateMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("appUpdateService")
public class AppUpdateServiceImpl implements AppUpdateService{

	@Resource
	private AppUpdateMappers<AppUpdate, AppUpdateQuery> appUpdateMappers;

	@Resource
	private AppConfig appConf;

	@Override
	public List<AppUpdate> findListByParam(AppUpdateQuery query){

		return this.appUpdateMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(AppUpdateQuery query){

		return this.appUpdateMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<AppUpdate> findListByPage(AppUpdateQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<AppUpdate> list = this.findListByParam(query);
		PaginationResultVo<AppUpdate> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}
	@Override
	public Integer add(AppUpdate bean){

		return this.appUpdateMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<AppUpdate> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.appUpdateMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<AppUpdate> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.appUpdateMappers.insertOrUpdateBatch(listBean);
	}
	@Override
	public Integer updateByParam(AppUpdate bean, AppUpdateQuery query) {
		StringTools.checkParam(query);
		return this.appUpdateMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(AppUpdateQuery query) {
		StringTools.checkParam(query);
		return this.appUpdateMappers.deleteByParam(query);
	}

	/**
	 *  根据Id查询
	 */
	@Override
	public AppUpdate getAppUpdateById(Integer id){

		return this.appUpdateMappers.selectAppUpdateById(id);
	}
	@Override
	public Integer updateAppUpdateById(AppUpdate bean, Integer id){

		return this.appUpdateMappers.updateAppUpdateById(bean, id);
	}
	@Override
	public Integer deleteAppUpdateById(Integer id){

		return this.appUpdateMappers.deleteAppUpdateById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUpdate(AppUpdate bean, MultipartFile file) {

		AppUpdateQuery updateQry = new AppUpdateQuery(); // 检验版本正误
		updateQry.setOrderBy("id desc"); // 最新在前
		updateQry.setSimplePage(new SimplePage(0,1)); // 只需要最后一条 因此利用分页减少传输量

		List<AppUpdate> updateLst = this.appUpdateMappers.selectList(updateQry);
		if(!updateLst.isEmpty()){ // 有记录 则与当前bean提交版本比对

			AppUpdate latestVerDb = updateLst.get(0);
			long verNumDb = Long.parseLong(latestVerDb.getVersion().replace(".", ""));
			long curVerNum = Long.parseLong(bean.getVersion().replace(".", ""));
			if(verNumDb <= curVerNum) throw new BusinessException("当前版本必须大于历史版本");

		}

		if(bean.getId() == null){ // insert
			bean.setCreateTime(new Date());
			bean.setStatus(AppUpdateStatusEnum.INIT.getStatus());
			this.appUpdateMappers.insert(bean);
		}else {
			bean.setStatus(null); // update
			bean.setGrayscaleDevice(null);
			this.appUpdateMappers.updateAppUpdateById(bean, bean.getId());
		}

		if(file != null){ // 保存发布文件 d:/data/tmp /app/

			File folder = new File(appConf.getPrjFolder() + Constants.APP_UPDATE_FOLDER);
			if(!folder.exists()) folder.mkdir();

			AppUpdateTypeEnum updateType = AppUpdateTypeEnum.getByType(bean.getUpdateType());
			if(updateType == null) throw new BusinessException(ResponseCodeEnum.CODE_600);
			try {
				file.transferTo(new File(folder.getAbsolutePath() + "/" + bean.getId() + updateType.getSuffix()));
			} catch (IOException e) {
				throw new BusinessException("更新App失败");
			}

		}
	}


	@Override
	public void postUpdate(Integer id, Integer status, String grayscaleDevice) {

		AppUpdateStatusEnum updateStatus = AppUpdateStatusEnum.getByStatus(status);
		if(updateStatus == null) throw new BusinessException(ResponseCodeEnum.CODE_600);

		if(AppUpdateStatusEnum.GRAYSCALE == updateStatus && StringTools.isEmpty(grayscaleDevice)){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		} // 是灰度 但无设备

		if(AppUpdateStatusEnum.GRAYSCALE != updateStatus) grayscaleDevice=""; // 不是灰度 无视设备

		AppUpdate neoUpdateInfo = new AppUpdate();
		neoUpdateInfo.setStatus(status);
		neoUpdateInfo.setGrayscaleDevice(grayscaleDevice); // 是不是灰度都设置

		this.appUpdateMappers.updateAppUpdateById(neoUpdateInfo, id);

	}
}