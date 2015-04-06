<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信通道</title>
<script type="text/javascript">
	var mmsGrid;
	var gridTunnelPro = {
		url: "./mmsAction/listMms.action",
		colNames:['状态','彩信主题','创建人', '彩信大小(K)', '发送时间'],
		colModel:[
			{name:'sendResult',width:50,align:"center",formatter:function(data){
				var iconSms = '<img title="未发送" src="${ctx}/themes/mas3/images/u16220_normal.png" width="15" height="13">';
				switch(parseInt(data.sendResult,10)){
					case -1: iconSms = '<img title="已取消" src="${ctx}/themes/mas3/images/sms_cancel_normal.png" width="15" height="16">'; break;
					case 0: iconSms = '<img title="未发送" src="${ctx}/themes/mas3/images/u16220_normal.png" width="13" height="16">'; break;
					case 1: iconSms = '<img title="已提交网关" src="${ctx}/themes/mas3/images/u1223_normal.png" width="15" height="13">'; break;
				}
				return iconSms;
			}},
			{name:'mmses.title',align:"center",width:270},
			{name:'mmses.createUser.name',align:"center",width:100},
			{name:'mmses.attachmentSize',align:"center",width:80},
			{name:'readySendTime',align:"center",width:100}
		],
		buttons: [{
			text: "删除",
			classes: "",
			click: function(){
				var url = '${ctx}/mmsAction/deleteMmsByIds.action';
				var showMessage = "是否删除选中的{0}条彩信？";
                confirmAjaxFunc(mmsGrid,url,showMessage);
			}
		} /* ,{
			text: "取消发送",
			classes: "",
			name: "cancel",
			click: function(){
				var url = '${ctx}/mmsAction/cancelSendByIds.action';
				var showMessage = "是否取消发送选中的{0}条彩信？";
                confirmAjaxFunc(mmsGrid,url,showMessage);
			} 
		} */],
		multiselect: true,
		height: 400
	};
	$(function() {
		mmsGrid = new TableGrid("mmsGrid",gridTunnelPro);
		mmsGrid.redrawGrid(gridTunnelPro);
	});
</script>
</head>
<body>
	<table id="mmsGrid"></table>
</body>
</html>

