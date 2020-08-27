package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.FriendDao;
import com.dcl.domain.Friend;
import com.dcl.service.FriendService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("friendService")
public class FriendServiceImpl implements FriendService {
	
	@Autowired
	private FriendDao friendDao;

	@Override
	public boolean addFriend(Friend friend) {
		int res = friendDao.insert(friend);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delFriend(int id) {
		int res = friendDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Friend> query(Map<String, Object> params) {
		return friendDao.select(params);
	}

}
