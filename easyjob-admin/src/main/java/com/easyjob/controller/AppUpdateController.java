package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.AppUpdate;
import com.easyjob.entity.query.AppUpdateQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.AppUpdateService;
import com.easyjob.controller.BaseController;
import com.easyjob.controller.GlobalExceptionHandlerController;
import org.springframework.web.multipart.MultipartFile;


@RestController("appUpdateController")
@RequestMapping("/app")
public class AppUpdateController extends BaseController{

	@Resource
	private AppUpdateService appUpdateService;

	// 查询列表 查询数量 分页查询 都用一个
	@RequestMapping("loadDataList")
	@GlobalInterceptor
	public ResponseVo loadDataList(AppUpdateQuery query){

		query.setOrderBy("id desc");
		return getSuccessResponseVo(appUpdateService.findListByPage(query));
	}

	@RequestMapping("saveUpdate") // 更新记录
	@GlobalInterceptor
	public ResponseVo<Void> saveUpdate(
			Integer id, // 更新时有id
			@VerifyParam(required = true) String version,
			@VerifyParam(required = true) String updateDesc,
			@VerifyParam(required = true) Integer updateType,
			MultipartFile file
	){
		AppUpdate appUp = new AppUpdate();
		appUp.setId(id);
		appUp.setVersion(version);
		appUp.setUpdateDesc(updateDesc);
		appUp.setUpdateType(updateType);
		this.appUpdateService.saveUpdate(appUp, file);

		return getSuccessResponseVo(null);
	}

	@RequestMapping("postUpdate")
	@GlobalInterceptor
	public ResponseVo<Void> postUpdate(
			@VerifyParam(required = true) Integer id,
			@VerifyParam(required = true) Integer status,
			String grayscaleDevice
	){

		this.appUpdateService.postUpdate(id, status, grayscaleDevice);
		return getSuccessResponseVo(null);
	}


	@RequestMapping("delUpdate")
	@GlobalInterceptor
	public ResponseVo<Void> delUpdate(@VerifyParam(required = true) Integer id){

		this.appUpdateService.deleteAppUpdateById(id);

		return getSuccessResponseVo(null);
	}
}