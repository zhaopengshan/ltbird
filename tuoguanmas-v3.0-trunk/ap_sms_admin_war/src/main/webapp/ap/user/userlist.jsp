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
    	<div id="userSmsCheck"></div>
        <div class="gridwrapper" style="overflow-y: auto;overflow-x: hidden;">
            <table id="userGrid" >
                <thead>
                	 <tr>
                        <td style="width:50px;"></td>
                        <td style="width:100px;"></td>
                        <td style="width:150px;"></td>
                        <td style="width:50px;"></td>
                        <td style="width:150px;"></td>
                        <td></td>
                        <td style="width:50px;"></td>
                        <td style="width:100px;"></td>
                    </tr> 
                    <tr class="tableopts">
                        <td colspan="4" class="btn">
                            <a href="javascript:void(0);"  id="forwardedituser">编辑</a>
                            <a href="javascript:void(0);"  id="deleteuser">删除</a>
                              <a href="javascript:void(0);"  id="lockFlag_button">解锁</a>
                        </td>
                        <td colspan="4" id="userpaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="userprepage">上一页</a>
                            <span id="usercurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="usertotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="usernextpage">下一页</a>
                            <a href="javascript:void(0)" id="userjumppage">跳转</a>
                            <input type="text" id="userenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                    <tr>
                        <th class="tabletrhead"><input type="checkbox" name="usersselect" id="usersselect" /></th>
                        <th class="tabletrhead">用户名</th>
                        <th class="tabletrhead">姓名</th>
                        <th class="tabletrhead">性别</th>
                        <th class="tabletrhead">手机号</th>
                        <th class="tabletrhead">角色</th>
                        <th class="tabletrhead">状态</th>
                        <th class="tabletrhead">短信验证码</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="userrowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="userdatalist">

                </tbody>
                <tfoot>
                    <tr class="tableopts">
                        <td colspan="4" class="btn">
                            <a href="javascript:void(0);"  id="footforwardedituser">编辑</a>
                            <a href="javascript:void(0);"  id="footdeleteuser">删除</a>
                            <a href="javascript:void(0);"  id="lockFlag_button2">解锁</a>
                        </td>
                        <td colspan="4" id="footuserpaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="footuserprepage">上一页</a>
                            <span id="footusercurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="footusertotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="footusernextpage">下一页</a>
                            <a href="javascript:void(0)" id="footuserjumppage">跳转</a>
                            <input type="text" id="footuserenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <ul class="rightbox">
            <li><span>-按用户名称查找</span></li>
            <li><input name="portalUser.account" type="text" class="input_search" id="searchUserAccount"/></li>
            <li><span>-按手机号查找</span></li>
            <li><input name="portalUser.mobile" type="text"	class="input_search" id="searchUserMobile" /></li>
            <li><span>-按电子邮件查找</span></li>
            <li><input name="portalUser.email" type="text" class="input_search" id="searchUserEmail"/></li>
            <li><span>-按状态查找</span></li>
            <li>
                <select name="portalUser.activeFlag" id="searchUserActiveFlag">
                    <option value="">全部</option>
                    <option value="1">使用</option>
                    <option value="0">暂停/锁定</option>
                </select>
            </li>
            <li><span>-按用户所属角色查找</span></li>
            <li>
                <select id="searchUserRole">
                    <option value="-99">全部</option>
                    <s:iterator id="role" value="#request.rolesList">
                        <option value="${role.id }">${role.name}</option>
                    </s:iterator>
                </select>
            </li>
            <li class="btn"><a href="javascript:void(0);" id="queryUser">查 询</a></li>
        </ul>
    </body>
