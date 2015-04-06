<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form action="./roleAction/addrole.action" method="post" id="roleAdd_addForm">
    <input type="hidden" name="portalRole.id" id="roleAdd_roleId" value="${roleVO.id }" />
    <ul class="gridwrapper"  style="overflow-y: auto;overflow-x: hidden;">
        <li>
            <label><span>*</span>角色名称：</label> 
            <input type="hidden" id="roleAdd_tmp_name" value="${roleVO.name }" />
            <input size="50" maxlength="20" type="text" name="portalRole.name" id="roleAdd_name" value="${roleVO.name }" />
            <span id="nameResult"></span>
        </li>
        <li>
            <label><span>*</span>角色描述： </label> 
            <input type="text" name="portalRole.description" id="roleAdd_desc" size="50" maxlength="20" value="${roleVO.description }" /> 
            <span id="descriptionResult"></span>
        </li>
        <li>
            <ul  class="rolelabel">
                <li>
                    <label><span>*</span>使用授权：</label>
                </li>
            </ul>
            <ul class="unselectrolemargin">
                <li>
                    <span><b>功能名称</b></span>    <span><b>使用授权</b></span>
                </li>

                <s:iterator id="resource" value="#request.resourceList">
                    <li>
                        ${resource.name}
                        <input type="checkbox" value="${resource.id }"
                               <s:iterator id="res" value="#request.roleVO.resources">
                                   <s:if test="%{#resource.id == #res.id }">checked</s:if>
                               </s:iterator>
                               name="multiResources"/>
                    </li>
                </s:iterator>
                <li>
                    <span id="resourcesResult"></span>
                </li>
            </ul>
        </li>
        <li>
            <ul  class="rolelabel">
                <li>
                    <label>分配该角色的用户： </label>
                </li>
            </ul>
            <ul class="unselectrolemargin">
                <li>
                    <span>待选用户:</span>
                </li>
                <li>
                    <select id="roleAdd_roleUserLeft" name="un_selected_multiUsers" style="width: 180px" size="6" multiple="multiple">
                        <s:iterator id="user" value="#request.userList">
                            <option value="${user.id }">${user.account}&lt;${user.name}&gt;</option>
                        </s:iterator>
                    </select>
                </li>
            </ul>
            <ul   class="roleselectmargin">
                <li>	
                    <input type="button" id="roleAdd_toright" value="&gt;&gt;" />
                </li>
                <li>
                    <input type="button" id="roleAdd_toleft" value="&lt;&lt;" />
                </li>
            </ul>
            <ul>
                <li>
                    <span>已选用户:</span> 
                </li>
                <li>
                    <select id="roleAdd_roleUserRight" name="multiUsers" style="width: 180px" size="6" multiple="multiple" >
                        <s:iterator id="user" value="#request.roleVO.users">
                            <option value="${user.id }">${user.account}&lt;${user.name}&gt;</option>
                        </s:iterator>
                    </select>
                </li>                
            </ul>
        </li>
        <li class="extrainputlisttip">
            <span id="roleResult"></span>
        </li>
        <li class="btn">
            <a href="javascript:void(0);" id="roleAdd_sub">提交</a>
            <a href="javascript:void(0);" id="roleAdd_clear">取消</a>
        </li> 
    </ul>
