// JavaScript Document
/**
  * @license leadtone  0.0.1 - Grid
  * @base jqGrid  4.4.1  - jQuery Grid
  * @Copyright (c) 2012, sunyadong sunyadong@leadtone.cn
  *
  *
  */
 
  var leadtoneProes = {
	    url: "",
		data: [],
		datatype: "json",
		colNames:[],
		colModel:[],
		altRows: false,
		altclass: 'altclass',
		rowNum:200,
		//rowList:[20,30,40],
		pager: '', //下分页ID
		toppager: true,//如果不需要显示上分页此值设置为false, 默认显示default: true;
		pagerpos: 'right', //暂时不需要改动
		recordpos: 'center',//暂时不需要改动
		sortname: 'id', //
		sortorder: "asc",
		multiselect: true,
		multiselectWidth: 30,
		viewrecords: false,
		//width: 850,
		height: 290,
		resizable: false,
		grouping: false,
		groupingView : {
			groupField : ['group'],
			groupColumnShow : [false],
			groupOrder: 'asc',
			groupText : ['<font size="2">{0}</font> <font size="1">({1}条)</font>']
		},
		toolbar: [true,"both"],
		buttons: [],
		autowidth: true,
		autoChangeWidth: [false,242], //用于 页面动态变化242 表示变化的程度定制于管理页面
		jsonReader : {
	      root:"rows",
	      page: "currpage",
	      total: "total",
	      records: "totalrecords",
	      repeatitems: false,
	      id: "0"
	   }
	};
  
  LeadToneGrid = function(id,proes){
	  this.id = id;
	  this.proes = {};
	  jQuery.extend (this.proes,leadtoneProes, proes);
	};
  LeadToneGrid.prototype={
	init:function(){
		jQuery("#" + this.id ).jqGrid(this.proes);
		for (i=this.proes.buttons.length -1 ;i >=0;i--){
			var btn = $('<div class="'+this.proes.buttons[i].classes+'"><a href="javascript:void(0);">'+this.proes.buttons[i].text+'</a></div>');
			var func = this.proes.buttons[i].click;
			btn.children().first().unbind('click').bind('click',func);
			var cloneBtn = btn.clone();
			cloneBtn.children().first().unbind('click').bind('click',func);
			$("#t_" + this.id).prepend(btn);
			$("#tb_" + this.id).prepend(cloneBtn);
		}
		if(typeof this.proes.pager == "string") {if(this.proes.pager.substr(0,1) !="#") { this.proes.pager = "#"+this.proes.pager;} }
		else { this.proes.pager = "#"+ $(this.proes.pager).attr("id");}
		//分页工具自适应
		$(this.proes.pager).css({width: 'auto'});
		$("#"+this.id+"_toppager").css({width: 'auto'});
		var self = this;
		if(this.proes.autoChangeWidth[0]){
			$(window).resize(function(){
	           self.reWidth();
	       	});
		}
	},
	getSelectedItemIds:function(){
		/**
		  *description 返回当前选中行的 ids
		  *return  Array[]
		  */
		return $("#" + this.id ).jqGrid('getGridParam','selarrrow');
	},
	getItemDetailsById:function(id){
		/**
		  *description 返回当前选中行的 详细
		  *return  Array[]
		  */
		return $("#" + this.id ).jqGrid('getRowData',id);
	},
	filterGrid: function(arg){
		/**
		  *description 查询分页
		  *return  Array[]
		  */
		jQuery.extend (this.proes, arg);
		var param = {url: this.proes.url,page:1};
		$("#" + this.id ).jqGrid('setGridParam', param).trigger("reloadGrid");
	},
	refresh: function(){
		var param = {url: this.proes.url,page:1}
		$("#" + this.id ).jqGrid('setGridParam', param).trigger("reloadGrid");
	},
	redrawGrid: function(arg){
		/**
		  *description grid重定义
		  *return  Array[]
		  */
		jQuery.extend (this.proes, leadtoneProes, arg);
		$("#" + this.id ).GridUnload();
		this.init();
	},
	reWidth: function(){
		/**
		 * 短信办公室 列表自适应
		 * @returns 
		 */
		var width = $(document).width() - this.proes.autoChangeWidth[1];//$("#gbox_" + this.id).parent().next().width();
		$("#" + this.id ).setGridWidth(width);
		//分页工具自适应
		$(this.proes.pager).css({width: 'auto'});
		$("#"+this.id+"_toppager").css({width: 'auto'});
	}
  };
  
  $.extend($.jgrid,{
		template : function(format){ //jqgformat
			var args = $.makeArray(arguments).slice(1), j = 1;
			//sunyadong change args
			switch(parseInt(args[0],10)){
				case 0: args[0] = "今天"; break;
				case 1: args[0] = "昨天"; break;
				case 2: args[0] = "两天前"; break;
				case 3: args[0] = "三天前"; break;
				case 4: args[0] = "四天前"; break;
				case 5: args[0] = "五天前"; break;
				case 6: args[0] = "六天前"; break;
				case 7: args[0] = "七天前"; break;
				case 8: args[0] = "八天前"; break;
			}
			if(format===undefined) { format = ""; }
			return format.replace(/\{([\w\-]+)(?:\:([\w\.]*)(?:\((.*?)?\))?)?\}/g, function(m,i){
				if(!isNaN(parseInt(i,10))) {
					j++;
					return args[parseInt(i,10)];
				} else {
					var nmarr = args[ j ],
					k = nmarr.length;
					while(k--) {
						if(i===nmarr[k].nm) {
							return nmarr[k].v;
							break;
						}
					}
					j++;
				}
			});
		}
	});