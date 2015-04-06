<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/jquery-1.7.2.min.js"></script>
	<script>
	var $j = jQuery.noConflict();
	
	$j(document).ready(function() {
		$j("#login_button").bind("click",function(){
			$j("#login_form").submit();
		});		
	});

	</script>
<title>短信办公室</title>
<style type="text/css">
<!--
body {
	background-image: url(images/duanxin_login_01.gif);
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
#apDiv1 {
	position: absolute;
	width: 66px;
	height: 28px;
	z-index: 1;
	left: 298px;
	top: 125px;
}
#apDiv2 {
	position: absolute;
	width: 200px;
	height: 115px;
	z-index: 1;
	left: 663px;
	top: 509px;
}
-->
</style></head>

<body>
	<div class="main_bg">
	<div class="logo"><img src="images/logo.png" /></div>
		<div class="login_box">
		  <div class="login">
		  	<form class="login_form" id="login_form" name="login_form" action="${ctx}/j_spring_security_check" method="post">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="100" height="40" class="login_text">用户名：</td>
                  <td colspan="2"><input type="text" name="j_username" id="username" class="input_login" /></td>
                </tr>
                <tr>
                  <td height="40" class="login_text">密码：</td>
                  <td colspan="2"><input type="password" name="j_password" id="password" class="input_login" /></td>
                </tr>
                <tr>
                  <td height="40" class="login_text">手机验证码：</td>
                  <td width="197"><input type="text" name="mobile"  class="input_login" /></td>
                  <td width="52" valign="middle"><input type="submit" name="button" id="button" value="获取" /></td>
                </tr>
                <tr>
                  <td height="40" class="login_text">验证码：</td>
                  <td colspan="2"><input type="text" name="verifycode"  class="input_yanzheng" /></td>
                </tr>
                <tr>
                  <td height="40">&nbsp;</td>
                  <td colspan="2" valign="middle">
                  	<img id="login_button" src="images/duanxin_login1_15.gif" width="74" height="29" />
                  	<a href="#">忘记密码？</a>
                  </td>
                </tr>
              </table>
             </form>
			</div>
		</div>
		<div class="copy">Copyright&copy;2011-2012 中国移动通信 版权所有</div>
	</div>
</body>
</html>
