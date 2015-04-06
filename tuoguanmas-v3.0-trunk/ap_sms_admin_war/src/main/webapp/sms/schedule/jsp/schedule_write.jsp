<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
    </head>
<body>
<div class="main_body">
<div class="contents">
  <form id="scheduleSmsForm" action="" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents" style="height: 410px;overflow-y: auto;overflow-x: hidden;">
		<div class="table_box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="13%" height="50" align="right"><span style="color:#FF0000">*</span>任务名称：</td>
				<td width="87%"><input type="text" name="textfield2" class="input2" /></td>
			</tr>
			<tr>
				<td height="50" align="right"><span style="color:#FF0000">*</span>接收人：</td>
				<td><input name="textfield2" type="text" class="input2" value="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" /></td>
			</tr>
			<tr>
				<td align="right" valign="top"><span style="color:#FF0000">*</span>提醒内容：</td>
				<td><textarea name="textarea" style="width: 98%; height: 137px; font-size: 12px;"></textarea>
				<div class="tixing_tab">还可以输入350字，本次将以0条计费（编辑时提醒） 短信共XX字，分X条发送</div></td>
			</tr>
    		<tr>
				<td rowspan="3" align="right">提醒方式：</td>
				<td><input name="radiobutton" type="radio" value="radiobutton" checked="checked" />
				立即提醒</td>
			</tr>
			<tr>
				<td><input type="radio" name="radiobutton" value="radiobutton" />
				定时提醒：
				  <input type="text" name="textfield33" class="input3" />
				  <select name="select3">
				<option>0</option>
				  </select>
				年
				<select name="select3">
				  <option>0</option>
				</select>
				分</td>
			</tr>
			<tr>
				<td><input type="radio" name="radiobutton" value="radiobutton" />
				  周期提醒：
				     <select name="select2">
				 <option selected="selected">周期插件</option>
				 </select></td>
			</tr>
			</table>
		</div>
		<div class="bottom_box">
		  <div class="tubh"><a href="#">发送</a></div>
		  <div class="tubh"><a href="#">取消</a></div>
		</div>
	  </div>
      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="scheduleQuery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer;">
	        	<div id="scheduleInputFileContainer">
					<input type="file" id="scheduleInputfile" onchange="selectFile();" class="hide_input_file" name="addrUpload" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png" style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="scheduleSelfsend"><img src="./themes/mas3/images/user.png" style="margin-right:5px;" />发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 10px; margin-top: -5px;">
          		<ul id="scheduleTreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;"></ul>
          	</div>
          <div class="box_bottom"><img src="./themes/mas3/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
    </tr>
  </table>
  </form>
