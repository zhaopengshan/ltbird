var err_style = "input_verify_error";
var suc_style = "input_verify_right";

/**
 * 清除以前的样式
 * 
 * @param chkResult
 */
function removeCss(chkResult) {
	$(chkResult).removeClass(suc_style);
	$(chkResult).removeClass(err_style);
}
/*
 * 用途：检查输入字符串是否符合正整数格式 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
 * 
 */

function isNumber(s) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}
/*
 * 用途：检查输入对象的值是否符合整数格式 输入：str 输入的字符串 返回：如果通过验证返回true,否则返回false
 * 
 */

function isInteger(str) {
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	return regu.test(str);
}
/*
 * 用途：检查输入字符串是否为空或者全部都是空格 输入：str 返回： 如果全是空返回true,否则返回false
 */
function isNull(str) {
	if (str == "" || str == null)
		return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}

/*
 * 
 * 用途：校验ip地址的格式 输入：strIP：ip地址 返回：如果通过验证返回true,否则返回false；
 */
function checkIP(ipId, chkResult, isFocus) {
	removeCss(chkResult);
	var strIP = $(ipId).val();
	// var ip_info = document.getElementById("ipResult");
	// ip_info.innerHTML = err_style+" IP地址不能为空";
	if (isNull(strIP)) {
		$(chkResult).addClass(err_style).html("IP地址不能为空");
		if (isFocus) {
			$(ipId).focus();
		}
		return false;
	}
	var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g // 匹配IP地址的正则表达式
	if (re.test(strIP)) {
		if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
				&& RegExp.$4 < 256) {
			$(chkResult).addClass(suc_style).html("IP地址输入正确");
		}
		return true;
	}
	$(chkResult).addClass(err_style).html("IP地址格式错误");
	if (isFocus) {
		$(ipId).focus();
	}
	return false;
}

/*
 * 
 * 用途：检查输入手机号码是否正确 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
 * 
 */

function checkMobile(mId, chkResult, isFocus) {
	removeCss(chkResult);
	var mobile = $(mId).val();
	if (isNull(mobile)) {
		$(chkResult).addClass(err_style).html("手机号不能为空");
		if (isFocus) {
			$(mId).focus();
		}
		return false;
	}
	var regu = /^[1][358][0-9]{9}$/;

	var re = new RegExp(regu);
	if (re.test(mobile)) {
		$(chkResult).addClass(suc_style).html("手机号输入正确");
		return true;
	} else {
		$(chkResult).addClass(err_style).html("手机号格式错误");
		if (isFocus) {
			$(mId).focus();
		}
		return false;
	}
}

/**
 * 验证邮箱
 */
function checkEmail(eId, chkResult, isFocus) {
	removeCss(chkResult);
	var strEmail = $(eId).val();
	if (isNull(strEmail)) {
		// 电子邮箱可以为空
		/*
		 * $(chkResult).addClass(err_style).html("电子邮件不能为空"); if(isFocus){
		 * $(eId).focus(); } return false;
		 */
		return true;
	}

	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	email_Flag = reg.test(strEmail);
	if (email_Flag) {
		$(chkResult).addClass(suc_style).html("电子邮件输入正确");
		return true;
	} else {
		$(chkResult).addClass(err_style).html("电子邮件格式错误");
		if (isFocus) {
			$(eId).focus();
		}
		return false;
	}
}

/**
 * 验证用户名
 * 
 * @param id
 *            用户id 带#
 * @param defaultValue
 *            默认的值，提示信息
 * @param chkResult
 *            提示结果id
 * @param isFocus
 *            是否定位到当前文本框
 * 
 */
function checkUser(uId, defaultValue, chkResult, isFocus) {
	removeCss(chkResult);
	if ($("#userAdd_userId").val() != "") {
		return true;
	}
	/*
	 * if($("#flag").val() == "update" || $("#flag").val() == "updateUser"){ //
	 * 如果是修改页面则不验证用户名，因为用户名不可修改的 return true; }
	 */
	var username = $(uId).val();
	if (isNull(username) || username == defaultValue) {
		$(chkResult).addClass(err_style).html("用户名不能为空");
		// $("#" + val).focus();
		if (isFocus) {
			$(uId).focus();
		}
		return false;
	}

	if (username.length < 6 || username.length > 20) {
		$(chkResult).addClass(err_style).html("用户名长度为(6-20)");
		if (isFocus) {
			$(uId).focus();
		}
		return false;
	}
	if (username == "administrator") {
		$(chkResult).addClass(err_style).html("用户名不能为administrator");
		if (isFocus) {
			$(uId).focus();
		}
		return false;
	}
	var usern = /^[a-zA-Z0-9_]{1,}$/;
	var f = usern.test(username);

	if (!f) {
		$(chkResult).addClass(err_style).html("用户名只能由字母、数字、下划线组成");
		if (isFocus) {
			$(uId).focus();
		}
		return false;
	} else {
		var bool = true;
		$.ajax({
			url : "./userAction/queryUserExist.action",
			data : "flag=add&portalUser.account=" + username,
			context : document.body,
			dataType : "json",
			type : "post",
			async : false,
			success : function(res) {
				if (res.flag > 0) {
					$(chkResult).addClass(err_style).html("用户已存在");
					if (isFocus) {
						$(uId).focus();
					}
					bool = false;
				} else {
					bool = true;
					$(chkResult).addClass(suc_style).html("用户名正确");
				}
			}
		});
		return bool;
	}
}
/**
 * 验证密码
 * 用于创建用户
 */
