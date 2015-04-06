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
<script type="text/javascript">
	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/mbnSmsSelectedAction/getSmsDetails.action?selectedId=";
		//$("#sms_hd_frame").load(localUrl+selectedId);
	}
	var gridPro = {
		url:"",//执行地址
		colNames:['状态', '任务名称','提醒类别','提醒内容','最后提醒时间','发送结果'],//列名
		colModel:[//列所有名称的属性
			{name:'status',width:100,align:"center"},
			{name:'title',width:100,align:"center"},
			{name:'content',width:320,formatter:function(data){
					if($.trim(data.content) == "" ){
						return "";
					}else{
						return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content+'</a>';
					}
				}},
			{name:'sendTime',width:100,align:"center"},
			{name:'createBy',width:100,align:"center"},
			{name:'sendResult',width:100,align:"center"}
		],
		buttons: [{//按钮
			text: "编辑",
			name:'edit',
			classes: "",
			click: function(){
				var url = '';
				editSmsFunc(smsGrid,url);
			}
		},{
			text: "删除",
			name:'delete',
			classes: "",
			click: function(){
				var url = '';
				var showMessage = "是否删除选中的{0}条短信？";
                confirmAjaxFunc(smsGrid,url,showMessage);
			}
		}],
		multiselect: true
	};
	var smsGrid;
	$(function() {
		smsGrid = new TableGrid("scheduleGrid",gridPro);
		smsGrid.redrawGrid(gridPro);
	});
</script>
<title>会议通知</title>
</head>

<body>
<div class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top" width="876" id="sms_schedule_frame">
			    	<table id="scheduleGrid"></table>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="list">
				          <ul>
				            <li id="schedule_hadsend_menu" class="zhan"><a href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/schedule/lise_lcon1.gif" style="vertical-align:middle"/> 已发送</a></li>
				            <li id="schedule_hadreply_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/schedule/ico-huifu.png" style="vertical-align:middle"/>&nbsp;已回复</a></li>
				            <li id="schedule_readysend_menu" ><a href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/schedule/lise_lcon3.gif" style="vertical-align:middle"/>&nbsp;待发送</a></li>
				          </ul>
				        </div>
				        <div class="box_contents">
				        <form id="scheduleSearch" method="post">
				          <div id="scheduleTitle" class="search_list">
					            <h1>-按任务名称查找</h1>
					            <ul>
					              <li>
					                <input id="scheduleTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="scheduleCreateBy" class="search_list">
					            <h1>-按提醒内容查找</h1>
					            <ul>
					              <li>
					                <input id="scheduleCreateBySearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="scheduleRemind" class="search_list">
					            <h1>-按提醒类别查找</h1>
					            <ul><li>
					                <select id="scheduleRemindSearch">
						               	<option selected="selected" >提醒类别</option>
						            </select>
					            </li></ul>
				          </div>
				          <div id="scheduleTimeInterval" class="datebox_list">
				          <h1>-按发送时间查找</h1>
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="scheduleDateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="scheduleDateTo" />
				            </li></ul>
				          </div>
				          <table id="scheduleSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="scheduleSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
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

