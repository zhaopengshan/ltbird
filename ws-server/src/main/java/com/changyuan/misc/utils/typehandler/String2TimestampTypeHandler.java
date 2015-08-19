/**
 * Project Name:ws-server <br/>
 * File Name:String2TimestampTypeHandler.java <br/>
 * Package Name:com.changyuan.misc.utils <br/>
 * Date:2015年8月18日下午5:38:31 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.utils.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

import com.changyuan.misc.utils.DateUtils;

/**
 * ClassName:String2TimestampTypeHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月18日 下午5:38:31 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class String2TimestampTypeHandler extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        String timeStr = parameter.toString();
        ps.setTimestamp(i, new Timestamp(DateUtils.str2Date(timeStr, DateUtils.PATTERN_YYYYMMDDHHMMSS).getTime()));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String timeStr = null;
        Timestamp timestamp = rs.getTimestamp(columnName);
        if (timestamp != null) {
            timeStr = DateUtils.date2Str(new DateTime(timestamp.getTime()).toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS);
        }
        return timeStr;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String timeStr = null;
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (timestamp != null) {
            timeStr = DateUtils.date2Str(new DateTime(timestamp.getTime()).toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS);
        }
        return timeStr;
    }

    // @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String timeStr = null;
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        if (timestamp != null) {
            timeStr = DateUtils.date2Str(new DateTime(timestamp.getTime()).toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS);
        }
        return timeStr;
    }
}
