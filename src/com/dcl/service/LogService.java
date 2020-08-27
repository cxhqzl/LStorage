package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Log;

public interface LogService {
	/**
	 * д�����ݿ�
	 * @param log
	 * @return
	 */
	public boolean addLog(Log log);
	
	/**
	 * ɾ����¼
	 * @param id
	 * @return
	 */
	public boolean delLog(int id);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<Log> query(Map<String,Object> params);
	
	
}
