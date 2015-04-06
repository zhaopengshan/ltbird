package com.leadtone.mas.admin.util;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {
	
	public static Long[] arrStringToLong(String[] stringArr){
		List<Long> longList = new ArrayList<Long>();
		for( int i = 0; i < stringArr.length; i++){
			Long userId = Long.valueOf(stringArr[i]);
			longList.add(userId);
		}
		Long[] pks = longList.toArray(new Long[0]);
		return pks;
	}
}
