package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.dcl.domain.Document;
import com.dcl.provider.DocumentProvider;

public interface DocumentDao {

	@Insert("INSERT INTO "
			+ "t_document(folderName,pid,fatherName,size,createDate,account) "
			+ "VALUES(#{folderName},#{pid},#{fatherName},#{size},#{createDate},#{account})")
	public int insert(Document document);
	
	@Delete("DELETE FROM t_document WHERE id = #{id}")
	public int delete(int id);
	
	@UpdateProvider(type=DocumentProvider.class,method="update")
	public int update(Map<String,Object> params);
	
	@SelectProvider(type=DocumentProvider.class,method="select")
	public List<Document> select(Map<String,Object> params);
	
}
