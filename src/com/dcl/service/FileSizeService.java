package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.FileSize;

public interface FileSizeService {
	/**
	 * 添加
	 * @param fileSize
	 * @return
	 */
	public boolean addFileSize(FileSize fileSize);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delFileSize(int id);
	
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
	public List<FileSize> query(Map<String,Object> params);
	
	
}
