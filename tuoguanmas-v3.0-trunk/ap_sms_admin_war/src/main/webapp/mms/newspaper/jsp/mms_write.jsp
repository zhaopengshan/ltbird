<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
    </head>
	<script type="text/javascript">

	function selectFile(){
		$("#mmsReceiver").after("</br><span style='color: blue;'>已选择号码文件</span>");
	}
	var curCaixinPage = 1;
	function addGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"addToReceiver('"+treeNode.name+"');return false;\"></a>" ;//("+treeNode.counts+")
				aObj.after(editStr1);
			}
		}
		if (treeNode.level>0) return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='button lastPage' id='lastBtnCaixin_" + treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtnCaixin_" + treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtnCaixin_" + treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtnCaixin_" + treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
		aObj.after(addStr);
		var first = $("#firstBtnCaixin_"+treeNode.id);
		var prev = $("#prevBtnCaixin_"+treeNode.id);
		var next = $("#nextBtnCaixin_"+treeNode.id);
		var last = $("#lastBtnCaixin_"+treeNode.id);
		treeNode.maxPage = Math.round(treeNode.counts/treeNode.pageSize - .5) + (treeNode.counts%treeNode.pageSize == 0 ? 0:1);
		first.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goCaixinPage(treeNode, 1);
			}
		});
		last.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goCaixinPage(treeNode, treeNode.maxPage);
			}
		});
		prev.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goCaixinPage(treeNode, treeNode.page-1);
			}
		});
		next.bind("click", function(){
			if (!treeNode.isAjaxing) {
				goCaixinPage(treeNode, treeNode.page+1);
			}
		});
	 }
	 function getCaixinUrl(treeId, treeNode) {
		if(treeNode == null){
			return "${ctx}/addr/getAddr.action";
		}else{
			var param = "id="+ treeNode.id +"&page="+treeNode.page +"&rows="+treeNode.pageSize,
			aObj = $("#" + treeNode.tId + "_a");
			aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页")
			return "${ctx}/addr/getAddr.action?" + param;
		}
		
	}
	function goCaixinPage(treeNode, page) {
		treeNode.page = page;
		if (treeNode.page<1) treeNode.page = 1;
		if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
		if (curCaixinPage == treeNode.page) return;
		curCaixinPage = treeNode.page;
		var zTree = $.fn.zTree.getZTreeObj("mmsTreeDemo");
		zTree.reAsyncChildNodes(treeNode, "refresh");
	}

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			if( childNodes[i] && childNodes[i].name){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
		}
		return childNodes;
	}
	function beforeClick(treeId, treeNode, clickFlag) {
		className = (className === "dark" ? "":"dark");
		return (treeNode.click != false);
	}
	function onClick(event, treeId, treeNode, clickFlag) {
		addToReceiver(treeNode.name);
	}		

	function addToReceiver(recv){
		if( textChanged){
			$('#mmsReceiver').attr({ value: $('#mmsReceiver').attr("value")+","+recv });
		}
		else{
			$('#mmsReceiver').attr({ value: recv });
		}
		textChanged = true;
	}
	function checkUserAddr(userAddr){
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
	// 检查输入
	function checkInput(){
		if( (!textChanged || $('#mmsReceiver').attr("value") == "") && $('#addrUploadMms').val() == ""){
            alert('彩信接收人不允许为空！');
            return false;
		}
		// 检查收件人
		if( $('#addrUploadMms').val() == ""){
			if( !textChanged){
				$('#mmsReceiver').attr("value","");
			}
		}else if( textChanged){
			var strs= new Array(); 
			strs=$('#mmsReceiver').attr("value").split(",");
			var result=true;
			for( var i=0; i<strs.length; i++){
				if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
				result = checkUserAddr(strs[i]);
				if(!result){
		            alert('地址不合法 [' + strs[i] + ']');
		        	return false;
				}
			}			
		}
		return true;
	}

	function receiverEventProc(event){
        if (event.type == "focusin" && !textChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            textChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" });
        } else {
            switch(event.which) {
                case 27:
                    textChanged = false;
                    $(this).attr("value", "");
                    $(this).blur();
                    break;
                default:
                    textChanged = true;
            }
        }
	}

    /**
    *
    * 提交表单成功后处理方法
    *
    */  
  	function toMmsList(){
		var originalUrl = "./mms/newspaper/jsp/mms_news.jsp",
	        tempUrl = "./mms/newspaper/jsp/mms_news.jsp",
	        localUrl = "./mmsAction/writeMms.action",
	        _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
        tabpanel.kill(_killId);
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
	}
	
	var setting = {
		view: {
			showIcon: false,
			addDiyDom: addGroupCounts,
			fontCss : {"font-size": "14px", color: "#222222", "font-family": "微软雅黑"}
		},
		data: {
			key: {
				title:"mobile"
			},
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		},
		async: {
			enable: true,
			//url:"${ctx}/addr/getAddr.action",
			url: getCaixinUrl,
			autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		}
	};

	var zNodes =[];
	var log, className = "dark";
	
	var textChanged = false;

	$(document).ready(function(){
		$.fn.zTree.init($("#mmsTreeDemo"), setting, zNodes);
		$('#mmsReceiver').unbind("keyup focusin focusout").bind("keyup focusin focusout", receiverEventProc);

        $( "#mmsQuery" ).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "${ctx}/addr/getAddrByKey.action",
                    dataType: "json",
                    data: {
                        featureClass: "P",
                        style: "full",
                        maxRows: 12,
                        term: request.term
                    },
                    success: function( data ) {
                        response( $.map( data.addrs, function( item ) {
                            return {
                                label: item.name,
                                value: item.name
                            }
                        }));
                    }
                });
            },
            minLength: 2,
            select: function( event, ui ) {
            	addToReceiver(ui.item.value);
            }
        });
        
	});
	</script>
