package com.leadtone.mas.bizplug.mms.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysend;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysendVO;

@Repository(value="mbnMmsReadysendIDao")
public class MbnMmsReadysendDaoImpl extends BaseDao implements
		MbnMmsReadysendIDao {

	protected static String NAMESPACE = "mms";
	protected static String INSERT = ".readysendInsert";
	protected static String DELETEBYID = ".readysendDeleteById";
	protected static String UPDATE = ".readysendUpdate";
	protected static String PAGE = ".readysendPage";
	protected static String PAGEVO = ".readysendPageVO";
	protected static String PAGECOUNT = ".readysendCount";
	protected static String PAGECOUNTVO = ".readysendCountVO";
	protected static String LOADBYID = ".readysendLoadById";
	protected static String LOADVOBYID = ".readysendvoLoadById";
	protected static String GETBYPKS = ".readysendGetByPks";
	
	@SuppressWarnings("unchecked")
	public List<MbnMmsReadysend> getByPks(String[] ids){
		return (List<MbnMmsReadysend>) getSqlMapClientTemplate()
				.queryForList(NAMESPACE+ GETBYPKS, ids);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page page(PageUtil pageUtil) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("merchantPin", 	pageUtil.getMerchantPin());
		paraMap.put("startPage", 	pageUtil.getStartPage());
		paraMap.put("pageSize", 	pageUtil.getPageSize());
                paraMap.put("createBy", pageUtil.getCreateBy());
		Integer recordes = this.pageCount(paraMap);
		List<MbnMmsReadysendVO> results = null;
		if (recordes > 0) {
			results = (List<MbnMmsReadysendVO>) getSqlMapClientTemplate()
					.queryForList(NAMESPACE+ PAGEVO, paraMap);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + PAGECOUNTVO, paraMap);
	}

	@Override
	public void insert(MbnMmsReadysend mbnMms) {
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}

	@Override
	public void delete(MbnMmsReadysend mbnMms) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Long id) {
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYID, id);
	}

	@Override
	public void update(MbnMmsReadysend mbnMms) {
		this.getSqlMapClientTemplate().update(NAMESPACE + UPDATE, mbnMms);
	}

	@Override
	public MbnMmsReadysend loadById(Long id) {
		return (MbnMmsReadysend) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADBYID,id);
	}

	@Override
	public MbnMmsReadysendVO loadVOById(Long id) {
		return (MbnMmsReadysendVO) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADVOBYID,id);
	}

	public Integer batchUpdateByList(final List<MbnMmsReadysend> paramList) {
		//
		int executorCount = 1;
		final String sta = NAMESPACE + UPDATE;
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<MbnMmsReadysend>() {
			public MbnMmsReadysend doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				//
				int executorCount = 0;
				executor.startBatch();
				for (Iterator<MbnMmsReadysend> iterator = paramList.iterator(); iterator
						.hasNext();) {
					MbnMmsReadysend domain = iterator.next();
					executor.update(sta, domain);
					executorCount++;
					if (executorCount % 1000 == 0) {
						executor.executeBatch();
					}
				}
				if (executorCount % 1000 != 0) {
					executor.executeBatch();
				}
				return null;
			}
		});
		return executorCount;
	}
}
