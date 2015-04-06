<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <base href="${pageContext.request.requestURL}" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0"> 
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
        <!--[if lt IE 9]>
        <link href="${ctx}/themes/mas3admin/css/ie8css.css" rel="stylesheet" type="text/css" />
        <![endif]-->
        <!--[if IE 9]>
        <link href="${ctx}/themes/mas3admin/css/css.css" rel="stylesheet" type="text/css" />
         <![endif]-->
        <!--[if !IE]><!-->
        <link href="${ctx}/themes/mas3admin/css/css.css" rel="stylesheet" type="text/css" />
        <!--<![endif]-->
        <link href="${ctx}/themes/mas3admin/css/TabPanel.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${ctx}/themes/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="${ctx}/themes/js/TabPanel.js"></script>
		<script type="text/javascript" src="${ctx}/themes/js/Fader.js"></script>
        <link href="${ctx}/themes/mas3admin/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet" />
        <script language="javascript" src="${ctx}/themes/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${ctx}/themes/js/validate.js"></script>
        <script type="text/javascript" src="${ctx}/themes/js/jquery.form.js"></script>
        <title>中国移动云MAS管理平台</title>
        <s:set name="currentdate" value="@com.leadtone.mas.admin.util.DateUtils@getToday()" />
    </head>
    <body>        
        <div class="tabContent"></div>
        <div class="nav" style="position:relative;">
                <div class="main_logo"></div>
                <div class="shortcut_function_nav">
                	<div style="position:absolute;top:7px; right:200px;">
                		<label>用户：</label>
	                   	<label id="userName"><%=account %></label>
	                </div>
                    <div class="function_normal function_icon">
                        <p style="margin:0px; padding:0px;">
                            <a href="javascript:void(0);" style="padding-left:45px;">修改密码</a>
                            <a href="security/logout.action" style="padding-left:15px;">退出</a>
                        </p>
                    </div>
					<div class="function_often" >
	                    <ul style="float:right; margin:0px; padding:0px;">
	                        <!--<s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_CITY_ADMIN"> 
	                            <li><a href="javascript:void(0);" id="gotocorpmanage" class="function_icon icon_cjgl">&nbsp;</a></li>
	                            <li><a href="javascript:void(0);" id="gotousermanage" class="function_icon icon_lypz">&nbsp;</a></li>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_PROVINCE_ADMIN">
	                            <li><a href="javascript:void(0);" id="gotocorpmanage" class="function_icon icon_cjgl">&nbsp;</a></li>
	                            <li><a href="javascript:void(0);" id="gotousermanage" class="function_icon icon_gjgl" />&nbsp;</a></li>
	                        </s:if>
	                        <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_SUPER_ADMIN">
	                            <li><a href="javascript:void(0);" id="gotousermanage" class="function_icon icon_txl">&nbsp;</a></li>
	                        </s:if>    -->
	                        <li><a class="function_icon icon_home" href="${ctx}/main.jsp">&nbsp;</a></li>
	                    </ul>
					</div>
               </div>
                <div class="welcome_inf"><strong>欢迎您，</strong><span><%=account %></span>
                <span>今天是<s:date name="#currentdate" format="yyyy年MM月dd日" /> 
                <s:property value="@com.leadtone.mas.admin.util.DateUtils@getWhatDayToday()" /></span></div>
        </div>
        <div class="work_view">
                <div class="menu_view">
                    <div class="title">功能导航</div>
                   
                        <ul class="menu_items">
                            <!--<s:iterator value="#session.resources" var="parentResource">
                                <li class="<s:property value="#parentResource.icon"/>"><span class="api-icon"></span><span><s:property value="#parentResource.name"/></span></li>
                                <li>
                                    <ul>
                                        <s:iterator value="#parentResource.sortedSubRes" var="subResource">
                                            <li><a href="javascript:void(0)" tabid="<s:property value="#subResource.id" />" taburl="<s:property value="#subResource.url" />"><s:property value="#subResource.name" /></a></li>
                                        </s:iterator>
                                    </ul>
                                </li>
                            </s:iterator>  -->                          
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
            var jcTabs = tabContentClone.removeClass("tabContent").load("${ctx}/mas3/secure/adminFirstuserpwd.jsp");
        </s:if>
        <s:else>
            var jcTabs = tabContentClone.removeClass("tabContent").load("${ctx}/mas3/secure/adminFirstuserpwd.jsp");
        </s:else>    
        var tabheight=$(".work_view").css('height');
        if( tabheight && tabheight != undefined && tabheight.toLowerCase().indexOf('px')> 0){
        	tabheight=parseInt(tabheight)*0.93 + 'px';
        }
        $(document).ready(function(){
        	//add wangyu 2013.2.1 主页显示登陆者姓名
            $("#navpath").html("当前位置：强制修改密码");
            tabpanel = new TabPanel({  
                renderTo:'tab',  
                width:$.browser.msie && ($.browser.version == "6.0") && !$.support.style ? '960px' : '100%',  
                height:tabheight,  
                //border:'none',  
                active : 0,
                //maxLength : 10,  
                items : [
                    {id:'welcome',title:'强制修改密码',html:jcTabs,closable: false}
                ]
            });
        });
    </script>
</html>
