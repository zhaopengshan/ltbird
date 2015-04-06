<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function(){
		initParas();
		$("#reset").unbind("click").click(
			function(){
               // $("#uploadSettings").val("");
				$("#sms_interaction_validityPrefix").val("25");
				$("#sms_interaction_validitySuffix option[value='60']").attr("selected",true);
				$("#sms_meeting_notice_validityPrefix").val("25");
				$("#sms_meeting_notice_validitySuffix option[value='60']").attr("selected",true);
				$("#sms_schedule_remind_validityPrefix").val("25");
				$("#sms_schedule_remind_validitySuffix option[value='60']").attr("selected",true);
				$("#sms_vote_research_validityPrefix").val("25");
				$("#sms_vote_research_validitySuffix option[value='60']").attr("selected",true);
				$("#sms_answer_validityPrefix").val("25");
				$("#sms_answer_validitySuffix option[value='60']").attr("selected",true);
				$("#sms_lucky_draw_validityPrefix").val("25");
				$("#sms_lucky_draw_validitySuffix option[value='60']").attr("selected",true);
				
				$("input[name='sms_interaction_priority'][value='3']").attr("checked",true);
				$("input[name='sms_meetint_notice_priority'][value='3']").attr("checked",true);
				$("input[name='sms_schedule_remind_priority'][value='3']").attr("checked",true);
				$("input[name='sms_vote_research_priority'][value='3']").attr("checked",true);
				$("input[name='sms_answer_priority'][value='3']").attr("checked",true);
				$("input[name='sms_lucky_draw_priority'][value='3']").attr("checked",true); 
				
				$("#message_length").val("325");
				$("input[name='status_report_is_need'][value='y']").attr("checked",true);
				
				$("#sms_send_time_intervalPrefix option[value='08']").attr("selected",true);
				$("#sms_send_time_intervalSuffix option[value='20']").attr("selected",true);
				$("input[name='authentication'][value='page']").attr("checked",true);
			}
		);
		
		$("#confirm").unbind("click").click(
			function(){
				submitLogo();
			}
		);
	});
        function submitParas(){
        	var testUrl="./systemSettingsAction/updateParas.action";
			var msg = $("#paraList").serialize();
			$.ajax({
				type: "POST",
				url: testUrl,
                async: false,
				beforeSend:validateParas,
				data: msg,
                dataType:  "json",
				success: function(data){
					var entityMap=data;
                    alert(entityMap.resultMsg);
				}
			}); 
        }
        function submitLogo(){
        	var homePageLogo = {     
					url:  "./systemSettingsAction/updateLogo.action",
					type: "post",
					//timeout: "30000",
					dataType: "json",
		        	beforeSubmit: checkFileType,
		        	 success: function(data) {
						var entityMap=data;
						if(entityMap.flag){
							//首页LOGO可以不上传，但上传一定要传成功
							if(entityMap.oldUploadName!=""){
								$("#oldUploadName").val(entityMap.oldUploadName);
							}
							submitParas();
						}else{
							alert(entityMap.resultMsg);
						}
					},
	    			error:function(){
	    				alert("上传文件过大,导入超时！");
	    			} 
			};
		    $("#homePageLogo").ajaxSubmit(homePageLogo); 
        }
	function initParas(){
		if($("#sms_interaction_validityPrefix").val()==null||$("#sms_interaction_validityPrefix").val()==""){
			$("#sms_interaction_validityPrefix").val("25");
		}
		if(""=="${sms_interaction_validitySuffix}"){
			$("#sms_interaction_validitySuffix option[value='60']").attr("selected",true);
		}
		if($("#sms_meeting_notice_validityPrefix").val()==null||$("#sms_meeting_notice_validityPrefix").val()==""){
			$("#sms_meeting_notice_validityPrefix").val("25");
		}
		if(""=="${sms_meeting_notice_validitySuffix}"){
			$("#sms_meeting_notice_validitySuffix option[value='60']").attr("selected",true);
		}
		if($("#sms_schedule_remind_validityPrefix").val()==null||$("#sms_schedule_remind_validityPrefix").val()==""){
			$("#sms_schedule_remind_validityPrefix").val("25");
		}
		if(""=="${sms_schedule_remind_validitySuffix}"){
			$("#sms_schedule_remind_validitySuffix option[value='60']").attr("selected",true);
		}
		if($("#sms_vote_research_validityPrefix").val()==null||$("#sms_vote_research_validityPrefix").val()==""){
			$("#sms_vote_research_validityPrefix").val("25");
		}
		if(""=="${sms_vote_research_validitySuffix}"){
			$("#sms_vote_research_validitySuffix option[value='60']").attr("selected",true);
		}
		if($("#sms_answer_validityPrefix").val()==null||$("#sms_answer_validityPrefix").val()==""){
			$("#sms_answer_validityPrefix").val("25");
		}
		if(""=="${sms_answer_validitySuffix}"){
			$("#sms_answer_validitySuffix option[value='60']").attr("selected",true);
		}
		if($("#sms_lucky_draw_validityPrefix").val()==null||$("#sms_lucky_draw_validityPrefix").val()==""){
			$("#sms_lucky_draw_validityPrefix").val("25");
		}
		if(""=="${sms_lucky_draw_validitySuffix}"){
			$("#sms_lucky_draw_validitySuffix option[value='60']").attr("selected",true);
		}
		
		if($("input[name='sms_interaction_priority']:checked").length==0){
			$("input[name='sms_interaction_priority'][value='3']").attr("checked",true);
		}
		if($("input[name='sms_meetint_notice_priority']:checked").length==0){
			$("input[name='sms_meetint_notice_priority'][value='3']").attr("checked",true);
		}
		if($("input[name='sms_schedule_remind_priority']:checked").length==0){
			$("input[name='sms_schedule_remind_priority'][value='3']").attr("checked",true);
		}
		if($("input[name='sms_vote_research_priority']:checked").length==0){
			$("input[name='sms_vote_research_priority'][value='3']").attr("checked",true);
		}
		if($("input[name='sms_answer_priority']:checked").length==0){
			$("input[name='sms_answer_priority'][value='3']").attr("checked",true);
		}
		if($("input[name='sms_lucky_draw_priority']:checked").length==0){
			$("input[name='sms_lucky_draw_priority'][value='3']").attr("checked",true);
		} 
                
		if($("#message_length").val()==null||$("#message_length").val()==""){
			$("#message_length").val("325");
		}
		if($("input[name='status_report_is_need']:checked").length==0){
			$("input[name='status_report_is_need'][value='y']").attr("checked",true);
		}
		if(""=="${sms_send_time_intervalPrefix}"){
			$("#sms_send_time_intervalPrefix option[value='08']").attr("selected",true);
		}else{
			$("#sms_send_time_intervalPrefix option[value='"+"${sms_send_time_intervalPrefix}"+"']").attr("selected",true);
		}
		if(""=="${sms_send_time_intervalSuffix}"){
			$("#sms_send_time_intervalSuffix option[value='20']").attr("selected",true);
		}else{
			$("#sms_send_time_intervalSuffix option[value='"+"${sms_send_time_intervalSuffix}"+"']").attr("selected",true);
		}
		if("" == "${authentication}"){
			$("input[name='authentication'][value='page']").attr("checked",true);
		}else{
			$("input[name='authentication'][value='"+"${authentication}"+"']").attr("checked",true);
		}
	}
	function validateParas(){
		 var obj=new Object();
                if(!checkInputAndSelect("sms_interaction_validity",obj)){
			alert("短信互动"+obj.msg);
			return false;
		}
		if(!checkInputAndSelect("sms_meeting_notice_validity",obj)){
			alert("会议通知"+obj.msg);
			return false;
		}
		if(!checkInputAndSelect("sms_schedule_remind_validity",obj)){
			alert("日程提醒"+obj.msg);
			return false;
		}
		if(!checkInputAndSelect("sms_vote_research_validity",obj)){
			alert("投票调查"+obj.msg);
			return false;
		}
		if(!checkInputAndSelect("sms_answer_validity",obj)){
			alert("短信答题"+obj.msg);
			return false;
		}
		if(!checkInputAndSelect("sms_lucky_draw_validity",obj)){
			alert("短信抽奖"+obj.msg);
			return false;
		}
		var temp=$.trim($("#message_length").val());
        if(temp != null && temp != ""){
        	var reg=/^[0-9]{1,3}$/;
            if(!reg.test(temp)){
            	alert("消息长度必须由数字组成，且最大为325");
                return false;
            }
            try{
				temp=parseInt(temp);	
				if(temp>=326){
					alert("输入的数字必须小于等于325");
					return false;
				}
           	}catch(e){
				alert("请输入数字");
				return false;
            }
      	}   
        var smsFromTime=$("#sms_send_time_intervalPrefix").val();
        var smsToTime=$("#sms_send_time_intervalSuffix").val();
        try{
        	smsFromTime=parseInt(smsFromTime);	
        	smsToTime=parseInt(smsToTime);
			if(smsFromTime >= smsToTime){
				alert("允许发送时段,开始时间要早于结束时间");
				return false;
			}
       	}catch(e){
			alert("允许发送时段,开始时间要早于结束时间");
			return false;
        }
	}
	function checkInputAndSelect(baseInfo,obj){
		var prefix=$.trim($("#"+baseInfo+"Prefix").val());
        var suffix=parseInt($.trim($("#"+baseInfo+"Suffix").val()));
		if(prefix==""){
        	return true;
        }
        var reg=/^[0-9]{1,2}$/;
        if(!reg.test(prefix)){
        	obj.msg="只能填写两位数字";
            return false;
        }
        try{
        	prefix=parseInt(prefix);	
			if(1==suffix){
            	if(prefix<10){
                	obj.msg="最短有效期不能低于10分钟";
                    return false;
                }
			}else if(60==suffix){
                    if(prefix<1){
                    	obj.msg="最短有效期不能低于10分钟";
                        return false;
                    }
            }else if(1440==suffix){
                    if(prefix>31){
                        obj.msg="最长有效期不能高于31天";
                        return false;
                    }else if(prefix<1){
                        obj.msg="最短有效期不能低于10分钟";
                        return false;
                    }   
            }
            return true;
       	}catch(e){
            obj.msg="输入数据格式有误";
			return false;
		}
	} 
	
	function checkFileType() {
    	/* if ($.trim($('#uploadSettings').val()) == '') {
        	$.messager.alert('系统提示','提示：请选择上传文件！', 'warning');
        	return false;
        } */
        var str = $.trim($('#uploadSettings').val());
        var str2 = str.substring(str.indexOf('.'),str.length);
        if(str2.toLocaleLowerCase()!='' && str2.toLocaleLowerCase()!='.jpg' && str2.toLocaleLowerCase()!='.png' 
        	&& str2.toLocaleLowerCase()!='.gif'&& str2.toLocaleLowerCase()!='.jpeg'){
            alert("上传文件必须为.jpg,.png,.gif或.jpeg为后缀的图片文件！");
            return false;
        } 
    }	
