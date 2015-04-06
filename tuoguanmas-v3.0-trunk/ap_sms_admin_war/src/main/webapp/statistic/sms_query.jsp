<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
        <link type="text/css" rel="stylesheet"  href="./statistic/style.css" />
        <title>短信查询</title>
    </head>
    <body>
        <div>
            <div id="numberQuery">
                <ul class="search_cer" id="top1search">
                    <li>
                        <span class="name">短信时间：</span>
                        <span>
                            <select style="width:100px" id="statisticyear">
                                <option value="2011">2011年</option>
                                <option value="2012">2012年</option>
                                <option value="2013">2013年</option>
                                <option value="2014" selected="selected">2014年</option>
                                <option value="2015">2015年</option>
                                <option value="2016">2016年</option>
                                <option value="2017">2017年</option>
                                <option value="2018">2018年</option>
                                <option value="2019">2019年</option>
                                <option value="2020">2020年</option>                
                            </select>
                        </span>
                        <span>
                            <select style="width:100px" id="statisticmonth">
                                <option value="01">1月</option>
                                <option value="02">2月</option>
                                <option value="03">3月</option>
                                <option value="04">4月</option>
                                <option value="05">5月</option>
                                <option value="06">6月</option>
                                <option value="07">7月</option>
                                <option value="08">8月</option>
                                <option value="09">9月</option>
                                <option value="10">10月</option>
                                <option value="11">11月</option>
                                <option value="12">12月</option>                
                            </select></span>   
                        <span>
                            <select style="width:100px" id="statisticstartday">

                            </select>
                        </span> 
                        <span style="padding:0px 35px 0 27px;">到</span> 
                        <span>
                            <select style="width:115px" id="statisticendday">

                            </select>
                        </span>
                    </li>
                    <li>
                        <span class="name">通道类型：</span>
                        <span>
                            <select id="accessnumber" name="accessNumber" style="width:115px;">
                                <option value="0">全部</option>
                            </select>
                        </span>            
                    </li>
                    <li>
                        <span class="name">发送用户：</span>
                        <span>
                            <select id="createby" name="createBy" style="width:115px;">
                                <!--<option value="0">全部用户</option>-->
                            </select>
                        </span>
                    </li>
                    <li>
                        <span class="name">通信方式：</span>
                        <span>
                            <select id="communicationway" name="communicationWay" style="width:115px;">
                                <option value="0">发送</option>
                                <option value="1">接收</option>
                            </select>
                        </span>
                    </li>

                    <li>
                        <span class="name">发送结果：</span>
                        <span><select id="sendresults" name="sendResults" style="width:115px;">
                                <option value="0">全部</option>
                                <option value="2">成功</option>
                                <option value="3">失败</option>
                            </select>
                        </span>
                    </li>
                    <li>
                        <span class="name">短信类型：</span>
                        <span><select id="smtype" name="smType" style="width:115px;">
                                <option value="0">全部</option>
                                <option value="1">普通短信</option>
                                <option value="2">长短信</option>
                            </select>
                        </span>
                    </li>
                    <li>
                        <span class="name">对方号码：</span>
                        <span>
                            <input id="oppositemobile" name="oppositeMobile" type="text" class="textW" />
                        </span>
                    </li>
                    <li>
                        <span class="name">发送接口：</span>
                        <span>
                            <select id="intfType" name="intfType" style="width:115px;">
                                <option value="0">全部</option>
                                <option value="1">页面发送</option>
                                <option value="2">WebService</option>
                                <option value="3">数据库接口</option>
                            </select>
                        </span>
                    </li>
                </ul>
            </div>
            <div class="graybg">
            <span id="statisticsTotal">共查询到0条记录！</span>
            <a href="javascript:void(0)" id="statisticSearch">查  询</a>
            <a href="javascript:void(0)" id="statisticccount">统  计</a>
            <a href="javascript:void(0)" id="statisticsExport">导  出</a>
            </div>
            <div class="maintable" id="dddd">
                <p class="newpage" style="text-align:right; padding-top:8px; padding-bottom:8px;">
                    <span class="float_l show">提示：以下信息不代表收费记录，话单以中国移动为准</span>
                    <span>共<span id="allCounts">0</span>项, 本页<span id="fromEnd">0-0</span>项</span>
                    <span></span>
                    <a id="statistic_pre_page" href="javascript:void(0)" class="tubh-page">上一页</a>
                    <span id="statistic_cur_page">1</span>
                    <span>/</span>
                    <span id="statistic_total_page">1</span>
                    <a id="statistic_next_page" href="javascript:void(0)" class="tubh-page">下一页</a>
                    <span>跳转到 <input type="text" style="width:30px;" value="1" id="statisticjustPage"/> 页</span>
                    <a id="statistic_jump_page" href="javascript:void(0)" class="tubh-page">GO</a>
                </p>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="querytable" id="querytable">
                    <thead>
                        <tr>
                            <th scope="col">发送用户</th>
                            <th scope="col" style="width:150px">短信时间</th>
                            <th scope="col">通信方式</th>
                            <th scope="col">短信类型</th>
                            <th scope="col">发送接口</th>
                            <th scope="col">对方号码</th>
                            <th scope="col">拆分条数</th>
                            <th scope="col">发送结果</th>
                            <th scope="col">失败原因</th>
                        </tr>
                        <tr style="display:none" id="statisticrowtemplate">
                            <td></td>
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
                    <tbody id="statisticbody">

                    </tbody>
                </table>
                <p class="newpage" style="text-align:right; padding-top:8px; padding-bottom:8px;">
                	<span class="float_l show"></span>
                	<span>共<span id="allCounts_foot">0</span>项, 本页<span id="fromEnd_foot">0-0</span>项</span>
                	<span></span>
                    <a id="statistic_pre_page_foot" href="javascript:void(0)" class="tubh-page">上一页</a>
                    <span id="statistic_cur_page_foot">1</span><span>/</span>
                    <span id="statistic_total_page_foot">1</span>
                    <a id="statistic_next_page_foot" href="javascript:void(0)" class="tubh-page">下一页</a>
                    <span>跳转到 <input type="text" style="width:30px;" value="1" id="statisticjustPagefoot" /> 页</span>
                    <a id="statistic_jump_page_foot" href="javascript:void(0)" class="tubh-page">GO</a>
                    <input type="hidden" id="statistic_page_no" value="1" />
                </p>
            </div>
        </div>
        <div id="dialog-message" title="&#x77ED;&#x4FE1;&#x6C47;&#x603B;&#x7EDF;&#x8BA1;">
            <div class="content">
                <div class="maintable" id="sss">
                    <ul class="countlist">
                        <li>
                            <span class="textname">短信发送量：</span>
                            <span class="con" id="smsendsummary"></span>
                            <span class="ex">注：长短信不拆分计数</span>
                        </li>
                        <li>
                            <span class="textname">短信发送条数：</span>
                            <span class="con" id="smsendsegmentssummary"></span>
                            <span class="ex">注：长短信拆分后计数，下同</span>
                        </li>        

                        <li>
                            <span class="textname">发送成功条数：</span>
                            <span class="con" id="smsendsuccesssummary"></span>
                        </li>        
                        <li>
                            <span class="textname">发送失败条数：</span>
                            <span class="con" id="smsendfailursummary"></span>
                        </li>

                        <li>
                            <span class="textname">短信接收条数：</span>
                            <span class="con" id="smreceivesummary"></span>
                        </li>  
                    </ul>

                </div>
            </div>
        </div>
        <div id="downloadDialogStatistic"></div>
    </body>
