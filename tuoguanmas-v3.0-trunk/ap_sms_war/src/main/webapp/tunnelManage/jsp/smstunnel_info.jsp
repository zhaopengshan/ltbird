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
	var sms_tunnel_url = '${ctx}/smsTunnelAction/listTunnelInfo.action';
	var add_tunnel_url = '${ctx}/smsTunnelAction/addTunnelInfo.action';
	var edit_tunnel_url = '${ctx}/smsTunnelAction/editTunnelInfo.action';
	var get_tunnen_details = '${ctx}/smsTunnelAction/getTunnelDetails.action?forJson=1';
	var smsGrid,form_url=add_tunnel_url, tunnelName;
	function edit_tunnel(rowId){
		$.ajax({
            url : get_tunnen_details,
            type : 'post',
            dataType: "json",
            data: {
            	selectedId: rowId
            },
            success : function(data) {
                if(data.resultcode == "success"){
                	document.getElementById('proForm').reset();
                	var tunnelObj = data.data;
                	$("#pro_id").val(tunnelObj.id);
                	$("#pro_name").val(tunnelObj.name).attr("readonly",true);
                	$("#pro_accessNumber").val(tunnelObj.accessNumber);
                	$("#pro_corpExtLen").val(tunnelObj.corpExtLen);
                	$("#pro_gatewayAddr").val(tunnelObj.gatewayAddr);
                	$("#pro_gatewayPort").val(tunnelObj.gatewayPort);
                	$("#pro_user").val(tunnelObj.user);
                	$("#pro_passwd").val(tunnelObj.passwd);
                	$("#pro_description").val(tunnelObj.description);
                	$("#pro_smsSendPath").val(tunnelObj.smsSendPath);
                	$("#pro_smsReceivePath").val(tunnelObj.smsReceivePath);
                	$("#pro_smsReportPath").val(tunnelObj.smsReportPath);
                	$("#pro_state").val(tunnelObj.state);
                	$("#pro_classify").val(tunnelObj.classify);
                	$("#pro_province").val(tunnelObj.province);
                	$("#pro_attribute").val(tunnelObj.attribute);
					form_url = edit_tunnel_url;
                	$('#tunnelPro').dialog('open');
                }else{
	                $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
                }
            },
            error : function() {
                $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
            }
        }); 
	}
	var gridPro = {
		url: sms_tunnel_url,
		colNames:['id','通道名称','通道说明', '通道状态', '通道类型','通道属性','创建日期','上次修改时间','余量(条)','操作'],
		colModel:[
			{name:'id',index:'id', width:0,sortable: false, align: 'center',hidden: true},
			{name:'name',index:'name', width:40,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var edit = '<a href="javascript:void(0);" class="grid-sms" onclick="edit_tunnel(\''+rowObject.id+'\');">'+cellvalue+'</a>';
					return  edit;
				}},
			{name:'description',index:'description', width:90,sortable: false},
			{name:'state',index:'state', width:40,sortable: false,align: 'center',formatter:function(cellvalue, options, rowObject){
					var strState = "";
					switch(cellvalue){
						case 0: strState = "关闭"; break;
						case 1: strState = "可用"; break;
					}
					return strState;
				}},
			{name:'classify',index:'classify', width:40,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var strState = "";
					switch(cellvalue){
						case 1: strState = "本省移动"; break;
						case 2: strState = "移动"; break;
						case 3: strState = "本省联通"; break;
						case 4: strState = "联通"; break;
						case 5: strState = "本省电信"; break;
						case 6: strState = "电信"; break;
						case 7: strState = "全网"; break;
					}
					return strState;
				}},
			{name:'attribute',index:'attribute', width:40,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
					var strState = "";
					switch(cellvalue){
						case 1: strState = "直连"; break;
						case 2: strState = "第三方"; break;
					}
					return  strState;
				}},
			{name:'createTime',index:'createTime', width:60,sortable: false, align: 'center'},
			{name:'updateTime',index:'updateTime', width:60,sortable: false, align: 'center'},
			{name:'smsNumber',index:'smsNumber', width:40,sortable: false, align: 'center'},
			{name:'id',index:'id', width:40,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
				return '<a href="${ctx}/smsTunnelAction/getTunnelDetails.action?selectedId='+ rowObject.id +'" class="grid-sms">查看流水</a>';
			}}
		],
		pager: '#smsPage',
		buttons: [{
			text: "新增",
			classes: "tubh",
			click: function(){ 
				document.getElementById("proForm").reset();
				$("#pro_name").attr("readonly",false);
				form_url = add_tunnel_url;
				$('#tunnelPro').dialog('open');
			}
		},{
			text: "删除",
			classes: "tubh",
			click: function(){
				var url = '${ctx}/smsTunnelAction/deleteTunnelByIds.action';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(url,showMessage);
			}
		}]
	};
	
	$(function() {
		$("#searchBtn").bind('click',function(){
			var url = sms_tunnel_url + "?tunnelName="+encodeURI(encodeURI(tunnelName.getValue()))
				+"&tunnelState="+($("#tunnelState").val() == -1?"":$("#tunnelState").val())
				+"&tunnelAttribute=" + ($("#tunnelAttribute").val() == -1?"":$("#tunnelAttribute").val())
				+"&tunnelClassify=" + ($("#tunnelClassify").val() == -1?"":$("#tunnelClassify").val())
				+"&tunnelProvince=" + ($("#tunnelProvince").val() == -1?"":$("#tunnelProvince").val());
			smsGrid.filterGrid({
				url: url
			});
		});
		
		$('#tunnelPro').dialog({
		    title: '短信通道属性',  
		    width: 600,  
		    height: 380,  
		    closed: true,  
		    cache: false,  
		    modal: true,
		    buttons:[{
				text:'保存',
				handler:function(){
					$('#proForm').form('submit', {  
				        url: form_url,
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
								smsGrid.refresh();
								$('#tunnelPro').dialog('close');
							}
						}
				    }); 
				}
			},{
				text:'关闭',
				handler:function(){
					$('#tunnelPro').dialog('close');
				}
			}]
		});
		tunnelName = new PlaceHolder("tunnelName","输入通道名称...");
		tunnelName.init();
		tunnelName.refresh();
		smsGrid = new LeadToneGrid("smsGrid",gridPro);
		smsGrid.init();
	});
	 -->
