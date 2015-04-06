<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="expires" content="0"/>
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
        <script type="text/javascript">
            var dynamic_sms_title_tip="标题不会作为短信内容发出，仅作为查询依据";
            $(function() {
                $("#dynamicsms_sendAtTimeDyna").unbind("click").click(function() {
                    $("#dynamicsms_sendTimeDyna").css({"display" : "inline"});
                });
                $("#dynamicsms_sendNowDyna").unbind("click").click(function() {
                    $("#dynamicsms_sendTimeDyna").css({"display" : "none"});
                });
                $("#dynamicsms_title").unbind("focus").focus(function(){
                    if($(this).val() == dynamic_sms_title_tip){
                        $(this).val("");
                    }
                }).focusout(function(){
                    if($.trim($(this).val()) == ""){
                        $(this).val(dynamic_sms_title_tip);
                    }
                });
                $("#dynamicsms_submit").unbind("click").bind("click", function() {
                    var options = {
                        beforeSubmit : checkDynamicSmsInput, // pre-submit callback 
                        success : sendDynamicSms, // post-submit callback 
                        dataType : 'json'
                    };
                    $("#dynamicsms_form1").ajaxSubmit(options);
                });
            });
		
            function checkDynamicSmsInput(formData, jqForm, options){
                var str = $.trim($('#dynamicsms_addrUpload').val());
                if(str == null || str == ""){
                    alert("请先上传文件");
                    return false;
                }
                var str2 = str.substring(str.indexOf('.'),str.length);
                if(str2.toLocaleLowerCase()!='.xls' && str2.toLocaleLowerCase()!='.xlsx' ){
                    alert('上传文件必须为.xls,.xlsx为后缀的excel文件！');
                    return false;
                } 
                var dynamic_title = $("#dynamicsms_title").val();
                if("" == dynamic_title || dynamic_sms_title_tip == dynamic_title){
                    alert("请输入短信标题");
                    $("#dynamicsms_title").focus();
                    return false;
                }
                if("" == $("#dynamicsms_smsText").val() ){
                    alert("请输入短信内容");
                    $("#dynamicsms_smsText").focus();
                    return false;
                }
            }
		
            function sendDynamicSms(responseText, statusText, xhr, $form){
                if(responseText.resultcode == "success"){
                    alert(responseText.message);
                    var originalUrl = "./sms/smssend/jsp/sms_info.jsp";
                    var tempUrl = "./sms/smssend/jsp/sms_info.jsp";
                    var localUrl = "./smssend/writesms.action?flag=dynamic";
                    var _killId = $("a:[taburl='"+localUrl+"']").attr("tabid");
                    tabpanel.kill(_killId);
                    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
                    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
                }else{
                    alert(responseText.message)
                }
			
            }
        </script>
    </head>
    <body>
        <form action="./smssend/send.action?flag=dynamic" id="dynamicsms_form1" method="post">
            <div  class="gridwrapper" style="width:100%">
                <ul class="dynamicstyle">
                    <li>
                        <label>动态短信使用方法：</label>
                        <span>第1步，编辑保存一个excel文件，</span>
                        <span style="color:red">第一列必须为手机号码</span>
                        <span>，例如：</span>                        
                    </li>
                    <li>
                        <label></label>
                        <img src="./themes/mas3/images/dynamic_sm.png" alt="示例图片" />
                    </li>
                    <li>
                        <label>
                            <span>*</span>
                            上传编辑好的excel： 
                        </label>
                        <span>
                            第2步，上传这个excel文件
                        </span>                        
                    </li>
                    <li>
                        <label></label>
                        <input type="file" name="addrUpload" id="dynamicsms_addrUpload" size="50" maxlength="20"/>
                    </li>
                    <li>
                        <label>
                            <span>*</span>短信标题： 
                        </label>
                        <span>第3步，编辑短信标题</span>                            
                    </li>
                    <li>
                        <label></label>
                        <input type="text" name="title" id="dynamicsms_title" style="width:400px;" value="标题不会作为短信内容发出，仅作为查询依据"/>
                    </li>
                    <li>
                        <label>
                            <span>*</span>短信内容： 
                        </label>
                        <span>第4步，编辑短信内容</span>                           
                    </li>
                    <li style="overflow:hidden; zoom:1;">
                        <span style=" margin-left:140px;float:left;width:40%;">
                        <textarea name="smsText" id="dynamicsms_smsText" style="width:98%;height:140px;"></textarea></span>
                        <span style="float:left; margin-left:10px;width:40%;"><p style="font-size:12px;line-height:20px;padding:0px; margin:0px;">
							编辑短信时，将excel第一行的列名作为动态变量放在两个%之间，系统会自动取得此列的文字，拼成各条不同的短信发送出去<br/>
							在上面的例子中，短信内容可以为：<br/>
							尊敬的%客户姓名%先生/女士，您需要缴纳的费用如下：水费%水费%元，电费%电费%元，燃气费%燃气费%元。请尽快缴纳，谢谢合作。<br/>
							13911112222收到的短信为：<br />
							尊敬的张小龙先生/女士，您需要缴纳的费用如下：水费160元，电费88.4元，燃气费120元。请尽快缴纳，谢谢合作。
						</p></span>
                    </li>
                    <li>
                        <label>企业签名： </label> 
                        <input type="text" id="dynamicsms_entSign" class="input_entSign" name="entSign" readonly="readonly" value="${sessionScope.ent_sms_sign}" />
                    </li>
                    <li style="padding: 0px">
						<label style="height:18px; line-height: 18px;" align="right"></label>
						<span style="height:18px; line-height: 18px;" >
						  <span style="color:#FF0000">*</span>如果用户收到的短信后缀签名与平台不符，请联系客户经理或者平台管理员修改短信后缀签名
						</span>
					</li>
                    <li>
                        <label>发送时间：</label>
                        <input type="radio" id="dynamicsms_sendNowDyna" name="sendType" value="NOW" checked="checked" />立即发送&nbsp;&nbsp; 
                        <input type="radio" id="dynamicsms_sendAtTimeDyna" name="sendType" value="TIMER" />定时发送
                        <input type="text" name="sendTime" id="dynamicsms_sendTimeDyna" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d H:mm:ss'});" style="display: none;" />
                    </li>
                    <li style="display: none">
                        <label>通道选择：</label>
                        <ul>
                            <li>
                                移动
                                
                              <select id="ydTunnel" name="ydTunnel" style="width:220px;height: 20px;">
						<c:set var="ydTunnelNumber" value="-1000"></c:set>
						<c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
								<c:if test="${(tunnel.classify eq 1) or (tunnel.classify eq 2) or (tunnel.classify eq 7)}">
								<c:if test="${(ydTunnelNumber eq '-1000')}">
									<c:set var="ydTunnelNumber" value="${tunnel.smsNumber}"></c:set>
								</c:if>
								<option value="${tunnel.id}" <c:if test="${(tunnel.id eq ydTunnel)}"><c:set var="ydTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }
								</option></c:if>
						</c:forEach>
					</select>
                                通道可用余量<span id="dynamicsms_ydTip">${ydTunnelNumber}</span>条
                            </li>
                            <li>
                                联通
                                <select id="dynamicsms_ltTunnel" name="ltTunnel" style="width:220px;height: 20px;">
                                    <c:set var="ltTunnelNumber" value="-1000"></c:set>
                                    <c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
                                        <c:if test="${(tunnel.classify eq 3) or (tunnel.classify eq 4) or (tunnel.classify eq 7)}">
                                            <c:if test="${(ltTunnelNumber eq '-1000')}">
                                                <c:set var="ltTunnelNumber" value="${tunnel.smsNumber}"></c:set>
                                            </c:if>
                                            <option value="${tunnel.id}"  <c:if test="${(tunnel.id eq ltTunnel)}"><c:set var="ltTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                通道可用余量<span id="dynamicsms_ltTip">${ltTunnelNumber}</span>条
                            </li>
                            <li>
                                电信
                                <select id="dynamicsms_dxTunnel"  name="dxTunnel" style="width:220px;height: 20px;">
                                    <c:set var="dxTunnelNumber" value="-1000"></c:set>
                                    <c:forEach items="${tunnelList}" var="tunnel" varStatus="st">
                                        <c:if test="${(tunnel.classify eq 5) or (tunnel.classify eq 6) or (tunnel.classify eq 7)}">
                                            <c:if test="${(dxTunnelNumber eq '-1000')}">
                                                <c:set var="dxTunnelNumber" value="${tunnel.smsNumber}"></c:set>
                                            </c:if>
                                            <option value="${tunnel.id}" <c:if test="${(tunnel.id eq dxTunnel)}"><c:set var="dxTunnelNumber" value="${tunnel.smsNumber }"></c:set>selected="selected"</c:if> >${tunnel.name }</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                通道可用余量<span id="dynamicsms_dxTip">${dxTunnelNumber}</span>条
                            </li>
                            <li>注：以上3条通道有任意一条可用余量不足，则整批短信被取消发送，并自动存入草稿箱</li>
                        </ul>
                    </li>
                    <li class="btn" style="padding-top:30px; padding-bottom:15px;">
                        <label></label>
                        <a href="#" id="dynamicsms_submit">发送</a> 
                        <a href="#" id="dynamicsms_clear">取消</a>
                    </li>
                </ul>
            </div>
        </form>
    </body>

</html>
