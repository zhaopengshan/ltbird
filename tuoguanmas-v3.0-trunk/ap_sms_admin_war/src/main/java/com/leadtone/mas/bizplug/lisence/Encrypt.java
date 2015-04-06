package com.leadtone.mas.bizplug.lisence;

/**
 * IMEI加密类
 *
 * @author ChangJun
 *
 */
public class Encrypt {

	private static byte[] encrypt(String imei) {
		int length = imei.length();
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			switch (i) {
			case 0:
				array[i] = Integer.parseInt(imei.charAt(8) + "");
				break;
			case 1:
				array[i] = Integer.parseInt(imei.charAt(12) + "");
				break;
			case 2:
				array[i] = Integer.parseInt(imei.charAt(0) + "");
				break;
			case 3:
				array[i] = Integer.parseInt(imei.charAt(10) + "");
				break;
			case 4:
				array[i] = Integer.parseInt(imei.charAt(7) + "");
				break;
			case 5:
				array[i] = Integer.parseInt(imei.charAt(2) + "");
				break;
			case 6:
				array[i] = Integer.parseInt(imei.charAt(11) + "");
				break;
			case 7:
				array[i] = Integer.parseInt(imei.charAt(4) + "");
				break;
			case 8:
				array[i] = Integer.parseInt(imei.charAt(1) + "");
				break;
			case 9:
				array[i] = Integer.parseInt(imei.charAt(6) + "");
				break;
			case 10:
				array[i] = Integer.parseInt(imei.charAt(14) + "");
				break;
			case 11:
				array[i] = Integer.parseInt(imei.charAt(13) + "");
				break;
			case 12:
				array[i] = Integer.parseInt(imei.charAt(5) + "");
				break;
			case 13:
				array[i] = Integer.parseInt(imei.charAt(9) + "");
				break;
			case 14:
				array[i] = Integer.parseInt(imei.charAt(3) + "");
				break;
			default:
				break;
			}
		}

		byte[] result = new byte[length];

		for (int i = 0; i < array.length; i++) {
			int temp = array[i];

			switch (i) {
			case 0:
				result[i] = (byte) (temp << 3);
				break;
			case 1:
				result[i] = (byte) (temp << 1);
				break;
			case 2:
				result[i] = (byte) (temp << 2);
				break;
			case 3:
				result[i] = (byte) (temp << 1);
				break;
			case 4:
				result[i] = (byte) (temp << 3);
				break;
			case 5:
				result[i] = (byte) (temp << 1);
				break;
			case 6:
				result[i] = (byte) (temp << 2);
				break;
			case 7:
				result[i] = (byte) (temp << 2);
				break;
			case 8:
				result[i] = (byte) (temp << 3);
				break;
			case 9:
				result[i] = (byte) (temp << 3);
				break;
			case 10:
				result[i] = (byte) (temp << 1);
				break;
			case 11:
				result[i] = (byte) (temp << 2);
				break;
			case 12:
				result[i] = (byte) (temp << 3);
				break;
			case 13:
				result[i] = (byte) (temp << 1);
				break;
			case 14:
				result[i] = (byte) (temp << 1);
				break;
			default:
				break;
			}
		}

		return result;
	}
	
	public static String calculateSerial(String imei) {
		String md5 = MD5.md52string(encrypt(imei));

		int length = md5.length();
		StringBuffer serial = new StringBuffer();
		for (int i = 0; i < length; i = i + 2) {
			serial.append(md5.charAt(i));
		}

		return serial.toString();
	}

}
