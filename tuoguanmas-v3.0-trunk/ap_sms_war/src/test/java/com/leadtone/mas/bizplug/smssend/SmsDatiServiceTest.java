package com.leadtone.mas.bizplug.smssend;

import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
import com.leadtone.mas.bizplug.dati.service.MasSmsDatiService;

public class SmsDatiServiceTest extends TestCase {

	private ClassPathXmlApplicationContext context;
	public void setUp(){
		  context = new ClassPathXmlApplicationContext(
	              new String[]{"applicationContext.xml"});
	}
	
	public void testAddTiKu(){
		
		MasSmsDatiService service = (MasSmsDatiService)context.getBean("masSmsDatiService");
		MasSmsDatiTiKu tiku = new MasSmsDatiTiKu();
		tiku.setAnswer("an");
		tiku.setQuestion("qu");
		tiku.setCreateBy(1);
		tiku.setCreateTime(new Date());
		tiku.setId(PinGen.getSerialPin());
		tiku.setModifyTime(null);
		tiku.setScore(3);
		service.createDatiTiKu(tiku);
	}
	
	public void tearDown(){
		context = null;
	}
}
