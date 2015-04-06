<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>top</title>
	<link type="text/css" rel="stylesheet" href="${ctx }/css/common.css" />
	<!-- 
	<script type="text/javascript" src="${ctx}/js/jquery-1.7.2.min.js"></script>
	<script>
	   $(function(){
			var today=new Date();
			var weekday=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
			$("#currentTime").text("今天是"+today.getFullYear()+"年"+today.getMonth()+"月"+today.getDate()+"日 "+weekday[today.getDay()]);
		});
	</script>
	 -->
</head>
<body>
	<div class="head">
		<div class="head_bg">
			<div class="logo1"><img width="666" height="97" src="${ctx}/images/duanxin_INDEX_01.png"></div>
			<div class="lanmu">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tbody><tr>
	              <td width="74" valign="middle" align="center" onmouseout="this.style.background='';this.style.backgroundColor='';" onmouseover="this.style.background='url(${ctx}/images/t_bg.gif)';" style=""><img src="${ctx}/images/home.png">&nbsp;<a href="#">首页</a></td>
	              <td><img width="1" height="15" src="${ctx}/images/fenge.gif"></td>
	              <td width="74" valign="middle" align="center" onmouseout="this.style.background='';this.style.backgroundColor='';" onmouseover="this.style.background='url(${ctx}/images/t_bg.gif)';" style=""><img src="${ctx}/images/lcon2.png">&nbsp;<a href="#">帐户修改</a></td>
	              <td><img width="1" height="15" src="${ctx}/images/fenge.gif"></td>
	              <td width="74" valign="middle" align="center" onmouseout="this.style.background='';this.style.backgroundColor='';" onmouseover="this.style.background='url(${ctx}/images/t_bg.gif)';" style=""><img src="${ctx}/images/lcon1.png">&nbsp;<a href="#">系统帮助</a></td>
	              <td><img width="1" height="15" src="${ctx}/images/fenge.gif"></td>
	              <td width="74" valign="middle" align="center" onmouseout="this.style.background='';this.style.backgroundColor='';" onmouseover="this.style.background='url(${ctx}/images/t_bg.gif)';" style=""><img src="${ctx}/images/lcon4.png">&nbsp;<a href="${ctx}/j_spring_security_logout">安全退出</a></td>
	            </tr>
	          	</tbody>
	          </table>
			</div>
		</div>
	</div>
    <!-- 
	<div class="connav"><strong>欢迎您，</strong><span>admin</span> <span id="currentTime"></span></div>
 	-->
</body>
</html>
