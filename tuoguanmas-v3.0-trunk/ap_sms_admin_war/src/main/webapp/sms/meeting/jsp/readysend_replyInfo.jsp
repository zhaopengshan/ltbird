<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String batchId = (String)request.getParameter("batchId");
    String smsReadySendId = request.getParameter("smsReadySendId") == null?"":(String)request.getParameter("smsReadySendId");
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
<title>短信待发回复</title>
<script type="text/javascript">
	var meetNoticeselfUrl = "${ctx }/sms/meeting/jsp/readysend_replyInfo.jsp?batchId=<%=batchId%>&smsReadySendId=<%=smsReadySendId%>",
	meetNoticeself_export_url = "${ctx }/meetSmsReadySendAction/exportReply.action?batchId=<%=batchId%>&smsReadySendId=<%=smsReadySendId%>";
	//&smsRe=<%=smsRe%>
	<%if(smsRe!=null){%>
	meetNoticecurrent_url = meetNoticeselfUrl+"&smsRe=<%=smsRe%>";
	meetNoticeexport_sms_url = meetNoticeself_export_url + "&smsRe=<%=smsRe%>";
	<%}else{%>
	meetNoticecurrent_url = meetNoticeselfUrl;
	meetNoticeexport_sms_url = meetNoticeself_export_url;
	<%}%>
	meetNoticerightMenuBar.showReplyReadySendMenu();
	var resultGridPro = {
			url:"${ctx }/meetSmsReadySendAction/getReplyInfo.action?batchId=<%=batchId%>&searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&replyName="+encodeURI("<%=replyName%>")+"&replyMobile="+encodeURI("<%=replyMobile%>"),
		colNames:['回复人姓名','回复内容', '回复时间'],
		colModel:[
			{name:'tosName',align:"center",width:100,formatter:function(data){
				var sName = data.tos;
				if( data.tosName != null && $.trim(data.tosName) != "" ){
					sName = sName + "&lt;"+ data.tosName + "&gt;";
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
				meetNoticecurrent_url = meetNoticereadysend_list_url;
				<%}else{%>
				meetNoticecurrent_url = "${ctx }/meetSmsReadySendAction/getSmsDetails.action?selectedId=<%=smsReadySendId%>";
				<%}%>
				meetNoticerightMenuBar.showSendBoxMenu();
			}
		},{
			text: "删除",
			classes: "",
			name: "delete",
			click: function(){ 
				var url="${ctx}meetSmsInboxAction/deleteByIds.action";
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(meetNoticesmsGrid,url,showMessage);
			}
		}],
		multiselect: true
	};
	var meetNoticesmsGrid;
	$(function() {
		meetNoticesmsGrid = new TableGrid("meetNoticereadyReply",resultGridPro);
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
			    <table id="meetNoticereadyReply"></table>
			</div>
		</div>
	</td>
  </tr>
</table>
  </div>
  
</body>
</html> 