<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="UTF-8"%>
<%
//String path = request.getContextPath();
String filePath = request.getParameter("path").replace(",", "\\");
FileSaver fs=new FileSaver(request,response);
String fileName = fs.getFileName();
fileName = java.net.URLDecoder.decode(fileName,"UTF-8");
fs.saveToFile(filePath + "\\" + fileName);
fs.close();
%>

