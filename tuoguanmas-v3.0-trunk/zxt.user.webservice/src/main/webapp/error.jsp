<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<title>商户管家-商户的好帮手</title>
<style type="text/css">
<!--
.Table_01 tr td {
	color: red;
	font-size: 18px;
	margin-top: 10px;
	text-align: center;
}

body {
    background-color: #F2F2F2;
    color: #E6F2FB;
    margin-top: 20px;
}
.red {
	color: #F30;
	font-size: 16px;
}
-->
</style>
<script type="text/javascript" src="/common/js/jquery-1.6.min.js"></script>
<script type="text/javascript">
		var isOpenedWindow = false;
		function getTopWin(win)
		{
		    if(win.top.opener != undefined && win.top.opener != null)
		    {
		        return getTopWin(win.top.opener);
		    }
		    else if(win.top.dialogArguments != undefined && win.top.dialogArguments != null)
		    {
		        return getTopWin(win.top.dialogArguments);
		    }
		    else
		    {
		        return win.top;
		    }
		}
		
		  function closeAllWin(win)
		    {
		        if(win.top.opener != undefined && win.top.opener != null)
		        {
		            isOpenedWindow = true;
		            closeAllWin(win.top.opener);
		            win.top.opener.close();
		        }
		        else if(win.top.dialogArguments != undefined && win.top.dialogArguments != null)
		        {
		            isOpenedWindow = true;
		            closeAllWin(win.top.dialogArguments);
		            win.top.dialogArguments.close();
		        }
		    } 
        /**
         * 是否进行强行登陆操作
         */
         function go(){
            //表示强行登陆
	       var topWin = getTopWin(window);
	        closeAllWin(window);
	        if( isOpenedWindow )
	        {
	            window.top.close();
	        }
	        topWin.location.href = "login.jsp";
         }
</script>
</head>
<body style="margin: 0px;">
    <table width="888" border="0" align="center" cellpadding="0" cellspacing="0" class="Table_01" >
      <tr>
            <td rowspan="2" >
            </td>

            <td rowspan="2" >&nbsp;</td>
            <td colspan="5" rowspan="2" valign="middle" ><!-- <img src="/common/images/head_01.jpg" /> --></td>

            <td rowspan="2">&nbsp;</td>
      </tr>
    </table>
    <table width="420px"  border="0" align="center" cellpadding="0" cellspacing="10" class="Table_01" style=" border:#F60 3px solid; background-color:#FCFCFC; margin-top: 40px;">
      <tr>
            <td style=" font-family:Arial, Helvetica, sans-serif;font-weight:bold;"><img src="images/201110100528446.png" width="128" height="128" /><br />
            <span class="red">系统登录失败或会话超时！</span></td>
      </tr>
       <tr>
            <td></td>
      </tr>
      <tr>
            <td> <input style="height:30px; width:130px;" type="button" id="forceLogin"   value="重新登陆" onclick="go()"></input></td>
      </tr>
    </table>
</body>
</html>