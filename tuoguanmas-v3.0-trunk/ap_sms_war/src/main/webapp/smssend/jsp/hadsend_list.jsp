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
<title>短信已发箱</title>
<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/easyui/easyui.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/ui.jqgrid.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/leadtone.grid.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="${ctx }/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="javascript" src="${ctx }/js/i18n/grid.locale-cn.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.easyui.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.jqGrid.src.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.LeadToneGrid.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/smssend/js/sms_info.js" type="text/javascript" ></script>
<script type="text/javascript">
<!-- 
	var gridPro = {
		url:"../../mbnSmsHadSendAction/listHadSendSms.action?searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>")+"&searchBySmsTitle="+encodeURI("<%=searchBySmsTitle%>"),
		colNames:['id','状态','短信标题','短信内容', '短信长度','发送时间','回复'],
		colModel:[
			{name:'id',index:'id', width:0,sortable: false, align: 'center',hidden: true},
			{name:'sendResult',index:'sendResult', width:70,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var iconSms = '<img title="已发送" src="../../images/u262_normal.png" width="15" height="13">';
					return iconSms;
				}},
			{name:'title',index:'title', width:110,sortable: false, align: 'center'},
			{name:'content',index:'content', width:250,sortable: false,formatter:function(cellvalue, options, rowObject){
					if($.trim(cellvalue) == "" ){
						return "";
					}else{
						return '<a href="${ctx}/mbnSmsHadSendAction/getSmsDetails.action?selectedId='+ rowObject.id +'" class="grid-sms">'+cellvalue+'</a>';
					}
				}},
			{name:'content',index:'content', width:80,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					if($.trim(cellvalue) == "" ){
						return 0;
					}else{
						return cellvalue.length;
					}
				}},
			{name:'completeTime',index:'completeTime', width:130,sortable: false, align: 'center',sorttype:'date', datefmt:'Y-m-d'},		
			{name:'replyCount',index:'replyCount', width:60,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){ 
				return "<a href='${ctx }/smssend/jsp/hadsend_replyInfo.jsp?batchId="+rowObject.batchId+ "&smsReadySendId="+rowObject.id+"&smsRe=0'>"+cellvalue+"条</a>";
				}}
		],
		pager: '#smsPage',
		buttons: [{
			text: "删除",
			classes: "tubh",
			click: function(){
				var url = '../../mbnSmsHadSendAction/updateByIds.action';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(url,showMessage);
			}
		},{
			text: "编辑转发",
			classes: "tubh",
			click: function(){
				var url = '${ctx}/mbnSmsHadSendAction/edit.action';
				editSmsFunc(url);
			}
		},{
			text: "保存珍藏",
			classes: "tubh",
			click: function(){
                var url = '../../mbnSmsHadSendAction/collectByIds.action';
                collectSmsFunc(url);
			}
		}]
	};
	var smsGrid;
	$(function() {
		smsGrid = new LeadToneGrid("smsGrid",gridPro);
		smsGrid.init();
	});
	 -->
</script>
</head>
<body>
	<table id="smsGrid"></table>
    <div id="smsPage"></div>
</body>
</html>

