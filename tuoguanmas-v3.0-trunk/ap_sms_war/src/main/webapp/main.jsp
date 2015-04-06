<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>短信办公室</title>
	<link href="${ctx }/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }/css/TabPanel.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
   <div class="mainbody">
	   	<jsp:include page="left.jsp"></jsp:include>
	   	<div class="main-com clear">
	   		<div class="crumbs" id="currLocation">当前位置：首页</div>
	        <div class="info_detail clear">
	        	 <div id="tab"></div>
	   		</div>
	   	</div>
	  </div><!-- center -->
</body>
</body>
<jsp:include page="foot.jsp"></jsp:include>
</html>
