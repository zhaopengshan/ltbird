package com.leadtone.mas.register.utils;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class MasPasswordTool {
	public static final String ALGORITHM = "PBEWITHMD5andDES";
	// 迭代次数
	public static final int ITERATION_COUNT = 100;

	/**
	 * 盐初始化<br>
	 * 盐长度必须为8字节
	 * 
	 * @return byte[] 盐
	 */
	public static byte[] initSalt(String salt) {
		byte[] tmp = DigestUtils.md5(salt);
		byte[] result = new byte[8];
		System.arraycopy(tmp, 0, result, 0, 8);
		return result;
	}

	/**
	 * 转换密钥
	 * 
	 * @param password
	 *            密码
	 * @return key 密钥
	 * @throws Exception
	 */
	private static Key toKey(String password) throws Exception {
		// 密钥材料转换
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		// 实例化
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}

	/**
	 * 加密
	 * 
	 * @param source
	 *            源密码
	 * @param password
	 *            盐 user.loginAccount
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static String getEncString(String source, String password) {
		String strMi = "";
		try {
			byte[] data = source.getBytes();
			byte[] salt = initSalt(password);
			Key key = toKey(password);
			// 实例化PBE参数材料
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt,
					ITERATION_COUNT);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

			byte[] byteMi = cipher.doFinal(data);
			strMi = new String(Base64.encodeBase64(byteMi), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strMi;
	}

	/**
	 * 
	 * @param source
	 * 		密文密码
	 * @param password
	 * 		盐 user.loginAccount
	 * @return
	 */
	public static String getDesString(String source, String password) {
		String strMi = "";
		try {
			byte[] data = Base64.decodeBase64(source);
			byte[] salt = initSalt(password);
			Key key = toKey(password);
			// 实例化PBE参数材料
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt,
					ITERATION_COUNT);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
			byte[] byteMi = cipher.doFinal(data);
			strMi = new String(byteMi, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strMi;
	}

	public static void main(String[] args) throws Exception {
		String secret = "system_admin";
		String x = MasPasswordTool.getEncString(secret, secret);//.getDesString("G7G9aISmKtg=", "test");
		System.out.println(x);
		String str="";
		Random random=new Random();
		for (int i = 0; i < 5; i++) {
			str+=(int)(random.nextFloat()*10) ;
		}
		System.out.println(x); 
	}

}
