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
var dialogOptionsEditGroup = {
     /**************新增或修改弹出层相关参数***********************/
     //height: 140,
     resizable: false,
     width: 600,  
     height: 400,  
     modal: true,
     autoOpen: true,
     title: "修改通讯录组",
     close: function(){
    	$(this).dialog("destroy");
       	$("#editGroupDiv").remove();
     },
     buttons:{
         "确定": function(){
            	var msg=$("#updateGroupForm").serialize();
 			$.ajax({
				type: "POST",
				data: msg,
				beforeSend:function(){
					var name=$.trim($("#groupEditName").val());
					if(name==""||name==null){
						alert("通讯录组名不能为空");
						return false;
					}
				},
				url: updateGroupUrl,
                dataType:  "json",
				success: function(data){
					getPageDataShowGroup();
					if(data.flag){
						alert(data.resultMsg);
						$("#editGroupDiv").dialog("close");
					}else{
						alert(data.resultMsg);
					}
				}
			});
         }/* ,
         "关闭": function(){
             $("#editGroupDiv").resetForm();
             $(this).dialog("close");
         } */
     }
 };
$(function() {
	$("#editGroupDiv").dialog(dialogOptionsEditGroup);
});
</script>
</head>
<body>
<div id="editGroupDiv" class="config" style="display:none;">
	<form  id="updateGroupForm">
		<ul>
			<li>
				<label class="rname">
					<span class="needtip">*</span>
					<span>分组名称</span>
				</label>
				<input id="groupEditName" type="text" name="group.groupName" />
				<input id="groupEditId" type="hidden" name="group.id" value="-1"/>
				<input id="groupEditCreateBy" type="hidden" name="group.createBy" />
				<input id="originalName" type="hidden" name="originalName" />
			</li>
			<li>
				<label class="rname">
					<span>分组描述</span>
				</label>
				<textarea id="groupEditTextarea" name="group.description" cols="40" rows="8"></textarea>
			</li>
		</ul>
	</form>
</div>
</body>
</html>

