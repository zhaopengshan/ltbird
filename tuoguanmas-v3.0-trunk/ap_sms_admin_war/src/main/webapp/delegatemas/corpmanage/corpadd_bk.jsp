<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form action="./corpManageAction/addCorpAndConfig.action" method="post" id="merchantAdd_addForm">    
    <ul class="gridwrapper" style="width:90%">
        <li>
	        <ul>
	        	<li>
		            <label><span>*</span>企业名称：</label> 
		            <input type="hidden" name="merchant.merchantPin" value="<s:property value='merchant.merchantPin' />" id="merchantAdd_MerchantPin"/>
		            <input size="50" maxlength="20" type="text" name="merchant.name" id="merchantAdd_name" value="<s:property value='merchant.name' />" />
		            <label>企业强制签名： </label>
		            <input type="hidden" name="merchantConfigs[0].id" value="<s:property value='merchantConfigs[0].id'/>"/>
		            <input type="hidden" name="merchantConfigs[0].name" value="sms_sign_content"/>
		            <input type="text" name="merchantConfigs[0].itemValue" id="merchantAdd_sign" size="50" maxlength="20" value="<s:property value='merchantConfigs[0].itemValue'/>" />
	            </li>
	            <li>
		            <label><span>*</span>是否托管：</label>
		            <input type="hidden" name="merchantConfigs[1].id" value="<s:property value='merchantConfigs[1].id'/>"/>
		            <input type="hidden" name="merchantConfigs[1].name" value="isdelegate"/>
		            <select name="merchantConfigs[1].itemValue" style="width: 266px;height: 20px;">
		                <option value="1" <s:if test="merchantConfigs[1].itemValue==1">selected</s:if>>托管</option>
		                <option value="0" <s:if test="merchantConfigs[1].itemValue==0">selected</s:if>>非托管</option>
		            </select>
		            
		            <label><span>*</span>指定节点：</label>
		            <select name="nodeId" style="width: 266px;height: 20px;" <s:if test="nodeTOStatus">onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;"</s:if>>
		                <s:iterator value="mbnNodeTOes" var="nodeTO">
		                    <option value="<s:property value="#nodeTO.id"/>" <s:if test="#nodeTO.isSelected">selected</s:if>><s:property value="#nodeTO.name"/></option>
		                </s:iterator>
		            </select>
	            </li>
	            <li>
					<label>MAS服务器IP地址：</label>
                    <input type="hidden" name="merchantConfigs[2].id" value="<s:property value='merchantConfigs[2].id'/>"/>
                    <input type="hidden" name="merchantConfigs[2].name" value="masserverip"/>
                    <input size="50" maxlength="20" type="text" name="merchantConfigs[2].itemValue" id="merchantAdd_masip" value="<s:property value='merchantConfigs[2].itemValue'/>" />
                    <label>允许的发送速度：</label>
                    <input type="hidden" name="merchantConfigs[3].id" value="<s:property value='merchantConfigs[3].id'/>"/>
                    <input type="hidden" name="merchantConfigs[3].name" value="gatewaylimit"/>
                    <input size="50" maxlength="20" type="text" name="merchantConfigs[3].itemValue" id="merchantAdd_gatewaylimit" value="<s:property value='merchantConfigs[3].itemValue'/>" />                    
	            </li>
	        </ul>
        </li>
        <li> 
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">配置移动</label>
                </li>
                <li>
                    <label>短信企业代码：</label>
                    <input type="hidden" name="tunnel.id" value="<s:property value='tunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="tunnel.smsCorpIdent" id="tunnelAdd_smsCorpIdent" value="<s:property value='tunnel.smsCorpIdent'/>" />
                    <label><span>*</span>短信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="tunnel.accessNumber" id="tunnelAdd_accessNumber" value="<s:property value='tunnel.accessNumber'/>" />
                </li>
                <li>
                    <label>短信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="tunnel.serviceId" id="tunnelAdd_ServiceId" value="<s:property value='tunnel.serviceId'/>" />
                    <label>短信协议版本：</label>
                    <select name="tunnel.protocalVersion"  style="width: 266px;height: 20px;" id="tunnelAdd_protocalVersion">
                        <option value="cmpp2.0" <s:if test="tunnel.protocalVersion=='cmpp2.0'">selected</s:if>>cmpp2.0</option>
                        <option value="cmpp3.0" <s:if test="tunnel.protocalVersion=='cmpp3.0'">selected</s:if>>cmpp3.0</option>
                    </select>
                </li>
                <li>
                    <label>短信网关IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="tunnel.gatewayAddr" id="tunnelAdd_gatewayAddr" value="<s:property value='tunnel.gatewayAddr'/>" />
                    <label><span>*</span>短信网关端口：</label>
                    <input size="50" maxlength="20" type="text" name="tunnel.gatewayPort" id="tunnelAdd_gatewayPort" value="<s:property value='tunnel.gatewayPort'/>" />
                </li>
                <li>
                    <label>短信网关用户名：</label>
                    <input size="50" maxlength="20" type="text" name="tunnel.user" id="tunnelAdd_user" value="<s:property value='tunnel.user'/>" />
                    <label>短信网关密码：</label>
                    <input size="50" maxlength="20" type="text" name="tunnel.passwd" id="tunnelAdd_passwd" value="<s:property value='tunnel.passwd'/>" />
                </li>            
            </ul>            
        </li>
        <li> 
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">配置联通</label>
                </li>
                <li>
                    <label>短信企业代码：</label>
                    <input type="hidden" name="ltTunnel.id" value="<s:property value='ltTunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.smsCorpIdent" id="ltTunnelAdd_smsCorpIdent" value="<s:property value='ltTunnel.smsCorpIdent'/>" /> 
                    <label>短信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.accessNumber" id="ltTunnelAdd_accessNumber" value="<s:property value='ltTunnel.accessNumber'/>" />
                </li>
                <li>
                    <label>短信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.serviceId" id="ltTunnelAdd_ServiceId" value="<s:property value='ltTunnel.serviceId'/>" />
                    <label>短信协议版本：</label>
                    <select name="ltTunnel.protocalVersion"  style="width: 266px;height: 20px;" id="ltTunnelAdd_protocalVersion">
                        <option value="SGIP" <s:if test="ltTunnel.protocalVersion=='SGIP'">selected</s:if>>SGIP</option>
                    </select>
                </li>
                <li>
                    <label>短信网关IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.gatewayAddr" id="ltTunnelAdd_gatewayAddr" value="<s:property value='ltTunnel.gatewayAddr'/>" />
                    <label>短信网关端口：</label>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.gatewayPort" id="ltTunnelAdd_gatewayPort" value="<s:property value='ltTunnel.gatewayPort'/>" />
                </li>
                <li>
                    <label>短信网关用户名：</label>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.user" id="ltTunnelAdd_user" value="<s:property value='ltTunnel.user'/>" />
                    <label>短信网关密码：</label>
                    <input size="50" maxlength="20" type="text" name="ltTunnel.passwd" id="ltTunnelAdd_passwd" value="<s:property value='ltTunnel.passwd'/>" />
                </li>            
            </ul>            
        </li>
        <li> 
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">配置电信</label>
                </li>
                <li>
                    <label>短信企业代码：</label>
                    <input type="hidden" name="dxTunnel.id" value="<s:property value='dxTunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.smsCorpIdent" id="dxTunnelAdd_smsCorpIdent" value="<s:property value='dxTunnel.smsCorpIdent'/>" /> 
                    <label>短信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.accessNumber" id="dxTunnelAdd_accessNumber" value="<s:property value='dxTunnel.accessNumber'/>" />
                </li>
                <li>
                    <label>短信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.serviceId" id="dxTunnelAdd_ServiceId" value="<s:property value='dxTunnel.serviceId'/>" />
                    <label>短信协议版本：</label>
                    <select name="dxTunnel.protocalVersion"  style="width: 266px;height: 20px;" id="dxTunnelAdd_protocalVersion">
                        <option value="SMGP3.0" <s:if test="dxTunnel.protocalVersion=='SMGP3.0'">selected</s:if>>SMGP3.0</option>
                    </select>
                </li>
                <li>
                    <label>短信网关IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.gatewayAddr" id="dxTunnelAdd_gatewayAddr" value="<s:property value='dxTunnel.gatewayAddr'/>" />
                    <label>短信网关端口：</label>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.gatewayPort" id="dxTunnelAdd_gatewayPort" value="<s:property value='dxTunnel.gatewayPort'/>" />
                </li>
                <li>
                    <label>短信网关用户名：</label>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.user" id="dxTunnelAdd_user" value="<s:property value='dxTunnel.user'/>" />
                    <label>短信网关密码：</label>
                    <input size="50" maxlength="20" type="text" name="dxTunnel.passwd" id="dxTunnelAdd_passwd" value="<s:property value='dxTunnel.passwd'/>" />
                </li>
            </ul>            
        </li>
        <li> 
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">配置话机</label>
                </li>
                <li>
                    <label>SIM卡号：</label>
                    <input type="hidden" name="mobileTunnel.id" value="<s:property value='mobileTunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.accessNumber" id="mobiletunnelAdd_accessNumber" value="<s:property value='mobileTunnel.accessNumber'/>" />
                    <label>SIM卡发送限制：</label>
                    <input type="hidden" name="mobileConsume.id" value="<s:property value='mobileConsume.id'/>"/>
                    <input type="hidden" name="mobileConsume.remainNumber" id="mobileconsumeAdd_remainNumber" value="<s:property value='mobileConsume.remainNumber'/>" />
                    <input type="hidden" name="merchantConfigs[4].id" value="<s:property value='merchantConfigs[4].id'/>"/>
                    <input type="hidden" name="merchantConfigs[4].name" value="sms_send_limit"/>
                    <input size="50" maxlength="20" type="text" name="merchantConfigs[4].itemValue" id="merchantAdd_sms_send_limit" value="<s:property value='merchantConfigs[4].itemValue'/>" />
                </li>                
            </ul>            
        </li>
        <li>
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">配置彩信</label>
                </li>
                <li>
                    <label>彩信企业代码：</label>
                    <input type="hidden" name="mmTunnel.id" value="<s:property value='mmTunnel.id'/>" />
                    <input size="50" maxlength="20" type="text" name="mmTunnel.smsCorpIdent" id="mmtunnelAdd_smsCorpIdent" value="<s:property value='mmTunnel.smsCorpIdent'/>" />
                    <label>彩信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="mmTunnel.accessNumber" id="mmtunnelAdd_accessNumber" value="<s:property value='mmTunnel.accessNumber'/>" />
                </li>               
                <li>
                    <label>彩信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="mmTunnel.serviceId" id="mmtunnelAdd_ServiceId" value="<s:property value='mmTunnel.serviceId'/>" />
                    <label>彩信中心IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="mmTunnel.gatewayAddr" id="mmtunnelAdd_gatewayAddr" value="<s:property value='mmTunnel.gatewayAddr'/>" />
                </li>
                <li>
                    <label>彩信中心端口：</label>
                    <input size="50" maxlength="20" type="text" name="mmTunnel.gatewayPort" id="mmtunnelAdd_gatewayPort" value="<s:property value='mmTunnel.gatewayPort'/>" />
                    <label>彩信上行监听端口：</label>
                    <input type="hidden" name="merchantConfigs[5].id" value="<s:property value='merchantConfigs[5].id'/>"/>
                    <input type="hidden" name="merchantConfigs[5].name" value="mmtopport"/>
                    <input type="text" name="merchantConfigs[5].itemValue" id="mmtopport" size="50" maxlength="20" value="<s:property value='merchantConfigs[5].itemValue'/>" />
                </li>
                <li>
                    <label>彩信登录用户名：</label>
                    <input size="50" maxlength="20" type="text" name="mmTunnel.user" id="mmtunnelAdd_user" value="<s:property value='mmTunnel.user'/>" />
                    <label>彩信登录密码：</label>
                    <input size="50" maxlength="20" type="text" name="mmTunnel.passwd" id="mmtunnelAdd_passwd" value="<s:property value='mmTunnel.passwd'/>" />
                </li>
            </ul>
        </li>
        <li class="btn">
            <a href="#" id="merchantAdd_sub">提交</a>
            <a href="#" id="merchantAdd_clear">取消</a>
        </li> 
    </ul>
