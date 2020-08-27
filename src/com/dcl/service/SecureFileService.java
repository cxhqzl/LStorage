package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.SecureFile;

public interface SecureFileService {
	/**
	 * ����ļ�
	 * @param document
	 * @return
	 */
	public boolean addSecureFile(SecureFile secureFile);
	
	/**
	 * ɾ���ļ�
	 * @param id
	 * @return
	 */
	public boolean delSecureFile(int id);
	
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
	public List<SecureFile> query(Map<String,Object> params);
	
	
}
