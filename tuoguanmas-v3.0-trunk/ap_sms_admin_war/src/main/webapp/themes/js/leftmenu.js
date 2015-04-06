// JavaScript Document
$(function() {  
	 //左侧栏
	
	
	$("ul.apilist-mms > li").each(function(){
		$(this).find("span").click(function(){ //alert($(this).text()+"; "+ $(this).parent(".current").length);
				if($(this).parent(".current").length > 0){
					$(this).parent(".paneli").removeClass("current").find("div.pane").hide('10');  
				}else{
					$(this).parent(".paneli").addClass("current").find("div.pane").show('50');
					$(this).parent(".paneli").siblings().removeClass("current").find("div.pane").hide('50');
				}
		});
	});
	// 初始化时的tabpanel宽度
	//var vwidth = screen.width -400 ;
	//if(screen.width > 1024)
	//	vwidth = screen.width - 300;
	
	// 初始化的tabpanel高度百分比
	var vheight='95%';
	// 当收缩左侧菜单时,右侧的tabpanel宽度
	var nnwidth= $("#tab").width(); 
	var exwidth = nnwidth+180;// 1263+180=1443
	//var nnwidth=$(".tabpanel").width
	$(".leftbar_hide").toggle(function(){ 
		$(".main-com").animate({"margin-left":"10px"},400); 
		$(this).parents(".api_leftbar").animate({"width":"10px"},400,function(){
			$(this).find(".leftbar_hide").addClass("leftbar_show").removeClass("leftbar_hide"); 
			$(this).find(".barline").css({"background":"#0078a9"});
			
		});
			/*******************自己扩展[展开]********************/
		$("#tab").animate({"margin-left":"10px","margin-right":"10px"},"normal", function(){
			$(this).find(".tabpanel").css({ width: exwidth});
			$(this).find(".tabpanel_move_content").css({ width: exwidth});	// tab工具栏的滚动条滚动时各选项卡空间复用效果
			$(this).find(".tabpanel_tab_content").css({ width: exwidth});
			$(this).find(".tabpanel_content").css({background: "#FFF",overflow: "auto", 
				position: "relative",width: exwidth, height: vheight});
		});
		
			/*****************结束**************************/
		},function(){
			$(".main-com").animate({"margin-left":"210px"},400);
			$(this).parents(".api_leftbar").animate({"width":"210px"},"normal",function(){
				$(this).find(".leftbar_show").addClass("leftbar_hide").removeClass("leftbar_show");
				$(this).find(".barline").css("background","none");
				
				
			});
			/*******************自己扩展[收缩]********************/
			$("#tab").animate({"margin-right":"5px","margin-left":"5px"},"normal", function(){
				$(this).find(".tabpanel").css({ width: nnwidth-2});
				$(this).find(".tabpanel_move_content").css({ width: nnwidth-2});
				$(this).find(".tabpanel_tab_content").css({ width: nnwidth-2});
				$(this).find(".tabpanel_content").css({background: "#FFF",overflow: "auto", 
					position: "relative",width: nnwidth-2, height: '95%'});
			});
			/*****************结束**************************/
		});
  }); 