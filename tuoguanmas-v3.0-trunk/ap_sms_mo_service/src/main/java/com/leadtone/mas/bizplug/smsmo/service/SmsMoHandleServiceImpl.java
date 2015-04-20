package com.leadtone.mas.bizplug.smsmo.service;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.dao.ContactDao;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;
import com.leadtone.mas.bizplug.sms.dao.MbnMerchantTunnelRelationMoDao;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsInboxMoDao;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsReadySendDao;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.util.spring.AppConfig;

@Service("smsMoHandleService")
@Transactional
public class SmsMoHandleServiceImpl implements SmsMoHandleService {

	@Resource
	private SmsMoLogDao smsMoLogDao;
	@Resource
	private MbnSmsInboxMoDao mbnSmsInboxMoDao;
	@Resource
	private MbnMerchantTunnelRelationMoDao mbnMerchantTunnelRelationMoDao;
	@Resource
	private ContactDao contactDao;

	@Resource
	private UsersDao userDao;

	@Resource
	private MbnSmsReadySendDao mbnSmsReadySendDaoImpl;

	private final Logger logger = Logger
			.getLogger(SmsMoHandleServiceImpl.class);

	public SmsMoLogDao getSmsMoLogDao() {
		return smsMoLogDao;
	}

	public void setSmsMoLogDao(SmsMoLogDao smsMoLogDao) {
		this.smsMoLogDao = smsMoLogDao;
	}

	public MbnSmsInboxMoDao getMbnSmsInboxMoDao() {
		return mbnSmsInboxMoDao;
	}

	public void setMbnSmsInboxMoDao(MbnSmsInboxMoDao mbnSmsInboxMoDao) {
		this.mbnSmsInboxMoDao = mbnSmsInboxMoDao;
	}

	public MbnMerchantTunnelRelationMoDao getMbnMerchantTunnelRelationMoDao() {
		return mbnMerchantTunnelRelationMoDao;
	}

	public void setMbnMerchantTunnelRelationMoDao(
			MbnMerchantTunnelRelationMoDao mbnMerchantTunnelRelationMoDao) {
		this.mbnMerchantTunnelRelationMoDao = mbnMerchantTunnelRelationMoDao;
	}

	public ContactDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	public UsersDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UsersDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<SmsMoLogBean> getSmsMoLogs(int status, String type) {
		return this.smsMoLogDao.queryByStatus(status, type);
	}
	@Override
	public List<SmsMoLogBeanVO> queryByStatusVo(int status, String type){
		return this.smsMoLogDao.queryByStatusVo(status, type);
	}

	/**
	 * user java reg to filter phone number and replace 86 or +86 only filter
	 * start with "+86" or "86" ex +8615911119999 13100009999 replace +86 or 86
	 * with ""
	 * 
	 * @param phoneNum
	 * @return
	 * @throws Exception
	 */
	protected String filterPhoneNum(String phoneNum) {

		Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
		Matcher m1 = p1.matcher(phoneNum);
		if (m1.matches()) {
			Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
			Matcher m2 = p2.matcher(phoneNum);
			StringBuffer sb = new StringBuffer();
			while (m2.find()) {
				m2.appendReplacement(sb, "");
			}
			m2.appendTail(sb);
			return sb.toString();
		}
		logger.info("The format of phoneNum " + phoneNum + " is not correct!");
		return "";
	}

