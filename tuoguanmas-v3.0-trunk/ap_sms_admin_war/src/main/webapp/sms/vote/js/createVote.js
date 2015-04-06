function addVoteOption(){
	var optionTb=document.getElementById("voteoptions");
	var trs=optionTb.getElementsByTagName("tr");
	var trCount=trs.length;
	
	if(trCount==2){
		for(var i=0;i<2;i++){
			 trs[i].getElementsByTagName("td")[0].innerHTML=i+1;
			 if(trCount<3){
				 trs[i].getElementsByTagName("td")[2].innerHTML="<a  href='javascript:removeRow("+i+")'><img src='./themes/mas3admin/images/vote/u109_normal.gif' width='16' height='16' /></a>";
			 }
		 }
	}
	var tr=document.createElement("tr");
	var cell=document.createElement("td");
	cell.className='td22';
	cell.width="8%";
	cell.align="center";
	var celltext=document.createTextNode((trCount+1));
	cell.appendChild(celltext);
	var cell1=document.createElement("td");
	cell1.className='td7';
	cell1.width="8%";
	var input1=document.createElement("input");
	input1.name="optionContent_"+(trCount+1);
	input1.width="98%";
	input1.type="text";
	input1.id='optionContent_'+(trCount+1);
	input1.className="input2 optionContent";
	cell1.appendChild(input1);
	var cell2=document.createElement("td");
	cell2.className='td7';
	cell2.width="8%";
	var a2=document.createElement("a");
	a2.href="javascript:removeRow("+trCount+")";
	var img2=document.createElement("img");
	img2.src="./themes/mas3admin/images/vote/u109_normal.gif";
	img2.width="16";
	img2.height="16";
	a2.appendChild(img2);
	cell2.appendChild(a2);
	tr.appendChild(cell);
	tr.appendChild(cell1);
	tr.appendChild(cell2);
	optionTb.getElementsByTagName('tbody')[0].appendChild(tr);
    if(trCount+1>9){
    	document.getElementById("btnAddOption").style.display='none';
    }
    document.getElementById("optionCount").value=trCount+1;
    smsTextEventProc();
    $(".optionContent").bind('keyup keypress change', smsTextEventProc);
    
}
function removeRow(index)  
{  
 document.getElementById("voteoptions").deleteRow(index);
 
 var optionTb=document.getElementById("voteoptions");
 var trs=optionTb.getElementsByTagName("tr");
 var trCount=trs.length;
 for(var i=0;i<trCount;i++){
	 trs[i].getElementsByTagName("td")[0].innerHTML=i+1;
	 trs[i].getElementsByTagName("td")[2].getElementsByTagName('a')[0].href="javascript:removeRow("+(trCount-1)+")";
	 trs[i].getElementsByTagName("td")[1].getElementsByTagName('input')[0].id="optionContent_"+(i+1);
	 trs[i].getElementsByTagName("td")[1].getElementsByTagName('input')[0].name="optionContent_"+(i+1);
	 if(trCount<3){
		 trs[i].getElementsByTagName("td")[2].innerHTML="&nbsp;";
	 }
 }
 document.getElementById("optionCount").value=trCount;
 document.getElementById("btnAddOption").style.display='block';
 smsTextEventProc();

}
function voteCheckInput(){
	if( $('#voteTitle').attr("value") == "" || $('#voteTitle').attr("value") == '输入投票调查任务名称，仅作为查询依据'){
		alert('任务名称为空');
		return false;
	}
	if( (!votereceiverChanged || $('#voteReceiver').attr("value") == "" || $('#voteReceiver').attr("value") == "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" ) && $('#voteInputfile').val() == ""){
        alert('接收人为空');
        return false;
	}
	if( $('#voteInputfile').val() == ""){
		if( !votereceiverChanged){
			$('#voteReceiver').attr("value","");
		}
	}else if( votereceiverChanged){
		var strs= new Array(); 
		strs=$('#voteReceiver').attr("value").split(",");
		var result=true;
		for( var i=0; i<strs.length; i++){
			if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
			result = votecheckUserAddr(strs[i]);
			if(!result){
	            alert('地址不合法 [' + strs[i] + ']');
	        	return false;
			}
		}			
	}
	if( (!votereceiverChanged || $('#votecontent').attr("value") == "") && $('#voteInputfile').val() == ""){
		alert('邀请短信内容不能为空');
    	return false;
	}
	var optionCount=document.getElementById("optionCount").value;
	for(var i=1;i<=optionCount;i++){
		if($('#optionContent_'+i).val() == ""){
			 alert('选项不能为空');
			 return false;
		}
	}
	if(document.getElementById("voteTime1").checked){
		if($("#begin_time").val() == ""){
			alert('开始时间不能为空');
			 return false;
		}
		if($("#end_time").val() == ""){
			alert('结束时间不能为空');
			 return false;
		}
	}
	if(document.getElementById("voteSendAtTime").checked){
		if($("#ready_send_time").val() == ""){
			alert('定时发送时间不能为空');
			 return false;
		}
	}
	var multi_selected_number=document.getElementById("multi_selected_number").value;
	var optionCount=parseInt(document.getElementById("optionCount").value);
	if(!/^[0-9]*$/.test(multi_selected_number)){
        alert("请输入数字!");
        return false;
    }
	if(parseInt(multi_selected_number)<1 || parseInt(multi_selected_number)>optionCount){
		alert('高级设置的选项数目不能为0或者大于选项数目');
		 return false;
	}
	if(document.getElementById("need_successful_remind_0").checked){
		if(document.getElementById("need_successful_content").value==""){
			alert('投票成功短信不能为空');
			 return false;
		}
	}
	if(document.getElementById("need_not_permmit_remind_0").checked){
		if(document.getElementById("need_not_permmit_content").value==""){
			alert('不允许投票短信不能为空');
			 return false;
		}
	}
	if(document.getElementById("need_content_error_remind_0").checked){
		if(document.getElementById("need_content_error_content").value==""){
			alert('回复投票错误的提醒短信不能为空');
			 return false;
		}
	}
	$("#waitTipVote").removeClass("ui-helper-hidden");
	return true;
}
function votecheckUserAddr(userAddr){
	if(!userAddr || userAddr==""){
		return false;
	}
	var reg0 = /^1\d{10}\s*(<.*>|\s*)$/;
	var reg1 = /^.*\s*<用户组>$/;
	if( reg0.test(userAddr) || reg1.test(userAddr)){
		return true;
	}
	return false;
}
function votesendSms(responseText, statusText, xhr, $form){
	if(responseText.resultcode == "success" ){
		alert(responseText.message);
		$("#waitTipVote").addClass("ui-helper-hidden");
		var originalUrl = "./sms/vote/jsp/vote_list.jsp";
        var tempUrl = "./sms/vote/jsp/vote_list.jsp";
        var localUrl = "./voteAction/writeSms.action";
        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
        tabpanel.kill(_killId,true);
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
	}else{
		alert(responseText.message);
		$("#waitTipVote").addClass("ui-helper-hidden");
	}
}

function showMoreOption(){
	document.getElementById('voteMoreOption').style.display='block';
}
function closeVoteOption(){
	document.getElementById('voteMoreOption').style.display='none';
}