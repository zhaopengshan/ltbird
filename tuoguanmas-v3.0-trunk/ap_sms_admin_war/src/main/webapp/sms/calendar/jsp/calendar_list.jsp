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
<title>日历提醒</title>
<script type="text/javascript" src="./sms/calendar/js/calendar_info.js"></script>
<script type="text/javascript" src="./sms/calendar/js/calendar_list.js"></script>
<script type="text/javascript">
	calendarOnload();
</script>
</head>
<body>
<div id="calendarBody" class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top" id="sms_calendar_frame">
			    	<div><table id="calendarGrid" ></table></div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="list">
				          <ul>
				            <li id="calendar_hadsend_menu" class="zhan"><a onclick="switchCalendarType('send')" href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/vote/lise_lcon1.gif" style="vertical-align:middle"/> 已发送</a></li>
				           <!--   <li id="calendar_hadreply_menu" ><a onclick="switchVoteType('reply')" href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/vote/ico-huifu.png" style="vertical-align:middle"/>&nbsp;已回复</a></li>-->
				            <li id="calendar_readysend_menu" ><a onclick="switchCalendarType('notSend')" href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/vote/lise_lcon3.gif" style="vertical-align:middle"/>&nbsp;待发送</a></li>
				          </ul>
				        </div>
				        <div class="box_contents">
				        <form id="calendarSearch" method="post">
				          <div id="calendarTitle" class="search_list">
					            <h1>-按任务名称查找</h1>
					            <ul>
					              <li>
					                <input id="calendarTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="calendarTypeBy" class="search_list">
					            <h1>-按提醒类别查找</h1>
					            <ul>
					              <li>
					              	<select id="calendarTypeSearch" title="请选择..." name="Input2" style="margin:2px; width:167px;">
					              	<option id='calendarTypeSearch3'>请选择……</option>
					              	<option id='calendarTypeSearch0'>立即</option>
					              	<option id='calendarTypeSearch1'>定时</option>
					              	<option id='calendarTypeSearch2'>周期</option>
					              	</select>
					              </li>
					            </ul>
				          </div>
				          <div id="calendarTimeInterval" class="datebox_list">
				          <h1>-按有发送时间查找</h1>
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="calendarDateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="calendarDateTo" />
				            </li></ul>
				          </div>
				          <table id="calendarSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a onclick="calendarSearch()" id="calendarSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				            <tr>
				            <td>
				            	<div class="btn_export"><a onclick="exportCalendarList()" href="javascript:void(0);" id="exportCalendarBtn">导出查询结果</a></div>
				            </td>
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

<div id="calendarResultBody" style="display:none;" class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top" id="sms_calendar_frame">
			    	<div id="showCalendarCountMsg" class="botton_box1">
			    	共发送
<span style="color: #FF0000" id="counttotails">1 </span>
条短信，成功
<span style="color:#00cc00" id="countSuccess">0</span>
条， 失败
<span style="color:#FF0000" id="countFail">0</span>
条， 已提交网关
<span style="color:#FF0000" id="countSending">0</span>
条， 已取消
<span style="color:#FF0000" id="countCancel">0</span>
条， 等待发送
<span style="color:#FF0000" id="countWaiting">1</span>
条 
					</div>
			    	<div><table id="calendarResultGrid"></table></div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="box_contents">
				        <form id="calendarSearch" method="post">
				          <div class="search_list">
					            <h1>-按接收人查找</h1>
					            <ul>
					              <li>
					                <input id="calendarResultReceiverSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				           <div class="search_list">
					            <h1>-按手机号查找</h1>
					            <ul>
					              <li>
					                <input id="calendarResultMobileSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div class="search_list">
					            <h1>-按发送结果查找</h1>
					            <ul>
					              <li>
					              	<select id="calendarResultSearch" title="请选择..." name="Input2">
					              	<option>请选择……</option>
					              	<option>等待发送</option>
					              	<option>成功</option>
					              	<option>失败</option>
					              	<option>已提交网关</option>
					              	<option>取消发送</option>
					              	</select>
					              </li>
					            </ul>
				          </div>
				          <div class="search_list">
					            <h1>-按失败原因查找</h1>
					            <ul>
					              <li>
					              	<select id="calendarResultFailSearch" title="请选择..." name="Input2">
					              	<option>请选择……</option>
					              	<option>超时</option>
					              	<option>未知</option>
					              	</select>
					              </li>
					            </ul>
				          </div>
				          <table id="calendarResultSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a onclick="calendarResultValSearch()" id="calendarSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				              <tr>
				            <td>
				            	<div class="btn_export"><a onclick="exportCalendarResultList()" href="javascript:void(0);" id="exportCalendarBtn">导出查询结果</a></div>
				            </td>
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
<div id="calendarReplyBody" style="display:none;" class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top" id="sms_calendar_frame">
			    	<div><table id="calendarReplyGrid"></table></div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="box_contents">
				        <form id="calendarSearch" method="post">
				            <div class="search_list">
					            <h1>-按回复人查找</h1>
					            <ul>
					              <li>
					                <input id="calendarReplyNmSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				           <div class="search_list">
					            <h1>-按回复内容查找</h1>
					            <ul>
					              <li>
					                <input id="calendarReplyContentSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				            <div class="datebox_list">
				          <h1>-按回复时间查找</h1>
				            <ul><li>
				              <input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="calendarReplyDateSearch" />
				            </li>
				            </ul>
				            </div>
				          <table id="calendarSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a onclick="calendarReplySearch()" id="calendarSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
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

