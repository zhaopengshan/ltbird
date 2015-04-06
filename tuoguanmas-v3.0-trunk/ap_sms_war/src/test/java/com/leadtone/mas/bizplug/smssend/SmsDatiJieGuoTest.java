package com.leadtone.mas.bizplug.smssend;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiResult;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiResultDao;

public class SmsDatiJieGuoTest extends TestCase {
	private ClassPathXmlApplicationContext context;
	public void setUp(){
		  context = new ClassPathXmlApplicationContext(
	              new String[]{"applicationContext.xml"});
	}
	
	public void tearDown(){
		  context = null;
	}
	
	public void testInsertJieGuo(){
	   MasSmsDatiResultDao resultDao = (MasSmsDatiResultDao)context.getBean("smsDatiResultDao");	
	   MasSmsDatiResult result = new MasSmsDatiResult();
	   result.setId(PinGen.getSerialPin());
	   result.setAnswer("answer");
	   result.setCreateBy(1);
	   resultDao.insert(result);
	}
	
	public void testSelectJieGuo(){
		MasSmsDatiResultDao resultDao = (MasSmsDatiResultDao)context.getBean("smsDatiResultDao");
		List<MasSmsDatiResult> resultList = resultDao.getMosSmsDtResultByIdAndMobileAndOrder(Long.parseLong("1"), "15010309461", 0);
		for(MasSmsDatiResult result:resultList){
			System.out.println("手机号码："+result.getMobile());
		}
	}
}
