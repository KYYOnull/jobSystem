package com.easyjob.service;

import java.util.List;

import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.Account;
import com.easyjob.entity.query.AccountQuery;
import com.easyjob.entity.vo.PaginationResultVo;


public interface AccountService{

	List<Account> findListByParam(AccountQuery query);
	Integer findCntByParam(AccountQuery query);
	PaginationResultVo<Account> findListByPage(AccountQuery query);

	Integer add(Account bean);
	Integer addBatch(List<Account> bean);
	Integer addOrUpdateBatch(List<Account> bean);

	Integer updateByParam(Account bean, AccountQuery query);
	Integer deleteByParam(AccountQuery query);

	Account getAccountByUserId(Integer userId);
	Integer updateAccountByUserId(Account bean, Integer userId);
	Integer deleteAccountByUserId(Integer userId);

	Account getAccountByPhone(String phone);
	Integer updateAccountByPhone(Account bean, String phone);
	Integer deleteAccountByPhone(String phone);

	SessionUserAdminDto login(String phone, String password) ;

}