package com.leadtone.mas.bizplug.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 分页公用类
 *
 * @author ChangJun
 * @since 2011-09-14
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	// 默认每页条数，10条
	protected static final int DEFAULT_PAGE_SIZE = 50;

	private int pageSize = DEFAULT_PAGE_SIZE;
	// 当前数据开始的页数
	private int start;
	// 总共的数据条数
	private int total;
	// 显示的数据
	private Object data;

	/**
	 * @param pageSize
	 * @param start
	 * @param total
	 * @param data
	 */
	public Page(int pageSize, int start, int total, Object data ) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.total = total;
		this.data = data;
	}

	public Page() {
		this(DEFAULT_PAGE_SIZE, 0, 0, new ArrayList());
	}

	/**
	 * 根据每页默认显示条数获取本页开始的条数
	 *
	 * @param pageNo
	 * @return
	 */
	public static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 根据参数获取本页开始的条数
	 *
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * 默认每页条数，10条
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 默认每页条数，10条
	 * @return
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 当前数据开始的页数
	 * @param start
	 */
	public int getStart() {
		if(start > 0){
			return start;
		}else{
			return 1;
		}
	}
	
	public int getStartPage(){
		if(start > 0 ){
			return (start -1) * pageSize;
		}else{
			return 0;
		}
	}
	public int getRecords(){
		if( this.total % this.pageSize == 0){
			return this.total / this.pageSize;
		}else{
			return (int)(this.total / this.pageSize) + 1;
		}
	}
	/**
	 * 当前数据开始的页数
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
