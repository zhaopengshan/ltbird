<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>
<script type="text/javascript">
	function successCalBack(data) {
        alert(data.message);
    }
    function errorCalBack() {
        alert("出现系统错误，请稍后再试");
    }
    
	$(function() {
		var contacts = new PlaceHolder("contactIds","输入联系人...");
			contacts.init();
		rightMenuBar.hidExportHref();
		//删除
		$("#deleteBtn").unbind( "click.qs").bind("click.qs",function(){
			var selectedId = $("#smsSelected").val();
			if(confirm("是否删除本条短信？")){
				$.ajax({
	                url : "${ctx }/mbnSmsSelectedAction/deleteByIds.action",
	                type : 'post',
	                dataType: "json",
	                data: {
	                	smsIds: selectedId
	                },
	                success : function(data) {
	                	successCalBack(data);
	                	if(data.resultcode == "success"){
				        	href="${ctx }/sms/smssend/jsp/collect_list.jsp";
				        	$("#sms_hd_frame").load(href);
				        }
	                },
	                error : errorCalBack
	            }); 
			}
		});
		//快速发送
		$("#fastSendBtn").unbind("click.qs").bind("click.qs",function(){
			var selectedId = $("#smsSelected").val();
			var smsContent = $("#smsContent").text();
			var contactIds = $("#contactIds").val();
			var tmp = encodeURI(encodeURI(smsContent));
			var url = '${ctx}/mbnSmsSelectedAction/fastSend.action';
		    var originalUrl = "./smssend/writesms.action";
		    var tempUrl = url+'?selectedId='+selectedId+'&contactIds='+contactIds+'&smsContent=' + tmp;
		    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
		    try{
		        tabpanel.kill(killId);
		    }catch(e){
		    }
		    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
		    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			current_url = collect_list_url;
			rightMenuBar.showCollectBoxMenu();
		});
		$("#editBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsSelectedAction/edit.action';
			var selectedId = $("#smsSelected").val();
		    var originalUrl = "./smssend/writesms.action";
		    var tempUrl = url+'?selectedId='+selectedId;
		    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
		    try{
		        tabpanel.kill(killId);
		    }catch(e){
		    }
		    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
		    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			alert("已经是最后一条！");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsSelectedAction/followPage.action?selectedId=${mbnSmsSelected.id }";
		url = url + "&pageDirect=" + direction;
		$("#sms_hd_frame").load(url);
	}
</script>
<body>
<div class="contents">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
				<div class="left_contents">
					<div class="botton_box">
					  <div class="tubh"><a id="returnBtn" href="javascript:void(0);">返回</a></div>
					  <div class="tubh"><a href="javascript:void(0);" id="editBtn">编辑发送</a></div>
					  <div class="tubh"><a href="javascript:void(0);" id="deleteBtn">删除</a></div>
					  <a href="javascript:void(0);" onclick="javascript: followPage(0);" style="color:#067599">上一条</a>
					  <a href="javascript:void(0);" onclick="javascript: followPage(1);" id="nextPage" style="color:#067599">下一条</a> </div>
					<div class="table_box">
						<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
						  <tr>
						  	<td width="10%" align="left" style="vertical-align: top">保存时间：</td>
							<td width="90%" id="smsDate"><fmt:formatDate value="${mbnSmsSelected.createTime }"  type="both" /></td>
						  </tr>
						  <tr>
							<td width="70px"  align="left">短信内容：</td>
							
							<td ><span style="color:#006699" id="smsContent">${mbnSmsSelected.content }</span></td>
						  </tr>
						</table>
						<br />
					</div>
					<div class="huifu_box_noheight"><input type="text" id="contactIds" class="input2"  style="width: 99%; margin-bottom: 5px;" value=""/>
					</div>
					<div class="bottom_box1" style="display:none;">
						<input type="hidden" id="smsSelected" value="${mbnSmsSelected.id }"/>
						<div class="tubh"><a href="javascript:void(0);" id="fastSendBtn">快速转发</a></div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

	<div style="clear:both; height:0px; overflow:hidden;"></div> 
</body>
</html>

