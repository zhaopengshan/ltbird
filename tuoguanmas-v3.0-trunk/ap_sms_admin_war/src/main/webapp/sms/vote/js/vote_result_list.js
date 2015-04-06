var voteResultResultListBaseUrl="voteAction/voteResultList.action?id=";
var voteResultRequestUrl="";
var voteresultsearchText="";
function voteResultOnload(id){
	document.getElementById("voteResult_tpdc_id").value=id;
	document.getElementById("voteList_div").style.display="none";
	document.getElementById("voteResultDetails_div").style.display="none";
	document.getElementById("voteResult_div").style.display="";
	voteresultsearchText="";
	initVoteResultSearch();
	showVoteResultList();
}
function voteResultDetailsOnload(id){
	document.getElementById("voteResult_tpdc_id").value=id;
	document.getElementById("voteList_div").style.display="none";
	document.getElementById("voteResultDetails_div").style.display="";
	document.getElementById("voteResult_div").style.display="none";
	drawVoteResult();
}
function voteDetailsReturn(){
	document.getElementById("voteList_div").style.display="";
	document.getElementById("voteResultDetails_div").style.display="none";
	document.getElementById("voteResult_div").style.display="none";
	smsGrid.selectedAll();
}
var voteResultGrid;
function showVoteResultList(){
	var tpdc_id=document.getElementById("voteResult_tpdc_id").value;
	var url=voteResultResultListBaseUrl+tpdc_id+voteresultsearchText;
	var gridPro = {
			url:url,//执行地址
			colNames:['回复人姓名', '回复人手机号码','回复内容','回复时间'],//列名
			colModel:[//列所有名称的属性
			          	{name:'name',width:50,align:"center",formatter:function(data){
			          		var sName = data.name;
							if( sName != null && $.trim(sName) != "" ){
								if(sName.length > 8){
									return sName.substring(0,8)+"...";
								}
							}
							return sName;
						}},
						{name:'mobile',width:140},
						{name:'answer_content',width:100,formatter:function(data){
							var sName = data.answer_content;
							if( sName != null && $.trim(sName) != "" ){
								if(sName.length > 8){
									return sName.substring(0,8)+"...";
								}
							}
							return sName;
						}},
						{name:'create_time',width:100,align:"center"}
			],
			buttons: [{
				text: "返回",
				classes: "",
				name: "return",
				click: function(){
					document.getElementById("voteList_div").style.display="";
					document.getElementById("voteResult_div").style.display="none";
					document.getElementById("voteResultDetails_div").style.display="none";
					smsGrid.selectedAll();
				}
			},{
				text: "删除",
				name:'delete',
				classes: "",
				click: function(){
					var url = 'voteAction/deleteVoteResult.action';
					var showMessage = "是否删除选中的{0}条投票结果？";
	                confirmAjaxFunc(voteResultGrid,url,showMessage);
				}
			}],
			multiselect: true
		};
		
		$(function() {
			voteResultGrid = new TableGrid("voteResultGrid",gridPro);
			voteResultGrid.redrawGrid(gridPro);
		});
}

