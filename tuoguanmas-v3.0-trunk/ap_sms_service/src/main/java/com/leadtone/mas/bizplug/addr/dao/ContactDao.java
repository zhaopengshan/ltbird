package com.leadtone.mas.bizplug.addr.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.addr.bean.AffirmReceive;
import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.ContactVO;
import com.leadtone.mas.bizplug.addr.bean.Group;
import com.leadtone.mas.bizplug.addr.bean.GroupVO;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.security.dao.base.PageBaseIDao;


public interface ContactDao extends PageBaseIDao{

	// contact
	public void insertContact(Contact contact);
	public void updateContact(Contact contact);
	public void deleteContact(Contact contact);
	public void deleteContact(Long id, Long bookId);
	public List<ContactVO> exportContact(Map<String, Object> paraHashMap);
	public boolean batchInsertContact(List<Contact> list);
	public void batchDeleteContact(List<Long> deleList, Long bookId);
	public void batchUpdateContactGroup(List<Long> longs, Long groupId, Long bookId, Long groupCreateBy);	// 批量移动联系人到某组
	public Contact loadByMobileContact(Long mobile);
	public AffirmReceive getAffirmReceive(String mobile, Long merchantPin);
	public List<ContactVO> countGroupContacts(Long merchantPin, Long createBy);

	/**
	 * 依据联系人手机号查询联系人姓名
	 *
	 * @param mobile：联系人手机号
	 * @return 有存在的联系人，则返回姓名，否则返回""
	 */
	public String getNameByMobile(String mobile);

	public List<Contact> findContactByGroup(Long merchantPin, Long groupId);
	public List<Contact> findContactByGroup(Long merchantPin, Long groupId, Integer page, Integer pageSize);
	public List<Contact> loadByMobileContact(String mobile,Long merchantPin, Long createBy, Long groupId);
	public Contact loadContactById(Long id);
	//Group
	public List<Group> loadByNameGroup(String name,Long merchantPin,Long createBy);
	public Group loadGroupById(Long id);
	public boolean insertGroup(Group group);
	public boolean updateGroup(Group group);
	public boolean deleteGroup(Long id);
	public boolean deleteBatchGroup(List<Long> ids);
	public List<Group> findAllGroup(Long merhcantPin, Long userId, String name);
	public Page pageGroup(PageUtil pageUtil);

	public List<GroupVO> findAllGroupVO(Long merchantPin, Long createBy);
	public List<Contact> findByGroupId(Long merchantPin, Long groupId);
	public List<Contact> findByUserName(Long merchantPin, String userName);
	
}