</div>
</div>
	<script type="text/javascript">
	var zNodes =[];
	var log, className = "dark";
	
	var scheduleTextChanged = false;
	var curSchedulePage = 1;
	function addGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"addToReceiver('"+treeNode.name+"');return false;\"></a>";//("+treeNode.counts+")
				aObj.after(editStr1);
			}
		}
		if (treeNode.level>0) return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button lastPage' id='lastBtnSchedule_" + treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnSchedule_" + treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnSchedule_" + treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnSchedule_" + treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtnSchedule_"+treeNode.id);
		var prev = $("#prevBtnSchedule_"+treeNode.id);
		var next = $("#nextBtnSchedule_"+treeNode.id);
		var last = $("#lastBtnSchedule_"+treeNode.id);
		treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
		first.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goSchedulePage(treeNode, 1);
			}
		});
		last.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goSchedulePage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goSchedulePage(treeNode, treeNode.page-1);
			}
		});
		next.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goSchedulePage(treeNode, treeNode.page+1);
			}
		});
	}
	
	function getScheduleUrl(treeId, treeNode) {
		if(treeNode == null){
			return "${ctx}/addr/getAddr.action";
		}else{
			var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
			aObj = $("#" + treeNode.tId + "_a");
			aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
			return "${ctx}/addr/getAddr.action?" + param;
		}
		
	}
	function goSchedulePage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page<1) treeNode.page = 1;
		if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
		if (curSchedulePage == treeNode.page) return;
		curSchedulePage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("scheduleTreeDemo");
		zTree.reAsyncChildNodes(treeNode, "refresh");
	}

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			if( childNodes[i] && childNodes[i].name){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
		}
		return childNodes;
	}
	function beforeClick(treeId, treeNode, clickFlag) {
		className = (className === "dark" ? "":"dark");
		return (treeNode.click != false);
	}
	function onClick(event, treeId, treeNode, clickFlag) {
		if( treeNode.isParent){
			return;
		}
		addToReceiver(treeNode.name);
	}		

	function addToReceiver(recv){
		if( scheduleTextChanged){
			$('#scheduleReceiver').attr({ value: $('#scheduleReceiver').attr("value")+","+recv });
		}
		else{
			$('#scheduleReceiver').attr({ value: recv });
		}
		scheduleTextChanged = true;
	}
	function checkUserAddr(userAddr){
		if(!userAddr || userAddr==""){
			return false;
		}
		var reg0 = /^1\d{10}\s*(<.*>|\s*)$/;
		var reg1 = /^.*\s*<用户组>$/;
		if( reg0.test(userAddr) || reg1.test(userAddr)){
			return true;
		}
		return false;
	}

	function receiverEventProc(event){
        if (event.type == "focusin" && !scheduleTextChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            scheduleTextChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
        } else {
            switch(event.which) {
                case 27:
                    scheduleTextChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                    scheduleTextChanged = true;
            }
        }
	}

	function smsTextEventProc(){
		var extLen = $("#scheduleReplyText").val().length;
		var extLen2 = $("#scheduleEntSign").val().length;
		var len =$(this).val().length + extLen + extLen2;
        var remain = 350 - len;
        var cnt = Math.ceil(len/70);  
        if( remain<0 ){
        	$("#scheduleSmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
            $("#scheduleSmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
        }
	}
	
	function selectFile(){
		$("#scheduleReceiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
	}
	
	var setting = {
		view: {
			showIcon: false,
			addDiyDom: addGroupCounts,
			fontCss : {"font-size": "14px", color: "#222222", "font-family": "微软雅黑"}
		},
		data: {
			key: {
				title:"mobile"
			},
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		},
		async: {
			enable: true,
			//url:"${ctx}/addr/getAddr.action",
			url: getScheduleUrl,
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};


	$(document).ready(function(){
		$.fn.zTree.init($("#scheduleTreeDemo"), setting, zNodes);
		$('#scheduleSmsText').bind('keyup keypress change', smsTextEventProc);
		$("#scheduleSendAtTime").click(function(){
				$("#scheduleSendTime").css({"display": "inline"});
		});
		$("#scheduleSendNow").click(function(){
			$("#scheduleSendTime").css({"display": "none"});
		});
		$('#scheduleReceiver').bind("keyup focusin focusout", receiverEventProc);

		$( "#scheduleQuery" ).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "${ctx}/addr/getAddrByKey.action",
                    dataType: "json",
                    data: {
                        featureClass: "P",
                        style: "full",
                        maxRows: 12,
                        term: request.term
                    },
                    success: function( data ) {
                        response( $.map( data.addrs, function( item ) {
                            return {
                                label: item.name,
                                value: item.name
                            }
                        }));
                    }
                });
            },
            minLength: 2,
            select: function( event, ui ) {
            	addToReceiver(ui.item.value);
            }
        });
        
    	<c:if test="${ not empty entityMap}">
		    alert('${entityMap["message"]}');
		</c:if>
    	<c:if test="${ not empty smsText}">
    		$('#scheduleSmsText').attr("value","${smsText}");
    		$('#scheduleSmsText').trigger("keyup");
		</c:if>
    	<c:if test="${ not empty title}">
			$('#scheduleTitle').attr("value","${title}");
		</c:if>
    	<c:if test="${ not empty receiver}">
			addToReceiver("${receiver}");
		</c:if>
    	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
    		$("#scheduleSelfsend").bind("click", function(){addToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
		</c:if>
	});
	</script>

</body>
</html>
