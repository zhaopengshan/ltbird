<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
    </head>
    <body>
    	<div style="overflow-y: auto;overflow-x: hidden;">
	    	<div class="config" style="overflow-y: auto;overflow-x: hidden;">
	           	<ul>
	            	<li>
	            		<input id="searchmerchantName" name="mbnMerchantVipVO.name" type="text" value="输入企业名称"   />
						<input id="searchmerchantMobile" name="mbnMerchantVipVO.user.mobile" type="text" value="输入企业管理员手机号"   />
	        			<input type="image" id="querymerchant" src="./themes/mas3admin/images/address/address_search.gif" alt="查询" title="查询"/>
	        		</li>
	        	</ul>
	        </div>
	        <div class="gridfullscreen"  style="overflow-y: auto;overflow-x: hidden;height: 382px;">
            <table id="merchantGrid" >
                <thead>
                    <tr class="tableopts">
                        <td colspan="4" class="btn">
                            <a href="javascript:void(0);"  id="forwardeditmerchant">新增（注册）</a>
                            <a href="javascript:void(0);"  id="deletemerchant">删除</a>
                        </td>
                        <td colspan="3" id="merchantpaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="merchantprepage">上一页</a>
                            <span id="merchantcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="merchanttotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="merchantnextpage">下一页</a>
                            <a href="javascript:void(0)" id="merchantjumppage">跳转</a>
                            <input type="text" id="merchantenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                    <tr>
                        <th class="tabletrhead"><input type="checkbox" name="merchantsselect" id="merchantsselect" /></th>
                        <th class="tabletrhead">企业名称</th>
                        <th class="tabletrhead">企业管理员账号</th>
                        <th class="tabletrhead">企业管理员手机号码</th>
                        <th class="tabletrhead">创建日期</th>
                        <th class="tabletrhead">最后更新日期</th>
                        <th class="tabletrhead">操作</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="merchantrowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="merchantdatalist">

                </tbody>
                <tfoot>
                    <tr class="tableopts">
                        <td colspan="4" class="btn">
                            <a href="javascript:void(0);"  id="footforwardeditmerchant">新增（注册）</a>
                            <a href="javascript:void(0);"  id="footdeletemerchant">删除</a>
                        </td>
                        <td colspan="3" id="footmerchantpaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="footmerchantprepage">上一页</a>
                            <span id="footmerchantcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="footmerchanttotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="footmerchantnextpage">下一页</a>
                            <a href="javascript:void(0)" id="footmerchantjumppage">跳转</a>
                            <input type="text" id="footmerchantenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                </tfoot>
            </table>
            <div id="merchantPage"></div>
        </div>
        </div>
