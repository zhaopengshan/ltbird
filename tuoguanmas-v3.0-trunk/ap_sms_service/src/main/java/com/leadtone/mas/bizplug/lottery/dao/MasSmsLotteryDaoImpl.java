package com.leadtone.mas.bizplug.lottery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

@Component("masSmsLotteryDao")
@SuppressWarnings("unchecked")
public class MasSmsLotteryDaoImpl extends BaseDao implements MasSmsLotteryDao {

	private static Log log = LogFactory.getLog(MasSmsLotteryDaoImpl.class);
	protected static String NAMESPACE = "Lottery";
	
	@Override
	public int addLottery(MasSmsLottery lottery){
		int count=0;
		try{
			this.getSqlMapClientTemplate().insert(NAMESPACE + ".addSmsLottery", lottery);
			count=1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;

	}
	
	@Override
	public List<MasSmsLottery> queryLotteryById(Map<String, Object> paraMap,int type) {
		List<MasSmsLottery> list=null;
		if(0==type){//未发送
			list= this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".selesctNoSendLottery",paraMap);
		}else if(1==type){
			list= this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".selesctIsSendLottery",paraMap);
		}else if(2==type){
			list= this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".selesctIsLottery",paraMap);
		}
		return list;
	}
	
	public MasSmsLottery queryLotteryById(String id) {
		List<MasSmsLottery> list=null;
		list= this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".querySmsLotteryById",id);
		if(list!=null){
			return list.get(0);
		}else
			return null;
	}
	
	@Override
	public int updateLottery(Map<String,Object> param) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().update(NAMESPACE + ".updateSmsLottery",param);
	}
	
	@Override
	public int deleteLottery(String id) {
		String[] ids=id.split(",");
		return 	this.getSqlMapClientTemplate().delete(NAMESPACE + ".deleteSmsLottery",ids);
	}

	
	@Override
	public List<MasSmsLottery> queryLotteryByIsLottery(Map<String,Object> param) {
/*		Map<String,Object> param=new HashMap<String,Object>();
		param.put("title", title);
		param.put("createBy", createBy);
		param.put("tos", tos);*/
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".filterIsLottery",param);
	}
	
	@Override
	public List<MasSmsLottery> queryLotteryByIsSend(Map<String,Object> param) {
/*		Map<String,Object> param=new HashMap<String,Object>();
		param.put("title", title);
		param.put("createBy", createBy);
		param.put("tos", tos);*/
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".filterIsSendLottery",param);
	}
	
	@Override
	public List<MasSmsLottery> queryLotteryByNoSend(Map<String,Object> param) {
/*		Map<String,Object> param=new HashMap<String,Object>();
		param.put("title", title);
		param.put("createBy", createBy);
		param.put("tos", tos);*/
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".filterNoSendLottery",param);
	}
	
	@Override
	public int replySmsLottery(Map<String,Object> param) {
		return this.getSqlMapClientTemplate().update(NAMESPACE+".updateReplyLottery",param);
	}
	
	@Override
	public List<MasSmsLottery> moSms(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<MasSmsLottery> list=null;
		list= this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".moSms",param);
		return list;
	}
	
}
