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
<script type="text/javascript" src="./sms/meeting/js/info_right_menu.js"></script>
<script type="text/javascript">
	var meetNoticerightMenuBar;
	$(function() {
		var meetNoticecontactSearch = new PlaceHolder("meetNoticecontactSearch","输入创建人姓名"),
		meetNoticesmsTitleSearch = new PlaceHolder("meetNoticesmsTitleSearch","输入关键字"),
		   receivernameSearch= new PlaceHolder("meetNoticereceivernameSearch","输入关键字"),
          receivermobileSearch= new PlaceHolder("meetNoticereceivermobileSearch","输入关键字");
		 replynameSearch= new PlaceHolder("meetNoticereplynameSearch","输入关键字"),
         replymobileSearch= new PlaceHolder("meetNoticereplymobileSearch","输入关键字");
		 meetNoticerightMenuBar = new MeetNoticeInfoRightMenu("meetNoticesms_hd_frame",
        		  meetNoticecontactSearch,meetNoticesmsTitleSearch,receivernameSearch,receivermobileSearch,replynameSearch,replymobileSearch);
		 meetNoticerightMenuBar.init();
		$.ajax({
            url: "./meeting/searchFailReason.action",
            type:"POST",
            dataType:"json",
            success:function(data){
            	var $readyfailReason=$("#meetNoticeReadySendResultDetail");
            	var $hadfailReason=$("#meetNoticeHadSendResultDetail");
            	$readyfailReason.children().remove();
            	$hadfailReason.children().remove();
            	$readyfailReason.append("<option value='全部'>全部</option>");
            	$hadfailReason.append("<option value='全部'>全部</option>");
            	for(var i = 0;i < data.length;i++){
            		$readyfailReason.append("<option value='"+data[i].name+"'>"+data[i].name+"</option>");
            		$hadfailReason.append("<option value='"+data[i].name+"'>"+data[i].name+"</option>");
            	}
            }
		});
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
			    <td valign="top"  id="meetNoticesms_hd_frame">
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="list">
				          <ul>
				            <li id="meetNoticesendbox_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/ico_outbox.png" style="vertical-align:middle"/>&nbsp;已提交网关</a></li>
				            <li id="meetNoticehadsendbox_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3/images/ico_outbox.png" style="vertical-align:middle"/>&nbsp;已发送</a></li>
				          </ul>
				        </div>
				        <div class="box_contents">
				        <form id="meetNoticesmsSearch" method="post">
				          <div id="meetNoticesmsTitle" class="search_list ui-helper-hidden">
					            <h1>-按标题查找</h1>
					            <ul>
					              <li>
					                <input id="meetNoticesmsTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="meetNoticecontactName" class="search_list ui-helper-hidden">
					            <h1>-按创建人查找</h1>
					            <ul>
					              <li>
					                <input id="meetNoticecontactSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="meetNoticereceiverSearchDiv" class="search_list ui-helper-hidden">
					            <h1>-按接收人姓名查找</h1>
					            <ul>
					              <li>
					                <input id="meetNoticereceivernameSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
					            <h1>-按接收人手机号查找</h1>
					            <ul>
					              <li>
					                <input id="meetNoticereceivermobileSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="meetNoticereadySendDiv" class="search_list ui-helper-hidden">
				            <h1>-按发送结果查找</h1>
				            <ul>
				              <li>
				               <select id="meetNoticereadySendResult">
				               	<option selected="selected" value="4">全部</option>
				               	<option value="2">成功</option>
				               	<option value="3">失败</option>
				               	<option value="1">已提交网关</option>
				               	<option value="-1">已取消</option>
				               	<option value="0">等待发送</option>
				               </select>
				              </li>
				            </ul>
				            <h1>-按失败原因查找</h1>
				            <ul>
				              <li>
				               <select id="meetNoticeReadySendResultDetail">
				               </select>
				              </li>
				            </ul>
				          </div>
				          <div id="meetNoticehadSendDiv" class="search_list ui-helper-hidden">
				            <h1>-按发送结果查找</h1>
				            <ul>
				              <li>
				               <select id="meetNoticehadSendResult">
				               	<option selected="selected" value="4">全部</option>
				               	<option value="2">成功</option>
				               	<option value="3">失败</option>
				               	<option value="-1">已取消</option>
				               </select>
				              </li>
				            </ul>
				            <h1>-按失败原因查找</h1>
				            <ul>
				              <li>
				               <select id="meetNoticeHadSendResultDetail">
				               </select>
				              </li>
				            </ul>
				          </div>
				          <div id="meetNoticesmsreply" class="search_list ui-helper-hidden">
					            <h1>-按回复人姓名查找</h1>
					            <ul>
					              <li>
					                <input id="meetNoticereplynameSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
					            <h1>-按回复人手机号查找</h1>
					            <ul>
					              <li>
					                <input id="meetNoticereplymobileSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="meetNoticesmsSendTime" class="search_list">
					            <h1>-按发送时间查找</h1>
				          </div>
				          <div id="meetNoticetimeInterval" class="datebox_list">
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="meetNoticedateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="meetNoticedateTo" />
				            </li></ul>
				          </div>
				          <table id="meetNoticerootSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="meetNoticesearchTimeInterval" act="5" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				          <table id="meetNoticereadySendSubmit" class="ui-helper-hidden" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="meetNoticereadySendBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				          <table id="meetNoticehadSendSubmit" class="ui-helper-hidden" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="meetNoticehadSendBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				          <table id="meetNoticesmsReplySubmit" class="ui-helper-hidden" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="meetNoticesmsReplyBtn" href="javascript:void(0);" >查 询</a></div></td>
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

