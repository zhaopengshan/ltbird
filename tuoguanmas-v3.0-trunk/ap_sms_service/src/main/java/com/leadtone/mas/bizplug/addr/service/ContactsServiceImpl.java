package com.leadtone.mas.bizplug.addr.service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.addr.bean.AffirmReceive;
import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.ContactOrderVO;
import com.leadtone.mas.bizplug.addr.bean.ContactVO;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.bean.Group;
import com.leadtone.mas.bizplug.addr.bean.GroupVO;
import com.leadtone.mas.bizplug.addr.dao.ContactDao;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.im.DataImport;

@Service("contactsService")
@Transactional
public class ContactsServiceImpl implements ContactsService {
	@Resource
	private ContactDao contactDao;
	@Resource
	private ContactsService contactsService;
	private Logger logger = Logger.getLogger(this.getClass());
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-hh");

	public List<Contacts> getGroups(Long merchantPin, Long createBy, Integer page, Integer pageSize){
		List<Contacts> list = new ArrayList<Contacts>();
		List<GroupVO> gList = contactDao.findAllGroupVO(merchantPin, createBy);
		if( gList != null){
			for( GroupVO gvo :gList){
				Contacts c = new Contacts();
				c.setId(gvo.getId());
				c.setName(gvo.getGroupName().concat(" <用户组>"));
				c.setMobile("");
				c.setParentId(-100L);
				c.setCounts(gvo.getCounts());
				c.setIsParent(true);
				c.setPage(page);
				c.setPageSize(pageSize);
				list.add(c);
			}
		}
		// 增加一次查询未分组的信息
		List<Contact> cList = contactDao.findContactByGroup(-1L, merchantPin);
		if( cList != null){
			Contacts c = new Contacts();
			c.setId(-1L);
			c.setName("未分组".concat(" <用户组>"));
			c.setMobile("");
			c.setParentId(-100L);
			c.setCounts(cList.size());
			c.setIsParent(true);
			c.setPage(page);
			c.setPageSize(pageSize);
			list.add(c);
		}
		return list;
	}
	@Override
	public List<Contacts> getContactsByParentId(Long merchantPin, Long id, Long createBy, Integer page, Integer pageSize) {
		if( id == -100L){
			return getGroups(merchantPin,createBy, page, pageSize);
		}
		List<Contacts> list = new ArrayList<Contacts>();
		List<Contact> cList = contactDao.findContactByGroup(id, merchantPin, page, pageSize);
		if( cList != null){
			for(Contact ct: cList){
				Contacts c = new Contacts();
				c.setId(ct.getId());
				c.setName(ct.getMobile().concat(" <").concat(ct.getName()).concat(">"));
				c.setMobile(ct.getMobile());
				c.setParentId(id);
				c.setCounts(0);
				c.setIsParent(false);
				list.add(c);
			}
		}
		
		return list;
	}

	@Override
	public List<Contacts> getContactsByGroupName(Long merchantPin, String groupName, Long createBy) {
		List<Contacts> list = new ArrayList<Contacts>();
		Long groupId = Long.MIN_VALUE;
		//findAllGroup(merchantPin, userId, null);
		List<Group> group = contactDao.findAllGroup(merchantPin, createBy, groupName);
		if( group != null){
			Iterator<Group> groupIterator = group.iterator();
			while( groupIterator.hasNext() ){
				Group temp = groupIterator.next();
				groupId = temp.getId();
				List<Contact> cList = contactDao.findContactByGroup(groupId, merchantPin);
				if( cList != null){
					for(Contact ct: cList){
						Contacts c = new Contacts();
						c.setId(ct.getId());
						c.setName(ct.getName());
						c.setMobile(ct.getMobile());
						c.setParentId(ct.getBookGroupId());
						c.setCounts(0);
						c.setIsParent(false);
						list.add(c);
					}
				}
			}
		}else if( "未分组".equals(groupName)){
			groupId = -1L;
			List<Contact> cList = contactDao.findContactByGroup(groupId, merchantPin);
			if( cList != null){
				for(Contact ct: cList){
					Contacts c = new Contacts();
					c.setId(ct.getId());
					c.setName(ct.getName());
					c.setMobile(ct.getMobile());
					c.setParentId(ct.getBookGroupId());
					c.setCounts(0);
					c.setIsParent(false);
					list.add(c);
				}
			}
		}
		return list;
	}
	@Override
	public List<Contacts> getContactsByUserName(Long merchantPin, String userName) {
		List<Contacts> list = new ArrayList<Contacts>();
		List<Contact> cList = contactDao.findByUserName(merchantPin, userName);
		if( cList != null){
			for(Contact ct: cList){
				Contacts c = new Contacts();
				c.setId(ct.getId());
				c.setName(ct.getMobile().concat(" <").concat(ct.getName()).concat(">"));
				c.setMobile(ct.getMobile());
				c.setParentId(ct.getBookGroupId());
				c.setCounts(0);
				c.setIsParent(false);
				list.add(c);
			}
		}
		return list;
	}