function checkPWD(pId, defaultValue, chkResult, isFocus) {
	removeCss(chkResult);
	// 修改用户页面
	/*
	 * if($("#flag").val() == "updateUser" ){ // 从userList页面过的修改用户 pId =
	 * "#updatePwd"; }
	 */
	var pre = "", filter=/^(?![a-z]+$)(?!\d+$)(?!\_+$)[a-z0-9_]+$/i;//{8,20}
//	if ($("#flag").val() == "updatePwd") {
//		pre = "新";
//	}
	var pwd = $(pId).val();
	if (isNull(pwd) || pwd == defaultValue) {
		$(chkResult).addClass(err_style).html(pre + "密码不能为空");
		if (isFocus) {
			$("#pwd_text").hide();
			$(pId).show().focus();
		}
		return false;
	}
	if (!filter.test(pwd)) { 
		$(chkResult).addClass(err_style).html(pre + "密码至少包含数字、字母、下划线的两种组合");
		if (isFocus) {
			$(pId).focus();
		}
		return false;
	}
	if (pwd.length < 8 || pwd.length > 20) {
		$(chkResult).addClass(err_style).html(pre + "密码长度为(8-20)");
		if (isFocus) {
			$(pId).focus();
		}
		return false;
	} else {
		$(chkResult).addClass(suc_style).html(pre + "密码输入正确");
		return true;
	}
}
/**
 * 验证密码
 * 用于修改密码
 */
function checkPWD3(pId, pId2, defaultValue, chkResult, isFocus) {
	removeCss(chkResult);
	// 修改用户页面
	/*
	 * if($("#flag").val() == "updateUser" ){ // 从userList页面过的修改用户 pId =
	 * "#updatePwd"; }
	 */
	var pre = "", filter=/^(?![a-z]+$)(?!\d+$)(?!\_+$)[a-z0-9_]+$/i;//{8,20}
//	if ($("#flag").val() == "updatePwd") {
		pre = "新";
//	}
	var pwd = $(pId).val();
	var oldpwd = $(pId2).val();
	if (isNull(pwd) || pwd == defaultValue) {
		$(chkResult).addClass(err_style).html(pre + "密码不能为空");
		if (isFocus) {
			$("#pwd_text").hide();
			$(pId).show().focus();
		}
		return false;
	}
	if (isNull(oldpwd)) {
		$(chkResult).addClass(err_style).html("原密码不能为空");
		if (isFocus) {
			$(pId2).focus();
		}
		return false;
	}
	if ( oldpwd==pwd ) {
		$(chkResult).addClass(err_style).html(pre +"密码必须与旧密码不同");
		if (isFocus) {
			$(pId).show().focus();
		}
		return false;
	}
	if (!filter.test(pwd)) { 
		$(chkResult).addClass(err_style).html(pre + "密码至少包含数字、字母、下划线的两种组合");
		if (isFocus) {
			$(pId).focus();
		}
		return false;
	}
	if (pwd.length < 8 || pwd.length > 20) {
		$(chkResult).addClass(err_style).html(pre + "密码长度为(8-20)");
		if (isFocus) {
			$(pId).focus();
		}
		return false;
	} else {
		$(chkResult).addClass(suc_style).html(pre + "密码输入正确");
		return true;
	}
}
/**
 * 两次输入的密码是否一样
 * 
 * @param pwd
 * @param pwd2
 * @param chkResult
 */
