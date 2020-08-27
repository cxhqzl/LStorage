package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.Rsa;

public interface RsaService {
	/**
	 * 添加文件
	 * @param rsa
	 * @return
	 */
	public boolean addRsa(Rsa rsa);
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	public boolean delRsa(int id);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public List<Rsa> query(Map<String,Object> params);
	
	
}