	@Override
	public void addContact(Contact contact) {
		contact.setId(PinGen.getSerialPin());
		Date nowDate = Calendar.getInstance().getTime();
		contact.setCreateTime(nowDate);
		contact.setLastModifyTime(nowDate);
		contact.setCustomerAffirmFlag(getAffirmFlag(contact.getMobile(),
				contact.getBookId()));
		contactDao.insertContact(contact);
	}

	@Override
	public void updateContact(Contact contact) {
		contact.setLastModifyTime(Calendar.getInstance().getTime());
		contactDao.updateContact(contact);
	}

	@Override
	public void deleteContact(Long[] ids, Long bookId) {
		List<Long> deleList = new ArrayList<Long>();
		for (Long id : ids) {
			deleList.add(id);
		}
		contactDao.batchDeleteContact(deleList, bookId);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return contactDao.page(pageUtil);
	}
	@Override
	public Page pageGroup(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		return contactDao.pageGroup(pageUtil);
	}
	@Override
	public boolean batchUpdateContactGroup(Map<String, Object> entityMap, Long[] updateLongs, Long groupId,
			Long bookId, Long groupCreateBy, boolean privateGroupOpen, boolean superUser, Long createBy) {
		boolean result = true;
		List<Long> longs = new ArrayList<Long>();
		int count = 0, remain = 0, noPower = 0;
		for (Long id : updateLongs) {
			Contact tempContact = contactDao.loadContactById(id);
			if( privateGroupOpen ){
				if(!superUser){
					if( !tempContact.getCreateBy().equals(createBy) ){
						noPower++;
						continue;
					}
				}
			}
			List<Contact> tContact = contactDao.loadByMobileContact(tempContact.getMobile(), bookId, groupCreateBy, groupId);
			if ( tContact != null && tContact.size() > 0 ) {
				result = false;
				count++;
				continue;
			}
			remain++;
			longs.add(id);
		}
		contactDao.batchUpdateContactGroup(longs, groupId, bookId, groupCreateBy);
		if(privateGroupOpen){
			entityMap.put("message", remain+"个联系人移动成功;"+count+"个联系人已经存在于此组;"+ noPower + "个联系人无权进行此操作！");
		}else{
			entityMap.put("message", remain+"个联系人移动成功;"+count+"个联系人已经存在于此组！");
		}
		return result;
	}

	/**
	 * 删除联系人(通过搜索条件，删除全部)
	 */
	@Override
	public void deleteContact(Contact contact) {
		contactDao.deleteContact(contact);
	}

	@Override
	public List<Group> getAllGroup(Long merchantPin, Long userId) {
		return this.contactDao.findAllGroup(merchantPin, userId, null);
	}
	
	@Override
	public Group loadGroupById(Long id) {
		return this.contactDao.loadGroupById(id);
	}

	@Override
	public boolean addGroup(Group group) {
		return this.contactDao.insertGroup(group);
	}

	@Override
	public boolean removeGroup(Long id) {
		return this.contactDao.deleteGroup(id);
	}

	@Override
	public boolean updateGroup(Group group) {
		return this.contactDao.updateGroup(group);
	}

	@Override
	public List<Contact> checkGroup(Long groupId, Long merchantPin) {
		return this.contactDao.findContactByGroup(groupId, merchantPin);
	}

	public ContactDao getContactDao() {
		return contactDao;
	}

