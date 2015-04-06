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
<title>已发短信详细</title>
</head>
<script type="text/javascript">
<!-- 
	window.parent.current_url = window.parent.hadsend_list_url;
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
			var smsHadSendId = $("#smsHadSendId").val();
			$.ajax({
                url : "${ctx }/mbnSmsHadSendAction/updateByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsHadSendId
                },
                success : function(data){
                	successCalBack(data);
                	if(data.resultcode =="success"){
                		window.location.href="${ctx }/smssend/jsp/hadsend_list.jsp";
                	}
                },
                error : errorCalBack
            }); 
		});
		
		$("#collectSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsHadSendId = $("#smsHadSendId").val();
			$.ajax({
                url : "${ctx }/mbnSmsHadSendAction/collectByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsHadSendId
                },
                success : successCalBack,
                error : errorCalBack
            }); 
		});
		$("#editSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsHadSendAction/edit.action';
			var smsHadSendId = $("#smsHadSendId").val();
			window.parent.parent.addTabs('itemx','itemxs'+smsHadSendId,'短信互动','编辑转发',url+'?selectedId='+smsHadSendId);
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			window.parent.current_url = window.parent.hadsend_list_url;
			window.parent.rightMenuBar.showHadSendBoxMenu();
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			jQuery.messager.alert("系统提示","已经是最后一条！","warning");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsHadSendAction/followPage.action?selectedId=${mbnSmsHadSendVO.id }&batchId=${mbnSmsHadSendVO.batchId }";
		url = url + "&pageDirect=" + direction;
		window.location.href = url;
	}
	 -->
</script>
<body>

<div class="left_contents">
	<div class="botton_box">
	  <div class="tubh"><a id="returnBtn" href="javascript:void(0);">返回</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="editSmsBtn">编辑转发</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="deleteBtn">删除</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="collectSmsBtn">保存珍藏</a></div>
	  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
	  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="nextPage" style="color:#067599">下一条</a> </div>
	<div class="table_box">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  	<td width="10%"  align="left" style="vertical-align: top">发送时间：</td>
			<td width="90%"><fmt:formatDate value="${mbnSmsHadSendVO.commitTime }"  type="both" />
<a href="${ctx }/mbnSmsHadSendAction/getBatchSms.action?batchId=${mbnSmsHadSendVO.batchId}&smsHadSendId=${mbnSmsHadSendVO.id}">查看发送结果</a>
<a href="${ctx }/smssend/jsp/hadsend_replyInfo.jsp?smsHadSendId=${mbnSmsHadSendVO.id }&batchId=${mbnSmsHadSendVO.batchId}">${mbnSmsHadSendVO.replyCount }条回复</a></td>
			</tr>
		  <tr>
			<td width="10%"  align="left" style="vertical-align: top">收件人：</td>
			<td width="90%"><c:forEach items="${smsHadSendList}" var="hadSendSms">${hadSendSms.tos }
				<c:if test="${hadSendSms.tosName !=null && hadSendSms.tosName !=null}" >&lt;${hadSendSms.tosName}&gt;</c:if>;
			</c:forEach></td>
			</tr>
		  <tr>
		  	<td width="10%" align="left" style="vertical-align: top">短信标题：</td>
			<td width="90%">${mbnSmsHadSendVO.title }</td>
			</tr>
		  <tr>
			<td  align="left">短信内容：</td>
			<td ></td>
			</tr>
		  <tr>
			<td  align="right"></td>
			<td ><span style="color:#006699">${mbnSmsHadSendVO.content }</span></td>
			</tr>
		</table>
		<input type="hidden" id="smsHadSendId" value="${mbnSmsHadSendVO.id }"/>
		<br />
	</div>
</div>

</body>
</html>

