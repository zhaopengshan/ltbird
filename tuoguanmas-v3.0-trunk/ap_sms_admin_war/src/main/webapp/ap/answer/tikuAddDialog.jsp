<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
 
  
  //String question = request.getParameter("question") == null ?"":new String(request.getParameter("question").getBytes("ISO8859-1"),"UTF-8");
  //String answer = request.getParameter("answer") == null ?"":new String(request.getParameter("answer").getBytes("ISO8859-1"),"UTF-8");
  //String score = request.getParameter("score") == null?"":request.getParameter("score");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>题库编辑</title>
<script type="text/javascript">
   var title_info = "";
   if("<%=request.getParameter("id") == null ?"":request.getParameter("id")%>"== ''){
	   title_info = "创建题库";
   }else{
	   title_info = "编辑题库";
   }
 var dialogOptionsCreateTiKu = {
    /**************新增或修改弹出层相关参数***********************/
    //height: 140,
    resizable: false,
    width: 600,  
    height: 400,  
    modal: true,
    autoOpen: true,
    title: title_info,
    close: function(){
    	$(this).dialog("destroy");
       	$("#createTikuDiv").remove();
    },
    buttons:{
        "确定": function(){
        	var msg=$("#createTikuForm").serialize();
            var url_info ="";
        	if($("#id").val()== ''){
        		url_info = "masDatiAction/addTiKu.action";
            }else{
            	url_info = "masDatiAction/updateTiKu.action";
            }
			$.ajax({
				type: "POST",
				data: msg,
				beforeSend:function(){
					var name=$.trim($("#question").val());
					if(name==""||name==null){
						alert("题目内容不能为空");
						return false;
					}

					if($.trim($("#answer").val()) == ""){
                         alert("答案不能为空"); 
                         return false;     
					}

					if($.trim($("#score").val()) == ""){
                         alert("分数不能为空");
                         return false;
				    }

				    if($.trim($("#score").val()) != ""){
                        if(isNaN($.trim($("#score").val()))){
                               alert("请输入数字值");
                               return false;
                        }
				    }
				},
				url: url_info,
                dataType:  "json",
				success: function(data){
					
					if(data.resultcode){
						alert(data.message);
						$("#createTikuDiv").dialog("close");
						datiTiKuListGrid.refresh();
					}else{
						alert(data.message);
					}

					
					
				}
			});
        },
        "关闭": function(){
            $("#createTikuDiv").resetForm();
            $(this).dialog("close");
        } 
    }
};
$(function() {
	$("#createTikuDiv").dialog(dialogOptionsCreateTiKu);
	var tikuId = "<%=request.getParameter("id") == null ?"":request.getParameter("id")%>";
	
	//alert(tikuId);
	if(tikuId != ''){
		$.ajax({
			type: "POST",
			data: {
               	"selectedId": "<%=request.getParameter("id") == null ?"":request.getParameter("id")%>"
            },
			url: 'masDatiAction/getTiKu.action',
            dataType:  "json",
			success: function(data){
				//alert("data.question:"+data.info.question);
            	//createDaTiTiKu();
            	$("#question").val(data.info.question);
            	$("#answer").val(data.info.answer);
            	$("#score").val(data.info.score);
            	$("#id").val("<%=request.getParameter("id") == null ?"":request.getParameter("id")%>");
				//$("#createTikuDiv").dialog("close");
            	//getDaTiTiKu(data.info.id,data.info.question,data.info.answer,data.info.score);
			}
		});
	}
});
</script>
</head>
<body>
<div id="createTikuDiv" class="config" style="display:none;">
		<form  id="createTikuForm">
			<ul>
				<!-- <li>
					<label class="rname">
						<span class="needtip">*</span>
						<span>分组名称</span>
					</label>
					<input id="tikuCreateName" type="text" name="group.groupName" />
					<input id="tikuCreateId" type="hidden" name="group.id" value="-1"/>
				</li>
				<li> -->
					<label class="rname">
					<span class="needtip">*</span>
						<span>题目内容</span>
					</label>
					<textarea id="question" name="tiku.question" cols="40" rows="6" ></textarea>
				</li>
				
				<li>
					<label class="rname">
					<span class="needtip">*</span>
						<span>答案</span>
					</label>
					<textarea id="answer" name="tiku.answer" cols="40" rows="2" ></textarea>
				</li>
				
				<li>
					<label class="rname">
						<span class="needtip">*</span>
						<span>分数</span>
					</label>
					<input id="score" type="text" name="tiku.score" />
					<input id="id" type="hidden" name="tiku.id" /> 
				</li>
			</ul>
		</form>
	</div>
</body>
</html>

