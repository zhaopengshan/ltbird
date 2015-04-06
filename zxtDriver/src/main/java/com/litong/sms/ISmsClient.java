package com.litong.sms;

public interface ISmsClient {
	/**
	 * MAS���Žӿ�
	 * @param username �û���
	 * @param pwd �û�����
	 * @param key ��Կ
	 * @param url �ӿڵ�ַ
	 * @param phone �ֻ����
	 * @param msgcontent ��������
	 * @param mttime �·�ʱ��
	 * @return ���ͽ��(�ɹ�ʱ����success,ʧ��ʱ�Ż���ERROR:+������Ϣ)
	 */
	public String smsSend(String username,String pwd,String key,String url,String phone,String msgcontent,String mttime,String cpoid, String extendCode);
}
