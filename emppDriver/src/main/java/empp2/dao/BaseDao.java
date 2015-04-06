package empp2.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class BaseDao<T extends Serializable> 
{
	
	protected JdbcTemplate _jt ;
	
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	
	public boolean saveDefault(T entity, String sql) {
		try {
			SqlParameterSource param = new BeanPropertySqlParameterSource(entity);

			int result = simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql, param);
			return result >= 1;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();

			return false;
		}finally {

		}
	}
	
	//插入数据返回数据ID   
		public int saveDefaultReturnId(String sql,T entity){   
			SqlParameterSource param = new BeanPropertySqlParameterSource(entity);   
			KeyHolder keyHolder = new GeneratedKeyHolder();   
			this.simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql,   
					param, keyHolder);   
			return keyHolder.getKey().intValue();          
		}   

	
	public boolean[] batchSave(List<T> entity, String sql) {
//		log.debug("SQL: " + sql);
		try {
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(entity.toArray());
			int[] ii = simpleJdbcTemplate.batchUpdate(sql, batch);
			boolean[] flag = null;
			if (ii != null) {
				flag = new boolean[ii.length];
				for (int j = 0; j < ii.length; j++) {
					flag[j] = (ii[j] >= 1 ? true : false);
				}
			}
			return flag;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	
	public boolean updateDefault(T entity, String sql) {
//		log.debug("SQL: " + sql);
		try{
			SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
			return simpleJdbcTemplate.update(sql, param) >= 1;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();

			return false;
		}finally {

		}
	}

	public boolean deleteDefault(String sql, T entity) {
//		log.debug("SQL: " + sql);
		try{
			SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
			return simpleJdbcTemplate.update(sql, param) >= 1;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();

			return false;
		}finally {

		}
	}

	public boolean delete(String sql, Object... property) {

//		log.debug("SQL: " + sql);
		try{
			return simpleJdbcTemplate.update(sql, property) >= 1;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();

			return false;
		}finally {

		}
	}

	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(Class<? extends Serializable> entity, String sql, Object... property) {
		try {
//			log.debug("SQL: " + sql);
			return (T) simpleJdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(entity), property);
		} catch (EmptyResultDataAccessException e) {
//			log.error("错误，返回值为空[" + e.getMessage() + "]");
		} catch (IncorrectResultSizeDataAccessException e) {
//			log.error("错误，返回值不止一条[" + e.getMessage() + "]");
		}finally {

		}
		return null;
	}

	public boolean update(String sql, Object... property) {
		try{
//			log.debug("SQL: " + sql);
			return simpleJdbcTemplate.update(sql, property) >= 1;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();

			return false;
		} finally {

		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(Class<? extends Serializable> entity, Object[] property, String sql) {
		try{
//			log.debug("SQL: " + sql);
			List<T> list = (List<T>) simpleJdbcTemplate.getJdbcOperations().query(sql, property, ParameterizedBeanPropertyRowMapper.newInstance(entity));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();

			return null;
		}finally {

		}
	}
	
	public int Execute(String sql)
	{

		simpleJdbcTemplate.getJdbcOperations().execute(sql);


		return 0;
	}

	/**
	 * 根据SQL查询，该表中是否有相同记录
	 * @param checkSql
	 * @return
	 */
	public boolean checkExist(String checkSql) {
		
		
		Boolean result = (Boolean) _jt.query(checkSql,	new ResultSetExtractor()
				{
					public Object extractData(ResultSet rs) throws SQLException
					{
						boolean result = false;

						if (rs.next())
						{
							if (rs.getInt(1) != 0)
								result = true;
						}

						return new Boolean(result);

					}
				});

		return result.booleanValue();
	}
	
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public JdbcTemplate get_jt() {
		return _jt;
	}

	public void set_jt(JdbcTemplate _jt) {
		this._jt = _jt;
	}
}
