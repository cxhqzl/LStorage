package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.dcl.domain.Share;
import com.dcl.provider.ShareProvider;

public interface ShareDao {

	@Insert("INSERT INTO "
			+ "t_share(name,path,fileLock,lockPassword,account,userName,createDate,size) "
			+ "VALUES(#{name},#{path},#{fileLock},#{lockPassword},#{account},#{userName},#{createDate},#{size})")
	public int insert(Share share);
	
	@Delete("DELETE FROM t_share WHERE id = #{id}")
	public int delete(int id);
	
	@UpdateProvider(type=ShareProvider.class,method="update")
	public int update(Map<String,Object> params);
	
	@SelectProvider(type=ShareProvider.class,method="select")
	public List<Share> select(Map<String,Object> params);
	
}