</html>
<script type="text/javascript">	
    $(document).ready(function(){
        getPageData();
        $("#usersselect").unbind("click").click(function(){
            if($("#userdatalist tr").length > 0) {
                if($("#usersselect").attr("checked")) {
                    $("#userdatalist tr").each(function(){
                        $(this).find("input").attr("checked",true);
                    });
                } else {
                    $("#userdatalist tr").each(function(){
                        $(this).find("input").attr("checked",false);
                    });
                }                
            }
        });
        $("#userprepage").unbind("click").click(function(){
            if(parseInt($("#usercurpage").html()) > 1) {
                var curPageNo = parseInt($("#usercurpage").html())-1;
                $("#usercurpage").html(curPageNo);
                $("#footusercurpage").html(curPageNo);
            }
            cancelUserAllSelectedId();            
            getPageData();
        });
        $("#usernextpage").unbind("click").click(function(){
            if(parseInt($("#usercurpage").html()) < parseInt($("#usertotalpages").html())) {
                var curPageNo = parseInt($("#usercurpage").html())+1;
                $("#usercurpage").html(curPageNo);
                $("#footusercurpage").html(curPageNo);
            }
            cancelUserAllSelectedId();
            getPageData();
        });
        $("#userjumppage").unbind("click").click(function(){
            if($("#userenterpage").val() < 1 || $("#userenterpage").val() > parseInt($("#usertotalpages").html())) {
                alert("对不起,您输入的页号非法!");
            } else {
                $("#usercurpage").html($("#userenterpage").val());
                $("#footusercurpage").html($("#userenterpage").val());
                $("#footuserenterpage").val($("#userenterpage").val());
                cancelUserAllSelectedId();
                getPageData();
            }
        });
        $("#footuserprepage").unbind("click").click(function(){
            $("#userprepage").click();
        });
        $("#footusernextpage").unbind("click").click(function(){
            $("#usernextpage").click();
        });
        $("#footuserjumppage").unbind("click").click(function(){
            $("#userenterpage").val($("#footuserenterpage").val());
            $("#userjumppage").click();
        });
        $("#lockFlag_button2").unbind("click").click(function(){
            $("#lockFlag_button").click();
        });
        $("#forwardedituser").unbind("click").click(function(){
            if($("#userdatalist tr").find(":checked").length < 1) {
                alert("请选择要编辑的用户!");
                return;
            }
            if($("#userdatalist tr").find(":checked").length > 1) {
                alert("请选择一个用户进行编辑!");
                return;
            }
            editListUser($("#userdatalist tr").find(":checked").val());
        });
        $("#deleteuser").unbind("click").click(function(){
            if($("#userdatalist tr").find(":checked").length < 1) {
                alert("请选择要删除的用户!");
                return;
            }
            if(confirm("确定要删除选中的"+$("#userdatalist tr").find(":checked").length+"个用户吗？")){
                var rows = "";
                $("#userdatalist tr").find(":checked").each(function(){
                    rows+=$(this).val()+","
                })
                $.ajax({ url: "./userAction/deleteUser.action",
                    data : "userId="+ rows,
                    dataType: "json",
                    type: "post",
                    success: function(data){
                        getPageData();
                    },
                    error:function(data){
                    }
                });
                return;
            }            
        });
        $("#footforwardedituser").unbind("click").click(function(){
            $("#forwardedituser").click();
        });
        $("#footdeleteuser").unbind("click").click(function(){
            $("#deleteuser").click();
        });
        $("#queryUser").unbind("click").click(function(){
            $("#usercurpage").html("1");
            $("#userenterpage").val("1");
            $("#footusercurpage").html("1");
            $("#footuserenterpage").val("1");
            cancelUserAllSelectedId(); 
            getPageData();
        });
        /*锁定标识修改*/
        $("#lockFlag_button").unbind("click").click(function(){
            if($("#userdatalist tr").find(":checked").length < 1) {
                alert("请选择要解锁的用户!");
                return;
            }
            if(confirm("确定要解锁选中的"+$("#userdatalist tr").find(":checked").length+"个用户吗？")){
                var rows = "";
                $("#userdatalist tr").find(":checked").each(function(){
                    rows+=$(this).val()+","
                })
                $.ajax({ url: "./userAction/updateLockFlag.action",
                    data : "userId="+ rows,
                    dataType: "json",
                    type: "post",
                    success: function(data){
                    	alert(data.message);
                        getPageData();
                    },
                    error:function(data){
                    }
                });
            }            
        }); 
    });
    function getPageData(){
        $.ajax({
            url: "./userAction/query.action",
            type:"GET",
            data:{"page":$("#usercurpage").html(),"rows":20,"portalUser.account":$("#searchUserAccount").val(),"portalUser.mobile":$("#searchUserMobile").val(),"portalUser.email":$("#searchUserEmail").val(),"portalUser.activeFlag":$("#searchUserActiveFlag").val(),"roleId":$("#searchUserRole").val()},
            dataType: 'json',
            success:function(data){
                refreshList(data);
                $("#userdatalist tr").each(function(){
                    $(this).find("input").live("click",function(event){
                        event.stopPropagation();
                        if($(this).attr("checked")) {
                            setUserAllSelectedId();
                        } else {
                            cancelUserAllSelectedId();
                        }
                    });                    
                    $(this).live("click",function(){
                        if($(this).find("input").attr("checked")) {
                            $(this).find("input").attr("checked",false);
                            cancelUserAllSelectedId();                            
                        } else {
                            $(this).find("input").attr("checked",true);
                            setUserAllSelectedId();
                        }                        
                    })
                })
            },
            error:function(data){
            }
        });
    }
    function refreshList(data){        
        //设置翻页相关信息
        $("#usertotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        $("#footusertotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        //设置表格数据
        $("#userdatalist tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            addRow(data.rows[i]);
        }
    }
    function addRow(data){
        //使用jquery拷贝方式生成行数据
        $("#userrowtemplate").clone(true).appendTo("#userdatalist");
        $("#userdatalist tr:last").attr("id",data.id).show();
        <s:if test="#session.SESSION_USER_INFO.userType == 3 || #session.SESSION_USER_INFO.userType == 4">
        	if(data.userType==4){
	        	$("#userdatalist tr:last td").eq(0).html("<input type='checkbox' name='userId' value='"+data.id+"'/>");
	        	$("#userdatalist tr:last td").eq(1).html("<a href='javascript:void(0)' onclick='editListUser(\""+data.id+"\")'>"+data.account+"</a>");
	        }else{
	        	$("#userdatalist tr:last td").eq(0).html("");
	        	$("#userdatalist tr:last td").eq(1).html(data.account);
	        }
        </s:if>
    	<s:else>
        	$("#userdatalist tr:last td").eq(0).html("<input type='checkbox' name='userId' value='"+data.id+"'/>");
        	$("#userdatalist tr:last td").eq(1).html("<a href='javascript:void(0)' onclick='editListUser(\""+data.id+"\")'>"+data.account+"</a>");
        </s:else>
        $("#userdatalist tr:last td").eq(2).html(data.name);
        $("#userdatalist tr:last td").eq(3).html(data.gender==0?"女":"男");
        $("#userdatalist tr:last td").eq(4).html(data.mobile);
        var rols = "";
        for(i=0;i<data.role.length;i++){
            if(i == data.role.length-1){
                rols+=data.role[i].name;
            } else {
                rols+=data.role[i].name+",";   
            }            
        }
        $("#userdatalist tr:last td").eq(5).html(rols);
        var userstatus = "";
        switch(data.activeFlag){
            case 1: userstatus = '<img src="./themes/mas3admin/images/user/u127_normal.png" width="13" height="16" alt="使用" title="使用">'; break;
            case 0: userstatus = '<img src="./themes/mas3admin/images/user/u129_normal.png" width="13" height="16" alt="暂停/锁定" title="暂停/锁定">'; break;
            default: userstatus = '<img src="./themes/mas3admin/images/user/u127_normal.png" width="13" height="16" alt="使用" title="使用">'; break;
        }
        $("#userdatalist tr:last td").eq(6).html(userstatus);
        <s:if test="#session.SESSION_USER_INFO.userType == 3">
        	$("#userdatalist tr:last td").eq(7).html("<a href='javascript:void(0)' onclick='editSmsCheck(\""+data.id+"\",\""+data.merchantPin+"\")'>绑定/解绑</a>");
        </s:if>
        <s:else>
        	$("#userdatalist tr:last td").eq(7).html("--|--");
        </s:else>
    }
    function setUserAllSelectedId(){
        var allselected = true;
        $("#userdatalist tr").each(function(){                 
            if($(this).find("input").attr("checked")){                                    
            } else {
                allselected = false;
            }            
        });
        if(allselected) {
            $("#usersselect").attr("checked",true);
        }
    }
    function cancelUserAllSelectedId(){
        if($("#usersselect").attr("checked")){
            $("#usersselect").attr("checked",false);
        }
    }   
    function editListUser(userId){
        var originalUrl = "./userAction/forward.action?flag=addForward";
        var tempUrl = "./userAction/forward.action?flag=updateForward&portalUser.id="+userId;
        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
        try{
            tabpanel.kill(killId);
        }catch(e){
        }
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    }
     function editSmsCheck(userId,merchantPin){
     	//userSmsCheck
         $("#userSmsCheck").load("./userAction/smsCheckSettingForward.action?portalUser.id="+userId+"&portalUser.merchantPin="+merchantPin,function(){
    		
            //$("#addContactDiv" ).dialog(dialogOptions);
    	});
    }
    
</script>