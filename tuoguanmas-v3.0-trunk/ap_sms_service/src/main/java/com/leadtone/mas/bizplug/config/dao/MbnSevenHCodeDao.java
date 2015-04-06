package com.leadtone.mas.bizplug.config.dao;

import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseIDao;

public interface MbnSevenHCodeDao extends ConfigBaseIDao<MbnSevenHCode, Long> {

	public MbnSevenHCode queryByBobilePrefix(String prefix);

}
