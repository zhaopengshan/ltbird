<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" > 
<link rel="stylesheet" href="${ctx }/mas3/css/main.css" /> 
<link rel="stylesheet" href="${ctx }/mas3/css/zTreeStyle/zTreeStyle.css" />  
<!--[if lte IE 9]>
	<script src="${ctx }/js/html5.js"></script>
<![endif]-->
<title>安全管理</title> 
</head>
<body>   
	<form id="f1" method="post"  action="${ctx }/securifyAdminAction/execute.action">
				<div class="info_detail clear">
				  <div class="rightbk">
				    <table width="100%"  class="marginb10" cellpadding="0"  cellspacing="1"   >
				      <tr>
				        <td width="6%" height="35" align="center"><input name="securifyValue.checkeds" 
				        <c:if test="${request.secureMap.first_login_modify_pwd_flag.validFlag ne 2 }">checked="checked"</c:if>
				         type="checkbox" value="0" ></td>
				        <td>首次登录必须修改密码</td>
			          </tr>
				      <tr>
				        <td height="30" align="center"><input id="firstPwd" name="securifyValue.checkeds"
				       		 <c:if test="${request.secureMap.pwd_modify_period.validFlag ne 2 }">checked="checked"</c:if> 
				         type="checkbox" value="1" ></td>
				        <td>密码有效时间设置：
				          <input id="pwdTime" name="securifyValue.pwdModifyPeriod" class="width140" value="${request.secureMap.pwd_modify_period.itemValue }"  type="text" ><label>天</label>
				         <span class="red" id="pwdTex"></span> </td>
			          </tr>
			          
			          
				      <tr>
				        <td height="30" align="center"><input name="securifyValue.checkeds"
				        		<c:if test="${request.secureMap.sms_checking_number_time.validFlag ne 2 }">checked="checked"</c:if>
				         type="checkbox" value="2" ></td>
				        <td>动态短信校验码验证:有效时间   <input id="smsTime" name="securifyValue.smsCheckingNumberTime" class="width100" 
				        									value="${request.secureMap.sms_checking_number_time.itemValue }" type="text" >  分钟
				        									<span class="red" id="smsTex"></span>
				        									</td>
			          </tr>
				      <tr>
				        <td height="30" align="center"><input name="securifyValue.checkeds"
				        		<c:if test="${request.secureMap.pwd_error_number_flag.validFlag ne 2 }">checked="checked"</c:if>
				         type="checkbox" value="3" ></td>
				        <td>登录错误次数限制：
				          <input id="logNum" name="securifyValue.pwdErrorNumberFlag" class="width100" value="${request.secureMap.pwd_error_number_flag.itemValue }" type="text" >
				          次，超过后锁定
				          <input id="lockNum" name="securifyValue.pwdLockTime" class="width60" value="${request.secureMap.pwd_lock_time.itemValue }" type="text" >
				          分钟&nbsp;&nbsp;<span id="lockTex" class="red"> 注：此&nbsp;规则“admin”帐号不限制</span></td>
			          </tr>
			          
				      <tr id="userIp">
				        <td height="30" align="center"><input name="securifyValue.checkeds" type="checkbox"
				        		<c:if test="${request.secureMap.ip_limit_lock.validFlag ne 2 }">checked="checked"</c:if>
				         value="4" ></td>
				        <td>用户账号绑定：
                        <select name="userSel"  id="sele">
                       	<option value="-1" selected="selected">请选择用户</option>
                        <c:forEach var="user" items="${request.users}"> 
                            <option value="${user.id }">${user.account }</option>
                        </c:forEach>
                        </select>
			            绑定到ip地址：<input id="ipaddress" name="securifyValue.user.ipAddress" class="width180" type="text" />
                         <input type="button" id="ipSub" value="添加"/><input id="delIp" type="button" value="删除"/> <span class="red" id="ipTex"></span></td>
			          </tr>
			          <tbody id="ipTbody">
			          	
			          </tbody>
			        </table>
                    <p class="margint10"><span class="red">注：以上设置需勾选前面的复选框后方可生效</span></p>
				    <div class="btnlist margint10 alignc"> <span id="sub" class="btn">保存</span><span id="res" class="btn">重置</span></div>
                  </div>
			  </div>
			</form>
	<script src="${ctx }/mas3/js/ui/util.js"></script> 
	<script type="text/javascript" src="${ctx }/themes/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/jquery.tools.min.js"></script>
	 <script type="text/javascript" defer="defer" src="${ctx }/themes/js/secureInstall/common.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/jquery.form.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/TabPanel.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/Fader.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/ui/util.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/main/deskUtil.js" ></script>
	<script type="text/javascript" src="${ctx }/themes/js/datepicker/WdatePicker.js" ></script>


