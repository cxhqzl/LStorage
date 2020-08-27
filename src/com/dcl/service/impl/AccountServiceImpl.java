package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.AccountDao;
import com.dcl.domain.Account;
import com.dcl.service.AccountService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public boolean register(Account account) {
		int res = accountDao.insert(account);
		if(res > 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean delAccount(String account) {
		int res = accountDao.delete(account);
		if(res > 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean modifiy(Map<String, Object> params) {
		int res = accountDao.update(params);
		if(res > 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Account> query(Map<String, Object> params) {
		return accountDao.select(params);
	}

}
