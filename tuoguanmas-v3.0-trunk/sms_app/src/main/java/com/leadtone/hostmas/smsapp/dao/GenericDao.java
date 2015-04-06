package com.leadtone.hostmas.smsapp.dao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T> {

	public abstract void save(T obj);

	public abstract void update(T obj);

	public abstract void deleteById(Serializable id);
	
	public abstract void delete(Serializable id);

	public abstract T loadById(Serializable id);

	public abstract List<T> list(Map<String, Object> map);

	public abstract List<T> list(String as[], Object objs[]);

	public abstract void batchSave(List<T> list1);

	public abstract void batchUpdate(List<T> list1);

	public abstract void batchDelete(Serializable ids[]);

	public abstract int count(Map<String, Object> map);

	public abstract int queryForInt(T obj);

}