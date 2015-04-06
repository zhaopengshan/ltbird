<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
  /**去除jquery ui close按钮**/
  .my-dialog .ui-dialog-titlebar-close{
    display: none;
  }
</style> 
<title>短信通道</title>
<script type="text/javascript">

var groupFlag=0;

$(document).ready(function(){
	groupFlag=0;
	$("#createGroupDiv").hide();
    $("#selectGroupDiv").hide();
})
 var dialogOptionsCreateGroup = {
    /**************新增或修改弹出层相关参数***********************/
    //height: 140,
    resizable: false,
    width: 500,  
    modal: true,
    autoOpen: true,
    title: "提示：",
    dialogClass: "my-dialog",
    close: function(){
    	$(this).dialog("destroy");
       	$("#chooseGroupDiv").remove();
    },
    buttons:{
        "确定": function(){
        	if(groupFlag==1){
        		var name=$.trim($("#groupCreateName").val());
				if(name==""||name==null){
					alert("通讯录组名不能为空");
					return false;
				}
            	$("#setGroupName").attr("value",$("#groupCreateName").val());
            	$("#chooseGroupDiv").dialog("close");
        	}else if(groupFlag==2){
            	$("#setGroupId").attr("value",$('#groupList option:selected').val());
            	$("#chooseGroupDiv").dialog("close");
        	}
        }/* ,
        "关闭": function(){
            $("#createGroupDiv").resetForm();
            $(this).dialog("close");
        } */
    }
};
$(function() {
    $.ajax({
        url: "./address/getGroupDate.action",
        type:"POST",
        dataType: 'json',
        success:function(data){
    		var groupList = $("#groupList");
    		groupList.empty();
            jQuery.each(data.groupsList, function(i,item){
            	var option = $("<option>").text(item.groupName).val(item.id)
            	groupList.append(option);
            });
        },
        error:function(data){
        }
    });
	$("#chooseGroupDiv").dialog(dialogOptionsCreateGroup);
});

function showNewGroup(){
	groupFlag=1;
	$("#setGroupId").attr("value","");
	$("#setGroupName").attr("value","");
    $("#selectGroupDiv").hide();
    $("#createGroupDiv").show();
}
function showSelectGroup(){
	groupFlag=2;
	$("#setGroupId").attr("value","");
	$("#setGroupName").attr("value","");
    $("#createGroupDiv").hide();
    $("#selectGroupDiv").show();

}
</script>
</head>
<body>
<div id="chooseGroupDiv">
导入文件中包含有未分组联系人，<span style="color: red;">必须</span>为此联系人<a href="javascript:void(0);" onclick="showSelectGroup()" style="color: blue;size: 12px;">选择分组</a>，或<a href="javascript:void(0);" onclick="showNewGroup()" style="color: blue;size: 12px;">新建分组</a>
    <div id="selectGroupDiv" class="config" align="center" style="padding-top: 10px">
               <span class="needtip">*</span>选择分组： <select id="groupList" name="bookGroupId" style="width: 130px">
         </select>
    </div>
    <div id="createGroupDiv" class="config" align="center" style="padding-top: 10px">
               <span class="needtip">*</span>分组名称：<input id="groupCreateName" type="text" name="group.groupName" />
    </div>
</div>
</body>
</html>

