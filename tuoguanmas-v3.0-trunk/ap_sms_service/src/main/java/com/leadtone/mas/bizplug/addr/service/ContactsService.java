package com.leadtone.mas.bizplug.addr.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.ContactOrderVO;
import com.leadtone.mas.bizplug.addr.bean.ContactVO;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.bean.Group;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;

public interface ContactsService {
	public List<Contacts> getContactsByParentId(Long merchantPin, Long id, Long createBy, Integer page, Integer pageSize);
	public List<Contacts> getContactsByGroupName(Long merchantPin, String groupName, Long createBy);
	public List<Contacts> getContactsByUserName(Long merchantPin, String userName);
	
	/**
	 * 验证此商户下手机是否存在
	 * @param mobile
	 * @param pinId
	 * @return
	 */
	public List<Contact> checkContactByMobile(String mobile, Long pinId, Long createBy, Long groupId);
	/**
	 * 新增联系人
	 * @param contact
	 */
	public void addContact(Contact contact);
	/**
	 * 修改联系人
	 * @param contact	条件 
	 */
	public void updateContact(Contact contact);
	/**
	 * 删除联系人(通过搜索条件或删除全部联系人)
	 * @param contact	删除条件 
	 * @return
	 */
	public void deleteContact(Contact contact);
	/**
	 * 删除联系人(通过选择结果删除)
	 * @param ids	选择的id
	 * @param bookId	商户pin码
	 * @return
	 */
	public void deleteContact(Long[] ids, Long bookId);
	/**
	 * 导出联系人(通过搜索条件或导出全部联系人)
	 * @param contact	导出条件 
	 * @return
	 */
	public List<ContactVO> exportContact(Contact contact);
	/**
	 * 导出联系人(通过选择结果导出)
	 * @param ids	选择的id
	 * @param bookId	商户pin码
	 * @return
	 */
	public List<ContactVO> exportContact(Long[] ids, Long bookId);
	/**
	 * 根据 Contact Id 查询 联系人
	 * @param id
	 * @return
	 */
	public Contact loadContactById(Long id);
	
	/**
	 * 分页查询联系人
	 * @param pageUtil	查询条件 
	 * @return
	 */
	public Page page(PageUtil pageUtil);
	/**
	 * 分页查询联系人组
	 * @param pageUtil	查询条件 
	 * @return
	 */
	public Page pageGroup(PageUtil pageUtil);
	/**
	 * 批量移动联系人到某组
	 * @param updateLongs 要移动的联系人id
	 * @param groupId	要移动到的组id
	 * @param bookId	商户pin码
	 */
	public boolean batchUpdateContactGroup(Map<String, Object> entityMap,Long[] updateLongs, Long groupId, Long bookId,
			Long groupCreateBy, boolean privateGroupOpen, boolean superUser, Long createBy);	
	
	/**
	 *  统计当前库里的联系人总数
	 * @param pinId	商户pin码
	 * @return
	 */
	public Integer countContact(Long pinId, Long createBy);	
	/**
	 * 统计组，及组下的联系人记录数
	 * @param merchantPin 商户pin码
	 * @return
	 */
	public List<ContactVO> countGroupContacts(Long merchantPin, Long createBy);	
	/**
	 * 查询该商户下所有的通讯录组
	 * @param merchantPin
	 * @return
	 */
	public List<Group> getAllGroup(Long merchantPin, Long userId);
	/**
	 * 根据Group ID 查询 Group 详细信息
	 * @param id
	 * @return
	 */
	public Group loadGroupById(Long id);
	/**
	 * 为商户添加通讯录组
	 * @param group
	 * @return
	 */
	public boolean addGroup(Group group);
	/**
	 * 删除通讯录组
	 * @param id
	 * @return
	 */
	public boolean removeGroup(Long id);
	public boolean removeBatchGroup(List<Long> ids);
	/**
	 * 修改通讯录组
	 * @param group
	 * @return
	 */
	public boolean updateGroup(Group group);
	/**
	 * 检查通讯录下面是否有用户
	 * @param groupId
	 * @param merchantPin
	 * @return
	 */
	public List<Contact> checkGroup(Long groupId,Long merchantPin);
	/**
	 * 根据Excel批量导入联系人
	 * @param file				//导入联系人的Excel
	 * @param contactOrderVO	//Excel列排序类
	 * @param merchantPin		//商户唯一标识
	 * @param map				//将批量导入结果存入此map中
	 */
	public void importContactFromExcel(String[][] content,ContactOrderVO contactOrderVO,Long merchantPin,Map map,Long createBy, boolean privateGroupOpen, boolean superUser, String setGroupName);
	/**
	 * 根据组名称和商户PIN取得组
	 * @param name
	 * @param pin
	 * @return
	 */
	public Group findGroupByNameAndPin(String name,Long pin,Long createBy);
}
