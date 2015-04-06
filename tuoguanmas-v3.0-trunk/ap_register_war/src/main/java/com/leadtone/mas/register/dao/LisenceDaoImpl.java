package com.leadtone.mas.register.dao;


import java.util.List;
import org.springframework.stereotype.Component;
import com.leadtone.mas.register.bean.Lisence;

@Component("lisenceDao")
public class LisenceDaoImpl extends BaseDao implements LisenceDao {
	private static String namespace = "lisence";
	@SuppressWarnings("unchecked")
	@Override
	public List<Lisence> getAllLisence(Lisence lis) {
		return this.getSqlMapClientTemplate().queryForList(namespace + ".list",lis);
	}

	@Override
	public void insert(Lisence lis) {
		this.getSqlMapClientTemplate().insert(namespace + ".insert", lis);
	}

	@Override
	public void update(Lisence lis) {
		this.getSqlMapClientTemplate().update(namespace + ".update", lis);
	}
	

}
