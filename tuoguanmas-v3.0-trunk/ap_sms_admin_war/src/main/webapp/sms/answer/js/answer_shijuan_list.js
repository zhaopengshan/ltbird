//var tikuShiJuanListGrid;
var datiShiJuanGridPro;
var datiShiJuanListGrid;

function initdatiShiJuanGridPro(title,createBy,pageFrom){
	var titleInfo = "";
	var dateFromInfo = "";
	var dateToInfo = "";
	var pageFromInfo = "";
	var createInfo = "";
	if(typeof title !='undefined'){
		titleInfo = title;
	}
	
	/**if(typeof dateFrom !='undefined'){
		dateFromInfo = dateFrom;
	}*/
	
	if(typeof createBy !='undefined'){
		createInfo = createBy;
	}
	
	
	if(typeof pageFrom !='undefined'){
		pageFromInfo = pageFrom;
	}
	fillInputValue(titleInfo,createInfo,dateFromInfo,dateToInfo);
	datiShiJuanGridPro = {
			url:"masDatiAction/pageDatiList.action?searchBySmsTitle="+encodeURI(encodeURI(titleInfo))+"&pageFrom="+pageFromInfo+"&createInfo="+encodeURI(encodeURI(createInfo)),
			colNames:['任务名称','有效时间','题目数','得分','排名统计','创建人'],
			colModel:[
				
				{name:'title',width:40,formatter:function(data){
					var sName = data.title;
					if( sName != null && $.trim(sName) != "" ){
						if(sName.length > 8){
							return sName.substring(0,8)+"...";
						}
					}
					return sName;
				}},
				{name:'beginToEnd',width:300,formatter:function(data){
					var sName = data.beginTime+"~"+data.endTime;
					/**if( sName != null && $.trim(sName) != "" ){
						if(sName.length > 8){
							return sName.substring(0,8)+"~";
						}
					}*/
					return sName;
				}},
				{name:'dtSum',width:30,formatter:function(data){
					var sName = data.dtSum;
					/**if( sName != null && $.trim(sName) != "" ){
						if(sName.length > 8){
							return sName.substring(0,8)+"~";
						}
					}*/
					return sName;
				}},
				{name:'score',width:20,align:"center",formatter:function(data){
						if($.trim(data.score) == "" ){
							return 0;
						}else{
							return data.score;
						}
				}},
				{name:'viewInfo',width:30,align:"center",formatter:function(data){
					return "<a style=''  href='javascript:void(0)' onclick=\"answerResultOnload('"+data.id+"',this)\">查看</a>";
					//return "<a style='' id='vc"+data.id+"' href="javascript:answerResultOnload('"+data.id+"') >查看</a>";
			    }},
			    {name:'createdBy',width:60,align:"center",formatter:function(data){
					//return "<a style='' id='vc"+data.id+"' href='javascript:void(0)' onclick=\"closeVote('"+data.id+"',this)\">查看</a>";
					return data.createBy;
			    }}	
				
			],
			buttons: [{
				text: "编辑转发",
				classes: "",
				name: "edit",
				click: function(){
					var url = 'masDatiAction/writeDatiInfoById.action';
					editDatiFunc(datiShiJuanListGrid,url);
					//editSmsFunc(datiTiKuListGrid,url);
					//editTiKuAjaxFunc(datiTiKuListGrid,url)
				}
			},{
				text: "删除",
				classes: "",
				name: "delete",
				click: function(){
					var url = 'masDatiAction/updateDeleteStatusShiJuan.action';
					var showMessage = "是否取消发送选中的{0}条试卷？";
					confirmDatiAjaxFunc(datiShiJuanListGrid,url,showMessage);
				}
			}]
		};
	
}


function closeVote(id){
	$.ajax({
        url: "voteAction/closeVote.action",
        type:"post",
        dataType: 'json',
        data:  {
        	id: id.toString()
        },
        success:function(data){
        	alert(data.message);
        	smsGrid.refresh();
        },
		error:function(data){
			 alert("出现系统错误，请稍后再试");
		}
	 }
	);
}

