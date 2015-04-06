<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String batchId = (String)request.getParameter("batchId");
    String smsHadSendId = (String)request.getParameter("smsHadSendId");
    String receiverName = request.getParameter("receiverName") == null?"":(String)request.getParameter("receiverName");
    String receiveMoble=request.getParameter("receiveMoble")==null?"":(String)request.getParameter("receiveMoble");
    String failReason=request.getParameter("failReason")==null?"":(String)request.getParameter("failReason");
    String sendResult = request.getParameter("sendResult") == null?"":(String)request.getParameter("sendResult");
    String smsRe = request.getParameter("smsRe") == null?null:(String)request.getParameter("smsRe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信已发箱详细</title>
<script type="text/javascript">
	var meetNoticeselfUrl = "${ctx }/meetSmsHadSendAction/getBatchSms.action?batchId=<%=batchId%>&smsHadSendId=<%=smsHadSendId%>",
	meetNoticeself_export_url = "${ctx }/meetSmsHadSendAction/exportResult.action?batchId=<%=batchId%>&smsHadSendId=<%=smsHadSendId%>";
	meetNoticecurrent_url = meetNoticeselfUrl;
	meetNoticeexport_sms_url = meetNoticeself_export_url;
	meetNoticerightMenuBar.showHadSendResultMenu();
	var resultGridPro = {
		url:"${ctx }/meetSmsHadSendAction/listBatchSendResult.action?batchId=<%=batchId%>&sendResult=<%=sendResult%>&receiverName="+encodeURI("<%=receiverName%>")+"&receiveMoble="+encodeURI("<%=receiveMoble%>")+"&failReason="+encodeURI("<%=failReason%>"),
		colNames:['手机号码','接收人姓名', '发送结果', '失败原因'],
		colModel:[
			{name:'tos',align:"center",width:100},
			{name:'tosName',align:"center",width:100,formatter:function(data){
					var reValue= data.tosName;
					if( data.tosName == null && $.trim(data.tosName) == "" ){
						reValue = "(未知)";
					}
					return reValue;
				}},
			{name:'sendResult',align:"center",width:100,formatter:function(data){
					var reValue= "无";
					switch(data.sendResult){
						case -1: reValue= "已取消";break;
						case  0: reValue= "等待发送";break;
						case  1: reValue= "已提交网关";break;
						case  2: reValue= "成功";break;
						case  3: reValue= "失败";break;
					}
					return reValue;
				}},
			{name:'failReason',align:"center",width:100,formatter:function(data){
					var reValue= data.failReason;
					if( data.failReason == null && $.trim(data.failReason) == "" ){
						reValue = "(无)";
					}
					return reValue;
				}}
		],
		buttons: [{
			text: "返回",
			classes: "",
			name: "return",
			click: function(){
				<%if(smsRe!=null){%>
				meetNoticecurrent_url = meetNoticehadsend_list_url;
				<%}else{%>
				meetNoticecurrent_url = "${ctx }/meetSmsHadSendAction/getSmsDetails.action?selectedId=<%=smsHadSendId%>";
				<%}%>
				meetNoticerightMenuBar.showSendBoxMenu();
			}
		},{
			text: "重发失败项",
			classes: "",
			name: "resend",
			click: function(){
				var url = "${ctx }/meetSmsHadSendAction/retry.action";
				collectSmsFunc(hadResultGrid,url);
			}
		}],
		multiselect: true,
		height: 390
	};
	var hadResultGrid;
	$(function() {
		hadResultGrid = new TableGrid("meetNoticehadResultGrid",resultGridPro);
		hadResultGrid.init();
	});
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
				已提交网关<span style="color:#FF0000">${smsSendResult["sending"]}</span>条，
				已取消<span style="color:#FF0000">${smsSendResult["cancel"]}</span>条，
				等待发送<span style="color:#FF0000">${smsSendResult["waiting"]}</span>条
			</div>
			<div class="table_box1">
			    <table id="meetNoticehadResultGrid"></table>
			</div>
		</div>
	</td>
  </tr>
</table>
  </div>
  
</body>
</html>

