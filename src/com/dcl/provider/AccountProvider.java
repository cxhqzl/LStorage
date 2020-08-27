package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {
	public String update(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				UPDATE("t_account");
				if(map.get("password") != null){
					SET("password = #{password}");
				}
				if(map.get("securePassword") != null){
					SET("securePassword = #{securePassword}");
				}
				if(map.get("role") != null){
					SET("role = #{role}");
				}
				if(map.get("userName") != null){
					SET("userName = #{userName}");
				}
				if(map.get("image") != null){
					SET("image = #{image}");
				}
				if(map.get("remark") != null){
					SET("remark = #{remark}");
				}
				if(map.get("type") != null){
					SET("type = #{type}");
				}
				WHERE("account = #{account}");
			}
		}.toString();
		return sql;
	}
	
	public String select(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM("t_account");
				if(map.get("account") != null){
					WHERE("account = #{account}");
				}
				if(map.get("securePassword") != null){
					WHERE("securePassword = #{securePassword}");
				}
				if(map.get("password") != null){
					WHERE("password = #{password}");
				}
				if(map.get("role") != null){
					WHERE("role = #{role}");
				}
				if(map.get("noAccount") != null){
					WHERE("account != #{noAccount}");
				}
				if(map.get("keys") != null){
					WHERE("account LIKE CONCAT ('%',#{keys},'%') "
							+ "OR userName LIKE CONCAT ('%',#{keys},'%')");
				}
			}
		}.toString();
		if(map.get("limit") != null && map.get("index") != null) {
			sql += " LIMIT #{index}, #{limit};";
		}
		return sql;
	}
}
