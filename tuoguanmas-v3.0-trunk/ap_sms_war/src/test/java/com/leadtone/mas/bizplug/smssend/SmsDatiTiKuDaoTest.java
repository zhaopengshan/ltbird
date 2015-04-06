package com.leadtone.mas.bizplug.smssend;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
import com.leadtone.mas.bizplug.dati.dao.MasSmsDatiTiKuDao;

public class SmsDatiTiKuDaoTest extends TestCase {
  private ClassPathXmlApplicationContext context;
  public void setUp(){
	  context = new ClassPathXmlApplicationContext(
              new String[]{"applicationContext.xml"});
  } 
	
  public void testTiKuInsertDao(){
	  
	  //smsDatiTiKuDao
	  MasSmsDatiTiKuDao sms = (MasSmsDatiTiKuDao) context.getBean("smsDatiTiKuDao");
	  //assertTrue( false );
	  MasSmsDatiTiKu tiku = new MasSmsDatiTiKu();
	  tiku.setAnswer("an");
	  tiku.setQuestion("qu");
	  tiku.setCreateBy(1);
	  tiku.setCreateTime(new Date());
	  tiku.setId(PinGen.getSerialPin());
	  tiku.setModifyTime(null);
	 
	  tiku.setScore(3);
	  sms.insert(tiku);
	  //this.assertNotNull(sms);
  }
  
  public void testTiKuDeleteDao(){
	  MasSmsDatiTiKu tiku = new MasSmsDatiTiKu();
	  tiku.setId(new Long(1));
	  MasSmsDatiTiKuDao sms = (MasSmsDatiTiKuDao) context.getBean("smsDatiTiKuDao");
	  sms.delete(tiku);
  }
  
  public void testTiKuListByCreator(){
	  MasSmsDatiTiKuDao sms = (MasSmsDatiTiKuDao) context.getBean("smsDatiTiKuDao");
	  List<MasSmsDatiTiKu> tikuList = sms.getSmsDatiTiKuListById(1);
	  for(MasSmsDatiTiKu tiku:tikuList){
		  System.out.println(tiku.getQuestion()+":"+tiku.getId());
	  }
  }
  
  public void testTiKuDeleteDaoById(){
	  
	  MasSmsDatiTiKuDao sms = (MasSmsDatiTiKuDao) context.getBean("smsDatiTiKuDao");
	  sms.delete(2, 1);
  }
  
  public void testTiKuListForPage(){
	  MasSmsDatiTiKuDao sms = (MasSmsDatiTiKuDao) context.getBean("smsDatiTiKuDao");
	  List<MasSmsDatiTiKu> masSmsTiKuList = sms.getSmsDatiTiKuListByKeywordAndCreatorForPage(Long.parseLong("1130200163497944497"), "qu3", "2013-01-23 15:38:03", "2013-01-23 15:38:04", 0, 20);
	  for(MasSmsDatiTiKu tiku:masSmsTiKuList){
		  System.out.println("题库id："+tiku.getId());
	  }
  }
  
  public void testTiKuListCount(){
	  MasSmsDatiTiKuDao sms = (MasSmsDatiTiKuDao) context.getBean("smsDatiTiKuDao");
	  Integer count = sms.getSmsDatiTiKuCountByKeywordAndCreator(Long.parseLong("1130200163497944497"), "qu3", "2013-01-23 15:38:03", "2013-01-23 15:38:08");
	  System.out.println("符合条件的数量："+count.intValue());
  }
  
  public void tearDown(){
	  
	  context = null;
	  
  }
}
