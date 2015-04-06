package com.leadtone.mas.bizplug.calendar.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;
import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTask;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskExample;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskSms;
import com.leadtone.mas.bizplug.calendar.bean.MbnSmsDuanxinrili;
import com.leadtone.mas.bizplug.calendar.bean.MbnSmsDuanxinriliExample;
import com.leadtone.mas.bizplug.calendar.bean.MbnSmsDuanxinriliExample.Criteria;
import com.leadtone.mas.bizplug.calendar.dao.MbnPeriodcTaskDAO;
import com.leadtone.mas.bizplug.calendar.dao.MbnPeriodcTaskSmsDAO;
import com.leadtone.mas.bizplug.calendar.dao.MbnSmsDuanxinriliDAO;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.bean.MasCommonBean;
import com.leadtone.mas.bizplug.common.service.MasCommonService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsReadySendDao;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaoxuanxiang;

@Service("masSmsCalendarService")
public class MasSmsCalendarServiceImp implements MasSmsCalendarService{
	private static Log LOG = LogFactory.getLog(MasSmsCalendarServiceImp.class);
	
	@Resource
	private MbnPeriodcTaskDAO mbnPeriodcTaskDAO;
	@Resource
	private MbnPeriodcTaskSmsDAO mbnPeriodcTaskSmsDAO;
	@Resource
	private MbnSmsDuanxinriliDAO mbnSmsDuanxinriliDAO;
	
	@Resource
	private MbnSmsReadySendDao mbnSmsReadySendDao;
	
	@Resource
	private UsersDao userDao;
	
	@Resource
    private MbnMerchantVipIDao mbnMerchantVipIDao;
	
	@Resource(name="regionDAOImpl")
    IRegionDAO regionDAO;
	
	@Autowired
	private ContactsService contactsService;
	
	@Autowired
	private MasCommonService masCommonServiceImp;
	

	
	
