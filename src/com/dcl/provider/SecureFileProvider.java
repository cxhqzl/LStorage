package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class SecureFileProvider {
	public String update(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				UPDATE("t_securefile");
				if(map.get("folderName") != null){
					SET("folderName = #{folderName}");
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
				FROM("t_securefile");
				if(map.get("account") != null){
					WHERE("account = #{account}");
				}
				if(map.get("fatherName") != null){
					WHERE("fatherName = #{fatherName}");
				}
				if(map.get("pid") != null){
					WHERE("pid = #{pid}");
				}
				if(map.get("dateNumber") != null){
					WHERE("DATE_SUB(CURDATE(), INTERVAL #{dateNumber} DAY) <= date(createDate)");
				}
			}
		}.toString();
		sql = sql + " ORDER BY pid ";
		if(map.get("limit") != null && map.get("index") != null) {
			sql += " LIMIT #{index}, #{limit};";
		}
		return sql;
	}
}
