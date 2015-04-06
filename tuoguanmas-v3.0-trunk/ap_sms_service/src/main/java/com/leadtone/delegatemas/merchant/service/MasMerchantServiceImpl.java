/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.merchant.service;

import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.delegatemas.merchant.dao.IMasMerchantDAO;
import com.leadtone.delegatemas.merchant.dao.IMerchantConfigDAO;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnConfigMerchantIDao;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author blueskybluesea
 */
@Service("masMerchantServiceImpl")
public class MasMerchantServiceImpl implements IMasMerchantService {
    @Resource(name="masMerchantDAOImpl")
    private IMasMerchantDAO masMerchantDAOImpl;
    @Resource(name="merchantConfigDAOImpl")
    private IMerchantConfigDAO merchantConfigDAOImpl;       
    @Override
    public Page paginateMasMerchants(Map<String, Object> paraMap) {
        return masMerchantDAOImpl.page(paraMap);
    }

    @Override
    public List<PointsCityStatistic> merchantCountByCity(Long provinceId) {
        return masMerchantDAOImpl.merchantCountByRegion(provinceId);
    }

    @Override
    public void newMerchantAndMerchantConfig(MbnMerchantVip merchant, List<MbnConfigMerchant> merchantConfigs) {
        MbnMerchantVipIDao mbnMerchantVipIDao = (MbnMerchantVipIDao)masMerchantDAOImpl;
        merchant.setMerchantPin(PinGen.getMerchantPin());
        merchant.setCreateTime(Calendar.getInstance().getTime());
        mbnMerchantVipIDao.insert(merchant);        
        this.initMerchantConfig(merchant.getMerchantPin(), merchantConfigs);
        MbnConfigMerchantIDao mbnConfigMerchantIDao = (MbnConfigMerchantIDao)merchantConfigDAOImpl;
        mbnConfigMerchantIDao.batchSave(merchantConfigs);
    }
    
    @Override
    public void createMerchantAndMerchantConfig(MbnMerchantVip merchant, List<MbnConfigMerchant> merchantConfigs) {
        MbnMerchantVipIDao mbnMerchantVipIDao = (MbnMerchantVipIDao)masMerchantDAOImpl;
//        merchant.setMerchantPin(PinGen.getMerchantPin());
        merchant.setCreateTime(Calendar.getInstance().getTime());
        mbnMerchantVipIDao.insert(merchant);
//        this.initMerchantConfig(merchant.getMerchantPin(), merchantConfigs);
        MbnConfigMerchantIDao mbnConfigMerchantIDao = (MbnConfigMerchantIDao)merchantConfigDAOImpl;
        mbnConfigMerchantIDao.batchSave(merchantConfigs);
    }

    @Override
    public void updateMerchantAndMerchantConfig(MbnMerchantVip merchant, List<MbnConfigMerchant> merchantConfigs) {
        MbnMerchantVipIDao mbnMerchantVipIDao = (MbnMerchantVipIDao)masMerchantDAOImpl;
        mbnMerchantVipIDao.update(merchant);
        //merchantConfigDAOImpl.removeMerchantConfig(merchant.getMerchantPin());
        List<MbnConfigMerchant> addConfigs = new ArrayList<MbnConfigMerchant>();
        Iterator<MbnConfigMerchant> it = merchantConfigs.iterator();
        MbnConfigMerchant config = null;
        while(it.hasNext()) {
            config = it.next();
            config.setMerchantPin(merchant.getMerchantPin());
            if(config.getId() == null) {
                config.setId(PinGen.getSerialPin());
                config.setMerchantPin(merchant.getMerchantPin());
                addConfigs.add(config);
                it.remove();
            }            
        }        
        MbnConfigMerchantIDao mbnConfigMerchantIDao = (MbnConfigMerchantIDao)merchantConfigDAOImpl;
        mbnConfigMerchantIDao.batchSave(addConfigs);
        mbnConfigMerchantIDao.batchUpdate(merchantConfigs);
    }
    private void initMerchantConfig(Long merchantPin, List<MbnConfigMerchant> merchantConfigs){
        for(MbnConfigMerchant mbnConfigMerchant : merchantConfigs) {
            mbnConfigMerchant.setMerchantPin(merchantPin);
            mbnConfigMerchant.setId(PinGen.getBasePin());
        }
    }

    @Override
    public void removeMerchantAndConfigs(Long merchantPin) {
        this.merchantConfigDAOImpl.removeMerchantConfig(merchantPin);
        this.masMerchantDAOImpl.deleteMerchantVip(merchantPin);
    }
}
