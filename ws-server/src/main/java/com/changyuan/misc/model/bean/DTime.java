/**
 * Project Name: ws-server <br/>
 * File Name: DTime.java <br/>
 * Package Name: com.changyuan.misc.bean <br/>
 * Date: 2015年8月17日上午9:33:32 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.model.bean;

import org.joda.time.DateTime;

/**
 * ClassName: DTime <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月17日 上午9:33:32 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class DTime {
    public static final String Simple_DateTime_Format = "yyyy-MM-dd HH:mm:ss";
    private DateTime dateTime;

    public DTime() {
        this.dateTime = new DateTime();
    }

    public String getDTime() {
        return dateTime.toString(DTime.Simple_DateTime_Format);
    }
}
