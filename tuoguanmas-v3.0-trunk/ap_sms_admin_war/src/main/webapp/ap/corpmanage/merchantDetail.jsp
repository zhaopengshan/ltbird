<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragram" content="no-cache"> 
    <meta http-equiv="expires" content="0">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
    <title>企业自助注册</title>
</head>

<body>
	<div>
		<div class="config" style="overflow-y: auto;overflow-x: hidden;">
        <form id="regCorp">
        	<input type="hidden" id="openaccountPin" name="mbnMerchantVip.merchantPin" value="${mbnMerchantVipVO.merchantPin }"/>
        	<ul>
 				<li class="btn">
 					<input type="button" onclick="backToCorpmanage();" value="返回企业列表"/>
 				</li>
        		<li>
        			<span>企业基本信息</span>
        		</li>
        		<li>
        			<label class="rnameex">
        				<span class="needtip">*</span>
        				<span>企业名称（全称）</span>
        			</label>
            		<input id="name" type="text" name="mbnMerchantVip.name" readOnly="readOnly" value="${mbnMerchantVipVO.name }" />
        			<label class="rnameex">
        				<span class="needtip">*</span>
        				<span>企业管理员手机号</span>
        			</label>
            		<input id="mobile" type="text" name="userVO.mobile" readOnly="readOnly" value="${mbnMerchantVipVO.user.mobile}" />
            	</li>
        		<li>
        			<label class="rnameex">
        				<span class="needtip">*</span>
        				<span>企业管员理账号</span>
        			</label>
            		<input id="account" type="text" name="userVO.account" readOnly="readOnly" value="${mbnMerchantVipVO.user.account }"  />
        			<label class="rnameex">
        				<span class="needtip">*</span>
        				<span>企业管员理密码</span>
        			</label>
            		<input id="pwd" type="text" name="userVO.password" readOnly="readOnly" value="${mbnMerchantVipVO.user.password }" />
            	</li>
        		<li class="btn"><input type="button" onclick="sub();" value="提交"/></li>
        	</ul>
        </form>
        </div>
        
        <hr>
        <div class="config" style="overflow-y: auto;overflow-x: hidden;">
   		 <ul>
			<li>
				<input type="checkbox" id="dateFromTo" />
				起始日期:
				<input class="Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" 
						id="detailCorp_dateFrom" disabled="disabled"/>
				结束日期:
				<input class="Wdate" type="text"
						onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d H:mm:ss'})" 
						id="detailCorp_dateTo" disabled="disabled"/>
				<select id="detailCorp_operationType">
					<option selected="selected" value="-1">选择操作类型</option>
					<option value="1">增加(购买)</option>
					<option value="2">增加(赠送)</option>
					<option value="3">减少(撤销购买)</option>
					<option value="4">减少(撤销赠送)</option>
				</select>
                <input type="image" id="querydetailCorp_" src="./themes/mas3admin/images/address/address_search.gif" alt="查询" title="查询"/>
            </li>
        </ul>        
   		</div>
   		
   		<div class=gridfullscreen  style="overflow-y: auto;overflow-x: hidden; height: 239px;">
            <table id="detailCorp_Grid" >
                <thead>
                    <tr class="tableopts">
                        <td colspan="5" id="detailCorp_paginate" style="text-align: right">
                            <a href="javascript:void(0)" id="detailCorp_prepage">上一页</a>
                            <span id="detailCorp_curpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="detailCorp_totalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="detailCorp_nextpage">下一页</a>
                            <a href="javascript:void(0)" id="detailCorp_jumppage">跳转</a>
                            <input type="text" id="detailCorp_enterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                    <tr>
                        <th class="tabletrhead">流水号</th>
                        <th class="tabletrhead">时间</th>
                        <th class="tabletrhead">操作类型</th>
                        <th class="tabletrhead">操作条数（条）</th>
                        <th class="tabletrhead">当前余量（条）</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="detailCorp_rowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="detailCorp_datalist">

                </tbody>
                <tfoot>
                    <tr class="tableopts">
                        <td colspan="5" id="footdetailCorp_paginate" style="text-align: right">
                            <a href="javascript:void(0)" id="footdetailCorp_prepage">上一页</a>
                            <span id="footdetailCorp_curpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="footdetailCorp_totalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="footdetailCorp_nextpage">下一页</a>
                            <a href="javascript:void(0)" id="footdetailCorp_jumppage">跳转</a>
                            <input type="text" id="footdetailCorp_enterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                </tfoot>
            </table>
            <div id="detailCorp_Page"></div>
        </div>
     </div>      
