package empp2.util;

import java.util.UUID;

public class UuidUtil {
    public static String getUuid(){
    	return UUID.randomUUID().toString().replaceAll("-","");
    }
    
    public static String getUuid(Long value){ 
    	StringBuilder builder = new StringBuilder("f5973d6212314");
    	builder.append(String.valueOf(value));
    	return builder.toString();
    }
    
    public static Long getValue(String uuid){
    	if( uuid == null || uuid.length() != 32){
    		return -1L;
    	}
    	Long result = 0L;
    	String tmp = uuid.substring(13);
    	try{
    		result = Long.parseLong(tmp);
    	}catch(Exception e){
    		result = Long.MIN_VALUE;
    	}
    	return result;
    }
}
