package com.easyjob.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.Menu;
import com.easyjob.entity.query.MenuQuery;
import com.easyjob.entity.vo.MenuVo;
import com.easyjob.enums.SysAccStatusEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.service.MenuService;
import com.easyjob.utils.CopyTools;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.easyjob.entity.po.Account;
import com.easyjob.entity.query.AccountQuery;
import com.easyjob.entity.vo.PaginationResultVo;
import com.easyjob.service.AccountService;
import com.easyjob.mappers.AccountMappers;
import com.easyjob.entity.query.SimplePage;
import com.easyjob.enums.PageSize;
import com.easyjob.utils.StringTools;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Resource
	private AccountMappers<Account, AccountQuery> accMapper;

	@Resource
	private MenuService menuSvc;

	@Override
	public List<Account> findListByParam(AccountQuery query){

		return this.accMapper.selectList(query);
	}
	@Override
	public Integer findCntByParam(AccountQuery query){

		return this.accMapper.selectCount(query);
	}
	@Override
	public PaginationResultVo<Account> findListByPage(AccountQuery query){

		Integer cnt = this.findCntByParam(query);
		Integer pageSize = query.getPageSize()==null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), cnt, pageSize);
		query.setSimplePage(page);
		List<Account> list = this.findListByParam(query);
		PaginationResultVo<Account> res = new PaginationResultVo(cnt, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return res;
	}

	@Override
	public Integer add(Account bean){

		return this.accMapper.insert(bean);
	}
	@Override
	public Integer addBatch(List<Account> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.accMapper.insertBatch(listBean);
	}
	@Override
	public Integer addOrUpdateBatch(List<Account> listBean){

		if(listBean == null || listBean.isEmpty()){
			return 0;
		}
		return this.accMapper.insertOrUpdateBatch(listBean);
	}

	@Override
	public Integer updateByParam(Account bean, AccountQuery query) {
		StringTools.checkParam(query);
		return this.accMapper.updateByParam(bean, query);
	}
	@Override
	public Integer deleteByParam(AccountQuery query) {
		StringTools.checkParam(query);
		return this.accMapper.deleteByParam(query);
	}

	@Override
	public Account getAccountByUserId(Integer userId){

		return this.accMapper.selectAccountByUserId(userId);
	}
	@Override
	public Integer updateAccountByUserId(Account bean, Integer userId){

		return this.accMapper.updateAccountByUserId(bean, userId);
	}
	@Override
	public Integer deleteAccountByUserId(Integer userId){

		return this.accMapper.deleteAccountByUserId(userId);
	}

	@Override
	public Account getAccountByPhone(String phone){

		return this.accMapper.selectAccountByPhone(phone);
	}
	@Override
	public Integer updateAccountByPhone(Account bean, String phone){

		return this.accMapper.updateAccountByPhone(bean, phone);
	}
	@Override
	public Integer deleteAccountByPhone(String phone){

		return this.accMapper.deleteAccountByPhone(phone);
	}

	@Override
	public SessionUserAdminDto login(String phone, String password) { // 验证码正确 再校验ses

		Account acc = accMapper.selectAccountByPhone(phone); // select by phone

		if(acc == null) throw new BusinessException("账号不存在"); // 依据库中内容 给出业务异常
		if(SysAccStatusEnum.DISABLE.getStatus().equals(acc.getStatus())) throw new BusinessException("账号已禁用"); // 冻结
		if(!acc.getPassword().equals(password)) throw new BusinessException("密码错误"); // 确定查到 再比密码

		// 确定能返回 则 填充菜单信息   登录接口无需完整Menu信息因此改为MenuVo
		MenuQuery qry = new MenuQuery();
		qry.setFormat2Tree(false);
		qry.setOrderBy("sort asc"); // + order by sort asc 根据 sort 字段排序列表
		List<Menu> menuLst = menuSvc.findListByParam(qry); // 给该用户提供所有菜单 前端根据权限码调整详情

		List<String> permissionLst = new ArrayList<>(); // 权限
		menuLst.forEach(it -> permissionLst.add(it.getPermissionCode()));

		menuLst = menuSvc.convertLine2Tree4Menu(menuLst, 0); // 手动转树 不加根标签
		List<MenuVo> menoVoLst = new ArrayList<>(); // 构建前端可见信息
		menuLst.forEach(it->{

			MenuVo menuVo = CopyTools.copy(it, MenuVo.class); // Menu -> MenuVo 构造
			menuVo.setChildren(CopyTools.copyList(it.getChildren(), MenuVo.class)); // MenuVo.children
			menoVoLst.add(menuVo);
		});

		SessionUserAdminDto adminDto = new SessionUserAdminDto();
		adminDto.setUserId(acc.getUserId()); // ses mem userId
		adminDto.setUserName(acc.getUserName());
		adminDto.setSuperAdmin(true); // 空构造+setX
		adminDto.setMenuLst(menoVoLst); // 可见菜单
		adminDto.setPermissionLst(permissionLst); // 用户权限码

		return adminDto;
	}
}