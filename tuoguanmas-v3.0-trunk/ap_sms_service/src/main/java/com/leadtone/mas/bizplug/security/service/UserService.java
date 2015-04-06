package com.leadtone.mas.bizplug.security.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;

public interface UserService {
	public List<Users> getAllUser(Long pinId);
	public void addUser(UserVO user);
	public UserVO getUserByAccount(String username);
	public Users validateUser(UserVO userVO);
	public Page page(PageUtil pageUtil);
	public List<UserVO> queryUserLikeAccount(UserVO user);
	public List<Role> getAllRoles();
	public void updateUser(UserVO user);
	public void deleteUser(Long[] deleteIds);
	public List<UserVO> validateLogin(Map<String,Object> user);
	public void updatePwd(UserVO user);
	/**
	 * 批量注销商户
	 * @return
	 */
	public boolean logoutMerchants(List<Long> pins);
	
	/**
	 * PAN_Z_G
	 * 根据手机号查找用户信息
	 * @param mobile
	 * @return
	 */
	public List<UserVO> findByUsers(UserVO userVO);
	
	/**
	 * 修改
	 * @param userVO
	 * @return
	 */
	public boolean update(UserVO userVO);
	/**
	 * 根据pin查询角色
	 * @param merchantPin
	 * @return
	 */
	public List<Role> getRolesByMerchantPin(Long merchantPin);
	
	/**
	 * 根据接入号查询对象
	 * @param userVO
	 * @return
	 */
	public List<Users> getUserBycorpAccessNumber(UserVO userVO);
        
	public List<Users> findUsersByUserTypeRegion(Long province,Long city, Integer userType);
	
	public boolean checkZxtUserIdInUse(String zxtUserId);
	public void addUserWebservice(UserVO user);
	public Users queryByUserId(Long userId);
}
