package com.dcl.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.QR.CreateQR;
import com.dcl.domain.Account;
import com.dcl.service.AccountService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/rsa")
public class RsaController {
	

	@RequestMapping(value="/addRsa")
	@ResponseBody
	JSONObject addRsa(HttpServletRequest request,
							@RequestParam("account") String account,
							@RequestParam("path") String path,
							@RequestParam("fileName") String fileName,
							@RequestParam("password") String password) throws UnsupportedEncodingException {
		JSONObject json = new JSONObject();
		JSONObject res = new JSONObject();
		res.put("account", account);
		res.put("path",  path);
		res.put("fileName", fileName);
		res.put("password", password);
		String url = BasicUtils.getURL(request);
		url = url + "share_public_file.html?context=" + URLEncoder.encode( res.toString(), "UTF-8" );
		String path1 = BasicUtils.getStoragePath("userImage");
		String fileName1 = account + new Date().getTime() +  "-QRCode.png";
		
		AccountService accountService = (AccountService) Beans.getBean("accountService");
		Map<String,Object> params = new HashMap<String,Object>();
		//查询账号信息
		params.put("account", account);
		Account ai = accountService.query(params).get(0);
		
		//获取和定义参数
		String imagePath = path1 + "\\" + ai.getImage();
		String outPath = path1;
		CreateQR.Encode_QR_CODE(url, imagePath, outPath, fileName1);
		json.put("url", url);
		json.put("fileName", fileName1);
		return json;
	}
	/**
	 * 链接解密
	 * @param context
	 * @param password
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/decrypt_url")
	@ResponseBody
	JSONObject decrypt_url(@RequestParam("context") String context,
					@RequestParam("password") String password) throws UnsupportedEncodingException {
		JSONObject res = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		JSONObject json = JSONObject.fromObject(URLDecoder.decode( context, "UTF-8" ));
		String p = json.getString("password");
		if(!password.equals("")) {
			if(!p.equals(password)) {
				res.put("flag", false);
				return res;
			}
		}
		res.put("flag", true);
		String path = URLDecoder.decode( json.getString("path"), "UTF-8" );
		String fileNames = URLDecoder.decode( json.getString("fileName"), "UTF-8" );
		String account = json.getString("account");
		AccountService accountService = (AccountService) Beans.getBean("accountService");
		params.clear();
		params.put("account", account);
		Account a = accountService.query(params).get(0);
		res.put("account", account);
		res.put("userName", a.getUserName());
		res.put("image", a.getImage());
		res.put("path", path);
		res.put("fileNames", fileNames);
		return res;
	}
}