	@Override
	public void importContactFromExcel(String[][] content,
			ContactOrderVO contactOrderVO, Long merchantPin, Map map, Long createBy, boolean privateGroupOpen, boolean superUser, String setGroupName) {
		HashMap<Integer, String> hashMapError = new HashMap<Integer, String>();// 负责记录添加成功或者失败，用户组等
		List<Contact> createContactGroup = new ArrayList<Contact>();// 批量添加的联系人List

		int successCount = 0;
		int totalCount = 0;
		String messageTotal = null;
		String messageContent = null;
		
		if (content != null && content.length > 0) {
			totalCount = content.length;
			boolean flag = false;
            Map<String,String> mobileMap=new HashMap<String,String>();//用于存储手机号,判断Excel中是否有重复手机号
			for (int i = 0; i < content.length; i++) {
				String[] contactArray = content[i];
//				ContactVO contact = this.arrayToContact(contactArray, contactOrderVO, merchantPin, setGroupName);
//				// 对联系人进行合法性验证
//				flag = this.checkContact(contact, hashMapError, i,mobileMap, createBy, privateGroupOpen, superUser);
//				if (flag) {
//					createContactGroup.add(contact);
//				}
				ContactVO contact = this.arrayToContact(contactArray, contactOrderVO, merchantPin, setGroupName,
						hashMapError, i,mobileMap, createBy, privateGroupOpen, superUser);
				if( contact != null ){
					createContactGroup.add(contact);
				}
			}
			successCount = createContactGroup.size();
			if (successCount <= 0) {
				flag = true;
			} else {
				flag = this.contactDao.batchInsertContact(createContactGroup);
			}
			if (flag) {
				int failCount = totalCount - successCount;
				messageTotal = "提示：本次共导入客户资料 " + totalCount + " 条，成功 "
						+ successCount + " 条，失败 " + failCount + " 条! ";
				StringBuilder sb = new StringBuilder();
				for (Iterator<Map.Entry<Integer, String>> it = hashMapError
						.entrySet().iterator(); it.hasNext();) {
					Map.Entry<Integer, String> entry = it.next();
					sb.append("第" + (entry.getKey()+1) + "行 " + entry.getValue()
							+ ";");
				}
				messageContent = sb.toString();
				map.put("messageTotal", messageTotal);
				map.put("messageContent", messageContent);
				map.put("flag", true);
			} else {
				messageTotal = "通讯录批量导入失败";
				map.put("messageTotal", messageTotal);
				map.put("flag", false);
				map.put("messageContent", "");
			}
		} else {
			messageTotal = "不存在导入数据";
			map.put("messageTotal", messageTotal);
			map.put("flag", false);
			map.put("messageContent", "");
		}
	}
	
