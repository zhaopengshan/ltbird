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
        <div class="gridfullscreen" style="overflow-y: auto;overflow-x: hidden;">
            <table id="groupGrid" >
                <thead>
                	<tr >
                        <td style="width:50px;"></td>
                        <td style="width:350px;"></td>
                        <td></td>
                    </tr> 
                    <tr class="tableopts">
                    	<!-- 按钮开始 -->
                        <td colspan="2" class="btn">
                            <a href="javascript:void(0);"  id="forwardcreategroup">新增</a>
                            <a href="javascript:void(0);"  id="deletegroup">删除</a>
                        </td>
                        <!-- 按钮结束 -->
                      	<!-- 上分页开始 -->
                        <td colspan="1" id="grouppaginate" style="text-align: right" class="newpage">
                            <a href="javascript:void(0)" id="groupprepage">上一页</a>
                            <span id="groupcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="grouptotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="groupnextpage">下一页</a>
                            跳转到<input type="text" id="groupenterpage" value="1" style="width:30px;" />页                            
                            <a href="javascript:void(0)" id="groupjumppage">GO</a>
                        </td>
                       <!--  上分页结束 -->
                    </tr>
                    <tr>
                    	<!-- 表头开始 -->
                        <th class="tabletrhead"><input type="checkbox" name="groupsselect" id="groupsselect" /></th>
                        <th class="tabletrhead">分组名称</th>
                        <th class="tabletrhead">分组描述</th>
                    	<!-- 表头结束 -->
                    </tr>
                    <!-- 表内容开始 -->
                    <tr class="tabletrboby" style="display:none" id="grouprowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
              		<!-- 表内容结束 -->
                </thead>
                <tbody id="groupdatalist">

                </tbody>
                <tfoot>
                	<!-- 下按钮和分页 -->
                    <tr class="tableopts">
                        <td colspan="2" class="btn">
                            <a href="javascript:void(0);"  id="footforwardcreategroup">新增</a>
                            <a href="javascript:void(0);"  id="footdeletegroup">删除</a>
                        </td>
                        <td colspan="1" id="footgrouppaginate" style="text-align: right" class="newpage">
                            <a href="javascript:void(0)" id="footgroupprepage">上一页</a>
                            <span id="footgroupcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="footgrouptotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="footgroupnextpage">下一页</a>
                            跳转到 <input type="text" id="footgroupenterpage" value="1" style="width:30px;" /> 页
                            <a href="javascript:void(0)" id="footgroupjumppage">GO</a>
                        </td>
                    </tr>
                    <!-- 下按钮和分页 -->
                </tfoot>
            </table>
        </div>
        
    <!-- 用于添加的div -->
    <div id="groupAddDialogLoad"></div>
	<!-- 用于更新的div -->
	<div id="groupEditDialogLoad"></div>
