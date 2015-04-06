package com.leadtone.mas.bizplug.addr.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.addr.bean.AffirmReceive;
import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.ContactVO;
import com.leadtone.mas.bizplug.addr.bean.Group;
import com.leadtone.mas.bizplug.addr.bean.GroupVO;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;

@Component("contactDao")
public class ContactDaoImpl extends BaseDao implements ContactDao{
	private static final Log log = LogFactory.getLog(ContactDaoImpl.class);
	private static String NAMESPACE = "contact";
	@Override
	public boolean batchInsertContact(final List<Contact> list) {
		 try {
			getSqlMapClientTemplate().execute(
					new SqlMapClientCallback<Integer>() {
						@Override
						public Integer doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							int result = 0;
							int executorCount = 0;
							// TODO Auto-generated method stub
							executor.startBatch();
							for (Iterator<Contact> iterator = list.iterator(); iterator
									.hasNext();) {
								Contact t = iterator.next();
//								executor.insert(NAMESPACE + ".insertContact", t); 
								//sunyadong remove should add union unique mobile&merchant_pin
								executor.insert(NAMESPACE + ".replaceContact", t);
								executorCount++;
								if (executorCount % 1000 == 0) {
									result = executor.executeBatch();
								}
							}
							if (executorCount % 1000 != 0) {
								result = executor.executeBatch();
							}
							if (log.isDebugEnabled()) {
								log.debug("批量保存成功。");
							}
							return result;
						}
					});
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("批量保存失败。");
			return false;
		}
	}
	/**
	 * 新增联系人
	 */
	@Override
	public void insertContact(Contact contact) {
		getSqlMapClientTemplate().insert(NAMESPACE + ".insertContact", contact);
	}

	/**
	 * 编辑联系人
	 */
	@Override
	public void updateContact(Contact contact) {
		getSqlMapClientTemplate().update(NAMESPACE + ".updateContact", contact);
	}

	@Override
	public void deleteContact(Long id, Long bookId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("bookId", bookId);
		getSqlMapClientTemplate().delete(NAMESPACE + ".deleteContact", paramMap);
	}

	/**
	 * 分页查询联系人
	 */
	@Override
	public Page page(PageUtil pageUtil) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("mobile", 		pageUtil.getMobile());
		paraMap.put("name", 		pageUtil.getAccount());
		paraMap.put("groupId",  	pageUtil.getBatchId());
		paraMap.put("bookId", 		pageUtil.getMerchantPin());
        paraMap.put("startPage", 	pageUtil.getStartPage());
        paraMap.put("pageSize", 	pageUtil.getPageSize());
        paraMap.put("createBy", 	pageUtil.getCreateBy());
		Integer recordes = this.pageCount(paraMap);
		List<ContactVO> results = null;
		if( recordes > 0 ){
			results = getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".selectContact", paraMap);
		}
		paraMap.clear();
		return new Page( pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
	}
	/**
	 * 查询总记录数
	 */
	@Override
	public Integer pageCount(Map<String,Object> paraMap ){
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".pageCountConcat", paraMap);
	}
	
	/**
	 * 分页查询联系人
	 */
	@Override
	public Page pageGroup(PageUtil pageUtil) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("bookId", 		pageUtil.getMerchantPin());
        paraMap.put("startPage", 	pageUtil.getStartPage());
        paraMap.put("pageSize", 	pageUtil.getPageSize());
        paraMap.put("createBy", 	pageUtil.getCreateBy());
		Integer recordes = this.pageCountGroup(paraMap);
		List<Group> results = null;
		if( recordes > 0 ){
			results = getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".selectContactGroup", paraMap);
		}
		paraMap.clear();
		return new Page( pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
	}
	/**
	 * 查询总记录数
	 */
	public Integer pageCountGroup(Map<String,Object> paraMap ){
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".pageCountGroup", paraMap);
	}

	/**
	 * 批量删除联系人
	 */
	@Override
	public void batchDeleteContact(final List<Long> deleList, final Long bookId) {
		final String sqlName = NAMESPACE + ".deleteContact";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String, Object> param = new HashMap<String, Object>();
                for (Iterator<Long> iterator = deleList.iterator(); iterator.hasNext();) {
                    Long contactId = iterator.next();
                    param.put("id", contactId);
                	param.put("bookId", bookId);
                    executor.delete(sqlName, param);
                    param.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });

	}
	/**
	 * 批量移动联系人到某组
	 */
	@Override
	public void batchUpdateContactGroup(final List<Long> longs, final Long groupId, final Long bookId, final Long groupCreateBy) {
		final String sqlName = NAMESPACE + ".moveContact";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String, Object> param = new HashMap<String, Object>();
                for (Iterator<Long> iterator = longs.iterator(); iterator.hasNext();) {
                	Long addressId =  iterator.next();
                	param.put("id", addressId);
                	param.put("groupId", groupId);
                	param.put("bookId", bookId);
                	param.put("createBy", groupCreateBy);
                	param.put("updateTime", Calendar.getInstance().getTime());
                    executor.update(sqlName, param);
                    param.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
	}

	@Override
	public Contact loadByMobileContact(Long mobile) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Contact> loadByMobileContact(String mobile,Long merchantPin, Long createBy, Long groupId) {
		Contact cv=new Contact();
		cv.setMobile(mobile);
		cv.setBookId(merchantPin);
		cv.setCreateBy(createBy);
		cv.setBookGroupId(groupId);
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".findContact", cv);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> loadByNameGroup(String name,Long merchantPin, Long createBy) {
		Group g=new Group();
		g.setGroupName(name);
		g.setBookId(merchantPin);
		g.setCreateBy(createBy);
		return (List<Group>) this.getSqlMapClientTemplate().queryForList(NAMESPACE+".loadGroup",g);
	}

	@Override
	public boolean insertGroup(Group group) {
		this.getSqlMapClientTemplate().insert(NAMESPACE +".insertGroup", group);
		return true;
	}

	@Override
	public boolean updateGroup(Group group) {
		this.getSqlMapClientTemplate().update(NAMESPACE+".updateGroup", group);
		return true;
	}

	@Override
	public boolean deleteGroup(Long id) {
		this.getSqlMapClientTemplate().delete(NAMESPACE+".deleteGroup", id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> findAllGroup(Long merchantPin, Long userId, String name) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("bookId", 	merchantPin);
        paraMap.put("createBy", 	userId);
        paraMap.put("groupName", 	name);
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+".queryAllGroups",paraMap);
	}
	
	@Override
	public Group loadGroupById(Long id) {
		return (Group) this.getSqlMapClientTemplate().queryForObject(NAMESPACE+".loadGroupById",id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> findContactByGroup(Long groupId, Long merchantPin) {
		Contact c=new Contact();
		c.setBookGroupId(groupId);
		c.setBookId(merchantPin);
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".findContact", c);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> findContactByGroup(Long groupId, Long merchantPin, Integer page, Integer pageSize) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("bookGroupId", 	groupId);
		paraMap.put("bookId", 	merchantPin);
        paraMap.put("startPage", 	(page-1)*pageSize);
        paraMap.put("pageSize", 	pageSize);
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".findContactVO", paraMap);
	}
	@Override
	public boolean deleteBatchGroup(final List<Long> deleList) {
		final String sqlName = NAMESPACE + ".deleteGroup";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String, Object> param = new HashMap<String, Object>();
                for (Iterator<Long> iterator = deleList.iterator(); iterator.hasNext();) {
                    Long groupId = iterator.next();
                    executor.delete(sqlName, groupId);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
        return true;
	}
	@Override
	public AffirmReceive getAffirmReceive(String mobile, Long merchantPin) {
		AffirmReceive affirmReceive = new AffirmReceive();
		affirmReceive.setCustomerMobile(mobile);
		affirmReceive.setMerchantPin(merchantPin);
		return (AffirmReceive)getSqlMapClientTemplate().queryForObject(NAMESPACE+ ".queryAfirmReceive", affirmReceive);
	}
	@Override
	public void deleteContact(Contact contact) {
		int deleteCnt = getSqlMapClientTemplate().delete(NAMESPACE+".deleteContactBySelectedOrAll", contact);
		logger.info("delete contact count: "+ deleteCnt);
	}
	/**
	 * 批量导出联系人
	 */
	@Override
	public List<ContactVO> exportContact(Map<String, Object> param ) {
		return  getSqlMapClientTemplate().queryForList(NAMESPACE+".exportContact", param);
	}
	/**
	 * 统计组及组下的联系人的记录数
	 */
	@Override
	public List<ContactVO> countGroupContacts(Long merchantPin, Long createBy) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("bookId", 	merchantPin);
        paraMap.put("createBy", 	createBy);
		return getSqlMapClientTemplate().queryForList(NAMESPACE+".countGroupContacts", paraMap);
	}

	@Override
	public String getNameByMobile(String mobile) {
		String name = (String)getSqlMapClientTemplate().queryForObject("contact.queryNamebyMobile", mobile);
		if(null == name){
			return "";
		}
		return  name;
	}
	@Override
	public List<GroupVO> findAllGroupVO(Long merchantPin, Long createBy) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("bookId", 	merchantPin);
        paraMap.put("createBy", 	createBy);
		return getSqlMapClientTemplate().queryForList(this.NAMESPACE+".queryAllGroupVOs", paraMap);
	}
	@Override
	public List<Contact> findByGroupId(Long merchantPin, Long groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId",groupId);
		map.put("bookId",merchantPin);
		return getSqlMapClientTemplate().queryForList(this.NAMESPACE+".findByGroupId", map);
	}
	@Override
	public List<Contact> findByUserName(Long merchantPin, String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName",userName);
		map.put("bookId",merchantPin);
		return getSqlMapClientTemplate().queryForList(this.NAMESPACE+".findByUserName", map);
	}
	@Override
	public Contact loadContactById(Long id) {
		return (Contact) getSqlMapClientTemplate().queryForObject(this.NAMESPACE+".loadContactById", id);
	}

}
