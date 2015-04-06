<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form action="./corpManageAction/addCorpFordAndConfig.action" method="post" id="merchantAdd_addForm">    
    <ul class="gridwrapper" style="width:90%">
        <li>
            <label><span>*</span>企业名称：</label> 
            <input type="hidden" name="merchant.merchantPin" value="<s:property value='merchant.merchantPin' />" id="merchantAdd_MerchantPin"/>
            <input size="50" maxlength="20" type="text" name="merchant.name" id="merchantAdd_name" value="<s:property value='merchant.name' />" />
        </li>        
        <li> 
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">移动短信参数配置</label>
                </li>
                <li>
                    <label><span>*</span>短信企业代码：</label>
                    <input type="hidden" name="mobileTunnel.id" value="<s:property value='mobileTunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.smsCorpIdent" id="mobileTunnelAdd_smsCorpIdent" value="<s:property value='mobileTunnel.smsCorpIdent'/>" /> 
                    <label><span>*</span>短信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.accessNumber" id="mobileTunnelAdd_accessNumber" value="<s:property value='mobileTunnel.accessNumber'/>" />
                </li>
                <li>                    
                    <label><span>*</span>短信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.serviceId" id="mobileTunnelAdd_ServiceId" value="<s:property value='mobileTunnel.serviceId'/>" />
                    <label><span>*</span>短信网关IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.gatewayAddr" id="mobileTunnelAdd_gatewayAddr" value="<s:property value='mobileTunnel.gatewayAddr'/>" />
                </li>
                <li>                    
                    <label><span>*</span>短信网关端口：</label>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.gatewayPort" id="mobileTunnelAdd_gatewayPort" value="<s:property value='mobileTunnel.gatewayPort'/>" />
                    <label><span>*</span>短信协议版本：</label>
                    <select name="mobileTunnel.protocalVersion"  style="width: 266px;height: 20px;" id="mobileTunnelAdd_protocalVersion">
                        <option value="cmpp2.0" <s:if test="mobileTunnel.protocalVersion=='cmpp2.0'">selected</s:if>>cmpp2.0</option>
                        <option value="cmpp3.0" <s:if test="mobileTunnel.protocalVersion=='cmpp3.0'">selected</s:if>>cmpp3.0</option>
                    </select>
                </li>
                <li>                    
                    <label><span>*</span>短信网关用户名：</label>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.user" id="mobileTunnelAdd_user" value="<s:property value='mobileTunnel.user'/>" />
                    <label><span>*</span>短信网关密码：</label>
                    <input size="50" maxlength="20" type="text" name="mobileTunnel.passwd" id="mobileTunnelAdd_passwd" value="<s:property value='mobileTunnel.passwd'/>" />
                </li>                                     
            </ul>            
        </li>
        <li>
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">联通短信参数配置</label>
                </li>
                <li>
                    <label><span>*</span>短信企业代码：</label>
                    <input type="hidden" name="unicomTunnel.id" value="<s:property value='unicomTunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.smsCorpIdent" id="unicomTunnelAdd_smsCorpIdent" value="<s:property value='unicomTunnel.smsCorpIdent'/>" /> 
                    <label><span>*</span>短信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.accessNumber" id="unicomTunnelAdd_accessNumber" value="<s:property value='unicomTunnel.accessNumber'/>" />
                </li>
                <li>                    
                    <label><span>*</span>短信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.serviceId" id="unicomTunnelAdd_ServiceId" value="<s:property value='unicomTunnel.serviceId'/>" />
                    <label><span>*</span>短信网关IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.gatewayAddr" id="unicomTunnelAdd_gatewayAddr" value="<s:property value='unicomTunnel.gatewayAddr'/>" />
                </li>
                <li>                    
                    <label><span>*</span>短信网关端口：</label>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.gatewayPort" id="unicomTunnelAdd_gatewayPort" value="<s:property value='unicomTunnel.gatewayPort'/>" />
                    <label><span>*</span>短信协议版本：</label>
                    <select name="unicomTunnel.protocalVersion"  style="width: 266px;height: 20px;" id="unicomTunnelAdd_protocalVersion">
                        <option value="cmpp2.0" <s:if test="unicomTunnel.protocalVersion=='cmpp2.0'">selected</s:if>>cmpp2.0</option>
                        <option value="cmpp3.0" <s:if test="unicomTunnel.protocalVersion=='cmpp3.0'">selected</s:if>>cmpp3.0</option>
                    </select>
                </li>
                <li>                    
                    <label><span>*</span>短信网关用户名：</label>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.user" id="unicomTunnelAdd_user" value="<s:property value='unicomTunnel.user'/>" />
                    <label>短信网关密码：</label>
                    <input size="50" maxlength="20" type="text" name="unicomTunnel.passwd" id="unicomTunnelAdd_passwd" value="<s:property value='unicomTunnel.passwd'/>" />
                </li>
            </ul>
        </li>
        <li>
            <ul>
                <li>
                    <label style="font-weight: bold;font-size: 18px;">电信短信参数配置</label>
                </li>
                <li>
                    <label><span>*</span>短信企业代码：</label>
                    <input type="hidden" name="telecomTunnel.id" value="<s:property value='telecomTunnel.id'/>"/>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.smsCorpIdent" id="telecomTunnelAdd_smsCorpIdent" value="<s:property value='telecomTunnel.smsCorpIdent'/>" /> 
                    <label><span>*</span>短信接入号：</label>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.accessNumber" id="telecomTunnelAdd_accessNumber" value="<s:property value='telecomTunnel.accessNumber'/>" />
                </li>
                <li>                    
                    <label><span>*</span>短信业务代码：</label>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.serviceId" id="telecomTunnelAdd_ServiceId" value="<s:property value='telecomTunnel.serviceId'/>" />
                    <label><span>*</span>短信网关IP地址：</label>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.gatewayAddr" id="telecomTunnelAdd_gatewayAddr" value="<s:property value='telecomTunnel.gatewayAddr'/>" />
                </li>
                <li>                    
                    <label><span>*</span>短信网关端口：</label>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.gatewayPort" id="telecomTunnelAdd_gatewayPort" value="<s:property value='telecomTunnel.gatewayPort'/>" />
                    <label><span>*</span>短信协议版本：</label>
                    <select name="telecomTunnel.protocalVersion"  style="width: 266px;height: 20px;" id="telecomTunnelAdd_protocalVersion">
                        <option value="cmpp2.0" <s:if test="telecomTunnel.protocalVersion=='cmpp2.0'">selected</s:if>>cmpp2.0</option>
                        <option value="cmpp3.0" <s:if test="telecomTunnel.protocalVersion=='cmpp3.0'">selected</s:if>>cmpp3.0</option>
                    </select>
                </li>
                <li>                    
                    <label><span>*</span>短信网关用户名：</label>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.user" id="telecomTunnelAdd_user" value="<s:property value='telecomTunnel.user'/>" />
                    <label><span>*</span>短信网关密码：</label>
                    <input size="50" maxlength="20" type="text" name="telecomTunnel.passwd" id="telecomTunnelAdd_passwd" value="<s:property value='telecomTunnel.passwd'/>" />
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
            if($("#merchantAdd_MerchantPin").val() != "") {
                $('#merchantAdd_addForm').attr("action", "./corpManageAction/updateCorpFordAndConfig.action")
            }
            if($("#merchantAdd_name").val() == ""){
                alert("请填写企业名称");
                return;
            }
            if($("#mobileTunnelAdd_smsCorpIdent").val() == ""){
                alert("请填写短信企业代码");
                return;
            }
            if($("#mobileTunnelAdd_accessNumber").val() == ""){
                alert("请填写短信接入号");
                return;
            }
            if($("#mobileTunnelAdd_ServiceId").val() == ""){
                alert("请填写短信业务代码");
                return;
            }
            if($("#mobileTunnelAdd_smsCorpIdent").val() == ""){
                alert("请填写短信企业代码");
                return;
            }
            if($("#mobileTunnelAdd_gatewayAddr").val() == ""){
                alert("请填写短信网关ip地址");
                return;
            }
            if($("#mobileTunnelAdd_gatewayPort").val() == ""){
                alert("请填写短信网关端口");
                return;
            }
            if($("#mobileTunnelAdd_user").val() == ""){
                alert("请填写短信网关用户名");
                return;
            }
            if($("#mobileTunnelAdd_passwd").val() == ""){
                alert("请填写短信网关密码");
                return;
            }   
            if($("#mobileTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#mobileTunnelAdd_gatewayAddr").val())){
                alert("短信网关IP地址格式有误,请检查");
                return;
            }
            if($("#mobileTunnelAdd_gatewayPort").val() != "" && !isNumber($("#mobileTunnelAdd_gatewayPort").val())){
                alert("短信网关端口格式有误,请检查");
                return;
            }
            
            if($("#unicomTunnelAdd_smsCorpIdent").val() == ""){
                alert("请填写短信企业代码");
                return;
            }
            if($("#unicomTunnelAdd_ServiceId").val() == ""){
                alert("请填写短信业务代码");
                return;
            }
            if($("#unicomTunnelAdd_accessNumber").val() == ""){
                alert("请填写短信接入号");
                return;
            }
            if($("#unicomTunnelAdd_smsCorpIdent").val() == ""){
                alert("请填写短信企业代码");
                return;
            }
            if($("#unicomTunnelAdd_gatewayAddr").val() == ""){
                alert("请填写短信网关ip地址");
                return;
            }
            if($("#unicomTunnelAdd_gatewayPort").val() == ""){
                alert("请填写短信网关端口");
                return;
            }
            if($("#unicomTunnelAdd_user").val() == ""){
                alert("请填写短信网关用户名");
                return;
            }
            if($("#unicomTunnelAdd_passwd").val() == ""){
                alert("请填写短信网关密码");
                return;
            }   
            if($("#unicomTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#unicomTunnelAdd_gatewayAddr").val())){
                alert("短信网关IP地址格式有误,请检查");
                return;
            }
            if($("#unicomTunnelAdd_gatewayPort").val() != "" && !isNumber($("#unicomTunnelAdd_gatewayPort").val())){
                alert("短信网关端口格式有误,请检查");
                return;
            }
            
            if($("#teleTunnelAdd_smsCorpIdent").val() == ""){
                alert("请填写短信企业代码");
                return;
            }
            if($("#telecomTunnelAdd_accessNumber").val() == ""){
                alert("请填写短信接入号");
                return;
            }
            if($("#telecomTunnelAdd_ServiceId").val() == ""){
                alert("请填写短信业务代码");
                return;
            }
            if($("#telecomTunnelAdd_smsCorpIdent").val() == ""){
                alert("请填写短信企业代码");
                return;
            }
            if($("#telecomTunnelAdd_gatewayAddr").val() == ""){
                alert("请填写短信网关ip地址");
                return;
            }
            if($("#telecomTunnelAdd_gatewayPort").val() == ""){
                alert("请填写短信网关端口");
                return;
            }
            if($("#telecomTunnelAdd_user").val() == ""){
                alert("请填写短信网关用户名");
                return;
            }
            if($("#telecomTunnelAdd_passwd").val() == ""){
                alert("请填写短信网关密码");
                return;
            }   
            if($("#telecomTunnelAdd_gatewayAddr").val() != "" && !ipRegular.test($("#telecomTunnelAdd_gatewayAddr").val())){
                alert("短信网关IP地址格式有误,请检查");
                return;
            }
            if($("#telecomTunnelAdd_gatewayPort").val() != "" && !isNumber($("#telecomTunnelAdd_gatewayPort").val())){
                alert("短信网关端口格式有误,请检查");
                return;
            }
            $('#merchantAdd_addForm').ajaxSubmit(function(data) {                
                var resultInfo = jQuery.parseJSON(data)
                alert(resultInfo.resultInfo);
            });
        })        	
    })
</script>
