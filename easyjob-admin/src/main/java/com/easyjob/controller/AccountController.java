package com.easyjob.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjob.entity.po.Account;
import com.easyjob.entity.query.AccountQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.AccountService;

@RestController("accountController")
@RequestMapping("/account")
public class AccountController extends BaseController{

	@Resource
	private AccountService accountService;

	// 查询列表 查询数量 分页查询 都用一个
	@RequestMapping("loadDataList")
	public ResponseVo loadDataList(AccountQuery query){

		return getSuccessResponseVo(accountService.findListByPage(query));
	}


	@RequestMapping("add")
	public ResponseVo add(Account bean){

		this.accountService.add(bean);
		return getSuccessResponseVo(null);
	}
	@RequestMapping("addBatch")
	public ResponseVo addBatch(@RequestBody List<Account> listBean){

		this.accountService.addBatch(listBean);
		return getSuccessResponseVo(null);
	}
	@RequestMapping("addOrUpdateBatch")
	public ResponseVo addOrUpdateBatch(@RequestBody List<Account> listBean){

		this.accountService.addOrUpdateBatch(listBean);
		return getSuccessResponseVo(null);
	}


	@RequestMapping("getAccountByUserId")
	public ResponseVo getAccountByUserId(Integer userId){

		return getSuccessResponseVo(this.accountService.getAccountByUserId(userId));
	}
	@RequestMapping("updateAccountByUserId")
	public ResponseVo updateAccountByUserId(Account bean, Integer userId){

		this.accountService.updateAccountByUserId(bean, userId);
		return getSuccessResponseVo(null);
	}
	@RequestMapping("deleteAccountByUserId")
	public ResponseVo deleteAccountByUserId(Integer userId){

		this.accountService.deleteAccountByUserId(userId);
		return getSuccessResponseVo(null);
	}


	@RequestMapping("getAccountByPhone")
	public ResponseVo getAccountByPhone(String phone){

		return getSuccessResponseVo(this.accountService.getAccountByPhone(phone));
	}
	@RequestMapping("updateAccountByPhone")
	public ResponseVo updateAccountByPhone(Account bean, String phone){

		this.accountService.updateAccountByPhone(bean, phone);
		return getSuccessResponseVo(null);
	}
	@RequestMapping("deleteAccountByPhone")
	public ResponseVo deleteAccountByPhone(String phone){

		this.accountService.deleteAccountByPhone(phone);
		return getSuccessResponseVo(null);
	}
}