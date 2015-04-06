package com.leadtone.mas.bizplug.dati.service;

import java.util.ArrayList;
import java.util.Date;
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

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;
import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.bean.MasCommonBean;
import com.leadtone.mas.bizplug.common.service.MasCommonService;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiResult;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiShiJuan;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiDao;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiResultDao;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiShiJuanDao;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiTiKuDao;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiBean;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiResultBean;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiTiKuInfo;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
@Service("masSmsDatiService")
public class MasSmsDatiServiceImp implements MasSmsDatiService {
    private static final Log log = LogFactory.getLog(MasSmsDatiServiceImp.class);
	@Resource
	private MasSmsDatiDao masSmsDatiDao;
	@Resource
	private MasSmsDatiResultDao masSmsDatiResultDao;
	@Resource
	private MasSmsDatiTiKuDao masSmsDatiTiKuDao;
	@Resource
	private MasSmsDatiShiJuanDao masSmsDatiShiJuanDao;
	
	@Resource
    private MbnMerchantVipIDao mbnMerchantVipIDao;
	 @Resource(name="regionDAOImpl")
	private IRegionDAO regionDAO;
	
	@Autowired
    private ContactsService contactsService;
		
	@Autowired
	private MasCommonService masCommonServiceImp;
	@Resource
	private UsersDao userDao;
	
	@Override
	public void createDatiInfo(MasSmsDati masSmsDati,
			List<MasSmsDatiTiKuInfo> datiTiKuInfoList) {
		// TODO Auto-generated method stub
		//先插入mbn_sms_duanxindati表中
		//再插入mbn_sms_duanxindati_shijuan表中
		if(masSmsDati.getId() == null){
			masSmsDati.setId(PinGen.getSerialPin());
		}
		masSmsDatiDao.insert(masSmsDati);
		masSmsDatiShiJuanDao.insertShiJuanSelectTiKu(masSmsDati.getId(), masSmsDati.getCreateBy(), datiTiKuInfoList);
	}

	@Override
	public void createDatiResultInfo(MasSmsDatiResult datiResult) {
		// TODO Auto-generated method stub
		masSmsDatiResultDao.insert(datiResult);
	}

	@Override
	public void createDatiTiKu(MasSmsDatiTiKu masSmsDatiTiku) {
		// TODO Auto-generated method stub
		if(masSmsDatiTiku != null){
			masSmsDatiTiku.setId(PinGen.getSerialPin());
		}
		masSmsDatiTiKuDao.insert(masSmsDatiTiku);
	}

	@Override
	public void deleteTiKuById(long tikuId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MasSmsDatiTiKu> getAllTiKuByCreatorId(long creatorId) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getSmsDatiTiKuListById(creatorId);
		
	}

	@Override
	public List<MasSmsDatiResult> getDatiResultInfoByIdAndMobileAndRecord(
			long dxdtId, String mobile, int record) {
		// TODO Auto-generated method stub
		return masSmsDatiResultDao.getMosSmsDtResultByIdAndMobileAndOrder(dxdtId, mobile, record);
		
	}

	@Override
	public void updateDatiInfo(MasSmsDati masSmsDati,
			List<MasSmsDatiTiKuInfo> datiTiKuInfoList) {
		// TODO Auto-generated method stub
         //先更新mbn_sms_duanxindati表
		 //删除mbn_sms_duanxindati_shijuan中数据
		 //再添加mbn_sms_duanxindati_shijuan表中数据
		masSmsDatiDao.update(masSmsDati);
		masSmsDatiShiJuanDao.delete(masSmsDati.getId(), masSmsDati.getCreateBy());
		masSmsDatiShiJuanDao.insertShiJuanSelectTiKu(masSmsDati.getId(), masSmsDati.getCreateBy(), datiTiKuInfoList);
	}

	@Override
	public void deleteTiKuById(long tikuId, long creatorId) {
		// TODO Auto-generated method stub
		masSmsDatiTiKuDao.delete(tikuId, creatorId);
	}

