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
            <table id="roleGrid">
                <thead>
					<tr>
                        <td style="width:50px"></td>
                        <td style="width:150px"></td>
                        <td></td>
                        <td style="width:150px"></td>
                    </tr> 
                    <tr class="tableopts">
                        <td colspan="2" class="btn">
                            <a href="javascript:void(0);"  id="forwardeditrole">编辑</a>
                            <a href="javascript:void(0);"  id="deleterole">删除</a>
                        </td>
                        <td colspan="2" id="rolepaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="roleprepage">上一页</a>
                            <span id="rolecurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="roletotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="rolenextpage">下一页</a>
                            <a href="javascript:void(0)" id="rolejumppage">跳转</a>
                            <input type="text" id="roleenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                    <tr>
                        <th class="tabletrhead"><input type="checkbox" name="rolesselect" id="rolesselect" /></th>
                        <th class="tabletrhead">角色名称</th>
                        <th class="tabletrhead">角色描述</th>
                        <th class="tabletrhead">角色授权</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="rolerowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="roledatalist">

                </tbody>
                <tfoot>
                    <tr class="tableopts">
                        <td colspan="2" class="btn">
                            <a href="javascript:void(0);"  id="footforwardeditrole">编辑</a>
                            <a href="javascript:void(0);"  id="footdeleterole">删除</a>
                        </td>
                        <td colspan="2" id="footrolepaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="footroleprepage">上一页</a>
                            <span id="footrolecurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="footroletotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="footrolenextpage">下一页</a>
                            <a href="javascript:void(0)" id="footrolejumppage">跳转</a>
                            <input type="text" id="footroleenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <ul class="rightbox">
            <li><span>-按角色名称查找</span></li>
            <li><input id="searchRoleName" name="portalRole.name" type="text" class="input_search"  /></li>
            <li><span>-按角色描述查找</span></li>
            <li><input id="searchRoleDesc" name="portalRole.description" type="text" class="input_search"  /></li>
            <li class="btn"><a href="javascript:void(0);"  id="queryRole" style="margin-left:50px;">查 询</a></li>
        </ul>
    </body>    
