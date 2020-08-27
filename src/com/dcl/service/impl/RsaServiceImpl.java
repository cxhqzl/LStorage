package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.RsaDao;
import com.dcl.domain.Rsa;
import com.dcl.service.RsaService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("rsaService")
public class RsaServiceImpl implements RsaService {

	@Autowired
	private RsaDao rsaDao;
	
	@Override
	public boolean addRsa(Rsa rsa) {
		int res = rsaDao.insert(rsa);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delRsa(int id) {
		int res = rsaDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Rsa> query(Map<String,Object> params) {
		return rsaDao.select(params);
	}

}
