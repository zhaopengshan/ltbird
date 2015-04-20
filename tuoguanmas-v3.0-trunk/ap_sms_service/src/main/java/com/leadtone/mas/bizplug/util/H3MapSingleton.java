package com.leadtone.mas.bizplug.util;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;

@Component(value="")
public class H3MapSingleton {
	private static Log logger = LogFactory.getLog(H3MapSingleton.class);
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	public static HashMap<String,MbnThreeHCode> h3Map = null;
	
	@PostConstruct
	private void init(){
		logger.info("init h3code start");
		List<MbnThreeHCode> h3Code = mbnThreeHCodeService.queryAll();
		h3Map = new HashMap<String,MbnThreeHCode>();
		for(MbnThreeHCode h3Bean: h3Code){
			h3Map.put(h3Bean.getMobilePrefix(), h3Bean);
		}
		logger.info("init h3code over");
	}
	
	public  boolean checkExist(String h3string){
		MbnThreeHCode h3bean = h3Map.get(h3string);
		if(h3bean!=null){
			return true;
		}
		return false;
	}
	public MbnThreeHCode fetchH3Code(String h3string){
		return h3Map.get(h3string);
	}
	
	
}
