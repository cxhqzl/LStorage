package com.dcl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;

import net.sf.json.JSONObject;

public class BasicUtils {
	
	/**
	 * 获取登录IP---get login IP
	 * @param request
	 * @return
	 */
	public static String getLoginIP(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	/**
	 * 调用百度API获取IP的地理地址
	 * @param IP
	 * @return
	 */
	public static String getAddressByIP(String IP){ 
		String url = "https://api.map.baidu.com/location/ip?ip="+IP+"&ak=mRdryUGzZpUi1IHw2vyaaqtzYv29hIux&coor=bd09ll";
		try {
			String json = loadJSON(url);
			JSONObject obj = JSONObject.fromObject(json);
			JSONObject obj1 = JSONObject.fromObject(obj.get("content"));
			return obj1.get("address").toString();
		}catch(Exception e) {
			return "localhost";
		}
	}
	
	/**
	 * HTTP请求
	 * @param url
	 * @return
	 */
	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}

	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isNullString(String s) {
		if(s != null && !s.equals("")) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 获取N为数字
	 * @param count
	 * @return
	 */
	public static String getCode(int count) {
		String str = "";
		for(int i=0;i<count;i++) {
			str += (int) Math.floor(Math.random() * 10);
		}
		return str;
	}
	/**
	 * 发送邮箱验证码
	 * @param email
	 * @param code
	 * @return
	 */
	public static boolean sendEmailCode(String email,String code) {
		try {
			HtmlEmail Email = new HtmlEmail();
			Email.setHostName("smtp.163.com");
			Email.setCharset("UTF-8");
			Email.addTo(email);
			Email.setFrom("18555502667@163.com", "LStorage");
			Email.setAuthentication("18555502667@163.com", "cxh123456");
			Email.setSubject("欢迎使用");
			Email.setMsg("您本次注册的验证码是:" + code);

			Email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 创建文件夹
	 * @param name
	 * @return
	 */
	public static String getStoragePath(String name) {
		String path = "C:\\images\\DStorageFileStorage\\"+name;
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	/**
	 * 获取系统当前日期时间
	 * @return
	 */
	public static String getDatetime() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sd.format(new Date());
		return date;
	}
	/**
	 * 获取系统当前日期
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		return date;
	}
	/**
	 * 获取到前n天日期---get lastDate
	 * @param num
	 * @return
	 */
	public static String getLastDate(int num) {
		num = num - 1;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		String date = sd.format(new Date(d.getTime() - num * 24 * 60 * 60 * 1000));
		return date;
	}
	/**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		boolean flag = false;
		File f = new File(path);
		if(f.isFile()) {
			f.delete();
			flag = true;
		}else if(f.isDirectory()) {
			File[] files = f.listFiles();
			if(files.length == 0) {
				flag = f.delete();
			}else {
				for(File ff : files) {
					flag = delFile(ff.getPath());
				}
			}
		}
		return flag;
	}
	/**
	 * 移动文件或文件夹
	 * @param path
	 * @param newPath
	 * @return
	 */
	public static boolean moveFile(String path,String newPath) {
		boolean flag = false;
		File file = new File(path);
		flag = file.renameTo(new File(newPath));
		return flag;
	}
	/**
	 * 修改文件名
	 * @param path
	 * @param name
	 * @return
	 */
	public static boolean modifiyName(String path,String name) {
		boolean flag = false;
		File file = new File(path);
		String newName = name;
		if(file.exists()) {
			if(file.isFile()) {
				String oldName = file.getName();
				newName = name + "." + oldName.substring(oldName.lastIndexOf("."),oldName.length());
			}
			flag = file.renameTo(new File(path.substring(0,path.lastIndexOf("\\")) + "\\" + newName));
		}
		return flag;
	}
	
	/**
	* 判断User-Agent 是不是来自于手机
	* @param ua
	* @return
	*/
	public static boolean checkAgentIsMobile(String ua) {
		String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	/**
	 * 获取URL---get url
	 * @param request
	 * @return
	 */
	public static String getURL(HttpServletRequest request) {
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		return url+"/";
	}
	/**
	 * 获取图片URL---get img url
	 * @param request
	 * @return
	 */
	public static String getImgURL(HttpServletRequest request) {
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		return url+"/image/";
	}
}
