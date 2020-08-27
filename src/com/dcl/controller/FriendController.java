package com.dcl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.domain.Account;
import com.dcl.domain.Friend;
import com.dcl.service.AccountService;
import com.dcl.service.FriendService;
import com.dcl.service.MsgService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/friend")
public class FriendController {

	@Autowired
	private FriendService friendService;
	
	/**
	 * 添加好友
	 * @param myAccount
	 * @param friendAccount
	 * @return
	 */
	@RequestMapping(value="/addFriend")
	@ResponseBody
	JSONObject addFriend(@RequestParam("myAccount") String myAccount,
							@RequestParam("friendAccount") String friendAccount) {
		JSONObject json = new JSONObject();
		Friend f = new Friend(0,myAccount,friendAccount,BasicUtils.getDatetime());
		boolean flag = friendService.addFriend(f);
		json.put("flag", flag);
		return json;
	}
	/**
	 * 添加多个好友
	 * @param myAccount
	 * @param friendAccounts
	 * @return
	 */
	@RequestMapping(value="/addFriends")
	@ResponseBody
	JSONObject addFriends(@RequestParam("myAccount") String myAccount,
							@RequestParam("friendAccounts") String friendAccounts) {
		JSONObject json = new JSONObject();
		MsgService msgService = (MsgService) Beans.getBean("msgService");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toAccount", myAccount);
		params.put("type", "未读");
		params.put("context", "申请消息");
		if(friendAccounts.indexOf(",") == -1) {
			Friend f = new Friend(0,myAccount,friendAccounts,BasicUtils.getDatetime());
			boolean flag = friendService.addFriend(f);
			Friend f1 = new Friend(0,friendAccounts,myAccount,BasicUtils.getDatetime());
			flag = friendService.addFriend(f1);
			json.put("flag", flag);
			params.put("fromAccount", friendAccounts);
			int id = msgService.query(params).get(0).getId();
			msgService.delMsg(id);
			return json;
		}else {
			boolean flag = false;
			String[] fas = friendAccounts.split(",");
			for(String fa : fas) {
				Friend f = new Friend(0,myAccount,fa,BasicUtils.getDatetime());
				flag = friendService.addFriend(f);
				Friend f1 = new Friend(0,fa,myAccount,BasicUtils.getDatetime());
				flag = friendService.addFriend(f1);
				params.put("fromAccount", fa);
				int id = msgService.query(params).get(0).getId();
				msgService.delMsg(id);
			}
			json.put("flag", flag);
			return json;
		}
	}
	/**
	 * 删除好友
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delFriend")
	@ResponseBody
	JSONObject delFriend(@RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		boolean flag = friendService.delFriend(id);
		json.put("flag", flag);
		return json;
	}
	/**
	 * 获取好友资料
	 * @param myAccount
	 * @return
	 */
	@RequestMapping(value="/getMyFriends")
	@ResponseBody
	JSONObject getMyFriends(@RequestParam("myAccount") String myAccount) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("myAccount", myAccount);
		List<Friend> friends = friendService.query(params);
		params.clear();
		AccountService as = (AccountService) Beans.getBean("accountService");
		JSONArray friendData = new JSONArray();
		for(Friend f : friends) {
			params.put("account", f.getFriendAccount());
			Account a = as.query(params).get(0);
			friendData.add(JSONObject.fromObject(a));
		}
		json.put("friends", JSONArray.fromObject(friends));
		json.put("friendData", friendData);
		return json;
	}
	
}