</script>
<title>无标题文档</title>
</head>
<body>
<div>
<div >
	<form id="homePageLogo" enctype="multipart/form-data">
	<ul <s:if  test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('tuoguan')!='true'">style="display:none"</s:if>>
		<li>
			<label class="rnameex">
				首页LOGO：
			</label>
    			<input id="oldUploadName" type="hidden" name="oldUploadName" value="${home_page_logo }"/>
    			<input id="uploadSettings" name="upload" type="file" onchange="checkFileType();" />
    			&nbsp;
    			<span style="color:red;" >（建议：图片大小为496*86、图片背景透明。）</span>
    	</li>
    </ul>
    </form>
    <form id="paraList">
    <ul class="gridwrapper">
    <!--<li>
    	<label class="rnameex">
    		是否开启来电弹屏功能：
    	</label>
    	<input name="" type="radio" value="yes" checked="checked"/>是
    	<input name="" type="radio" value="no"/>否
    </li>-->
    <li>
    	<label class="rnameex">
    		允许发送时段：
    	</label>
       	<select id="sms_send_time_intervalPrefix" name="sms_send_time_intervalPrefix" style="width:60px;height: 22px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
      </select>
		至
      <select id="sms_send_time_intervalSuffix" name="sms_send_time_intervalSuffix" style="width:60px;height: 22px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
       </select>
        	为避免客户休息时间收到短信，此时间段外短信会自动推迟到第二天发送
    </li>
	<li style="display:none">
	  <ul class="rolelabel" style="overflow-y: auto;overflow-x: hidden;">
    	<li >
    		<label class="rnameex">
    			<span class="needtip">*</span>
    			短信有效期
    		</label>
    	</li>
    </ul>
	<ul class="unselectrolemargin">
    	<li>
    			短信互动
      		<input id="sms_interaction_validityPrefix" name="sms_interaction_validityPrefix" type="text" maxlength="2" value="${sms_interaction_validityPrefix }" style="width:50px;"/>
       		<select id="sms_interaction_validitySuffix" name="sms_interaction_validitySuffix" style="width:60px;height: 22px;">
      			<option value="1"${sms_interaction_validitySuffix=='1'?'selected':''}>分钟</option>
    			<option value="60"${sms_interaction_validitySuffix=='60'?'selected':''}>小时</option>
      			<option value="1440"${sms_interaction_validitySuffix=='1440'?'selected':''}>天</option>
   	 		</select>
   	 	</li>
   	 	<li>
    			会议通知
      		<input id="sms_meeting_notice_validityPrefix" name="sms_meeting_notice_validityPrefix" type="text" maxlength="2" value="${sms_meeting_notice_validityPrefix }" style="width:50px;"/>
       		<select id="sms_meeting_notice_validitySuffix" name="sms_meeting_notice_validitySuffix" style="width:60px;height: 22px;">
      			<option value="1"${sms_meeting_notice_validitySuffix=='1'?'selected':''}>分钟</option>
      			<option value="60"${sms_meeting_notice_validitySuffix=='60'?'selected':''}>小时</option>
      			<option value="1440"${sms_meeting_notice_validitySuffix=='1440'?'selected':''}>天</option>
    		</select>
    	</li>
		<li>
	    		日程提醒
      		<input id="sms_schedule_remind_validityPrefix" name="sms_schedule_remind_validityPrefix" type="text" maxlength="2" value="${sms_schedule_remind_validityPrefix }" style="width:50px;"/>
       		<select id="sms_schedule_remind_validitySuffix" name="sms_schedule_remind_validitySuffix" style="width:60px;height: 22px;">
      			<option value="1"${sms_schedule_remind_validitySuffix=='1'?'selected':''}>分钟</option>
      			<option value="60"${sms_schedule_remind_validitySuffix=='60'?'selected':''}>小时</option>
      			<option value="1440"${sms_schedule_remind_validitySuffix=='1440'?'selected':''}>天</option>
    		</select>
    	</li>
  		<li>
    			投票调查
      		<input id="sms_vote_research_validityPrefix" name="sms_vote_research_validityPrefix" type="text" maxlength="2" value="${sms_vote_research_validityPrefix }" style="width:50px;"/>
    		<select id="sms_vote_research_validitySuffix" name="sms_vote_research_validitySuffix" style="width:60px;height: 22px;">
      			<option value="1"${sms_vote_research_validitySuffix=='1'?'selected':''}>分钟</option>
      			<option value="60"${sms_vote_research_validitySuffix=='60'?'selected':''}>小时</option>
      			<option value="1440"${sms_vote_research_validitySuffix=='1440'?'selected':''}>天</option>
    		</select>
    	</li>
    	<li>
    			短信答题
      		<input id="sms_answer_validityPrefix" name="sms_answer_validityPrefix" type="text" maxlength="2" value="${sms_answer_validityPrefix }" style="width:50px;"/>
       		<select id="sms_answer_validitySuffix" name="sms_answer_validitySuffix" style="width:60px;height: 22px;">
      			<option value="1"${sms_answer_validitySuffix=='1'?'selected':''}>分钟</option>
      			<option value="60"${sms_answer_validitySuffix=='60'?'selected':''}>小时</option>
      			<option value="1440"${sms_answer_validitySuffix=='1440'?'selected':''}>天</option>
    		</select>
    	</li>
   		<li>
   				短信抽奖
      		<input id="sms_lucky_draw_validityPrefix" name="sms_lucky_draw_validityPrefix" type="text" maxlength="2" value="${sms_lucky_draw_validityPrefix }" style="width:50px;"/>
       		<select id="sms_lucky_draw_validitySuffix" name="sms_lucky_draw_validitySuffix" style="width:60px;height: 22px;">
      			<option value="1"${sms_lucky_draw_validitySuffix=='1'?'selected':''}>分钟</option>
      			<option value="60"${sms_lucky_draw_validitySuffix=='60'?'selected':''}>小时</option>
      			<option value="1440"${sms_lucky_draw_validitySuffix=='1440'?'selected':''}>天</option>
    		</select>
    	</li>
	</ul>
	</li>
	<li  style="display:none">
	<ul class="rolelabel">
   		<li>
   			<label class="rnameex">
   				<span class="needtip">*</span>
   				消息优先级：
   			</label>
   		</li>
	</ul>
	<ul class="unselectrolemargin">
   		<li>
   				短信互动
      		<input name="sms_interaction_priority" type="radio" value="1" ${sms_interaction_priority=='1'?'checked':''}/>紧急 
      		<input name="sms_interaction_priority" type="radio" value="2" ${sms_interaction_priority=='2'?'checked':''}/>重要 
      		<input name="sms_interaction_priority" type="radio" value="3" ${sms_interaction_priority=='3'?'checked':''}/>次重要 
      		<input name="sms_interaction_priority" type="radio" value="4" ${sms_interaction_priority=='4'?'checked':''}/>普通
      	</li>
  		<li>
    			会议通知
      		<input name="sms_meetint_notice_priority" type="radio" value="1" ${sms_meetint_notice_priority=='1'?'checked':''}/>紧急
       		<input name="sms_meetint_notice_priority" type="radio" value="2" ${sms_meetint_notice_priority=='2'?'checked':''}/>重要 
       		<input name="sms_meetint_notice_priority" type="radio" value="3" ${sms_meetint_notice_priority=='3'?'checked':''}/>次重要
        	<input name="sms_meetint_notice_priority" type="radio" value="4" ${sms_meetint_notice_priority=='4'?'checked':''}/>普通
  		</li>
  		<li>
    			日程提醒
      		<input name="sms_schedule_remind_priority" type="radio" value="1" ${sms_schedule_remind_priority=='1'?'checked':''}/>紧急
       		<input name="sms_schedule_remind_priority" type="radio" value="2" ${sms_schedule_remind_priority=='2'?'checked':''}/>重要 
       		<input name="sms_schedule_remind_priority" type="radio" value="3" ${sms_schedule_remind_priority=='3'?'checked':''}/>次重要
        	<input name="sms_schedule_remind_priority" type="radio" value="4" ${sms_schedule_remind_priority=='4'?'checked':''}/>普通
  		</li>
  		<li>
    			投票调查
      		<input name="sms_vote_research_priority" type="radio" value="1" ${sms_vote_research_priority=='1'?'checked':''}/>紧急 
      		<input name="sms_vote_research_priority" type="radio" value="2" ${sms_vote_research_priority=='2'?'checked':''}/>重要 
      		<input name="sms_vote_research_priority" type="radio" value="3" ${sms_vote_research_priority=='3'?'checked':''}/>次重要 
      		<input name="sms_vote_research_priority" type="radio" value="4" ${sms_vote_research_priority=='4'?'checked':''}/>普通
  		</li>
  		<li>
    			短信答题
      		<input name="sms_answer_priority" type="radio" value="1" ${sms_answer_priority=='1'?'checked':''}/>紧急
       		<input name="sms_answer_priority" type="radio" value="2" ${sms_answer_priority=='2'?'checked':''}/>重要 
       		<input name="sms_answer_priority" type="radio" value="3" ${sms_answer_priority=='3'?'checked':''}/>次重要
        	<input name="sms_answer_priority" type="radio" value="4" ${sms_answer_priority=='4'?'checked':''}/>普通
  		</li>
  		<li>
    			短信抽奖
      		<input name="sms_lucky_draw_priority" type="radio" value="1" ${sms_lucky_draw_priority=='1'?'checked':''}/>紧急 
      		<input name="sms_lucky_draw_priority" type="radio" value="2" ${sms_lucky_draw_priority=='2'?'checked':''}/>重要
       		<input name="sms_lucky_draw_priority" type="radio" value="3" ${sms_lucky_draw_priority=='3'?'checked':''}/>次重要
        	<input name="sms_lucky_draw_priority" type="radio" value="4" ${sms_lucky_draw_priority=='4'?'checked':''}/>普通
  		</li>
   	</ul>
   	</li>
    <li style="display:none">
    		<label class="rnameex">
    			消息长度设定：
    		</label>
		 	<span>最长</span>
     		<input id="message_length" name="message_length" type="text" maxlength="3" value="${message_length }" style="width:50px;"/>
      		<span>字</span>
      	</li>
  		<li style="display:none">
  			<label class="rnameex">
  				是否需要状态报告：
  			</label>
    		<input name="status_report_is_need" type="radio" value="y" ${status_report_is_need=='y'?'checked':''} />是
    		<input name="status_report_is_need" type="radio" value="n" ${status_report_is_need=='n'?'checked':''} />否
    	</li>
    	<li>
    		<label class="rnameex">
    			系统鉴权方式：
    		</label>
    		<input name="authentication" type="radio" value="page"/>页面
    		<input name="authentication" type="radio" value="webservice"/>webservice接口
    	</li>
    	
    	<li style="display:none;">
    		<label class="rnameex">
    			学生刷卡有效时段：
    		</label>
    		周一到周五上午<select style="width:60px;height: 22px; margin:0 2px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07" selected="selected">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
      </select>
		至
      <select style="width:60px;height: 22px; margin:0 2px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09"  selected="selected">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
       </select> 及下午 <select style="width:60px;height: 22px; margin:0 2px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15" selected="selected">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
      </select>
		至
      <select style="width:60px;height: 22px; margin:0 2px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09" >9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17" selected="selected">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
       </select><br/>
       	<p style="margin-left:125px;padding:0px;margin-top:0px;">为避免学生随意刷卡，在此时间段内学生刷卡会触发向亲情号发送短信功能，其他时间段不触发</p>
       
    	</li>
    	<li style="display:none;">
    		<label class="rnameex">
    			教职工上下班时间：
    		</label>
    		周一到周五上午	<select style="width:60px;height: 22px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09" selected="selected">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
      </select>
		至下午
      <select style="width:60px;height: 22px;">
        	<option value="00">0:00</option>
        	<option value="01">1:00</option>
        	<option value="02">2:00</option>
        	<option value="03">3:00</option>
        	<option value="04">4:00</option>
        	<option value="05">5:00</option>
        	<option value="06">6:00</option>
        	<option value="07">7:00</option>
        	<option value="08">8:00</option>
        	<option value="09">9:00</option>
        	<option value="10">10:00</option>
        	<option value="11">11:00</option>
        	<option value="12">12:00</option>
        	<option value="13">13:00</option>
        	<option value="14">14:00</option>
        	<option value="15">15:00</option>
        	<option value="16">16:00</option>
        	<option value="17"  selected="selected">17:00</option>
        	<option value="18">18:00</option>
        	<option value="19">19:00</option>
        	<option value="20">20:00</option>
        	<option value="21">21:00</option>
        	<option value="22">22:00</option>
        	<option value="23">23:00</option>
       </select>
    	</li>
        <li class="btn" style="margin-top:20px; padding-left:50px;">
        	<a href="javaScript:void(0);" id="confirm">确定</a>
			<a href="javaScript:void(0);" id="reset">重置</a>
		</li>
	</ul>
	</form>
</div>
</div>
</body>
</html>
