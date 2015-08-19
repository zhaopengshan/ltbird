/**
 * Project Name:ws-server <br/>
 * File Name:JodaDateTime2DateTypeHandler.java <br/>
 * Package Name:com.changyuan.misc.utils <br/>
 * Date:2015年8月18日下午5:36:27 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.utils.typehandler;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

/**
 * ClassName:JodaDateTime2DateTypeHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月18日 下午5:36:27 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class JodaDateTime2DateTypeHandler extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        DateTime dateTime = (DateTime) parameter;
        ps.setDate(i, new Date(dateTime.getMillis()));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        DateTime dateTime = null;
        Date date = rs.getDate(columnName);
        if (date != null) {
            dateTime = new DateTime(date.getTime());
        }
        return dateTime;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        DateTime dateTime = null;
        Date date = cs.getDate(columnIndex);
        if (date != null) {
            dateTime = new DateTime(date.getTime());
        }
        return dateTime;
    }

    // @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        DateTime dateTime = null;
        Date date = rs.getDate(columnIndex);
        if (date != null) {
            dateTime = new DateTime(date.getTime());
        }
        return dateTime;
    }
}
