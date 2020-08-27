package com.dcl.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.domain.Document;
import com.dcl.domain.FileSize;
import com.dcl.domain.Share;
import com.dcl.service.DocumentService;
import com.dcl.service.FileSizeService;
import com.dcl.service.ShareService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/fileSize")
public class FileSizeController {
	
	@Autowired
	FileSizeService fileSizeService;
	
	@RequestMapping(value="/getChartDataForDate")
	@ResponseBody
	JSONObject getChartDataForDate(@RequestParam("account") String account,
							@RequestParam("startDate") String startDate,
							@RequestParam("stopDate") String stopDate) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("startDate", startDate);
		params.put("stopDate", stopDate);
		DecimalFormat df = new DecimalFormat ("#.00");
		List<FileSize> monthDs = fileSizeService.query(params);
		JSONArray monthX = new JSONArray();
		JSONArray monthY1 = new JSONArray();
		JSONArray monthY2 = new JSONArray();
		for(FileSize fs : monthDs) {
			monthX.add(fs.getCreateDate().split(" ")[0]);
			monthY1.add(df.format((float) (fs.getUploadSize() / 1024)));
			monthY2.add(df.format((float) (fs.getDownloadSize() / 1024)));
		}
		json.put("monthX", monthX);
		json.put("monthY1", monthY1);
		json.put("monthY2", monthY2);
		
