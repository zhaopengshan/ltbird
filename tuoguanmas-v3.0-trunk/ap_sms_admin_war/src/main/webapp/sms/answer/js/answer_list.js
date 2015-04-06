var tikuTiKuListGrid;
var datiTiKuGridPro;
var datiTiKuListGrid;

function initdatiTiKuGridPro(title,dateFrom,dateTo,pageFrom){
	var titleInfo = "";
	var dateFromInfo = "";
	var dateToInfo = "";
	var pageFromInfo = "";
	if(typeof title !='undefined'){
		titleInfo = title;
	}
	
	if(typeof dateFrom !='undefined'){
		dateFromInfo = dateFrom;
	}
	
	if(typeof dateTo !='undefined'){
		dateToInfo = dateTo;
	}
	
	
	if(typeof pageFrom !='undefined'){
		pageFromInfo = pageFrom;
	}
	fillInputValue(titleInfo,dateFromInfo,dateToInfo);
	datiTiKuGridPro = {
			url:"masDatiAction/pageDatiTiKuList.action?searchBySmsTitle="+encodeURI(encodeURI(titleInfo))+"&dateFrom="+dateFromInfo+"&dateTo="+dateToInfo+"&pageFrom="+pageFromInfo,
			colNames:['题目内容','答案','分数', '创建时间'],
			colModel:[
				
				{name:'question',width:140,formatter:function(data){
					var sName = data.question;
					if( sName != null && $.trim(sName) != "" ){
						if(sName.length > 8){
							return sName.substring(0,8)+"...";
						}
					}
					return sName;
				}},
				{name:'answer',width:300,formatter:function(data){
						if($.trim(data.answer) == "" ){
							return "";
						}else{
							if(typeof data.answer !== 'undefined'){
								if(data.answer.length>8){
									return data.answer.substring(0,8)+'...';
								}else{
									return data.answer;
								} 
							}else{
								return "";
							}
							//return '<a onclick="javascript:showSmsDetail(\''+data.id+'\');" href="javascript:void(0);" class="grid-sms">'+data.content+'</a>';
						}
					}},
				{name:'score',width:60,align:"center",formatter:function(data){
						if($.trim(data.score) == "" ){
							return 0;
						}else{
							return data.score;
						}
					}},
				{name:'createTime',width:120}		
				
			],
			buttons: [{
				text: "新建",
				classes: "",
				name: "new",
				click: function(){
					var url = 'mbnSmsReadySendAction/cancelSendByIds.action';
					var showMessage = "是否取消发送选中的{0}条短信？";
	                confirmAjaxFunc(smsGrid,url,showMessage);
				}
			},{
				text: "编辑",
				classes: "",
				name: "edit",
				click: function(){
					var url = 'masDatiAction/getTiKu.action';
					//editSmsFunc(datiTiKuListGrid,url);
					editTiKuAjaxFunc(datiTiKuListGrid,url)
				}
			},{
				text: "删除",
				classes: "",
				name: "delete",
				click: function(){
					var url = 'masDatiAction/updateDeleteStatusTiKu.action';
					var showMessage = "是否取消发送选中的{0}条题库？";
					confirmDatiAjaxFunc(datiTiKuListGrid,url,showMessage);
				}
			}]
		};
	
}

function editTiKuAjaxFunc(datiTiKuListGrid,url){
	var rows = datiTiKuListGrid.getSelectedItemIds();
	
	if( rows.length == 1 ){ 
		//window.parent.parent.addTabs('itemx','itemxs'+rows,'短信互动','编辑转发',url+'?selectedId='+rows);
		
		/**$.ajax({
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
		});*/
		
		getDaTiTiKuInfo(rows.toString());
	    
	}else if( rows.length > 1){
		alert("只能选择一项进行操作！");
	}else{
		alert("请先选择需要操作的项！");
	}
}

function confirmDatiAjaxFunc(datiTiKuListGrid,url,showMessage){
	var ids = datiTiKuListGrid.getSelectedItemIds();
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
                	   datiTiKuListGrid.refresh();
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

function searchdatiTiKuList(){
	var tikuTitleSearch = $("#tikuTitleSearch").val();
	var tikuDateFrom = $("#tikuDateFrom").val();
	var tikuDateTo = $("#tikuDateTo").val();
	
	initdatiTiKuGridPro(tikuTitleSearch,tikuDateFrom,tikuDateTo,'');
	datiTiKuListGrid = new TableGrid("datiTiKuListGrid",datiTiKuGridPro);
	datiTiKuListGrid.redrawGrid(datiTiKuGridPro);
}

function exportdatiTiKuList(){
	var tikuTitleSearch = $("#tikuTitleSearch").val();
	var tikuDateFrom = $("#tikuDateFrom").val();
	var tikuDateTo = $("#tikuDateTo").val();
	var tiku_listType  = $("#tiku_listType").val();
	
	$.ajax({
        url: "masDatiAction/exportDatiTiKuList.action",
        type:"post",
        dataType: 'json',
        data:  {
        	"searchBySmsTitle": tikuTitleSearch,
        	"dateFrom":tikuDateFrom,
        	"dateTo":tikuDateTo,
        	"listType":tiku_listType
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

function fillInputValue(title,dateFrom,dateTo){
	$("#tikuTitleSearch").val(title);
	$("#tikuDateFrom").val(dateFrom);
	$("#tikuDateTo").val(dateTo);
}



function createDaTiTiKu(){
	//$("#createGroupDiv").dialog(dialogOptionsCreateGroup);
	$("#tikuAddDialogLoad").load("./ap/answer/tikuAddDialog.jsp");
}

function getDaTiTiKu(id,question,answer,score){
	//$("#createGroupDiv").dialog(dialogOptionsCreateGroup);
	//$("#tikuAddDialogLoad").load("./ap/answer/tikuAddDialog.jsp");
	$("#tikuAddDialogLoad").load("./ap/answer/tikuAddDialog.jsp?id="+id+"&question="+question+"&answer="+answer+"&score="+score);
}

function getDaTiTiKuInfo(id){
	
	$("#tikuAddDialogLoad").load("./ap/answer/tikuAddDialog.jsp?id="+id);
}

		