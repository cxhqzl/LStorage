package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.dcl.domain.FileSize;
import com.dcl.provider.FileSizeProvider;

public interface FileSizeDao {

	@Insert("INSERT INTO "
			+ "t_filesize(uploadSize,downloadSize,createDate,account) "
			+ "VALUES(#{uploadSize},#{downloadSize},#{createDate},#{account})")
	public int insert(FileSize fileSize);
	
	@Delete("DELETE FROM t_filesize WHERE id = #{id}")
	public int delete(int id);
	
	@UpdateProvider(type=FileSizeProvider.class,method="update")
	public int update(Map<String,Object> params);
	
	@SelectProvider(type=FileSizeProvider.class,method="select")
	public List<FileSize> select(Map<String,Object> params);
	
}
