package com.easyjob.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyjob.entity.constants.Constants;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.Menu;
import com.easyjob.entity.query.MenuQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.MenuService;
import com.easyjob.mappers.MenuMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("menuService")
public class MenuServiceImpl implements MenuService{

	private static final String ROOT_MENU_NAME= "所有菜单";
	public static final Integer DEFAULT_ROOT_MENU_ID = 0;

	@Resource
	private MenuMappers<Menu, MenuQuery> menuMappers;
	@Override
	public List<Menu> findListByParam(MenuQuery qry){

		List<Menu> menuLst = this.menuMappers.selectList(qry); // svc -> mapper

		if(qry.isFormat2Tree() != null && qry.isFormat2Tree()){ // not null then true

			Menu root = new Menu(); // true -> 加一个根标签
			root.setMenuId(DEFAULT_ROOT_MENU_ID);
			root.setMenuName(ROOT_MENU_NAME);
			root.setPId(-1);
			menuLst.add(root);
			menuLst = convertLine2Tree4Menu(menuLst, -1);

		} // convert to tree

		return menuLst;
	}
	@Override
	public Integer findCntByParam(MenuQuery query){

		return this.menuMappers.selectCount(query);
	}
	@Override
	public PaginationResultVo<Menu> findListByPage(MenuQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<Menu> list = this.findListByParam(query);
		PaginationResultVo<Menu> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(Menu bean){

		return this.menuMappers.insert(bean);
	}
	@Override
	public Integer addBatch(List<Menu> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.menuMappers.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<Menu> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.menuMappers.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(Menu bean, MenuQuery query) {
		StringTools.checkParam(query);
		return this.menuMappers.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(MenuQuery query) {
		StringTools.checkParam(query);
		return this.menuMappers.deleteByParam(query);
	}

	@Override
	public Menu getMenuByMenuId(Integer menuId){

		return this.menuMappers.selectMenuByMenuId(menuId);
	}
	@Override
	public Integer updateMenuByMenuId(Menu bean, Integer menuId){

		return this.menuMappers.updateMenuByMenuId(bean, menuId);
	}
	@Override
	public Integer deleteMenuByMenuId(Integer menuId){

		return this.menuMappers.deleteMenuByMenuId(menuId);
	}

	@Override
	public List<Menu> convertLine2Tree4Menu(List<Menu> menuLst, Integer pid){ // 递归转树

		List<Menu> children = new ArrayList<>(); // 当前层的子
		for(Menu m : menuLst){ // 找到父是传入父的节点 即当前的所有孩子

			if(m.getPId().equals(pid)){ // is child

				m.setChildren(convertLine2Tree4Menu(menuLst, m.getMenuId())); // 以子为父
				children.add(m); // collect children
			}
		}
		return children;
	}
	@Override
	public void saveMenu(Menu menu) {

		if(menu.getMenuId() == null){ // 新增 修改菜单

			this.menuMappers.insert(menu);
		}else {

			this.menuMappers.updateMenuByMenuId(menu, menu.getMenuId());
		}

	}
}