package com.leadtone.mas.bizplug.smssend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiShiJuan;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiShiJuanDao;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiTiKuInfo;

public class SmsDatiShiJuanDaoTest extends TestCase {

	private ClassPathXmlApplicationContext context;
	public void setUp(){
		  context = new ClassPathXmlApplicationContext(
	              new String[]{"applicationContext.xml"});
	}
	
	
	public void testInsertShiJuan(){
		MasSmsDatiShiJuanDao masSmsDatiShiJuan = (MasSmsDatiShiJuanDao)context.getBean("smsDatiShiJuanDao"); 
		MasSmsDatiShiJuan shiJuan = new MasSmsDatiShiJuan();
		shiJuan.setId(PinGen.getSerialPin());
		shiJuan.setAnswer("answer");
		shiJuan.setCreateBy(1);
		shiJuan.setCreateTime(new Date());
		shiJuan.setDxdtId(Long.parseLong("11111"));
		shiJuan.setOrderNumber(1);
		shiJuan.setQuestion("123");
		masSmsDatiShiJuan.insert(shiJuan);
	}
	
	public void testDeleteShiJuan(){
		MasSmsDatiShiJuanDao masSmsDatiShiJuan = (MasSmsDatiShiJuanDao)context.getBean("smsDatiShiJuanDao"); 
		MasSmsDatiShiJuan shiJuan = new MasSmsDatiShiJuan();
		shiJuan.setId(Long.parseLong("8130243767012237755"));
		shiJuan.setAnswer("answer1");
		masSmsDatiShiJuan.delete(shiJuan);
	}
	
	public void testInsertShiJuanFromTiKu(){
		
		
		List<MasSmsDatiTiKuInfo> infoList = new ArrayList<MasSmsDatiTiKuInfo>();
		MasSmsDatiTiKuInfo info = new MasSmsDatiTiKuInfo();
		info.setSerialId(1);
		info.setTikuId(3);
		
		MasSmsDatiTiKuInfo info1 = new MasSmsDatiTiKuInfo();
		info1.setSerialId(2);
		info1.setTikuId(4);
		
		infoList.add(info);
		infoList.add(info1);
		
		MasSmsDatiShiJuanDao masSmsDatiShiJuan = (MasSmsDatiShiJuanDao)context.getBean("smsDatiShiJuanDao");
		masSmsDatiShiJuan.insertShiJuanSelectTiKu( Long.parseLong("3"),  1, infoList);
		
		
	}
	public void tearDown(){
		  context = null;
	}
}
