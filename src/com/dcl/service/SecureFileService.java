package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.SecureFile;

public interface SecureFileService {
	/**
	 * 添加文件
	 * @param document
	 * @return
	 */
	public boolean addSecureFile(SecureFile secureFile);
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	public boolean delSecureFile(int id);
	
	/**
	 * 修改相关信息
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<SecureFile> query(Map<String,Object> params);
	
	
}