</form>
<script type="text/javascript">
    $(function() {
		
        // 初次加载页面时，清除结果信息
        clearHtml();
		
        //$("#oldRoles").val($("#roleAdd_roleUser").html());
        $("#roleAdd_name").focusout(function() {
            checkRoleName("#roleAdd_name", "#nameResult", false);
        });

        $("#roleAdd_desc").focusout(function() {
            checkRoleDesc("#roleAdd_desc", "#descriptionResult", false);
        });

        var leftSel = $("#roleAdd_roleUserLeft");
        var rightSel = $("#roleAdd_roleUserRight");
        $("#roleAdd_toright").unbind("click").bind("click", function() {
            leftSel.find("option:selected").each(function() {
                $(this).remove().appendTo(rightSel);
            });
        });
        $("#roleAdd_toleft").unbind("click").bind("click", function() {
            rightSel.find("option:selected").each(function() {
                $(this).remove().appendTo(leftSel);
            });
        });
        leftSel.unbind("dbclick").dblclick(function() {
            $(this).find("option:selected").each(function() {
                $(this).remove().appendTo(rightSel);
            });
        });
        rightSel.unbind("dbclick").dblclick(function() {
            $(this).find("option:selected").each(function() {
                $(this).remove().appendTo(leftSel);
            });
        });
        $("#roleAdd_clear").unbind("click").click(function() {
            if (confirm("是否取消本次角色编辑？")) {
                // 如果取消，直接跳到角色列表页面
				
                // 新增页面，
                $("#roleAdd_name").val("");
                $("#roleAdd_desc").val("");
                $("input:checked").each(function() {
                    // 清除已选的资源
                    $(this).attr("checked", false);
                });
                $("#roleAdd_roleUserRight").find("option").each(function() {
                    // 清除已选的用户
                    $(this).remove().appendTo(leftSel);
                });
                clearHtml();
                if ($("#roleAdd_roleId").val() != "") {
                    // 修改页面
                    //window.location.href = "./ap/role/rolelist.jsp";
                    forwardRoleList();
                }
            }
        });

        $("#roleAdd_sub").unbind("click").click(
        function() {
            // 将右边已选框里的列表选定，这样，就能传到后台了。
            $("#roleAdd_roleUserRight option").each(function(){
                $(this).attr("selected","true");
            });
            if ($("#roleAdd_roleId").val() != "") {
                // 修改角色
                $("#roleAdd_addForm").attr("action",
                "./roleAction/updateRole.action");
            } 
            //alert("roleid: "+ $("#roleAdd_roleId").val() +";  "+ $("#roleAdd_addForm").attr("action"));
            var options = { 
                beforeSubmit:  roleAddValidate,  // pre-submit callback 
                success:       roleAddSuccess,  // post-submit callback 
                dataType:		'json'
            }; 
            // 提交表单
            $("#roleAdd_addForm").ajaxSubmit(options); 
        });
    });
	
    function roleAddValidate(formData, jqForm, options){
		
        if($("#roleAdd_roleId").val() != ""){
            // 如果是修改角色
            if( $("#roleAdd_tmp_name").val() != $("#roleAdd_name").val()){
                // 角色名称改变了，则需要验证新的角色名称(主要是防止角色重复)
                if(!checkRoleName("#roleAdd_name", "#nameResult", true)){
                    return false;
                }
            } 
        }else{
            // 新增角色
            // 需要验证新的角色名称(主要是防止角色重复)
            if(!checkRoleName("#roleAdd_name", "#nameResult", true)){
                return false;
            }
        }
		
        if (!checkRoleDesc("#roleAdd_desc", "#descriptionResult", true)
            || !checkRoleResources("multiResources", "#resourcesResult")) {
			
            return false;
        }        
        $("#roleResult").html("");
        //alert("validate success.");
    }
    function roleAddSuccess(responseText, statusText, xhr, $form){
        var suc_msg = "";
        var err_msg = "";
        if ($("#roleAdd_roleId").val() != "") {
            suc_msg = "角色修改成功";
            err_msg = "角色修改失败，请稍后重试";
        }else{
            suc_msg = "角色新增成功";
            err_msg = "角色新增失败，请稍后重试";
        }
        if(responseText.flag == "success" ){
            alert(suc_msg);
            forwardRoleList();
        }else{
            alert(err_msg);
        }
    }
	
    /**
     * 清除结果信息
     */
    function clearHtml(){
        $("span[id$=Result]").removeAttr("class").html("");
        $("#resourcesResult").removeAttr("class").html("");
    }
    /**
     * 角色列表页面跳转
     *
     */
    function forwardRoleList(){
        var originalUrl = "./ap/role/rolelist.jsp";
        var tempUrl = "./ap/role/rolelist.jsp";
        var localUrl = "./roleAction/forward.action?flag=addForward";
        var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
        tabpanel.kill(_killId);
        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
    }
</script>
