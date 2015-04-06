<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<form action="./corpManageAction/addCorp.action" method="post" id="merchantAdd_addForm">    
    <ul class="gridwrapper" style="width:92%; margin: 10px auto; border: 5px solid #eaf1ee;">
        <li>
	        <ul>
	        	<li>
		            <label><span>*</span>企业名称：</label> 
		            <input type="hidden" name="merchant.merchantPin" value="<s:property value='merchant.merchantPin' />" id="merchantAdd_MerchantPin"/>
		            <input size="50" maxlength="20" type="text" name="merchant.name" id="merchantAdd_name" value="<s:property value='merchant.name' />" />
		            <label>企业强制签名： </label>
		            <input type="hidden" name="smsSignContent.id" value="<s:property value='smsSignContent.id'/>"/>
		            <input type="hidden" name="smsSignContent.name" value="sms_sign_content"/>
		            <input type="text" name="smsSignContent.itemValue" id="merchantAdd_sign" size="50" maxlength="20" value="<s:property value='smsSignContent.itemValue'/>" />
	            </li>
	            <li>
		            <label><span>*</span>是否托管：</label>
		            <input type="hidden" name="isdelegate.id" value="<s:property value='isdelegate.id'/>"/>
		            <input type="hidden" name="isdelegate.name" value="isdelegate"/>
		            <select name="isdelegate.itemValue" style="width: 266px;height: 20px;">
		                <option value="1" <s:if test="isdelegate.itemValue==1">selected</s:if>>托管</option>
		                <option value="0" <s:if test="isdelegate.itemValue==0">selected</s:if>>非托管</option>
		            </select>
		            
		            <label><span>*</span>指定节点：</label>
		            <select name="nodeId" id="corp_nodeId" style="width: 266px;height: 20px;" <s:if test="nodeTOStatus">onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;"</s:if>>
		                <s:iterator value="mbnNodeTOes" var="nodeTO">
		                    <option value="<s:property value="#nodeTO.id"/>" <s:if test="#nodeTO.isSelected">selected</s:if>><s:property value="#nodeTO.name"/></option>
		                </s:iterator>
		            </select>
	            </li>
	            <li>
					<label>MAS服务器IP地址：</label>
                    <input type="hidden" name="masserverip.id" value="<s:property value='masserverip.id'/>"/>
                    <input type="hidden" name="masserverip.name" value="masserverip"/>
                    <input size="50" maxlength="20" type="text" name="masserverip.itemValue" id="merchantAdd_masip" value="<s:property value='masserverip.itemValue'/>" />
                    <!--<label>允许的发送速度：</label>-->
                    <input type="hidden" name="gatewaylimit.id" value="<s:property value='gatewaylimit.id'/>"/>
                    <input type="hidden" name="gatewaylimit.name" value="gatewaylimit"/>
                    <input size="50" maxlength="20" type="hidden" name="gatewaylimit.itemValue" id="merchantAdd_gatewaylimit" value="<s:property value='gatewaylimit.itemValue'/>" />
                    <label><span>*</span>企业端口号：</label> 
		            <input type="hidden" name="corpLoginPort.id" value="<s:property value='corpLoginPort.id'/>"/>
		            <input type="hidden" name="corpLoginPort.name" value="corp_login_port"/>
		            <input size="50" maxlength="20" disabled="disabled" type="text" value="<s:if test='#request.corpPort == null'><s:property value='corpLoginPort.itemValue' /></s:if><s:else>${request.corpPort}</s:else>" />
		            <input size="50" maxlength="20" type="hidden" name="corpLoginPort.itemValue" value="<s:if test='#request.corpPort == null'><s:property value='corpLoginPort.itemValue' /></s:if><s:else>${request.corpPort}</s:else>" />                    
	            </li>
	        </ul>
        </li>
        <li>
        	<div style="overflow-y: auto;overflow-x: hidden; width:92%; height:250px; margin:auto;">
                <table id="tunnelCorpGrid">
                    <thead>
                        <tr class="tableopts">
                            <td colspan="3" class="btn">
                                <a href="javascript:void(0);"  id="forwardcreatetunnel">通道配置</a>
                                <!--<a href="javascript:void(0);"  id="deletetunnelCorp">删除</a>-->
                            </td>
                            <td colspan="3" id="tunnelCorppaginate" style="text-align: right">
<!--                                <a href="javascript:void(0)" id="tunnelCorpprepage">上一页</a>
                                <span id="tunnelCorpcurpage" style="margin: 0;padding: 0;" >1</span>
                                <span style="margin: 0 -5px 0 -5px;">/</span>-->
                                <span id="tunnelCorptotalpages" style="margin: 0;padding: 0;"></span>
                                <!--<a href="javascript:void(0)" id="tunnelCorpnextpage">下一页</a>
                                <a href="javascript:void(0)" id="tunnelCorpjumppage">跳转</a>
                                <input type="text" id="tunnelCorpenterpagee" value="1" style="width:40px;" />-->
                            </td>
                        </tr>
                        <tr>
                        	<th class="tabletrhead">通道名称</th>
                            <th class="tabletrhead">通道账号</th>
                            <th class="tabletrhead">通道密码</th>
                            <th class="tabletrhead">通道接口地址</th>
                            <th class="tabletrhead">状态</th>
                            <th class="tabletrhead">最后更新时间</th>
                        </tr>
                        <tr class="tabletrboby" style="display:none" id="tunnelCorprowtemplate">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>                    
                    </thead>
                    <tbody id="tunnelCorpdatalist">

                    </tbody>
                </table>
            </div>
        </li>
        <li class="btn" style="float: right; margin-right: 80px;">
            <a href="#" id="merchantAdd_sub">提交</a>
            <a href="#" id="merchantAdd_clear">取消</a>
        </li> 
    </ul>
