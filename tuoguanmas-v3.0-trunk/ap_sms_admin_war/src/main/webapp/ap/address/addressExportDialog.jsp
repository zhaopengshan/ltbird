<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信通道</title>
<script type="text/javascript">
var exportDialogOptions = {
    /**************导出结果弹出层相关参数***********************/
    width: 220,
    resizable: false,
    modal: true,
    autoOpen: true,
    title: "下载联系人",
    close: function(){
    	$(this).dialog("destroy");
       	$("#downloadDiv").remove();
    },
    buttons:{
        "关闭": function(){
            $(this).dialog("close");
        }
    }
};
$(function() {
	$("#downloadDiv").dialog(exportDialogOptions);
});
</script>
</head>
<body>
<div id="downloadDiv" style="display: none;">
    <ul>
        <li><strong>导出<span id="exportContactNum"></span>个联系人成功！&nbsp;&nbsp;&nbsp;</strong></li>
        <li><a href="#" style="color:blue" id="downloadExcel" >下载excel文件</a></li>
    </ul>
</div>
</body>
</html>

