<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
  <form id="lotterySmsForm" action="${ctx}/lottery/lotterySms.action" method="post">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents" style="height: 410px;overflow-y: auto;overflow-x: hidden;">
	  <div>
	  <table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_box">
	  <tr>
	    <td width="15%" height="50" align="right"><span style="color:#FF0000">*</span>任务名称：</td>
	    <td><input type="text" id="lotterytitle" name="form.title" class="input2" value="输入短信抽奖任务名称，仅作为查询依据" /></td>
	    </tr>
	  <tr>
	    <td rowspan="2" align="right" valign="top"><span style="color:#FF0000">*</span>参与人范围：</td>
	    <td><input id="lotteryReceiver"  name="form.tos" type="text" class="input2" value="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" /></td>
	  </tr>
	  <tr>
	    <!-- <td><span style="color:#009933">（注:可直接输入手机号码，号码之间用中文或英文逗号隔开）</span></td> -->
	  </tr>
	  <tr>
	    <td  align="right"><span style="color:#FF0000">*</span>邀请短信内容：</td>
	    <td><textarea id="lotteryContent" name="form.content" style="width: 98%; height: 87px; font-size: 12px;"></textarea>
	    </br>
	    <input id="lotteryreplyText" type="text" class="input2"  readonly="readonly"  name="replyText" style="display: none;"/>
	    <input type="hidden" id="lotteryreplyCode" name="form.replyCode"/>
	    <div id="lotterySmsTips" class="tixing_tab">   还可以输入335字，本次将以0条计费 短信共0字(含企业签名)，分0条发送</div></td>
	    </td>
	  </tr>
	  <tr>
	   <td  align="right"><span style="color:#FF0000">*</span>奖项设置：</td>
	    <td  style="line-height: 30px; padding: 4px;">
	    <table width="80%" cellspacing="0" cellpadding="0"  style="float: left;" id="lotteryTable">
	    	<thead>
	    	<tr>
	        <th width="8%" class="head5">序号</th>
	        <th width="20%" class="head3">奖项名称</th>
	        <th width="50%" class="head3">奖品内容</th>
	        <th width="11%" class="head3">名额</th>
	        <th width="11%" class="head3">操作</th>
	        </tr>
	      </thead>
	      <tbody>
	      <tr>
	        <td class="td22" align="center">1</td>
	        <td class="td7" align="center"><input name="gradeLevelName1" style="width:90%;"/></td>
	        <td class="td7" align="center"><input name="awardContent1" style="width:90%;"/></td>
	        <td class="td7" align="center"><input name="quotaOfPeople1" style="width: 18px;"/></td>
	        <td class="td7" align="center"><a href="javascript:void(0);" onclick="awardClean(this);"><img width="16" height="16" src="${ctx }/themes/mas3admin/images/vote/u109_normal.gif"/></a></td>
	      </tr>
	      <tr>
	        <td class="td22" align="center">2</td>
	        <td class="td7" align="center"><input name="gradeLevelName2" style="width:90%;"/></td>
	        <td class="td7" align="center"><input name="awardContent2" style="width:90%;"/></td>
	        <td class="td7" align="center"><input name="quotaOfPeople2" style="width: 18px;"/></td>
	        <td class="td7" align="center"><a href="javascript:void(0); " onclick="awardRemove(this);"><img width="16" height="16" src="${ctx }/themes/mas3admin/images/vote/u109_normal.gif"/></a></td>
	      </tr>
	      </tbody>
	    </table>
	    <div class="tubh" style="float: left;"><a href="javascript:void(0);" onclick="javascript:addAward();">增加奖项</a></div>
	    </td>
	    </tr>
		  <tr>
	    <td height="50" align="right"><span style="color:#FF0000">*</span>有效时间：</td>
	    <td>
	    <input type="text" class="Wdate" id="lotteryBeginTime" name="startTime" readonly="readonly"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});"/>
	       到
	 	<input type="text" class="Wdate" id="lotteryEndTime" name="endTime" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});"/>
	</td>
	    </tr>
	 <tr style="display:none;">
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>通道选择：</td>
	              <td width="91%">
	             	 移动
					<select id="lotteryNoticeydTunnel" name="ydTunnel" style="width:220px;">
						<c:set var="ydTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 1) or (tunnel.classify eq 2) or (tunnel.classify eq 7)}">
								<c:if test="${(ydTunnelNumber eq '-1000')}">
									<c:set var="ydTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}" <c:if test="${(tunnel.id eq ydTunnel)}"><c:set var="ydTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }
								</option></c:if>
						</c:forEach>

					</select>
					通道可用余量<span id="lotteryNoticeydTip">${ydTunnelNumber}</span>条
					<br/>
					联通
					<select id="lotteryNoticeltTunnel" name="ltTunnel" style="width:220px;">
						<c:set var="ltTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 3) or (tunnel.classify eq 4) or (tunnel.classify eq 7)}">
								<c:if test="${(ltTunnelNumber eq '-1000')}">
									<c:set var="ltTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}"  <c:if test="${(tunnel.id eq ltTunnel)}"><c:set var="ltTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }</option>
								</c:if>
						</c:forEach>
					</select>
					通道可用余量<span id="lotteryNoticeltTip">${ltTunnelNumber}</span>条
					<br/>
					电信
					<select id="lotteryNoticedxTunnel"  name="dxTunnel" style="width:220px;">
						<c:set var="dxTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 5) or (tunnel.classify eq 6) or (tunnel.classify eq 7)}">
								<c:if test="${(dxTunnelNumber eq '-1000')}">
									<c:set var="dxTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}" <c:if test="${(tunnel.id eq dxTunnel)}"><c:set var="dxTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }</option>
								</c:if>
						</c:forEach>
					</select>
					通道可用余量<span id="lotteryNoticedxTip">${dxTunnelNumber}</span>条
					<br/>
					注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送
				  </td>
	            </tr>
	            
	            <tr>
              <td width="12%" height="50" align="right">企业签名：</td>
              <td width="91%"><input type="text" id="LotteryEntSign" name="entSign" class="input_entSign" readonly="readonly" value="${sessionScope.ent_sms_sign}"/></td>
            </tr>
            <tr>
				<td style="height:18px; line-height: 18px;" align="right"></td>
				<td style="height:18px; line-height: 18px;" >
				  <span style="color:#FF0000">*</span>如果用户收到的短信后缀签名与平台不符，请联系客户经理或者平台管理员修改短信后缀签名
				</td>
			</tr>
	        <tr class="ui-helper-hidden" id="waitTipLottery">
				<td width="12%" height="50" align="right"></td>
	            <td height="50" ><span id="loadingTip" class="needtip"><img src="themes/mas3admin/images/helper/loading.gif"/>正在提交网关，请耐心等待。请勿重复执行此操作！</span></td>
	        </tr> 
		</table>
		<div class="bottom_box"><input  type="hidden" name="awardNum"/>
	       <div class="tubh"><a id="lotterySubmit" href="javascript:void(0);" onclick="saveLottery();">提交</a></div>
	       <div class="tubh"><a href="javascript:lotteryRecall()" >重置</a></div>
	     </div>
		</div>
		</div> 
      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="lotteryQuery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer;position:relative;">
	        	<div id="lotteryInputFileContainer">
					<input type="file" id="lotteryInputfile" onchange="selectFile();" class="hide_input_file" name="addrUpload" style="width:200px;" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png"  style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="lotterySelfsend"><img src="./themes/mas3/images/user.png" style="margin-right:5px;"/>发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 10px; margin-top: -5px;">
          		<ul id="lotteryTreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;"></ul>
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
	
	function lotteryRecall(){
        var localUrl = "./lottery/writeSms.action";
        $(".menu_items > li > ul > li > a:[taburl='"+localUrl+"']").click();
        document.getElementById("lotterySmsForm").reset();
        $("#lotteryReceiver").val("可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送");
        
	}
	
	function AwradIsTrue(){
		var count=$("#lotteryTable tr:gt(0)").size();
		for(i=1;i<=count;i++){
			var gradeLevelName=$.trim($("[name=gradeLevelName"+i+"]").val());
			var awardContent=$.trim($("[name=awardContent"+i+"]").val());
			var quotaOfPeople=$.trim($("[name=quotaOfPeople"+i+"]").val());
			//alert("c"+quotaOfPeople+"0000000"+(/[^\d]/.test(quotaOfPeople)));
			if(gradeLevelName==null ||  awardContent==null  || quotaOfPeople==null  || (/[^\d]/.test(quotaOfPeople)) ){
				return true;
			}
		}
	}
	
	function awardClean(obj){
		$(obj).closest("tr").find("input").val("");
	}
	
	function awardRemove(obj){
		var parentTR=$(obj).closest("tr");
		var index=$("#lotteryTable tbody tr").index(parentTR);
		parentTR.nextAll().each(function(i){
			$(this).find("td").first().html(index+i+1);
			$(this).find("td:eq(1)").find("input").attr("name","gradeLevelName"+(index+i+1));
			$(this).find("td:eq(2)").find("input").attr("name","awardContent"+(index+i+1));
			$(this).find("td:eq(3)").find("input").attr("name","quotaOfPeople"+(index+i+1));
			i++;
		});
		$(obj).closest("tr").remove();
	}
	
	function addAward(){
		var i=$("#lotteryTable tr:gt(0)").size()+1;
		var str="<tr>"+
	        "<td class=\"td22\" align=\"center\">"+i+"</td>"+
	        "<td class=\"td7\" align=\"center\"><input name=\"gradeLevelName"+i+"\" style=\"width:90%;\"/></td>"+
	        "<td class=\"td7\" align=\"center\"><input name=\"awardContent"+i+"\" style=\"width:90%;\"/></td>"+
	        "<td class=\"td7\" align=\"center\"><input name=\"quotaOfPeople"+i+"\" style=\"width: 18px;\"/></td>"+
	        "<td class=\"td7\" align=\"center\"><a href=\"javascript:void(0); \" onclick=\"awardRemove(this);\"><img width=\"16\" height=\"16\" src=\"${ctx }/themes/mas3admin/images/vote/u109_normal.gif\"/></a></td>"+
	      "</tr>";
		$("#lotteryTable tr:last").after(str);
	}
	var zNodes =[];
	var log, className = "dark";
	
	var lotteryTextChanged = false;
	var curLotteryPage = 1;
	function addGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"addToReceiver('"+treeNode.name+"');return false;\"></a>" ;//("+treeNode.counts+")
				aObj.after(editStr1);
			}
		}
		if (treeNode.level>0) return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button lastPage' id='lastBtnLottery_" + treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnLottery_" + treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnLottery_" + treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnLottery_" + treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtnLottery_"+treeNode.id);
		var prev = $("#prevBtnLottery_"+treeNode.id);
		var next = $("#nextBtnLottery_"+treeNode.id);
		var last = $("#lastBtnLottery_"+treeNode.id);
		treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
		first.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goLotteryPage(treeNode, 1);
			}
		});
		last.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goLotteryPage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goLotteryPage(treeNode, treeNode.page-1);
			}
		});
		next.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goLotteryPage(treeNode, treeNode.page+1);
			}
		});
	}
	
	function getLotteryUrl(treeId, treeNode) {
		if(treeNode == null){
			return "${ctx}/addr/getAddr.action";
		}else{
			var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
			aObj = $("#" + treeNode.tId + "_a");
			aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
			return "${ctx}/addr/getAddr.action?" + param;
		}
		
	}
	function goLotteryPage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page<1) treeNode.page = 1;
		if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
		if (curLotteryPage == treeNode.page) return;
		curLotteryPage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("lotteryTreeDemo");
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
/* 		if( treeNode.isParent){
			return;
		} */
		addToReceiver(treeNode.name);
	}		

	function addToReceiver(recv){
		if( lotteryTextChanged){
			$('#lotteryReceiver').attr({ value: $('#lotteryReceiver').attr("value")+","+recv });
		}
		else{
			$('#lotteryReceiver').attr({ value: recv });
		}
		lotteryTextChanged = true;
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

	function lotteryTitleEventProc(event){
        if (event.type == "focusin" && !lotteryTitleChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            titleChanged = false;
            $(this).attr({ value: "输入短信抽奖任务名称，仅作为查询依据" });
        } else {
            switch(event.which) {
                case 27:
                    titleChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                    titleChanged = true;
            }
        }
	}
	
	function receiverEventProc(event){
        if (event.type == "focusin" && !lotteryTextChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            lotteryTextChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
        } else {
            switch(event.which) {
                case 27:
                    lotteryTextChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                    lotteryTextChanged = true;
            }
        }
	}
	
	function lotterysmsTextEventProc(){
		var extLen = $("#lotteryContent").val().length;
		var entsignLen = $("#LotteryEntSign").val().length;
		var len =extLen;
        var remain = 335 - len-entsignLen;
        var cnt = Math.ceil(len/70);  
        if( remain<0 ){
        	$("#lotterySmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
            $("#lotterySmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
        }
	}
	
	function selectFile(){
		$("#lotteryReceiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
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
			url: getLotteryUrl,
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};

	function addLottery(){
		var lotterytitle=$("#lotterytitle");
		var lotteryReceiver=$("#lotteryReceiver");
		var lotteryContent=$("#lotteryContent");
		var lotteryBeginTime=$("#lotteryBeginTime");
		var lotteryEndTime=$("#lotteryEndTime");
		if(lotterytitle.val()=="" || $.trim(lotterytitle.val())=="" || lotterytitle.val()=="输入短信抽奖任务名称，仅作为查询依据"){
			alert("任务名称为空");
			return false;
		}
		if(lotteryReceiver.val()=="" || $.trim(lotteryReceiver.val())=="" || lotteryReceiver.val()=="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送"){
			alert("参与人为空");
			return false;
		}
		var strs= new Array(); 
		strs=lotteryReceiver.val().split(",");
		var result=true;
		for( var i=0; i<strs.length; i++){
			if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
			result = lotteryCheckUserAddr(strs[i]);
			if(!result){
	            alert('地址不合法 [' + strs[i] + ']');
	        	return false;
			}
		}
		
/* 		if(!lotteryCheckUserAddr(lotteryReceiver.val())){
			alert("参与人不正确");
			return false;
		} */
		if(lotteryContent.val()=="" || $.trim(lotteryContent.val())==""){
			alert("邀请短信内容为空");
			return false;
		}if(AwradIsTrue()){
			alert("奖项设置不正确");
			return false;
		}if(lotteryBeginTime.val()=="" || $.trim(lotteryBeginTime.val())==""){
			alert("有效时间没有开始时间");
			return false;
		}if(lotteryEndTime.val()=="" || $.trim(lotteryEndTime.val())==""){
			alert("有效时间没有结束时间");
			return false;
		}
		if(!contrastDate(lotteryBeginTime.val(),lotteryEndTime.val())){
			alert('结束时间应大于开始时间');
			return false;
		}
		
		var str=$("#lotteryReceiver").val().split(",");
		var tosCount=0;
		for(i=0;i<str.length;i++){
			if(str[i].length>0)
				tosCount++;
		}
		var lotteryCount=0;
		$("#lotteryTable tbody tr").each(function(i){
			lotteryCount+=Number($(this).find("[name='quotaOfPeople"+(i+1)+"']").val());
		});

/* 		if(lotteryCount>tosCount){//抽奖人数大于奖品分配人数
			alert('参与人少于所有奖品分配名额');
			return false;
		} */
		//在这里用ajaxSubmit提交 awardCount后台获取不到  其他参数可以获取到，我想起来了，是否是form的参数才能获取，最后无奈我用了隐藏域传的参数
		var count=$("#lotteryTable tr:gt(0)").size();
		$(":input[name=awardNum]").val(count);
		$("#waitTipLottery").removeClass("ui-helper-hidden");
		return true;
	}
	
	function contrastDate(begin,end){
		//获取年月
		var beginTime=begin.split('-');
		var endTime=end.split('-');
		//获取天
		var beginTimeDay=beginTime[2].split(' ');
		var endTimeDay=endTime[2].split(' ');
		//获取时分 
		var beginTimeHour=beginTimeDay[1].split(':');
		var endTimeHour=endTimeDay[1].split(':');

		var startDate = new Date(beginTime[0], beginTime[1] - 1, beginTimeDay[0], beginTimeHour[0], beginTimeHour[1]);
		var endDate=new Date(endTime[0], endTime[1] - 1, endTimeDay[0], endTimeHour[0], endTimeHour[1]);
		return (startDate-endDate<0);
	}
	
	function saveLottery(){
    	var options = { 
  		        beforeSubmit:  addLottery,  // pre-submit callback 
  		        success:       lotteryCallback,  // post-submit callback 
  		        dataType:		'json'
  		}; 
    	$("#lotterySmsForm").ajaxSubmit(options); 
	}

	function lotteryCallback(responseText, statusText, xhr, $form){
		if(responseText.resultcode == "success" ){
			alert("添加成功");
			$("#waitTipLottery").addClass("ui-helper-hidden");
			var originalUrl = "./sms/lottery/jsp/lottery_list.jsp";
			var tempUrl = "./sms/lottery/jsp/lottery_list.jsp";
	        var localUrl = "./lottery/writeSms.action";
	        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
	        tabpanel.kill(_killId, true);
	        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		}else{
			alert(responseText.message);
			$("#waitTipLottery").addClass("ui-helper-hidden");
		}
	}
	
	function lotteryCheckUserAddr(userAddr){
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
	
	function getLotteryReplyCode(){
		$.ajax({
	        url: "lottery/queryLotteryNumber.action",
	        type:"post",
	        dataType: 'json',
	        data:  { },
	        success:function(data){
	        	document.getElementById("lotteryreplyCode").value=data.code;
	        	document.getElementById("lotteryreplyText").value="回复“CJ"+data.code+"”参与抽奖";
	        	document.getElementById("lotteryreplyCode").value=data.code;
	        	$('#lotteryreplyText').css({"display": "block"});
	        	//smsTextEventProc();
	        },
			error:function(data){
			}
		 }
		);
	}
	
	var lotteryTitleChanged = false;
	$(document).ready(function(){
		<s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getExtCodeStyle()==2">
   			getLotteryReplyCode();
   		</s:if>
		$.fn.zTree.init($("#lotteryTreeDemo"), setting, zNodes);
		//keyup focusin focusout
		$('#lotteryReceiver').bind('keyup keypress change', receiverEventProc );//参与人
		lotterysmsTextEventProc();
		$('#lotteryContent').bind('keyup keypress change', lotterysmsTextEventProc);
		
		$('#lotterytitle').unbind("keyup focusin focusout").bind("keyup focusin focusout", lotteryTitleEventProc);

		$("#lotterySendAtTime").click(function(){
				$("#lotterySendTime").css({"display": "inline"});
		});
		$("#lotterySendNow").click(function(){
			$("#lotterySendTime").css({"display": "none"});
		});
		$('#lotteryReceiver').bind("keyup focusin focusout", receiverEventProc);

		$( "#lotteryQuery" ).autocomplete({
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
    	<c:if test="${ not empty lotteryText}">
    		$('#lotteryContent').attr("value","${lotteryText}");
    		$('#lotteryContent').trigger("keyup");
		</c:if>
    	<c:if test="${ not empty lotteryTitle}">
			$('#lotterytitle').attr("value","${lotteryTitle}");
		</c:if>
    	<c:if test="${ not empty receiver}">
			addToReceiver("${receiver}");
		</c:if>
    	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
    		$("#lotterySelfsend").bind("click", function(){addToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
		</c:if>
	});
	</script>

</body>
</html>
