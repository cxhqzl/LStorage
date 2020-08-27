package com.dcl.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.domain.Log;
import com.dcl.domain.Share;
import com.dcl.service.LogService;
import com.dcl.service.ShareService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;
import com.dcl.utils.FolderToZip;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/share")
public class ShareController {

	@Autowired
	private ShareService shareService;
	
	/**
	 * 文件分享接口
	 * @param fileNames
	 * @param path
	 * @param lock
	 * @param lockPassword
	 * @param userName
	 * @return
	 */
	@RequestMapping(value="/addShare")
	@ResponseBody
	JSONObject addShare(HttpServletRequest request,
			@RequestParam("fileNames") String fileNames,
			@RequestParam("path") String path,
			@RequestParam("lock") int lock,
			@RequestParam("lockPassword") String lockPassword,
			@RequestParam("userName") String userName) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		String path1 = BasicUtils.getStoragePath(path.replace(",", "\\"));
		String account = "";
		if(path.indexOf(",") == -1) {
			account = path;
		}else {
			account = path.split(",")[0];
		}
		if(fileNames.indexOf(",") == -1) {//只分享一个文件或文件夹
			String p = path1 + "\\" + fileNames;
			params.put("name", fileNames);
			params.put("account", account);
			List<Share> s = shareService.query(params);
			for(Share ss : s) {
				if(p.replace("\\", ",").equals(ss.getPath())) {
					json.put("msg", "文件已被分享！");
					json.put("fileName", fileNames);
					json.put("type", 1);
					return json;
				}
			}
			File f = new File(p);
			double size = -1;
			if(f.isFile()) {
				size = f.length(); 
			}
			Share share = new Share(0,fileNames,p.replace("\\", ","),lock,lockPassword,account,userName,BasicUtils.getDatetime(),size);
			boolean flag = shareService.register(share);
			json.put("flag", flag);
			json.put("msg", "文件分享成功");
			LogService logService = (LogService) Beans.getBean("logService");
			Log log = new Log(0,"分享文件："+fileNames,"分享",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
			logService.addLog(log);
		}else {//多个文件或文件夹同时分享
			String[] fns = fileNames.split(",");
			boolean flag = false;
			for(int i=0;i<fns.length;i++) {
				String p = path1 + "\\" + fns[i];
				params.clear();
				params.put("name", fns[i]);
				params.put("account", account);
				List<Share> s = shareService.query(params);
				for(Share ss : s) {
					if(p.replace("\\", ",").equals(ss.getPath())) {
						json.put("msg", "文件已被分享！");
						json.put("fileName", fns[i]);
						json.put("type", 1);
						return json;
					}
				}
				File f = new File(p);
				double size = -1;
				if(f.isFile()) {
					size = f.length();
				}
				Share share = new Share(0,fns[i],p.replace("\\", ","),lock,lockPassword,account,userName,BasicUtils.getDatetime(),size);
				flag = shareService.register(share);
			}
			LogService logService = (LogService) Beans.getBean("logService");
			Log log = new Log(0,"分享文件："+fileNames,"分享",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
			logService.addLog(log);
			json.put("flag", flag);
		}
		return json;
	}
	/**
	 * 删除共享文件
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delShare")
	@ResponseBody
	JSONObject delShare(HttpServletRequest request,
			@RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		String account = shareService.query(params).get(0).getAccount();
		boolean flag = shareService.delShare(id);
		json.put("flag", flag);
		LogService logService = (LogService) Beans.getBean("logService");
		Log log = new Log(0,"分享文件删除","删除",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
		logService.addLog(log);
		json.put("flag", flag);
		return json;
	}
	/**
	 * 查询分享的文件接口
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getShare")
	@ResponseBody
	JSONObject getShare(@RequestParam("account") String account,
			@RequestParam("index") int index) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		if(!account.equals("")) {
			params.put("account", account);
		}
		int size = shareService.query(params).size();
		json.put("size", size);
		json.put("pages", Math.ceil((float) size / 8));
		params.put("limit", 8);
		index = (index - 1) * 8;
		params.put("index", index);
		List<Share> share = shareService.query(params);
		json.put("share", JSONArray.fromObject(share));
		json.put("index", index);
		return json;
	}
	/**
	 * 匹配分享文件密码是否正确
	 * @param id
	 * @param lockPassword
	 * @return
	 */
	@RequestMapping(value="/checkSharePassword")
	@ResponseBody
	JSONObject checkSharePassword(@RequestParam("id") int id,
			@RequestParam("lockPassword") String lockPassword) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("lockPassword", lockPassword);
		List<Share> share = shareService.query(params);
		if(share.size() <= 0) {
			json.put("msg", "密码错误");
			json.put("type", 1);
		}else {
			json.put("msg", "密码正确");
			json.put("type", 0);
		}
		return json;
	}
	/**
	 * 共享文件下载
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/downloadShare")
	@ResponseBody
	public ResponseEntity<byte[]> downloadFiles(HttpServletRequest request,@RequestParam("id") int id) throws IOException  {
		ResponseEntity<byte[]> result = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		List<Share> share = shareService.query(params);
		String account = share.get(0).getAccount();
		String path = share.get(0).getPath().replace(",", "\\");
		File file = new File(path);
		String fileName = share.get(0).getName();
		LogService logService = (LogService) Beans.getBean("logService");
		Log log = new Log(0,"分享文件下载："+fileName,"下载",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
		logService.addLog(log);
		if(file.isFile()) {
			HttpHeaders headers = new HttpHeaders();  
	        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
	        headers.setContentDispositionFormData("attachment", fileName); 
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
	        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
		}else if(file.isDirectory()) {
			List<String> listNames = new ArrayList<String>();
			listNames.add(fileName);
			String path1 = FolderToZip.fileToZip(path.substring(0,path.lastIndexOf("\\")), listNames);
			File file1 = new File(path1);
			fileName = fileName + ".zip";
			//String fileName1 = file1.getName();
			HttpHeaders headers = new HttpHeaders();  
	        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
	        headers.setContentDispositionFormData("attachment", fileName); 
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
	        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file1),headers, HttpStatus.CREATED);
	        file1.delete();
		}
		return result; 
	}
}
