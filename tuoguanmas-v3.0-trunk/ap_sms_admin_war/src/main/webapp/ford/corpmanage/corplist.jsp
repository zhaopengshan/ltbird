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
        <form action="./corpManageAction/removeCorpFordAndConfig.action" method="post" id="deletecorplist">
            <input type="hidden" name="merchant.city" value="<s:property value='#parameters["merchant.city"]' />" id="merchantquery_citycondition" />
            <div class="gridwrapper" style="overflow-y: auto;overflow-x: hidden;">
                <table id="corpGrid">
                    <thead>
                        <tr class="tableopts">
                            <td colspan="3" class="btn">
                                <a href="javascript:void(0);"  id="forwardeditcorp">编辑</a>
                                <a href="javascript:void(0);"  id="deletecorp">删除</a>
                            </td>
                            <td colspan="3" id="corppaginate" style="text-align: right">
                                <a href="javascript:void(0)" id="corpprepage">上一页</a>
                                <span id="corpcurpage" style="margin: 0;padding: 0;" >1</span>
                                <span style="margin: 0 -5px 0 -5px;">/</span>
                                <span id="corptotalpages" style="margin: 0;padding: 0;"></span>
                                <a href="javascript:void(0)" id="corpnextpage">下一页</a>
                                <a href="javascript:void(0)" id="corpjumppage">跳转</a>
                                <input type="text" id="corpenterpagee" value="1" style="width:40px;" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tabletrhead"><input type="checkbox" name="corpsselect" id="corpsselect" /></th>
                            <th class="tabletrhead">企业名称</th>
                            <th class="tabletrhead">企业管理员账号</th>
                            <th class="tabletrhead">企业管理员密码</th>
                            <th class="tabletrhead">创建时间</th>
                            <th class="tabletrhead">最后更新时间</th>
                        </tr>
                        <tr class="tabletrboby" style="display:none" id="corprowtemplate">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>                    
                    </thead>
                    <tbody id="corpdatalist">

                    </tbody>
                    <tfoot>
                        <tr class="tableopts">
                            <td colspan="3" class="btn">
                                <a href="javascript:void(0);"  id="footforwardeditcorp">编辑</a>
                                <a href="javascript:void(0);"  id="footdeletecorp">删除</a>
                            </td>
                            <td colspan="3" id="footcorppaginate" style="text-align: right">
                                <a href="javascript:void(0)" id="footcorpprepage">上一页</a>
                                <span id="footcorpcurpage" style="margin: 0;padding: 0;" >1</span>
                                <span style="margin: 0 -5px 0 -5px;">/</span>
                                <span id="footcorptotalpages" style="margin: 0;padding: 0;"></span>
                                <a href="javascript:void(0)" id="footcorpnextpage">下一页</a>
                                <a href="javascript:void(0)" id="footcorpjumppage">跳转</a>
                                <input type="text" id="footcorpenterpagee" value="1" style="width:40px;" />
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </form>
        <ul class="rightbox">
            <li><span>-按企业名称查找</span></li>
            <li><input id="searchCorpName" name="merchant.name" type="text" class="input_search"  /></li>            
            <li class="btn"><a href="javascript:void(0);"  id="queryCorp">查 询</a></li>
        </ul>
    </body>    
