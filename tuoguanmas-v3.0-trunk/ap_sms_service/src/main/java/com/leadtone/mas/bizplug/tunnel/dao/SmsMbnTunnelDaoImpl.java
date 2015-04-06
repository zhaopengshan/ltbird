package com.leadtone.mas.bizplug.tunnel.dao; 

import com.leadtone.delegatemas.tunnel.bean.MasTunnel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelConsumerVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.dao.base.TunnelBaseDaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class SmsMbnTunnelDaoImpl extends
		TunnelBaseDaoImpl<SmsMbnTunnel, java.lang.Long> implements
		SmsMbnTunnelDao {
	@Resource
	private SmsTunnelAccountDao smsTunnelAccountDao;
	protected static final String UPDATEDEL = ".updateDel";
	protected static final String GETBYNAME = ".getByName";
	protected static final String GETTUNNELBYNAME = ".getTunnelByName";

	public boolean updateDel(HashMap<String, Object> pro) {
		boolean result = true;
		try {
			getSqlMapClientTemplate().update(this.sqlMapNamespace + UPDATEDEL,
					pro);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public Integer getByName(String tunnelName) throws Exception {
		Integer count = 0;

		try {
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					this.sqlMapNamespace + GETBYNAME, tunnelName);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMbnTunnel> getTunnelByName(String tunnelName) throws Exception {
		List<SmsMbnTunnel> result = null;
		try {
			result = getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + GETTUNNELBYNAME, tunnelName);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	
	@Override
	public Integer insert(SmsMbnTunnel smsMbnTunnel,
			SmsTunnelAccount tunnelAccount) throws Exception { 
		try { 
			this.insert(smsMbnTunnel);
			tunnelAccount.setTunnelId(smsMbnTunnel.getId());
			smsTunnelAccountDao.insert(tunnelAccount); 
		} catch (Exception e) {
			throw e;
		}  
		return 1;
	}

	public Page pageConsumer(PageUtil pageUtil) {
		Integer recordes = this.pageCount(pageUtil);
		List<SmsMbnTunnelConsumerVO> results = new ArrayList<SmsMbnTunnelConsumerVO>();
		if (recordes > 0) {
			results = (List<SmsMbnTunnelConsumerVO>) getSqlMapClientTemplate()
					.queryForList(this.sqlMapNamespace + ".pageConsumer", pageUtil);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	public Integer pageConsumerCount(PageUtil pageUtil) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + ".pageConsumerCount", pageUtil);
	}

	@Override
	public SmsMbnTunnelVO queryByTunnelId(Long tunnelId) {
		return (SmsMbnTunnelVO) this.getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace+".queryByTunnelId", tunnelId);
	}

	
	@Override
	public SmsMbnTunnel getTunnelByMerchantPin(Long merchantPin) {
		 List<SmsMbnTunnel> smsMbnTunnels =getSqlMapClientTemplate().queryForList(
					this.sqlMapNamespace + ".getTunnelByMerchantPin", merchantPin);
		return smsMbnTunnels.size()>0?smsMbnTunnels.get(0):null;
	}
	public List<MasTunnel> getMmsTunnelByMerchantPin(Long merchantPin){
		List<MasTunnel> smsMbnTunnels =getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + ".getMmsTunnelByMerchantPin", merchantPin);
		return smsMbnTunnels;
	}
    public void insertMasTunnel(MasTunnel tunnel){
        this.getSqlMapClientTemplate().insert(sqlMapNamespace+".insertMasTunnel",tunnel);
    }

    @Override
    public List<MasTunnel> queryMasTunnelsByMerchantPin(Long merchantPin) {
         return this.getSqlMapClientTemplate().queryForList(sqlMapNamespace+".getTunnelByMerchantPinForMas",merchantPin);
    }
    @Override
    public List<MasTunnel> getMerchantPinTunnels(Long merchantPin){
    	return this.getSqlMapClientTemplate().queryForList(sqlMapNamespace+".getMerchantPinTunnels",merchantPin);
    }
    @Override
    public void updateMasTunnel(MasTunnel tunnel) {
        this.getSqlMapClientTemplate().update(sqlMapNamespace+".updateMasTunnel",tunnel);
    }

    @Override
    public void deleteMasTunnel(Long merchantPin) {
        this.getSqlMapClientTemplate().delete(sqlMapNamespace+".deleteTunnelByMerchantPin", merchantPin);
    }

	@Override
	public MasTunnel getByTunnelPk(Long id) {
		return (MasTunnel) this.getSqlMapClientTemplate().queryForObject(sqlMapNamespace+".getByTunnelPk", id);
	}
}
