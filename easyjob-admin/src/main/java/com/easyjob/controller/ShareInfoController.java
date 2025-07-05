package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.QuestionInfo;
import com.easyjob.entity.query.QuestionInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.enums.PostStatusEnum;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.ShareInfo;
import com.easyjob.entity.query.ShareInfoQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.ShareInfoService;
import com.easyjob.controller.BaseController;
import com.easyjob.controller.GlobalExceptionHandlerController;

@RestController("shareInfoController")
@RequestMapping("/shareInfo")
public class ShareInfoController extends BaseController{

	@Resource
	private ShareInfoService shareInfoService;

	// 查询列表 查询数量 分页查询 都用一个
	@RequestMapping("loadDataList")
	@GlobalInterceptor()
	public ResponseVo<PaginationResultVo<ShareInfo>> loadDataList(ShareInfoQuery query){

		query.setOrderBy("share_id desc");
		query.setQryTxtCont(true); // get content
		return getSuccessResponseVo(shareInfoService.findListByPage(query));
	}

	@RequestMapping("saveShareInfo")
	@GlobalInterceptor()
	public ResponseVo<Void> saveShareInfo(
			HttpSession ses,
//			@VerifyParam(required = true) ShareInfo shareInfo // 不使用 则其他参数传不进来
//			注意 所有参数命名与实体一致
			Integer shareId,
			@VerifyParam(required = true) String title,
			@VerifyParam(required = true) Integer coverType,
			String coverPath,
			@VerifyParam(required = true) String content
	){

		ShareInfo bean = new ShareInfo();
		bean.setShareId(shareId);
		bean.setTitle(title);
		bean.setCoverType(coverType);
		bean.setCoverPath(coverPath);
		bean.setContent(content);

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);
		bean.setCreateUserId(String.valueOf(userAdminDto.getUserId()));
		bean.setCreateUserName(userAdminDto.getUserName());

		shareInfoService.saveShareInfo(bean, userAdminDto.getSuperAdmin());

		return getSuccessResponseVo(null);
	}

	@RequestMapping("delShare")
	@GlobalInterceptor()
	public ResponseVo<Void> delShare(HttpSession ses, @VerifyParam(required = true) String shareIds){

		SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);
		shareInfoService.delShareInfoBatchByIds(
				shareIds,
				userAdminDto.getSuperAdmin()?null:userAdminDto.getUserId()
		);

		return getSuccessResponseVo(null);
	}

	@RequestMapping("delShareBatch")
	@GlobalInterceptor()
	public ResponseVo<Void> delShareBatch(@VerifyParam(required = true) String shareIds){

		shareInfoService.delShareInfoBatchByIds(shareIds, null);

		return getSuccessResponseVo(null);
	}

	@RequestMapping("postShare") // 修改指定ids 的状态
	@GlobalInterceptor()
	public ResponseVo<Void> postShare(@VerifyParam(required = true) String shareIds){

		updateStatus(shareIds, PostStatusEnum.POST.getStatus());
		return getSuccessResponseVo(null);
	}

	@RequestMapping("cancelPostShare") // 修改指定ids 的状态
	@GlobalInterceptor()
	public ResponseVo<Void> cancelPostShare(@VerifyParam(required = true) String shareIds){

		updateStatus(shareIds, PostStatusEnum.NO_POST.getStatus());
		return getSuccessResponseVo(null);
	}

	private void updateStatus(String shareIds, Integer stat){

		ShareInfoQuery shareQry = new ShareInfoQuery();
		shareQry.setShareIds(shareIds.split(","));

		ShareInfo shareInfo = new ShareInfo();
		shareInfo.setStatus(stat);

		shareInfoService.updateByParam(shareInfo, shareQry);
	}

	@RequestMapping("/showShareDetailNext") // 上一条下一条
	@GlobalInterceptor
	public ResponseVo<ShareInfo> showShareDetailNext(
			ShareInfoQuery shareQry,
			@VerifyParam(required = true)Integer curId,
			Integer nxtType
	){

		ShareInfo shareInfo = shareInfoService.showDetailNext(shareQry, curId, nxtType, false);
		return getSuccessResponseVo(shareInfo);
	}
}