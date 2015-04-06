package com.leadtone.mas.bizplug.vote.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.leadtone.mas.bizplug.addr.dao.ContactDao;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.bean.MasCommonBean;
import com.leadtone.mas.bizplug.common.service.MasCommonService;
import com.leadtone.mas.bizplug.common.service.MasCommonServiceImp;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaoxuanxiang;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteExportList;
import com.leadtone.mas.bizplug.vote.dao.MasVoteDao;
import com.leadtone.mas.bizplug.vote.dao.MasVoteDaoImpl;
import com.leadtone.mas.bizplug.vote.dao.MasVoteResultDao;
import com.opensymphony.xwork2.ActionContext;
@Service("masVoteService")
@Transactional
public class MasVoteServiceImpl  implements MasVoteService  {
	private static final Log log = LogFactory.getLog(MasVoteServiceImpl.class);
	@Resource
	private MasVoteDao voteDao;
	@Resource
    private MbnMerchantVipIDao mbnMerchantVipIDao;
	 @Resource(name="regionDAOImpl")
	    IRegionDAO regionDAO;
	@Resource
	private MasVoteResultDao voteResultDao;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private MasCommonService masCommonServiceImp;
	@Resource
	private UsersDao userDao;
	@Override
	public boolean insertVote(MasSmsToupiaodiaocha vote) {
		Date nowDate = Calendar.getInstance().getTime();
		vote.setCommit_time(nowDate);
		return voteDao.insertVote(vote);
	}

	@Override
	public boolean insertVoteOption(MasSmsToupiaoxuanxiang option) {
		Date nowDate = Calendar.getInstance().getTime();
		option.setCreate_time(nowDate);
		option.setModify_time(nowDate);
		return voteDao.insertVoteOption(option);
	}

	@Override
	public boolean insertBatchVoteOptions(List<MasSmsToupiaoxuanxiang> options) {
		Date nowDate = Calendar.getInstance().getTime();
		for(int i=0;i<options.size();i++){
			MasSmsToupiaoxuanxiang option=options.get(i);
			option.setCreate_time(nowDate);
			option.setModify_time(nowDate);
		}
		return voteDao.insertBatchVoteOptions(options);
	}

	@Override
	public boolean closeVote(Long id) {
		return voteDao.closeVote(id);
	}

	@Override
	public boolean deleteVote(Long id) {
		return voteDao.deleteVote(id);
	}

	@Override
	public boolean deleteBatchVote(List<Long> deleList) {
		return voteDao.deleteBatchVote(deleList);
	}

	@Override
	public List<MasSmsToupiaoxuanxiang> getOptionList(Long id) {
		// TODO Auto-generated method stub
		return voteDao.getOptionList(id);
	}

	@Override
	public boolean delteVoteResult(Long id) {
		// TODO Auto-generated method stub
		return voteResultDao.delteVoteResult(id);
	}

	@Override
	public boolean deleteBatchVoteResult(List<Long> deleList) {
		
		// TODO Auto-generated method stub
		return voteResultDao.deleteBatchVoteResult(deleList);
	}

	@Override
	public boolean insertVoteResult(MasSmsToupiaojieguo result) {
		Date nowDate = Calendar.getInstance().getTime();
		result.setCreate_time(nowDate);
		// TODO Auto-generated method stub
		return voteResultDao.insertVoteResult(result);
	}

	@Override
	public boolean insertBatchVoteResult(List<MasSmsToupiaojieguo> results) {
		Date nowDate = Calendar.getInstance().getTime();
		for(int i=0;i<results.size();i++){
			MasSmsToupiaojieguo result=results.get(i);
			result.setCreate_time(nowDate);
		}
		// TODO Auto-generated method stub
		return voteResultDao.insertBatchVoteResult(results);
		
	}

	@Override
	public List<MasSmsToupiaojieguo> getVoteResultByID(Long id) {
		return voteResultDao.getVoteResultByID(id);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return voteDao.page(pageUtil);
	}

	@Override
	public Page pageResult(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		return voteResultDao.page(pageUtil);
	}
	public MasVoteDao getMasVoteDao() {
		return voteDao;
	}
	public MasVoteResultDao getMasVoteResultDao() {
		return voteResultDao;
	}

	@Override
	public MasSmsToupiaodiaocha queryVoteById(Long id) {
		// TODO Auto-generated method stub
		return voteDao.queryVoteById(id);
	}

	@Override
	public MasSmsToupiaodiaocha queryVoteByTaskNumber(
			Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return voteDao.queryVoteByTaskNumber(paraMap);
	}

	@Override
	public List<MasSmsToupiaojieguo> queryVoteResultByNum(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return voteResultDao.queryVoteResultByNum(paraMap);
	}

