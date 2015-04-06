/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*******************************************************************************
 * 扩展validatebox，验证手机号码
 ******************************************************************************/
$.extend($.fn.validatebox.defaults.rules, {
    validateString : {
        validator : function(value, param) {
            var regex = /^[\u0391-\uFFE5\w]+$/;
            return regex.test($.trim(value));
        },
        message : "只能包括中文字、英文字母、数字和下划线"
    },
    validateIntegerLen : {
        validator : function(value, param) {
            var len = $.trim(value).length;
            var regex = /^\d+$/;
            return regex.test(value) && len<=param[0];
        },
        message : "输入内容必须为非负整数，且不能大于{0}位。"
    },
    validateIPAddress: {
    	validator: function(value,param){
    		var regex=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    		return regex.test(value);
    	},
    	message: "请输入合法的IP地址,例如：xxx.xxx.xxx.xxx"
    },
    validateURLAddress: {
    	validator: function(value,param){
    		var regex=/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
    		return regex.test(value);
    	},
    	message: "请输入合法的URL地址,例如：http://xxx.xxx.xxx;https://xxx.xxx.xxx;ftp://xxx.xxx"
    },
    validatePositiveFloat : {
        validator : function(value, param) {
            var regex = /^\d+(.\d+)?$/;
            return regex.test( value );
        },
        message : "请输入有效金额"
    }
});

/**
 * 验证时间格式是否正确
 * @param {} value
 * @returns {} 
 */
function dateISO(value) {
	return ($.trim(value)=="")||/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test($.trim(value));
}

