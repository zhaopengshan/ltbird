<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String pat = request.getContextPath();
    String baseP = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + pat + "/";
%>
<script>
    var CONTEXT_PATH = '${ctx}';
</script>
<script src="${ctx}/common/js/menu.js" type="text/javascript"></script>

<!-- Save for Web Slices (index-qie.jpg) -->
<table width="888"  border="0" align="center" cellpadding="0" cellspacing="0" id="Table_01" class="Table_01">
<tr>
    <td height="113" colspan="14" rowspan="0" valign="top" bgcolor="#E6F2FB">
        <table id="Table_" width="888" height="100%"  border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="8"><img src="${ctx}/common/images/head_01.jpg" width="100%" height="106" alt=""/></td>
            </tr>
            <tr>
                <td><img src="${ctx}/common/images/head_02.jpg" width="148" height="40" alt=""/></td>
                <td><img onclick="firstPage('<%=baseP%>'+'login.jsp')" src="${ctx}/common/images/head_03.jpg" width="115" height="40" alt=""/></td>
                <td><img src="${ctx}/common/images/head_04_01.jpg" alt="" width="119" height="40" id="Image1" onMouseOver="MM_swapImage('Image1','','${ctx}/common/images/head_04.jpg',1)" onMouseOut="MM_swapImgRestore()"/></td>
                <td><img src="${ctx}/common/images/head_05_01.jpg" alt="" width="118" height="40" id="Image2" onMouseOver="MM_swapImage('Image2','','${ctx}/common/images/head_05.jpg',1)" onMouseOut="MM_swapImgRestore()"/></td>
                <td><img src="${ctx}/common/images/head_06_01.jpg" alt="" width="119" height="40" id="Image3" onMouseOver="MM_swapImage('Image3','','${ctx}/common/images/head_06.jpg',1)" onMouseOut="MM_swapImgRestore()"/></td>
                <td><img src="${ctx}/common/images/head_07_01.jpg" onclick="javascript:download('${ctx}/download.jsp')" alt="" width="119" height="40" id="Image4" onMouseOver="MM_swapImage('Image4','','${ctx}/common/images/head_07.jpg',1)" onMouseOut="MM_swapImgRestore()"/></td>
                <td><img src="${ctx}/common/images/head_08_01.jpg" alt="" width="121" height="40" id="Image5" onMouseOver="MM_swapImage('Image5','','${ctx}/common/images/head_08.jpg',1)" onMouseOut="MM_swapImgRestore()"/></td>
                <td><img src="${ctx}/common/images/head_09.jpg" width="29" height="40" alt=""/></td>
            </tr>
            <tr>
                <td height="13" colspan="8">&nbsp;</td>
            </tr>
        </table>
    </td>
</tr>
</table>