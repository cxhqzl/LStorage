package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Account;

public interface AccountService {
	/**
	 * д�����ݿ�
	 * @param account
	 * @return
	 */
	public boolean register(Account account);
	
	/**
	 * ɾ����¼
	 * @param account
	 * @return
	 */
	public boolean delAccount(String account);
	
	/**
	 * �޸������Ϣ
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<Account> query(Map<String,Object> params);
	
	
}
