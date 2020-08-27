<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.dcl.utils.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String filePath = BasicUtils.getStoragePath(request.getParameter("path").replace(",", "\\"));
String fileName = request.getParameter("fileName");
fileName = java.net.URLDecoder.decode(fileName,"UTF-8");
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//设置保存页面
poCtrl.addCustomToolButton("保存", "Save()", 1);
poCtrl.setSaveFilePage("SaveFile.jsp?path=" + filePath.replace("\\", ","));
//打开Word文档
poCtrl.webOpen(filePath + "\\" + fileName ,OpenModeType.docNormalEdit,fileName);
try{
	
}catch(Exception e){
	
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>在线编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div style="height:100%;width:100%;overflow:hidden;">
	<%=poCtrl.getHtmlCode("PageOfficeCtrl1")%>
	</div>
  </body>
  <script type="text/javascript">
  function Save() {
      document.getElementById("PageOfficeCtrl1").WebSave();
  }

  </script>
</html>