<script type="text/javascript">	
	var showShowMerchantUrl='./corpManageAction/queryMerchant.action';
	var deleteShowMerchantUrl='./openAccountAction/logoutMerchant.action';
	
	var insertShowMerchantUrl='./corpManageAction/preForUpdate.action';
	var updateShowMerchantUrl='./corpManageAction/preForUpdate.action';
	
	var detailShowMerchantUrl='./corpManageAction/preForCorpDetail.action';
	var searchmerchantName_word="输入企业名称";
	var searchmerchantMobile_word="输入企业管理员手机号";
    $(document).ready(function(){
    	$("#searchmerchantName").focus(function(){
            if($("#searchmerchantName").val() == searchmerchantName_word)
                $("#searchmerchantName").val("");
        }).focusout(function() {
            if($("#searchmerchantName").val() == ""){
                $("#searchmerchantName").val(searchmerchantName_word);
            }
        });
    	$("#searchmerchantMobile").focus(function(){
            if($("#searchmerchantMobile").val() == searchmerchantMobile_word)
                $("#searchmerchantMobile").val("");
        }).focusout(function() {
            if($("#searchmerchantMobile").val() == ""){
                $("#searchmerchantMobile").val(searchmerchantMobile_word);
            }
        }); 
        getPageDataShowMerchant();
        $("#merchantsselect").unbind("click").click(function(){
            if($("#merchantdatalist tr").length > 0) {
                if($("#merchantsselect").attr("checked")) {
                    $("#merchantdatalist tr").each(function(){
                        $(this).find("input").attr("checked",true);
                    });
                } else {
                    $("#merchantdatalist tr").each(function(){
                        $(this).find("input").attr("checked",false);
                    });
                }                
            }
        });
      $("#merchantprepage").unbind("click").click(function(){
            if(parseInt($("#merchantcurpage").html()) > 1) {
                var curPageNo = parseInt($("#merchantcurpage").html())-1;
                $("#merchantcurpage").html(curPageNo);
                $("#footmerchantcurpage").html(curPageNo);
            }
            cancelShowMerchantAllSelectedId();            
            getPageDataShowMerchant();
        });
        $("#merchantnextpage").unbind("click").click(function(){
            if(parseInt($("#merchantcurpage").html()) < parseInt($("#merchanttotalpages").html())) {
                var curPageNo = parseInt($("#merchantcurpage").html())+1;
                $("#merchantcurpage").html(curPageNo);
                $("#footmerchantcurpage").html(curPageNo);
            }
            cancelShowMerchantAllSelectedId();
            getPageDataShowMerchant();
        });
        $("#merchantjumppage").unbind("click").click(function(){
            if(parseInt($("#merchantenterpage").val()) < 1 || parseInt($("#merchantenterpage").val()) > parseInt($("#merchanttotalpages").html())) {
                alert("对不起,您输入的页号非法!");
            } else {
                $("#merchantcurpage").html($("#merchantenterpage").val());
                $("#footmerchantcurpage").html($("#merchantenterpage").val());
                $("#footmerchantenterpage").val($("#merchantenterpage").val());
                cancelShowMerchantAllSelectedId();
                getPageDataShowMerchant();
            }
        });
        $("#footmerchantprepage").unbind("click").click(function(){
            $("#merchantprepage").click();
        });
        $("#footmerchantnextpage").unbind("click").click(function(){
            $("#merchantnextpage").click();
        });
        $("#footmerchantjumppage").unbind("click").click(function(){
            $("#merchantenterpage").val($("#footmerchantenterpage").val());
            $("#merchantjumppage").click();
        });
      $("#forwardeditmerchant").unbind("click").click(function(){
    	  editCorp('-1','（填写企业全称）','（11位手机号码）','（6-20位，字母/数字/下划线组成）','（6-20位，字母/数字/下划线组成）');
        }); 
         $("#deletemerchant").unbind("click").click(function(){
            if($("#merchantdatalist tr").find(":checked").length < 1) {
                alert("请选择要删除的用户!");
                return;
            }
            if(confirm("您确定要删除选中的"+$("#merchantdatalist tr").find(":checked").length+"条用户吗？")){
                var rows = "";
                $("#merchantdatalist tr").find(":checked").each(function(){
                    rows+=$(this).val()+","
                })
                $.ajax({ url: deleteShowMerchantUrl,
                    data : "merchantIds="+ rows,
                    dataType: "json",
                    type: "post",
                    success: function(data){
                        getPageDataShowMerchant();
                        alert(data.resultMsg);
                    },
                    error:function(data){
                    }
                });
            }            
        }); 
        $("#footforwardeditmerchant").unbind("click").click(function(){
            $("#forwardeditmerchant").click();
        });
        $("#footdeletemerchant").unbind("click").click(function(){
            $("#deletemerchant").click();
        }); 
        $("#querymerchant").unbind("click").click(function(){
            $("#merchantcurpage").html("1");
            $("#merchantenterpage").val("1");
            $("#footmerchantcurpage").html("1");
            $("#footmerchantenterpage").val("1");
            cancelShowMerchantAllSelectedId(); 
            getPageDataShowMerchant();
        }); 
    });
    function getPageDataShowMerchant(){
    	//alert("getPageDataShowMerchant");
    	var merchantName=$("#searchmerchantName").val();
		var userMobile=$("#searchmerchantMobile").val();
    	if(merchantName=="输入企业名称"){
    		merchantName="";
		}/* else{
			merchantName=encodeURI(encodeURI(merchantName));
		} */
		if(userMobile=="输入企业管理员手机号"){
			userMobile="";
		}/* else{
			userMobile=encodeURI(encodeURI(userMobile));
		}  */
       $.ajax({
            url: showShowMerchantUrl,
            type:"post",
            data:{
            	"page":$("#merchantcurpage").html(),
            	"rows":20,
            	"mbnMerchantVipVO.name":merchantName,
            	"mbnMerchantVipVO.user.mobile":userMobile},
            dataType: 'json',
            success:function(data){
            	
                refreshListShowMerchant(data);
                $("#merchantdatalist tr").each(function(){
                    $(this).find("input").live("click",function(event){
                        event.stopPropagation();
                        if($(this).attr("checked")) {
                            setShowMerchantAllSelectedId();
                        } else {
                            cancelShowMerchantAllSelectedId();
                        }
                    });
                    $(this).live("click",function(){
                        if($(this).find("input").attr("checked")) {
                            $(this).find("input").attr("checked",false);
                            cancelShowMerchantAllSelectedId();                            
                        } else {
                            $(this).find("input").attr("checked",true);
                            setShowMerchantAllSelectedId();
                        }                        
                    })
                })
            },
            error:function(data){
            } 
        });
    }
    function refreshListShowMerchant(data){        
        //设置翻页相关信息
        $("#merchanttotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        $("#footmerchanttotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        //设置表格数据
        $("#merchantdatalist tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            addShowMerchantRow(data.rows[i]);
        }
    }
    function addShowMerchantRow(data){
        //使用jquery拷贝方式生成行数据
        $("#merchantrowtemplate").clone(true).appendTo("#merchantdatalist");
        $("#merchantdatalist tr:last").attr("id",data.id).show();
        $("#merchantdatalist tr:last td").eq(0).html("<input type='checkbox' name='merchantId' value='"+data.merchantPin+"'/>");
        $("#merchantdatalist tr:last td").eq(1).html("<a href='javascript:void(0)' onclick='editCorp(\""+data.merchantPin+"\",\""+data.name+"\",\""+data.user.mobile+"\",\""+data.user.account+"\",\""+data.user.password+"\");'>"+data.name+"</a>");
        $("#merchantdatalist tr:last td").eq(2).html(data.user.account);
        $("#merchantdatalist tr:last td").eq(3).html(data.user.mobile);
        $("#merchantdatalist tr:last td").eq(4).html(data.createTime);
        $("#merchantdatalist tr:last td").eq(5).html(data.lastUpdateTime);
        $("#merchantdatalist tr:last td").eq(6).html("<a href='javascript:void(0)' onclick='detailCorp(\""+data.merchantPin+"\",\""+data.name+"\",\""+data.user.mobile+"\",\""+data.user.account+"\",\""+data.user.password+"\");'>"+"查看充值及消费流水"+"</a>");
    }
    function setShowMerchantAllSelectedId(){
        var allselected = true;
        $("#merchantdatalist tr").each(function(){                 
            if($(this).find("input").attr("checked")){                                    
            } else {
                allselected = false;
            }            
        });
        if(allselected) {
            $("#merchantsselect").attr("checked",true);
        }
    }
    function cancelShowMerchantAllSelectedId(){
        if($("#merchantsselect").attr("checked")){
            $("#merchantsselect").attr("checked",false);
        }
    }
    function editCorp(pin,name,mobile,account,pwd){
    	var temp=updateShowMerchantUrl;
    	temp=temp
    			 +
    			"?mbnMerchantVipVO.merchantPin="+pin+
    			"&mbnMerchantVipVO.name="+encodeURI(encodeURI(name))+
    			"&mbnMerchantVipVO.user.account="+encodeURI(encodeURI(account))+
    			"&mbnMerchantVipVO.user.password="+encodeURI(encodeURI(pwd))+
    			"&mbnMerchantVipVO.user.mobile="+encodeURI(encodeURI(mobile)) ;
    	
    	var originalUrl = "./corpManageAction/forwardCorpList.action";
        var tempUrl = temp;
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
       $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
       $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    	
    }
    function detailCorp(pin,name,mobile,account,pwd){
    	var temp=detailShowMerchantUrl;
    	temp=temp
    			 +
    			"?mbnMerchantVipVO.merchantPin="+pin+
    			"&mbnMerchantVipVO.name="+encodeURI(encodeURI(name))+
    			"&mbnMerchantVipVO.user.account="+encodeURI(encodeURI(account))+
    			"&mbnMerchantVipVO.user.password="+encodeURI(encodeURI(pwd))+
    			"&mbnMerchantVipVO.user.mobile="+encodeURI(encodeURI(mobile)) ;
    	var originalUrl = "./corpManageAction/forwardCorpList.action";
        var tempUrl = temp;
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
       $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
       $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    }
    //开新页面
    /* function editListMerchant(merchantId){
        var originalUrl = "./merchantAction/forward.action?flag=addForward";
        var tempUrl = "./merchantAction/forward.action?flag=updateForward&portalmerchant.id="+merchantId;
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
       
    }
    */
</script>
    </body>
</html>