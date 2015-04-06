var 
    meetNoticereadysend_list_url = "./sms/meeting/jsp/readysend_list.jsp?type=3&pageNum=1",//待发箱加载路径
    meetNoticehadsend_list_url = "./sms/meeting/jsp/hadsend_list.jsp?type=4&pageNum=1",//已发箱加载路径
    meetNoticecurrent_url = meetNoticereadysend_list_url;
	
var
meetNoticereadysend_export_url = "./meetSmsReadySendAction/export.action?pageNum=1",//待发箱 导出 action
meetNoticehadsend_export_url = "./meetSmsHadSendAction/export.action?pageNum=1",//已发箱 导出 action
meetNoticereadysend_exportResult_url = "",//待发箱发送结果 导出action,定义见其相关页面
meetNoticereadysend_exportReply_url = "",//已发箱回复 导出 action,定义见其相关页面
meetNoticehadsend_exportResult_url = "",//已发箱发送结果 导出action,定义见其相关页面
meetNoticehadsend_exportReply_url = "",//已发箱回复 导出 使用了收件箱的action,定义见其相关页面
meetNoticeexport_sms_url = meetNoticereadysend_export_url;

 MeetNoticeInfoRightMenu = function(smsHdFrame,
		 	contactSearch,smsTitleSearch,namesearch,mobilesearch,replynameSearch,replymobileSearch){
 	this.smsHdFrame = smsHdFrame;
 	this.contactSearch = contactSearch;
 	this.smsTitleSearch = smsTitleSearch;
 	this.namesearch = namesearch;
 	this.mobilesearch = mobilesearch;
 	this.replynameSearch=replynameSearch;
 	this.replymobileSearch=replymobileSearch;
 	//菜单栏对应的标识。1,collectbox;2,inbox;3,readysendbox;
 	//4,hadsendbox;5,draftbox;6,readysend-result;7,hadsend-result;
 	//8,reply-readysend;9,reply-hadsend;
 	this.menuItemId = 0;
 	this.act="5";
 	//this.exportHref = true;
 };
 
 MeetNoticeInfoRightMenu.prototype={
 	init: function(){
 		var self = this;
 		//初始化查询条件的显示样式
 		self.contactSearch.init();
 		self.smsTitleSearch.init();
		//初始化 【待发箱】 选择操作
		$("#meetNoticesendbox_menu").unbind("click.rm").bind("click.rm",function(){
			//控制   当前点选的  li样式 唯一
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			//赋值 此 点选 的跳转页面的URL。
			meetNoticecurrent_url = meetNoticereadysend_list_url;
			//查询菜单变化：  对应不同操作的查询条件
			self.showSendBoxMenu();
		});
		//初始化 【已发箱】 选择操作
		$("#meetNoticehadsendbox_menu").unbind("click.rm").bind("click.rm",function(){
			//控制   当前点选的  li样式 唯一
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			//赋值 此 点选 的跳转页面的URL。
			meetNoticecurrent_url = meetNoticehadsend_list_url;
			//查询菜单变化：  对应不同操作的查询条件
			self.showHadSendBoxMenu();
		});
		//初始化，绑定【时间相关】查询操作
		$("#meetNoticesearchTimeInterval").unbind("click.sc").bind("click.sc",function(){
			//为相关标签设置自定义属性act, values:1-all,2-day,3-week,4-month,5-time interval
				dateFrom = "",
				dateTo = "";
				dateFrom = $("#meetNoticedateFrom").val(),
				dateTo = $("#meetNoticedateTo").val();
				if(dateISO(dateFrom)&&dateISO(dateTo)){
					$("#meetNoticesearchAll").parent().parent().children().removeClass("zhan");
				}else{
			        alert("请输入正确的日期格式，例如：2012-12-12");
					return;
				}
			//拼接查询条件
			var url = "&contacts="+ encodeURI(encodeURI(self.contactSearch.getValue()))+"&smsTitle="
					+encodeURI(encodeURI(self.smsTitleSearch.getValue()))
					+"&dateFrom=" + dateFrom+"&dateTo=" +dateTo
					+"&act=" + encodeURI(self.act);
			$("#meetNoticesms_hd_frame").load(meetNoticecurrent_url + url);
		});
		//初始化 待发箱 回复结果查询 按钮操作，区别于有时间选择框的操作。
		$("#meetNoticereadySendBtn").unbind("click.rds").bind("click.rds",function(){
			//拼接查询条件
			var url = "&sendResult=" 
				+ $("#meetNoticereadySendResult").val()
				+"&receiverName="+ encodeURI(encodeURI(self.namesearch.getValue()))
				+"&receiveMoble="+ encodeURI(encodeURI(self.mobilesearch.getValue()))
				+"&failReason="+ encodeURI(encodeURI($("#meetNoticeReadySendResultDetail").val()));
			$("#meetNoticesms_hd_frame").load(meetNoticecurrent_url + url);
		});
		//初始化 已发箱 回复结果查询 按钮操作，区别于有时间选择框的操作。
		$("#meetNoticehadSendBtn").unbind("click.hds").bind("click.hds",function(){
			//拼接查询条件
			var url = "&sendResult=" 
				+ $("#meetNoticehadSendResult").val()
				+"&receiverName="+ encodeURI(encodeURI(self.namesearch.getValue()))
				+"&receiveMoble="+ encodeURI(encodeURI(self.mobilesearch.getValue()))
				+"&failReason="+ encodeURI(encodeURI($("#meetNoticeHadSendResultDetail").val()));
				$("#meetNoticesms_hd_frame").load(meetNoticecurrent_url + url);
		});
		//回复页
		$("#meetNoticesmsReplyBtn").unbind("click.hds").bind("click.hds",function(){
			self.replynameSearch.init();
	 		self.replymobileSearch.init();
			dateFrom = "",
			dateTo = "";
			dateFrom = $("#meetNoticedateFrom").val(),
			dateTo = $("#meetNoticedateTo").val();
			if(dateISO(dateFrom)&&dateISO(dateTo)){
				$("#meetNoticesearchAll").parent().parent().children().removeClass("zhan");
			}else{
		        alert("请输入正确的日期格式，例如：2012-12-12");
				return;
			}
			//拼接查询条件
			var url = "&replyName="
				+ encodeURI(encodeURI(self.replynameSearch.getValue()))
					+"&replyMobile="
				+encodeURI(encodeURI(self.replymobileSearch.getValue()))
			+"&dateFrom=" + dateFrom+"&dateTo=" +dateTo
			+"&act=" + encodeURI(self.act);
	$("#meetNoticesms_hd_frame").load(meetNoticecurrent_url + url);
		});
		//初始化 导出相关操作
		$("#meetNoticeexportResultBtn").unbind("click.ep").bind("click.ep",function(){
				url = "",
				dateFrom = "",
				dateTo = "";
				$("#meetNoticesmsSendTime").find("li").each(function(){
					if($(this).hasClass("zhan")){
						act = $(this).children().first().attr("act");
					}
				});
					dateFrom = $("#meetNoticedateFrom").val(),
					dateTo = $("#meetNoticedateTo").val();
					if(dateISO(dateFrom)&&dateISO(dateTo)){
						;
					}else{
				        alert("请输入正确的日期格式，例如：2012-12-12");
						return;
					}
				url = "&searchBycontacts="+ encodeURI(encodeURI(self.contactSearch.getValue()))+"&searchBySmsTitle="
					+encodeURI(encodeURI(self.smsTitleSearch.getValue()))
					+"&dateFrom=" + dateFrom+"&dateTo=" +dateTo
					+"&searchAct=" + act;
				if(self.getMenuItemId() === 6 ){
					url += "&sendResult=" + $("#meetNoticereadySendResult").val();
				}
				if(self.getMenuItemId() === 7 ){
					url += "&sendResult=" + $("#meetNoticehadSendResult").val();
				}
				$.ajax({
	                url : meetNoticeexport_sms_url + url,
	                type : 'post',
	                dataType: "json",
	                success : function(data) {
	                    if(data.resultcode == "success"){
	                    	//alert("文件："+data.fileName+"导出成果！！");
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
		});
		//初始化  【待发箱】  为当前显示 页面
		$("#meetNoticesendbox_menu").trigger("click.rm");
 	},
 	getMenuItemId: function(){
 		//返回当前菜单的ID标识
 		return this.menuItemId;
 	},
 	formReset: function(){
 		document.getElementById("meetNoticesmsSearch").reset();
 	},
 	showSendBoxMenu: function(){
 		//标识待发箱菜单ID 为3，来区分相应的功能
 		this.menuItemId = 3;
 		//待发箱对应查询条件控制显示
 		this.formReset();
		$("#meetNoticecontactName,#meetNoticesmsTitle,#meetNoticesmsSendTime,#meetNoticetimeInterval,#meetNoticerootSubmit").removeClass( "ui-helper-hidden" );
		//$("#meetNoticesearchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticereceiverSearchDiv,#meetNoticereadySendDiv,#meetNoticehadSendDiv,#meetNoticereadySendSubmit,#meetNoticehadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.smsHdFrame).load(meetNoticecurrent_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		meetNoticeexport_sms_url = meetNoticereadysend_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showHadSendBoxMenu: function(){
 		//标识已发箱菜单ID 为4，来区分相应的功能
 		this.menuItemId = 4;
 		//已发箱对应查询条件控制显示
 		this.formReset();
		$("#meetNoticecontactName,#meetNoticesmsTitle,#meetNoticesmsSendTime,#meetNoticetimeInterval,#meetNoticerootSubmit").removeClass( "ui-helper-hidden" );
		//$("#meetNoticesearchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticereceiverSearchDiv,#meetNoticereadySendDiv,#meetNoticehadSendDiv,#meetNoticereadySendSubmit,#meetNoticehadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.smsHdFrame).load(meetNoticecurrent_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		meetNoticeexport_sms_url = meetNoticehadsend_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showHadSendDetailBoxMenu: function(){
 		//控制   当前点选的  li样式 唯一
		$("#meetNoticehadsendbox_menu").parent().children().removeClass("zhan").end().end().addClass("zhan");
 		//标识已发箱菜单ID 为4，来区分相应的功能
 		this.menuItemId = 4;
 		//已发箱对应查询条件控制显示
 		this.formReset();
		$("#meetNoticecontactName,#meetNoticesmsTitle,#meetNoticesmsSendTime,#meetNoticetimeInterval,#meetNoticerootSubmit").removeClass( "ui-helper-hidden" );
		//$("#meetNoticesearchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticereceiverSearchDiv,#meetNoticereadySendDiv,#meetNoticehadSendDiv,#meetNoticereadySendSubmit,#meetNoticehadSendSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		meetNoticeexport_sms_url = meetNoticehadsend_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showReadySendResultMenu: function(){
 		var self = this;
 		self.namesearch.init();
 		self.mobilesearch.init();
 		//标识待发箱发送结果菜单ID为6，来区分相应的功能
 		this.menuItemId = 6;
 		// 显示待发箱 发送结果 列表,查询条件
 		$("#meetNoticereceiverSearchDiv,#meetNoticereadySendDiv,#meetNoticereadySendSubmit").removeClass( "ui-helper-hidden" );
		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticecontactName,#meetNoticesmsTitle,#meetNoticesmsSendTime,#meetNoticetimeInterval,#meetNoticerootSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		
		//控制显示导出链接
		this.showExportHref();
 	},
 	showHadSendResultMenu: function(){
 		var self = this;
 		self.namesearch.init();
 		self.mobilesearch.init();
 		//标识已发箱发送结果菜单ID 为7，来区分相应的功能
 		this.menuItemId = 7;
 		//显示已发箱 发送结果  列表,查询条件
 		$("#meetNoticereceiverSearchDiv,#meetNoticehadSendDiv,#meetNoticehadSendSubmit").removeClass( "ui-helper-hidden" );
		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticecontactName,#meetNoticesmsTitle,#meetNoticesmsSendTime,#meetNoticetimeInterval,#meetNoticerootSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		//控制显示导出链接
		this.showExportHref();
 	},
 	showReplyReadySendMenu: function(){
 		//标识待发箱回复菜单ID为8，来区分相应的功能
 		this.menuItemId = 8;
 		//显示待发箱 回复列表,查询条件
 		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticesmsSendTime").removeClass( "ui-helper-hidden" );
		$("#meetNoticerootSubmit,#meetNoticecontactName,#meetNoticereceiverSearchDiv,#meetNoticesmsTitle,#meetNoticereadySendSubmit,#meetNoticereadySendDiv").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		//控制显示导出链接
		this.showExportHref();
 	},
 	showReplyHadSendMenu: function(){
 		//标识已发箱回复菜单ID为9，来区分相应的功能
 		this.menuItemId = 9;
 		//显示已发箱  回复列表,查询条件
 		$("#meetNoticesmsReplySubmit,#meetNoticesmsreply,#meetNoticesmsSendTime").removeClass( "ui-helper-hidden" );
		$("#meetNoticerootSubmit,#meetNoticecontactName,#meetNoticereceiverSearchDiv,#meetNoticesmsTitle,#meetNoticehadSendSubmit,#meetNoticehadSendDiv").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		//控制显示导出链接
		this.showExportHref();
 	},
 	showExportHref: function(){
 		//显示已发箱  回复列表,查询条件
 		$("#meetNoticeexportSearchSubmit").removeClass( "ui-helper-hidden" );
 	},
 	hidExportHref: function(){
 		//显示已发箱  回复列表,查询条件
 		$("#exportSearchSubmit").addClass( "ui-helper-hidden" );
 	}
 };
 /**
  *	
  * 导出的下载excel文件
  *
  */
 function download(url){
     window.open(url);
 }