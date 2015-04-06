package empp2.center;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import empp2.Engine;
import empp2.Log;
import empp2.config.SubsystemConfig;



/**
 * 系统中心类，也是系统的根节点，由子类继承实现不同的工作模式
 * 具体工作模式的解释参考具体的子类描述
 */
public class Center extends Engine
{
	public static String log_file_name = "smsEmpp";
    /**
     * 系统是否需要关闭的标识
     */
    public static boolean m_bNeedTerminate = false;
    
    public Center( )
            throws IOException
    {
        super("Center");
    }

    /**
     * 装载系统类Center
     * @return 返回装载的Center对象
     */
    public static Center loadCenter()
    {
    	EMPPCenter emppCenter = null;
		try {
			emppCenter = new EMPPCenter();
		} catch (IOException e) {
			Log.log(e);
		}
    	return emppCenter;
    }


    /**
     * 表示log4j是否已初始化的标识，用于外部系统处理用户上行部分的日志初始化，外部系统采用了log4j日志系统，
     * 没有和本系统使用同样的日志系统
     */
    public static boolean _log4jConfiged = false;

    /**
     * 初始化外部系统的log4j
     * @param str log4j配置文件路径
     */
    public static void configLog4j(String str) {

        if (!_log4jConfiged) {
            PropertyConfigurator.configure(str);

            _log4jConfiged = true;
            Log.log("config log4j !", 1L);
        }
    }

    public static Center center = null;
    
    /**
     * 系统主进入点
     * @param args
     */
    public static void main(String args[])
    {
//    	String path = "D:\\temp\\sms_empp\\src\\main\\resources\\";
//		args = new String[3];
//		args[0] = path + "applicationContext.xml";
//		args[1] = path + "log4j.properties";
//		args[2] = path + "subsystem.properties";

//		MasBeanFactory.init(args[0]);
		
    	//判断系统启动时必须传入参数
        if(args.length < 1)
        {
            System.out.println("Usage : java LeadTone.Center.Center [config.xml] [applicationContext.xml] [log4j.properties]");
            return;
        }
        if(args.length >= 3){
//        	Properties properties = ClassLoaderUtils.getProperties(args[2]);
//        	log_file_name = properties.getProperty("log.MT.fileName", "smsEmpp");
        	log_file_name = SubsystemConfig.getInstances().getProperty("log.MT.fileName").trim();;
        }
        
        Log.open(log_file_name,"log");
        Log.setLog(-1L);
        
            //判断系统启动时必须的传入参数
            if(args.length != 3)
            {
                System.out.println("Usage : java LeadTone.Center.Center [config.xml] [applicationContext.xml] [log4j.properties]");
                return;
            }
                	
            //由于用于用户上行的外部系统使用了log4j的日志系统，因此在此读取外部系统所需的logj的配置文件
            File log4jConfigFile = new File(args[1]);//log4j
            if (!log4jConfigFile.exists())
            {
                Log.log("Center.main : log4j config file is not exist !", 0x2000000000000001L);
                return;
            }
            if (!log4jConfigFile.canRead())
            {
                Log.log("Center.main : fail to load log4j config file !", 0x2000000000000001L);
                return;
            }

            try{

            //初始化外部系统所需的log4j和Spring，并测试外部系统的Spring启动是否成功，
            //不成功的话则默认强制关闭处理用户上行的需要外部系统支持的功能
            Log.log("Center.main : Test Database Status Begin !", 0x2000000000000001L);
            configLog4j(log4jConfigFile.getPath());
            Log.log("Center.main : Test Database Status End !", 0x2000000000000001L);
            }catch(Exception e){
               Log.log("Center.main : Test Database Status Error, ResendEmn and LeadToneLogic Function has been canceled !", 0x2000000000000001L);
               e.printStackTrace();
            }
        
      //装载Center对象
        center = loadCenter();
        try
        { 

	       //启动系统，系统的启动方式为树形结构，启动主干程序后，程序将按照隶属关系，
	       //启动子线程，线程间也有检测功能，当某一子线程停止或异常将只重启该子线程，
	       //不影响系统整体运行
	       center.startup();
	       //等待线程启动完毕
	       Engine.wait(center);
        }catch(Exception e)
        {
           Log.log(e);
        }
    }

}
