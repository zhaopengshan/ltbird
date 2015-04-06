package LeadTone.LeadToneLogic.MO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.leadtone.gegw.entity.cef.Cef;
import com.leadtone.gegw.entity.pushuser.CefUser;
import com.leadtone.gegw.smsservice.exception.SmsGatewayException;
import com.leadtone.gegw.smsservice.service.ErrorCode;
import com.leadtone.gegw.smsservice.service.ISmsService;
import com.leadtone.gegw.smsservice.service.impl.ThrowExceptionUtil;
import com.leadtone.gegw.userservice.IUserService;
import com.leadtone.gegw.util.Common;

import LeadTone.LeadToneLogic.MO.BaseSmsServiceMO;
import LeadTone.LeadToneLogic.MO.Constant;
import LeadTone.LeadToneLogic.MO.ContextInitializeUtil;

public class SmsServiceMO extends HibernateDaoSupport {

	protected static final Logger log = Logger.getLogger(SmsServiceMO.class);
	
	ThrowExceptionUtil teu = new ThrowExceptionUtil();

	BaseSmsServiceMO bss = new BaseSmsServiceMO();

	ISmsService smsService = (ISmsService) ContextInitializeUtil.getContext()
			.getBean("smsService");
	IUserService iUserService = (IUserService) ContextInitializeUtil.getContext()
	.getBean("userService");

	public String sendSetting(String phonenumber) {
		log.info("======================sendSetting For App Begin===================");
		try {
			List cefUserList = bss.getCefUserList(phonenumber);
			for (Iterator it = cefUserList.iterator(); it.hasNext();) {
				CefUser cefuser = (CefUser) it.next();
				String cid = cefuser.getCef().getCid();
				smsService.sendSetting(cid, phonenumber, Constant.ISSSL, null);
			}
			log.info("======================sendSetting For App End=================");
			return Common.logAndReturn(ErrorCode.NewErrorCode000, "SMS002", null);
		} catch (SmsGatewayException se) {
			return Common.logAndReturn(se.getErrorCode(), "SMS002", null);
		} catch (Exception e) {
			teu.logException(e);
			return Common.logAndReturn(ErrorCode.NewErrorCode100, "SMS002", null);
		}
	}

	public String sendCA(String phonenumber) {
		log.info("======================sendCA For App Begin=================");
		String urltype = "CA";
		try {
			String cid = bss.getCid(phonenumber);
			log.info("cid = " + cid);
			smsService.sendSi(cid, phonenumber, urltype, Constant.ISTEXT,
					Constant.VERSION, Constant.LANGUAGE, null);
			log.info("======================sendCA For App End=================");
			return Common.logAndReturn(ErrorCode.NewErrorCode000, "SMS004", null);
		} catch (SmsGatewayException se) {
			return Common.logAndReturn(se.getErrorCode(), "SMS004", null);
		} catch (Exception e) {
			teu.logException(e);
			return Common.logAndReturn(ErrorCode.NewErrorCode100, "SMS004", null); 
		}
	}

	public String sendSoftWare(String phonenumber) {
		log.info("======================sendSoftWare For App Begin=================");
		String urltype = "DEVICE";
		try {
			String cid = bss.getCid(phonenumber);
			log.info("cid = " + cid);
			smsService.sendSi(cid, phonenumber, urltype, Constant.ISTEXT,
					Constant.VERSION, Constant.LANGUAGE, null);
			log.info("======================sendSoftWare For App End=================");
			return Common.logAndReturn(ErrorCode.NewErrorCode000, "SMS004", null);
		} catch (SmsGatewayException se) {
			return Common.logAndReturn(se.getErrorCode(), "SMS004", null);
		} catch (Exception e) {
			teu.logException(e);
			return Common.logAndReturn(ErrorCode.NewErrorCode100, "SMS004", null); 
		}
	}
/**
 * 增加发送扩展Setting
 */
	public String sendExSendSetting(String msgcontent,String mobile){
		
		log.info("======================sendExSendSetting For App Begin===================");		
		String platforms="";
		String versions="";
		log.info("================msgcontent============"+msgcontent);
		int msgcontentsigncount=msgcontent.split("=").length;
		int position_pt=msgcontent.toUpperCase().indexOf("PT");
		int position_ver=msgcontent.toUpperCase().indexOf("VER");
		int message_length=msgcontent.length();	
		
		if((msgcontentsigncount==3)&&(position_pt !=-1)&&(position_ver!=-1)){		
			
			int first_sign=msgcontent.indexOf("=");
			int last_sign=msgcontent.lastIndexOf("=");
		if(position_pt < position_ver){
			//during normal state		
			platforms=msgcontent.substring(first_sign+1, position_ver).trim();		
			versions=msgcontent.substring(last_sign+1,message_length).trim();		
		}else{
			//during "ver" and "pt" position reverse			
			platforms=msgcontent.substring(last_sign+1,message_length).trim();
			versions=msgcontent.substring(first_sign+1, position_pt).trim();					
		 }
		}
		List cefuserlist=iUserService.getCefuserList(mobile);	
		ArrayList smslist=new ArrayList();				
		if(cefuserlist!=null&&(cefuserlist.size()>0)){
		for(int h=0;h<cefuserlist.size();h++){
			CefUser cefuser=(CefUser)cefuserlist.get(h);
			Cef cef=cefuser.getCef();				
			smslist.add(cef.getCid());	
			log.info("cid = " + cef.getCid());
		 }	
		}
		
		try {
			smsService.sendConfigureSetting(smslist, mobile,platforms,versions, null);	
			log.info("======================sendExSendSetting For App End=================");
			return Common.logAndReturn(ErrorCode.NewErrorCode000, "SMS002", null);
		} catch (SmsGatewayException se) {
			return Common.logAndReturn(se.getErrorCode(), "SMS002", null);
		} catch (Exception e) {
			teu.logException(e);
			return Common.logAndReturn(ErrorCode.NewErrorCode100, "SMS002", null);
		}
	}
}
