var listCalendarType="";
var calendarListBaseUrl="calendar/calendarList.action?";
var calendarResultListBaseUrl="calendar/calendarResultList.action?";
var calendarRequestUrl="";
var searchText="";
var resultListType=0;//发送记录展示方式
var searchListResult=0;//0发送结果展示，1发送记录展示
function calendarOnload(){
	initCalendarSearch();
	listCalendarType="send";
	switchCalendarType("send");
}
function showCalendarList(requestUrl){
	//获取查询条件
	calendarRequestUrl=calendarRequestUrl+searchText;
	if(requestUrl){
		calendarRequestUrl=requestUrl;
	}
	var gridCalendarPro = {
			url:calendarRequestUrl,//执行地址
			colNames:['状态', '任务名称','提醒类别','提醒内容','最新提醒时间','发送记录','发送结果'],
			colModel:[//列所有名称的属性
			          {name:'sendResult',width:50,align:"center",formatter:function(data){
			        	  var iconSms = '<img title="未发送" src="${ctx}/themes/mas3/images/u16220_normal.png" width="15" height="13"/>';
							switch(listCalendarType){
								case "send": iconSms = '<img title="已发送" src="./themes/mas3admin/images/vote/lise_lcon1.gif" width="15" height="13"/>'; break;
								case "notSend": iconSms = '<img title="待发送" src="./themes/mas3admin/images/vote/lise_lcon3.gif" width="13" height="16"/>'; break;
							}
							return iconSms;
						}},
						{name:'title',width:140,formatter:function(data){
							var sName = data.title;
							if( sName != null && $.trim(sName) != "" ){
								if(sName.length > 8){
									return sName.substring(0,8)+"...";
								}
							}
							return sName;
						}},
						{name:'remindType',width:100,formatter:function(data){
							var remindWay=data.remindway;
							var rewindWayNm='立即';
							switch(remindWay){
								case 1: rewindWayNm = '定时'; break;
								case 2: rewindWayNm = '周期'; break;
							}
							return rewindWayNm;
						}},
						{name:'content',width:100,formatter:function(data){
							var content=data.content;
							if( content != null && $.trim(content) != "" ){
								if(content.length > 8){
									return content.substring(0,8)+"...";
								}
							}
							return content;
						}},
						{name:'remindTime',width:100,formatter:function(data){
							return data.remindtime;
						}},
						{name:'calendarResult',width:100,align:"center",formatter:function(data){
							return "<a href='javascript:void(0)' onclick=initCalendarSendListResult('"+data.id+"')>查看</a>";
						}},
						{name:'calendarReply',width:100,align:"center",formatter:function(data){
							
							return "<a href='javascript:void(0)' onclick=initCalendarListResult('"+data.id+"')>查看</a>";
						}}
			],
			buttons: [{
				text: "编辑转发",
				name:'editCalendar',
				classes: "",
				click: function(){
					var url = 'calendar/edit.action';
					editCalendarSmsFunc(smsCalendarGrid,url);
					//var showMessage = "是否删除选中的{0}条日程提醒？";
	                //confirmAjaxFunc(smsCalendarGrid,url,showMessage);
				}
			},{
				text: "删除",
				name:'delete',
				classes: "",
				click: function(){
					var url = 'calendar/calendarDelete.action';
					var showMessage = "是否删除选中的{0}条日程提醒？";
	                confirmAjaxFunc(smsCalendarGrid,url,showMessage);
				}
			}],
			multiselect: true
		};
		var smsCalendarGrid;
		$(function() {
			smsCalendarGrid = new TableGrid("calendarGrid",gridCalendarPro);
			smsCalendarGrid.redrawGrid(gridCalendarPro);
			backCalendarList();
		});
}
//日程提醒发送结果
var smsCalendarResultGrid;
function showCalendarResultList(requestUrl){
	searchListResult=0;
	var gridCalendarResultPro = {
			url:requestUrl,
			colNames:['手机号码','接收人姓名','发送结果','失败原因'],
			colModel:[
			{name:'tos',align:"center",width:100},
			{name:'tosName',align:"center",width:100,formatter:function(data){
					var reValue= data.tosName;
					if( data.tosName == null && $.trim(data.tosName) == "" ){
						reValue = "(未知)";
					}
					return reValue;
				}},
			{name:'sendResult',align:"center",width:100,formatter:function(data){
					var reValue= "无";
					switch(data.sendResult){
						case -1: reValue= "已取消";break;
						case  0: reValue= "等待发送";break;
						case  1: reValue= "已提交网关";break;
						case  2: reValue= "成功";break;
						case  3: reValue= "失败";break;
					}
					return reValue;
				}},
			{name:'failReason',align:"center",width:100,formatter:function(data){
					var reValue= data.failReason;
					if( data.failReason == null && $.trim(data.failReason) == "" ){
						reValue = "(无)";
					}
					return reValue;
				}}
			],
			buttons: [{
				text: "返回",
				name:'returnListCalendar',
				classes: "",
				click: function(){
					backCalendarList();
				}
			},{
				text: "删除",
				name:'delete',
				classes: "",
				click: function(){
					var url = 'calendar/calendarResultDelete.action';
					var showMessage = "是否删除选中的{0}条日程发送结果？";
	                confirmAjaxFunc(smsCalendarResultGrid,url,showMessage);
				}
			}
			/**,{
				text: "重发失败项",
				name:'editCalendar',
				classes: "",
				click: function(){
					var url = 'calendar/editByResult.action';
					editCalendarSmsFunc(smsCalendarResultGrid,url);
				}
			}**/
			
			],
			multiselect: true
		};
		$(function() {
			smsCalendarResultGrid = new TableGrid("calendarResultGrid",gridCalendarResultPro);
			smsCalendarResultGrid.redrawGrid(gridCalendarResultPro);
		});
}
//日程提醒发送记录列表
var smsCalendarListGrid;
function showCalendarSendList(requestUrl){
	searchListResult=1;
	var gridCalendarResultPro = {
			url:requestUrl,//执行地址
			colNames:['手机号码','接收人姓名','提醒内容','发送时间','回执'],
			colModel:[//列所有名称的属性
						{name:'mobile',width:140,formatter:function(data){
							return data.tos;
						}},
						{name:'receiverName',width:100,formatter:function(data){
							var reValue= data.tosName;
							if( data.tosName == null && $.trim(data.tosName) == "" ){
								reValue = "(未知)";
							}
							return reValue;
						}},
						{name:'content',width:100,formatter:function(data){
							var content=data.content;
							if( content != null && $.trim(content) != "" ){
								if(content.length > 8){
									return content.substring(0,8)+"...";
								}
							}
							return content;
						}},
						{name:'sendTime',width:100,formatter:function(data){
							return data.readySendTime;
						}},
						{name:'calendarReceipt',width:100,align:"center",formatter:function(data){
							return "<a href='javascript:void(0)' onclick=initCalendarSendListReplyResult('"+data.id+"')>查看</a>";
						}}
			],
			buttons: [{
				text: "返回",
				name:'returnListCalendar',
				classes: "",
				click: function(){
					backCalendarList();
				}
			},{
				text: "删除",
				name:'delete',
				classes: "",
				click: function(){
					var url = 'calendar/calendarResultDelete.action';
					var showMessage = "是否删除选中的{0}条日程发送结果？";
	                confirmAjaxFunc(smsCalendarListGrid,url,showMessage);
				}
			}
			/**,{
				text: "重发失败项",
				name:'editCalendar',
				classes: "",
				click: function(){
					var url = 'calendar/editByResult.action';
					editCalendarSmsFunc(smsCalendarResultGrid,url);
				}
			}**/
			
			],
			multiselect: true
		};
		$(function() {
			smsCalendarListGrid = new TableGrid("calendarResultGrid",gridCalendarResultPro);
			smsCalendarListGrid.redrawGrid(gridCalendarResultPro);
		});
}
//回执列表
function showCalendarSendReplyList(requestUrl){
	var gridCalendarReplyResultPro = {
			url:requestUrl,//执行地址
			colNames:['回复人','回复内容', '回复时间'],
			colModel:[
				{name:'senderName',align:"center",width:100,formatter:function(data){
						var sName = data.senderMobile;
						if( data.senderName != null && $.trim(data.senderName) != "" ){
							sName = sName + "&lt;"+ data.senderName + "&gt;";
						}
						return sName;
					}},
				{name:'content',width:300}, 
				{name:'receiveTime',align:"center",width:100,formatter:function(data){
						var reValue= data.receiveTime;
						if( data.receiveTime == null && $.trim(data.receiveTime) == "" ){
							reValue = "(无)";
						}
						return reValue;
					}}
			],
			buttons: [{
				text: "返回",
				classes: "",
				name: "return",
				click: function(){
					$('#calendarResultBody').css({"display": "block"});
					$('#calendarReplyBody').css({"display": "none"});
//					<%if(smsRe!=null){%>
//					meetNoticecurrent_url = meetNoticehadsend_list_url;
//					<%}else{%>
//					meetNoticecurrent_url = "${ctx }/meetSmsHadSendAction/getSmsDetails.action?selectedId=<%=smsHadSendId%>";
//					<%}%>
//					meetNoticerightMenuBar.showHadSendBoxMenu();
				}
			},{
				text: "删除",
				classes: "",
				name: "delete",
				click: function(){
//					var url=" ${ctx }/meetSmsInboxAction/deleteByIds.action";
//					var showMessage = "是否删除选中的{0}条短信？";
//	                confirmAjaxFunc(meetNoticesmsGrid,url,showMessage);
					
				}
			}],
			multiselect: true
		};
		var smsCalendarReplyResultGrid;
		$(function() {
			smsCalendarReplyResultGrid = new TableGrid("calendarReplyGrid",gridCalendarReplyResultPro);
			smsCalendarReplyResultGrid.redrawGrid(gridCalendarReplyResultPro);
		});
}
var selectCalendarResultId;//查看结果的batchId;
var selectCalendarReplyId;
//结果列表展示
function initCalendarListResult(selectItem){
	selectCalendarResultId=selectItem;
	var resultListUrl="calendar/calendarResultList.action?listType="+listCalendarType+"&batchId="+selectItem;
	showCalendarResultList(resultListUrl);
	$('#calendarBody').css({"display": "none"});
	$('#calendarResultBody').css({"display": "block"});
	$('#calendarReplyBody').css({"display": "none"});
	initCalendarResultSearch();
	resultListType=1;
}
//发送记录展示
function initCalendarSendListResult(selectItem){
	selectCalendarResultId=selectItem;
	var resultListUrl="calendar/calendarResultList.action?listType="+listCalendarType+"&batchId="+selectItem;
	showCalendarSendList(resultListUrl);
	$('#calendarBody').css({"display": "none"});
	$('#calendarResultBody').css({"display": "block"});
	$('#calendarReplyBody').css({"display": "none"});
	initCalendarResultSearch();
	resultListType=0;
}
//回执初始化
function initCalendarSendListReplyResult(selectItem){
	selectCalendarReplyId=selectItem;
	var resultListUrl="calendar/getReplyInfo.action?listType=sendResult&batchId="+selectItem;
	showCalendarSendReplyList(resultListUrl);
	$('#calendarBody').css({"display": "none"});
	$('#calendarResultBody').css({"display": "none"});
	$('#calendarReplyBody').css({"display": "block"});
	
}
function backCalendarList(){
	$('#calendarBody').css({"display": "block"});
	$('#calendarResultBody').css({"display": "none"});
}
function initCalendarSearch(){
	document.getElementById("calendarTitleSearch").value="";
	document.getElementById("calendarDateFrom").value="";
	document.getElementById("calendarDateTo").value="";
	$("#calendarTypeSearch").get(0).options[0].selected = true;
	$('#calendarReplyBody').css({"display": "none"});
}
/**
 * 查
 * @return
 */
