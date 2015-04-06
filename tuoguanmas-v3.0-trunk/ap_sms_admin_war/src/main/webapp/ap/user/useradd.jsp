<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form action="./userAction/adduser.action" method="post" id="userAdd_form">
    <s:if test="#request.userVO == null">
        <!-- 新增 -->
        <input type="hidden" name="flag" value="save" id="flag"/>
    </s:if>
    <s:else>
        <!-- 从用户列表过的修改 -->
        <input type="hidden" name="flag" value="updateUser" id="flag"/>
        <!-- 当从用户列表进来修改用户时，这个div用于储存所有的角色 -->
        <select id="allSelect" name="selectL" style="width: 180px;display: none" size="6" multiple="multiple">
            <s:iterator id="allRole" value="#request.allList">
                <option value="${allRole.id }">${allRole.name}</option>
            </s:iterator>
        </select>
    </s:else>
	<s:if test="#request.province != null">
        <input type="hidden" name="portalUser.province" value="${request.province}" id="userAdd_province_hidden"/>
    </s:if>
	<s:if test="#request.city != null">
        <input type="hidden" name="portalUser.city" value="${request.city}" id="userAdd_city_hidden"/>
    </s:if>
  <s:if test="#request.merchantPin != null">
        <input type="hidden" name="portalUser.merchantPin" value="${request.merchantPin}" id="userAdd_merchantvip_hidden"/>
    </s:if>
    <input type="hidden" name="oldRoles" id="oldRoles"/><!-- 存放所有的角色信息 -->
    <input type="hidden" name="portalUser.id" id="userAdd_userId" value="${userVO.id }"/>
   <!--  用于存放当前登录用户类型 -->
    <input type="hidden" name="userTypehidden" id="userAdd_userType" value="${session.SESSION_USER_INFO.userType }"/>
    <ul class="gridwrapper"  style="overflow-y: auto;overflow-x: hidden; position:relative; width: 100%;">
        <li>
            <label><span>*</span>用户名：</label>
            <input type="text" name="portalUser.account" id="loginAccount" 
                   <s:if test="#request.userVO == null || #request.userVO.account == null" > 
                       value="（6~20个字符，只能包含字母/数字/下划线）" 
                   </s:if>
                   <s:if test="#request.userVO.account != null">
                       value="${userVO.account }" readonly="readonly" 
                   </s:if>
                   />
            <span id="userResult"></span>
        </li>
        <li>  <label><span>*</span>密码：</label>
            <input name="portalUser.password" id="loginPwd"  type="password" 
                   onKeyUp="pwStrength(this.value)" onBlur="pwStrength(this.value)" 
                   value="${userVO.password }"
                   />
            <s:if test="#request.userVO == null || #request.userVO.password == null">
                <input type="text" id="pwd_text" name="password" value="（8~20个字符，区分大小写）"/>
            </s:if>
            <span id="pass1Result"></span>
            <label id="pswdstrongshow">密码安全度：<span class="percentcur" id="setpercentcur"></span><span class="pswd_level"></span><span id="pswd_strong" style="color: green">低</span></label>

        </li>
        <li>
            <label><span>*</span>校检密码：</label>
            <s:if test="#request.userVO == null || #request.userVO.password == null">
                <input type="text" id="pwd_text2" name="password2"  value="（必须与密码相同）"/>
            </s:if>
            <input type="password" name="pass2" id="pass2" value="${userVO.password }"/> 
            <span id="pass2Result"></span>
        </li>
        <li>
            <label><span>*</span>姓名：</label>
            <input type="text" name="portalUser.name" id="userAdd_name" value="${userVO.name }"/>
            <span id="usernameResult"></span>
        </li>
        <li>
            <label>性别：</label>
            <input name="portalUser.gender" type="radio" value="1" <s:if test="#request.userVO == null || #request.userVO.gender == 1">checked</s:if> id="male" />男
            <input name="portalUser.gender" type="radio" <s:if test="#request.userVO.gender == 0">checked</s:if> value="0" id="female"/>女
        </li>
        <li>
            <label><span>*</span>手机号：</label>
            <input type="text" name="portalUser.mobile" id="userAdd_mobile" value="${userVO.mobile }" maxlength="11" /> 
            <span id="mobileResult"></span>
        </li>
        <li>
        	<label><input type="checkbox" name="portalUserExt.smsLimit" <s:if test="#request.portalUserExt != null && #request.portalUserExt.smsLimit == 1">checked="checked"</s:if>/>启用用户条数限制：</label>
        	每&nbsp;<select style="width:50px; height:22px;" name="portalUserExt.smsLimitPeriod">
        		<option value="0" <s:if test="#request.portalUserExt == null || #request.portalUserExt.smsLimitPeriod == 0">selected="selected"</s:if>>日</op	ion>
        		<option value="1" <s:if test="#request.portalUserExt.smsLimitPeriod == 1">selected="selected"</s:if>>月</option>
        	</select>
        	发送上限为&nbsp;<input type="text" id="" name="portalUserExt.smsLimitCount" style="width:120px;" value="${portalUserExt.smsLimitCount }"/>&nbsp;条
        </li>
        <li>
        	<label>用户发送优先级：</label>
        	<select style="width:70px; height:22px;" name="portalUserExt.smsPriority" size="1">
        		<option value="4" <s:if test="#request.portalUserExt.smsPriority==4">selected="selected"</s:if>>高</option>
        		<option value="3" <s:if test="#request.portalUserExt.smsPriority==3">selected="selected"</s:if>>中</option>
        		<option value="2" <s:if test="#request.portalUserExt.smsPriority==2">selected="selected"</s:if>>低</option>
        	</select>
        </li>
        <li>
            <label>电子邮件：</label>
            <input type="text" name="portalUser.email" id="userAdd_email" value="${userVO.email }"/>
            <span id="emailResult"></span>
        </li>
        <li>
            <label>用户使用标志：</label>
            <input name="portalUser.activeFlag"  <s:if test="#request.userVO == null || #request.userVO.activeFlag == 1 ">checked</s:if> type="radio" value="1"  />使用
            <input name="portalUser.activeFlag"  <s:if test="#request.userVO.activeFlag == 0 ">checked</s:if> type="radio" value="0" />暂停/锁定
        </li>
        <li>
            <label>登录错误锁定阈值：</label>
            <select style="height:23px;" name="portalUser.limitTryCount" size="1">
           		<option value="0" <s:if test="#request.userVO.limitTryCount==0">selected="selected"</s:if>>无限制</option>
            	<option value="3" <s:if test="#request.userVO.limitTryCount==3">selected="selected"</s:if> >3</option>
            	<option value="5" <s:if test="#request.userVO.limitTryCount==5">selected="selected"</s:if>>5</option>
            </select>次连续输入密码错误后锁定用户，需要管理员解锁
        </li>
        <li>
            <label>登录IP限制：</label>
            <input type="radio" name="portalUser.ipLimitFlag" <s:if test="#request.userVO == null || #request.userVO.ipLimitFlag == 0">checked</s:if> value="0" onclick="checks()" />无限制 
            <input type="radio" <s:if test="#request.userVO.ipLimitFlag == 1">checked</s:if> name="portalUser.ipLimitFlag" value="1" onclick="checks()"/>限制
            <input type="text" name="portalUser.ipAddress" maxlength="15" id="userAdd_ip" value="${userVO.ipAddress }"
                   <s:if test="#request.userVO == null || #request.userVO.ipLimitFlag == 0 ">disabled</s:if> /> 
            <span id="ipResult"></span>
        </li>
        <s:if test="#session.SESSION_USER_INFO.userType != 0 && #session.SESSION_USER_INFO.userType != 1">
         <li style="display: none;">
            <label>登录鉴权方式：</label>
            <input type="radio" name="portalUser.loginType" id="userAdd_loginType" value="1" checked="checked"  />用户名+密码
            <input type="radio" name="portalUser.loginType" id="userAdd_loginType" value="2"  <s:if test="#request.userVO.loginType==2">checked="checked"</s:if>/>用户名+密码+短信验证
            <span id="loginType"></span>
        </li>
        </s:if>
        <s:if test="#session.SESSION_USER_INFO.userType == 0">
			<s:if test="#request.regionList != null">
				<label>选择用户省份：</label>
		        <select id="userAdd_province" name="portalUser.province" style="width: 180px; height:23px;" size="1">
		            <s:iterator id="region" value="#request.regionList">
		                <option value="${region.id }">${region.name}</option>
		            </s:iterator>
		        </select>
		        <span id="provinceResult"></span>
	    	</s:if>
    	</s:if>
        <s:if test="#session.SESSION_USER_INFO.userType == 1">
			<s:if test="#request.regionList != null">
			<li>
			
				<label>选择用户地区：</label>
		        <select id="userAdd_city" name="portalUser.city" style="width: 180px; height:23px;" size="1">
		            <s:iterator id="region" value="#request.regionList">
		                <option value="${region.id }">${region.name}</option>
		            </s:iterator>
		        </select>
		        <span id="cityResult"></span>
		        </li>
	    	</s:if>
    	</s:if>
    	<s:if test="#session.SESSION_USER_INFO.userType == 2">
			<%-- <s:if test="#request.merchantList != null"> --%>
			<li >
				<label>选择用户企业：</label>
		        <select id="userAdd_merchantvip" name="portalUser.merchantPin" style="width: 180px; height:23px;" size="1">
		            <s:iterator var="merchantVip" value="#request.merchantList">
                                <option value="${merchantVip.merchantPin }" <s:if test="#request.userVO.merchantPin==#merchantVip.merchantPin">selected</s:if>>${merchantVip.name}</option>
		            </s:iterator>
		        </select>
		        <span id="merchantVipResult"></span>
		        </li>
	    	<%-- </s:if> --%>
    	</s:if>
    	<s:if test="#session.SESSION_USER_INFO.userType == 3">
    	<li >
			<label>用户类型：</label>
	        <select id="userAdd_webservice" name="portalUser.webService" style="width: 180px; height:23px;" size="1">
        		<option value="1" <s:if test="#request.userVO.webService==1">selected="selected"</s:if>>页面</option>
        		<option value="2" <s:if test="#request.userVO.webService==2">selected="selected"</s:if>>webservice</option>
	        </select>
	        <span id="merchantVipResult"></span>
	    </li>
	    </s:if>
    	<s:if test="#session.SESSION_USER_INFO.userType != 0 && #session.SESSION_USER_INFO.userType != 1">
        <li>
            <label><span>*</span>短信扩展码：</label>
            <input style="width:65px;" type="text" maxlength="2" name="portalUser.userExtCode" id="userAdd_userExtCode" value="${userVO.userExtCode }"/>
            <span id="CodeResult"></span>
        </li>
        <!-- <li> remove sunyadong
            <label>短信网关接入号：</label>
            <input style="width:95px;" maxlength="12" id="userAdd_corpAccessNumber"
              <%-- 用于判断当前登录的用户是否是地市 管理 员，如果是，则网关接入号可写入 --%>
              <%--<s:if test="#request.userVO !=null ">value="${userVO.corpAccessNumber}"</s:if> --%>
            <%-- 用于判断当前登录的用户是否是企业 管理 员，如果是，则网关接入号不可写入 --%>
            <%--<s:if test="#session.SESSION_USER_INFO.userType == 3 || #session.SESSION_USER_INFO.userType == 4"> readonly="readonly" value="${SESSION_USER_INFO.corpAccessNumber}"</s:if>
            <s:if test="#session.SESSION_USER_INFO.userType == 2">value="10657311"</s:if> --%>
           />
            <span id="corpAccessNumberResult"></span>
        </li> -->
        <li>
            <label>企业端口号：</label>
            <input style="width:95px;" name="portalUser.corpAccessNumber" maxlength="12" id="userAdd_corpAccessNumber" readonly="readonly"
              <%-- 用于判断当前登录的用户是否是地市 管理 员，如果是，则网关接入号可写入 --%>
            <%-- 用于判断当前登录的用户是否是企业 管理 员，如果是，则网关接入号不可写入 --%>
            <s:if test="#session.SESSION_USER_INFO.userType == 3 || #session.SESSION_USER_INFO.userType == 4">value="${request.corpLoginPort}"</s:if>
           />
            <span id="corpAccessNumberResult"></span>
        </li>
        </s:if>
        <s:if test="#session.SESSION_USER_INFO.userType!=@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN">
        <li>
            <ul  class="rolelabel">
                <li><label><span>*</span>用户所属角色：</label></li>
            </ul>            
            <ul  class="unselectrolemargin">
                <li>
                    <span>待选角色:</span>
                </li>
                <li>
                    <select  id="userAdd_selectL" name="selectL" multiple="multiple" style="width: 180px" size="6">
                        <s:iterator id="role" value="#request.rolesList">
                            <option value="${role.id }">${role.name}</option>
                        </s:iterator>
                    </select>
                </li>
            </ul>
            <ul class="roleselectmargin">
                <li>
                    <input type="button" id="toright" value="&gt;&gt;" />
                </li>
                <li>
                    <input type="button" id="toleft" value="&lt;&lt;" />
                </li>
            </ul>
            <ul>
                <li>
                    <span>已选角色:</span>
                </li>
                <li>
                    <select id="userAdd_selectR" name="multiUserRoles" multiple="multiple" style="width: 180px" size="6">
                        <s:iterator id="role" value="#request.userVO.role">
                            <option value="${role.id }">${role.name}</option>
                        </s:iterator>
                    </select>
                </li>                
            </ul>
        </li>
        <li class="extrainputlisttip">
            <span id="userroleResult"></span>
        </li>
        </s:if>
        <li class="btn">
            <a href="javascript:void(0);" id="userAdd_sub">提交</a>
            <a href="javascript:void(0);" id="userAdd_clear">取消</a>
        </li>
    </ul>
