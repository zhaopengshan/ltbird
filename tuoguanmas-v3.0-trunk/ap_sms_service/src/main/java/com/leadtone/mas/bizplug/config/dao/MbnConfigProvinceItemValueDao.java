/**
 * 
 */
package com.leadtone.mas.bizplug.config.dao; 
import java.util.List;

import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItem;
import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItemValue;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseIDao;

/**
 * @author PAN-Z-G
 *
 */ 
public interface MbnConfigProvinceItemValueDao extends ConfigBaseIDao<MbnConfigProvinceItemValue, Long>{
	public List<MbnConfigProvinceItem> getProvinceItemInfo(String province);
}
