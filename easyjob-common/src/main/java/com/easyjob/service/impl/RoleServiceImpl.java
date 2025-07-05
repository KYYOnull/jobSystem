package com.easyjob.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.easyjob.entity.po.Role2Menu;
import com.easyjob.entity.query.Role2MenuQuery;
import com.easyjob.enums.MenuCheckTypeEnum;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.mappers.Role2MenuMappers;
import com.easyjob.utils.StringTools;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.Role;
import com.easyjob.entity.query.RoleQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.RoleService;
import com.easyjob.mappers.RoleMappers;
import com.easyjob.entity.query.SimplePage;import com.easyjob.enums.PageSize;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleMappers<Role, RoleQuery> roleMappers;

	@Resource
	private Role2MenuMappers<Role2Menu, Role2MenuQuery> role2MenuMappers;

	@Override
	public List<Role> findListByParam(RoleQuery query){

		return this.roleMappers.selectList(query);
	}
	@Override
	public Integer findCntByParam(RoleQuery query){

		return this.roleMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<Role> findListByPage(RoleQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<Role> list = this.findListByParam(query);
		PaginationResultVo<Role> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(Role bean){

		return this.roleMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<Role> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.roleMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<Role> listBean){

		if(listBean == null || listBean.isEmpty()) return 0;
		return this.roleMappers.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(Role bean, RoleQuery query) {
		StringTools.checkParam(query);
		return this.roleMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(RoleQuery query) {
		StringTools.checkParam(query);
		return this.roleMappers.deleteByParam(query);
	}

	@Override
	public Role getRoleByRoleId(Integer roleId){

		return this.roleMappers.selectRoleByRoleId(roleId);
	}
	@Override
	public Integer updateRoleByRoleId(Role bean, Integer roleId){

		return this.roleMappers.updateRoleByRoleId(bean, roleId);
	}
	@Override
	public Integer deleteRoleByRoleId(Integer roleId){

		return this.roleMappers.deleteRoleByRoleId(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRole(Role ro, String menuIds, String halfMenuIds) {

		Integer roleId = ro.getRoleId();
		boolean isAdd= false;
		if(null==roleId) {

			Date curDate = new Date();
			ro.setCreateTime(curDate);
			ro.setLastUpdateTime(curDate);
			roleId= this.roleMappers.insert(ro);
            isAdd = true;
		}else {

			ro.setCreateTime(null);
			this.roleMappers.updateRoleByRoleId(ro, roleId);
		}

		RoleQuery roQry = new RoleQuery(); // 加进去后 不行再回滚 从而实现在没有索引情况下保证唯一
		roQry.setRoleName(ro.getRoleName());
		Integer roCnt = roleMappers.selectCount(roQry);
		if(roCnt>1) throw new BusinessException("角色已经存在");

		if(isAdd) saveRoleMenu(roleId, menuIds, halfMenuIds);
	}

	public void saveRoleMenu(Integer roleId, String menuIds, String halfMenuIds) {

		if(null==roleId || null==menuIds) throw new BusinessException(ResponseCodeEnum.CODE_600);

		Role2MenuQuery ro2MeQry = new Role2MenuQuery();
		ro2MeQry.setRoleId(roleId);
		role2MenuMappers.deleteByParam(ro2MeQry);

		String[] menuIdArr = menuIds.split(",");
		String[] halfMenuIdArr = StringTools.isEmpty(halfMenuIds)?
				new String[0] : halfMenuIds.split(",");

		ArrayList<Role2Menu> ro2MeLst = new ArrayList<>();
		for(String menuId : menuIdArr){
			Role2Menu ro2Me = new Role2Menu();
			ro2Me.setMenuId(Integer.parseInt(menuId));
			ro2Me.setRoleId(roleId);
			ro2Me.setCheckType(MenuCheckTypeEnum.ALL.getType());
			ro2MeLst.add(ro2Me);
		}
		for(String halfMenuId : halfMenuIdArr){
			Role2Menu ro2Me = new Role2Menu();
			ro2Me.setMenuId(Integer.parseInt(halfMenuId));
			ro2Me.setRoleId(roleId);
			ro2Me.setCheckType(MenuCheckTypeEnum.HALF.getType());
			ro2MeLst.add(ro2Me);
		}

		if(!ro2MeLst.isEmpty()) role2MenuMappers.insertBatch(ro2MeLst);
	}

	private void transMenuId2RoMe(List<Role2Menu> ro2MeLst, Integer roleId){

		Role2Menu ro2Me = new Role2Menu();
//		ro2Me.setMenuId(Integer.parseInt(halfMenuId));
		ro2Me.setRoleId(roleId);
		ro2Me.setCheckType(MenuCheckTypeEnum.HALF.getType());
		ro2MeLst.add(ro2Me);
	}
}