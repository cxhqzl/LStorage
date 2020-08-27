package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class RsaProvider {
	
	public String select(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM("t_rsa");
				if(map.get("id") != null){
					WHERE("id = #{id}");
				}
			}
		}.toString();
		return sql;
	}
}
