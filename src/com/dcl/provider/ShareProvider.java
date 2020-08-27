package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class ShareProvider {
	public String update(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				UPDATE("t_share");
				if(map.get("lock") != null){
					SET("lock = #{lock}");
				}
				if(map.get("lockPassword") != null){
					SET("lockPassword = #{lockPassword}");
				}
				WHERE("id = #{id}");
			}
		}.toString();
		return sql;
	}
	
	public String select(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM("t_share");
				if(map.get("id") != null){
					WHERE("id = #{id}");
				}
				if(map.get("name") != null){
					WHERE("name = #{name}");
				}
				if(map.get("fileLock") != null){
					WHERE("fileLock = #{fileLock}");
				}
				if(map.get("lockPassword") != null){
					WHERE("lockPassword = #{lockPassword}");
				}
				if(map.get("account") != null){
					WHERE("account = #{account}");
				}
				if(map.get("todayDate") != null){
					WHERE("TO_DAYS(createDate) = TO_DAYS(NOW())");
				}
			}
		}.toString(); 
		sql = sql + " ORDER BY createDate DESC ";
		if(map.get("limit") != null && map.get("index") != null) {
			sql += " LIMIT #{index}, #{limit};";
		}
		return sql;
	}

}