<script type="text/javascript">
$(function() {
	var ipz=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	$("#sub").unbind("click").click(function(){
			
				var options = { 
		                beforeSubmit:function(formData, jqForm, options){
		                	if(ckech("#pwdTime","#pwdTex")&&ckech("#smsTime","#smsTex")
		            				&&ckech("#logNum","#lockTex")&&ckech("#lockNum","#lockTex")
		            				&&ckechIP("#ipaddress","#ipTex")) 
		                			return true;
		                	return false;			
		               				},  // pre-submit callback 
		                success:       function(responseText, statusText, xhr, $form){
		                	if(!isFirstNull(responseText))
		                				alert(responseText.message);
		                	else alert("返回结果不识别");
		                },  // post-submit callback 
		                dataType:	'json'
		            }; 
		            // 提交表单
		            $("#f1").ajaxSubmit(options); 

			 
	});
	$("#sele").bind("change",function (){
		if($(this).val()==-1){
			$(this).next().val("");
			return false;
		}
		 var url="${ctx}/securifyAdminAction/loadIp.action";
		 var data="securifyValue.user.id="+$(this).val();
		 var resource= ajax(url, data, false, 'post', 'json');
		 if(!isFirstNull(resource))
		 	$(this).next().val(resource.ipAddr);
	}); 
	$("#ipSub").unbind("click").bind("click",function(){
		if(getValue("#sele")==-1){
			alert('请选择一个用户');
			return false;
		}
		if(ckechIP("#ipaddress","#ipTex")){
			var url="${ctx}/securifyAdminAction/updateIp.action";
		 	var data="securifyValue.user.id="+$("#sele").val()+"&securifyValue.user.ipAddress="+$("#ipaddress").val();
		 	var resource= ajax(url, data, false, 'post', 'json');
		 	if(!isFirstNull(resource))
		 	alert(resource.message);
		 else
			alert("IP删除失败");
		}
	});
	$("#delIp").unbind("click").bind("click",function(){
		if(getValue("#sele")==-1){
			alert('请选择一个用户');
			return false;
		}
		var url="${ctx}/securifyAdminAction/updateIp.action";
		var data="securifyValue.user.id="+$("#sele").val()+"&securifyValue.user.ipAddress=";
		var resource= ajax(url, data, false, 'post', 'json');
		if(!isFirstNull(resource)){
			 $("#ipaddress").val("");
		 	alert("IP删除成功");
		 }else
			 alert("IP删除失败");
	});
	function ckech(idStr,teStr){
		var moben=/^[0-9]+$/;
		var na=getValue(idStr);
		if(isFirstNull(na)){
			$(teStr).html("*不能为空!");
			return false;
		}  
		if(!isReg(moben,na)){
			 $(teStr).html("*只能是数字!");
			return false;
		}
		$(teStr).html("");
		return true;
	}
	function ckechIP(idStr,teStr){
		if(!isFirstNull(getValue(idStr)))
		if(!isReg(ipz,getValue(idStr))){
			 $(teStr).html("*ip输入不正确!格式应为xxx.xxx.xxx.xxx,'x'只能是数字组成");
			return false;
		}
		$(teStr).html("");
		return true;
	}
	
	$("#res").unbind("click").click(function(){
		//$("#f1").reset();
		 var bool=confirm("确定恢复默认值!");
			if(bool){
				//密码有效期默认30天
				$("#pwdTime").val(30);
				//短信有效期
				$("#smsTime").val("");
				//密码错误次数默认为3分钟
				$("#logNum").val(3);
				//密码锁定时间默认为3分钟
				$("#lockNum").val(3);
				//ip地址
				$("#ipaddress").val("");
				$('#sele option:first').attr('selected','selected');
			} 	
	});
  }); 
</script>
</body>
</html> 