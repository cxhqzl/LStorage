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
import com.dcl.domain.Msg;
import com.dcl.service.AccountService;
import com.dcl.service.FriendService;
import com.dcl.service.MsgService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/msg")
public class MsgController {

	@Autowired
	private MsgService msgService;
	
	/**
	 * �����Ϣ�����ݿ�
	 * @param context
	 * @param fromAccount
	 * @param toAccount
	 * @return
	 */
	@RequestMapping(value="/addMsg")
	@ResponseBody
	JSONObject addMsg(@RequestParam("context") String context,
					@RequestParam("fromAccount") String fromAccount,
					@RequestParam("toAccount") String toAccount) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		//�ж��Ƿ�Ϊ����
		FriendService friendService = (FriendService) Beans.getBean("friendService");
		params.put("myAccount", fromAccount);
		params.put("friendAccount", toAccount);
		int size = friendService.query(params).size();
		if(size > 0) {
			json.put("type", 1);
			json.put("msg", "�Ѿ��Ǻ���");
			return json;
		}
		params.clear();
		//�ж��Ƿ����ύ
		params.put("toAccount", toAccount);
		params.put("fromAccount", fromAccount);
		params.put("type", "δ��");
		params.put("context", "������Ϣ");
		List<Msg> msgs = msgService.query(params);
		if(msgs.size() > 0) {
			json.put("type", 2);
			json.put("msg", "�������ύ");
			return json;
		}
		Msg msg = new Msg(0,context,fromAccount,toAccount,BasicUtils.getDatetime(),"δ��");
		boolean flag = msgService.addMsg(msg);
		json.put("flag", flag);
		return json;
	}
	/**
	 * �ܾ�����
	 * @param fromAccounts
	 * @param toAccount
	 * @return
	 */
	@RequestMapping(value="/refuseFriend")
	@ResponseBody
	JSONObject refuseFriend(@RequestParam("fromAccounts") String fromAccounts,
					@RequestParam("toAccount") String toAccount) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		if(fromAccounts.indexOf(",") == -1) {
			params.put("toAccount", toAccount);
			params.put("fromAccount", fromAccounts);
			params.put("type", "δ��");
			params.put("context", "������Ϣ");
			int id = msgService.query(params).get(0).getId();
			boolean flag = msgService.delMsg(id);
			json.put("flag", flag);
			return json;
		}else {
			String[] fas = fromAccounts.split(",");
			params.put("toAccount", toAccount);
			params.put("type", "δ��");
			params.put("context", "������Ϣ");
			boolean flag = false;
			for(String fa : fas) {
				params.put("fromAccount", fa);
				int id = msgService.query(params).get(0).getId();
				flag = msgService.delMsg(id);
			}
			json.put("flag", flag);
			return json;
		}
	}
	
	/**
	 * ��ѯ����������Ϣ
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getAddFriendMsg")
	@ResponseBody
	JSONObject getAddFriendMsg(@RequestParam("account") String account) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toAccount", account);
		params.put("type", "δ��");
		params.put("context", "������Ϣ");
		List<Msg> msgs = msgService.query(params);
		params.clear();
		AccountService as = (AccountService) Beans.getBean("accountService");
		JSONArray accounts = new JSONArray();
		for(Msg m : msgs) {
			params.put("account", m.getFromAccount());
			Account a = as.query(params).get(0);
			accounts.add(JSONObject.fromObject(a));
		}
		json.put("accounts", accounts);
		json.put("msgs", JSONArray.fromObject(msgs));
		return json;
	}
	/**
	 * ��ѯ������Ϣ
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getChatMsg")
	@ResponseBody
	JSONObject getChatMsg(@RequestParam("account") String account,
					@RequestParam("fromAccount") String fromAccount) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toAccount", account);
		params.put("fromAccount", fromAccount);
		params.put("type", "δ��");
		params.put("noContext", "������Ϣ");
		List<Msg> msgs = msgService.query(params);
		for(Msg m : msgs) {
			msgService.delMsg(m.getId());
		}
		json.put("msgs", JSONArray.fromObject(msgs));
		return json;
	}
}