</form>
<script type="text/javascript">
$(document).ready(function(){
    getCorpPageData();
});
function getCorpPageData(){
    $.ajax({
        url: "./corpManageAction/showCorpTunnel.action?merchant.merchantPin="+$("#merchantHidden_MerchantPin").val(),
        type:"POST",
        dataType: 'json',
        success:function(data){
            refreshCorpList(data);
            $("#tunnelCorpdatalist tr").each(function(){
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
    $("#tunnelCorptotalpages").html(Math.floor((data.totalrecords-1)/20)+1);
    //设置表格数据
    $("#tunnelCorpdatalist tr").remove();
    for(var i=0;data&&data.rows&&i<data.rows.length;i++){
        addCorpRow(data.rows[i]);
    }
}
function addCorpRow(data){
    //使用jquery拷贝方式生成行数据
    $("#tunnelCorprowtemplate").clone(true).appendTo("#tunnelCorpdatalist");
    $("#tunnelCorpdatalist tr:last").attr("id",data.id).show();
    $("#tunnelCorpdatalist tr:last td").eq(0).html(data.name);
    $("#tunnelCorpdatalist tr:last td").eq(1).html(data.user);
    $("#tunnelCorpdatalist tr:last td").eq(2).html(data.passwd);        
    $("#tunnelCorpdatalist tr:last td").eq(3).html(data.gatewayAddr);
    $("#tunnelCorpdatalist tr:last td").eq(4).html(data.state == 1?"可用":"不可用");
    $("#tunnelCorpdatalist tr:last td").eq(5).html(data.updateTime);
}
</script>

<script type="text/javascript">
     var mobilRegular = /^(134|135|136|137|138|139|147|150|151|152|157|158|159|182|187|188)\d{8}$/;            
    var ipRegular = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    $(function() {
    	$("#merchantAdd_clear").unbind("click").click(function(){
    		$('#merchantAdd_addForm').resetForm();
			$("#paramForm").resetForm();
    	});
        $("#merchantAdd_sub").unbind("click").click(function(){
            if($("#merchantAdd_sms_send_limit").val() == "") {
                $("#merchantAdd_sms_send_limit").val("500");
            }
            $("#mobileconsumeAdd_remainNumber").val($("#merchantAdd_sms_send_limit").val());
            if($("#merchantAdd_MerchantPin").val() != "") {
                $('#merchantAdd_addForm').attr("action", "./corpManageAction/updateCorp.action");//updateCorpAndConfig
                $('#paramForm').attr("action", "./corpManageAction/updateConfigs.action");
            }
            
            if($("#merchantAdd_name").val() == "") {
                alert("请填写企业名称");
                return;
            }
            if($("#merchantAdd_masip").val() != "" && !ipRegular.test($("#merchantAdd_masip").val())){
                alert("MAS服务器IP地址格式有误,请检查");
                return;
            }
            if($("#merchantAdd_gatewaylimit").val() != "" && !isNumber($("#merchantAdd_gatewaylimit").val())){
                alert("允许的发送速度格式有误,请检查");
                return;
            }
            
            
            if($("#tunnel_type_1").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_1").attr("tRange"))!==""){
            	if($("#tunnelAdd_accessNumber").val() == ""){
	            	alert("请填写移动CMPP短信接入号");
	                return;
            	}
	            if($("#tunnelAdd_gatewayPort").val() == ""){
	                alert("请填写移动CMPP短信网关端口");
	                return;
	            }
	            /*if($("#merchantAdd_sign").val() == ""){
	                alert("请填写企业签名");
	                return;
	            }*/
	            if($("#tunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#tunnelAdd_gatewayAddr").val())){
	                alert("移动CMPP短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#tunnelAdd_gatewayPort").val() != "" && !isNumber($("#tunnelAdd_gatewayPort").val())){
	                alert("移动CMPP短信网关端口格式有误,请检查");
	                return;
	            }
            }
            <s:if test="registStatus">
            //ltTunnelAdd_accessNumber
            if($("#tunnel_type_2").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_2").attr("tRange"))!==""){
            	if($("#ltTunnelAdd_accessNumber").val() == ""){
	            	alert("请填写联通SGIP短信接入号");
	                return;
            	}
	            if($("#ltTunnelAdd_gatewayPort").val() == ""){
	                alert("请填写联通SGIP短信网关端口");
	                return;
	            }
	            if($("#ltTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#ltTunnelAdd_gatewayAddr").val())){
	                alert("联通SGIP短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#ltTunnelAdd_gatewayPort").val() != "" && !isNumber($("#ltTunnelAdd_gatewayPort").val())){
	                alert("联通SGIP短信网关端口格式有误,请检查");
	                return;
	            }
            }
            
            if($("#tunnel_type_3").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_3").attr("tRange"))!==""){
            	if($("#dxTunnelAdd_accessNumber").val() == ""){
	            	alert("请填写电信SMGP短信接入号");
	                return;
            	}
	            if($("#dxTunnelAdd_gatewayPort").val() == ""){
	                alert("请填写电信SMGP短信网关端口");
	                return;
	            }
	            if($("#dxTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#dxTunnelAdd_gatewayAddr").val())){
	                alert("电信SMGP短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#dxTunnelAdd_gatewayPort").val() != "" && !isNumber($("#dxTunnelAdd_gatewayPort").val())){
	                alert("电信SMGP短信网关端口格式有误,请检查");
	                return;
	            }
            }
            
            if($("#tunnel_type_5").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_5").attr("tRange"))!==""){
            	if($("#zxtTunnelAdd_user").val() == ""){
	                alert("请输入资信通帐号");
	                return;
	            }
	            if($("#zxtTunnelAdd_passwd").val() == ""){
	                alert("请输入资信通密码");
	                return;
	            }
	            if($("#zxtTunnelAdd_gatewayAddr").val() == ""){
	                alert("请输入资信通地址");
	                return;
	            }
            }
            if($("#tunnel_type_6").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_6").attr("tRange"))!==""){
            	if($("#newQxtTunnelAdd_user").val() == ""){
	                alert("请输入企信通帐号");
	                return;
	            }
	            if($("#newQxtTunnelAdd_passwd").val() == ""){
	                alert("请输入企信通密码");
	                return;
	            }
	            if($("#newQxtTunnelAdd_gatewayAddr").val() == ""){
	                alert("请输入企信通地址");
	                return;
	            }
            }
            if($("#tunnel_type_7").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_7").attr("tRange"))!==""){
            	if($("#emppTunnelAdd_gatewayAddr").val() == ""){
	                alert("请输入上海移动EMPP地址");
	                return;
	            }
	            if($("#emppTunnelAdd_gatewayPort").val() == ""){
	                alert("请输入上海移动EMPP网关端口");
	                return;
	            }
	            if($("#emppTunnelAdd_user").val() == ""){
	                alert("请输入上海移动EMPP帐号");
	                return;
	            }
	            if($("#emppTunnelAdd_passwd").val() == ""){
	                alert("请输入上海移动EMPP密码");
	                return;
	            }
            }
            if($("#tunnel_type_9").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_9").attr("tRange"))!==""){
            	if($("#tunnelywAdd_accessNumber").val() == ""){
	            	alert("请填写辽宁CMPP异网短信接入号");
	                return;
            	}
	            if($("#tunnelywAdd_gatewayPort").val() == ""){
	                alert("请填写辽宁CMPP异网短信网关端口");
	                return;
	            }
	            /*if($("#merchantywAdd_sign").val() == ""){
	                alert("请填写企业签名");
	                return;
	            }*/
	            if($("#tunnelywAdd_gatewayAddr").val() != "" && !ipRegular.test($("#tunnelywAdd_gatewayAddr").val())){
	                alert("辽宁CMPP异网短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#tunnelywAdd_gatewayPort").val() != "" && !isNumber($("#tunnelywAdd_gatewayPort").val())){
	                alert("辽宁CMPP异网短信网关端口格式有误,请检查");
	                return;
	            }
            }
            if($("#tunnel_type_4").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_4").attr("tRange"))!==""){
            	if($("#mobiletunnelAdd_accessNumber").val() == "" ){
            		alert("请输入SIM卡号");
	                return;
            	}
            	if(!mobilRegular.test($("#mobiletunnelAdd_accessNumber").val())){
	                alert("SIM卡号格式有误,请检查");
	                return;
	            }
	            if($("#merchantAdd_sms_send_limit").val() != "" && !isNumber($("#merchantAdd_sms_send_limit").val())){
	                alert("SIM卡发送限制格式有误,请检查");
	                return;
	            }
            }
            </s:if>
            //if($("#tunnel_type_8").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_8").attr("tRange"))!==""){
            	if($("#mmtunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#mmtunnelAdd_gatewayAddr").val())){
	                alert("彩信中心IP地址格式有误,请检查");
	                return;
	            }
	            if($("#mmtunnelAdd_gatewayPort").val() != "" && !isNumber($("#mmtunnelAdd_gatewayPort").val())){
	                alert("彩信中心端口格式有误,请检查");
	                return;
	            }
	            if($("#mmtopport").val() != "" && !isNumber($("#mmtopport").val())){
	                alert("彩信中心上行端口格式有误,请检查");
	                return;
	            }
            //}
            
            $('#merchantAdd_addForm').ajaxSubmit(function(data) {
            	$("#tunnel_corp_nodeId").val($("#corp_nodeId").val());
                var resultInfo = jQuery.parseJSON(data);
                if(resultInfo.flag){
                	$("#merchantHidden_MerchantPin").val(resultInfo.merchant.merchantPin);
                	$("#paramForm").ajaxSubmit(function(data){
                		var resultTunnel = jQuery.parseJSON(data);
                		alert(resultTunnel.resultInfo);
                		
                		var originalUrl = "./corpManageAction/preCorpConfigInfoNodes.action";
				        var tempUrl = "./corpManageAction/showCorpAndConfig.action?merchant.merchantPin="+$("#merchantHidden_MerchantPin").val();
				        var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
				        try{
				            tabpanel.kill(killId);
				        }catch(e){
				        }
				        $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
				        $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
                		//getCorpPageData();
                		//if(resultTunnel.flag){
                			/*$("input[group='tunnelRange']").each(function(){
								$(this).attr("disabled",false);
							});*/
							
                			//$("#paramForm").resetForm();
                		//}
                	});
                }else{
                	alert(resultInfo.resultInfo);
                }
            });
        })        	
    })
</script>
<script type="text/javascript">
 var dialogDivOwn = "";
 var dialogOptionsCreateTunnel = {
    /**************新增或修改弹出层相关参数***********************/
    //height: 140,
    resizable: false,
    width: 630,  
    height: 400,  
    modal: true,
    autoOpen: true,
    title: "通道参数",
    open: function(){
    	$("#tunnelAdd_tunnelType").trigger("change");
    },
    close: function(){
    	var tempValue = $("#tunnelAdd_protocalVersion").val();
    	var tempYWValue = $("#tunnelywAdd_protocalVersion").val();
    	$("#tunnelAdd_protocalVersion").children().attr("selected",false);
    	$("#tunnelywAdd_protocalVersion").children().attr("selected",false);
    	dialogDivOwn = $("#corpTunnelSettingDiv").clone();
    	dialogDivOwn.appendTo($("#paramForm"));
    	$("#tunnelAdd_protocalVersion").find("option[value='"+tempValue+"']").attr("selected",true);
    	$("#tunnelywAdd_protocalVersion").find("option[value='"+tempYWValue+"']").attr("selected",true);
    	//paramForm
    	$(this).dialog("destroy").remove();
    	$("#forwardcreatetunnel").unbind("click").click(function(){
			//dialogDivOwn = $("#corpTunnelSettingDiv").clone();
			$("#corpTunnelSettingDiv").dialog(dialogOptionsCreateTunnel);
			//$("#corpTunnelSettingDiv").dialog("open");
	    });
	    $("#tunnelAdd_tunnelType").unbind("change").bind("change",function(){
	    	$("#tunnelAdd_tunnelType").children().each(function(){
	    		var thisRange = $(this).attr("tRange");
	    		if(thisRange!=="undefined"){
	    			var tRangeArray = thisRange.split(",");
	    			for(var index = 0 ; index < tRangeArray.length; index++ ){
	    				if(tRangeArray[index]!==""){
	    					$("#mobileRange"+tRangeArray[index]).attr("disabled",true);
	    				}
	    			}
	    		}
	    		
	    	});
	    	var  theRange = $("#tunnelAdd_tunnelType").find("option:selected").attr("tRange");
	    	if(theRange!=="undefined"){
				var seRangeArray = theRange.split(",");
				for(var i= 0 ; i < seRangeArray.length; i++ ){
					if(seRangeArray[i]!==""){
						$("#mobileRange"+seRangeArray[i]).attr("disabled",false);
					}
				}
			}
	    	$("li[class='b_margin']").parent().parent().hide();
	    	$("#"+$(this).val()).show();
	    });
	    $("input[group='tunnelRange']").unbind("click").bind("click",function(){
	    	var tRange = "";
	    	var vRange = 0;
			$("input[group='tunnelRange']").each(function(){
				if($(this).is(":checked")&&!$(this).attr("disabled")){
					tRange = $(this).val() + ","+ tRange;
					vRange = vRange|$(this).val();
				}
			});
			$("#tunnelAdd_tunnelType").find("option:selected").attr("tRange", tRange);//1
			$("#"+$("#tunnelAdd_tunnelType").val()+"_range").val(vRange);
	    });
    },
    buttons:{
    	"确定": function(){
    		if($("#tunnel_type_1").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_1").attr("tRange"))!==""){
            	if($("#tunnelAdd_accessNumber").val() == ""){
	            	alert("请填写移动CMPP短信接入号");
	                return;
            	}
	            if($("#tunnelAdd_gatewayPort").val() == ""){
	                alert("请填写移动CMPP短信网关端口");
	                return;
	            }
	            /*if($("#merchantAdd_sign").val() == ""){
	                alert("请填写企业签名");
	                return;
	            }*/
	            if($("#tunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#tunnelAdd_gatewayAddr").val())){
	                alert("移动CMPP短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#tunnelAdd_gatewayPort").val() != "" && !isNumber($("#tunnelAdd_gatewayPort").val())){
	                alert("移动CMPP短信网关端口格式有误,请检查");
	                return;
	            }
            }
            <s:if test="registStatus">
            //ltTunnelAdd_accessNumber
            if($("#tunnel_type_2").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_2").attr("tRange"))!==""){
            	if($("#ltTunnelAdd_accessNumber").val() == ""){
	            	alert("请填写联通SGIP短信接入号");
	                return;
            	}
	            if($("#ltTunnelAdd_gatewayPort").val() == ""){
	                alert("请填写联通SGIP短信网关端口");
	                return;
	            }
	            if($("#ltTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#ltTunnelAdd_gatewayAddr").val())){
	                alert("联通SGIP短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#ltTunnelAdd_gatewayPort").val() != "" && !isNumber($("#ltTunnelAdd_gatewayPort").val())){
	                alert("联通SGIP短信网关端口格式有误,请检查");
	                return;
	            }
            }
            
            if($("#tunnel_type_3").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_3").attr("tRange"))!==""){
            	if($("#dxTunnelAdd_accessNumber").val() == ""){
	            	alert("请填写电信SMGP短信接入号");
	                return;
            	}
	            if($("#dxTunnelAdd_gatewayPort").val() == ""){
	                alert("请填写电信SMGP短信网关端口");
	                return;
	            }
	            if($("#dxTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#dxTunnelAdd_gatewayAddr").val())){
	                alert("电信SMGP短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#dxTunnelAdd_gatewayPort").val() != "" && !isNumber($("#dxTunnelAdd_gatewayPort").val())){
	                alert("电信SMGP短信网关端口格式有误,请检查");
	                return;
	            }
            }
            
            if($("#tunnel_type_5").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_5").attr("tRange"))!==""){
            	if($("#zxtTunnelAdd_user").val() == ""){
	                alert("请输入资信通帐号");
	                return;
	            }
	            if($("#zxtTunnelAdd_passwd").val() == ""){
	                alert("请输入资信通密码");
	                return;
	            }
	            if($("#zxtTunnelAdd_gatewayAddr").val() == ""){
	                alert("请输入资信通地址");
	                return;
	            }
            }
            if($("#tunnel_type_6").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_6").attr("tRange"))!==""){
            	if($("#newQxtTunnelAdd_user").val() == ""){
	                alert("请输入企信通帐号");
	                return;
	            }
	            if($("#newQxtTunnelAdd_passwd").val() == ""){
	                alert("请输入企信通密码");
	                return;
	            }
	            if($("#newQxtTunnelAdd_gatewayAddr").val() == ""){
	                alert("请输入企信通地址");
	                return;
	            }
            }
            if($("#tunnel_type_7").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_7").attr("tRange"))!==""){
            	if($("#emppTunnelAdd_gatewayAddr").val() == ""){
	                alert("请输入上海移动EMPP地址");
	                return;
	            }
	            if($("#emppTunnelAdd_gatewayPort").val() == ""){
	                alert("请输入上海移动EMPP网关端口");
	                return;
	            }
	            if($("#emppTunnelAdd_user").val() == ""){
	                alert("请输入上海移动EMPP帐号");
	                return;
	            }
	            if($("#emppTunnelAdd_passwd").val() == ""){
	                alert("请输入上海移动EMPP密码");
	                return;
	            }
            }
            if($("#tunnel_type_9").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_9").attr("tRange"))!==""){
            	if($("#tunnelywAdd_accessNumber").val() == ""){
	            	alert("请填写辽宁CMPP异网短信接入号");
	                return;
            	}
	            if($("#tunnelywAdd_gatewayPort").val() == ""){
	                alert("请填写辽宁CMPP异网短信网关端口");
	                return;
	            }
	            /*if($("#merchantywAdd_sign").val() == ""){
	                alert("请填写企业签名");
	                return;
	            }*/
	            if($("#tunnelywAdd_gatewayAddr").val() != "" && !ipRegular.test($("#tunnelywAdd_gatewayAddr").val())){
	                alert("辽宁CMPP异网短信网关IP地址格式有误,请检查");
	                return;
	            }
	            if($("#tunnelywAdd_gatewayPort").val() != "" && !isNumber($("#tunnelywAdd_gatewayPort").val())){
	                alert("辽宁CMPP异网短信网关端口格式有误,请检查");
	                return;
	            }
            }
            if($("#tunnel_type_4").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_4").attr("tRange"))!==""){
            	if($("#mobiletunnelAdd_accessNumber").val() == "" ){
            		alert("请输入SIM卡号");
	                return;
            	}
            	if(!mobilRegular.test($("#mobiletunnelAdd_accessNumber").val())){
	                alert("SIM卡号格式有误,请检查");
	                return;
	            }
	            if($("#merchantAdd_sms_send_limit").val() != "" && !isNumber($("#merchantAdd_sms_send_limit").val())){
	                alert("SIM卡发送限制格式有误,请检查");
	                return;
	            }
            }
            </s:if>
            //if($("#tunnel_type_8").attr("tRange")!=="undefined"&&$.trim($("#tunnel_type_8").attr("tRange"))!==""){
            	if($("#mmtunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#mmtunnelAdd_gatewayAddr").val())){
	                alert("彩信中心IP地址格式有误,请检查");
	                return;
	            }
	            if($("#mmtunnelAdd_gatewayPort").val() != "" && !isNumber($("#mmtunnelAdd_gatewayPort").val())){
	                alert("彩信中心端口格式有误,请检查");
	                return;
	            }
	            if($("#mmtopport").val() != "" && !isNumber($("#mmtopport").val())){
	                alert("彩信中心上行端口格式有误,请检查");
	                return;
	            }
            //}
    		$(this).dialog("close");
        }/*,
        "取消": function(){
            $(this).dialog("close");
        }*/
    }
};
$(function() {
	$("#forwardcreatetunnel").unbind("click").click(function(){
		//dialogDivOwn = $("#corpTunnelSettingDiv").clone();
		$("#corpTunnelSettingDiv").dialog(dialogOptionsCreateTunnel);
		//$("#corpTunnelSettingDiv").dialog("open");
    });
    //$("#tunnelAdd_protocalVersion").find("option[value='"+$("#tunnelAdd_protocalVersion_handler").val()+"']").attr("selected",true);
    /*$("#tunnelAdd_protocalVersion").unbind("change").bind("change",function(){
    	$("#tunnelAdd_protocalVersion").children().attr("selected",false).end().find("option[value='"+$(this).val()+"']").attr("selected",true);
    });*/
    $("#tunnelAdd_tunnelType").unbind("change").bind("change",function(){
    	$("#tunnelAdd_tunnelType").children().each(function(){
    		var thisRange = $(this).attr("tRange");
    		if(thisRange!=="undefined"){
    			var tRangeArray = thisRange.split(",");
    			for(var index = 0 ; index < tRangeArray.length; index++ ){
    				if(tRangeArray[index]!==""){
    					$("#mobileRange"+tRangeArray[index]).attr("disabled",true);
    				}
    			}
    		}
    		
    	});
    	var  theRange = $("#tunnelAdd_tunnelType").find("option:selected").attr("tRange");
    	if(theRange!=="undefined"){
			var seRangeArray = theRange.split(",");
			for(var i= 0 ; i < seRangeArray.length; i++ ){
				if(seRangeArray[i]!==""){
					$("#mobileRange"+seRangeArray[i]).attr("disabled",false);
				}
			}
		}
    	$("li[class='b_margin']").parent().parent().hide();
    	$("#"+$(this).val()).show();
    });
    $("input[group='tunnelRange']").unbind("click").bind("click",function(){
    	var tRange = "";
    	var vRange = 0;
		$("input[group='tunnelRange']").each(function(){
			if($(this).is(":checked")&&!$(this).attr("disabled")){
				tRange = $(this).val() + ","+ tRange;
				vRange = vRange|$(this).val();
			}
		});
		$("#tunnelAdd_tunnelType").find("option:selected").attr("tRange", tRange);//1
		$("#"+$("#tunnelAdd_tunnelType").val()+"_range").val(vRange);
    	/*if($(this).is(":checked")){
    		var tRange = "";
    		$("input[group='tunnelRange']").each(function(){
    			tRange = $(this).val() + ","+ tRange;
    		});
			$("#tunnelAdd_tunnelType").find("option:selected").attr("tRange", tRange);//1
		}else{
			$('#merchantBlackFlag').val("1");//0
		}*/
    });
});
</script>
<style type="text/css">
.tunnel_title{width: 120px;display: inline-block;}
.red_color{color: #FF0000;}
.b_margin .b_margin_both{ margin: 5px 0; }
.b_margin label, .b_margin_both label{ font-weight: bold;font-size: 18px; }
.tunnel_hidden{ display: none; };
</style>
<div id="corpTunnelSettingDivHolder" class="tunnel_hidden">
<form id="paramForm" action="./corpManageAction/addConfig.action" method="post">
<div id="corpTunnelSettingDiv">
	<input type="hidden" name="merchant.merchantPin" value="<s:property value='merchant.merchantPin' />" id="merchantHidden_MerchantPin"/>
	<input type="hidden" name="nodeId" value="" id="tunnel_corp_nodeId"/>
	<ul>
		<li>
			<ul>
                <li class="b_margin_both" >
                    <label class="tunnel_title">通道类型：</label>
                    <select name="tunnelAdd_tunnelType"  style="width: 150px;height: 20px;" id="tunnelAdd_tunnelType">
                        <option value="cmpp_tunnel" id="tunnel_type_1" tRange="" >移动CMPP</option>
                        <s:if test="registStatus">
                        <option value="sgip_tunnel" id="tunnel_type_2" tRange="">联通SGIP</option>
                        <option value="smgp_tunnel" id="tunnel_type_3" tRange="">电信SMGP</option>
                        <option value="zxt_tunnel" id="tunnel_type_5" tRange="">资信通</option>
                        <!--<option value="5" >企信通</option>-->
                        <option value="new_qxt_tunnel" id="tunnel_type_6" tRange="">新企信通</option>
                        <option value="empp_tunnel" id="tunnel_type_7" tRange="">上海移动empp</option>
                        <option value="sim_tunnel" id="tunnel_type_4" tRange="">话机SIM卡</option>
                        <option value="cmppyw_tunnel" id="tunnel_type_9" tRange="">辽宁CMPP异网</option>
                        </s:if>
                        <option value="mms_tunnel" id="tunnel_type_8" tRange="">彩信</option>
                    </select>
                    <s:if test="!registStatus">
                    	<a style="color:red;" target="_blank" href="${ctx}/register.jsp">激活第三方通道</a>
                    </s:if>
                </li>
                <li class="b_margin_both">
                    <label class="tunnel_title">通道范围：</label>
                    <input type="checkbox" group="tunnelRange" id="mobileRange1" value="1">本省移动
                    <input type="checkbox" group="tunnelRange" id="mobileRange2" value="2">他省移动
                   <!-- <input type="checkbox" group="tunnelRange" id="mobileRange3" value="3">全国移动-->
                    <input type="checkbox" group="tunnelRange" id="mobileRange4" value="4">联通
                    <input type="checkbox" group="tunnelRange" id="mobileRange8" value="8">电信
                </li>
        	</ul>
		</li>
        <li id="cmpp_tunnel"> 
            <ul>
                <li class="b_margin">
                    <label>配置移动</label>
                </li>
                <li>
                    <label class="tunnel_title">短信企业代码：</label>
                    <input type="hidden" name="tunnel.id" value="<s:property value='tunnel.id'/>"/>
                    <input type="hidden" name="tunnel.tunnelRange" id="cmpp_tunnel_range" value="<s:property value='tunnel.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#cmpp_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_1").attr("tRange",strRange);
					});
					</script>
                    <input maxlength="20" type="text" name="tunnel.smsCorpIdent" id="tunnelAdd_smsCorpIdent" value="<s:property value='tunnel.smsCorpIdent'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>短信接入号：</label>
                    <input maxlength="20" type="text" name="tunnel.accessNumber" id="tunnelAdd_accessNumber" value="<s:property value='tunnel.accessNumber'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信业务代码：</label>
                    <input maxlength="20" type="text" name="tunnel.serviceId" id="tunnelAdd_ServiceId" value="<s:property value='tunnel.serviceId'/>" />
                    <label class="tunnel_title">短信协议版本：</label>
                    <select name="tunnel.protocalVersion"  style="width: 150px;height: 20px;" id="tunnelAdd_protocalVersion">
                        <option value="cmpp2.0" <s:if test="tunnel.protocalVersion=='cmpp2.0'">selected</s:if>>cmpp2.0</option>
                        <option value="cmpp3.0" <s:if test="tunnel.protocalVersion=='cmpp3.0'">selected</s:if>>cmpp3.0</option>
                    </select>
                </li>
                <li>
                    <label class="tunnel_title">短信网关IP地址：</label>
                    <input maxlength="20" type="text" name="tunnel.gatewayAddr" id="tunnelAdd_gatewayAddr" value="<s:property value='tunnel.gatewayAddr'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>短信网关端口：</label>
                    <input maxlength="20" type="text" name="tunnel.gatewayPort" id="tunnelAdd_gatewayPort" value="<s:property value='tunnel.gatewayPort'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信网关用户名：</label>
                    <input maxlength="20" type="text" name="tunnel.user" id="tunnelAdd_user" value="<s:property value='tunnel.user'/>" />
                    <label class="tunnel_title">短信网关密码：</label>
                    <input maxlength="20" type="text" name="tunnel.passwd" id="tunnelAdd_passwd" value="<s:property value='tunnel.passwd'/>" />
                </li>            
            </ul>
        </li>
        <s:if test="registStatus">
        <li id="sgip_tunnel" class="tunnel_hidden"> 
            <ul>
                <li class="b_margin">
                    <label>配置联通</label>
                </li>
                <li>
                    <label class="tunnel_title">短信企业代码：</label>
                    <input type="hidden" name="ltTunnel.id" value="<s:property value='ltTunnel.id'/>"/>
                    <input type="hidden" name="ltTunnel.tunnelRange" id="sgip_tunnel_range" value="<s:property value='ltTunnel.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#sgip_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_2").attr("tRange",strRange);
					});
					</script>
                    <input maxlength="20" type="text" name="ltTunnel.smsCorpIdent" id="ltTunnelAdd_smsCorpIdent" value="<s:property value='ltTunnel.smsCorpIdent'/>" /> 
                    <label class="tunnel_title"><span class="red_color">*</span>短信接入号：</label>
                    <input maxlength="20" type="text" name="ltTunnel.accessNumber" id="ltTunnelAdd_accessNumber" value="<s:property value='ltTunnel.accessNumber'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信业务代码：</label>
                    <input maxlength="20" type="text" name="ltTunnel.serviceId" id="ltTunnelAdd_ServiceId" value="<s:property value='ltTunnel.serviceId'/>" />
                    <label class="tunnel_title">短信协议版本：</label>
                    <select name="ltTunnel.protocalVersion"  style="width: 150px;height: 20px;" id="ltTunnelAdd_protocalVersion">
                        <option value="SGIP" <s:if test="ltTunnel.protocalVersion=='SGIP'">selected</s:if>>SGIP</option>
                    </select>
                </li>
                <li>
                    <label class="tunnel_title">短信网关IP地址：</label>
                    <input maxlength="20" type="text" name="ltTunnel.gatewayAddr" id="ltTunnelAdd_gatewayAddr" value="<s:property value='ltTunnel.gatewayAddr'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>短信网关端口：</label>
                    <input maxlength="20" type="text" name="ltTunnel.gatewayPort" id="ltTunnelAdd_gatewayPort" value="<s:property value='ltTunnel.gatewayPort'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信网关用户名：</label>
                    <input maxlength="20" type="text" name="ltTunnel.user" id="ltTunnelAdd_user" value="<s:property value='ltTunnel.user'/>" />
                    <label class="tunnel_title">短信网关密码：</label>
                    <input maxlength="20" type="text" name="ltTunnel.passwd" id="ltTunnelAdd_passwd" value="<s:property value='ltTunnel.passwd'/>" />
                </li>            
            </ul>     
        </li>
        <li id="smgp_tunnel" class="tunnel_hidden"> 
            <ul>
                <li class="b_margin">
                    <label>配置电信</label>
                </li>
                <li>
                    <label class="tunnel_title">短信企业代码：</label>
                    <input type="hidden" name="dxTunnel.tunnelRange" id="smgp_tunnel_range" value="<s:property value='dxTunnel.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#smgp_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_3").attr("tRange",strRange);
					});
					</script>
                    <input type="hidden" name="dxTunnel.id" value="<s:property value='dxTunnel.id'/>"/>
                    <input maxlength="20" type="text" name="dxTunnel.smsCorpIdent" id="dxTunnelAdd_smsCorpIdent" value="<s:property value='dxTunnel.smsCorpIdent'/>" /> 
                    <label class="tunnel_title"><span class="red_color">*</span>短信接入号：</label>
                    <input maxlength="20" type="text" name="dxTunnel.accessNumber" id="dxTunnelAdd_accessNumber" value="<s:property value='dxTunnel.accessNumber'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信业务代码：</label>
                    <input maxlength="20" type="text" name="dxTunnel.serviceId" id="dxTunnelAdd_ServiceId" value="<s:property value='dxTunnel.serviceId'/>" />
                    <label class="tunnel_title">短信协议版本：</label>
                    <select name="dxTunnel.protocalVersion"  style="width: 150px;height: 20px;" id="dxTunnelAdd_protocalVersion">
                        <option value="SMGP3.0" <s:if test="dxTunnel.protocalVersion=='SMGP3.0'">selected</s:if>>SMGP3.0</option>
                    </select>
                </li>
                <li>
                    <label class="tunnel_title">短信网关IP地址：</label>
                    <input maxlength="20" type="text" name="dxTunnel.gatewayAddr" id="dxTunnelAdd_gatewayAddr" value="<s:property value='dxTunnel.gatewayAddr'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>短信网关端口：</label>
                    <input maxlength="20" type="text" name="dxTunnel.gatewayPort" id="dxTunnelAdd_gatewayPort" value="<s:property value='dxTunnel.gatewayPort'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信网关用户名：</label>
                    <input maxlength="20" type="text" name="dxTunnel.user" id="dxTunnelAdd_user" value="<s:property value='dxTunnel.user'/>" />
                    <label class="tunnel_title">短信网关密码：</label>
                    <input maxlength="20" type="text" name="dxTunnel.passwd" id="dxTunnelAdd_passwd" value="<s:property value='dxTunnel.passwd'/>" />
                </li>
            </ul>    
        </li>
        <li id="zxt_tunnel" class="tunnel_hidden"> 
            <ul>
                <li class="b_margin">
                    <label>配置资信通</label>
                </li>
                <li>
                    <label class="tunnel_title"><span class="red_color">*</span>用户名：</label>
                    <input type="hidden" name="zxtTunnel.tunnelRange" id="zxt_tunnel_range" value="<s:property value='zxtTunnel.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#zxt_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_5").attr("tRange",strRange);
					});
					</script>
                    <input type="hidden" name="zxtTunnel.id" value="<s:property value='zxtTunnel.id'/>"/>
                    <input maxlength="20" type="text" name="zxtTunnel.user" id="zxtTunnelAdd_user" value="<s:property value='zxtTunnel.user'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>密码：</label>
                    <input type="text" name="zxtTunnel.passwd" id="zxtTunnelAdd_passwd" value="<s:property value='zxtTunnel.passwd'/>" />
                </li>
                <li>
                    <label class="tunnel_title"><span class="red_color">*</span>接口地址：</label>
                    <input maxlength="20" type="text" name="zxtTunnel.gatewayAddr" id="zxtTunnelAdd_gatewayAddr" value="<s:property value='zxtTunnel.gatewayAddr'/>" />
                    <input maxlength="20" type="hidden" name="zxtTunnel.accessNumber" id="zxtTunnelAdd_accessNumber" value="<s:property value='zxtTunnel.accessNumber'/>" />
                </li>
            </ul>     
        </li>
        <li id="new_qxt_tunnel" class="tunnel_hidden"> 
            <ul>
                <li class="b_margin">
                    <label>配置企信通</label>
                </li>
                <li>
                    <label class="tunnel_title"><span class="red_color">*</span>用户名：</label>
                    <input type="hidden" name="newQxtTunnel.tunnelRange" id="new_qxt_tunnel_range" value="<s:property value='newQxtTunnel.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#new_qxt_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_6").attr("tRange",strRange);
					});
					</script>
                    <input type="hidden" name="newQxtTunnel.id" value="<s:property value='newQxtTunnel.id'/>"/>
                    <input maxlength="20" type="text" name="newQxtTunnel.user" id="newQxtTunnelAdd_user" value="<s:property value='newQxtTunnel.user'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>密码：</label>
                    <input type="text" name="newQxtTunnel.passwd" id="newQxtTunnelAdd_passwd" value="<s:property value='newQxtTunnel.passwd'/>" />
                </li>
                <li>
                    <label class="tunnel_title"><span class="red_color">*</span>接口地址：</label>
                    <input maxlength="20" type="text" name="newQxtTunnel.gatewayAddr" id="newQxtTunnelAdd_gatewayAddr" value="<s:property value='newQxtTunnel.gatewayAddr'/>" />
                    <input maxlength="20" type="hidden" name="newQxtTunnel.accessNumber" id="newQxtTunnelAdd_accessNumber" value="<s:property value='newQxtTunnel.accessNumber'/>" />
                </li>
            </ul>     
        </li>
        <li id="empp_tunnel" class="tunnel_hidden"> 
            <ul>
                <li class="b_margin">
                    <label>配置上海移动empp</label>
                </li>
                <li>
                	<label class="tunnel_title"><span class="red_color">*</span>网关IP地址：</label>
                	<input type="hidden" name="emppTunnel.tunnelRange" id="empp_tunnel_range" value="<s:property value='emppTunnel.tunnelRange'/>"/>
                	<script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#empp_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_7").attr("tRange",strRange);
					});
					</script>
                	<input type="hidden" name="emppTunnel.id" value="<s:property value='emppTunnel.id'/>"/>
                    <input maxlength="20" type="text" name="emppTunnel.gatewayAddr" id="emppTunnelAdd_gatewayAddr" value="<s:property value='emppTunnel.gatewayAddr'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>网关端口：</label>
                    <input maxlength="20" type="text" name="emppTunnel.gatewayPort" id="emppTunnelAdd_gatewayPort" value="<s:property value='emppTunnel.gatewayPort'/>" />
                </li>
                <li>
                	<label class="tunnel_title"><span class="red_color">*</span>网关帐号：</label>
                    <input maxlength="20" type="text" name="emppTunnel.user" id="emppTunnelAdd_user" value="<s:property value='emppTunnel.user'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>网关密码：</label>
                    <input type="text" name="emppTunnel.passwd" id="emppTunnelAdd_passwd" value="<s:property value='emppTunnel.passwd'/>" />
                </li>
                <li>
                	<label class="tunnel_title"><span class="red_color">*</span>短信接入号：</label>
                    <input maxlength="20" type="text" name="emppTunnel.accessNumber" id="emppTunnelAdd_accessNumber" value="<s:property value='emppTunnel.accessNumber'/>" />
                </li>
            </ul>     
        </li>
        <li id="cmppyw_tunnel"> 
            <ul>
                <li class="b_margin">
                    <label>配置移动</label>
                </li>
                <li>
                    <label class="tunnel_title">短信企业代码：</label>
                    <input type="hidden" name="tunnelyw.id" value="<s:property value='tunnelyw.id'/>"/>
                    <input type="hidden" name="tunnelyw.tunnelRange" id="cmppyw_tunnel_range" value="<s:property value='tunnelyw.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#cmppyw_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_9").attr("tRange",strRange);
					});
					</script>
                    <input maxlength="20" type="text" name="tunnelyw.smsCorpIdent" id="tunnelywAdd_smsCorpIdent" value="<s:property value='tunnelyw.smsCorpIdent'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>短信接入号：</label>
                    <input maxlength="20" type="text" name="tunnelyw.accessNumber" id="tunnelywAdd_accessNumber" value="<s:property value='tunnelyw.accessNumber'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信业务代码：</label>
                    <input maxlength="20" type="text" name="tunnelyw.serviceId" id="tunnelywAdd_ServiceId" value="<s:property value='tunnelyw.serviceId'/>" />
                    <label class="tunnel_title">短信协议版本：</label>
                    <select name="tunnelyw.protocalVersion"  style="width: 150px;height: 20px;" id="tunnelywAdd_protocalVersion">
                        <option value="cmpp2.0" <s:if test="tunnelyw.protocalVersion=='cmpp2.0'">selected</s:if>>cmpp2.0</option>
                        <option value="cmpp3.0" <s:if test="tunnelyw.protocalVersion=='cmpp3.0'">selected</s:if>>cmpp3.0</option>
                    </select>
                </li>
                <li>
                    <label class="tunnel_title">短信网关IP地址：</label>
                    <input maxlength="20" type="text" name="tunnelyw.gatewayAddr" id="tunnelywAdd_gatewayAddr" value="<s:property value='tunnelyw.gatewayAddr'/>" />
                    <label class="tunnel_title"><span class="red_color">*</span>短信网关端口：</label>
                    <input maxlength="20" type="text" name="tunnelyw.gatewayPort" id="tunnelywAdd_gatewayPort" value="<s:property value='tunnelyw.gatewayPort'/>" />
                </li>
                <li>
                    <label class="tunnel_title">短信网关用户名：</label>
                    <input maxlength="20" type="text" name="tunnelyw.user" id="tunnelywAdd_user" value="<s:property value='tunnelyw.user'/>" />
                    <label class="tunnel_title">短信网关密码：</label>
                    <input maxlength="20" type="text" name="tunnelyw.passwd" id="tunnelywAdd_passwd" value="<s:property value='tunnelyw.passwd'/>" />
                </li>            
            </ul>
        </li>
        <li id="sim_tunnel" class="tunnel_hidden"> 
            <ul>
                <li class="b_margin">
                    <label>配置话机</label>
                </li>
                <li>
                    <label class="tunnel_title"><span class="red_color">*</span>SIM卡号：</label>
                    <input type="hidden" name="mobileTunnel.tunnelRange" id="sim_tunnel_range" value="<s:property value='mobileTunnel.tunnelRange'/>"/>
                    <script type="text/javascript">
					$(document).ready(function(){
						var tunnelRangeValue = $("#sim_tunnel_range").val();
						var strRange = "";
						if((tunnelRangeValue&1)==1){
							$("#mobileRange1").attr("checked",true);
							strRange = "1" + ","+ strRange;
						}
						if((tunnelRangeValue&2)==2){
							$("#mobileRange2").attr("checked",true);
							strRange = "2" + ","+ strRange;
						}
						if((tunnelRangeValue&4)==4){
							$("#mobileRange4").attr("checked",true);
							strRange = "4" + ","+ strRange;
						}
						if((tunnelRangeValue&8)==8){
							$("#mobileRange8").attr("checked",true);
							strRange = "8" + ","+ strRange;
						}
						$("#tunnel_type_4").attr("tRange",strRange);
					});
					</script>
                    <input type="hidden" name="mobileTunnel.id" value="<s:property value='mobileTunnel.id'/>"/>
                    <input maxlength="20" type="text" name="mobileTunnel.accessNumber" id="mobiletunnelAdd_accessNumber" value="<s:property value='mobileTunnel.accessNumber'/>" />
                    <label class="tunnel_title">SIM卡发送限制：</label>
                    <input type="hidden" name="mobileConsume.id" value="<s:property value='mobileConsume.id'/>"/>
                    <input type="hidden" name="mobileConsume.remainNumber" id="mobileconsumeAdd_remainNumber" value="<s:property value='mobileConsume.remainNumber'/>" />
                    <input type="hidden" name="smsSendLimit.id" value="<s:property value='smsSendLimit.id'/>"/>
                    <input type="hidden" name="smsSendLimit.name" value="sms_send_limit"/>
                    <input maxlength="20" type="text" name="smsSendLimit.itemValue" id="merchantAdd_sms_send_limit" value="<s:property value='smsSendLimit.itemValue'/>" />
                </li>                
            </ul>     
        </li>
        </s:if>
        <li id="mms_tunnel" class="tunnel_hidden">
            <ul>
                <li class="b_margin">
                    <label>配置彩信</label>
                </li>
                <li>
                    <label class="tunnel_title">彩信企业代码：</label>
                    <input type="hidden" name="mmTunnel.tunnelRange" id="mms_tunnel_range" value="0"/>
                    <input type="hidden" name="mmTunnel.id" value="<s:property value='mmTunnel.id'/>" />
                    <input maxlength="20" type="text" name="mmTunnel.smsCorpIdent" id="mmtunnelAdd_smsCorpIdent" value="<s:property value='mmTunnel.smsCorpIdent'/>" />
                    <label class="tunnel_title">彩信接入号：</label>
                    <input maxlength="20" type="text" name="mmTunnel.accessNumber" id="mmtunnelAdd_accessNumber" value="<s:property value='mmTunnel.accessNumber'/>" />
                </li>               
                <li>
                    <label class="tunnel_title">彩信业务代码：</label>
                    <input maxlength="20" type="text" name="mmTunnel.serviceId" id="mmtunnelAdd_ServiceId" value="<s:property value='mmTunnel.serviceId'/>" />
                    <label class="tunnel_title">彩信中心IP地址：</label>
                    <input maxlength="20" type="text" name="mmTunnel.gatewayAddr" id="mmtunnelAdd_gatewayAddr" value="<s:property value='mmTunnel.gatewayAddr'/>" />
                </li>
                <li>
                    <label class="tunnel_title">彩信中心端口：</label>
                    <input maxlength="20" type="text" name="mmTunnel.gatewayPort" id="mmtunnelAdd_gatewayPort" value="<s:property value='mmTunnel.gatewayPort'/>" />
                    <label class="tunnel_title">彩信上行监听端口：</label>
                    <input type="hidden" name="mmtopport.id" value="<s:property value='mmtopport.id'/>"/>
                    <input type="hidden" name="mmtopport.name" value="mmtopport"/>
                    <input type="text" name="mmtopport.itemValue" id="mmtopport" maxlength="20" value="<s:property value='mmtopport.itemValue'/>" />
                </li>
                <li>
                    <label class="tunnel_title">彩信登录用户名：</label>
                    <input maxlength="20" type="text" name="mmTunnel.user" id="mmtunnelAdd_user" value="<s:property value='mmTunnel.user'/>" />
                    <label class="tunnel_title">彩信登录密码：</label>
                    <input maxlength="20" type="text" name="mmTunnel.passwd" id="mmtunnelAdd_passwd" value="<s:property value='mmTunnel.passwd'/>" />
                </li>
            </ul>
        </li>
	</ul>
</div>
</form>
</div>