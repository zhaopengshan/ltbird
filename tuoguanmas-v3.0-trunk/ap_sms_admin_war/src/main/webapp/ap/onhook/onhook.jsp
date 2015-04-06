<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
		<script language="javascript" type="text/javascript">
			$(function() {
				$("#huifua").unbind("click").click(function(){
					fetchSettingContext("call_onhook_normal_sms", "#onhooktxt");
				});
				$("#huifub").unbind("click").click(function(){
					fetchSettingContext("call_busy_sms", "#busylinetxt");
				});
				$("#huifuc").unbind("click").click(function(){
					fetchSettingContext("call_timeout_sms", "#delayedtxt");
				});
				$("#huifud").unbind("click").click(function(){
					fetchSettingContext("call_other_sms", "#othertxt");
				});
				
				$("#onHookSave").unbind("click").bind("click", function(){
		        	var options = {
		      		        beforeSubmit:  checkOnHookSetting,  // pre-submit callback 
		      		        success:       afterOnHookSetting,  // post-submit callback 
		      		        dataType:		'json'
		      		}; 
		        	$("#onHookSetting").ajaxSubmit(options); 
		        });
		        
		        $("#onHookCancel").unbind().bind("click",function(){
		        	$("#onHookSetting").resetForm();
		        });
			});
			// 检查输入
			function checkOnHookSetting(){
				return true;
			}
		    //提交表单成功后处理方法
		  	function afterOnHookSetting(responseText, statusText, xhr, $form){
				if(responseText.flag){
					alert(responseText.resultMsg);
				}else{
					alert(responseText.resultMsg);
				}
			}
			function fetchSettingContext(key, ids){
				$.ajax({
                    url: "./onHookSmsSettingAction/fetchSettingContext.action",
                    type:"POST",
                    dataType: 'json',
                    data:{"onHookKey": key},
                    success:function(data){
                    	if(data.flag){
                    		$(ids).val(data.onHookContent);
                    	}else{
                    		alert("恢复内容失败，请重试！");
                    	}
                    }
                });
			}
		</script>
    </head>
    <body>
    <div class="left_contents">
    	<form  id="onHookSetting" action="./onHookSmsSettingAction/onHookSetting.action" method="post" >
		<div class="onhookbox">
			<div class="ohleftbox">是否启用挂机短信功能：</div>
			<div class="ohrightbox">
				<input id="yeshook" type="radio" <s:if test="onHookStatus == 1">checked="checked"</s:if> name="onHookStatus" value="1">是 &nbsp;&nbsp;
				<input id="nohook" type="radio" <s:if test="onHookStatus == 0">checked="checked"</s:if> name="onHookStatus" value="0">否
			</div>			
		</div>
		<div class="onhookbox" id="">
			<div class="ohleftbox">正常挂机：</div>
			<div class="ohrightbox">
				<textarea id="onhooktxt" class="rtextarea" name="onHookNormalContent">${onHookNormalContent}</textarea>
			</div>	
			<div class="ohbtn tubh">
				<a href="javascript:void(0);" id="huifua">恢复内容</a>
 			</div>
		</div>		
		<div class="onhookbox" id="">
			<div class="ohleftbox">通话占线：</div>
			<div class="ohrightbox">
				<textarea id="busylinetxt" class="rtextarea" name="onHookBusyContent">${onHookBusyContent}</textarea>
			</div>	
			<div class="ohbtn tubh">
				<a href="javascript:void(0);" id="huifub">恢复内容</a>
 			</div>
		</div>
		<div class="onhookbox" id="">
			<div class="ohleftbox">呼叫超时：</div>
			<div class="ohrightbox">
				<textarea id="delayedtxt" class="rtextarea" name="onHookTimeoutContent">${onHookTimeoutContent}</textarea>
			</div>	
			<div class="ohbtn tubh">
				<a href="javascript:void(0);" id="huifuc">恢复内容</a>
 			</div>
		</div>
		<div class="onhookbox" id="">
			<div class="ohleftbox">其它：</div>
			<div class="ohrightbox">
				<textarea id="othertxt" class="rtextarea" name="onHookOtherContent">${onHookOtherContent}</textarea>
			</div>	
			<div class="ohbtn tubh">
				<a href="javascript:void(0);" id="huifud">恢复内容</a>
 			</div>
		</div>
		<div class="tableopts" style="margin-left:0px;">
			<span class="tubh"><a href="javascript:void(0);" id="onHookSave">保存更改</a>&nbsp;&nbsp;</span>
			<span class="tubh"><a href="javascript:void(0);" id="onHookCancel">取消</a>&nbsp;&nbsp;</span>
		</div>
		</form>
	</div>
	</body>
	</html>   
      
       
       

    
