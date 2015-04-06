<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx}/themes/js/validate.js"></script>
<script type="text/javascript" src="${ctx }/themes/js/secureInstall/common.js"></script>
</head>
<body>
<form action="${ctx}/userAction/updatePwd.action" method="post" id="f1">
    <ul class="gridwrapper" style="width:98%;">
        <!--<li>
            <label>用户名：</label>
            <input type="text" value="<s:property value="#session.SESSION_USER_INFO.account" />"  readonly="readonly" />
        </li>-->
        <li>
            <label><span style="color: #FF0000">*</span>原密码：</label>
            <input id="userPwd_oldPwd"  type="password"/>
            <span id="oldPwdResult"></span>
        </li>
        <li>
            <label><span style="color: #FF0000">*</span>新密码：</label>
            <input type="text" id="userPwd_pwd_text" size="50" maxlength="20" name="password" value="（8~20个字符，区分大小写）"/>
            <input size="50" maxlength="20"  name="portalUser.password" id="userPwd_loginPwd"  type="password" onkeyup="pwStrength(this.value)"  onblur="pwStrength(this.value)" />
            <span id="pass1Result"></span>
            <label id="changepswdstrongshow">密码安全度：<span class="percentcur" id="changepswdsetpercentcur"></span><span class="pswd_level"></span><span id="change_pswd_strong" style="color: green">低</span></label>
            
        </li>
        <li>
            <label><span style="color: #FF0000">*</span>确认新密码：</label>
            <input type="text" id="userPwd_pwd_text2" size="50" maxlength="20" name="password2"  value="（必须与密码相同）"/>
            <input type="password" name="userPwd_pass2" id="userPwd_pass2" size="50" maxlength="20"></input> 
            <span id="pass2Result"></span>
        </li>
        <li class="btn">
            <a href="javascript:void(0);" id="sub">提交</a>
            <a href="javascript:void(0);" id="clear">取消</a>
        </li>
    </ul>   
    <!--<input type="hidden" value="updatePwd" id="flag"></input>
    <input type=hidden name="portalUser.id" value="" id="userPwd_userId"></input>
    <input type=hidden name="portalUser.account" value="" id="userPwd_account"></input>
   	<input type="hidden" value=""  readonly="readonly" />-->
   	
