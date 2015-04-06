<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/jquery-ui-1.8.22.custom.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/easyui/easyui.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/ui.jqgrid.css" type="text/css" rel="stylesheet"/>
<link href="${ctx }/css/leadtone.grid.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="${ctx }/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="javascript" src="${ctx }/js/i18n/grid.locale-cn.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery-ui-1.8.22.custom.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.easyui.min.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.PlaceHolder.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/jquery.jqGrid.src.js" type="text/javascript" ></script>
<script language="javascript" src="${ctx }/js/leadtone.LeadToneGrid.js" type="text/javascript" ></script>
<script type="text/javascript">
	$(function() {
		var resultGridPro = {
			url:"",
			colNames:['id','手机号码','接收人姓名', '发送结果', '失败原因'],
			colModel:[
				{name:'id',index:'id', width:0,sortable: false, align: 'center',hidden: true},
				{name:'status',index:'status', width:40,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
						var iconSms = '<img title="未读" src="../../images/u124_normal.png" width="15" height="13">';
						switch(parseInt(cellvalue,10)){
							case 0: iconSms = '<img title="未读" src="../../images/u124_normal.png" width="15" height="13">'; break;
							case 1: iconSms = '<img title="已读" src="../../images/u164_normal.png" width="15" height="13">'; break;
							case 2: iconSms = '<img title="已回复" src="../../images/u128_normal.png" width="15" height="13">'; break;
						}
						return iconSms;
					}},
				{name:'customerName',index:'customerName', width:100,sortable: false, align: 'center'},
				{name:'content',index:'content', width:250,sortable: false,formatter:function(cellvalue, options, rowObject){
						return '<a href="../../mbnSmsInboxAction/getSmsDetails.action?selectedId='+ rowObject.id +'" class="grid-sms">'+cellvalue+'</a>';
					}},
				{name:'content',index:'content', width:40,sortable: false, align: 'center',formatter:function(cellvalue, options, rowObject){
						return cellvalue.length;
					}}
			],
			pager: '#smsPage',
			buttons: [{
				text: "返回",
				classes: "tubh",
				click: function(){
					var url = '../../mbnSmsInboxAction/deleteByIds.action';
					var showMessage = "是否删除选中的{0}条短信？";
                	confirmAjaxFunc(smsGrid,url,showMessage);
				}
			}]
		};
			
	});
</script>
<title>短信收件箱</title>

</head>

<body>
	
	<div class="contents">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    <div class="left_contents">
	 <div class="botton_box1">共发送<span style="color: #FF0000">20</span>条短信，成功<span style="color:#00cc00">18</span>条，<span style="color:#FF0000">失败2</span>条，<span lang="EN-US" xml:lang="EN-US">XX</span>条短信发送状态未知</div>
      <div class="botton_box">
        <div class="tubh"><a href="#">返回</a></div>
        1/2页 <a href="#" style="color:#067599">下一页</a> <a href="#"  style="color:#067599">跳转</a> </div>
      <div class="table_box1">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mail_tab">
          <tr>
            <td width="6%" class="head1"><input name="checkbox2" type="checkbox" value="checkbox" checked="checked" /></td>
            <td width="19%" class="head3">手机号码</td>
            <td class="head3">接收人姓名</td>
            <td class="head3">发送结果（可排序）</td>
            <td class="head3">失败原因</td>
            </tr>
          <tr>
            <td width="6%" class="td2"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
            <td width="19%" class="td2">&nbsp;</td>
            <td class="td2">接收人姓名</td>
            <td align="center" class="td2"><span style="color:#009900">成功</span></td>
            <td class="td2">失败原因</td>
            </tr>
          <tr>
            <td width="6%" class="td2"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
            <td width="19%" class="td2">&nbsp;</td>
            <td class="td2">接收人姓名</td>
            <td align="center" class="td2"><span style="color:#009900">成功</span></td>
            <td class="td2">&nbsp;</td>
            </tr>
          <tr>
            <td width="6%" class="td2"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
            <td width="19%" class="td2">&nbsp;</td>
            <td class="td2">接收人姓名</td>
            <td align="center" class="td2"><span style="color:#009900">成功</span></td>
            <td class="td2">&nbsp;</td>
            </tr>
          <tr>
            <td width="6%" class="td2"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
            <td width="19%" class="td2">&nbsp;</td>
            <td class="td2">接收人姓名</td>
            <td align="center" class="td2"><span style="color:#009900">成功</span></td>
            <td class="td2">&nbsp;</td>
            </tr>
          <tr>
            <td width="6%" class="td2"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
            <td width="19%" class="td2">&nbsp;</td>
            <td class="td2">接收人姓名</td>
            <td align="center" class="td2"><span style="color:#ff0000">失败</span></td>
            <td class="td2">&nbsp;</td>
            </tr>
          <tr>
            <td width="6%" class="td2"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
            <td width="19%" class="td2">&nbsp;</td>
            <td class="td2">接收人姓名</td>
            <td align="center" class="td2"><span style="color:#4444fe">未返回结果</span></td>
            <td class="td2">&nbsp;</td>
            </tr>
        </table>
      </div>
      <div class="botton_box">
        <div class="tubh"><a href="#">返回</a></div>
        1/2页 <a href="#" style="color:#067599">下一页</a> <a href="#"  style="color:#067599">跳转</a> </div>
    </div>
    </td>
    <td width="242" valign="top">
    <div class="right_contents">
      <div class="right_head1"></div>
      <div class="right_box">
        <div class="input_right">
          <input name="Input2" type="text"  class="input_search" value="接收人姓名"/>
          <div class="search"><img src="images/search.gif" /></div>
        </div>
        <div class="box_contents">
          <div class="search_list">
            <h1>-按联系人查找</h1>
            <ul>
              <li><a href="#">关键字</a></li>
			  <li><a href="#">联系人姓名</a></li>
            </ul>
          </div>
          <div class="search_list">
            <h1>-按发送结果查找</h1>
            <ul>
              <li>
               <a href="#">关键字</a>
              </li>
            </ul>
          </div>
        </div>
        <div class="box_bottom"><img src="images/box_bottom.jpg" /></div>
      </div>
    </div>
    </td>
  </tr>
</table>
  </div>
  
</body>
</html>

