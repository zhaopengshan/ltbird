package com.leadtone.util;

import java.util.HashMap;
import com.litong.utils.StringUtil;
/**
 * CMPP 错误翻译工具类
 * @author admin
 *
 */
public class ErrorCodeUtils {
	public static HashMap<String, String> ERROR_TABLE = new HashMap<String, String>();
	static {
		ERROR_TABLE.put("EXPIRED", "过期");
		ERROR_TABLE.put("DELETED", "被删除");
		ERROR_TABLE.put("UNDELIV", "未投递");
		ERROR_TABLE.put("ACCEPTD", "已接受");
		ERROR_TABLE.put("UNKNOWN", "未知");
		ERROR_TABLE.put("REJECTD", "拒绝");
	}

	/**
	 * 获取错误码解释 (最长为15位)
	 * @param errorCode
	 * @return
	 */
	public static final String getErrorDesc(String errorCode) {
		String result = "";
		if (StringUtil.isEmpty(errorCode)) {
			return errorCode;
		} else {
			result = ERROR_TABLE.get(errorCode.toUpperCase());
			if (StringUtil.isEmpty(result)) {
				result = errorCode;
			}
		}
		if( result.length() > 15){
			result = result.substring(0, 16);
		}
		return result;
	}
}
