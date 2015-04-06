package com.leadtone.mas.bizplug.config.dao;

import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseIDao;

public interface MbnThreeHCodeDao extends ConfigBaseIDao<MbnThreeHCode, Long> {

	public MbnThreeHCode queryByBobilePrefix(String prefix);

}
