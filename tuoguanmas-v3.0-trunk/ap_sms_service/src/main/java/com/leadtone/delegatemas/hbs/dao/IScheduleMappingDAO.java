/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.hbs.dao;

import com.leadtone.delegatemas.hbs.bean.ScheduleMapping;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public interface IScheduleMappingDAO {
    public void batchSave(List<ScheduleMapping> mappings);
    public void batchUpdate(List<ScheduleMapping> mappings);
    public void batchDelete(List<ScheduleMapping> mappings);
}
