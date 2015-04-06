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
        <link href="${ctx}/themes/mas3admin/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctx}/themes/mas3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
        <script type="text/javascript" src="${ctx}/themes/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="${ctx}/themes/js/TabPanel.js"></script>
        <script type="text/javascript" src="${ctx}/themes/js/Fader.js"></script>
        <script language="javascript" src="${ctx}/themes/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${ctx}/themes/js/jquery.form.js"></script>
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
                <div class="main_logo"></div>
                <div class="shortcut_function_nav">
                	<div style="position:absolute;top:7px; right:200px;">
	                       	<label id="userName"></label>
					</div>
                    <div class="function_normal function_icon_bz">
                        <p style="margin:0px; padding:0px;">
                        	
                            <a href="javascript:void(0);" style="padding-left:45px;">修改密码</a>
                            <a href="${ctx}/masLoginLogicAction/logout.action" style="padding-left:15px;">退出</a>
                        </p>
                    </div>
					<div class="function_often">
	                    <ul style="float:right; margin:0px; padding:0px;" >
	                        <li><a href="javascript:void(0);" tabtitle="联系人管理" class="function_icon_bz icon_txl">&nbsp;</a></li>
	                        <li><a href="javascript:void(0);" tabtitle="用户管理" class="function_icon_bz icon_gjgl">&nbsp;</a></li>
	                        <li><a href="javascript:void(0);" tabtitle="角色管理" class="function_icon_bz icon_lypz">&nbsp;</a></li>
	                        <li><a href="javascript:void(0);" tabtitle="系统设置" class="function_icon_bz icon_cjgl">&nbsp;</a></li>
	                        <li><a class="function_icon_bz icon_home" href="">&nbsp;</a></li>
	                    </ul>
					</div>
                </div>
        </div>
        <div class="work_view">
                <div class="menu_view">
                    <div class="title">功能导航</div>
                    <ul class="menu_items">
	                    <li class="ggfb"><span class="api-icon"></span><span>短信中心</span></li>
	                    <li class="hytz"><span class="api-icon"></span><span>会议通知</span></li>
	                    <li class="cxb"><span class="api-icon"></span><span>彩信中心</span></li>
	                    <li class="txlgl"><span class="api-icon"></span><span>通讯录</span></li>
	                    <li class="customeradmin"><span class="api-icon"></span><span>用户管理</span></li>
	                    <li class="roleadmin"><span class="api-icon"></span><span>角色管理</span></li>
	                    <li class="xtsz"><span class="api-icon"></span><span>系统管理</span></li>
	                    <li class="rctx"><span class="api-icon"></span><span>日程提醒</span></li>
	                    <li class="wjdc"><span class="api-icon"></span><span>投票调查</span></li>
	                    <li class="dxdt"><span class="api-icon"></span><span>短信答题</span></li>
	                    <li class="dxcj"><span class="api-icon"></span><span>短信抽奖</span></li>
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
            var jcTabs = tabContentClone.removeClass("tabContent").load("${ctx}/mas3/secure/firstuserpwd.jsp");
        </s:if>
        <s:else>
            var jcTabs = tabContentClone.removeClass("tabContent").load("${ctx}/mas3/secure/firstuserpwd.jsp");
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
