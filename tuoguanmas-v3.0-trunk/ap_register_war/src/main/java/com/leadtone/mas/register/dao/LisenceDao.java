package com.leadtone.mas.register.dao;

import java.util.List;

import com.leadtone.mas.register.bean.Lisence;

public interface LisenceDao {
	List<Lisence> getAllLisence(Lisence lis);
	void insert(Lisence lis);
	void update(Lisence lis);
}

