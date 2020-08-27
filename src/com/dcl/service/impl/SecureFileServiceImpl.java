package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.SecureFileDao;
import com.dcl.domain.SecureFile;
import com.dcl.service.SecureFileService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("secureFileService")
public class SecureFileServiceImpl implements SecureFileService {

	@Autowired
	private SecureFileDao secureFileDao;
	
	@Override
	public boolean addSecureFile(SecureFile secureFile) {
		int res = secureFileDao.insert(secureFile);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delSecureFile(int id) {
		int res = secureFileDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean modifiy(Map<String, Object> params) {
		int res = secureFileDao.update(params);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<SecureFile> query(Map<String, Object> params) {
		return secureFileDao.select(params);
	}


}
