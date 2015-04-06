package com.leadtone.mas.bizplug.security.dao;

import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;

public interface PortalUserExtDao {
	/**
	 * 查询用户扩展信息
	 * @param id
	 * @return
	 */
	public PortalUserExtBean getByPk(Long id);

	/**
	 * 保存用户扩展信息
	 * @param bean
	 * @return
	 */
	public boolean save(PortalUserExtBean bean);

	/**
	 * 删除用户扩展信息
	 * @param id
	 * @return
	 */
	public boolean delete(Long id);

	/**
	 * 更新用户扩展信息
	 * @param bean
	 * @return
	 */
	public boolean update(PortalUserExtBean bean);
}
