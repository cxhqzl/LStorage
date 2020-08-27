package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Msg;

public interface MsgService {
	/**
	 * 添加文件
	 * @param msg
	 * @return
	 */
	public boolean addMsg(Msg msg);
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	public boolean delMsg(int id);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<Msg> query(Map<String,Object> params);
	
	
}
