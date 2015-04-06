<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- pan_z_g 更改标题 -->
<title>销售管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript" src="${ctx}/common/js/jquery-1.6.min.js"></script>
<script src="${ctx}/common/js/popup.js" type="text/javascript"></script>
<script src="${ctx}/common/js/login.js" type="text/javascript"></script>
<script src="${ctx}/common/js/mbncX.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/common/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/common/js/common.js"></script>
<script type="text/javascript">
    
</script>
</head>
<body>
    <div>
        <div>
            <div style="padding-bottom: 300px;"><img src="${ctx}/images/loginbg3.png" alt="中国移动-移动代理服务器" /></div>
            <div><img src="${ctx}/images/logindlg.png" alt="中国移动-移动代理服务器" /></div>
        </div>
    </div>
</body>
</html>