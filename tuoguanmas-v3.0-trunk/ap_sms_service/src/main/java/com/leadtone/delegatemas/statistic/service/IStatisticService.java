/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.statistic.service;

import com.leadtone.delegatemas.statistic.bean.SmQueryResult;
import com.leadtone.delegatemas.statistic.bean.SmSummary;
import com.leadtone.mas.bizplug.common.Page;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author blueskybluesea
 */
public interface IStatisticService {
	/**
	 * 查询详情（增加发送接口类型字段 intfType 1WEB 2WebService 3DB）
	 * @param merchantPin
	 * @param startTime
	 * @param endTime
	 * @param accessNumber
	 * @param userId
	 * @param communicationWay
	 * @param sendResult
	 * @param smType
	 * @param targetMobile
	 * @param intfType
	 * @param pageSize
	 * @param currentPageNo
	 * @return
	 */
	public Page statisticQuery(Long merchantPin, Date startTime, Date endTime,Integer classify,
			String accessNumber, Long userId, String communicationWay,
			Long sendResult, String smType, String targetMobile,
			Integer intfType, Integer pageSize, Integer currentPageNo);

	/**
	 * 导出查询结果（增加发送接口类型字段 intfType 1WEB 2WebService 3DB）
	 * @param merchantPin
	 * @param startTime
	 * @param endTime
	 * @param accessNumber
	 * @param userId
	 * @param communicationWay
	 * @param sendResult
	 * @param smType
	 * @param targetMobile
	 * @param intfType
	 * @param pageSize
	 * @param currentPageNo
	 * @return
	 */
	public List<SmQueryResult> statisticQueryExport(Long merchantPin,
			Date startTime, Date endTime,Integer classify, String accessNumber, Long userId,
			String communicationWay, Long sendResult, String smType,
			String targetMobile, Integer intfType, Integer pageSize,
			Integer currentPageNo);

	/**
	 * 统计（增加发送接口类型字段 intfType 1WEB 2WebService 3DB）
	 * @param merchantPin
	 * @param startTime
	 * @param endTime
	 * @param accessNumber
	 * @param userId
	 * @param intfType
	 * @param isAdmin
	 * @return
	 */
	public SmSummary statisticSummary(Long merchantPin, Date startTime,
			Date endTime, Integer classify, String accessNumber, Long userId, Integer intfType,
			boolean isAdmin);
}
