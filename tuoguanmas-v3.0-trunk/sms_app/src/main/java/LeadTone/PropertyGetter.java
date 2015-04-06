/*  
 *  This is a part of the ADC Platform.
 *  Copyright (C) 2006-2009 j2eebestpractice.cn Corporation
 *  All rights reserved.
 *
 *  Licensed under the j2eebestpractice.cn private License.
 *  
 *
 */
package LeadTone;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author wangwp
 * @version $Id: PropertyGetter.java 8525 2009-11-25 04:10:10Z wangweiping $
 * 
 */
public class PropertyGetter {
	private static final String GLOBAL_CONFIG_FILE = "config/config.properties";
	
	private static final Properties properties;

	static {
		properties =ClassLoaderUtils.getProperties(GLOBAL_CONFIG_FILE);//GlobalConfiguration.getInstance().getConfiguration(GLOBAL_CONFIG_FILE);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return getInt(key, -1);
	}

	/**
	 * 
	 * @param key
	 * @param _default
	 * @return
	 */
	public static int getInt(String key, int _default) {
		int value = _default;
		try{
			value = Integer.parseInt(properties.getProperty(key));
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static long getLong(String key) {
		return getLong(key, -1);
	}

	/**
	 * 
	 * @param key
	 * @param _default
	 * @return
	 */
	public static long getLong(String key, long _default) {
		Long value = _default;
		try{
			value = Long.parseLong(properties.getProperty(key));
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return getString(key, "");
	}

	/**
	 * 
	 * @param key
	 * @param _default
	 * @return
	 */
	public static String getString(String key, String _default) {
		String value = _default;
		try{
			String _value=properties.getProperty(key);
			if(_value!=null){
				value=_value;
			}
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static double getDouble(String key) {
		return getDouble(key, -1D);
	}

	/**
	 * 
	 * @param key
	 * @param _default
	 * @return
	 */
	public static double getDouble(String key, double _default) {
		Double value = _default;
		try{
			value =Double.parseDouble(properties.getProperty(key));
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static float getFloat(String key) {
		return getFloat(key, -1.0F);
	}

	/**
	 * 
	 * @param key
	 * @param _default
	 * @return
	 */
	public static float getFloat(String key, float _default) {
		Float value = _default;
		try{
			value =Float.parseFloat(properties.getProperty(key));
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static short getShort(String key) {
		return getShort(key, (short) -1);
	}

	/**
	 * 
	 * @param key
	 * @param _default
	 * @return
	 */
	public static short getShort(String key, short _default) {
		Short value = _default;
		try{
			value =Short.parseShort(properties.getProperty(key));
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}
	
	public static boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public static boolean getBoolean(String key, boolean _default) {
		boolean value = _default;
		try{
			value =Boolean.parseBoolean(properties.getProperty(key));
		}catch (Exception e) {
			// do nothing
		}
		
		return value;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (properties != null) {
			for (Enumeration en = properties.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				map.put(key, properties.getProperty(key));
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	/*public static String[] getStringArray(String key) {
		return getStringArray(key);
	}*/
}
