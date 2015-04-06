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
<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/easyui/easyui.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/ui.jqgrid.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/leadtone.grid.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="${ctx }/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="javascript" src="${ctx }/js/i18n/grid.locale-cn.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.easyui.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.easyui.validateextend.js" type="text/javascript"></script>
<script language="javascript" src="${ctx }/js/i18n/easyui-lang-zh_CN.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/json2.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.jqGrid.src.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.LeadToneGrid.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/smssend/js/sms_info.js" type="text/javascript" ></script>
<script type="text/javascript">
<!-- 
	//返回路径
	var return_url = "${ctx}/smsTunnelAction/initTunnelList.action";
	var tunnel_flow = "${ctx}/smsTunnelAccountFlowAction/listTunnelFlow.action?tunnelId=${smsMbnTunnelVO.id}";
	var gridPro = {
		url: tunnel_flow,
		colNames:['id','流水号','时间', '操作类型', '操作数量(条)','当前余量(条)','相关金额(元)','相关用户名'],
		colModel:[
			{name:'id',index:'id', width:0,sortable: false, align: 'center',hidden: true},
			{name:'id',index:'id', width:40,sortable: false, align: 'center'},
			{name:'modifyTime',index:'modifyTime', width:90,sortable: false, align: 'center'},
			{name:'operationType',index:'operationType', width:40,sortable: false,formatter:function(cellvalue, options, rowObject){
					var strState = "";
					switch(cellvalue){
						case 1: strState = "增加(向代理商购买)"; break;
						case 2: strState = "增加(代理商赠送)"; break;
						case 3: strState = "减少(代理商退款)"; break;
						case 4: strState = "减少(商户购买)"; break;
					}
					return strState;
				}},
			{name:'number',index:'number', width:40,sortable: false, align: 'center'},
			{name:'balanceNumber',index:'balanceNumber', width:40,sortable: false, align: 'center'},
			{name:'amount',index:'amount', width:40,sortable: false, align: 'center'},
			{name:'modifyBy',index:'modifyBy', width:40,sortable: false, align: 'center'}
		],
		height: 250,
		multiselect: false,
		pager: '#smsPage'
	};
	var smsGrid;
	$(function() {
		smsGrid = new LeadToneGrid("smsGrid",gridPro);
		smsGrid.init();
		$('#rechargeDialog').dialog({
		    title: '短信通道充值',  
		    width: 400,  
		    height: 180,  
		    closed: true,  
		    cache: false,  
		    modal: true,
		    buttons:[{
				text:'确定',
				handler:function(){
					$('#rechargeForm').form('submit', {  
				        url: "${ctx}/smsTunnelAccountFlowAction/addTunnelAccountFlow.action",
						onSubmit:function(){
							return $(this).form('validate');
						},
						success:function(data){
							var resultObj = jQuery.parseJSON(data);
							$.messager.show({
			                    title: '用户操作',
			                    msg:resultObj.message,
			                    timeout:5000
			                });
			                if(resultObj.resultcode == "success"){
			                	$("#smsNumber").text(resultObj.balanceNumber);
								smsGrid.refresh();
								$('#rechargeDialog').dialog('close');
							}
						}
				    }); 
				}
			},{
				text:'关闭',
				handler:function(){
					$('#rechargeDialog').dialog('close');
				}
			}]
		});
		$("#searchBtn").bind('click',function(){
			var dateFrom = $("#dateFrom").datebox("getValue"),
				dateTo = $("#dateTo").datebox("getValue");
			if(dateISO(dateFrom)&&dateISO(dateTo)){
				var url = tunnel_flow + "&dateFrom="+dateFrom
					+"&dateTo="+dateTo
					+"&operationType=" + ($("#operationType").val() == -1?"":$("#operationType").val());
				smsGrid.filterGrid({
					url: url
				});
			}else{
				$.messager.show({
		            title: '警告',
		            msg:"请输入正确的日期格式，例如：2012-12-12",
		            timeout:5000
		        });
			}
		});
		$("#dateFrom").datebox({  
		});
		$("#dateTo").datebox({  
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			window.location.href = return_url;
		});
		$("#rechargeBtn").unbind("click.rs").bind( "click.rs",function(){
			$('#rechargeDialog').dialog('open');
		});
	});
	 -->
</script>
</head>
<body>

<div >
	<div class="field-group">
		<fieldset>
			<div >
	          <div class="tubh"><a id="returnBtn" href="javascript:void(0);">返回</a></div>
	        </div>
	        <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="lname">通道名称：</span><span class="txt">
	            	${smsMbnTunnelVO.name}
	            </span></li>
	            <li><span class="lname">当前余量(条)：</span><span id="smsNumber" class="txt">
	            	${smsMbnTunnelVO.smsNumber ==null?0:smsMbnTunnelVO.smsNumber}
	            </span></li>
	          </ul>
	        </div>
	        <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="name ">通道说明：</span><span class="txt">
	            	${smsMbnTunnelVO.description}
	            </span></li>
	            <li><span class="name ">通道状态：</span><span class="txt">
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
	            <li><span class="name ">通道属性：</span><span class="txt">
	            	<c:choose>
					<c:when test="${smsMbnTunnelVO.attribute == 1}">
						直连网关
					</c:when>
					<c:when test="${smsMbnTunnelVO.attribute == 2}">
						第三方通道
					</c:when>
					</c:choose>
	            </span></li>
	            <li><span class="name ">通道类型：</span><span class="txt">
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
				<input id="dateFrom" type="text" />
	            </span>
		</div>
		<div class="toolbar" >
			<span class="lname">结束日期：</span><span class="txt">
				<input id="dateTo" type="text" />
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
		<div class="toolbar" ><a href="javascript:void(0);" id="searchBtn"><img width="20px" height="20px" src="/ap_sms_war/images/search.gif"></a></div>
		<div class="clear"></div>
	</div>
	<div >
		<table id="smsGrid"></table>
		<div id="smsPage"></div>
	</div>
</div>
<div id="rechargeDialog">
<form id="rechargeForm" method="post">
	<div class="recharge">
		<ul class="ms_config">
			<li><span class="">操作类型：</span><span class="txt">
				<select name="smsMbnTunnelAccountFlow.operationType">
					<option value="1">增加(向代理商购买)</option>
					<option value="2">增加(代理商赠送)</option>
					<option value="3">减少(代理商退款)</option>
				</select>
			</span></li>
			<li><span class="">操作数量：</span><span class="txt">
				<input id="pro_user" name="smsMbnTunnelAccountFlow.number" class="easyui-validatebox" required="true" validType="validateIntegerLen[11]" type="text"  />
			</span><span class="">条   (注：输入大于等于0的整数)</span></li>
			<li><span class="">相关金额：</span><span class="txt">
				<input id="pro_passwd" name="smsMbnTunnelAccountFlow.amount" class="easyui-validatebox" required="true" validType="validatePositiveFloat" type="text"  />
			</span><span class="">元   (注：只填金额数，不填正负)</span></li>
		</ul>
	</div>
	<input name="smsMbnTunnelAccountFlow.tunnelId" value="${smsMbnTunnelVO.id}" type="hidden" />
</form>
</div>

</body>
</html>

