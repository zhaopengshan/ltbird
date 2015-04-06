<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <base href="${pageContext.request.requestURL}" />
        <meta http-equiv="Content-Type" content="text/html" charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"/> 
        <meta http-equiv="expires" content="0"> 
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
      
       <link href="./themes/mas3admin/css/css.css" rel="stylesheet" type="text/css" />
        <link href="./themes/mas3admin/css/TabPanel.css" rel="stylesheet" type="text/css" />
        <link href="./themes/mas3admin/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="./themes/mas3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
        <script type="text/javascript" src="./themes/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="./themes/js/TabPanel.js"></script>
        <script type="text/javascript" src="./themes/js/Fader.js"></script>
        <script language="javascript" src="./themes/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="./themes/js/validate.js"></script>
        <script type="text/javascript" defer="defer"  src="./themes/js/datepicker/WdatePicker.js"></script>
        <script type="text/javascript" src="./themes/js/jquery.form.js"></script>
		<script language="javascript" src="./sms/smssend/js/sms_info.js" type="text/javascript" ></script>
		<script type="text/javascript" src="./themes/mas3/js/jquery.ztree.core-3.4.js"></script>
		<script language="javascript" src="./themes/mas3/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
		<!--<script language="javascript" src="./sms/smssend/js/info_right_menu.js" type="text/javascript" ></script>-->
		<script type="text/javascript" src="./themes/js/tableGrid.js"></script>
                <title>
                    <s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('delegatemas')=='true'">
                        中国移动云MAS平台
                    </s:if>
                    <s:else>
                        中国移动MAS平台
                    </s:else>    
               </title>
    </head>
    <body>        
        <div class="tabContent"></div>
        <div class="nav" style="position:relative;">
                <div class="main_logo" <s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('tuoguan')=='true'">
                style="background: url('${ctx }${home_page_logo }') no-repeat scroll 0 0 transparent;" </s:if>></div>
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
                    <div class="function_normal function_icon_bz">
                        <p style="margin:0px; padding:0px;">
                        	
                            <a href="javascript:void(0);" id="changeloginpswd" tabid="changeloginpswd111" taburl="./ap/user/userpwd.jsp" style="padding-left:45px;">修改密码</a>
                            <a href="${ctx}/masLoginLogicAction/logout.action" style="padding-left:15px;">退出</a>
                        </p>
                    </div>
					<div class="function_often">
	                    <ul style="float:right; margin:0px; padding:0px;" >
	                        <li><a href="javascript:void(0);" id="gotoaddressbook" tabid="1000" taburl="./ap/address/addresslist.jsp" tabtitle="联系人管理" class="function_icon_bz icon_txl">&nbsp;</a></li>
	                        <li><a href="javascript:void(0);" id="gotousermanage" tabid="1001" taburl="./userAction/queryForward.action" tabtitle="用户管理" class="function_icon_bz icon_gjgl">&nbsp;</a></li>
	                        <li><a href="javascript:void(0);" id="gotorolemanage" tabid="1002" taburl="./delegatemas/role/rolelist.jsp" tabtitle="角色管理" class="function_icon_bz icon_lypz">&nbsp;</a></li>
	                        <li><a href="javascript:void(0);" id="gotosyssetting" tabid="1003" taburl="./systemSettingsAction/showParas.action" tabtitle="系统设置" class="function_icon_bz icon_cjgl">&nbsp;</a></li>
	                        <li><a class="function_icon_bz icon_home" href="">&nbsp;</a></li>
	                    </ul>
					</div>
                </div>
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
        <s:if test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('delegatemas')=='true'">
            var jcTabs = tabContentClone.removeClass("tabContent").load("welcome.jsp");
        </s:if>
        <s:else>
            var jcTabs = tabContentClone.removeClass("tabContent").load("welcome_ford.jsp");
        </s:else>    
        var tabheight=$(".work_view").css('height');
        if( tabheight && tabheight != undefined && tabheight.toLowerCase().indexOf('px')> 0){
        	tabheight=parseInt(tabheight)*0.93 + 'px';
        }
        $(document).ready(function(){
        	//add wangyu 2013.2.1 主页显示登陆者姓名
        	if("${sessionScope.SESSION_USER_INFO.name}".length>8){
        		$("#userName").html("${sessionScope.SESSION_USER_INFO.name}".substring(0,8));
        	}else{
        		$("#userName").html("${sessionScope.SESSION_USER_INFO.name}");
        	}
            $("#navpath").html("当前位置：欢迎页");
            tabpanel = new TabPanel({  
                renderTo:'tab',  
                width:$.browser.msie && ($.browser.version == "6.0") && !$.support.style ? '960px' : '100%',  
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
                    tabpanel.addTab({
                        id: tabid,
                        title: tabTitle ,     
                        html:jcTabs,     
                        closable: true
                    }); 
                });
            });
            $(".tabpanel_mover > li").live("click",function(){
                $(".on").removeClass("on");
                var tabobject = tabpanel.getActiveTab();
                var navpathparenttitile=$('a[tabid="'+tabobject.id+'"]').parent().parent().parent().prev().children().eq(1).html();
                if(navpathparenttitile == null || navpathparenttitile == "") {
                    navpathparenttitile = "";
                } else {
                    navpathparenttitile = navpathparenttitile+" > ";
                }
                var navpathtitle="当前位置："+navpathparenttitile+tabpanel.getTitle(tabobject.id);
                $('a[tabid="'+tabobject.id+'"]').addClass("on");
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
            function fastgoto(){
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
            $("#gotosyssetting").unbind("click").bind("click", fastgoto);
        })
    </script>
</html>
