package com.leadtone.mas.bizplug.calendar.dao;

import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTask;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskExample;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MbnPeriodcTaskDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    void insert(MbnPeriodcTask record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByPrimaryKey(MbnPeriodcTask record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByPrimaryKeySelective(MbnPeriodcTask record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    List selectByExample(MbnPeriodcTaskExample example) throws SQLException;

    /**
     * 根据后台任务查询需处理的周期事件
     * @param mbnPeriodcTask
     * @return
     * @throws SQLException
     */
    public List selectTaskList(Map<String,Object> paraMap)throws SQLException;
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    MbnPeriodcTask selectByPrimaryKey(Long id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int deleteByExample(MbnPeriodcTaskExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int deleteByPrimaryKey(Long id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int countByExample(MbnPeriodcTaskExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByExampleSelective(MbnPeriodcTask record, MbnPeriodcTaskExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByExample(MbnPeriodcTask record, MbnPeriodcTaskExample example) throws SQLException;
}