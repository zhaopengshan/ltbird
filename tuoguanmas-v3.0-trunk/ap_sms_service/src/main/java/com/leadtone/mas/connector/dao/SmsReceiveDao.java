package com.leadtone.mas.connector.dao;

import java.util.List;

import com.leadtone.mas.connector.domain.SmsReceive;
/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public interface SmsReceiveDao {
	List<SmsReceive> loadByPk(String accessNumber);
	List<SmsReceive> loadByQxtUserId(Integer userId);
	List<String> loadAccByUid(String uid);
	void updateSmsStatus(String Uid);
	/**
	 * 查询需要数据库同步的短信
	 * @param accessNumberList 接入号列表
	 * @return
	 */
	List<SmsReceive> loadDbSyncSmsList(List<String> accessNumberList);
	/**
	 * 更新数据库同步短信状态
	 * @param id
	 */
	void updateDbSyncSmsStatus(Long id);
	List<SmsReceive> loadDbSyncSmsByAccessNumber(String accessNumber, String type);
	List<SmsReceive> loadDbSyncSmsByUid(Long userId, String type);
}
