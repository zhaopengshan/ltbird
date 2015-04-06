package com.leadtone.mas.bizplug.util;

import javax.servlet.http.HttpSession;
import com.leadtone.mas.bizplug.security.bean.UserVO;

public class LoginCheckUtil{
	//锁定计数
	public static boolean isAccountlock(HttpSession session,UserVO user){
		String pin=user.getMerchantPin().toString();
		if(session.getAttribute(pin)==null){
			session.setAttribute(pin,1);
			return false;
		}else{
			Integer object= (Integer)session.getAttribute(pin);
			session.setAttribute(pin,++object);
			if(object>=user.getLimitTryCount())
				return true;
			else  
				return false; 
		}
		
	}
}
