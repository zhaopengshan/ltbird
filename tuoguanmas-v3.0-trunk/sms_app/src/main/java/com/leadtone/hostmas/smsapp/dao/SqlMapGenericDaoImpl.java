package com.leadtone.hostmas.smsapp.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.hostmas.smsapp.exception.BizException;

public class SqlMapGenericDaoImpl<T> extends SqlMapClientDaoSupport implements GenericDao<T> {

	protected static final String INSERT =".insert";
	protected static final String DELETE =".delete";
	protected static final String UPDATE =".update";
	protected static final String LOAD =".load";
	protected static final String LIST =".list";
	protected static final String EXISTS =".exists";
	protected static final String COUNT =".count";
	protected static final String BATCH_SAVE =".batchSave";
	protected static final String BATCH_UPDATE =".batchUpdate";
	protected static final String BATCH_DELETE =".batchDelete";
	
	/*
	 * typeָ���������ļ���Ҫ���ݽ�����ʵ����entity��
	 * ��Ŀ��Ҳ��Ϊ�˸�sqlMapNamespace��ֵ��
	 */
	protected String type;
	protected String sqlMapNamespace ="";
	
	
	public String getSqlMapNamespace() {
		return sqlMapNamespace;
	}

	public void setSqlMapNamespace(String sqlMapNamespace) {
		this.sqlMapNamespace = sqlMapNamespace;
	}

	public void setType(String type) {
		this.type=type;
		this.sqlMapNamespace=type.substring(type.lastIndexOf(".")+1);
	}
	
	@Override
	public void batchSave(final List<T> list) {
		final String statement = sqlMapNamespace + BATCH_SAVE;
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			
			@Override
			public Object doInSqlMapClient(SqlMapExecutor sqlmapexecutor)
					throws SQLException {
				
				sqlmapexecutor.startBatch();
				
				for(int i=0; i<list.size();i++){
					sqlmapexecutor.insert(statement, list.get(i));
				}
				//��������Զ��ύ���п��ƣ����չ�500��ʱ��executeBatch()����һ�Σ�����Ч�����ѡ�
				sqlmapexecutor.executeBatch();
				return null;
			}
		});
	}
	
	/*������ı��档������������ܿ��Ʊ��������ύ���п���*/
	//���Զ��ύ���п��ƣ����չ�500��ʱ��executeBatch()����һ�Σ�����Ч�����ѡ�
	public void batchSaveV2(final List<T> list) {
		final String statement = sqlMapNamespace + BATCH_SAVE;
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			
			@Override
			public Object doInSqlMapClient(SqlMapExecutor sqlmapexecutor)
					throws SQLException {
				
				sqlmapexecutor.startBatch();
				int flag = 0;
				for(int i=0; i<list.size();i++){
					sqlmapexecutor.insert(statement, list.get(i));
					//1������������ж����
					if(flag++==500){
						sqlmapexecutor.executeBatch();
						flag=0;
					}
					
				}
				//2�����Ƕ����ʣ�²���500����¼���ٴ��ύִ�С�
				sqlmapexecutor.executeBatch();
				return null;
			}
		});
	}
	
	@Override
	public void batchUpdate(final List<T> list) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			final String statement = sqlMapNamespace + BATCH_UPDATE;
			@Override
			public Object doInSqlMapClient(SqlMapExecutor sqlmapexecutor)
					throws SQLException {
				sqlmapexecutor.startBatch();
				
				for(Iterator<T> it = list.iterator();it.hasNext();){
					sqlmapexecutor.update(statement, it.next());
				}
				sqlmapexecutor.executeBatch();
				return null;
			}
		});
	}

	@Override
	public void batchDelete(final Serializable[] ids) {
		final String statement = sqlMapNamespace + BATCH_DELETE;
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			
			@Override
			public Object doInSqlMapClient(SqlMapExecutor sqlmapexecutor)
					throws SQLException {
				sqlmapexecutor.startBatch();
				for(int i = 0;i<ids.length;i++){
					sqlmapexecutor.delete(statement, ids[i]);
				}
				sqlmapexecutor.executeBatch();
				return null;
			}
		});
	}
	
	@Override
	public int count(Map map) {
		String statement = sqlMapNamespace + COUNT;
		Integer result = (Integer) this.getSqlMapClientTemplate().queryForObject(statement, map);
		return result!=null? result.intValue():0;
	}

	@Override
	public void deleteById(Serializable id) {
		String statement = sqlMapNamespace + DELETE;
		this.getSqlMapClientTemplate().delete(statement, id);
	}

	@Override
	public List<T> list(Map<String, Object> map) {
		String statement = sqlMapNamespace + LIST;
		return this.getSqlMapClientTemplate().queryForList(statement, map);
	}

	@Override
	public List<T> list(String[] as, Object[] objs) {
		
		if(as.length!=objs.length){
			throw new BizException("����������һ��");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i = 0 ;i <as.length;i++){
			map.put(as[i], objs[i]);
		}
		return this.list(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T loadById(Serializable id) {
		String statement = sqlMapNamespace + LOAD;
		return (T) this.getSqlMapClientTemplate().queryForObject(statement, id);
	}

	@Override
	public int queryForInt(T obj) {
		String statement = sqlMapNamespace + EXISTS;
		Integer result = (Integer) this.getSqlMapClientTemplate().queryForObject(statement, obj);
		return result !=null?result.intValue():0;
	}

	@Override
	public void save(T obj) {
		String statement = sqlMapNamespace + INSERT;
	this.getSqlMapClientTemplate().insert(statement, obj);
	}

	@Override
	public void update(T obj) {
		String statement = sqlMapNamespace + UPDATE;
		this.getSqlMapClientTemplate().update(statement, obj);
	}

	@Override
	public void delete(Serializable id) {
		String statement = sqlMapNamespace + DELETE;
		this.getSqlMapClientTemplate().delete(statement, id);
	}
}
