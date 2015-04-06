<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
 var dialogOptionsCreateGroup = {
    /**************新增或修改弹出层相关参数***********************/
    //height: 140,
    resizable: false,
    width: 530,  
    height: 250,  
    modal: true,
    autoOpen: true,
    title: "短信验证码设置",
    close: function(){
    	clearInterval(tttimerSmsCheck);
    	$(this).dialog("destroy");
       	$("#smsCheckSettingDiv").remove();
    },
    buttons:{
    	"绑定": function(){
			$.ajax({
				type: "POST",
				data: {"smsCheckContent":$("#smsCheckContent").val(), 
					"userPsw":$("#userPsw").val(),
					"portalUserExt.id":$("#userSettingPsw").val(),
					"portalUserExt.smsMobile":$("#receiveMobile").val(),
					"portalUser.merchantPin":$("#userSettingMPin").val()
				},
				beforeSend:function(){
					if( !checkMobile("#receiveMobile", "#mobileResult", false)
						||!checkEmpty("#smsCheckContent","#smsCheckContentResult",false) 
						||!checkEmpty("#userPsw","#userPswResult",false) ){
						return false;
					}
				},
				url: "./userAction/smsCheckSetting.action",
                dataType:  "json",
				success: function(data){
					if(data.flag){
						alert(data.resultMsg);
						$("#smsCheckSettingDiv").dialog("close");
					}else{
						alert(data.resultMsg);
					}
				}
			});
        },
	    "解绑": function(){
				$.ajax({
					type: "POST",
					data: {"smsCheckContent":$("#smsCheckContent").val(), 
						"userPsw":$("#userPsw").val(),
						"portalUserExt.id":$("#userSettingPsw").val(),
						"portalUserExt.smsMobile":$("#receiveMobile").val(),
						"portalUser.merchantPin":$("#userSettingMPin").val()
					},
					beforeSend:function(){
						if( !checkMobile("#receiveMobile", "#mobileResult", false)
							||!checkEmpty("#smsCheckContent","#smsCheckContentResult",false) 
							||!checkEmpty("#userPsw","#userPswResult",false) ){
							return false;
						}
					},
					url: "./userAction/smsCancelSetting.action",
	                dataType:  "json",
					success: function(data){
						if(data.flag){
							alert(data.resultMsg);
							$("#smsCheckSettingDiv").dialog("close");
						}else{
							alert(data.resultMsg);
						}
					}
				});
	    },
        "取消": function(){
            $("#smsCheckSettingDiv").resetForm();
            $(this).dialog("close");
        }
    }
};
function CountDownSmsCheck() {
    if(maxtimeSmsCheck>=0) {
    	$("#subSmsCheck").val("重发("+maxtimeSmsCheck+"s)");
        --maxtimeSmsCheck;  
        //console.log("reduce--"+maxtimeSmsCheck);
    } else {
    	maxtimeSmsCheck = 2*60;
    	clearInterval(tttimerSmsCheck);
    	$("#subSmsCheck").val("获取验证码").attr("disabled", false).unbind("click").bind("click", submitCallSmsCheck);
    	//console.log("reset -- Interval clear"+maxtimeSmsCheck);
    }  
}
var maxtimeSmsCheck = 2*60, tttimerSmsCheck;
function submitCallSmsCheck (){
	if(!checkMobile("#receiveMobile", "#mobileResult", true)){
		return;
	}
	$("#subSmsCheck").attr("disabled", true).unbind("click");
	tttimerSmsCheck = setInterval("CountDownSmsCheck()",1000);
	
	$.ajax({
		url: "./smssend/smsCheckSettingGet.action",
		data : {"receiver": $("#receiveMobile").val(),"portalUserExt.id":$("#userSettingPsw").val()},
		dataType : "json",
		type : "post",
		success : function(dat) {
			if (dat.resultcode==="success") {
				alert(dat.message);
			}else{
				alert(dat.message);
			}
		},
		error : function(dat) {alert("验证码发送失败!");}
	});

}
$(function() {
	$("#smsCheckSettingDiv").dialog(dialogOptionsCreateGroup);
	$("#subSmsCheck").val("获取验证码").attr("disabled", false).unbind("click").bind("click", submitCallSmsCheck);
	$("#receiveMobile").focusout(function() {
        checkMobile("#receiveMobile", "#mobileResult", false);
    });
});
</script>

<div id="smsCheckSettingDiv" class="config" style="display:none;">
	<form  id="smsCheckSettingForm">
		<table  width="500px;" border="0" align="center" cellpadding="0" cellspacing="10" class="Table_01" style=" background-color:#FCFCFC; margin-top: 20px;">
	      <tr>
	            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">接收号码：</span>
	            <input  type="text" id="receiveMobile" name="portalUserExt.smsMobile" class="text" value="${portalUserExt.smsMobile }">
	            <span id="mobileResult"></span>
	           </td>
	      </tr>
	      <tr>
	            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">短信验证码：</span>
	            <input  type="text" id="smsCheckContent" name="smsCheckContent" class="text">
	            <input style="width:100px; height:30px; margin:0 15px 0 0;" type="button" disabled="disabled" id="subSmsCheck" value="获取验证码"></input>
	            <span id="smsCheckContentResult"></span>
	            </td>
	      </tr>
	      <tr id="smsCheckTip">
	            <td style=" color:#C6C6C6; font-size:12px;">(提示：点击获取验证码后，如果在120s内未收到短信验证码，请重新点击获取验证码按钮。)</td>
	      </tr>
	      <tr>
	            <td style="font-weight:bold;"><span style="color:red;">*</span><span style="width:100px; display: inline-block;">管理员密码：</span>
	            <input  type="password" id="userPsw" name="userPsw" class="text">
	           	<span id="userPswResult"></span>
	           	<input  type="hidden" id="userSettingPsw" name="portalUserExt.id" class="text" value="${portalUserExt.id }">
	           	<input  type="hidden" id="userSettingMPin" name="portalUser.merchantPin" class="text" value="${portalUser.merchantPin }">
	           	</td>
	      </tr>
	    </table>
	</form>
</div>


