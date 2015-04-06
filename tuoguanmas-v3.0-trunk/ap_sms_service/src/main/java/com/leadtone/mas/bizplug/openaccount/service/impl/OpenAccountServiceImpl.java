package com.leadtone.mas.bizplug.openaccount.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItem;
import com.leadtone.mas.bizplug.config.dao.MbnConfigProvinceItemValueDao;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantSmsMmsLimit;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnConfigMerchantIDao;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantSmsMmsLimitIDao;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.openaccount.service.OpenAccountIService;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
@Service("OpenAccountIService")
@Transactional
public class OpenAccountServiceImpl implements OpenAccountIService {

    @Resource
    private MbnConfigMerchantIDao mbnConfigMerchantIDao;
    @Resource
    private MbnConfigProvinceItemValueDao mbnConfigProvinceItemValueDao;
    @Resource
    private MbnMerchantVipIDao mbnMerchantVipIDao;
    @Resource
    private MbnMerchantSmsMmsLimitIDao mbnMerchantSmsMmsLimitIDao;
    @Resource
    private UsersDao usersDao;
    @Resource
    private UserService userService;
    private static final Log log = LogFactory.getLog(OpenAccountServiceImpl.class);

    /**
     * 用于验证企业是否存在
     * 存在为true,不存在为false
     */
    @Override
    public boolean checkMerchantExist(Users users) {
        if (users == null) {
            return false;
        } else {
            if (users.getMerchantPin() == null) {
                return false;
            } else {
                MbnMerchantVip mmv = mbnMerchantVipIDao.load(users.getMerchantPin());
                if (mmv == null) {
                    return false;
                }/*else{
                 if(mmv.getMerchantMobile()==null){
                 return false;
                 }
                 if(mmv.getName()==null){
                 return false;
                 }
                 if(mmv.getProvince()==null){
                 return false;
                 }
                 if(mmv.getPlatform()==null){
                 return false;
                 }
                 if(mmv.getCreateTime()==null){
                 return false;
                 }
                 if(mmv.getSmsAccessNumber()==null){
                 return false;
                 }
                 if("D".equalsIgnoreCase(mmv.getSmsState())&&"D".equalsIgnoreCase(mmv.getMmsState())){
                 return false;
                 }
                 }*/
            }
        }
        return true;
    }

    @Override
    public void openAccount(int num, Map parasMap, Map entityMap) {
        before();
        switch (num) {
            case 1:
                this.createMerchant(parasMap, entityMap);
                break;
            case 2:
                this.updateMerchant(parasMap, entityMap);
                break;
            case 3:
                this.delayMerchant();
                break;
            case 4:
                this.logoutMerchant();
                break;
            default:
                log.info("操作类别无法识别");
                break;
        }
        after();
    }

