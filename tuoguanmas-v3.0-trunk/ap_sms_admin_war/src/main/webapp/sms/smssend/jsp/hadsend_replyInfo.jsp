<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String batchId = (String)request.getParameter("batchId");
    String smsHadSendId = request.getParameter("smsHadSendId") == null?"":(String)request.getParameter("smsHadSendId");
    String searchBycontacts = request.getParameter("contacts") == null?"":(String)request.getParameter("contacts");
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
	var selfUrl = "${ctx }/sms/smssend/jsp/hadsend_replyInfo.jsp?batchId=<%=batchId%>&smsHadSendId=<%=smsHadSendId%>",
		self_export_url = "${ctx }/mbnSmsInboxAction/exportReply.action?batchId=<%=batchId%>&smsHadSendId=<%=smsHadSendId%>";
	<%if(smsRe!=null){%>
		current_url = selfUrl+"&smsRe=<%=smsRe%>";
		export_sms_url = self_export_url+"&smsRe=<%=smsRe%>";
	<%}else{%>
		current_url = selfUrl;
		export_sms_url = self_export_url;
	<%}%>
	rightMenuBar.showReplyHadSendMenu();
	var resultGridPro = {
		url:"${ctx }/mbnSmsInboxAction/getReplyInfo.action?batchId=<%=batchId%>&searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>"),
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
					current_url = hadsend_list_url;
				<%}else{%>
					current_url = "${ctx }/mbnSmsHadSendAction/getSmsDetails.action?selectedId=<%=smsHadSendId%>";
				<%}%>
				rightMenuBar.showHadSendBoxMenu();
			}
		},{
			text: "删除",
			classes: "",
			name: "delete",
			click: function(){
				var url=" ${ctx }/mbnSmsInboxAction/deleteByIds.action";
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(smsGrid,url,showMessage);
				
			}
		},{
			text: "回复",
			classes: "",
			name: "reply",
			click: function(){
				var rows = smsGrid.getSelectedItemIds();
				if( rows.length == 1 ){ 
					var url = '${ctx}/mbnSmsInboxAction/reply.action';
				    var originalUrl = "./smssend/writesms.action";
				    var tempUrl = url+'?selectedId='+rows;
				    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
				    try{
				        tabpanel.kill(killId);
				    }catch(e){
				    }
				    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
				    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
				}else if( rows.length > 1){
					alert("只能选择一项进行操作！");
				}else{
					alert("请先选择需要操作的项！");
				}
			}
		},{
			text: "编辑转发",
			classes: "",
			name: "edit",
			click: function(){
				var rows = smsGrid.getSelectedItemIds();
				if( rows.length == 1 ){ 
					var url = '${ctx}/mbnSmsInboxAction/edit.action';
				    var originalUrl = "./smssend/writesms.action";
				    var tempUrl = url+'?selectedId='+rows;
				    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
				    try{
				        tabpanel.kill(killId);
				    }catch(e){
				    }
				    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
				    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
				}else if( rows.length > 1){
					alert("只能选择一项进行操作！");
				}else{
					alert("请先选择需要操作的项！");
				}
			}
		},{
			text: "保存珍藏",
			classes: "",
			name: "collect",
			click: function(){
				var url = '${ctx}/mbnSmsInboxAction/collectByIds.action';
                collectSmsFunc(smsGrid,url);
			}
		}],
		multiselect: true
	};
	var smsGrid;
	$(function() {
		smsGrid = new TableGrid("hadsentReply",resultGridPro);
		smsGrid.init();
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
			    <table id="hadsentReply"></table>
			</div>
		</div>
	</td>
  </tr>
</table>
  </div>
  
</body>
</html>

