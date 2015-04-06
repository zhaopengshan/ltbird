/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.merchant.dao.impl;

import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.delegatemas.merchant.dao.IMasMerchantDAO;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author blueskybluesea
 */
@Repository("masMerchantDAOImpl")
public class MasMerchantDAOImpl extends BaseDao implements IMasMerchantDAO,MbnMerchantVipIDao{
    private String namespace="MbnMerchantVip";
    @Resource(name="mbnMerchantVipIDao")
    private MbnMerchantVipIDao merchantVipDao;
    @Override
    public Page page(Map<String, Object> paraMap) {
//        List merchants = this.getSqlMapClientTemplate().queryForList(namespace+".queryMasMerchantByCondition", paraMap);
//        Integer recordCount = (Integer)getSqlMapClientTemplate().queryForObject(namespace + ".pageMasCountMerchant", paraMap);
//        Page page = new Page();
//        page.setData(merchants);
//        page.setTotal(recordCount);
//        return page;
    	Integer recordCount = (Integer)getSqlMapClientTemplate().queryForObject(namespace + ".pageMasCountMerchant", paraMap);
        Object data = null;
        if (recordCount != 0) {
        	try{
        		data = this.getSqlMapClientTemplate().queryForList(namespace+".queryMasMerchantByCondition", paraMap);
        	}catch (Exception e){
        		e.printStackTrace();
        	}
        }
//        Page page = new Page();
//        page.setData(data);
//        page.setTotal(recordCount);
//        page.setPageSize((Integer)paraMap.get("pageSize"));
//        page.setStart((Integer)paraMap.get("startPage"));
        return new Page((Integer)paraMap.get("pageSize"), (Integer)paraMap.get("startPage"), recordCount, data);
    }

    @Override
    public List<PointsCityStatistic> merchantCountByRegion(Long regionId) {
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("province", regionId);
        return this.getSqlMapClientTemplate().queryForList(namespace+".queryPointsCityStatistic", paraMap);
    }

    @Override
    public boolean insert(MbnMerchantVip merchantVip) {
       return merchantVipDao.insert(merchantVip);
    }

    @Override
    public boolean update(MbnMerchantVip merchantVip) {
        return merchantVipDao.update(merchantVip);
    }

    @Override
    public MbnMerchantVip load(long merchantPin) {
        return merchantVipDao.load(merchantPin);
    }

    @Override
    public MbnMerchantVip loadByName(String name) {
        return merchantVipDao.loadByName(name);
    }

    @Override
    public boolean updateBatch(List<Long> pins, String smsState) {
        return merchantVipDao.updateBatch(pins, smsState);
    }

    @Override
    public Page page(PageUtil pageUtil) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer pageCount(Map<String, Object> paraMap) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public MbnMerchantVip loadVirtualCityMerchant(String privinceCode,
			String merchantType) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public MbnMerchantVip loadVirtualProvinceMerchant(String privinceCode,
			String merchantType) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
    
	@Override
	public List<MbnMerchantVip> loadByProvinceAndCity(String privinceCode,
			String cityCode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    @Override
    public void deleteMerchantVip(Long merchantPin) {
        this.getSqlMapClientTemplate().delete(namespace+".deleteMerchantVip", merchantPin);
    }

	@Override
	public Integer checkZxtUserIdInUse(String zxtUserId) {
		return (Integer)getSqlMapClientTemplate().queryForObject(namespace + ".countByZxtUserId", zxtUserId);
	}

	@Override
	public int getCorpZXTId(Long merchantPin) {
		return (Integer)getSqlMapClientTemplate().queryForObject(namespace + ".getCorpZXTId", merchantPin);
	}
}