</form>
<!--<ul class="rightbox">
    <li class="title">
        <span>所有用户</span>
    </li>
    <li>
        <input name="account" type="text" id="userAdd_searchAccount" class="input_search" 
               onkeyup="javascript: queryUser(this.value)"/>
        <span id="userAdd_search1" class="search_icon" onclick="clearSearch();"></span>
    </li>
    <li>
        <ul id="userAdd_queryContext">
        </ul>
    </li>
</ul>-->
<script type="text/javascript">
    var user_tip = "（6~20个字符，只能包含字母/数字/下划线）"
    var pass_tip = "（8~20个字符，区分大小写）";
    var pass_tip2= "（必须与密码相同）";
    var leftSel = $("#userAdd_selectL");
    var rightSel = $("#userAdd_selectR");
    $(function() {
        clearHtml();
        if($("#flag").val() == "updateUser"){
            // 从用户列表进来的
            $("#oldRoles").val($("#allSelect").html());
        }else{
            // 从右侧搜索框进来的
            $("#oldRoles").val($("#userAdd_selectL").html());
        }
	
        $("#loginAccount").focus(function(){
            if($("#loginAccount").val() == user_tip)
                $("#loginAccount").val("");
        }).focusout(function() {
            if($("#loginAccount").val() == ""){
                $("#loginAccount").val(user_tip);
            }else{
                checkUser("#loginAccount", user_tip, "#userResult", false);
            }
		
        });

        //对密码框进行操作 
        if($("#userAdd_userId").val() == ""){
            $("#loginPwd").hide();
            $("#pwd_text").focus(function(){ 
                if($("#pwd_text").val()== pass_tip){
                    $("#pwd_text").hide();
                } 
                $("#loginPwd").show().focus(); 
                //$("#portalUser.loginPwd").show(); 
                $("#loginPwd").addClass("focus"); 
            }); 
            $("#loginPwd").blur(function(){ 
                if($("#loginPwd").val()==""){ 
                    $("#loginPwd").hide(); 
                    $("#pwd_text").val(pass_tip);
                    $("#pwd_text").show(); 
                }else{
                    checkPWD( "#loginPwd" , pass_tip, "#pass1Result", false);
                }
                $(this).removeClass("focus"); 
            }); 
		 
            //对校验密码框进行操作 
            $("#pass2").hide(); 
            $("#pwd_text2").focus(function(){ 
                if($("#pwd_text2").val()== pass_tip2){
                    $("#pwd_text2").hide();
                } 
                $("#pass2").show().focus(); 
                //$("#portalUser.loginPwd").show(); 
                $("#pass2").addClass("focus"); 
            }); 
            $("#pass2").blur(function(){ 
                if($("#pass2").val()==""){ 
                    $("#pass2").hide(); 
                    $("#pwd_text2").val(pass_tip2);
                    $("#pwd_text2").show(); 
                }else{
                    checkPWD2( "#loginPwd" , "#pass2", pass_tip2,
                    "#pass2Result", false);
                }
                $(this).removeClass("focus"); 
            }); 
        }
        $("#userAdd_name").focusout(function() {
            checkName("#userAdd_name",  "#usernameResult", false);
        });
        //扩展码验证 
        $("#userAdd_userExtCode").focusout(function() {
        	checkUserExtCoding("#userAdd_userExtCode","#CodeResult", false, 2);
        });
        //短信接入号不能为空
         /*$("#userAdd_corpAccessNumber").focusout(function() {
        	 checkCorpAccessNumber("#userAdd_userType","#userAdd_userId","#userAdd_corpAccessNumber","#corpAccessNumberResult", false);
        });*/
        
        
        
        $("#userAdd_ip").focusout(function() {
            checkIP("#userAdd_ip", "#ipResult", false);
        });
        $("#userAdd_mobile").focusout(function() {
            checkMobile("#userAdd_mobile", "#mobileResult", false);
        });
        $("#userAdd_email").focusout(function() {
            checkEmail("#userAdd_email", "#emailResult", false);
        });

        $("#toright").unbind("click").bind("click", function() {
            leftSel.find("option:selected").each(function() {
                $(this).remove().appendTo(rightSel);
            });
        });
        $("#toleft").unbind("click").bind("click", function() {
            rightSel.find("option:selected").each(function() {
                $(this).remove().appendTo(leftSel);
            });
        });
        leftSel.unbind("dblclick").dblclick(function() {
            $(this).find("option:selected").each(function() {
                $(this).remove().appendTo(rightSel);
            });
        });
        rightSel.unbind("dblclick").dblclick(function() {
            $(this).find("option:selected").each(function() {
                $(this).remove().appendTo(leftSel);
            });
        });
	
        $("#userAdd_clear").unbind("click").click(function(){
            if(confirm("是否取消本次用户编辑？")){
                $("#userAdd_selectR").find("option").each(function() {
                    // 清除已选的用户
                    $(this).remove().appendTo(leftSel);
                });
                //$("#userAdd_form").clearForm();
                $("#loginAccount").val("（6~20个字符，只能包含字母/数字/下划线）");
                $("#loginPwd").val("").hide(); 
                $("#pwd_text").val("（8~20个字符，区分大小写）").show();
                pwStrength('');
                $("#pass2").val("").hide(); 
                $("#pwd_text2").val("（必须与密码相同）").show();
                //短信接入口
                $("#userAdd_corpAccessNumber").val("");
                $("#userAdd_userExtCode").val("");
                $("#userAdd_name").val("");
                $("#userAdd_mobile").val("");
                $("#userAdd_email").val("");
               	// $("#userAdd_ip").val("");
			
               	$("input[name=portalUser.gender][value=1]").attr("checked",true);
               	$("input[name=portalUser.activeFlag][value=1]").attr("checked",true);
                $("input[name=portalUser.ipLimitFlag][value=0]").attr("checked",true);
                $("#userAdd_ip").attr("disabled", true);
                $("#userAdd_ip").val("");
			
                
                $("span[id$=Result]").removeAttr("class").html("");
                if ($("#userAdd_userId").val() != "") {
                    $("#loginPwd").hide(); $("#pass2").hide();
                    $("#pwd_text").show(); $("#pwd_text2").show();
        	        
                    forwardUserList();
                }
            }
           
        });
        $("#userAdd_sub").unbind("click").click(function() {
            var options = { 
                beforeSubmit:  userAddValidate,  // pre-submit callback 
                success:       userAddSuccess,  // post-submit callback 
                dataType:		'json'
            }; 
            if($("#userAdd_userId").val() != ""){
                $("#userAdd_form").attr("action", "./userAction/updateUser.action")
            }
            // 将右边已选框里的列表选定，这样，就能传到后台了。
            $("#userAdd_selectR").find("option").attr("selected", "selected");
            // 提交表单
            $("#userAdd_form").ajaxSubmit(options); 
        });
        //加入地市管理员加用户自动显示对应的短信接入号
        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN">
            <s:if test="#request.merchantList==null || #request.merchantList.size()==0">
                alert("请先添加企业信息!");
            </s:if>
            if($("#userAdd_merchantvip").val() != "") {
                $.ajax({
                    url: "./userAction/getSmTunnelByMerchantPin.action",
                    type:"POST",
                    data:"portalUser.merchantPin="+$("#userAdd_merchantvip").val(),
                    dataType: 'json',
                    success:function(data){
                    	if(data.resultData==null||data.resultData.itemValue==null){
                    		alert("企业端口号未配置，请联系管理员！");
                    	}else{
                    		$("#userAdd_corpAccessNumber").val(data.resultData.itemValue);
                        	$("#userAdd_corpAccessNumber").attr("readonly","true");
                    	}
                    },
                    error:function(data){
                    }
                });
            }
            $("#userAdd_merchantvip").change(function(){
            	$("#userAdd_corpAccessNumber").val("");
                $.ajax({
                    url: "./userAction/getSmTunnelByMerchantPin.action",
                    type:"POST",
                    data:"portalUser.merchantPin="+$(this).val(),
                    dataType: 'json',
                    success:function(data){
                    	if(data.resultData==null||data.resultData.itemValue==null){
                    		alert("企业端口号未配置，请联系管理员！");
                    	}else{
                    		$("#userAdd_corpAccessNumber").val(data.resultData.itemValue);
                        	$("#userAdd_corpAccessNumber").attr("readonly","true");
                    	}
                    },
                    error:function(data){
                    }
                });
            });
        </s:if>
        <s:if test="#session.SESSION_USER_INFO.userType!=@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN">
        <s:if test="#request.rolesList==null || #request.rolesList.size() == 0">
            if($("#flag").val() == "save"){
                alert("当前系统中不存在角色,请先添加角色");
            }            
        </s:if>
        </s:if>
    });
    
    /**
     *
     * 提交表单成功后处理方法
     *
     */  
    function userAddSuccess(responseText, statusText, xhr, $form){
        var suc_msg = "";
        var err_msg = "";
        if ($("#userAdd_userId").val() != "") {
            suc_msg = "用户修改成功";
            err_msg = "用户修改失败，请稍后重试";
        }else{
            suc_msg = "用户新增成功";
            err_msg = "用户新增失败，请稍后重试";
        }
        if(responseText.flag == "success" ){
            alert(suc_msg);
            forwardUserList();
        }else{
            alert(err_msg);
        }
    }
    /**
     * 表单验证
     *
     */
    function userAddValidate(formData, jqForm, options){
        if (!checkUser("#loginAccount", user_tip, "#userResult", true)
            || !checkPWD( "#loginPwd" , pass_tip, "#pass1Result", true)
            || !checkPWD2( "#loginPwd" , "#pass2", pass_tip2, "#pass2Result", true)
            || !checkName("#userAdd_name",  "#usernameResult", true)
            || !checkMobile("#userAdd_mobile", "#mobileResult", true)
            || !checkEmail("#userAdd_email", "#emailResult", true)) {
    	
            return false;
        }
        if($("#userAdd_userType").val()!=0
        			&&$("#userAdd_userType").val()!=1){

            if( !checkUserExtCoding("#userAdd_userExtCode","#CodeResult",true, 2)
                    //|| !checkCorpAccessNumber("#userAdd_userType","#userAdd_userId","#userAdd_corpAccessNumber","#corpAccessNumberResult", false)
                    )
            	return false;
            if($("#userAdd_corpAccessNumber").val() == ""){
                //$("#corpAccessNumberResult").html("企业端口号不能为空");
                checkCorpAccessNumber("#userAdd_userType","#userAdd_userId","#userAdd_corpAccessNumber","#corpAccessNumberResult", false)
                return false;
            }
        }
        if ($("input[name='portalUser.ipLimitFlag']:checked").val() == 1 
            && (!checkIP("#userAdd_ip", "#ipResult", true))) {
            return false;
        }
        <s:if test="#session.SESSION_USER_INFO.userType!=@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN">
        // 验证角色选择框
        var selVal = [];
        rightSel.find("option").each(function() {
            selVal.push(this.value);
        });
        selVals = selVal.join(",");
        if (selVals == "") {
            $("#userroleResult").addClass("input_verify_error").html("至少为用户分配一种角色")
            leftSel.focus();
            return false;
        }
        </s:if>
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
            $("#setpercentcur").css("width","0px");        
        } else {
            S_level = checkStrong(pwd); 
            switch (S_level) {
                case 0: // 低(初始化)
                    $("#setpercentcur").css("width","25px");
                    slevel = "低";
                case 1: // 低
                    $("#setpercentcur").css("width","25px");
                    slevel = "低";
                    break;
                case 2: // 中
                    $("#setpercentcur").css("width","50px");
                    slevel = "中";
                    break;
                case 3: // 中高
                    $("#setpercentcur").css("width","75px");
                    slevel = "中高";
                    break;
                default:
                    $("#setpercentcur").css("width","100px");
                    slevel = "高";
            }
        }
        $("#pswd_strong").html(slevel);
        return;
    }
    /**
     *  ip是否限制与IP地址关联效果
     */
    function checks() {
        if ($("input[name='portalUser.ipLimitFlag']:checked").val() == 1) {
            $("#userAdd_ip").attr("disabled", false);
        } else {
            $("#userAdd_ip").attr("disabled", true);
            $("#ipResult").removeAttr("class").html("");
        }
    }
    /**
     *
     * 右边通讯录的查询
     * userId, pwd, name, gender, mobile, email, activeFlag, ipLimitFlag, ipAddress, roles
     */
    function queryUser(queryAccount){
        if(queryAccount == "" || queryAccount == null){
            clearSearch();
            return false;
        }
        $("#userAdd_search1").removeAttr("class").addClass("search_icon_current");
        //.html("<img onclick='javascript:clearSearch();' src='./themes/mas3/images/user/u148_normal.png' />");
        $.ajax({ url: "./userAction/queryUserExist.action",
            data : "flag=query&portalUser.account="+ queryAccount,
            //context: document.body, 
            dataType: "json",
            type: "post",
            success: function(res){//alert(res);
                var result = "";
                for(var i=0,len=res.users.length; i<len; i++ ){
                    var user = res.users[i];
                    var arr_roles= new Array();
                    var tmpRoles = user.role;
                    var roles = "[";
                    for(var j in user.role){//alert(arr_role)
                        roles +="{'id':'"+user.role[j].id+"','name':'"+user.role[j].name+"'},";
                    }
                    roles = roles.substring(0,roles.length-1);
                    roles+="]";
                    result+="<li><label id='"+user.id+"' onclick=\"javascript: editUser('"+user.id+"','"+user.account+"','"+user.password+"','"+user.name+"',"+user.gender+",'"+
                        user.mobile+"','"+user.email+"',"+user.activeFlag+","+user.ipLimitFlag+",'"+user.ipAddress+"',"+roles+",'"+user.userExtCode+",'"+user.corpAccessNumber+")\">"+user.account+"</label></li></br>";
                }
                $("#userAdd_queryContext").html(result);
			 		
            }});
    }
    /*
     * 清除搜索框内容
     */
    function clearSearch(){
        $("#userAdd_searchAccount").val("");
        $("#userAdd_queryContext").html("");
        $("#userAdd_search1").removeAttr("class").addClass("search_icon");
        $("#userAdd_searchAccount").focus();
    }
    /*
     * 从右边通讯录里单击，将对应的属性填充到左边对应的文本框里
     * 
     *  注： 用户名不能修改
     * 
     */
    function editUser(userId, account, pwd, name, gender, mobile, email, activeFlag, ipLimitFlag, ipAddress, roles,userExtCoding,corpAccessNumber){
    	console.log(userId);
        //$("input").removeAttr("checked");
        //$("#flag").val("update");
        //$("#f1").attr("action", "./userAction/adduser.action");
        $("#userAdd_userId").val(userId);
        //$("#loginAccount").hide();$("#loginAccount").attr("disabled", true);$("#labelAccount").text(account).show();
        $("#loginAccount").attr("readonly", 'readonly').val(account);
        $("#pwd_text").hide(); $("#loginPwd").val(pwd).show();pwStrength(pwd);
        $("#pwd_text2").hide(); $("#pass2").val(pwd).show();
        $("#userAdd_name").val(name);
        $("#userAdd_mobile").val(mobile);
        $("#userAdd_userExtCode").val(userExtCoding);
        $("#userAdd_email").val(email);
        $("#userAdd_corpAccessNumber").val(corpAccessNumber);
        $("input[name=portalUser.gender][value="+gender+"]").attr("checked",true);
        $("input[name=portalUser.activeFlag][value="+activeFlag+"]").attr("checked",true);
        $("input[name=portalUser.ipLimitFlag][value="+ipLimitFlag+"]").attr("checked",true);
        if(ipLimitFlag == 1){
            $("#userAdd_ip").attr("disabled", false);
            $("#userAdd_ip").val(ipAddress);
        }else{
            $("#userAdd_ip").val("");
            $("#userAdd_ip").attr("disabled", true);;
        }
        $("#userAdd_selectL").html("");
        $("#userAdd_selectL").append($("#oldRoles").val());
        $("#userAdd_selectR").html("");//alert(roles.length);
        // 角色   在待选框里先删除用户已经存在的角色，然后把用户已经有的角色放在右边
        for(var i=0, len=roles.length; i<len ; i++){ 
            $("#userAdd_selectL").find("option").each(function() {
                if($(this).val() == roles[i].id){
                    $(this).remove();
                    $("#userAdd_selectR").append("<option value='"+roles[i].id+"'>"+roles[i].name+"</option>");
                }
            });
        }
        clearHtml();
    }
    
    /**
     *
     * 用户列表跳转
     *
     */
    function forwardUserList(){
        var originalUrl = "./userAction/queryForward.action";
        var tempUrl = "./userAction/queryForward.action";
        var localUrl = "./userAction/forward.action?flag=addForward";
        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
        tabpanel.kill(_killId);
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    }
    
    /**
     * 清除结果信息
     */
    function clearHtml(){
        $("span[id$=Result]").removeAttr("class").html("");
        //$("#resourcesResult").removeAttr("class").html("");
    }
</script>
