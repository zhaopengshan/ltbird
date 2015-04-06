import empp2.cef.spring.MasBeanFactory;
import empp2.center.Center;
import empp2.config.SubsystemConfig;
//import empp2.util.JarTool;


public class EmppSmsApp {
	public static String[] args = null;
	/**
	 * @param args
	 */
	public static void main(String[] arg) {
		try {
//			JarTool tools = new JarTool(EmppSmsApp.class);
//			System.out.println(tools.getJarDir());
			String path = SubsystemConfig.getInstances().getProperty("configPath").trim();//"D:\\temp\\sms_empp\\src\\main\\resources\\";
			args = new String[3];
			args[0] = path + "applicationContext.xml";
			args[1] = path + "log4j.properties";
			args[2] = path + "subsystem.properties";

			MasBeanFactory.init(args[0]);

			Center.main(EmppSmsApp.args);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
