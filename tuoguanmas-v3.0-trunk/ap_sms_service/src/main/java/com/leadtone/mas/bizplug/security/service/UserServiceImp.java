package com.leadtone.mas.bizplug.security.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen; 
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
@SuppressWarnings("static-access")
@Service("userService")
@Transactional
public class UserServiceImp implements UserService {
	@Resource
	private UsersDao userDao;
	@Resource
	private MbnMerchantVipIDao mbnMerchantVipIDao;
	@Override
	
	public UserVO getUserByAccount(String username) {
		//  
		return userDao.findByName(username);
	}
	
	public MbnMerchantVipIDao getMbnMerchantVipIDao() {
		return mbnMerchantVipIDao;
	}

	public List<Role> getAllRoles(){
		return userDao.getAllRoles();
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return userDao.page(pageUtil);
	}

	@Override
	public List<UserVO> queryUserLikeAccount(UserVO user) {
		return userDao.queryUserLikeAccount(user);
	}

	@Override
	public void updateUser(UserVO user) {
		List<Long> readyRemoveUsers = new ArrayList<Long>();
		readyRemoveUsers.add(user.getId());
		if(user.getPassword()!=null)
		user.setPassword(MasPasswordTool.getEncString(user.getPassword(), user.getAccount()));
		user.setUpdateTime(Calendar.getInstance().getTime());
		userDao.deleteUserRoles(readyRemoveUsers);
		userDao.addUserRoles(user);
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(Long[] deleteIds) {
		List<Long> deleteUsers = new ArrayList<Long>();
        for(Long roleIds : deleteIds) {
        	deleteUsers.add(roleIds);
        }
        userDao.deleteUserRoles(deleteUsers);
		userDao.deleteUser(deleteUsers);
	}

	@Override
	public List<Users> getAllUser(Long pinId) {
		//  
		return userDao.getAllUser(pinId);
	}

	@Override
	public void addUser(UserVO user) {
		// 将setId提前，用于级联操作
		if( user.getId() == null || user.getId() <=0 ){
			user.setId(PinGen.getSerialPin());
		}
		user.setPassword(new MasPasswordTool()
				.getEncString(user.getPassword(), user.getAccount()));
		user.setCreateTime(Calendar.getInstance().getTime());
		
		userDao.addUserRoles(user);   
		userDao.addUser(user);
	}
	public void addUserWebservice(UserVO user){
		// 将setId提前，用于级联操作
		if( user.getId() == null || user.getId() <=0 ){
			user.setId(PinGen.getSerialPin());
		}
		//传过来的就已经是加密过的了，sunyadong mark
//				user.setPassword(new MasPasswordTool()
//						.getEncString(user.getPassword(), user.getAccount()));
		user.setCreateTime(Calendar.getInstance().getTime());
		
		userDao.addUserRoles(user);   
		userDao.addUser(user);
	}
	@Override
	public List<UserVO> validateLogin(Map<String,Object> user) {
		//  
		return userDao.validateLogin(user);
	}
	

	@Override
	public void updatePwd(UserVO user){
		user.setPassword(new MasPasswordTool()
		.getEncString(user.getPassword(), user.getAccount()));
		user.setUpdateTime(Calendar.getInstance().getTime());
		userDao.updateUser(user);
	}

	@Override
	public boolean logoutMerchants(List<Long> pins) {
		boolean flag=this.mbnMerchantVipIDao.updateBatch(pins, "D");
		return flag&this.userDao.updateUserActiveFlagByMerchantPin(pins, 0);
	}

	@Override
	public Users validateUser(UserVO userVO) {
		return userDao.findByAccount(userVO);
	}

 

	@Override
	public boolean update(UserVO userVO) {
		try {
			userDao.updateUser(userVO);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	@Override
	public List<Role> getRolesByMerchantPin(Long merchantPin) {
		return userDao.getRolesByMerchantPin(merchantPin);
	}

 	@Override
	public List<Users> getUserBycorpAccessNumber(UserVO userVO) {
		//  
		return userDao.getUsersByCorpAccessNumber(userVO);
	}

	@Override
	public List<UserVO> findByUsers(UserVO userVO) {
		// TODO Auto-generated method stub
		return userDao.findByUsers(userVO);
		
	}

    @Override
    public List<Users> findUsersByUserTypeRegion(Long province, Long city, Integer userType) {
        Map<String,Object>  paraMap = new HashMap<String,Object>();
        paraMap.put("province", province);
        paraMap.put("city", city);
        paraMap.put("userType", userType);
        return this.userDao.getUsersByUserType(paraMap);
    }
    @Override
    public boolean checkZxtUserIdInUse(String zxtUserId){
    	Integer count = this.userDao.getZxtUserIdCount(zxtUserId);
    	if(count != null && count > 0){
    		return true;
    	}
    	return false;
    }
    @Override
    public Users queryByUserId(Long userId){
    	return userDao.queryByUserId(userId);
    }
}
