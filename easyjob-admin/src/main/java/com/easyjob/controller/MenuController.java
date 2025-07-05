package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.Menu;
import com.easyjob.entity.query.MenuQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.MenuService;


@RestController("menuController")
@RequestMapping("/settings") // 设置模块
public class MenuController extends BaseController{

	@Resource
	private MenuService menuSvc;

	@RequestMapping("/menuList")
	@GlobalInterceptor
	public ResponseVo<List<Menu>> menuList(){

		MenuQuery query = new MenuQuery();
		query.setFormat2Tree(true);
		query.setOrderBy("sort asc"); // + order by sort asc 根据 sort 字段排序列表
		List<Menu> menuLst = menuSvc.findListByParam(query); // 菜单管理时 要完整Menu

		return getSuccessResponseVo(menuLst);
	}

	@RequestMapping("/saveMenu") // 前端展示该用户可操作的菜单 用户可以提交创建新菜单目录
	@GlobalInterceptor // 加了才能参数校验
	public ResponseVo<List<Menu>> saveMenu(@VerifyParam Menu menu){

		menuSvc.saveMenu(menu);
		return getSuccessResponseVo(null);
	}

	@RequestMapping("/delMenu")
	@GlobalInterceptor
	public ResponseVo<List<Menu>> delMenu(@VerifyParam(required = true) Integer menuId){

		System.out.println("after jrebel 我们可以热部署");
//		menuSvc.deleteMenuByMenuId(menuId);
		return getSuccessResponseVo(null);
	}
}