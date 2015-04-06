package com.leadtone.mas.bizplug.calendar.dao;

import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskSms;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskSmsExample;
import java.sql.SQLException;
import java.util.List;

public interface MbnPeriodcTaskSmsDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    void insert(MbnPeriodcTaskSms record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByPrimaryKeyWithoutBLOBs(MbnPeriodcTaskSms record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByPrimaryKeyWithBLOBs(MbnPeriodcTaskSms record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByPrimaryKeySelective(MbnPeriodcTaskSms record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    List selectByExampleWithoutBLOBs(MbnPeriodcTaskSmsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    List selectByExampleWithBLOBs(MbnPeriodcTaskSmsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    MbnPeriodcTaskSms selectByPrimaryKey(Long id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int deleteByExample(MbnPeriodcTaskSmsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int deleteByPrimaryKey(Long id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int countByExample(MbnPeriodcTaskSmsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByExampleSelective(MbnPeriodcTaskSms record, MbnPeriodcTaskSmsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByExampleWithBLOBs(MbnPeriodcTaskSms record, MbnPeriodcTaskSmsExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table mbn_periodc_task_sms
     *
     * @abatorgenerated Fri Jan 25 10:45:02 CST 2013
     */
    int updateByExampleWithoutBLOBs(MbnPeriodcTaskSms record, MbnPeriodcTaskSmsExample example) throws SQLException;
}