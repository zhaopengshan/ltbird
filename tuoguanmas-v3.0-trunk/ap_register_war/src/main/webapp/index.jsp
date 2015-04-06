<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./themes/css/jquery-ui-1.8.22.custom.css" rel="stylesheet" type="text/css" />
<title>用户激活</title>
<style type="text/css">
<!--
body{ color:#246da7;font-family:"宋体";height:100%;font-size:12px;  background:#fff url(themes/images/body_iabg.gif) repeat-x 0 0;padding-top:15px;}
.contain{width:1003px;margin:0 auto;padding-bottom:40px;}
.logo {height:72px;background:url(themes/images/jhlogo.gif) no-repeat 0 0;} 
.forgetpassword{ overflow:hidden; zoom:1; border:1px solid #ccc; margin:20px 0px; background:#fff; height:400px;}
.ftitle{background:url(themes/images/forgetp_bg.gif) repeat-x scroll 0 0 transparent;    color: #5A5A5B;    font-size: 14px;    font-weight: bold;    height: 29px;    line-height: 29px;    overflow: hidden;    padding-left: 10px;}
.fpcontent{ padding:20px 0 0 200px; text-align:left; overflow:hidden; zoom:1; }
.fpcontent div{line-height:36px; height:45px; margin-top:10px; text-align:left; font-size:14px;}
.fpcontent div span.name{ width:120px; text-align:right; padding-right:8px;float:left;}
.fpcontent div input.text{ margin-top:8px; line-height:20px; height:20px; padding:2px 5px; width:180px; border:1px solid #ccc; }
.fpcontent div input.ps{border:2px solid #65B1D8;border-radius:4px; background-color:#CCE2ED; color:#033C6C;line-height:20px; height:20px; padding:2px 5px;width:180px;}
.fpcontent div input.code{background: #FFF9E1;border: 2px solid #FF5325;border-radius:4px;line-height:20px; height:20px; padding:2px 5px;width:180px; color:red;  margin-top:5px;}
.fpcontent div input.btn1{ background:url(themes/images/gain.gif) repeat-x 0 0; width:112px; height:31px; border:None; cursor:pointer;}
.fpcontent div span.pro{color:red; font-size:12px;padding-left:8px;}
.footer{height:30px;line-height:30px;color:#666666; text-align:center;font-size:13px;padding:10px 0; }

-->
</style>
<script type="text/javascript" src="./themes/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./themes/js/jquery-ui-1.8.22.custom.min.js"></script>
<script type="text/javascript" src="./themes/js/jquery.form.js"></script>
<script type="text/javascript" src="./themes/js/jquery.validate.js"></script>
<script type="text/javascript">
	$(function(){
		function validateSubmitFunc(responseText, statusText){
			if( responseText.status ){
				$("#lisenceShow").css('display', 'block');
				$("#lisenceKey").val(responseText.values);
			}
			alert(responseText.message);
		}
		$("#login_form").validate({
			rules:{
				"lisenceObj.userName":{
					required: true
				},
				"lisenceObj.userPwd":{
					required: true
				},
				"lisenceObj.userKey":{
					required: true,
					rangelength: [15, 15],
					digits:true
				}
			},
			messages:{
				"lisenceObj.userName":{
					required: "<span class='pro'>*请输入产品用户名</span>"
				},
				"lisenceObj.userPwd":{
					required: "<span class='pro'>*请输入产品密码</span>"
				},
				"lisenceObj.userKey":{
					required: "<span class='pro'>*请输入产品提供的15位IMEI</span>",
					rangelength: "<span class='pro'>*产品IMEI为15位数字</span>",
					digits: "<span class='pro'>*输入必须是数字</span>"
				}
			},
			submitHandler: function(form){
				$("#login_form").ajaxSubmit({
					dataType:"json",
					success: validateSubmitFunc
				});
			}
		});
	});
</script>
</head>

<body>
<div class="contain">
<div class="logo"></div> 
<div class="forgetpassword">
	<div class="ftitle">当前位置：获取激活码</div>
    <div class="fpcontent">
    <form id="login_form" action="registerAction/register.action" method="post">
	    <div><span class="name">产品用户名：</span><input id="userName" name="lisenceObj.userName" type="text" class="text" /></div>
	    <div><span class="name">产品密码：</span><input id="userPwd" name="lisenceObj.userPwd" type="password" class="text"  /></div>
	    <div><span class="name">您的有效IMEI:</span><input id="userKey" name="lisenceObj.userKey" type="text" class="ps"  />
	    <!--<span style="color:red; padding-left:5px;font-size:12px;">请将离线激活页上任一<span style=" color:#033C6C; font-weight:bold;">IMEI</span>拷贝至左侧蓝色输入框</span>--></div>
	    <div style="margin-left:170px;margin-top:25px; margin-bottom:25px;" ><input type="submit" class="btn1" value=""/></div>
		<div id="lisenceShow" style="display:none;"><span class="name">产品激活码:</span><input id="lisenceKey" class="code"  type="text" readonly="readonly" /><span style="color:red; padding-left:5px;font-size:12px;">请复制左侧的<span style="font-weight:bold">16位产品激活码</span>并返回离线激活页，拷贝至红色输入框中，完成离线激活！</span>

				 
</div>
		

</form>
    </div>
</div>
<div class="footer">Copyright(@)2011-2012 中国移动通信 版权所有</div>
</div>

</body>
</html>