	@Override
	public void handleVoteInbox(MbnSmsInbox inbox) {
		// TODO Auto-generated method stub
		Map<String,Object> paraMap=new HashMap<String, Object>();
		Long pin=inbox.getMerchantPin();
		String taskNumber=inbox.getServiceCode();
		String userNum=inbox.getSenderMobile();
		String userName=inbox.getSenderName();
		String content=inbox.getContent();
		Date submit_Time=inbox.getReceiveTime();
		paraMap.put("merchant_pin",pin);
		paraMap.put("taskNumber",taskNumber);
		MasSmsToupiaodiaocha vote= voteDao.queryVoteByTaskNumber(paraMap);
		if(vote==null){
			log.info("没有相关的投票");
			return;
		}
		List<MasSmsToupiaoxuanxiang> options=voteDao.getOptionList(vote.getId());
		List<String> colsList=new ArrayList();
		colsList.add(inbox.getSenderMobile());
		
		if(vote.getDeleted()==1){
			log.info("投票已删除");
			return;
		}
		Date endTime=vote.getEnd_time();
		Date startTime=vote.getBegin_time();
		if(endTime.compareTo(submit_Time)==-1){
			log.info("投票已结束");
			if(vote.getNeed_not_permmit_remind()==1)
				log.info("发送投票已结束");
				replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_not_permmit_content());
			return;
		}
		/*if(startTime.compareTo(submit_Time)!=1){
			if(vote.getNeed_not_permmit_remind()==1)
				replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_not_permmit_content());
			return;
		}*/
		if(vote.getTos().indexOf("<"+userNum+">")<0){
			log.info("投票人未在参与者中");
			if(vote.getNeed_not_permmit_remind()==1)
				log.info("发送短信 投票人未在参与者中");
				replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_not_permmit_content());
			return;
		}
		int nulti_number=vote.getMulti_selected_number();
		if(content==null || "".equals(content.trim())){
			log.info("投票内容为空");
			if(vote.getNeed_content_error_remind()==1)
				log.info("发送投票内容为空短信");
				replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_content_error_content());
			return;
		}
		String[] userContents=content.trim().split(",");
		if(userContents.length>nulti_number){
			if(vote.getNeed_content_error_remind()==1)
				//sendReplyTPSMS(vote.getNeed_content_error_content());
				replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_content_error_content());
			return;
		}
		for(int j=0;j<userContents.length;j++){
			int userContent=0;
			try{
				userContent=Integer.parseInt(userContents[j]);
				if(userContent>options.size() || userContent<1){
					if(vote.getNeed_content_error_remind()==1)
						replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_content_error_content());
					return;
				}
			}catch(Exception e){
				if(vote.getNeed_content_error_remind()==1)
					replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_content_error_content());
				return;
			}
		}
		if(vote.getSupport_repeat()==0){
			if(!checkRepeat(userContents)){
				if(vote.getNeed_content_error_remind()==1)
					replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_content_error_content());
				return;
			}
		}
		if(vote.getEffective_mode()!=1){
			Map<String, Object> paraMap1 =new HashMap();
			paraMap1.put("tpdc_id", vote.getId());
			paraMap1.put("mobile", inbox.getSenderMobile());
			List<MasSmsToupiaojieguo> results=voteResultDao.queryVoteResultByNum(paraMap1);
			if(results.size()>0){
				if(vote.getEffective_mode()==2){
					return;
				}
				if(vote.getEffective_mode()==3){
					List<Long> delList=new ArrayList();
					for(int i=0;i<results.size();i++){
						delList.add(results.get(i).getId());
					}
					voteResultDao.deleteBatchVoteResult(delList);
				}
			}
		}
		log.info("vote.getNeed_successful_remind()"+(vote.getNeed_successful_remind()==1));
		if(vote.getNeed_successful_remind()==1){
			log.info("回复成功短信");
			replyVoteSMS(vote.getId(),colsList,vote.getCreate_by(),inbox.getMerchantPin(),inbox.getSenderMobile(),vote.getNeed_successful_content());
		}
		try{
			MasSmsToupiaojieguo result=new MasSmsToupiaojieguo();
			result.setAnswer_content(inbox.getContent().trim());
			result.setCreate_by(vote.getCreate_by());
			result.setCreate_time(new Date());
			result.setId(PinGen.getSerialPin());
			result.setMobile(inbox.getSenderMobile());
			result.setName(inbox.getSenderName());
			result.setOrder_number(inbox.getContent().trim());
			result.setTpdc_id(vote.getId());
			voteResultDao.insertVoteResult(result);
		}catch(Exception e){
			return;
		}
		
		
	}
	private void replyVoteSMS(Long batchID,List<String> colsList,Long create_by,Long merchantPin,String receiver,String smsText){
		try{
			MasCommonBean masCommonBean=new MasCommonBean();
			masCommonBean.setBatchId(batchID);
			masCommonBean.setCodeType("TP");
			masCommonBean.setColsList(colsList);
			masCommonBean.setEntSign("");
			masCommonBean.setFlag(null);
			Users longinUser=userDao.findById(create_by);
			masCommonBean.setLoginUser(longinUser);
			masCommonBean.setMerchantPin(merchantPin);
			masCommonBean.setOperationType(4);
			MbnMerchantVip merchant = mbnMerchantVipIDao.load(merchantPin);
			String prov = merchant.getProvince();
			Region region = regionDAO.queryByProvinceId(Long.parseLong(prov));
			String provCode = region.getCode();
			masCommonBean.setProv_code(provCode);
			masCommonBean.setReadySendTime(new Date());
			masCommonBean.setReceiver(receiver);
			masCommonBean.setReplyCode("");
			masCommonBean.setReplyText("");
			masCommonBean.setSmsText(smsText);
			masCommonBean.setTitle("");
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
	private static boolean checkRepeat(String[] array){

		Set<String> set = new HashSet<String>();
		for(String str : array){
			set.add(str);
		}
		if(set.size() != array.length){
			return false;//有重复
		}else{
			return true;//不重复
		}

	}

	@Override
	public List<MasSmsVoteExportList> exportVoteList(String type, Map<String,Object> paraMap) {
		// TODO Auto-generated method stub
		if("send".equals(type)){
			return voteDao.exportSendVote(paraMap);
		}else if("notsend".equals(type.toLowerCase())){
			return voteDao.exportNotSendVote(paraMap);
		}else if("reply".equals(type)){
			return voteDao.exportReplyVote(paraMap);
		}else
			return null;
	}

}