	/**
	 * 将数组转化为Contact对象
	 * 并对联系人进行合法性校验
	 * @param arr
	 * @return
	 */
	private ContactVO arrayToContact(String[] arr, ContactOrderVO coo,
			Long merchantPin,String setGroupName,
			Map map, int row,Map<String,String> mobileMap, 
			Long createBy, boolean privateGroupOpen, boolean superUser) {
		ContactVO con = new ContactVO();
        for(int i=0;i<arr.length;i++){
            if(arr[i]==null){
                arr[i]="";
            }
        }
		if ( merchantPin == null ) {
			map.put(row, "导入异常");
			return null;
		}
		con.setBookId(merchantPin);
		// con.setBookGroupId(Long.valueOf(arr[coo.getGroup()]));//组IDz再校验时候赋值
//		联系人姓名不能为空
		if(coo.getName() != -1){
			con.setName(arr[coo.getName()].trim());
		}else{
			con.setName("");
		}
		if (con.getName() == null
				|| con.getName().trim().equals("")) {
//			map.put(row, "联系人姓名不能为空");
			map.put(row, "姓名不能为空");
			return null;
		}
		if(coo.getMobile() != -1){
			con.setMobile(arr[coo.getMobile()].trim());
		}else{
			con.setMobile(null);
		}
		// 联系人手机号不能为空
		if (con.getMobile() == null
				|| con.getMobile().trim().equals("")) {
//			map.put(row, "联系人手机号不能为空");
			map.put(row, "手机号不能为空");
			return null;
		} else {
			String reg = "((13[0-9])|(15[0-9])|(14[0-9])|(18[0-9]))\\d{8}";
			if (!Pattern.matches(reg, con.getMobile().trim())) {
				map.put(row, "手机号格式不正确");
				return null;
			}
			if( !privateGroupOpen && mobileMap.get(con.getMobile().trim())!=null){
				map.put(row, "手机号码已经在Excel中出现过");
				return null;
			}
		}
//		通讯组
		if(coo.getGroup() != -1){
			if(arr[coo.getGroup()]!=null && !"".equals(arr[coo.getGroup()].trim())){
			    con.setGroupName(arr[coo.getGroup()].trim());
			}else{
				con.setGroupName(setGroupName);
			}
		}else{
			con.setGroupName("");
		}
		if (con.getGroupName() == null
				|| con.getGroupName().trim().equals("")) {
			con.setBookGroupId(-1L);
			con.setCreateBy(createBy);
			if(!superUser){
				map.put(row, "联系人必须指定分组");
				return null;
			}
			if ( this.contactDao.loadByMobileContact(con.getMobile(),
					con.getBookId(), privateGroupOpen?createBy:null, privateGroupOpen?con.getBookGroupId():null).size() > 0) {
				map.put(row, "手机号码已经存在");
				return null;
			}
		} else {
			List<Group> g = this.contactDao.loadByNameGroup(
					con.getGroupName(), con.getBookId(), null);
			Group tempG = null;
			if( g!=null && g.size() >0 ){
				tempG = g.get(0);
			}
			if (tempG == null || tempG.getId() == null) {
				tempG=new Group();
				tempG.setId(PinGen.getSerialPin());
				tempG.setBookId(con.getBookId());
				tempG.setPid(-1L);
				tempG.setCreateBy(createBy);
				tempG.setGroupName(con.getGroupName());
				this.contactsService.addGroup(tempG);
				con.setBookGroupId(tempG.getId());
				con.setCreateBy(createBy);
			} else {
				if( privateGroupOpen && !createBy.toString().equals(tempG.getCreateBy().toString()) ){
					map.put(row, con.getGroupName()+"组,"+"在企业下已存在,请修改组名重新导入");
					return null;
				}
				con.setBookGroupId(tempG.getId());
				con.setCreateBy(createBy);
				if (this.contactDao.loadByMobileContact(con.getMobile(),
						con.getBookId(), privateGroupOpen?createBy:null, privateGroupOpen?con.getBookGroupId():null).size() > 0) {
					map.put(row, "手机号码已经存在");
					return null;
				}
			}
		}
//		设置 id
		con.setId(PinGen.getSerialPin());
		if(coo.getSex() != -1){
			if ("男".equals(arr[coo.getSex()].trim())) {
				con.setGender(1);
			} else if ("女".equals(arr[coo.getSex()].trim())) {
				con.setGender(0);
			} else {
				con.setGender(null);
			}
		}else{
			con.setGender(null);
		}
		
		try {
			if(coo.getBirthday() != -1){
				con.setBirthday(sdf.parse(arr[coo.getBirthday()]));
			}else{
				con.setBirthday(null);
			}
		} catch (ParseException e) {
			con.setBirthday(null);
		}
		
		if(coo.getTel() != -1){
			con.setTelephone(arr[coo.getTel()].trim());
		}else{
			con.setTelephone(null);
		}
		if(coo.getAddr() != -1){
			con.setAddress(arr[coo.getAddr()].trim());
		}else{
			con.setAddress("");
		}
		if(coo.getCompany() != -1){
			con.setCompany(arr[coo.getCompany()].trim());
		}else{
			con.setCompany("");
		}
		// con.setPreferences(arr[]); //喜好
		con.setCustomerAffirmFlag(getAffirmFlag(con.getMobile(),
				con.getBookId()));// 业务状态标志
		con.setMerchantBlackFlag(1);// 商户定义黑白名单
		con.setLastModifyTime(new Date());
		if(coo.getIdentity() != -1){
			con.setIdentificationNumber(arr[coo.getIdentity()].trim());
		}else{
			con.setIdentificationNumber(null);
		}
		if(coo.getEmail() != -1){
			con.setEmail(arr[coo.getEmail()].trim());
		}else{
			con.setEmail(null);
		}
		if(coo.getQq() != -1){
			con.setQqNumber(arr[coo.getQq()].trim());
		}else{
			con.setQqNumber(null);
		}
		if(coo.getMsn() != -1){
			con.setMsn(arr[coo.getMsn()].trim());
		}else{
			con.setMsn(null);
		}
		if(coo.getBlog() != -1){
			con.setMicroBlog(arr[coo.getBlog()].trim());
		}else{
			con.setMicroBlog(null);
		}
		if(coo.getVpmn() != -1){
			con.setVpmn(arr[coo.getVpmn()].trim());
		}else{
			con.setVpmn(null);
		}
		con.setCreateTime(new Date());
		if(coo.getDescription() != -1){
			con.setDescription(arr[coo.getDescription()].trim());
		}else{
			con.setDescription(null);
		}
		mobileMap.put(con.getMobile().trim(),con.getMobile().trim());
		return con;
	}

