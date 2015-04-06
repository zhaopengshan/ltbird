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
	<script type="text/javascript">

	var curBirthdayPage = 1;
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
		var addStr = "<span class='button lastPage' id='lastBtnBirthday_" + treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnBirthday_" + treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnBirthday_" + treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnBirthday_" + treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtnBirthday_"+treeNode.id);
		var prev = $("#prevBtnBirthday_"+treeNode.id);
		var next = $("#nextBtnBirthday_"+treeNode.id);
		var last = $("#lastBtnBirthday_"+treeNode.id);
		treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
		first.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goBirthdayPage(treeNode, 1);
			}
		});
		last.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goBirthdayPage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goBirthdayPage(treeNode, treeNode.page-1);
			}
		});
		next.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goBirthdayPage(treeNode, treeNode.page+1);
			}
		});
	}
	
	function getBirthdayUrl(treeId, treeNode) {
		if(treeNode == null){
			return "${ctx}/addr/getAddr.action";
		}else{
			var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
			aObj = $("#" + treeNode.tId + "_a");
			aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
			return "${ctx}/addr/getAddr.action?" + param;
		}
		
	}
	function goBirthdayPage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page<1) treeNode.page = 1;
		if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
		if (curBirthdayPage == treeNode.page) return;
		curBirthdayPage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("birthdaytreeDemo");
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
		addToReceiver(treeNode.name);
	}		

	function addToReceiver(recv){
		if( textChanged){
			$('#receiver').attr({ value: $('#receiver').attr("value")+","+recv });
		}
		else{
			$('#receiver').attr({ value: recv });
		}
		textChanged = true;
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
	// 检查输入
	function checkInput(){
		if( $('#title').attr("value") == '输入生日祝福短信的标题，作为查询依据' || $('#title').attr("value") == ""){
			alert('标题为空');
			return false;
		}
		if( (!textChanged || $('#receiver').attr("value") == "") && $('#addrUpload').val() == ""){
            alert('接收人为空');
            return false;
		}
		// 检查收件人
		if( $('#addrUpload').val() == ""){
			if( !textChanged){
				$('#receiver').attr("value","");
			}
		}else if( textChanged){
			var strs= new Array(); 
			strs=$('#receiver').attr("value").split(",");
			var result=true;
			for( var i=0; i<strs.length; i++){
				if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
				result = checkUserAddr(strs[i]);
				if(!result){
		            alert('地址不合法 [' + strs[i] + ']');
		        	return false;
				}
			}			
		}
		if( $('#smsText').attr("value") == "" || $.trim( $('#smsText').attr("value") ) == ""){
            alert('短信内容为空');
            return false;
		}
		if( $("#sendAtTime").attr("checked") == 'checked'){
			if( $("#sendTime").attr("value") == ""){
                alert('定时时间为空');
                return false;
			}
		}
        smsSending=true;
		return true;
	}

	function receiverEventProc(event){
        if (event.type == "focusin" && !textChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            textChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号”，“隔开，支持向移动、联通、电信用户发送" });
        } else {
            switch(event.which) {
                case 27:
                    textChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                    textChanged = true;
            }
        }
	}
	function titleEventProc(event){
        if (event.type == "focusin" && !titleChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            titleChanged = false;
            $(this).attr({ value: "输入生日祝福短信的标题，仅作为查询依据" });
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
	function saveEventProc(event){
        if( $("#smsText").attr("value") == "" || $.trim( $("#smsText").attr("value") ) == ""){
            alert('短信内容为空');
            return;
        }
       	$.ajax({
            url : event.data.url,
            type : 'post',
            dataType: "json",
            data: {
            	smsText: $("#smsText").attr("value"),
            	title: $("#title").attr("value")
            },
            success : function(data) {
                if(data.resultcode == "success"){
                    alert(data.message);
                } else {
                    alert(data.message);
                }
            },
            error : function() {
                alert("出现系统错误，请稍后再试");
            }
        }); 
	}

	function smsTextEventProc(){
		var extLen = $("#replyText").val().length;
		var extLen2 = $("#entSign").val().length;
		var len =$(this).val().length + extLen + extLen2;
		var remain = 335 - len;
        var cnt
        if(len<=70){
        	cnt=1;
        }else{
        	cnt = Math.ceil(len/67);  
        }
        if( remain<0 ){
        	$("#smsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
            $("#smsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 "&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)");
        }
	}
	
	function setRemainNumber(event){
		var selTunnelId=$("#"+event.data.sel).val();
		$.ajax({
            url : event.data.url,
            type : 'post',
            dataType: "json",
            data: {
				selTunnelId: selTunnelId
            },
            success : function(data) {
                if(data && data.resultcode == "success"){
                	$("#"+event.data.tip).text(data.remain);
                }
                else{
                	alert("出现系统错误，请稍后再试");
                }
            },
            error : function() {
                alert("出现系统错误，请稍后再试");
            }
        }); 
	}
	function selectFile(){
		$("#receiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
	}

    /**
    *
    * 提交表单成功后处理方法
    *
    */  
  	function sendSms(responseText, statusText, xhr, $form){
		if(responseText.resultcode == "success" ){
			alert(responseText.message);
			var originalUrl = "./sms/birthday/jsp/sms_info.jsp";
	        var tempUrl = "./sms/birthday/jsp/sms_info.jsp";
	        var localUrl = "./birthday/writesms.action";
	        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
	        tabpanel.kill(_killId, true);
	        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		}else{
			alert(responseText.message);
		}
		smsSending = false;
	}

	var smsSending = false;
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
			url: getBirthdayUrl,
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};

	var zNodes =[];
	var log, className = "dark";
	
	var textChanged = false;
	var titleChanged = false;

	$(document).ready(function(){
		$.fn.zTree.init($("#birthdaytreeDemo"), setting, zNodes);
		$('#smsText').unbind('keyup keypress change').bind('keyup keypress change', smsTextEventProc);
		$("#sendAtTime").unbind("click").click(function(){
				$("#sendTime").css({"display": "inline"});
		});
		$("#sendNow").unbind('click').click(function(){
			$("#sendTime").css({"display": "none"});
		});
		$('#receiver').unbind("keyup focusin focusout").bind("keyup focusin focusout", receiverEventProc);
		$('#title').unbind("keyup focusin focusout").bind("keyup focusin focusout", titleEventProc);
        $("#sendButton").unbind("click").bind("click",function(){
            if( smsSending){
                alert("正在发送中");
                return;
            }
        	var options = { 
      		        beforeSubmit:  checkInput,  // pre-submit callback 
      		        success:       sendSms,  // post-submit callback 
      		        dataType:		'json'
      		}; 
        	$("#smsForm").ajaxSubmit(options); 
        });
        $("#cancelButton").unbind("click").bind("click",function(){
        	$("#smsForm")[0].reset();
        });
        $("#saveButton").unbind("click").bind("click",{url: "${ctx}/mbnSmsDraftAction/saveDraft.action"}, saveEventProc);
		$("#needReply").unbind("click").bind("click",function(){
			if(this.checked){
				$.ajax({
                    url: "${ctx}/birthday/queryTaskNumber.action",
                    dataType: "json",
                    success: function( data ) {
                        if( data && data.resultcode && data.resultcode == 'success'){
                            $("#replyCode").attr("value",data.code);
                        	$("#replyText").attr("value","回复此短信编辑“HD"+data.code+"回复内容”");
                        }
                        else{
                        	alert("获取回复编号错误，请稍后再试");
                        }
                        $('#smsText').trigger("keyup");
                    }
                });
			}else{$("#replyCode").attr("value","");$("#replyText").attr("value","");$('#smsText').trigger("keyup");}			
		});
		$("#ydTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ydTunnel', tip:'ydTip'}, setRemainNumber);
		$("#ltTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'ltTunnel', tip:'ltTip'}, setRemainNumber);
		$("#dxTunnel").unbind("change").bind("change",{url: "${ctx}/smsTunnelAction/queryTunnelRemain.action",sel:'dxTunnel', tip:'dxTip'}, setRemainNumber);

        $( "#query" ).autocomplete({
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
    		$('#smsText').attr("value","${smsText}");
    		$('#smsText').trigger("keyup");
		</c:if>
    	<c:if test="${ not empty title}">
			$('#title').attr("value","${title}");
		</c:if>
    	<c:if test="${ not empty receiver}">
			addToReceiver("${receiver}");
		</c:if>
    	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
    		$("#selfsend").unbind("click").bind("click", function(){addToReceiver('${sessionScope.SESSION_USER_INFO.mobile}');});
		</c:if>
	});
	</script>
<body>
<div class="main_body">
<div class="contents">
  <form id="smsForm" action="${ctx }/birthday/send.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents">
	        <div class="table_box">
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>标题：</td>
	              <td width="91%"><input id="title" name="title" type="text" class="input2" value="输入生日祝福短信的标题，仅作为查询依据"/></td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right" valign="top"><span style="color:#FF0000">*</span>接收人：</td>
	              <td width="91%"><input id="receiver" name="receiver" type="text" class="input2" value="可同时发给多人，以逗号”，“隔开，支持向移动、联通、电信用户发送" />
	               <div id="" class="tixing_tab">   已选入联系人XXX个，其中有效号码（设置了生日日期）XXX个</div>
	              </td>
	            </tr>
	            <tr>
	              <td align="right" valign="top"><span style="color:#FF0000">*</span>短信内容：</td>
	              <td><textarea id="smsText" name="smsText" style="width: 98%; height: 90px; font-size: 12px;"></textarea>
	                <div id="smsTips" class="tixing_tab">&nbsp;&nbsp;&nbsp;还可以输入350字，本次将以0条计费， 短信共0字，分0条发送</div>
	                
	              </td>
	            </tr>
				<tr>
					    <td  align="right">发送方式：</td>
					    <td>
					    	<select><option>生日当天发送</option><option>生日前1天发送 </option><option>生日前2天发送 </option></select>
					    </td>
					  </tr>
	            <tr>
	              <td align="right">发送时间：</td>
	              <td>
				 	<input type="text" name="sendTime" class="Wdate" id="sendTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});"/>
				  </td>
	            </tr>
	          </table>
	        </div>
	        <div class="bottom_box">
	          <div class="tubh" id="sendButton"><a href="javascript:void(0);">发送</a></div>
	          <div class="tubh" id="cancelButton"><a href="javascript:void(0);">取消</a></div>
	          <div class="tubh" id="saveButton"><a href="javascript:void(0);">存草稿</a></div>
	        </div>
	      </div>

      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="query" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer;position:relative;">
	        	<div id="inputFileContainer">
					<input type="file" id="inputfile" onchange="selectFile();" class="hide_input_file" name="addrUpload" style="width:200px;" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png" style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="selfsend"><img src="./themes/mas3/images/user.png" style="margin-right:5px;" />发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 0px; margin-top: -5px;">
          		<ul id="birthdaytreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;padding-left:0px;"></ul>
          	</div>
          <div class="box_bottom"><img src="./themes/mas3/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
    </tr>
  </table>
  </form>
</div>
</div>
</body>
</html>
