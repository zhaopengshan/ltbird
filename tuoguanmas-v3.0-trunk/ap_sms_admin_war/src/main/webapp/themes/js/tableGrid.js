/**
 * 
 */
 var tableGridProes = {
	    url: "",
		buttons: [],//name,classes,text,click,single
		colNames:[],//列名
		colModel:[],//formatter,name，hidden,width,align列属性
		multiselect: true,
		multiselectWidth: 50,//checkbox 的宽度
		height: null, //
		page: 1,
		rows: 20,
		param:{}
	};
 
  TableGrid = function(gridId,proes){
 	this.gridId = gridId;
 	this.proes = {};
// 	this.singleTopBtn=new Array();
// 	this.singleBtmBtn=new Array();
// 	this.singleHandle=new Array();
	jQuery.extend (this.proes,tableGridProes, proes);
	if(typeof this.proes.height !== 'undefined'&& this.proes.height !== null){
		this.paDiv = '<div class="gridfullscreen" style="overflow-y: auto;overflow-x: hidden; height:'
		+this.proes.height+'px;"/>';
	}else{
		this.paDiv = '<div class="gridfullscreen" style="overflow-y: auto;overflow-x: hidden;"/>';
	}
  };
 
  TableGrid.prototype={
    redrawGrid: function(arg){
		jQuery.extend (this.proes, tableGridProes, arg);
		jQuery('#'+ this.gridId ).unwrap(this.paDiv).children().remove();
		this.init();
	},
  	init: function(){
  		if(typeof this.gridId == "string") {
  			if(this.gridId.substr(0,1) =="#") { 
  				this.gridId = this.gridId.substring(1);
  			}
  		}else { 
  			this.gridId = $(this.gridId).attr("id");
  		}
  		jQuery('#'+ this.gridId ).wrap(this.paDiv);
  		var colums = '',
  			rowTemplate = '',
  			cols = this.proes.multiselect?(this.proes.colNames.length+1):this.proes.colNames.length;
	    if(this.proes.colNames.length != this.proes.colModel.length){
	    	alert('列名与列长度不相等');
	    	return;
	    }else{
	    	if(this.proes.multiselect){
	    		colums='<th class="tabletrhead"><input type="checkbox" name="name'+this.gridId+'" id="mul_select_'+this.gridId+'" /></th>';
	    		rowTemplate = '<td></td>';
	    	}
	    	for(j=0;j < this.proes.colModel.length; j++){
	    		if(typeof this.proes.colModel[j].hidden === 'undefined'||!this.proes.colModel[j].hidden){
			    	colums += '<th class="tabletrhead">'+this.proes.colNames[j]+'</th>';
			    	rowTemplate += '<td></td>';
		    	}else{
		    		//this.proes.colModel[j].pop();
		    		cols--;
			    	continue;
		    	}
		    }
	    }
	    var tmptr="<tr style=''>";
	    if(this.proes.multiselect)
	    	tmptr+="<th style='width:"+this.proes.multiselectWidth+"px;'></th>"
	    for(j=0;j < this.proes.colModel.length; j++){
	    	if(this.proes.colModel[j].width!=="undefined")
	    		tmptr+="<th style='width:"+this.proes.colModel[j].width+"px;'></th>";
	    	else
	    		tmptr+="<th></th>"
	    }
	    tmptr+="</tr>"
  		var gtitle = '<thead>'
  						+tmptr
	                    +'<tr class="tableopts">'
	                    +'<td colspan="'+cols+'">'
	                    +'<table>'
	                    +'<thead>'
		                +'<tr class="tableopts">'
			            +'<td class="tubh" colspan="2" id="t_'+this.gridId+'"></td>'
	                    +'<td style="text-align: right; " class="newpage">'
	                    +'<a class="tubh-page" href="javascript:void(0)" id="pre_'+this.gridId+'">上一页</a>'
	                    +'<span id="cur_'+this.gridId+'" style="margin: 0;padding: 0;" >1</span>'
	                    +'<span style="margin: 0 2px 0 2px;">/</span>'
	                    +'<span id="total_page_'+this.gridId+'" style="margin: 0;padding: 0;"></span>'
	                    +'<a class="tubh-page" href="javascript:void(0)" id="flw_'+this.gridId+'">下一页</a>'
	                    +'跳转到 <input type="text" id="enter_page_'+this.gridId+'" value="1" style="width:30px;" /> 页'
	                    +'<span class="" style="width:40px;"><a class="tubh-page" href="javascript:void(0)" id="jump_'+this.gridId+'">GO</a></span>'
	                    +'</td>'
	                    +'</tr>'
	                    +'</thead>'
	                    +'</table>'
	                    +'</td>'
	                    +'</tr>'
	                    +'<tr>'+colums+'</tr>'
	                    +'<tr class="tabletrboby" style="display:none" id="row_template'+this.gridId+'">'
	                    +rowTemplate
       					+'</tr>'
	                    +'</thead>';
		var gfoot ='<tfoot>'
                    +'<tr class="tableopts">'
                    +'<td colspan="'+cols+'">'
                    +'<table>'
	                +'<tr>'
                    +'<td class="tubh" colspan="2" id="tb_'+this.gridId+'"></td>'
                    +'<td style="text-align: right" class="newpage">'
                    +'<a class="tubh-page" href="javascript:void(0)" id="tb_pre_'+this.gridId+'">上一页</a>'
                    +'<span id="tb_cur_'+this.gridId+'" style="margin: 0px;padding: 0;" >1</span>'
                    +'<span style="margin: 0 2px 0 2px;">/</span>'
                    +'<span id="tb_total_page_'+this.gridId+'" style="margin: 0px;padding: 0;"></span>'
                    +'<a class="tubh-page" href="javascript:void(0)" id="tb_flw_'+this.gridId+'">下一页</a>'
                    +'跳转到 <input type="text" id="tb_enter_page_'+this.gridId+'" value="1" style="width:30px;" /> 页'
                    +'<span class="" style="width:40px;"><a class="tubh-page" href="javascript:void(0)" id="tb_jump_'+this.gridId+'">GO</a></span>'
                    +'</td>'
                    +'</tr>'
                    +'</table>'
                    +'</td>'
                    +'</tr>'
                	+'</tfoot>';
        var gbody = '<tbody id="data_'+this.gridId+'"></tbody>';
        //内嵌列表统一代码
        jQuery('#'+ this.gridId ).append(gtitle).append(gbody).append(gfoot);
        //绑定全选事件
        $("#mul_select_" + this.gridId).unbind('click').bind('click',(function(self){return function(){self.selectedAll();}})(this));
		$("#pre_"+this.gridId+",#tb_pre_"+this.gridId).unbind('click').bind('click',(function(self){return function(){self.prePage();}})(this));
		$("#flw_"+this.gridId+",#tb_flw_"+this.gridId).unbind('click').bind('click',(function(self){return function(){self.nextPage();}})(this));
		$("#jump_"+this.gridId).unbind('click').bind('click',(function(self){return function(){self.topJumpPage();}})(this));
		$("#tb_jump_"+this.gridId).unbind('click').bind('click',(function(self){return function(){self.bottomJumpPage();}})(this));
		//生成button及绑定相关事件
		var btn = '';
		$("#t_" + this.gridId+",#tb_" + this.gridId).children().remove();
		for (i=0;i <= this.proes.buttons.length -1; i++){
			var btn = $('<a href="javascript:void(0);" class="'+this.proes.buttons[i].classes+'"  id="'+this.gridId+this.proes.buttons[i].name+'">'+this.proes.buttons[i].text+'</a>');
			var cloneBtn = btn.clone();
			var func = this.proes.buttons[i].click;
			btn.unbind('click').bind('click',func);
			cloneBtn.attr('id','foot_'+this.gridId+this.proes.buttons[i].name).unbind('click').bind('click',func);
			$("#t_" + this.gridId).append(btn);
			$("#tb_" + this.gridId).append(cloneBtn);
//			if(typeof this.proes.buttons[i].single !== 'undefined'){
//        		this.singleTopBtn.push($("#"+this.gridId+this.proes.buttons[i].name));
//        		this.singleBtmBtn.push($("#foot_"+this.gridId+this.proes.buttons[i].name));
//        		this.singleHandle.push(func);
//        	}
		}
		this.getData();
  	},
  	selectedAll:function(){
  		if($("#data_"+this.gridId+" tr").length > 0) {
            if($("#mul_select_" + this.gridId).attr("checked")) {
                $("#data_"+ this.gridId +" tr").each(function(){
                    $(this).find("input").attr("checked",true);
                });
            } else {
                $("#data_"+ this.gridId +" tr").each(function(){
                    $(this).find("input").attr("checked",false);
                });
            }                
        }
  	},
  	filterGrid: function(arg){
		jQuery.extend (this.proes, arg);
		var param = {url: this.proes.url,page:1};
		this.getData();
	},
	refresh: function(){
		this.getData();
	},
  	getData:function(){
  		var self = this,url = this.proes.url,parames={
  				page: $("#cur_"+this.gridId).html(),
            	rows: this.proes.rows
  				};
  		jQuery.extend (parames,this.proes.param);
  		$.ajax({
            url: url,
            type:"post",
            dataType: 'json',
            data: parames,
            success:function(data){
                self.refreshList(data);
                $("#data_"+ self.gridId +" tr").each(function(){
                    $(this).find("input").live("click",function(event){
                        event.stopPropagation();
                        if($(this).attr("checked")) {
                            self.setSelected();
                        } else {
                            self.cancelAllSelectedId();
                        }
                    });
                    $(this).live("click",function(){
                        if($(this).find("input").attr("checked")) {
                            $(this).find("input").attr("checked",false);
                            self.cancelAllSelectedId();                            
                        } else {
                            $(this).find("input").attr("checked",true);
                            self.setSelected();
                        }                        
                    });
                });
                self.autoY();
            },
            error:function(data){
            }
        });
  	},
  	autoY:function(){
  		$("#"+ this.gridId).parent().attr("style",$("#"+ this.gridId).parent().attr("style")+";overflow-y: auto;");
  	},
  	refreshList: function (data){        
        //设置翻页相关信息
        $("#total_page_"+this.gridId).html(Math.floor((data.totalrecords-1)/this.proes.rows)+1);
        $("#tb_total_page_"+this.gridId).html(Math.floor((data.totalrecords-1)/this.proes.rows)+1);
        //设置表格数据
        $("#data_"+ this.gridId +" tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            this.addRow(data.rows[i]);
        }
    },
    jsonHanoi: function(data,name){
    	var nameArray = name.split(".");
    	var colProValue = data;
    	for(var a = 0; a < nameArray.length; a++ ){
    		colProValue = colProValue[nameArray[a]];
    		if( typeof(colProValue) ==="undefined" ){
    			colProValue= "";
    			break;
    		}
    	}
    	return colProValue;
    },
    addRow:function (data){
        //使用jquery拷贝方式生成行数据
        $("#row_template"+this.gridId).clone(true).appendTo("#data_"+this.gridId);
        $("#data_"+this.gridId +" tr:last").attr("id",data.id).show();
        var mulTemp = this.proes.multiselect,colPro,j=0,
        	$currentTd = $("#data_"+this.gridId +" tr:last td");
        if(mulTemp){
    		colPro = "<input type='checkbox' name='name"+this.gridId+"' value='"+data.id+"'/>";
    		$currentTd.eq(j).html(colPro).attr("width",this.proes.multiselectWidth).attr("class","tabletdmulti");
    		j++;
    		for(;j <= this.proes.colModel.length; j++){
	        	tempj = j;
	        	if(mulTemp){
	        		tempj-=1;
	        	}
	        	
	        	//colPro = data[this.proes.colModel[tempj].name];
	        	if(typeof this.proes.colModel[tempj].formatter !== 'undefined'){
	        		colPro = this.proes.colModel[tempj].formatter(data);
	        	}else{
	        		colPro = this.jsonHanoi(data,this.proes.colModel[tempj].name);
	        	}
	        	if(typeof this.proes.colModel[tempj].width !== 'undefined'){
	        		$currentTd.eq(j).attr("width",this.proes.colModel[tempj].width);
	        	}
	        	if(typeof this.proes.colModel[tempj].align !== 'undefined'){
	        		$currentTd.eq(j).attr("align",this.proes.colModel[tempj].align);
	        	}
	        	$currentTd.eq(j).html(colPro);
		    }
    	}else{
	        for(;j < this.proes.colModel.length; j++){
	        	//colPro = data[this.proes.colModel[j].name];
	        	if(typeof this.proes.colModel[j].formatter !== 'undefined'){
	        		colPro = this.proes.colModel[j].formatter(data);
	        	}else{
	        		colPro = this.jsonHanoi(data,this.proes.colModel[tempj].name);
	        	}
	        	if(typeof this.proes.colModel[j].width !== 'undefined'){
	        		$currentTd.eq(j).attr("width",this.proes.colModel[j].width);
	        	}
	        	if(typeof this.proes.colModel[j].align !== 'undefined'){
	        		$currentTd.eq(j).attr("align",this.proes.colModel[j].align);
	        	}
	        	$currentTd.eq(j).html(colPro);
		    }
		}
    },
    setSelected: function (){
        var allselected = true,count=0;
        $("#data_"+ this.gridId +" tr").each(function(){                 
            if($(this).find("input").attr("checked")){   
            	count++;                     
            } else {
                allselected = false;
            }            
        });
        //this.setBtnStatus(count);
        if(allselected) {
            $("#mul_select_" + this.gridId).attr("checked",true);
        }
    },
    setBtnStatus: function(count){
    	if(count <= 1){
//    		this.singleTopBtn.push(this.gridId+this.proes.buttons[i].name);
//			this.singleBtmBtn.push("foot_"+this.gridId+this.proes.buttons[i].name);
//			this.singleHandle.push(func);
			for(var i =0; i < this.singleTopBtn.length; i++ ){
				if(this.singleTopBtn[i].data("events")["click"]){
					alert("fafa");
				}
			}
    	}else{
    		for(var i =0; i < this.singleTopBtn.length; i++ ){
				if(this.singleTopBtn[i].data("events")["click"]){
					alert("bb");
				}
			}
    	}
    },
    cancelAllSelectedId:function (){
        if($("#mul_select_" + this.gridId).attr("checked")){
            $("#mul_select_" + this.gridId).attr("checked",false);
        }
    },
    prePage: function(){
    	var curPage = parseInt($("#cur_"+this.gridId).html());
        if(curPage > 1) {
            var curPageNo = curPage-1;
            $("#cur_"+this.gridId).html(curPageNo);
            $("#tb_cur_"+this.gridId).html(curPageNo);
            $("#enter_page_"+this.gridId).val(curPageNo);
            $("#tb_enter_page_"+this.gridId).val(curPageNo);
        }
        this.cancelAllSelectedId();            
        this.getData();
    },
    nextPage: function(){
    	var curPage = parseInt($("#cur_"+this.gridId).html()),
    		totalPage = parseInt($("#total_page_"+this.gridId).html());
        if(curPage < totalPage) {
            var curPageNo = curPage+1;
            $("#cur_"+this.gridId).html(curPageNo);
            $("#tb_cur_"+this.gridId).html(curPageNo);
            $("#enter_page_"+this.gridId).val(curPageNo);
            $("#tb_enter_page_"+this.gridId).val(curPageNo);
        }
        this.cancelAllSelectedId();            
        this.getData();
    },
    bottomJumpPage: function(){
        $("#enter_page_"+this.gridId).val($("#tb_enter_page_"+this.gridId).val());
        this.topJumpPage();
    },
    topJumpPage: function(){
    	var totalPage = parseInt($("#total_page_"+this.gridId).html()),
    		enterPage = parseInt($("#enter_page_"+this.gridId).val());
        if( enterPage < 1 || enterPage > totalPage) {
            alert("对不起,您输入的页号非法!");
        } else {
            $("#cur_"+this.gridId).html(enterPage);
            $("#tb_cur_"+this.gridId).html(enterPage);
            $("#tb_enter_page_"+this.gridId).val(enterPage);
            this.cancelAllSelectedId();            
        	this.getData();
        }
    },
    getSelectedItemIds:function(){
		var idArray = new Array();
		$("#data_"+ this.gridId +" tr").find(":checked").each(function(){
			idArray.push($(this).val());
		});
		return idArray;//.toString();
    }
  };
  
  
  
  
  
  
  
  
  
  
  
  