package com.leadtone.mas;

import com.leadtone.mas.register.utils.MasPasswordTool;

public class SecurityCreator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String userName = "swlnmasproduct91";//password
		String userPWD = "swlnmasproduct91_1n1t.c0m";//account
		
		System.out.println( MasPasswordTool.getEncString(userName, userPWD) );
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		String encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
		System.out.println( MasPasswordTool.getDesString("JgoHITILYgIiBSz7/M4YoA==", "mascity_admin") );
//		
//		System.out.println("-----------------");
//		
//		userName = "Changrun1";//password
//		userPWD = "changrunrealty";//account
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
////		System.out.println( MasPasswordTool.getDesString(encodePwd, userName) );
//		
//		System.out.println("-----------------");
//		userName = "Hisam1";//password
//		userPWD = "hisamhairdressing";//account
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
////		System.out.println( MasPasswordTool.getDesString(encodePwd, userName) );
//		
//		System.out.println("-----------------");
//		userName = "Zhshmzjjc1";//password
//		userPWD = "zhshmzjjc";//account
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
////		System.out.println( MasPasswordTool.getDesString(encodePwd, userName) );
//		
//		System.out.println("-----------------");
//		userName = "Ddzhxxz1";//password
//		userPWD = "ddzhxxz";//account
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
////		System.out.println( MasPasswordTool.getDesString(encodePwd, userName) );
//		
//		System.out.println("-----------------");
//		userName = "Ddyzsd1";//password
//		userPWD = "ddyzsd";//account
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
////		System.out.println( MasPasswordTool.getDesString(encodePwd, userName) );
//		
//		System.out.println("-----------------");
//		userName = "Dgpublicsecurity1";//password
//		userPWD = "dgpublicsecurity";//account
//		System.out.println("帐号:"+userPWD);
//		System.out.println("密码:"+userName);
//		encodePwd = MasPasswordTool.getEncString(userPWD, userName);
//		System.out.println("密文:"+encodePwd);
////		System.out.println( MasPasswordTool.getDesString(encodePwd, userName) );
		
		// TODO Auto-generated method stub
//		String xmlDoc = "05201617404137,13900000001,DELIVRD|05220128211118,13900000002,DELIVRD";
//		
//		if(xmlDoc.contains("|")){
//			String[] rptArray = xmlDoc.split("\\|");
//			for( int i = 0; i < rptArray.length; i++ ){
//				String rptPerOne = rptArray[i];
//				String[] perResult = rptPerOne.split("\\,");
//				System.out.println("");
//			}
//		}else{
//			String[] perResult = xmlDoc.split("\\,");
//			System.out.println("");
//			if(perResult!=null&&perResult.length>2){
//			}
//		}
	}

}
