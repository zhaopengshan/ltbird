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
<script type="text/javascript" src="${ctx}/sms/lottery/js/lotteryList.js"></script>
<title>短信抽奖</title>
</head>

<body>
<div id="lotterypopDiv" class="lotterypop" style="display: none;">
		<table width="620"  rules="rows" cellspacing="10" cellpadding="10">
 		  <tr>
		    <td width="100px;"><span class="lotteryName">一等奖</span></td>
		    <td width="300px;"><div class="prizebox">13567676767等X人</div></td>
		    <td style="padding-left: 20px;"><span  class="tubh" ><a href="" class="">开始抽奖</a></span><span class="tubh"><a href="" class="dis">抽奖结束</a></span></td>
		  </tr>
		  <tr>
		    <td width="100px;"><span class="lotteryName">一等奖</span></td>
		    <td width="300px;"><div class="prizebox">13567676767等X人</div></td>
		    <td style="padding-left: 20px;"><span  class="tubh" ><a href="" class="">开始抽奖</a></span><span class="tubh"><a href="" class="dis">抽奖结束</a></span></td>
		  </tr>
		</table>
</div>
<div class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF">
    <div class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="float: left;">
			  <tr>
			    <td valign="top"  id="sms_lottery_frame">
			    	<div>
			    	<table id="lotteryGrid"  ></table>
			    	</div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents" id="filterData">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="list">
				          <ul>
				            <li id="lottery_unSend_menu" class="zhan"><a href="javascript:void(0);" onclick="selectLottery('unsend')"><img src="${ctx }/themes/mas3admin/images/vote/lise_lcon3.gif" style="vertical-align:middle"/>&nbsp;待发送</a></li>
				            <li id="lottery_send_menu" ><a href="javascript:void(0);" onclick="selectLottery('send')"><img src="${ctx }/themes/mas3admin/images/vote/lise_lcon1.gif" style="vertical-align:middle"/> 已发送</a></li>
				            <li id="lottery_award_menu"><a href="javascript:void(0);" onclick="selectLottery('lottery')"><img src="${ctx }/themes/mas3admin/images/lottery/u165_normal.png" style="vertical-align:middle"/>&nbsp;已抽奖</a></li>
				            <%-- <li id="lottery_getLottery_menu"><a href="javascript:void(0);" onclick="selectLottery('lottery')"><img src="${ctx }/themes/mas3admin/images/lottery/u171_normal.png" style="vertical-align:middle"/>&nbsp;已得奖</a></li> --%>
				          </ul>
				        </div>
				        <div class="box_contents">
				          <div id="lotteryTitle" class="search_list">
					            <h1>-按任务名称查找</h1>
					            <ul>
					              <li>
					                <input id="lotteryTitleSearch" name="filterTitle" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="lotteryCreateBy" class="search_list">
					            <h1>-按创建人查找</h1>
					            <ul>
					              <li>
					                <input id="lotteryCreateBySearch" name="filterLoginAccount" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="lotteryUser" class="search_list">
					            <h1>-按参与用户查找</h1>
					            <ul>
					              <li>
					                <input id="lotteryUserSearch" name="filterTos" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="lotteryStatus" class="search_list">
					            <h1>-按目前状态查找</h1>
					            <ul><li>
					                <select id="lotteryStatusSearch" name="filterState" style="width:170px;margin-left:2px;">
						               	<option  value="unsend">待发送</option>
						               	<option value="send" selected="selected" >已发送</option>
						               	<option  value="lottery">已抽奖</option>
						            </select>
					            </li></ul>
				          </div>
				          <table id="lotterySubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="lotterySerarchBtn" onclick="filterLottery()" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				        </div>
				        <div class="box_bottom"><img src="${ctx }/themes/mas3/images/box_bottom.jpg" /></div>
				      </div>
					</div>
					
					
				<div class="right_contents" id="filterResult" style="display: none;">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="box_contents">
				          <div id="" class="search_list">
					            <h1>-按奖品等级查找</h1>
					            <ul>
					              <li>
					                <input id="gradeLevelName" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="" class="search_list">
					            <h1>-按手机号码查找</h1>
					            <ul>
					              <li>
					                <input id="LotteryRusultMobile" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="lotteryUser" class="search_list">
					            <h1>-按中奖时间查找</h1>
					            <ul>
					              <li>
					                <input class="Wdate" style="width:170px;" id="lotteryCreateTime" readonly="readonly"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'});"/>
					              </li>
					            </ul>
				          </div>
				          <table id="" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><input type="hidden" id="lotteryResultById"/><a id="lotteryResultSerarchBtn" onclick="filterLotteryResult()" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				          </table>
				        </div>
				        <div class="box_bottom"><img src="${ctx }/themes/mas3/images/box_bottom.jpg" /></div>
				      </div>
					</div>
			    </td>
			  </tr>
			</table> 

		  </div>
		</div>
		</td>
	</tr>
</table>

</div>
<div class="clear"></div>
</body>
</html>

