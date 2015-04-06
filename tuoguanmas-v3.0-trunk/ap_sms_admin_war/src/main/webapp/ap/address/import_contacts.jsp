<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<title>导入联系人</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta http-equiv="imagetoolbar" content="no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
<script type="text/javascript" >
	$(function() {
		$("#order").hide();
		$("#next").attr('disabled','disabled');
        $("#uploadContacts").bind('change',function(){
        	if(this.value){
        		$("#order").hide();
        		var str = $.trim($('#uploadContacts').val());
    	        var str2 = str.substring(str.indexOf('.'),str.length);
    	        if(str2.toLocaleLowerCase()!='.xls' && str2.toLocaleLowerCase()!='.xlsx' ){
    	            alert('上传文件必须为.xls,.xlsx为后缀的excel文件！');
    	            $("#next").attr("disabled",true);
    	            $("#order").hide();
    	        }else{
    	        	$("#next").attr("disabled",false);
    	        } 
            }
        });
        $("#next").unbind("click").bind("click",function(){
        	var ajaxPrepareStap = {     
        			url:  "./address/importContactsSteps.action",
        			type: "post",
        			dataType: "json",
        			//timeout: "30000",
        			beforeSubmit:  checkFile,
        		    success: function(data) {
        		    	if(data.flag){
        		    		var cap=data.captions;
        		    		var len=cap.length;
        		    		
        		    		$("#order select").each(function(){
        		    			$(this).children().remove();
        		    			$(this).append("<option value='-1'>  </option>");

        		    			for(var i=0;i<len;i++){
            		    			$(this).append("<option value='"+i+"'>"+cap[i]+"</option>");
            		    			if(cap[i]===$(this).prev().children().last().html()){
            		    				$(this).attr('value',i);
            		    			}
            		    		}
        		    		});
        		    		/*var j=0;
        		    		var appendhtml = "<option value='-1'>  </option>";
        		    		for(var i=0;i<len;i++){
        		    			appendhtml += "<option value='"+i+"'>"+cap[i]+"</option>";
        		    		}
        		    		$("#order select").each(function(){
        		    			$(this).children().remove();
        		    			$(this).append(appendhtml);
        		    			if(j < 2){
        		    				$(this).children("option[value='-1']").remove();
        		    				$(this).attr('value',j);
        		    			}
        		    			j++;
        		    		});*/
        		    	}else{
        		    		alert(data.resultMsg);
        		    	}
        			},
        			error:function(){
        				alert("上传文件过大,导入超时！");
        			}
        		};
        		$("#importForm").ajaxSubmit(ajaxPrepareStap);
        	$("#order").show(); 
        });
        $("#import").unbind("click").bind("click", importBtnFunc);
	});
	function importBtnFunc(){
    	var setGroupName =$("#setGroupName").val();
    	var setGroupId =$("#setGroupId").val();
    	var ajaxPrepare = {
			url:  "./address/importContacts.action",
			type: "post",
			dataType: "json",
			//timeout: "30000",
			beforeSubmit:  checkFile,
		    success: function(data) {
		    	if(data.resultMsg=='groupnull'){
		    	    $("#importGroupAddDialogLoad").load("./ap/address/importGroupAddDialog.jsp");
		    	}else if(data.resultMsg=='unselectgroup'){
		    	    alert('请在页面选择excel文件中对应的分组列名！');
		    	}else{
		    	    importContactsResult(data);
		    	}
		    	$("#loadingTip").addClass("ui-helper-hidden");
		    	$("#import").unbind("click").bind("click", importBtnFunc).attr("disabled", false);
			},
			error:function(){
				alert("上传文件过大,导入超时！");
				$("#loadingTip").addClass("ui-helper-hidden");
				$("#import").unbind("click").bind("click", importBtnFunc).attr("disabled", false);
			}
		};
		$("#import").unbind("click").attr("disabled", true);
		$("#loadingTip").removeClass("ui-helper-hidden");
		$("#importForm").ajaxSubmit(ajaxPrepare); 
    }
	//页面跳转
	function importContactsResult(data){
        var originalUrl = "./ap/address/import_contacts.jsp";
        var tempUrl = "./address/importContactsResult.action"
        		+"?resultflag="+data.flag
        		+"&messageTotal="+encodeURI(encodeURI(data.messageTotal));
        //var errortip = ;
        //var errortipJson = $.parseJSON(errortip);
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId, true);
            tabpanel.kill("tabidexportresult");
        }catch(e){
        }
        tabContentClone = $(".tabContent").clone();
        jcTabs = tabContentClone.removeClass("tabContent").load(tempUrl, {"messageContent": data.messageContent });
        tabpanel.addTab({
            id: "tabidexportresult",
            title: "通讯录导入" ,     
            html:jcTabs,     
            closable: true
        }); 
        //$(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        //$(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
       
    }
	function checkFile(){
		  var str = $.trim($('#uploadContacts').val());
	        var str2 = str.substring(str.indexOf('.'),str.length);
	         if(str2.toLocaleLowerCase()!='.xls' && str2.toLocaleLowerCase()!='.xlsx' ){
	            alert('上传文件必须为.xls,.xlsx为后缀的excel文件！');
	            return false;
	        } 
	}
	function download(url){
		//$(this).attr({src:"images/head_07.jpg"});
		//$(this)[0].setAttribute("src","images/head_07.jpg");

		window.open(url);
	}
