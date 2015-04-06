package com.leadtone.mas.bizplug.lisence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 *
 * @author ChangJun
 *
 */

public class MD5 {

	public static String md52string(byte[] source) {
		if (source == null) {
			return null;
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(source);
		byte b[] = md.digest();
		StringBuffer buf = new StringBuffer("");

		int num = 0;
		for (int i = 0; i < b.length; i++) {
			num = b[i];
			if (num < 0) {
				num += 256;
			}
			if (num < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(num));
		}

		return buf.toString();
	}

	public static byte[] md52byte(byte[] source) {
		if (source == null) {
			return null;
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(source);
		return md.digest();
	}

	public static void main(String[] args) {
		System.out.println(MD5.md52byte("吃饭了".getBytes()));
	}

}
