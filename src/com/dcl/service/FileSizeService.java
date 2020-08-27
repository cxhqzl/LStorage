package com.dcl.service;

import java.util.List;
import java.util.Map;

import com.dcl.domain.FileSize;

public interface FileSizeService {
	/**
	 * ���
	 * @param fileSize
	 * @return
	 */
	public boolean addFileSize(FileSize fileSize);
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	public boolean delFileSize(int id);
	
	/**
	 * �޸������Ϣ
	 * @param params
	 * @return
	 */
	public boolean modifiy(Map<String,Object> params);
	
	/**
	 * ��ѯ
	 * @param params
	 * @return
	 */
	public List<FileSize> query(Map<String,Object> params);
	
	
}
