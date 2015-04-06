package com.leadtone.mas.bizplug.security.bean;

import java.util.Set;

public class UserVO extends Users {
	// 登录用户对应的角色及资源
	private Set<RoleVO> roles;
	
	// 模糊查询，页面分页时用
	private Set<Role> role; 
	public Set<RoleVO> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleVO> roles) {
		this.roles = roles;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	 
}
