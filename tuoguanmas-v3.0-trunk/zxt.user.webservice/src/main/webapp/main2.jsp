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

        <title>中国移动-移动代理服务器</title>
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
        <!-- 动态菜单js文件 -->
         <script type="text/javascript" src="<%=basePath %>common/js/DynamicsMenu.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/jquery-1.6.4.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/jquery.timers.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/mbncX.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/main.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/json2.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/common.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/Bind2Form.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/login.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/menu.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/changeskin.js"></script>
        <script type="text/javascript" src="<%=basePath %>common/js/ie6img.js"></script>
         

        <OBJECT ID="MbnClientX"	CLASSID="CLSID:32E6CD5D-C6C6-4EDE-A36C-E7136D162A64" height="0"	width="0"></OBJECT>
        <script type="text/javascript">
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
        
        
         
        <input type="hidden" id="roleId" value="${sessionScope.loginAdmin.roleId }"/>
            <div  style=" background:none; width:100%; line-height:30px; height:30px; vertical-align:middle; font-size:12px;">
                <div style=" background:none;font-family: 微软雅黑,宋体,Arial, Helvetica, sans-serif; font-size: 12px; float:left; width: 40%; color: #D5E2FF" id="bgclock"></div>
                <div style=" background:none;float:right; width: 60%;  margin:0px; ">
                    <div style="padding: 0 2px 0 0; float: right; font-size: 12px; color: #ffffff;">
                        <span
                            style="margin: 0 8px 0 35px;">欢迎您！
                        </span>
                 </div>
                </div>
            </div>
            <div style=" width:100%; clear:both; overflow: hidden; zoom:1; margin: 0px; height:70px;">
                <span style="float: left; padding-top:10px;"><img
                        src="common/images/yidonglogo_2.png"
                        style="height: 50px; " align="middle" /></span>
                <div style="  float:right;">
                </div>
            </div>
        </div>
        <div id="mainPanle" region="center"	style="background: #eee; overflow-y:hidden;">
            <div id="tabs" class="easyui-tabs" fit="true" border="false">
                <div title="欢迎使用" style="padding:20px; overflow: hidden;" id="home">
                    <iframe src="funnel/FunnelList.jsp" width="100%" height="100%" frameborder="0" style=" overflow: hidden;"></iframe>
                </div>
            </div>
        </div>
        <div id="mainWindow" class="easyui-window" title="客户资料"
             style="padding: 10px; margin:auto; min-width:550px;  width:600px;" iconCls="icon-ok"
             closed="true" maximizable="false" minimizable="false" closable="false"
             collapsible="false" resizable="true" modal="true">
            <div id="mainDiv"></div>
            <div id="mainAction" style="width:550px; text-align:center; margin:0 auto 30px auto;">
                <a class="easyui-linkbutton" iconCls="icon-ok"
                   href="javascript:void(0)" onclick="operateEntity(false)">确定</a> 
                <!--<a id="addOrder" class="easyui-linkbutton" iconCls="icon-reload"
                   href="javascript:void(0)" onclick="addOrder('add')">保存并添加订单</a>--> 
                <a class="easyui-linkbutton" iconCls="icon-reload"
                   href="javascript:void(0)" onclick="resetForm()">重置</a> <a
                   class="easyui-linkbutton" iconCls="icon-cancel"
                   href="javascript:void(0)" onclick="closeWindow()">关闭</a>
            </div>
            <div id="mainViewAction" style="width:550px; text-align:center; margin:0 auto 30px auto;">
                <!--<a class="easyui-linkbutton" iconCls="icon-reload"
                   href="javascript:void(0)" onclick="addOrder('view')">添加订单</a>--> 
                <a class="easyui-linkbutton" iconCls="icon-cancel"
                   href="javascript:void(0)" onclick="closeWindow()">关闭</a>
            </div>
        </div>
        <input style="background:none; border:none;" id="refreshHidden" value="" type="text" onchange="refreshTab('客户资源')"/>
        <div id="forcepswdchange" class="easyui-dialog" title="密码变更" style="width:600px;height:380px;padding:10px 20px;_padding-bottom:0px;_height:400px;" closable="false" closed="true" modal="true" buttons="#dlgpswdchange-buttons">  
            <div class="ftitle"><span style="color:red;" >尊敬的客户：您已成功登录中国移动-移动代理服务器系统。</span></div>
            <div class="ftitle"><span style="color:red;" >为了您的账号安全，请设置登录中国移动-移动代理服务器系统的密码：</span></div>
            <form id="fmpswdchange" method="post">
                <div class="fitem">
                    <label>TD号码/用户名：</label>  
                    <span><input id="merchantMobile" name="loginVO.loginName" class="easyui-validatebox" readonly="true" value="<s:property value='#session.loginfo.tdNumber'/>" style="border:1px solid #ccc;vertical-align:bottom;width:130px"/></span>
                    <span style="color:red">不可编辑</span>
                </div>
                <div class="fitem">
                    <label><span style="color:red;" >*</span>请输入新密码：</label>  
                    <span><input id="newpassword" name="loginVO.newLoginPwd" type="password" class="easyui-validatebox" style="border:1px solid #ccc;vertical-align:bottom;width:130px"/></span>
                    <span id="newpassworderror" style="color:red"></span>
                </div>
                <div class="fitem">
                    <span  style="margin:0px 0px 0px 120px;color:#999999;">1-16位之间，(英文、数字、符号)，区分大小写</span> 
                </div>
                <div class="fitem">
                    <label><span style="color:red;" >*</span>请再次输入新密码：</label>
                    <span><input id="repeatepassword" name="repeatepassowrd" type="password" class="easyui-validatebox" style="border:1px solid #ccc;vertical-align:bottom;width:130px"/></span>
                    <span id="repeatepassworderror" style="color:red"></span>
                </div>
                <div class="fitem">
                    <span  style="margin:0px 0px 0px 120px;color:#999999;">再输入一次密码，请确保同新密码一致 </span> 
                </div>
                <div class="fitem">
                    <label><span style="color:red;" >*</span>找回密码的邮箱：</label>
                    <span><input id="protectemail" name="loginVO.protectEmail" class="easyui-validatebox" style="border:1px solid #ccc;vertical-align:bottom;width:130px"/></span>
                    <span id="protectemailerror" style="color:red"></span>
                </div>
                <div class="fitem">
                    <span  style="margin:0px 0px 0px 120px;color:#999999;">密保邮箱(支持50位字符)用于找回登录密码。</span> 
                </div>
                <div class="fitem">
                    <span style="margin:0px 0px 0px 120px;color:#999999;" >推荐使用中国移动139邮箱或移动办公套件（邮箱）</span>
                </div>
                <div class="fitem">
                    <span style="margin:0px 0px 0px 80px;color:#999999;" >设置新密码成功后，下次请用新密码登录中国移动-移动代理服务器系统。</span>
                </div>
                <div class="fitem">
                    <span style="margin:0px 0px 0px 80px;color:#999999;" >登录后您还可以在“系统设置”中修改密码。</span>
                </div>
                <input type="hidden" id="oldpassword" name="loginVO.loginPwd" value="<s:property value="#session.loginfo.loginPwd" />"/>
                <input type="hidden" id="merchantPin" name="loginVO.merchantPin" value="<s:property value='#session.loginfo.merchantPin'/>"/>
            </form>  
        </div>  
        <div id="dlgpswdchange-buttons" style="_padding-bottom:15px; margin-left:30px">  
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" id="savepswdchange">确定</a>
            <a href="javascript:logout();" class="easyui-linkbutton" iconCls="icon-cancel" id="exitsystem">退出系统</a>
        </div>
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

