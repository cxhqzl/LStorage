package com.dcl.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class FileSizeProvider {
	public String update(Map<String,Object> map) {
		String sql =  new SQL(){
			{
				UPDATE("t_filesize");
				if(map.get("uploadSize") != null){
					SET("uploadSize = #{uploadSize}");
				}
				if(map.get("downloadSize") != null){
					SET("downloadSize = #{downloadSize}");
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
				FROM("t_filesize");
				if(map.get("id") != null){
					WHERE("id = #{id}");
				}
				if(map.get("account") != null){
					WHERE("account = #{account}");
				}
				if(map.get("createDate") != null){
					WHERE("createDate = #{createDate}");
				}
				if(map.get("dateNumber") != null){
					WHERE("DATE_SUB(CURDATE(), INTERVAL #{dateNumber} DAY) <= date(createDate)");
				}
				if(map.get("startDate") != null && map.get("stopDate") != null){
					WHERE("createDate >= #{startDate} AND createDate <= #{stopDate}");
				}
			}
		}.toString();
		return sql;
	}
}
