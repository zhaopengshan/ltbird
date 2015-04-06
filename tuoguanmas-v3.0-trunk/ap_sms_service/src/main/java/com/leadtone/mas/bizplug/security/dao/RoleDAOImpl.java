/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.security.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.Users;

/**
 *
 * @author blueskybluesea
 */
@Repository("roleDAOImpl")
public class RoleDAOImpl extends BaseDao implements IRoleDAO {

    private static String namespace = "security";

    @Override
    public List<Role> findAllRoles() {
        return this.getSqlMapClientTemplate().queryForList(namespace + ".queryAllRoles");
    }

    @Override
    public List<Role> pageFindRoles(Role queryCondition, int PageNo, int PageSize) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addRole(Role role) {
        this.getSqlMapClientTemplate().insert(namespace + ".addRole", role);
    }

    @Override
    public void updateRole(Role role) {
        this.getSqlMapClientTemplate().update(namespace + ".updateRole", role);
    }

    @Override
    public void addRoleUsers(final RoleVO roleVO) {
        final String sqlName = namespace + ".addRoleUser";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String,Object> paraMap = new HashMap<String,Object>();
                for (Iterator<Users> iterator = roleVO.getUsers().iterator(); iterator.hasNext();) {
                    Users user = iterator.next();
                    paraMap.put("id", PinGen.getSerialPin());
                    paraMap.put("roleId", roleVO.getId());
                    paraMap.put("userId", user.getId());
                    paraMap.put("createTime", Calendar.getInstance().getTime());
                    paraMap.put("createBy", user.getCreateBy());
                    executor.insert(sqlName, paraMap);
                    paraMap.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }

    @Override
    public void removeRoleUsers(final List<Long> roleUserIds) {
        final String sqlName = namespace + ".removeRoleUser";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (Iterator<Long> iterator = roleUserIds.iterator(); iterator.hasNext();) {
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
    public RoleVO viewRoleResources(Long roleId) {
        return (RoleVO) this.getSqlMapClientTemplate().queryForObject(namespace + ".queryRoleResource", roleId);
    }
    @Override
    public void removeRoles(final List<Long> roleIds){
	    final String sqlName = namespace + ".removeRole";
	    getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
	        @Override
	        public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	            int result = 0;
	            executor.startBatch();
	            for (Iterator<Long> iterator = roleIds.iterator(); iterator.hasNext();) {
	                Long roleId = iterator.next();
	                executor.delete(sqlName, roleId);
	                result++;
	            }
	            executor.executeBatch();
	            return result;
	        }
	    });
    }
    @Override
    public List<RoleVO> findRolesByUserId(Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleVO viewRoleUsers(Long roleId, Long pinId) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("roleId", roleId);
    	param.put("pinId", pinId);
        return (RoleVO) this.getSqlMapClientTemplate().queryForObject(namespace + ".queryRoleUser", param);
    }

    @Override
    public void addRoleResources(final RoleVO roleVO) {
        final String sqlName = namespace + ".addRoleResource";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String, Object> paraMap = new HashMap<String, Object>();
                for (Iterator<Resources> iterator = roleVO.getResources().iterator(); iterator.hasNext();) {
                    Resources resource = iterator.next();
                    paraMap.put("id", PinGen.getSerialPin());
                    paraMap.put("roleId", roleVO.getId());
                    paraMap.put("resourceId", resource.getId());
                    paraMap.put("createTime", Calendar.getInstance().getTime());
                    paraMap.put("createBy", resource.getCreateBy());
                    executor.insert(sqlName, paraMap);
                    paraMap.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }

    @Override
    public void removeRoleResources(final List<Long> roleResourceIds) {
        final String sqlName = namespace + ".removeRoleResource";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (Iterator<Long> iterator = roleResourceIds.iterator(); iterator.hasNext();) {
                    Long roleResourceId = iterator.next();
                    executor.delete(sqlName, roleResourceId);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }
    
    /**
     * 查询某个角色的信息
     */
	@Override
	public List<Role> queryRoleByName(String name) {
		return  this.getSqlMapClientTemplate().queryForList(namespace + ".queryRoleByName", name);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
                paraMap.put("merchantPin", 	pageUtil.getMerchantPin());
		paraMap.put("roleName", 	pageUtil.getRoleName());
		paraMap.put("roleDesc", 	pageUtil.getRoleDesc());
        paraMap.put("startPage", 	pageUtil.getStartPage());
        paraMap.put("pageSize", 	pageUtil.getPageSize());
		Integer recordes = this.pageCount(paraMap);
		List<Role> results = null;
		if (recordes > 0) {
			results = (List<Role>) getSqlMapClientTemplate()
					.queryForList(namespace+ ".pageQueryRoles", paraMap);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	@Override
	public Integer pageCount(Map<String,Object> paraMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject(namespace + ".pageCountRoles", paraMap);
	}

    @Override
    public List<Role> queryRoleByNameMerchantPin(Map<String, Object> paraMap) {
        return this.getSqlMapClientTemplate().queryForList(namespace+".queryRoleByNameMerchantPin", paraMap);
    }
}
