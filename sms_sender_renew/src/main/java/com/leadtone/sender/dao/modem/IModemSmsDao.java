package com.leadtone.sender.dao.modem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leadtone.sender.bean.ModemSmsBean;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.SmsLimitBean;

/**
 * @author limh 
 */

public interface IModemSmsDao {
    public List getProvince();
    
    public List getMerchantPinByProvince(String province);
    
    public SmsLimitBean getLimitByMerchantPin(Long merchantPin);
    
    public List<Map<String, Object>> saveModemSms(List<ModemSmsBean> list);
    
    public List getModemSmsResult(int limit); 
    
    public void deleteSendedSms(List<Map<String, Object>> list);
    
    List<Map<String, Object>> saveQxtDriverSms(List<SmsBean> list);
    
    List<SmsBean> getQxtSmsResult(int limit);
    void updateQxtMoRestlt(List<SmsBean> list);
}
