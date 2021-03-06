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
<title>已发送</title>
<script type="text/javascript">
	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/mbnSmsHadSendAction/getSmsDetails.action?selectedId=";
		$("#birthday_hd_frame").load(localUrl+selectedId);
	}
	function showSmsReply(batchId,rowId){
		var localUrl = "${ctx }/sms/birthday/jsp/handsend_replyInfo.jsp?batchId="+batchId+ "&smsReadySendId="+rowId+"&smsRe=0";
		$("#birthday_hd_frame").load(localUrl);
	}
	var gridPro = {
		url:"${ctx}/mbnSmsHadSendAction/listHadSendSms.action?searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>")+"&searchBySmsTitle="+encodeURI("<%=searchBySmsTitle%>"),
		colNames:['状态','祝福标题','祝福内容', '发送','最新发送时间','发送记录'],
		colModel:[
			{name:'sendResult',align:"center",width:50,formatter:function(data){
					var iconSms = '<img title="已发送" src="${ctx}/themes/mas3/images/u262_normal.png" width="15" height="13">';
					return iconSms;
				}},
			{name:'title',align:"center",width:90,formatter:function(data){
				var sName = data.title;
				if( sName != null && $.trim(sName) != "" ){
					if(sName.length > 8){
						return sName.substring(0,8)+"...";
					}
				}
				return sName;
			}},
			{name:'content',width:180,formatter:function(data){
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
			{name:'content',align:"center",width:80,formatter:function(data){
					if($.trim(data.content) == "" ){
						return 0;
					}else{
						return data.content.length;
					}
				}},
			{name:'completeTime',align:"center",width:120},		
			{name:'replyCount',align:"center",width:70,formatter:function(data){ 
				return '<a onclick="showSmsReply(\''+data.batchId+'\',\''+data.id+'\')" href="javascript:void(0);">'+data.replyCount+'条</a>';
				}}
		],
		buttons: [{
			text: "删除",
			classes: "",
			name: "delete",
			click: function(){
				var url = '${ctx}/mbnSmsHadSendAction/updateByIds.action';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(smsGrid,url,showMessage);
			}
		},{
			text: "编辑转发",
			classes: "",
			name: "edit",
			click: function(){
				var url = '${ctx}/mbnSmsHadSendAction/edit.action';
				editSmsFunc(smsGrid,url);
			}
		}],
		multiselect: true
	};
	var smsGrid;
	$(function() {
		smsGrid = new TableGrid("smsGrid",gridPro);
		smsGrid.init();
	});
</script>
</head>
<body>
	<table id="smsGrid"></table>
</body>
</html>

