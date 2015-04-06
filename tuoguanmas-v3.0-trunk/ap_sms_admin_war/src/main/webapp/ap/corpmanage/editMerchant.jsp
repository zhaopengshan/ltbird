<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
    </head>
<script type="text/javascript">
var str;
var reg;
$(function(){
	if('-1' !='${mbnMerchantVipVO.merchantPin }'){
		$("#account").attr("readOnly","readOnly");
		$("#operateCorp").html("编辑");
	}else{
		$("#operateCorp").html("填写");
	}
	$("#name").unbind("keyup","click").bind("keyup",function(){
		str=$.trim($("#name").val());
		if(str==null||str==""){
			$("#nameMsg").html("企业名称不能为空！");
		}else{
			$("#nameMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（填写企业全称）"){
			$(this).val("");
		}
	});
	$("#account").unbind("keyup","click").bind("keyup",function(){
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#account").val());
		if(str==""||str==null){
			$("#accountMsg").html("企业管理员账号不能为空!");
		}else if(!reg.test(str)){
			$("#accountMsg").html("企业管理员账号必须由字母、数字、下划线组成！");
		}else if("administrator" == str.toLocaleLowerCase()){
			$("#accountMsg").html("企业管理员账号不能为administrator大小写形式！");
		}else if(str.length<6||str.length>20){
			$("#accountMsg").html("企业管理员密码必须在6到20位之间！");
		}else{
			$("#accountMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（6-20位，字母/数字/下划线组成）"){
			$(this).val("");
		}
	});
	$("#pwd").unbind("keyup","click").bind("keyup",function(){
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#pwd").val());
		if(str==""||str==null){
			$("#pwdMsg").html("企业管理员密码不能为空!");
		}else if(!reg.test(str)){
			$("#pwdMsg").html("企业管理员密码必须由字母、数字、下划线组成！");
		}else if(str.length<6||str.length>20){
			$("#pwdMsg").html("企业管理员密码必须在6到20位之间！");
		}else{
			$("#pwdMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（6-20位，字母/数字/下划线组成）"){
			$(this).val("");
		}
	});
	$("#mobile").unbind("keyup","click").bind("keyup",function(){
		reg=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		str=$.trim($("#mobile").val());
		if(str==""||str==null){
			$("#mobileMsg").html("企业管理员手机号不能为空!");
		}else if(!reg.test(str)){
			$("#mobileMsg").html("手机号码格式不符合要求，请重新输入!");
		}else{
			$("#mobileMsg").html("");
		}
	}).click(function(){
		if($(this).val()=="（11位手机号码）"){
			$(this).val("");
		}
	});
});
function validateParas(){
		str=$.trim($("#name").val());
		if(str==null||str==""){
			$("#nameMsg").html("企业名称不能为空！");
			return false;
		}
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#account").val());
		if(str==""||str==null){
			$("#accountMsg").html("企业管理员账号不能为空!");
			return false;
		}else if(!reg.test(str)){
			$("#accountMsg").html("企业管理员账号必须由字母、数字、下划线组成！");
			return false;
		}else if(str.length<6||str.length>20){
			$("#accountMsg").html("企业管理员账号必须在6到20位之间！");
		}
		else if("administrator" == str.toLocaleLowerCase()){
			$("#accountMsg").html("企业管理员账号不能为administrator大小写形式！");
			return false;
		}
		reg=/^[a-zA-Z_0-9]+$/;
		str=$.trim($("#pwd").val());
		if(str==""||str==null){
			$("#pwdMsg").html("企业管理员密码不能为空!");
			return false;
		}else if(!reg.test(str)){
			$("#pwdMsg").html("企业管理员密码必须由字母、数字、下划线组成！");
			return false;
		}else if(str.length<6||str.length>20){
			$("#pwdMsg").html("企业管理员密码必须在6到20位之间！");
		}
		reg=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		str=$.trim($("#mobile").val());
		if(str==""||str==null){
			$("#mobileMsg").html("企业管理员手机号不能为空！");
			return false;
		}else if(!reg.test(str)){
			$("#mobileMsg").html("手机号码格式不符合要求，请重新输入！");
			return false;
		}
}
function sub(){
		var tempUrl="";
		var pin=$("#openaccountPin").val();
		if(pin==null || pin==""||pin==-1){
			tempUrl="./openAccountAction/createMerchant.action";
		}else{
			tempUrl="./openAccountAction/updateMerchant.action";
		}
        var insertUrl=tempUrl;
		var msg = $("#regCorp").serialize();
			$.ajax({
			type: "POST",
			beforeSend:validateParas(),
			url: insertUrl,
			data: msg,
            dataType:  "json",
			success: 
				function(data){
					var entityMap=data;
					if(entityMap.flag){
						$("#openaccountPin").val(entityMap.merchantPin);
						$("#account").attr("readOnly","readOnly");
						$("#operateCorp").html("编辑");
					}else{
						$("#operateCorp").html("填写");
					}
					/*$("#openaccountPin").val(entityMap.merchantPin);
					if('-1' !=$("#openaccountPin").val()){
						$("#account").attr("readOnly","readOnly");
						$("#operateCorp").html("编辑");
					}else{
						$("#operateCorp").html("填写");
					}
					*/
					getPageDataEditMerchant()
                    alert(entityMap.resultMsg);
				}
			});
}
function backToCorpmanage(){
	var originalUrl = "./corpManageAction/forwardCorpList.action";
    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
    try{
        tabpanel.kill(killId);
    }catch(e){
    }
   $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").click();
}
</script>
<body>
	<div>
        <div class="config">
        	<form id="regCorp">
        	<input type="hidden" id="openaccountPin" name="mbnMerchantVip.merchantPin" value="${mbnMerchantVipVO.merchantPin }"/>
        	<ul>
        		<li class="btn">
        			<input type="button" onclick="backToCorpmanage();" value="返回企业列表"/>
        		</li>
        		<li>
        			<span id="operateCorp"></span>
        			<span>企业基本信息</span>
        		</li>
       			<li>
       				<label class="rnameex">
       					<span class="needtip">*</span>
       					<span>企业名称（全称）</span>
       				</label>
            		<input  id="name" type="text" name="mbnMerchantVip.name" value="${mbnMerchantVipVO.name }" />
		       		<label class="rnameex">
		       			<span class="needtip">*</span>
		       			<span>企业管理员手机号</span>
		       		</label>
            		<input id="mobile" type="text" name="userVO.mobile" value="${mbnMerchantVipVO.user.mobile}" />
            		   
				</li>
            	<li>
            		<span id="nameMsg" class="needtipresult"></span>
            		<span id="mobileMsg" class="needtipresult"></span>   
            	</li>
	       		<li>
	       			<label class="rnameex">
	       				<span class="needtip">*</span>
	       				<span>企业管员理账号</span>
	       			</label>
            		<input id="account" type="text" name="userVO.account" value="${mbnMerchantVipVO.user.account }"  />
		       		<label class="rnameex">
		       			<span class="needtip">*</span>
		       			<span>企业管员理密码</span>
		       		</label>
            		<input id="pwd" type="text" name="userVO.password" value="${mbnMerchantVipVO.user.password }" />
		       	</li>
				<li>
					<span id="accountMsg" class="needtipresult"></span>
					<span id="pwdMsg" class="needtipresult"  ></span>
				</li>
				<li class="btn">
        			<input type="button" onclick="sub();" value="提交"/>
				</li>
			</ul>
			</form>	
        </div>
        
        <hr>
			<ul>
				<li>
					<b>为企业通道充值</b>
				</li>
			</ul>
			<div class="config">
			<ul>
				<li>
					<input id="editMerchant_tunnelName" value="输入通道名称..." name="title" type="text"/>
					<select  id="editMerchant_tunnelState" >
						<option selected="selected" value="-1">选择通道状态</option>
						<option value="1">可用</option>
						<option value="0">关闭</option>
					</select>
					<select id="editMerchant_tunnelAttribute">
						<option selected="selected" value="-1">选择通道属性</option>
						<option value="2">第三方通道</option>
						<option value="1">直连通道</option>
					</select>
					<select id="editMerchant_tunnelClassify">
						<option selected="selected" value="-1">选择通道类型</option>
						<option value="1">本省移动</option>
						<option value="2">移动</option>
						<option value="3">本省联通</option>
						<option value="4">联通</option>
						<option value="5">本省电信</option>
						<option value="6">电信</option>
						<option value="7">全网</option>
					</select>
					<select id="editMerchant_tunnelProvince">
						<option value="-1">选择省份</option>
						<s:iterator id="province" value="#request.provinceList">
							<option value="${province.coding}">${province.name}</option>
						</s:iterator>
					</select>
					<input type="image" id="editMerchant_search" src="./themes/mas3admin/images/address/address_search.gif" alt="查询" title="查询"/>
				</li>
			</ul>
		</div>
        <div class="gridfullscreen"  style="overflow-y: auto;overflow-x: hidden; height: 185px;">
            <table id="edit_merchantGrid" >
                <thead>
                    <tr>
                        <th class="tabletrhead">通道名称</th>
                        <th class="tabletrhead">通道说明</th>
                        <th class="tabletrhead">通道状态</th>
                        <th class="tabletrhead">通道类型</th>
                        <th class="tabletrhead">通道属性</th>
                        <th class="tabletrhead">系统总余量（条）</th>
                        <th class="tabletrhead">企业当前余量（条）</th>
                        <th class="tabletrhead">为企业充值（条）</th>
                    </tr>
                    <tr class="tabletrboby" style="display:none" id="edit_merchantrowtemplate">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>                    
                </thead>
                <tbody id="edit_merchantdatalist">

                </tbody>
            </table>
            <div id="edit_merchantPage"></div>
        </div>
   	</div>	
   		 <!-- 用于更新的div -->
	<div id="corpRechargeDialogLoad"></div>
	<!-- 用于消息提示的div -->
   		
</body>
<script type="text/javascript">
var editMerchant_tunnelName_word="输入通道名称...";
 $(function(){
		var reg;
		 $("#editMerchant_tunnelNumber").unbind("keyup").bind("keyup",function(){
			 $("#editMerchant_tunnelNumberMsg").html("");
			reg=/^[0-9]+$/;
			str=$.trim($("#editMerchant_tunnelNumber").val());
			if(str==""||str==null){
					$("#editMerchant_tunnelNumberMsg").html("输入大于等于0的整数");
			}else{
				if(!reg.test(str)){
					$("#editMerchant_tunnelNumberMsg").html("输入大于等于0的整数");
				}else if(parseInt(str)<0){
					$("#editMerchant_tunnelNumberMsg").html("输入大于等于0的整数");
				}
			}
		});
		 $("#editMerchant_tunnelPrice").unbind("keyup").bind("keyup",function(){
			 $("#editMerchant_tunnelPriceMsg").html("");
			reg=/^[0-9]+(\.[0-9]+)*$/;
			str=$.trim($("#editMerchant_tunnelPrice").val());
			if(str==null||str==""){
				
			}else if(str!=null){
				if(!reg.test(str)){
					$("#editMerchant_tunnelPriceMsg").html("只填金额数，不填正负");
				}else if(parseInt(str)<0){
					$("#editMerchant_tunnelPriceMsg").html("只填金额数，不填正负");
				}
			}
		}); 
	});
		function validate(){
		reg=/^[0-9]+$/;
			str=$.trim($("#editMerchant_tunnelNumber").val());
			if(str==""||str==null){
				alert("输入大于等于0的整数");
				return false;
			}else{
				if(!reg.test(str)){
					alert("输入大于等于0的整数");
					return false;
				}else if(parseInt(str)<0){
					alert("输入大于等于0的整数");
					return false;
				}
			}
			reg=/^[0-9]+(\.[0-9]+)*$/;
			str=$.trim($("#editMerchant_tunnelPrice").val());
			if(str==""||str==null){
				$("#editMerchant_tunnelPrice").val("0");
			}else{
				if(!reg.test(str)){
					alert("只填金额数，不填正负");
					return false;
				}else if(parseInt(str)<0){
					alert("只填金额数，不填正负");
					return false;
				}
			}
			if($("#openaccountPin").val()=="-1"){
				alert("请先注册");
				return false;
			}
			return true;
	}   
	
	//列表开始
    $(document).ready(function(){
    	$("#editMerchant_tunnelName").focus(function(){
            if($("#editMerchant_tunnelName").val() == editMerchant_tunnelName_word)
                $("#editMerchant_tunnelName").val("");
        }).focusout(function() {
            if($("#editMerchant_tunnelName").val() == ""){
                $("#editMerchant_tunnelName").val(editMerchant_tunnelName_word);
            }
        });
        getPageDataEditMerchant();
        $("#editMerchant_search").unbind("click").click(function(){
            getPageDataEditMerchant();
        }); 
    });
    function getPageDataEditMerchant(){
    	var name=$("#editMerchant_tunnelName").val();
    	var state=$("#editMerchant_tunnelState").val();
    	var attribute=$("#editMerchant_tunnelAttribute").val();
    	var classify=$("#editMerchant_tunnelClassify").val();
    	var province=$("#editMerchant_tunnelProvince").val();
    	var merchantPin=$("#openaccountPin").val();
    	if(name==editMerchant_tunnelName_word){
			name="";
		}
    	if(merchantPin==null || merchantPin==""||merchantPin==-1){
    		merchantPin='-1';
    	}
        $.ajax({
            url: "./corpManageAction/queryAllTunnel.action",
            type:"post",
            data:{
            	"page":1,
            	"rows":2000,
            	"smsMbnTunnelConsumerVO.name":name,
				"smsMbnTunnelConsumerVO.state":state,
				"smsMbnTunnelConsumerVO.attribute":attribute,
				"smsMbnTunnelConsumerVO.classify":classify,
				"smsMbnTunnelConsumerVO.province":province,
				"mbnMerchantTunnelRelation.merchantPin":merchantPin
            },
            dataType: 'json',
            success:function(data){
                refreshListEditMerchant(data);
            },
            error:function(data){
            }
        });
    }
    function refreshListEditMerchant(data){        
        //设置表格数据
        $("#edit_merchantdatalist tr").remove();
        for(var i=0;data&&data.rows&&i<data.rows.length;i++){
            addRowEditMerchant(data.rows[i]);
        }
    } 
    function addRowEditMerchant(data){
        //使用jquery拷贝方式生成行数据
        $("#edit_merchantrowtemplate").clone(true).appendTo("#edit_merchantdatalist");
        $("#edit_merchantdatalist tr:last").attr("id",data.id).show();
        $("#edit_merchantdatalist tr:last td").eq(0).html(data.name);
        $("#edit_merchantdatalist tr:last td").eq(1).html(data.description);
        $("#edit_merchantdatalist tr:last td").eq(2).html(data.state==1?"可用":"不可用");
        //'1本省移动；2移动；3本省联通；4联通；5本省电信；6电信；7全网',
        var classify=data.classify;
        if(classify==1){
        	classify="本省移动";
        }else if(classify==2){
        	classify="移动";
        }else if(classify==3){
        	classify="本省联通";
        }else if(classify==4){
        	classify="联通";
        }else if(classify==5){
        	classify="本省电信";
        }else if(classify==6){
        	classify="电信";
        }else if(classify==7){
        	classify="全网";
        }else{
        	classify="";
        }
        $("#edit_merchantdatalist tr:last td").eq(3).html(classify);
       //'通道属性：1直连网关；2第三方通道',
       var attribute=data.attribute;
       if(attribute==1){
    	   attribute="直连网关";
       }else if(attribute==2){
			attribute="第三方通道";
       }else{
    	   attribute="";
       }
        $("#edit_merchantdatalist tr:last td").eq(4).html(attribute);
        $("#edit_merchantdatalist tr:last td").eq(5).html(data.smsNumber==null?"0":data.smsNumber);
        $("#edit_merchantdatalist tr:last td").eq(6).html(data.remainNumber==null?"0":data.remainNumber);
        if(data.state==1){
        	$("#edit_merchantdatalist tr:last td").eq(7).html("<a href='javascript:void(0)' onclick='corpCharge(\""+data.id+"\");'>"+"为此企业充值"+"</a>");
        }else{
        	$("#edit_merchantdatalist tr:last td").eq(7).html("不可用");
        }
    }
    function corpCharge(id){
    	$("#corpRechargeDialogLoad").load("./ap/corpmanage/corpCharge_dialog.jsp",function(){
    		var merchantPin=$("#openaccountPin").val();
				$("#editMerchant_merchantPin").val(merchantPin);
				$("#editMerchant_tunnelId").val(id);
    	});
    }

</script>
</html>