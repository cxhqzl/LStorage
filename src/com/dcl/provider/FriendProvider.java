package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class FriendProvider {
	
	public String select(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM("t_friend");
				if(map.get("myAccount") != null){
					WHERE("myAccount = #{myAccount}");
				}
				if(map.get("friendAccount") != null){
					WHERE("friendAccount = #{friendAccount}");
				}
				if(map.get("id") != null){
					WHERE("id = #{id}");
				}
			}
		}.toString();
		return sql;
	}
}