</html>
<script type="text/javascript">
    $(document).ready(function(){
        getCorpPageData();
        $("#corpsselect").unbind("click").click(function(){
            if($("#corpdatalist tr").length > 0) {
                if($("#corpsselect").attr("checked")) {
                    $("#corpdatalist tr").each(function(){
                        $(this).find("input").attr("checked",true);
                    });
                } else {
                    $("#corpdatalist tr").each(function(){
                        $(this).find("input").attr("checked",false);
                    });
                }                
            }
        });
        $("#corpprepage").unbind("click").click(function(){
            if(parseInt($("#corpcurpage").html()) > 1) {
                var curPageNo = parseInt($("#corpcurpage").html())-1;
                $("#corpcurpage").html(curPageNo);
                $("#footcorpcurpage").html(curPageNo);
            }
            cancelCorpAllSelectedId();            
            getCorpPageData();
        });
    $("#corpnextpage").unbind("click").click(function(){
        var totalpages = parseInt($("#corptotalpages").html());
        if($("#corpcurpage").html() < totalpages) {
            var curPageNo = parseInt($("#corpcurpage").html())+1;
            $("#corpcurpage").html(curPageNo);
            $("#footcorpcurpage").html(curPageNo);
        }
        cancelCorpAllSelectedId();
        getCorpPageData();
    });
    $("#corpjumppage").unbind("click").click(function(){
        var totalpages = parseInt($("#corptotalpages").html());
        if($("#corpenterpagee").val() < 1 || $("#corpenterpagee").val() > totalpages) {
            alert("对不起,您输入的页号非法!");
        } else {
            $("#corpcurpage").html($("#corpenterpagee").val());
            $("#footcorpcurpage").html($("#corpenterpagee").val());
            $("#footcorpenterpagee").val($("#corpenterpagee").val());
            cancelCorpAllSelectedId();
            getCorpPageData();
        }
    });
    $("#footcorpprepage").unbind("click").click(function(){
        $("#corpprepage").click();
    });
    $("#footcorpnextpage").unbind("click").click(function(){
        $("#corpnextpage").click();
    });
    $("#footcorpjumppage").unbind("click").click(function(){
        $("#corpenterpagee").val($("#footcorpenterpagee").val());
        $("#corpjumppage").click();
    });
    $("#forwardeditcorp").unbind("click").click(function(){
        if($("#corpdatalist tr").find(":checked").length < 1) {
            alert("请选择要编辑的企业!");
            return;
        }
        if($("#corpdatalist tr").find(":checked").length > 1) {
            alert("请选择一个企业进行编辑!");
            return;
        }
        fowardEditCorp($("#corpdatalist tr").find(":checked").val());
    });
    $("#deletecorp").unbind("click").click(function(){
        if($("#corpdatalist tr").find(":checked").length < 1) {
            alert("请选择要删除的企业!");
            return;
        }
        if(confirm("您确定要删除选中的"+$("#corpdatalist tr").find(":checked").length+"个企业吗？")){ 
            $('#deletecorplist').ajaxSubmit(function(data){
                var resultInfo = jQuery.parseJSON(data)
                alert(resultInfo.resultInfo);
                getCorpPageData();
            });           
        }            
    });
    $("#footforwardeditcorp").unbind("click").click(function(){
        $("#forwardeditcorp").click();
    });
    $("#footdeletecorp").unbind("click").click(function(){
        $("#deletecorp").click();
    });
    $("#queryCorp").unbind("click").click(function(){           
        $("#corpcurpage").html("1");
        $("#corpenterpagee").val("1");
        $("#footcorpcurpage").html("1");
        $("#footcorpenterpagee").val("1");             
        cancelCorpAllSelectedId();            
        getCorpPageData();
    });
});
function getCorpPageData(){
    $.ajax({
        url: "./corpManageAction/pageFordCorp.action?page="+$("#corpcurpage").html()+"&rows="+20
            +"&merchant.name="+encodeURI(encodeURI($("#searchCorpName").val()))+"&merchant.city="+$("#merchantquery_citycondition").val(),
        type:"POST",
        dataType: 'json',
        success:function(data){
            refreshCorpList(data);
            $("#corpdatalist tr").each(function(){
                $(this).find("input").live("click",function(event){
                    event.stopPropagation();
                    if($(this).attr("checked")) {
                        setCorpAllSelectedId();
                    } else {
                        cancelCorpAllSelectedId();
                    }
                });
                $(this).live("click",function(){
                    if($(this).find("input").attr("checked")) {
                        $(this).find("input").attr("checked",false);
                        cancelCorpAllSelectedId();                            
                    } else {
                        $(this).find("input").attr("checked",true);
                        setCorpAllSelectedId();
                    }                        
                })
            })
        },
        error:function(data){
        }
    });
}
function refreshCorpList(data){        
    //设置翻页相关信息
    $("#corptotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
    $("#footcorptotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
    //设置表格数据
    $("#corpdatalist tr").remove();
    for(var i=0;data&&data.rows&&i<data.rows.length;i++){
        addCorpRow(data.rows[i]);
    }
}
function addCorpRow(data){
    //使用jquery拷贝方式生成行数据
    $("#corprowtemplate").clone(true).appendTo("#corpdatalist");
    $("#corpdatalist tr:last").attr("id",data.merchantPin).show();
    $("#corpdatalist tr:last td").eq(0).html("<input type='checkbox' name='merchants.merchantPin' value='"+data.merchantPin+"'/>");
    $("#corpdatalist tr:last td").eq(1).html("<a href='javascript:void(0)' onclick='fowardEditCorp(\""+data.merchantPin+"\")'>"+data.name+"</a>");
    $("#corpdatalist tr:last td").eq(2).html(data.user.account);        
    $("#corpdatalist tr:last td").eq(3).html(data.user.password);
    $("#corpdatalist tr:last td").eq(4).html(data.createTime);
    $("#corpdatalist tr:last td").eq(5).html(data.lastUpdateTime);
}
function setCorpAllSelectedId(){
    var allselected = true;
    $("#corpdatalist tr").each(function(){                 
        if($(this).find("input").attr("checked")){                                    
        } else {
            allselected = false;
        }            
    });
    if(allselected) {
        $("#corpsselect").attr("checked",true);
    }
}
function cancelCorpAllSelectedId(){
    if($("#corpsselect").attr("checked")){
        $("#corpsselect").attr("checked",false);
    }
}
function fowardEditCorp(merchantPin){
    // 提交 编辑
    <s:if test="#session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_PROVINCE_ADMIN || #session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_ENTERPRISE_ADMIN">
        var tabid="corpview0001";
        var taburl="./corpManageAction/showCorpFordAndConfig.action?merchant.merchantPin="+merchantPin;
        var tabTitle="企业信息浏览";
        var navpathtitle="当前位置：企业信息浏览";
        $("#navpath").html(navpathtitle);
        var tabContentClone = $(".tabContent").clone();
        var jcTabs = tabContentClone.removeClass("tabContent").load(taburl);
        tabpanel.addTab({
            id: tabid,
            title: tabTitle ,     
            html:jcTabs,     
            closable: true
        }); 
    </s:if>
    <s:else>
        var originalUrl = "./ford/corpmanage/corpadd.jsp";
        var tempUrl = "./corpManageAction/showCorpFordAndConfig.action?merchant.merchantPin="+merchantPin;
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    </s:else>    
}
</script>