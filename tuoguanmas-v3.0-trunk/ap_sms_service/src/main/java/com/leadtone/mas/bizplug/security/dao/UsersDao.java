package com.leadtone.mas.bizplug.security.dao;

import java.util.List;

import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.base.PageBaseIDao;
import java.util.Map;


public interface UsersDao extends PageBaseIDao{
	public Users findById(Long id);
	public UserVO findByName(String username);
	public Users findByAccount(UserVO userVO);
	public List<Role> getAllRoles();
	public List<Users> getAllUser(Long pinId);
	public void addUser(UserVO user);
	public void updateUser(UserVO user);
	public void deleteUser(List<Long> deleteIds);
	public List<UserVO> queryUserLikeAccount(UserVO user);
	public void addUserRoles(UserVO userVO);
	public void deleteUserRoles(List<Long> userRoles);
	public List<UserVO> validateLogin(Map<String,Object> user);
	/**
	 * 更加账号更新用户
	 * @param user
	 * @return
	 */
	public boolean updateUserByAccount(Users user);
	/**
	 * 批量修改账号的状态
	 * @param pins
	 * @param flag
	 * @return
	 */
	public boolean updateUserActiveFlagByMerchantPin(List<Long> pins,int flag);
	/**
	 * PAN_Z_G
	 * 根据手机号查找用户信息
	 * @param mobile
	 * @return
	 */
	public List<UserVO> findByUsers(UserVO userVO);
	/**
	 * 单纯修改用户信息
	 * @param users
	 * @return
	 */
	//public Integer update(Users users);
	/**
	 * 获取企业的角色
	 */
	public List<Role> getRolesByMerchantPin(Long merchantPin);
	
	/**
	 * 根据接入号获取用户
	 * @param userVO
	 * @return
	 */
	public List<Users> getUsersByCorpAccessNumber(UserVO userVO);
	
    public List<Users> getUsersByUserType(Map<String,Object> paraMap);
    /**
     * 查询zxtUserId总数，用于判断是否在用
     * @param zxtUserId
     * @return
     */
    public Integer getZxtUserIdCount(String zxtUserId);
    
    public Users queryByUserId(Long userId);
}
