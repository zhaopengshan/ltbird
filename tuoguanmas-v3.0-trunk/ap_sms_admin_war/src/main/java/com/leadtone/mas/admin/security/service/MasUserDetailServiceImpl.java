package com.leadtone.mas.admin.security.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;

public class MasUserDetailServiceImpl implements UserDetailsService {
	private UsersDao usersDao;

	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserVO users = this.usersDao.findByName(username);
		if (users == null) {
			throw new UsernameNotFoundException(username);
		}
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);

		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		User userdetail = new User(users.getAccount(), users.getPassword(),
				enables, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantedAuths);
		return userdetail;
	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(UserVO user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		Set<RoleVO> roles = user.getRoles();

		for (RoleVO role : roles) {
			Set<Resources> tempRes = role.getResources();
			for (Resources res : tempRes) {
				authSet.add(new GrantedAuthorityImpl(res.getName()));
			}
		}
		return authSet;
	}
}