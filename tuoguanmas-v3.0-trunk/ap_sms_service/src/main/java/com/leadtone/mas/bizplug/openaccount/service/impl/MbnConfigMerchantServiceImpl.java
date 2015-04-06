package com.leadtone.mas.bizplug.openaccount.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.dao.MbnConfigMerchantIDao;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;

/**
 *
 * @author wangyu
 */
@Service("MbnConfigMerchantIService")
public class MbnConfigMerchantServiceImpl implements MbnConfigMerchantIService {
       @Resource
    private MbnConfigMerchantIDao mbnConfigMerchantIDao;

    public MbnConfigMerchantIDao getMbnConfigMerchantIDao() {
        return mbnConfigMerchantIDao;
    }
    @Override
    public MbnConfigMerchant loadByMerchantPin(Long merchantPin,String name) {
        return this.mbnConfigMerchantIDao.load(merchantPin, name);
    }

    @Override
    public Map<String, MbnConfigMerchant> loadByList(Long merchantPin, List<String> list) {
        Map<String,MbnConfigMerchant> map=new HashMap<String,MbnConfigMerchant>();
        for(Iterator<String> it=list.iterator();it.hasNext();){
            String temp=it.next();
            MbnConfigMerchant mcm=this.mbnConfigMerchantIDao.load(merchantPin, temp);
            map.put(temp, mcm);
        }
        return map;
    }

    @Override
    public boolean insertBatch(List<MbnConfigMerchant> list) {
        return this.mbnConfigMerchantIDao.batchSave(list);
    }

    @Override
    public boolean updateBatch(List<MbnConfigMerchant> list) {
       return this.mbnConfigMerchantIDao.batchUpdate(list);
    }
	@Override
	public boolean insert(MbnConfigMerchant mbnConfigMerchant) {
		return this.mbnConfigMerchantIDao.insert(mbnConfigMerchant);
	}
	@Override
	public boolean update(MbnConfigMerchant mbnConfigMerchant) {
		return this.mbnConfigMerchantIDao.update(mbnConfigMerchant);
	}

    @Override
    public List<MbnConfigMerchant> queryMerchantConfigList(Long merchantPin) {
        MbnConfigMerchant config = new MbnConfigMerchant();
        config.setMerchantPin(merchantPin);
        return this.mbnConfigMerchantIDao.list(config);
    }
}
