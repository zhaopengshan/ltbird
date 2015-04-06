<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>导入联系人</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta http-equiv="imagetoolbar" content="no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
<script type="text/javascript" >
function toAddresslist(){
    var originalUrl = "./ap/address/import_contacts.jsp";
    var tempUrl = "./ap/address/addresslist.jsp";
   /*  var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
    try{
        tabpanel.kill(killId);
    }catch(e){
    } */
    try{
        tabpanel.kill("tabidexportresult");
    }catch(e){
    }
    
    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").click();
    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").click();
}
function toInportContacts(){
    var originalUrl = "./ap/address/import_contacts.jsp";
   // var tempUrl = "./ap/address/import_contacts.jsp";
    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
    try{
    	tabpanel.kill("tabidexportresult");
        tabpanel.kill(killId);
    }catch(e){
    }
    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", originalUrl).click();
    //$(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
}
</script>
</head>
<body>
	<div class="config" style="overflow-y: auto;overflow-x: hidden;hight: 420px;">
		<input type="hidden" value="${resultflag }"/>
		<ul>
			<li><span>${messageTotal }</span></li>
		</ul>
		<ul>
			<li>
				<label>
					<span>错误信息提示</span>
				</label>
			</li>
			<li>
				<select  multiple="multiple" style="width: 360px" >
                        <s:iterator id="content" value="#request.resultContent">
                            <option>${content}</option>
                        </s:iterator>
                </select>
			</li>
			<li class="btn">
				<input type="button" value="继续导入" onclick="toInportContacts();"/>
				<input type="button" value="返回联系人列表" onclick="toAddresslist();"/>
			</li>
		</ul>
	</div>
</body>
</html>