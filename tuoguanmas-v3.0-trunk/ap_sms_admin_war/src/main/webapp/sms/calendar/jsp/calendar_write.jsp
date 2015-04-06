<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<script type="text/javascript">
var calendarNoticezNodes =[];
var log, className = "dark";

var calendarNoticetextChanged = false;
var calendarNoticetitleChanged = false;
var curCalendarPage = 1;
function calendarNoticeaddGroupCounts(treeId, treeNode) {
	if(treeNode.isParent  ==true){
		if(!isNaN(treeNode.id)){
			var aObj = jQuery("#" + treeNode.tId + "_a");
			var editStr1 = "<a  onclick=\"calendarNoticeaddToReceiver('"+treeNode.name+"');return false;\"></a>" ;//("+treeNode.counts+")
			aObj.after(editStr1);
		}
	}
	if (treeNode.level>0) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addBtn_"+treeNode.id).length>0) return;
	var addStr = "<span class='button lastPage' id='lastBtnCalendar_" + treeNode.id
		+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnCalendar_" + treeNode.id
		+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnCalendar_" + treeNode.id
		+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnCalendar_" + treeNode.id
		+ "' title='first page' onfocus='this.blur();'></span>";
	aObj.after(addStr);
	var first = $("#firstBtnCalendar_"+treeNode.id);
	var prev = $("#prevBtnCalendar_"+treeNode.id);
	var next = $("#nextBtnCalendar_"+treeNode.id);
	var last = $("#lastBtnCalendar_"+treeNode.id);
	treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
	first.bind("click", function(){
		if (!treeNode.isAjaxing) {
			goCalendarPage(treeNode, 1);
		}
	});
	last.bind("click", function(){
		if (!treeNode.isAjaxing) {
			goCalendarPage(treeNode, treeNode.maxPage);
		}
	});
	prev.bind("click", function(){
		if (!treeNode.isAjaxing) {
			goCalendarPage(treeNode, treeNode.page-1);
		}
	});
	next.bind("click", function(){
		if (!treeNode.isAjaxing) {
			goCalendarPage(treeNode, treeNode.page+1);
		}
	});
}

function getCalendarUrl(treeId, treeNode) {
	if(treeNode == null){
		return "${ctx}/addr/getAddr.action";
	}else{
		var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
		aObj = $("#" + treeNode.tId + "_a");
		aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
		return "${ctx}/addr/getAddr.action?" + param;
	}
	
}
function goCalendarPage(treeNode, page) {
	treeNode.page = page;
	if (treeNode.page<1) treeNode.page = 1;
	if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
	if (curCalendarPage == treeNode.page) return;
	curCalendarPage = treeNode.page;
	var zTree = $.fn.zTree.getZTreeObj("calendarNoticetreeDemo");
	zTree.reAsyncChildNodes(treeNode, "refresh");
}

function calendarNoticefilter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		if( childNodes[i] && childNodes[i].name){
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
	}
	return childNodes;
}
function calendarNoticebeforeClick(treeId, treeNode, clickFlag) {
	className = (className === "dark" ? "":"dark");
	return (treeNode.click != false);
}
function calendarNoticeonClick(event, treeId, treeNode, clickFlag) {
	calendarNoticeaddToReceiver(treeNode.name);
}		