</script>
<title>无标题文档</title>
</head>
<body>
	<div style="overflow-y: auto;overflow-x: hidden;">
	<form id="importForm" enctype="multipart/form-data">
	<input id="setGroupName" name="setGroupName" type="hidden"/>
	<input id="setGroupId" name="setGroupId" type="hidden"/>
	<div id="importGroupAddDialogLoad"></div>
		<div class="config">
			<ul>
				<li>
					<label class="rname">
						<span class="needtip">*</span>
						<span>文件格式</span>
					</label>
					<input type="radio" checked="checked"/>
					<span>excel（可支持2003及2007格式）</span>
					<a href="javaScript:download('<%=basePath%>fileDownload?fileName=./downloads/importExcelDemo.xls');">联系人导入模板下载</a>
					<span>（只需下载一次）</span>
				</li>
				<li>
					<label class="rname">
						<span class="needtip">*</span>
						<span>选择文件</span>
					</label>
					<input type="file" id="uploadContacts" name="upload" />
				</li>
				<li class="btn">
					<input id="next" type="button"value="下一步" style="cursor: pointer"/>
				</li>
			</ul>
		</div>
		<div id="order" class="config" style="overflow-y: auto;overflow-x: hidden;">
			<ul>
				<li>
					<label>
						<span>注：请将右侧的excel列名与左侧对应。如您使用系统提供的模板，则</span>
						<span >已自动对应好</span>
					</label>
				</li>
				<li>
					<label class="rname">
						<span class="needtip">*</span>
						<span>手机号码</span>
					</label>
					<select id="importMobile" name="contactOrderVO.mobile">
					
					</select>
					<label class="rname">
						<span class="needtip">*</span>
						<span>联系人姓名</span>
					</label>
					<select id="importName" name="contactOrderVO.name">

					</select>
				</li>
				<li>
					<label class="rname">
						<span>性别</span>
					</label>
					<select id="importSex" name="contactOrderVO.sex">

					</select>
					<label class="rname">
						<span>身份证号</span>
					</label>
					<select id="importIdentity" name="contactOrderVO.identity">
	
					</select>
				</li>
				<li>
					<label class="rname">
						<span>生日</span>
					</label>
					<select id="importBirthday" name="contactOrderVO.birthday">

					</select>
					<label class="rname">
					    <span class="needtip">*</span>
						<span>分组</span>
					</label>
					<select id="importGroup" name="contactOrderVO.group">

					</select>
				</li>
				<li>
					<label class="rname">
						<span>集团短号</span>
					</label>
					<select id="importVpmn" name="contactOrderVO.vpmn">

					</select>
					<label class="rname">
						<span>座机号码</span>
					</label>
					<select id="importTel" name="contactOrderVO.tel">

					</select>
				</li>
				<li>
					<label class="rname">
						<span>单位</span>
					</label>
					<select id="importCompany" name="contactOrderVO.company">

					</select>
					<label class="rname">
						<span>地址</span>
					</label>
					<select id="importAddr" name="contactOrderVO.addr">

					</select>
				</li>
				<li>
					<label class="rname">
						<span>MSN</span>
					</label>
					<select id="importMsn" name="contactOrderVO.msn">

					</select>
					<label class="rname">
						<span>QQ</span>
					</label>
					<select id="importQq" name="contactOrderVO.qq">

					</select>
				</li>
				<li>
					<label class="rname">
						<span>电子邮件</span>
					</label>
					<select id="importEmail" name="contactOrderVO.email">

					</select>
					<label class="rname">
						<span>微博</span>
					</label>
					<select id="importBlog" name="contactOrderVO.blog">

					</select>
					<label class="rname">
						<span>备注</span>
					</label>
					<select id="importDescription" name="contactOrderVO.description">

					</select>
				</li>
				<li class="btn">
					<input id="import" type="button" value="开始导入" style="cursor: pointer;"/><span id="loadingTip" class="ui-helper-hidden needtip"><img src="themes/mas3admin/images/helper/loading.gif"/>正在执行导入联系人操作，请耐心等待。请勿重复执行此操作！</span>
				</li>
			</ul>
            </div>
            </form>
       </div>
</body>
</html>
