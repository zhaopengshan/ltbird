package com.leadtone.mas.connector.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public abstract class EncryptUtils {
	private static final Log logger = LogFactory.getLog(EncryptUtils.class);
	private static final String DEFAULT_ENCRYPT_MODE = "DES/ECB/PKCS5Padding";
	private static final String DEFAULT_KEY_ALGORITHM = "DES";
	private static final String DESEDE_KEY_ALGORITHM = "DESede";

	private static final String DEFAULT_ENCODING = "UTF-8";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 
	 * @param bytes
	 *            待加密的字节数组
	 * @param algorithm
	 *            算法以及填充模式
	 * @param key
	 *            DES密钥
	 * @param iv
	 *            DES初始化向量
	 * @return
	 */

	public static byte[] DES(byte[] bytes, String algorithm, int iMode, byte[] key, byte[] iv) {
		if (StringUtils.isEmpty(algorithm)) {
			algorithm = DEFAULT_ENCRYPT_MODE;
		}
		if (iMode != Cipher.ENCRYPT_MODE && iMode != Cipher.DECRYPT_MODE) {
			throw new IllegalArgumentException("iMode只能为1或2");
		}

		try {
			DESKeySpec desKeySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DEFAULT_KEY_ALGORITHM);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			Cipher cipher = Cipher.getInstance(algorithm);
			if (iv != null) {
				IvParameterSpec ivps = new IvParameterSpec(iv);
				cipher.init(iMode, secretKey, ivps);
			} else {
				cipher.init(iMode, secretKey);
			}
			return cipher.doFinal(bytes);
		} catch (Exception ex) {
//			logger.error("", ex);
			logger.error("*************DES methord Exception error");
		}
		return null;
	}

	/**
	 * 微软加解密转换用DESede
	 * 
	 * @param bytes
	 *            待加密的字节数组
	 * @param algorithm
	 *            算法以及填充模式
	 * @param iMode
	 * @param key
	 *            DES密钥
	 * @param iv
	 *            DES初始化向量
	 * @return
	 */
	public static byte[] DESede(byte[] bytes, String algorithm, int iMode, byte[] key, byte[] iv) {
		if (StringUtils.isEmpty(algorithm)) {
			algorithm = DEFAULT_ENCRYPT_MODE;
		}
		if (iMode != Cipher.ENCRYPT_MODE && iMode != Cipher.DECRYPT_MODE) {
			throw new IllegalArgumentException("iMode只能为1或2");
		}

		try {
			// DESKeySpec desKeySpec = new DESKeySpec(key);
			DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE_KEY_ALGORITHM);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			Cipher cipher = Cipher.getInstance(algorithm);
			if (iv != null) {
				IvParameterSpec ivps = new IvParameterSpec(iv);
				cipher.init(iMode, secretKey, ivps);
			} else {
				cipher.init(iMode, secretKey);
			}
			return cipher.doFinal(bytes);
		} catch (Exception ex) {
//			logger.error("", ex);
			logger.error("*************DESede methord Exception error");
		}
		return null;
	}

	public static byte[] DESedeWithPKCS7Padding(byte[] bytes, String algorithm, int iMode,
			byte[] key, byte[] iv) {
		// DESede/ECB/PKCS5Padding
		if (StringUtils.isEmpty(algorithm)) {
			algorithm = DEFAULT_ENCRYPT_MODE;
		}
		if (iMode != Cipher.ENCRYPT_MODE && iMode != Cipher.DECRYPT_MODE) {
			throw new IllegalArgumentException("iMode只能为1或2");
		}

		try {
			// DESKeySpec desKeySpec = new DESKeySpec(key);
			DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE_KEY_ALGORITHM);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			Cipher cipher = Cipher.getInstance(algorithm, "BC");
			if (iv != null) {
				IvParameterSpec ivps = new IvParameterSpec(iv);
				cipher.init(iMode, secretKey, ivps);
			} else {
				cipher.init(iMode, secretKey);
			}
			return cipher.doFinal(bytes);
		} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.error("", ex);
		    logger.error("*************DESedeWithPKCS7Padding methord Exception error");
		}
		return null;
	}

	/**
	 * 将给定字符串进行MD5编码
	 * 
	 * @param str
	 *            待编码的MD5字符串，以UTF-8编码
	 * @return
	 */
	public static byte[] MD5(String str) {
		return MD5(str, "UTF-8");
	}

	public static byte[] MD5BySysEncoding(String str) {
		return MD5(str.getBytes());
	}

	/**
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static byte[] MD5(String str, String charset) {
		try {
			return MD5(str.getBytes(charset));
		} catch (UnsupportedEncodingException ex) {
//			logger.error("不支持的charset：[" + charset + "]", ex);
			logger.error("*******MD5 methord  不支持的charset：[" + charset + "] UnsupportedEncodingException error");
		}
		return null;
	}

	/**
	 * MD5加密
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] MD5(byte[] bytes) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(bytes);
			return md5.digest();
		} catch (NoSuchAlgorithmException ex) {
//			logger.error("", ex);
			logger.error("*************MD5 methord NoSuchAlgorithmException error");
		}
		return null;
	}

	/**
	 * SHA1加密
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] SHA1(byte[] input) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.update(input);
			return sha1.digest();
		} catch (NoSuchAlgorithmException ex) {
//			logger.error("", ex);
			logger.error("*************SHA1 methord NoSuchAlgorithmException error");
		}
		return null;
	}

	public static String base64Encode(byte[] input) {
		byte[] encodeBytes = Base64.encodeBase64(input, true);
		return new String(encodeBytes).trim();
	}

	public static String base64Decode(String str) {
		return new String(Base64.decodeBase64(str.getBytes())).trim();
	}

	/**
	 * 
	 * @param base64
	 * @return
	 */
	public static String encodeBase64(byte[] input) {
		return encodeBase64(input, DEFAULT_ENCODING);
	}

	public static String encodeBase64(byte[] input, String encoding) {
		return encodeBase64(input, encoding, false);
	}

	public static String encodeBase64(byte[] input, String encoding, boolean isChunked) {
		if (StringUtils.isBlank(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		byte[] encodedBytes = Base64.encodeBase64(input, isChunked);
		try {
			return new String(encodedBytes, encoding).trim();
		} catch (UnsupportedEncodingException ex) {
//			logger.error("", ex);
			logger.error("*************encodeBase64 methord UnsupportedEncodingException error");
		}
		return null;
	}

	/**
	 * 将base64字符串解码
	 * 
	 * @param base64
	 *            待解码的base64字符串
	 * @return
	 */
	public static byte[] decodeBase64(String base64, String encoding) {
		if (StringUtils.isBlank(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		try {
			return Base64.decodeBase64(base64.getBytes(encoding));
		} catch (UnsupportedEncodingException ex) {
//			logger.error("", ex);
			logger.error("*************decodeBase64 methord UnsupportedEncodingException error");
		}
		return null;
	}

	public static byte[] decodeBase64(String base64) {
		return decodeBase64(base64, DEFAULT_ENCODING);
	}

	/**
	 * 将字节数组转换为16进制字符串表示
	 * 
	 * @param bytes
	 *            待转换的字节数组
	 * @return 16进制表示的字符串
	 */
	public static String bytes2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		String hex = null;
		for (int i = 0; i < bytes.length; i++) {
			hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * 将16进制字符串转换为字节数组
	 * 
	 * @param hexstr
	 * @return
	 */
	public static byte[] hex2Bytes(String hexstr) {
		byte[] b = hexstr.getBytes();
		int length = b.length;
		if ((length % 2) != 0) {
			throw new IllegalArgumentException("字符串对应字节数必须是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String initDESKey(int keySize) {
		try {
			KeyGenerator kg = KeyGenerator.getInstance("DESede", "BC");
			kg.init(keySize);
			SecretKey secretKey = kg.generateKey();
			return base64Encode(secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			logger.error("", e);
			logger.error("*************initDESKey methord NoSuchAlgorithmException error");
		} catch (NoSuchProviderException e) {
//			e.printStackTrace();
//			logger.error("", e)
			logger.error("*************initDESKey methord NoSuchProviderException error");
		}
		return null;
	}
}
