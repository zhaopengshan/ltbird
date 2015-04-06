package com.leadtone.mas.bizplug.security.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUserHolder {  

    /** 
     * Returns the current user 
     *  
     * @return 
     */  
    public static User getCurrentUser() {  
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
    }  

}  