<body>
<div class="main_body">
<div class="contents">
<!--  <form id="mmsForm" action="${ctx }/mmsAction/send.action" method="post" enctype="multipart/form-data">-->
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><div class="left_contents">
	        <div class="table_box">
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>彩信主题：</td>
	              <td width="91%"><input id="mmsTitle" name="mmsTitle" type="text" class="input2"/></td>
	            </tr>
	            <tr>
	              <td width="12%" height="50" align="right"><span style="color:#FF0000">*</span>接收人：</td>
	              <td width="91%"><input id="mmsReceiver" name="mmsReceiver" type="text" class="input2" value="可同时发给多人，以逗号隔开，已连接三网通道的客户可向联通电信号码发送" /></td>
	            </tr>
	            <tr>
	              <td align="right" height="300px" valign="top"><span style="color:#FF0000">*</span>彩信内容：</td>
	              <td>
	              	<script type="text/javascript" >
	              		var intervalObj;
	              		function checkPic(){
						  var str = $.trim($('#mmspicUpload').val());
					      var str2 = str.substring(str.indexOf('.'),str.length);
				          if(str2.toLocaleLowerCase()!='.jpg' && str2.toLocaleLowerCase()!='.png' &&
				          	str2.toLocaleLowerCase()!='.jpeg' && str2.toLocaleLowerCase()!='.gif' && str2.toLocaleLowerCase()!='.bmp'){
				            alert('图片支持格式为*.jpg,*.png,*.jpeg,*.gif,*.bmp');
				            return false;
				          } 
						}
						function checkMusic(){
						  var str = $.trim($('#mmsmusicUpload').val());
					      var str2 = str.substring(str.indexOf('.'),str.length);
				          if(str2.toLocaleLowerCase()!='.mp3' && str2.toLocaleLowerCase()!='.mp4' ){
				            alert('音频支持格式为*.mp3,*.mp4');
				            return false;
				          } 
						}
	              		var picUploadPro = {     
		        			url:  "${ctx }/mmsAction/uploadPic.action",
		        			type: "post",
		        			dataType: "json",
		        			beforeSubmit:  checkPic,
		        		    success: function(data) {
		        		    	if(data.flag){
		        		    		$("#mmspicList").children().each(function(){
		        		    			if( $(this).hasClass("current") ){
		        		    				var curSize = parseFloat($("#mmsSize").text());
		        		    				if( $(this).attr("isize") != "" ){
		        		    					curSize = curSize - parseFloat($(this).attr("isize"));
		        		    				}
											$(this).attr("isrc",data.filePro[1]).attr("isize",data.filePro[2]/1024);
											$(this).children().first().attr("src","./mmsuploads/"+data.filePro[1]).end().end().trigger("click");
											//彩信最大100K，当前0K
											curSize = (curSize + data.filePro[2]/1024).toFixed(2);
											$("#mmsSize").text(curSize);
										}
		        		    		});
		        		    	}else{
		        		    		alert(data.message);
		        		    	}
		        		    	var picUploadMir = $("#mmspicUpload").clone(false);
		        		    		$("#mmspicUpload").remove();
		        		    		picUploadMir.val("").appendTo("#mmspicUploadForm");	
		        		    		$("#mmspicUpload").unbind("change").bind("change",function(){
										$("#mmspicUploadForm").ajaxSubmit(picUploadPro);
									});
		        			},
		        			error:function(){
		        				alert("上传文件过大,导入超时！");
		        				var picUploadMir = $("#mmspicUpload").clone(false);
		        		    		$("#mmspicUpload").remove();
		        		    		picUploadMir.val("").appendTo("#mmspicUploadForm");	
		        		    		$("#mmspicUpload").unbind("change").bind("change",function(){
										$("#mmspicUploadForm").ajaxSubmit(picUploadPro);
									});
		        			}
		        		};
		        		var musicUploadPro = {     
		        			url:  "${ctx }/mmsAction/uploadMusic.action",
		        			type: "post",
		        			dataType: "json",
		        			beforeSubmit:  checkMusic,
		        		    success: function(data) {
		        		    	if(data.flag){
		        		    		$("#mmspicList").children().each(function(){
		        		    			if( $(this).hasClass("current") ){
		        		    				var curSize = parseFloat($("#mmsSize").text());
		        		    				if( $(this).attr("msize") != "" ){
		        		    					curSize = curSize - parseFloat($(this).attr("msize"));
		        		    				}
		        		    				$("#musicSrcPanel").text("本帧包含音乐：" + data.filePro[0]).removeClass("ui-helper-hidden");
											$(this).attr("smsrc",data.filePro[0]).attr("msrc",data.filePro[1]).attr("msize",data.filePro[2]/1024);
											//彩信最大100K，当前0K
											curSize = (curSize + data.filePro[2]/1024).toFixed(2);
											$("#mmsSize").text(curSize);
										}
		        		    		});
		        		    	}else{
		        		    		alert(data.message);
		        		    	}
		        		    	var musicUploadMir = $("#mmsmusicUpload").clone(false);
		        		    		$("#mmsmusicUpload").remove();
		        		    		musicUploadMir.val("").appendTo("#mmsmusicUploadForm");	
		        		    		$("#mmsmusicUpload").unbind("change").bind("change",function(){
										$("#mmsmusicUploadForm").ajaxSubmit(musicUploadPro);
									});
		        			},
		        			error:function(){
		        				alert("上传文件过大,导入超时！");
		        				var musicUploadMir = $("#mmsmusicUpload").clone(false);
		        		    		$("#mmsmusicUpload").remove();
		        		    		musicUploadMir.val("").appendTo("#mmsmusicUploadForm");	
		        		    		$("#mmsmusicUpload").unbind("change").bind("change",function(){
										$("#mmsmusicUploadForm").ajaxSubmit(musicUploadPro);
									});
		        			}
		        		};
		        		
		        		function frameOpFunc(){
							$(this).parent().children().each(function(){
								if( $(this).hasClass("current") ){
									$(this).attr("tcontent", encodeURI($("#frameText").val()));
								}
							});
							if( $.trim( $(this).attr("smsrc")) !== "" ){
								$("#musicSrcPanel").text("本帧包含音乐：" + $(this).attr("smsrc")).removeClass("ui-helper-hidden");
							}else{
								$("#musicSrcPanel").addClass("ui-helper-hidden");
							}
							$(this).parent().children().removeClass("current").end().end().addClass("current");
							
							$("#frameText").val("");
							if( $.trim($(this).attr("tcontent")) === "" ){
								$("#frameText").val("");
							}else{
								$("#frameText").val( decodeURI($(this).attr("tcontent")));
							}
							if( $.trim( $(this).attr("isrc")) === ""){
								$("#mmsscreenPic").attr("src","./themes/mas3admin/images/mms/qmark.png");
							}else{
								$("#mmsscreenPic").attr("src","./mmsuploads/"+$(this).attr("isrc"));
							}
							var num=$(this).nextAll().length, max=$("#mmspicList").children().length;
							$("#selectIndex").text("当前  "+(max-num)+"/"+max);
						}
						function resetFrameLi(){
							var frameLi = '<li class="" isrc="" msize="0" isize="0" msrc="" smsrc="" tcontent=""><img src="./themes/mas3admin/images/mms/qmark.png"/></li>';
							$(frameLi).bind("click",frameOpFunc).appendTo("#mmspicList").trigger("click");
							//彩信最大100K，当前0K
							$("#mmsSize").text("0");
						}
	              		
						$(function(){
							$("#mmsfrontPage").unbind("click").bind("click",function(){
								//scrollHeight
								$("#mmslistPic").scrollTop($("#mmslistPic").scrollTop()-70);
							});
							$("#mmsnextPage").unbind("click").bind("click",function(){
								$("#mmslistPic").scrollTop($("#mmslistPic").scrollTop()+70);
							});
							$("#mmspicList").children().unbind("click").bind("click", frameOpFunc).first().trigger("click");
							
							$("#mmspicUpload").unbind("change").bind("change",function(){
								$("#mmspicUploadForm").ajaxSubmit(picUploadPro);
							});
							
							$("#mmsmusicUpload").unbind("change").bind("change",function(){
								$("#mmsmusicUploadForm").ajaxSubmit(musicUploadPro);
							});
							
							$("#deleteFrame").unbind("click").bind("click",function(){
								var curLi;
								$("#mmspicList").children().each(function(){
									if( $(this).hasClass("current") ){
										curLi = this;
									}
								});
								if( $(curLi).hasClass("current") ){
									var num=$(curLi).nextAll().length, 
										max=$("#mmspicList").children().length,
									    curSize = parseFloat($("#mmsSize").text()),
										isize = parseFloat($(curLi).attr("isize")),
										msize = parseFloat($(curLi).attr("msize")),
										curSize = (curSize - isize - msize).toFixed(2);
									$("#mmsSize").text(curSize>=0? curSize: 0);
									if( max == 1){
										$(curLi).remove();
										resetFrameLi();
									}else{
										if(num==0){
									        //alert("最后一个");
									        var $prevLi = $(curLi).prev();
									        $(curLi).remove();
									        $prevLi.trigger("click");
									        return;
									    }else{
									        //alert("第一个");
									        var $nextLi = $(curLi).next();
									        $(curLi).remove();
									        $nextLi.trigger("click");
									        return;
									    }
									}
								}
							});
							$("#addFrame").unbind("click").bind("click",function(){
								$("#mmspicList").children().each(function(){
									if( $(this).hasClass("current") ){
										/*var max=$("#mmspicList").children().length;
										if( max > 9 ){
											alert("帧数不能超过10帧！");
										}else{*/
										var frameLi = '<li class="" isrc="" msize="0" isize="0" msrc="" smsrc="" tcontent=""><img src="./themes/mas3admin/images/mms/qmark.png"/></li>';
										$(frameLi).bind("click", frameOpFunc).insertAfter($(this)).trigger("click");
										//}
									}
								});
							});
							
							$("#sendMms").unbind("click").bind("click",function(){
								var contentValue = $("#frameText").val(),
									imageArray = new Array(),
									musicArray = new Array(),
									contentArray = new Array(),
									hasNullFrame = false,
									mmsReceiver = $("#mmsReceiver").val(),
									mmsTitle = $("#mmsTitle").val();
									
								if( $.trim(mmsTitle) === ""){
									alert("彩信标题不允许为空！");
									return ;
								}
								if(!checkInput()){
									return ;
								}
								var curSize = parseFloat($("#mmsSize").text());
								if( curSize > 100 ){
									alert("彩信允许最大100K！");
									return ;
								}
								$("#mmspicList").children().each(function(){
									if( $(this).hasClass("current") ){
										$(this).attr("tcontent", encodeURI(contentValue));
									}
									var isrc = $.trim($(this).attr("isrc")),
										msrc = $(this).attr("msrc"),
										tcontent = $(this).attr("tcontent");
									if( isrc === "" && msrc === "" && tcontent === "" ){
										hasNullFrame = true;
										return ;
									}
									imageArray.push(isrc);
									musicArray.push(msrc);
									contentArray.push(tcontent);
								});
								if( hasNullFrame ){
									alert("彩信存在空帧，请删除无用帧！");
									return ;
								}
								$.ajax({
						            url : "${ctx }/mmsAction/sendMms.action",
						            type : 'post',
						            dataType: "json",
						            data: {
						            	mmsReceiver: mmsReceiver,
						            	mmsTitle: mmsTitle,
						            	imageArrayString: imageArray.toString(),
						            	musicArrayString: musicArray.toString(),
						            	contentArrayString: contentArray.toString(),
						            	attachmentSize: curSize
						            },
						            success : function(data) {
						                if(data.flag){
						                    alert(data.message);
						                    toMmsList();
						                } else {
						                    alert(data.message);
						                }
						            },
						            error : function(data) {
						                alert("出现系统错误，请稍后再试");
						            }
						        }); 
							});
							
							$("#mmsPicUploadLabel").unbind("click").bind("click",function(){
								$("#mmspicUpload").trigger("click");
							});
							//
							$("#mmsmusicUploadLabel").unbind("click").bind("click",function(){
								$("#mmsmusicUpload").trigger("click");
							});
							$("#preShowMms").unbind("click").bind("click",function(){
								clearInterval(intervalObj);
								$("#mmslistPic").scrollTop(0);
								var $firstLi = $("#mmspicList").children().first();
									$firstLi.trigger("click");
								intervalObj = setInterval(callbackFunc($firstLi),1000);
							});
						});
						function callbackFunc($firstLi){
							return (function(abc){
									return function(){
										var num= abc.nextAll().length;
										if(num==0){
											clearInterval(intervalObj);
											$("#mmslistPic").scrollTop(0);
											$("#mmspicList").children().first().trigger("click");
									    }else{
									        abc = abc.next();
									        $("#mmslistPic").scrollTop($("#mmslistPic").scrollTop()+75);
									        abc.trigger("click");
									    }
									};
								})($firstLi);
						}
					</script>
	              	<div class="exhibit">
						<div class="lt">
					    	<div class="canvas">
					        	<div class="btop" id="mmsfrontPage"></div>
					            <div class="midcontent">
					            	<div class="list" id="mmslistPic">
					                	<ul id="mmspicList">
					                    	<li class="" isrc="" msize="0" isize="0" msrc="" smsrc="" tcontent=""><img src="./themes/mas3admin/images/mms/qmark.png"/></li>
					                    </ul>
					                </div>
					            </div>
					            <div class="bbottom" id="mmsnextPage"></div>
					        </div>
					        <div class="menu">
					        	<div class="mbtn">
					            	<div class="ltb"><input id="deleteFrame" type="button" value="删除帧" /></div>
					                <div class="rtb"><input id="addFrame" type="button" value="增加帧" /></div>
					            </div>
					            <div class="page" id="selectIndex">当前  1/5</div>
					            <div class="detail">彩信最大100K，当前<span id="mmsSize">0</span>K</div>
					        </div>
					    </div>
					    <div class="rt">
					    	<div class="tool">
					        	<div class="ltb">图片尺寸：
					            	<select>
					            		<option>240*320</option>
					                	<!--<option>180*160</option>
					                	<option>150*140</option>-->
					            	</select>
					            </div>
					            <div class="ctb">
					            	<label for="mmspicUpload">上传图片</label>
					            	<form id="mmspicUploadForm" enctype="multipart/form-data">
					            		<input type="file" style="display: block;filter:alpha(opacity=0); -moz-opacity:0; -khtml-opacity: 0; opacity: 0; cursor:pointer;width:90px; position:absolute;left:-40px;top:0px;height:30px; " name="picture" id="mmspicUpload" />
					            	</form>
					            </div>
					            <div class="rtb">
					            	<label for="mmsmusicUpload">上传音频</label>
					            	<form id="mmsmusicUploadForm" enctype="multipart/form-data">
					            		<input type="file" style="display: block;filter:alpha(opacity=0); -moz-opacity:0; -khtml-opacity: 0; opacity: 0; cursor:pointer;width:90px;position:absolute;left:-40px;top:0px; height:30px;" name="music" id="mmsmusicUpload" />
					            	</form>
					            </div>
					        </div>
					        <div class="canvas">
					        	<div class="main">
					            	<img id="mmsscreenPic"/>
					            </div>
					            <div class="foot" id="musicSrcPanel"></div>
					        </div>
					        <div class="foot">
					        	<div class="ftitle">本帧文字：</div>
					            <div class="textarea"><textarea rows="3" cols="62" id="frameText" > </textarea></div>
					        </div>
					        <div class="menu">
					        	<div class="lbtn"><input id="preShowMms" type="button" value="预览" /></div>
					            <div class="rbtn"><input id="sendMms" type="button" value="发送" /></div>
					        </div>
					    </div>
					</div>
	              </td>
	            </tr>
	          </table>
	        </div>
	      </div>

      </td>
      <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="./themes/mas3/images/contact_locn.png" />&nbsp;&nbsp;通讯录查询</div>
        <div class="right_box">
			<div class="input_right">
			    <div class="container">
			        <div>
			            <input type="text" name="query" id="mmsQuery" style="width:200px;"/>
			        </div>
			    </div>
				<!-- <input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div> -->
			</div>
			<div class="input_right" style="font-size: 12px;cursor: pointer; position:relative;">
	        	<div id="inputFileContainerMms">
					<input type="file" id="addrUploadMms" onchange="selectFile();" class="hide_input_file" name="addrUpload"  />
				</div>
				<a href="javascript:void(0);"><img src="./themes/mas3/images/add.png" style="margin-right:5px;"/>添加号码文件</a>
			</div>
			<div class="input_right" style="font-size: 12px; ">
				<a href="javascript:void(0);" id="selfsend"><img src="./themes/mas3/images/user.png"  style="margin-right:5px;"/>发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 350px; margin-left: 0px; margin-top: -5px;">
          		<ul id="mmsTreeDemo" class="ztree" style="height: 340px; background: none; border: 0px; overflow-y:auto;padding-left:0px;"></ul>
          	</div>
          <div class="box_bottom"><img src="./themes/mas3/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
    </tr>
  </table>
<!--  </form>-->
</div>
</div>
</body>
</html>
