<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String searchAct = request.getParameter("act")==null?"":(String)request.getParameter("act");
    String dateFrom = request.getParameter("dateFrom")==null?"":(String)request.getParameter("dateFrom");
    String dateTo = request.getParameter("dateTo")==null?"":(String)request.getParameter("dateTo");
    String pageNum = request.getParameter("pageNum")==null?"":(String)request.getParameter("pageNum");
    String searchBycontacts = request.getParameter("contacts")==null?"":(String)request.getParameter("contacts");
    String searchBySmsTitle = request.getParameter("smsTitle")==null?"":(String)request.getParameter("smsTitle");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信待发箱</title>
<script type="text/javascript">
	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/meetSmsReadySendAction/getSmsDetails.action?selectedId=";
		$("#meetNoticesms_hd_frame").load(localUrl+selectedId);
	}
	function showSendResult(url){
		url=url+"&smsRe=0";
		$("#meetNoticesms_hd_frame").load(url);
	}
	function showSmsReply(batchId,rowId){
		var localUrl = "${ctx }/sms/meeting/jsp/readysend_replyInfo.jsp?batchId="+batchId+ "&smsReadySendId="+rowId+"&smsRe=0";
		$("#meetNoticesms_hd_frame").load(localUrl);
	}
	var gridPro = {
		url:"${ctx}/meetSmsReadySendAction/listSmsReadySend.action?searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>")+"&searchBySmsTitle="+encodeURI("<%=searchBySmsTitle%>"),
		colNames:['状态','会议主题','会议通知内容','发送时间','创建人','发送结果','回复'],
		colModel:[
			{name:'sendResult',width:50,align:"center",formatter:function(data){
					var iconSms = '<img title="未发送" src="${ctx}/themes/mas3/images/u16220_normal.png" width="15" height="13">';
					switch(parseInt(data.sendResult,10)){
						case -1: iconSms = '<img title="已取消" src="${ctx}/themes/mas3/images/sms_cancel_normal.png" width="15" height="16">'; break;
						case 0: iconSms = '<img title="未发送" src="${ctx}/themes/mas3/images/u16220_normal.png" width="13" height="16">'; break;
						case 1: iconSms = '<img title="已提交网关" src="${ctx}/themes/mas3/images/u1223_normal.png" width="15" height="13">'; break;
					}
					return iconSms;
				}},
			{name:'title',width:100,formatter:function(data){
				var sName = data.title;
				if( sName != null && $.trim(sName) != "" ){
					if(sName.length > 8){
						return sName.substring(0,8)+"...";
					}
				}
				return sName;
			}},
			{name:'content',width:210,formatter:function(data){
					if($.trim(data.content) == "" ){
						return "";
					}else{
						if(typeof data.content !== 'undefined'){
							if(data.content.length>8){
								return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content.substring(0,8)+'...</a>';
							}else{
								return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content+'</a>';
							}
						}else{
							return "";
						}
						//return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content+'</a>';
					}
				}},
			{name:'readySendTime',width:150},
			{name:'user.name',width:100},
			{name:'sendResult',align:"center",width:80,formatter:function(data){
				return '<a onclick="javascript:showSendResult(\'${ctx }/meetSmsReadySendAction/getBatchSms.action?batchId='+data.batchId+'&smsReadySendId='+data.id+'\');" href="javascript:void(0);">&nbsp;&nbsp;结果</a>';
			}},
			{name:'replyCount',width:80,align:"center",formatter:function(data){
				//此方法用的是已发箱里的url,因为相关的操作与已发箱的回复信息列表一样
					return '<a onclick="showSmsReply(\''+data.batchId+'\',\''+data.id+'\')" href="javascript:void(0);">'+data.replyCount+'条</a>';
				}}
		],
		buttons: [{
			text: "编辑",
			classes: "",
			name: "edit",
			click: function(){
				var url = '${ctx}/meetSmsReadySendAction/edit.action';
				meetNoticeeditSmsFunc(meetNoticesmsGrid,url);
			}
		},{
			text: "删除",
			classes: "",
			name: "cancel",
			click: function(){
				var url = '${ctx}/meetSmsReadySendAction/cancelSendByIds.action';
				var showMessage = "是否取消发送选中的{0}条短信？";
                confirmAjaxFunc(meetNoticesmsGrid,url,showMessage);
			}
		}]
	};
	var meetNoticesmsGrid;
	$(function() {
		meetNoticesmsGrid = new TableGrid("meetNoticesmsGrid",gridPro);
		meetNoticesmsGrid.init();
	});
</script>
</head>
<body>
	<table id="meetNoticesmsGrid"></table>
</body>
</html>

