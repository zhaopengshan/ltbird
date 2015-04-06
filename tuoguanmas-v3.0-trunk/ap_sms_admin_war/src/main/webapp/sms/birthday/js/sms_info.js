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
	function confirmAjaxFunc(smsGrid,url,showMessage){
		var ids = smsGrid.getSelectedItemIds();
		if( ids.length > 0 ){
			//var showMessage = "是否取消发送选中的"+ids.length+"条短信？";
			showMessage = String.format(showMessage,ids.length);
			if(confirm(showMessage)){
	            	$.ajax({
	                    url : url,
	                    type : 'post',
	                    dataType: "json",
	                    data: {
	                    	smsIds: ids.toString()
	                    },
	                    success : function(data) {
							alert(data.message);
	                        if(data.resultcode == "success"){
	                            smsGrid.refresh();
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
	/**
	 * 收藏通用方法
	 * @param {} url
	 * @returns {} 
	 */
	function collectSmsFunc(smsGrid,url){
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
					alert(data.message);
                },
                error : function() {
                    alert("出现系统错误，请稍后再试");
                }
            }); 
		}else{
			alert("请先选择需要操作的项！");
		}
	}
	

	/**
	 * 编辑转发通用方法
	 * @param {} url
	 * @returns {} 
	 */
	function editSmsFunc(smsGrid,url){
		var rows = smsGrid.getSelectedItemIds();
		if( rows.length == 1 ){ 
			//window.parent.parent.addTabs('itemx','itemxs'+rows,'短信互动','编辑转发',url+'?selectedId='+rows);
		    var originalUrl = "./birthday/writesms.action";
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
	
	/**
	 * 验证时间格式是否正确
	 * @param {} value
	 * @returns {} 
	 */
	function dateISO(value) {
		return ($.trim(value)=="")||/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test($.trim(value));
	}
	/**
	 * 验证
	 * 孙亚东 2012.12.27
	 */
	ValidateForm = function(formId){
		this.formId = formId;
		this.outputTarId = this.formId + "_output";
	};
	ValidateForm.prototype={
		init: function(){
			//归一化
			if(typeof this.formId == "string") {if(this.formId.substr(0,1) !== "#") { this.formId = "#"+this.formId;}
			}else { this.formId = "#"+ $(this.formId).attr("id");}
			if(typeof this.outputTarId == "string") {
	  			if(this.outputTarId.substr(0,1) =="#") { 
	  				this.outputTarId = this.outputTarId.substring(1);
	  			}
	  		}else { 
	  			this.outputTarId = $(this.outputTarId).attr("id");
	  		}
			outputDiv = '<font color="red" ><div id="'+ this.outputTarId +'" class="ui-helper-hidden"></div></font>'
			$(this.formId).prepend(outputDiv)
		},
		//ValidateForm.validate("#inputid","#outId",[1,2],["required","validateString"]);
		validate: function(formTar,exportTar,param,funcName){
			var self = this,
				formTarId = formTar,
				exportId=exportTar,
				value="",
				reResult=false,
				functionName = funcName;
			//归一化
			if(typeof formTarId == "string") {if(formTarId.substr(0,1) !== "#") { formTarId = "#"+formTarId;}
			}else { formTarId = "#"+ $(formTarId).attr("id");}
			//归一化
			if(typeof exportId == "string") {if(exportId.substr(0,1) !== "#") { exportId = "#"+exportId;}
			}else { exportId = "#"+ $(exportId).attr("id");}
			
			value = $(formTarId).val();
			
			if( typeof(functionName)=="object"&&functionName.push ){
				for(var i = 0; i < functionName.length; i++){
					var func = functionName[i],
						funcString = "self." + func+"(value,param)";
						reResult = eval(funcString);
					if( typeof reResult !== "boolean" ){
						$(exportId).html( $(exportId).html()+"<br/>" + reResult);
						return false;
					}
				}
			}else{
				var funcString = "self." + functionName+"(value,param)";
				reResult = eval(funcString);
				if( typeof reResult !== "boolean" ){
					$(exportId).html( $(exportId).html()+"<br/>" + reResult);
					return false;
				}
			}
			return reResult;
		},
		validateForm: function(){
			var self = this,
				reResult = true;
				$("#"+this.outputTarId).html("");
				$(self.formId).find(".leadui-validatebox").each((function(self){
					return function(){
						var formTar = $(this).attr("id"),
						outputTar = self.outputTarId,
						funcArray = new Array(),
						paramArray = new Array(),
						required = $(this).attr("required"),//true/false
						valName = $(this).attr("validType"),//"func1","func2","func3"
						valParam = $(this).attr("validParam");//param1,param2,param3
						if( typeof(required) !=="undefined" && required ){
							funcArray.push("required");
						}
						if( typeof(valName) !=="undefined" ){
							funcArray.push(valName);
						}
						if( typeof(valParam) !=="undefined" ){
							var temparray = valParam.split(",");
							for(var a =0; a < temparray.length; a++){
								paramArray.push(temparray[a]);
							}
						}
						
						reResult = self.validate(formTar,outputTar,paramArray,funcArray)&&reResult;
					};
				})(self));
				if( !reResult ){
					$("#"+this.outputTarId).removeClass("ui-helper-hidden");
					setTimeout((function(outputTarId){
						return function(){ 
							$("#"+outputTarId).addClass("ui-helper-hidden"); 
						};
					})(this.outputTarId),5000);
				}
			return reResult;
		},
		required: function(value, param){
			var len = $.trim(value).length,
				message="{0}不允许为空;";
			return len > 0?true: String.format(message,param[0]);
		},
		validateString: function (value, param) {
			if(value.length==0){
				return true;
			}
	   		var regex = /^[\u0391-\uFFE5\w]+$/,
	   			message="{0}只能包括中文字、英文字母、数字和下划线;";
	  	 	return regex.test($.trim(value))?true:String.format(message,param[0]);
		},
		validateIntegerLen: function(value, param) {
			if(value.length==0){
				return true;
			}
            var len = $.trim(value).length,
            	regex = /^\d+$/,
            	message = "{0}输入内容必须为非负整数，且不能大于{1}位;";
            return regex.test(value) && len<=param[1]?true:String.format(message,param[0],param[1]);
        },
        validateIPAddress: function(value, param){
        	if(value.length==0){
				return true;
			}
    		var regex=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    		var message="{0}请输入合法的IP地址,例如：xxx.xxx.xxx.xxx;";
    		return regex.test(value)?true:String.format(message,param[0]);
    	},
    	validateURLAddress: function(value, param){
    		if(value.length==0){
				return true;
			}
    		var regex=/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
    		var message=  "{0}请输入合法的URL地址,例如：http://xxx.xxx.xxx;https://xxx.xxx.xxx;ftp://xxx.xxx;";
    		return regex.test(value)?true:String.format(message,param[0]);
    	},
    	validatePositiveFloat: function(value, param) {
    		if(value.length==0){
				return true;
			}
            var regex = /^\d+(.\d+)?$/,
            	message = "{0}请输入有效金额;";
            return regex.test(value)?true:String.format(message,param[0]);
        }
	};
	 