function checkPWD2(pId1, pId2, defaultValue, chkResult, isFocus) {
	removeCss(chkResult);
	/*
	 * if($("#flag").val() == "updateUser" ){ // 从userList页面过的修改用户 pId1 =
	 * "#updatePwd"; pId2 = "#updatePass2"; }
	 */
	var pre = "校验"
	if ($("#flag").val() == "updatePwd") {
		pre = "确认新";
	}
	var pwd = $(pId1).val();
	var pwd2 = $(pId2).val();
	if (isNull(pwd2) || pwd2 == defaultValue) {
		$(chkResult).addClass(err_style).html(pre + "密码不能为空");
		if (isFocus) {
			$("#pwd_text2").hide();
			$(pId2).show().focus()
		}
		return false;
	}
	if (pwd2.length < 8 || pwd2.length > 20) {
		$(chkResult).addClass(err_style).html(pre + "密码长度为(8-20)");
		if (isFocus) {
			$(pId2).focus();
		}
		return false;
	}
	if (pwd != pwd2) {
		$(chkResult).addClass(err_style).html("两次密码输入不一致");
		if (isFocus) {
			$(pId2).focus();
		}
		return false;
	} else {
		$(chkResult).addClass(suc_style).html(pre + "密码输入正确");
		return true;
	}
}

/**
 * 修改密码时，验证旧密码是否正确
 */
