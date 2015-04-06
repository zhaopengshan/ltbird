<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<title>中国移动云MAS平台</title>
<style type="text/css">
<!--
.Table_01 tr td {
	color: #F30;
	font-size: 16px;
	margin-top: 10px;
	text-align: center;
}

body {
    background-color: #F2F2F2;
    color: #E6F2FB;
    margin-top: 20px;
}
-->
</style>
<script type="text/javascript" src="${ctx }/themes/js/jquery-1.7.2.min.js"></script>
<!--<script type="text/javascript" src="${ctx}/common/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/common/js/easyui-lang-zh_CN.js"></script>-->
<script type="text/javascript">
/**
 * 是否进行强行登陆操作
 */
<s:if test="msg==1">alert("短信验证码输入错误。");</s:if>
 function dynclogin(){
 	if(jQuery.trim($("#mobileChecking").val()) == null || jQuery.trim($("#mobileChecking").val()) == ""){
        alert("短信验证码不能为空");
        return true;
    };
    var form = $("#form");
    form.attr("action","./masLoginLogicAction/loginSmsCheck.action");
    form.attr("method","post");
    form.submit();
    return true;
 }
function loginout(){
//退出登陆,但其它人可以继续使用
   var form = $("#form");
   form.attr("action","masLoginLogicAction/logout.action");
   form.submit();
   //window.location.href = "login.jsp";
   //return true;
}
//
var maxtime = 2*60, tttimer;
function submitCall (){
	$("#subFindSms").attr("disabled", true).unbind("click");
	tttimer = setInterval("CountDown()",1000);
	$.ajax({
		url: "./smssend/smsCheckLoginGet.action",
		data :{"portalUserExt.id":$("#tempUserExt_id").val()},
		//async : false,
		type : 'post',
		dataType : 'json',
		success : function(dat) {
			if (dat.resultcode==="success") {
				alert(dat.message);
			}else{
				alert(dat.message);
			}
		},
		error : function(dat) {
			maxtime = -1;
			alert("信息提交失败!");
		}
	});
}

function CountDown() {
    if(maxtime>=0) {
    	$("#subFindSms").val("重发("+maxtime+"s)");
        --maxtime;  
    } else {
    	$("#subFindSms").val("获取验证码").attr("disabled", false).unbind("click").bind("click", submitCall);
        clearInterval(tttimer);
    }  
}

$(document).ready(function(){
	$("#subFindSms").val("获取验证码").attr("disabled", false).unbind("click").bind("click", submitCall);
});
</script>
</head>
<body style="margin:0px;">
<form id="form" method="post">
    <table width="888" border="0" align="center" cellpadding="0" cellspacing="0" class="Table_01" >
      <tr>
            <td rowspan="2" >
            </td>

            <td rowspan="2" >&nbsp;</td>
            <td colspan="5" rowspan="2" valign="middle" ><!-- <img src="/common/images/head_01.jpg" /> --></td>

            <td rowspan="2">&nbsp;</td>
      </tr>
    </table>
    <table  width="420px;" border="0" align="center" cellpadding="0" cellspacing="10" class="Table_01" style=" border:#F60 3px solid; background-color:#FCFCFC; margin-top: 40px;">
      <tr>
            <td style="font-weight:bold;">短信验证码：<input  type="text" id="mobileChecking" name="mobileChecking" class="text">
            <input style="width:100px; height:30px; margin:0 15px 0 0;" type="button" disabled="disabled" id="subFindSms" value="获取验证码"></input></td>
      </tr>
       <tr id="messageTip">
            <td style=" color:#C6C6C6; font-size:12px;">(提示：点击获取验证码后，如果在120s内未收到短信验证码，请重新点击获取验证码按钮。)</td>
      </tr>
      <tr>
            <td> <input style="width:130px; height:30px; margin:0 15px 0 0;" type="button" id="forceLogin"   value="进入系统" onclick="return dynclogin()">
            <input style="width:60px; height:30px; margin:0 15px 0 0;" type="button" id="exit" value="退出" onclick="return loginout()"></input></td>
      </tr>
    </table>
<s:hidden name="tempUserExt.id"></s:hidden>
</form>
</body>
</html>