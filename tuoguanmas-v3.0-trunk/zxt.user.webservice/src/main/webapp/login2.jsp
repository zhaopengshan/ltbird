<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- pan_z_g 更改标题 -->
<title>项目管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript" src="${ctx}/common/js/jquery-1.6.min.js"></script>
<script src="${ctx}/common/js/AC_RunActiveContent.js" type="text/javascript"></script>
<script src="${ctx}/common/js/popup.js" type="text/javascript"></script>
<script src="${ctx}/common/js/login.js" type="text/javascript"></script>
<script src="${ctx}/common/js/mbncX.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/common/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/common/js/common.js"></script>
<script type="text/javascript">
    var CONTEXT_PATH = '${ctx}';
	function loging(){
        var login =  $("#login");
        login.submit(); 
    }
</script>
</head>
<body>
    <div id="wrap">
    	<div id="top">
        	<div class="logo"><img src="${ctx}/images/logo_1.png" alt="中国移动-移动代理服务器" /></div>
            <div class="mbnmenu">
            	<ul class="menulist">
            	<!-- pan_z_g 更改超链接 -->
                	<li class="leftborder"><a href="${ctx}" class="leftchoose" title="首页">首&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
                	<li><a href="<%=basePath%>product.html" title="产品特性">产品特性</a></li>
                	<li><a href="<%=basePath%>function.html" title="功能演示">功能演示</a></li>
                	<li><a href="<%=basePath%>download.jsp" title="软件下载">软件下载</a></li>
                	<li class="rightborder"><a href="<%=basePath%>question.html" title="常见问题">常见问题</a></li>
                </ul>
            </div>
        </div>
        <div class="maincontent">
		    <form id="login" name="login" action="<%=path%>/loginAction/login.action" method="post">
        	<div class="topclass">
            	<div class="banner"><img src="${ctx}/images/banner.gif" alt="中国移动-移动代理服务器" /></div>
                <div class="login">
               	  <p><img src="${ctx}/images/login_title.gif" alt="用户登录" /></p>
                    <ul class="loginlist">
                    	<li>
                            <span class="name">账&nbsp;&nbsp;号：</span>
                            <span class="textinput"><input type="text" id="textfield3" name="loginName" value="<s:property value='loginVO.loginName' />" /></span>
                        </li>
                    	<li>
                        	<span class="name">密&nbsp;&nbsp;码：</span>
                            <span class="textinput"><input type="password" id="textfield2" name="password"/></span>
                            <!--<span class="getps"><a href="getpassword.jsp" title="忘记密码">忘记密码？</a></span>-->
                        </li>
                        <li class="center mt20px">
                            <input type="image" id="imageField" src="${ctx}/images/login.gif" /> 
                        </li>
                        <li class="center mt10px">
                              <div id="errorMsg" style="color: red;">
                                  <s:if test="msg==1">验证码不一致。</s:if>
                                  <s:if test="msg==2">用户名或密码有误，请重新输入。</s:if>
                                  <s:if test="msg==3">用户名已被锁定，请联系管理员。</s:if>
                              </div>
                        </li>
                  </ul>
                </div>
				</form>
            </div>
            <!-- pan_z_g 更改产品介绍... -->
           	   <div class="textcontent column">
             <div class="intro">
               	  <div class="title"><h2>产品介绍</h2></div>
                    <div class="content"> <p>中国移动-企信通是一款集成了TD(3G)语音通话、短信发送、联系人管理(CRM)等多功能于一体的信息化产品。</p>
       <P>本产品普遍适用于各行业移动集团客户，能够很好地满足其管理内外部人员、短信群发等移动信息化需求。</P>
       <p>产品核心功能突出、操作简便易懂、界面简洁美观。
       </p>
                    </div>
              </div>
                <div class="announce">
                	<div class="title"><h2>网站公告</h2></div>
                    <div class="content"> 
                        <p>尊敬的中国移动用户：本产品于2012年11月XX日正式上线。欢迎体验！
<%-- 

                            <span class="red">此次升级解决了部分瑞恒话机发送短信出现故障的问题。 </span>
                            <span class="red">请在“软件下载”栏下载新版客户端进行体验！</span> --%>
                        </P>
                        <p>&nbsp;</P>
                        <p>&nbsp;</P>
                        <p>&nbsp;</P>
                    </div>
                </div> 
            </div>
        </div> 
        <div class="footer column"></div>
    </div>
</body>
</html>