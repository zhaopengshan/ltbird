/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.statistic.action;

import com.leadtone.delegatemas.statistic.bean.SmQueryResult;
import com.leadtone.delegatemas.statistic.bean.SmSummary;
import com.leadtone.delegatemas.statistic.service.IStatisticService;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.bizplug.addr.bean.ContactVO;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.ex.export.DataExport;
import com.leadtone.mas.bizplug.security.action.BaseAction;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 *
 * @author blueskybluesea
 */
@ParentPackage("json-default")
@Namespace(value = "/statisticAction")
public class StatisticAction extends BaseAction {

    private Date startTime;
    private Date endTime;
    private String accessNumber;
    private Long createBy;
    private String communicationWay;
    private Long sendResults;
    private String smType;
    private String oppositeMobile;
    private Integer intfType;
    private Integer classify;
    private int pageSize;
    private int currentPageNo;
    @Resource
    private SmsMbnTunnelService smsMbnTunnelService;
    @Resource
    private UserService userService;
    @Resource
    private IStatisticService statisticServiceImpl;
    private List<MasTunnel> tunnels;
    private List<Users> users;
    private Page pages;
    private SmSummary smSummary;
    private HttpServletRequest request = ServletActionContext.getRequest();

    @Action(value = "queryTunnels", results = {
        @Result(type = "json", params = {
            "root", "tunnels", "contentType", "text/html"})})
    public String getMerchantTunnels() {
        Users u = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
        tunnels = smsMbnTunnelService.getMasTunnelsByMerchantPin(u.getMerchantPin());
        return SUCCESS;
    }

    @Action(value = "queryUsers", results = {
        @Result(type = "json", params = {
            "root", "users", "contentType", "text/html"})})
    public String getMerchantUsers() {
        Users u = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
        if (u.getUserType() == 3) {
            UserVO userVO = new UserVO();
            userVO.setMerchantPin(u.getMerchantPin());
            users = userService.getUserBycorpAccessNumber(userVO);
            Users allUser = new Users();
            allUser.setName("全部用户");
            allUser.setId(0L);
            users.add(0, allUser);
        } else {
            users.add(u);
        }
        return SUCCESS;
    }

    @Action(value = "statisticQuery", results = {
        @Result(type = "json", params = {
            "root", "pages", "contentType", "text/html"})})
    public String statisticQuery() {
        Users u = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
        pages = statisticServiceImpl.statisticQuery(u.getMerchantPin(), startTime, endTime, classify,
        		accessNumber, createBy, communicationWay, sendResults, smType, 
        		oppositeMobile,intfType, pageSize, currentPageNo);
        return SUCCESS;
    }
    
    @Action(value = "statisticQueryExport", results = {
            @Result(type = "json", params = {
                "root", "entityMap", "contentType", "text/html"})})
    public String statisticQueryExport() {
    	try{
    		Users u = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
            List<SmQueryResult> queryResult = statisticServiceImpl.statisticQueryExport(u.getMerchantPin(), 
            		startTime, endTime, classify, accessNumber, createBy, communicationWay, sendResults, smType, 
            		oppositeMobile, intfType, pageSize, currentPageNo);
            String downLoadPath = exportToExcel(queryResult);
            entityMap = new HashMap<String, Object>();
            entityMap.put("fileName", downLoadPath);
    		entityMap.put("flag", true);
    	} catch (Exception e) {
			logger.error("export contact error", e);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", false);
		}
    	return SUCCESS;
    }
    
    private String[][] listToArr(List<SmQueryResult> smQueryResult) {
		String[][] billsInArr = new String[smQueryResult.size()][10];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int j = 0; j < smQueryResult.size(); j++) {
			SmQueryResult queryResult = smQueryResult.get(j);
			billsInArr[j][0] = queryResult.getAccountName();
			billsInArr[j][1] = sdf.format( queryResult.getSmTime() );
			billsInArr[j][2] = queryResult.getCommunicationWay();
			billsInArr[j][3] = queryResult.getSmType();
			billsInArr[j][4] = queryResult.getIntfType();
			billsInArr[j][5] = queryResult.getOppositeMobile();
			billsInArr[j][6] = String.valueOf( queryResult.getSmSegments() );
			billsInArr[j][7] = queryResult.getResult();
			billsInArr[j][8] = queryResult.getFailurReason();
			billsInArr[j][9] = queryResult.getContent();
		}

