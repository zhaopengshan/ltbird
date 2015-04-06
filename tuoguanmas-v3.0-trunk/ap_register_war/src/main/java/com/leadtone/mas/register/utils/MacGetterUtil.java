package com.leadtone.mas.register.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class MacGetterUtil {
	private static final Logger log = Logger.getLogger(MacGetterUtil.class);
	static private String hexByte(byte b) {
//        String s = "000000" + Integer.toHexString(b);
//        return s.substring(s.length() - 2);
		return String.format("%02x",b);
    }
	static private List<String> getMAC() {
        Enumeration<NetworkInterface> el;
        List<String> mac_s = new ArrayList<String>();
        try {
            el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                byte[] mac = el.nextElement().getHardwareAddress();
                
                if (mac == null || mac.length == 0 || mac.length > 6)
                    continue;
                mac_s.add( getMACAddress(mac) );
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        return mac_s;
    }
	static private String getMACAddress(byte[] mac){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("");
            }
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        return sb.toString().toUpperCase();
    }
	static public List<String> getMACLisence() {
		List<String> macList = MacGetterUtil.getMAC();
		Iterator<String> macIterator = macList.iterator();
		List<String> macLisence = new ArrayList<String>();
		while( macIterator.hasNext() ){
			Long macAddr = Long.parseLong(macIterator.next(), 16);
			String macAddrStr = macAddr.toString();
			macAddrStr = String.format("%015d", macAddr);
			String serial = Encrypt.calculateSerial(macAddrStr);
			macLisence.add(serial);
		}
		return macLisence;
    }
	static public List<String> getFormatMAC() {
		List<String> macList = MacGetterUtil.getMAC();
		Iterator<String> macIterator = macList.iterator();
		List<String> macFormat = new ArrayList<String>();
		while( macIterator.hasNext() ){
			Long macAddr = Long.parseLong(macIterator.next(), 16);
			String macAddrStr = macAddr.toString();
			macAddrStr = String.format("%015d", macAddr);
			macFormat.add(macAddrStr);
		}
		return macFormat;
    }
	static public String getLisenceByMac(String imei) {
		Pattern pattern = Pattern.compile("[0-9]\\d{14}(?!\\d)");
		Matcher matcher = pattern.matcher(imei);
		String serial="";
		if (!matcher.find()) {
			return "error";
		} else {
			serial = Encrypt.calculateSerial(imei);
			log.info("系统根据IMEI号[" + imei + "]生成序列号[" + serial + "]成功");
		}
		return serial;
    }
//	public static void main(String[] args) {
//		MacGetterUtil a = new MacGetterUtil();
//		List<String> macList = a.getMAC();
//		Iterator<String> macIterator = macList.iterator();
//		int index = 100;
//		while( macIterator.hasNext() ){
//			Long macAddr = Long.parseLong(macIterator.next(), 16);
//			String macAddrStr = macAddr.toString();
//			macAddrStr = String.format("%015d", macAddr);
//			String serial = Encrypt.calculateSerial(macAddrStr);
//			System.out.println(macAddrStr+"->"+serial);
//		}
//    }
}