	@Override
	public List<MasSmsDati> getMasSmsDatiByCreatorId(long creatorId) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiByCreatorId(creatorId);
		
	}

	@Override
	public Integer getAllTiKuCountByCreatorIdAndKeywordAndTime(int creatorId,
			String keyword, String startTime, String endTime) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getSmsDatiTiKuCountByKeywordAndCreator(creatorId, keyword, startTime, endTime);
		
	}

	@Override
	public Integer getAllTiKuCountByCreatorIdAndKeywordAndTime(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getSmsDatiTiKuCountByKeywordAndCreator(searchInfo);
		
	}

	@Override
	public List<MasSmsDatiTiKu> getAllTiKuListByCreatorIdAndKeywordAndTime(
			int creatorId, String keyword, String startTime, String endTime,
			int startPage, int pageSize) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getSmsDatiTiKuListByKeywordAndCreatorForPage(creatorId, keyword, startTime, endTime, startPage, pageSize);
		
	}

	@Override
	public List<MasSmsDatiTiKu> getAllTiKuListByCreatorIdAndKeywordAndTime(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getSmsDatiTiKuListByKeywordAndCreatorForPage(searchInfo);
		
	}

	@Override
	public void updateTiKuDeleteStatus(long tikuId, long creatorId) {
		// TODO Auto-generated method stub
		masSmsDatiTiKuDao.updateDeleteStatus(tikuId, creatorId);
		
	}

	@Override
	public MasSmsDatiTiKu getTiKuById(long tikuId, long creatorId) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getTiKuRecordById(tikuId, creatorId);
	}

	@Override
	public void updateTiKuShortInfo(String question, String answer, int score,
			long tikuId, long createBy) {
		// TODO Auto-generated method stub
		masSmsDatiTiKuDao.updateSmsDatiTiKuInfo(question, answer, score, tikuId, createBy);
	}

	@Override
	public List<MasSmsDati> getMasSmsDatiByCreatorIdAndSearchInfo(
			long creatorId, String keyword, int startPage, int pageSize) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiByCreatorIdAndSearchInfoForPage(creatorId, keyword, startPage, pageSize);
	}

	@Override
	public List<MasSmsDati> getMasSmsDatiBySearchMap(
			Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiByCreatorIdAndSearchInfoForPage(searchMap);
	}

	@Override
	public Integer getMasSmsDatiCountByCreatorIdAndSearchInfo(long creatorId,
			String keyword) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiByCreatorIdAndSearchInfoCount(creatorId, keyword);
	}

	@Override
	public Integer getMasSmsDatiCountBySearchMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiByCreatorIdAndSearchInfoCount(map);
	}

	@Override
	public Integer getMasSmsDatiSumBySearchInfo(Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		return masSmsDatiTiKuDao.getSmsDatiTiKuSumBySearchInfo(mapInfo);
	}

	@Override
	public void updateSmsDatiResultInfo(String mobile, String replyContent,
			Long entPin, String sendName, String taskNumber,Date commitTime) {
		// TODO Auto-generated method stub
		//首先根据entPin、taskNumber、手机号进行过相应下发  如果存在将进行答题判断
		//查询出该答题id并得到该答题的最大题目值 进行相应记录
		//如果尚未达到最后一题 继续下发下一题 
		//如果已答完 将不再进行相应处理
		Long entId = null;
		if(entPin != null){
			MasSmsDati masSmsDati = masSmsDatiDao.getMasSmsDatiByPinAndMobileAndTaskNumberQueryInfo(mobile, entPin, "DT"+taskNumber);
			if(masSmsDati != null){
				//得到开始与结束时间
				Date startTime = masSmsDati.getBeginTime();
				Date endTime = masSmsDati.getEndTime();
				boolean isBeginVaild = false;
				boolean isEndVaild = false;
				if(endTime.compareTo(commitTime)==-1){
					isEndVaild = true;
					if(log.isInfoEnabled()){
						log.info("该答题已经结束");
					}
				}
				
				/**if(startTime.compareTo(commitTime) != 1){
					isBeginVaild = true;
					if(log.isInfoEnabled()){
						log.info("该答题尚未开始");
					}
				}*/
				if(!isBeginVaild && !isEndVaild){
					//得到所有答题总数
					entId  = masSmsDati.getCreateBy();
					int datiCount = masSmsDati.getDtSum();
					long dxdtId = masSmsDati.getId();//得到答题id
					String datiInfo = "参与此短信答题编辑\""+masSmsDati.getTaskNumber()+"+回复内容\"";
					Integer maxNumber = masSmsDatiResultDao.getMosSmsDtMaxNumResultBySearch(dxdtId, mobile, entId);
					
					if(maxNumber != null){
						//说明已经答过题
						//再进行比对是否已经超出了最大值
						if(datiCount > maxNumber){
							//继续写入结果值 并构造新的短信
							MasSmsDatiShiJuan masSmsDatiShiJuan = masSmsDatiShiJuanDao.getMasSmsDatiShiJuanBySearchInfo(dxdtId, maxNumber+1);
							//判断是否还有下一题
							if(datiCount >= (maxNumber+2)){
								//有下一题
								MasSmsDatiShiJuan masSmsDatiShiJuanNext  = masSmsDatiShiJuanDao.getMasSmsDatiShiJuanBySearchInfo(dxdtId, maxNumber+2);
								//进行下发下一题
								List<String> colsList = new ArrayList();
								colsList.add(mobile);
								replyVoteSMS(masSmsDati.getId(),colsList,masSmsDati.getCreateBy(),entPin,mobile,masSmsDatiShiJuanNext.getQuestion()+datiInfo,taskNumber);
							}
							if(masSmsDatiShiJuan != null){
								int score = 0;
								if(masSmsDatiShiJuan.getAnswer().equalsIgnoreCase(replyContent)){
									//回答正确
									score = masSmsDatiShiJuan.getScore();
								}else{
									//回答错误
									score = 0;
								}
								//添加答题结果记录
								MasSmsDatiResult masSmsDatiResult = new MasSmsDatiResult();
								masSmsDatiResult.setAnswer(masSmsDatiShiJuan.getAnswer());
								masSmsDatiResult.setCreateBy(entId);
								masSmsDatiResult.setCreateTime(new Date());
								masSmsDatiResult.setDxdtId(dxdtId);
								masSmsDatiResult.setId(PinGen.getSerialPin());
								masSmsDatiResult.setMobile(mobile);
								masSmsDatiResult.setName(sendName);
								masSmsDatiResult.setOrderNumber(maxNumber+1);
								masSmsDatiResult.setQuestion(masSmsDatiShiJuan.getQuestion());
								masSmsDatiResult.setScore(score);
								masSmsDatiResultDao.insert(masSmsDatiResult);
							}
						}
					}else{
						//第一次答题
						//继续写入结果值 并构造新的短信
						MasSmsDatiShiJuan masSmsDatiShiJuan = masSmsDatiShiJuanDao.getMasSmsDatiShiJuanBySearchInfo(dxdtId, 1);
						
						//判断是否还有下一题
						if(datiCount >= 2){
							//有下一题
							MasSmsDatiShiJuan masSmsDatiShiJuanNext  = masSmsDatiShiJuanDao.getMasSmsDatiShiJuanBySearchInfo(dxdtId, 2);
							//进行下发下一题
							List<String> colsList = new ArrayList();
							colsList.add(mobile);
							replyVoteSMS(masSmsDati.getId(),colsList,masSmsDati.getCreateBy(),entPin,mobile,masSmsDatiShiJuanNext.getQuestion()+datiInfo,taskNumber);
						}
						int score = 0;
						if(masSmsDatiShiJuan.getAnswer().equalsIgnoreCase(replyContent)){
							//回答正确
							score = masSmsDatiShiJuan.getScore();
						}else{
							//回答错误
							score = 0;
						}
						MasSmsDatiResult masSmsDatiResult = new MasSmsDatiResult();
						masSmsDatiResult.setAnswer(masSmsDatiShiJuan.getAnswer());
						masSmsDatiResult.setCreateBy(entId);
						masSmsDatiResult.setCreateTime(new Date());
						masSmsDatiResult.setDxdtId(dxdtId);
						masSmsDatiResult.setId(PinGen.getSerialPin());
						masSmsDatiResult.setMobile(mobile);
						masSmsDatiResult.setName(sendName);
						masSmsDatiResult.setOrderNumber(1);
						masSmsDatiResult.setQuestion(masSmsDatiShiJuan.getQuestion());
						masSmsDatiResult.setScore(score);
						masSmsDatiResultDao.insert(masSmsDatiResult);
					}
					
				}
			}else{
				if(log.isInfoEnabled()){
					log.info("该试题不存在或者已经被移除");
				}
			}
		}
	}

	@Override
	public void updateSmsDatiDeleteStatus( long createBy,String...  ids) {
		// TODO Auto-generated method stub
		masSmsDatiDao.updateDTDeleteStatusBySearchInfo(createBy, ids);
	}

	@Override
	public List<MasSmsDatiBean> getMasSmsDatiBeanListBySearchMap(
			Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiListBySearchInfo(searchMap);
	}
	
	private void replyVoteSMS(Long batchID,List<String> colsList,Long create_by,Long merchantPin,String receiver,String smsText,String taskNumber){
		try{
			MasCommonBean masCommonBean=new MasCommonBean();
			masCommonBean.setBatchId(batchID);
			masCommonBean.setCodeType("DT");
			masCommonBean.setColsList(colsList);
			masCommonBean.setDxTunnel(0L);
			masCommonBean.setEntSign("");
			masCommonBean.setFlag(null);
			masCommonBean.setLtTunnel(0L);
			Users longinUser=userDao.findById(create_by);
			masCommonBean.setLoginUser(longinUser);
			masCommonBean.setLtTunnel(0L);
			masCommonBean.setMerchantPin(merchantPin);
			masCommonBean.setOperationType(5);
			
			MbnMerchantVip merchant = mbnMerchantVipIDao.load(merchantPin);
			String prov = merchant.getProvince();
			Region region = regionDAO.queryByProvinceId(Long.parseLong(prov));
			String provCode = region.getCode();
			masCommonBean.setProv_code(provCode);
			
			masCommonBean.setReadySendTime(new Date());
			masCommonBean.setReceiver(receiver);
			masCommonBean.setReplyCode(taskNumber);
			masCommonBean.setReplyText("");
			masCommonBean.setSmsText(smsText);
			masCommonBean.setTdTunnel(0L);
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

	@Override
	public Integer getDatiResultGroupCountBySearchInfo(
			Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		return masSmsDatiResultDao.getMosSmsDtResultCountBySearchInfo(mapInfo);
	}

	@Override
	public List<MasSmsDatiResultBean> getDatiResultGroupInfoListBySearchInfo(
			Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		return masSmsDatiResultDao.getMosSmsDtResultListBySearchInfo(mapInfo);
	}

	@Override
	public void updateSmsDatiResultDeleteStatus(Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		masSmsDatiResultDao.updateMosSmsResultDeleteStatus(mapInfo);
	}

	@Override
	public MasSmsDatiBean getMasSmsDatiBySearchInfo(Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		return masSmsDatiDao.getMasSmsDatiBySearchInfo(mapInfo);
		
	}

}
