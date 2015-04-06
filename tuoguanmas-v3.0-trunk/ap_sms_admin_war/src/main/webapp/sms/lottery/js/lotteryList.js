	function showSmsDetail(selectedId){
		var localUrl = "${ctx}/mbnSmsSelectedAction/getSmsDetails.action?selectedId=";
		// $("#sms_hd_frame").load(localUrl+selectedId);
	}
	var unSendGridPro = {
		url:"lottery/pageDatiLotteryList.action?sendType=0",// 执行地址
		colNames:['状态', '任务名称','有效时间','应参与人数','创建人'],// 列名
		colModel:[// 列所有名称的属性
			{name:'state',width:50,align:"center",formatter:function(data){
				return "<img width='13' height='16' title='待发送' src='./themes/mas3admin/images/vote/lise_lcon3.gif' complete='complete'/>";
			}},
			{name:'title',width:150,align:"center"},
			{name:'endTime',width:200,align:"center",formatter:function(data){
				return data.beginTime+"~"+data.endTime;
			}},
			{name:'tos',width:80,align:"center",formatter:function(data){
				var str=data.tos.split(",");
				var count=0;
				for(i=0;i<str.length;i++){
					if(str[i].length>0)
						count++;
				}
				return count;
			}},
			{name:'loginAccount',width:50,align:"center"}
		],
		buttons: [{// 按钮
			text: "编辑转发",
			name:'edit',
			classes: "",
			click: function(){
				var url = 'lottery/editLottery.action';
				editLotteryFunc(smsGrid,url);
			}
		},{
			text: "删除",
			name:'delete',
			classes: "",
			click: function(){
				var url = 'lottery/deleteLottery.action?sendType=0';
				var showMessage = "是否删除选中的{0}条记录？";
                confirmAjaxFunc(smsGrid,url,showMessage);
			}
		}],
		multiselect: true
	};
	var SendGridPro = {
			url:"lottery/pageDatiLotteryList.action?sendType=1",// 执行地址
			colNames:['状态', '任务名称','有效时间','应参与人数','实参与人数','抽奖','创建人'],// 列名
			colModel:[// 列所有名称的属性
			          {name:'state',width:10,align:"center",formatter:function(data){
			        	  return "<img width='13' height='16' title='已发送' src='./themes/mas3admin/images/vote/lise_lcon1.gif' complete='complete'/>";
			          }},
			          {name:'title',width:150,align:"center"},
			          {name:'endTime',width:150,align:"center",formatter:function(data){
			        	  return data.beginTime+"~"+data.endTime;
			          }},
			          {name:'tos',width:50,align:"center",formatter:function(data){
			        	  var str=data.tos.split(",");
			        	  var count=0;
			        	  for(i=0;i<str.length;i++){
			        		  if(str[i].length>0)
			        			  count++;
			        	  }
			        	  return count;
			          }},
			          {name:'validTos',width:50,align:"center",formatter:function(data){
			        	  if(data.validTos==null){
			        		  return 0;
			        	  }else{
				        	  var str=data.validTos.split(",");
				        	  var count=0;
				        	  for(i=0;i<str.length;i++){
				        		  if(str[i].length>0)
				        			  count++;
				        	  }
				        	  return count;
			        	  }
			          }},
			          {name:'id',width:50,align:"center",formatter:function(data){
				        	  if(data.validTos==null){
				        		  return "<a href='javascript:void(0);' onclick=\"javascript:alert('没有参与人，暂时无法抽奖')\">抽奖</a>"; 
				        	  }else{
				        		  return "<a href='javascript:void(0);' onclick=\"toAward('"+data.id+"','"+data.validTos+"')\">抽奖</a>"; 
				        	  }
				          }},
			          {name:'loginAccount',width:50,align:"center"}
			          ],
			          buttons: [
			              {
			        	  text: "删除",
			        	  name:'delete',
			        	  classes: "",
			        	  click: function(){
			        		  var url = 'lottery/deleteLottery.action?sendType=0';
			        		  var showMessage = "是否删除选中的{0}条短信？";
			        		  confirmAjaxFunc(smsGrid,url,showMessage);
			        	  }
			          }],
			          multiselect: true
	};
	var LotteryGridPro = {
			url:"lottery/pageDatiLotteryList.action?sendType=2",// 执行地址
			colNames:['状态', '任务名称','有效时间','应参与人数','实参与人数','结果','创建人'],// 列名
			colModel:[// 列所有名称的属性
			          {name:'state',width:10,align:"center",formatter:function(data){
			        	  return "<img width='13' height='16' title='已发送' src='./themes/mas3admin/images/vote/lise_lcon1.gif' complete='complete'/>";
			          }},
			          {name:'title',width:150,align:"center"},
			          {name:'endTime',width:150,align:"center",formatter:function(data){
			        	  return data.beginTime+"~"+data.endTime;
			          }},
			          {name:'tos',width:50,align:"center",formatter:function(data){
			        	  var str=data.tos.split(",");
			        	  var count=0;
			        	  for(i=0;i<str.length;i++){
			        		  if(str[i].length>0)
			        			  count++;
			        	  }
			        	  return count;
			          }},
			          {name:'tos',width:50,align:"center",formatter:function(data){
			        	  var str=data.tos.split(",");
			        	  var count=0;
			        	  for(i=0;i<str.length;i++){
			        		  if(str[i].length>0)
			        			  count++;
			        	  }
			        	  return count;
			          }},
			          {name:'id',width:50,align:"center",formatter:function(data){
			        	  return "<a href='javascript:void(0);' onclick=\"upshot('"+data.id+"')\">查看</a>"; 
			          }},
			          {name:'loginAccount',width:50,align:"center"}
			          ],
			          buttons: [
			                    {
			                    	text: "删除",
			                    	name:'delete',
			                    	classes: "",
			                    	click: function(){
			                    		var url = 'lottery/deleteLottery.action?sendType=0';
			                    		var showMessage = "是否删除选中的{0}条短信？";
			                    		confirmAjaxFunc(smsGrid,url,showMessage);
			                    	}
			                    }],
			                    multiselect: true
	};
	var UpshotGridPro = {
			url:"lottery/lotteryUpshotList.action?lotteryId=",// 执行地址
			colNames:['状态', '奖品等级','奖品名称','手机号码','中奖时间'],// 列名
			colModel:[// 列所有名称的属性
			          {name:'id',width:10,align:"center",formatter:function(data){
			        	  return "<img width='13' height='16' title='已抽奖' src='themes/mas3admin/images/lottery/u165_normal.png' complete='complete'/>";
			          }},
			          {name:'gradeLevelName',width:150,align:"center"},
			          {name:'awardContent',width:150,align:"center"},
			          {name:'mobile',width:50,align:"center"},
			          {name:'createTime',width:50,align:"center"}
			          ],
			          buttons: [
			                    {
			                    	text: "删除",
			                    	name:'delete',
			                    	classes: "",
			                    	click: function(){
			                    		var url = 'lottery/deleteLotteryUpshot.action';
			                    		var showMessage = "是否删除选中的{0}条记录？";
			                    		confirmAjaxFunc(smsGrid,url,showMessage);
			                    	}
			                    },{
			                    	text: "返回",
			                    	name:'back',
			                    	classes: "",
			                    	click: function(){
			                    		selectLottery('lottery');
			                    		$("#filterResult").hide();
			                    		$("#filterData").show();
			                    	}
			                    }
			                    ],
			                    multiselect: true
	};
	var smsGrid;
	function upshot(id){
		$("#lotteryResultById").val(id);
		UpshotGridPro.url+=id;
		smsGrid = new TableGrid("lotteryGrid",UpshotGridPro);
		smsGrid.redrawGrid(UpshotGridPro);
		UpshotGridPro.url="lottery/lotteryUpshotList.action?lotteryId=";
		$("#filterData").hide();
		$("#filterResult").show();
		
	}
	
	function filterLottery(){
		var action="lottery/filterLottery.action";
		var param="?title="+$(":input[name='filterTitle']").val()+"&loginAccount="+$(":input[name='filterLoginAccount']").val()+"&tos="+$(":input[name='filterTos']").val()+"&state="+$(":input[name='filterState']").val();
		var type=$("#lotteryStatusSearch option").val();
		if("unsend"==type){
			unSendGridPro.url=action+encodeURI(encodeURI(param));
		}else if("send"==type){
			SendGridPro.url=action+encodeURI(encodeURI(param));
		}else if("lottery"==type){
			LotteryGridPro.url=action+encodeURI(encodeURI(param));
		}
		selectLottery(type);
	}
	
	//抽奖结果过滤
	function filterLotteryResult() {
		//url:"lottery/lotteryUpshotList.action?lotteryId="
		id=$("#lotteryResultById").val();
		var param="&gradeLevelName="+$.trim($("#gradeLevelName").val());
		param+="&mobile="+$.trim($("#LotteryRusultMobile").val());
		param+="&createTime="+$("#lotteryCreateTime").val();
		UpshotGridPro.url+=id+encodeURI(encodeURI(param));
		smsGrid = new TableGrid("lotteryGrid",UpshotGridPro);
		smsGrid.redrawGrid(UpshotGridPro);
		UpshotGridPro.url="lottery/lotteryUpshotList.action?lotteryId=";
	}
	
	function selectLottery(type){
		$("#lottery_send_menu,#lottery_award_menu,#lottery_unSend_menu").removeClass("zhan");
		if("unsend"==type){
			smsGrid = new TableGrid("lotteryGrid",unSendGridPro);
			smsGrid.redrawGrid(unSendGridPro);
			unSendGridPro.url="lottery/pageDatiLotteryList.action?sendType=0";
			$("#lottery_unSend_menu").attr("class","zhan");
			
		}else if("send"==type){
			smsGrid = new TableGrid("lotteryGrid",SendGridPro);
			smsGrid.redrawGrid(SendGridPro);
			SendGridPro.url="lottery/pageDatiLotteryList.action?sendType=1";
			$("#lottery_send_menu").attr("class","zhan");
		}else if("lottery"==type){
			smsGrid = new TableGrid("lotteryGrid",LotteryGridPro);
			smsGrid.redrawGrid(LotteryGridPro);
			LotteryGridPro.url="lottery/pageDatiLotteryList.action?sendType=2";
			$("#lottery_award_menu").attr("class","zhan");
		}
		$("#lotteryStatusSearch option[value='"+type+"']").attr("selected","selected");
	}
	
	
	 var lotterypop= {
			    /** ************新增或修改弹出层相关参数********************** */
			    resizable: false,
			    width: 700,  
			    height: 356,  
			    modal: true,
			    autoOpen: true,
			    title: "短信抽奖",
			    close: function(){
			    	$(this).dialog("destroy");
			       	$("#lotterypopDiv").hide();
			    },
			    buttons:{
			        "关闭": function(){
			            $(this).dialog("close");
			        } 
			    }
 	};
	 
	 function toAward(lotteryId,tos){
		 $("#lotterypopDiv").empty();
		 $.getJSON("lottery/awardList.action?lotteryId="+lotteryId, function(json){
			 		if(""==json.message){
						  $.each(json.content, function(i,item){
							  $("#lotterypopDiv").append(getAwardHtml(item,tos));
						  });
						  $("#lotterypopDiv").dialog(lotterypop);
			 		}else{
			 			alert(json.message);
			 		}
			 	
				});
		 return ;
		 }
	 
	 function getAwardHtml(item,tos){
		 var str="";
		 str+="<tr>"+
		 "<td width=\"100;\"><span class=\"lotteryName\">"+item.awardContent+"</span></td>";
		 str+="<td width=\"300px;\">";
		 //除去第一个逗号，以防产生空的抽奖参与人
		 var oneStr=tos.substr(0,1);
		 if(","==oneStr){
			 tos=tos.substr(1,tos.length);
		 }
		 var nums=tos.split(",");
		 var tosCount=0;
		 for(var i=0;i<nums.length;i++){
			 if(""!=nums[i]){
				 if(i==0){
					 str+="<div class=\"prizebox\">"+nums[i]+"</div>";
				 }
				 else{
					 str+="<div style=\"display:none;\" class=\"prizebox\">"+nums[i]+"</div>";
				 }
				 tosCount++;
			 }
		 }
		 str+="</td>";
		 str+="<td style=\"padding-left: 20px;\"><span  class=\"tubh\" ><input type=\"hidden\" value='"+tosCount+"'/><button onclick=\"startLottery(this,'"+item.quotaOfPeople+"');\"  class=\"\">开始抽奖</button></span><span class=\"tubh\">" +
		 		"<button onclick=\"stopLottery(this,'"+item.quotaOfPeople+"','"+item.dxcjId+"','"+item.gradeLevelName+"','"+item.awardContent+"');\" class=\"dis\">抽奖结束</button>" +
		 		"还剩<font>"+item.quotaOfPeople+"</font>个名额,共<font>"+item.quotaOfPeople+"</font>个</span></td>";
		 str+="</tr>";
			 //onclick=\"alert('"+item.awardContent+","+item.gradeLevelName+","+item.quotaOfPeople+"')\"
		 return str;
	 }

	 var timer;

	 function startLottery(obj,quotaOfPeople){
		 if(timer!=null){
			 alert('请结束上一个抽奖');
			 return false;
		 }
		 var NumInput=$(obj).prev();
		 var count=Number(NumInput.val());
		 if(count<=0){
			 return false;
		 }else{
			 $(obj).attr('class',"dis").attr("disabled","disabled");
			 $(obj).parent().next().find("button").attr('class',"").removeAttr("disabled");
			 timer=window.setInterval(function(){
				 awardPlay(obj,quotaOfPeople)
			 },500);
		 }

	 }
	 function stopLottery(obj,quotaOfPeople,dxcjId,gradeLevelName,awardContent){
		 clearInterval(timer);
		 timer=null;
		 $(obj).attr('class',"dis").attr("disabled","disabled");;
		 var lotteryer=$(obj).closest("td").prev().find("div:visible");
		 var NumInput=$(obj).closest("span").prev().find(":input[type='hidden']");
		 var count=Number(NumInput.val());
		 if(count>1){
			 NumInput.next().attr('class',"").removeAttr("disabled");
		 }
		 var Lotterytos=lotteryer.html();
		 if(count==1){
			 lotteryer.append("等"+quotaOfPeople+"人");
		 }
		 var param="?dxcjId="+dxcjId+"&gradeLevelName="+gradeLevelName+"&awardContent="+awardContent+"&mobile="+Lotterytos;
		 param=encodeURI(encodeURI(param));
		 $.getJSON("lottery/addLotteryUpshot.action"+param, 
			function(json){
					if(confirm(json.message+"用户:"+Lotterytos+"\n需要在下面奖项中移除此用户吗？")){
						removeTos(Lotterytos);
						return true;
					}
			});
		 NumInput.val((count-1));
		 $(obj).next("font").html((count-1));

		 
	 }
	 
	 function removeTos(tos){
		 $("#lotterypopDiv tr").each(function(i){
			 var num=$(this).find("td").eq(1).find("div");
			 var nextTos=num.get(0);
			 var $size=num.size();
			 $(num).each(function(){
				 if($(this).html().indexOf(tos)!=-1){
					 if($size==1){
						 $(this).html('');
					 }else{
						 $(this).remove();
					 }
					 
				 }
			 });
			 $(nextTos).show();
		 });
	 }
	 function awardPlay(obj,quotaOfPeople){
		 var alltos=$(obj).closest("td").prev().find("div");
		var mathTos=alltos[Math.floor(Math.random()*alltos.length)];
		 //var mathTos=getArrayItems(alltos,quotaOfPeople);
		 $(alltos).hide();
		 $(mathTos).fadeIn("fast");
	 }//fadeIn("slow");
	
	 function getArrayItems(arr, num) {
		    //新建一个数组,将传入的数组复制过来,用于运算,而不要直接操作传入的数组;
		    var temp_array = new Array();
		    for (var index in arr) {
		        temp_array.push(arr[index]);
		    }
		    //取出的数值项,保存在此数组
		    var return_array = new Array();
		    for (var i = 0; i<num; i++) {
		        //判断如果数组还有可以取出的元素,以防下标越界
		        if (temp_array.length>0) {
		            //在数组中产生一个随机索引
		            var arrIndex = Math.floor(Math.random()*temp_array.length);
		            //将此随机索引的对应的数组元素值复制出来
		            return_array[i] = temp_array[arrIndex];
		            //然后删掉此索引的数组元素,这时候temp_array变为新的数组
		            temp_array.splice(arrIndex, 1);
		        } else {
		            //数组中数据项取完后,退出循环,比如数组本来只有10项,但要求取出20项.
		            break;
		        }
		    }
		    return return_array;
		}

	 
	$(function() {
		selectLottery("unsend");
		// $("#lotterypopDiv").dialog(lotterypop);
		
	});
	
	function editLotteryFunc(smsGrid,url){
		var rows = smsGrid.getSelectedItemIds();
		if( rows.length == 1 ){ 
		    var originalUrl = "./lottery/writeSms.action";
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