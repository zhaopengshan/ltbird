<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业自助注册</title>
<%-- <link href="<%=basePath%>themes/mas3admin/css/css.css" rel="stylesheet" type="text/css" /> --%>
<script type="text/javascript" src="<%=basePath%>themes/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
var str;
var reg;
$(function(){
	$("#name").bind("keyup",function(){
		str=$.trim($("#name").val());
		if(str==null||str==""){
			$("#nameMsg").html("企业名称不能为空！");
		}else{
			$("#nameMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（填写企业全称）"){
			$(this).val("");
		}
	});
	$("#account").bind("keyup",function(){
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#account").val());
		if(str==""||str==null){
			$("#accountMsg").html("企业管理员账号不能为空!");
		}else if(!reg.test(str)){
			$("#accountMsg").html("企业管理员账号必须由字母、数字、下划线组成！");
		}else if("administrator" == str.toLocaleLowerCase()){
			$("#accountMsg").html("企业管理员账号不能为administrator大小写形式！");
		}else if(str.length<6||str.length>20){
			$("#accountMsg").html("企业管理员密码必须在6到20位之间！");
		}else{
			$("#accountMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（6-20位，字母/数字/下划线组成）"){
			$(this).val("");
		}
	});
	$("#pwd").bind("keyup",function(){
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#pwd").val());
		if(str==""||str==null){
			$("#pwdMsg").html("企业管理员密码不能为空!");
		}else if(!reg.test(str)){
			$("#pwdMsg").html("企业管理员密码必须由字母、数字、下划线组成！");
		}else if(str.length<6||str.length>20){
			$("#pwdMsg").html("企业管理员密码必须在6到20位之间！");
		}else{
			$("#pwdMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（6-20位，字母/数字/下划线组成）"){
			$(this).val("");
		}
	});
	$("#mobile").bind("keyup",function(){
		reg=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		str=$.trim($("#mobile").val());
		if(str==""||str==null){
			$("#mobileMsg").html("企业管理员手机号不能为空!");
		}else if(!reg.test(str)){
			$("#mobileMsg").html("手机号码格式不符合要求，请重新输入!");
		}else{
			$("#mobileMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（11位手机号码）"){
			$(this).val("");
		}
	});
});
function validateParas(){
		str=$.trim($("#name").val());
		if(str==null||str==""){
			$("#nameMsg").html("企业名称不能为空！");
			return false;
		}
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#account").val());
		if(str==""||str==null){
			$("#accountMsg").html("企业管理员账号不能为空!");
			return false;
		}else if(!reg.test(str)){
			$("#accountMsg").html("企业管理员账号必须由字母、数字、下划线组成！");
			return false;
		}else if(str.length<6||str.length>20){
			$("#accountMsg").html("企业管理员账号必须在6到20位之间！");
		}
		else if("administrator" == str.toLocaleLowerCase()){
			$("#accountMsg").html("企业管理员账号不能为administrator大小写形式！");
			return false;
		}
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#pwd").val());
		if(str==""||str==null){
			$("#pwdMsg").html("企业管理员密码不能为空!");
			return false;
		}else if(!reg.test(str)){
			$("#pwdMsg").html("企业管理员密码必须由字母、数字、下划线组成！");
			return false;
		}else if(str.length<6||str.length>20){
			$("#pwdMsg").html("企业管理员密码必须在6到20位之间！");
		}
		reg=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		str=$.trim($("#mobile").val());
		if(str==""||str==null){
			$("#mobileMsg").html("企业管理员手机号不能为空！");
			return false;
		}else if(!reg.test(str)){
			$("#mobileMsg").html("手机号码格式不符合要求，请重新输入！");
			return false;
		}
}
function sub(){
        var insertUrl="<%=basePath%>openAccountAction/createMerhchant.action";
		var msg = $("#regCorp").serialize();
			$.ajax({
			type: "POST",
			beforeSend:validateParas(),
			url: insertUrl,
			data: msg,
            dataType:  "json",
			success: 
				function(data){
					var entityMap=data;
                    alert(entityMap.resultMsg);
				}
		});
}
</script>
<body>
	<div class="config">
		<form id="regCorp">
        <ul>
	        <li>
	        	<span>企业自助注册</span>
    	    	&nbsp;&nbsp;
        		<span>请如实填写以下信息</span>
        	</li>
       		<li>
       			<label class="rnameex">
       				<span class="needtip">*</span>
       				<span>企业名称（全称）</span>
	            </label>
	            <input id="name" type="text" name="mbnMerchantVip.name" value="（填写企业全称）" />
	            <span id="nameMsg" class="needtipresult"></span>      
	       	</li>
	       	<li>
	       		<label class="rnameex">
	       			<span class="needtip">*</span>
	       			<span>企业管员理账号</span>
	            </label>	
	            <input id="account" type="text" name="userVO.account" value="（6-20位，字母/数字/下划线组成）" />
	            <span id="accountMsg" class="needtipresult"></span>      
	       	</li>
	       	<li>
	       		<label class="rnameex">
	       			<span class="needtip">*</span>
	       			<span>企业管员理密码</span>
	       		</label>
	            <input id="pwd" type="text" name="userVO.password" value="（6-20位，字母/数字/下划线组成）" />
	            <span id="pwdMsg" class="needtipresult"></span>      
	       	</li>
	       	<li>
	       		<label class="rnameex">
	       			<span class="needtip">*</span>
	       			<span>企业管理员手机号</span>
	       		</label>
	            <input id="mobile" type="text" name="userVO.mobile" value="（11位手机号码）" />
	            <span id="mobileMsg" class="needtipresult"></span>      
	        </li>
	        <li class="btn">
	        	<input type="button" onclick="sub();" value="提交"/>
       		</li>
       	</ul>
       	</form>
	</div>
</body>
</html>