<script type="text/javascript">
    $(document).ready(function(){
    	$("#dateFromTo").unbind("click").click(function(){
    		if($(this).attr("checked")){
    			$("#detailCorp_dateFrom").attr("disabled",false);
    			$("#detailCorp_dateTo").attr("disabled",false);
    		}else{
    			$("#detailCorp_dateFrom").attr("disabled","disabled");
    			$("#detailCorp_dateTo").attr("disabled","disabled");
    		}
    	});
        getPageDataMerchantDetail();
        $("#detailCorp_sselect").unbind("click").click(function(){
            if($("#detailCorp_datalist tr").length > 0) {
                if($("#detailCorp_sselect").attr("checked")) {
                    $("#detailCorp_datalist tr").each(function(){
                        $(this).find("input").attr("checked",true);
                    });
                } else {
                    $("#detailCorp_datalist tr").each(function(){
                        $(this).find("input").attr("checked",false);
                    });
                }                
            }
        });
        $("#detailCorp_prepage").unbind("click").click(function(){
            if(parseInt($("#detailCorp_curpage").html()) > 1) {
                var curPageNo = parseInt($("#detailCorp_curpage").html())-1;
                $("#detailCorp_curpage").html(curPageNo);
                $("#footdetailCorp_curpage").html(curPageNo);
            }
            getPageDataMerchantDetail();
        });
        $("#detailCorp_nextpage").unbind("click").click(function(){
            if(parseInt($("#detailCorp_curpage").html()) < parseInt($("#detailCorp_totalpages").html())) {
                var curPageNo = parseInt($("#detailCorp_curpage").html())+1;
                $("#detailCorp_curpage").html(curPageNo);
                $("#footdetailCorp_curpage").html(curPageNo);
            }
            getPageDataMerchantDetail();
        });
        $("#detailCorp_jumppage").unbind("click").click(function(){
            if(parseInt($("#detailCorp_enterpage").val()) < 1 || parseInt($("#detailCorp_enterpage").val()) > parseInt($("#detailCorp_totalpages").html())) {
                alert("对不起,您输入的页号非法!");
            } else {
                $("#detailCorp_curpage").html($("#detailCorp_enterpage").val());
                $("#footdetailCorp_curpage").html($("#detailCorp_enterpage").val());
                $("#footdetailCorp_enterpage").val($("#detailCorp_enterpage").val());
                getPageDataMerchantDetail();
            }
        });
        $("#footdetailCorp_prepage").unbind("click").click(function(){
            $("#detailCorp_prepage").click();
        });
        $("#footdetailCorp_nextpage").unbind("click").click(function(){
            $("#detailCorp_nextpage").click();
        });
        $("#footdetailCorp_jumppage").unbind("click").click(function(){
            $("#detailCorp_enterpage").val($("#footdetailCorp_enterpage").val());
            $("#detailCorp_jumppage").click();
        });
        $("#querydetailCorp_").unbind("click").click(function(){
            $("#detailCorp_curpage").html("1");
            $("#detailCorp_enterpage").val("1");
            $("#footdetailCorp_curpage").html("1");
            $("#footdetailCorp_enterpage").val("1");
            getPageDataMerchantDetail();
        });
    });
    function getPageDataMerchantDetail(){
    	var dateFrom="";
    	var dateTo="";
    	if($("#dateFromTo").attr("checked")){
    		dateFrom=$("#detailCorp_dateFrom").val();
    		dateTo=$("#detailCorp_dateTo").val();
    	}
        $.ajax({
            url: "./corpManageAction/listTunnelFlow.action",
            type:"GET",
            data:{
            	"page":$("#detailCorp_curpage").html(),
            	"rows":20,
            	"merchantPin":$("#openaccountPin").val(),
            	"dateTo":dateTo,
            	"dateFrom":dateFrom,
            	"operationType":$("#detailCorp_operationType").val()
            	},
            dataType: 'json',
            success:function(data){
                refreshListMerchantDetail(data);
            },
            error:function(data){
            }
        });
    }
    function refreshListMerchantDetail(data){        
        //设置翻页相关信息
        $("#detailCorp_totalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        $("#footdetailCorp_totalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        //设置表格数据
        $("#detailCorp_datalist tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            addRowMerchantDetail(data.rows[i]);
        }
    }
    function addRowMerchantDetail(data){
        //使用jquery拷贝方式生成行数据
        $("#detailCorp_rowtemplate").clone(true).appendTo("#detailCorp_datalist");
        $("#detailCorp_datalist tr:last").attr("id",data.id).show();
        $("#detailCorp_datalist tr:last td").eq(0).html(data.id);
        $("#detailCorp_datalist tr:last td").eq(1).html(data.modifyTime);
        // '1增加（购买）；2增加（赠送）;3减少（撤销购买）;4减少（撤销赠送）',
        var type=data.operationType;
        if(type==1){
        	type="增加（购买）";
        }else if(type==2){
        	type="增加（赠送）";
        }else if(type==3){
        	type="减少（撤销购买）";
        }else if(type==4){
        	type="减少（撤销赠送）";
        }
        $("#detailCorp_datalist tr:last td").eq(2).html(type);
        $("#detailCorp_datalist tr:last td").eq(3).html(data.number);
        $("#detailCorp_datalist tr:last td").eq(4).html(data.remainNumber);
    }
    function backToCorpmanage(){
    	var originalUrl = "./corpManageAction/forwardCorpList.action";
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
            alert(e);
        }
       $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").click();
    }
</script>
</body>
</html>