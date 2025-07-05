package com.easyjob.mappers;

import org.apache.ibatis.annotations.Param;


public interface AccountMappers<T, P> extends BaseMapper<T, P> {

	T selectAccountByUserId(@Param("userId") Integer userId);
	Integer updateAccountByUserId(@Param("bean") T t, @Param("userId") Integer userId);
	Integer deleteAccountByUserId(@Param("userId") Integer userId);

	T selectAccountByPhone(@Param("phone") String phone);
	Integer updateAccountByPhone(@Param("bean") T t, @Param("phone") String phone);
	Integer deleteAccountByPhone(@Param("phone") String phone);

}
