package com.dcl.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dcl.domain.Account;
import com.dcl.domain.Log;
import com.dcl.service.AccountService;
import com.dcl.service.LogService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	/**
	 * 注册——邮箱验证码获取接口
	 * @param email
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/account/emailCode")
	@ResponseBody
	JSONObject getRegisterCode(@RequestParam("email") String email,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", email);
		int size = accountService.query(params).size();
		if(size > 0) {
			json.put("type", 1);
			json.put("msg", "账号已存在！");
			return json;
		}
		String code = BasicUtils.getCode(6);
		request.getSession().setAttribute("code", code);
		boolean flag = BasicUtils.sendEmailCode(email, code);
		if(flag) {
			json.put("type",0);
			json.put("msg", "发送成功");
		}else {
			json.put("type", 2);
			json.put("msg", "请求失败，请重试！");
		}
		return json;
	}
	
	/**
	 * 找回密码——邮箱验证码获取接口
	 * @param email
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/account/emailCode1")
	@ResponseBody
	JSONObject getRegisterCode1(@RequestParam("email") String email,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", email);
		int size = accountService.query(params).size();
		if(size > 0) {
			String code = BasicUtils.getCode(6);
			request.getSession().setAttribute("code", code);
			boolean flag = BasicUtils.sendEmailCode(email, code);
			if(flag) {
				json.put("type",0);
				json.put("msg", "发送成功");
			}else {
				json.put("type", 2);
				json.put("msg", "请求失败，请重试！");
			}
			
		}else {
			json.put("type", 1);
			json.put("msg", "账号不存在！");
		}
		return json;
	}
	
	/**
	 * 注册
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/account/register")
	@ResponseBody
	JSONObject register(HttpServletRequest request,
			@RequestParam("account") String account,
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam("code") String code) {
		JSONObject json = new JSONObject();
		if(!code.equals(request.getSession().getAttribute("code"))){
			json.put("type", 3);
			json.put("msg", "验证码错误");
			return json;
		}
		if(!BasicUtils.isNullString(account) && !BasicUtils.isNullString(password)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("account", account);
			List<Account> as = accountService.query(params);
			if(as.size() > 0) {
				json.put("type", 1);
				json.put("msg", "账号已存在！");
			}else {
				Account a = new Account(account,password,password,1,userName,"default.jpg","",0);
				boolean flag = accountService.register(a);
				if(flag) {
					json.put("type", 0);
					json.put("msg", "注册成功！");
					String ua = request.getHeader("User-Agent");
					String user_agent = "";
					if(BasicUtils.checkAgentIsMobile(ua)) {
						user_agent = "移动端";
					}else {
						user_agent = "PC端";
					}
					request.getSession().setAttribute("user_agent", user_agent);
					LogService logService = (LogService) Beans.getBean("logService");
					Log log = new Log(0,"注册账号","注册",BasicUtils.getDatetime(),user_agent,account);
					logService.addLog(log);
				}else {
					json.put("type", 2);
					json.put("msg", "请求失败！");
				}
			}
		}else {
			json.put("msg", "传入参数不合法！");
		}
		return json;
	}
	
	/**
	 * 忘记密码接口
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/account/forgetPassword")
	@ResponseBody
	JSONObject forgetPassword(HttpServletRequest request,
			@RequestParam("account") String account,
			@RequestParam("password") String password,
			@RequestParam("code") String code) {
		JSONObject json = new JSONObject();
		if(!code.equals(request.getSession().getAttribute("code"))){
			json.put("type", 3);
			json.put("msg", "验证码错误");
			return json;
		}
		if(!BasicUtils.isNullString(account) && !BasicUtils.isNullString(password)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("account", account);
			params.put("password", password);
			boolean flag = accountService.modifiy(params);
			if(flag) {
				json.put("type", 0);
				json.put("msg", "修改成功！");
			}else {
				json.put("type", 1);
				json.put("msg", "修改失败！");
			}
		}else {
			json.put("msg", "传入参数不合法！");
		}
		return json;
	}
	/**
	 * 删除账号
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/account/delAccount")
	@ResponseBody
	JSONObject delAccount(@RequestParam("account") String account) {
		JSONObject json = new JSONObject();
		if(!BasicUtils.isNullString(account)) {
			boolean flag = accountService.delAccount(account);
			if(flag) {
				json.put("type", true);
				json.put("msg", "删除成功！");
			}else {
				json.put("type", false);
				json.put("msg", "删除失败！");
			}
		}else {
			json.put("msg", "传入参数不合法！");
		}
		return json;
	}
	/**
	 * 修改资料
	 * @param account
	 * @param password
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/account/modifiyData")
	@ResponseBody
	JSONObject modifiyData(@RequestParam("account") String account,
			@RequestParam("password") String password,
			@RequestParam("role") int role) {
		JSONObject json = new JSONObject();
		if(!BasicUtils.isNullString(account) && !BasicUtils.isNullString(password)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("account", account);
			if(!BasicUtils.isNullString(password)) {
				params.put("password", password);
			}
			if(role >= 0) {
				params.put("role", role);
			}
			boolean flag = accountService.modifiy(params);
			if(flag) {
				json.put("type", 0);
				json.put("msg", "修改成功！");
			}else {
				json.put("type", 1);
				json.put("msg", "修改失败！");
			}
		}else {
			json.put("msg", "传入参数不合法！");
		}
		return json;
	}
	/**
	 * 修改账号资料
	 * @param account
	 * @param userName
	 * @param remark
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/account/modifiyAccount")
	@ResponseBody
	JSONObject modifiyAccount(@RequestParam("account") String account,
						@RequestParam("userName") String userName,
						@RequestParam("remark") String remark,
						@RequestParam("type") int type,
						@RequestParam("image") MultipartFile file) {
		JSONObject json = new JSONObject();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("account", account);
		String oldImage = accountService.query(map).get(0).getImage();
		map.put("userName", userName);
		map.put("remark", remark);
		json.put("userName", userName);
		json.put("remark", remark);
		if(file != null && type == 0) {
			try {
				String path = BasicUtils.getStoragePath("userImage");
				String fn = file.getOriginalFilename();
				String fileName = account + new Date().getTime() + fn.substring(fn.lastIndexOf("."),fn.length());
				File filepath = new File(path , fileName);
				if(!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				file.transferTo(filepath);
				if(!oldImage.equals("default.jpg")) {
					File f = new File(path + "\\" + oldImage);
					f.delete();
				}
				map.put("image", fileName);
				json.put("image", fileName);
			}catch(Exception e) {
				
			}
		}
		boolean flag = accountService.modifiy(map);
		json.put("flag", flag);
		return json;
	}
	/**
	 * 登录接口
	 * @param request
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/account/login")
	@ResponseBody
	JSONObject login(HttpServletRequest request,
			@RequestParam("account") String account,
			@RequestParam("password") String password) {
		JSONObject json = new JSONObject();
		if(!BasicUtils.isNullString(account) && !BasicUtils.isNullString(password)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("account", account);
			List<Account> as = accountService.query(params);
			if(as.size() > 0) {
				params.put("password", password);
				List<Account> as1 = accountService.query(params);
				int size = as1.size();
				if(size > 0) {
					if(as1.get(0).getType() == 0) {
						json.put("type", 0);
						json.put("msg", "登录成功！");
						String ua = request.getHeader("User-Agent");
						String user_agent = "";
						if(BasicUtils.checkAgentIsMobile(ua)) {
							user_agent = "移动端";
						}else {
							user_agent = "PC端";
						}
						request.getSession().setAttribute("account", account);
						request.getSession().setAttribute("user_agent", user_agent);
						LogService logService = (LogService) Beans.getBean("logService");
						Log log = new Log(0,BasicUtils.getAddressByIP(BasicUtils.getLoginIP(request)) + "登录","登录",BasicUtils.getDatetime(),user_agent,account);
						logService.addLog(log);
						json.putAll(JSONObject.fromObject(as1.get(0)));
					}else {
						json.put("type", 3);
						json.put("msg", "账号已冻结！");
					}
					
				}else {
					json.put("type", 2);
					json.put("msg", "密码错误！");
				}
			}else {
				json.put("type", 1);
				json.put("msg", "账号不存在！");
			}
		}else {
			json.put("msg", "传入参数不合法！");
		}
		return json;
	}
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/account/logout")
	@ResponseBody
	boolean logout(HttpServletRequest request) {
		boolean flag = false;
		try {
			request.getSession().invalidate();//清空session
			flag = true;
		}catch(Exception e) {
			
		}
		return flag;
	}
	/**
	 * 加密空间密码校验
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/account/checkSecure")
	@ResponseBody
	boolean checkSecure(@RequestParam("account") String account,
			@RequestParam("password") String password) {
		if(!BasicUtils.isNullString(account) && !BasicUtils.isNullString(password)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("account", account);
			params.put("securePassword", password);
			List<Account> as = accountService.query(params);
			if(as.size() > 0) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	/**
	 * 修改账号密码
	 * @param account
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value="/account/changePassword")
	@ResponseBody
	JSONObject changePassword(HttpServletRequest request,
			@RequestParam("account") String account,
			@RequestParam("password") String password,
			@RequestParam("newPassword") String newPassword) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("password", password);
		List<Account> as = accountService.query(params);
		if(as.size() == 0) {
			json.put("type", 1);
			json.put("msg", "原密码错误");
		}else {
			params.put("password", newPassword);
			boolean flag = accountService.modifiy(params);
			if(flag) {
				json.put("type", 0);
				json.put("msg", "修改成功");
				LogService logService = (LogService) Beans.getBean("logService");
				Log log = new Log(0,"修改账号密码","修改",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
				logService.addLog(log);
			}else {
				json.put("type", 2);
				json.put("msg", "修改失败");
			}
		}
		return json;
	}
	/**
	 * 修改加密空间密码
	 * @param account
	 * @param securePassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value="/account/changeSecureP")
	@ResponseBody
	JSONObject changeSecureP(HttpServletRequest request,
			@RequestParam("account") String account,
			@RequestParam("password") String securePassword,
			@RequestParam("newPassword") String newPassword) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("securePassword", securePassword);
		List<Account> as = accountService.query(params);
		if(as.size() == 0) {
			json.put("type", 1);
			json.put("msg", "原密码错误");
		}else {
			params.put("securePassword", newPassword);
			boolean flag = accountService.modifiy(params);
			if(flag) {
				json.put("type", 0);
				json.put("msg", "修改成功");
				LogService logService = (LogService) Beans.getBean("logService");
				Log log = new Log(0,"修改加密空间密码","修改",BasicUtils.getDatetime(),request.getSession().getAttribute("user_agent").toString(),account);
				logService.addLog(log);
			}else {
				json.put("type", 2);
				json.put("msg", "修改失败");
			}
		}
		return json;
	}
	/**
	 * 搜索好友
	 * @param keys
	 * @return
	 */
	@RequestMapping(value="/account/searchAccount")
	@ResponseBody
	JSONObject searchAccount(@RequestParam("keys") String keys) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("keys", keys);
		List<Account> as = accountService.query(params);
		json.put("accounts", JSONArray.fromObject(as));
		return json;
	}
	/**
	 * 获取账号信息
	 * @param index
	 * @return
	 */
	@RequestMapping(value="/account/getAccounts")
	@ResponseBody
	JSONObject getAccounts(@RequestParam("account") String account,
			@RequestParam("index") int index) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("noAccount", account);
		int size = accountService.query(params).size();
		json.put("pages", Math.ceil((float) size / 10));
		params.put("limit", 10);
		index = (index - 1) * 10;
		params.put("index", index);
		List<Account> as = accountService.query(params);
		json.put("accounts", JSONArray.fromObject(as));
		json.put("index", index);
		return json;
	}
	/**
	 * 账号冻结操作
	 * @param account
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/account/operateAccount")
	@ResponseBody
	boolean operateAccount(@RequestParam("account") String account,
			@RequestParam("type") int type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("type", type);
		return accountService.modifiy(params);
	}
}
