package com.leadtone.delegatemas.admin.security.action;
 
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.UserService;
/**
 * MasUser工具类
 * @author PAN-Z-G
 *
 */ 
public class UserUtil {
	public static boolean isCorpAccessNumber(UserService userService,UserVO userVO){
		try {
			UserVO u=new UserVO();
			u.setCorpAccessNumber(userVO.getCorpAccessNumber());
			List<Users> users=userService.getUserBycorpAccessNumber(u);
			if(users.size()>0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String getRandomZxtUserId(){
		Random r = new Random();
		r.setSeed(new Date().getTime());
		int i = r.nextInt(99999);
		String x = String.format("%05d", i);
		return x;
	}	
	public static void main(String[] args){
		for(int i=0;i<10;i++)
		System.out.println(UserUtil.getRandomZxtUserId());
	}
}
