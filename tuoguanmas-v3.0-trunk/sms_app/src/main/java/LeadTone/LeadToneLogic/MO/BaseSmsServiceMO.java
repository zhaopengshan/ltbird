package LeadTone.LeadToneLogic.MO;

import java.util.List;

import org.apache.log4j.Logger;

import com.leadtone.gegw.entity.pushuser.CefUser;
import com.leadtone.gegw.entity.pushuser.PushUser;
import com.leadtone.gegw.smsservice.exception.SmsGatewayException;
import com.leadtone.gegw.smsservice.service.ErrorCode;
import com.leadtone.gegw.smsservice.service.impl.ThrowExceptionUtil;
import com.leadtone.gegw.userservice.IUserService;

public class BaseSmsServiceMO {

	protected static final Logger log = Logger.getLogger(BaseSmsServiceMO.class);
	ThrowExceptionUtil teu = new ThrowExceptionUtil();
	
	public List getCefUserList(String phonenumber) throws SmsGatewayException {
		try {
			IUserService userService = (IUserService) ContextInitializeUtil
					.getContext().getBean("userService");
			PushUser pushUser = new PushUser();
			pushUser.setPushUserId(phonenumber);
			PushUser pushuser = userService.getPushUser(pushUser);
			List cefUserList = pushuser.getCefUsersList();
			return cefUserList;
		} catch (Exception e) {
			teu.throwSmsServiceException(ErrorCode.NewErrorCode721, e);
			return null;
		}
	}

	public String getCid(String phonenumber) throws SmsGatewayException {
		try {
			IUserService userService = (IUserService) ContextInitializeUtil
					.getContext().getBean("userService");
			PushUser pushUser = new PushUser();
			pushUser.setPushUserId(phonenumber);
			PushUser pushuser = userService.getPushUser(pushUser);
			CefUser cefUser = (CefUser) pushuser.getCefUsersList().iterator()
					.next();
			String cid = cefUser.getCef().getCid();
			return cid;
		} catch (Exception e) {
			teu.throwSmsServiceException(ErrorCode.NewErrorCode721, e);
			return null;
		}
		
	}
}