</html>
<script language="javascript" type="text/javascript">
	/**
     *	
     * 导出的下载excel文件
     *
     */
    function downloadSmsQuery(url){
        $("#downloadStatisticDiv").dialog("close");
        window.open(url);
    }
    $(document).ready(function(){
		$("#statisticsExport").unbind("click").bind("click", function(){
			var showMessage = "确定导出查询记录吗？";
			if( confirm( showMessage ) ){
                var startTime = $("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticstartday").val();
		        var endTime = $("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticendday").val();
		        $.ajax({
		            url: "./statisticAction/statisticQueryExport.action",
		            data:"startTime="+startTime                
		                +"&endTime="+endTime
		                +"&classify="+$("#accessnumber").find("option:selected").attr("cid")
		                +"&accessNumber="+$("#accessnumber").val()+"&createBy="+$("#createby").val()
		                +"&communicationWay="+$("#communicationway").val()+"&sendResults="+$("#sendresults").val()
		                +"&smType="+$("#smtype").val()+"&oppositeMobile="+$("#oppositemobile").val()
		                +"&intfType="+$("#intfType").val()
		                +"&pageSize=20"+"&currentPageNo="+($("#statistic_page_no").val()-1)*20,
		            dataType: "json",
		            type: "POST",
                    dataType: "json",
                    success : function(data) {
                        if(data.flag){
                            $("#downloadDialogStatistic").load("./statistic/sms_query_export_dialog.jsp",function(){
					    		$("#downloadStatisticExcel").attr("href", "javascript:downloadSmsQuery('<%=basePath%>fileDownload?fileName=./downloads/contacts/" +data.fileName+"')");
                                //$("#exportContactNum").html(exportCount);
					    	});
                        }else{
                            alert("导出失败，请稍后重试");
                        }
                    },
                    error : function() {
                        alert("导出出错，请稍后再试");
                    }
                });
            }
		});
        $("#statisticyear").change(function(){
            $("#statisticmonth").change();
        });
        $("#statisticmonth").change(function(){
            $("#statisticstartday").empty();
            $("#statisticendday").empty();
            switch($(this).val()){
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    for(var i = 0; i < 31; i++ ){
                        $("#statisticstartday").append("<option value='"+(i < 10 ? "0"+(i+1):(i+1))+"'>"+(i+1)+"日"+"</option>");
                        $("#statisticendday").append("<option value='"+(i < 10 ? "0"+(i+1):(i+1))+"'>"+(i+1)+"日"+"</option>");
                    }
                    break;
                case "02":
                    for(i = 0; i < 28; i++ ){
                        $("#statisticstartday").append("<option value='"+(i < 10 ? "0"+(i+1):(i+1))+"'>"+(i+1)+"日"+"</option>");
                        $("#statisticendday").append("<option value='"+(i < 10 ? "0"+(i+1):(i+1))+"'>"+(i+1)+"日"+"</option>");
                    }
                    if(parseInt($("#statisticyear").val()) %400 == 0 || (parseInt($("#statisticyear").val()) %4 == 0 && parseInt($("#statisticyear").val()) %100 != 0)){
                        $("#statisticstartday").append("<option value='"+29+"'>"+29+"日"+"</option>");
                        $("#statisticendday").append("<option value='"+29+"'>"+29+"日"+"</option>");
                    }
                    break;
                case "04":
                case "06":
                case "09":
                case "11": 
                    for(var j = 0; j < 30; j++ ){                        
                        $("#statisticstartday").append("<option value="+(j < 10 ? "0"+(j+1):(j+1)) +">"+(j+1)+"日"+"</option>");
                        $("#statisticendday").append("<option value="+(j < 10 ? "0"+(j+1):(j+1)) +">"+(j+1)+"日"+"</option>");
                    }
                    break;
            }
            $("#statisticendday").get(0).selectedIndex = $("#statisticendday").children().length-1 <0 ?0:$("#statisticendday").children().length-1;
        });
        $("#statisticmonth").change();
        $.ajax({ url: "./statisticAction/queryTunnels.action",
            dataType: "json",
            type: "GET",
            success: function(data){
                $("#accessnumber").empty();
                $("#accessnumber").append("<option value='0' cid='0'>全部</option>");
                $.each(data,function(index,item){
                    $("#accessnumber").append("<option value='"+item.accessNumber+"' cid='"+item.classify+"'>"+item.name+"</option>");
                });
            },
            error:function(data){
            }
        });
        $.ajax({ url: "./statisticAction/queryUsers.action",
            dataType: "json",
            type: "GET",
            success: function(data){
                $("#createby").empty();
                $.each(data,function(index,item){
                    $("#createby").append("<option value='"+item.id+"'>"+item.name+"</option>");
                });
            },
            error:function(data){
            }
        });
        $("#statisticSearch").click(function(){            
            if($("#statisticstartday").val() > $("#statisticsendday").val()) {
                alert("日期输入有误,开始日期不能大于结束日期");
                return;
            }
            pageQuery(true);
        });
        $("#statistic_pre_page,#statistic_pre_page_foot").click(function(){
            if($("#statistic_page_no").val() > 1){
                $("#statistic_page_no").val($("#statistic_page_no").val()-1);
                $("#statistic_cur_page").html($("#statistic_page_no").val());
                $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
                pageQuery(false);
            } else {
                $("#statistic_page_no").val(1);
                $("#statistic_cur_page").html($("#statistic_page_no").val());
                $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
            }
        });
        $("#statistic_next_page,#statistic_next_page_foot").click(function(){
            if($("#statistic_page_no").val() < $("#statistic_total_page").html()){
                $("#statistic_page_no").val(parseInt($("#statistic_page_no").val())+1);
                $("#statistic_cur_page").html($("#statistic_page_no").val());
                $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
                pageQuery(false);
            } else {
                $("#statistic_page_no").val($("#statistic_total_page").html());
                $("#statistic_cur_page").html($("#statistic_page_no").val());
                $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
            }
        });
        $("#statistic_jump_page").click(function(){
            if($("#statisticstartday").val() > $("#statisticsendday").val()) {
                alert("日期输入有误,开始日期不能大于结束日期");
                return;
            }
            $("#statisticjustPagefoot").val($("#statisticjustPage").val());
            $("#statistic_page_no").val($("#statisticjustPage").val());
            $("#statistic_cur_page").html($("#statistic_page_no").val());
            $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
            pageQuery(false);
        });        
        $("#statistic_jump_page_foot").click(function(){
            if($("#statisticjustPagefoot").val() < 1 || $("#statisticjustPagefoot").val() > $("#statistic_total_page").html()) {
                alert("输入页号有误!");
                return;
            }
            if($("#statisticjustPagefoot").val() == $("#statistic_page_no").val()) {
                return;
            }
            $("#statisticjustPage").val($("#statisticjustPagefoot").val());
            $("#statistic_page_no").val($("#statisticjustPage").val());
            $("#statistic_cur_page").html($("#statistic_page_no").val());
            $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
            pageQuery(false);
        });
        $("#statisticccount").click(function(){
            if($("#statisticstartday").val() > $("#statisticsendday").val()) {
                alert("日期输入有误,开始日期不能大于结束日期");
                return;
            }
            $.ajax({
                url:"./statisticAction/statisticSummary.action",
                data:"startTime="+$("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticstartday").val()
                    +"&endTime="+$("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticendday").val()
                    +"&classify="+$("#accessnumber").find("option:selected").attr("cid")
                    +"&accessNumber="+$("#accessnumber").val()+"&createBy="+$("#createby").val()
                    +"&communicationWay="+$("#communicationway").val()+"&sendResults="+$("#sendresults").val()
                    +"&smType="+$("#smtype").val()+"&oppositeMobile="+$("#oppositemobile").val()
                    +"&intfType="+$("#intfType").val(),
                dataType: "json",
                type: "POST",
                success: function(data){  
                    $("#smsendsummary").html(data.smSendSummary+"项");
                    $("#smsendsegmentssummary").html(data.smSendSegmentsSummary+"条");
                    $("#smsendsuccesssummary").html(data.smSendSuccessSummary+"条(占比"+data.smSuccessPercent+"%)");
                    $("#smsendfailursummary").html(data.smSendFailurSummary+"条(占比"+data.smFailurPercent+"%)");
                    $("#smreceivesummary").html(data.smReceiveSummary+"条");
                },
                error:function(data){
                }
            });
            $( "#dialog-message" ).dialog('open');
            $(".ui-icon-closethick").parent().hide();
            /*var tabid="smsummary";
            var taburl="./statistic/sms_count.jsp";
            var tabTitle="短信汇总统计";
            var navpathtitle="当前位置：短信统计> 短信汇总";
            $("#navpath").html(navpathtitle);
            var tabContentClone = $(".tabContent").clone();
            var jcTabs = tabContentClone.removeClass("tabContent").load(taburl);
            tabpanel.addTab({
                id: tabid,
                title: tabTitle ,     
                html:jcTabs,     
                closable: true
            });*/            
        });
        $("#dialog-message").dialog({
            autoOpen: false,
            modal:true,
            /*show: {
                effect: "blind",
                duration: 1000
            },
            hide: {
                effect: "explode",
                duration: 1000
            },*/
            buttons:[{text:'关闭',click:function(){
                        $(this).dialog('close');
                    }}],
            minWidth: 700
        });
        $("#communicationway").change(function(){
            if($(this).val() == "1"){
                $("#createby").attr("disabled",true);
                $("#sendresults").attr("disabled",true);
                $("#smtype").attr("disabled",true);
            } else {
                $("#createby").attr("disabled",false);
                $("#sendresults").attr("disabled",false);
                $("#smtype").attr("disabled",false);
            }
        });
    });
    function pageQuery(isSearch){
        if(isSearch) {
            $("#statistic_page_no").val(1);
        }
        var startTime = $("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticstartday").val();
        var endTime = $("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticendday").val();
        $.ajax({
            url: "./statisticAction/statisticQuery.action",
            data:"startTime="+startTime                
                +"&endTime="+endTime
                +"&classify="+$("#accessnumber").find("option:selected").attr("cid")
                +"&accessNumber="+$("#accessnumber").val()+"&createBy="+$("#createby").val()
                +"&communicationWay="+$("#communicationway").val()+"&sendResults="+$("#sendresults").val()
                +"&smType="+$("#smtype").val()+"&oppositeMobile="+$("#oppositemobile").val()
                +"&intfType="+$("#intfType").val()
                +"&pageSize=20"+"&currentPageNo="+($("#statistic_page_no").val()-1)*20,
            dataType: "json",
            type: "POST",
            success: function(data){
                //设置表格数据
                $("#statisticbody tr").remove();
                if(data.total > 0) {
                    //设置翻页相关信息
                    $("#statistic_cur_page").html($("#statistic_page_no").val());
                    $("#statistic_total_page").html(Math.floor((data.total-1)/20)+1);
                    $("#statistic_cur_page_foot").html($("#statistic_page_no").val());
                    $("#statistic_total_page_foot").html(Math.floor((data.total-1)/20)+1);                    
                    if(data.data != null) {
                        $.each(data.data,function(index,item){   
                            //使用jquery拷贝方式生成行数据
                            $("#statisticrowtemplate").clone(true).appendTo("#statisticbody");
                            $("#statisticbody tr:last").show();
                            $("#statisticbody tr:last td").eq(0).html(item.accountName);
                            $("#statisticbody tr:last td").eq(1).html(item.smTime);
                            $("#statisticbody tr:last td").eq(2).html(item.communicationWay);        
                            $("#statisticbody tr:last td").eq(3).html(item.smType);
                            $("#statisticbody tr:last td").eq(4).html(item.intfType);
                            $("#statisticbody tr:last td").eq(5).html(item.oppositeMobile);
                            $("#statisticbody tr:last td").eq(6).html(item.smSegments);
                            $("#statisticbody tr:last td").eq(7).html(item.result);
                            $("#statisticbody tr:last td").eq(8).html(item.failurReason);
                        });
                    }
                    //查询结果总数
                	$("#allCounts").html(data.total);
                	$("#statisticsTotal").html("共查询到"+data.total+"条记录！");
                	$("#allCounts_foot").html(data.total);
                	var current_no = parseInt( $("#statistic_page_no").val() ) * 20 ;
                	var from_no = current_no-1 * 20 + 1;
                	if( current_no > data.total ){
                		$("#fromEnd").html(from_no + "-" +data.total);
                		$("#fromEnd_foot").html(from_no + "-" +data.total);
                	}else{
                		$("#fromEnd").html(from_no + "-" + current_no);
                		$("#fromEnd_foot").html(from_no + "-" + current_no);
                	}
                } else {
                	//查询结果总数
                	$("#allCounts").html(0);
                	$("#statisticsTotal").html("共查询到0条记录！");
                	$("#fromEnd").html("0-0");
                	$("#allCounts_foot").html(0);
                	$("#fromEnd_foot").html("0-0");
                	//
                    $("#statistic_cur_page").html("1");
                    $("#statistic_total_page").html("1");
                    $("#statistic_cur_page_foot").html("1");
                    $("#statistic_total_page_foot").html("1");
                }
            },
            error:function(data){
            }
        });        
    }
</script>