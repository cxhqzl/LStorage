package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.MsgDao;
import com.dcl.domain.Msg;
import com.dcl.service.MsgService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("msgService")
public class MsgServiceImpl implements MsgService {
	
	@Autowired
	private MsgDao msgDao;

	@Override
	public boolean addMsg(Msg msg) {
		int res = msgDao.insert(msg);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delMsg(int id) {
		int res = msgDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Msg> query(Map<String, Object> params) {
		return msgDao.select(params);
	}

}
