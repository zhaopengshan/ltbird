<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragram" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
        <link  type="text/css" rel="stylesheet"  href="statistic/style.css" />
        <title>短信统计</title>
    </head>
    <body>
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
    </body>
</html>
<script language="javascript" type="text/javascript">
    $.ajax({
        url:"./statisticAction/statisticSummary.action",
        data:"startTime="+$("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticstartday").val()
            +"&endTime="+$("#statisticyear").val()+"-"+$("#statisticmonth").val()+"-"+$("#statisticendday").val()
            +"&accessNumber="+$("#accessnumber").val()+"&createBy="+$("#createby").val()
            +"&communicationWay="+$("#communicationway").val()+"&sendResults="+$("#sendresults").val()
            +"&smType="+$("#smtype").val()+"&oppositeMobile="+$("#oppositemobile").val(),
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
</script>