<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
		
    </head>
    <body>
        
      
        <div class="gridwrapper" style="overflow-y: auto;overflow-x: hidden; margin:5px; width:99%;">
            <table id="contactGrid" >
                <thead>
                 <tr id="">
                        <td style="width:50px;"></td>
                        <td></td>
                        <td style="width:100px;"></td>
                        <td style="width:150px;"></td>
                    </tr>   
                    <tr class="tableopts">
                        <td colspan="2" class="btn">
                            <a href="javascript:void(0);"  id="">返回</a>
                            <a href="javascript:void(0);"  id="">删除</a>
                        </td>
                        <td colspan="2" id="contactpaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="contactprepage">上一页</a>
                            <span id="contactcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="contacttotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="contactnextpage">下一页</a>
                            <a href="javascript:void(0)" id="contactjumppage">跳转</a>
                            <input type="text" id="contactenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                    <tr style="height: 30px">
                        <td colspan="4" align="left">
                            <input type="text" value="输入手机号码查询..." style="width: 150px" id=""/>
							<input type="text" style="width: 150px" value="输入举报短信内容查询..." id=""/>
							<input type="text" style="width: 150px" class="Wdate" value="举报日期" id=""/>
							<select><option>是否处理</option><option>是</option><option>否</option></select>
                            <input type="image" id="queryContact" src="./themes/mas3admin/images/address/address_search.gif" alt="查询" title="查询"/></td>
                    </tr>
                    <tr>
                        <th class="tabletrhead"><input type="checkbox" name="contactsselect" id="contactsselect" /></th>
                        <th class="tabletrhead" colspan="2">回复内容</th>
                        <th class="tabletrhead">回复日期</th>
                    </tr>          
                </thead>
                <tbody id="">
					<tr class="tabletrboby" >
                        <td><input type="checkbox" name="contactsselect" id="" /></td>
                        <td colspan="2"><a href="">举报内容</a></td>
                        <td>2013-08-23 14:00:00</td>
                    </tr> 
                    <tr class="tabletrboby" >
                        <td><input type="checkbox" name="contactsselect" id="" /></td>
                        <td colspan="2"><a href="">举报内容122233日日日日日日日日日日日日日日是</a></td>
                        <td>2013-08-23 14:00:00</td>
                    </tr>   
                </tbody>
                <tfoot>
                    <tr class="tableopts">
                        <td colspan="2" class="btn">
                            <a href="javascript:void(0);"  id="footdeletecontact">删除</a>
                            <a href="javascript:void(0);"  id="footexportcontact">导出查询结果</a>
                        </td>
                        <td colspan="2" id="footcontactpaginate" style="text-align: right">
                            <a href="javascript:void(0)" id="footcontactprepage">上一页</a>
                            <span id="footcontactcurpage" style="margin: 0;padding: 0;" >1</span>
                            <span style="margin: 0 -5px 0 -5px;">/</span>
                            <span id="footcontacttotalpages" style="margin: 0;padding: 0;"></span>
                            <a href="javascript:void(0)" id="footcontactnextpage">下一页</a>
                            <a href="javascript:void(0)" id="footcontactjumppage">跳转</a>
                            <input type="text" id="footcontactenterpage" value="1" style="width:40px;" />
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>

       

    