	/**
	 * 对联系人进行合法性校验
	 * 
	 * @param map
	 *            map中保存行号与失败原因或警告信息
	 */
//	private boolean checkContact(ContactVO contact, Map map, int row,Map<String,String> mobileMap, 
//			Long createBy, boolean privateGroupOpen, boolean superUser) {
//		if (contact == null || contact.getBookId() == null) {
//			map.put(row, "导入异常");
//			return false;
//		} else {
//			// 姓名
//			if (contact.getName() == null
//					|| contact.getName().trim().equals("")) {
//				map.put(row, "联系人姓名不能为空");
//				return false;
//			}
//			// 手机号
//			if (contact.getMobile() == null
//					|| contact.getMobile().trim().equals("")) {
//				map.put(row, "联系人手机号不能为空");
//				return false;
//			} else {
//				String reg = "((13[0-9])|(15[0-9])|(14[0-9])|(18[0-9]))\\d{8}";
//				if (!Pattern.matches(reg, contact.getMobile().trim())) {
//					map.put(row, "手机号格式不正确");
//					return false;
//				}
//				if( !privateGroupOpen && mobileMap.get(contact.getMobile().trim())!=null){
//					map.put(row, "联系人的手机号码已经在Excel中出现过");
//					return false;
//				}
//			}
//			// 通讯组
//			if (contact.getGroupName() == null
//					|| contact.getGroupName().trim().equals("")) {
//				contact.setBookGroupId(-1L);
//				contact.setCreateBy(createBy);
//				if(!superUser){
//					map.put(row, "联系人必须指定分组");
//					return false;
//				}
//				if ( this.contactDao.loadByMobileContact(contact.getMobile(),
//						contact.getBookId(), privateGroupOpen?createBy:null, privateGroupOpen?contact.getBookGroupId():null).size() > 0) {
//					map.put(row, "联系人的手机号码已经存在");
//					return false;
//				}
//				//TODO
//			} else {
//				List<Group> g = this.contactDao.loadByNameGroup(
//						contact.getGroupName(), contact.getBookId(), null);
//				Group tempG = null;
//				if( g!=null && g.size() >0 ){
//					tempG = g.get(0);
//				}
//				if (tempG == null || tempG.getId() == null) {
//					tempG=new Group();
//					tempG.setId(PinGen.getSerialPin());
//					tempG.setBookId(contact.getBookId());
//					tempG.setPid(-1L);
//					tempG.setCreateBy(createBy);
//					tempG.setGroupName(contact.getGroupName());
//					this.contactsService.addGroup(tempG);
//					contact.setBookGroupId(tempG.getId());
//					contact.setCreateBy(createBy);
//				} else {
//					if(privateGroupOpen && !createBy.toString().equals(tempG.getCreateBy().toString())){
//						map.put(row, contact.getGroupName()+"组,"+"在企业下已存在,请修改组名重新导入");
//						return false;
//					}
//					contact.setBookGroupId(tempG.getId());
//					contact.setCreateBy(createBy);
//					if (this.contactDao.loadByMobileContact(contact.getMobile(),
//							contact.getBookId(), privateGroupOpen?createBy:null, privateGroupOpen?contact.getBookGroupId():null).size() > 0) {
//						map.put(row, "联系人的手机号码已经存在");
//						return false;
//					}
//				}
//			}
//			mobileMap.put(contact.getMobile().trim(),contact.getMobile().trim());
//			return true;
//		}
//	}

