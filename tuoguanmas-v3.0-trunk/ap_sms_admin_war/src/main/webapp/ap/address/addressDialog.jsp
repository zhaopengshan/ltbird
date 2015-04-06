<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信通道</title>
<script type="text/javascript">
/**
 * 新增或修改时验证表单数据
 *
 */
function validateForm(formData, jqForm, options){
    if(!checkContactMobile("#addresslist_mobile", "#contactResult", true, "#group" ) ||
        (!checkName("#addresslist_name", "#contactResult", true))||
        (!checkEmail("#addresslist_email", "#contactResult", true)) ||
        (!isCardID("#identificationNumber", "#contactResult", true)) ||
        (!checkQQ("#qqNumber", "#contactResult", true))){
        return false;
    }
}
function afterSubmit(responseText, statusText, xhr, $form)  { 
		
    if(responseText.flag == "success"){
        //if("" == $("cId").val()){
        //	alert("保存联系人信息成功！");
        //}else{
	
        //}
        $("#contactResult").removeAttr("class");	// 清除此次验证产生的样式
        $("#contactResult").html("");	// 清除此次验证的结果信息
        alert("保存联系人信息成功！");
        $("#addForm").resetForm();
        $("#addContactDiv" ).dialog("close");
        var groupContacts = responseText.countGroupContacts;
        $("#countGroupContacts").html("");
        for(var j in groupContacts){
            $("#countGroupContacts").append("<li class='group_icon' onclick=\"queryByGroup('"
                +groupContacts[j].bookGroupId+"')\">"
                +groupContacts[j].groupName+"("+groupContacts[j].counts+")</li>");
        }

        getAddressPageData();
    }else {
        alert(responseText.message);
    }
}
var dialogOptions = {
    /**************新增或修改弹出层相关参数***********************/
    //height: 140,
    resizable: false,
    width: 600,  
    height: 400,  
    modal: true,
    autoOpen: true,
    title: "联系人详细信息",
    close: function(){
    	$(this).dialog("destroy");
       	$("#addContactDiv").remove();
    },
    buttons:{
        "保存": function(){
            var options = { 
                beforeSubmit:  validateForm,  // pre-submit callback 
                success:       afterSubmit,  // post-submit callback 
                dataType:	'json'
            }; 
            // 提交表单
            $("#addForm").ajaxSubmit(options); 
        },
        "关闭": function(){
            $("#addForm").resetForm();
            $("#contactResult").removeAttr("class").html("");
            $(this).dialog("close");
        }
    }
};
$(function() {
	$("#addContactDiv").dialog(dialogOptions);
	$("#merchantBlackFlag").unbind("click").bind("click",function(){
		if($('#merchantBlackFlag').is(":checked")){
			$('#merchantBlackFlag').val("1");
		}else{
			$('#merchantBlackFlag').val("0");
		}
	});
});
</script>
</head>
<body>
<div id="addContactDiv" class="config" style="display:none; margin: 0px; padding:0px;">
    <!-- 新增联系人 弹出层   开始-->
    <form method="post" id="addForm" action="">
        <!--<div class="config_left">-->
        <!-- class="table_box" -->
        <span id="contactResult"></span>
        <ul>
            <li>
            	<label  class="lname">
                    <span  class="needtip">*</span>
                    <span>姓名：</span>
                </label>
                <input type="text" name="contact.name" id="addresslist_name"/>
                <label class="rname">
                    <span class="needtip">*</span>
                    <span>手机号码：</span>
                </label>
                <input maxlength="11" type="text" name="contact.mobile" id="addresslist_mobile" />
            </li>
            <li>
                <label  class="lname">
                    <span>性别：</span>
                </label>
                <label class="extraforalign">
                    <input name="contact.gender" type="radio" value="1" checked>男
                    <input  name="contact.gender" type="radio" value="0">女
                </label>
                <label class="rname">
                    <span>身份证号：</span>
                </label>
                <input maxlength="18" size="25" type="text" name="contact.identificationNumber"  id="identificationNumber"/>
            </li>
            <li >
                <label class="lname">
                    <span>生日：</span>
                </label>
                <input class="Wdate" type="text"  name="contact.birthday" readonly="readonly"
                       onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="birthday" />
                <label class="rname">
                    <span class="needtip">*</span>
                    <span>分组： </span>
                </label>
                <select id="group" name="contact.bookGroupId" style="width: 130px">
                	<s:if  test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('privategroupopen')=='true' && #session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_ENTERPRISE_ADMIN">
                		<option value="-1">未分组</option>
                	</s:if>
                    <s:iterator id="group" value="#request.groupsList">
                        <option value="${group.id }">${group.groupName}</option>
                    </s:iterator>
                </select>
            </li>
            <li>
            	<label class="lname">
                    <span>单位：</span>
                </label>
                <input type="text" name="contact.company"  id="company" />
                <label class="rname">
                    <span>集团短号：</span>
                </label>
                <input type="text" name="contact.vpmn" id="vpmn" />
            </li>
            <li>
                <label class="lname">
                    <span>地址：</span>
                </label>
                <input type="text" name="contact.address" id="address"  />
                <label class="rname">
                    <span>座机号码：</span>
                </label>
                <input type="text" name="contact.telephone" id="telephone" />
            </li>
            <li>
                <label class="lname">
                    <span>MSN：</span>
                </label>
                <input type="text" name="contact.msn"  id="msn" />
                <label class="rname">
                    <span>QQ：</span>
                </label>
                <input type="text" name="contact.qqNumber" id="qqNumber"  />
            </li>
            <li>
            	<label class="lname">
                    <span>微博：</span>
                </label>
                <input type="text" name="contact.microBlog" id="microBlog"  />
                <label class="rname">
                    <span>电子邮件：</span>
                </label>
                <input type="text" name="contact.email" id="addresslist_email"  />
            </li>
            <li>
                <label class="lname">
                    <span>备注：</span>
                </label>
                <input type="text" name="contact.description" size="30" id="addresslist_description" />
            </li>
            <li>
                <label class="rname">
                    <input type="checkbox" name="contact.merchantBlackFlag" id="merchantBlackFlag" value="0">
                </label>
                <span>暂停发送短信（选中后将暂停向此联系人发送短信）</span>
            </li>
        </ul>       
        <input type="hidden" name="contact.id" id="cId"/>
        <input type="hidden" name="contact.createBy" id="addressCreateBy"/>         
    </form>
    <!-- 新增联系人 弹出层  结束-->
</div>
</body>
</html>

