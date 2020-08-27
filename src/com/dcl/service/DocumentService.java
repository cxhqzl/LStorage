package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Document;

public interface DocumentService {
	/**
	 * 添加文件
	 * @param document
	 * @return
	 */
	public boolean addDocument(Document document);
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	public boolean delDocument(int id);
	
	/**
	 * 修改相关信息
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<Document> query(Map<String,Object> params);
	
	
}