	/**
	 * 将数组转化为Contact对象
	 * 
	 * @param arr
	 * @return
	 */
//	private ContactVO arrayToContact(String[] arr, ContactOrderVO coo,
//			Long merchantPin,String setGroupName) {
//		logger.error("******A1:"+String.valueOf(System.currentTimeMillis()-startTime));
//		ContactVO con = new ContactVO();
//        for(int i=0;i<arr.length;i++){
//            if(arr[i]==null){
//                arr[i]="";
//            }
//        }
//        logger.error("******A2:"+String.valueOf(System.currentTimeMillis()-startTime));
//		con.setId(PinGen.getSerialPin());
//		con.setBookId(merchantPin);
//		// con.setBookGroupId(Long.valueOf(arr[coo.getGroup()]));//组IDz再校验时候赋值
//		if(coo.getGroup() != -1){
//			if(arr[coo.getGroup()]!=null && !"".equals(arr[coo.getGroup()].trim())){
//			    con.setGroupName(arr[coo.getGroup()].trim());
//			}else{
//				con.setGroupName(setGroupName);
//			}
//		}else{
//			con.setGroupName("");
//		}
//		if(coo.getName() != -1){
//			con.setName(arr[coo.getName()].trim());
//		}else{
//			con.setName("");
//		}
//		if(coo.getSex() != -1){
//			if ("男".equals(arr[coo.getSex()].trim())) {
//				con.setGender(1);
//			} else if ("女".equals(arr[coo.getSex()].trim())) {
//				con.setGender(0);
//			} else {
//				con.setGender(null);
//			}
//		}else{
//			con.setGender(null);
//		}
//		
//		try {
//			if(coo.getBirthday() != -1){
//				con.setBirthday(sdf.parse(arr[coo.getBirthday()]));
//			}else{
//				con.setBirthday(null);
//			}
//		} catch (ParseException e) {
//			con.setBirthday(null);
//		}
//		if(coo.getMobile() != -1){
//			con.setMobile(arr[coo.getMobile()].trim());
//		}else{
//			con.setMobile(null);
//		}
//		if(coo.getTel() != -1){
//			con.setTelephone(arr[coo.getTel()].trim());
//		}else{
//			con.setTelephone(null);
//		}
//		if(coo.getAddr() != -1){
//			con.setAddress(arr[coo.getAddr()].trim());
//		}else{
//			con.setAddress("");
//		}
//		if(coo.getCompany() != -1){
//			con.setCompany(arr[coo.getCompany()].trim());
//		}else{
//			con.setCompany("");
//		}
//		logger.error("******A3:"+String.valueOf(System.currentTimeMillis()-startTime));
//		// con.setPreferences(arr[]); //喜好
//		con.setCustomerAffirmFlag(getAffirmFlag(con.getMobile(),
//				con.getBookId()));// 业务状态标志
//		logger.error("******A4:"+String.valueOf(System.currentTimeMillis()-startTime));
//		con.setMerchantBlackFlag(1);// 商户定义黑白名单
//		con.setLastModifyTime(new Date());
//		if(coo.getIdentity() != -1){
//			con.setIdentificationNumber(arr[coo.getIdentity()].trim());
//		}else{
//			con.setIdentificationNumber(null);
//		}
//		if(coo.getEmail() != -1){
//			con.setEmail(arr[coo.getEmail()].trim());
//		}else{
//			con.setEmail(null);
//		}
//		if(coo.getQq() != -1){
//			con.setQqNumber(arr[coo.getQq()].trim());
//		}else{
//			con.setQqNumber(null);
//		}
//		if(coo.getMsn() != -1){
//			con.setMsn(arr[coo.getMsn()].trim());
//		}else{
//			con.setMsn(null);
//		}
//		if(coo.getBlog() != -1){
//			con.setMicroBlog(arr[coo.getBlog()].trim());
//		}else{
//			con.setMicroBlog(null);
//		}
//		if(coo.getVpmn() != -1){
//			con.setVpmn(arr[coo.getVpmn()].trim());
//		}else{
//			con.setVpmn(null);
//		}
//		con.setCreateTime(new Date());
//		if(coo.getDescription() != -1){
//			con.setDescription(arr[coo.getDescription()].trim());
//		}else{
//			con.setDescription(null);
//		}
//		logger.error("******A5:"+String.valueOf(System.currentTimeMillis()-startTime));
//		return con;
//	}

