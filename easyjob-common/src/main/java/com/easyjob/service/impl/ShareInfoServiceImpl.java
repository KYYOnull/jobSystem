package com.easyjob.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.po.QuestionInfo;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.mappers.CommonMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.ShareInfo;
import com.easyjob.entity.query.ShareInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.ShareInfoService;
import com.easyjob.mappers.ShareInfoMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("shareInfoService")
public class ShareInfoServiceImpl implements ShareInfoService{

	@Resource
	private ShareInfoMappers<ShareInfo, ShareInfoQuery> shareInfoMappers;

	@Resource
	private CommonMapper commonMapper;

	@Override
	public List<ShareInfo> findListByParam(ShareInfoQuery query){

		return this.shareInfoMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(ShareInfoQuery query){

		return this.shareInfoMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<ShareInfo> findListByPage(ShareInfoQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<ShareInfo> list = this.findListByParam(query);
		PaginationResultVo<ShareInfo> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(ShareInfo bean){

		return this.shareInfoMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<ShareInfo> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.shareInfoMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<ShareInfo> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.shareInfoMappers.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(ShareInfo bean, ShareInfoQuery query) {
		StringTools.checkParam(query);
		return this.shareInfoMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(ShareInfoQuery query) {
		StringTools.checkParam(query);
		return this.shareInfoMappers.deleteByParam(query);
	}

	@Override
	public ShareInfo getShareInfoByShareId(Integer shareId){

		return this.shareInfoMappers.selectShareInfoByShareId(shareId);
	}
	@Override
	public Integer updateShareInfoByShareId(ShareInfo bean, Integer shareId){

		return this.shareInfoMappers.updateShareInfoByShareId(bean, shareId);
	}
	@Override
	public Integer deleteShareInfoByShareId(Integer shareId){

		return this.shareInfoMappers.deleteShareInfoByShareId(shareId);
	}


	@Override
	public void saveShareInfo(ShareInfo bean, Boolean isSuper) {

		Integer beanId = bean.getShareId();
		if(null == beanId){ // insert
			bean.setCreateTime(new Date());
			this.shareInfoMappers.insert(bean);
		}else { // update
			ShareInfo dbShare = this.shareInfoMappers.selectShareInfoByShareId(beanId);
			if(!dbShare.getCreateUserId().equals(bean.getCreateUserId()) && !isSuper){

				throw new BusinessException(ResponseCodeEnum.CODE_600);
			} // 修改了别人的 shareInfo 会发生在绕过前端时

//			bean.setCreateUserId(null);
//			bean.setCreateUserName(null);
//			bean.setCreateTime(null); // 由于mvc接收的不是完整的bean 可以不置空
			this.shareInfoMappers.updateShareInfoByShareId(bean, beanId);
		}
	}

	@Override
	public void delShareInfoBatchByIds(String shareIds, Integer userId) {

		String[] idArr = shareIds.split(",");
		ShareInfoQuery shareQry = new ShareInfoQuery();

		if(userId != null){

			shareQry.setShareIds(idArr);
			List<ShareInfo> shareLstDb = this.shareInfoMappers.selectList(shareQry);
			List<ShareInfo> curUserShareLst = shareLstDb.stream().filter(it -> !it.getCreateUserId().equals(String.valueOf(userId))).collect(Collectors.toList());
			if(!curUserShareLst.isEmpty()){
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			} // 删了别人
		}
		// 管理员 或 校验成功
		shareQry = new ShareInfoQuery();
		shareQry.setShareIds(idArr);
		this.shareInfoMappers.deleteByParam(shareQry); // 加了 by ids 条件

	}

	@Override
	public ShareInfo showDetailNext(ShareInfoQuery shareQry, Integer curId, Integer nxtType, Boolean updateReadCnt) {

		if(null == nxtType){
			shareQry.setShareId(curId); // 依据字段的普通查询
		}else {
			shareQry.setNxtType(nxtType); // 额外升降序查询前后记录
			shareQry.setCurId(curId);
		}

		ShareInfo shareInfo = shareInfoMappers.showDetailNext(shareQry); // 取前一或后一
		if(shareInfo == null ){
			if(nxtType == null){
				throw new BusinessException("当前 分享内容 不存在");
			}else if (nxtType == -1) {
				throw new BusinessException("已经是第一个分享内容"); // 没有上一个
			}else if (nxtType == 1) {
				throw new BusinessException("已经是最后一个分享内容"); // 没有下一个
			}
		}else if(updateReadCnt){ // qsInfo != null 需要更新阅读量

			commonMapper.updateCount( // 改表名
					Constants.TABLE_NAME_SHARE_INFO, 1,
					null, curId
			);

			shareInfo.setReadCount(shareInfo.getReadCount() + 1); // 给前端
		}

		return shareInfo;
	}
}