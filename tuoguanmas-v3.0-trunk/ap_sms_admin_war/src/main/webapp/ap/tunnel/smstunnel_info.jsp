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
	var sms_tunnel_url = '${ctx}/smsTunnelAction/listTunnelInfo.action',
		add_tunnel_url = '${ctx}/smsTunnelAction/addTunnelInfo.action',
		edit_tunnel_url = '${ctx}/smsTunnelAction/editTunnelInfo.action',
		get_tunnen_details = '${ctx}/smsTunnelAction/getTunnelDetails.action?forJson=1',
		smsTunnelGrid,form_url=add_tunnel_url,tunnelName;
	function showTunnelDetail(selectedId){
		var localUrl = "${ctx}/smsTunnelAction/getTunnelDetails.action?selectedId=" + selectedId,
			originalUrl = "./smsTunnelAction/initTunnelList.action",
	    	killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
	    try{
	    	tabpanel.kill(killId);
	    }catch(e){
	    }
	    var tabid=killId,
	    	tabTitle="短信通道",
        	navpathtitle="当前位置：通道管理";
        $("#navpath").html(navpathtitle);
        var tabContentClone = $(".tabContent").clone();
        var jcTabs = tabContentClone.removeClass("tabContent").load(localUrl);
	        tabpanel.addTab({
	        id: tabid,
	        title: tabTitle ,     
	        html:jcTabs,     
	        closable: true
        }); 
	}
	function edit_tunnel(rowId){
		$("#tunnelDialogLoad").load("${ctx}/smsTunnelAction/dialogInit.action?proName=1",function(){
			$.ajax({
	            url : get_tunnen_details,
	            type : 'post',
	            dataType: "json",
	            data: {
	            	selectedId: rowId
	            },
	            success : function(data) {
	                if(data.resultcode == "success"){
	                	//document.getElementById('tunnelProForm').reset();
	                	var tunnelObj = data.data;
	                	$("#pro_id").val(tunnelObj.id);
	                	$("#pro_name").val(tunnelObj.name);//.attr("readonly","readonly");
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
		                alert("出现系统错误，请稍后再试");
	                }
	            },
	            error : function() {
	                alert("出现系统错误，请稍后再试");
	            }
	        });
		});
	}
	var gridTunnelPro = {
		url: sms_tunnel_url,
		colNames:['通道名称','通道说明', '通道状态', '通道类型','通道属性','创建日期','上次修改时间','余量(条)','操作'],
		colModel:[
			{name:'name',align:"center",width:100,formatter:function(data){
					var edit = '<a href="javascript:void(0);" class="grid-sms" onclick="javascript:edit_tunnel(\''+data.id+'\');">'+data.name+'</a>';
					return  edit;
				}},
			{name:'description',align:"center",width:100},
			{name:'state',align:"center",width:100,formatter:function(data){
					var strState = "";
					switch(data.state){
						case 0: strState = "关闭"; break;
						case 1: strState = "可用"; break;
					}
					return strState;
				}},
			{name:'classify',align:"center",width:100,formatter:function(data){
					var strState = "";
					switch(data.classify){
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
			{name:'attribute',align:"center",width:100,formatter:function(data){
					var strState = "";
					switch(data.attribute){
						case 1: strState = "直连"; break;
						case 2: strState = "第三方"; break;
					}
					return  strState;
				}},
			{name:'createTime',align:"center",width:100},
			{name:'updateTime',align:"center",width:100},
			{name:'smsNumber',align:"center",width:100},
			{name:'id',align:"center",width:100,formatter:function(data){
				return '<a onclick="javascript:showTunnelDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">查看流水</a>';
			}}
		],
		buttons: [{
			text: "新增",
			classes: "",
			click: function(){ 
				$("#tunnelDialogLoad").load("${ctx}/smsTunnelAction/dialogInit.action?proName=0",function(){
					//document.getElementById("tunnelProForm").reset();
					//$("#pro_name").attr("readonly",false);
					form_url = add_tunnel_url;
					$('#tunnelPro').dialog('open');
				});
			}
		},{
			text: "删除",
			classes: "",
			click: function(){
				var url = '${ctx}/smsTunnelAction/deleteTunnelByIds.action';
				var showMessage = "是否删除选中的{0}条通道？";
                confirmAjaxFunc(smsTunnelGrid,url,showMessage);
			}
		}],
		multiselect: true,
		height: 380
	};
	$(function() {
		tunnelName = new PlaceHolder("tunnelName","输入通道名称...");
		tunnelName.init();
		tunnelName.refresh();
		smsTunnelGrid = new TableGrid("smsTunnelGrid",gridTunnelPro);
		smsTunnelGrid.redrawGrid(gridTunnelPro);
		
		$("#searchTunnelBtn").unbind("click").bind('click',function(){
			var url = sms_tunnel_url + "?tunnelName="+encodeURI(encodeURI(tunnelName.getValue()))
				+"&tunnelState="+($("#tunnelState").val() == -1?"":$("#tunnelState").val())
				+"&tunnelAttribute=" + ($("#tunnelAttribute").val() == -1?"":$("#tunnelAttribute").val())
				+"&tunnelClassify=" + ($("#tunnelClassify").val() == -1?"":$("#tunnelClassify").val())
				+"&tunnelProvince=" + ($("#tunnelProvince").val() == -1?"":$("#tunnelProvince").val());
			smsTunnelGrid.filterGrid({
				url: url
			});
		});
		/*$('#tunnelPro').dialog({
		    title: '短信通道属性',  
		    autoOpen: false,
		    width: 580,  
		    height: 420, 
		    modal: true,
		    resizable: false,
		    buttons:[{
				text:'保存',
				click:function(){
					var options = { 
						url: form_url,
						dataType:	'json',
				        beforeSubmit:  tunnelValidate,  // pre-submit callback 
				        success: showTunnelResponse  // post-submit callback 
				    };
					$('#tunnelProForm').ajaxSubmit(options); 
				}
			},{
				text:'关闭',
				click:function(){
					$('#tunnelPro').dialog('close');
				}
			}]
		});
		*/
	});
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
		<div class="toolbar" ><a href="javascript:void(0);" id="searchTunnelBtn"><img width="20px" height="20px" src="${ctx}/themes/mas3/images/search.gif"></a></div>
	</div>
	<div class="clear"></div>
	<div>
		<table id="smsTunnelGrid"></table>
	</div>
</div>
<div id="tunnelDialogLoad"></div>
</body>
</html>

