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
            </li>          
            <li>
                <ul>
                    <li>
                        <label style="font-weight: bold;font-size: 18px;">移动短信参数配置</label>
                    </li>
                    <li>
                        <label>短信企业代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.smsCorpIdent'/></label>
                        <label>短信接入号：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.accessNumber'/></label>
                    </li>
                    <li>                        
                        <label>短信业务代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.serviceId'/></label>
                        <label>短信网关IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.gatewayAddr'/></label>
                    </li>
                    <li>                        
                        <label>短信网关端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.gatewayPort'/></label>
                        <label>短信协议版本：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.protocalVersion'/></label>
                    </li>
                    <li>                        
                        <label>短信网关用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.user'/></label>
                        <label>短信网关密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='mobileTunnel.passwd'/></label>
                    </li>                            
                </ul>                
            </li>    
            <li>
                <ul>
                    <li>
                        <label style="font-weight: bold;font-size: 18px;">联通短信参数配置</label>
                    </li>
                    <li>
                        <label>短信企业代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.smsCorpIdent'/></label>
                        <label>短信接入号：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.accessNumber'/></label>
                    </li>
                    <li>                        
                        <label>短信业务代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.serviceId'/></label>
                        <label>短信网关IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.gatewayAddr'/></label>
                    </li>
                    <li>                        
                        <label>短信网关端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.gatewayPort'/></label>
                        <label>短信协议版本：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.protocalVersion'/></label>
                    </li>
                    <li>                        
                        <label>短信网关用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.user'/></label>
                        <label>短信网关密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='unicomTunnel.passwd'/></label>
                    </li>   
                </ul>
            </li>
            <li>
                <ul>
                    <li>
                        <label style="font-weight: bold;font-size: 18px;">电信短信参数配置</label>
                    </li>
                    <li>
                        <label>短信企业代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.smsCorpIdent'/></label>
                        <label>短信接入号：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.accessNumber'/></label>
                    </li>
                    <li>                        
                        <label>短信业务代码：</label>
                        <label  style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.serviceId'/></label>
                        <label>短信网关IP地址：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.gatewayAddr'/></label>
                    </li>
                    <li>                        
                        <label>短信网关端口：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.gatewayPort'/></label>
                        <label>短信协议版本：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.protocalVersion'/></label>
                    </li>
                    <li>                        
                        <label>短信网关用户名：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.user'/></label>
                        <label>短信网关密码：</label>
                        <label style="display:-moz-inline-box;display:inline-block;width: 120px;text-align: left;"><s:property value='telecomTunnel.passwd'/></label>
                    </li>   
                </ul>
            </li>
        </ul>
    </form>
</s:else>