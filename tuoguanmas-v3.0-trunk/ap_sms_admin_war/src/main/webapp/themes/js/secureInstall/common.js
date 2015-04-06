/**
 * 封装请求
 * 
 * @param url
 *            请求地址
 * @param data
 *            上传数据
 * @param type
 *            请求数据类型
 * @param datatype
 *            上传数据类型
 * @returns 返回反馈结果，交由调用者处理
 */
function ajax(url, data, async, type, datatype) {
	var re = null;
	$.ajax({
		url : url,
		data : data,
		async : async,
		type : type,
		datatype : 'json',
		success : function(dat) {
			if (isFirstNull(dat)) {
				return false;
			} else {
				re= eval("(" + dat + ")");
			}
		},
		error : function(dat) {
			alert("pageError:" + dat);
		}
	});
	return re;
}

/**
 * 页面跳转
 * 
 * @param url
 */
function goUrl(url) {
	window.location.href = url;
}

/**
 * 属性及对象空判断
 * 
 * @param str
 * @returns {Boolean}
 */
function isFirstNull(str) {
	if (str == '' || str == 'null' || str == undefined || str == null
			|| str == 'undefined') {
		return true;
	}
	return false;
}

/**
 * 首次登录验证旧密码
 * 
 * @param account
 * @param opId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkFirstUserPwd(account, opId, chkResult, isFocus) {
	removeCss(chkResult);
	var pwd = $(opId).val();
	var useAccount = $(account).val();
	if (isNull(pwd)) {
		$(chkResult).addClass(err_style).html("原密码不能为空");
		if (isFocus)
			$(opId).focus();
		return false;
	}
	if (pwd.length < 6 || pwd.length > 20) {
		$(chkResult).addClass(err_style).html("原密码长度为(6-20)");
		if (isFocus)
			$(opId).focus();
		return false;
	} else {
		var bool = true;
		$.ajax({
			url : "./../../userAction/validatePwd.action",
			data : "pwd=" + pwd + "&users.account=" + useAccount,
			context : document.body,
			dataType : "json",
			type : "post",
			async : false,
			success : function(dat) {
				if (dat.flag == "error") {
					$(chkResult).addClass(err_style).html("原密码不正确");
					if (isFocus)
						$(opId).focus();
					bool = false;
				} else {
					$(chkResult).addClass(suc_style).html("原密码正确");
					bool = true;
				}
			}
		});
		return bool;
	}
}

function comptime(dbDate) {
	// 当前系统时间
	var DateTwo = getTime(new Date());
	var cha = ((Date.parse(subymd(dbDate)) - Date.parse(subymd(DateTwo))) / 86400000);
	//alert(cha);
	return  parseInt(cha);
}

function getTime(date) {
	var now = "";
	now = date.getFullYear() + "-"; // 读英文就行了
	now = now + timeConvert((date.getMonth() + 1).toString()) + "-";// 取月的时候取的是当前月-1如果想取当前月+1就可以了
	now = now + timeConvert(date.getDate().toString());
	return now;
}
//返回一定格式的年月日时间
function subymd(DateOne) {
	//alert(DateOne);
	if(isFirstNull(DateOne)){alert("下一次修改密码时间获取为空");return false;}
		
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
	var OneDay = DateOne
			.substring(DateOne.length, DateOne.lastIndexOf('-') + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
	return (OneMonth + '/' + OneDay + '/' + OneYear).toString();
}

function timeConvert(da) {
	if (da < 10) {
		return '0' + da;
	}
	return da;
}

/**
 * 获取元素值
 * @param str
 * @returns
 */
function getValue(str){
	return $(str).val();
}
/**
 * 获取元素对象
 * @param str
 * @returns
 */
function getObj(str){
	return $(str);
}


/**
 * 正则判断(值，模版)
 * @param demo =/^[0-9]{1,20}$/
 * @param val 
 * @returns {Boolean}
 */
 function isReg(demo,val){
	if (!demo.exec(val))
		return false;
	return true;
}
 
