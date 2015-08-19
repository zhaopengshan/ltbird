/**
 * Project Name: ws-server <br/>
 * File Name: DataBean.java <br/>
 * Package Name: com.changyuan.misc.bean <br/>
 * Date: 2015年8月17日上午6:38:34 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.model.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName: DataBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月17日 上午6:38:34 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class DataBean {
    // 将数组按单元插入数据库 返回插入行数
    public static int InsertTable(Connection conn, String tableName, String[] rows) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            StringBuffer row = new StringBuffer();
            int i;
            for (i = 0; i < rows.length - 1; i++) {
                row.append("'" + rows[i] + "'" + ",");
            }
            row.append("'" + rows[i] + "'");
            String sql = "INSERT INTO " + tableName + " VALUES (" + new String(row) + ")";
            int rowcount = stmt.executeUpdate(sql);
            stmt.close();
            return rowcount;
        } catch (SQLException e) {
            System.out.println("Insert Table " + tableName + " Error");
            e.printStackTrace();
            return -1;
        }
    }

    // 按条件删除 返回删除的行数
    public static int deleteRow(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // stmt.execute(sql);
            int rows = stmt.executeUpdate(sql);
            stmt.close();
            return rows;
        } catch (SQLException e) {
            System.out.println("delete sql = " + sql + " ERROR");
            e.printStackTrace();
            return -1;
        }
    }

    // 给定sql返回2唯数组结果
    public static String[][] getResultToArray(Connection conn, String sql) throws SQLException {
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(sql);
        if (DataBean.getRowCounts(rs) == 0)
            return null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int rows = DataBean.getRowCounts(rs);
        String[][] Information = new String[rows][rsmd.getColumnCount() - 1];
        while (rs.next()) {
            if (rs.getRow() > rows)
                break;
            for (int j = 0; j < rsmd.getColumnCount() - 1; j++) {
                Information[rs.getRow() - 1][j] = rs.getString(j + 2);// 我的数据表都有自动增长的ID 做主键 我不取它
                                                                      // getString从1开始取，第1字段为ID号，故+2
            }
        }
        rs.close();
        stmt.close();
        return Information;
    }

    public static int getRowCounts(ResultSet rs) {
        int counts = 0;
        try {
            while (rs.next()) {
                counts++;
            }
            rs.beforeFirst();
            return counts;
        } catch (Exception e) {
            System.out.println("getRowCounts ERROR");
            e.printStackTrace();
            return -1;
        }
    }

    public static Connection getConnection(String dbname, String user, String passWord) throws SQLException, Exception {
        Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        // 我用的MS SQL
        String url = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=" + dbname;
        Connection conn = DriverManager.getConnection(url, user, passWord);
        return conn;
    }
}