		return billsInArr;
	}
    /**
	 * 导出excel
	 * 
	 * 返回下载文件名
	 */
	private String exportToExcel(List<SmQueryResult> smQueryResults) {
		String strFileFlag = DataExport.EXCEL_XLS;
		long start = System.currentTimeMillis();
		String strFileName = null;
		if (smQueryResults != null && !smQueryResults.isEmpty()) {
			String exportFile = "";

			String[] cols = { "发送用户", "短信时间", "通信方式", "短信类型", "发送接口", "对方号码", "拆分条数",
					"发送结果", "失败原因", "短信内容"};
			String[][] contents = this.listToArr(smQueryResults);

			// 获取应用的绝对路径
			String appPath = this.request.getRequestURL().toString();
			appPath = appPath.substring(0, appPath.lastIndexOf("/"));

			// 获取导出文件所在的目录的本地路径,以用户生成新的文件
			String filePath = File.separator + "downloads" + File.separator
					+ "contacts";
			String localDir = request.getSession().getServletContext()
					.getRealPath(filePath)
					+ File.separator;
			// 获取导出文件所在的网络路径
			String downLoadPath = appPath + filePath.replaceAll("\\\\", "/");
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				strFileName = sdf.format(new Date()) + strFileFlag;

				exportFile = new DataExport().exportToFile(localDir,
						strFileName, contents, cols, strFileFlag);// 默认导出.xls格式的文件
				downLoadPath = filePath
						+ File.separator
						+ exportFile.substring(exportFile
								.lastIndexOf(File.separator) + 1);
				// this.request.setAttribute("exportFile", downLoadPath);
				// / this.request.setAttribute("message", "客户资料导出成功！");
			} catch (Exception e) {
				logger.error("export contact error,", e);
				long end = System.currentTimeMillis();
				logger.info(" export contact,totally use " + (end - start)
						+ " millseconds ");
			}
		}
		long end = System.currentTimeMillis();
		logger.info("export contact,totally use " + (end - start)
				+ " millseconds ");
		return strFileName;
	}

    @Action(value = "statisticSummary", results = {
        @Result(type = "json", params = {
            "root", "smSummary", "contentType", "text/html"})})
    public String statisticSummary() {
        Users u = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
        int adminAttr = u.getUserType();
        boolean isAdmin = false;
        if (adminAttr == 3) {
            isAdmin = true;
        }
        smSummary = statisticServiceImpl.statisticSummary(u.getMerchantPin(), startTime, endTime, classify,
        		accessNumber, createBy, intfType, isAdmin);
        return SUCCESS;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getCommunicationWay() {
        return communicationWay;
    }

    public void setCommunicationWay(String communicationWay) {
        this.communicationWay = communicationWay;
    }

    public String getSmType() {
        return smType;
    }

    public void setSmType(String smType) {
        this.smType = smType;
    }

    public String getOppositeMobile() {
        return oppositeMobile;
    }

    public void setOppositeMobile(String oppositeMobile) {
        this.oppositeMobile = oppositeMobile;
    }

    public Integer getIntfType() {
		return intfType;
	}

	public void setIntfType(Integer intfType) {
		this.intfType = intfType;
	}

	public Long getSendResults() {
        return sendResults;
    }

    public void setSendResults(Long sendResults) {
        this.sendResults = sendResults;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public List<MasTunnel> getTunnels() {
        return tunnels;
    }

    public void setTunnels(List<MasTunnel> tunnels) {
        this.tunnels = tunnels;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Page getPages() {
        return pages;
    }

    public void setPages(Page pages) {
        this.pages = pages;
    }

    public SmSummary getSmSummary() {
        return smSummary;
    }

    public void setSmSummary(SmSummary smSummary) {
        this.smSummary = smSummary;
    }

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}
}
