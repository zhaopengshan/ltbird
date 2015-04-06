package com.leadtone.mas.util.spring;

import java.io.File;  
/** 
 * 获取打包后jar的路径信息 
 * @author Administrator 
 *  2011-01-16 13:53:12 
 */  
public class JarTool {
	private File file;
	@SuppressWarnings("rawtypes")
	public JarTool(Class cl) {  
        //关键是这行...  
        String path = cl.getProtectionDomain().getCodeSource()  
                .getLocation().getFile();  
        try{  
            path = java.net.URLDecoder.decode(path, "UTF-8");//转换处理中文及空格  
            file = new File(path);
        }catch (java.io.UnsupportedEncodingException e){  
           // return null;  
        }  
        //return new File(path);  
    } 
	
	//获取jar绝对路径  
	public String getJarPath(){  
	    //File file = getFile();  
	    if(file==null)return null;  
	     return file.getAbsolutePath();  
	}  
	//获取jar目录  
	public String getJarDir() {  
	  //  File file = getFile();  
	    if(file==null)return null;  
	     return file.getParent();  
	}  
	//获取jar包名  
	public String getJarName() {  
	  //  File file = getFile();  
	    if(file==null)return null;  
	    return file.getName();  
	}
}  