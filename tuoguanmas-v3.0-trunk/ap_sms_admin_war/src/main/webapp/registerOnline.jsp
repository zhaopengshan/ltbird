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
.ftitle{background:url(${ctx}/themes/mas3/images/login/forgetp_bg.gif) repeat-x scroll 0 0 transparent;    color: #5A5A5B;    font-size: 14px;    font-weight: bold;    height: 29px;    line-height: 29px;    overflow: hidden;    padding-left: 10px;}
.fpcontent{ padding:20px 0px 0px 200px; text-align:center; overflow:hidden; zoom:1; }
.fpcontent div{line-height:36px;height:45px; margin-top:10px; text-align:left; font-size:14px; clear:both;}
.fpcontent div span.name{ width:140px; text-align:right; padding-right:8px;float:left;}
.fpcontent div input.text{ margin-top:5px; line-height:20px; height:20px; padding:2px 5px; width:180px; border:1px solid #ccc; }
.fpcontent div input.btn1{ background:url(${ctx}/themes/mas3/images/login/online.gif) repeat-x 0 0; width:112px; height:31px; border:None; cursor:pointer;}
.fpcontent div span.prompt{color:red; font-size:12px;padding-left:8px;}
.footer{height:30px;line-height:30px;color:#666666; text-align:center;font-size:13px;padding:10px 0; }
-->
</style>
<script type="text/javascript" src="${ctx}/themes/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/themes/js/jquery-ui-1.8.22.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/themes/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/themes/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx }/themes/js/secureInstall/common.js"></script>
<script type="text/javascript">
	$(function(){
		function validateSubmitFunc(responseText, statusText){
			alert(responseText.message);
			if( responseText.status ){
				goUrl("${ctx}/smslogin.jsp");
			}
		}
		$("#register_online").validate({
			rules:{
				"userName":{
					required: true
				},
				"userPwd":{
					required: true
				},
				"lisenceObj.lisenceValue":{
					required: true,
					rangelength: [16, 16],
					digits:true
				}
			},
			messages:{
				"userName":{
					required: "<span class='prompt'>*请输入产品用户名</span>"
				},
				"userPwd":{
					required: "<span class='prompt'>*请输入产品密码</span>"
				},
				"lisenceObj.lisenceValue":{
					required: "<span  class='prompt'>*请输入厂家提供的16位数字证书</span>",
					rangelength: "<span  class='prompt'>*数字证书为16位</span>",
					digits: "<span class='prompt'>*输入必须为数字</span>",
				}
			},
			submitHandler: function(form){
				$("#register_online").ajaxSubmit({
					dataType:"json",
					success: validateSubmitFunc
				});
			},
		});
	});
</script>
</head>

<body>
<div class="contain">
<div class="logo"></div> 
<div class="forgetpassword">
	<div class="ftitle">当前位置：在线激活</div>
    <div class="fpcontent">
	    <form id="register_online" action="${ctx}/registerAction/onlineRegister.action" method="post">
	    <div><span class="name">您的有效IMEI是:</span>
	      <s:iterator value="localList" var="subResource">
	      <input type="text" class="text" style="border:none;" value="<s:property value="subResource" />" readonly="readonly"/>
	      </s:iterator>
	      </div>
	      <div><span class="name">产品用户名：</span><input name="userName" type="text" class="text" /></div>
	      <div><span class="name">产品密码：</span><input name="userPwd" type="password" class="text" /></div>
	      <div style="margin-left:190px;margin-top:25px;" ><input type="submit" value="" class="btn1" /></div>
	
	  </form>
    </div>
</div>
<div class="footer">Copyright(@)2011-2012 中国移动通信 版权所有</div>
</div>
</body>
</html>