		return json;
	}
	
	/**
	 * chart���ݻ�ȡ
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getChartData")
	@ResponseBody
	JSONObject getChartData(@RequestParam("account") String account) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		//���ռ�¼��ѯ
		JSONObject todayData = new JSONObject();
		//���ļ�����ȡ��ʹ�ÿռ�
		DocumentService documentService = (DocumentService) Beans.getBean("documentService");
		List<Document> ds = documentService.query(params);
		double totalSize = 0;
		for(Document d : ds) {
			totalSize += d.getSize();
		}
		todayData.put("totalSize", totalSize);
		//��������ȡ���չ����ļ�����
		params.put("todayDate", BasicUtils.getDate());
		ShareService shareService = (ShareService) Beans.getBean("shareService");
		List<Share> ss = shareService.query(params);
		todayData.put("shareNumber", ss.size());
		//���ļ�ͳ�Ʊ���ȡ�����ϴ�������
		params.remove("todayDate");
		params.put("createDate", BasicUtils.getDate());
		List<FileSize> fss0 = fileSizeService.query(params);
		if(fss0.size() > 0) {
			todayData.put("uploadSize", fss0.get(0).getUploadSize());
			todayData.put("downloadSize", fss0.get(0).getDownloadSize());
		}else {
			todayData.put("uploadSize", 0);
			todayData.put("downloadSize", 0);
		}
		//end ���ռ�¼��ѯ
		
		params.remove("createDate");
		DecimalFormat df = new DecimalFormat ("#.00");
		//�ܼ�¼��ѯ
		params.put("dateNumber", 6);
		List<FileSize> fss = fileSizeService.query(params);
		JSONArray weekX = new JSONArray();
		JSONArray weekY1 = new JSONArray();
		JSONArray weekY2 = new JSONArray();
		for(FileSize fs : fss) {
			weekX.add(fs.getCreateDate().split(" ")[0]);
			weekY1.add(df.format((float) (fs.getUploadSize() / 1024)));
			weekY2.add(df.format((float) (fs.getDownloadSize() / 1024)));
		}
		JSONObject weekData = new JSONObject();
		weekData.put("weekX", weekX);
		weekData.put("weekY1", weekY1);
		weekData.put("weekY2", weekY2);
		//end �ܼ�¼��ѯ
		
		//�¼�¼��ѯ
		params.put("dateNumber", 30);
		List<FileSize> monthDs = fileSizeService.query(params);
		JSONArray monthX = new JSONArray();
		JSONArray monthY1 = new JSONArray();
		JSONArray monthY2 = new JSONArray();
		for(FileSize fs : monthDs) {
			monthX.add(fs.getCreateDate().split(" ")[0]);
			monthY1.add(df.format((float) (fs.getUploadSize() / 1024)));
			monthY2.add(df.format((float) (fs.getDownloadSize() / 1024)));
		}
		JSONObject monthData = new JSONObject();
		monthData.put("monthX", monthX);
		monthData.put("monthY1", monthY1);
		monthData.put("monthY2", monthY2);
		//end �¼�¼��ѯ
		
		//��װ����
		json.put("todayData", todayData);
		json.put("weekData", weekData);
		json.put("monthData", monthData);
		return json;
	}
	/**
	 * ����Ա��ȡ����
	 * @return
	 */
	@RequestMapping(value="/adminGetChartData")
	@ResponseBody
	JSONObject adminGetChartData() {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		//���ռ�¼��ѯ
		JSONObject todayData = new JSONObject();
		//���ļ�����ȡ��ʹ�ÿռ�
		DocumentService documentService = (DocumentService) Beans.getBean("documentService");
		List<Document> ds = documentService.query(params);
		double totalSize = 0;
		for(Document d : ds) {
			totalSize += d.getSize();
		}
		todayData.put("totalSize", totalSize);
		//��������ȡ���չ����ļ�����
		params.put("todayDate", BasicUtils.getDate());
		ShareService shareService = (ShareService) Beans.getBean("shareService");
		List<Share> ss = shareService.query(params);
		todayData.put("shareNumber", ss.size());
		//���ļ�ͳ�Ʊ���ȡ�����ϴ�������
		params.remove("todayDate");
		params.put("createDate", BasicUtils.getDate());
		List<FileSize> fss0 = fileSizeService.query(params);
		if(fss0.size() > 0) {
			todayData.put("uploadSize", fss0.get(0).getUploadSize());
			todayData.put("downloadSize", fss0.get(0).getDownloadSize());
		}else {
			todayData.put("uploadSize", 0);
			todayData.put("downloadSize", 0);
		}
		//end ���ռ�¼��ѯ
		
		params.remove("createDate");
		DecimalFormat df = new DecimalFormat ("#.00");
		//�ܼ�¼��ѯ
		params.put("dateNumber", 6);
		List<FileSize> fss = fileSizeService.query(params);
		JSONArray weekX = new JSONArray();
		JSONArray weekY1 = new JSONArray();
		JSONArray weekY2 = new JSONArray();
		for(FileSize fs : fss) {
			weekX.add(fs.getCreateDate().split(" ")[0]);
			weekY1.add(df.format((float) (fs.getUploadSize() / 1024)));
			weekY2.add(df.format((float) (fs.getDownloadSize() / 1024)));
		}
		JSONObject weekData = new JSONObject();
		weekData.put("weekX", weekX);
		weekData.put("weekY1", weekY1);
		weekData.put("weekY2", weekY2);
		//end �ܼ�¼��ѯ
		
		//�¼�¼��ѯ
		params.put("dateNumber", 30);
		List<FileSize> monthDs = fileSizeService.query(params);
		JSONArray monthX = new JSONArray();
		JSONArray monthY1 = new JSONArray();
		JSONArray monthY2 = new JSONArray();
		for(FileSize fs : monthDs) {
			monthX.add(fs.getCreateDate().split(" ")[0]);
			monthY1.add(df.format((float) (fs.getUploadSize() / 1024)));
			monthY2.add(df.format((float) (fs.getDownloadSize() / 1024)));
		}
		JSONObject monthData = new JSONObject();
		monthData.put("monthX", monthX);
		monthData.put("monthY1", monthY1);
		monthData.put("monthY2", monthY2);
		//end �¼�¼��ѯ
		
		//��װ����
		json.put("todayData", todayData);
		json.put("weekData", weekData);
		json.put("monthData", monthData);
		return json;
	}
	/**
	 * ����Ա��ʱ���ѯ
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	@RequestMapping(value="/adminGetChartDataForDate")
	@ResponseBody
	JSONObject adminGetChartDataForDate(@RequestParam("startDate") String startDate,
							@RequestParam("stopDate") String stopDate) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", startDate);
		params.put("stopDate", stopDate);
		DecimalFormat df = new DecimalFormat ("#.00");
		List<FileSize> monthDs = fileSizeService.query(params);
		JSONArray monthX = new JSONArray();
		JSONArray monthY1 = new JSONArray();
		JSONArray monthY2 = new JSONArray();
		for(FileSize fs : monthDs) {
			monthX.add(fs.getCreateDate().split(" ")[0]);
			monthY1.add(df.format((float) (fs.getUploadSize() / 1024)));
			monthY2.add(df.format((float) (fs.getDownloadSize() / 1024)));
		}
		json.put("monthX", monthX);
		json.put("monthY1", monthY1);
		json.put("monthY2", monthY2);
		
		return json;
	}
	
}