</form>
</body>
<script type="text/javascript">
    $(function() {
        var pass_tip = "（8~20个字符，区分大小写）";
        var pass_tip2= "（必须与密码相同）";

        //对密码框进行操作 
        $("#userPwd_loginPwd").hide(); 
        $("#userPwd_pwd_text").focus(function(){ 
            if($("#userPwd_pwd_text").val()== pass_tip){
                $("#userPwd_pwd_text").hide();
            } 
            $("#userPwd_loginPwd").show().focus(); 
            $("#userPwd_loginPwd").addClass("focus"); 
        }); 
        $("#userPwd_loginPwd").blur(function(){ 
            if($("#userPwd_loginPwd").val()==""){ 
                $("#userPwd_loginPwd").hide(); 
                $("#userPwd_pwd_text").val(pass_tip);
                $("#userPwd_pwd_text").show(); 
            }else{
                checkPWD3( "#userPwd_loginPwd","#userPwd_oldPwd", pass_tip, "#pass1Result", false);
            }
            $(this).removeClass("focus"); 
        }); 
	 
        //对校验密码框进行操作 
        $("#userPwd_pass2").hide(); 
        $("#userPwd_pwd_text2").focus(function(){ 
            if($("#userPwd_pwd_text2").val()== pass_tip2){
                $("#userPwd_pwd_text2").hide();
            } 
            $("#userPwd_pass2").show().focus(); 
            $("#userPwd_pass2").addClass("focus"); 
        }); 
        $("#userPwd_pass2").blur(function(){ 
            if($("#userPwd_pass2").val()==""){ 
                $("#userPwd_pass2").hide(); 
                $("#userPwd_pwd_text2").val(pass_tip2);
                $("#userPwd_pwd_text2").show(); 
            }else{
                checkPWD2( "#userPwd_loginPwd" , "#userPwd_pass2", pass_tip2,
                "#pass2Result", false);
            }
            $(this).removeClass("focus"); 
        }); 
        $("#clear").unbind("click").click(function(){
            clearHtml();
        });
        $("#sub").unbind("click").click(function() {
        	/*checkFirstUserPwd('#userPwd_account','#userPwd_oldPwd', '#oldPwdResult', true) 
                && */
            if (checkPWD3( "#userPwd_loginPwd","#userPwd_oldPwd", pass_tip, "#pass1Result", false)
                && checkPWD2( "#userPwd_loginPwd" , "#userPwd_pass2", pass_tip2, "#pass2Result", false)) {
                
                //首次登录标识判断处理
            	/*var userData="users.id="+$("#userPwd_userId").val()+"&portalUser.id="+$("#userPwd_userId").val()+"&portalUser.password="
            							+$("#userPwd_loginPwd").val()+"&portalUser.account="+$("#userPwd_account").val();
            	var re=ajax('${ctx}/userAction/updatePwd.action',userData,false,'post','json');
            	if(re.flag=='success'){
            		alert("密码修改成功!请重新登录系统!");
            		goUrl("${ctx}/smslogin.jsp");
            	}else{
            		alert("密码修改失败，请联系管理员");
            	}*/
            	//首次登录标识判断处理
            	var userData="portalUser.password="+$("#userPwd_loginPwd").val()+"&pwd="+$("#userPwd_oldPwd").val();
            	var re=ajax('${ctx}/userAction/updatePwdFirst.action',userData,false,'post','json');
            	if(re.flag=='success'){
            		alert(re.message);
            		goUrl("${ctx}/smslogin.jsp");
            	}else{
            		alert(re.message);
            	}
            } else {
                return false;
            }
            //alert(TabPanel.getActiveIndex());return ;
            /*$.ajax({ url:  "${ctx}/userAction/updatePwd.action",
                data : "portalUser.id="+ $("#userPwd_userId").val()+"&portalUser.account="
                    +$("#userPwd_account").val()+"&portalUser.password="+$("#userPwd_loginPwd").val(),
                context: document.body, 
                dataType: "json",
                type: "post",
                success: function(res){
                    if(res.flag == "success"){
                        clearHtml();
                        //	addTabs('mainPage','mainPage','首页','首页',
                        //			$("#basePath").val()+'/main/main.jsp');
                        ///alert(tabpanel.getActiveIndex());
                        alert("密码修改成功!");
                    }else{
                        clearHtml();
                        alert("密码修改失败!");
                        //$(chkResult).html( suc_style+" 角色正确");
                    }
                }
            });
            */
            //$("#f1").submit();
            return false;
        });
    });
		
    function clearHtml(){
        $("#userPwd_oldPwd").val("");
        $("#userPwd_loginPwd").val("");
        $("#userPwd_loginPwd").val("").hide(); 
        $("#userPwd_pwd_text").val("（8~20个字符，区分大小写）").show();
        pwStrength('');
        $("#userPwd_pass2").val("").hide(); 
        $("#userPwd_pwd_text2").val("（必须与密码相同）").show();
        $("span[id$=Result]").removeAttr("class").html("");
    }
    //CharMode函数 
    //测试某个字符是属于哪一类. 
    function CharMode(iN) {
        if (iN >= 48 && iN <= 57) //数字 
            return 1;
        if ((iN >= 65 && iN <= 90)) //小写字母 
            return 2;
        if (iN >= 97 && iN <= 122)// 大写字母
            return 4;
        else
            return 8; //特殊字符 
    }
    //bitTotal函数 
    //计算出当前密码当中一共有多少种模式 
    function bitTotal(num) {
        modes = 0;
        for (i = 0; i < 4; i++) {
            if (num & 1)
                modes++;
            num >>>= 1;
        }
        return modes;
    }
    //checkStrong函数 
    //返回密码的强度级别 
    function checkStrong(sPW) {
        if (sPW.length <= 8)
            return 0; //密码太短 
        Modes = 0;
        for (i = 0; i < sPW.length; i++) {
            //测试每一个字符的类别并统计一共有多少种模式. 
            Modes |= CharMode(sPW.charCodeAt(i));
        }
        return bitTotal(Modes);
    }
    //pwStrength函数 
    //当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色 
    function pwStrength(pwd) { 
    	var slevel = "低";
        if (pwd == null || pwd == '') {
            $("#changepswdsetpercentcur").css("width","0px");        
        } else {
            S_level = checkStrong(pwd); 
            switch (S_level) {
                case 0: // 低(初始化)
                    $("#changepswdsetpercentcur").css("width","25px");
                    slevel = "低";
                case 1: // 低
                    $("#changepswdsetpercentcur").css("width","25px");
                    slevel = "低";
                    break;
                case 2: // 中
                    $("#changepswdsetpercentcur").css("width","50px");
                    slevel = "中";
                    break;
                case 3: // 中高
                    $("#changepswdsetpercentcur").css("width","75px");
                    slevel = "中高";
                    break;
                default:
                    $("#changepswdsetpercentcur").css("width","100px");
                    slevel = "高";
            }
        }
        $("#change_pswd_strong").html(slevel);
        return;
    }
	
</script>

</html>