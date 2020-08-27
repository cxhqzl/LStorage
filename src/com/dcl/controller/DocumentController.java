package com.dcl.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.domain.Document;
import com.dcl.domain.Log;
import com.dcl.domain.SecureFile;
import com.dcl.service.DocumentService;
import com.dcl.service.LogService;
import com.dcl.service.SecureFileService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	/**
	 * 创建文件夹
	 * @param fileName
	 * @param path
	 * @return
	 */
	@RequestMapping(value="/document/createFolder")
	@ResponseBody
	JSONObject addDocument(HttpServletRequest request,
			@RequestParam("fileName") String fileName,
			@RequestParam("path") String path) {
		JSONObject json = new JSONObject();
		int c_pid = 1;
		if(path.indexOf(",") != -1) {
			c_pid = path.split(",").length;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", c_pid);
		List <Document> ds = ((DocumentService) Beans.getBean("documentService")).query(params);
		params.clear();
		for(Document d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		if(params.containsKey(fileName)) {
			json.put("type", 1);
			json.put("msg", "新建文件夹");
			return json;
		}
		int pid = 1;
		String fatherName = "";
		String account = "";
		if(path.indexOf(",") == -1) {
			pid = 1;
			fatherName = path;
			account = path;
		}else {
			String[] folders = path.split(",");
			pid = folders.length;
			fatherName = folders[pid - 1];
			account = folders[0];
		}
		BasicUtils.getStoragePath(path.replace(",", "\\") + "\\" + fileName);
		Document d = new Document(0,fileName,pid,fatherName,-1,BasicUtils.getDatetime(),account);
		boolean flag = documentService.addDocument(d);
		LogService logService = (LogService) Beans.getBean("logService");
		Log log = new Log(0,"新建文件夹："+fileName,"新建",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
		logService.addLog(log);
		json.put("flag", flag);
		return json;
	}
	/**
	 * 修改文件夹
	 * @param fileName
	 * @param path
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/document/modifiyDocument")
	@ResponseBody
	JSONObject modifiyDocument(@RequestParam("fileName") String fileName,
							@RequestParam("path") String path,
							@RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		int c_pid = 1;
		if(path.indexOf(",") != -1) {
			c_pid = path.split(",").length;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", c_pid);
		List <Document> ds = ((DocumentService) Beans.getBean("documentService")).query(params);
		params.clear();
		for(Document d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		if(params.containsKey(fileName)) {
			json.put("type", 1);
			json.put("msg", "修改文件夹");
			return json;
		}
		params.clear();
		params.put("id", id);
		Document d = documentService.query(params).get(0);
		String filePath = BasicUtils.getStoragePath(path.replace(",", "\\") + "\\" + d.getFolderName());
		boolean flag = BasicUtils.modifiyName(filePath, fileName);
		if(flag) {
			params.put("folderName", fileName);
			flag = documentService.modifiy(params);
		}
		json.put("flag", flag);
		return json;
	}
	/**
	 * 获取文件
	 * @param path
	 * @return
	 */
	@RequestMapping(value="/document/getDocument")
	@ResponseBody
	JSONObject getDocument(@RequestParam("path") String path,
			@RequestParam("index") int index) {
		JSONObject json = new JSONObject();
		int pid = 1;
		String account = "";
		String fatherName = "";
		if(path.indexOf(",") == -1) {
			pid = 1;
			account = path;
			fatherName = path;
		}else {
			String[] folders = path.split(",");
			pid = folders.length;
			account = folders[0];
			fatherName = folders[pid - 1];
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pid);
		params.put("account", account);
		params.put("fatherName", fatherName);
		int size = documentService.query(params).size();
		json.put("size", size);
		json.put("pages", Math.ceil((float) size / 8));
		params.put("limit", 8);
		index = (index - 1) * 8;
		params.put("index", index);
		List<Document> ds = documentService.query(params);
		json.put("documents", JSONArray.fromObject(ds));
		json.put("index", index);
		return json;
	}
	@RequestMapping(value="/document/searchDocument")
	@ResponseBody
	JSONObject searchDocument(@RequestParam("path") String path,
			@RequestParam("index") int index,
			@RequestParam("keys") String keys) {
		JSONObject json = new JSONObject();
		int pid = 1;
		String account = "";
		String fatherName = "";
		if(path.indexOf(",") == -1) {
			pid = 1;
			account = path;
			fatherName = path;
		}else {
			String[] folders = path.split(",");
			pid = folders.length;
			account = folders[0];
			fatherName = folders[pid - 1];
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pid);
		params.put("account", account);
		params.put("fatherName", fatherName);
		params.put("keys", keys);
		int size = documentService.query(params).size();
		json.put("size", size);
		json.put("pages", Math.ceil((float) size / 8));
		params.put("limit", 8);
		index = (index - 1) * 8;
		params.put("index", index);
		List<Document> ds = documentService.query(params);
		json.put("documents", JSONArray.fromObject(ds));
		json.put("index", index);
		return json;
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/document/deleteDocument")
	@ResponseBody
	JSONObject deleteDocument(HttpServletRequest request,
							@RequestParam("ids") String ids,
							@RequestParam("path") String path,
							@RequestParam("fileNames") String fileNames) {
		JSONObject json = new JSONObject();
		String path1 = BasicUtils.getStoragePath(path.replace(",", "\\"));
		String account = "";
		if(path.indexOf(",") == -1) {
			account = path;
		}else {
			account = path.split(",")[0];
		}
		if(ids.indexOf(",") == -1) {
			boolean flag = BasicUtils.delFile(path1 + "\\" + fileNames);
			if(flag) {
				flag = documentService.delDocument(Integer.parseInt(ids));
			}else {
				json.put("msg", "删除失败");
			}
			json.put("flag", flag);
		}else {
			String[] IDs = ids.split(",");
			String[] fns = fileNames.split(",");
			for(int i=0;i<IDs.length;i++) {
				boolean flag = BasicUtils.delFile(path1 + "\\" + fns[i]);
				if(flag) {
					flag = documentService.delDocument(Integer.parseInt(IDs[i]));
					LogService logService = (LogService) Beans.getBean("logService");
					Log log = new Log(0,"删除文件："+fileNames,"删除",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
					logService.addLog(log);
				}else{
					json.put("msg", "删除失败");
				}
				json.put("flag", flag);
			}
		}
		return json;
	}
	/**
	 * 获取安全文件
	 * @param request
	 * @param ids
	 * @param path
	 * @param fileNames
	 * @return
	 */
	@RequestMapping(value="/document/clockFiles")
	@ResponseBody
	JSONObject clockFiles(HttpServletRequest request,
							@RequestParam("ids") String ids,
							@RequestParam("path") String path,
							@RequestParam("fileNames") String fileNames) {
		JSONObject json = new JSONObject();
		String path1 = BasicUtils.getStoragePath(path.replace(",", "\\"));
		String account = "";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", 2);
		params.put("fatherName", "secure");
		SecureFileService secureFileService = (SecureFileService) Beans.getBean("secureFileService");
		List <SecureFile> ds = secureFileService.query(params);
		for(SecureFile d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		if(path.indexOf(",") == -1) {
			account = path;
		}else {
			account = path.split(",")[0];
		}
		if(ids.indexOf(",") == -1) {
			String oldPath = path1 + "\\" + fileNames;
			while(params.containsKey(fileNames)) {
				fileNames = fileNames.substring(0,fileNames.lastIndexOf(".")) + "(1)" + fileNames.substring(fileNames.lastIndexOf("."),fileNames.length());
			}
			String newPath = BasicUtils.getStoragePath(account) + "\\secure\\" + fileNames;
			boolean flag = BasicUtils.moveFile(oldPath, newPath);
			documentService.delDocument(Integer.parseInt(ids));
			SecureFile d = new SecureFile(0,fileNames,2,"secure",new File(newPath).length(),BasicUtils.getDatetime(),account);
			secureFileService.addSecureFile(d);
			json.put("flag", flag);
		}else {
			String[] IDs = ids.split(",");
			String[] fns = fileNames.split(",");
			for(int i=0;i<IDs.length;i++) {
				String oldPath = path1 + "\\" + fns[i];
				String fileName = fns[i];
				while(params.containsKey(fns[i])) {
					fileName = fileName.substring(0,fileName.lastIndexOf(".")) + "(1)" + fileName.substring(fileName.lastIndexOf("."),fileName.length());
				}
				String newPath = BasicUtils.getStoragePath(account) + "\\secure\\" + fileName;
				boolean flag = BasicUtils.moveFile(oldPath, newPath);
				documentService.delDocument(Integer.parseInt(IDs[i]));
				SecureFile d = new SecureFile(0,fileName,2,"secure",new File(newPath).length(),BasicUtils.getDatetime(),account);
				secureFileService.addSecureFile(d);
				json.put("flag", flag);
			}
		}
		return json;
	}
}
