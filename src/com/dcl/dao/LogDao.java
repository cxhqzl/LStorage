package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;

import com.dcl.domain.Log;
import com.dcl.provider.LogProvider;

public interface LogDao {

	@Insert("INSERT INTO "
			+ "t_log(context,type,createDate,source,account) "
			+ "VALUES(#{context},#{type},#{createDate},#{source},#{account})")
	public int insert(Log log);
	
	@Delete("DELETE FROM t_log WHERE id = #{id}")
	public int delete(int id);
	
	@SelectProvider(type=LogProvider.class,method="select")
	public List<Log> select(Map<String,Object> params);
	
}