    @Override
    public void createMerchant(Map parasMap, Map entityMap) {
        MbnMerchantVip mmv = (MbnMerchantVip) parasMap.get("mbnMerchantVip");
        MbnMerchantVip mbnMerchantVip = null;
        UserVO usersVO = (UserVO) parasMap.get("users");
        Users operater=(Users) parasMap.get("operater");
        int userType=-1;
        long operaterId=-1l;
        if(operater!=null){
        	userType=operater.getUserType();//0:系统管理员；1：省级管理员 2地市管理员3、企业管理员4、普通用户   其它、企业自开
        	operaterId=operater.getId();
        }
        //必须在开户前就判断管理员是否开过商户，在此处是无法判断的！
        if (mmv.getMerchantPin() != null&&mmv.getMerchantPin()!=-1) {
            mbnMerchantVip = mbnMerchantVipIDao.load(mmv.getMerchantPin());
        }
        try {
            if (mbnMerchantVip == null) {
                mbnMerchantVip = mmv;
                //保存mbnMerchantVip 记录
                this.mbnMerchantVipIDao.insert(mbnMerchantVip);
                //更新user
                switch (userType) {
                case 0:
                case 2:
                	usersVO.setId(PinGen.getSerialPin());
                	usersVO.setMerchantPin(mbnMerchantVip.getMerchantPin());
                    usersVO.setPassword(MasPasswordTool.getEncString(usersVO.getPassword(), usersVO.getAccount()));
                	usersVO.setUserType(3);
                	usersVO.setLockFlag(0);
                	usersVO.setLimitTryCount(3);
                	usersVO.setActiveFlag(1);
                	usersVO.setCreateBy(operaterId);
                	usersVO.setCreateTime(new Date());
                	RoleVO r=new RoleVO();
                	r.setId(4L);//企业管理员
                	Set<RoleVO> s=new HashSet<RoleVO>();
                	s.add(r);
                	usersVO.setRoles(s);
                    this.usersDao.addUser(usersVO);
                    this.usersDao.addUserRoles(usersVO);
                    break;
                case 3:
                    usersVO.setId(operaterId);
                	usersVO.setUpdateTime(new Date());
                    usersVO.setAccount(operater.getAccount());
                	usersVO.setPassword(MasPasswordTool.getEncString(usersVO.getPassword(),usersVO.getAccount()));
                	usersVO.setUpdateBy(operaterId);
                	usersVO.setUpdateTime(new Date());
                	this.usersDao.updateUser(usersVO);
                    break;
                default:
                        if(userType !=-1){//运行出现异常，此处生管理员和
                            throw new Exception();
                        }
                	//企业自开
                	usersVO.setId(PinGen.getSerialPin());
                	usersVO.setMerchantPin(mbnMerchantVip.getMerchantPin());
                	usersVO.setPassword(MasPasswordTool.getEncString( usersVO.getPassword(),usersVO.getAccount()));
                	usersVO.setUserType(3);
                	usersVO.setLockFlag(0);
                	usersVO.setLimitTryCount(3);
                	usersVO.setActiveFlag(1);
                	usersVO.setCreateBy(operaterId);
                	usersVO.setCreateTime(new Date());
                	RoleVO ro=new RoleVO();
                	ro.setId(4L);//企业管理员
                	Set<RoleVO> se=new HashSet<RoleVO>();
                	se.add(ro);
                	usersVO.setRoles(se);
                    this.usersDao.addUser(usersVO);
                    this.usersDao.addUserRoles(usersVO);
                    break;
                }
                //保存用户配置信息TODO
                List<MbnConfigProvinceItem> items = this.mbnConfigProvinceItemValueDao.getProvinceItemInfo(mbnMerchantVip.getProvince());
                if (null != items && items.size() > 0) {
                    List<MbnConfigMerchant> settings = new ArrayList<MbnConfigMerchant>(
                            items.size());
                    for (MbnConfigProvinceItem item : items) {
                        MbnConfigMerchant setting = new MbnConfigMerchant();
                        setting.setId(PinGen.getSerialPin());
                        setting.setName(item.getName());
                        setting.setItemValue(item.getItemValue());
                        setting.setMerchantPin(mbnMerchantVip.getMerchantPin());
                        settings.add(setting);
                    }
                    // 将条目信息插入到商户配置表中
                    mbnConfigMerchantIDao.batchSave(settings);
                }

                MbnMerchantSmsMmsLimit merchantLimit = (MbnMerchantSmsMmsLimit) parasMap.get("merchantLimit");
                this.mbnMerchantSmsMmsLimitIDao.insert(merchantLimit);

                //修改企业强制签名
                MbnConfigMerchant mbnConfigMerchant = new MbnConfigMerchant();
                mbnConfigMerchant.setMerchantPin(mbnMerchantVip.getMerchantPin());
                mbnConfigMerchant.setName("sms_sign_content");
                mbnConfigMerchant.setItemValue((String) parasMap.get("merchantSign"));
                if (this.mbnConfigMerchantIDao.load(mbnMerchantVip.getMerchantPin(), "sms_sign_content") == null) {
                    mbnConfigMerchant.setId(PinGen.getSerialPin());
                    this.mbnConfigMerchantIDao.insert(mbnConfigMerchant);
                } else {
                    this.mbnConfigMerchantIDao.update(mbnConfigMerchant);
                }
            }
        } catch (Exception e) {
            log.info(e.getStackTrace());
            log.debug("新增商户失败" + e.getMessage());
            entityMap.put("resultMsg", "新增企业失败");
            entityMap.put("flag", false);
            return;
        }
        entityMap.put("resultMsg", "新增企业成功");
        entityMap.put("merchantPin",mmv.getMerchantPin().toString());
        entityMap.put("flag", true);
    }