function calendarNoticeaddToReceiver(recv){
	if( calendarNoticetextChanged){
		$('#calendarNoticereceiver').attr({ value: $('#calendarNoticereceiver').attr("value")+","+recv });
	}
	else{
		$('#calendarNoticereceiver').attr({ value: recv });
	}
	calendarNoticetextChanged = true;
}
function calendarNoticeaddToCycle(sendType,selectCycle,selectCycleHour,selectCycleMinutes){
	//"${sendType}","${selectCycle}","${selectCycleHour}","${selectCycleMinutes}"
	$("input[name=sendType]:eq("+sendType+")").attr("checked",'checked');
	$('#selectCycle')[0].selectedIndex = selectCycle;

	initCycleSelect();
	$("#selectCycleHour option[text="+selectCycleHour+"]").attr("selected", true);
	$("#selectCycleMinutes option[text="+selectCycleMinutes+"]").attr("selected", true);
}
function calendarNoticecheckUserAddr(userAddr){
	if(!userAddr || userAddr==""){
		return false;
	}
	var reg0 = /^1\d{10}\s*(<.*>|\s*)$/;
	var reg1 = /^.*\s*<用户组>$/;
	if( reg0.test(userAddr) || reg1.test(userAddr)){
		return true;
	}
	return false;
}
// 检查输入
function calendarNoticecheckInput(){
	if( ($('#calendarNoticetitle').attr("value") == '输入日程提醒任务名称，仅作为查询依据' || $('#calendarNoticetitle').attr("value") == "")){
		alert('任务名称为空');
		return false;
	}
	if( (!calendarNoticetextChanged || $('#calendarNoticereceiver').attr("value") == "" || $('#calendarNoticereceiver').attr("value") == "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送") && $('#calendarNoticeinputfile').val() == ""){
        alert('接收人为空');
        return false;
	}
	// 检查收件人
	if( $('#calendarNoticeinputfile').val() == ""){
		if( !calendarNoticetextChanged){
			$('#calendarNoticereceiver').attr("value","");
		}
	}else if( calendarNoticetextChanged){
		var strs= new Array(); 
		strs=$('#calendarNoticereceiver').attr("value").split(",");
		var result=true;
		for( var i=0; i<strs.length; i++){
			if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
			result = calendarNoticecheckUserAddr(strs[i]);
			if(!result){
	            alert('地址不合法 [' + strs[i] + ']');
	        	return false;
			}
		}			
	}
	if( $('#calendarNoticesmsText').attr("value") == "" || $.trim( $('#calendarNoticesmsText').attr("value") ) == ""){
        alert('短信内容为空');
        return false;
	}
	if( $("#calendarNoticesendAtTime").attr("checked") == 'checked'){
		if( $("#calendarNoticesendTime").attr("value") == ""){
            alert('定时时间为空');
            return false;
		}
	}
	$("#waitTipCalendar").removeClass("ui-helper-hidden");
	return true;
}

function calendarNoticereceiverEventProc(event){
    if (event.type == "focusin" && !calendarNoticetextChanged) {
        $(this).attr({ value: "" });
    } else if (event.type == "focusout" && $(this).attr("value") == "") {
    	calendarNoticetextChanged = false;
        $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
    } else {
        switch(event.which) {
            case 27:
            	calendarNoticetextChanged = false;
                $(this).attr("value", "");
                $(this).blur();
                break;
            default:
            	calendarNoticetextChanged = true;
        }
        if($(this).attr("value"))
            if($(this).attr("value")=="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送"){
              $(this).attr("value", "");
                 //$(this).blur();
            }
    }
}
function calendarNoticetitleEventProc(event){
    if (event.type == "focusin" && !calendarNoticetitleChanged) {
        $(this).attr({ value: "" });
    } else if (event.type == "focusout" && $(this).attr("value") == "") {
    	calendarNoticetitleChanged = false;
        $(this).attr({ value: "输入日程提醒任务名称，仅作为查询依据" });
    } else {
        switch(event.which) {
            case 27:
            	calendarNoticetitleChanged = false;
                $(this).attr("value", "");
                $(this).blur();
                break;
            default:
            	calendarNoticetitleChanged = true;
        }
        if($(this).attr("value"))
            if($(this).attr("value")=="输入日程提醒任务名称，仅作为查询依据"){
              $(this).attr("value", "");
                 //$(this).blur();
            }
    }
}
function calendarNoticesaveEventProc(event){
    if( $("#calendarNoticesmsText").attr("value") == "" || $.trim( $("#calendarNoticesmsText").attr("value") ) == ""){
        alert('短信内容为空');
        return;
    }
   	$.ajax({
        url : event.data.url,
        type : 'post',
        dataType: "json",
        data: {
        	smsText: $("#calendarNoticesmsText").attr("value"),
        	title: $("#calendarNoticetitle").attr("value")
        },
        success : function(data) {
            if(data.resultcode == "success"){
                alert(data.message);
            } else {
                alert(data.message);
            }
        },
        error : function() {
            alert("出现系统错误，请稍后再试");
        }
    }); 
}

