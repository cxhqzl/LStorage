package com.dcl.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dcl.domain.Document;
import com.dcl.domain.FileSize;
import com.dcl.domain.Log;
import com.dcl.domain.SecureFile;
import com.dcl.service.DocumentService;
import com.dcl.service.FileSizeService;
import com.dcl.service.LogService;
import com.dcl.service.SecureFileService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;
import com.dcl.utils.FolderToZip;

import net.sf.json.JSONObject;

@Controller
public class FileController {

	
	/**
	 * 文件上传接口
	 * @param request
	 * @param path1
	 * @param account
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadFile(HttpServletRequest request,
			@RequestParam("path") String path1,
			@RequestParam("account") String account) throws IOException {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		JSONObject json = new JSONObject();
		int c_pid = 1;
		if(path1.indexOf(",") != -1) {
			c_pid = path1.split(",").length;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", c_pid);
		List <Document> ds = ((DocumentService) Beans.getBean("documentService")).query(params);
		params.clear();
		for(Document d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		
		String path = BasicUtils.getStoragePath(path1.replace(",", "\\"));
		MultipartFile file = null;
		boolean flag = false;
		double uploadSize = 0;
		try {
			OutputStream outStream = null;
			for (int i = 0; i < files.size(); ++i) {
				file = files.get(i);
				uploadSize += file.getSize();
				byte[] bytes = file.getBytes();
				String fileName = file.getOriginalFilename();
				while(params.containsKey(fileName)) {//文件名存在时
					fileName = fileName.substring(0,fileName.lastIndexOf(".")) + "(1)" + fileName.substring(fileName.lastIndexOf("."),fileName.length());
				}
				File targetFile = new File(path + "\\" + fileName);
				outStream = new FileOutputStream(targetFile);
				outStream.write(bytes);
				//上传后写入数据库
				int pid = 1;
				String fatherName = "";
				if(path1.indexOf(",") == -1) {
					pid = 1;
					fatherName = account;
				}else {
					String[] folders = path1.split(",");
					pid = folders.length;
					fatherName = folders[pid - 1];
				}
				Document d = new Document(0,fileName,pid,fatherName,file.getSize(),BasicUtils.getDatetime(),account);
				((DocumentService) Beans.getBean("documentService")).addDocument(d);
				LogService logService = (LogService) Beans.getBean("logService");
				Log log = new Log(0,"文件上传："+fileName,"上传",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
				logService.addLog(log);
			}
			//写入文件大小统计表
			String nowDate = BasicUtils.getDate();
			params.clear();
			params.put("account", account);
			params.put("createDate", nowDate);
			FileSizeService fileSizeService = (FileSizeService) Beans.getBean("fileSizeService");
			List<FileSize> fss = fileSizeService.query(params);
			if(fss.size() > 0) {//存在直接修改
				params.clear();
				params.put("id", fss.get(0).getId());
				params.put("uploadSize", uploadSize + fss.get(0).getDownloadSize());
				fileSizeService.modifiy(params);
			}else {//不存在直接写入
				FileSize fs = new FileSize(0,uploadSize,0,nowDate,account);
				fileSizeService.addFileSize(fs);
			}
			
			outStream.close();
			flag = true;
		}catch(Exception e) {
			
		}
		json.put("flag", flag);
		return json;
	}
	/**
	 * 安全文件上传
	 * @param request
	 * @param path1
	 * @param account
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadSecureFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadSecureFile(HttpServletRequest request,
			@RequestParam("path") String path1,
			@RequestParam("account") String account) throws IOException {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		JSONObject json = new JSONObject();
		int c_pid = 1;
		if(path1.indexOf(",") != -1) {
			c_pid = path1.split(",").length;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", c_pid);
		SecureFileService secureFileService = (SecureFileService) Beans.getBean("secureFileService");
		List <SecureFile> ds = secureFileService.query(params);
		params.clear();
		for(SecureFile d : ds) {
			params.put(d.getFolderName(), d.getFolderName());
		}
		
		String path = BasicUtils.getStoragePath(path1.replace(",", "\\"));
		MultipartFile file = null;
		boolean flag = false;
		double uploadSize = 0;
		try {
			OutputStream outStream = null;
			for (int i = 0; i < files.size(); ++i) {
				file = files.get(i);
				uploadSize += file.getSize();
				byte[] bytes = file.getBytes();
				String fileName = file.getOriginalFilename();
				while(params.containsKey(fileName)) {//文件名存在时
					fileName = fileName.substring(0,fileName.lastIndexOf(".")) + "(1)" + fileName.substring(fileName.lastIndexOf("."),fileName.length());
				}
				File targetFile = new File(path + "\\" + fileName);
				outStream = new FileOutputStream(targetFile);
				outStream.write(bytes);
				//上传后写入数据库
				int pid = 1;
				String fatherName = "";
				if(path1.indexOf(",") == -1) {
					pid = 1;
					fatherName = account;
				}else {
					String[] folders = path1.split(",");
					pid = folders.length;
					fatherName = folders[pid - 1];
				}
				SecureFile d = new SecureFile(0,fileName,pid,fatherName,file.getSize(),BasicUtils.getDatetime(),account);
				secureFileService.addSecureFile(d);
				LogService logService = (LogService) Beans.getBean("logService");
				Log log = new Log(0,"文件上传："+fileName,"上传",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
				logService.addLog(log);
			}
			//写入文件大小统计表
			String nowDate = BasicUtils.getDate();
			params.clear();
			params.put("account", account);
			params.put("createDate", nowDate);
			FileSizeService fileSizeService = (FileSizeService) Beans.getBean("fileSizeService");
			List<FileSize> fss = fileSizeService.query(params);
			if(fss.size() > 0) {//存在直接修改
				params.clear();
				params.put("id", fss.get(0).getId());
				params.put("uploadSize", uploadSize + fss.get(0).getDownloadSize());
				fileSizeService.modifiy(params);
			}else {//不存在直接写入
				FileSize fs = new FileSize(0,uploadSize,0,nowDate,account);
				fileSizeService.addFileSize(fs);
			}
			
			outStream.close();
			flag = true;
		}catch(Exception e) {
			
		}
		json.put("flag", flag);
		return json;
	}
	/**
	 * 文件下载接口
	 * @param path
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/downloadFile")
	ResponseEntity<byte[]> downloadFile(HttpServletRequest request,
								@RequestParam("path") String path,
								@RequestParam("fileName") String fileName) throws IOException  {
		String account = "";
		if(path.indexOf(",") == -1) {
			account = path;
		}else {
			account = path.split(",")[0];
		}
		String nowDate = BasicUtils.getDate();
		String path1 = BasicUtils.getStoragePath(path.replace(",", "\\")+"\\"+fileName);
        File file = new File(path1);
        double downloadSize = file.length();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account", account);
		params.put("createDate", nowDate);
		FileSizeService fileSizeService = (FileSizeService) Beans.getBean("fileSizeService");
		List<FileSize> fss = fileSizeService.query(params);
		if(fss.size() > 0) {//存在直接修改
			params.clear();
			params.put("id", fss.get(0).getId());
			params.put("downloadSize", downloadSize + fss.get(0).getDownloadSize());
			fileSizeService.modifiy(params);
		}else {//不存在直接写入
			FileSize fs = new FileSize(0,0,downloadSize,nowDate,account);
			fileSizeService.addFileSize(fs);
		}
		LogService logService = (LogService) Beans.getBean("logService");
		Log log = new Log(0,"文件下载："+fileName,"下载",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
		logService.addLog(log);
        HttpHeaders headers = new HttpHeaders();  
        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        return result;
	}
	/**
	 * 多文件下载接口
	 * @param fileNames
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/downloadFiles")
	public ResponseEntity<byte[]> downloadFiles(HttpServletRequest request,@RequestParam("fileNames") String fileNames,
			@RequestParam("path") String path) throws IOException  {
		ResponseEntity<byte[]> result = null;
		String account = "";
		if(path.indexOf(",") == -1) {
			account = path;
		}else {
			account = path.split(",")[0];
		}
		double downloadSize = 0;
        
		String nowDate = BasicUtils.getDate();
		if(fileNames.indexOf(",") == -1) {
			String path1 = BasicUtils.getStoragePath(path.replace(",", "\\") + "\\" + fileNames);
			File file = new File(path1);
			if(file.isFile()) {
				String fileName = fileNames;
				LogService logService = (LogService) Beans.getBean("logService");
				Log log = new Log(0,"文件下载："+fileName,"下载",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
				logService.addLog(log);
				HttpHeaders headers = new HttpHeaders();
		        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		        headers.setContentDispositionFormData("attachment", fileName); 
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
			}else if(file.isDirectory()){
				List<String> listNames = new ArrayList<String>();
				listNames.add(fileNames);
				String path2 = FolderToZip.fileToZip(BasicUtils.getStoragePath(path.replace(",", "\\")), listNames);
				File file1 = new File(path2);
				String fileName = file1.getName();
				LogService logService = (LogService) Beans.getBean("logService");
				Log log = new Log(0,"文件下载："+fileName,"下载",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
				logService.addLog(log);
				HttpHeaders headers = new HttpHeaders();  
		        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		        headers.setContentDispositionFormData("attachment", fileName); 
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file1),headers, HttpStatus.CREATED);
		        file1.delete();
			}
		}else {
			String[] names = fileNames.split(",");
			List<String> listNames = new ArrayList<String>();
			for(int i=0;i<names.length;i++) {
				listNames.add(names[i]);
			}
			String path1 = FolderToZip.fileToZip(BasicUtils.getStoragePath(path.replace(",", "\\")), listNames);
			File file = new File(path1);
			String fileName = file.getName();
			LogService logService = (LogService) Beans.getBean("logService");
			Log log = new Log(0,"文件下载："+fileName,"下载",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
			logService.addLog(log);
			HttpHeaders headers = new HttpHeaders();  
	        fileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");
	        headers.setContentDispositionFormData("attachment", fileName); 
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
	        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
	        file.delete();
		}
		Map<String,Object> params = new HashMap<String,Object>();
        params.put("account", account);
		params.put("createDate", nowDate);
		FileSizeService fileSizeService = (FileSizeService) Beans.getBean("fileSizeService");
		List<FileSize> fss = fileSizeService.query(params);
		if(fss.size() > 0) {//存在直接修改
			params.clear();
			params.put("id", fss.get(0).getId());
			params.put("downloadSize", downloadSize + fss.get(0).getDownloadSize());
			fileSizeService.modifiy(params);
		}else {//不存在直接写入
			FileSize fs = new FileSize(0,0,downloadSize,nowDate,account);
			fileSizeService.addFileSize(fs);
		}
		 return result; 
	}
	/**
	 * 盘外共享文件下载
	 * @param request
	 * @param fileNames
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/download_public_file")
	public ResponseEntity<byte[]> download_public_file(HttpServletRequest request,@RequestParam("fileNames") String fileNames,
			@RequestParam("path") String path) throws IOException  {
		ResponseEntity<byte[]> result = null;
		if(fileNames.indexOf(",") == -1) {
			String path1 = BasicUtils.getStoragePath(path.replace(",", "\\") + "\\" + fileNames);
			File file = new File(path1);
			if(file.isFile()) {
				String fileName = fileNames;
				HttpHeaders headers = new HttpHeaders();  
		        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		        headers.setContentDispositionFormData("attachment", fileName); 
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
			}else if(file.isDirectory()){
				List<String> listNames = new ArrayList<String>();
				listNames.add(fileNames);
				String path2 = FolderToZip.fileToZip(BasicUtils.getStoragePath(path.replace(",", "\\")), listNames);
				File file1 = new File(path2);
				String fileName = file1.getName();
				HttpHeaders headers = new HttpHeaders();  
		        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		        headers.setContentDispositionFormData("attachment", fileName); 
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file1),headers, HttpStatus.CREATED);
		        file1.delete();
			}
		}else {
			String[] names = fileNames.split(",");
			List<String> listNames = new ArrayList<String>();
			for(int i=0;i<names.length;i++) {
				listNames.add(names[i]);
			}
			String path1 = FolderToZip.fileToZip(BasicUtils.getStoragePath(path.replace(",", "\\")), listNames);
			File file = new File(path1);
			String fileName = file.getName();
			HttpHeaders headers = new HttpHeaders();  
	        fileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");
	        headers.setContentDispositionFormData("attachment", fileName); 
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
	        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
	        file.delete();
		}
		 return result; 
	}
	/**
	 * 链接二维码
	 * @param request
	 * @param account
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/createQR")
	ResponseEntity<byte[]> createQR(HttpServletRequest request,
			@RequestParam("fileName") String fileName) throws IOException  {
		String path = BasicUtils.getStoragePath("userImage");
		
		File file = new File(path + "\\" + fileName);
        HttpHeaders headers = new HttpHeaders();  
        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", fileName); 
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
        ResponseEntity<byte[]> result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        return result;
	}
}
