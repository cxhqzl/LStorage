package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.LogDao;
import com.dcl.domain.Log;
import com.dcl.service.LogService;


@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("logService")
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;
	
	@Override
	public boolean addLog(Log log) {
		int res = logDao.insert(log);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delLog(int id) {
		int res = logDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Log> query(Map<String, Object> params) {
		return logDao.select(params);
	}

}