<script type="text/javascript">	
var insertGroupUrl="./address/insertGroup.action";
var updateGroupUrl="./address/updateGroup.action";
var deleteGroupUrl="./address/deleteGroup.action";
var showGroupUrl="./address/showGroupList.action";
    $(document).ready(function(){
        getPageDataShowGroup();
        /* 全选 */
       $("#groupsselect").unbind("click").click(function(){
            if($("#groupdatalist tr").length > 0) {
                if($("#groupsselect").attr("checked")) {
                    $("#groupdatalist tr").each(function(){
                        $(this).find("input").attr("checked",true);
                    });
                } else {
                    $("#groupdatalist tr").each(function(){
                        $(this).find("input").attr("checked",false);
                    });
                }                
            }
        });
       /*  分页按钮开始 */
       $("#groupprepage").unbind("click").click(function(){
            if(parseInt($("#groupcurpage").html()) > 1) {
                var curPageNo = parseInt($("#groupcurpage").html())-1;
                $("#groupcurpage").html(curPageNo);
                $("#footgroupcurpage").html(curPageNo);
            }
            cancelGroupAllSelectedId();            
            getPageDataShowGroup();
        });
       $("#groupnextpage").unbind("click").click(function(){
            if(parseInt($("#groupcurpage").html()) < parseInt($("#grouptotalpages").html())) {
                var curPageNo = parseInt($("#groupcurpage").html())+1;
                $("#groupcurpage").html(curPageNo);
                $("#footgroupcurpage").html(curPageNo);
            }
            cancelGroupAllSelectedId();
            getPageDataShowGroup();
        });
        $("#groupjumppage").unbind("click").click(function(){
            if(parseInt($("#groupenterpage").val()) < 1 || parseInt($("#groupenterpage").val()) > parseInt($("#grouptotalpages").html())) {
                alert("对不起,您输入的页号非法!");
            } else {
                $("#groupcurpage").html($("#groupenterpage").val());
                $("#footgroupcurpage").html($("#groupenterpage").val());
                $("#footgroupenterpage").val($("#groupenterpage").val());
                cancelGroupAllSelectedId();
                getPageDataShowGroup();
            }
        });
        $("#footgroupprepage").unbind("click").click(function(){
            $("#groupprepage").click();
        });
        $("#footgroupnextpage").unbind("click").click(function(){
            $("#groupnextpage").click();
        });
        $("#footgroupjumppage").unbind("click").click(function(){
            $("#groupenterpage").val($("#footgroupenterpage").val());
            $("#groupjumppage").click();
        });
        /*  分页按钮结束 */
        $("#forwardcreategroup").unbind("click").click(function(){
        	createGroup();
        });
        $("#deletegroup").unbind("click").click(function(){
        	deleteGroup();
        });
        $("#footforwardcreategroup").unbind("click").click(function(){
            $("#forwardcreategroup").click();
        });
        $("#footdeletegroup").unbind("click").click(function(){
            $("#deletegroup").click();
        });
    }); 
    /* 页面初始化开始 */
    function getPageDataShowGroup(){
        $.ajax({
            url: showGroupUrl,
            type:"POST",
            data:{"page":$("#groupcurpage").html(),"rows":20},
            dataType: 'json',
            success:function(data){
                refreshListShowGroup(data);//先加载内容
                $("#groupdatalist tr").each(function(){
                    $(this).find("input").live("click",function(event){
                        event.stopPropagation();
                        if($(this).attr("checked")) {
                            setGroupAllSelectedId();
                        } else {
                            cancelGroupAllSelectedId();
                        }
                    });                    
                    $(this).live("click",function(){
                        if($(this).find("input").attr("checked")) {
                            $(this).find("input").attr("checked",false);
                            cancelGroupAllSelectedId();                            
                        } else {
                            $(this).find("input").attr("checked",true);
                            setGroupAllSelectedId();
                        }                        
                    });
                   
                });
            },
            error:function(data){
            }
        });
    }
    function refreshListShowGroup(data){        
        //设置翻页相关信息
        $("#grouptotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        $("#footgrouptotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
        //设置表格数据
        $("#groupdatalist tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            addRowShowGroup(data.rows[i]);
        }
    }
    /* 具体添加方法 */
    function addRowShowGroup(data){
        //使用jquery拷贝方式生成行数据
        $("#grouprowtemplate").clone(true).appendTo("#groupdatalist");
        $("#groupdatalist tr:last").attr("id",data.id).show();
        $("#groupdatalist tr:last td").eq(0).html("<input type='checkbox' name='groupId' value='"+data.id+"'/>");
        $("#groupdatalist tr:last td").eq(1).html("<a href='javascript:void(0)' onclick='editGroup(\""+data.id+"\",\""+data.groupName+"\",\""+data.createBy+"\",\""+data.description+"\");'>"+data.groupName+"</a>");
        $("#groupdatalist tr:last td").eq(2).html(data.description);
    }
   /*  全选 */
    function setGroupAllSelectedId(){
        var allselected = true;
        $("#groupdatalist tr").each(function(){                 
            if($(this).find("input").attr("checked")){                                    
            } else {
                allselected = false;
            }            
        });
        if(allselected) {
            $("#groupsselect").attr("checked",true);
        }
    }
 /*   取消全选 */
    function cancelGroupAllSelectedId(){
        if($("#groupsselect").attr("checked")){
            $("#groupsselect").attr("checked",false);
        }
    }  
/*  页面初始化结束 */

        
    function editGroup(id,name,createBy,description){
    	$("#groupEditDialogLoad").load("./ap/address/groupEditDialog.jsp",function(){
    		$("#groupEditId").attr('value',-1);
			$("#groupEditName").attr('value',"");
			$("#groupEditTextarea").html("");
			$("#groupEditId").attr('value',id);
			$("#groupEditName").attr('value',name);
			$("#groupEditCreateBy").attr('value',createBy);
			$("#originalName").attr('value',name);
			if(description !=null){
				$("#groupEditTextarea").html(description);
			} 
    	});
        //$("#editGroupDiv").dialog(dialogOptionsEditGroup);
    }
    function createGroup(){
    	//$("#createGroupDiv").dialog(dialogOptionsCreateGroup);
    	$("#groupAddDialogLoad").load("./ap/address/groupAddDialog.jsp");
    }
    function deleteGroup(){
    	if($("#groupdatalist tr").find(":checked").length < 1) {
            alert("请选择要删除的通讯录组!");
            return;
        }
        if(confirm("您确定要删除选中的"+$("#groupdatalist tr").find(":checked").length+"条通讯录组吗？")){
            var rows = "";
            $("#groupdatalist tr").find(":checked").each(function(){
                rows+=$(this).val()+",";
            })
            $.ajax({ url: deleteGroupUrl,
                data : "groupIds="+ rows,
                dataType: "json",
                type: "post",
                success: function(data){
                    getPageDataShowGroup();
                    var entityMap=data;
					if(entityMap.flag){
						alert(entityMap.resultMsg);
						//$("#msgRightSpan").html(entityMap.resultMsg);
						//$("#msgRight").show();
					}else{
						alert(entityMap.resultMsg);
						//$("#msgWrongSpan").html(entityMap.resultMsg);
						//$("#msgWrong").show();
					}
                },
                error:function(data){
                }
            });
        }            
    }
</script>
   </body>
</html>