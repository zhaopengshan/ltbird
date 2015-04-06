package com.leadtone.mas.bizplug.config.dao; 
import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItem;
import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItemValue;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseDaoImpl;
@Repository
public class MbnConfigProvinceItemValueDaoImpl extends ConfigBaseDaoImpl<MbnConfigProvinceItemValue,Long> implements
		MbnConfigProvinceItemValueDao { 
	@Override
	public List<MbnConfigProvinceItem> getProvinceItemInfo(
			String province) {
		return this.getSqlMapClientTemplate().queryForList("MbnConfigProvinceItemValue.queryItems", province);
	}
}
