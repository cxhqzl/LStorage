package com.dcl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.domain.SecureFile;
import com.dcl.service.SecureFileService;
import com.dcl.utils.BasicUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/secureFile")
public class SecureFileController {

	@Autowired
	private SecureFileService secureFileService;
	
	/**
	 * 创建文件夹
	 * @param fileName
	 * @param path
	 * @return
	 */
	@RequestMapping(value="/createFolder")
	@ResponseBody
	JSONObject addDocument(@RequestParam("fileName") String fileName,
			@RequestParam("path") String path) {
		JSONObject json = new JSONObject();
		int c_pid = 1;
		if(path.indexOf(",") != -1) {
			c_pid = path.split(",").length;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", c_pid);
		List <SecureFile> ds = secureFileService.query(params);
		params.clear();
		for(SecureFile d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		if(params.containsKey(fileName)) {
			json.put("type", 1);
			json.put("msg", "名称已存在！");
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
		SecureFile sf = new SecureFile(0,fileName,pid,fatherName,-1,BasicUtils.getDatetime(),account);
		boolean flag = secureFileService.addSecureFile(sf);
		json.put("flag", flag);
		return json;
	}
	/**
	 * 修改文件名
	 * @param fileName
	 * @param path
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/modifiyDocument")
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
		List <SecureFile> ds = secureFileService.query(params);
		params.clear();
		for(SecureFile d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		if(params.containsKey(fileName)) {
			json.put("type", 1);
			json.put("msg", "名称已存在！");
			return json;
		}
		params.clear();
		params.put("id", id);
		SecureFile d = secureFileService.query(params).get(0);
		String filePath = BasicUtils.getStoragePath(path.replace(",", "\\") + "\\" + d.getFolderName());
		boolean flag = BasicUtils.modifiyName(filePath, fileName);
		if(flag) {
			params.put("folderName", fileName);
			flag = secureFileService.modifiy(params);
		}
		json.put("flag", flag);
		return json;
	}
	/**
	 * 获取文件
	 * @param path
	 * @return
	 */
	@RequestMapping(value="/getDocument")
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
		int size = secureFileService.query(params).size();
		json.put("size", size);
		json.put("pages", Math.ceil((float) size / 8));
		params.put("limit", 8);
		index = (index - 1) * 8;
		params.put("index", index);
		List<SecureFile> ds = secureFileService.query(params);
		json.put("documents", JSONArray.fromObject(ds));
		json.put("index", index);
		return json;
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteDocument")
	@ResponseBody
	JSONObject deleteDocument(@RequestParam("ids") String ids,
							@RequestParam("path") String path,
							@RequestParam("fileNames") String fileNames) {
		JSONObject json = new JSONObject();
		String path1 = BasicUtils.getStoragePath(path.replace(",", "\\"));
		if(ids.indexOf(",") == -1) {
			boolean flag = BasicUtils.delFile(path1 + "\\" + fileNames);
			if(flag) {
				flag = secureFileService.delSecureFile(Integer.parseInt(ids));
			}else {
				json.put("msg", "文件删除失败");
			}
			json.put("flag", flag);
		}else {
			String[] IDs = ids.split(",");
			String[] fns = fileNames.split(",");
			for(int i=0;i<IDs.length;i++) {
				boolean flag = BasicUtils.delFile(path1 + "\\" + fns[i]);
				if(flag) {
					flag = secureFileService.delSecureFile(Integer.parseInt(IDs[i]));
				}else{
					json.put("msg", "文件删除失败");
				}
				json.put("flag", flag);
			}
		}
		return json;
	}
}
