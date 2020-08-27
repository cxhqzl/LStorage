package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Document;

public interface DocumentService {
	/**
	 * ����ļ�
	 * @param document
	 * @return
	 */
	public boolean addDocument(Document document);
	
	/**
	 * ɾ���ļ�
	 * @param id
	 * @return
	 */
	public boolean delDocument(int id);
	
	/**
	 * �޸������Ϣ
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<Document> query(Map<String,Object> params);
	
	
}
