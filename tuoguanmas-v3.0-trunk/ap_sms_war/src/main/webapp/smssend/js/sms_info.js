	/**
	 * 字符串格式化eg:
	 * var a = "I Love {0}, and You Love {1}";
	 * 	   alert(String.format(a, "You","Me"));
	 * @returns {} 
	 */
	String.format = function() {
	    if( arguments.length == 0 )
	        return null;
	
	    var str = arguments[0]; 
	    for(var i=1;i<arguments.length;i++) {
	        var re = new RegExp('\\{' + (i-1) + '\\}','gm');
	        str = str.replace(re, arguments[i]);
	    }
	    return str;
	}
	/**
	 *
	 * @param {} url:action url
	 * @param {} message: confim show message
	 * @returns {} 
	 */
	function confirmAjaxFunc(url,showMessage){
		var ids = smsGrid.getSelectedItemIds();
		if( ids.length > 0 ){
			//var showMessage = "是否取消发送选中的"+ids.length+"条短信？";
			showMessage = String.format(showMessage,ids.length);
			$.messager.confirm('提示', showMessage, function(r){//如选择是，则执行此匿名方法
				if (r){
	            	$.ajax({
	                    url : url,
	                    type : 'post',
	                    dataType: "json",
	                    data: {
	                    	smsIds: ids.toString()
	                    },
	                    success : function(data) {
	                    	$.messager.show({
                                title: '用户操作',
                                msg:data.message,
                                timeout:5000
                            });
	                        if(data.resultcode == "success"){
	                            smsGrid.refresh();
	                        }
	                    },
	                    error : function() {
	                        $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
	                    }
	                }); 
				}
			});
		}else{//如没有选中行，则提示此信息
			$.messager.alert("系统提示","请先选择需要操作的项！","info");
		}
	}
	/**
	 * 收藏通用方法
	 * @param {} url
	 * @returns {} 
	 */
	function collectSmsFunc(url){
		var ids = smsGrid.getSelectedItemIds();
		if( ids.length > 0 ){
        	$.ajax({
                url : url,
                type : 'post',
                dataType: "json",
                data: {
                	smsIds: ids.toString()
                },
                success : function(data) {
                	$.messager.show({
                        title: '用户操作',
                        msg:data.message,
                        timeout:5000
                    });
                },
                error : function() {
                    $.messager.alert("系统提示","出现系统错误，请稍后再试","warning");
                }
            }); 
		}else{
			$.messager.alert("系统提示","请先选择需要操作的项！","info");
		}
	}
	/**
	 * 编辑转发通用方法
	 * @param {} url
	 * @returns {} 
	 */
	function editSmsFunc(url){
		var rows = smsGrid.getSelectedItemIds();
		if( rows.length == 1 ){ 
			window.parent.parent.addTabs('itemx','itemxs'+rows,'短信互动','编辑转发',url+'?selectedId='+rows);
		}else if( rows.length > 1){
			$.messager.alert("系统提示","只能选择一项进行操作！","info");
		}else{
			$.messager.alert("系统提示","请先选择需要操作的项！","info");
		}
	}
	