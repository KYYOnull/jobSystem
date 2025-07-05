package com.easyjob.controller;

import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.po.AppDevice;
import com.easyjob.entity.query.AppDeviceQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.enums.UserStatusEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.service.AppDeviceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.AppUserInfo;
import com.easyjob.entity.query.AppUserInfoQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.AppUserInfoService;


@RestController("appUserInfoController")
@RequestMapping("/appUser")
public class AppUserInfoController extends BaseController{

	@Resource
	private AppUserInfoService appUserInfoService;

	@Resource
	private AppDeviceService appDeviceService;

	@RequestMapping("/loadDeviceList")
	@GlobalInterceptor
	public ResponseVo<PaginationResultVo<AppDevice>> loadDataList(AppDeviceQuery query){

		query.setOrderBy("create_time desc");

		return getSuccessResponseVo(appDeviceService.findListByPage(query));
	}

	@RequestMapping("/loadDataList")
	@GlobalInterceptor
	public ResponseVo<PaginationResultVo<AppUserInfo>> loadDataList(AppUserInfoQuery query){

		query.setOrderBy("join_time desc"); // 最新
		return getSuccessResponseVo(appUserInfoService.findListByPage(query));
	}

	@RequestMapping("/updateStatus")
	@GlobalInterceptor
	public ResponseVo<Void> updateStatus(
			@VerifyParam(required = true) String userId,
			@VerifyParam(required = true) Integer status
	){
		UserStatusEnum userStatus = UserStatusEnum.getByStatus(status);
		if(userStatus == null){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		} // 枚举校验

		AppUserInfo userInfo = new AppUserInfo();
		userInfo.setStatus(status);
		appUserInfoService.updateAppUserInfoByUserId(userInfo, userId);

		return getSuccessResponseVo(null);
	}
}