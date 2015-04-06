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
    String searchBySmsTitle = request.getParameter("smsTitle")==null?"":(String)request.getParameter("smsTitle");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信草稿箱</title>
<script type="text/javascript">
	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/mbnSmsDraftAction/getSmsDetails.action?selectedId=";
		$("#sms_hd_frame").load(localUrl+selectedId);
	}
	var gridPro = {
		url:"${ctx}/mbnSmsDraftAction/listSmsDraft.action?searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>&searchBySmsTitle="+encodeURI("<%=searchBySmsTitle%>"),
		colNames:['短信标题','短信内容', '短信长度','保存时间'],
		colModel:[
			{name:'title',align:"center",width:100,formatter:function(data){
				var sName = data.title;
				if( sName != null && $.trim(sName) != "" ){
					if(sName.length > 24){
						return sName.substring(0,19)+"...";
					}
				}
				return sName;
			}},
			{name:'content',width:300,formatter:function(data){
					if($.trim(data.content) == "" ){
						return "";
					}else{
						if(typeof data.content !== 'undefined'){
							if(data.content.length>30){
								return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content.substring(0,19)+'...</a>';
							}else{
								return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content+'</a>';
							}
						}else{
							return "";
						}
						//return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content+'</a>';
					}
				}},
			{name:'content',align:"center",width:50,formatter:function(data){
					if($.trim(data.content) == "" ){
						return 0;
					}else{
						return data.content.length;
					}
				}},
			{name:'createTime',align:"center",width:100}
		],
		buttons: [{
			text: "编辑发送",
			name: "edit",
			classes: "",
			click: function(){
				var url = '${ctx}/mbnSmsDraftAction/edit.action';
				editSmsFunc(smsGrid,url);
			}
		},{
			text: "删除",
			name: "delete",
			classes: "",
			click: function(){
				var url = '${ctx}/mbnSmsDraftAction/deleteByIds.action';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(smsGrid,url,showMessage);
			}
		},{
			text: "保存珍藏",
			classes: "",
			name: "collect",
			click: function(){
				var url = '${ctx}/mbnSmsDraftAction/collectByIds.action';
                collectSmsFunc(smsGrid,url);
			}
		}],
		multiselect: true
	};
	var smsGrid;
	$(function() {
		smsGrid = new TableGrid("smsGrid", gridPro);
		smsGrid.init();
	});
</script>
</head>
<body>
	<table id="smsGrid"></table>
</body>
</html>

