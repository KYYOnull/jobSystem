package com.easyjob.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.Role2Menu;
import com.easyjob.entity.query.Role2MenuQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.Role2MenuService;
import com.easyjob.mappers.Role2MenuMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;


@Service("role2MenuService")
public class Role2MenuServiceImpl implements Role2MenuService{

	@Resource
	private Role2MenuMappers<Role2Menu, Role2MenuQuery> role2MenuMappers;

	@Override
	public List<Role2Menu> findListByParam(Role2MenuQuery query){

		return this.role2MenuMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(Role2MenuQuery query){

		return this.role2MenuMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<Role2Menu> findListByPage(Role2MenuQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<Role2Menu> list = this.findListByParam(query);
		PaginationResultVo<Role2Menu> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer updateByParam(Role2Menu bean, Role2MenuQuery query) {
		StringTools.checkParam(query);
		return this.role2MenuMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(Role2MenuQuery query) {
		StringTools.checkParam(query);
		return this.role2MenuMappers.deleteByParam(query);
	}

	@Override
	public Integer add(Role2Menu bean){

		return this.role2MenuMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<Role2Menu> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.role2MenuMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<Role2Menu> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.role2MenuMappers.insertOrUpdateBatch(listBean);
	}

	@Override
	public Role2Menu getRole2MenuByRoleIdAndMenuId(Integer roleId, Integer menuId){

		return this.role2MenuMappers.selectRole2MenuByRoleIdAndMenuId(roleId, menuId);
	}
	@Override
	public Integer updateRole2MenuByRoleIdAndMenuId(Role2Menu bean, Integer roleId, Integer menuId){

		return this.role2MenuMappers.updateRole2MenuByRoleIdAndMenuId(bean, roleId, menuId);
	}
	@Override
	public Integer deleteRole2MenuByRoleIdAndMenuId(Integer roleId, Integer menuId){

		return this.role2MenuMappers.deleteRole2MenuByRoleIdAndMenuId(roleId, menuId);
	}
}