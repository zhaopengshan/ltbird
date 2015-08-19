package com.changyuan.misc.demo.web.ws.user;

import java.util.List;

import com.changyuan.misc.demo.domain.user.User;


public interface UserWS {
	public User getUser(Integer userId);
	
	public List<User> getUserList();
}