</html>
<script type="text/javascript">
    $(document).ready(function(){
        getRolePageData();
        $("#rolesselect").unbind("click").click(function(){
            if($("#roledatalist tr").length > 0) {
                if($("#rolesselect").attr("checked")) {
                    $("#roledatalist tr").each(function(){
                        $(this).find("input").attr("checked",true);
                    });
                } else {
                    $("#roledatalist tr").each(function(){
                        $(this).find("input").attr("checked",false);
                    });
                }                
            }
        });
        $("#roleprepage").unbind("click").click(function(){
            if(parseInt($("#rolecurpage").html()) > 1) {
                var curPageNo = parseInt($("#rolecurpage").html())-1;
                $("#rolecurpage").html(curPageNo);
                $("#footrolecurpage").html(curPageNo);
            }
            cancelRoleAllSelectedId();            
            getRolePageData();
        });
        $("#rolenextpage").unbind("click").click(function(){
        	var totalpages = parseInt($("#roletotalpages").html());
            if($("#rolecurpage").html() < totalpages) {
                var curPageNo = parseInt($("#rolecurpage").html())+1;
                $("#rolecurpage").html(curPageNo);
                $("#footrolecurpage").html(curPageNo);
            }
            cancelRoleAllSelectedId();
            getRolePageData();
        });
        $("#rolejumppage").unbind("click").click(function(){
        	var totalpages = parseInt($("#roletotalpages").html());
            if($("#roleenterpage").val() < 1 || $("#roleenterpage").val() > totalpages) {
                alert("对不起,您输入的页号非法!");
            } else {
                $("#rolecurpage").html($("#roleenterpage").val());
                $("#footrolecurpage").html($("#roleenterpage").val());
                $("#footroleenterpage").val($("#roleenterpage").val());
                cancelRoleAllSelectedId();
                getRolePageData();
            }
        });
        $("#footroleprepage").unbind("click").click(function(){
            $("#roleprepage").click();
        });
        $("#footrolenextpage").unbind("click").click(function(){
            $("#rolenextpage").click();
        });
        $("#footrolejumppage").unbind("click").click(function(){
            $("#roleenterpage").val($("#footroleenterpage").val());
            $("#rolejumppage").click();
        });
        $("#forwardeditrole").unbind("click").click(function(){
            if($("#roledatalist tr").find(":checked").length < 1) {
                alert("请选择要编辑的角色!");
                return;
            }
            if($("#roledatalist tr").find(":checked").length > 1) {
                alert("请选择一个角色进行编辑!");
                return;
            }
            fowardEditRole($("#roledatalist tr").find(":checked").val());
        });
        $("#deleterole").unbind("click").click(function(){
            if($("#roledatalist tr").find(":checked").length < 1) {
                alert("请选择要删除的角色!");
                return;
            }
            if(confirm("您确定要删除选中的"+$("#roledatalist tr").find(":checked").length+"条角色吗？")){
                var rows = "";
                $("#roledatalist tr").find(":checked").each(function(){
                    rows+=$(this).val()+","
                })
                $.ajax({ url: "./roleAction/deleteRoles.action",
                    data : {"roleIds":rows},
                    dataType: "json",
                    type: "post",
                    success: function(data){
                        if(data.resultInfo){
                            alert(data.resultInfo);
                        } else {
                            getRolePageData();
                        }                        
                    },
                    error:function(data){
                    }
                });
            }            
        });
        $("#footforwardeditrole").unbind("click").click(function(){
            $("#forwardeditrole").click();
        });
        $("#footdeleterole").unbind("click").click(function(){
            $("#deleterole").click();
        });
        $("#queryRole").unbind("click").click(function(){           
            $("#rolecurpage").html("1");
            $("#roleenterpage").val("1");
            $("#footrolecurpage").html("1");
            $("#footroleenterpage").val("1");             
            cancelRoleAllSelectedId();            
            getRolePageData();
        });
    });
    function getRolePageData(){
        $.ajax({
            url: "./roleAction/masquery.action?page="+$("#rolecurpage").html()+"&rows="+20
            		+"&portalRole.name="+encodeURI(encodeURI($("#searchRoleName").val()))+"&portalRole.description="+encodeURI(encodeURI($("#searchRoleDesc").val())),
            type:"GET",
            dataType: 'json',
            success:function(data){
                refreshRoleList(data);
                $("#roledatalist tr").each(function(){
                    $(this).find("input").live("click",function(event){
                        event.stopPropagation();
                        if($(this).attr("checked")) {
                            setRoleAllSelectedId();
                        } else {
                            cancelRoleAllSelectedId();
                        }
                    });
                    $(this).live("click",function(){
                        if($(this).find("input").attr("checked")) {
                            $(this).find("input").attr("checked",false);
                            cancelRoleAllSelectedId();                            
                        } else {
                            $(this).find("input").attr("checked",true);
                            setRoleAllSelectedId();
                        }                        
                    })
                })
            },
            error:function(data){
            }
        });
    }
    function refreshRoleList(data){        
        //设置翻页相关信息
        $("#roletotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        $("#footroletotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        //设置表格数据
        $("#roledatalist tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            addRoleRow(data.rows[i]);
        }
    }
    function addRoleRow(data){
        //使用jquery拷贝方式生成行数据
        $("#rolerowtemplate").clone(true).appendTo("#roledatalist");
        $("#roledatalist tr:last").attr("id",data.id).show();
        $("#roledatalist tr:last td").eq(0).html("<input type='checkbox' name='roleId' value='"+data.id+"'/>");
        $("#roledatalist tr:last td").eq(1).html("<a href='javascript:void(0)' onclick='fowardEditRole(\""+data.id+"\")'>"+data.name+"</a>");
        $("#roledatalist tr:last td").eq(2).html(data.description);        
        $("#roledatalist tr:last td").eq(3).html("<a href=\"javascript:void(0);\" onclick=\"javascript:givePower('"+data.id+"','"+data.name+"')\"><img src=\"./themes/mas3/images/role/u108_normal.gif\" width=\"13\" height=\"16\" border=\"0\" ></a>");
    }
    function setRoleAllSelectedId(){
        var allselected = true;
        $("#roledatalist tr").each(function(){                 
            if($(this).find("input").attr("checked")){                                    
            } else {
                allselected = false;
            }            
        });
        if(allselected) {
            $("#rolesselect").attr("checked",true);
        }
    }
    function cancelRoleAllSelectedId(){
        if($("#rolesselect").attr("checked")){
            $("#rolesselect").attr("checked",false);
        }
    }
    function fowardEditRole(roleId){
        // 提交 编辑
        var originalUrl = "./roleAction/masforward.action?flag=addForward";
        var tempUrl = "./roleAction/masforward.action?portalRole.id="+roleId+"&flag=updateForward";
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    }
    function givePower(id, name){
        var tabid="rolepower222";
        var taburl="./roleAction/masforward.action?flag=powerForward&portalRole.id="+id+"&portalRole.name="+name
        var tabTitle="角色赋权";
        var navpathtitle="当前位置：角色赋权";
        $("#navpath").html(navpathtitle);
        var tabContentClone = $(".tabContent").clone();
        var jcTabs = tabContentClone.removeClass("tabContent").load(taburl);
        tabpanel.addTab({
            id: tabid,
            title: tabTitle ,     
            html:jcTabs,     
            closable: true
        }); 
    }    
</script>
