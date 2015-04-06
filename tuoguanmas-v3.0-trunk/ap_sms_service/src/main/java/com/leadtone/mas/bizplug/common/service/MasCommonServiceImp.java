package com.leadtone.mas.bizplug.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.common.bean.ContactsContainer;
import com.leadtone.mas.bizplug.common.bean.MasCommonBean;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.BizUtils;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;

@Service("masCommonService")
public class MasCommonServiceImp implements MasCommonService {
	
	private static Log LOG = LogFactory.getLog(MasCommonServiceImp.class);
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	@Autowired
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	
	
	public void insertMbnSmsSendTask(MasCommonBean masCommonBean) throws Exception{
		Long tdTunnel=0L;
		
		// 1,获取业务优先级
		try{
			MbnSmsOperationClass mbnSmsOperationClass 
				= mbnSmsOperationClassService.findByCoding(masCommonBean.getCodeType());
			final int priority = mbnSmsOperationClass.getPriorityLevel().intValue();
			
			List<Long> tunnelList=getTunnel(masCommonBean.getMerchantPin());
			masCommonBean.setYdTunnel(tunnelList.get(0));
			masCommonBean.setLtTunnel(tunnelList.get(1));
			masCommonBean.setDxTunnel(tunnelList.get(2));
			if( WebUtils.isHostMas()){
				tdTunnel = BizUtils.getTdTunnelId(mbnMerchantTunnelRelationService,
						masCommonBean.getLoginUser().getMerchantPin());
				if( tdTunnel == null || tdTunnel <= 0){
					throw new Exception("短信通道未配置，请联系管理员！");
				}else{
					masCommonBean.setTdTunnel( tdTunnel);
				}

	        }
			ContactsContainer container = new ContactsContainer();
			
			for (Contacts info : masCommonBean.getUserSet()) {
				MbnThreeHCode hcode = mbnThreeHCodeService.queryByBobilePrefix(StringUtil.getShortPrefix(info.getMobile()));
				if( hcode != null){
					container.addContacts(info, hcode.getCorp());
				}
			}
			if( WebUtils.isHostMas()){
				String[] result = rebuildContainerForHostMas(masCommonBean.getLoginUser(), container,getTunnelList(masCommonBean.getMerchantPin()).get(0).getId());
				if( Boolean.parseBoolean(result[0])){
					String taskNumber = "";
					if( WebUtils.getExtCodeStyle() == ApSmsConstants.OPERATION_EXT_CODE_TYPE){
						if(masCommonBean.getReplyCode() != null && masCommonBean.getReplyCode().length()>0){
							MbnSmsTaskNumber num = new MbnSmsTaskNumber();
							num.setId(PinGen.getSerialPin());
							num.setMerchantPin(masCommonBean.getLoginUser().getMerchantPin());
							num.setOperationCoding(masCommonBean.getCodeType());
							num.setBatchId(masCommonBean.getBatchId());
							num.setTaskNumber(masCommonBean.getReplyCode());
							num.setBeginTime(new Date());
							num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
							num.setState(1);
							taskNumber = num.getTaskNumber();
							mbnSmsTaskNumberService.addTaskNumber(num);
						}
					}else{
	            		// 根据用户来生成2位扩展码
		                taskNumber = mbnSmsTaskNumberService.getTaskNumber2(masCommonBean.getLoginUser().getMerchantPin(), masCommonBean.getLoginUser().getUserExtCode());
						// 记录扩展码
						MbnSmsTaskNumber num = new MbnSmsTaskNumber();
						num.setId(PinGen.getSerialPin());
						num.setMerchantPin(masCommonBean.getLoginUser().getMerchantPin());
						num.setOperationCoding(masCommonBean.getLoginUser().getUserExtCode()); //使用用户的两位扩展
						num.setBatchId(masCommonBean.getBatchId());
						num.setTaskNumber(taskNumber);
						num.setBeginTime(new Date());
						num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
						num.setState(1);
						taskNumber = num.getTaskNumber();
						mbnSmsTaskNumberService.addTaskNumber(num);
					}

					MbnSmsReadySendContainer smsContainer = buildContainerForHostMas(masCommonBean, container, priority, taskNumber);
					mbnSmsReadySendService.batchSave(smsContainer,masCommonBean.getOperationType());
				}else{
					throw new Exception("发送失败!");
				}
			}else{
				String taskNumber = "";
	            if(  masCommonBean.getReplyCode() != null && masCommonBean.getReplyCode().length()>0){
	                MbnSmsTaskNumber num = new MbnSmsTaskNumber();
	                num.setId(PinGen.getSerialPin());
	                num.setMerchantPin(masCommonBean.getLoginUser().getMerchantPin());
	                num.setOperationCoding(masCommonBean.getCodeType());
	                num.setBatchId(masCommonBean.getBatchId());
	                num.setTaskNumber(masCommonBean.getReplyCode());
	                num.setBeginTime(new Date());
	                num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
	                num.setState(1);
	                taskNumber = num.getTaskNumber();
	                mbnSmsTaskNumberService.addTaskNumber(num);
	            }
	            try{
	            	MbnSmsReadySendContainer smsContainer = buildContainerForHostMas(masCommonBean, container, priority, taskNumber);
	            	mbnSmsReadySendService.batchSave(smsContainer,masCommonBean.getOperationType());
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
			}
		}catch(Exception se){
			
			throw new Exception("发送失败!");
		}
	}
	
	
	/**
	 * 构建待发送操作
	 * @param loginUser
	 * @param container
	 * @param batchId
	 * @param readySendTime
	 * @param priority
	 * @return
	 */
	private MbnSmsReadySendContainer buildContainerForHostMas(MasCommonBean masCommonBean, ContactsContainer container, 
			  Integer priority, String taskNumber ){
		MbnSmsReadySendContainer smsContainer = new MbnSmsReadySendContainer();
		smsContainer.setMerchantPin(masCommonBean.getLoginUser().getMerchantPin());
		// 构建移动通道短信列表
		if( container.getYdList() != null &&  container.getYdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(masCommonBean, 
					container.getYdList(), priority, masCommonBean.getYdTunnel(), masCommonBean.getColsList(), taskNumber);
			smsContainer.addSmsMap(masCommonBean.getYdTunnel(), msrsList);
		}
		// 构建联通通道短信列表
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(masCommonBean, 
					container.getLtList(), priority, masCommonBean.getTdTunnel(), masCommonBean.getColsList(), taskNumber);
			smsContainer.addSmsMap( masCommonBean.getTdTunnel(), msrsList);
		}
		// 构建电信通道短信列表
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(masCommonBean, 
					container.getDxList(), priority,  masCommonBean.getTdTunnel(), masCommonBean.getColsList(), taskNumber);
			smsContainer.addSmsMap( masCommonBean.getTdTunnel(), msrsList);
		}
		// 构建电信通道短信列表
		if( container.getTdList() != null &&  container.getTdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(masCommonBean, 
					container.getTdList(), priority, masCommonBean.getTdTunnel(), masCommonBean.getColsList(), taskNumber);
			smsContainer.addSmsMap(masCommonBean.getTdTunnel(), msrsList);
		}
		return smsContainer;
	}
	
	
	
	/**
	 * 构建待发送短信列表
	 * @param loginUser
	 * @param list
	 * @param batchId
	 * @param readySendTime
	 * @param priority
	 * @param tunnelId
	 * @return
	 */
	private List<MbnSmsReadySend> buildSmsList(MasCommonBean masCommonBean, List<Contacts> list, 
			  Integer priority, Long tunnelId, List<String> colNameList, String taskNumber){
		
		//获取企业签名
		MbnConfigMerchant confMerchant = mbnConfigMerchantIService.loadByMerchantPin(masCommonBean.getLoginUser().getMerchantPin(), "sms_sign_content");
		if( confMerchant != null && confMerchant.getItemValue() != null){
			if(masCommonBean.getSmsText().indexOf(confMerchant.getItemValue())<0)
				masCommonBean.setEntSign(confMerchant.getItemValue());
		}
		SmsMbnTunnelVO svo = null;
		MbnMerchantTunnelRelation mmtr = null;
		try {
			mmtr = mbnMerchantTunnelRelationService.findByPinAndTunnelId(masCommonBean.getLoginUser().getMerchantPin(), tunnelId);
			svo =smsMbnTunnelService.queryByPk(tunnelId);
		} catch (Exception e) {
			LOG.error(e);
		}
		List<MbnSmsReadySend> msrsList = new ArrayList<MbnSmsReadySend>();
		for(Contacts info: list ){
			MbnSmsReadySend msrs = new MbnSmsReadySend();
			msrs.setId(PinGen.getSerialPin());
			msrs.setMerchantPin(masCommonBean.getLoginUser().getMerchantPin());
			//bean改好解开注释Integer=》long
			msrs.setOperationId(new Long(masCommonBean.getOperationType()));
			msrs.setBatchId(masCommonBean.getBatchId());
			msrs.setTitle(masCommonBean.getTitle());
			msrs.setProvince(masCommonBean.getProv_code());
			msrs.setTos(info.getMobile());
			msrs.setTosName(info.getName());
			msrs.setTaskNumber(taskNumber);
			if(null != masCommonBean.getFlag() && "dynamic".equals(masCommonBean.getFlag())){
				// 如果是发送动态短信，则替换短信内容里的变量
				String _smsText = masCommonBean.getSmsText();
				for(String colName : colNameList){
					try{
						_smsText = _smsText.replace("%"+ colName+ "%", info.get(colName));
					}catch (Exception e) {
						System.out.println("没有["+ colName+"]列");
						continue;
					}
				}
				msrs.setContent(_smsText + (masCommonBean.getReplyText()==null?"":masCommonBean.getReplyText()) + (masCommonBean.getEntSign()==null?"":masCommonBean.getEntSign()));
			} else{
				msrs.setContent(masCommonBean.getSmsText() + (masCommonBean.getReplyText()==null?"":masCommonBean.getReplyText()) + (masCommonBean.getEntSign()==null?"":masCommonBean.getEntSign()));
			}
			msrs.setCommitTime(new Date());
			msrs.setReadySendTime(masCommonBean.getReadySendTime());
            msrs.setTunnelType(svo.getClassify());

            // 设置通道类型
            int tunnelType = svo.getClassify();
            
            if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_ZXT 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_TD 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_MODEM){
            	// 资信通, TD, MODEM
            	msrs.setSmsAccessNumber(masCommonBean.getLoginUser().getZxtUserId());
            }
            else if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_QXT){
            	// 企信通
            	msrs.setSmsAccessNumber(String.valueOf(masCommonBean.getLoginUser().getZxtId()));
            }
            else{
            	//20130904 还需要增加 taskNumber
            	String accessNumber = BizUtils.buildAccessNumber(mmtr.getAccessNumber(),
            			masCommonBean.getLoginUser().getUserExtCode(), msrs.getTaskNumber());
            	msrs.setSmsAccessNumber(accessNumber);
            }

			//
			msrs.setPriorityLevel(priority);
			msrs.setSendResult(0);
			msrs.setCreateBy(masCommonBean.getLoginUser().getId());
			msrs.setSelfMobile(mmtr.getAccessNumber());
			msrs.setCutApartNumber(1000);
			msrs.setWebService(1);
			msrsList.add(msrs);
		}
		return msrsList;
	}
	
	
	/**
	 * 拖管MAS 重建发送集，非本省、联通、电信移到td通道
	 * @param loginUser
	 * @param container
	 * @return
	 */
	public String[] rebuildContainerForHostMas(Users loginUser, ContactsContainer container,long ydTunnel){
		String[] result = new String[]{Boolean.TRUE.toString(), "OK","OK","OK"};
		String provinceCode = "";
		SmsMbnTunnelVO svo = null;
		try {
			
			svo = smsMbnTunnelService.queryByPk(ydTunnel);
			provinceCode = svo.getProvince();
		} catch (Exception e) {
			LOG.error("Find " + ydTunnel + " FAIL", e);
		}
		
		if( container.getYdList().size() > 0){
			for( Contacts contacts: container.getYdList()){
				
				MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(contacts.getMobile()));
				if( code == null || !provinceCode.equalsIgnoreCase(code.getProvinceCoding())){
					container.addTdContacts(contacts);
				}
			}
		}
		// 将猫池发送从YD列表中删除
		for( Contacts contacts: container.getTdList()){
			container.removeContacts(contacts, ApSmsConstants.YD_CODE);
		}
		// 联通移至TD列表
		if( container.getLtList().size() > 0){
			for( Contacts contacts: container.getLtList()){
				container.addTdContacts(contacts);
			}
		}
		container.setLtList(new ArrayList<Contacts>());
		// 电信移至TD列表
		if( container.getDxList().size() > 0){
			for( Contacts contacts: container.getDxList()){
				container.addTdContacts(contacts);
			}
		}
		container.setDxList(new ArrayList<Contacts>());
		return result;
	}
	private List<SmsMbnTunnelVO> getTunnelList(Long merchantPin){
        // 获取商户通道列表
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(merchantPin);
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(merchantPin, tvo.getId());
				if( consume!=null){
					tvo.setSmsNumber(consume.getRemainNumber());
				}else{
					tvo.setSmsNumber(0L);
				}
				tunnelList.add(tvo);
			}
		}
		return tunnelList;
	}
	private List<Long> getTunnel(Long merchantPin){
		List<Long> list=new ArrayList();
		List<SmsMbnTunnelVO> tunnelList= getTunnelList(merchantPin);
		Long ydTunnelNumber=0L;
		Long ltTunnelNumber=0L;
		Long dxTunnelNumber=0L;
		for(int i=0;i<tunnelList.size();i++){
			SmsMbnTunnelVO smtv=tunnelList.get(i);
			if(smtv.getClassify()==1 || smtv.getClassify()==2 || smtv.getClassify()==7){
				if(ydTunnelNumber==0L){
					ydTunnelNumber=smtv.getId();
				}
			}
			if(smtv.getClassify()==3 || smtv.getClassify()==4 || smtv.getClassify()==7){
				if(ltTunnelNumber==0L){
					ltTunnelNumber=smtv.getId();
				}
			}
			if(smtv.getClassify()==5 || smtv.getClassify()==6 || smtv.getClassify()==7){
				if(dxTunnelNumber==0L){
					dxTunnelNumber=smtv.getId();
				}
			}
		}
		list.add(ydTunnelNumber);
		list.add(ltTunnelNumber);
		list.add(dxTunnelNumber);
		return list;
	}
	

}
