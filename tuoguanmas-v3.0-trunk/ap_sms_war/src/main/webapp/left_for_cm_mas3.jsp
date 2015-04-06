<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="${ctx}/css/common.css" />
		<script type="text/javascript" src="${ctx}/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/leftmenu.js"></script>
		<script type="text/javascript" src="${ctx}/js/window.js"></script>
		<script type="text/javascript" src="${ctx}/js/ui/util.js"></script>
		<script type="text/javascript" src="${ctx}/js/Fader.js"></script>
		<script type="text/javascript" src="${ctx}/js/TabPanel.js"></script>
		<script type="text/javascript" src="${ctx}/js/Math.uuid.js"></script>
		<script type="text/javascript">
	var tabpanel;  
	var jcTabs = ['Welcome'];

	$(document).ready(function(){  
	    tabpanel = new TabPanel({  
	        renderTo:'tab',  
	        width:1100,  
	        height:550,  
	        //border:'none',  
	        active : 0,
	        //maxLength : 5,  
	        items : [{id:'首页_null_main',title:'首页',html:jcTabs[0],closable: false}]
	    }); 
	    
	    
	    // 选中菜单时，样式改变
	    $("a").click(function(){
	    	// 所有的div下的a的on样式去掉
	    	$("div > a").removeClass("on");
	    	// 为选中的菜单加上on样式
			$(this).addClass("on");
		});
	    
	});  
		function addTabs(pId, sId, pName, sName, url){
			$("#currLocation").html("当前位置："+pName+">"+sName);
			//to add a new tab
			 tabpanel.addTab({
				 id: pName+"_"+sName+"_"+pId+"_"+sId,
				 title: sName ,     
				 html:'<iframe src="'+url+'" width="100%" height="100%" frameborder="0"></iframe>',     
				 closable: true
				 }); 
			 }
		
		
	</script>
	</head>
	<body>
		<div id="mainbody">
			<div class="api_leftbar fl">
				<div class="barline">
					<span class="leftbar_hide"></span>
				</div>
				<div class="title">
					<span class="icon_header leftnav"></span>短信办公室
				</div>
				<ul class="apilist-mms">
						<li class="paneli dxhd">
							<span id="item1" onclick="javascript:addTabs('item1','item1s1','短信互动','发短信','${ctx}/smssend/writesms.action');addTabs('item1','item1s1','短信互动','短信记录','${ctx}/smssend/jsp/sms_info.jsp');">短信互动</span>
						</li>
						<li class="paneli hytz">
							<span id="item1">会议通知</span>
						</li>
						<li class="paneli rctx">
							<span id="item1">日期提醒</span>
						</li>
						<li class="paneli tpdc">
							<span id="item1">投票调查</span>
						</li>
						<li class="paneli dxdt">
							<span id="item1">短信答题</span>
						</li>
						<li class="paneli dxcj">
							<span id="item1">短信抽奖</span>
						</li>
						<li class="paneli djbh">
							<span id="item1">点击拔号</span>
						</li>
						<li class="paneli yhgl">
							<span id="item1">用户管理</span>
						</li>
						<li class="paneli jcgl">
							<span id="item1">角色管理</span>
						</li>
						<li class="paneli xtsz">
							<span id="item1">系统设置</span>
						</li>
				</ul>
			</div>
		</div>
	</body>
</html>
