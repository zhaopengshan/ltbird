<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<form action="./userAction/updatePwd.action" method="post" id="f1">
    <input type="hidden" value="updatePwd" id="flag"></input>
    <input type=hidden name="portalUser.id" value="<s:property value="#session.SESSION_USER_INFO.id" />" id="userModPwd_userId"></input>
    <input type=hidden name="portalUser.account" value="<s:property value="#session.SESSION_USER_INFO.account" />" id="userModPwd_account"></input><!-- 密码加密时所用 -->
    <ul class="gridwrapper" style="width:98%;">
        <li>
            <label>用户名：</label>
            <input type="text" value="<s:property value="#session.SESSION_USER_INFO.account" />"  readonly="readonly" />
        </li>
        <li>
            <label><span style="color: #FF0000">*</span>原密码：</label>
            <input id="userModPwd_oldPwd"  type="password" onblur="checkUserPwd('#userModPwd_oldPwd', '#oldModPwdResult', false)"/>
            <span id="oldModPwdResult"></span>
        </li>
        <li>
            <label><span style="color: #FF0000">*</span>新密码：</label>
            <input type="text" id="userModPwd_pwd_text" size="50" maxlength="20" name="password" value="（8~20个字符，区分大小写）"/>
            <input size="50" maxlength="20"  name="portalUser.password" id="userModPwd_loginPwd"  type="password" onkeyup="pwStrength(this.value)"  onblur="pwStrength(this.value)" />
            <span id="pass1ModResult"></span>
            <label id="changepswdstrongshow">密码安全度：<span class="percentcur" id="changepswdsetpercentcur"></span><span class="pswd_level"></span><span id="change_pswd_strong" style="color: green">低</span></label>
            
        </li>
        <li>
            <label><span style="color: #FF0000">*</span>确认新密码：</label>
            <input type="text" id="userModPwd_pwd_text2" size="50" maxlength="20" name="password2"  value="（必须与密码相同）"/>
            <input type="password" name="userModPwd_pass2" id="userModPwd_pass2" size="50" maxlength="20"></input> 
            <span id="pass2ModResult"></span>
        </li>
        <li class="btn">
            <a href="#" id="sub">提交</a>
            <a href="#" id="clear">取消</a>
        </li>
    </ul>   
</form>
</body>
<script type="text/javascript">
    $(function() {
        var pass_tip = "（8~20个字符，区分大小写）";
        var pass_tip2= "（必须与密码相同）";

        //对密码框进行操作 
        $("#userModPwd_loginPwd").hide(); 
        $("#userModPwd_pwd_text").focus(function(){ 
            if($("#userModPwd_pwd_text").val()== pass_tip){
                $("#userModPwd_pwd_text").hide();
            } 
            $("#userModPwd_loginPwd").show().focus(); 
            $("#userModPwd_loginPwd").addClass("focus"); 
        }); 
        $("#userModPwd_loginPwd").blur(function(){ 
            if($("#userModPwd_loginPwd").val()==""){ 
                $("#userModPwd_loginPwd").hide(); 
                $("#userModPwd_pwd_text").val(pass_tip);
                $("#userModPwd_pwd_text").show(); 
            }else{
                checkPWD3( "#userModPwd_loginPwd" ,"#userModPwd_oldPwd", pass_tip, "#pass1ModResult", false);
            }
            $(this).removeClass("focus"); 
        }); 
	 
        //对校验密码框进行操作 
        $("#userModPwd_pass2").hide(); 
        $("#userModPwd_pwd_text2").focus(function(){ 
            if($("#userModPwd_pwd_text2").val()== pass_tip2){
                $("#userModPwd_pwd_text2").hide();
            } 
            $("#userModPwd_pass2").show().focus(); 
            $("#userModPwd_pass2").addClass("focus"); 
        }); 
        $("#userModPwd_pass2").blur(function(){ 
            if($("#userModPwd_pass2").val()==""){ 
                $("#userModPwd_pass2").hide(); 
                $("#userModPwd_pwd_text2").val(pass_tip2);
                $("#userModPwd_pwd_text2").show(); 
            }else{
                checkPWD2( "#userModPwd_loginPwd" , "#userModPwd_pass2", pass_tip2,
                "#pass2ModResult", false);
            }
            $(this).removeClass("focus"); 
        }); 
         
        $("#clear").unbind("click").click(function(){
            clearHtml();
        });
        $("#sub").unbind("click")
        .click(
        function() {
            if (checkUserPwd('#userModPwd_oldPwd', '#oldModPwdResult', true) 
                && checkPWD3( "#userModPwd_loginPwd","#userModPwd_oldPwd", pass_tip, "#pass1ModResult", true)
                && checkPWD2( "#userModPwd_loginPwd" , "#userModPwd_pass2", pass_tip2, "#pass2ModResult", true)
        ) {
            } else {
                return false;
            }
            //alert(TabPanel.getActiveIndex());return ;
            $.ajax({ url:  "./userAction/updatePwd.action",
                data : "portalUser.id="+ $("#userModPwd_userId").val()+"&portalUser.account="
                    +$("#userModPwd_account").val()+"&portalUser.password="+$("#userModPwd_loginPwd").val(),
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
            //$("#f1").submit();
            return true;
        });
    });
		
    function clearHtml(){
        $("#userModPwd_oldPwd").val("");
        $("#userModPwd_loginPwd").val("");
        $("#userModPwd_loginPwd").val("").hide(); 
        $("#userModPwd_pwd_text").val("（8~20个字符，区分大小写）").show();
        pwStrength('');
        $("#userModPwd_pass2").val("").hide(); 
        $("#userModPwd_pwd_text2").val("（必须与密码相同）").show();
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