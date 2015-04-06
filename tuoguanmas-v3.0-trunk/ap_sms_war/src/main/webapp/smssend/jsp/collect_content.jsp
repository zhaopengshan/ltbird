<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/easyui/easyui.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="${ctx }/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="javascript" src="${ctx }/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.easyui.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
<title>无标题文档</title>
</head>
<script type="text/javascript">
<!-- 
	function successCalBack(data) {
    	$.messager.show({
            title: '用户操作',
            msg:data.message,
            timeout:5000
        });
    }
    function errorCalBack() {
        $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
    }
    
	$(function() {
		var contacts = new PlaceHolder("contactIds","输入联系人...");
			contacts.init();
		//删除
		$("#deleteBtn").unbind( "click.qs").bind("click.qs",function(){
			var selectedId = $("#smsSelected").val();
			$.ajax({
                url : "${ctx }/mbnSmsSelectedAction/deleteByIds.action",
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: selectedId
                },
                success : function(data) {
                	if(data.resultcode == "success"){
			        	window.location.href="${ctx }/smssend/jsp/collect_list.jsp";
			        } else {
			            $.messager.show({
			                title: '用户操作',
			                msg:data.message,
			                timeout:5000
			            });
			        }
                },
                error : errorCalBack
            }); 
		});
		//快速发送
		$("#fastSendBtn").unbind("click.qs").bind("click.qs",function(){
			var selectedId = $("#smsSelected").val();
			var smsContent = $("#smsContent").text();
			var contactIds = $("#contactIds").val();
			var tmp = encodeURI(encodeURI(smsContent));
			var url = '${ctx}/mbnSmsSelectedAction/fastSend.action';
			window.parent.parent.addTabs('selectedId','itemxs'+selectedId,'短信互动','快速转发',url+'?selectedId='+selectedId+'&contactIds='+contactIds+'&smsContent=' + tmp);
		});
		$("#returnBtn").unbind("click.rs").bind( "click.rs",function(){
			window.parent.current_url = window.parent.collect_list_url;
			window.parent.rightMenuBar.showCollectBoxMenu();
		});
		$("#editBtn").unbind("click.rs").bind( "click.rs",function(){
			var url = '${ctx}/mbnSmsSelectedAction/edit.action';
			var selectedId = $("#smsSelected").val();
			window.parent.parent.addTabs('selectedId','itemxs'+selectedId,'短信互动','编辑转发',url+'?selectedId='+selectedId);
		});
		<c:if test="${hasFollow!=null&&!hasFollow}" >
			jQuery.messager.alert("系统提示","已经是最后一条！","warning");
		</c:if>
	});
	//前后翻页
	function followPage(direction){
		var url = "${ctx }/mbnSmsSelectedAction/followPage.action?selectedId=${mbnSmsSelected.id }";
		url = url + "&pageDirect=" + direction;
		window.location.href = url;
	}
	 -->
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
							<td colspan="2" id="smsDate"><fmt:formatDate value="${mbnSmsSelected.createTime }"  type="both" /></td>
						  </tr>
						  <tr>
							<td width="70px"  align="left">短信内容：</td>
							<td></td>
						  </tr>
						  <tr>
							<td  align="right"></td>
							<td ><span style="color:#006699" id="smsContent">${mbnSmsSelected.content }</span></td>
						  </tr>
						</table>
						<br />
					</div>
					<div class="huifu_box_noheight"><input type="text" id="contactIds" class="input2"  style="width: 99%; margin-bottom: 5px;" value=""/>
					</div>
					<div class="bottom_box1">
						<input type="hidden" id="smsSelected" value="${mbnSmsSelected.id }"/>
						<div class="tubh"><a href="javascript:void(0);" id="fastSendBtn">快速转发</a></div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

</body>
</html>

