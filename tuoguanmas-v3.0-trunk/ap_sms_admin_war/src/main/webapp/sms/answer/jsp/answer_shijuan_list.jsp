<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache"/> 
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
<script type="text/javascript" src="${ctx}/sms/answer/js/answer_result_list.js"></script>
<script type="text/javascript" src="${ctx}/sms/answer/js/answer_shijuan_list.js"></script>
<script type="text/javascript">
//var tikuTiKuListGrid;


//alert(initdatiTiKuGridPro());



$(function() {
	
	initdatiShiJuanGridPro();
	datiShiJuanListGrid = new TableGrid("datiShiJuanListGrid",datiShiJuanGridPro);
	datiShiJuanListGrid.redrawGrid(datiShiJuanGridPro);
	$("#shijuan_list_div").css("display","block");
	$("#shijuan_result_div").css("display","none");

	$("#shijuanSerarchBtn").unbind("click");
	$("#shijuanSerarchBtn").bind("click",function(){
		searchdatiShiJuanList();
	});

	$("#shijuanExportBtn").unbind("click");
	$("#shijuanExportBtn").bind("click",function(){
		exportdatiShiJuanList();
	});

	$("#datiShiJuanListGridnew").unbind("click").click(function(){
		createDaTiTiKu();
    });

	$("#foot_datiShiJuanListGridnew").unbind("click").click(function(){
		createDaTiTiKu();
    });
});

</script>
<title>短信答题列表</title>
</head>

<body>
<div id="shijuan_list_div" class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top"  id="sms_vote_frame">
			    	<div>
				    	<table id="datiShiJuanListGrid"></table>
			    	
			    	</div>
			    	
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        
				        <div class="box_contents">
				        <form id="shijuanSearch" method="post">
				          <div id="shijuanTitle" class="search_list">
					            <h1>-按题目内容查找</h1>
					            <ul>
					              <li>
					                <input id="shijuanTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          
				          <div id="shijuanTitle" class="search_list">
					            <h1>-按创建人查找</h1>
					            <ul>
					              <li>
					                <input id="shijuanCreateBySearch" name="Input2" type="text"  class="input_search" />
					                <input id="shijuan_listType" name="shijuan_listType" type="hidden" value="shijuan"/>
					              </li>
					            </ul>
				          </div>
				          
				          
				          <table id="shijuanSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="shijuanSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				             <tr>
				              <td><div class="btn_export"><a id="shijuanExportBtn" href="javascript:void(0);" >导出查询结果</a></div></td>
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


<div id="shijuan_result_div" style="display:none" class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><DIV class=demo>
         <input type="hidden" value="" id="answerId"></input>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top"  id="sms_vote_frame">
			      <div>
			    	<table id="datiResultListGrid"></table>
			        
			      </div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        
				        <div class="box_contents">
				        <form id="shijuanResultSearch" method="post">
				          <div id="shijuanResultTitle" class="search_list">
					            <h1>-按题目内容查找</h1>
					            <ul>
					              <li>
					                <input id="shijuanResultTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          
				          
				          <table id="ahijuanResultSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a id="shijuanResultSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
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


</body>
</html>

