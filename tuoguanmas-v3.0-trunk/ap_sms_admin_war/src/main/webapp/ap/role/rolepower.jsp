<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form action="./roleAction/givePower.action" method="post" id="rolePower_givePowerForm">
	<input type="hidden" name="flag" value="save" id="flag" /> 
	<input type="hidden" name="portalRole.id" id="roleId" value="${roleVO.id }" />
	<ul class="gridwrapper">
		<li  style="height:40px;">
			<label>角色名称：</label>
		    <label id="labelName">${roleVO.name}</label>
		</li>
		<li>
			<label><span>*</span>使用授权：</label>
			<ul class="rolelistiestyle">
				<li><span style="font-weight:bold">功能名称</span> <span style="font-weight:bold">使用授权</span></li>

				<s:iterator id="resource" value="#request.resourceList">
					<li>
						${resource.name}
						<input type="checkbox" value="${resource.id }"
							<s:iterator id="res" value="#request.roleVO.resources">
								<s:if test="%{#res.id == #resource.id }">checked</s:if>
							</s:iterator>
							name="multiResources"></input>
					</li>
				</s:iterator>
				<li><span id="rolePowerResourcesResult"></span></li>
			</ul></li>
			<li class="btn">
				<a href="#" id="rolePower_sub">提交</a> 
				<a href="#" id="rolePower_clear">返回</a>
			</li>
	</ul>
</form>
<script type="text/javascript">
	$(function() {
		$("#rolePower_clear").unbind("click").click(function(){
			// 返回按扭，跳到角色列表页面
			forwardRoleList();
		});
		$("#rolePower_sub").unbind("click").click(
				// 提交按钮
			function() {
				var options = { 
      		        beforeSubmit:  rolePowerValidate,  // pre-submit callback 
      		        success:       rolePowerSuccess,  // post-submit callback 
      		        dataType:		'json'
      		    }; 
           			// 提交表单
           		$("#rolePower_givePowerForm").ajaxSubmit(options); 
			});
	});
	
	/**
	  * 验证给角色分配资源
	  *
	  */
	function rolePowerValidate(formData, jqForm, options){
		if (!checkRoleResources("multiResources", "#rolePowerResourcesResult")) {
			return false;
		} 
	}
	
	/**
	  * 处理赋权结果， 赋权成功后，跳到角色列表页面，失败给出提示
	  *
	  */
	function rolePowerSuccess(responseText, statusText, xhr, $form){
		if(responseText.flag == "success" ){
			alert("赋权成功");
			forwardRoleList();
		}else{
			alert("赋权失败，请稍后重试");
		}
	}
	
	/**
	  *
	  * 跳转到角色列表页面
	  */ 
	  
	  function forwardRoleList(){
		  tabpanel.kill("rolepower222");
		  var originalUrl = "./ap/role/rolelist.jsp";
	      var tempUrl  = "./ap/role/rolelist.jsp";
		  $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
	      $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
	}
</script>