function checkUserPwd(opId, chkResult, isFocus) {
	removeCss(chkResult);
	var pwd = $(opId).val();
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
			url : "./userAction/validatePwd.action",
			data : "pwd=" + pwd,
			context : document.body,
			dataType : "json",
			type : "post",
			async : false,
			success : function(data) {
				if (data.flag == "error") {
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
/**
 * 验证名称
 * 
 * @param nId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkEmpty(nId, chkResult, isFocus) {
	$(chkResult).html("");
	removeCss(chkResult);
	var name = $(nId).val();
	if (isNull(name)) {
		$(chkResult).addClass(err_style).html("不能为空");
		if (isFocus) {
			$(nId).focus();
		}
		return false;
	}
	return true;
}
/**
 * 验证名称
 * 
 * @param nId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkName(nId, chkResult, isFocus) {
	removeCss(chkResult);
	var name = $(nId).val();
	if (isNull(name)) {
		$(chkResult).addClass(err_style).html("姓名不能为空");
		if (isFocus) {
			$(nId).focus();
		}
		return false;
	} else {
		$(chkResult).addClass(suc_style).html("姓名输入正确");
		return true;
	}
}
/**
 * 短信网关接入号
 * 
 * @param nId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkCorpAccessNumber(uType, uId, nId, chkResult, isFocus) {
	$(chkResult).html("");
	removeCss(chkResult);
	var name = $(nId).val();
	if (isNull(name)) {
		$(chkResult).addClass(err_style).html("企业端口号不能为空");
		if (isFocus) {
			$(nId).focus();
		}
		return false;
	}
	return true;
//	var userId = $(uId).val();
//	removeCss(chkResult);
//	var utype = $(uType).val();
//	var name = $(nId).val();
//	var re = new RegExp(/^[1][0][6][5][7][3][1][1][0-9][0-9][0-9]$/);
//
//	if (isNull(name)) {
//		if (utype == 3) {
//		$(chkResult).addClass(err_style).html("短信网关接入号不能为空");
//		if (isFocus) {
//			$(nId).focus();
//		}
//		return false;
//		}
//		return true;
//	} else if (!re.test(name)) {
//		if (utype ==2) {
//		$(chkResult).addClass(err_style).html("短信网关接入应以10657311开头，后加三位数字!");
//		if (isFocus) {
//			$(nId).focus();
//		}
//		return false;
//		}
//		return true;
//	} else {
//		if (utype ==2) {
//			if (userId == "" || userId == null) {
//				var bool = true;
//				$.ajax({
//							url : "./userAction/checkCorpAccessNumber.action",
//							data : {
//								"portalUser.corpAccessNumber" : name
//							},
//							context : document.body,
//							dataType : "json",
//							type : "post",
//							async : false,
//							success : function(res) {
//								if (res > 0) {
//									$(chkResult).addClass(err_style).html(
//											"短信网关接入号已存在");
//									if (isFocus) {
//										$(rId).focus();
//									}
//									bool = false;
//								} else {
//									$(chkResult).addClass(suc_style).html(
//											"短信网关接入号输入正确");
//									bool = true;
//								}
//							}
//						});
//				return bool;
//			}
//		}
//		return true;
//	}
}

/**
 * 验证短信扩展码
 * 
 * @param nId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkUserExtCoding(nId, chkResult, isFocus, nameLength) {
	removeCss(chkResult);
	var name = $(nId).val();
	if (isNull(name)) {
		$(chkResult).addClass(err_style).html("扩展码不能为空");
		if (isFocus) {
			$(nId).focus();
		}
		return false;
        //return true;
	} else {
		if (!/^-?[0-9]\d*$/.test(name)) {
			$(chkResult).addClass(err_style).html("扩展码只能是数字");
			return false;
		}
                if(name.length != nameLength){
                        $(chkResult).addClass(err_style).html("扩展码只能是"+nameLength+"位数字");
			return false;
                }
		$(chkResult).addClass(suc_style).html("扩展码输入正确");
		return true;
	}
}
/**
 * 验证角色名称
 * 
 * @param rId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkRoleName(rId, chkResult, isFocus) {
	removeCss(chkResult);
	/*
	 * if($("#roleId").val() != ""){ // 修改角色 return true; }
	 */
	var roleName = $(rId).val();// alert(rId+"; "+ roleName);
	if (isNull(roleName)) {
		$(chkResult).addClass(err_style).html("角色名称不能为空");
		if (isFocus) {
			$(rId).focus();
		}
		return false;
	} else {
		var bool = true;
		$.ajax({
			url : "./roleAction/queryRoleExist.action",
			data : "roleName=" + roleName,
			context : document.body,
			dataType : "json",
			type : "post",
			async : false,
			success : function(res) {
				if (res.flag > 0) {
					$(chkResult).addClass(err_style).html("角色已存在");
					if (isFocus) {
						$(rId).focus();
					}
					bool = false;
				} else {
					$(chkResult).addClass(suc_style).html("角色正确");
					bool = true;
				}
			}
		});
		return bool;
	}
}
/**
 * 验证角色描述
 * 
 * @param rId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkRoleDesc(rdId, chkResult, isFocus) {
	removeCss(chkResult);
	var roleDesc = $(rdId).val();
	if (isNull(roleDesc)) {
		$(chkResult).addClass(err_style).html("角色描述不能为空");
		if (isFocus) {
			$(rdId).focus();
		}
		return false;
	}
	$(chkResult).addClass(suc_style).html("角色描述正确");
	return true;
}

/**
 * 验证角色授权资源
 * 
 * @param rrId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkRoleResources(resName, chkResult) {
	removeCss(chkResult);
	// var len = $("input[name='"+resName+"']:checkbox").length;
	var chooseNum = 0;
	$("[name = '" + resName + "']:checkbox").each(function() {
		if ($(this).is(":checked")) {
			chooseNum++;
		}
	});
	if (chooseNum == 0) {
		$(chkResult).addClass(err_style).html("请至少为角色勾选一项授权");
		return false;
	} else {
		return true;
	}
}
/**
 * 联系人 手机号验证
 * 
 * @param mId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkContactMobile(mId, chkResult, isFocus, gId) {
	removeCss(chkResult);
	var mobile = $(mId).val();
	var groupId = $(gId).val();
	// var f = 0;

	if (checkMobile(mId, chkResult, isFocus)) {
		if (!isNull($("#cId").val())) {
			// 判断是否为修改联系人，如果是修改,不需要判断手机号存在,直接返回true
			return true;
		}
		var bol = true;
		$.ajax({
			url : "./address/validateContact.action?contact.mobile=" + mobile + "&contact.bookGroupId=" + groupId,
			type : 'post',
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.flag == 1) {
					$(chkResult).addClass(err_style).html("手机号已存在");
					if (isFocus) {
						$(mId).focus();
					}
					bol = false;
				}
			}
		});
		return bol;
	} else {
		return false;
	}
}
/**
 * 验证身份证号，允许为空
 * 
 * @param cId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function isCardID(cId, chkResult, isFocus) {
	removeCss(chkResult);
	var sId = $(cId).val();
	if (isNull(sId)) {
		return true;
	}
	var iSum = 0;
	var info = "";
	if (!/^\d{17}(\d|x)$/i.test(sId)) {
		$(chkResult).addClass(err_style).html("身份证长度或格式错误");
		if (isFocus) {
			$(cId).focus();
		}
		return false;
	}
	sId = sId.replace(/x$/i, "a");
	/*
	 * if(aCity[parseInt(sId.substr(0,2))]==null) {
	 * $(chkResult).addClass(err_style).html("你的身份证地区非法"); if(isFocus){
	 * $(cId).focus(); } return false; }
	 */
	var sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-"
			+ Number(sId.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"));
	// alert(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate());
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d
			.getDate())) {
		$(chkResult).addClass(err_style).html("身份证上的出生日期非法");
		if (isFocus) {
			$(cId).focus();
		}
		return false;
	}
	for ( var i = 17; i >= 0; i--)
		iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
	if (iSum % 11 != 1) {
		$(chkResult).addClass(err_style).html("你输入的身份证号非法");
		if (isFocus) {
			$(cId).focus();
		}
		return false;
	}
	return true;
}
/**
 * 验证qq号，必须为数字
 * 
 * @param qId
 * @param chkResult
 * @param isFocus
 * @returns {Boolean}
 */
function checkQQ(qId, chkResult, isFocus) {
	removeCss(chkResult);
	var qq = $(qId).val();
	if (isNull(qq)) {
		return true;
	}
	if (!isNumber(qq)) {
		$(chkResult).addClass(err_style).html("qq号格式不正确,请输入数字");
		if (isFocus)
			$(qId).focus();
		return false;
	}
	return true;

}