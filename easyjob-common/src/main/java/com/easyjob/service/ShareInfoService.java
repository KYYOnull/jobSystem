package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.ShareInfo;
import com.easyjob.entity.query.ShareInfoQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface ShareInfoService{

	List<ShareInfo> findListByParam(ShareInfoQuery query);
	Integer findCntByParam(ShareInfoQuery query);
	PaginationResultVo<ShareInfo> findListByPage(ShareInfoQuery query);

	Integer add(ShareInfo bean);
	Integer addBatch(List<ShareInfo> bean);
	Integer addOrUpdateBatch(List<ShareInfo> bean);

	Integer updateByParam(ShareInfo bean, ShareInfoQuery query);
	Integer deleteByParam(ShareInfoQuery query);

	ShareInfo getShareInfoByShareId(Integer shareId);
	Integer updateShareInfoByShareId(ShareInfo bean, Integer shareId);
	Integer deleteShareInfoByShareId(Integer shareId);

	void saveShareInfo(ShareInfo bean, Boolean isSuper);
	void delShareInfoBatchByIds(String shareIds, Integer userId);
	ShareInfo showDetailNext(ShareInfoQuery shareQry, Integer curId, Integer nxtType, Boolean updateReadCnt);
}