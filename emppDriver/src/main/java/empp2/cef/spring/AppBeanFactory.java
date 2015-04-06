package empp2.cef.spring;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

public class AppBeanFactory extends MasBeanFactory
{
	String configFile;

	public AppBeanFactory(String configFile)
	{
		this.configFile = configFile;
		this.init();
	}

	public void init()
	{
		Resource is;
		try
		{
			is = new InputStreamResource(new FileInputStream(configFile));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e); 
		}
		setBeanFactory(new XmlBeanFactory(is));
	}

	public static BeanFactory initfileInJarfile(String filename)
	{
		MasBeanFactory masBeanFactory = new MasBeanFactory();
		masBeanFactory.setBeanFactory(new ClassPathXmlApplicationContext(filename).getBeanFactory());
		return masBeanFactory.getBeanFactory();
	}
	public static BeanFactory initByAbsoluteFile(String filename)
	{
		MasBeanFactory masBeanFactory = new MasBeanFactory();
		masBeanFactory.setBeanFactory(new FileSystemXmlApplicationContext(filename).getBeanFactory());
		return masBeanFactory.getBeanFactory();
	}
}