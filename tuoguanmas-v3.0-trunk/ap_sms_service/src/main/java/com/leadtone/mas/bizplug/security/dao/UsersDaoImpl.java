package com.leadtone.mas.bizplug.security.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;

@Component("usersDao")
@SuppressWarnings("unchecked")
public class UsersDaoImpl extends BaseDao implements UsersDao{
	
	private static Logger logger  = Logger.getLogger(UsersDaoImpl.class);
	
	private String NAMESPACE = "security";

	public UserVO findByName(String username) {
		List<UserVO> users=(List<UserVO>) getSqlMapClientTemplate().queryForList(
				NAMESPACE + ".queryByAccount", username);
		return users.size()>0?users.get(0):null;
	}
	
	public Users findById(Long id) {
		UserVO userVO  = new UserVO();
		userVO.setId(id);
		return (Users) getSqlMapClientTemplate().queryForObject(
				NAMESPACE + ".queryByAccountId", userVO);
	}
	
	public List<Users> getAllUser(Long pinId) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE+".queryAllUsers", pinId);
	}
	
	public Users findByAccount(UserVO userVO){
		return (Users) getSqlMapClientTemplate().queryForObject(
			NAMESPACE + ".queryByAccountId", userVO);
	}

	/**
	 * 新增用户及用户
	 * @param user
	 */
	public void addUser(UserVO user) {
		logger.debug("addUser param: "+ user);
		getSqlMapClientTemplate().insert(NAMESPACE + ".addPortalUser", user);
	}
	
	/**
	 * 新增用户-角色关系表信息
	 * @param userRoles
	 * @param userId
	 * @param createBy
	 */
	public void addUserRoles(final UserVO userVO){
		// 批量插入用户所对应的所有的角色
		final String sqlName = NAMESPACE + ".addRoleUser";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String,Object> paraMap = new HashMap<String,Object>();
                for (Iterator<RoleVO> iterator = userVO.getRoles().iterator(); iterator.hasNext();) {
                	RoleVO roleVO = iterator.next();
                    paraMap.put("id", PinGen.getSerialPin());
                    paraMap.put("roleId", roleVO.getId());
                    paraMap.put("userId", userVO.getId());
                    paraMap.put("createTime", Calendar.getInstance().getTime());
                    paraMap.put("createBy", roleVO.getCreateBy());
                    executor.insert(sqlName, paraMap);
                    paraMap.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
	}
	/**
	 * 模糊查询某个用户信息
	 */
	public List<UserVO> queryUserLikeAccount(UserVO user){
		return  getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryLikeUser", user);
	}

	/**
	 * 获取所有的角色
	 */
	@Override
	public List<Role> getAllRoles() {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryAllRoles");
	}	
	
	/**
	 * 分页查询
	 */
	public Page page(PageUtil pageUtil){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("pinId", 		pageUtil.getMerchantPin());
		paraMap.put("roleId", 		pageUtil.getRoleId());
		paraMap.put("activeFlag", 	pageUtil.getActiveFlag());
		paraMap.put("mobile", 		pageUtil.getMobile());
		paraMap.put("account", 		pageUtil.getAccount());
		paraMap.put("email", 		pageUtil.getEmail());
        paraMap.put("startPage", 	pageUtil.getStartPage());
        paraMap.put("pageSize", 	pageUtil.getPageSize());
        // REX@20130112 判断用户类型 超、省、地、企业
        if(pageUtil!= null)
        if( pageUtil.getUserType() == 0){
        	// 超级管理员
        	paraMap.put("provArray", 	pageUtil.getAreaRange());
        	paraMap.put("userType", 	new int[]{1});
        }else if( pageUtil.getUserType() == 1){
        	// 省管理员
        	paraMap.put("cityArray", 	pageUtil.getAreaRange());
        	paraMap.put("userType", 	new int[]{2});
        }else if( pageUtil.getUserType() == 2){
        	// 地区管理员
        	paraMap.put("cityArray", 	pageUtil.getAreaRange());
        	paraMap.put("userType", 	new int[]{3});
        } else if( pageUtil.getUserType() == 3){
        	// 企业管理员
        	//paraMap.put("cityArray", 	pageUtil.getAreaRange());
        	paraMap.put("userType", 	new int[]{3, 4});
        } else{
        	paraMap.put("userType", 	new int[]{4});
        }
        
		Integer recordes = this.pageCount(paraMap);
		List<UserVO> results = null;
		if( recordes > 0 ){
			results = (List<UserVO>)getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".queryMerchantVipByCondition", paraMap);
		}
		return new Page( pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
	}
	
	/**
	 * 查询总记录数
	 */
	public Integer pageCount(Map<String,Object> paraMap ){
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".pageCountUser", paraMap);
	}

	/**
	 * 
	 * 删除用户,及删除用户-角色关系表里与该用户所关联的角色
	 */
	@Override
	public void deleteUser(final List<Long> deleteIds) {
		
		final String sqlName = NAMESPACE + ".deleteUser";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (Iterator<Long> iterator = deleteIds.iterator(); iterator.hasNext();) {
                    Long roleResourceId = iterator.next();
                    executor.delete(sqlName, roleResourceId);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
	}
	@Override
    public void deleteUserRoles(final List<Long> userRoles) {
        final String sqlName = NAMESPACE + ".deleteUserRoles";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (Iterator<Long> iterator = userRoles.iterator(); iterator.hasNext();) {
                    Long roleUserId = iterator.next();
                    executor.delete(sqlName, roleUserId);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }
	@Override
	public List<UserVO> validateLogin(Map<String,Object> user) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE+".loginUser", user);
	}

	@Override
	public void updateUser(UserVO user) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update(NAMESPACE +".updateUser", user);
	}

	@Override
	public boolean updateUserByAccount(Users user) {
		this.getSqlMapClientTemplate().update(NAMESPACE+".updateUserByAccount", user);
		return true;
	}

	@Override
	public boolean updateUserActiveFlagByMerchantPin(final List<Long> pins, final int flag) {
		final String sqlName = NAMESPACE + ".updateUserActiveFlag";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String,Object> paraMap = new HashMap<String,Object>();
                for (Iterator<Long> iterator = pins.iterator(); iterator.hasNext();) {
                	Long pin = iterator.next();
                    paraMap.put("merchantPin", pin);
                    paraMap.put("activeFlag", flag);
                    executor.update(sqlName, paraMap);
                    paraMap.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
        return true;
	}

	 
	
	/**
	 * 获取企业的角色
	 */
	@Override
	public List<Role> getRolesByMerchantPin(Long merchantPin) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryAllRolesByMerchantPin", merchantPin);
	}

	@Override
	public List<Users> getUsersByCorpAccessNumber(UserVO userVO) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryByAccountId", userVO);
	}

	@Override
	public List<UserVO> findByUsers(UserVO userVO) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryByUserVO", userVO);
	}	

    @Override
    public List<Users> getUsersByUserType(Map<String, Object> paraMap) {
        return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryUsersByUserType", paraMap);
    }
    
    public Integer getZxtUserIdCount(String zxtUserId){
    	return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".countByZxtUserId", zxtUserId);
    }
    
    @Override
    public Users queryByUserId(Long userId){
    	return (Users) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryByUserId", userId);
    }
}
