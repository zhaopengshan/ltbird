var answerResultResultListBaseUrl="masDatiAction/pageDatiResultList.action?dtId=";
var answerResultUpdateStatusBaseUrl="masDatiAction/updateResultDeleteStatus.action";
var answerResultGrid;
var answerResultsearchText="";

function answerResultOnload(id){
	$("#answerId").val(id);
	$("#shijuan_list_div").css("display","none");
	$("#shijuan_result_div").css("display","block");
	
	answerResultsearchText="";
	initAnswerResultSearch();
	showAnswerResultList();
}


function showAnswerResultList(){
	var answerId = $("#answerId").val();
	var url=answerResultResultListBaseUrl+answerId+answerResultsearchText;
	var gridPro = {
			url:url,//执行地址
			colNames:['参与人', '分数'],//列名
			colModel:[//列所有名称的属性
			          	{name:'name',width:50,align:"center",formatter:function(data){
			          		var sName = data.name;
							if( sName != null && $.trim(sName) != "" ){
								if(sName.length > 8){
									return sName.substring(0,8)+"...";
								}
							}else{
								sName = data.mobile;
							}
							return sName;
						}},
						
						{name:'score',width:100,formatter:function(data){
							var sName = data.sumScore;
							
							return sName;
						}}
			],
			buttons: [{
				text: "返回",
				classes: "",
				name: "return",
				click: function(){
					$("#shijuan_list_div").css("display","block");
					$("#shijuan_result_div").css("display","none");
					
					
					datiShiJuanListGrid.selectedAll();
				}
			},{
				text: "删除",
				name:'delete',
				classes: "",
				click: function(){
					var url = 'voteAction/deleteVoteResult.action';
					var showMessage = "是否删除选中的{0}条答题结果？";
					confirmDatiResultAjaxFunc(answerResultGrid,answerResultUpdateStatusBaseUrl,showMessage);
				}
			}],
			multiselect: true
		};
		
	$(function() {
		answerResultGrid = new TableGrid("datiResultListGrid",gridPro);
		answerResultGrid.redrawGrid(gridPro);
	});
}


function initAnswerResultSearch(){
	$("#shijuanResultTitleSearch").val("");
	
}


function confirmDatiResultAjaxFunc(answerResultGrid,url,showMessage){
	var ids = answerResultGrid.getSelectedItemIds();
	if( ids.length > 0 ){
		//var showMessage = "是否取消发送选中的"+ids.length+"条短信？";
		showMessage = String.format(showMessage,ids.length);
		if(confirm(showMessage)){
           	$.ajax({
                   url : url,
                   type : 'post',
                   dataType: "json",
                   data: {
                   	"smsIds": ids.toString(),
                   	"dtId":$("#answerId").val()
                   },
                   success : function(data) {
						/**alert(data.message);
                       if(data.resultcode == "success"){
                    	   datiTiKuListGrid.refresh();
                       }*/
                	   if(data.resultcode){
                		   alert(data.message);
                		   answerResultGrid.refresh();
                	   }
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


function answerResultSearch(){
	var tp_Name=$("#shijuanResultTitleSearch").val();
	
	answerResultsearchText="&tp_Name="+encodeURI(encodeURI(tp_Name));
	showAnswerResultList();
}