</script>
</head>
<body>

<div>
	<div>
		<div class="toolbar" >
			<input id="tunnelName" value="输入通道名称..." name="title" type="text"/>
		</div>
		<div class="toolbar">
			<select  id="tunnelState" >
				<option selected="selected" value="-1">选择通道状态</option>
				<option value="1">可用</option>
				<option value="0">关闭</option>
			</select>
		</div>
		<div class="toolbar" >
			<select id="tunnelAttribute">
				<option selected="selected" value="-1">选择通道属性</option>
				<option value="2">第三方通道</option>
				<option value="1">直连通道</option>
			</select>
		</div>
		<div class="toolbar" >
			<select id="tunnelClassify">
				<option selected="selected" value="-1">选择通道类型</option>
				<option value="1">本省移动</option>
				<option value="2">移动</option>
				<option value="3">本省联通</option>
				<option value="4">联通</option>
				<option value="5">本省电信</option>
				<option value="6">电信</option>
				<option value="7">全网</option>
			</select>
		</div>
		<div class="toolbar" >
			<select id="tunnelProvince">
				<option value="-1">选择省份</option>
				<c:forEach items="${provinceList}" var="province">
					<option value="${province.coding}">${province.name}</option>
				</c:forEach>
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
<div id="tunnelPro">
<form id="proForm" method="post">
	<div class="config">
        <div class="config_left">
          <ul class="ms_config">
            <li><span class="name red_color">通道名称(唯一)：</span><span class="txt">
              <input id="pro_name" class="easyui-validatebox" name="smsMbnTunnelVO.name" required="true" validType="validateString" type="text"  />
            </span></li>
            <li><span class="name">通道状态：</span><span class="txt">
              <select id="pro_state" name="smsMbnTunnelVO.state">
				<option selected="selected" value="1">可用</option>
				<option value="0">关闭</option>
			  </select>
            </span></li>
            <li><span class="name red_color">通道类型：</span><span class="txt">
				<select id="pro_classify" name="smsMbnTunnelVO.classify" class="easyui-validatebox" required="true">
					<option value="1">本省移动</option>
					<option selected="selected" value="2">移动</option>
					<option value="3">本省联通</option>
					<option value="4">联通</option>
					<option value="5">本省电信</option>
					<option value="6">电信</option>
					<option value="7">全网</option>
				</select>
            </span></li>
            <li><span class="name red_color">省份：</span><span class="txt">
            	<select id="pro_province" name="smsMbnTunnelVO.province" class="easyui-validatebox" required="true">
					<c:forEach items="${provinceList}" var="province">
						<option value="${province.coding}">${province.name}</option>
					</c:forEach>
				</select>
            </span></li>
          </ul>
        </div>
        <div class="config_right">
          <ul class="ms_config">
            <li><span class="name red_color">特服号：</span><span class="txt">
              <input id="pro_accessNumber" name="smsMbnTunnelVO.accessNumber" class="easyui-validatebox" required="true" validType="validateIntegerLen[6]" type="text"  />
            </span></li>
            <li><span class="name red_color">扩展位数：</span><span class="txt">
              <input id="pro_corpExtLen" name="smsMbnTunnelVO.corpExtLen" class="easyui-validatebox" required="true" validType="validateIntegerLen[2]" type="text" />
            </span></li>
            <li><span class="name">通道属性：</span><span class="txt">
            	<select id="pro_attribute" name="smsMbnTunnelVO.attribute">
					<option selected="selected" value="2">第三方通道</option>
					<option value="1">直连通道</option>
				</select>
            </span></li>
            <li><span class="name">通道说明：</span><span class="txt">
              <textarea id="pro_description" name="smsMbnTunnelVO.description" cols="13" rows="3"></textarea>
            </span></li>
          </ul>
        </div>
      </div>
	<div class="field-group">
		<fieldset>
	        <div class="group_left">
	          <ul class="ms_config">
	            <li><span class="lname red_color">短信网关地址：</span><span class="txt">
	              <input id="pro_gatewayAddr" name="smsMbnTunnelVO.gatewayAddr" class="easyui-validatebox" required="true" validType="validateIPAddress" type="text"  />
	            </span></li>
	            <li><span class="lname red_color">短信网关发送URL：</span><span class="txt">
	              <input id="pro_smsSendPath" name="smsMbnTunnelVO.smsSendPath" class="easyui-validatebox" required="true" validType="validateURLAddress" type="text"  />
	            </span></li>
	            <li><span class="lname">短信网关接收URL：</span><span class="txt">
	              <input id="pro_smsReceivePath" name="smsMbnTunnelVO.smsReceivePath"  class="easyui-validatebox" validType="validateURLAddress" type="text"  />
	            </span></li>
	            <li><span class="lname">短信网关状态报告URL：</span><span class="txt">
	              <input id="pro_smsReportPath" name="smsMbnTunnelVO.smsReportPath"  class="easyui-validatebox" validType="validateURLAddress" type="text"  />
	            </span></li>
	          </ul>
	        </div>
	        <div class="group_right">
	          <ul class="ms_config">
	            <li><span class="name red_color">短信网关端口：</span><span class="txt">
	              <input id="pro_gatewayPort" name="smsMbnTunnelVO.gatewayPort" class="easyui-validatebox" required="true" validType="validateIntegerLen[4]" type="text"  />
	            </span></li>
	            <li><span class="name red_color">短信网关用户名：</span><span class="txt">
	              <input id="pro_user" name="smsMbnTunnelVO.user" class="easyui-validatebox" required="true" validType="validateString" type="text"  />
	            </span></li>
	            <li><span class="name red_color">短信网关密码：</span><span class="txt">
	              <input id="pro_passwd" name="smsMbnTunnelVO.passwd" class="easyui-validatebox" required="true" validType="validateString" type="text"  />
	            </span></li>
	          </ul>
	        </div>
	        <input id="pro_id" name="smsMbnTunnelVO.id" type="hidden"  />
	  	</fieldset>
	</div>
</form>
</div>
</body>
</html>

