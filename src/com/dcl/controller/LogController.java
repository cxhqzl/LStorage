package com.dcl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.domain.Log;
import com.dcl.service.LogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/log")
public class LogController {

	@Autowired
	private LogService logService;
	
	/**
	 * 获取日志信息
	 * @param account
	 * @param index
	 * @return
	 */
	@RequestMapping(value="/getLog")
	@ResponseBody
	JSONObject getLog(@RequestParam("account") String account,
							@RequestParam("index") int index) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		if(account != null && !account.equals("")) {
			params.put("account", account);
		}
		int logSize = logService.query(params).size();
		json.put("pages", Math.ceil((float) logSize / 10));
		params.put("limit", 10);
		index = (index - 1) * 10;
		params.put("index", index);
		List<Log> logs = logService.query(params);
		json.put("logs", JSONArray.fromObject(logs));
		json.put("index", index);
		return json;
	}
	
	@RequestMapping(value="/getLogs")
	@ResponseBody
	JSONObject getLogs(@RequestParam("account") String account,
					@RequestParam("index") int index,
					@RequestParam("startDate") String startDate,
					@RequestParam("stopDate") String stopDate,
					@RequestParam("type") String type,
					@RequestParam("source") String source) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		if(account != null && !account.equals("")) {
			params.put("account", account);
		}
		
		if(startDate != null && !startDate.equals("")) {
			params.put("startDate", startDate);
		}
		if(stopDate != null && !stopDate.equals("")) {
			params.put("stopDate", stopDate);
		}
		if(type != null && !type.equals("")) {
			if(!type.equals("t_all")) {
				params.put("type", type);
			}
		}
		if(source != null && !source.equals("")) {
			if(!source.equals("s_all")) {
				params.put("source", source);
			}
		}
		int logSize = logService.query(params).size();
		json.put("pages", Math.ceil((float) logSize / 10));
		params.put("limit", 10);
		index = (index - 1) * 10;
		params.put("index", index);
		List<Log> logs = logService.query(params);
		json.put("logs", JSONArray.fromObject(logs));
		json.put("index", index);
		return json;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delLog")
	@ResponseBody
	boolean delLog(@RequestParam("id") int id) {
		return logService.delLog(id);
	}
}
