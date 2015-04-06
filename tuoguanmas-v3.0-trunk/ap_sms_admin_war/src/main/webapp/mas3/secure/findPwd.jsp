<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
	<script type="text/javascript" src="${ctx }/themes/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/mas3/js/jquery.tools.min.js"></script> 
	<script type="text/javascript" src="${ctx }/themes/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx }/themes/js/jquery-ui-1.8.22.custom.min.js"></script>
	<script type="text/javascript" src="${ctx }/themes/js/secureInstall/common.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/validate.js"></script>
<link rel="stylesheet" href="${ctx}/mas3/css/mms_login.css" />  
<style type="text/css">
.nameTex{
	color: red;
	} 
	
.mobile{
	color: red;
	}
</style>
<!--[if lte IE 9]>
	<script src="${ctx}/mas3/js/html5.js"></script>
<![endif]-->
<title>找回密码</title>
</head>
<body>   
<div class="contain">
	<div class="logo"></div> 
	<div class="forgetpassword">
    	<div class="title">当前位置：找回密码</div>
        <div class="fpcontent">
        	<p>提示：第一步，填写用户名和手机号后，点击“获取验证码”；第二步，输入手机上收到的验证码，点击“确定”按钮。</p>
            <ul class="fplist">
            	<li><span class="name">用户名：</span>
                <span><input id="userName"  type="text" value="" class="text" >
               </span> <label class="nameTex" style="color:reg;" id="nameTex"></label>
                </li>
                <li><span class="name">手机号：</span>
                <span>
                <input id="mobile" type="text"  value="" class="text"></span>
				<span><input  id="subFindSms" type="button"  value="获取验证码" class="yzm"></span>
				<label  class="mobile" id="mobileTex"></label>
              </li>
				<li><span class="name">短信验证码：</span>
                <span><input  type="text" id="smsNumber" class="text"></span>
				<span id="checkTex" class="f12">5分钟内输入有效，超时请重新获取</span>
                </li>
                <li><input id="subChecking" type="button" class="submit" value=""></li>
            </ul>
        </div>
    </div>
<div class="footer">Copyright(@)2011-2012 中国移动通信 版权所有</div>
</div>    
</body>
<script type="text/javascript">
$(function(){
	   $("#subFindSms").unbind("click").bind("click",function(){
		 if(check()){
		 // ajax(url, data, async, type, datatype)
		 var url="${ctx}/adminFindPwdAction/userChelcking.action";
		 var dat="userVO.account="+getValue('#userName')+"&userVO.mobile="+getValue('#mobile');
		 $.ajax({
				url : url,
				data :dat,// 'account='+getValue('#userName')+"&userVO.mobile="+getValue('#mobile'),
				async : false,
				type : 'post',
				datatype : 'json',
				success : function(dat) {
					if (isFirstNull(dat)) {
						return false;
					} else {
						dat= eval("(" + dat + ")");
						if(dat.message==true){
							alert("验证码发送成功,请注意接收!");
							return true;
						}
						alert(dat.message); 
						
					}
				},
				error : function(dat) {
					//alert("pageError:" + dat);
				}
			});
		
		 }
	 });
	 
	 $("#subChecking").unbind("click").bind("click",function(){
		 if(check()){
			var moben=/^d\$/;
			var na=getValue("#smsNumber");
			 if(isFirstNull(na)){
				 $("#checkTex").html("验证码不能为空!");
			 }else if(isReg(moben,na)){
				 $("#checkTex").html("验证码只能是数字!");
			 }else{
				 getObj("#mobileTex").html("");
			 }
			 var url="${ctx}/adminFindPwdAction/sendPwd.action";
			 var dat="userVO.account="+getValue('#userName')+"&userVO.mobile="+getValue('#mobile')+"&smsNumber="+getValue('#smsNumber');
			 $.ajax({
					url : url,
					data :dat,// 'account='+getValue('#userName')+"&userVO.mobile="+getValue('#mobile'),
					async : false,
					type : 'post',
					datatype : 'json',
					success : function(dat) {
						if (isFirstNull(dat)) {
							alert("信息提交失败!");
							return false;
						} 
						dat=eval("(" + dat + ")");
							alert(dat.message);
							if(dat.success=='success')
							goUrl("${ctx}/login.jsp");
						 
					},
					error : function(dat) {
						//alert("pageError:" +"验证失败:"+ dat);
					}
				});
		 }
	 });  
	 
	 function check(){
			var na="";
			var moben="";
			na=getValue('#userName');
			moben=/^[0-9a-zA-a_]{6,20}$/;
			 if(isFirstNull(na)){ 
				 getObj("#nameTex").html("*用户名不能为空!");
				 return false;
			 }else{
				 getObj("#nameTex").html("");
			 }
			 if(!isReg(moben,na)){
				 getObj("#nameTex").html("用户名只能是数字、字母、下划线组成!");
				 return false;
			 }else{
				 getObj("#nameTex").html("");
			 }
			   na=getValue("#mobile");
			   moben=/^[1][3,5,8][0-9]{9}$/;
			 if(isFirstNull(na)){
				 $("#mobileTex").html("*手机号不能为空!");
				 return false;
			 }else{
				 getObj("#mobileTex").html("");
			 }
			 if(!isReg(moben,na)){
				 $("#mobileTex").html("*手机号只能是数字!并且为11位");
				 return false;
			 }else{
				 getObj("#mobileTex").html("");
			 } 
			 return true;
		} 
	});
	/* 
function test(){
	 $.ajax({
			url : '${ctx}/findPwdAction/userChelcking.action',
			data : 'account='+getValue('#userName')+"&userVO.mobile="+getValue('#mobile'),
			async : false,
			type : 'post',
			datatype : 'json',
			success : function(dat) {
				if (isFirstNull(dat)) {
					return false;
				} else {
					re= JSON.parse(dat); 
				}
			},
			error : function(dat) {
				alert("pageError:" + dat);
			}
		});
} */
</script>
</html>  