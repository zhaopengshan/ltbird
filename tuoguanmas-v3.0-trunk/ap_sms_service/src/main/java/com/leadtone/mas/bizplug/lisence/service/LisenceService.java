package com.leadtone.mas.bizplug.lisence.service;

import java.util.List;

import com.leadtone.mas.bizplug.lisence.bean.Lisence;

public interface LisenceService {
	List<Lisence> getAllLisence();
	void insert(Lisence lis);
	void update(Lisence lis);
	void truncate();
}
