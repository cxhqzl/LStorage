package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Log;

public interface LogService {
	/**
	 * 写入数据库
	 * @param log
	 * @return
	 */
	public boolean addLog(Log log);
	
	/**
	 * 删除记录
	 * @param id
	 * @return
	 */
	public boolean delLog(int id);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<Log> query(Map<String,Object> params);
	
	
}