function editTiKuAjaxFunc(datiTiKuListGrid,url){
	var rows = datiTiKuListGrid.getSelectedItemIds();
	if( rows.length == 1 ){ 
		//window.parent.parent.addTabs('itemx','itemxs'+rows,'短信互动','编辑转发',url+'?selectedId='+rows);
		
		$.ajax({
			type: "POST",
			data: {
               	"selectedId": rows.toString()
            },
			url: 'masDatiAction/getTiKu.action',
            dataType:  "json",
			success: function(data){
				//alert("data.question:"+data.info.question);
            	//createDaTiTiKu();
            	//$("#question").val(data.info.question);
            	//$("#answer").val(data.info.answer);
            	//$("#score").val(data.info.score);
				//$("#createTikuDiv").dialog("close");
            	getDaTiTiKu(data.info.id,data.info.question,data.info.answer,data.info.score);
			}
		});
		
		
	    
	}else if( rows.length > 1){
		alert("只能选择一项进行操作！");
	}else{
		alert("请先选择需要操作的项！");
	}
}

function confirmDatiAjaxFunc(datiShiJuanListGrid,url,showMessage){
	var ids = datiShiJuanListGrid.getSelectedItemIds();
	if( ids.length > 0 ){
		//var showMessage = "是否取消发送选中的"+ids.length+"条短信？";
		showMessage = String.format(showMessage,ids.length);
		if(confirm(showMessage)){
           	$.ajax({
                   url : url,
                   type : 'post',
                   dataType: "json",
                   data: {
                   	"smsIds": ids.toString()
                   },
                   success : function(data) {
						/**alert(data.message);
                       if(data.resultcode == "success"){
                    	   datiTiKuListGrid.refresh();
                       }*/
                	   datiShiJuanListGrid.refresh();
                   },
                   error : function() {
                       alert("出现系统错误，请稍后再试");
                   }
               }); 
			}
	}else{//如没有选中行，则提示此信息
		alert("请先选择需要操作的项！");
	}
}

function searchdatiShiJuanList(){
	var shijuanTitleSearch = $("#shijuanTitleSearch").val();
	//var tikuDateFrom = $("#tikuDateFrom").val();
	//var tikuDateTo = $("#tikuDateTo").val();
	var shijuanCreateBySearch = $("#shijuanCreateBySearch").val();
	initdatiShiJuanGridPro(shijuanTitleSearch,shijuanCreateBySearch,'');
	datiShiJuanListGrid = new TableGrid("datiShiJuanListGrid",datiShiJuanGridPro);
	datiShiJuanListGrid.redrawGrid(datiShiJuanGridPro);
}

function exportdatiShiJuanList(){
	var shijuanTitleSearch = $("#shijuanTitleSearch").val();
	var shijuanCreateBySearch = $("#shijuanCreateBySearch").val();
	var shijuan_listType  = $("#shijuan_listType").val();
	
	$.ajax({
        url: "masDatiAction/exportDatiList.action",
        type:"post",
        dataType: 'json',
        data:  {
        	"createInfo": shijuanCreateBySearch,
        	"searchBySmsTitle":shijuanTitleSearch,
        	"listType":shijuan_listType
        },
        success:function(data){
        	if(data.resultcode == "success"){
            	if(confirm(data.message+"是否下载导出内容")){
            		download("./fileDownload?fileName=./downloads/sms/" +data.fileName);
            	}
            }else{
            	alert(data.message);
            }
        	//smsGrid.refresh();
        },
		error:function(data){
			 alert("出现系统错误，请稍后再试");
		}
	 }
	);
}


function fillInputValue(title,createBy,dateFrom,dateTo){
	$("#shijuanTitleSearch").val(title);
	$("#shijuanTitleSearch").val(title);
	$("#shijuanDateFrom").val(dateFrom);
	$("#shijuanCreateBySearch").val(createBy);
	$("#shijuanDateTo").val(dateTo);
}



function editDatiFunc(smsGrid,url){
	var rows = smsGrid.getSelectedItemIds();
	if( rows.length == 1 ){ 
	    var originalUrl = "./masDatiAction/writeDatiInfoPage.action";
	    var tempUrl = url+'?smsIds='+rows;
	    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
	    try{
	        tabpanel.kill(killId);
	    }catch(e){
	    }
	    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
	}else if( rows.length > 1){
		alert("只能选择一项进行操作！");
	}else{
		alert("请先选择需要操作的项！");
	}
}


		