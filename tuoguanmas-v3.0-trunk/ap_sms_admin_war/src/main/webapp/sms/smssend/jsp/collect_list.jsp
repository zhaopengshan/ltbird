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

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信收件箱</title>

<script type="text/javascript">
	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/mbnSmsSelectedAction/getSmsDetails.action?selectedId=";
		$("#sms_hd_frame").load(localUrl+selectedId);
	}
	var gridPro = {
		url:"${ctx}/mbnSmsSelectedAction/listSmsSelected.action?searchAct=<%=searchAct%>&dateFrom=<%=dateFrom%>&dateTo=<%=dateTo%>",//执行地址
		colNames:['短信内容', '短信长度','珍藏时间'],//列名
		colModel:[//列所有名称的属性
			{name:'content',width:320,formatter:function(data){
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
					}
				}},
			{name:'content',width:70,align:"center",formatter:function(data){
					if($.trim(data.content) == "" ){
						return 0;
					}else{
						return data.content.length;
					}
				}},
			{name:'createTime',width:100,align:"center"}
		],
		buttons: [{//按钮
			text: "编辑发送",
			name:'edit',
			classes: "",
			click: function(){
				var url = '${ctx}/mbnSmsSelectedAction/edit.action';
				editSmsFunc(smsGrid,url);
			}
		},{
			text: "删除",
			name:'delete',
			classes: "",
			click: function(){
				var url = '${ctx}/mbnSmsSelectedAction/deleteByIds.action';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(smsGrid,url,showMessage);
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

