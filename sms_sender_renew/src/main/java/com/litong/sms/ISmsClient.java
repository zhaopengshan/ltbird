package com.litong.sms;

public interface ISmsClient {
	/**
	 */
	public String smsSend(String username,String pwd,String key,String url,/*String extendCode,*/ String phone,String msgcontent,String mttime,String cpoid);
}
