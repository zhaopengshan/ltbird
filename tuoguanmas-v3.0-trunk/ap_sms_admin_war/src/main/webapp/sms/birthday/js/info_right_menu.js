var 
    readysend_list_url = "./sms/birthday/jsp/readysend_list.jsp?type=3&pageNum=1",//待发箱加载路径
    hadsend_list_url = "./sms/birthday/jsp/handsend_list.jsp?type=4&pageNum=1",//已发箱加载路径
   
	current_url = hadsend_list_url;
	
var 
    
    readysend_export_url = "./mbnSmsReadySendAction/export.action?pageNum=1",//待发箱 导出 action
    hadsend_export_url = "./mbnSmsHadSendAction/export.action?pageNum=1",//已发箱 导出 action
 
    readysend_exportResult_url = "",//待发箱发送结果 导出action,定义见其相关页面
    readysend_exportReply_url = "",//已发箱回复 导出 action,定义见其相关页面
    hadsend_exportResult_url = "",//已发箱发送结果 导出action,定义见其相关页面
    hadsend_exportReply_url = "",//已发箱回复 导出 使用了收件箱的action,定义见其相关页面
	export_sms_url = readysend_export_url;

 BirthdayRightMenu = function(smsHdFrame,contactSearch,smsTitleSearch){
 	this.smsHdFrame = smsHdFrame;
 	this.contactSearch = contactSearch;
 	this.smsTitleSearch = smsTitleSearch;
 	//菜单栏对应的标识。1,collectbox;2,inbox;3,readysendbox;
 	//4,hadsendbox;5,draftbox;6,readysend-result;7,hadsend-result;
 	//8,reply-readysend;9,reply-hadsend;
 	this.menuItemId = 0;
 	//this.exportHref = true;
 };
 
 BirthdayRightMenu.prototype={
 	init: function(){
 		var self = this;
 		//初始化查询条件的显示样式
 		self.contactSearch.init();
 		self.smsTitleSearch.init();
//		//初始化 【待发箱】 选择操作
		$("#sendbox_menu").unbind("click.rm").bind("click.rm",function(){
			//控制   当前点选的  li样式 唯一
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			//赋值 此 点选 的跳转页面的URL。
			current_url = readysend_list_url;
			//查询菜单变化：  对应不同操作的查询条件
			self.showSendBoxMenu();
		});
		//初始化 【已发箱】 选择操作
		$("#hadsendbox_menu").unbind("click.rm").bind("click.rm",function(){
			//控制   当前点选的  li样式 唯一
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			//赋值 此 点选 的跳转页面的URL。
			current_url = hadsend_list_url;
			//查询菜单变化：  对应不同操作的查询条件
			self.showHadSendBoxMenu();
		});
		//初始化，绑定【时间相关】查询操作
		$("#searchAll,#searchDay,#searchWeek,#searchMonth,#searchTimeInterval").unbind("click.sc").bind("click.sc",function(){
			//为相关标签设置自定义属性act, values:1-all,2-day,3-week,4-month,5-time interval
			var act = $(this).attr("act"),
				dateFrom = "",
				dateTo = "";
			if( act != 5 ){
				$('#dateFrom').val('');
				$('#dateTo').val('');
				$(this).parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
			}else{
				dateFrom = $("#dateFrom").val(),
				dateTo = $("#dateTo").val();
				if(dateISO(dateFrom)&&dateISO(dateTo)){
					$("#searchAll").parent().parent().children().removeClass("zhan");
				}else{
			        alert("请输入正确的日期格式，例如：2012-12-12");
					return;
				}
			}
			//拼接查询条件
			var url = "&contacts="+ encodeURI(encodeURI(self.contactSearch.getValue()))+"&smsTitle="
					+encodeURI(encodeURI(self.smsTitleSearch.getValue()))
					+"&dateFrom=" + dateFrom+"&dateTo=" +dateTo
					+"&act=" + act;
			$("#sms_hd_frame").load(current_url + url);
		});
		//初始化 待发箱 回复结果查询 按钮操作，区别于有时间选择框的操作。
		$("#readySendBtn").unbind("click.rds").bind("click.rds",function(){
			//拼接查询条件
			var url = "&contacts="+ encodeURI(encodeURI(self.contactSearch.getValue())) +"&sendResult=" 
				+ $("#readySendResult").val();
			$("#sms_hd_frame").load(current_url + url);
		});
		//初始化 已发箱 回复结果查询 按钮操作，区别于有时间选择框的操作。
		$("#hadSendBtn").unbind("click.hds").bind("click.hds",function(){
			//拼接查询条件
			var url = "&contacts="+ encodeURI(encodeURI(self.contactSearch.getValue())) +"&sendResult=" 
				+ $("#hadSendResult").val();
				$("#sms_hd_frame").load(current_url + url);
		});
		//初始化 导出相关操作
		$("#exportResultBtn").unbind("click.ep").bind("click.ep",function(){
			var act = 5,
				url = "",
				dateFrom = "",
				dateTo = "";
				$("#smsSendTime").find("li").each(function(){
					if($(this).hasClass("zhan")){
						act = $(this).children().first().attr("act");
					}
				});
				if( act != 5 ){
					$('#dateFrom').val('');
					$('#dateTo').val('');
				}else{
					dateFrom = $("#dateFrom").val(),
					dateTo = $("#dateTo").val();
					if(dateISO(dateFrom)&&dateISO(dateTo)){
						;
					}else{
				        alert("请输入正确的日期格式，例如：2012-12-12");
						return;
					}
				}
				url = "&searchBycontacts="+ encodeURI(encodeURI(self.contactSearch.getValue()))+"&searchBySmsTitle="
					+encodeURI(encodeURI(self.smsTitleSearch.getValue()))
					+"&dateFrom=" + dateFrom+"&dateTo=" +dateTo
					+"&searchAct=" + act;
				if(self.getMenuItemId() === 6 ){
					url += "&sendResult=" + $("#readySendResult").val();
				}
				if(self.getMenuItemId() === 7 ){
					url += "&sendResult=" + $("#hadSendResult").val();
				}
				$.ajax({
	                url : export_sms_url + url,
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
		$("#sendbox_menu").trigger("click.rm");
 	},
 	getMenuItemId: function(){
 		//返回当前菜单的ID标识
 		return this.menuItemId;
 	},
 	formReset: function(){
 		document.getElementById("smsSearch").reset();
 	},
 	showCollectBoxMenu: function(){
 		//标识珍藏箱菜单ID 为1，来区分相应的功能
 		this.menuItemId = 1;
 		//珍藏箱对应查询条件控制显示
		this.formReset();
		//隐藏 联系人姓名，短信内容 输入框。css样式取自jquery  css
		$("#contactName,#smsTitle").addClass( "ui-helper-hidden" );
		//显示时间选择框及对应的提交按钮
		$("#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		//默认当前时间 过滤条件为  ALL
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		//隐藏与此功能 模块 无关的 项
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		//LOAD加载此功能对应 的页面
		$("#" + this.smsHdFrame).load(current_url);
		//控制 输入框内 提示显示
		this.contactSearch.refresh();
		//控制 输入框内 提示显示
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		export_sms_url = collect_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showInBoxMenu: function(){
 		//标识收件箱菜单ID 为2，来区分相应的功能
 		this.menuItemId = 2;
 		//收件箱对应查询条件控制显示
		this.formReset();
		$("#contactName,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle").addClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.smsHdFrame).load(current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		export_sms_url = inbox_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showSendBoxMenu: function(){
 		//标识待发箱菜单ID 为3，来区分相应的功能
 		this.menuItemId = 3;
 		//待发箱对应查询条件控制显示
 		this.formReset();
		$("#contactName,#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.smsHdFrame).load(current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		export_sms_url = readysend_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showHadSendBoxMenu: function(){
 		//标识已发箱菜单ID 为4，来区分相应的功能
 		this.menuItemId = 4;
 		//已发箱对应查询条件控制显示
 		this.formReset();
		$("#contactName,#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.smsHdFrame).load(current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		export_sms_url = hadsend_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
	showHadSendDetails: function(){
		$("#hadsendbox_menu").parent().children().removeClass("zhan").end().end().addClass("zhan");
 		//标识已发箱菜单ID 为4，来区分相应的功能
 		this.menuItemId = 4;
 		//已发箱对应查询条件控制显示
 		this.formReset();
		$("#contactName,#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		export_sms_url = hadsend_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showDraftBoxMenu: function(){
 		//标识草稿箱菜单ID为5，来区分相应的功能
 		this.menuItemId = 5;
 		//草稿箱对应查询条件控制显示
		this.formReset();
		$("#contactName").addClass( "ui-helper-hidden" );
		$("#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.smsHdFrame).load(current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
		//设定导出动作操作的URL 路径
		export_sms_url = draft_export_url;
		//控制显示导出链接
		this.showExportHref();
 	},
 	showReadySendResultMenu: function(){
 		//标识待发箱发送结果菜单ID为6，来区分相应的功能
 		this.menuItemId = 6;
 		// 显示待发箱 发送结果 列表,查询条件
 		$("#contactName,#readySendDiv,#readySendSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		
		//控制显示导出链接
		this.showExportHref();
 	},
 	showHadSendResultMenu: function(){
 		//标识已发箱发送结果菜单ID 为7，来区分相应的功能
 		this.menuItemId = 7;
 		//显示已发箱 发送结果  列表,查询条件
 		$("#contactName,#hadSendDiv,#hadSendSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		//控制显示导出链接
		this.showExportHref();
 	},
 	showReplyReadySendMenu: function(){
 		//标识待发箱回复菜单ID为8，来区分相应的功能
 		this.menuItemId = 8;
 		//显示待发箱 回复列表,查询条件
 		$("#contactName,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#readySendSubmit,#readySendDiv").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		//控制显示导出链接
		this.showExportHref();
 	},
 	showReplyHadSendMenu: function(){
 		//标识已发箱回复菜单ID为9，来区分相应的功能
 		this.menuItemId = 9;
 		//显示已发箱  回复列表,查询条件
 		$("#contactName,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#hadSendSubmit,#hadSendDiv").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
		//设定导出动作操作的URL 路径,//放到独自的页面里了
		//控制显示导出链接
		this.showExportHref();
 	},
 	showExportHref: function(){
 		//显示已发箱  回复列表,查询条件
 		$("#exportSearchSubmit").removeClass( "ui-helper-hidden" );
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
// function download(url){
//    var w = window.open(url);
//    w.location.href = url;
// }