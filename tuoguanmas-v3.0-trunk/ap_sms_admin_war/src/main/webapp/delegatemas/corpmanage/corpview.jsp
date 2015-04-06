<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<s:if test="merchant==null || merchant.merchantPin==null">
    <label>请选择企业查看其相关企业信息</label>
</s:if>
<s:else>
    <form action="./roleAction/addrole.action" method="post" id="roleAdd_addForm">
        <input type="hidden" name="portalRole.id" id="roleAdd_roleId" value="${roleVO.id }" />
        <ul class="gridwrapper">
            <li>
                <label>企业名称：</label> 
                <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;">
                    <s:property value='merchant.name' />
                </label>
                <label>企业强制签名：</label>
                <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='merchantConfigs[0].itemValue'/></label>            
            </li>
            <li>
                <label>是否托管：</label>
                <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:if test="merchantConfigs[1].itemValue=1">托管</s:if><s:else>非托管</s:else></label>
                <label>所在节点：</label>
                <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;">
	                <s:iterator value="mbnNodeTOes" var="nodeTO">
	                    <s:if test="#nodeTO.isSelected"><s:property value="#nodeTO.name"/></s:if>
	                </s:iterator>
                </label>
            </li>
            <li>
                <label>MAS服务器IP地址：</label>
                <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='merchantConfigs[2].itemValue'/></label>
                <label>允许的发送速度：</label>
                <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='merchantConfigs[3].itemValue'/></label>
            </li>
            <li>
                <ul>
                    <li>
                        <label style="font-weight: bold;font-size: 18px;">配置移动</label>
                    </li>
                    <li>
                        <label>短信企业代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.smsCorpIdent'/></label>
                         <label>短信接入号：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.accessNumber'/></label>
                    </li>
                    <li>
                        <label>短信业务代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.serviceId'/></label>
                        <label>短信协议版本：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.protocalVersion'/></label>
                    </li>
                    <li>
                        <label>短信网关IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.gatewayAddr'/></label>
                        <label>短信网关端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.gatewayPort'/></label>
                    </li>
                    <li>
                        <label>短信网关用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.user'/></label>
                       <label>短信网关密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='tunnel.passwd'/></label>
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
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.smsCorpIdent'/></label>
                         <label>短信接入号：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.accessNumber'/></label>
                    </li>
                    <li>
                        <label>短信业务代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.serviceId'/></label>
                        <label>短信协议版本：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.protocalVersion'/></label>
                    </li>
                    <li>
                        <label>短信网关IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.gatewayAddr'/></label>
                        <label>短信网关端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.gatewayPort'/></label>
                    </li>
                    <li>
                        <label>短信网关用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.user'/></label>
                       <label>短信网关密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='ltTunnel.passwd'/></label>
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
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.smsCorpIdent'/></label>
                         <label>短信接入号：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.accessNumber'/></label>
                    </li>
                    <li>
                        <label>短信业务代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.serviceId'/></label>
                        <label>短信协议版本：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.protocalVersion'/></label>
                    </li>
                    <li>
                        <label>短信网关IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.gatewayAddr'/></label>
                        <label>短信网关端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.gatewayPort'/></label>
                    </li>
                    <li>
                        <label>短信网关用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.user'/></label>
                       <label>短信网关密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='dxTunnel.passwd'/></label>
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
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.accessNumber'/></label>
                        <label>SIM卡发送限制：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='merchantConfigs[4].itemValue'/></label>
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
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.smsCorpIdent'/></label> 
                         <label>彩信接入号：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.accessNumber'/></label>
                    </li>               
                    <li>
                       <label>彩信业务代码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.serviceId'/></label>
                        <label>彩信中心IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.gatewayAddr'/></label>
                    </li>
                    <li>
                        <label>彩信中心端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.gatewayPort'/></label>
                        <label>彩信上行监听端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='merchantConfigs[5].itemValue'/></label>
                    </li>
                    <li>
                        <label>彩信登录用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.user'/></label>
                        <label>彩信登录密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mmTunnel.passwd'/></label>
                    </li>
                </ul>
            </li>
        </ul>
    </form>
</s:else>