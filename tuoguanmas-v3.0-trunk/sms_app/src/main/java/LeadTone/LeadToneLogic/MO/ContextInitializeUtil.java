package LeadTone.LeadToneLogic.MO;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextInitializeUtil {

	protected static final Logger log = Logger
			.getLogger(ContextInitializeUtil.class);

	/*private static BeanFactory context;

	public static void setContext(BeanFactory ctx) {
		context = ctx;
	}

	public static BeanFactory getContext() {
		return context;
	}

	*//**
	 * 初始化ApplicationContext 需要将applicaitonContext.xml放在classpath下
	 *
	 *//*
	public static void initialize() {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		setContext(appContext);

	}

	*//**
	 * 初始化applicationContext
	 * @param applicationContext 文件路径
	 *//*

	public static void initialize(String applicationContext) {
		InputStream ins;
		try {
			ins = new FileInputStream(applicationContext);
			Resource resource = new InputStreamResource(ins);
			BeanFactory factory = new XmlBeanFactory(resource);
			setContext(factory);
		} catch (FileNotFoundException e) {
			log.info("ApplicationContext File not Found !");
			log.info(e.getMessage(), e);
			e.printStackTrace();
		} catch (BeansException e) {
			log.info("Initialize ApplicationContext Error! ");
			log.info(e.getMessage(), e);
		}catch (Exception e) {
			log.info("Some errors occured !");
			e.printStackTrace();
		}

	}
*/
	
	private static  ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ContextInitializeUtil.context = context;
	}
	
	public static void initialize() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		setContext(appContext);
	}
	
	public static void initialize(String applicationContext) {
		
		//ApplicationContext appContext = new FileSystemXmlApplicationContext(new String[]{applicationContext});
		ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{applicationContext});
		setContext(appContext);
	}
}