function initCalendarResultSearch(){
	document.getElementById("calendarResultReceiverSearch").value="";
	document.getElementById("calendarResultMobileSearch").value="";
	$("#calendarResultSearch").get(0).options[0].selected = true;
	$("#calendarResultFailSearch").get(0).options[0].selected = true;
	loadSendSmsResult();
	
}
/**
 * 获取发送列表的统计
 * @return
 */
function loadSendSmsResult(){
	jQuery.ajax({
        url:'calendar/calendarResultCountList.action',
        data:{
			  'listType':listCalendarType,
			  "batchId":selectCalendarResultId
		 },
		 type:'post',
		 async:false,
		 dataType:'json',
		 success:function(data1){
		   var dataInfo = eval(data1);
		   document.getElementById('counttotails').innerHTML=dataInfo.result.totails;
		   document.getElementById('countSuccess').innerHTML=dataInfo.result.success;
		   document.getElementById('countFail').innerHTML=dataInfo.result.failure;
		   document.getElementById('countSending').innerHTML=dataInfo.result.sending;
		   document.getElementById('countCancel').innerHTML=dataInfo.result.cancel;
		   document.getElementById('countWaiting').innerHTML=dataInfo.result.waiting;
		 }
 });
	
}
function getCalendarSearchRequestText(){
	var searchTitle=document.getElementById("calendarTitleSearch").value;//标题
	var searchDateFrom=document.getElementById("calendarDateFrom").value;//开始时间
	var searchDateTo=document.getElementById("calendarDateTo").value;//结束时间
	var selectId= $("#calendarTypeSearch option:selected").get(0).id;//提醒类别
	selectId=selectId.replace('calendarTypeSearch','');
	searchText="listType="+listCalendarType+"&searchTitle="+encodeURI(encodeURI(searchTitle))+"&searchRemindWay="+selectId+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
	return searchText;
}
function getCalendarResultSearchRequestText(){
	var searchReceiver=document.getElementById("calendarResultReceiverSearch").value;//接收者
	var searchMobile=document.getElementById("calendarResultMobileSearch").value;//手机号
	//var searchResult=document.getElementById("calendarResultSearch").value;//结果
	var searchResult=$("#calendarResultSearch").find("option:selected").text();
	var sendResult=4;//未选择
	//-1取消发送,0未发送,1已提交网关,2成功,3失败
	if(searchResult=='取消发送')sendResult=-1;
	if(searchResult=='已提交网关')sendResult=1;
	if(searchResult=='成功')sendResult=2;
	if(searchResult=='失败')sendResult=3;
	if(searchResult=='等待发送')sendResult=0;
	//var searchFailResult=document.getElementById("calendarResultFailSearch").value;//失败原因
	var searchFailResult=$("#calendarResultFailSearch").find("option:selected").text();
	if(searchFailResult=='请选择……')searchFailResult='';
	var resultListUrl="listType="+listCalendarType+"&batchId="+selectCalendarResultId+"&searchBycontacts="+encodeURI(encodeURI(searchReceiver))+"&searchByMobile="+searchMobile+"&sendResult="+sendResult+"&searchByFailReason="+encodeURI(encodeURI(searchFailResult));
	return resultListUrl;
}
//提醒列表查询
function calendarSearch(){
	showCalendarList(calendarListBaseUrl+getCalendarSearchRequestText());
}
//结果列表查询
function calendarResultSearch(){
	showCalendarResultList(calendarResultListBaseUrl+getCalendarResultSearchRequestText());
}
function calendarResultValSearch(){
	//显示发送结果列表
	if(searchListResult==0)
		showCalendarResultList(calendarResultListBaseUrl+getCalendarResultSearchRequestText());
	else
		showCalendarSendList(calendarResultListBaseUrl+getCalendarResultSearchRequestText());
}
//回复查询
function  calendarReplySearch(){
	var calendarReplyNmSearch=document.getElementById("calendarReplyNmSearch").value;//回复人
	var calendarReplyContentSearch=document.getElementById("calendarReplyContentSearch").value;//回复内容
	var calendarReplyDateSearch=document.getElementById("calendarReplyDateSearch").value;//回复时间
	
	var resultListUrl="calendar/getReplyInfo.action?listType=sendResult&batchId="+selectCalendarReplyId+"&replyMobile="+calendarReplyNmSearch;
	showCalendarSendReplyList(resultListUrl);
}
function switchCalendarType(type){
	initCalendarSearch();
	listCalendarType=type;
	calendarRequestUrl=calendarListBaseUrl+"listType="+listCalendarType;//+"&pageNum=1";
	showCalendarList(calendarRequestUrl);
	switch(listCalendarType){
		case "send":
			document.getElementById("calendar_hadsend_menu").className="zhan";
			document.getElementById("calendar_readysend_menu").removeAttribute("class");
			break;
		case "notSend":
			document.getElementById("calendar_readysend_menu").className="zhan";
			document.getElementById("calendar_hadsend_menu").removeAttribute("class");
			break;
	}
}
/**
 * 导出日程提醒列表数据
 * @return
 */
function exportCalendarList(){
	var requestText=getCalendarSearchRequestText();
	var url="calendar/exportCalendarList.action?"+requestText;
	serviceUpload(url);
}
/**
 * 导出日程提醒结果列表
 * @return
 */
function exportCalendarResultList(){
	var requestText=getCalendarResultSearchRequestText();
	var url="calendar/exportCalendarResultList.action?"+requestText+"&resultListType="+resultListType;
	serviceUpload(url);
}
function serviceUpload(url){
	$.ajax({
        url : url,
        type : 'post',
        dataType: "json",
        success : function(data) {
            if(data.resultcode == "success"){
            	if(confirm(data.message+"是否下载导出内容")){
            		download("./fileDownload?fileName=./downloads/sms/" +data.fileName);
            	}
            }else{
            	alert(data.message);
            }
        },
        error : function() {
            alert("出现系统错误，请稍后再试");
        }
    }); 
	
}