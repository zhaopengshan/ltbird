package empp2.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ChangJun
 * 
 *         读取子系统通用配置文件工具类
 */
public class SubsystemConfig {

	private static SubsystemConfig subsystemConfig = null;
	private Properties properties = null;

	private SubsystemConfig() {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("subsystem.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized SubsystemConfig getInstances() {
		if (subsystemConfig == null) {
			subsystemConfig = new SubsystemConfig();
		}

		return subsystemConfig;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public int getIntProperty(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}
}
