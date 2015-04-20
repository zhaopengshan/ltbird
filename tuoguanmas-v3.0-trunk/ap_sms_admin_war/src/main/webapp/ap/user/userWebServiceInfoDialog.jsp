<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.leadtone.mas.bizplug.util.WebUtils"%>   
<script type="text/javascript">
 var dialogOptionsCreateGroup = {
    /**************新增或修改弹出层相关参数***********************/
    //height: 140,
    resizable: false,
    width: 530,  
    height: 250,  
    modal: true,
    autoOpen: true,
    title: "WebService参数",
    close: function(){
    	$(this).dialog("destroy");
       	$("#webserviceInfoDiv").remove();
    },
    buttons:{
        "取消": function(){
            $("#webserviceInfoDiv").resetForm();
            $(this).dialog("close");
        }
    }
};
$(function() {
	$("#webserviceInfoDiv").dialog(dialogOptionsCreateGroup);
});
</script>

<div id="webserviceInfoDiv" class="config" style="display:none;">
	<table  width="500px;" border="0" align="center" cellpadding="0" cellspacing="10" class="Table_01" style=" background-color:#FCFCFC; margin-top: 20px;">
      <tr>
            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">接口地址：</span>
            <input  type="text" class="text" readonly="readonly" style="width:350px;" value="<%=WebUtils.getPropertyByName("webserviceConnect")%>">
            <span id="mobileResult"></span>
           </td>
      </tr>
      <tr>
            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">PINID：</span>
            <input  type="text" class="text" readonly="readonly" style="width:350px;" value="${userInfo.merchantPin }">
            <span id="mobileResult"></span>
           </td>
      </tr>
      <tr>
            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">AccountId：</span>
            <input  type="text" class="text" readonly="readonly"  style="width:350px;" value="${userInfo.account }">
            <span id="mobileResult"></span>
           </td>
      </tr>
      <tr>
            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">AccountPwd：</span>
            <input  type="text" class="text" readonly="readonly" style="width:350px;" value="${userInfo.password }">
            <span id="mobileResult"></span>
           </td>
      </tr>
      <tr>
            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">key：</span>
            <input  type="text" class="text" readonly="readonly" style="width:350px;" value="${userInfo.id }">
            <span id="mobileResult"></span>
           </td>
      </tr>
    </table>
</div>


