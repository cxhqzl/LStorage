package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;

import com.dcl.domain.Rsa;
import com.dcl.provider.RsaProvider;

public interface RsaDao {

	@Insert("INSERT INTO "
			+ "t_rsa(public_key,private_key) "
			+ "VALUES(#{public_key},#{private_key})")
	public int insert(Rsa rsa);
	
	@Delete("DELETE FROM t_rsa WHERE id = #{id}")
	public int delete(int id);
	
	
	@SelectProvider(type=RsaProvider.class,method="select")
	public List<Rsa> select(Map<String,Object> params);
	
}
