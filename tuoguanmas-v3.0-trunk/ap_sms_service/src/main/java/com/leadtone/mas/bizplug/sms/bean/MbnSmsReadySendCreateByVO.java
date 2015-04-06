package com.leadtone.mas.bizplug.sms.bean;

import com.leadtone.mas.bizplug.security.bean.Users;


public class MbnSmsReadySendCreateByVO extends MbnSmsReadySendVO {
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	
}
