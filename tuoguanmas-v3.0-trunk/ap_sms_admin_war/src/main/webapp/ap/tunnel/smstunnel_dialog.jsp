<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    //Integer proName = (Integer)request.getParameter("proName")==null?0:(Integer)request.getParameter("proName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信通道</title>
<script type="text/javascript">
function tunnelValidate (formData, jqForm, options){
	//ValidateForm.validate("#inputid","#outId",[1,2],["required","validateString"]);
	return validateUtil.validateForm();
}
function showTunnelResponse(data, statusText){
	var resultObj = data;
		alert(resultObj.message);
        if(resultObj.resultcode == "success"){
			smsTunnelGrid.refresh();
			$('#tunnelPro').dialog('close');
		}
}
var validateUtil;
$(function() {
	validateUtil = new ValidateForm("#tunnelProForm");
	validateUtil.init();
	$('#tunnelPro').dialog({
	    title: '短信通道属性',  
	    autoOpen: true,
	    width: 640,  
	    height: 420, 
	    modal: true,
	    resizable: false,
	    close:function(){
	    	$('#tunnelPro').dialog( "destroy" );
	    	$('#tunnelPro').remove();
	    },
	    buttons:[{
			text:'保存',
			click:function(){
				var options = { 
					url: form_url,
					dataType:	'json',
			        beforeSubmit:  tunnelValidate,  // pre-submit callback 
			        success: showTunnelResponse  // post-submit callback 
			    };
				$('#tunnelProForm').ajaxSubmit(options); 
			}
		},{
			text:'关闭',
			click:function(){
				$('#tunnelPro').dialog('close');
			}
		}]
	});
});
</script>
</head>
<body>
<div id="tunnelPro" >
<form id="tunnelProForm" method="post">
	<div class="config">
		<div class="config_left">
			<ul class="ms_config">
		        <li><span class="name"><font color="red">*</font>通道名称：</span><span class="txt">
		        	<c:if test="${proName == 1}">
		        	<input id="pro_name" readonly="readonly" class="leadui-validatebox" name="smsMbnTunnelVO.name" required="true" validType="validateString" validParam="通道名称：" type="text"  />
		        	</c:if>
		        	<c:if test="${proName == 0}">
		        	<input id="pro_name" class="leadui-validatebox" name="smsMbnTunnelVO.name" required="true" validType="validateString" validParam="通道名称：" type="text"  />
		        	</c:if>
		        </span></li>
		        <li><span class="name">通道状态：</span><span class="txt">
		            <select id="pro_state" name="smsMbnTunnelVO.state">
						<option selected="selected" value="1">可用</option>
						<option value="0">关闭</option>
					</select>
		        </span></li>
		        <li><span class="name"><font color="red">*</font>通道类型：</span><span class="txt">
		            <select id="pro_classify" name="smsMbnTunnelVO.classify" class="leadui-validatebox" required="true" validParam="通道类型：">
						<option value="1">本省移动</option>
						<option selected="selected" value="2">移动</option>
						<option value="3">本省联通</option>
						<option value="4">联通</option>
						<option value="5">本省电信</option>
						<option value="6">电信</option>
						<option value="7">全网</option>
					</select>
		        </span></li>
		        <li><span class="name"><font color="red">*</font>省份：</span><span class="txt">
		            <select id="pro_province" name="smsMbnTunnelVO.province" class="leadui-validatebox" required="true" validParam="省份：">
						<c:forEach items="${provinceList}" var="province" varStatus="status">
							<option value="${province.coding}" <c:if test="${status.index == 0}">selected="selected"</c:if> >${province.name}</option>
						</c:forEach>
					</select>
		        </span></li>
		    </ul>
		</div>
		<div  class="config_right">
		    <ul class="ms_config">
		        <li><span class="name"><font color="red">*</font>特服号：</span><span class="txt">
		            <input id="pro_accessNumber" name="smsMbnTunnelVO.accessNumber" class="leadui-validatebox" required="true" validType="validateIntegerLen" validParam="特服号:,6" type="text"  />
		        </span></li>
		        <li><span class="name"><font color="red">*</font>扩展位数：</span><span class="txt">
		            <input id="pro_corpExtLen" name="smsMbnTunnelVO.corpExtLen" class="leadui-validatebox" required="true" validType="validateIntegerLen" validParam="扩展位数：,2" type="text" />
		        </span></li>
		        <li><span class="name">通道属性：</span><span class="txt">
		            <select id="pro_attribute" name="smsMbnTunnelVO.attribute">
						<option selected="selected" value="2">第三方通道</option>
						<option value="1">直连通道</option>
					</select>
		        </span></li>
		        <li><span class="name">通道说明：</span><span class="txt">
		            <textarea id="pro_description" name="smsMbnTunnelVO.description" cols="13" rows="3"></textarea>
		        </span></li>
		    </ul>
		</div>
	</div>
	<div class="config">
		<fieldset>
		    <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="lname"><font color="red">*</font>短信网关地址：</span><span class="txt">
	              <input id="pro_gatewayAddr" name="smsMbnTunnelVO.gatewayAddr" class="leadui-validatebox" required="true" validType="validateIPAddress" validParam="短信网关地址：" type="text"  />
	            </span></li>
	            <li><span class="lname"><font color="red">*</font>短信网关发送URL：</span><span class="txt">
	              <input id="pro_smsSendPath" name="smsMbnTunnelVO.smsSendPath" class="leadui-validatebox" required="true" validType="validateURLAddress" validParam="短信网关发送URL：" type="text"  />
	            </span></li>
	            <li><span class="lname">短信网关接收URL：</span><span class="txt">
	              <input id="pro_smsReceivePath" name="smsMbnTunnelVO.smsReceivePath"  class="leadui-validatebox" validType="validateURLAddress" validParam="短信网关接收URL：" type="text"  />
	            </span></li>
	            <li><span class="lname">短信网关状态报告URL：</span><span class="txt">
	              <input id="pro_smsReportPath" name="smsMbnTunnelVO.smsReportPath"  class="leadui-validatebox" validType="validateURLAddress" validParam="短信网关状态报告URL：" type="text"  />
	            </span></li>
	          </ul>
	        </div>
	        <div class="group_right">
	          <ul class="ms_config">
	            <li><span class="rname"><font color="red">*</font>短信网关端口：</span><span class="txt">
	              <input id="pro_gatewayPort" name="smsMbnTunnelVO.gatewayPort" class="leadui-validatebox" required="true" validType="validateIntegerLen" validParam="短信网关端口:,4" type="text"  />
	            </span></li>
	            <li><span class="rname"><font color="red">*</font>短信网关用户名：</span><span class="txt">
	              <input id="pro_user" name="smsMbnTunnelVO.user" class="leadui-validatebox" required="true" validType="validateString" validParam="短信网关用户名：" type="text"  />
	            </span></li>
	            <li><span class="rname"><font color="red">*</font>短信网关密码：</span><span class="txt">
	              <input id="pro_passwd" name="smsMbnTunnelVO.passwd" class="leadui-validatebox" required="true" validType="validateString" validParam="短信网关密码：" type="text"  />
	            </span></li>
	          </ul>
	        </div>
	        <input id="pro_id" name="smsMbnTunnelVO.id" type="hidden"  />
	  	</fieldset>
	</div>
</form>
</div>
</body>
</html>

