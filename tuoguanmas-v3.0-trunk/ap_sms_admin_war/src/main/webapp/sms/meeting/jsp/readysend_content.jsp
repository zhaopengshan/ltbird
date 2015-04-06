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
meetNoticecurrent_url = meetNoticereadysend_list_url;
	function successCalBack(data) {
        alert(data.message);
	}
	function errorCalBack() {
        alert("出现系统错误，请稍后再试");
    }
	$(function() {
		meetNoticerightMenuBar.hidExportHref();
		$("#meetNoticecancelSendBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsReadySendId = $("#meetNoticesmsReadySendId").val();
			$.ajax({
                url : "${ctx }/meetSmsReadySendAction/cancelSendByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsReadySendId
                },
                success : function(data){
                	successCalBack(data);
					//window.location.href="${ctx }/meeting/jsp/sms_info.jsp";
                },
                error : errorCalBack
            }); 
		});
		
		$("#meetNoticecollectSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsReadySendId = $("#meetNoticesmsReadySendId").val();
			$.ajax({
                url : "${ctx }/meetSmsReadySendAction/collectByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsReadySendId
                },
                success : successCalBack,
                error : errorCalBack
            }); 
		});
		$("#meetNoticeeditSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/meetSmsReadySendAction/edit.action';
			var smsReadySendId = $("#meetNoticesmsReadySendId").val();
		    var originalUrl = "./meeting/writesms.action";
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
		$("#meetNoticereturnBtn").unbind("click.rs").bind( "click.rs",function(){
			meetNoticecurrent_url = meetNoticereadysend_list_url;
			meetNoticerightMenuBar.showSendBoxMenu();
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			alert("已经是最后一条！");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/meetSmsReadySendAction/followPage.action?selectedId=${mbnSmsReadySendVO.id }&batchId=${mbnSmsReadySendVO.batchId }";
		url = url + "&pageDirect=" + direction;
		$("#meetNoticesms_hd_frame").load(url);
	}
	function showSendResult(url){
		$("#meetNoticesms_hd_frame").load(url);
	}
</script>
<body> 
<div class="left_contents">
	<div class="botton_box">
	  <div class="tubh"><a id="meetNoticereturnBtn" href="javascript:void(0);">返回</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="meetNoticeeditSmsBtn">转发</a></div>
	  <div class="tubh"><a href="javascript:void(0);" id="meetNoticecancelSendBtn">删除</a></div>
	  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
	  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="meetNoticenextPage" style="color:#067599">下一条</a> </div>
	<div class="table_box">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		 	<td width="10%"  align="left" style="vertical-align: top">发送时间：</td>
			<td width="90%"><fmt:formatDate value="${mbnSmsReadySendVO.commitTime }" type="both" />
			<a onclick="javascript:showSendResult('${ctx }/meetSmsReadySendAction/getBatchSms.action?batchId=${mbnSmsReadySendVO.batchId}&smsReadySendId=${mbnSmsReadySendVO.id}');" href="javascript:void(0);">&nbsp;&nbsp;查看发送结果</a>
			<a onclick="javascript:showSendResult('${ctx }/sms/meeting/jsp/readysend_replyInfo.jsp?batchId=${mbnSmsReadySendVO.batchId}&smsReadySendId=${mbnSmsReadySendVO.id }');" href="javascript:void(0);"> &nbsp;&nbsp;${mbnSmsReadySendVO.replyCount } 条回复</a></td>
			</tr>
		 <%--  <tr>
			<td width="10%"  align="left" style="vertical-align: top">收件人：</td>
			<td width="90%"><c:forEach items="${smsReadySendList}" var="readySendSms">${readySendSms.tos }
				<c:if test="${readySendSms.tosName !=null && readySendSms.tosName !=null}" >&lt;${readySendSms.tosName}&gt;</c:if>;
			</c:forEach></td>
			</tr> --%>
		  <tr>
		  	<td width="10%" align="left" style="vertical-align: top">会议主题：</td>
			<td width="90%">${mbnSmsReadySendVO.title }</td>
			</tr>
		  <tr>
		   <tr>
		  	<td width="10%" align="left" style="vertical-align: top">创建人：</td>
			<td width="90%">${createBy }</td>
			</tr>
		  <tr>
			<td  width="10%" align="left">会议通知内容：</td>
			<td ></td>
			</tr>
		  <tr>
			<td  align="right"></td>
			<td ><span style="color:#006699">${mbnSmsReadySendVO.content }</span></td>
			</tr>
		</table>
		<input type="hidden" id="meetNoticesmsReadySendId" value="${mbnSmsReadySendVO.id }"/>
		<br />
	</div>
</div>

</body>
</html>