function initVoteResultSearch(){
	document.getElementById("voteReplyNameSearch").value="";
	document.getElementById("voteReplynumSearch").value="";
	document.getElementById("voteResultDateFrom").value="";
	document.getElementById("voteResultDateTo").value="";
}
function voteResultSearch(){
	var tp_Name=document.getElementById("voteReplyNameSearch").value;
	var tp_Num=document.getElementById("voteReplynumSearch").value;
	var tp_DateFrom=document.getElementById("voteResultDateFrom").value;
	var tp_DateTo=document.getElementById("voteResultDateTo").value;
	if(tp_DateFrom!="" && tp_DateTo!=""){
		if(Date.parse(tp_DateFrom.replace('-','/').replace('-','/'))>Date.parse(tp_DateTo.replace('-','/').replace('-','/'))){
			alert("开始时间不能大于结束时间");
			return;
		}
	}
	voteresultsearchText="&tp_Name="+encodeURI(encodeURI(tp_Name))+"&tp_Num="+tp_Num+"&tp_DateFrom="+tp_DateFrom+"&tp_DateTo="+tp_DateTo;
	showVoteResultList();
}
var bar = function (id,title,data){
	//展示的id
	this.id = '';
	//投票总数
	this.totalCout=0;
	//s是否显示总投票数
	this.isShowTotal=false;
	//标题
	this.title = '';
	//数据
	this.data = '';
	//宽
	this.width = 800;
	//背景图片位置
	this.bgimg = './themes/mas3/images/plan.gif';
	//动画速度
	this.speed = 1000;
	//投票总数
	var num_all = 0;
	this.show = function (){
		
		//添加一个table对象
		$("#"+this.id).append("<table width='"+this.width+"' cellpadding=0 cellspacing=6 border=0 style='font-size:22px;' ></table>")
		$("#"+this.id+" table").append("<tr><td colspan=4 align='center' ><span style='font:900 24px ;color:#444'>"+this.title+"</span></td></tr>")
		//计算总数
		$(this.data).each(function(i,n){
			num_all += parseInt(n[1]);
		})
		var that = this;
		//起始
		var s_img = [0,-52,-104,-156,-208];
		//中间点起始坐标
		var c_img = [-312,-339,-367,-395,-420];
		//结束
		var e_img = [-26,-78,-130,-182,-234];
		var that = this;
		var div;
		var count=this.data.length;
		$(this.data).each(function(i,n){
			//计算比例
			var bili = (n[1]*100/num_all).toFixed(2);
			//计算图片长度, *0.96是为了给前后图片留空间
			var img = parseFloat(bili)*0.96;
			var imgIndex=i%5;
			if(img>0)
			{
				div = "<div style='width:3px;height:16px;background:url("+that.bgimg+") 0px "+s_img[imgIndex]+"px ;float:left;'></div><div fag='"+img+"' style='width:0%;height:16px;background:url("+that.bgimg+") 0 "+c_img[imgIndex]+"px repeat-x ;float:left;'></div><div style='width:3px;height:16px;background:url("+that.bgimg+") 0px "+e_img[imgIndex]+"px ;float:left;'></div>";
			}
			else
			{
				div='';
			}
			var tmp="";
			if(bili<10)
				tmp="&nbsp;";
			
			$("#"+that.id+" table").append("<tr><td width='25%' align='right' >"+n[0]+"：</td><td width='55%' bgcolor='#fffae2' >"+div+"</td><td width='10%' nowrap >"+tmp+bili+"%&nbsp;&nbsp;&nbsp;得票数:"+n[1]+"</td></tr>")
			if(i==count-1 && that.isShowTotal){
				bili = (num_all*100/that.totalCout).toFixed(2);
				img = parseFloat(bili)*0.96;
				if(img>0)
				{
					div = "<div style='width:3px;height:16px;background:url("+that.bgimg+") 0px "+s_img[(imgIndex+1)%5]+"px ;float:left;'></div><div fag='"+img+"' style='width:0%;height:16px;background:url("+that.bgimg+") 0 "+c_img[(imgIndex+1)%5]+"px repeat-x ;float:left;'></div><div style='width:3px;height:16px;background:url("+that.bgimg+") 0px "+e_img[(imgIndex+1)%5]+"px ;float:left;'></div>";
				}
				else
				{
					div='';
				}
				$("#"+that.id+" table").append("<tr><td width='30%' align='right' >总投票占比：</td><td width='60%' bgcolor='#fffae2' >"+div+"</td><td width='10%' nowrap >"+bili+"%&nbsp;&nbsp;&nbsp;得票数:"+num_all+"</td></tr>");
				$("#"+that.id+" table").append("<tr><td colspan=4 width='100%' style='color:red' align='center' >应投总票数："+that.totalCout+"</td></tr>");
			}
		})
		
		this.play();
	}

	this.play = function (){
		var that = this;		
		$("#"+this.id+" div").each(function(i,n){
			if($(n).attr('fag'))
			{
				$(n).animate( { width: $(n).attr('fag')+'%'}, that.speed )
			}
		})
	}
}
function drawVoteResult(){
	$("#voteResult_details").empty();
	var tpdc_id=document.getElementById("voteResult_tpdc_id").value;
	$.ajax({
        url: "voteAction/drawVoteResult.action",
        type:"post",
        dataType: 'json',
        data:  {
        	id: tpdc_id
        },
        success:function(data){
        	try{
        		var data1 = [];
        		for(var i=0;i<data.rows.length;i++){
        			var sName=data.rows[i].answer;
        			if( sName != null && $.trim(sName) != "" ){
						if(sName.length > 8){
							sName=sName.substring(0,8)+"...";
						}
					}
        			data1[i]=[sName,data.rows[i].count];
        		}
        	var bar1 = new bar();
        	bar1.id = 'voteResult_details';
        	
        	bar1.title = data.voteTile;
        	bar1.totalCout=data.tocalCount;
        	bar1.isShowTotal=data.voteMode!=1;
        	bar1.data = data1;
        	bar1.show();
        	}catch(e){alert("出现系统错误，请稍后再试")}
        },
		error:function(data){
			 alert("出现系统错误，请稍后再试");
		}
	 }
	);
	
}
function exportVoteResultList(){
	var tpdc_id=document.getElementById("voteResult_tpdc_id").value;
	$.ajax({
        url : "voteAction/exportVoteResultList.action?id="+tpdc_id+voteresultsearchText,
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
