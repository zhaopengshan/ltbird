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
	//返回路径
	var return_url = "${ctx}/smsTunnelAction/initTunnelList.action";
	var tunnel_flow = "${ctx}/smsTunnelAccountFlowAction/listTunnelFlow.action?tunnelId=${smsMbnTunnelVO.id}";
	var gridTunnelFlowPro = {
		url: tunnel_flow,
		colNames:['流水号','时间', '操作类型', '操作数量(条)','当前余量(条)','相关金额(元)','相关用户名'],
		colModel:[
			{name:'id',align:"center",width:100},
			{name:'modifyTime',align:"center",width:100},
			{name:'operationType',align:"center",width:100,formatter:function(data){
					var strState = "";
					switch(data.operationType){
						case 1: strState = "增加(向代理商购买)"; break;
						case 2: strState = "增加(代理商赠送)"; break;
						case 3: strState = "减少(代理商退款)"; break;
						case 4: strState = "减少(商户购买)"; break;
					}
					return strState;
				}},
			{name:'number',align:"center",width:100},
			{name:'balanceNumber',align:"center",width:100},
			{name:'amount',align:"center",width:100},
			{name:'modifyBy',align:"center",width:100}
		],
		multiselect: false,
		rows: 20,
		height: 310
	};
	var smsTunnelFlowGrid;
	$(function() {
		smsTunnelFlowGrid = new TableGrid("smsTunnelFlowGrid",gridTunnelFlowPro);
		smsTunnelFlowGrid.init();
		
		$("#searchBtn").unbind("click").bind('click',function(){
			var dateTlFlowFrom = $("#dateTlFlowFrom").val(),
				dateTlFlowTo = $("#dateTlFlowTo").val();
			if(dateISO(dateTlFlowFrom)&&dateISO(dateTlFlowTo)){
				var url = tunnel_flow + "&dateFrom="+dateTlFlowFrom
					+"&dateTo="+dateTlFlowTo
					+"&operationType=" + ($("#operationType").val() == -1?"":$("#operationType").val());
				smsTunnelFlowGrid.filterGrid({
					url: url
				});
			}else{
		        alert("请输入正确的日期格式，例如：2012-12-12");
			}
		});

		$("#returnTunnelBtn").unbind("click.rs").bind( "click.rs",function(){
			var localUrl = return_url;
			var originalUrl = "./smsTunnelAction/initTunnelList.action";
		    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
		    try{
		    	tabpanel.kill(killId);
		    }catch(e){
		    }
		    var tabid=killId;
		    var tabTitle="短信通道";
	         var navpathtitle="当前位置：通道管理";
	         $("#navpath").html(navpathtitle);
	         var tabContentClone = $(".tabContent").clone();
	         var jcTabs = tabContentClone.removeClass("tabContent").load(localUrl);
	         tabpanel.addTab({
	             id: tabid,
	             title: tabTitle ,     
	             html:jcTabs,     
	             closable: true
	         }); 
		});
		$("#rechargeBtn").unbind("click.rs").bind( "click.rs",function(){
			$("#tunnelFlowDialogLoad").load("${ctx}/ap/tunnel/smstunnel_flow_dialog.jsp",{tunnelFlowId: $("#tunnelFlowId").val()});
		});
	});
</script>
</head>
<body>

<div >
	<div class="field-group">
		<fieldset>
			<div >
	          <div class="tubh"><a id="returnTunnelBtn" href="javascript:void(0);">返回</a></div>
	        </div>
	        <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="lname">通道名称：</span><span class="">
	            	${smsMbnTunnelVO.name}
	            </span></li>
	            <li><span class="lname">当前余量(条)：</span><span id="smsNumber" class="">
	            	${smsMbnTunnelVO.smsNumber ==null?0:smsMbnTunnelVO.smsNumber}
	            </span></li>
	          </ul>
	        </div>
	        <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="name ">通道说明：</span><span class="">
	            	${smsMbnTunnelVO.description}
	            </span></li>
	            <li><span class="name ">通道状态：</span><span class="">
	            	<c:choose>
					<c:when test="${smsMbnTunnelVO.state == 0}">
						不可用
					</c:when>
					<c:when test="${smsMbnTunnelVO.state == 1}">
						可用
					</c:when>
					</c:choose>
	            </span></li>
	          </ul>
	        </div>
	        <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="name ">通道属性：</span><span class="">
	            	<c:choose>
					<c:when test="${smsMbnTunnelVO.attribute == 1}">
						直连网关
					</c:when>
					<c:when test="${smsMbnTunnelVO.attribute == 2}">
						第三方通道
					</c:when>
					</c:choose>
	            </span></li>
	            <li><span class="name ">通道类型：</span><span class="">
	            	<c:choose>
					<c:when test="${smsMbnTunnelVO.classify == 1}">
						本省移动
					</c:when>
					<c:when test="${smsMbnTunnelVO.classify == 2}">
						移动
					</c:when>
					<c:when test="${smsMbnTunnelVO.classify == 3}">
						本省联通
					</c:when>
					<c:when test="${smsMbnTunnelVO.classify == 4}">
						联通
					</c:when>
					<c:when test="${smsMbnTunnelVO.classify == 5}">
						本省电信
					</c:when>
					<c:when test="${smsMbnTunnelVO.classify == 6}">
						电信
					</c:when>
					<c:when test="${smsMbnTunnelVO.classify == 7}">
						全网
					</c:when>
					</c:choose>
	            </span></li>
	          </ul>
	        </div>
	        <div >
	          <div class="tubh"><a id="rechargeBtn" href="javascript:void(0);">充值</a></div>
	        </div>
	  	</fieldset>
	</div>
	<div >
		<div class="toolbar" >
			<span class="lname">起始日期：</span><span class="txt">
			<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="dateTlFlowFrom" />
            </span>
		</div>
		<div class="toolbar" >
			<span class="lname">结束日期：</span><span class="txt">
			<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="dateTlFlowTo" />
            </span>
		</div>
		<div class="toolbar" >
			<select id="operationType">
				<option selected="selected" value="-1">选择操作类型</option>
				<option value="1">增加(向代理商购买)</option>
				<option value="2">增加(代理商赠送)</option>
				<option value="3">减少(代理商退款)</option>
				<option value="4">减少(商户购买)</option>
			</select>
		</div>
		<div class="toolbar" ><a href="javascript:void(0);" id="searchBtn"><img width="20px" height="20px" src="${ctx}/themes/mas3/images/search.gif"></a></div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	<table id="smsTunnelFlowGrid"></table>
</div>
<input name="tempTunnelId" value="${smsMbnTunnelVO.id}" type="hidden" id="tunnelFlowId"/>
<div id="tunnelFlowDialogLoad"></div>

</body>
</html>

