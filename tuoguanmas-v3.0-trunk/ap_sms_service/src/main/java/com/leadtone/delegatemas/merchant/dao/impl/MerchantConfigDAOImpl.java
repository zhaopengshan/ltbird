/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.merchant.dao.impl;

import com.leadtone.delegatemas.merchant.dao.IMerchantConfigDAO;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.dao.MbnConfigMerchantIDao;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author blueskybluesea
 */
@Repository("merchantConfigDAOImpl")
public class MerchantConfigDAOImpl extends BaseDao implements IMerchantConfigDAO,MbnConfigMerchantIDao {
    private String namespace="MbnConfigMerchant";
    @Resource(name="mbnConfigMerchantIDao")
    MbnConfigMerchantIDao mbnConfigMerchantIDao;
    @Override
    public void removeMerchantConfig(Long merchantPin) {
        this.getSqlMapClientTemplate().delete(namespace+".deleteConfigByMerchantPin",merchantPin);
    }

    @Override
    public boolean insert(MbnConfigMerchant mbnConfigMerchant) {
        return mbnConfigMerchantIDao.insert(mbnConfigMerchant);
    }

    @Override
    public boolean update(MbnConfigMerchant mbnConfigMerchant) {
        return mbnConfigMerchantIDao.update(mbnConfigMerchant);
    }

    @Override
    public boolean batchSave(List<MbnConfigMerchant> list) {
        return mbnConfigMerchantIDao.batchSave(list);
    }

    @Override
    public boolean batchUpdate(List<MbnConfigMerchant> list) {
        return mbnConfigMerchantIDao.batchUpdate(list);
    }

    @Override
    public List<MbnConfigMerchant> list(MbnConfigMerchant mbnConfigMerhcant) {
        return mbnConfigMerchantIDao.list(mbnConfigMerhcant);
    }

    @Override
    public MbnConfigMerchant load(long MerchantPin, String name) {
        return mbnConfigMerchantIDao.load(MerchantPin, name);
    }
    
}
