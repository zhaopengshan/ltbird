<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String batchId = (String)request.getParameter("batchId");
    String smsReadySendId = (String)request.getParameter("smsReadySendId");
    String searchBycontacts = request.getParameter("contacts") == null?"":(String)request.getParameter("contacts");
    String sendResult = request.getParameter("sendResult") == null?"":(String)request.getParameter("sendResult");
    String smsRe = request.getParameter("smsRe") == null?null:(String)request.getParameter("smsRe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信待发详细</title>
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
<script type="text/javascript">
<!-- 
	var selfUrl = "${ctx }/mbnSmsReadySendAction/getBatchSms.action?batchId=<%=batchId%>&smsReadySendId=<%=smsReadySendId%>";
	window.parent.current_url = selfUrl;
	window.parent.rightMenuBar.showReadySendMenu();
	var resultGridPro = {
		url:"${ctx }/mbnSmsReadySendAction/listBatchSendResult.action?batchId=<%=batchId%>&sendResult=<%=sendResult%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>"),
		colNames:['id','手机号码','接收人姓名', '发送结果', '失败原因'],
		colModel:[
			{name:'id',index:'id', width:0,sortable: false, align: 'center',hidden: true},
			{name:'tos',index:'tos', width:100,sortable: false, align: 'center'},
			{name:'tosName',index:'tosName', width:100,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var reValue= cellvalue;
					if( cellvalue == null && $.trim(cellvalue) == "" ){
						reValue = "(未知)";
					}
					return reValue;
				}},
			{name:'sendResult',index:'sendResult', width:100,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var reValue= "无";
					switch(cellvalue){
						case -1: reValue= "已取消";break;
						case  0: reValue= "等待发送";break;
						case  1: reValue= "发送中";break;
						case  2: reValue= "成功";break;
						case  3: reValue= "失败";break;
					}
					return reValue;
				}},
			{name:'failReason',index:'failReason', width:100,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var reValue= cellvalue;
					if( cellvalue == null && $.trim(cellvalue) == "" ){
						reValue = "(无)";
					}
					return reValue;
				}}
		],
		pager: '#readyResultPage',
		buttons: [{
			text: "返回",
			classes: "tubh",
			click: function(){
				
				window.parent.current_url = "${ctx }/mbnSmsReadySendAction/getSmsDetails.action?selectedId=<%=smsReadySendId%>";
				window.parent.rightMenuBar.showSendBoxMenu();
				//window.location.href="${ctx }/mbnSmsReadySendAction/getSmsDetails.action?selectedId=<%=smsReadySendId%>";
			}
		}]
	};
	var readyResultGrid;
	$(function() {
		readyResultGrid = new LeadToneGrid("readyResultGrid",resultGridPro);
		readyResultGrid.init();
	});
	 -->
</script>

</head>

<body>
	<div class="contents">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td valign="top">
		<div class="left_contents">
			<div class="botton_box1">共发送<span style="color: #FF0000">${smsSendResult["totails"]}
				</span>条短信，成功<span style="color:#00cc00">${smsSendResult["success"]}</span>条，
				失败<span style="color:#FF0000">${smsSendResult["failure"]}</span>条，
				发送中<span style="color:#FF0000">${smsSendResult["sending"]}</span>条，
				已取消<span style="color:#FF0000">${smsSendResult["cancel"]}</span>条，
				等待发送<span style="color:#FF0000">${smsSendResult["waiting"]}</span>条
			</div>
			<div class="table_box1">
			    <table id="readyResultGrid"></table>
    			<div id="readyResultPage"></div>
			</div>
		</div>
	</td>
  </tr>
</table>
  </div>
  
</body>
</html>

