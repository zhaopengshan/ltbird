package com.leadtone.mas.bizplug.security.service;

import java.util.List;

import com.leadtone.mas.bizplug.security.bean.Resources;

public interface MenuService {
	public List<Resources> findAll();
	public List<Resources> findTopMenus();
	public List<Resources> findTopMenusByAccount(String username);
}
