package com.leadtone.mas.bizplug.lisence.dao;

import com.leadtone.mas.bizplug.lisence.bean.Lisence;
import java.util.List;

public interface LisenceDao {
	List<Lisence> getAllLisence();
	void insert(Lisence lis);
	void update(Lisence lis);
	void truncate();
}

