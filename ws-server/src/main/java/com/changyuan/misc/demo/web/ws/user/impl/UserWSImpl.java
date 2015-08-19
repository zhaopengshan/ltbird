package com.changyuan.misc.demo.web.ws.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.changyuan.misc.demo.domain.user.User;
import com.changyuan.misc.demo.web.ws.user.UserWS;

@Repository(value = "userService")
public class UserWSImpl implements UserWS {

	@Override
	public User getUser(Integer userId) {
		User u = new User();
		u.setUserId(1);
		u.setUserName("青石板街");
		u.setAge(26);
		
		return u;
	}

	@Override
	public List<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		userList.add(new User(1,"滕召奇", 26));
		userList.add(new User(2, "张三", 20));
		userList.add(new User(3,"李四", 25));
		return userList;
	}
	
}
