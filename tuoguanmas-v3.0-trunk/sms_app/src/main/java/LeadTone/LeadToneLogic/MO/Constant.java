package LeadTone.LeadToneLogic.MO;

import java.io.File;

import LeadTone.Utility;

public class Constant {

	public static final boolean ISSSL=true;
	
	public static final boolean ISTEXT = false;
	
	public static final String VERSION = "0";
	
	public static final String LANGUAGE = "default";
	
	public static final String CONFIG_PATH = Utility.getCefHome() + File.separator + "conf" + File.separator +

			"masService" +File.separator + "app-conf" +File.separator;
	
	public static final String SMS_FILE = "sms-config.xml";
	
	public static final String LOGNAME_CONFIG_FILE = "config.properties";
	
	public static final String LOG4J_CONFIG_FILE = "log4j.properties";
	
	
}
