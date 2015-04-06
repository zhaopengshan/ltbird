<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String batchId = (String)request.getParameter("batchId");
    String smsReadySendId = request.getParameter("smsReadySendId") == null?"":(String)request.getParameter("smsReadySendId");
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
<title>短信待发回复</title>
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
	var selfUrl = "${ctx }/smssend/jsp/readysend_replyInfo.jsp?batchId=<%=batchId%>&smsReadySendId=<%=smsReadySendId%>";
	//&smsRe=<%=smsRe%>
	<%if(smsRe!=null){%>
		window.parent.current_url = selfUrl+"&smsRe=<%=smsRe%>";
	<%}else{%>
		window.parent.current_url = selfUrl;
	<%}%>
	window.parent.rightMenuBar.showReplyReadySendMenu();
	var resultGridPro = {
			url:"${ctx }/mbnSmsReadySendAction/getReplyInfo.action?batchId=<%=batchId%>&searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBycontacts="+encodeURI("<%=searchBycontacts%>"),
		colNames:['id','回复人','回复内容', '回复时间'],
		colModel:[
			{name:'id',index:'id', width:0,sortable: false, align: 'center',hidden: true},
			{name:'tosName',index:'tosName', width:100,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var sName = rowObject.tos;
					if( cellvalue != null && $.trim(cellvalue) != "" ){
						sName = sName + "<"+ cellvalue + ">";
					}
					return sName;
				}},
			{name:'content',index:'content', width:100,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					 
					return cellvalue;
				}}, 
			{name:'receiveTime',index:'failReason', width:100,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var reValue= cellvalue;
					if( cellvalue == null && $.trim(cellvalue) == "" ){
						reValue = "(无)";
					}
					return reValue;
				}}
		],
		pager: '#hadResultPage',
		buttons: [{
			text: "返回",
			classes: "tubh",
			click: function(){
				<%if(smsRe!=null){%>
					window.parent.current_url = window.parent.readysend_list_url;
				<%}else{%>
					window.parent.current_url = "${ctx }/mbnSmsReadySendAction/getSmsDetails.action?selectedId=<%=smsReadySendId%>";
				<%}%>
				window.parent.rightMenuBar.showSendBoxMenu();
			}
		},{
			text: "删除",
			classes: "tubh",
			click: function(){ 
				var url="${ctx}mbnSmsInboxAction/deleteByIds.action";
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(url,showMessage);
			}
		},{
			text: "回复",
			classes: "tubh",
			click: function(){
				
			}
		},{
			text: "编辑转发",
			classes: "tubh",
			click: function(){
				
			}
		},{
			text: "保存珍藏",
			classes: "tubh",
			click: function(){
				var url = '${ctx}/mbnSmsInboxAction/collectByIds.action';
                collectSmsFunc(url);
			}
		}]
	};
	var smsGrid;
	$(function() {
		smsGrid = new LeadToneGrid("readyReply",resultGridPro);
		smsGrid.init();
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
			<div class="table_box1">
			    <table id="readyReply"></table>
    			<div id="hadResultPage"></div>
			</div>
		</div>
	</td>
  </tr>
</table>
  </div>
  
</body>
</html> 