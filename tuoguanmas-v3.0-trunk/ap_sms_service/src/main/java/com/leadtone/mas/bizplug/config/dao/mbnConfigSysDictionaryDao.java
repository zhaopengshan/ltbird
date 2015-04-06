/**
 * 
 */
package com.leadtone.mas.bizplug.config.dao; 
import com.leadtone.mas.bizplug.config.bean.MbnConfigSysDictionary;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseIDao; 
/**
 * @author PAN-Z-G
 *
 */
public interface mbnConfigSysDictionaryDao extends ConfigBaseIDao< MbnConfigSysDictionary, Long>{
	/**
	 * 根据省编码查询obj
	 * @param coding
	 * @return
	 */
	 MbnConfigSysDictionary  getByCoding(String coding); 
}
