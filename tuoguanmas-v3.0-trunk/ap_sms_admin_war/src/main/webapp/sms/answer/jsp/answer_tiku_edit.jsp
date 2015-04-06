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


	function addGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"addToReceiver('"+treeNode.name+"');return false;\">("+treeNode.counts+")</a>" ;
				aObj.after(editStr1);
			}
		}
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
		if( $('#title').attr("value") == '标题不会作为短信内容发出，仅作为查询依据' || $('#title').attr("value") == ""){
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
		return true;
	}

	function receiverEventProc(event){
        if (event.type == "focusin" && !textChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            textChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
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
            $(this).attr({ value: "标题不会作为短信内容发出，仅作为查询依据" });
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
            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
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
			var originalUrl = "./sms/smssend/jsp/sms_info.jsp";
	        var tempUrl = "./sms/smssend/jsp/sms_info.jsp";
	        var localUrl = "./smssend/writesms.action";
	        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
	        tabpanel.kill(_killId);
	        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		}else{
			alert(responseText.message);
		}
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
			url:"${ctx}/addr/getAddr.action",
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};

	var zNodes =[];
	var log, className = "dark";
	
	var textChanged = false;
	var titleChanged = false;

	/**$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
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
                    url: "${ctx}/smssend/queryTaskNumber.action",
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
        
    	
	});*/
	</script>
<body>
<div class="main_body">
<div class="contents">
  <form id="smsForm" action="${ctx }/smssend/send.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents">
	        <div class="table_box">
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            
	            
	            <tr>
	              <td align="right" valign="top"><span style="color:#FF0000">*</span>题目内容：</td>
	              <td>
	                <textarea id="smsText" name="smsText" style="width: 98%; height: 90px; font-size: 12px;"></textarea>
	              </td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right">分数：</td>
	              <td width="91%">
	               <input type="text" id="score" name="score" class="input2"  value=""/>
	              </td>
	            </tr>
	            
	            <!--<tr style="display: none">-->
	           
	          </table>
	        </div>
	        <div class="bottom_box">
	          <div class="tubh" id="sendButton"><a href="javascript:void(0);">保存</a></div>
	          <div class="tubh" id="cancelButton"><a href="javascript:void(0);">取消</a></div>
	          
	        </div>
	      </div>

      </td>
      
    </tr>
  </table>
  </form>
</div>
</div>
</body>
</html>
