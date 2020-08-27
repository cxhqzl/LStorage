package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.dcl.domain.SecureFile;
import com.dcl.provider.SecureFileProvider;

public interface SecureFileDao {

	@Insert("INSERT INTO "
			+ "t_securefile(folderName,pid,fatherName,size,createDate,account) "
			+ "VALUES(#{folderName},#{pid},#{fatherName},#{size},#{createDate},#{account})")
	public int insert(SecureFile secureFile);
	
	@Delete("DELETE FROM t_securefile WHERE id = #{id}")
	public int delete(int id);
	
	@UpdateProvider(type=SecureFileProvider.class,method="update")
	public int update(Map<String,Object> params);
	
	@SelectProvider(type=SecureFileProvider.class,method="select")
	public List<SecureFile> select(Map<String,Object> params);
	
}