function calendarNoticesmsTextEventProc(){
	//var extLen = $("#calendarNoticereplyText").val().length;
	var extLen2 = $("#entSign").val().length;
	var len =$(this).val().length  + extLen2;
	//var len =$(this).val().length;// + extLen2;
    var remain = 335 - len;
    var cnt
    if(len<=70){
    	cnt=1;
    }else{
    	cnt = Math.ceil(len/67);  
    }
    
    if( remain<0 ){
    	$("#calendarNoticesmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
   	}else{
        $("#calendarNoticesmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
        		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
    }
}

function calendarNoticesetRemainNumber(event){
	var selTunnelId=$("#"+event.data.sel).val();
	$.ajax({
        url : event.data.url,
        type : 'post',
        dataType: "json",
        data: {
			selTunnelId: selTunnelId
        },
        success : function(data) {
            if(data && data.resultcode == "success"){
            	$("#"+event.data.tip).text(data.remain);
            }
            else{
            	alert("出现系统错误，请稍后再试");
            }
        },
        error : function() {
            alert("出现系统错误，请稍后再试");
        }
    }); 
}
function calendarNoticeselectFile(){
	$("#calendarNoticereceiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
}

/**
*
* 提交表单成功后处理方法
*
*/  
function calendarNoticesendSms(responseText, statusText, xhr, $form){
	
	if(responseText){
		if(responseText.resultcode == "success"){
			$("#waitTipCalendar").addClass("ui-helper-hidden");
			var originalUrl = "./sms/calendar/jsp/calendar_list.jsp";
	        var tempUrl = "./sms/calendar/jsp/calendar_list.jsp";
	        var localUrl = "./calendar/writesms.action";
	        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
	        tabpanel.kill(_killId, true);
	        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		}else{
			alert(responseText.message);
			$("#waitTipCalendar").addClass("ui-helper-hidden");
		}
	}else{
		alert(responseText.message);
		$("#waitTipCalendar").addClass("ui-helper-hidden");
	}
}

var calendarNoticesetting = {
	view: {
		showIcon: false,
		addDiyDom: calendarNoticeaddGroupCounts,
		fontCss : {"font-size": "14px", color: "#222222", "font-family": "微软雅黑"}
	},
	data: {
		key: {
			title:"mobile"
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: calendarNoticebeforeClick,
		onClick: calendarNoticeonClick
	},
	async: {
		enable: true,
		//url:"${ctx}/addr/getAddr.action",
		url: getCalendarUrl,
		autoParam:["id", "name=n", "level=lv"],
		otherParam:{"otherParam":"zTreeAsyncTest"},
		dataFilter: calendarNoticefilter
	}
};

function initCycleSelect(){
	$("#selectCycleHour").empty();
	var selectHourRange="";
	for(var i=0;i<24;i++){
		selectHourRange+="<option id='cycleHour"+i+"'>"+i+"</option>";
	}
	$(selectHourRange).appendTo("#selectCycleHour");
	$('#selectCycleMonth').css({"display": "none"});
	$('#selectCycleWeek').css({"display": "none"});
	$("#cycleSelectTable tr").find('td:eq(2)').hide();
	$("#cycleSelectTable tr").find('td:eq(3)').hide();
	$('#selectCycle')[0].selectedIndex = 0;//将周期选择设为天

	$('#selectCycle').unbind('change').change(function(){
		var selectId= $("#selectCycle option:selected").get(0).id;
		if(selectId=='day'){//每天
			//隐藏周及月选项
			$('#selectCycleMonth').css({"display": "none"});
			$('#selectCycleWeek').css({"display": "none"});
			$("#cycleSelectTable tr").find('td:eq(2)').hide();
			$("#cycleSelectTable tr").find('td:eq(3)').hide();
		}else if(selectId=='week'){//每周
			//隐藏月选项
			$('#selectCycleMonth').css({"display": "none"});
			$('#selectCycleWeek').css({"display": "block"});
			$("#cycleSelectTable tr").find('td:eq(2)').hide();
			$("#cycleSelectTable tr").find('td:eq(3)').show();
			var selectWeekRange=new Array();
			selectWeekRange[0]='周一';
			selectWeekRange[1]='周二';
			selectWeekRange[2]='周三';
			selectWeekRange[3]='周四';
			selectWeekRange[4]='周五';
			selectWeekRange[5]='周六';
			selectWeekRange[6]='周日';
			var selectWeeks="";
			for(var i=0;i<selectWeekRange.length;i++){
				selectWeeks+="<option>"+selectWeekRange[i]+"</option>";
			}
			$(selectWeeks).appendTo("#selectCycleWeek");
			
		}else{//每月
			//隐藏
			$('#selectCycleMonth').css({"display": "block"});
			$('#selectCycleWeek').css({"display": "none"});
			$("#cycleSelectTable tr").find('td:eq(2)').show();
			$("#cycleSelectTable tr").find('td:eq(3)').hide();
			selectHourRange="";
			for(var i=1;i<32;i++){
				selectHourRange+="<option>"+i+"号</option>";
			}
			$(selectHourRange).appendTo("#selectCycleMonth");
		}
	});
}


function initWriteCalendar(){
	$.fn.zTree.init($("#calendarNoticetreeDemo"), calendarNoticesetting, calendarNoticezNodes);
	$('#calendarNoticesmsText').unbind('keyup keypress change').bind('keyup keypress change', calendarNoticesmsTextEventProc);
	$("#calendarNoticesendAtTime").unbind("click").click(function(){
			$("#calendarNoticesendTime").css({"display": "inline"});
			initCycleSelect();
	});
	$("#calendarNoticesendNow").unbind('click').click(function(){
		$("#calendarNoticesendTime").css({"display": "none"});
		initCycleSelect();
	});
	$("#calendarNoticesendAtCycle").unbind('click').click(function(){
		$("#calendarNoticesendTime").css({"display": "none"});
	});
	initCycleSelect();//初始化周期信息
	$('#calendarNoticereceiver').unbind("keyup focusin focusout").bind("keyup focusin focusout", calendarNoticereceiverEventProc);
	$('#calendarNoticetitle').unbind("keyup focusin focusout").bind("keyup focusin focusout", calendarNoticetitleEventProc);
	$("#calendarNoticesendButton").unbind("click").bind("click",function(){
    	var options = { 
  		        beforeSubmit:  calendarNoticecheckInput,  // pre-submit callback 
  		        success:       calendarNoticesendSms,  // post-submit callback 
  		        dataType:		'json'
  		}; 
    	$("#calendarNoticesmsForm").ajaxSubmit(options); 
    });
    $("#calendarNoticecancelButton").unbind("click").bind("click",function(){
    	$("#calendarNoticesmsForm")[0].reset();
    });
    $("#calendarNoticesaveButton").unbind("click").bind("click",{url: "${ctx}/calendarSmsDraftAction/saveDraft.action"}, calendarNoticesaveEventProc);
	
    
	$("#calendarNoticeydTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ydTunnel', tip:'ydTip'}, calendarNoticesetRemainNumber);
	$("#calendarNoticeltTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ltTunnel', tip:'ltTip'}, calendarNoticesetRemainNumber);
	$("#calendarNoticedxTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'dxTunnel', tip:'dxTip'}, calendarNoticesetRemainNumber);

    $( "#calendarNoticequery" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "${ctx}/addr/getAddrByKey.action",
                dataType: "json",
                data: {
                    featureClass: "P",
                    style: "full",
                    maxRows: 12,
                    term: request.term
                },
                success: function( data ) {
                    response( $.map( data.addrs, function( item ) {
                        return {
                            label: item.name,
                            value: item.name
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {
        	calendarNoticeaddToReceiver(ui.item.value);
        }
    });
    
	<c:if test="${ not empty entityMap}">
	    alert('${entityMap["message"]}');
	</c:if>
	<c:if test="${ not empty smsText}">
		$('#calendarNoticesmsText').attr("value","${smsText}");
		$('#calendarNoticesmsText').trigger("keyup");
	</c:if>
	<c:if test="${ not empty title}">
		$('#calendarNoticetitle').attr("value","${title}");
		 $("#calendarNoticereceiver").focus();
	</c:if>
	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
		$("#calendarNoticeselfsend").unbind("click").bind("click", function(){calendarNoticeaddToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
	</c:if>
	//selectCycle
	<c:if test="${ not empty sendType}">
	calendarNoticeaddToCycle("${sendType}","${selectCycle}","${selectCycleHour}","${selectCycleMinutes}");
		//alert("${selectCycle}");
	</c:if>

}
$(document).ready(function(){
	initWriteCalendar();
});
	</script>
</head>
<body>
<div class="main_body" style="overflow-y: auto;overflow-x: hidden;hight: 400px;">
<div class="contents" >
  <form id="calendarNoticesmsForm" action="${ctx }/calendar/calendarSend.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents" >
	        <div class="table_box">
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>任务名称：</td>
	              <td width="91%"><input id="calendarNoticetitle" name="title" type="text" class="input2" value="输入日程提醒任务名称，仅作为查询依据"/></td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>接收人：</td>
	              <td width="91%"><input id="calendarNoticereceiver" name="receiver" type="text" class="input2" value="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" /></td>
	            </tr>
	            <tr>
	              <td align="right" valign="top"><span style="color:#FF0000">*</span>提醒内容：</td>
	              <td><textarea id="calendarNoticesmsText" name="smsText" style="width: 98%; height: 90px; font-size: 12px;"></textarea>
	                <div id="calendarNoticesmsTips" class="tixing_tab">&nbsp;&nbsp;&nbsp;还可以输入335字，本次将以0条计费&nbsp;短信共0字(含企业签名)，分0条发送</div>
	              </td>
	            </tr>
	            <tr>
					<td width="12%" height="50" align="right">企业签名：</td>
					<td width="91%">
					<input id="entSign" class="input_entSign" type="text" value="${sessionScope.ent_sms_sign}" readonly="readonly" name="entSign">
					</td>
				</tr>
				<tr>
					<td style="height:18px; line-height: 18px;" align="right"></td>
					<td style="height:18px; line-height: 18px;" >
					  <span style="color:#FF0000">*</span>如果用户收到的短信后缀签名与平台不符，请联系客户经理或者平台管理员修改短信后缀签名
					</td>
				</tr>
	            <tr>
	              <td align="right">提醒方式：</td>
	              <td>
	              	<table id="calendarSelect" >
	              		<tr><td><input type="radio" id="calendarNoticesendNow" name="sendType" value="NOW" checked="checked" />立即提醒</td></tr>
	              		<tr><td><input type="radio" id="calendarNoticesendAtTime" name="sendType" value="TIMER"/>定时提醒
				 		<input type="text" name="sendTime" id="calendarNoticesendTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});" class="Wdate" style="display:none;"/></td></tr>
	              		<tr><td>
	              		<table id="cycleSelectTable" style="width:450px;">
	              		<tr>
	              		<td style="width: 70px;">
	              		<input type="radio" id="calendarNoticesendAtCycle" name="sendType" value="CYCLE"/>周期提醒
	              		</td>
	              		<td>
	              		<select id="selectCycle" name="selectCycle">
	              		<option id="day">每天</option>
	              		<option id="week">每周</option>
	              		<option id="month">每月</option>
	              		</select>
	              		</td>
	              		<td>
	              		<select id="selectCycleMonth" name="selectCycleMonth" style="display:none"></select>
	              		</td>
	              		<td>
	              		<select id="selectCycleWeek" name="selectCycleWeek" style="display:none"></select>
	              		</td>
	              		<td>
	              		<select id="selectCycleHour" name="selectCycleHour"></select>时
	              		</td>
	              		<td>
	              		<select id="selectCycleMinutes" name="selectCycleMinutes">
	              		<option>0</option>
	              		<option>15</option>
	              		<option>30</option>
	              		<option>45</option>
	              		</select>分
	              		</td>
	              		<td style="width: 120px;">
	              		&nbsp;&nbsp;提醒次数<input type="text" name="remindCounts" id="calendarRemindCounts" style="width:50px;"/>
	              		</td>
	              		</tr>
	              		</table>
	              		</td></tr>
	              	</table>
				  </td>
	            </tr>
	            <!--<tr style="display: none">-->
	            <tr style="display:none;">
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>通道选择：</td>
	              <td width="91%">
	             	 移动
					<select id="calendarNoticeydTunnel" name="ydTunnel" style="width:220px;">
						<c:set var="ydTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 1) or (tunnel.classify eq 2) or (tunnel.classify eq 7)}">
								<c:if test="${(ydTunnelNumber eq '-1000')}">
									<c:set var="ydTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}" <c:if test="${(tunnel.id eq ydTunnel)}"><c:set var="ydTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }
								</option></c:if>
						</c:forEach>
					</select>
					通道可用余量<span id="calendarNoticeydTip">${ydTunnelNumber}</span>条
					<br/>
					联通
					<select id="calendarNoticeltTunnel" name="ltTunnel" style="width:220px;">
						<c:set var="ltTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 3) or (tunnel.classify eq 4) or (tunnel.classify eq 7)}">
								<c:if test="${(ltTunnelNumber eq '-1000')}">
									<c:set var="ltTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}"  <c:if test="${(tunnel.id eq ltTunnel)}"><c:set var="ltTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }</option>
								</c:if>
						</c:forEach>
					</select>
					通道可用余量<span id="calendarNoticeltTip">${ltTunnelNumber}</span>条
					<br/>
					电信
					<select id="calendarNoticedxTunnel"  name="dxTunnel" style="width:220px;">
						<c:set var="dxTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 5) or (tunnel.classify eq 6) or (tunnel.classify eq 7)}">
								<c:if test="${(dxTunnelNumber eq '-1000')}">
									<c:set var="dxTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}" <c:if test="${(tunnel.id eq dxTunnel)}"><c:set var="dxTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }</option>
								</c:if>
						</c:forEach>
					</select>
					通道可用余量<span id="calendarNoticedxTip">${dxTunnelNumber}</span>条
					<br/>
					注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送
				  </td>
	            </tr>
	            <tr class="ui-helper-hidden" id="waitTipCalendar">
					<td width="12%" height="50" align="right"></td>
		            <td height="50" ><span id="loadingTip" class="needtip"><img src="themes/mas3admin/images/helper/loading.gif"/>正在提交网关，请耐心等待。请勿重复执行此操作！</span></td>
		        </tr>
	          </table>
	        </div>
	        <div class="bottom_box">
	          <div class="tubh" id="calendarNoticesendButton"><a href="javascript:void(0);">发送</a></div>
	          <div class="tubh" id="calendarNoticecancelButton"><a href="javascript:void(0);">重置</a></div>
	        </div>
	      </div>

      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="calendarNoticequery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer; position:relative;">
	        	<div id="calendarNoticeinputFileContainer">
					<input type="file" id="calendarNoticeinputfile" onchange="calendarNoticeselectFile();" class="hide_input_file" name="addrUpload" style="width:200px;" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png" style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="calendarNoticeselfsend"><img src="./themes/mas3/images/user.png"  style="margin-right:5px;"/>发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 0px; margin-top: -5px;">
          		<ul id="calendarNoticetreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;padding-left:0px;"></ul>
          	</div>
          <div class="box_bottom"><img src="./themes/mas3/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
    </tr>
  </table>
  </form>
</div>
</div>
</body>
</html>
