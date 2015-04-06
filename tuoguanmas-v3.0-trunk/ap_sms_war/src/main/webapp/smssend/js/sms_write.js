

	function addGroupCounts(treeId, treeNode) {
		if(treeNode.isParent  ==true){
			if(!isNaN(treeNode.id)){
				var aObj = jQuery("#" + treeNode.tId + "_a");
				var editStr1 = "<a  onclick=\"addToReceiver('"+treeNode.name+"');return false;\">("+treeNode.counts+")</a>" ;
				aObj.after(editStr1);
			}
		}
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
		if( treeNode.isParent){
			return;
		}
		addToReceiver(treeNode.name);
	}		

	function addToReceiver(recv){
		if( textChanged){
			$('#receiver').attr({ value: $('#receiver').attr("value")+","+recv });
		}
		else{
			$('#receiver').attr({ value: recv });
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
		if( (!textChanged || $('#receiver').attr("value") == "") && $('#addrUpload').val() == ""){
        	$.messager.show({
                title: '用户操作',
                msg: '接收人为空',
                timeout:5000
            });
            return false;
		}
		// 检查收件人
		if( $('#addrUpload').val() == ""){
			if( !textChanged){
				$('#receiver').attr("value","");
			}
		}else if( textChanged){
			var strs= new Array(); 
			strs=$('#receiver').attr("value").split(",");
			var result=true;
			for( var i=0; i<strs.length; i++){
				if(strs[i]!="") strs[i]=strs[i].replace(/(^\s*)|(\s*$)/g, "");
				result = checkUserAddr(strs[i]);
				if(!result){
		        	$.messager.show({
		                title: '用户操作',
		                msg: '地址不合法 [' + strs[i] + ']',
		                timeout:5000
		            });
		        	return false;
				}
			}			
		}
		if( $('#smsText').attr("value") == "" || $('#smsText').attr("value").trim() == ""){
        	$.messager.show({
                title: '用户操作',
                msg: '短信内容为空',
                timeout:5000
            });
            return false;
		}
		if( $("#sendAtTime").attr("checked") == 'checked'){
			if( $("#sendTime").attr("value") == ""){
	        	$.messager.show({
                    title: '用户操作',
                    msg: '定时时间为空',
                    timeout:5000
                });
                return false;
			}
		}
		return true;
	}

	function receiverEventProc(event){
        if (event.type == "focusin" && !textChanged) {
            $(this).attr({ value: "" });
        } else if (event.type == "focusout" && $(this).attr("value") == "") {
            textChanged = false;
            $(this).attr({ value: "可同时发给多人，以逗号”，“隔开，支持向移动、联通、电信用户发送" });
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
	function saveEventProc(event){
        if( $("#smsText").attr("value") == "" || $("#smsText").attr("value").trim() == ""){
        	$.messager.show({
                title: '用户操作',
                msg: '短信内容为空',
                timeout:5000
            });
            return;
        }
       	$.ajax({
            url : event.data.url,
            type : 'post',
            dataType: "json",
            data: {
            	smsText: $("#smsText").attr("value"),
            	title: $("#title").attr("value")
            },
            success : function(data) {
                if(data.resultcode == "success"){
                	$.messager.show({
                       title: '用户操作',
                       msg:data.message,
                        timeout:5000
                    });
                } else {
                    $.messager.show({
                        title: '用户操作',
                        msg:data.message,
                        timeout:5000
                    });
                }
            },
            error : function() {
                $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
            }
        }); 
	}

	function smsTextEventProc(){
		var extLen = $("#replyText").val().length;
		var extLen2 = $("#entSign").val().length;
		var len =$(this).val().length + extLen + extLen2;
        var remain = 350 - len;
        var cnt = Math.ceil(len/70);  
        if( remain<0 ){
        	$("#smsTips").html("已经超过<b style='color:red'>"+Math.abs(remain)+"</b>字");
       	}else{
            $("#smsTips").html("&nbsp;&nbsp;&nbsp;&nbsp;还可以输入<strong>"+Math.abs(remain)+"</strong>字"+
            		 ",本次将以<strong>"+cnt+"</strong>条计费&nbsp;短信共<strong>"+ len+ "</strong>字(含企业签名)," + "分<b>"+cnt+"</b>条发送");
        }
	}
	
	function setRemainNumber(event){
		var selTunnelId=$("#"+event.data.sel).val();
		$.ajax({
            url : event.data.url,
            type : 'post',
            dataType: "json",
            data: {
				selTunnelId: selTunnelId
            },
            success : function(data) {
                if(data && data.resultcode == "success"){
                	$("#"+event.data.tip).text(data.remain);
                }
                else{
                	$.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
                }
            },
            error : function() {
                $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
            }
        }); 
	}