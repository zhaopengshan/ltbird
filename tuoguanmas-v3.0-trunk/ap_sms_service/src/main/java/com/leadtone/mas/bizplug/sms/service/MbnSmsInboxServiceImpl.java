package com.leadtone.mas.bizplug.sms.service; 
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource; 
import org.springframework.stereotype.Service;   
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsInboxDao;
 

@Service("mbnSmsInboxService")
@SuppressWarnings("unchecked")
public class MbnSmsInboxServiceImpl  implements
		MbnSmsInboxService {
	@Resource
	private MbnSmsInboxDao mbnSmsInboxDao;
	
	@Override
	public Page page(PageUtil pageUtil) {
		//  
		return  mbnSmsInboxDao.page(pageUtil);
	}

	public List<MbnSmsInbox> followPage(HashMap<String,Object> page){
		return mbnSmsInboxDao.followPage(page);
	}
	
	@Override
	public Integer pageCount(PageUtil pageUtil) {
		//  
		return  mbnSmsInboxDao.pageCount(pageUtil);
	}

	public List<MbnSmsInbox> getByPks(Long[] ids){
		return mbnSmsInboxDao.getByPks(ids);
	}
	@Override
	
	public MbnSmsInbox queryByPk(Long pk) {
		//  
		
//		List<MbnSmsInbox> i=(List<MbnSmsInbox>) mbnSmsInboxDao.queryByPk(pk);
//		return i.size()>0?(MbnSmsInbox)i.get(0):null;
		return (MbnSmsInbox) mbnSmsInboxDao.queryByPk(pk);
	}

	
	@Override
	public List<MbnSmsInbox> queryByPks(Long pk) {
		//  
		return (List<MbnSmsInbox>) mbnSmsInboxDao.queryByPk(pk);
	}
 	@Override
	public MbnSmsInbox queryByPin(Long pin) {
		
		 
		List<MbnSmsInbox>  i=(List<MbnSmsInbox>) mbnSmsInboxDao.queryByPin(pin);
		return i.size()>0?(MbnSmsInbox)i.get(0):null;
	}

	
	@Override
	public List<MbnSmsInbox> queryByPins(Long pin) {
		//  
		return (List<MbnSmsInbox>)mbnSmsInboxDao.queryByPin(pin);
	}
	

	
	@Override
	public Integer insert(MbnSmsInbox mbnSmsInbox) {
		//  
		return mbnSmsInboxDao.insert(mbnSmsInbox);
	}

	
	@Override
	public Integer update(MbnSmsInbox mbnSmsInbox) {
		//  
		return mbnSmsInboxDao.update(mbnSmsInbox);
	}

	@Override
	public Integer delete(MbnSmsInbox mbnSmsInbox) {
		//  
		return mbnSmsInboxDao.delete(mbnSmsInbox);
	}  
	
	/**
	 * 访问器 
	 * @return
	 */  
	public MbnSmsInboxDao getMbnSmsInboxDao() {
		return mbnSmsInboxDao;
	}

	public void setMbnSmsInboxDao(MbnSmsInboxDao mbnSmsInboxDao) {
		this.mbnSmsInboxDao = mbnSmsInboxDao;
	}

	@Override
	public Integer batchUpdateByList(List<MbnSmsInbox> paramList) {
		//  
		return mbnSmsInboxDao.batchUpdateByList(paramList);
	}


	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		//  
		return mbnSmsInboxDao.batchDeleteByPks(pks);
	}


	@Override
	public Integer batchDeleteByList(List<MbnSmsInbox> entitys) {
		//  
		return mbnSmsInboxDao.batchDeleteByList(entitys);
	}


	@Override
	public List<MbnSmsInbox> batchSelectByPks(Long[] pks) {
		//  
		return (List<MbnSmsInbox>) mbnSmsInboxDao.batchSelectByPks(pks);
	}

	@Override
	public Page pageVO(PageUtil pageUtil) {
		//  
		return mbnSmsInboxDao.pageVO(pageUtil);
	}

	@Override
	public Page replyPage(PageUtil pageUtil) {
		//  
		return mbnSmsInboxDao.replyPage(pageUtil);
	}

	@Override
	public List<MbnSmsInbox> getIndoxBycoding(String coding) {
		return mbnSmsInboxDao.getIndoxBycoding(coding);
	}
 

	@Override
	public Page export(PageUtil pageUtil) {
		//  
		return mbnSmsInboxDao.export(pageUtil);
	}
 

	@Override
	public List<MbnSmsInbox> getInboxAllInfo() {
		//  
		return mbnSmsInboxDao.getInboxAllInfo();
	}
 }
