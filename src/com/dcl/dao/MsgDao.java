package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;

import com.dcl.domain.Msg;
import com.dcl.provider.MsgProvider;

public interface MsgDao {

	@Insert("INSERT INTO "
			+ "t_msg(context,fromAccount,toAccount,createDate,type) "
			+ "VALUES(#{context},#{fromAccount},#{toAccount},#{createDate},#{type})")
	public int insert(Msg msg);
	
	@Delete("DELETE FROM t_msg WHERE id = #{id}")
	public int delete(int id);
	
	@SelectProvider(type=MsgProvider.class,method="select")
	public List<Msg> select(Map<String,Object> params);
	
}
