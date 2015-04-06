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
        <script type="text/javascript" src="./sms/vote/js/createVote.js"></script>
        <script type="text/javascript">
	var zNodes =[];
	var log, className = "dark";
	var voteTextChanged = false;
	var votereceiverChanged=false;
	var voteTitileChanged=false;
	var curVotePage = 1;
	function getVoteReplyCode(){
		$.ajax({
	        url: "voteAction/queryTaskNumber.action",
	        type:"post",
	        dataType: 'json',
	        data:  { },
	        success:function(data){
	        	document.getElementById("votereplyCode").value=data.code;
	        	document.getElementById("votereplyText").value="参与此投票调查编辑“TP"+data.code+"回复内容”";
	        	$("#votereplyDiv").css({"display": "block"});
	        	smsTextEventProc();
	        },
			error:function(data){
			}
		 }
		);
	}
	
	function getVoteUrl(treeId, treeNode) {
		if(treeNode == null){
			return "${ctx}/addr/getAddr.action";
		}else{
			var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
			aObj = $("#" + treeNode.tId + "_a");
			aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
			return "${ctx}/addr/getAddr.action?" + param;
		}
		
	}
	function goVotePage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page<1) treeNode.page = 1;
		if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
		if (curVotePage == treeNode.page) return;
		curVotePage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("voteTreeDemo");
		zTree.reAsyncChildNodes(treeNode, "refresh");
	}
	function addGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"addToReceiver('"+treeNode.name+"');return false;\"></a>";//("+treeNode.counts+")
				aObj.after(editStr1);
			}
		}
		if (treeNode.level>0) return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button lastPage' id='lastBtnVote_" + treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnVote_" + treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnVote_" + treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnVote_" + treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtnVote_"+treeNode.id);
		var prev = $("#prevBtnVote_"+treeNode.id);
		var next = $("#nextBtnVote_"+treeNode.id);
		var last = $("#lastBtnVote_"+treeNode.id);
		treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
		first.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goVotePage(treeNode, 1);
			}
		});
		last.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goVotePage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goVotePage(treeNode, treeNode.page-1);
			}
		});
		next.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goVotePage(treeNode, treeNode.page+1);
			}
		});
	}

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			if( childNodes[i] && childNodes[i].name){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
		}
		return childNodes;
	}
	function beforeVoteClick(treeId, treeNode, clickFlag) {
		className = (className === "dark" ? "":"dark");
		return (treeNode.click != false);
	}
	function onvoteClick(event, treeId, treeNode, clickFlag) {
		/*if( treeNode.isParent){
			return;
		}*/
		addToReceiver(treeNode.name);
	}		

	function addToReceiver(recv){
		if( votereceiverChanged){
			$('#voteReceiver').attr({ value: $('#voteReceiver').attr("value")+","+recv });
		}
		else{
			$('#voteReceiver').attr({ value: recv });
		}
		votereceiverChanged = true;
	}
	function checkUserAddr(userAddr){
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
	function voteTitleEventProc(event){
		if (event.type == "focusin" && !voteTitileChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
        	voteTitileChanged = false;
            $(this).attr({ value: "输入投票调查任务名称，仅作为查询依据" });
        } else {
            switch(event.which) {
                case 27:
                	voteTitileChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                	voteTitileChanged = true;
            }
        }
	}
	function votereceiverEventProc(event){
        if (event.type == "focusin" && !votereceiverChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
        	votereceiverChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
        } else {
            switch(event.which) {
                case 27:
                	votereceiverChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                	votereceiverChanged = true;
            }
        }
	}
	function votecontentEventProc(event){
        if (event.type == "focusin" && !voteTextChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            voteTextChanged = false;
            $(this).attr({ value: "发送短信时所设选项自动添加至邀请短信内容结尾……" });
        } else {
            switch(event.which) {
                case 27:
                    voteTextChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                    voteTextChanged = true;
            }
        }
	}

	function smsTextEventProc(){
		var extLen = $("#votecontent").val().length;
		var optionCount= $("#optionCount").val();
		var entSignLen=$("#entSign").val().length;
		var tmpLen=0;
		for(var i=0;i<optionCount;i++){
			tmpLen+=$("#optionContent_"+(i+1)).val().length;
		}
		//var extLen2 = $("#voteEntSign").val().length;
		var replyLen = $("#votereplyText").val().length;
		var len = extLen+tmpLen+replyLen+entSignLen;
        var remain = 335-replyLen - len-optionCount*3-2-entSignLen;
        var cnt = Math.ceil(len/70);  
        if( remain<0 ){
        	$("#voteSmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
            $("#voteSmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
        }
	}
	
	function selectVoteFile(){
		$("#voteReceiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
	}
	
	var setting = {
		view: {
			showIcon: false,
			addDiyDom: addGroupCounts,
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
			beforeClick: beforeVoteClick,
			onClick: onvoteClick
		},
		async: {
			enable: true,
			//url:"${ctx}/addr/getAddr.action",
			url: getVoteUrl,
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};
	function votesetRemainNumber(event){
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
	function voteaddToReceiver(recv){
		if( votereceiverChanged){
			$('#voteReceiver').attr({ value: $('#voteReceiver').attr("value")+","+recv });
		}
		else{
			$('#voteReceiver').attr({ value: recv });
		}
		votereceiverChanged = true;
	}
	$(document).ready(function(){
		<s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getExtCodeStyle()==2">
			getVoteReplyCode();
		</s:if>
		$.fn.zTree.init($("#voteTreeDemo"), setting, zNodes);
		$('#votecontent').bind('keyup keypress change', smsTextEventProc);
		$(".optionContent").bind('keyup keypress change', smsTextEventProc);
		$("#voteSendAtTime").click(function(){
				$("#ready_send_time").css({"display": "inline"});
		});
		$("#voteTime1").click(function(){
			$("#voteTimeBetween").css({"display": "inline"});
		});
		$("#need_content_error_remind_1").click(function(){
			$("#need_content_error_content_span").css({"display": "none"});
		});
		$("#need_content_error_remind_0").click(function(){
			$("#need_content_error_content_span").css({"display": "inline"});
		});
		$("#need_not_permmit_remind_1").click(function(){
			$("#need_not_permmit_content_span").css({"display": "none"});
		});
		$("#need_not_permmit_remind_0").click(function(){
			$("#need_not_permmit_content_span").css({"display": "inline"});
		});
		
		$("#need_successful_remind_1").click(function(){
			$("#need_successful_content_span").css({"display": "none"});
		});
		$("#need_successful_remind_0").click(function(){
			$("#need_successful_content_span").css({"display": "inline"});
		});
		
		$("#voteTime").click(function(){
			$("#voteTimeBetween").css({"display": "none"});
		});
		$("#voteSendNow").click(function(){
			$("#ready_send_time").css({"display": "none"});
		});
		$('#voteReceiver').bind("keyup focusin focusout", votereceiverEventProc);
		$('#voteTitle').bind("keyup focusin focusout", voteTitleEventProc);
		//$('#votecontent').bind('focusin focusout', votecontentEventProc);
		$("#voteFormSubmitButton").unbind("click").bind("click",function(){
        	var formoptions = { 
      		        beforeSubmit:  voteCheckInput,  // pre-submit callback 
      		        success:       votesendSms,  // post-submit callback 
      		        dataType:		'json'
      		}; 
        	$("#voteSmsForm").ajaxSubmit(formoptions); 
        });
		$("#voteFormCancelButton").unbind("click").bind("click",function(){
			votereceiverChanged=false;
			voteTitileChanged=false;
        	$("#voteSmsForm")[0].reset();
        });
		
		$("#voteydTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ydTunnel', tip:'ydTip'}, votesetRemainNumber);
		$("#voteltTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ltTunnel', tip:'ltTip'}, votesetRemainNumber);
		$("#votedxTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'dxTunnel', tip:'dxTip'}, votesetRemainNumber);

		smsTextEventProc();
		$( "#voteQuery" ).autocomplete({
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
            	addToReceiver(ui.item.value);
            }
        });
		<c:if test="${ not empty entityMap}">
	    	alert('${entityMap["message"]}');
		</c:if>
		<c:if test="${ not empty smsText}">
			$('#votecontent').attr("value","${smsText}");
			$('#votecontent').trigger("keyup");
		</c:if>
		<c:if test="${ not empty title}">
			$('#voteTitle').attr("value","${title}");
		</c:if>
		<c:if test="${ not empty receiver}">
			voteaddToReceiver("${receiver}");
		</c:if>
		<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
			$("#voteSelfsend").unbind("click").bind("click", function(){voteaddToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
		</c:if>
    	
	});
	</script>
    </head>
<body>
<div class="main_body">
<div class="contents">
  <form id="voteSmsForm" action="${ctx }/voteAction/saveVote.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents" style="height: 410px;overflow-y: auto;overflow-x: hidden;">
	  <div class="table_box">
	  <table width="98%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td height="50" width="100" align="right"><span style="color:#FF0000">*</span>任务名称：</td>
	    <td><input id="voteTitle" type="text" value="输入投票调查任务名称，仅作为查询依据" name="title" class="input2" /></td>
	  </tr>
	  <tr>
	    <td rowspan="2" width="100" align="right" valign="top"><span style="color:#FF0000">*</span>编辑投票人：</td>
	    <td><input id="voteReceiver" name="tos" type="text" class="input2" value="可同时发给多人，以逗号”,“隔开，支持向移动、联通、电信用户发送" />
	      </td>
	  </tr>
	  <tr></tr>
	  <tr>
	    <td width="100" align="right" valign="top"><span style="color:#FF0000">*</span>邀请短信内容：</td>
	    <td><textarea id="votecontent" name="content" style="width: 98%; height: 87px; font-size: 12px;"></textarea>
	    
	    <div id="voteSmsTips" class="tixing_tab">   还可以输入350字，本次将以0条计费 短信共0字(含企业签名)，分0条发送</div>
	    <div id="votereplyDiv" style="display: none;">
	   		 此短信需要接收人回复，短信正文需加上以下文字
	    	<input id="votereplyText" type="text" class="input2"  readonly="readonly"  name="replyText" value=""/>
	    </div>
	    </td>
	  </tr>
	  <tr>
	    <td width="100" align="right" valign="top"><span style="color:#FF0000">*</span>企业签名：</td>
	    <td><input type="text" id="entSign" name="entSign" class="input_entSign" readonly="readonly" value="${sessionScope.ent_sms_sign}"/></td>
	  </tr>
	  <tr>
		<td style="height:18px; line-height: 18px;" align="right"></td>
		<td style="height:18px; line-height: 18px;" >
		  <span style="color:#FF0000">*</span>如果用户收到的短信后缀签名与平台不符，请联系客户经理或者平台管理员修改短信后缀签名
		</td>
	  </tr>
	  <tr>
	    <td width="100"  align="right"><span style="color:#FF0000">*</span>投票选项：</td>
	    <input type="hidden" value="3" id="optionCount" name="optionCount"></input>
	    <td align="left" valign="middle"  style="height:150px; line-height:30px;">
	    	<table id="voteoptions" width="98%" border="0" align="left" cellpadding="0" cellspacing="0" class="mail_tab">
	    	<tbody>
		      <tr >
		        <td width="5%" class="td10" align="center">1</td>
		        <td width="85%" class="td8"><input name="optionContent_1" id="optionContent_1" type="text" name="textfield4" class="input2 optionContent" /></td>
		        <td width="10%" class="td8"><a  href="javascript:removeRow(0)"><img  src="${ctx }/themes/mas3admin/images/vote/u109_normal.gif" width="16" height="16" /></a></td>
		      </tr>
		      <tr >
		        <td class="td22"  align="center">2</td>
		        <td class="td7"><input name="optionContent_2" type="text" id="optionContent_2" name="textfield3" class="input2 optionContent" /></td>
		        <td class="td7"><a  width="400px" href="javascript:removeRow(1)"><img  src="${ctx }/themes/mas3admin/images/vote/u109_normal.gif" width="16" height="16" /></a></td>
		      </tr>
		      <tr >
		        <td class="td22" align="center" >3</td>
		        <td class="td7"><input name="optionContent_3" type="text" id="optionContent_3" name="textfield5" class="input2 optionContent" /></td>
		        <td class="td7"><a  width="400px" href="javascript:removeRow(2)"><img  src="${ctx }/themes/mas3admin/images/vote/u109_normal.gif" width="16" height="16" /></a></td>
		      </tr>
		      </tbody>
	    	</table>
	    	<div class="tubh" style="clear:both;"><a onclick="addVoteOption()" id="btnAddOption" href="javascript:void(0)">增加</a></div>
	    </td>
	  </tr>
	  <tr>
	    <td width="100"  align="right"><span style="color:#FF0000">*</span>有效时间：</td>
	    <td><input type="radio" name="voteTime" id="voteTime" value="0" checked="checked"  />长期有效
	    <input name="voteTime" type="radio" id="voteTime1" value="1" />短期有效<span style="color:#FF0000">*</span><span id="voteTimeBetween" style="display:none"><input id="begin_time" type="text" name="begin_time" class="input3" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});">
	       	 到
		<input id="end_time" name="end_time" type="text" class="input3" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});" /></span></td>
	  </tr>
	  
	   <tr>
	    <td width="100"  align="right"><span style="color:#FF0000">*</span>发送时间：</td>
	    <td><input name="txi" id="voteSendNow" checked="checked" type="radio" value="0" />
	      立即发送
	        <input type="radio" name="txi" id="voteSendAtTime" value="1" />
	        定时发送<span style="color:#FF0000">*
	        <input style="display:none" name="ready_send_time" id="ready_send_time" type="text" class="input3" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});" />
	        </span></td>
	  </tr>
	   <tr style="display:none;">
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>通道选择：</td>
	              <td width="91%">
	             	 移动
					<select id="VoteydTunnel" name="ydTunnel" style="width:220px;">
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
					通道可用余量<span id="VoteydTip">${ydTunnelNumber}</span>条
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
					通道可用余量<span id="VoteltTip">${ltTunnelNumber}</span>条
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
					通道可用余量<span id="VotedxTip">${dxTunnelNumber}</span>条
					<br/>
					注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送
				  </td>
	            </tr>
	  <tr>
	   <td  align="right"><span style="color:#FF0000">*</span>高级选项：</td>
	    <td><div class="tubh"><a href="javascript:showMoreOption()">更多投票设置</a></div></td>
	  </tr>
	  <tr class="ui-helper-hidden" id="waitTipVote">
		<td width="12%" height="50" align="right"></td>
		<td height="50" ><span id="loadingTip" class="needtip"><img src="themes/mas3admin/images/helper/loading.gif"/>正在提交网关，请耐心等待。请勿重复执行此操作！</span></td>
	  </tr>
	</table>
			</div>
			<div class="bottom_box">
		  <div id="voteFormSubmitButton" class="tubh"><a   href="javascript:void(0)">提交</a></div>
		  <div id="voteFormCancelButton" class="tubh"><a href="javascript:void(0)">重置</a></div>
		</div>
		</div>
      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="voteQuery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer; position:relative;">
	        	<div id="voteInputFileContainer">
					<input type="file" id="voteInputfile" onchange="selectVoteFile();" class="hide_input_file" name="addrUpload" style="width:200px;" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png"  style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="voteSelfsend"><img src="./themes/mas3/images/user.png" style="margin-right:5px;" />发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 10px; margin-top: -5px;">
          		<ul id="voteTreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;"></ul>
          	</div>
          <div class="box_bottom"><img src="./themes/mas3/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
    </tr>
  </table>
 
 
  <div class="tanchuang" id="voteMoreOption" style="overflow-x:auto;background-color:#FFFFFF;border: solid 1px #000000;;display: none; z-index: 9999; position: absolute; top: 88px; height: 280px; width: 600px; left: 100px;">
  <div class="toupiao_box">
    <table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tbody><tr>
        <td bgcolor="#eaf1ee" align="center" height="30px" width="100%"><strong>高级投票设置</strong></td>
      </tr>
      <tr>
        <td align="center"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="table_box2">
            <tbody><tr>
              <td align="right" width="40">选项数目：</td>
              <input id="votereplyCode" type="hidden" value="" name="replyCode">
              
              <td bgcolor="#FFFFFF" align="left" width="70" colspan="2">
              <input type="text" size="3" value="1" id="multi_selected_number" name="multi_selected_number">
              <span style="color:#FF0000">*</span>（每次投票最多可以选择的投票选项，以“，”为分隔号）</td>
            </tr>

            <tr>
              <td align="right"  width="40">投票次数设置：</td>
              <td bgcolor="#FFFFFF" align="left" colspan="2"><input type="radio" value="1" name="effective_mode">
				每次投票都有效  
				  <input type="radio" checked="checked"  value="2" name="effective_mode">
				仅第一次有效 
				<input type="radio" value="3" name="effective_mode">
				仅最后一次有效 </td>
            </tr>
                        <tr>
              <td align="right"  width="40">是否支持重复项：</td>
              <td bgcolor="#FFFFFF" align="left" colspan="2"><input type="radio" value="1" name="support_repeat">
				是
				  <input type="radio" checked="checked" value="0" name="support_repeat">
				否 <span style="color:#FF0000">*</span>（每次投票是否允许有重复项，即每次是否可以投票多次）</td>
            </tr>
            <tr>
              <td align="right"  width="40">是否回复投票成功短信：</td>
              <td bgcolor="#FFFFFF" align="left" colspan="2">
              
              <span valign="center"><input type="radio" id="need_successful_remind_0" value="1" name="need_successful_remind">是
                <input type="radio" checked="checked" id="need_successful_remind_1" value="0" name="need_successful_remind">否</span>
                <span id="need_successful_content_span" style="display:none;" valign="center">
                <textarea id="need_successful_content" name="need_successful_content" style="width:240px; height:87px; font-size:12px;"></textarea>
                </span>
                </td>
            </tr>
            <tr>
              <td align="right"  width="40">是否回复不允许投票短信：</td>
              <td bgcolor="#FFFFFF" align="left" colspan="2">
              <span valign="center"><input type="radio" value="1" id="need_not_permmit_remind_0" name="need_not_permmit_remind">是
                <input type="radio" checked="checked" value="0" id="need_not_permmit_remind_1" name="need_not_permmit_remind"> 否</span>
                <span id="need_not_permmit_content_span" style="display:none;" valign="center">
                	<textarea id="need_not_permmit_content" name="need_not_permmit_content" style="width:240px; height:87px; font-size:12px;"></textarea>
                </span>
                </td>
            </tr>
			<tr>
              <td align="right" valign="top"  width="40">是否回复投票错误的提醒短信：</td>
              <td bgcolor="#FFFFFF" align="left" COLSPAN=2 valign="top">
              
             	<span valign="center"> <input  type="radio"  value="1" id="need_content_error_remind_0" name="need_content_error_remind"> 是
                <input type="radio" value="0" checked="checked" id="need_content_error_remind_1" name="need_content_error_remind"> 否 
                <span id="need_content_error_content_span" style="display:none;" valign="center"><textarea id="need_content_error_content" name="need_content_error_content" style="width:240px; height:87px; font-size:12px;"></textarea>
                </span></td>
            </tr>
			
        </tbody></table></td>
      </tr>
    </tbody></table>
  </div>
  <div class="botton_box3" width="100%" align="center">
    <div class="tubh"><a href="javascript:closeVoteOption()">关闭</a></div>
  </div>
</div>
 
 
  </form>
</div>
</div>
	

</body>
</html>
