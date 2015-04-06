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
	$(".leftbar_hide").toggle(function(){ 
		$(".main-com").animate({"margin-left":"10px"},400); 
		$(this).parents(".api_leftbar").animate({"width":"10px"},400,function(){
			$(this).find(".leftbar_hide").addClass("leftbar_show").removeClass("leftbar_hide"); 
			$(this).find(".barline").css({"background":"#0078a9"});
			
		}); 
		},function(){
			$(".main-com").animate({"margin-left":"210px"},400);
			$(this).parents(".api_leftbar").animate({"width":"210px"},"normal",function(){
				$(this).find(".leftbar_show").addClass("leftbar_hide").removeClass("leftbar_show");
				$(this).find(".barline").css("background","none");
				
				
			});
			
		});
  }); 