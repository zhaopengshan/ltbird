package com.leadtone.zxt.dao;

import com.leadtone.zxt.bean.ZXTUser;

public interface ZXTUserDao {

	int addUser(ZXTUser user);
	int delUser(ZXTUser user);
	
	int getIdByAccount(String account);
}
