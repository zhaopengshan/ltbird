<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
		
    </head>
    <body>
        <!-- 移动联系人到组的时候，存放联系人的id -->
        <input type="hidden" id="tmpIds" />
        <!-- 存放总记录数 -->
        <input type="hidden" id="tmpTotal" value="0"/>
        <!-- 存放根据条件搜索结果记录数 -->
        <input type="hidden" id="tmpSearchTotal" value="0"/>
        <!-- 存放分组id, 点击左边组名时，列表显示组下的联系人 -->
        <input type="hidden" id="SearchContactGroupId" value=""/>
        
        
        
        <!-- 分组div -->	
        <div id="groupDiv" style="display: none;" class="moveGroup_div">
            <ul id="forgroupList">
                <s:if  test="@com.leadtone.mas.bizplug.util.WebUtils@getPropertyByName('privategroupopen')=='true' && #session.SESSION_USER_INFO.userType==@com.leadtone.mas.admin.common.ApSmsConstants@USER_TYPE_ENTERPRISE_ADMIN">
                <li class="contact_div_content" onclick="moveContact('-1')"><label>未分组</label></li>
                </s:if>
               <%--  <s:iterator id="group" value="#request.groupsList">
                    <li class="contact_div_content" onclick="moveContact('${group.id}')"><label>${group.groupName}</label></li>
                </s:iterator> --%>
            </ul>
        </div>
        <!-- 删除div -->
        <div id="deleteDiv" style="display: none;" class="delete_div">
            <ul>
                <li class="contact_div_content" onclick="deleteContact('searchResult')"><label>搜索结果(<span id="delSearchCount"></span>)</label></li>
                <li class="contact_div_content" onclick="deleteContact('selected')"><label>选中的联系人(<span id="delSelectedCount"></span>)</label></li>
                <li class="contact_div_content" onclick="deleteContact('all')"><label>全部联系人(<span id="delAllCount"></span>)</label></li>
            </ul>
        </div>
        <!-- 导出div -->
        <div id="exportDiv" style="display: none;" class="exportContact_div">
            <ul>
                <li class="contact_div_content" onclick="exportContact('searchResult')"><label>搜索结果(<span id="exportSearchCount"></span>)</label></li>
                <li class="contact_div_content" onclick="exportContact('selected')"><label>选中的联系人(<span id="exportSelectedCount"></span>)</label></li>
                <li class="contact_div_content" onclick="exportContact('all')"><label>全部联系人(<span id="exportAllCount"></span>)</label></li>
            </ul>
        </div>
        <div id="addressDialogLoad"></div>
        <div id="downloadDialogLoad"></div>
        <div class="gridwrapper" style="overflow-y: auto;overflow-x: hidden;">
            <table id="contactGrid" >
                <thead>
                	<tr>
                        <td style="width:50px;"></td>
                        <td style="width:120px;"></td>
                        <td style="width:120px;"></td>
                        <td style="width:80px;"></td>
                        <!--<td style="width:150px;"></td>-->
                        <td style="width:200px;"></td>
                        <td></td>
                    </tr>   
                    <tr class="tableopts">
                    	<td colspan="6" >
                    		<table>
                    			<tr>
                    				 <td  class="btn">
                            <a href="javascript:void(0);"  id="forwardnewcontact">新增</a>
                            <a href="javascript:void(0);"  id="forwarddeletecontact">删除</a>
                            <a href="javascript:void(0);"  id="forwardexportcontact">导出</a>
                            <a href="javascript:void(0);"  id="forwardmovecontact">移动到组...</a>
                        </td>
                        <td  id="contactpaginate" style="text-align: right;width:300px;" class="newpage" >
                            <a href="javascript:void(0)" id="contactprepage">上一页</a>
                            <span id="contactcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="contacttotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="contactnextpage">下一页</a>
                            跳转到 <input type="text" id="contactenterpage" value="1" style="width:30px;" /> 页
                            
                            <a href="javascript:void(0)" id="contactjumppage">G0</a>
                        </td>
                    			</tr>
                    		</table>
                    	</td>
                    </tr>
                    <tr style="height: 30px">
                        <td colspan="6" align="left"><input type="text" style="width: 150px" value="输入姓名查询..." id="searchContactName"/>
                            <input type="text" value="输入手机号码查询..." style="width: 150px" id="searchContactMobile"/> 
                            <input type="image" id="queryContact" src="./themes/mas3admin/images/address/address_search.gif" alt="查询" title="查询"/></td>
                    </tr>
                    <tr>
                        <th class="tabletrhead"><input type="checkbox" name="contactsselect" id="contactsselect" /></th>
                        <th class="tabletrhead">手机号码</th>
                        <th class="tabletrhead">联系人姓名</th>
                        <th class="tabletrhead">性别</th>
                        <th class="tabletrhead">分组</th>
                       <!-- <th class="tabletrhead">身份证号</th>-->
                        <th class="tabletrhead">最后更新</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="contactrowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                     <!--   <td></td>-->
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="contactdatalist">

                </tbody>
                <tfoot>
                    <tr class="tableopts">
                        <td colspan="6">
                        	<table>
                        		<tr>
                        			<td class="btn">
			                            <a href="javascript:void(0);"  id="footforwardnewcontact">新增</a>
			                            <a href="javascript:void(0);"  id="footdeletecontact">删除</a>
			                            <a href="javascript:void(0);"  id="footexportcontact">导出</a>
			                            <a href="javascript:void(0);"  id="footmovecontact">移动到组...</a>
			                        </td>
			                        <td id="footcontactpaginate" style="text-align: right;width:300px;" class="newpage">
			                            <a href="javascript:void(0)" id="footcontactprepage">上一页</a>
			                            <span id="footcontactcurpage" style="margin: 0;padding: 0;" >1</span>
			                            <span style="margin: 0 -5px 0 -5px;">/</span>
			                            <span id="footcontacttotalpages" style="margin: 0;padding: 0;"></span>
			                            <a href="javascript:void(0)" id="footcontactnextpage">下一页</a>
			                            跳转到  <input type="text" id="footcontactenterpage" value="1" style="width:30px;" /> 页
			                            
			                            <a href="javascript:void(0)" id="footcontactjumppage">GO</a>
			                        </td>
                        		</tr>
                        	</table>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <ul id="countGroupContacts" class="rightbox">
            <%-- <s:iterator id="group" value="#request.countGroupContacts">
                <li class="group_icon" onclick="queryByGroup('${group.bookGroupId}')">${group.groupName}(${group.counts})</li>
            </s:iterator> --%>
        </ul>

        <script type="text/javascript">	
            var contactGrid;
            var name_tip = "输入姓名查询...";
            var mobile_tip = "输入手机号码查询...";
            
            $(function() {
                $.ajax({
                    url: "./address/queryForward.action",
                    type:"GET",
                    dataType: 'json',
                    success:function(data){
            			var cgc=data.countGroupContacts;
            			$("#countGroupContacts").children().remove();
            			for(var i = 0;i < cgc.length;i++){
            				$("#countGroupContacts").append("<li class='group_icon' onclick='queryByGroup(\""+cgc[i].bookGroupId+"\")'>"+cgc[i].groupName+"("+cgc[i].counts+")</li>");
            			}
            			var gl=data.groupsList;
            			$("#forgroupList").children().remove();
            			for(var i = 0;i < gl.length;i++){
            				$("#forgroupList").append("<li class='contact_div_content' onclick='moveContact(\""+gl[i].id+"\")'><label>"+gl[i].groupName+"</label></li>");
            			}
                    }
                });
                
                getAddressPageData();
                $("#contactsselect").unbind("click").click(function(){
                    if($("#contactdatalist tr").length > 0) {
                        if($("#contactsselect").attr("checked")) {
                            $("#contactdatalist tr").each(function(){
                                $(this).find("input").attr("checked",true);
                            });
                        } else {
                            $("#contactdatalist tr").each(function(){
                                $(this).find("input").attr("checked",false);
                            });
                        }                
                    }
                });
                $("#contactprepage").unbind("click").click(function(){
                    if(parseInt($("#contactcurpage").html()) > 1) {
                        var curPageNo = parseInt($("#contactcurpage").html())-1;
                        $("#contactcurpage").html(curPageNo);
                        $("#footcontactcurpage").html(curPageNo);
                    }
                    cancelContactAllSelectedId();            
                    getAddressPageData();
                });
                $("#contactnextpage").unbind("click").click(function(){
                    if($("#contactcurpage").html() < parseInt($("#contacttotalpages").html())) {
                        var curPageNo = parseInt($("#contactcurpage").html())+1;
                        $("#contactcurpage").html(curPageNo);
                        $("#footcontactcurpage").html(curPageNo);
                    }
                    cancelContactAllSelectedId();
                    getAddressPageData();
                });
                $("#contactjumppage").unbind("click").click(function(){
                    if($("#contactenterpage").val() < 1 || $("#contactenterpage").val() > parseInt($("#contacttotalpages").html())) {
                        alert("对不起,您输入的页号非法!");
                    } else {
                        $("#contactcurpage").html($("#contactenterpage").val());
                        $("#footcontactcurpage").html($("#contactenterpage").val());
                        $("#footcontactenterpage").val($("#contactenterpage").val());
                        cancelContactAllSelectedId();
                        getAddressPageData();
                    }
                });
                $("#footcontactprepage").unbind("click").click(function(){
                    $("#contactprepage").click();
                });
                $("#footcontactnextpage").unbind("click").click(function(){
                    $("#contactnextpage").click();
                });
                $("#footcontactjumppage").unbind("click").click(function(){
                    $("#contactenterpage").val($("#footcontactenterpage").val());
                    $("#contactjumppage").click();
                });
                $("#forwardnewcontact").unbind("click").click(function(){
                    $("#addressDialogLoad").load("./address/queryGroup.action",function(){
			    		$("#group").find("option").each(function() {
	                        if($(this).val() == '-1'){
	                            $(this).attr("selected", true);
	                        }
	                    });
	                    $("#merchantBlackFlag").removeAttr("checked");
	                    $("#addForm").attr("action", "./address/addContact.action");
	                    $("#addresslist_mobile").focus();
	                    //$("#addContactDiv" ).dialog(dialogOptions);
			    	});
                });
                $("#forwarddeletecontact").unbind("click").click(function(){
                    // 删除操作
                    selectRows();
                    $("#delSearchCount").html($("#tmpSearchTotal").val());	// 待删除的搜索结果数
                    $("#deleteDiv").slideToggle("slow");	// 显示删除层
                });
                $("#forwarddeletecontact").mouseleave(function(e){
                    if(e.pageY < ($("#deleteDiv").offset().top-5) || e.pageX > ($(this).offset().left+$(this).width()) || e.pageX < $("#deleteDiv").offset().left){
                        $("#deleteDiv").hide();
                    }
                });
                $("#forwardexportcontact").unbind("click").click(function(){
                    // 导出操作
                    selectRows();
                    $("#exportSearchCount").html($("#tmpSearchTotal").val());	// 待导出的搜索结果数
                    $("#exportDiv").slideToggle("slow");	// 显示导出层
                });
                $("#forwardexportcontact").mouseleave(function(e){
                    if(e.pageY < ($("#exportDiv").offset().top-5) || e.pageX > ($(this).offset().left+$(this).width()) || e.pageX < $("#exportDiv").offset().left){
                        $("#exportDiv").hide();
                    }
                });
                $("#forwardmovecontact").unbind("click").click(function(){
                    // 移动联系人到某组操作
                    if( $("#contactdatalist tr").find(":checked").length >= 1 ){
                        // 将要移动的联系人放到临时变量里
                        var rows = "";
                        $("#contactdatalist tr").find(":checked").each(function(){
                            rows+=$(this).val()+",";
                        });
                        $("#groupDiv").slideToggle("slow");
                        $("#tmpIds").val(rows);
                    }else{
                        $("#groupDiv").hide();	
                        alert("请选择要移动的联系人");
                        return ;
                    }
                });
                $("#forwardmovecontact").mouseleave(function(e){
                    if(e.pageY < ($("#groupDiv").offset().top-5) || e.pageX > ($(this).offset().left+$(this).width()) || e.pageX < $("#groupDiv").offset().left){
                        $("#groupDiv").hide();
                    }
                });
                $("#footforwardnewcontact").unbind("click").click(function(){
                    $("#forwardnewcontact").click();
                });
                $("#footdeletecontact").unbind("click").click(function(){
                    $("#forwarddeletecontact").click();
                });
                $("#footexportcontact").unbind("click").click(function(){
                    $("#forwardexportcontact").click();
                });
                $("#footmovecontact").unbind("click").click(function(){
                    $("#forwardmovecontact").click();
                });
        
                $("#searchContactName").focus(function(){
                    if($("#searchContactName").val() == name_tip)
                        $("#searchContactName").val("");
                }).focusout(function() {
                    if($("#searchContactName").val() == ""){
                        $("#searchContactName").val(name_tip);
                    }
                });
                $("#searchContactMobile").focus(function(){
                    if($("#searchContactMobile").val() == mobile_tip)
                        $("#searchContactMobile").val("");
                }).focusout(function() {
                    if($("#searchContactMobile").val() == ""){
                        $("#searchContactMobile").val(mobile_tip);
                    }
                });
                $("#queryContact").unbind("click").click(function(){
                    // 查询条件(手机号，联系人名称)
                    var account=$("#searchContactName").val();
                    var mobile=$("#searchContactMobile").val();
                    if(account == name_tip){
                        account = "";
                    }
                    if(mobile == mobile_tip){
                        mobile = "";
                    }
                    $("#contactcurpage").html("1");
                    $("#contactenterpage").val("1");
                    $("#footcontactcurpage").html("1");
                    $("#footcontactenterpage").val("1");
                    cancelContactAllSelectedId(); 
                    getAddressPageData();
                    // 清空存放分组的条件
                    $("#SearchContactGroupId").val("");
			
                });
		
                $("div[class$='_div'] > ul > li").mouseover(function(){
                    $(this).css("background-color","#33CCFF");
                }); 
                $("div[class$='_div'] > ul > li").mouseout(function(){
                    $(this).css("background-color","#eaf1ee");
                });
                $("div[class$='_div']").mouseleave(function(){
                    $(this).hide();
                });
		
            });
	
            /**
             * 选中联系人的时候，设置联系人id，及选中的记录数，（删除，导出时用）
             *
             */
            function selectRows(){
                var rows = "";
                var selectedRows = 0 ;
                $("#contactdatalist tr").find(":checked").each(function(){
                    rows+=$(this).val()+",";
                    selectedRows++;
                });
                $("#tmpIds").val(rows);		// 将联系人id存入临时变量里
                $("#exportSelectedCount").html(selectedRows);	// 待导出的选中结果数
                $("#delSelectedCount").html(selectedRows);		// 待删除的选中结果数
            }
	
            function getAddressPageData(){
                var account=$("#searchContactName").val();
                var mobile=$("#searchContactMobile").val();
                var bookGroupId=$("#SearchContactGroupId").val();
                if(account == name_tip){
                    account = "";
                }
                if(mobile == mobile_tip){
                    mobile = "";
                }
		
                $.ajax({
                    url: "./address/query.action?contact.name="+encodeURI(encodeURI(account)),
                    type:"GET",
                    data:{"page":$("#contactcurpage").html(),"rows":20,"contact.mobile":encodeURI(encodeURI(mobile)), "contact.bookGroupId": bookGroupId},
                    dataType: 'json',
                    success:function(data){
                        refreshAddressList(data);
                        $("#contactdatalist tr").each(function(){
                            $(this).find("input").live("click",function(event){
                                event.stopPropagation();
                                if($(this).attr("checked")) {
                                    setContactAllSelectedId();
                                } else {
                                    cancelContactAllSelectedId();
                                }
                            });                    
                            $(this).live("click",function(){
                                if($(this).find("input").attr("checked")) {
                                    $(this).find("input").attr("checked",false);
                                    cancelContactAllSelectedId();                            
                                } else {
                                    $(this).find("input").attr("checked",true);
                                    setContactAllSelectedId();
                                }                        
                            })
                        })
                    },
                    error:function(data){
                    }
                });
            }
            function refreshAddressList(data){  
                $("#delAllCount").html(data.dbTotal);	// 等删除的全部结果数
                $("#exportAllCount").html(data.dbTotal);	// 等删除的全部结果数
                $("#tmpSearchTotal").val(data.totalrecords);
                //设置翻页相关信息
                $("#contactcurpage").html(data.currpage);
                $("#footcontactcurpage").html(data.currpage);
                $("#contacttotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
                $("#footcontacttotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
                //设置表格数据
                $("#contactdatalist tr").remove();
                for(var i=0;data&&data.rows&&i<data.rows.length;i++){
                    addAddressRow(data.rows[i]);
                }
            }
            function addAddressRow(data){
                //使用jquery拷贝方式生成行数据
                $("#contactrowtemplate").clone(true).appendTo("#contactdatalist");
                $("#contactdatalist tr:last").attr("id",data.id).attr("gender", data.gender).show();
                $("#contactdatalist tr:last td").eq(0).html("<input type='checkbox' name='contactId' value='"+data.id+"'/>");
                $("#contactdatalist tr:last td").eq(1).html('<a href="javascript:void(0)" onclick="editContact(\''
                    +data.id+'\',\''+data.mobile+'\',\''+data.name+'\',\''+data.gender+'\',\''
                    +data.identificationNumber+'\',\''+data.birthday+'\',\''+data.bookGroupId+'\',\''+data.vpmn+'\',\''
                    +data.telephone+'\',\''+data.company+'\',\''+data.address+'\',\''+data.msn+'\',\''
                    +data.qqNumber+'\',\''+data.email+'\',\''+data.microBlog+'\',\''+data.description+'\',\''
                    +data.merchantBlackFlag+'\',\''+data.lastModifyTime+'\',\''+data.createBy+'\')">'
                    +data.mobile+'<img src="./themes/mas3admin/images/contact_mobile.png" width="13" height="16" border="0" /></a>');
                $("#contactdatalist tr:last td").eq(2).html(data.name);
                $("#contactdatalist tr:last td").eq(3).html(data.gender==0?"女":"男");
                var groupName = "";
                if(data.bookGroupId==-1)
                    groupName = "未分组";
                else
                    groupName = data.groupName;
                $("#contactdatalist tr:last td").eq(4).html(groupName);
                //$("#contactdatalist tr:last td").eq(5).html(data.identificationNumber);
                $("#contactdatalist tr:last td").eq(5).html(data.lastModifyTime);
            }
            function setContactAllSelectedId(){
                var allselected = true;
                $("#contactdatalist tr").each(function(){                 
                    if($(this).find("input").attr("checked")){
                    } else {
                        allselected = false;
                    }
                });
                if(allselected) {
                    $("#contactsselect").attr("checked",true);
                }
            }
            function cancelContactAllSelectedId(){
                if($("#contactsselect").attr("checked")){
                    $("#contactsselect").attr("checked",false);
                }
            }   
            /**
             * 点击分组名称进行查询联系人
             *
             */
            function queryByGroup(groupId){
                $.ajax({
                    url: "./address/query.action",
                    type:"GET",
                    data:{"page":1,"rows":20,"contact.bookGroupId":encodeURI(encodeURI(groupId))},
                    dataType: 'json',
                    success:function(data){
                        refreshAddressList(data);
                        $("#contactdatalist tr").each(function(){
                            $(this).find("input").live("click",function(event){
                                event.stopPropagation();
                                if($(this).attr("checked")) {
                                    setContactAllSelectedId();
                                } else {
                                    cancelContactAllSelectedId();
                                }
                            });                    
                            $(this).live("click",function(){
                                if($(this).find("input").attr("checked")) {
                                    $(this).find("input").attr("checked",false);
                                    cancelContactAllSelectedId();                            
                                } else {
                                    $(this).find("input").attr("checked",true);
                                    setContactAllSelectedId();
                                }                        
                            })
                        })
                    },
                    error:function(data){
                    }
                });
                cancelContactAllSelectedId(); 
                // 清空name和手机的条件框
                $("#searchContactName").val(name_tip);
                $("#searchContactMobile").val(mobile_tip);
                $("#SearchContactGroupId").val(groupId);
            }
	
            /**
             *  验证搜索框条件
             * 
             */
            function validateQuery(){
                var account=$("#searchContactName").val();
                var mobile=$("#searchContactMobile").val();//alert(account+"; "+ mobile);
                /*if((account == name_tip) && (mobile == mobile_tip)){
                                alert("请至少输入一种查询条件");
                                return false;
                        }else*/ if(account == name_tip){
                    account = "";
                }
                if(mobile == mobile_tip){
                    mobile = "";
                }
                return true;
            }
            
            /**
             *
             * 删除联系人
             *
             */
            function deleteContact(flag){
                var url =  "./address/deleteContact.action?flag=" + flag;
                $("#deleteDiv").hide();
                var rows = $("#tmpIds").val();		
                var delCount = 0; 
                if("selected" == flag){
                    delCount = $("#delSelectedCount").html();
                    if("0" == delCount){
                        alert("请先选择删除的联系人");
                        return false;
                    }else{
                        url += "&contactIds=" + encodeURI(encodeURI(rows));				
                    }
                }
                if("searchResult" == flag ){
                    delCount = $("#delSearchCount").html();			
                    if("0" == delCount){
                        alert("请先搜索删除的联系人");
                        return false;
                    }else{
                        var account=$("#searchContactName").val();
                        var mobile=$("#searchContactMobile").val();//alert(account+"; "+ mobile);
                        var groupId=$("#SearchContactGroupId").val();
                        if(account == name_tip){
                            account = "";
                        }
                        if(mobile == mobile_tip){
                            mobile = "";
                        }
                        url += "&contact.name="+encodeURI(encodeURI(account))
                            +  "&contact.mobile="+encodeURI(encodeURI(mobile));
                        if(groupId != "")
                            url +=  "&contact.bookGroupId="+encodeURI(encodeURI(groupId));
                    }
                }
		
                if("all" == flag){
                    delCount = $("#delAllCount").html();
                }
                var showMessage = "确定删除"+delCount+"项联系人信息？";
                if(confirm( showMessage)){
                    $.ajax({
                        url : url,
                        type : 'post',
                        dataType: "json",
                        success : function(data) {
                        	if(data.flag == "success"){
                            	alert("联系人删除成功");
                                $("#tmpIds").val("");
                                var groupContacts = data.countGroupContacts;
                                $("#countGroupContacts").html("");
                                for(var j in groupContacts){
                                    $("#countGroupContacts").append("<li  class='group_icon' onclick=\"queryByGroup('"
                                        +groupContacts[j].bookGroupId+"')\">"
                                        +groupContacts[j].groupName+"("+groupContacts[j].counts+")</li>");
                                }
                                getAddressPageData();
                            }else{
                                alert("删除联系人失败，请稍后重试");
                            }
                        },
                        error : function() {
                            alert("删除联系人出错，请稍后再试");
                        }
                    }); 
                }else{
                    return false;
                }
            }
	
            /**
             * 修改联系人
             *
             */
            function editContact(id,mobile,name,gender,identificationNumber,birthday,bookGroupId,
            vpmn,telephone,company,address,msn,qqNumber,email,microBlog,description,
            merchantBlackFlag,lastModifyTime,createBy){
            	if(identificationNumber == "null"){
            		identificationNumber="";
            	}
            	if(birthday == "null"){
            		birthday="";
            	}
            	if(vpmn == "null"){
            		vpmn="";
            	}
            	if(telephone == "null"){
            		telephone="";
            	}
            	if(company == "null"){
            		company="";
            	}
            	if(address == "null"){
            		address="";
            	}
            	if(msn == "null"){
            		msn="";
            	}
            	if(qqNumber == "null"){
            		qqNumber="";
            	}
            	if(email == "null"){
            		email="";
            	}
            	if(microBlog == "null"){
            		microBlog="";
            	}
            	if(description == "null"){
            		description="";
            	}
            	
                // 打开编辑联系人层
                //$( "#addContactDiv" ).dialog(dialogOptions);
	        	$("#addressDialogLoad").load("./address/queryGroup.action",function(){
			    	$("#addForm").attr("action", "./address/updateContact.action");
	                $("#cId").val(id);	$("#addresslist_mobile").val(mobile);	$("#addresslist_name").val(name);
	                $("#addressCreateBy").val(createBy);
	                $("input[name=contact.gender][value="+gender+"]").attr("checked",true);
	                $("#identificationNumber").val(identificationNumber);
	                //alert(birthday);
	                if(birthday != "null"){
	                    $("#birthday").val(birthday);
	                }
	                $("#group").find("option").each(function() {
	                    if($(this).val() == bookGroupId){
	                        $(this).attr("selected", true);
	                    }
	                });
	                $("#vpmn").val(vpmn);	$("#telephone").val(telephone);	$("#company").val(company);
	                $("#address").val(address);	$("#msn").val(msn);	$("#qqNumber").val(qqNumber);
	                $("#addresslist_email").val(email);	$("#microBlog").val(microBlog);	$("#addresslist_description").val(description);
	                //alert(merchantBlackFlag);
	                if(merchantBlackFlag == 0 ){
	                    $("#merchantBlackFlag").attr("checked", true);
	                }else{
	                    $("#merchantBlackFlag").removeAttr("checked");
	                }
		    	});
            }
            /**
             * 将选中的联系人移动到某个组 
             * @param: 
                  groupId: 要移动到的组ID
             **/
            function moveContact(groupId){
                $("#groupDiv").hide();	  
                var rows = $("#tmpIds").val();
                var url =  "./address/moveContact.action";
                var showMessage = "确定移动"+(rows.split(",").length-1)+"项联系人信息？";
                if(confirm( showMessage)){
                    $.ajax({
                        url : url,
                        type : 'post',
                        dataType: "json",
                        data: {
                            contactIds: rows.toString(),
                            groupId: groupId
                        },
                        success : function(data) {
                            if(data.flag == "success"){
                            	alert(data.message);
                                $("#tmpIds").val("");   
                                var groupContacts = data.countGroupContacts;
                                $("#countGroupContacts").html("");
                                for(var j in groupContacts){
                                    $("#countGroupContacts").append("<li class='group_icon' onclick=\"queryByGroup('"
                                        +groupContacts[j].bookGroupId+"')\">"
                                        +groupContacts[j].groupName+"("+groupContacts[j].counts+")</li>");
                                }
                                getAddressPageData();
                            }else{
                                alert("移动联系人失败，请稍后重试");
                            }
                        },
                        error : function() {
                            alert("移动联系人失败，请稍后再试");
                        }
                    }); 
                }else{
                    return false;
                }
            }
            /**
             * 导出
             *
             */ 
            function exportContact(flag){
		
                $("#exportDiv").hide();
                var rows = $("#tmpIds").val();		
                var exportCount = 0; 
                if("searchResult" == flag  && 
                    $("#exportAllCount").html() == $("#exportSearchCount").html()){
                    // 当不输入条件时，默认的导出全部的联系人			 
                    flag = "all";
                }
                var url =  "./address/exportContact.action?flag=" + flag;
                if("selected" == flag){
                    exportCount = $("#exportSelectedCount").html();
                    if("0" == exportCount){
                        alert("请先选择导出的联系人");
                        return false;
                    }else{
                        url += "&contactIds=" + encodeURI(encodeURI(rows));				
                    }
                }
                if("searchResult" == flag ){
                    exportCount = $("#exportSearchCount").html();			
                    if("0" == exportCount){
                        alert("请先搜索导出的联系人");
                        return false;
                    }else{
                        var account=$("#searchContactName").val();
                        var mobile=$("#searchContactMobile").val();//alert(account+"; "+ mobile);
                        var groupId=$("#SearchContactGroupId").val();
                        if(account == name_tip){
                            account = "";
                        }
                        if(mobile == mobile_tip){
                            mobile = "";
                        }
                        url += "&contact.name="+encodeURI(encodeURI(account))
                            +  "&contact.mobile="+encodeURI(encodeURI(mobile));
                        if(groupId != "")
                            url +=  "&contact.bookGroupId="+encodeURI(encodeURI(groupId));
                    }
                }
		
                if("all" == flag){
                    exportCount = $("#exportAllCount").html();
                }
                var showMessage = "确定导出"+exportCount+"项联系人信息？";
                if(confirm( showMessage)){
                    $.ajax({
                        url : url,
                        type : 'post',
                        dataType: "json",
                        success : function(data) {
                            if(data.flag == "success"){
                                //$("#downloadDiv").dialog(exportDialogOptions);
                                $("#downloadDialogLoad").load("./ap/address/addressExportDialog.jsp",function(){
						    		$("#downloadExcel").attr("href", 
	                                "javascript:download('<%=basePath%>fileDownload?fileName=./downloads/contacts/" +data.fileName+"')");
	                                $("#exportContactNum").html(exportCount);
	                                $("#tmpIds").val("");
						    	});
                                getAddressPageData();
                            }else{
                                alert("导出联系人失败，请稍后重试");
                            }
                        },
                        error : function() {
                            alert("导出联系人出错，请稍后再试");
                        }
                    }); 
                }else{
                    return false;
                }
            }
            /**
             *	
             * 导出的下载excel文件
             *
             */
            function download(url){
                $("#downloadDiv").dialog("close");
                window.open(url);
            }
        </script>
