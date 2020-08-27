package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.ShareDao;
import com.dcl.domain.Share;
import com.dcl.service.ShareService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("shareService")
public class ShareServiceImpl implements ShareService {

	@Autowired
	private ShareDao shareDao;
	
	@Override
	public boolean register(Share share) {
		int res = shareDao.insert(share);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delShare(int id) {
		int res = shareDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean modifiy(Map<String, Object> params) {
		int res = shareDao.update(params);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Share> query(Map<String, Object> params) {
		return shareDao.select(params);
	}

}
