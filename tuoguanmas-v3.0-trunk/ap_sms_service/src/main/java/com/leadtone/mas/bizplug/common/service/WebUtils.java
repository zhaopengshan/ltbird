/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.common.service;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.leadtone.mas.bizplug.common.ApSmsConstants;

/**
 *
 * @author blueskybluesea
 */
public class WebUtils {
    private static ResourceBundle rb = ResourceBundle.getBundle("subsystem");
    public static String getPropertyByName(String propName){
    	String value;
    	try{
    		value = rb.getString(propName);
    	}catch(Exception e){
    		value = "";
    	}
        return value;
    }    
    
    /**
     * 判断是否为托管MAS
     * @return
     */
    public static boolean isHostMas(){
    	boolean isHostMas = false;
        if(!StringUtils.isBlank(WebUtils.getPropertyByName(ApSmsConstants.DELEGATEMAS))){
        	isHostMas = Boolean.valueOf(WebUtils.getPropertyByName(ApSmsConstants.DELEGATEMAS));                   
        }
    	return isHostMas;
    }
    
    /**
     * 获取扩展码生成方式　
     * @return 配置为user返回1 xxyy方式， 配置为operation返回2 ZXyyy方式
     */
    public static int getExtCodeStyle(){
    	int extCodeType = ApSmsConstants.USER_EXT_CODE_TYPE;
    	if( ApSmsConstants.OPERATION_EXT_CODE_TYPE_VALUE.equalsIgnoreCase(
    			WebUtils.getPropertyByName(ApSmsConstants.EXT_CODE_TYPE))){
    		extCodeType = ApSmsConstants.OPERATION_EXT_CODE_TYPE;
    	}
    	return extCodeType;
    }
    
    /**
     * 判断是否启用数据库接口
     * @return
     */
    public static boolean getDoSmsDbTask(){
    	String value = WebUtils.getPropertyByName("doSmsDbTask");
    	if( "true".equalsIgnoreCase(value)){
    		return true;
    	}
    	return false;
    }
}
