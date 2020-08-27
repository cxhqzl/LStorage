package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Rsa;

public interface RsaService {
	/**
	 * ����ļ�
	 * @param rsa
	 * @return
	 */
	public boolean addRsa(Rsa rsa);
	
	/**
	 * ɾ���ļ�
	 * @param id
	 * @return
	 */
	public boolean delRsa(int id);
	
	/**
	 * ��ѯ
	 * @param id
	 * @return
	 */
	public List<Rsa> query(Map<String,Object> params);
	
	
}
