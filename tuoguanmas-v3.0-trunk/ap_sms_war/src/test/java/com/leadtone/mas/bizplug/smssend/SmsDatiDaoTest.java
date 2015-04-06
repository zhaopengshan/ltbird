package com.leadtone.mas.bizplug.smssend;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiDao;

public class SmsDatiDaoTest extends TestCase {

	private ClassPathXmlApplicationContext context;
	public void setUp(){
		  context = new ClassPathXmlApplicationContext(
	              new String[]{"applicationContext.xml"});
	}
	
	
	public void testInsertDati(){
		MasSmsDatiDao smsDati = (MasSmsDatiDao)context.getBean("smsDatiDao");
		MasSmsDati sms = new MasSmsDati();
		sms.setBeginTime(new Date());
		sms.setCommitTime(new Date());
		sms.setContent("content");
		sms.setCreateBy(1);
		sms.setEndTime(new Date());
		sms.setTitle("test");
		sms.setId(PinGen.getSerialPin());
		sms.setTos("15010309461");
		smsDati.insert(sms);
		
	}
	
	public void testDeleteDati(){
		MasSmsDatiDao smsDati = (MasSmsDatiDao)context.getBean("smsDatiDao");
		MasSmsDati sms = new MasSmsDati();
		sms.setId(Long.parseLong("8130243623187269392"));
		smsDati.delete(sms);
		
	}
	
	public void testUpdateDati(){
		MasSmsDatiDao smsDati = (MasSmsDatiDao)context.getBean("smsDatiDao");
		MasSmsDati sms = new MasSmsDati();
		sms.setId(Long.parseLong("8130243623187269392"));
		sms.setContent("content1");
		smsDati.update(sms);
		
	}
	
	public void testQueryInfo(){
		MasSmsDatiDao smsDati = (MasSmsDatiDao)context.getBean("smsDatiDao");
		List<MasSmsDati> masSmsDatiList = smsDati.getMasSmsDatiByCreatorId(Long.parseLong("1130200163497944497"));
		System.out.println(masSmsDatiList.size());
	}
	
	public void tearDown(){
		  context = null;
	}
	  
}