	private MbnMerchantTunnelRelation getMbnMerchantTunnelRelation(
			String sender, String receiver) {
		try {
			List<MbnMerchantTunnelRelation> listMbnMerchantTunnelRelation = this.mbnMerchantTunnelRelationMoDao
					.findByAccessNumber(receiver);
			if (listMbnMerchantTunnelRelation.size() >= 1) {
				return listMbnMerchantTunnelRelation.get(0);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("Can't find MbnMerchantTunnelRelation by sender[" + sender
				+ "] and receiver[" + receiver + "]");
		return null;
	}

	private String getSenderName(String sender, Long pinId) {
		List<Contact> contacts = this.contactDao.loadByMobileContact(sender,
				pinId, null, null);
		if (contacts != null && contacts.size() > 0) {
			return contacts.get(0).getName();
		}
		return "";
	}
	@Override
	public MbnSmsInbox parseContent(SmsMoLogBeanVO smsMoLogBean) {
		try {
			String sender = filterPhoneNum(smsMoLogBean.getSender());

			/* 内容格式为：两位字母业务类型+三位数字编号+上行内容 */
			String strContent = smsMoLogBean.getContent();
			
			MbnSmsInbox mbnSmsInbox = new MbnSmsInbox();
			mbnSmsInbox.setId(PinGen.getSerialPin());
			mbnSmsInbox.setStatus(0); // 未读
			mbnSmsInbox.setSenderMobile(sender);
			mbnSmsInbox.setWebService(1);
			mbnSmsInbox.setClassify(smsMoLogBean.getClassify());
			try {
				UserVO userVO = new UserVO();
				
				if(smsMoLogBean.getType().equalsIgnoreCase("QXT")){
					//userid 为qxt用户 分配的ID
					userVO.setZxtUserId(smsMoLogBean.getUserId().toString());
				}else if(smsMoLogBean.getType().equalsIgnoreCase("QXTNEW")){
					//用户 的真实ID
					userVO.setId(smsMoLogBean.getUserId());
				}
				
				Users user = userDao.findByAccount(userVO);
				if( user != null ){
					//获取用户名
					mbnSmsInbox.setSenderName(getSenderName(sender, user.getMerchantPin()));
					mbnSmsInbox.setMerchantPin(user.getMerchantPin());
					mbnSmsInbox.setWebService(user.getWebService());
					
					mbnSmsInbox.setServiceCode("");
					mbnSmsInbox.setOperationId("");
					mbnSmsInbox.setContent(strContent);
					
					Long batchId = mbnSmsReadySendDaoImpl.getBatchIdByCpoid(smsMoLogBean.getCpoid(), user.getMerchantPin());
					if (batchId == null) {
						batchId = mbnSmsReadySendDaoImpl.getBatchIdByCpoidFromHad(smsMoLogBean.getCpoid(), user.getMerchantPin());
					}
					mbnSmsInbox.setReplyBatchId(batchId);
				}
			} catch (Exception e) {
				logger.info("QXT :" + e.getMessage());
			}
			mbnSmsInbox.setReceiverAccessNumber(smsMoLogBean.getUserId().toString());
			mbnSmsInbox.setReceiveTime(smsMoLogBean.getCreateTime());

			return mbnSmsInbox;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}
		
	@Override
	public MbnSmsInbox parseContent(SmsMoLogBean smsMoLogBean) {
		try {
			int userExtDist = AppConfig
					.getValueAsInt("sms.access.user.ext");
			int bizExtDist = AppConfig
					.getValueAsInt("sms.access.biz.ext");
			// 获取手机号及商户PIN码，然后依据此获取联系人姓名
			String sender = filterPhoneNum(smsMoLogBean.getSender());
			String accessNumber = smsMoLogBean.getReceiver();
			int accessNumberLen = accessNumber.length();
			
			if( accessNumberLen > ( userExtDist + bizExtDist ) ){
				String tunnelAccessNumber =  accessNumber.substring( 0, accessNumberLen - ( userExtDist + bizExtDist ) );
				String userBizExt =  accessNumber.substring( accessNumberLen - ( userExtDist + bizExtDist ) );
				//根据通道 accessnumber 查询企业关联
				MbnMerchantTunnelRelation mbnMerchantTunnelRelation = getMbnMerchantTunnelRelation(sender, tunnelAccessNumber);
				
				MbnSmsInbox mbnSmsInbox = new MbnSmsInbox();
				mbnSmsInbox.setId(PinGen.getSerialPin());
				mbnSmsInbox.setStatus(0); // 未读
				mbnSmsInbox.setSenderMobile(sender);
				mbnSmsInbox.setWebService(1);
				mbnSmsInbox.setClassify(smsMoLogBean.getClassify());
//				int accLen = smsMoLogBean.getReceiver().length();
				
				if (mbnMerchantTunnelRelation != null) {
					mbnSmsInbox.setSenderName(getSenderName(sender, mbnMerchantTunnelRelation.getMerchantPin()));
					mbnSmsInbox.setMerchantPin(mbnMerchantTunnelRelation.getMerchantPin());
					try {
						UserVO userVO = new UserVO();
						userVO.setMerchantPin(mbnMerchantTunnelRelation.getMerchantPin());
						userVO.setUserExtCode(userBizExt.substring(0, userExtDist));
						mbnSmsInbox.setServiceCode(userBizExt.substring(userExtDist, userBizExt.length()));
						mbnSmsInbox.setOperationId(userBizExt.substring(0, 2));
						mbnSmsInbox.setContent(smsMoLogBean.getContent());
//						mbnSmsInbox.setClassify(smsMoLogBean.getClassify());
						Long batchId = mbnSmsReadySendDaoImpl.getBatchIdByAcc(smsMoLogBean.getReceiver());
						if (batchId == null) {
							batchId = mbnSmsReadySendDaoImpl.getBatchIdByAccFromHad(smsMoLogBean.getReceiver());
						}
						if(batchId==null){
							batchId = 0L;
						}

						mbnSmsInbox.setReplyBatchId(batchId);

						logger.info("Can't find user by userVO[" + userVO.toString() + "]");
						Users user = userDao.findByAccount(userVO);
						mbnSmsInbox.setWebService(user.getWebService());
					} catch (Exception e) {
						logger.info("gw mo to inbox error--select user&batchid error:", e);
					}
					
					mbnSmsInbox.setReceiverAccessNumber(smsMoLogBean.getReceiver());
					mbnSmsInbox.setReceiveTime(smsMoLogBean.getCreateTime());
					return mbnSmsInbox;
				}else{
					logger.info("Can't find MbnMerchantTunnelRelation by tunnelAccessNumber[" + tunnelAccessNumber + "]");
					mbnMerchantTunnelRelation = getMbnMerchantTunnelRelation(sender, accessNumber);
					if(mbnMerchantTunnelRelation!=null){
						mbnSmsInbox.setSenderName(getSenderName(sender, mbnMerchantTunnelRelation.getMerchantPin()));
						mbnSmsInbox.setMerchantPin(mbnMerchantTunnelRelation.getMerchantPin());
						try {
							UserVO userVO = new UserVO();
							userVO.setMerchantPin(mbnMerchantTunnelRelation.getMerchantPin());
//							userVO.setUserExtCode(userBizExt.substring(0, userExtDist));
							userVO.setUserType(3);
							mbnSmsInbox.setServiceCode("00");
							mbnSmsInbox.setOperationId("00");
							mbnSmsInbox.setContent(smsMoLogBean.getContent());
//							mbnSmsInbox.setClassify(smsMoLogBean.getClassify());
							Long batchId = mbnSmsReadySendDaoImpl.getBatchIdByAcc(smsMoLogBean.getReceiver());
							if (batchId == null) {
								batchId = mbnSmsReadySendDaoImpl.getBatchIdByAccFromHad(smsMoLogBean.getReceiver());
							}
							if(batchId==null){
								batchId = 0L;
							}

							mbnSmsInbox.setReplyBatchId(batchId);

							logger.info("Can't find user by userVO[" + userVO.toString() + "]");
							Users user = userDao.findByAccount(userVO);
							mbnSmsInbox.setWebService(user.getWebService());
						} catch (Exception e) {
							logger.info("gw mo to inbox error--select user&batchid error:", e);
						}
						
						mbnSmsInbox.setReceiverAccessNumber(smsMoLogBean.getReceiver());
						mbnSmsInbox.setReceiveTime(smsMoLogBean.getCreateTime());
						return mbnSmsInbox;
					}else{
						logger.info("Can't find MbnMerchantTunnelRelation by tunnelAccessNumber[" + tunnelAccessNumber + " 忽略 处理]");
					}
				}
			}
		}catch(Exception e){
			logger.info("gw mo to inbox error:", e);
		}
		return null;
}
			
//back up			
//	public MbnSmsInbox parseContent(SmsMoLogBean smsMoLogBean) {
//		try {
//			boolean upUseExtDist = AppConfig
//					.getValueAsBoolean("sms.up.distbyext");
//			// 获取手机号及商户PIN码，然后依据此获取联系人姓名
//			String sender = filterPhoneNum(smsMoLogBean.getSender());
//			//通过 接收人 和 接收号 判断 无扩展情况下 是否有通道存在。
//			MbnMerchantTunnelRelation mbnMerchantTunnelRelation = getMbnMerchantTunnelRelation(
//					sender, smsMoLogBean.getReceiver());
//
//			/* 内容格式为：两位字母业务类型+三位数字编号+上行内容 */
//			String strContent = smsMoLogBean.getContent();
//			if (!upUseExtDist) {
//				if (!Pattern.compile("^[a-zA-Z]{2}\\d{3}").matcher(strContent)
//						.find()) {
//					logger.info("Format error, ignore");
//					// return null;
//				}
//			}
//
//			MbnSmsInbox mbnSmsInbox = new MbnSmsInbox();
//			mbnSmsInbox.setId(PinGen.getSerialPin());
//			mbnSmsInbox.setStatus(0); // 未读
//			mbnSmsInbox.setSenderMobile(sender);
//			mbnSmsInbox.setWebService(1);
//			int accLen = smsMoLogBean.getReceiver().length();
//
//			if (mbnMerchantTunnelRelation == null) {
//				String reciver = accLen > 4 ? smsMoLogBean.getReceiver()
//						.substring(0, accLen - 4) : smsMoLogBean.getReceiver();
//				mbnMerchantTunnelRelation = getMbnMerchantTunnelRelation(
//						sender, reciver);
//			}
//			if (mbnMerchantTunnelRelation != null) {
//				mbnSmsInbox.setSenderName(getSenderName(sender,
//						mbnMerchantTunnelRelation.getMerchantPin()));
//				mbnSmsInbox.setMerchantPin(mbnMerchantTunnelRelation
//						.getMerchantPin());
//				try {
//					String ydExt = accLen > 4 ? smsMoLogBean.getReceiver()
//							.substring(accLen - 4) : "";
//					UserVO userVO = new UserVO();
//					userVO.setMerchantPin(mbnMerchantTunnelRelation
//							.getMerchantPin());
//					if (upUseExtDist) {
//						userVO.setUserExtCode(ydExt.substring(2));
//						mbnSmsInbox.setServiceCode(ydExt.substring(2, 4));
//						mbnSmsInbox.setOperationId(ydExt.substring(0, 2));
//						mbnSmsInbox.setContent(strContent);
//						Long batchId = mbnSmsReadySendDaoImpl
//								.getBatchIdByAcc(smsMoLogBean.getReceiver());
//						if (batchId == null) {
//							batchId = mbnSmsReadySendDaoImpl
//									.getBatchIdByAccFromHad(smsMoLogBean
//											.getReceiver());
//						}
//
//						mbnSmsInbox.setReplyBatchId(batchId);
//					} else {
//						userVO.setUserExtCode(ydExt);
//					}
//
//					Users user = userDao.findByAccount(userVO);
//					mbnSmsInbox.setWebService(user.getWebService());
//
//				} catch (Exception e) {
//
//				}
//			} else {// 资信通通道
//				try {
//					UserVO userVO = new UserVO();
//					//userVO.setZxtId(Integer.parseInt(smsMoLogBean.getReceiver()));
//					userVO.setZxtUserId(smsMoLogBean.getReceiver());
//					Users user = userDao.findByAccount(userVO);
//					mbnSmsInbox.setSenderName(getSenderName(sender,
//							user.getMerchantPin()));
//					mbnSmsInbox.setMerchantPin(user.getMerchantPin());
//					mbnSmsInbox.setWebService(user.getWebService());
//					if (upUseExtDist) {
//						mbnSmsInbox.setServiceCode(smsMoLogBean.getReceiver()
//								.substring(2, 4));
//						mbnSmsInbox.setOperationId(smsMoLogBean.getReceiver()
//								.substring(0, 2));
//						mbnSmsInbox.setContent(strContent);
//						Long batchId = mbnSmsReadySendDaoImpl
//								.getBatchIdByAcc(smsMoLogBean.getReceiver());
//						if (batchId == null) {
//							batchId = mbnSmsReadySendDaoImpl
//									.getBatchIdByAccFromHad(smsMoLogBean
//											.getReceiver());
//						}
//						mbnSmsInbox.setReplyBatchId(batchId);
//					}
//				} catch (Exception e) {
//					logger.info("QXT :" + e.getMessage());
//				}
//			}
//
//			mbnSmsInbox.setReceiverAccessNumber(smsMoLogBean.getReceiver());
//			if (!upUseExtDist) {
//				if (!Pattern.compile("^[a-zA-Z]{2}\\d{3}").matcher(strContent)
//						.find()) {
//					mbnSmsInbox.setServiceCode("");
//					mbnSmsInbox.setOperationId("");
//					mbnSmsInbox.setContent(strContent);
//					mbnSmsInbox.setReplyBatchId(null);
//
//				} else {
//					mbnSmsInbox.setServiceCode(strContent.substring(2, 5));
//					mbnSmsInbox.setOperationId(strContent.substring(0, 2));
//					mbnSmsInbox.setContent(strContent.substring(5,
//							strContent.length()));
//					HashMap<String, Object> param = new HashMap<String, Object>();
//					param.put("service", strContent.substring(0, 2));
//					param.put("opt",
//							Integer.parseInt(strContent.substring(2, 5)));
//					Long batchId = mbnSmsReadySendDaoImpl
//							.getBatchByServiceAndOpt(param);
//					mbnSmsInbox.setReplyBatchId(batchId);
//				}
//			}
//
//			mbnSmsInbox.setReceiveTime(smsMoLogBean.getCreateTime());
//
//			return mbnSmsInbox;
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//		}
//		return null;
//	}

	@Override
	public void addSmsInbox(MbnSmsInbox bean) {
		this.mbnSmsInboxMoDao.insert(bean);
	}

	@Override
	public void updateSmsMoLogStatus(SmsMoLogBean bean) {
		this.smsMoLogDao.updateStatus(bean);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
