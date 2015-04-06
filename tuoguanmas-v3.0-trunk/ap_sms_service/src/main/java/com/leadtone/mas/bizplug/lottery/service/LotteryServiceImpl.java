package com.leadtone.mas.bizplug.lottery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsLotteryDao;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

@Service("lotteryService")
public class LotteryServiceImpl implements LotteryService {

	private static Log log = LogFactory.getLog(LotteryServiceImpl.class);
	
	@Resource
	private MasSmsLotteryDao masSmsLotteryDao;

	public MasSmsLotteryDao getMasSmsLotteryDao() {
		return masSmsLotteryDao;
	}

	public void setMasSmsLotteryDao(MasSmsLotteryDao masSmsLotteryDao) {
		this.masSmsLotteryDao = masSmsLotteryDao;
	}

	@Override
	public int addLottery(MasSmsLottery lottery) {
		if(lottery==null){
			log.info("添加lottery为null");
			return 0;
		}
		return masSmsLotteryDao.addLottery(lottery);
	}
	
	@Override
	public List<MasSmsLottery> queryLotteryById(Map<String, Object> paraMap,int type) {
		
		return masSmsLotteryDao.queryLotteryById(paraMap,type);
	}

	
	@Override
	public int deleteLottery(String id) {
		// TODO Auto-generated method stub
		return masSmsLotteryDao.deleteLottery(id);
	}
	
	public List<MasSmsLottery> filterLottery(Map<String,Object> param) {
		if("0".equals(param.get("type"))){//未发
			return masSmsLotteryDao.queryLotteryByNoSend(param);
		}else if("1".equals(param.get("type"))){
			return masSmsLotteryDao.queryLotteryByIsSend(param);
		}else if("2".equals(param.get("type"))){
			return masSmsLotteryDao.queryLotteryByIsLottery(param);
		}
		return null;
	}
	
	@Override
	public boolean handleLottery(MbnSmsInbox inbox) {
		boolean state=false;
		try{
			Map param=new HashMap<String, Object>();
			param.put("taskNumber", inbox.getServiceCode());
			List<MasSmsLottery> lotterys=moSms(param);
			for(MasSmsLottery lottery:lotterys){
				if(lottery!=null){
					int i = lottery.getEnd_time().compareTo(inbox.getReceiveTime());
					if (i >= 0) {
						Map<String,Object> data=new HashMap<String,Object>();
						//判断手机号码是否是参与抽奖者
						if(lottery.getTos().indexOf(inbox.getSenderMobile())>=0){//是参与者
							//是否已经发送过
							String ValidTos=lottery.getValid_tos();
							boolean istrue=false;//是否应该添加
							//上行内容是否匹配
							String moCode=inbox.getOperationId()+inbox.getServiceCode();
							if(moCode.equalsIgnoreCase("CJ"+lottery.getReply_code())){
								if(ValidTos==null){
									istrue=true;
								}else{
									if(ValidTos.indexOf(inbox.getSenderMobile())==-1){
										istrue=true;
									}
								}
							}else{
								log.info("回复的内容与回复码不一致");
							}
							if(istrue){
								Long id=lottery.getId();
								String ReplyNum=inbox.getSenderMobile();
								data.put("ReplyNum", ReplyNum);
								data.put("id", id);
								replySmsLottery(data);
							}

							}else{
								System.out.println("此上行手机不再参与人中，跳过");
							}
					}else {
						System.out.println("此投票已过期");
					}

					}
				}
			state=true;
		}catch (Exception e) {
			System.out.println("上行短信抽奖异常："+e.toString());
		}

		return state;

	}
	
	@Override
	public int replySmsLottery(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return masSmsLotteryDao.replySmsLottery(param);
	}
	
	@Override
	public MasSmsLottery queryLotteryById(String id) {
		// TODO Auto-generated method stub
		return masSmsLotteryDao.queryLotteryById(id);
	}
	
	@Override
	public List<MasSmsLottery> moSms(Map<String, Object> param) {
		return masSmsLotteryDao.moSms(param);
	}
	
	@Override
	public int updateLottery(Map<String, Object> param) {
		return masSmsLotteryDao.updateLottery(param);
	}
	
	/**
	 * 截取字符串前面的正整数，如"22"得"22","18个人"得到"18".
	 * @return
	 */
	public static String getQuantity(String regular){
		regular=regular.substring(2);
		int index = 0;
		for (int i = 0; i < regular.length(); i++) {
			char c = regular.charAt(i);
			if (Character.isDigit(c)) {
				if (i == regular.length() - 1) {
					index = i + 1;
				} else {
					index = i;
				}
				continue;
			} else {
				index = i;
				break;
			}
		}
		return regular.substring(0, index);
	}
	
}

