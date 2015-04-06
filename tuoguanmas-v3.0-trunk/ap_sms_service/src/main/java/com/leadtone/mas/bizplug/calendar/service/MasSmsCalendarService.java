package com.leadtone.mas.bizplug.calendar.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTask;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskSms;
import com.leadtone.mas.bizplug.calendar.bean.MbnSmsDuanxinrili;

public interface MasSmsCalendarService {
	/**
	 * 创建日历信息
	 * @param mbnSmsDuanxinrili
	 * @param mbnPeriodcTaskSms
	 * @param mbnPeriodcTask
	 * @throws SQLException
	 */
	public void createRiliInfo(MbnSmsDuanxinrili mbnSmsDuanxinrili,MbnPeriodcTaskSms mbnPeriodcTaskSms,MbnPeriodcTask mbnPeriodcTask) throws SQLException;

	
	/**
	 * 分页查询日历提醒信息
	 * @param mapPara
	 * @param listMbnSmsDuanxinrili
	 * @return
	 */
	public List<MbnSmsDuanxinrili> getAllCalendarList(Map<String,Object> mapPara);
	
	
	public int getAllCalendarCount(Map<String,Object> mapPara);
	
	public void deleteCalendarById(List<Long> calendarIds) throws SQLException;
	/**
	 * 根据ID查询提醒信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public MbnSmsDuanxinrili getMbnSmsDuanxinriliById(Long id) throws SQLException;
	/**
	 * 获取周期任务列表
	 * @param mbnPeriodcTask
	 * @return
	 */
	public List<MbnPeriodcTask> getPeriodcTaskList(Map<String,Object> paraMap);
	
	public int updatePeriodcTask(MbnPeriodcTask mbnPeriodcTask);
	
	
	public void taskSendMsg(MbnPeriodcTask mbnPeriodcTask);
}
