package com.leadtone.mas.admin.security.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	// 请求的URL地址
	protected String url;

	private static final long serialVersionUID = 1L;
	// 是否为get请求
	protected boolean isConditionGet = false;
	protected final transient Log logger = LogFactory.getLog(super.getClass());
	// 请求的方法名
	protected String method;
	/**
	 *@param 保存分页信息
	 * */
	protected Map<String, Object> entityMap;
	/**
	 * 当前页面,默认是1
	 */
	protected int page;

	/**
	 * 每页显示多少条数据，默认是值见PageUtil
	 */
	protected int rows;

	/** @return date pattern. */
	protected String getDatePattern() {
		return "yyyy-MM-dd";
	}

	/**
	 * struts2提供的校验方法，测试用.
	 */
	@Override
	public void validate() {
		logger.debug(this.getActionErrors());
		// logger.debug(this.getErrors());
	}

	/** @return excludes. */
	protected String[] getExcludes() {
		return new String[] { "", "" };
	}

	public void toHtml(String jsonStr) {
		try {
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("application/json; charset=UTF-8");
			// getResponse().setContentType("text/html; charset=utf-8");
			System.out.println("jsonStr----------" + jsonStr);
			out.print(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * object to json.
	 * 
	 * @param object
	 *            Object
	 * @throws Exception
	 *             ex
	 */
	protected void toJson(Object object) throws Exception {

	}

	/**
	 * 获得HttpServletRequest引用
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得HttpServletResponse引用
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得HttpSession引用
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获得ServletContext引用
	 * 
	 * @return
	 */
	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 获得请求参数的名值对Map集合
	 * 
	 * @return
	 */
	protected Map getParameterMap() {
		return ServletActionContext.getContext().getParameters();
	}

	/**
	 * 获得Session范围参数的名值对Map集合
	 * 
	 * @return
	 */
	protected Map getSessionMap() {
		return ServletActionContext.getContext().getSession();
	}

	/**
	 * 获得Application范围参数的名值对Map集合
	 * 
	 * @return
	 */
	protected Map getApplicationMap() {
		return ServletActionContext.getContext().getApplication();
	}

	/**
	 * 获得指定参数名的请求参数的值
	 * 
	 * @return
	 */
	protected Object getParameterValue(String name) {
		return this.getParameterMap().get(name);
	}

	/**
	 * 获得指定参数名的Session范围的参数的值
	 * 
	 * @return
	 */
	protected Object getSessionValue(String name) {
		return this.getSessionMap().get(name);
	}

	/**
	 * 获得指定参数名的Application范围的参数的值
	 * 
	 * @return
	 */
	protected Object getApplicationValue(String name) {
		return this.getApplicationMap().get(name);
	}

	/**
	 * 获得指定对象的JSON字符串表示
	 * 
	 * @return
	 */
	protected String getJsonString(Object object) throws Exception {
		return "";
	}

	protected String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}

	protected String getMethod() {
		return method;
	}

	protected void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 将不带查询条件的语句与查询条件拼成查询SQL文
	 */
	public String contactSql(String sqlNoCondition, String searchCondition) {

		if (null == searchCondition || "".equals(searchCondition)) {
			return sqlNoCondition;
		} else {
			return sqlNoCondition + searchCondition;
		}
	}

	/**
	 * 将查询条件内容保存到Session中
	 */
	public void saveConditionToSession(Map conditionMap) {

		this.getSession().setAttribute("conditionMap", conditionMap);
	}

	public Map<String, Object> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map<String, Object> entityMap) {
		this.entityMap = entityMap;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
