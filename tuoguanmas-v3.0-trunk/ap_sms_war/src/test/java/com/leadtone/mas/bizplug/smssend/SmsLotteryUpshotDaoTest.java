package com.leadtone.mas.bizplug.smssend;

import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLotteryUpshot;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsLotteryUpshotDao;

public class SmsLotteryUpshotDaoTest extends TestCase {
  private ClassPathXmlApplicationContext context;
  private MasSmsLotteryUpshotDao sms;
  public void setUp(){
	  context = new ClassPathXmlApplicationContext(
              new String[]{"applicationContext.xml"});
	  sms=(MasSmsLotteryUpshotDao) context.getBean("masSmsLotteryUpshotDao");
  } 

  public void insertLotteryUpshot(){
	  MasSmsLotteryUpshot lotteryUpshot = new MasSmsLotteryUpshot();
	  lotteryUpshot.setId(new Long(2));
	  lotteryUpshot.setAwardContent("mac电脑");
	  lotteryUpshot.setCreateBy(new Long("111"));
	  lotteryUpshot.setCreateTime(new Date());
	  lotteryUpshot.setDxcjId(new Long("1"));
	  lotteryUpshot.setGradeLevelName(new Long("1"));
	  lotteryUpshot.setMobile("15810615303");
	  lotteryUpshot.setName("一等奖");
	  lotteryUpshot.setState(0);
	  sms.addLotteryUpshot(lotteryUpshot);

  }
  
  public void QueryList(){
	  List<MasSmsLotteryUpshot> list=sms.queryLotteryUpshotList("");
	  for(MasSmsLotteryUpshot lottery:list){
		  System.out.println("id:"+lottery.getId());
	  }
  }
  
  public void testUpdate(){
	  MasSmsLotteryUpshot lotteryUpshot = new MasSmsLotteryUpshot();
	  lotteryUpshot.setId(new Long(2));
	  lotteryUpshot.setAwardContent("mac电脑11111111");
	  lotteryUpshot.setCreateBy(new Long("111111111111"));
	  lotteryUpshot.setCreateTime(new Date());
	  lotteryUpshot.setDxcjId(new Long("111111"));
	  lotteryUpshot.setGradeLevelName(new Long("1"));
	  lotteryUpshot.setMobile("15810615303");
	  lotteryUpshot.setName("一等奖11111111");
	  lotteryUpshot.setState(0);
	  int count=sms.updateLotteryUpshot(lotteryUpshot);
	  System.out.println(count);
  }
  
  public void testDelete(){
	  System.out.println("开始");
	  int count=sms.deleteLotteryUpshot("1");
	  System.out.println(count);
  }
  
  public static Test suite(){

      TestSuite test = new TestSuite("test QuestionDb");

      test.addTest(new SmsLotteryUpshotDaoTest("QueryList"));

      return test;

  }
  
  public SmsLotteryUpshotDaoTest(String m){
	  super(m);
  }
  
  public void tearDown(){
	  
	  context = null;
	  sms=null;
	  
  }
}
