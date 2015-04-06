package com.leadtone.mas.bizplug.smssend;

import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsAward;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsAwardDao;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsLotteryDao;

public class SmsAwardDaoTest extends TestCase {
  private ClassPathXmlApplicationContext context;
  private MasSmsAwardDao sms;
  public void setUp(){
	  context = new ClassPathXmlApplicationContext(
              new String[]{"applicationContext.xml"});
	  sms = (MasSmsAwardDao) context.getBean("masSmsAwardDao");
  } 
	
  public void testLotteryInsertDao(){
	  
	  MasSmsAward award = new MasSmsAward();
	  award.setId(new Long(4));
	  award.setAwardContent("内容");
	  award.setCreateBy(new Long(1));
	  award.setCreateTime(new Date());
	  award.setDxcjId(new Long(1));
	  award.setGradeLevelName("特等奖");
	  award.setModifyTime(new Date());
	  award.setOrderNumber(12);
	  award.setQuotaOfPeople(20);
	  sms.addAward(award);
  }
  
  public void delete(){
	  sms.deleteAward("1,2,3,4");
  }
  
  public void update(){
	  MasSmsAward award = new MasSmsAward();
	  award.setId(new Long(1));
	  award.setAwardContent("内容一");
	  award.setCreateBy(new Long(1));
	  award.setCreateTime(new Date());
	  award.setDxcjId(new Long(1));
	  award.setGradeLevelName("一等奖");
	  award.setModifyTime(new Date());
	  award.setOrderNumber(12);
	  award.setQuotaOfPeople(20);
	  sms.updateAward(award);
  }
  
  public void queryList(){
	  List<MasSmsAward> list=sms.queryAwardList("8130313492537528160");
	  for(MasSmsAward award:list){
		  System.out.println("id:"+award.getId());
	  }
  }
  
  public static Test suite(){

      TestSuite test = new TestSuite("test QuestionDb");

      test.addTest(new SmsAwardDaoTest("queryList"));

      return test;

  }
  
  public SmsAwardDaoTest(String m){
	  super(m);
  }
  
  public void tearDown(){
	  
	  context = null;
	  sms=null;
  }
}
