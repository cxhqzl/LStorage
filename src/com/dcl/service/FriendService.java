package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Friend;

public interface FriendService {
	/**
	 * 添加文件
	 * @param friend
	 * @return
	 */
	public boolean addFriend(Friend friend);
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	public boolean delFriend(int id);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<Friend> query(Map<String,Object> params);
	
	
}
