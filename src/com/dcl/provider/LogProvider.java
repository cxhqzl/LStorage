package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class LogProvider {
	
	public String select(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM("t_log");
				if(map.get("account") != null){
					WHERE("account = #{account}");
				}
				if(map.get("id") != null){
					WHERE("id = #{id}");
				}
				if(map.get("startDate") != null && map.get("stopDate") != null){
					WHERE("createDate >= #{startDate} AND createDate <= #{stopDate}");
				}
				if(map.get("type") != null){
					WHERE("type = #{type}");
				}
				if(map.get("source") != null){
					WHERE("source = #{source}");
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
