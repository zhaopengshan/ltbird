<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已发短信详细</title>
</head>
<script type="text/javascript">
	current_url = hadsend_list_url;
	rightMenuBar.showHadSendDetails();
	function successCalBack(data) {
        alert(data.message);
	}
	function errorCalBack() {
        alert("出现系统错误，请稍后再试");
    }
	$(function() {
		rightMenuBar.hidExportHref();
		$("#deleteBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsHadSendId = $("#smsHadSendId").val();
			if(confirm("是否删除本条短信？")){
				$.ajax({
	                url : "${ctx }/mbnSmsHadSendAction/updateByIds.action",
	                type : 'post',
	                dataType: "json",
	                data: {
	                	smsIds: smsHadSendId
	                },
	                success : function(data){
	                	successCalBack(data);
	                	if(data.resultcode =="success"){
	                		href="${ctx }/sms/smssend/jsp/hadsend_list.jsp";
	                		$("#sms_hd_frame").load(href);
	                	}
	                },
	                error : errorCalBack
	            });
	        }
		});
		
		$("#collectSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var smsHadSendId = $("#smsHadSendId").val();
			$.ajax({
                url : "${ctx }/mbnSmsHadSendAction/collectByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: smsHadSendId
                },
                success : successCalBack,
                error : errorCalBack
            }); 
		});
		$("#editSmsBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsHadSendAction/edit.action';
			var smsHadSendId = $("#smsHadSendId").val();
			//var url = '${ctx}/mbnSmsSelectedAction/fastSend.action';
		    var originalUrl = "./smssend/writesms.action";
		    var tempUrl = url+'?selectedId='+smsHadSendId;
		    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
		    try{
		        tabpanel.kill(killId);
		    }catch(e){
		    }
		    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
		    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
			//window.parent.parent.addTabs('itemx','itemxs'+smsHadSendId,'短信互动','编辑转发',url+'?selectedId='+smsHadSendId);
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			current_url = hadsend_list_url;
			rightMenuBar.showHadSendBoxMenu();
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			alert("已经是最后一条！");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsHadSendAction/followPage.action?selectedId=${mbnSmsHadSendVO.id }&batchId=${mbnSmsHadSendVO.batchId }";
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
	 <!-- <div class="tubh"><a href="javascript:void(0);" id="deleteBtn">删除</a></div>-->
	  <div class="tubh"><a href="javascript:void(0);" id="collectSmsBtn">保存珍藏</a></div>
	  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
	  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="nextPage" style="color:#067599">下一条</a> </div>
	<div class="table_box">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  	<td width="10%"  align="left" style="vertical-align: top">发送时间：</td>
			<td width="90%"><fmt:formatDate value="${mbnSmsHadSendVO.commitTime }"  type="both" />
<a onclick="javascript:showSendResult('${ctx }/mbnSmsHadSendAction/getBatchSms.action?batchId=${mbnSmsHadSendVO.batchId}&smsHadSendId=${mbnSmsHadSendVO.id}');" href="javascript:void(0);">&nbsp;&nbsp;查看发送结果</a>
<a onclick="javascript:showSendResult('${ctx }/sms/smssend/jsp/hadsend_replyInfo.jsp?smsHadSendId=${mbnSmsHadSendVO.id }&batchId=${mbnSmsHadSendVO.batchId}');" href="javascript:void(0);">&nbsp;&nbsp;${mbnSmsHadSendVO.replyCount }条回复</a></td>
			</tr>
		  <tr>
		  	<td width="10%" align="left" style="vertical-align: top">短信标题：</td>
			<td width="90%">${mbnSmsHadSendVO.title }</td>
			</tr>
		  <tr>
			<td  align="left">短信内容：</td>
			<td ><span style="color:#006699">${mbnSmsHadSendVO.content }</span></td>
			</tr>
		</table>
		<input type="hidden" id="smsHadSendId" value="${mbnSmsHadSendVO.id }"/>
		
	</div>
</div>

	<div style="clear:both; height:0px; overflow:hidden;"></div> 
</body>
</html>