</form>
<script type="text/javascript">
     var mobilRegular = /^(134|135|136|137|138|139|147|150|151|152|157|158|159|182|187|188)\d{8}$/;            
    var ipRegular = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    $(function() {
        $("#merchantAdd_sub").unbind("click").click(function(){
            if($("#merchantAdd_sms_send_limit").val() == "") {
                $("#merchantAdd_sms_send_limit").val("500");
            }
            $("#mobileconsumeAdd_remainNumber").val($("#merchantAdd_sms_send_limit").val());
            if($("#merchantAdd_MerchantPin").val() != "") {
                $('#merchantAdd_addForm').attr("action", "./corpManageAction/updateCorpAndConfig.action")
            }
            if($("#tunnelAdd_accessNumber").val() == ""){
                alert("请填写短信接入号");
                return;
            }
            if($("#tunnelAdd_gatewayPort").val() == ""){
                alert("请填写短信网关端口");
                return;
            }
            if($("#merchantAdd_name").val() == "") {
                alert("请填写企业名称");
                return;
            }
            /*if($("#merchantAdd_sign").val() == ""){
                alert("请填写企业签名");
                return;
            }*/
            if($("#merchantAdd_masip").val() != "" && !ipRegular.test($("#merchantAdd_masip").val())){
                alert("MAS服务器IP地址格式有误,请检查");
                return;
            }
            if($("#tunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#tunnelAdd_gatewayAddr").val())){
                alert("短信网关IP地址格式有误,请检查");
                return;
            }
            if($("#tunnelAdd_gatewayPort").val() != "" && !isNumber($("#tunnelAdd_gatewayPort").val())){
                alert("短信网关端口格式有误,请检查");
                return;
            }
            if($("#merchantAdd_gatewaylimit").val() != "" && !isNumber($("#merchantAdd_gatewaylimit").val())){
                alert("允许的发送速度格式有误,请检查");
                return;
            }
            if($("#mobiletunnelAdd_accessNumber").val() != "" && !mobilRegular.test($("#mobiletunnelAdd_accessNumber").val())){
                alert("SIM卡号格式有误,请检查");
                return;
            }
            if($("#merchantAdd_sms_send_limit").val() != "" && !isNumber($("#merchantAdd_sms_send_limit").val())){
                alert("SIM卡发送限制格式有误,请检查");
                return;
            }
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
            $('#merchantAdd_addForm').ajaxSubmit(function(data) {                
                var resultInfo = jQuery.parseJSON(data)
                alert(resultInfo.resultInfo);
            });
        })        	
    })
</script>
