package com.leadtone.mas.bizplug.tunnel.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao; 

@SuppressWarnings("unchecked")
public class TunnelBaseDaoImpl<T, PK extends Serializable> extends
		BaseDao implements TunnelBaseIDao<T, PK> {

	protected static final String DELETE = ".delete";
	protected static final String INSERT = ".insert";
 	protected static final String UPDATE = ".update";
	protected static final String PAGECOUNT = ".pageCount";
	protected static final String PAGE = ".page";
	protected static final String GETBYPK =".getByPk";
	
	protected String sqlMapNamespace;
	 

	/**
	 * 注入数据库操作对象
	 * 
	 * @param sqlMapClient
	 */
	@Autowired
	public void setSqlMapClientAround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Class<?> clazz = (Class<?>) ((ParameterizedType) type)
					.getActualTypeArguments()[0];
			this.sqlMapNamespace = clazz.getSimpleName();
		}
	}

	public Page page(PageUtil pageUtil) {
		Integer recordes = this.pageCount(pageUtil);
		List<Object> results = new ArrayList<Object>();
		if (recordes > 0) {
			results = (List<Object>) getSqlMapClientTemplate()
					.queryForList(this.sqlMapNamespace + PAGE, pageUtil);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	public Integer pageCount(PageUtil pageUtil) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + PAGECOUNT, pageUtil);
	}

	@Override
	public Integer insert(T param) {
		//
		return (Integer) getSqlMapClientTemplate().insert(
				this.sqlMapNamespace + INSERT, param);
	}

	@Override
	public Integer update(T param) {
		//
		return (Integer) getSqlMapClientTemplate().update(
				this.sqlMapNamespace + UPDATE, param);
	}

	@Override
	public Integer delete(T param) {
		//
		return (Integer) getSqlMapClientTemplate().delete(
				this.sqlMapNamespace + DELETE, param);
	}

	@Override
	public Object queryByPk(Long pk) {
		//
		return (Object) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + GETBYPK, pk);
	} 
	/**
	 * 根据参数集合批量修改
	 */
	@Override
	public Integer batchUpdateByList(final List<T> paramList) {
		//
		int executorCount = 1;
		final String sta = this.sqlMapNamespace + UPDATE;
		getSqlMapClientTemplate().execute(new SqlMapClientCallback<T>() {
			public T doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				//
				int executorCount = 0;
				executor.startBatch();
				for (Iterator<T> iterator = paramList.iterator(); iterator
						.hasNext();) {
					T domain = iterator.next();
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
