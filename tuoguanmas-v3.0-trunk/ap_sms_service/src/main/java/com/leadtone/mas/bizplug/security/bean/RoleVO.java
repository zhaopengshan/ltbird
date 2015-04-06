package com.leadtone.mas.bizplug.security.bean;
import java.util.Set;

public class RoleVO extends Role{
    private Set<Users> users;
    private Set<Resources> resources;

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
    
    public Set<Resources> getResources() {
        return resources;
    }

    public void setResources(Set<Resources> resources) {
        this.resources = resources;
    }
    @Override
 	public String toString() {
 		return "RoleVO [users=" + users + ", resources=" + resources
 				+ ", getId()=" + getId() + ", getName()=" + getName()
 				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
 				+ getUpdateTime() + ", getCreateBy()=" + getCreateBy()
 				+ ", getUpdateBy()=" + getUpdateBy() + ", getDescription()="
 				+ getDescription() + ", getActiveFlag()=" + getActiveFlag()
 				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
 				+ ", toString()=" + super.toString() + "]";
 	}
}
