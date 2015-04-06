<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待发短信详细</title>
</head>
<script type="text/javascript" >
	current_url = readysend_list_url;
	function successCalBack(data) {
        alert(data.message);
	}
	function errorCalBack() {
        alert("出现系统错误，请稍后再试");
    }
	$(function() {
		rightMenuBar.hidExportHref();
		$("#cancelSendBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsReadySendId = $("#smsReadySendId").val();
			$.ajax({
                url : "${ctx }/mbnSmsReadySendAction/cancelSendByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsReadySendId
                },
                success : function(data){
                	successCalBack(data);
					//window.location.href="${ctx }/smssend/jsp/sms_info.jsp";
                },
                error : errorCalBack
            }); 
		});
		
		$("#collectSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsReadySendId = $("#smsReadySendId").val();
			$.ajax({
                url : "${ctx }/mbnSmsReadySendAction/collectByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsReadySendId
                },
                success : successCalBack,
                error : errorCalBack
            }); 
		});
		$("#editSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsReadySendAction/edit.action';
			var smsReadySendId = $("#smsReadySendId").val();
		    var originalUrl = "./smssend/writesms.action";
		    var tempUrl = url+'?selectedId='+smsReadySendId;
		    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
		    try{
		        tabpanel.kill(killId);
		    }catch(e){
		    }
		    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
		    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
			//window.parent.parent.addTabs('itemx','itemxs'+smsReadySendId,'短信互动','编辑转发',url+'?selectedId='+smsReadySendId);
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			current_url = readysend_list_url;
			rightMenuBar.showSendBoxMenu();
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			alert("已经是最后一条！");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsReadySendAction/followPage.action?selectedId=${mbnSmsReadySendVO.id }&batchId=${mbnSmsReadySendVO.batchId }";
		url = url + "&pageDirect=" + direction;
		$("#sms_hd_frame").load(url);
	}
	function showSendResult(url){
		$("#sms_hd_frame").load(url);
	}
</script>
<body> 
<div class="left_contents">
	<div class="botton_box">
	  <div class="tubh"><a id="returnBtn" href="javascript:void(0);">返回</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="editSmsBtn">编辑转发</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="cancelSendBtn">取消发送</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="collectSmsBtn">保存珍藏</a></div>
	  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
	  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="nextPage" style="color:#067599">下一条</a> </div>
	<div class="table_box">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		 	<td width="10%" style="vertical-align: top">发送时间：</td>
			<td width="90%"><fmt:formatDate value="${mbnSmsReadySendVO.commitTime }" type="both" />
			<a onclick="javascript:showSendResult('${ctx }/mbnSmsReadySendAction/getBatchSms.action?batchId=${mbnSmsReadySendVO.batchId}&smsReadySendId=${mbnSmsReadySendVO.id}');" href="javascript:void(0);">&nbsp;&nbsp;查看发送结果</a>
			<a onclick="javascript:showSendResult('${ctx }/sms/smssend/jsp/readysend_replyInfo.jsp?batchId=${mbnSmsReadySendVO.batchId}&smsReadySendId=${mbnSmsReadySendVO.id }');" href="javascript:void(0);"> &nbsp;&nbsp;${mbnSmsReadySendVO.replyCount } 条回复</a></td>
			</tr>
		  <tr>
		  	<td width="10%"  style="vertical-align: top">短信标题：</td>
			<td width="90%">${mbnSmsReadySendVO.title }</td>
			</tr>
		  <tr>
			<td width="10%" >短信内容：</td>
			<td width="90%" style="color:#006699;">${mbnSmsReadySendVO.content }</td>
			</tr>
		</table>
		<input type="hidden" id="smsReadySendId" value="${mbnSmsReadySendVO.id }"/>
	</div>
</div>
	<div style="clear:both; height:0px; overflow:hidden;"></div> 
</body>
</html>

