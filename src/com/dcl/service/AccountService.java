package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Account;

public interface AccountService {
	/**
	 * 写入数据库
	 * @param account
	 * @return
	 */
	public boolean register(Account account);
	
	/**
	 * 删除记录
	 * @param account
	 * @return
	 */
	public boolean delAccount(String account);
	
	/**
	 * 修改相关信息
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<Account> query(Map<String,Object> params);
	
	
}
