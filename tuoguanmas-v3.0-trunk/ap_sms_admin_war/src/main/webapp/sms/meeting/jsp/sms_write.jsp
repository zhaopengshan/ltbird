<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    </head>
	<script type="text/javascript">
	var curMeetingPage = 1;
	function meetNoticeaddGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"meetNoticeaddToReceiver('"+treeNode.name+"');return false;\"></a>";//("+treeNode.counts+")
				aObj.after(editStr1);
			}
		}
		if (treeNode.level>0) return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button lastPage' id='lastBtnMeeting_" + treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnMeeting_" + treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnMeeting_" + treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnMeeting_" + treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtnMeeting_"+treeNode.id);
		var prev = $("#prevBtnMeeting_"+treeNode.id);
		var next = $("#nextBtnMeeting_"+treeNode.id);
		var last = $("#lastBtnMeeting_"+treeNode.id);
		treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
		first.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goMeetingPage(treeNode, 1);
			}
		});
		last.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goMeetingPage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goMeetingPage(treeNode, treeNode.page-1);
			}
		});
		next.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goMeetingPage(treeNode, treeNode.page+1);
			}
		});
	}
	
	function getMeetingUrl(treeId, treeNode) {
		if(treeNode == null){
			return "${ctx}/addr/getAddr.action";
		}else{
			var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
			aObj = $("#" + treeNode.tId + "_a");
			aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
			return "${ctx}/addr/getAddr.action?" + param;
		}
		
	}
	function goMeetingPage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page<1) treeNode.page = 1;
		if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
		if (curMeetingPage == treeNode.page) return;
		curMeetingPage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("meetNoticetreeDemo");
		zTree.reAsyncChildNodes(treeNode, "refresh");
	}
	function meetNoticefilter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			if( childNodes[i] && childNodes[i].name){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
		}
		return childNodes;
	}
	function meetNoticebeforeClick(treeId, treeNode, clickFlag) {
		className = (className === "dark" ? "":"dark");
		return (treeNode.click != false);
	}
	function meetNoticeonClick(event, treeId, treeNode, clickFlag) {
		meetNoticeaddToReceiver(treeNode.name);
	}		

	function meetNoticeaddToReceiver(recv){
		if( meetNoticetextChanged){
			$('#meetNoticereceiver').attr({ value: $('#meetNoticereceiver').attr("value")+","+recv });
		}
		else{
			$('#meetNoticereceiver').attr({ value: recv });
		}
		meetNoticetextChanged = true;
	}
	function meetNoticecheckUserAddr(userAddr){
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
	function meetNoticecheckInput(){
		if( $('#meetNoticetitle').attr("value") == '会议主题不会作为短信内容发出，仅作为查询依据' || $('#meetNoticetitle').attr("value") == "" ){
			alert('会议主题为空');
			return false;
		}
		if( (!meetNoticetextChanged || $('#meetNoticereceiver').attr("value") == "" || $('#meetNoticereceiver').attr("value") == "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送") && $('#meetNoticeinputfile').val() == ""){
            alert('接收人为空');
            return false;
		}
		// 检查收件人
		if( $('#meetNoticeinputfile').val() == ""){
			if( !meetNoticetextChanged){
				$('#meetNoticereceiver').attr("value","");
			}
		}else if( meetNoticetextChanged){
			var strs= new Array(); 
			strs=$('#meetNoticereceiver').attr("value").split(",");
			var result=true;
			for( var i=0; i<strs.length; i++){
				if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
				result = meetNoticecheckUserAddr(strs[i]);
				if(!result){
		            alert('地址不合法 [' + strs[i] + ']');
		        	return false;
				}
			}			
		}
		if( $('#meetNoticesmsText').attr("value") == "" || $.trim( $('#meetNoticesmsText').attr("value") ) == ""){
            alert('短信内容为空');
            return false;
		}
		if( $("#meetNoticesendAtTime").attr("checked") == 'checked'){
			if( $("#meetNoticesendTime").attr("value") == ""){
                alert('定时时间为空');
                return false;
			}
		}
		$("#waitTipMeeting").removeClass("ui-helper-hidden");
		return true;
	}

	function meetNoticereceiverEventProc(event){
        if (event.type == "focusin" && !meetNoticetextChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
        	meetNoticetextChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
        } else {
            switch(event.which) {
                case 27:
                	meetNoticetextChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                	meetNoticetextChanged = true;
            }
        }
	}
	function meetNoticetitleEventProc(event){
        if (event.type == "focusin" && !meetNoticetitleChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
        	meetNoticetitleChanged = false;
            $(this).attr({ value: "会议主题不会作为短信内容发出，仅作为查询依据" });
        } else {
            switch(event.which) {
                case 27:
                	meetNoticetitleChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                	meetNoticetitleChanged = true;
            }
        }
	}
	function meetNoticesaveEventProc(event){
        if( $("#meetNoticesmsText").attr("value") == "" || $.trim( $("#meetNoticesmsText").attr("value") ) == ""){
            alert('短信内容为空');
            return;
        }
       	$.ajax({
            url : event.data.url,
            type : 'post',
            dataType: "json",
            data: {
            	smsText: $("#meetNoticesmsText").attr("value"),
            	title: $("#meetNoticetitle").attr("value")
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

	function meetNoticesmsTextEventProc(){
		var extLen = $("#meetNoticereplyText").val().length;
		//var extLen2 = $("#entSign").val().length;
		var len = $(this).val().length + extLen;
		var signSmsLen = len + extLen;// + extLen2;
		var remain = 335 - signSmsLen;
        var cnt
        if(signSmsLen<=70){
        	cnt=1;
        }else{
        	cnt = Math.ceil(signSmsLen/67);  
        }
        if( remain<0 ){
        	//isMoreWords=true;
        	$("#meetNoticesmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
       		//isMoreWords=false;
            $("#meetNoticesmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
        }
		/*
		var extLen = $("#meetNoticereplyText").val().length;
		//var extLen2 = $("#meetNoticeentSign").val().length;
		var len =$(this).val().length + extLen;// + extLen2;
        var remain = 335 - len;
        var cnt
        if(len<=70){
        	cnt=1;
        }else{
        	cnt = Math.ceil(len/67);  
        }
        
        if( remain<0 ){
        	$("#meetNoticesmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
            $("#meetNoticesmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 "&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)");
        }
        */
	}
	
	function meetNoticesetRemainNumber(event){
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
	function meetNoticeselectFile(){
		$("#meetNoticereceiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
	}

    /**
    *
    * 提交表单成功后处理方法
    *
    */  
  	function meetNoticesendSms(responseText, statusText, xhr, $form){
		if(responseText.resultcode == "success" ){
			alert(responseText.message);
			$("#waitTipMeeting").addClass("ui-helper-hidden");
			var originalUrl = "./sms/meeting/jsp/sms_info.jsp";
	        var tempUrl = "./sms/meeting/jsp/sms_info.jsp";
	        var localUrl = "./meeting/writesms.action";
	        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
	        tabpanel.kill(_killId, true);
	        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		}else{
			alert(responseText.message);
			$("#waitTipMeeting").addClass("ui-helper-hidden");
		}
	}
	
	var meetNoticesetting = {
		view: {
			showIcon: false,
			addDiyDom: meetNoticeaddGroupCounts,
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
			beforeClick: meetNoticebeforeClick,
			onClick: meetNoticeonClick
		},
		async: {
			enable: true,
			//url:"${ctx}/addr/getAddr.action",
			url: getMeetingUrl,
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: meetNoticefilter
		}
	};

	var meetNoticezNodes =[];
	var log, className = "dark";
	
	var meetNoticetextChanged = false;
	var meetNoticetitleChanged = false;

	$(document).ready(function(){
		$.fn.zTree.init($("#meetNoticetreeDemo"), meetNoticesetting, meetNoticezNodes);
		$('#meetNoticesmsText').unbind('keyup keypress change').bind('keyup keypress change', meetNoticesmsTextEventProc);
		$("#meetNoticesendAtTime").unbind("click").click(function(){
				$("#meetNoticesendTime").css({"display": "inline"});
		});
		$("#meetNoticesendNow").unbind('click').click(function(){
			$("#meetNoticesendTime").css({"display": "none"});
		});
		$('#meetNoticereceiver').unbind("keyup focusin focusout").bind("keyup focusin focusout", meetNoticereceiverEventProc);
		$('#meetNoticetitle').unbind("keyup focusin focusout").bind("keyup focusin focusout", meetNoticetitleEventProc);
		$("#meetNoticesendButton").unbind("click").bind("click",function(){
        	var options = { 
      		        beforeSubmit:  meetNoticecheckInput,  // pre-submit callback 
      		        success:       meetNoticesendSms,  // post-submit callback 
      		        dataType:		'json'
      		}; 
        	$("#meetNoticesmsForm").ajaxSubmit(options); 
        });
        $("#meetNoticecancelButton").unbind("click").bind("click",function(){
        	$("#meetNoticesmsForm")[0].reset();
        });
        $("#meetNoticesaveButton").unbind("click").bind("click",{url: "${ctx}/meetSmsDraftAction/saveDraft.action"}, meetNoticesaveEventProc);
		$("#meetNoticeneedReply").unbind("click").bind("click",function(){
			if(this.checked){
				$.ajax({
                    url: "${ctx}/meeting/queryTaskNumber.action",
                    dataType: "json",
                    success: function( data ) {
                        if( data && data.resultcode && data.resultcode == 'success'){
                            $("#meetNoticereplyCode").attr("value",data.code);
                        	$("#meetNoticereplyText").attr("value","回复此短信编辑“HY"+data.code+"回复内容”");
                        }
                        else{
                        	alert("获取回复编号错误，请稍后再试");
                        }
                        $('#meetNoticesmsText').trigger("keyup");
                    }
                });
			}else{$("#meetNoticereplyCode").attr("value","");$("#meetNoticereplyText").attr("value","");$('#meetNoticesmsText').trigger("keyup");}			
		});
		$("#meetNoticeydTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ydTunnel', tip:'ydTip'}, meetNoticesetRemainNumber);
		$("#meetNoticeltTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ltTunnel', tip:'ltTip'}, meetNoticesetRemainNumber);
		$("#meetNoticedxTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'dxTunnel', tip:'dxTip'}, meetNoticesetRemainNumber);

        $( "#meetNoticequery" ).autocomplete({
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
            	meetNoticeaddToReceiver(ui.item.value);
            }
        });
        
    	<c:if test="${ not empty entityMap}">
		    alert('${entityMap["message"]}');
		</c:if>
    	<c:if test="${ not empty smsText}">
    		$('#meetNoticesmsText').attr("value","${smsText}");
    		$('#meetNoticesmsText').trigger("keyup");
		</c:if>
    	<c:if test="${ not empty title}">
			$('#meetNoticetitle').attr("value","${title}");
		</c:if>
    	<c:if test="${ not empty receiver}">
			meetNoticeaddToReceiver("${receiver}");
		</c:if>
    	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
    		$("#meetNoticeselfsend").unbind("click").bind("click", function(){meetNoticeaddToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
		</c:if>
	});
	</script>
<body>
<div class="main_body" style="overflow-y: auto;overflow-x: hidden;hight: 400px;">
<div class="contents" >
  <form id="meetNoticesmsForm" action="${ctx }/meeting/send.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents" >
	        <div class="table_box">
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>会议主题：</td>
	              <td width="91%"><input id="meetNoticetitle" name="title" type="text" class="input2" value="会议主题不会作为短信内容发出，仅作为查询依据"/></td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>接收人：</td>
	              <td width="91%"><input id="meetNoticereceiver" name="receiver" type="text" class="input2" value="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" /></td>
	            </tr>
	            <tr>
	              <td align="right" valign="top"><span style="color:#FF0000">*</span>会议通知内容：</td>
	              <td><textarea id="meetNoticesmsText" name="smsText" style="width: 98%; height: 90px; font-size: 12px;">会议主题：
必选参会人员：
可选参会人员：
会议地点：
开始时间：
结束时间：</textarea>
	                <div id="meetNoticesmsTips" class="tixing_tab">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;还可以输入296字，本次将以1条计费 短信共39字(含企业签名)，分1条发送</div>
                	<s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getExtCodeStyle()==1">
                       	<div id="meetNoticesmsReply" style="display: none;">
                   	</s:if>
                   	<s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getExtCodeStyle()==2">
                       	<div id="meetNoticesmsReply" style="display: block;">
                   	</s:if>
	                	<input type="checkbox" id="meetNoticeneedReply" name="needReply" />此短信需要接收人回复，短信正文需加上以下文字
	                	<input type="text" id="meetNoticereplyText" name="replyText" class="input2" readonly="readonly"/>
	                	<input type="hidden" id="meetNoticereplyCode" name="replyCode" value=""/>
	                </div>
	              </td>
	            </tr>
	            <tr>
	              <td align="right">发送时间：</td>
	              <td><input type="radio" id="meetNoticesendNow" name="sendType" value="NOW" checked="checked" />立即发送&nbsp;&nbsp;
				 	<input type="radio" id="meetNoticesendAtTime" name="sendType" value="TIMER"/>定时发送
				 	<input type="text" name="sendTime" id="meetNoticesendTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});" style="display:none;"/>
				  </td>
	            </tr>
	            <!--<tr style="display: none">-->
	            <tr style="display:none;">
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>通道选择：</td>
	              <td width="91%">
	             	 移动
					<select id="meetNoticeydTunnel" name="ydTunnel" style="width:220px;">
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
					通道可用余量<span id="meetNoticeydTip">${ydTunnelNumber}</span>条
					<br/>
					联通
					<select id="meetNoticeltTunnel" name="ltTunnel" style="width:220px;">
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
					通道可用余量<span id="meetNoticeltTip">${ltTunnelNumber}</span>条
					<br/>
					电信
					<select id="meetNoticedxTunnel"  name="dxTunnel" style="width:220px;">
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
					通道可用余量<span id="meetNoticedxTip">${dxTunnelNumber}</span>条
					<br/>
					注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送
				  </td>
	            </tr>
	            <tr class="ui-helper-hidden" id="waitTipMeeting">
					<td width="12%" height="50" align="right"></td>
		            <td height="50" ><span id="loadingTip" class="needtip"><img src="themes/mas3admin/images/helper/loading.gif"/>正在提交网关，请耐心等待。请勿重复执行此操作！</span></td>
		        </tr>
	          </table>
	        </div>
	        <div class="bottom_box">
	          <div class="tubh" id="meetNoticesendButton"><a href="javascript:void(0);">发送</a></div>
	          <div class="tubh" id="meetNoticecancelButton"><a href="javascript:void(0);">重置</a></div>
	         <!--  <div class="tubh" id="meetNoticesaveButton"><a href="javascript:void(0);">存草稿</a></div> -->
	        </div>
	      </div>

      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="meetNoticequery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer;position:relative;">
	        	<div id="meetNoticeinputFileContainer">
					<input type="file" id="meetNoticeinputfile" onchange="meetNoticeselectFile();" class="hide_input_file" name="addrUpload" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png" style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="meetNoticeselfsend"><img src="./themes/mas3/images/user.png"  style="margin-right:5px;"/>发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 0px; margin-top: -5px;">
          		<ul id="meetNoticetreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;padding-left:0px;"></ul>
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
