package empp2.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 *
 * 
 * @author   : liuenhuai 
 * @Corp     : leadtone
 * @datetime : 2011-4-15
 */
public final class ClassLoaderUtils {
	private static final String CONFIG = "properties/subsystem.properties";

	private static Properties EMPTY_PROPERTIES = new Properties();

	// 1classLoader-->inputStream-->properties-->reader-->list
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static Class loadClass(String name) {
		try {
			return getClassLoader().loadClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getInstance(String name) {
		try {
			return loadClass(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static URL getUrl(String name) {
		return getClassLoader().getResource(name);
	}

	public static String getPath(String name) {
		return getUrl(name).getPath();
	}

	public static InputStream getStream(String config) {
		if (config == null || "".equals(config)) {
			config = CONFIG;
		}
		InputStream is = getClassLoader().getResourceAsStream(config);
		return is;
	}

	public static Properties getProperties(String config) {
		Properties props = new Properties();
		try {
			InputStream is = getStream(config);
			if (is == null) {
				return EMPTY_PROPERTIES;
			}
			props.load(is);
		} catch (IOException e) {
			return EMPTY_PROPERTIES;
		}
		return props;
	}

	/**
	 * <p>
	 * intro: 
	 * </p>
	 * @param config
	 * 			
	 * @param property
	 * 			
	 * @param defaultValue
	 * 			
	 * @return
	 */
	public static String getPropertyValue(String config, String property,
			String defaultValue) {
		String value = (String) getProperties(config).get(property);
		if (StringUtils.isBlank(value)) {
			if (StringUtils.isNotBlank(defaultValue)) {
				return defaultValue;
			} else {
				return "";
			}
		}
		return value;
	}

	public static Reader getReader(String config) {
		InputStream is = getStream(config);
		if (is == null) {
			return null;
		}
		return new BufferedReader(new InputStreamReader(is));
	}

	
	public static List<String> getList(String config) {
		List<String> list = new ArrayList<String>();
		BufferedReader reader = (BufferedReader) getReader(config);
		String temp = "";
		try {
			while ((temp = reader.readLine()) != null) {
				list.add(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
	
	public static Map<String, Object> getMap(String config) {
		Properties props = getProperties(config);
		Map<String, Object> map = new HashMap<String, Object>();
		if (props != null) {
			for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				map.put(key, props.getProperty(key));
			}
		}
		return map;
	}
	
	public static Map<String, Object> getMap() {
		return getMap(CONFIG);
	}
	
	public static void main(String[] args) {
		System.out.println(ClassLoaderUtils.getClassLoader());
		System.out.println(getProperties(""));
		System.out.println(getList(""));
		System.out.println(getUrl(CONFIG));
		System.out.println(getPath(CONFIG));
		System.out
				.println(getInstance("com.leadtone.blog.utils.ClassLoaderUtils"));
	}

}
