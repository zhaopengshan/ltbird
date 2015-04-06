<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String tunnelFlowId = request.getParameter("tunnelFlowId")==null?"":(String)request.getParameter("tunnelFlowId");
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
        	$("#smsNumber").text(resultObj.balanceNumber);
			smsTunnelFlowGrid.refresh();
			$('#rechargeDialog').dialog('close');
		}
}
var validateUtil;
$(function() {
	validateUtil = new ValidateForm("#rechargeForm");
	validateUtil.init();
	$('#rechargeDialog').dialog({
	    title: '短信通道充值', 
	    autoOpen: true, 
	    width: 480,  
	    height: 240, 
	    modal: true,
	    close:function(){
	    	$('#rechargeDialog').dialog( "destroy" );
	    	$('#rechargeDialog').remove();
	    },
	    buttons:[{
			text:'确定',
			click:function(){
				var options = { 
					url: "${ctx}/smsTunnelAccountFlowAction/addTunnelAccountFlow.action",
					dataType:	'json',
			        beforeSubmit:  tunnelValidate,  // pre-submit callback 
			        success: showTunnelResponse  // post-submit callback 
			    };
				$('#rechargeForm').ajaxSubmit(options); 
			}
		},{
			text:'关闭',
			click:function(){
				$('#rechargeDialog').dialog('close');
			}
		}]
	});
});
</script>
</head>
<body>
<div id="rechargeDialog">
<form id="rechargeForm" method="post">
<div class="config">
	<div class="recharge_dialog">
		<ul class="ms_config">
			<li><span class="name">操作类型：</span><span class="txt">
				<select name="smsMbnTunnelAccountFlow.operationType">
					<option selected="selected" value="1">增加(向代理商购买)</option>
					<option value="2">增加(代理商赠送)</option>
					<option value="3">减少(代理商退款)</option>
				</select>
			</span></li>
			<li><span class="name">操作数量：</span><span class="txt">
				<input id="sms_flow_number" name="smsMbnTunnelAccountFlow.number" class="leadui-validatebox" required="true" validType="validateIntegerLen" validParam="操作数量:,11" type="text"  />
			</span><span class="">条   (注：输入大于等于0的整数)</span></li>
			<li><span class="name">相关金额：</span><span class="txt">
				<input id="sms_flow_money" name="smsMbnTunnelAccountFlow.amount" class="leadui-validatebox" required="true" validType="validatePositiveFloat" validParam="相关金额:" type="text"  />
			</span><span class="">元   (注：只填金额数，不填正负)</span></li>
		</ul>
	</div>
	<input name="smsMbnTunnelAccountFlow.tunnelId" value="<%= tunnelFlowId%>" type="hidden" />
</div>
</form>
</div>
</body>
</html>

