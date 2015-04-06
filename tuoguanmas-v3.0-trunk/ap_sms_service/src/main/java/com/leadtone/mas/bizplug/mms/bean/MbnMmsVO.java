package com.leadtone.mas.bizplug.mms.bean;

import com.leadtone.mas.bizplug.security.bean.Users;

public class MbnMmsVO extends MbnMms {
	private Users createUser; 
	
	public Users getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}
}
