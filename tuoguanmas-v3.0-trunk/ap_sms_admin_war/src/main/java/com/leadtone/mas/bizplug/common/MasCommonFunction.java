package com.leadtone.mas.bizplug.common;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.smssend.util.ContactsContainer;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.util.BizUtils;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;
import com.leadtone.mas.bizplug.util.WebUtils;

/**
 * 
 * @author xiazhy
 * 
 * MAS action层公用方法
 *
 */
public class MasCommonFunction {
	
	private static Log LOG = LogFactory.getLog(MasCommonFunction.class);
	
	
	private MasSmsBean masSmsBean;
	
	public MasCommonFunction(MasSmsBean masSmsBean){
		this.masSmsBean=masSmsBean;
	}
	
	/**
	 * 构造返回消息
	 * @param errMsg
	 * @return
	 */
	public static Map<String,Object> responseMapByMsg(String responseType,String errMsg){
		Map<String,Object> entityMap=null;
		if(responseType==null)responseType="error";
		entityMap=new HashMap<String,Object>();
		entityMap.put("resultcode",responseType );
        entityMap.put("message", errMsg);
		return entityMap;
	}
	
	/**
	 * 构造返回消息
	 * @param errMsg
	 * @return
	 */
	public static Map<String,Object> responseMapByMsg(String errMsg){
		Map<String,Object> entityMap=null;
		entityMap=new HashMap<String,Object>();
		entityMap.put("resultcode", "error" );
        entityMap.put("message", errMsg);
		return entityMap;
	}
	
	

