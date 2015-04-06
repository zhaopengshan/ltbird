<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>发短信</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx }/css/demo.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx }/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	<link href="${ctx }/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx }/css/easyui/easyui.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${ctx }/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/jquery.ztree.core-3.4.js"></script>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
	<script language="javascript" src="${ctx }/js/jquery.easyui.min.js" type="text/javascript" ></script>
	<script language="javascript" src="${ctx }/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript" ></script>
	<script language="javascript" src="${ctx }/smssend/js/sms_write.js" type="text/javascript" ></script>
	<script type="text/javascript">
	<!--

	function selectFile(){
		$("#receiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
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
			beforeClick: beforeClick,
			onClick: onClick
		},
		async: {
			enable: true,
			url:"${ctx}/addr/getAddr.action",
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};

	var zNodes =[];
	var log, className = "dark";
	
	var textChanged = false;

	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		$('#smsText').bind('keyup keypress change', smsTextEventProc);
		$("#sendAtTime").click(function(){
				$("#sendTime").css({"display": "inline"});
		});
		$("#sendNow").click(function(){
			$("#sendTime").css({"display": "none"});
		});
		$('#receiver').bind("keyup focusin focusout", receiverEventProc);
        $("#sendButton").bind("click",function(){
	        if(checkInput()){
        		$("#smsForm").attr("action","${ctx }/smssend/send.action");
				$("#smsForm").submit();
	        }
        });
        $("#cancelButton").bind("click",function(){
        	$("#smsForm")[0].reset();
        });
        $("#saveButton").bind("click",{url: "${ctx}/mbnSmsDraftAction/saveDraft.action"}, saveEventProc);
		$("#needReply").bind("click",function(){
			if(this.checked){
				$.ajax({
                    url: "${ctx}/smssend/queryTaskNumber.action",
                    dataType: "json",
                    success: function( data ) {
                        if( data && data.resultcode && data.resultcode == 'success'){
                            $("#replyCode").attr("value",data.code);
                        	$("#replyText").attr("value","回复此短信编辑“HD"+data.code+"+回复内容”");
                        }
                        else{
                        	$.messager.alert("系统提示","获取回复编号错误，请稍后再试","warning");
                        }
                    }
                });
			}else{$("#replyCode").attr("value","");$("#replyText").attr("value","");}
			$('#smsText').trigger("keyup");
		});
		$("#ydTunnel").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ydTunnel', tip:'ydTip'}, setRemainNumber);
		$("#ltTunnel").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ltTunnel', tip:'ltTip'}, setRemainNumber);
		$("#dxTunnel").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'dxTunnel', tip:'dxTip'}, setRemainNumber);

        $( "#query" ).autocomplete({
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
			$.messager.show({
		        title: '用户操作',
		        msg: '${entityMap["message"]}',
		        timeout:5000
		    });
		</c:if>
    	<c:if test="${ not empty smsText}">
    		$('#smsText').attr("value","${smsText}");
    		$('#smsText').trigger("keyup");
		</c:if>
    	<c:if test="${ not empty title}">
			$('#title').attr("value","${title}");
		</c:if>
    	<c:if test="${ not empty receiver}">
			addToReceiver("${receiver}");
		</c:if>
    	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
    		$("#selfsend").bind("click", function(){addToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
		</c:if>
	});

	-->
	</script>
</head>

<body>
<div class="main_body">
<div class="contents">
  <form id="smsForm" action="${ctx }/smssend/send.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents">
	        <div class="table_box">
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>标题：</td>
	              <td width="91%"><input id="title" name="title" type="text" class="input2"/></td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>接收人：</td>
	              <td width="91%"><input id="receiver" name="receiver" type="text" class="input2" value="可同时发给多人，以逗号”，“隔开，支持向移动、联通、电信用户发送" /></td>
	            </tr>
	            <tr>
	              <td align="right" valign="top"><span style="color:#FF0000">*</span>短信内容：</td>
	              <td><textarea id="smsText" name="smsText" style="width: 98%; height: 90px; font-size: 12px;"></textarea>
	                <div id="smsTips" class="tixing_tab">&nbsp;&nbsp;&nbsp;还可以输入350字，本次将以0条计费&nbsp;短信共0字(含企业签名)，分0条发送</div>
	                <div id="smsReply">
	                	<input type="checkbox" id="needReply" name="needReply" />此短信需要接收人回复，短信正文需加上以下文字
	                	<input type="text" id="replyText" name="replyText" class="input2" readonly="readonly"/>
	                	<input type="hidden" id="replyCode" name="replyCode" value=""/>
	                </div>
	              </td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right">企业签名：</td>
	              <td width="91%"><input type="text" id="entSign" name="entSign" class="input2" readonly="readonly" value="我是企业签名"/></td>
	            </tr>
	            <tr>
	              <td align="right">发送时间：</td>
	              <td><input type="radio" id="sendNow" name="sendType" value="NOW" checked="checked" />立即发送&nbsp;&nbsp;
				 	<input type="radio" id="sendAtTime" name="sendType" value="TIMER"/>定时发送
				 	<input type="text" name="sendTime" id="sendTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:new Date()});" style="display:none;"/>
				  </td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>通道选择：</td>
	              <td width="91%">
	             	 移动
					<select id="ydTunnel" name="ydTunnel" style="width:220px;">
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
					通道可用余量<span id="ydTip">${ydTunnelNumber}</span>条
					<br/>
					联通
					<select id="ltTunnel" name="ltTunnel" style="width:220px;">
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
					通道可用余量<span id="ltTip">${ltTunnelNumber}</span>条
					<br/>
					电信
					<select id="dxTunnel"  name="dxTunnel" style="width:220px;">
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
					通道可用余量<span id="dxTip">${dxTunnelNumber}</span>条
					<br/>
					注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送，并自动存入草稿箱
				  </td>
	            </tr>
	          </table>
	        </div>
	        <div class="bottom_box">
	          <div class="tubh" id="sendButton"><a href="#">发送</a></div>
	          <div class="tubh" id="cancelButton"><a href="#">取消</a></div>
	          <div class="tubh" id="saveButton"><a href="#">存草稿</a></div>
	        </div>
	      </div>

      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="${ctx }/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="query" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer;">
	        	<div id="inputFileContainer">
					<input type="file" id="inputfile" onchange="selectFile();" class="hide_input_file" name="addrUpload" />
				</div>
				<a href="javascript:void(0);"><img src="${ctx }/images/add.png"  style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="selfsend"><img src="${ctx }/images/user.png" style="margin-right:5px;" />发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 10px; margin-top: -5px;">
          		<ul id="treeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;"></ul>
          	</div>
          <div class="box_bottom"><img src="${ctx }/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
    </tr>
  </table>
  </form>
</div>
</div>
</body>
</html>
