/*util.js只用于提供工具方法，这次方法里不包含任何业务逻辑*/

/**
 * @version 0.0.1
 * @namespace Namespace
 * @author:sunmm
 * @description 创建一个命名空间,命名空间的父子关系使用半角点号"."隔开
 * @param {} namespacePath String 例："a.b.c"
 * @example <br/><br/>
 * 	Namespace.create("a.b.c");
 * 	a.b.c.num = 12;
 * 	a.b.c.str = "12";
 * 	a.b.c.Fun = function(){
 * 					alert("dd");
 * 				}
 * a.b.c.Obj = {a:"",c:""};
 * .....
 * @return {void}
 */
var Namespace = {};
Namespace.create =function(namespacePath){
	//以window为根
	var rootObject =window;
	//对命名空间路径拆分成数组
	var namespaceParts =namespacePath.split('.');
	for (var i =0;i <namespaceParts.length;i++) {
	   var currentPart =namespaceParts[i];
	   //如果当前命名空间下不存在，则新建一个Object对象，等效于一个关联数组。
	   if (!rootObject[currentPart]){
	      rootObject[currentPart]={};
	   }
	   rootObject =rootObject[currentPart];
	}
}; 

Namespace.create("myMas.ui");  //以下是实现UI效果的方法

myMas.ui = {    

	menuList : function(onedom,onedomli,twodom) {
				$(onedom).find(twodom).addClass("subnav").css("display","none"); 
				$(onedomli).hover(
					function(){
						$(this).addClass("hover");
						$(this).find(twodom).show('50');
					},
					function(){
						$(this).find(twodom).hide('10');
						$(this).removeClass("hover");
				});
	}, //menulist下拉二级菜单实现 如：menuList("ul.navmenu","ul>li","ul")
	
	switchTab : function (dot,block) {
					$(block).css("display","none"); 
					$(dot).first().addClass("on");
					$(block).first().css("display","block");  
					$(dot).click(function() {
						var num = $(this).index();
						$(this).addClass("on");
						$(this).siblings().removeClass("on");
						$(block+':eq('+num+')').css("display","block");
						$(block+':eq('+num+')').siblings(block).css("display","none");
				});
	}, //tabs切换效果实现 如：switchTab("ul.tabslist li",".tabsdiv")

	switchSelected : function (parentDot, crrentdot) {
					$(parentDot).not($(".tstyle")).each(function(){
						$(this).find(crrentdot).first().addClass("on"); 
						$(this).find(crrentdot).click(function() { 
							$(this).addClass("on");
							$(this).siblings().removeClass("on"); 
						}); 
					}); 
	}, //鼠标点击选中效果 switchSelected("ul","li")

	hoverSelected : function (parentDot, crrentdot) {
					$(parentDot).not($(".tstyle")).each(function(){
						$(this).find(crrentdot).hover(function() { 
							$(this).addClass("hover"); 
						},function () {
							$(this).removeClass("hover");
						}); 
					}); 
	}, //鼠标点击选中效果 switchSelected("ul","li")

	inputBlur : function () { 
					$(".queryInput").each(function(){
						var setvalue,valuenow;
						setvalue = $(this).attr("defaultValue"); 
						valuenow = $(this).attr("value"); 
						$(this).focus(function(){ 
							if((setvalue == valuenow)||valuenow==""){
								$(this).attr("value","").css({"color":"#555"}); 
							}
							valuenow = $(this).attr("value"); 
						});

						$(this).change(function(){
							valuenow = $(this).attr("value");
						});

						$(this).blur(function(){  
							if(valuenow==""){	 
								$(this).attr("value",setvalue).css({"color":"#CDCDCD"});
							}  
						}); 

					}); 
					
	}, //input输入框点击value清除,liuqiao改进后的

/*
	inputBlur : function () { 
					$("input[type='text']").each(function(){
						var setvalue,valuenow;
						setvalue = $(this).attr("value"); 
						valuenow = $(this).attr("value"); 
						$(this).focus(function(){ 
							if((setvalue == valuenow)||valuenow==""){
								$(this).attr("value","").css({"color":"#555"}); 
							}
							valuenow = $(this).attr("value"); 
						});

						$(this).change(function(){
							valuenow = $(this).attr("value");
						});

						$(this).blur(function(){  
							if(valuenow==""){	 
								$(this).attr("value",setvalue).css({"color":"#CDCDCD"});
							}  
						}); 

					}); 
					
	}, //input输入框点击value清除 */
	
	hoverEvent : function(obj,className,nclassName) {
					$(obj).hover(function() {
						$(this).addClass(nclassName).removeClass(className);
					},
					function() {
						$(this).removeClass(nclassName).addClass(className);
	                });
    },  //鼠标滑入滑出classname变换 如：hoverEvent(".get-btn","get-btn","get-btn-on")

	tableTrBg : function(tabledom,trbgcolor,strlen) {    
					$(tabledom).each(function(){
						$(this).not($(".tstyle")).find("tr:odd").find("td").css("background",trbgcolor); 
						$(this).find("td").each(function(){ 
							if(parseInt($(this).text().length)> strlen){
								$(this).wrapInner("<div style='width:200px; display:inline-block; overflow:hidden; word-wrap:break-word; word-break:break-all;'></div>");
							}
						});  
					});
	}, //tr隔行变换背景色，对长字符串换行处理 如：tableTrBg("table.recharge-list","#fafbfd",100)

	getSubstr : function(dom, str_len){
			$(dom).each(function(){ 
				var totleStr=$(this).text();   
				var byteLen = 0, len = totleStr.length;
				if(len > 0){
					for(var i=0; i<len; i++ ){
						if(totleStr.charCodeAt(i) > 255){
							byteLen += 2;
						}else{
							byteLen++;
						}   
						if(byteLen > str_len){ 
							var getString = totleStr.substr(0,i); 
							$(this).attr("title",totleStr); 
							$(this).text(getString + "...");
							return;
						}
					} 
				}
				return;
			});
	}, //td中的文本长度截取，如getSubstr("td.andson", 14);

	pageWidth : function() {
		var pageWidth = window.innerWidth; 
		var pageHeight = window.innerHeight; 
		if(typeof pageWidth != "number"){
			if(document.compatMode == "CSS1Compat"){
				pageWidth = document.documentElement.clientWidth;  
			}else{
				pageWidth = document.body.clientWidth; 
			}
		} 
		return pageWidth; 
	}, //获取窗口宽度 
	
	pageHeight : function() {
		var pageWidth = window.innerWidth; 
		var pageHeight = window.innerHeight; 
		if(typeof pageWidth != "number"){
			if(document.compatMode == "CSS1Compat"){
				pageHeight = document.documentElement.clientHeight; 
			}else{
				pageHeight = document.body.clientHeight;
			}
		}
		return pageHeight; 
	}, //获取窗口高度
	
	getElementsByClassName : function(className) {
		var all = document.all ? document.all : document.getElementsByTagName('*');
		var elements = new Array();
		for (var e = 0; e < all.length; e++) {
			if (all[e].className == className) {
				elements[elements.length] = all[e]; 
			}
		}
		if(elements.length==0){
			return false;
		}else{
			return elements;
		} 
	},

	tabsScroll : function (tabsNum){
		if($(".div-panes-list li").length > tabsNum){
			$(".div-panes-list li").each(function(){ 
					var sign=0;
					var temp=0;
					$(this).click(function(){   
						if($(this).position().top >= 120 && $(this).next().length != 0 && sign==temp){ 
							sign++;
							$(this).parent().children().first().animate({"margin-top" : '-=31px'}, "normal", function(){ 
								temp++;
							}); 
						}else if($(this).position().top < 5 && $(this).prev().length != 0 && sign==temp){ 
							sign++;
							$(this).parent().children().first().animate({"margin-top" : '+=31px'}, "normal", function(){ 
								temp++;
							}); 
						}
					});
				 
			});
		}
	}//实现tabs的点击滚动效果 如：myMas.ui.tabsScroll(5);
 
};

 
