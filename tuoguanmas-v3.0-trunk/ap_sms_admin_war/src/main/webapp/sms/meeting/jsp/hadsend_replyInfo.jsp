<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String batchId = (String)request.getParameter("batchId");
    String smsHadSendId = request.getParameter("smsHadSendId") == null?"":(String)request.getParameter("smsHadSendId");
    String replyName = request.getParameter("replyName") == null?"":(String)request.getParameter("replyName");
    String replyMobile = request.getParameter("replyMobile") == null?"":(String)request.getParameter("replyMobile");
    String searchAct = request.getParameter("act")==null?"":(String)request.getParameter("act");
    String dateFrom = request.getParameter("dateFrom")==null?"":(String)request.getParameter("dateFrom");
    String dateTo = request.getParameter("dateTo")==null?"":(String)request.getParameter("dateTo");
    String pageNum = request.getParameter("pageNum")==null?"":(String)request.getParameter("pageNum");
    String smsRe = request.getParameter("smsRe") == null?null:(String)request.getParameter("smsRe");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信已发箱回复</title>
<script type="text/javascript">
	var meetNoticeselfUrl= "${ctx }/sms/meeting/jsp/hadsend_replyInfo.jsp?batchId=<%=batchId%>&smsHadSendId=<%=smsHadSendId%>",
	meetNoticeself_export_url = "${ctx }/meetSmsInboxAction/exportReply.action?batchId=<%=batchId%>&smsHadSendId=<%=smsHadSendId%>";
	<%if(smsRe!=null){%>
	meetNoticecurrent_url = meetNoticeselfUrl+"&smsRe=<%=smsRe%>";
		meetNoticeexport_sms_url = meetNoticeself_export_url+"&smsRe=<%=smsRe%>";
	<%}else{%>
	meetNoticecurrent_url = meetNoticeselfUrl;
	meetNoticeexport_sms_url = meetNoticeself_export_url;
	<%}%>
	meetNoticerightMenuBar.showReplyHadSendMenu();
	var resultGridPro = {
		url:"${ctx }/meetSmsInboxAction/getReplyInfo.action?batchId=<%=batchId%>&searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&replyName="+encodeURI("<%=replyName%>")+"&replyMobile="+encodeURI("<%=replyMobile%>"),
		colNames:['回复人','回复内容', '回复时间'],
		colModel:[
			{name:'senderName',align:"center",width:100,formatter:function(data){
					var sName = data.senderMobile;
					if( data.senderName != null && $.trim(data.senderName) != "" ){
						sName = sName + "&lt;"+ data.senderName + "&gt;";
					}
					return sName;
				}},
			{name:'content',width:300}, 
			{name:'receiveTime',align:"center",width:100,formatter:function(data){
					var reValue= data.receiveTime;
					if( data.receiveTime == null && $.trim(data.receiveTime) == "" ){
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
				meetNoticerightMenuBar.showHadSendBoxMenu();
			}
		},{
			text: "删除",
			classes: "",
			name: "delete",
			click: function(){
				var url=" ${ctx }/meetSmsInboxAction/deleteByIds.action";
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(meetNoticesmsGrid,url,showMessage);
				
			}
		}],
		multiselect: true
	};
	var meetNoticesmsGrid;
	$(function() {
		meetNoticesmsGrid = new TableGrid("meetNoticehadsentReply",resultGridPro);
		meetNoticesmsGrid.init();
	});
</script>
</head>

<body>
	<div class="contents">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td valign="top">
		<div class="left_contents"> 
			<div class="table_box1">
			    <table id="meetNoticehadsentReply"></table>
			</div>
		</div>
	</td>
  </tr>
</table>
  </div>
  
</body>
</html>