    @Override
    public void updateMerchant(Map parasMap, Map entityMap) {
        MbnMerchantVip mmv = (MbnMerchantVip) parasMap.get("mbnMerchantVip");
        MbnMerchantVip mbnMerchantVip = mbnMerchantVipIDao.load(mmv.getMerchantPin());
        UserVO usersVO = (UserVO) parasMap.get("users");
        Users operater=(Users) parasMap.get("operater");
        int userType=-1;
        long operaterId=-1l;
        if(operater!=null){
        	userType=operater.getUserType();//1:省系统管理员；2：地市管理员3、企业管理员		其它、企业自开
        	operaterId=operater.getId();
        }
        try {
            if (mbnMerchantVip == null) {
                entityMap.put("resultMsg", "企业不存在，更新企业失败");
                entityMap.put("flag", false);
                return;
            } else {
                if (mbnMerchantVip.getSmsState().equals("D") && mbnMerchantVip.getMmsState().equals("D")) {
                    entityMap.put("resultMsg", "企业已经注销，更新企业户失败");
                    entityMap.put("flag", false);
                    return;
                } else {
                    mbnMerchantVip = mmv;
                    
                    this.mbnMerchantVipIDao.update(mbnMerchantVip);
                    //更新user
                    if(userType==2 || userType==0){
                        usersVO.setUserType(3);
                    	usersVO.setPassword(MasPasswordTool.getEncString(usersVO.getPassword(), usersVO.getAccount()));
                    	usersVO.setUpdateBy(operaterId);
                    	usersVO.setUpdateTime(new Date());
                    	this.usersDao.updateUserByAccount(usersVO);
                    }else if(userType==3){
                        usersVO.setUserType(3);
                        usersVO.setAccount(operater.getAccount());
                    	usersVO.setPassword(MasPasswordTool.getEncString( usersVO.getPassword(),usersVO.getAccount()));
                    	usersVO.setUpdateBy(operaterId);
                    	usersVO.setUpdateTime(new Date());
                    	this.usersDao.updateUserByAccount(usersVO);
                    }else{
                    	throw new Exception();
                    }
                    //修改企业强制签名
                    MbnMerchantSmsMmsLimit merchantLimit = (MbnMerchantSmsMmsLimit) parasMap.get("merchantLimit");
                    this.mbnMerchantSmsMmsLimitIDao.update(merchantLimit);

                    MbnConfigMerchant mbnConfigMerchant = new MbnConfigMerchant();
                    mbnConfigMerchant.setMerchantPin(mbnMerchantVip.getMerchantPin());
                    mbnConfigMerchant.setName("sms_sign_content");
                    mbnConfigMerchant.setItemValue((String) parasMap.get("merchantSign"));
                    if (this.mbnConfigMerchantIDao.load(mbnMerchantVip.getMerchantPin(), "sms_sign_content") == null) {
                        this.mbnConfigMerchantIDao.insert(mbnConfigMerchant);
                    } else {
                        this.mbnConfigMerchantIDao.update(mbnConfigMerchant);
                    }
                }
            }
        } catch (Exception e) {
            log.info(e.getStackTrace());
            entityMap.put("resultMsg", "更新企业失败");
            entityMap.put("flag", false);
            return;
        }
        entityMap.put("resultMsg", "更新企业成功");
        entityMap.put("merchantPin",mbnMerchantVip.getMerchantPin().toString());
        entityMap.put("flag", true);
    }

    @Override
    public void delayMerchant() {
        // TODO Auto-generated method stub
    }

    @Override
    public void logoutMerchant() {
        // TODO Auto-generated method stub
    }

    @Override
    public void before() {
        // TODO Auto-generated method stub
    }

    @Override
    public void after() {
        // TODO Auto-generated method stub
    }

    public MbnConfigMerchantIDao getMbnConfigMerchantIDao() {
        return mbnConfigMerchantIDao;
    }

    public MbnConfigProvinceItemValueDao getMbnConfigProvinceItemValueIDao() {
        return mbnConfigProvinceItemValueDao;
    }

    public MbnMerchantVipIDao getMbnMerchantVipIDao() {
        return mbnMerchantVipIDao;
    }

    public MbnMerchantSmsMmsLimitIDao getMbnMerchantSmsMmsLimitIDao() {
        return mbnMerchantSmsMmsLimitIDao;
    }

    public UsersDao getUsersDao() {
        return usersDao;
    }

	public UserService getUserService() {
		return userService;
	}
    
}
