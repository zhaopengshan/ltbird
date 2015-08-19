/**
 * Project Name: ws-server <br/>
 * File Name: Anatomy.java <br/>
 * Package Name: com.changyuan.misc.bean <br/>
 * Date: 2015年8月17日上午6:34:28 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.model.bean;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletInputStream;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * ClassName: Anatomy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月17日 上午6:34:28 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class Anatomy {
    private String TransactionID = "";// 该消息编号
    private String MsgType = "";// 消息类型
    private String LinkID = "";// 临时订购关系的事务ID
    private String Version = "";// 该接口消息的版本号
    private String ActionID = "";// 服务状态治理动作代码
    private String ActionReasonID = "";// 产生服务状态治理动作原因的代码
    private String SPID = "";// 企业代码
    private String SPServiceID = "";// 服务代码
    private String AccessMode = "";// 访问模式
    private String FeatureStr = "";// 服务订购参数
    private String Send_DeviceType = "";// 发送方地址
    private String Send_DeviceID = "";
    private String Dest_DeviceType = "";// 接收方地址
    private String Dest_DeviceID = "";
    private String Fee_UserIDType = "";// 计费用户标识
    private String Fee_MSISDN = "";
    private String Fee_PseudoCode = "";// 记费用户MID
    private String Dest_UserIDType = "";
    private String Dest_MSISDN = "";
    private String Dest_PseudoCode = "";
    private String hRet = "";// 返回值
    private ServletInputStream in;
    private PrintWriter out;
    private Document document;
    private Connection con;
    private String[][] relation;// 已存在的订购关系
    private String[][] pause;// 已经暂停的订购关系
    String delete_Sql_Relation;
    String delete_Sql_Pause;
    String relation_sql;
    String pause_sql;

    public Anatomy(ServletInputStream In, PrintWriter Out, Connection con) {
        try {
            this.in = In;
            this.out = Out;
            this.con = con;
            this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.in);
            getReqInfo();// 将各个字段付值
            if (Fee_UserIDType.equals("1"))// Fee_UserIDType为1用MID，2为违代码
            {
                relation_sql = "select * from Relation where SPServiceID = '" + SPServiceID + "' and Fee_MSISDN ='"
                        + Fee_MSISDN + "'";
                pause_sql = "select * from Pause where SPServiceID = '" + SPServiceID + "' and Fee_MSISDN ='"
                        + Fee_MSISDN + "'";
                delete_Sql_Relation = "DELETE FROM Relation WHERE SPServiceID = '" + SPServiceID
                        + "' and Fee_MSISDN ='" + Fee_MSISDN + "'";
                delete_Sql_Pause = "DELETE FROM Pause WHERE SPServiceID = '" + SPServiceID + "' and Fee_MSISDN ='"
                        + Fee_MSISDN + "'";
            } else {
                relation_sql = "select * from Relation where SPServiceID = '" + SPServiceID + "' and Fee_PseudoCode ='"
                        + Fee_PseudoCode + "'";
                pause_sql = "select * from Pause where SPServiceID = '" + SPServiceID + "' and Fee_PseudoCode ='"
                        + Fee_PseudoCode + "'";
                delete_Sql_Relation = "DELETE FROM Relation WHERE SPServiceID = '" + SPServiceID
                        + "' and Fee_PseudoCode ='" + Fee_PseudoCode + "'";
                delete_Sql_Pause = "DELETE FROM Pause WHERE SPServiceID = '" + SPServiceID + "' and Fee_PseudoCode ='"
                        + Fee_PseudoCode + "'";
            }
            relation = DataBean.getResultToArray(con, relation_sql);// 得到当前的一该业务已订购的关系
            System.out.print("Reveice ");
            String[] InfoArray = this.getInfoArray();// 将字段组合成数组
            DataBean.InsertTable(con, "AllReceive", InfoArray);// 插入同步数据库做记录
            System.out.println();
            this.sendBack(this.getHRet());// 返回状态值
        } catch (Exception e) {
            this.sendBack("1");// 未知错误
            e.printStackTrace();
        }
    }

    private void getReqInfo()// 将各字段付值
    {
        TransactionID = getNodeValue("TransactionID", 0);
        MsgType = getNodeValue("MsgType", 0);
        LinkID = getNodeValue("LinkID", 0);
        Version = getNodeValue("Version", 0);
        ActionID = getNodeValue("ActionID", 0);
        ActionReasonID = getNodeValue("ActionReasonID", 0);
        SPID = getNodeValue("SPID", 0);
        SPServiceID = getNodeValue("SPServiceID", 0);
        AccessMode = getNodeValue("AccessMode", 0);
        FeatureStr = getNodeValue("FeatureStr", 0);
        Send_DeviceType = getNodeValue("DeviceType", 0);
        Send_DeviceID = getNodeValue("DeviceID", 0);
        Dest_DeviceType = getNodeValue("DeviceType", 1);
        Dest_DeviceID = getNodeValue("DeviceID", 1);
        Fee_UserIDType = getNodeValue("UserIDType", 0);
        Fee_MSISDN = getNodeValue("MSISDN", 0);
        Fee_PseudoCode = getNodeValue("PseudoCode", 0);
        Dest_UserIDType = getNodeValue("UserIDType", 1);
        Dest_MSISDN = getNodeValue("MSISDN", 1);
        Dest_PseudoCode = getNodeValue("PseudoCode", 1);
    }

    private String getNodeValue(String TagName, int index) {
        if (document.getElementsByTagName(TagName) != null
                && document.getElementsByTagName(TagName).item(index) != null
                && document.getElementsByTagName(TagName).item(index).getFirstChild() != null) {
            String value = document.getElementsByTagName(TagName).item(index).getFirstChild().getNodeValue();
            System.out.print(" " + TagName + " = " + value);
            return value;
        } else {
            System.out.print(" " + TagName + " = null");
            return "";
        }
    }

    private String getHRet() {
        try {
            if (!MsgType.equalsIgnoreCase("SyncOrderRelationReq")) {
                return "4000";// 无效的MsgType
            } else if ((Integer.parseInt(ActionReasonID) > 4) || (Integer.parseInt(ActionReasonID) < 1)) {
                return "4002";// 无效的ActionReasonID
            } else if ((Integer.parseInt(AccessMode) > 3) || (Integer.parseInt(AccessMode) < 1)) {
                return "4006";// 无效的AccessMode
            } else if ((Fee_MSISDN == null) && (Fee_PseudoCode == null)) {
                return "1";
            }
            int id = Integer.parseInt(ActionID);
            switch (id)
            // relation==null 该消息在关系表无记录 pause==null
            // 该消息在暂停表无记录
            {
            case 1: {
                if (relation == null) {
                    String[] InfoArray = this.getInfoArray();// 订购成功
                    // 插入关系表
                    DataBean.InsertTable(con, "Relation", InfoArray);// 插入同步数据库做记录
                    return "0";
                } else {
                    return "4007";// Misc同步开通服务,但sp端以存在订购关系，且状态为开通
                }
            }
            case 2: {
                if (relation == null) {
                    return "4011";// Misc同步停止服务,但sp端不存在订购关系
                } else if (relation != null) {
                    DataBean.deleteRow(con, delete_Sql_Relation);
                    return "0";
                } else
                    return "1";
            }
            case 3: {
                pause = DataBean.getResultToArray(con, pause_sql);
                if ((relation == null) && (pause == null)) {
                    return "4016";// Misc平台激活服务,但SP端不存在订购关系
                } else if ((relation != null) && (pause == null)) {
                    return "4015";// Misc平台激活服务,但SP端已存在订购关系,切状态为开通
                } else if (pause != null) {
                    DataBean.InsertTable(con, "Relation", pause[0]);
                    DataBean.deleteRow(con, delete_Sql_Pause);
                    return "0";// 成功
                } else
                    return "1";
            }
            case 4: {
                pause = DataBean.getResultToArray(con, pause_sql);
                if ((relation == null) && (pause == null)) {
                    return "4013";// Misc平台暂停服务,但SP端不存在订购关系
                } else if (pause != null) {
                    return "4014";// Misc平台暂停服务,但SP端存在订购关系,//切状态为暂停
                } else if ((relation != null) && (pause == null)) {
                    DataBean.InsertTable(con, "Pause", relation[0]);
                    DataBean.deleteRow(con, delete_Sql_Relation);
                    return "0";
                } else
                    return "1";
            }
            default: {
                return "4001";
            }
            }
        } catch (SQLException e) {
            return "1";// 未知错误
        }
    }

    private void sendBack(String hRet) {
        out.println("");
        out.println("");
        out.println("");
        out.println("" + TransactionID + "");
        out.println("");
        out.println("");
        out.println("");
        out.println("1.5.0");
        out.println("SyncOrderRelationResp");
        out.println("" + hRet + "");
        out.println("");
        out.println("");
        out.println("");
        String[] sendInfo = { TransactionID, hRet, new DTime().getDTime() };
        System.out.println("Send TransactionID = " + TransactionID + " hRet = " + hRet);
        DataBean.InsertTable(con, "AllSend", sendInfo);// 将发送数据插入发送记录表
        out.close();
    }

    private String[] getInfoArray() {
        String[] InfoArray = { TransactionID, MsgType, LinkID, Version, ActionID, ActionReasonID, SPID, SPServiceID,
                AccessMode, FeatureStr, Send_DeviceType, Send_DeviceID, Dest_DeviceType, Dest_DeviceID, Fee_UserIDType,
                Fee_MSISDN, Fee_PseudoCode, Dest_UserIDType, Dest_MSISDN, Dest_PseudoCode, new DTime().getDTime() };
        return InfoArray;
    }
}
