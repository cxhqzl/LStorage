package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.FileSizeDao;
import com.dcl.domain.FileSize;
import com.dcl.service.FileSizeService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("fileSizeService")
public class FileSizeServiceImpl implements FileSizeService {

	@Autowired
	private FileSizeDao fileSizeDao;
	
	@Override
	public boolean addFileSize(FileSize fileSize) {
		int res = fileSizeDao.insert(fileSize);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delFileSize(int id) {
		int res = fileSizeDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean modifiy(Map<String, Object> params) {
		int res = fileSizeDao.update(params);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<FileSize> query(Map<String, Object> params) {
		return fileSizeDao.select(params);
	}

}
