package com.leadtone.mas.bizplug.openaccount.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVipVO;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
@Component("mbnMerchantVipIDao")
public class MbnMerchantVipDaoImpl extends BaseDao implements
		MbnMerchantVipIDao {
	private String NAMESPACE = "MbnMerchantVip";
	@Override
	public boolean insert(MbnMerchantVip merchantVip) {
		this.getSqlMapClientTemplate().insert("MbnMerchantVip.insert", merchantVip);
		return true;
	}

	@Override
	public boolean update(MbnMerchantVip merchantVip) {
		int i=this.getSqlMapClientTemplate().update("MbnMerchantVip.update", merchantVip);
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public MbnMerchantVip load(long merchantPin) {
		return (MbnMerchantVip)getSqlMapClientTemplate().queryForObject("MbnMerchantVip.loadByMerhcantPin", merchantPin);
	}

	@Override
	public MbnMerchantVip loadByName(String name) {
		return (MbnMerchantVip)getSqlMapClientTemplate().queryForObject("MbnMerchantVip.loadByName", name);
	}

	/**
	 * 分页查询
	 */
	public Page page(PageUtil pageUtil){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("mobile", 		pageUtil.getMobile());
		paraMap.put("name", 		pageUtil.getName());
        paraMap.put("startPage", 	pageUtil.getStartPage());
        paraMap.put("pageSize", 	pageUtil.getPageSize());
		Integer recordes = this.pageCount(paraMap);
		List<MbnMerchantVipVO> results = null;
		if( recordes > 0 ){
			results = (List<MbnMerchantVipVO>)getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".queryMerchantByCondition", paraMap);
			for(Iterator<MbnMerchantVipVO> it=results.iterator();it.hasNext();){//密码解密
	 			MbnMerchantVipVO temp=it.next();
	 			temp.getUser().setPassword(MasPasswordTool.getDesString( temp.getUser().getPassword(),temp.getUser().getAccount()));
	 		}
		}
		return new Page( pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
	}
	
	/**
	 * 查询总记录数
	 */
	public Integer pageCount(Map<String,Object> paraMap ){
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".pageCountMerchant", paraMap);
	}

	@Override
	public boolean updateBatch(final List<Long> pins, final String smsState) {
		final String sqlName = NAMESPACE + ".update";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                Map<String,Object> paraMap = new HashMap<String,Object>();
                for (Iterator<Long> iterator = pins.iterator(); iterator.hasNext();) {
                	Long pin = iterator.next();
                	MbnMerchantVip mmv=new MbnMerchantVip();
                	mmv.setMerchantPin(pin);
                	mmv.setSmsState(smsState);
                	mmv.setMmsState(smsState);
                	executor.update(sqlName, mmv);
                    paraMap.clear();
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
        return true;
	}

	@Override
	public MbnMerchantVip loadVirtualCityMerchant(String privinceCode,
			String merchantType) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("cityCode", privinceCode);
		paraMap.put("merchantType",merchantType);
		List<MbnMerchantVip> list = (List<MbnMerchantVip>)getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".queryVirtualCityMerchant", paraMap);
		if( list == null || list.size() == 0){
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public MbnMerchantVip loadVirtualProvinceMerchant(String privinceCode,
			String merchantType) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("privinceCode", privinceCode);
		paraMap.put("merchantType",merchantType);
		List<MbnMerchantVip> list = (List<MbnMerchantVip>)getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".queryVirtualProvinceMerchant", paraMap);
		if( list == null || list.size() == 0){
			return null;
		}else{
			return list.get(0);
		}
	}
	/**
	 * 查询省市企业
	 * @param privinceCode
	 * @param cityCode
	 * @return
	 */
	public List<MbnMerchantVip> loadByProvinceAndCity(String privinceCode, String cityCode){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("privinceCode", privinceCode);
		paraMap.put("cityCode", cityCode);
		return (List<MbnMerchantVip>)getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".loadByProvinceAndCity", paraMap);
	}

	@Override
	public Integer checkZxtUserIdInUse(String zxtUserId) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".countByZxtUserId", zxtUserId);
	}

	@Override
	public int getCorpZXTId(Long merchantPin) {
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".getCorpZXTId", merchantPin);
	}
}
