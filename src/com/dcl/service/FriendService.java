package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Friend;

public interface FriendService {
	/**
	 * ����ļ�
	 * @param friend
	 * @return
	 */
	public boolean addFriend(Friend friend);
	
	/**
	 * ɾ���ļ�
	 * @param id
	 * @return
	 */
	public boolean delFriend(int id);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<Friend> query(Map<String,Object> params);
	
	
}