	/*
	 * 批量获取流水号
	 * 
	 * @param length
	 * 
	 * @return
	 * 
	 * private Set<String> getPinGen(int length){ Set<String> pinGenSet =new
	 * HashSet<String>(); pinGenSet.clear(); while(pinGenSet.size() < length ){
	 * pinGenSet.add(String.valueOf(PinGen.getSerialPin())); } return pinGenSet;
	 * }
	 */

	@Override
	public boolean removeBatchGroup(List<Long> ids) {
		return this.contactDao.deleteBatchGroup(ids);
	}

	/**
	 * 根据二次确认表确认联系人的业务状态标志(0黑名单； 1永久白名单；2临时白名单)
	 * 
	 * @param mobile
	 * @param merchantPin
	 * @return
	 */
	public Integer getAffirmFlag(String mobile, Long merchantPin) {
		AffirmReceive affirmReceive = contactDao.getAffirmReceive(mobile,
				merchantPin);
		if (affirmReceive == null) {
			// 如果为空，则返回null
			return null;
		}
		if ("y".equalsIgnoreCase(affirmReceive.getContent())) {
			return 1;
		} else {
			// 为 n的情况（根据确认次数和创建时间判断）
			if (affirmReceive.getAffirmTime() <= 1) {
				// int nowMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
				// Calendar.getInstance().setTime(affirmReceive.getCreateTime());
				if (affirmReceive.getCreateTime().getMonth() == Calendar
						.getInstance().get(Calendar.MONTH)) {
					return 2;
				}
			}
		}
		return 0;
	}

	@Override
	public Integer countContact(Long pinId, Long createBy) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("mobile", "");
		paraMap.put("name", "");
		paraMap.put("bookId", pinId);
		paraMap.put("createBy", createBy);
		return contactDao.pageCount(paraMap);
	}
	@Override
	public List<ContactVO> exportContact(Contact contact, List<Long> userIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bookId", contact.getBookId());
		paramMap.put("mobile", contact.getMobile());
		paramMap.put("name", contact.getName());
		paramMap.put("createBy", userIds);
		paramMap.put("bookGroupId", (null == contact.getBookGroupId()? null : contact.getBookGroupId()));
		return contactDao.exportContact(paramMap);
	}

	@Override
	public List<ContactVO> exportContact(Long[] ids, Long bookId) {
//		String paramIds = "";
//		for(Long id : ids){
//             paramIds += id + ",";
//         }
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", ids);
		paramMap.put("bookId", bookId);
		return contactDao.exportContact(paramMap);
	}

	@Override
	public List<ContactVO> countGroupContacts(Long merchantPin, Long createBy) {
		List<ContactVO> contactVOs = contactDao.countGroupContacts(merchantPin, createBy);
		
		// 统计未分组的情况
		List<Contact> contacts = contactDao.findContactByGroup(-1L,merchantPin);
		ContactVO c = new ContactVO();
		c.setBookGroupId(-1L);
		c.setGroupName("未分组");
		if( contacts != null){
			c.setCounts(contacts.size());
		}else{
			c.setCounts(0);
		}
		
		// 统计全部联系人情况
		Integer allCount = this.countContact(merchantPin,createBy);
		ContactVO all = new ContactVO();
		all.setBookGroupId(0L);
		all.setGroupName("全部联系人");
		all.setCounts(allCount);
		
		if(null != contactVOs){
			contactVOs.add(c);
			contactVOs.add(all);
		}else{
			contactVOs = new ArrayList<ContactVO>();
			contactVOs.add(c);
			contactVOs.add(all);
		}
		return contactVOs;
	}
	@Override
	public List<Contact> checkContactByMobile(String mobile, Long pinId, Long createBy, Long groupId){
		return contactDao.loadByMobileContact(mobile, pinId, createBy, groupId);
	}
	@Override
	public Group findGroupByNameAndPin(String name, Long pin, Long createBy) {
		List<Group> group = this.contactDao.loadByNameGroup(name, pin, createBy);
		if( group !=null && group.size() > 0 ){
			return group.get(0);
		}else{
			return null;
		}
	}
	public ContactsService getContactsService() {
		return contactsService;
	}
	@Override
	public Contact loadContactById(Long id) {
		return contactDao.loadContactById(id);
	}
}
