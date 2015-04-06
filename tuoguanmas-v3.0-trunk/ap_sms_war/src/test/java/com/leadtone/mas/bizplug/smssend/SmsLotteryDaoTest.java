package com.leadtone.mas.bizplug.smssend;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsLotteryDao;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

public class SmsLotteryDaoTest extends TestCase {
  private ClassPathXmlApplicationContext context;
  private MasSmsLotteryDao sms;
  public void setUp(){
	  context = new ClassPathXmlApplicationContext(
              new String[]{"applicationContext.xml"});
	  sms=(MasSmsLotteryDao) context.getBean("masSmsLotteryDao");
  } 

  public void LotteryInsertDao(){
	  
	  MasSmsLottery lottery = new MasSmsLottery();
	  lottery.setId(new Long(4));
	  lottery.setTitle("title");
	  lottery.setTos("15810615303");
	  lottery.setReadySendTime(new Date());
	  lottery.setEndTime(new Date());
	  lottery.setCreateBy(new Long(1231));
	  lottery.setContent("ok");
	  lottery.setCommitTime(new Date());
	  lottery.setBeginTime(new Date());
	  sms.addLottery(lottery);

  }
  
  public void testQueryList(){
	  List<MasSmsLottery> list=sms.queryLotteryById(new Long("8130200243637940220"),2);
	  for(MasSmsLottery lottery:list){
		  System.out.println("id:"+lottery.getId());
		  System.out.println("tos:"+lottery.getTos());
		  System.out.println("title:"+lottery.getTitle());
	  }
  }
  
  public void queryLotteryByIsLottery(){
	  	Map<String,Object> param=new HashMap<String,Object>();
		param.put("title", "年会");
		param.put("loginAccount", "admin");
		param.put("tos", "15032641127");
	  List<MasSmsLottery> list=sms.queryLotteryByIsLottery(param);
	  for(MasSmsLottery lottery:list){
		  System.out.println("id:"+lottery.getId());
		  System.out.println("tos:"+lottery.getTos());
		  System.out.println("title:"+lottery.getTitle());
	  }
  }
  public void queryLotteryByIsSend(){
	  	Map<String,Object> param=new HashMap<String,Object>();
		param.put("title", "1斯");
		param.put("loginAccount", "admin");
		param.put("tos", null);
	  List<MasSmsLottery> list=sms.queryLotteryByIsSend(param);
	  for(MasSmsLottery lottery:list){
		  System.out.println("id:"+lottery.getId());
		  System.out.println("tos:"+lottery.getTos());
		  System.out.println("title:"+lottery.getTitle());
	  }
  }
  public void queryLotteryByNoSend(){
	  	Map<String,Object> param=new HashMap<String,Object>();
		param.put("title", "3423");
		param.put("loginAccount", "admin");
		param.put("tos", "13501180501");
	  List<MasSmsLottery> list=sms.queryLotteryByNoSend(param);
	  for(MasSmsLottery lottery:list){
		  System.out.println("id:"+lottery.getId());
		  System.out.println("tos:"+lottery.getTos());
		  System.out.println("title:"+lottery.getTitle());
	  }
  }
  
  public void testUpdate(){
	  MasSmsLottery lottery = new MasSmsLottery();
	  lottery.setId(new Long(1));
	  lottery.setTitle("titletitletitle");
	  lottery.setTos("15810615303");
	  lottery.setReadySendTime(new Date());
	  lottery.setEndTime(new Date());
	  lottery.setCreateBy(new Long(1231));
	  lottery.setContent("333333333");
	  lottery.setCommitTime(new Date());
	  lottery.setBeginTime(new Date());
	  int count=sms.updateLottery(lottery);
	  System.out.println(count);
  }
  
  public void testDelete(){
	  System.out.println("开始");
	  int count=sms.deleteLottery("1,4");
	  System.out.println(count);
  }
  
  public void replySmsLottery(){
	Map<String,Object> param=new HashMap<String,Object>();
	param.put("ReplyNum", "15032641127");
	param.put("replyCode", "001");
	sms.replySmsLottery(param);
  }
  public static Test suite(){

      TestSuite test = new TestSuite("test QuestionDb");

      test.addTest(new SmsLotteryDaoTest("replySmsLottery"));
//      test.addTest(new SmsLotteryDaoTest("queryLotteryByIsSend"));
//      test.addTest(new SmsLotteryDaoTest("queryLotteryByIsLottery"));

      return test;

  }
  
  public SmsLotteryDaoTest(String m){
	  super(m);
  }
  
  public void tearDown(){
	  
	  context = null;
	  sms=null;
	  
  }
}
