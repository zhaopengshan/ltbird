<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="${pageContext.request.requestURL}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${ctx }/themes/mas3/css/login.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/themes/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx }/themes/js/secureInstall/common.js"></script>
	
<title>中国移动云MAS平台</title>
<style type="text/css">
<!--
body {
	background-image: url(${ctx}/themes/mas3/images/login/duanxin_login_01.gif);
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
	<div class="logo"><img src="${ctx}/themes/mas3/images/login/logo.png" /></div>
		<div class="login_box">
		  <div class="login">
		  	<form class="login_form" id="login_form" name="login_form" action="${ctx}/masLoginLogicAction/login.action" method="post">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
                  <td width="90" height="38" class="login_text">企业端口号：</td>
                  <td colspan="2" valign="top"><input type="text" value="10657311" maxlength="20" name="corpAccessNumber" id="corp_AccessNumber" class="input_login" /></td>
                </tr>
                <tr>
                  <td width="90" height="38" class="login_text">用户名：</td>
                  <td colspan="2" valign="top"><input type="text" name="account" id="username" class="input_login" /></td>
                </tr>
                <tr>
                  <td height="38" class="login_text">密码：</td>
                  <td colspan="2" valign="top"><input type="password" name="loginPwd" id="password" class="input_login" /></td>
                </tr>
                <%--验证码现注释掉，因为验证身体方式很多  --%>
                <tr>
                  <td height="38" class="login_text">验证码：</td>
                  <td colspan="2"><input type="text" id="verify_code" name="verifyCode"  class="input_yanzheng" />
                  <img id="image_verify" src="${ctx }/security/verifyCode.action" alt="点击图片刷新验证码" title="点击图片刷新验证码"/></li>
                  </td>
                </tr>
				<!-- <tr>
                  <td height="38" class="login_text">手机验证码：</td>
                  <td colspan="2" valign="top"><input type="text" maxlength="4" name="mobileChecking"  class="input_yanzheng" /><input type="button" name="checkingNumber" id="checkingNumber" style="margin-left:8px;" value="获取" /></td>
                </tr>-->
                <!--验证码现注释掉，因为验证身体方式很多 <tr>
                  <td height="38" class="login_text">验证码：</td>
                  <td colspan="2"><input type="text" id="verify_code" name="verifyCode"  class="input_yanzheng" />
                  <img id="image_verify" src="${ctx }/security/verifyCode.action" alt="点击图片刷新验证码" title="点击图片刷新验证码"/></li>
                  </td>
                </tr>
                 -->
                <tr>
                  <td height="38">&nbsp;</td>
                  <td colspan="2" valign="top" style="padding-left:5px;padding-top:8px;">
                	<img id="login_button" src="${ctx}/themes/mas3/images/login/duanxin_login1_15.gif" style="float:left;" width="74" height="29" />
                  	<span style="float:left;">&nbsp;&nbsp;</span>
                  	<s:if  test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('tuoguan')!='true'">
                		<img id="register_btn" src="${ctx}/themes/mas3/images/login/jihuo.gif" style="float:left;" width="74" height="29"  />
                	</s:if>
                  	<!--<a href="#">忘记密码？</a>-->
                  </td>
                </tr>
              </table>
             </form>
             <span id="errorhint"></span>
			</div>
		</div>
		<div class="copy">Copyright&copy;2012-2013 中国移动通信 版权所有</div>
	</div>
</body>
<script> 
    var errorMessage="${requestScope.message}";
	$(document).ready(function() {
		$("#login_button").bind("click",function(){
			$("#login_form").submit();
		});	
		$("#register_btn").bind("click",function(){
			goUrl("${ctx}/register.jsp");
		});	
       $("#image_verify").click(function(){
            //获取当前的时间作为参数，无具体意义   
            var timenow = new Date().getTime();  
            //每次请求需要一个不同的参数，否则可能会返回同样的验证码   
            //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
            this.src="${ctx}/security/verifyCode.action?d="+timenow;   
        });
        if(errorMessage != null && errorMessage != "") {
            $("#errorhint").html("错误提示:"+errorMessage);
        }
        $("#login_form").submit(function(){
            if($("#username").val() == "") {
                $("#errorhint").html("错误提示:请填写登录用户名!");
                return false;
            }
            if($("#password").val() == "") {
                $("#errorhint").html("错误提示:请填写登录密码!");
                return false;
            }
            if($("#verify_code").val() == "") {
                $("#errorhint").html("错误提示:请填写图形验证码!");
                return false;
            } 
          //  return true;
          
          //首次登录标识判断处理
           var userData="userVO.account="+$("#username").val()+"&userVO.password="+$("#password").val()+"&verifyCode="+$("#verify_code").val()
           		+"&corpAccessNumber="+$("#corp_AccessNumber").val();
           var da= ajax('${ctx}/masLoginLogicAction/firstloginUser.action',userData,false,'post','json');
           var spaceTime;
          // alert(da.remoteIp);
           if(!da.existFlag){
           		$("#errorhint").html(da.existMessage);
           		return false;
           }else{
	           if(!isFirstNull(da.users)){
	        	   //ip地址限制判断 1为限制 da.users.ipAddress.lastIndexOf(da.remoteIp)==-1
	        	   /*if(!isFirstNull(da.ip_limit_lock)&& da.ip_limit_lock!=2 && !isFirstNull(da.users.ipAddress) && da.users.ipAddress!=da.remoteIp){
	        		   alert("登录失败，ip访问受限!");
	        		   return false;
	        	   }*/
	        	   //密码修改周期是否生效 
	        	   //if(da.users.nextPwdModifyTime!=null && da.modifyPeriod !=2 )
	        	   // 	 spaceTime=comptime(da.users.nextPwdModifyTime);
	           }
	          // alert(parseInt(spaceTime));
	          if(isFirstNull(da.users)){
	        	   return true;
	        	   // firstFlag为是否验证首次登录
	          }else if(da.firstFlag!=2 && da.users.firstLoginFlag==0 ){
	        	   alert("您为首次登录,请重新修改密码再进行登录!");
	        	   goUrl("${ctx}/mas3/secure/firstLoginPwd.jsp");
	        	   return false;
	           }else if( da.modifyPeriod ){
	        	   alert("您超过"+da.modifyPeriodValue+"天未修改密码,请重新修改密码再进行登录!");
	        	   goUrl("${ctx}/mas3/secure/firstLoginPwd.jsp");
	        	   return false;
	           }
	           return true;
	    	}
        });
        
        /*
         *	短信验证
         */
        $("#checkingNumber").click(function(){
        	 var bool=true;
        	if($("#username").val() == "") {
                $("#errorhint").html("错误提示:请填写登录用户名!");
                bool= false;
            }else if($("#password").val() == "") {
                $("#errorhint").html("错误提示:请填写登录密码!");
                bool=false;
            }else if($("#verify_code").val() == ""){
            	$("#errorhint").html("错误提示:请填写图形验证码!");
                bool=false;
            }
            if(bool){
            	 $.ajax({
            	 //+"&corpAccessNumber="+$("#corp_AccessNumber").val()
            		 url: '${ctx}/masLoginAction/checkingNumber.action',
                     data :{ "userVO.account":$("#username").val(), "userVO.password":$("#password").val(),
                     "corpAccessNumber":$("#corp_AccessNumber").val(), "verifyCode":$("#verify_code").val()}, 
                     type: "post",
                     success: function(data){
                    	 alert(data);
                     },
                     error:function(data){
                    	alert("发送验证码有误！");
                     }
                 });
            } 
        });
	});
	</script>
</html>