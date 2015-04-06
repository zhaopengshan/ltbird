function editCalendarSmsFunc(smsGrid,url){
		var rows = smsGrid.getSelectedItemIds();
		if( rows.length == 1 ){ 
			//window.parent.parent.addTabs('itemx','itemxs'+rows,'短信互动','编辑转发',url+'?selectedId='+rows);
		    var originalUrl = "./calendar/writesms.action";
		    var tempUrl = url+'?selectedId='+rows;
		    var killId = $("a:[taburl='"+originalUrl+"']").attr("tabid");
		    try{
		        tabpanel.kill(killId);
		    }catch(e){
		    }
		    $(".menu_items > li > ul > li > a:[taburl='"+originalUrl+"']").attr("taburl", tempUrl).click();
		    $(".menu_items > li > ul > li > a:[taburl='"+tempUrl+"']").attr("taburl", originalUrl);
		}else if( rows.length > 1){
			alert("只能选择一项进行操作！");
		}else{
			alert("请先选择需要操作的项！");
		}
	}