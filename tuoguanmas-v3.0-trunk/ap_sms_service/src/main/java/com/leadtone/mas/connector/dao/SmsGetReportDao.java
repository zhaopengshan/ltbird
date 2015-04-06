package com.leadtone.mas.connector.dao;

import java.util.List;

import com.leadtone.mas.connector.domain.SmsGetReport;
/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public interface SmsGetReportDao {
	List<SmsGetReport> loadbyPk(List<String> uid);
	/**
	 * 查询发送结果
	 * @param id
	 * @return
	 */
	SmsGetReport loadById(Long id);
	SmsGetReport loadByIdReady(Long id);
}
