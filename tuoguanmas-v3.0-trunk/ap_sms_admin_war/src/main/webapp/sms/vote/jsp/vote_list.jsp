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
<title>投票调查</title>
<script type="text/javascript" src="./sms/vote/js/vote_list.js"></script>
<script type="text/javascript" src="./sms/vote/js/vote_result_list.js"></script>
<script type="text/javascript">
	voteOnload();
</script>
</head>
<body>
<div id="voteList_div" class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF">
    <div class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top" id="sms_vote_frame">
			    	<div>
			    	<table id="voteGrid"></table></div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="list">
				          <ul>
				            <li id="vote_hadsend_menu" class="zhan"><a onclick="switchVoteType('send')" href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/vote/lise_lcon1.gif" style="vertical-align:middle"/> 已发送</a></li>
				            <li id="vote_hadreply_menu" ><a onclick="switchVoteType('reply')" href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/vote/ico-huifu.png" style="vertical-align:middle"/>&nbsp;已回复</a></li>
				            <li id="vote_readysend_menu" ><a onclick="switchVoteType('notSend')" href="javascript:void(0);"><img src="${ctx }/themes/mas3admin/images/vote/lise_lcon3.gif" style="vertical-align:middle"/>&nbsp;待发送</a></li>
				          </ul>
				        </div>
				        <div class="box_contents">
				        <form id="voteSearch" method="post">
				          <div id="voteTitle" class="search_list">
					            <h1>-按任务名称查找</h1>
					            <ul>
					              <li>
					                <input id="voteTitleSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="voteCreateBy" class="search_list">
					            <h1>-按创建人查找</h1>
					            <ul>
					              <li>
					                <input id="voteCreateBySearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="voteTimeInterval" class="datebox_list">
				          <h1>-按有效时间查找</h1>
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="voteDateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="voteDateTo" />
				            </li></ul>
				          </div>
				          <table id="voteSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a onclick="voteSearch()" id="voteSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				            <tr>
				            <td>
				            	<div class="btn_export"><a onclick="exportVoteList()" href="javascript:void(0);" id="exportVoteBtn">导出查询结果</a></div>
				            </td>
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
		</div>
		</td>
	</tr>
</table>

</div>
<div id="voteResult_div" class="main_body" style="display:none;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF">
    <input type="hidden" value="" id="voteResult_tpdc_id"></input>
    <div class=demo>
		  <div class="contents">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td valign="top" width="876" id="sms_voteResult_frame">
			    	<div>
			    	<table id="voteResultGrid"></table>
			    	</div>
			    </td>
			    <td width="242" valign="top">
			    	<div class="right_contents">
					  <div class="right_head1"></div>
					  <div class="right_box">
				        <div class="box_contents">
				        <form id="voteResultSearch" method="post">
				          <div id="voteTitle" class="search_list">
					            <h1>-按回复人名称查找</h1>
					            <ul>
					              <li>
					                <input id="voteReplyNameSearch" name="Input2" type="text"  class="input_search" />
					              </li>
					            </ul>
				          </div>
				          <div id="voteCreateBy" class="search_list">
					            <h1>-按回复人手机号码查找</h1>
					            <ul>
					              <li>
					                <input id="voteReplynumSearch" name="Input2" type="text"  class="input_search"/>
					              </li>
					            </ul>
				          </div>
				          <div id="voteResultTimeInterval" class="datebox_list">
				          <h1>-按回复时间查找</h1>
				            <ul><li>
				              -起始：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="voteResultDateFrom" />
				            </li><li>
				              -结束：<input class="input3 Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" id="voteResultDateTo" />
				            </li></ul>
				          </div>
				          <table id="voteSubmit" width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				              <td><div class="btn_search"><a onclick="voteResultSearch()" id="voteResultSerarchBtn" href="javascript:void(0);" >查 询</a></div></td>
				            </tr>
				            <tr>
					            <td>
					            	<div class="btn_export"><a onclick="exportVoteResultList()" href="javascript:void(0);" id="exportVoteResultBtn">导出查询结果</a></div>
					            </td></tr>
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
		</div>
		</td>
	</tr>
</table>

</div>
<div style="display:none;" class="main_body" id="voteResultDetails_div">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tbody><tr>
    <td valign="top" bgcolor="#FFFFFF">
    <div class="demo">
		  <div class="contents" style="background-color:#FFFFFF">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
			  <tbody><tr>
			    <td width="876" valign="top" id="vote_resultDetails_frame">
			    	<div style="overflow-y: auto;overflow-x: hidden;;overflow-y: auto;height:100px" class="gridfullscreen">
			    	<table>
				    	<thead>
					    	<tr class="tableopts">
						    	<td>
							    	<table>
								    	<thead>
									    	<tr class="tableopts">
									    		<td id="" class="tubh">
									    			<a id="" class="" href="javascript:voteDetailsReturn();">返回</a>
									    		</td>
									    	</tr>
								    	</thead>
							    	</table>
						    	</td>
					    	</tr>
				    	</thead>
			    	</table>
			    	</div>
			    </td>
			    </td>
			  </tr>
			</tbody></table> 
		  </div>
		</div>
		</td>
	</tr>
</tbody></table>
<div id="voteResult_details"></div>

</div>
<div class="clear"></div>
</body>
</html>

