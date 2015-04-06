package com.leadtone.sender.dao.local;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author limh
 *
 */
public interface BaseDao<T, PK extends Serializable> {

	public abstract void save(T paramT);

	public abstract int update(T paramT);

	public abstract int deleteById(PK pk);

	public abstract T loadById(PK PK);

	public abstract List<T> list(Map<String, Object> paramMap);

	public abstract List<T> list(T t);

	public abstract List<T> list(String[] paramArrayOfString,Object[] paramArrayOfObject);

	public abstract int batchSave(List<T> paramList);

	public abstract void batchUpdate(List<T> paramList);

	public abstract void batchDelete(PK[] pks);

	public abstract void batchDelete(List<T> entitys);

	public abstract int count(Map<String, Object> paramMap);


	public abstract <V extends T> int count(V v);

	public abstract int queryForInt(Object paramObject, String paramString);


}
