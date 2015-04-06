<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<script type="text/javascript" src="${ctx}/sms/answer/js/answer_list.js"></script>
<script type="text/javascript">
//var tikuTiKuListGrid;


//alert(initdatiTiKuGridPro());



$(function() {
	
	initdatiTiKuGridPro();
	datiTiKuListGrid = new TableGrid("datiTiKuListGrid",datiTiKuGridPro);
	datiTiKuListGrid.redrawGrid(datiTiKuGridPro);

	$("#tikuSerarchBtn").unbind("click");
	$("#tikuSerarchBtn").bind("click",function(){
		searchdatiTiKuList();
	});

	$("#tikuExportBtn").unbind("click");
	$("#tikuExportBtn").bind("click",function(){
		exportdatiTiKuList();
	});

	$("#datiTiKuListGridnew").unbind("click").click(function(){
		createDaTiTiKu();
    });

	$("#foot_datiTiKuListGridnew").unbind("click").click(function(){
		createDaTiTiKu();
    });
});

</script>
<title>短信答题题库列表</title>
</head>

<body>
<div class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top"  id="sms_vote_frame">
			    	<div>
			    	  <table id="datiTiKuListGrid" ></table>
			    	  
			    	</div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        
				        <div class="box_contents">
				        <form id="tikuSearch" method="post">
				          <div id="tikuTitle" class="search_list">
					            <h1>-按题目内容查找</h1>
					            <ul>
					              <li>
					                <input id="tikuTitleSearch" name="Input2" type="text"  class="input_search" />
					                <input id="tiku_listType" name="Input2" type="hidden" value="tiku"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          
				          <div id="voteTimeInterval" class="datebox_list">
				          <h1>-按创建时间查找</h1>
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="tikuDateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="tikuDateTo" />
				            </li></ul>
				          </div>
				          <table id="tikuSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="tikuSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				            <tr>
				              <td><div class="btn_export"><a id="tikuExportBtn" href="javascript:void(0);" >导出查询结果</a></div></td>
				            </tr>
				          </table>
				        </form>
				        </div>
				        <div class="box_bottom"><img src="${ctx }/themes/mas3/images/box_bottom.jpg" /></div>
				      </div>
					</div>
			    </td>
			  </tr>
			</table> 
		  </div>
		</DIV>
		</td>
	</tr>
</table>

</div>
<div class="clear"></div>

<div id="tikuAddDialogLoad"></div>
</body>
</html>

