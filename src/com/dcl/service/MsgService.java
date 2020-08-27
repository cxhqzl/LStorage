package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Msg;

public interface MsgService {
	/**
	 * ����ļ�
	 * @param msg
	 * @return
	 */
	public boolean addMsg(Msg msg);
	
	/**
	 * ɾ���ļ�
	 * @param id
	 * @return
	 */
	public boolean delMsg(int id);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<Msg> query(Map<String,Object> params);
	
	
}
