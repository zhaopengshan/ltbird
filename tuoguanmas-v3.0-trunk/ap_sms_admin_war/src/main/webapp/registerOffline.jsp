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
.fpcontent{ padding:20px 10px 20px 200px; text-align:center; overflow:hidden; zoom:1; }
.fpcontent div{line-height:36px; height:45px; margin-top:10px; text-align:left; font-size:14px;}
.fpcontent div span.name{ width:140px; text-align:right; padding-right:8px;float:left;}
.fpcontent div input.text{ margin-top:5px; line-height:20px; height:20px; padding:2px 5px; width:180px; border:1px solid #ccc; }
.fpcontent div input.ps{border:2px solid #65B1D8;border-radius:4px; background-color:#CCE2ED; color:#033C6C;line-height:20px; height:20px; padding:2px 5px;width:180px;}
.fpcontent div input.code{background: #FFF9E1;border: 2px solid #FF5325;border-radius:4px;line-height:20px; height:20px; padding:2px 5px;width:180px; color:red;  margin-top:5px;}
.fpcontent div input.btn1{ background:url(${ctx}/themes/mas3/images/login/offline.gif) repeat-x 0 0; width:112px; height:31px; border:None; cursor:pointer;}
.prompt{color:red; font-size:12px;text-align:left; line-height:24px;margin:0px 0 0 148px;padding:5px 10px;  width:500px; line-height:18px;}
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
		$("#register_offline").validate({
			rules:{
				"lisenceObj.lisenceValue":{
					required: true,
					rangelength: [16, 16]
				}
			},
			messages:{
				"lisenceObj.lisenceValue":{
					required: "<span style='color:red; font-size:12px; padding-left:8px;'>*请输入16位产品激活码</span>",
					rangelength: "<span style='color:red; font-size:12px; padding-left:8px;'>*产品激活码为16位</span>"
				}
			},
			submitHandler: function(form){
				$("#register_offline").ajaxSubmit({
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
	<div class="ftitle">当前位置：离线激活</div>
    <div class="fpcontent">
    <form id="register_offline" action="${ctx}/registerAction/offlineRegister.action" method="post">	  
		      <div><span class="name">您的有效IMEI是:</span>
		      <s:iterator value="localList" var="subResource">
		      	<input type="text" class="ps"  value="<s:property value="subResource" />" readonly="readonly"/>
		      </s:iterator>
		      </div>
		      <p class="prompt">1、请使用其他可正常使用互联网的计算机，打开以下网址：http://www.yunmas.com/auth；<br/>2、输入产品帐号和密码及上述<span style=" color:#033C6C; font-weight:bold;"> IMEI </span>，以获取<span style="font-weight:bold;">16位产品激活码</span><br>
		    	  <span style="font-weight:bold;">特别注意，红色输入框中输入正确内容前，先不要关闭本页面。</span></p>
		  	<div style="margin-top:0px;"><span class="name">产品激活码:</span>
		  	<input name="lisenceObj.lisenceValue" class="code" type="text" /></div>
		  	<div style="margin-left:190px;margin-top:15px;"><input type="submit" value="" class="btn1" /></div>
	  
	  </form>
    </div>
	</div>
	<div class="footer">Copyright(@)2011-2012 中国移动通信 版权所有</div>
</div>
</body>
</html>