package com.leadtone.mas.bizplug.smssend.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.util.ExcelImport;
import com.leadtone.mas.bizplug.util.FileImport;
import com.leadtone.mas.bizplug.util.TxtAndCsvImport;

public class ContactsUtil {

	/**
	 * 解析收件人
	 * 
	 * @param info
	 * @return
	 */
	public static Set<Contacts> getContactsSet(String info) {
		Set<Contacts> set = new HashSet<Contacts>();
		if (info != null && info.trim().length() > 0) {
			StringTokenizer st = new StringTokenizer(info, ",");
			while (st.hasMoreTokens()) {
				String mobile = null;
				String name = null;
				String uInfo = st.nextToken();
				uInfo = uInfo.trim();
				// 判断用户还是组
				if (uInfo.indexOf("<") >= 0) {
					mobile = uInfo.substring(0, uInfo.indexOf("<"));
					name = uInfo.substring(uInfo.indexOf("<") + 1, uInfo
							.length() - 1);
				} else {
					mobile = uInfo;
				}
				if( StringUtil.isMobile(mobile)){
					Contacts c = new Contacts(mobile, name);
					set.add(c);
				}
			}
		}
		return set;
	}

	/**
	 * 解析收件人/组
	 * 
	 * @param info
	 * @return
	 */
	public static Set<Contacts> getContactsSet(ContactsService contactsService,
			String info, Long merchantPin) {
		Set<Contacts> set = new HashSet<Contacts>();
		if (info != null && info.trim().length() > 0) {
			StringTokenizer st = new StringTokenizer(info, ",");
			while (st.hasMoreTokens()) {
				String mobile = null;
				String name = null;
				String uInfo = st.nextToken();
				uInfo = uInfo.trim();
				// 判断用户还是组
				if (uInfo.endsWith(ApSmsConstants.USER_GROUP_SUFFIX)) {
					String gName = uInfo.substring(0, uInfo.indexOf("<"));
					List<Contacts> list = contactsService.getContactsByGroupName(merchantPin, 
							gName.trim());
					if (list != null) {
						for (Contacts contact : list) {
							if( StringUtil.isMobile(contact.getMobile())){
								Contacts c = new Contacts(contact.getMobile(),
										contact.getName());
								set.add(c);
							}
						}
					}
				} else {
					if (uInfo.indexOf("<") >= 0) {
						mobile = uInfo.substring(0, uInfo.indexOf("<"));
						name = uInfo.substring(uInfo.indexOf("<") + 1, uInfo
								.length() - 1);
					} else {
						mobile = uInfo;
					}
					if( StringUtil.isMobile(mobile)){
						Contacts c = new Contacts(mobile, name);
						set.add(c);
					}
				}
			}
		}
		return set;
	}
	/**
	 * 解析上传文件
	 * 文件格式为xls, txt
	 * xls格式，无标题，使用两列，{手机号，姓名}
	 * txt格式，无标题，使用两列，{手机号[,姓名]}
	 * @param file
	 * @param filename
	 * @param storePath
	 * @return
	 */
	public static Set<Contacts> getContactsFromFile(File file, String filename, String storePath) {
		int BUFFER_SIZE = 1024 * 10;
		Set<Contacts> set = new HashSet<Contacts>();
		InputStream in = null;
		OutputStream out = null;
		try {
		
			Long rid = PinGen.getSerialPin();
			String tmpFilePath = storePath + File.separator + rid +"_"+ filename;
			in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(new File(tmpFilePath)), BUFFER_SIZE);
			byte buffer[] = new byte[1];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
			out.close();
			String[][] result = null;
			if(filename.toLowerCase().endsWith("xls")){
				FileImport imp = new ExcelImport(new File(tmpFilePath));
				result = imp.getFileContent(new Integer[]{0,1}, false);
			}else if(filename.toLowerCase().endsWith("txt")){
				FileImport imp = new TxtAndCsvImport(new File(tmpFilePath)); 
				result = imp.getFileContent(new Integer[]{0,1}, false);
			}
			if( result != null){
				for(int i=0; i<result.length; i++){
					if( StringUtil.isMobile(result[i][0])){
						Contacts c = new Contacts(result[i][0],result[i][1]);
						set.add(c);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// ignore
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return set;
	}
}
