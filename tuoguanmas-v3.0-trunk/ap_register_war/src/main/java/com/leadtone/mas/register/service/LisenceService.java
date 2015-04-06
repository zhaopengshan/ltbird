package com.leadtone.mas.register.service;

import java.util.List;

import com.leadtone.mas.register.bean.Lisence;

public interface LisenceService {
	List<Lisence> getAllLisence(Lisence lis);
	void insert(Lisence lis);
	void update(Lisence lis);
	String productRegister(String mxlStr);
}
