<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户激活</title>
<style type="text/css">
<!--
body{ color:#246da7;font-family:"宋体";height:100%;font-size:12px;  background:#fff url(${ctx}/mas3/images/mms_login/body_iabg.gif) repeat-x 0 0;padding-top:15px;}
.contain{width:1004px;margin:0 auto;padding-bottom:40px;}
.logo {height:72px;background:url(${ctx}/themes/mas3/images/login/jhlogo.gif) no-repeat 0 0;} 
.forgetpassword{ overflow:hidden; zoom:1; border:1px solid #ccc; margin:20px 0px; background:#fff; height:300px;}
.title{background:url(${ctx}/themes/mas3/images/login/forgetp_bg.gif) repeat-x scroll 0 0 transparent;    color: #5A5A5B;    font-size: 14px;    font-weight: bold;    height: 29px;    line-height: 29px;    overflow: hidden;    padding-left: 10px;}
.fpcontent{ padding:20px 200px; text-align:center; overflow:hidden; zoom:1; }
.btn{ width:200px; height:50px; text-align:center; float:left; background-color:#ADBEDE; line-height:50px; margin:10px 50px; font-size:18px; font-family:"微软雅黑"; }
.btn a{ color:#001891; text-decoration:none;position:relative; display:block;}
.prompt{color:red; background: none repeat scroll 0 0 #FFF9E1;border: 1px solid #FFD225;margin: 32px 0 30px 10px;padding: 8px 10px 10px;}
.footer{height:30px;line-height:30px;color:#666666; text-align:center;font-size:13px;padding:10px 0; }
-->
</style>
</head>

<body>
<div class="contain">
	<div class="logo"></div> 
	<div class="forgetpassword">
		<div class="title">当前位置：用户激活</div>
	    <div class="fpcontent">
	    	<p class="prompt">如您处于网络连通状态（可以正常使用互联网），请选择在线激活；否则选择“离线激活”。</p>
	    	<div class="btn"><a href="${ctx}/registerAction/register.action?online=1">在线激活</a></div>
	    	 <div class="btn">
	    	  	<a href="${ctx}/registerAction/register.action?online=0">离线激活</a>
	    	  </div>
	    </div>
	</div>
	<div class="footer">Copyright(@)2011-2012 中国移动通信 版权所有</div>
</div>
</body>
</html>