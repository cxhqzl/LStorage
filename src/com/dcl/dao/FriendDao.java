package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;

import com.dcl.domain.Friend;
import com.dcl.provider.FriendProvider;

public interface FriendDao {

	@Insert("INSERT INTO "
			+ "t_friend(myAccount,friendAccount,createDate) "
			+ "VALUES(#{myAccount},#{friendAccount},#{createDate})")
	public int insert(Friend friend);
	
	@Delete("DELETE FROM t_friend WHERE id = #{id}")
	public int delete(int id);
	
	@SelectProvider(type=FriendProvider.class,method="select")
	public List<Friend> select(Map<String,Object> params);
	
}
