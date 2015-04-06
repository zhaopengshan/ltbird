<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<script language="javascript" src="./sms/smssend/js/info_right_menu.js" type="text/javascript" ></script>
<script type="text/javascript">
	var rightMenuBar;
	$(function() {
		var contactSearch = new PlaceHolder("contactSearch","输入姓名/手机"),
		    smsTitleSearch = new PlaceHolder("smsTitleSearch","输入关键字");
		rightMenuBar = new InfoRightMenu("sms_hd_frame",contactSearch,smsTitleSearch);
		rightMenuBar.init();
	});
</script>
<title>短信收件箱</title>

</head>

<body>
<div class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top"  id="sms_hd_frame">
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="list">
				          <ul>
				            <li id="collect_menu" class="zhan"><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/lise_lcon2.gif" style="vertical-align:middle"/> 珍藏记录</a></li>
				            <li id="inbox_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/ico_Inbox.png" style="vertical-align:middle"/>&nbsp;收件箱</a></li>
				            <li id="sendbox_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/ico_outbox.png" style="vertical-align:middle"/>&nbsp;待发箱</a></li>
				            <li id="hadsendbox_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/ico_outbox.png" style="vertical-align:middle"/>&nbsp;已发箱</a></li>
				            <li id="draft_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/ico_Draft.gif" style="vertical-align:middle"/>&nbsp;草稿箱</a></li>
				          </ul>
				        </div>
				        <div class="box_contents">
				        <form id="smsSearch" method="post">
				          <div id="contactName" class="search_list ui-helper-hidden">
					            <h1>-按联系人查找</h1>
					            <ul>
					              <li>
					                <input id="contactSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="smsTitle" class="search_list ui-helper-hidden">
					            <h1>-按标题查找</h1>
					            <ul>
					              <li>
					                <input id="smsTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="smsSendTime" class="search_list">
					            <h1>-按发送时间查找</h1>
					            <ul>
					              <li class="zhan"><a id="searchAll" act="1" href="javascript:void(0);">所有</a></li>
					              <li><a id="searchDay" act="2" href="javascript:void(0);">一天内</a></li>
					              <li><a id="searchWeek" act="3" href="javascript:void(0);">一周内</a></li>
					              <li><a id="searchMonth" act="4" href="javascript:void(0);">一月内</a></li>
					            </ul>
				          </div>
				          <div id="timeInterval" class="datebox_list">
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="dateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="dateTo" />
				            </li></ul>
				          </div>
				          <div id="readySendDiv" class="search_list ui-helper-hidden">
				            <h1>-按发送结果查找</h1>
				            <ul>
				              <li>
				               <select id="readySendResult">
				               	<option selected="selected" value="4">全部</option>
				               	<option value="2">成功</option>
				               	<option value="3">失败</option>
				               	<option value="1">已提交网关</option>
				               	<option value="-1">已取消</option>
				               	<option value="0">等待发送</option>
				               </select>
				              </li>
				            </ul>
				          </div>
				          <div id="hadSendDiv" class="search_list ui-helper-hidden">
				            <h1>-按发送结果查找</h1>
				            <ul>
				              <li>
				               <select id="hadSendResult">
				               	<option selected="selected" value="4">全部</option>
				               	<option value="2">成功</option>
				               	<option value="3">失败</option>
				               	<option value="-1">已取消</option>
				               </select>
				              </li>
				            </ul>
				          </div>
				          <table id="rootSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="searchTimeInterval" act="5" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				          <table id="readySendSubmit" class="ui-helper-hidden" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="readySendBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				          <table id="hadSendSubmit" class="ui-helper-hidden" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="hadSendBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				          <table id="exportSearchSubmit" class="" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_export"><a id="exportResultBtn" href="javascript:void(0);" >导出查询结果</a></div></td>
				            </tr>
				          </table>
				        </form>
				        </div>
				        <div class="box_bottom"><img src="${ctx }/themes/mas3/images/box_bottom.jpg" /></div>
				      </div>
					</div>
			    </td>
			  </tr>
			</table> 
		  </div>
		</DIV>
		</td>
	</tr>
</table>

</div>
<div class="clear"></div>
</body>
</html>

