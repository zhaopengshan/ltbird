<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <base href="${pageContext.request.requestURL}" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0"> 
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
        <!--[if lt IE 9]>
        <link href="./themes/mas3admin/css/ie8css.css" rel="stylesheet" type="text/css" />
        <![endif]-->
        <!--[if IE 9]>
        <link href="./themes/mas3admin/css/css.css" rel="stylesheet" type="text/css" />
         <![endif]-->
        <!--[if !IE]><!-->
        <link href="./themes/mas3admin/css/css.css" rel="stylesheet" type="text/css" />
        <!--<![endif]-->
        <link href="./themes/mas3admin/css/TabPanel.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="./themes/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="./themes/js/TabPanel.js"></script>
		<script type="text/javascript" src="./themes/js/Fader.js"></script>
        <link href="./themes/mas3admin/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet" />
        <script language="javascript" src="./themes/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="./themes/js/validate.js"></script>
        <script type="text/javascript" defer="defer"  src="./themes/js/datepicker/WdatePicker.js"></script>
        <script type="text/javascript" src="./themes/js/jquery.form.js"></script>
        <script type="text/javascript" src="./themes/mas3/js/jquery.ztree.core-3.4.js"></script>
        <script language="javascript" src="./themes/mas3/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
		<!--<script language="javascript" src="./sms/smssend/js/info_right_menu.js" type="text/javascript" ></script>-->
		<script language="javascript" src="./sms/smssend/js/sms_info.js" type="text/javascript" ></script>
		<script type="text/javascript" src="./themes/js/tableGrid.js"></script>
        <title>中国移动云MAS管理平台</title>
        <s:set name="currentdate" value="@com.leadtone.mas.admin.util.DateUtils@getToday()" />
    </head>
    <body>        
        <div class="tabContent"></div>
        <div class="nav" style="position:relative;">
                <div class="main_logo"></div>
                <div class="shortcut_function_nav">
                	<div style="position:absolute;top:7px; right:200px;">
                	<s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN"> 
	                            <label>地市管理员：</label>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_PROVINCE_ADMIN">
	                            <label>省管理员：</label>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_SUPER_ADMIN">
	                        	<label>超级管理员：</label>
	                        </s:if> 
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_ENTERPRISE_ADMIN">
	                        	<label>企业管理员：</label>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_ENTERPRISE_NORMAL">
	                        	<label>用户：</label>
	                        </s:if>
	                       	<label id="userName"></label>
	                    </div>
                    <div class="function_normal function_icon">
                        <p style="margin:0px; padding:0px;">
                            <a href="javascript:void(0);" id="changeloginpswd" tabid="changeloginpswd111" taburl="./ap/user/userpwd.jsp" style="padding-left:45px;">修改密码</a>
                            <a href="security/logout.action" style="padding-left:15px;">退出</a>
                        </p>
                    </div>
					<div class="function_often" >
	                    <ul style="float:right; margin:0px; padding:0px;">
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN"> 
	                            <li><a href="javascript:void(0);" id="gotocorpmanage" class="function_icon icon_cjgl">&nbsp;</a></li>
	                            <li><a href="javascript:void(0);" id="gotousermanage" class="function_icon icon_lypz">&nbsp;</a></li>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_PROVINCE_ADMIN">
	                            <li><a href="javascript:void(0);" id="gotocorpmanage" class="function_icon icon_cjgl">&nbsp;</a></li>
	                            <li><a href="javascript:void(0);" id="gotousermanage" class="function_icon icon_gjgl" />&nbsp;</a></li>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_SUPER_ADMIN">
	                            <li><a href="javascript:void(0);" id="gotousermanage" class="function_icon icon_txl">&nbsp;</a></li>
	                        </s:if>    
	                        <li><a class="function_icon icon_home" href="./main.jsp">&nbsp;</a></li>
	                    </ul>
					</div>
               </div>
                <div class="welcome_inf"><strong>欢迎您，</strong><span><s:if test="#session.SESSION_USER_INFO.name==null|| #session.SESSION_USER_INFO.name==''"><s:property value="#session.SESSION_USER_INFO.account" /></s:if><s:else><s:property value="#session.SESSION_USER_INFO.name" /></s:else></span> <span>今天是<s:date name="#currentdate" format="yyyy年MM月dd日" /> <s:property value="@com.leadtone.mas.admin.util.DateUtils@getWhatDayToday()" /></span></div>
            
        </div>
        <div class="work_view">
                <div class="menu_view">
                    <div class="title">功能导航</div>
                   
                        <ul class="menu_items">
                            <s:iterator value="#session.resources" var="parentResource">
                                <li class="<s:property value="#parentResource.icon"/>"><span class="api-icon"></span><span><s:property value="#parentResource.name"/></span></li>
                                <li>
                                    <ul>
                                        <s:iterator value="#parentResource.sortedSubRes" var="subResource">
                                            <li><a href="javascript:void(0)" tabid="<s:property value="#subResource.id" />" taburl="<s:property value="#subResource.url" />"><s:property value="#subResource.name" /></a></li>
                                        </s:iterator>
                                    </ul>
                                </li>
                            </s:iterator>                            
                        </ul>
          			</div>
                <div class="content_view">
                    <div class="content_title"><span id="navpath" style="padding-left:10px;"></span></div>
                    <div class="content">
                        <div id="tab">

                        </div>
                    </div>
                </div>
        </div>
        <div class="copy">Copyright(@)2012-2013 中国移动通信 版权所有</div>
    </body>
    <script type="text/javascript">
    	//清除AJAX缓存设置
		$.ajaxSetup ({
			cache: false //close AJAX cache
		});
        var tabpanel;  
        var tabContentClone = $(".tabContent").clone();
        var jcTabs = tabContentClone.removeClass("tabContent").load("welcome.jsp");
        var tabheight=$(".work_view").css('height');
        if( tabheight && tabheight != undefined && tabheight.toLowerCase().indexOf('px')> 0){
        	tabheight=parseInt(tabheight)*0.93 + 'px';
        }
        $(document).ready(function(){
        	//add wangyu 2013.2.1 主页显示登陆者姓名
        	if("${sessionScope.SESSION_USER_INFO.name}".length>8){
        		console.log("${sessionScope.SESSION_USER_INFO.name}".substring(0,8));
        		$("#userName").html("${sessionScope.SESSION_USER_INFO.name}".substring(0,8));
        	}else{
        		$("#userName").html("${sessionScope.SESSION_USER_INFO.name}");
        	}
            $("#navpath").html("当前位置：欢迎页");
            tabpanel = new TabPanel({  
                renderTo:'tab',  
                width:'100%',  
                height:tabheight,  
                //border:'none',  
                active : 0,
                //maxLength : 10,  
                items : [
                    {id:'welcome',title:'欢迎页',html:jcTabs,closable: false}
                ]
            });
            $(".menu_items > li").each(function(){
                $(this).find("span").click(function(){
                    if($(".menu_items > li").index($(".menu_items > li").filter(".current").first()) == $(".menu_items > li").index($(this).parent())) {
                        $(".menu_items > li").filter(".current").first().removeClass("current").next().removeClass("current").find("ul").hide("10");
                    } else {
                        $(".menu_items > li").filter(".current").first().removeClass("current").next().removeClass("current").find("ul").hide("10");
                        $(this).parent().addClass("current").next().addClass("current").find("ul").show("50");
                    }
                });
            });
            $(".menu_items > li > ul > li").each(function(){
                $(this).find("a").click(function(){
                    $(".on").removeClass("on");
                    $(this).addClass("on");
                    var tabid=$(this).attr("tabid");
                    var taburl=$(this).attr("taburl");
                    var tabTitle=$(this).text();
                    var navpathtitle="当前位置："+$(this).parent().parent().parent().prev().children().eq(1).html()+" > "+tabTitle;
                    $("#navpath").html(navpathtitle);
                    tabContentClone = $(".tabContent").clone();
                    jcTabs = tabContentClone.removeClass("tabContent").load(taburl);
                    try{
                    	// 新建页面前，先kill
                    	tabpanel.kill(tabid);
                    }catch(e){
                    }
                    tabpanel.addTab({
                        id: tabid,
                        title: tabTitle ,     
                        html:jcTabs,     
                        closable: true
                    }); 
                });
            });
            $(".tabpanel_mover > li").live("click",function(){
                var currentParentMenuText = $(".on").parent().parent().parent().prev().children().eq(1).html();
                $(".on").removeClass("on");
                var tabobject = tabpanel.getActiveTab();
                var navpathparenttitile=$('a[tabid="'+tabobject.id+'"]').parent().parent().parent().prev().children().eq(1).html();
                var selectedParentMenuText = navpathparenttitile;
                if(navpathparenttitile == null || navpathparenttitile == "") {
                    navpathparenttitile = "";
                } else {
                    navpathparenttitile = navpathparenttitile+" > ";
                }
                var navpathtitle="当前位置："+navpathparenttitile+tabpanel.getTitle(tabobject.id);
                $('a[tabid="'+tabobject.id+'"]').addClass("on");
                if(currentParentMenuText != selectedParentMenuText) {
                    $('a[tabid="'+tabobject.id+'"]').parent().parent().parent().prev().children().eq(1).click();
                }
                $("#navpath").html(navpathtitle);
            });
            $("#changeloginpswd").click(function(){
                var tabid=$(this).attr("tabid");
                var taburl=$(this).attr("taburl");
                var tabTitle=$(this).text();
                var navpathtitle="当前位置："+tabTitle;
                $("#navpath").html(navpathtitle);
                tabContentClone = $(".tabContent").clone();
                jcTabs = tabContentClone.removeClass("tabContent").load(taburl);
                tabpanel.addTab({
                    id: tabid,
                    title: tabTitle ,     
                    html:jcTabs,     
                    closable: true
                }); 
            });
            /*function fastgoto(){
                var taburl=$(this).attr("taburl");
                var tabid=$('.menu_items a[taburl="'+taburl+'"]').attr("tabid");
                var tabTitle=$('.menu_items a[taburl="'+taburl+'"]').html();
				if( isNaN(tabid)){
					alert("页面获取错误");
					return;
				}
                var navpathtitle="当前位置："+tabTitle;
                $("#navpath").html(navpathtitle);
                tabContentClone = $(".tabContent").clone();
                jcTabs = tabContentClone.removeClass("tabContent").load(taburl);
                tabpanel.addTab({
                    id: tabid,
                    title: tabTitle ,     
                    html:jcTabs,     
                    closable: true
                }); 
            }
            $("#gotoaddressbook").unbind("click").bind("click", fastgoto);
            $("#gotousermanage").unbind("click").bind("click", fastgoto);
            $("#gotorolemanage").unbind("click").bind("click", fastgoto);
            $("#gotosyssetting").unbind("click").bind("click", fastgoto);*/
            <%
            	String delegatemas=com.leadtone.mas.bizplug.util.WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.DELEGATEMAS);
            
            %>
            if(<%=delegatemas%>){
            $("#gotocorpmanage").click(function(){
                <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN">
                    $(".menu_items > li > ul > li > a:[taburl='./delegatemas/corpmanage/corplist.jsp']").parent().parent().parent().prev().children().eq(1).click();    
                    $(".menu_items > li > ul > li > a:[taburl='./delegatemas/corpmanage/corplist.jsp']").click();
                </s:if>
                <s:else>
                    $(".menu_items > li > ul > li > a:[taburl='./delegatemas/corpmanage/corpcountbycitylist.jsp']").parent().parent().parent().prev().children().eq(1).click(); 
                    $(".menu_items > li > ul > li > a:[taburl='./delegatemas/corpmanage/corpcountbycitylist.jsp']").click();    
                </s:else>    
                });
            }else{
            if($("#gotocorpmanage")){
                $("#gotocorpmanage").click(function(){
                <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN">
                    $(".menu_items > li > ul > li > a:[taburl='./ford/corpmanage/corplist.jsp']").parent().parent().parent().prev().children().eq(1).click();    
                    $(".menu_items > li > ul > li > a:[taburl='./ford/corpmanage/corplist.jsp']").click();
                </s:if>
                <s:else>
                    $(".menu_items > li > ul > li > a:[taburl='./ford/corpmanage/corpcountbycitylist.jsp']").parent().parent().parent().prev().children().eq(1).click(); 
                    $(".menu_items > li > ul > li > a:[taburl='./ford/corpmanage/corpcountbycitylist.jsp']").click();    
                </s:else>    
                });
            }
            }
        });
        $("#gotousermanage").click(function(){
            $(".menu_items > li > ul > li > a:[taburl='./userAction/queryForward.action']").parent().parent().parent().prev().children().eq(1).click();
            $(".menu_items > li > ul > li > a:[taburl='./userAction/queryForward.action']").click();            
        });
    </script>
</html>
