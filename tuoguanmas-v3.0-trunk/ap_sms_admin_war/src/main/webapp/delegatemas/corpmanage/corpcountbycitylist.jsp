<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
    </head>
    <body>
        <div class="gridwrapper" style="overflow-y: auto;overflow-x: hidden;">
            <table id="corpCountGrip">
                <thead>                    
                    <tr>
                        <th class="tabletrhead">地市名称</th>
                        <th class="tabletrhead">企业数</th>
                        <th class="tabletrhead">地市管理员账号</th>
                        <th class="tabletrhead">地市管理员密码</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="corpcountrowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="corpcountdatalist">

                </tbody>                
            </table>
        </div>       
    </body>    
</html>
<script type="text/javascript">
    $(document).ready(function(){
        getCorpCountPageData();
    });
    function getCorpCountPageData(){
        $.ajax({
            url: "./corpManageAction/corpCountList.action",
            type:"GET",
            dataType: 'json',
            success:function(data){
                refreshCorpCountList(data);                
            },
            error:function(data){
            }
        });
    }
    function refreshCorpCountList(data){      
        //设置表格数据
        $("#corpcountdatalist tr").remove();
        for(var i=0;data&&data.corpCountList&&i<data.corpCountList.length;i++){
            addCorpCountRow(data.corpCountList[i]);
        }
    }
    function addCorpCountRow(data){
        //使用jquery拷贝方式生成行数据
        $("#corpcountrowtemplate").clone(true).appendTo("#corpcountdatalist");
        $("#corpcountdatalist tr:last").attr("id",data.cityId).show();
        $("#corpcountdatalist tr:last td").eq(0).html("<a href='javascript:void(0)' onclick='fowardEditCorpCount(\""+data.cityId+"\")'>"+data.cityName+"</a>");
        $("#corpcountdatalist tr:last td").eq(1).html(data.corpCount);
        $("#corpcountdatalist tr:last td").eq(2).html(data.loginAccount);        
        $("#corpcountdatalist tr:last td").eq(3).html(data.loginPwd);
    }    
    function fowardEditCorpCount(cityId){
        // 提交 编辑
        var originalUrl = "./delegatemas/corpmanage/corplist.jsp";
        var tempUrl = "./delegatemas/corpmanage/corplist.jsp?merchant.city="+cityId;
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    }    
</script>
