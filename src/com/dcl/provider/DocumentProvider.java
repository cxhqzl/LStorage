package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class DocumentProvider {
	public String update(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				UPDATE("t_document");
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
				FROM("t_document");
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
				if(map.get("keys") != null){
					WHERE("folderName LIKE CONCAT ('%',#{keys},'%') ");
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
