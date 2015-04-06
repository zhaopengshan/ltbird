package com.leadtone.driver.dataSource;

import org.apache.log4j.Logger;


public class BeforeAdvice {
	Logger logger = Logger.getLogger(BeforeAdvice.class);
    public void setLocalDataSource() {
    	DataSourceContextHolder.setDataSourceType("local");
    	logger.debug("设置数据源为local!");
    }
    public void setModemDataSource() {
    	DataSourceContextHolder.setDataSourceType("modem");
    	logger.debug("设置数据源为modem!");
    }
    
    public void setMandatoryDataSource(){
    	DataSourceContextHolder.setDataSourceType("mandatory");
    	logger.debug("设置数据源为mandatory!");
    }
}

