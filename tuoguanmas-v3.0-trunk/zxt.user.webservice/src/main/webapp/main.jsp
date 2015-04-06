<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String forceChangePswd = "noneed";
    if (session.getAttribute("forceresetpswd") != null) {
        forceChangePswd = (String) session.getAttribute("forceresetpswd");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" id="headerzhaoyao">
    <head>
        <base href="<%=basePath%>" />

        <title>项目管理系统</title>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <!--[if lt IE 7]>
            <script>
                document.execCommand("BackgroundImageCache",false,true);
            </script>
        <![endif]-->
        <link rel="stylesheet" type="text/css"	href="common/css/themes/default/easyui.css" />
        <link rel="stylesheet" type="text/css" href="common/css/themes/icon.css" />
        <link rel="stylesheet" type="text/css" href="common/css/main.css" />
        <link href="css/common.css" rel="stylesheet" type="text/css" />
        <link href="css/index.css" rel="stylesheet" type="text/css" />
        <link href="css/skin_0.css" rel="stylesheet" type="text/css" id="skinCss" />
        <script type="text/javascript" src="common/js/jquery-1.6.4.js"></script>
        <script type="text/javascript" src="common/js/jquery.timers.js"></script>
        <script type="text/javascript" src="common/js/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="common/js/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="common/js/mbncX.js"></script>
        <script type="text/javascript" src="common/js/main.js"></script>
        <script type="text/javascript" src="common/js/json2.js"></script>
        <script type="text/javascript" src="common/js/common.js"></script>
        <script type="text/javascript" src="common/js/Bind2Form.js"></script>
        <script type="text/javascript" src="common/js/login.js"></script>
        <script type="text/javascript" src="common/js/menu.js"></script>
        <script type="text/javascript" src="common/js/changeskin.js"></script>
        <script type="text/javascript" src="common/js/ie6img.js"></script>

        <script type="text/javascript">
            function requestUrl() {
                $.ajax({
                    type : "POST",
                    url : "loginAction/out.action",
                    error : function() {
                        alert("服务器请求出错，请等待session超时。");
                    }
                });
            }
            function illegalrequestUrl() {
                $.ajax({
                    type : "POST",
                    url : "loginAction/illegalout.action",
                    error : function() {
                        alert("服务器请求出错，请等待session超时。");
                    }
                });
            }
        </script>
        <style type="text/css" >
            #fm{
                margin:0;
                padding:10px 30px;
            }
            .ftitle{
                font-size:14px;
                font-weight:bold;
                color:#666;
                padding:5px 0;
                margin-bottom:10px;
                /*border-bottom:1px solid #ccc;*/
            }
            .fitem{               
                margin-bottom:5px;
            }
            .fitem label{
                display:inline-block;
                width:120px;
            }
            .panel-header{_padding-left:0px; _padding-right:0px;}
        </style>        
    </head>
    <body  style=" width:100%; margin:0px; padding:0px;" class="easyui-layout" >
        <div region="north" border="false" style="background:none;overflow:hidden;clear:both; zoom:1;">
            <div  style=" background:none; width:100%; line-height:30px; height:30px; vertical-align:middle; font-size:12px;">
                <div style=" background:none;font-family: 微软雅黑,宋体,Arial, Helvetica, sans-serif; font-size: 12px; float:left; width: 40%; color: #D5E2FF" id="bgclock"></div>
                <div style=" background:none;float:right; width: 60%;  margin:0px; ">
                    <div style="padding: 0 2px 0 0; float: right; font-size: 12px; color: #ffffff;">

                    </div>

                </div>
            </div>
            <div style=" width:100%; clear:both; overflow: hidden; zoom:1; margin: 0px; height:70px;">

                <span style="float: left; padding-top:10px;"><img
                        src="common/images/yidonglogo.png"
                        style="height: 50px; " align="middle" /></span>
                <div style="float:right;">
                <div class="stylezhaoyao"  onclick="" onmouseover="this.className='stylezhaoyao1'" onmouseup="this.className='stylezhaoyao1'"  onmouseout="this.className='stylezhaoyao2'" onmousedown="this.className='stylezhaoyao2'"
                         ><img src="images/zhaoyao20111230topicon03.png" width="48" height="48" /><a>退出</a></div>
                </div>
            </div>
        </div>
        <div region="west" split="false" title="导航菜单" style="width: 120px;"
             id="west">
            <div id="accordion" class="easyui-accordion" fit="true" border="false">
                <!--  导航内容 -->
            </div>
        </div>
        <div id="mainPanle" region="center"	style="background: #eee; overflow-y:hidden;">
            <div id="tabs" class="easyui-tabs" fit="true" border="false">
                <div title="项目管理" style="padding:20px; overflow: hidden;" id="home">
                    <iframe src="funnel/FunnelList.jsp" width="100%" height="100%" frameborder="0" ></iframe>
                </div>
            </div>
        </div>
        <div id="mainWindow" class="easyui-window" title="客户资料"
             style="padding: 10px; margin:auto; min-width:550px;  width:680px;" iconCls="icon-ok"
             closed="true" maximizable="false" minimizable="false" closable="false"
             collapsible="false" resizable="true" modal="true">
            <div id="mainDiv"></div>
            <div id="mainAction" style="width:550px; text-align:center; margin:0 auto 30px auto;">
                <a class="easyui-linkbutton" iconCls="icon-ok"
                   href="javascript:void(0)" onclick="operateEntity(false)">确定</a> <a
                   id="addOrder" class="easyui-linkbutton" iconCls="icon-reload"
                   href="javascript:void(0)" onclick="addOrder('add')">保存并添加订单</a> <a
                   class="easyui-linkbutton" iconCls="icon-reload"
                   href="javascript:void(0)" onclick="resetForm()">重置</a> <a
                   class="easyui-linkbutton" iconCls="icon-cancel"
                   href="javascript:void(0)" onclick="closeWindow()">关闭</a>
            </div>
            <div id="mainViewAction" style="width:550px; text-align:center; margin:0 auto 30px auto;">
                <a class="easyui-linkbutton" iconCls="icon-reload"
                   href="javascript:void(0)" onclick="addOrder('view')">添加订单</a> <a
                   class="easyui-linkbutton" iconCls="icon-cancel"
                   href="javascript:void(0)" onclick="closeWindow()">关闭</a>
            </div>
        </div>
        <input style="background:none; border:none;" id="refreshHidden" value="" type="text" onchange="refreshTab('客户资源')"/>
         <!--[if lt IE 8]>
            <script>
                correctPNG();
            </script>
        <![endif]-->
    </body>
    <!--[if lt IE 8]> 
        <script type="text/javascript">
            correctPNG();
        </script>
    <![endif]-->        
</html>

