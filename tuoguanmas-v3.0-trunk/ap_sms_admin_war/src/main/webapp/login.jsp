<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <base href="${pageContext.request.requestURL}" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="${ctx }/themes/mas3admin/css/login.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${ctx }/themes/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="${ctx }/themes/js/jquery.form.js"></script>
	    <script type="text/javascript" src="${ctx }/themes/js/jquery-ui-1.8.22.custom.min.js"></script>
	    <script type="text/javascript" src="${ctx }/themes/js/secureInstall/common.js"></script>
	    <script type="text/javascript" src="${ctx }/themes/js/validate.js"></script>
        <script type="text/javascript">
            var errorMessage="${requestScope.message}";
            $(document).ready(function(){
                $("#image_verify").click(function(){
                    //获取当前的时间作为参数，无具体意义   
                    var timenow = new Date().getTime();  
                    //每次请求需要一个不同的参数，否则可能会返回同样的验证码   
                    //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
                    this.src="${ctx }/security/verifyCode.action?d="+timenow;   
                });
                if(errorMessage != null && errorMessage != "") {
                    $("#errorhint").html("错误提示:"+errorMessage);
                }
                $("#login_form").submit(function(){
                    if($("#account").val() == "") {
                        $("#errorhint").html("错误提示:请填写登录用户名!");
                        return false;
                    }
                    if($("#login_pwd").val() == "") {
                        $("#errorhint").html("错误提示:请填写登录密码!");
                        return false;
                    }
                    if($("#verify_code").val() == "") {
                        $("#errorhint").html("错误提示:请填写验证码!");
                        return false;
                    }
                    //return true;
                    //首次登录标识判断处理
		           var userData="userVO.account="+$("#account").val()+"&userVO.password="+$("#login_pwd").val()+"&verifyCode="+$("#verify_code").val();
		           var da= ajax('${ctx }/masLoginAction/adminFirstloginUser.action',userData,false,'post','json');
		           var spaceTime;
		          // alert(da.remoteIp);
		           if(!da.existFlag){
		           		$("#errorhint").html(da.existMessage);
		           		$("#image_verify").attr("src", "${ctx }/security/verifyCode.action?" + new Date());
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
			        	   goUrl("${ctx }/mas3/secure/adminFirstLoginPwd.jsp");
			        	   return false;
			           }else if( da.modifyPeriod ){
			        	   alert("您超过"+da.modifyPeriodValue+"天未修改密码,请重新修改密码再进行登录!");
			        	   goUrl("${ctx }/mas3/secure/adminFirstLoginPwd.jsp");
			        	   return false;
			           }
			           return true;
			    	}
                });
            })            
        </script>
        <title>中国移动云MAS管理平台</title>        
    </head>

    <body class="login_body">        
        <form id="login_form" action="${ctx }/masLoginAction/login.action" method="post">
            <ul class="login_bg">
                <li class="login_logo"></li>
                <li class="login_ul_right">
                    <ul class="login_ul">
                        <li class="login_input login_account_bg"><span class="login_text">用户名：</span><input type="text" name="userVO.account" id="account"  class="login_input" /></li>
                        <li class="login_input login_pswd_bg"><span class="login_text">密码：</span><input type="password" name="userVO.password" id="login_pwd" class="login_input" /></li>
                        <li class="login_input image_verify_bg"><span class="login_text">验证码：</span><input type="text" name="verifyCode" id="verify_code"  class="login_input_verify" /><img id="image_verify" src="${ctx }/security/verifyCode.action" alt="点击图片刷新验证码" title="点击图片刷新验证码"/></li>
                        <li class="login_input login_button_bg"><input type="image" id="login_button" src="${ctx }/themes/mas3admin/images/login/login_button.gif" class="login_opt" /><!--<a id="forget_pwd" href="#">忘记密码？</a>--></li>
                    </ul>
                </li>
                <li class="copy">Copyright&copy;2012-2013 中国移动通信 版权所有</li>
            </ul>
        </form>
        <span id="errorhint"></span>
    </body>
</html>