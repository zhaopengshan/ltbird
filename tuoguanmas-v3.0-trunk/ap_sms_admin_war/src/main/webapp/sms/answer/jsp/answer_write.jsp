<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"/> 
        <meta http-equiv="expires" content="0"/>
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
        <script type="text/javascript">
	     var zNodes =[];
	     var log, className = "dark";
	     var answerTextChanged = false;
         var optionInfo = "";
		 var curAnswerPage = 1;
		 function addGroupCountsAnswer(treeId, treeNode) {
			if(treeNode.isParent  ==true){
				if(!isNaN(treeNode.id)){
					var aObj = jQuery("#" + treeNode.tId + "_a");
					var editStr1 = "<a  onclick=\"addToReceiverAnswer('"+treeNode.name+"');return false;\"></a>" ;//("+treeNode.counts+")
					aObj.after(editStr1);
				}
			}
			if (treeNode.level>0) return;
			var aObj = $("#" + treeNode.tId + "_a");
			if ($("#addBtn_"+treeNode.id).length>0) return;
			var addStr = "<span class='button lastPage' id='lastBtnAnswer_" + treeNode.id
				+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnAnswer_" + treeNode.id
				+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnAnswer_" + treeNode.id
				+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnAnswer_" + treeNode.id
				+ "' title='first page' onfocus='this.blur();'></span>";
			aObj.after(addStr);
			var first = $("#firstBtnAnswer_"+treeNode.id);
			var prev = $("#prevBtnAnswer_"+treeNode.id);
			var next = $("#nextBtnAnswer_"+treeNode.id);
			var last = $("#lastBtnAnswer_"+treeNode.id);
			treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
			first.bind("click", function(){
				if (!treeNode.isAjaxing) {
					goAnswerPage(treeNode, 1);
				}
			});
			last.bind("click", function(){
				if (!treeNode.isAjaxing) {
					goAnswerPage(treeNode, treeNode.maxPage);
				}
			});
			prev.bind("click", function(){
				if (!treeNode.isAjaxing) {
					goAnswerPage(treeNode, treeNode.page-1);
				}
			});
			next.bind("click", function(){
				if (!treeNode.isAjaxing) {
					goAnswerPage(treeNode, treeNode.page+1);
				}
			});
		 }
		 
		 function getAnswerUrl(treeId, treeNode) {
			if(treeNode == null){
				return "${ctx}/addr/getAddr.action";
			}else{
				var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
				aObj = $("#" + treeNode.tId + "_a");
				aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
				return "${ctx}/addr/getAddr.action?" + param;
			}
			
		}
		function goAnswerPage(treeNode, page) {
			treeNode.page = page;
			if (treeNode.page<1) treeNode.page = 1;
			if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
			if (curAnswerPage == treeNode.page) return;
			curAnswerPage = treeNode.page;
			var zTree = $.fn.zTree.getZTreeObj("answerTreeDemo");
			zTree.reAsyncChildNodes(treeNode, "refresh");
		}
		 
		 function onChangeSelect(obj){
			 if($("#dati_table select[id^=datiOption_]").length != 0 && $("#"+$("#dati_table select[id^=datiOption_]")[0].id).val() !="0"){
				 var optionName = $("#"+$("#dati_table select[id^=datiOption_]")[0].id).attr("id");
				 var textLength = $("#dati_table select[id='"+optionName+"']").find("option:selected").text().length;
				 var content  = $("#contentAnswer").val().length;
				 var sms_signLength = $("#entSign").val().length;
				 var replyContentLength = $("#datireplyText").val().length;
				 var remain = 335-content-textLength-sms_signLength-replyContentLength;
				 var len = content+textLength+sms_signLength+replyContentLength;
				 var cnt = Math.ceil(len/70);
				 //alert(355-content-textLength);
				 if( remain<0 ){
			        	$("#datiTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
			       	}else{
			            $("#datiTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
			            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
			     }
			 }
			 
		 }

		 function addQuestionTr(){
		   //alert($("#dati_table > tbody > tr").length)
	       //var info = "<tr><td><select name=\"\"><option>请选择题目</option></select> <img onClick='javascript:delQuestionTr(this)' src=\"themes/mas3admin/images/answer/u109_normal.gif\" /></td></tr>";
	       //$("#dati_table").append(info);
	       var info = "datiOption";
	       //addQuestionTrInfo(info,optionInfo);
	       addQuestionTr_(info,optionInfo);
	     }

		 function addQuestionTrInfo(nameInfo,str){
               var count_info = $("#dati_table > tbody > tr").length+1;
               //alert(count_info);
			   var name_info = nameInfo+"_"+count_info; 
			   //alert(name_info);
		       var info = "<tr><td><select  id="+name_info+" onChange='onChangeSelect(this)'><option value=0>请选择题目</option>"+str+"</select></td><td> <img onClick='javascript:delQuestionTr(this)' src=\"themes/mas3admin/images/answer/u109_normal.gif\" /></td></tr>";
		       $("#dati_table").append(info);
		 }

		 function addQuestionTrStrInfo(nameInfo,str){
		       var info = "<tr><td><select   id="+nameInfo+" onChange='onChangeSelect(this)'><option value=0>请选择题目</option>"+str+"</select> </td><td>&nbsp;&nbsp;</td></tr>";
		       $("#dati_table").append(info);
		 }

		 function addQuestionTr_(nameInfo,str){
				var i=$("#dati_table > tbody >tr").size()+1;
				var name_info = nameInfo+"_"+i; 
				var strInfo = "";
				if(i == 1){
                     
					strInfo="<tr>"+
			        "<td class=\"td22\" align=\"center\" style='width:15px'>"+i+"</td>"+
			        "<td class=\"td7\" align=\"center\" style='width:215px'><select  id="+name_info+" onChange='onChangeSelect(this)'><option value=0>请选择题目</option>"+str+"</select></td>"+
			        
			        "<td class=\"td7\" align=\"center\">&nbsp;&nbsp;</td>"+
			        "</tr>";
                    
			    }else{
			    	strInfo="<tr>"+
			        "<td class=\"td22\" align=\"center\"  style='width:15px'>"+i+"</td>"+
			        "<td class=\"td7\" align=\"center\" style='width:215px'><select  id="+name_info+" onChange='onChangeSelect(this)'><option value=0>请选择题目</option>"+str+"</select></td>"+
			        
			        "<td class=\"td7\" align=\"center\"><a href=\"javascript:void(0); \" onclick=\"delQuestionTr(this);\">清空</a></td>"+
			        "</tr>";
				}
				
			      //alert(str);
			    if($("#dati_table > tbody >tr").size()==1){
			    	$("#dati_table > tbody >tr > td").last().html("<a href=\"javascript:void(0); \" onclick=\"delQuestionTr(this);\">清空</a>");   
				}
				$("#dati_table").append(strInfo);
		}

	     function delQuestionTr(obj){
	    	 //$(id).parent().parent().remove();
	    	 var parentTR=$(obj).closest("tr");
	 		var index=$("#dati_table tbody tr").index(parentTR);
	 		parentTR.nextAll().each(function(i){
	 			$(this).find("td").first().html(index+i+1);
	 			i++;
	 		});
	 		if($("#dati_table > tbody >tr").size()>1){
	 			$(obj).closest("tr").remove();
		 	}

		 	if($("#dati_table > tbody >tr").size()==1){
		 		$("#dati_table > tbody >tr > td").last().html("&nbsp;&nbsp;");
			}
		 	
		 	if($("#dati_table select[id^=datiOption_]").length != 0 && $("#"+$("#dati_table select[id^=datiOption_]")[0].id).val() !="0"){
				 var optionName = $("#"+$("#dati_table select[id^=datiOption_]")[0].id).attr("id");
				 var textLength = $("#dati_table select[id='"+optionName+"']").find("option:selected").text().length;
				 var content  = $("#contentAnswer").val().length;
				 var sms_signLength = "${sessionScope.ent_sms_sign}".length;
				 var replyContentLength = $("#datireplyText").length;
				 var remain = 335-content-textLength-sms_signLength-replyContentLength;
				 //var remain = 335-content-textLength;
				 var len = content+textLength;
				 var cnt = Math.ceil(len/70);
				 //alert(355-content-textLength);
				 if( remain<0 ){
			        	$("#datiTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
			       	}else{
			            $("#datiTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
			            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
			     }
			 }
	 		
		 }

		 function filterAnswer(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				if( childNodes[i] && childNodes[i].name){
					childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				}
			}
			return childNodes;
		 }

		function beforeClickAnswer(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");
			return (treeNode.click != false);
		}

		function onClickAnswer(event, treeId, treeNode, clickFlag) {
			/**if( treeNode.isParent){
				return;
			}*/
			addToReceiverAnswer(treeNode.name);
		}		

		function addToReceiverAnswer(recv){
			if( answerTextChanged){
				$('#tosAnswer').attr({ value: $('#tosAnswer').attr("value")+","+recv });
			}
			else{
				$('#tosAnswer').attr({ value: recv });
			}
			answerTextChanged = true;
		}


		function checkUserAddrAnswer(userAddr){
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


		function receiverEventProcAnswer(event){
	        if (event.type == "focusin" && !answerTextChanged) {
	            $(this).attr({ value: "" });
	        } else if (event.type == "focusout" && $(this).attr("value") == "") {
	            answerTextChanged = false;
	            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
	        } else {
	            switch(event.which) {
	                case 27:
	                    answerTextChanged = false;
	                    $(this).attr("value", "");
	                    $(this).blur();
	                    break;
	                default:
	                    answerTextChanged = true;
	            }
	        }
		}

		function titleEventProcAnswer(event){
	        if (event.type == "focusin" && !titleChanged) {
	            $(this).attr({ value: "" });
	        } else if (event.type == "focusout" && $(this).attr("value") == "") {
	            titleChanged = false;
	            $(this).attr({ value: "输入短信答题任务名称，仅作为查询依据" });
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

		/**function smsTextEventProcAnswer(){
			var extLen = $("#answerReplyText").val().length;
			var extLen2 = $("#answerEntSign").val().length;
			var len =$(this).val().length + extLen + extLen2;
	        var remain = 335 - len;
	        var cnt = Math.ceil(len/70);  
	        if( remain<0 ){
	        	$("#answerSmsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
	       	}else{
	            $("#answerSmsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
	            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
	        }
		}*/
		
		function smsTextEventProcAnswer(){
			//alert('1111');
			var contentLen = $("#contentAnswer").val().length;
			var textLength = 0;
			if($("#dati_table select[id^=datiOption_]").length != 0 && $("#"+$("#dati_table select[id^=datiOption_]")[0].id).val() !="0"){
				 var optionName = $("#"+$("#dati_table select[id^=datiOption_]")[0].id).attr("id");
				 textLength = $("#dati_table select[id='"+optionName+"']").find("option:selected").text().length;
				 
			 }
			
			var content  = $("#contentAnswer").val().length;
			var sms_signLength = $("#entSign").val().length;
			var replyContentLength = $("#datireplyText").val().length;
			var remain = 335-content-textLength-sms_signLength-replyContentLength;
			
			var len = contentLen+textLength+sms_signLength+replyContentLength;
	       // var remain = 335-len;
	        var cnt = Math.ceil(len/70);  
	        if( remain<0 ){
	        	$("#datiTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
	       	}else{
	            $("#datiTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
	            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
	        }
		}
	
		function selectFileAnswer(){
			$("#tosAnswer").after("</br><span style='color: blue;'>已选择号码文件</span>");
		}

		function checkInputAnswer(){
            var datiId = "";
            var answerReceiver = $("#tosAnswer");
            var answerContent = $("#contentAnswer");
            if( $('#answerTitle').val() == "" || $("#answerTitle").val() == "输入短信答题任务名称，仅作为查询依据"){
        		alert('任务名称为空');
        		return false;
        	}

        	if($.trim($('#answerTitle').val()).length>250){
               alert("任务名称长度不能超过250字");
               return false;
            }
            if(answerReceiver.val()=="" || $.trim(answerReceiver.val())=="" || answerReceiver.val()=="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送"){
    			alert("参与人为空");
    			return false;
    		}
            
            
    		if(answerContent.val()=="" || $.trim(answerContent.val())==""){
    			alert("邀请短信内容为空");
    			return false;
    		}
    		
    		if(answerContent.val() !=""){
            	var contentLen = $("#contentAnswer").val().length;
    			var textLength = 0;
    			if($("#dati_table select[id^=datiOption_]").length != 0 && $("#"+$("#dati_table select[id^=datiOption_]")[0].id).val() !="0"){
    				 var optionName = $("#"+$("#dati_table select[id^=datiOption_]")[0].id).attr("id");
    				 textLength = $("#dati_table select[id='"+optionName+"']").find("option:selected").text().length;
    				 
    			 }
    			var len = contentLen+textLength;
    	        var remain = 335-len;
    	        if(remain<0){
    	        	alert("短信内容超过335字");
    	        	return false;
    	        }
            }
    		if($("#datiStartTime").val() == ""){
                alert("开始时间为空");
                return false;
        	}

    		if($("#datiEndTime").val() == ""){
                alert("结束时间为空");
                return false;
        	}

    		if(!contrastDate($("#datiStartTime").val(),$("#datiEndTime").val())){
    			alert('结束时间应大于开始时间');
    			return false;
    		}
        	
        	if($("#dati_table select[id^=datiOption_]").length != 0){
        		for(var i=0;i<$("#dati_table select[id^=datiOption_]").length;i++){
					  //alert($("#dati_table select[id^=datiOption_]")[i].id)
					  if($("#"+$("#dati_table select[id^=datiOption_]")[i].id).val() == 0){
	                     alert("请选择题目");
	                     return false;
					  }else{
	                      if(datiId == ""){
	                    	  datiId = $("#"+$("#dati_table select[id^=datiOption_]")[i].id).val();
	                      }else{
						      datiId = datiId+","+$("#"+$("#dati_table select[id^=datiOption_]")[i].id).val(); 
	                      }
				      }
				}
        	}else{
        		alert("请选择题目");
        		return false;
        	}
			$("#datiIds").val(datiId);
			$("#waitTipAnswer").removeClass("ui-helper-hidden");
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
		
		function sendSmsAnswer(responseText, statusText, xhr, $form){
			if(responseText.resultcode == "success" ){
				alert(responseText.message);
				$("#waitTipAnswer").addClass("ui-helper-hidden");
				var originalUrl = "./sms/answer/jsp/answer_shijuan_list.jsp";
		        var tempUrl = "./sms/answer/jsp/answer_shijuan_list.jsp";
		        var localUrl = "./masDatiAction/writeDatiInfoPage.action";
		        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
		        tabpanel.kill(_killId, true);
		        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
		        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
			}else{
				alert(responseText.message);
				$("#waitTipAnswer").addClass("ui-helper-hidden");
			}
		}
	

		var settingAnswer = {
			view: {
				showIcon: false,
				addDiyDom: addGroupCountsAnswer,
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
				beforeClick: beforeClickAnswer,
				onClick: onClickAnswer
			},
			async: {
				enable: true,
				//url:"${ctx}/addr/getAddr.action",
				url: getAnswerUrl,
				autoParam:["id", "name=n", "level=lv"],
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filterAnswer
			}
		};


		var textChanged = false;
		var titleChanged = false;
		$(document).ready(function(){
			$.fn.zTree.init($("#answerTreeDemo"), settingAnswer, zNodes);
			$('#answerTitle').unbind("keyup focusin focusout").bind("keyup focusin focusout", titleEventProcAnswer);
			$('#tosAnswer').unbind("keyup focusin focusout").bind("keyup focusin focusout", receiverEventProcAnswer);
			$('#contentAnswer').unbind('keyup keypress change').bind('keyup keypress change', smsTextEventProcAnswer);
			$("#answerSendAtTime").click(function(){
				$("#answerSendTime").css({"display": "inline"});
			});

			$('#addQuestionId').unbind("click");
			$('#addQuestionId').bind("click",addQuestionTr);
			$("#answerSendNow").click(function(){
				$("#answerSendTime").css({"display": "none"});
			});
	
			$( "#answerQuery" ).autocomplete({
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
	            	addToReceiverAnswer(ui.item.value);
	            }
	        });

			$("#summitDTData").unbind("click").bind("click",function(){
	        	var options = { 
	      		        beforeSubmit:  checkInputAnswer,  // pre-submit callback 
	      		        success:       sendSmsAnswer,  // post-submit callback 
	      		        dataType:		'json'
	      		}; 
	        	$("#answerSmsForm").ajaxSubmit(options); 
	        });
			
			$("#DtCancelButton").unbind("click").bind("click",function(){
				textChanged=false;
				titleChanged=false;
				answerTextChanged=false;
	        	$("#answerSmsForm")[0].reset();
	        	
            <c:if test="${not empty maxCode}">
		        
                $("#datireplyText").val("参与此短信答题编辑\"${maxCode}回复内容\"");
                $("#datireplyText").css({"display": "block"});
                var maxCodeLength = "参与此短信答题编辑\"${maxCode}回复内容\"".length;
                var sms_signLength = "${sessionScope.ent_sms_sign}".length;
                $("#datireplyCode").val("${maxCode}");
                
                var len = maxCodeLength+sms_signLength;
                var remain = 335 - len;
                var cnt = Math.ceil(len/70);  
                if( remain<0 ){
                	$("#datiTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
               	}else{
                    $("#datiTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
                    		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
                }
                
		    </c:if>
	        });
	        
	    	<c:if test="${ not empty entityMap}">
			    alert('${entityMap["message"]}');
			</c:if>
	    	<c:if test="${ not empty smsText}">
	    		$('#answerSmsText').attr("value","${smsText}");
	    		$('#answerSmsText').trigger("keyup");
			</c:if>

			<c:if test="${ not empty tikuList}">
			   <c:forEach items="${tikuList}" var="tiku">
			     optionInfo = optionInfo+"<option value=\"${tiku.id}\">${tiku.question}</option>"
                 
			   </c:forEach>
			   //alert("optionInfo:"+optionInfo);
			   //$("#dati_table").append("<tr><td></td></tr>")
			   $("#dati_table").html("");
			   
			   //addQuestionTrStrInfo("datiOption_1",optionInfo);
			   addQuestionTr_("datiOption",optionInfo);
		    </c:if>

		    <c:if test="${not empty maxCode}">
		        
                $("#datireplyText").val("参与此短信答题编辑\"${maxCode}回复内容\"");
                $("#datireplyText").css({"display": "block"});
                var maxCodeLength = "参与此短信答题编辑\"${maxCode}回复内容\"".length;
                var sms_signLength = "${sessionScope.ent_sms_sign}".length;
                $("#datireplyCode").val("${maxCode}");
                
                var len = maxCodeLength+sms_signLength;
                var remain = 335 - len;
                var cnt = Math.ceil(len/70);  
                if( remain<0 ){
                	$("#datiTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
               	}else{
                    $("#datiTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
                    		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
                }
                
		    </c:if>
			
	    	<c:if test="${ not empty title}">
				$('#answerTitle').attr("value","${title}");
			</c:if>
			
			<c:if test="${not empty contentInfo}">
			   $('#contentAnswer').attr("value","${contentInfo}");
			   $("#tosAnswer").val("");
			   $("#answerTitle").val("");
			</c:if>
	    	<c:if test="${ not empty receiver}">
				addToReceiverAnswer("${receiver}");
			</c:if>
	    	<c:if test="${ not empty sessionScope.SESSION_USER_INFO.mobile}">
	    		$("#answerSelfsend").bind("click", function(){addToReceiverAnswer('${sessionScope.SESSION_USER_INFO.mobile}');});
			</c:if>

			//alert($("#dati_table > tbody > tr > td").html());
		});
	</script> 
   </head>
<body>
<div class="main_body">
 <div class="contents">
  <form id="answerSmsForm" action="${ctx }/masDatiShiJuanAction/addShiJuan.action" method="post" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top">
      <div class="left_contents" style="height: 410px;overflow-y: auto;overflow-x: hidden;">
	   <div class="table_box">
	     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	       <td width="14%" height="50" align="right"><span style="color:#FF0000">*</span>任务名称：</td>
	       <td colspan="2">
	         <input id="answerTitle" type="text" name="title" class="input2" value="输入短信答题任务名称，仅作为查询依据"/>
	       </td>
	      </tr>
	      <tr>
	       <td  align="right" valign="top"><span style="color:#FF0000">*</span>答题人范围：</td>
	       <td colspan="2">
	         <input name="tos" id="tosAnswer" type="text" class="input2" value="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" />
	       </td>
	      </tr>
	      
	      <tr>
	       <td  align="right"><span style="color:#FF0000">*</span>邀请短信内容：</td>
	       <td colspan="2">
	         <textarea id="contentAnswer" name="content" style="width: 98%; height: 87px; font-size: 12px; color: #CCC;"></textarea>
	         <div id="datiTips" class="tixing_tab">&nbsp;&nbsp;&nbsp;还可以输入335字，本次将以0条计费&nbsp;短信共0字(含企业签名)，分0条发送</div> 
	         <div id="datiReply">
	            <!-- <input type="checkbox" id="datineedReply" name="datineedReply" />如此短信需要接收人回复，短信正文需加上以下文字 -->
	            <input type="text" id="datireplyText" name="datireplyText" class="input2" readonly="readonly" style="display: none;"/>
	            <input type="hidden" id="datireplyCode" name="datireplyCode" value=""/>
	            <input type="hidden" id="datiIds" name="datiIds"/>
	         </div>
	       </td>
	      </tr>
	      
	      
	      <tr>
	        <td height="50" align="right">企业签名：</td>
	        <td colspan="2">
	          <input type="text" id="entSign" name="entSign" class="input_entSign" readonly="readonly" value="${sessionScope.ent_sms_sign}"/>
	        </td>
	      </tr>
	      <tr>
			<td style="height:18px; line-height: 18px;" align="right"></td>
			<td style="height:18px; line-height: 18px;" colspan="2">
			  <span style="color:#FF0000">*</span>如果用户收到的短信后缀签名与平台不符，请联系客户经理或者平台管理员修改短信后缀签名
			</td>
		  </tr>
	      <tr>
	        <td height="50" align="right"><span style="color:#FF0000">*</span>有效时间：</td>
	        <td colspan="2">
	          <input type="text" id="datiStartTime" name="datiStartTime"  class="Wdate"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});"  />
	                           到
	          <input id="datiEndTime" name="datiEndTime"  type="text"  class="Wdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});" />
	        </td>
	      </tr>
	      <tr>
	       <td rowspan="2"  align="right"><span style="color:#FF0000">*</span>题目设置：</td>
	       <td width="80%">
	         <table id="dati_table" style="width:300px;">
	           <!-- <tr>
	             
	             <td>
	               <select name=""><option>请选择题目</option></select> 
	                
	             </td>
	             <td>
	               <img onClick='javascript:delQuestionTr(this)' src="${ctx }/themes/mas3admin/images/answer/u109_normal.gif" />
	             </td>
	           </tr> -->
	           
	         </table>
	          
	          
	       </td>
	       <td width="62%">
	         <div class="tubh"><a id="addQuestionId" href="javascript:;">增加题目</a></div>
	       </td>
	     </tr>
	     
	     <tr style="display:none;">
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>通道选择：</td>
	              <td width="91%">
	             	 移动
					<select id="VoteydTunnel" name="ydTunnel" style="width:220px;">
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
					通道可用余量<span id="VoteydTip">${ydTunnelNumber}</span>条
					<br/>
					联通
					<select id="calendarNoticeltTunnel" name="ltTunnel" style="width:220px;">
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
					通道可用余量<span id="VoteltTip">${ltTunnelNumber}</span>条
					<br/>
					电信
					<select id="calendarNoticedxTunnel"  name="dxTunnel" style="width:220px;">
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
					通道可用余量<span id="VotedxTip">${dxTunnelNumber}</span>条
					<br/>
					注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送
				  </td>
	            </tr>
	            <tr class="ui-helper-hidden" id="waitTipAnswer">
		            <td rowspan="2" colspan="2" height="50" ><span id="loadingTip" class="needtip"><img src="themes/mas3admin/images/helper/loading.gif"/>正在提交网关，请耐心等待。请勿重复执行此操作！</span></td>
		        </tr>
	   </table>
    </div>
    <div class="bottom_box">
      <div id="summitDTData" class="tubh"><a href="javascript:;">提交</a></div>
      <div id="DtCancelButton" class="tubh"><a href="javascript:;">重置</a></div>
    </div>
   </div>
  </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
          <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
          <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="answerQuery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer;position:relative;">
	        	<div id="answerInputFileContainer">
					<input type="file" id="answerInputfile" onchange="selectFileAnswer();" class="hide_input_file" name="addrUpload" style="width:200px;" />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png" style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="answerSelfsend"><img src="./themes/mas3/images/user.png"  style="margin-right:5px;"/>发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 10px; margin-top: -5px;">
          		<ul id="answerTreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;"></ul>
          	</div>
          <div class="box_bottom"><img src="./themes/mas3/images/box_bottom.jpg" /></div>
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
