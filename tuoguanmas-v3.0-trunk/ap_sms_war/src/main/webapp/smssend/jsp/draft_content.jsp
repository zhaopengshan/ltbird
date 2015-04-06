<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/easyui/easyui.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="${ctx }/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="javascript" src="${ctx }/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.easyui.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
<title>草稿详细</title>
</head>
<script type="text/javascript">
<!-- 
	function successCalBack(data) {
    	$.messager.show({
            title: '用户操作',
            msg:data.message,
            timeout:5000
        });
	}
	function errorCalBack() {
        $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
    }
	$(function() {
		$("#deleteBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsDraftId = $("#smsDraftId").val();
			$.ajax({
                url : "${ctx }/mbnSmsDraftAction/deleteByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsDraftId
                },
                success : function(data){
                	successCalBack(data);
                	if(data.resultcode =="success"){
                		window.location.href="${ctx }/smssend/jsp/draft_list.jsp";
                	}
                },
                error : errorCalBack
            }); 
		});
		
		$("#collectSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsDraftId = $("#smsDraftId").val();
			$.ajax({
                url : "${ctx }/mbnSmsDraftAction/collectByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsDraftId
                },
                success : successCalBack,
                error : errorCalBack
            }); 
		});
		$("#editSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsDraftAction/edit.action';
			var smsDraftId = $("#smsDraftId").val();
			window.parent.parent.addTabs('itemx','itemxs'+smsDraftId,'短信互动','编辑转发',url+'?selectedId='+smsDraftId);
		});
		
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			window.parent.current_url = window.parent.draft_list_url;
			window.parent.rightMenuBar.showDraftBoxMenu();
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			jQuery.messager.alert("系统提示","已经是最后一条！","warning");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsDraftAction/followPage.action?selectedId=${mbnSmsDraft.id }";
		url = url + "&pageDirect=" + direction;
		window.location.href = url;
	}
	 -->
</script>
<body>

<div class="left_contents">
	<div class="botton_box">
	  <div class="tubh"><a id="returnBtn" href="javascript:void(0);">返回</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="editSmsBtn">编辑发送</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="deleteBtn">删除</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="collectSmsBtn">保存珍藏</a></div>
	  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
	  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="nextPage" style="color:#067599">下一条</a> </div>
	<div class="table_box">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  	<td width="10%" align="left" style="vertical-align: top">保存时间：</td>
			<td width="90%"><fmt:formatDate value="${mbnSmsDraft.createTime }"  type="both" /></td>
			</tr>
		  <tr>
		  	<td width="10%" align="left" style="vertical-align: top">短信标题：</td>
			<td width="90%">${mbnSmsDraft.title }</td>
			</tr>
		  <tr>
			<td  width="10%" align="left" style="vertical-align: top">正文：</td>
			<td width="90%" ></td>
			</tr>
		  <tr>
			<td  align="right"></td>
			<td ><span style="color:#006699">${mbnSmsDraft.content }</span></td>
			</tr>
		</table>
		<input type="hidden" id="smsDraftId" value="${mbnSmsDraft.id }"/>
		<br />
	</div>
</div>

</body>
</html>