	/**
	 * 拖管MAS 重建发送集，非本省、联通、电信移到td通道
	 * @param loginUser
	 * @param container
	 * @return
	 */
	public String[] rebuildContainerForHostMas(Users loginUser, ContactsContainer container){
		String[] result = new String[]{Boolean.TRUE.toString(), "OK","OK","OK"};
		String provinceCode = "";
		SmsMbnTunnelVO svo = null;
		try {
			
			svo = masSmsBean.getSmsMbnTunnelService().queryByPk(masSmsBean.getYdTunnel());
			provinceCode = svo.getProvince();
		} catch (Exception e) {
			LOG.error("Find " + masSmsBean.getYdTunnel() + " FAIL", e);
		}
		
		if( container.getYdList().size() > 0){
			for( Contacts contacts: container.getYdList()){
				MbnSevenHCode code = masSmsBean.getMbnSevenHCodeService().queryByBobilePrefix(StringUtil.getLongPrefix(contacts.getMobile()));
				if( code == null || !provinceCode.equalsIgnoreCase(code.getProvinceCoding())){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_YD 
							||svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
					//	container.addYdContacts(contacts);
					} else {
						container.addTdContacts(contacts);
					}
				}
			}
		}
		// 将猫池发送从YD列表中删除
		for( Contacts contacts: container.getTdList()){
			container.removeContacts(contacts, ApSmsConstants.YD_CODE);
		}
		// 判断企业是否有联通通道，如有配置，使用联通通道，否则转移动TD
		// 原注释：联通移至TD列表
		Long ltTunnelId = BizUtils.getLtTunnelId(masSmsBean.getMbnMerchantTunnelRelationService(),
				loginUser.getMerchantPin());
		if( ltTunnelId <= 0){
			if( container.getLtList().size() > 0){
				for( Contacts contacts: container.getLtList()){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
						container.addYdContacts(contacts);
					} else{
						container.addTdContacts(contacts);
					}
				}
			}
			container.setLtList(new ArrayList<Contacts>());
		}
		// 判断企业是否有联通通道，如有配置，使用联通通道，否则转移动TD
		// 原注释：电信移至TD列表
		Long dxTunnelId = BizUtils.getLtTunnelId(masSmsBean.getMbnMerchantTunnelRelationService(),
				loginUser.getMerchantPin());
		if( dxTunnelId <= 0){
			if( container.getDxList().size() > 0){
				for( Contacts contacts: container.getDxList()){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
						container.addYdContacts(contacts);
					} else{
						container.addTdContacts(contacts);
					}
				}
			}
			container.setDxList(new ArrayList<Contacts>());
		}
		return result;
	}
	
	
	/**
	 * 生成短信记录，发送短信
	 * @param mercahngPin
	 * @param userSet
	 */
	public Map<String,Object> makeMbnSmsSendTask(Users loginUser,Date readySendTime,List<String> colsList){
		Long tdTunnel=0L;
		Map<String,Object> entityMap=null;
		final long batchId = masSmsBean.getBatchId();
		// 获取业务优先级
		MbnSmsOperationClass mbnSmsOperationClass 
			= masSmsBean.getMbnSmsOperationClassService().findByCoding(masSmsBean.getCodeType());
		
		PortalUserExtBean userExt = masSmsBean.getPortalUserExtService().getByPk(loginUser.getId());
		if(userExt!=null &&userExt.getSmsLimit()==1){
			int limit = userExt.getSmsLimitCount();//
			int curr = userExt.getSmsSendCount();//
			//如果限制，且量已经达到上限。则不发。
			if(curr+masSmsBean.getUserSet().size()>limit){
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "发送短信量限制！");
		        return entityMap;
			} else {
				userExt.setSmsSendCount(curr+masSmsBean.getUserSet().size());
				userExt.setCountTime(new Date());
				masSmsBean.getPortalUserExtService().update(userExt);
			}
		}
		
		int priority = mbnSmsOperationClass.getPriorityLevel().intValue();

		if("true".endsWith(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.SMS_PRIORITY_FROM_USER))){
			try{
				priority = userExt.getSmsPriority();//get priory from userExt
			} catch(Exception e){
				e.printStackTrace();//
			}
		}
		
        if(WebUtils.isHostMas()) {
            // 获取第三方通道ID
        	tdTunnel = BizUtils.getTdTunnelId(masSmsBean.getMbnMerchantTunnelRelationService(), loginUser.getMerchantPin());
            if( tdTunnel == null || tdTunnel <= 0){
                entityMap = new HashMap<String, Object>();
                entityMap.put("resultcode", "error" );
                entityMap.put("message", "短信通道未配置，请联系管理员！");
                return entityMap;
            }
            masSmsBean.setTdTunnel( tdTunnel);
        }
		
		// 把用户按运营商分类
		ContactsContainer container = new ContactsContainer();
		for (Contacts info : masSmsBean.getUserSet()) {
			MbnThreeHCode hcode = masSmsBean.getMbnThreeHCodeService().queryByBobilePrefix(StringUtil.getShortPrefix(info.getMobile()));
			if( hcode != null){
				container.addContacts(info, hcode.getCorp());
			}
		}
		if(WebUtils.isHostMas()) {
			String[] result = rebuildContainerForHostMas(loginUser, container);
			if( Boolean.parseBoolean(result[0])){
				// 检查成功
            	String taskNumber = "";
            	if( WebUtils.getExtCodeStyle() == com.leadtone.mas.admin.common.ApSmsConstants.OPERATION_EXT_CODE_TYPE){
            		// 根据业务类型生成扩展码
	                if( masSmsBean.getReplyCode() != null && masSmsBean.getReplyCode().length()>0){
	                    MbnSmsTaskNumber num = new MbnSmsTaskNumber();
	                    num.setId(PinGen.getSerialPin());
	                    num.setMerchantPin(loginUser.getMerchantPin());
	                    num.setOperationCoding(masSmsBean.getCodeType());
	                    num.setBatchId(batchId);
	                    num.setTaskNumber(masSmsBean.getReplyCode());
	                    num.setBeginTime(new Date());
	                    num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
	                    num.setState(1);
	                    taskNumber = num.getTaskNumber();
	                    masSmsBean.getMbnSmsTaskNumberService().addTaskNumber(num);
	                }
            	}else{
            		// 根据用户来生成2位扩展码
	                taskNumber = masSmsBean.getMbnSmsTaskNumberService().getTaskNumber2(loginUser.getMerchantPin(), loginUser.getUserExtCode());
					// 记录扩展码
					MbnSmsTaskNumber num = new MbnSmsTaskNumber();
					num.setId(PinGen.getSerialPin());
					num.setMerchantPin(loginUser.getMerchantPin());
					num.setOperationCoding(loginUser.getUserExtCode()); //使用用户的两位扩展
					num.setBatchId(batchId);
					num.setTaskNumber(taskNumber);
					num.setBeginTime(new Date());
					num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
					num.setState(1);
					taskNumber = num.getTaskNumber();
					masSmsBean.getMbnSmsTaskNumberService().addTaskNumber(num);
            	}
				
				MbnSmsReadySendContainer smsContainer = buildContainerForHostMas(loginUser, container, batchId, readySendTime, priority, colsList, taskNumber);
				masSmsBean.getMbnSmsReadySendService().batchSave(smsContainer);
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "success" );
		        entityMap.put("message", "发送成功！");
			}else{
				StringBuilder builder = new StringBuilder();
				builder.append("发送失败：");
				builder.append(result[1]);
				builder.append(result[2]);
				builder.append(result[3]);
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", builder.toString());
			}
		}else{
			String taskNumber = "";
            if(  masSmsBean.getReplyCode() != null && masSmsBean.getReplyCode().length()>0){
                MbnSmsTaskNumber num = new MbnSmsTaskNumber();
                num.setId(PinGen.getSerialPin());
                num.setMerchantPin(loginUser.getMerchantPin());
                num.setOperationCoding(masSmsBean.getCodeType());
                num.setBatchId(batchId);
                num.setTaskNumber(masSmsBean.getReplyCode());
                num.setBeginTime(new Date());
                num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
                num.setState(1);
                taskNumber = num.getTaskNumber();
                masSmsBean.getMbnSmsTaskNumberService().addTaskNumber(num);
            }
            try{
            	MbnSmsReadySendContainer smsContainer = buildContainerForHostMas(loginUser, container, batchId, readySendTime, priority, colsList, taskNumber);
            	masSmsBean.getMbnSmsReadySendService().batchSave(smsContainer);
            }catch(Exception e){
            	e.printStackTrace();
            }
            // 增加发送结果
            entityMap = new HashMap<String, Object>();
            entityMap.put("resultcode", "success" );
            entityMap.put("message", "发送成功！");
		}
		return entityMap;
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
	private MbnSmsReadySendContainer buildContainerForHostMas(Users loginUser, ContactsContainer container, 
			Long batchId, Date readySendTime, Integer priority, List<String> colsList, String taskNumber ){
		MbnSmsReadySendContainer smsContainer = new MbnSmsReadySendContainer();
		smsContainer.setMerchantPin(loginUser.getMerchantPin());
		// 构建移动通道短信列表
		if( container.getYdList() != null &&  container.getYdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getYdList(), batchId, readySendTime, priority, masSmsBean.getYdTunnel(), colsList, taskNumber);
			smsContainer.addSmsMap(masSmsBean.getYdTunnel(), msrsList);
		}
		// 构建联通通道短信列表
		Long ltTunnelId = BizUtils.getLtTunnelId(masSmsBean.getMbnMerchantTunnelRelationService(),
				loginUser.getMerchantPin());
		if (ltTunnelId <= 0) {
			ltTunnelId = masSmsBean.getTdTunnel();
		}
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getLtList(), batchId, readySendTime, priority, ltTunnelId, colsList, taskNumber);
			smsContainer.addSmsMap(ltTunnelId, msrsList);
		}
		// 构建电信通道短信列表
		Long dxTunnelId = BizUtils.getDxTunnelId(masSmsBean.getMbnMerchantTunnelRelationService(),
				loginUser.getMerchantPin());
		if (dxTunnelId <= 0) {
			dxTunnelId = masSmsBean.getTdTunnel();
		}
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getDxList(), batchId, readySendTime, priority, dxTunnelId, colsList, taskNumber);
			smsContainer.addSmsMap(dxTunnelId, msrsList);
		}
		/**
		// 构建联通通道短信列表
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getLtList(), batchId, readySendTime, priority, masSmsBean.getTdTunnel(), colsList, taskNumber);
			smsContainer.addSmsMap(masSmsBean.getTdTunnel(), msrsList);
		}
		// 构建电信通道短信列表
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getDxList(), batchId, readySendTime, priority, masSmsBean.getTdTunnel(), colsList, taskNumber);
			smsContainer.addSmsMap(masSmsBean.getTdTunnel(), msrsList);
		}
		*/
		// 构建TD通道短信列表
		if( container.getTdList() != null &&  container.getTdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getTdList(), batchId, readySendTime, priority, masSmsBean.getTdTunnel(), colsList, taskNumber);
			smsContainer.addSmsMap(masSmsBean.getTdTunnel(), msrsList);
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
	private List<MbnSmsReadySend> buildSmsList(Users loginUser, List<Contacts> list, 
			Long batchId, Date readySendTime, Integer priority, Long tunnelId, List<String> colNameList, String taskNumber){

		SmsMbnTunnelVO svo = null;
		MbnMerchantTunnelRelation mmtr = null;
		try {
			mmtr = masSmsBean.getMbnMerchantTunnelRelationService().findByPinAndTunnelId(loginUser.getMerchantPin(), tunnelId);
			svo = masSmsBean.getSmsMbnTunnelService().queryByPk(tunnelId);
		} catch (Exception e) {
			LOG.error(e);
		}
		List<MbnSmsReadySend> msrsList = new ArrayList<MbnSmsReadySend>();
		for(Contacts info: list ){
			MbnSmsReadySend msrs = new MbnSmsReadySend();
			
			//PAN-Z-G 修改webservice信息
			if(loginUser.getWebService()!=com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO)
				msrs.setWebService(com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_YES);
			else {
				msrs.setWebService(com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO);
			}
			
			msrs.setId(PinGen.getSerialPin());
			msrs.setMerchantPin(loginUser.getMerchantPin());
			//bean改好解开注释Integer=》long
			msrs.setOperationId(new Long(masSmsBean.getOperationType()));
			msrs.setBatchId(batchId);
			msrs.setTitle(masSmsBean.getTitle());
			msrs.setProvince(masSmsBean.getProv_code());
			msrs.setTos(info.getMobile());
			msrs.setTosName(info.getName());
			msrs.setTaskNumber(taskNumber);
			if(null != masSmsBean.getFlag() && "dynamic".equals(masSmsBean.getFlag())){
				// 如果是发送动态短信，则替换短信内容里的变量
				String _smsText = masSmsBean.getSmsText();
				for(String colName : colNameList){
					try{
						_smsText = _smsText.replace("%"+ colName+ "%", info.get(colName));
					}catch (Exception e) {
						System.out.println("没有["+ colName+"]列");
						continue;
					}
				}
				msrs.setContent(_smsText + (masSmsBean.getReplyText()==null?"":masSmsBean.getReplyText()) + (masSmsBean.getEntSign()==null?"":masSmsBean.getEntSign()));
			} else{
				msrs.setContent(masSmsBean.getSmsText() + (masSmsBean.getReplyText()==null?"":masSmsBean.getReplyText()) + (masSmsBean.getEntSign()==null?"":masSmsBean.getEntSign()));
			}
			msrs.setCommitTime(new Date());
			msrs.setReadySendTime(readySendTime);
			msrs.setTunnelType(svo.getClassify());
            msrs.setTunnelType(svo.getClassify());

            // 设置通道类型
            int tunnelType = svo.getClassify();
            
            if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_ZXT 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_TD 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_MODEM){
            	// 资信通, TD, MODEM
            	msrs.setSmsAccessNumber(loginUser.getZxtUserId());
            }
            else if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_QXT){
            	// 企信通
            	msrs.setSmsAccessNumber(String.valueOf(loginUser.getZxtId()));
            }
            else{
            	//20130904 还需要增加 taskNumber
            	String accessNumber = BizUtils.buildAccessNumber(mmtr.getAccessNumber(), loginUser.getUserExtCode(), msrs.getTaskNumber());
            	msrs.setSmsAccessNumber(accessNumber);
            }
			msrs.setPriorityLevel(priority);
			msrs.setSendResult(0);
			msrs.setCreateBy(loginUser.getId());
			msrs.setSelfMobile(mmtr.getAccessNumber());
			msrs.setCutApartNumber(1000);
			msrsList.add(msrs);
		}
		return msrsList;
	}
	
	
	/**
	 * 
	 * @param merchantPin
	 * @return
	 */
	public List<SmsMbnTunnelVO> getTunnelList(Long merchantPin){
        // 获取商户通道列表
		List<MbnMerchantTunnelRelation> relList = masSmsBean.getMbnMerchantTunnelRelationService().findByPin(merchantPin);
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = masSmsBean.getSmsMbnTunnelService().queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = masSmsBean.getMbnMerchantConsumeService().findByTunnelId(merchantPin, tvo.getId());
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
}
