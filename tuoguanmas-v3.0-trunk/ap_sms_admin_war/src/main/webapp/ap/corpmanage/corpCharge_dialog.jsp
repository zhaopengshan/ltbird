<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
var tunnelName,validateUtil;
$(function() {
	var dialogOptionsEditMerchant = {
        /**************新增或修改弹出层相关参数***********************/
        resizable: false,
        width: 600,  
        height: 400,  
        modal: true,
        autoOpen: true,
        title: "系统管理员为企业通道充值",
        close: function(){
        	 $(this).dialog("destroy");
        	 $("#operateTunnelByPin").remove();
        },
        buttons:{
            "确定": function(){
            	if(validate()){
    				var msg=$("#tunnelChargeFrom").serialize();
    				$.ajax({
    					type: "POST",
    					data: msg,
    					url: "./corpManageAction/updateMerchantAndTunnel.action",
    	                dataType:  "json",
    					success: function(data){
    						getPageDataEditMerchant();
    						var entityMap=data;
    						if(entityMap.flag){
    							//$("#msgRightSpan").html(entityMap.resultMsg);
    							//$("#msgRight").show();
    							alert(entityMap.resultMsg);
    							$("#operateTunnelByPin").dialog("close");
    						}else{
    							//$("#msgWrongSpan").html(entityMap.resultMsg);
    							//$("#msgWrong").show();
    							alert(entityMap.resultMsg);
    						}
    					}
    				});
    			}
            },
            "关闭": function(){
                $("#tunnelChargeFrom").resetForm();
                $(this).dialog("close");
            }
        }
    };
	$("#operateTunnelByPin").dialog(dialogOptionsEditMerchant);
});
</script>
</head>
<body>
<div id="operateTunnelByPin" class="config" style="display:none">
	<form id="tunnelChargeFrom"> 
	<ul>
		<li><input type="hidden" id="editMerchant_merchantPin" name="mbnMerchantTunnelRelation.merchantPin"/></li>
		<li><input type="hidden" id="editMerchant_tunnelId" name="mbnMerchantTunnelRelation.tunnelId"/></li>
		<li>
			<select id="editMerchant_tunnelType" name="mbnMerchantConsumeFlow.operationType">
				<option value="1">增加（购买）</option>
				<option value="2">增加（赠送）</option>
				<option value="3">减少（撤销购买）</option>
				<option value="4">减少（撤销赠送）</option>
			</select>
		</li>
		<li>
			<label>
				<span>操作数量</span>
				<input id="editMerchant_tunnelNumber" type="text" name="mbnMerchantConsumeFlow.number"/>
				<span>条（注：输入大于等于0的整数）</span>
				<span id="editMerchant_tunnelNumberMsg" class="needtip"></span>
			</label>
		</li>
		<li>
			<label>
				<span>相关金额</span>
				<input id="editMerchant_tunnelPrice" type="text" name="chargePrice"/>
				<span>元（注：只填金额数，不填正负）</span>
				<span id="editMerchant_tunnelPriceMsg" class="needtip"></span>
			</label>
		</li>
	</ul>
	</form>
</div>
</body>
</html>

