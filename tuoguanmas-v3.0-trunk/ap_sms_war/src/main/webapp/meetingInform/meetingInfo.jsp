<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="ctx"  value="${pageContext.request.contextPath }"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx }/css/demo.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx }/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	<script type="text/javascript" src="${ctx }/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/jquery.ztree.core-3.4.js"></script>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
<script>
 
</script>
<title>短信办公室</title>

</head>
<body>
<div id=tabs-1>
<div class="contents">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    <div class="left_contents">
	  <div class="bottom_box">
        <div class="tubh"><a href="会议发送结果.html">发送</a></div>
	  <div class="tubh"><a href="#">取消</a></div>
      </div>
	  <div class="table_box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="50" align="right"><span style="color:#FF0000">*</span>标题：</td>
    <td><input type="text" name="textfield2" class="input2" /></td>
    </tr>
   <tr>
    <td height="50" align="right"><span style="color:#FF0000">*</span>接收人：</td>
    <td><input name="textfield2" type="text" class="input2" value="可同时发给多人，以逗号”，“隔开，支持向移动、联通、电信用户发送" /></td>
    </tr>
  <tr>
    <td align="right" valign="top"><span style="color:#FF0000">*</span>通知内容：</td>
    <td><textarea name="textarea" style="width: 98%; height: 137px; font-size: 12px;"></textarea>
      <div class="tixing_tab">还可以输入350字，本次将以0条计费（编辑时提醒） 短信共XX字，分X条发送</div></td>
  </tr>
  
  <tr>
    <td align="right">发送时间：</td>
    <td><input name="radiobutton" type="radio" value="radiobutton" checked="checked" />
      立即发送 
        <input type="radio" name="radiobutton" value="radiobutton" />
        定时发送 
        <input name="textfield3" type="text" class="input3" value="时间控件" /></td>
  </tr>
  <tr>
    <td rowspan="2" align="right">发送时间：</td>
    <td><input name="" type="checkbox" value="" checked="checked" />接收人回复短信后，则自动转发给 <input type="text" name="textfield3" class="input3" /><span style="color:#FF0000">（默认为发信人号码）</span></td>
  </tr>
  <tr>
    <td><input name="" type="checkbox" value="" />到时提醒 <input name="textfield3" type="text" class="input3" value="时间控件" />
      <input name="textarea33" type="text" value="提醒不得超过10个字" /></td>
  </tr>
</table>
		</div>
		<div class="bottom_box">
	  <div class="tubh"><a href="会议发送结果.html">发送</a></div>
	  <div class="tubh"><a href="#">取消</a></div>
	</div>
	</div>
    </td>
    <td width="242" style="vertical-align:top;"><div class="right_contents" >
        <div class="right_head"><img src="${ctx }/images/contact_locn.png" />&nbsp;&nbsp;通讯录</div>
        <div class="right_box">
			<div class="input_right">
				<input type="text" class="input_search" name=""/><div class="search1"><img src="${ctx }/images/search.gif"/></div>
			</div>
			<div class="input_right" style="font-size: 14px; " onclick=""><a href="javascript:void(0);" ><img src="${ctx }/images/add.png"/>添加号码文件</a>
				<div id="inputFileContainer" style="display: none;">
					<input type="file" id="inputfile" name="inputfile" />
				</div>
			</div>
			<div class="input_right" style="font-size: 14px; " onclick=""><a href="javascript:void(0);"><img src="${ctx }/images/contact_locn.png" />发给自己</a>
			</div>
          	<div class="zTreeDemoBackground left" style="height: 180px; margin-left: 10px;">
          		<ul id="treeDemo" class="ztree" style="height: 180px; background: none; border: 0px; overflow-y:auto;"></ul>
          	</div>
          <div class="box_bottom"><img src="${ctx }/images/box_bottom.jpg" /></div>
        </div>
      </div></td>
  </tr>
</table> 
</div>
</div>
</body>
</html>