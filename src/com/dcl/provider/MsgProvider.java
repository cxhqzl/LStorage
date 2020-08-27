package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class MsgProvider {
	
	public String select(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM("t_msg");
				if(map.get("id") != null){
					WHERE("id = #{id}");
				}
				if(map.get("context") != null){
					WHERE("context = #{context}");
				}
				if(map.get("noContext") != null){
					WHERE("context != #{noContext}");
				}
				if(map.get("fromAccount") != null){
					WHERE("fromAccount = #{fromAccount}");
				}
				if(map.get("toAccount") != null){
					WHERE("toAccount = #{toAccount}");
				}
				if(map.get("type") != null){
					WHERE("type = #{type}");
				}
			}
		}.toString();
		return sql;
	}
}
