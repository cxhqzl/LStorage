package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Share;

public interface ShareService {
	/**
	 * д�����ݿ�
	 * @param share
	 * @return
	 */
	public boolean register(Share share);
	
	/**
	 * ɾ����¼
	 * @param id
	 * @return
	 */
	public boolean delShare(int id);
	
	/**
	 * �޸ļ�¼
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<Share> query(Map<String,Object> params);
	
	
}
