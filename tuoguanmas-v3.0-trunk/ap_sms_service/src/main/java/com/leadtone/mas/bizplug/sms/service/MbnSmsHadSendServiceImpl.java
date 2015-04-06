/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsHadSendDao;

/**
 * @author PAN-Z-G
 *
 */
@Service("mbnSmsHadSendService")
@Transactional
@SuppressWarnings("unchecked")
public class MbnSmsHadSendServiceImpl implements MbnSmsHadSendService {
		@Resource
		private MbnSmsHadSendDao mbnSmsHasSendDao;
	
	
	public Page batchPage(PageUtil pageUtil){
		return mbnSmsHasSendDao.batchPage(pageUtil);
	}
	public Page page(PageUtil pageUtil){
		return mbnSmsHasSendDao.page(pageUtil);
	} 

	public MbnSmsHadSendVO queryByPk(Long id){
		return (MbnSmsHadSendVO) mbnSmsHasSendDao.queryByPk(id);
	}
	/**
	 * 查询批次短信
	 * @param batchId
	 * @return
	 */
	public List<MbnSmsHadSend> getByBatchId(Long batchId, Long mPin, Long createBy){
		return mbnSmsHasSendDao.getByBatchId(batchId, mPin, createBy);
	}
	public List<MbnSmsHadSend> getByPks(Long[] ids){
		return mbnSmsHasSendDao.getByPks(ids);
	}
	public List<MbnSmsHadSendVO> followPage(HashMap<String,Object> page){
		return mbnSmsHasSendDao.followPage(page);
	}
	@Override
	
	public List<MbnSmsHadSend> mbnSmsHasSendByPks(long pk) {
		// 
		return (List<MbnSmsHadSend>) mbnSmsHasSendDao.queryByPk(pk);
	}

	 
	@Override
	
	public MbnSmsHadSend mbnSmsHasSendByPk(long pk) {
		// 
		 List<MbnSmsHadSend>  l=(List<MbnSmsHadSend>)mbnSmsHasSendDao.queryByPk(pk);
		return l.size()>0?(MbnSmsHadSend)l.get(0):null;
	}
 
	@Override
	
	public List<MbnSmsHadSend> mbnSmsHasSendByPins(long pin) {
		// 
		return (List<MbnSmsHadSend>) mbnSmsHasSendDao.queryByPin(pin);
	}

	 
	@Override
	
	public MbnSmsHadSend mbnSmsHasSendByPin(long pin) {
		
		List<MbnSmsHadSend> l=(List<MbnSmsHadSend>)mbnSmsHasSendDao.queryByPin(pin);
		return l.size()>0?(MbnSmsHadSend)l.get(0):null;
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {
		//
		return mbnSmsHasSendDao.pageCount(pageUtil);
	}


	
	@Override
	public Integer insert(MbnSmsHadSend mbnSmsHadSend) {
		//
		return mbnSmsHasSendDao.insert(mbnSmsHadSend);
	}


	
	@Override
	public Integer update(MbnSmsHadSend mbnSmsHadSend) {
		//
		return mbnSmsHasSendDao.update(mbnSmsHadSend);
	}

	public	Integer updateDel(String smsIds){
		 return mbnSmsHasSendDao.updateDel(smsIds);
	 }
	
	@Override
	public Integer delete(MbnSmsHadSend mbnSmsHadSend) {
		//
		return mbnSmsHasSendDao.delete(mbnSmsHadSend);
	}
	 
		/**
		 * 访问器
		 * @return
		 */
		public MbnSmsHadSendDao getMbnSmsHasSendDao() {
			return mbnSmsHasSendDao;
		}

		public void setMbnSmsHasSendDao(MbnSmsHadSendDao mbnSmsHasSendDao) {
			this.mbnSmsHasSendDao = mbnSmsHasSendDao;
		}


		@Override
		public Integer batchUpdateByList(List<MbnSmsHadSend> paramList) {
			// TODO Auto-generated method stub
			return mbnSmsHasSendDao.batchUpdateByList(paramList);
		}


		@Override
		public Integer batchDeleteByPks(Long[] pks) {
			// TODO Auto-generated method stub
			return mbnSmsHasSendDao.batchDeleteByPks(pks);
		}


		@Override
		public Integer batchDeleteByList(List<MbnSmsHadSend> entitys) {
			// TODO Auto-generated method stub
			return mbnSmsHasSendDao.batchDeleteByList(entitys);
		}


		@Override
		public List<MbnSmsHadSend> batchSelectByPks(Long[] pks) {
			// TODO Auto-generated method stub
			return (List<MbnSmsHadSend>) mbnSmsHasSendDao.batchSelectByPks(pks);
		}


		@Override
		public Page pageVO(PageUtil pageUtil) {
			// TODO Auto-generated method stub
			return mbnSmsHasSendDao.pageVO(pageUtil);
		}
		@Override
		public Page replyPage(PageUtil pageUtil) {
			// TODO Auto-generated method stub
			return mbnSmsHasSendDao.replyPage(pageUtil);
		}
		@Override
		public Page extPortAll(PageUtil pageUtil) {
			// TODO Auto-generated method stub
			return mbnSmsHasSendDao.extport(pageUtil);
		}
		@Override
		public Integer updateDelByBatchIds(Long[] batchIds) {
			return mbnSmsHasSendDao.updateDelByBatchId(batchIds);
		}
		 
 
}
