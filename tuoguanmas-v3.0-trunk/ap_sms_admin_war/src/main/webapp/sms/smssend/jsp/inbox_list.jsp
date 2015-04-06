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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信收件箱</title>
<script type="text/javascript">
	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/mbnSmsInboxAction/getSmsDetails.action?selectedId=";
		$("#sms_hd_frame").load(localUrl+selectedId);
	}
	var gridPro = {
		url:"${ctx}/mbnSmsInboxAction/listSmsInbox.action?searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>"),
		colNames:['状态','发件人', '短信内容', '短信长度','接收时间'],
		colModel:[
			{name:'status',align:"center",width:40,formatter:function(data){
					var iconSms = '<img title="未读" src="${ctx}/themes/mas3/images/u124_normal.png" width="15" height="13">';
					switch(parseInt(data.status,10)){
						case 0: iconSms = '<img title="未读" src="${ctx}/themes/mas3/images/u124_normal.png" width="15" height="13">'; break;
						case 1: iconSms = '<img title="已读" src="${ctx}/themes/mas3/images/u164_normal.png" width="15" height="13">'; break;
						case 2: iconSms = '<img title="已回复" src="${ctx}/themes/mas3/images/u128_normal.png" width="15" height="13">'; break;
					}
					return iconSms;
				}},
			{name:'senderName',align:"center",width:120,formatter:function(data){
					var sName = data.senderMobile;
					if( data.senderName != null && $.trim(data.senderName) != "" ){
						sName = sName + "&lt;"+ data.senderName + "&gt;";
						if(sName.length > 11){
							//return sName.substring(0,8)+"...";
							return '<a href="javascript:void(0);" class="grid-sms" title="'+sName+'">'+ sName.substring(0,11)+'...</a>';
						}
					}
					return sName;
				}},
			{name:'content',width:200,formatter:function(data){
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
			{name:'content',align:"center",width:110,formatter:function(data){
					if($.trim(data.content) == "" ){
						return 0;
					}else{
						return data.content.length;
					}
				}},
			{name:'receiveTime',align:"center",width:120}
		],
		buttons: [/*{
			text: "删除",
			classes: "",
			name: "delete",
			click: function(){
				var url = '${ctx}/mbnSmsInboxAction/deleteByIds.action';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(smsGrid,url,showMessage);
			}
		},*/
		{
			text: "编辑转发",
			classes: "",
			name: "edit",
			click: function(){ 
				var url = '${ctx}/mbnSmsInboxAction/edit.action';
				editSmsFunc(smsGrid,url);
			}
		},{
			text: "保存珍藏",
			classes: "",
			name: "collect",
			click: function(){
				var url = '${ctx}/mbnSmsInboxAction/collectByIds.action';
                collectSmsFunc(smsGrid,url);
			}
		}]
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

