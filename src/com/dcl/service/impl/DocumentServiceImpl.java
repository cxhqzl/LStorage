package com.dcl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.dao.DocumentDao;
import com.dcl.domain.Document;
import com.dcl.service.DocumentService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentDao documentDao;
	
	@Override
	public boolean addDocument(Document document) {
		int res = documentDao.insert(document);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delDocument(int id) {
		int res = documentDao.delete(id);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean modifiy(Map<String, Object> params) {
		int res = documentDao.update(params);
		if(res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Document> query(Map<String, Object> params) {
		return documentDao.select(params);
	}


}
