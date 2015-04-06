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
<title>接收短信详细</title>
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
		var reSmsContent = new PlaceHolder("reSmsContent","输入文字...");
			reSmsContent.init();
			reSmsContent.refresh();
		var contacts = new PlaceHolder("contactIds","输入联系人...");
			contacts.init();
			contacts.refresh();
		$( "#deleteBtn").unbind("click.in").bind("click.in",function(){
			var smsInbox = $("#smsInbox").val();
			$.ajax({
                url : "${ctx }/mbnSmsInboxAction/deleteByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsInbox
                },
                success : function(data){
                	if(data.resultcode == "success"){
				        window.location.href="${ctx }/smssend/jsp/inbox_list.jsp";
				    } else {
				        $.messager.show({
				            title: '用户操作',
				            msg:data.message,
				            timeout:5000
				        });
				    }
                },
                error : errorCalBack
            }); 
		});
		$( "#fastReplyBtn").unbind("click.in").bind("click.in",function(){
			var reSmsContent = $("#reSmsContent").val();
			var selectedId = $("#smsInbox").val();
			var contactIds = $("#contactIds").val();
			var tmp = encodeURI(encodeURI(reSmsContent));
			var url = '${ctx}/mbnSmsInboxAction/fastSend.action';
			window.parent.parent.addTabs('selectedId','itemxs'+selectedId,'短信互动','快速回复',url+'?selectedId='+selectedId+'&contactIds='+contactIds+'&smsContent=' + tmp);
		});
		$( "#replyBtn").unbind("click.in").bind("click.in",function(){
			var url = '${ctx}/mbnSmsInboxAction/reply.action';
			var selectedId = $("#smsInbox").val();
			window.parent.parent.addTabs('itemx','itemxs'+selectedId,'短信互动','编辑转发',url+'?selectedId='+selectedId);
		});
		$("#editSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsInboxAction/edit.action';
			var selectedId = $("#smsInbox").val();
			window.parent.parent.addTabs('itemx','itemxs'+selectedId,'短信互动','编辑转发',url+'?selectedId='+selectedId);
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			window.parent.current_url = window.parent.inbox_list_url;
			window.parent.rightMenuBar.showInBoxMenu();
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			jQuery.messager.alert("系统提示","已经是最后一条！","warning");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsInboxAction/followPage.action?selectedId=${mbnSmsInbox.id }";
		url = url + "&pageDirect=" + direction;
		window.location.href = url;
	}
	 -->
</script>
<body>

<div class="left_contents">
	<div class="botton_box">
	  <div class="tubh"><a id="returnBtn" href="javascript:void(0);">返回</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="replyBtn">回复</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="editSmsBtn">转发</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="deleteBtn">删除</a></div>
	  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
	  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="nextPage" style="color:#067599">下一条</a> </div>
	<div class="table_box">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10%"  align="left">接收时间：</td>
				<td width="90%" ><fmt:formatDate value="${mbnSmsInbox.receiveTime }"  type="both" /></td>
			</tr>
		  	<tr>
				<td width="10%"  align="left">发件人：</td>
				<td width="90%">${mbnSmsInbox.senderMobile }<c:if test="${mbnSmsInbox.senderName!=null&&mbnSmsInbox.senderName!=null}" >&lt;${mbnSmsInbox.senderName}&gt;</c:if></td>
		  	</tr>
		  	<tr>
				<td  align="left">短信内容：</td>
				<td ></td>
		  	</tr>
		  	<tr>
				<td  align="right"></td>
				<td ><span style="color:#006699">${mbnSmsInbox.content }</span></td>
		  	</tr>
		</table>
		<br />
  	</div>
	<div class="huifu_box"><input type="text" id="contactIds" class="input2"  style="width: 99%; margin-bottom: 5px;" value="${mbnSmsInbox.senderMobile }"/>
	<br><textarea cols="" id="reSmsContent" rows="" style="width: 99%; height: 120px; font-size: 12px;"></textarea>
	</div>
	<div class="bottom_box1">
		<input type="hidden" id="smsInbox" value="${mbnSmsInbox.id }"/>
		<div class="tubh"><a href="javascript:void(0);" id="fastReplyBtn">快速回复</a></div>
	</div>
</div>

</body>
</html>

