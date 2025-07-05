package com.easyjob.service;

import java.util.List;
import com.easyjob.entity.po.Menu;
import com.easyjob.entity.query.MenuQuery;
import com.easyjob.entity.vo.PaginationResultVo;

public interface MenuService{

	List<Menu> findListByParam(MenuQuery query);
	Integer findCntByParam(MenuQuery query);
	PaginationResultVo<Menu> findListByPage(MenuQuery query);

	Integer add(Menu bean);
	Integer addBatch(List<Menu> bean);
	Integer addOrUpdateBatch(List<Menu> bean);

	Integer updateByParam(Menu bean, MenuQuery query);
	Integer deleteByParam(MenuQuery query);

	Menu getMenuByMenuId(Integer menuId);
	Integer updateMenuByMenuId(Menu bean, Integer menuId);
	Integer deleteMenuByMenuId(Integer menuId);

	List<Menu> convertLine2Tree4Menu(List<Menu> menuLst, Integer pid);
	void saveMenu(Menu menu); // 新菜单入库

}