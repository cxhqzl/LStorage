package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Share;

public interface ShareService {
	/**
	 * 写入数据库
	 * @param share
	 * @return
	 */
	public boolean register(Share share);
	
	/**
	 * 删除记录
	 * @param id
	 * @return
	 */
	public boolean delShare(int id);
	
	/**
	 * 修改记录
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<Share> query(Map<String,Object> params);
	
	
}
