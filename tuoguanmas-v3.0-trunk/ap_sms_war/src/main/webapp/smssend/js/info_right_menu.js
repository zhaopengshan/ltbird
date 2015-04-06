var collect_list_url = "/smssend/jsp/collect_list.jsp";
var inbox_list_url = "/smssend/jsp/inbox_list.jsp";
var readysend_list_url = "/smssend/jsp/readysend_list.jsp";
var hadsend_list_url = "/smssend/jsp/hadsend_list.jsp";
var draft_list_url = "/smssend/jsp/draft_list.jsp";

var current_url = collect_list_url;

 InfoRightMenu = function(gridFrame,contactSearch,smsTitleSearch){
 	this.frameSrcId = gridFrame;
 	this.contactSearch = contactSearch;
 	this.smsTitleSearch = smsTitleSearch;
 	this.selectItem = 0;
 };
 
 InfoRightMenu.prototype={
 	init: function(){
 		var self = this;
 		self.contactSearch.init();
 		self.smsTitleSearch.init();
 		$("#" + this.frameSrcId).attr("src",current_url);
 		$("#collect_menu").bind("click",function(){
 			//查询条件 form reset
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			current_url = collect_list_url;
			self.showCollectBoxMenu();
 			self.selectItem = 1;
		});
		$("#inbox_menu").bind("click",function(){
			//查询条件 form reset
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			current_url = inbox_list_url;
			self.showInBoxMenu();
 			self.selectItem = 2;
		});
		$("#sendbox_menu").bind("click",function(){
			//查询条件 form reset
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			current_url = readysend_list_url;
			self.showSendBoxMenu();
 			self.selectItem = 3;
		});
		$("#hadsendbox_menu").bind("click",function(){
			//查询条件 form reset
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			current_url = hadsend_list_url;
			self.showHadSendBoxMenu();
 			self.selectItem = 4;
		});
		$("#draft_menu").bind("click",function(){
			//查询条件 form reset
			$(this).parent().children().removeClass("zhan").end().end().addClass("zhan");
			current_url = draft_list_url;
			self.showDraftBoxMenu();
 			self.selectItem = 5;
		});
 	},
 	getItemId: function(){
 		return this.selectItem;
 	},
 	showCollectBoxMenu: function(){
		this.formReset();
		$("#contactName,#smsTitle").addClass( "ui-helper-hidden" );
		$("#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.frameSrcId).attr("src",current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
 	},
 	showInBoxMenu: function(){
 		//查询条件 form reset
		this.formReset();
		$("#contactName,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle").addClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.frameSrcId).attr("src",current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
 	},
 	showSendBoxMenu: function(){
 		this.formReset();
		$("#contactName,#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.frameSrcId).attr("src",current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
 	},
 	showHadSendBoxMenu: function(){
 		this.formReset();
		$("#contactName,#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.frameSrcId).attr("src",current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
 	},
 	showDraftBoxMenu: function(){
 		//查询条件 form reset
		this.formReset();
		$("#contactName").addClass( "ui-helper-hidden" );
		$("#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#searchAll").parent().parent().children().removeClass("zhan").end().end().addClass("zhan");
		$("#readySendDiv,#hadSendDiv,#readySendSubmit,#hadSendSubmit").addClass( "ui-helper-hidden" );
		$("#" + this.frameSrcId).attr("src",current_url);
		this.contactSearch.refresh();
		this.smsTitleSearch.refresh();
 	},
 	formReset: function(){
 		document.getElementById("smsSearch").reset();
 	},
 	showReadySendMenu: function(){
 		/**
 		 * 显示待发箱 批次 列表,查询条件
 		 */
 		$("#contactName,#readySendDiv,#readySendSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
 	},
 	showHadSendMenu: function(){
 		/**
 		 * 显示已发箱 批次 列表,查询条件
 		 */
 		$("#contactName,#hadSendDiv,#hadSendSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#smsSendTime,#timeInterval,#rootSubmit").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
 	},
 	showReplyReadySendMenu: function(){
 		/**
 		 * 显示待发箱 回复列表,查询条件
 		 */
 		$("#contactName,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#readySendSubmit,#readySendDiv").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
 	},
 	showReplyHadSendMenu: function(){
 		/**
 		 * 显示已发箱  回复列表,查询条件
 		 */
 		$("#contactName,#smsSendTime,#timeInterval,#rootSubmit").removeClass( "ui-helper-hidden" );
		$("#smsTitle,#hadSendSubmit,#hadSendDiv").addClass( "ui-helper-hidden" );
		this.contactSearch.refresh();
 	}
 };