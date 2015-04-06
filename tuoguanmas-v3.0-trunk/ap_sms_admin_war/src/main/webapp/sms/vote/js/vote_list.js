var listType="";
var voteListBaseUrl="voteAction/voteList.action?";
var voteRequestUrl="";
var searchText="";
function voteOnload(){
	initVoteSearch();
	switchVoteType("send");
	document.getElementById("voteList_div").style.display="";
	document.getElementById("voteResult_div").style.display="none";
}
var smsGrid;
function showVoteList(){
	var url=voteRequestUrl+searchText;
	var gridPro = {
			url:url,//执行地址
			colNames:['状态', '任务名称','有效时间','投票结果','回复','创建人','关闭投票'],//列名
			colModel:[//列所有名称的属性
			          {name:'sendResult',width:50,align:"center",formatter:function(data){
							var iconSms = '<img title="未发送" src="${ctx}/themes/mas3/images/u16220_normal.png" width="15" height="13"/>';
							switch(listType){
								case "send": iconSms = '<img title="已发送" src="./themes/mas3admin/images/vote/lise_lcon1.gif" width="15" height="13"/>'; break;
								case "reply": iconSms = '<img title="已回复" src="./themes/mas3admin/images/vote/ico-huifu.png" width="15" height="16"/>'; break;
								case "notSend": iconSms = '<img title="未发送" src="./themes/mas3admin/images/vote/lise_lcon3.gif" width="13" height="16"/>'; break;
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
						{name:'voteTime',width:100,formatter:function(data){
							var begin_time = data.begin_time;
							var end_time=data.end_time;
							
							return begin_time+"-</br>"+end_time;
						}},
						{name:'voteResult',width:100,align:"center",formatter:function(data){
							var result_count=data.result_count;
							if(result_count=="0")
								return "无";
							else
								return "<a onclick=\"voteResultDetailsOnload('"+data.id+"')\" href='javascript:void(0)'>查看</a>";
						}},
						{name:'voteReply',width:100,align:"center",formatter:function(data){
							var result_count=data.result_count;
							if(result_count=="0")
								return "0";
							else
								return "<a onclick=\"voteResultOnload('"+data.id+"')\" href='javascript:void(0)'>"+data.result_count+"</a>";
						}},
						{name:'createBy',width:100,align:"center",formatter:function(data){
							
							return data.create_by;
						}},
						{name:'closeVote',width:100,align:"center",formatter:function(data){
							
							//时间比较
							var end_time=data.end_time;
							if(comptime(end_time)==true)
								return "<a style='' id='vc"+data.id+"' href='javascript:void(0)' onclick=\"closeVote('"+data.id+"',this)\">关闭投票</a>";
							else
								return "&nbsp;";
						}}
			],
			buttons: [{
				text: "编辑转发",
				name:'edit',
				classes: "",
				click: function(){
					var url = 'voteAction/editVote.action';
					editVoteFunc(smsGrid,url);
				}
				},
			          
			   {
				text: "删除",
				name:'delete',
				classes: "",
				click: function(){
					var url = 'voteAction/deleteVote.action';
					var showMessage = "是否删除选中的{0}条投票？";
	                confirmAjaxFunc(smsGrid,url,showMessage);
				}
			}],
			multiselect: true
		};
		
		$(function() {
			smsGrid = new TableGrid("voteGrid",gridPro);
			smsGrid.redrawGrid(gridPro);
		});
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
function initVoteSearch(){
	document.getElementById("voteTitleSearch").value="";
	document.getElementById("voteCreateBySearch").value="";
	document.getElementById("voteDateFrom").value="";
	document.getElementById("voteDateTo").value="";
}
function voteSearch(){
	var searchTitle=document.getElementById("voteTitleSearch").value;
	var searchCreateBy=document.getElementById("voteCreateBySearch").value;
	var searchDateFrom=document.getElementById("voteDateFrom").value;
	var searchDateTo=document.getElementById("voteDateTo").value;
	if(searchDateFrom!="" && searchDateTo!=""){
		if(Date.parse(searchDateFrom.replace('-','/').replace('-','/'))>Date.parse(searchDateTo.replace('-','/').replace('-','/'))){
			alert("开始时间不能大于结束时间");
			return;
		}
	}	
	searchText="&searchTitle="+encodeURI(encodeURI(searchTitle))+"&searchCreateBy="+encodeURI(encodeURI(searchCreateBy))+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
	showVoteList();
}
function switchVoteType(type){
	searchText="";
	initVoteSearch();
	listType=type;
	voteRequestUrl=voteListBaseUrl+"listType="+listType;//+"&pageNum=1";
	showVoteList();
	switch(listType){
		case "send":
			document.getElementById("vote_hadsend_menu").className="zhan";
			document.getElementById("vote_readysend_menu").removeAttribute("class");
			document.getElementById("vote_hadreply_menu").removeAttribute("class");
			break;
		case "reply":
			document.getElementById("vote_hadreply_menu").className="zhan";
			document.getElementById("vote_hadsend_menu").removeAttribute("class");
			document.getElementById("vote_readysend_menu").removeAttribute("class");
			break;
		case "notSend":
			document.getElementById("vote_readysend_menu").className="zhan";
			document.getElementById("vote_hadsend_menu").removeAttribute("class");
			document.getElementById("vote_hadreply_menu").removeAttribute("class");
			break;
	}
}
function comptime(endTime) {
	try{
		var d=new Date();
	    var year = endTime.substr(0,4);
		var month = endTime.substr(5,2);
		var day = endTime.substr(8,2);
		var hour = endTime.substr(11,2);
		var minut =endTime.substr(14,2);
		var ss =endTime.substr(17,2);
	    var endDate=new Date(year,month-1,day,hour,minut,ss);
	    if(endDate.getTime()>d.getTime()){
	    	return true;
	    }else
	    	return false;
	}catch(e){
		return false;
	}
}
function exportVoteList(){
	$.ajax({
        url : "voteAction/exportVoteList.action?listType="+listType+searchText,
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
function editVoteFunc(smsGrid,url){
	var rows = smsGrid.getSelectedItemIds();
	if( rows.length == 1 ){ 
	    var originalUrl = "./voteAction/writeSms.action";
	    var tempUrl = url+'?selectedId='+rows;
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