	public void taskSendMsg(MbnPeriodcTask mbnPeriodcTask){
		// TODO Auto-generated method stub
		
		MbnSmsDuanxinrili mbnSmsDuanxinrili;
		try {
			mbnSmsDuanxinrili = this.getMbnSmsDuanxinriliById(mbnPeriodcTask.getId());
			Long pin=mbnSmsDuanxinrili.getMerchantPin();
			String tos=mbnSmsDuanxinrili.getTos();
			String title=mbnSmsDuanxinrili.getTitle();
			String content=mbnSmsDuanxinrili.getContent();
			Date submit_Time=mbnPeriodcTask.getLastTime();
			List<String> colsList=new ArrayList<String>();
			String[] mobiles=tos.split(",");
			for(String mobile:mobiles){
				colsList.add(mobile);
			}
			replyCalendarSMS(mbnPeriodcTask.getId(),colsList,mbnSmsDuanxinrili.getCreateBy(),pin,tos,title,content,submit_Time);
			// 增加执行次数
			mbnPeriodcTask.setSendTimes(mbnPeriodcTask.getSendTimes()+1);
			//更改周期表状态
			this.updatePeriodcTask(mbnPeriodcTask);
			//更新日历表状态为已发送
			mbnSmsDuanxinrili.setType(ApSmsConstants.STATUS_SENDING);
			mbnSmsDuanxinrili.setRemindtime(mbnPeriodcTask.getLastTime());
			mbnSmsDuanxinriliDAO.updateByPrimaryKeyWithBLOBs(mbnSmsDuanxinrili);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
	
	private void replyCalendarSMS(Long batchID,List<String> colsList,Long create_by,Long merchantPin,String receiver,String title,String smsText,Date sendTime){
		try{
			MasCommonBean masCommonBean=new MasCommonBean();
			masCommonBean.setBatchId(batchID);
			masCommonBean.setCodeType(ApSmsConstants.OPERATION_CODING_TX);
			masCommonBean.setColsList(colsList);
			masCommonBean.setEntSign("");
			masCommonBean.setFlag(null);
			Users longinUser=userDao.findById(create_by);
			masCommonBean.setLoginUser(longinUser);
			masCommonBean.setMerchantPin(merchantPin);
			masCommonBean.setOperationType(ApSmsConstants.OPERATION_CODING_TYPE_TX);
			MbnMerchantVip merchant = mbnMerchantVipIDao.load(merchantPin);
			String prov = merchant.getProvince();
			Region region = regionDAO.queryByProvinceId(Long.parseLong(prov));
			String provCode = region.getCode();
			masCommonBean.setProv_code(provCode);
			masCommonBean.setReadySendTime(sendTime);
			masCommonBean.setReceiver(receiver);
			masCommonBean.setReplyCode("");
			masCommonBean.setReplyText("");
			masCommonBean.setSmsText(smsText);
			masCommonBean.setTitle(title);
			Set<Contacts> userSet=getContactsSet(contactsService, receiver, longinUser.getMerchantPin(),create_by);
			masCommonBean.setUserSet(userSet);
			masCommonBean.setYdTunnel(0L);
			masCommonServiceImp.insertMbnSmsSendTask(masCommonBean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static Set<Contacts> getContactsSet(ContactsService contactsService,
			String info, Long merchantPin, Long createBy) {
		Set<Contacts> set = new HashSet<Contacts>();
		if (info != null && info.trim().length() > 0) {
			StringTokenizer st = new StringTokenizer(info, ",");
			while (st.hasMoreTokens()) {
				String mobile = null;
				String name = null;
				String uInfo = st.nextToken();
				uInfo = uInfo.trim();
				// 判断用户还是组
				if (uInfo.endsWith("<用户组>")) {
					String gName = uInfo.substring(0, uInfo.indexOf("<"));
					List<Contacts> list = contactsService.getContactsByGroupName(merchantPin, 
							gName.trim(), createBy);
					if (list != null) {
						for (Contacts contact : list) {
							if( isMobile(contact.getMobile())){
								Contacts c = new Contacts(contact.getMobile(),
										contact.getName());
								set.add(c);
							}
						}
					}
				} else {
					if (uInfo.indexOf("<") >= 0) {
						mobile = uInfo.substring(0, uInfo.indexOf("<"));
						name = uInfo.substring(uInfo.indexOf("<") + 1, uInfo
								.length() - 1);
					} else {
						mobile = uInfo;
						List<Contact> tContact = contactsService.checkContactByMobile(
								mobile.trim(), merchantPin, createBy, null);
						if( tContact!=null && tContact.size()>0 ){
							name = tContact.get(0).getName();
						}
					}
					mobile = mobile.trim();
					if( isMobile(mobile)){
						Contacts c = new Contacts(mobile, name);
						set.add(c);
					}
				}
			}
		}
		return set;
	}
	
	private static boolean isMobile(String mobile){
		if (null == mobile || "".equals(mobile.trim())) {
			return false;
		}
		return Pattern.matches("^1\\d{10}$", mobile);
	}
	
	private boolean isQueryReadySend(Map<String,Object> mapPara){
		String sendResult="0";
		try{
			if(mapPara.containsKey("sendResult")){
				sendResult=(String)mapPara.get("sendResult");
				mapPara.remove("sendResult");
			}
		}catch(Exception se){
			LOG.error(se.getMessage());
		}
		if(sendResult.equals("0")){
			return true;
		}
		return false;
	}
	
	@Override
	public List<MbnSmsDuanxinrili> getAllCalendarList(Map<String,Object> mapPara){
		List<MbnSmsDuanxinrili> listSmsrili=null;
		try {
			if(isQueryReadySend(mapPara))
				listSmsrili=mbnSmsDuanxinriliDAO.selectByCreateWithoutExample(mapPara);
			else
				listSmsrili=mbnSmsDuanxinriliDAO.selectHadSendByCreateWithoutExample(mapPara);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return listSmsrili;
	}
	@Override
	public int getAllCalendarCount(Map<String,Object> mapPara){
		int count=0;
		try {
			if(isQueryReadySend(mapPara))
				count=mbnSmsDuanxinriliDAO.selectCountByCreateWithoutExample(mapPara);
			else
				count=mbnSmsDuanxinriliDAO.selectHadSendCountByCreateWithoutExample(mapPara);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return count;
	}
	@Override
	public MbnSmsDuanxinrili getMbnSmsDuanxinriliById(Long id) throws SQLException{
		return mbnSmsDuanxinriliDAO.selectByPrimaryKey(id);
	}
	@Override
	@Transactional( rollbackFor={Exception.class})
	public void deleteCalendarById(List<Long> calendarIds) throws SQLException{
			try {
				for(Long id:calendarIds){
					//删除周期表中周期任务
					mbnPeriodcTaskDAO.deleteByPrimaryKey(id);
					//删除日历表中任务提醒
					mbnSmsDuanxinriliDAO.deleteByPrimaryKey(id);
					//修改短信发送表中所有还未发送的状态为取消发送
				}
				HashMap<String, Object> cancelPro=null;
				cancelPro=new HashMap<String, Object>();
				cancelPro.put("newResult", -1);
				cancelPro.put("failReason", "delete");
				cancelPro.put("oldResult", 0);
				String ids="";
				for(int i=0;i<calendarIds.size();i++){
					ids+=calendarIds.get(i);
					if(i!=calendarIds.size()-1)
						ids+=",";
				}
				cancelPro.put("ids", ids);
				mbnSmsReadySendDao.cancelSend(cancelPro);
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				throw new SQLException("delete calendar error");
			}
	}
	
	
	@Transactional( rollbackFor={Exception.class})
	@Override
	public void createRiliInfo(MbnSmsDuanxinrili mbnSmsDuanxinrili,MbnPeriodcTaskSms mbnPeriodcTaskSms,MbnPeriodcTask mbnPeriodcTask) throws SQLException{
		try{
			//先插入日历信息表
			mbnSmsDuanxinriliDAO.insert(mbnSmsDuanxinrili);
			//根据查入结果将日历信息插入周期任务表
			if(mbnPeriodcTask!=null){
				//mbnPeriodcTaskSmsDAO.insert(mbnPeriodcTaskSms);
				mbnPeriodcTaskDAO.insert(mbnPeriodcTask);
			}else{
				//调用发送程序装短信信息发送
				LOG.info("暂时在WEB层.....");
			}
		}catch(Exception se){
			LOG.error(se.getMessage());
			throw new SQLException(se.getMessage());
		}
	}
	
	@Override
	public List<MbnPeriodcTask> getPeriodcTaskList(Map<String,Object> paraMap){
		List<MbnPeriodcTask>  mbnPeriodcTaskList=null;
		try {
			mbnPeriodcTaskList=mbnPeriodcTaskDAO.selectTaskList(paraMap);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return mbnPeriodcTaskList;
	}
	@Override
	public int updatePeriodcTask(MbnPeriodcTask mbnPeriodcTask){
		int execNum=0;
		try {
			execNum=mbnPeriodcTaskDAO.updateByPrimaryKey(mbnPeriodcTask);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return execNum;
	}
	
	

}
