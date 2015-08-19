/**
 * Project Name: ws-server <br/>
 * File Name: AnalysisXML.java <br/>
 * Package Name: com.changyuan.misc.utils <br/>
 * Date: 2015年8月17日上午9:47:41 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changyuan.misc.model.bean.ProvisionBean;


/**
 * ClassName: AnalysisXML <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月17日 上午9:47:41 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class AnalysisXML {
    private static Logger logger = LoggerFactory.getLogger(AnalysisXML.class);

    /**
     * 解析返回的XML信息
     * 
     * @param XMLData
     */
    public static ProvisionBean getXmlResultt(String XMLData) {
        ProvisionBean provision = new ProvisionBean();
        // File f = null;
        Reader xmlReader = null;
        try {
            org.jdom.input.SAXBuilder sb = new org.jdom.input.SAXBuilder();
            org.jdom.Document doc = null;
            // File ffFile = new File("E://temp");
            // if (!ffFile.exists()) {
            // ffFile.mkdir();
            // }
            // f = new File("E://temp//" + System.currentTimeMillis() + new Random().nextInt(100000) + ".xml");
            // if (f.exists()) {
            // f.delete();
            // }
            // f.createNewFile();
            // RandomAccessFile ra = new RandomAccessFile(f, "rw");
            // ra.writeBytes(XMLData);
            // ra.close();
            // doc = sb.build(f);// 读取PROVISION.XML文件，SB和DOC的使用方法不是很明白；
            xmlReader = new StringReader(XMLData);
            doc = sb.build(xmlReader);
            // 以下是读取PROVISION.XML的信息，解释XML文件的各部分定义，和读取数值到变量里。
            org.jdom.Element root = doc.getRootElement();
            // 读取header信息；
            Element header = (Element) XPath.selectSingleNode(root, "/SOAP-ENV:Envelope/SOAP-ENV:Header");
            List ns = header.getChildren();
            for (Iterator i = ns.iterator(); i.hasNext();) {
                Object n = i.next();
                if (n instanceof Element) {
                    Element e = (Element) n;
                    if (e.getName().equals("TransactionID")) {
                        provision.setTransactionID(e.getTextNormalize());
                    }
                }
            }
            // 读取body信息；
            Element SyncOrderRelationReq = null;
            Element body = (Element) XPath.selectSingleNode(root, "/SOAP-ENV:Envelope/SOAP-ENV:Body");
            ns = body.getChildren();
            for (Iterator i = ns.iterator(); i.hasNext();) {
                Object n = i.next();
                if (n instanceof Element) {
                    Element e = (Element) n;
                    if (e.getName().equals("SyncOrderRelationReq")) {
                        SyncOrderRelationReq = e;
                    }
                }
            }
            // 取得数据包的所有孩子。
            if (SyncOrderRelationReq != null) {
                ns = SyncOrderRelationReq.getChildren();
                for (Iterator i = ns.iterator(); i.hasNext();) {
                    Object n = i.next();
                    if (n instanceof Element) {
                        Element e = (Element) n;
                        // ------------------------------------
                        if (e.getName().equals("FeeUser_ID")) {
                            ns = e.getChildren();
                            for (Iterator j = ns.iterator(); j.hasNext();) {
                                Object k = j.next();
                                if (k instanceof Element) {
                                    Element eU = (Element) k;
                                    if (eU.getName().equals("MSISDN")) {
                                        provision.setMSISDN_U(eU.getText());
                                    }
                                }
                            }
                        }
                        if (e.getName().equals("DestUser_ID")) {
                            ns = e.getChildren();
                            for (Iterator j = ns.iterator(); j.hasNext();) {
                                Object k = j.next();
                                if (k instanceof Element) {
                                    Element eU = (Element) k;
                                    if (eU.getName().equals("MSISDN")) {
                                        provision.setMSISDN_D(eU.getText());
                                    }
                                }
                            }
                        }
                        // ------------------------------------
                        if (e.getName().equals("Version")) {
                            provision.setVersion(e.getTextNormalize());
                        }
                        if (e.getName().equals("MsgType")) {
                            provision.setMsgType(e.getTextNormalize());
                        }
                        if (e.getName().equals("LinkID")) {
                            provision.setLinkID(e.getTextNormalize());
                        }
                        if (e.getName().equals("ActionID")) {
                            provision.setActionID(Integer.valueOf(e.getTextNormalize()));
                        }
                        if (e.getName().equals("ActionReasonID")) {
                            provision.setActionReasonID(Integer.valueOf(e.getTextNormalize()));
                        }
                        if (e.getName().equals("SPID")) {
                            provision.setSPID(e.getTextNormalize());
                        }
                        if (e.getName().equals("SPServiceID")) {
                            provision.setSPServiceID(e.getTextNormalize());
                        }
                        if (e.getName().equals("FeatureStr")) {
                            provision.setFeatureStr(e.getTextNormalize());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("同步MISC请求，解析出错!");
        } finally {
            // if (f != null) {
            // f.delete();
            // }
            if (xmlReader != null) {
                try {
                    xmlReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return provision;
    }

    /**
     * 模拟获得请求的XML
     * 
     * @return
     */
    public static String getXml() {
        return "<?xml version='1.0' encoding='UTF-8'?><SOAP-ENV:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
                + "xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/' xmlns:SOAP-ENC='http://schemas.xmlsoap.org/soap/encoding/'>"
                + "<SOAP-ENV:Header><TransactionID xmlns='http://10.1.2.122/misc/dsmp.xsd'>00110100037392</TransactionID>"
                + "</SOAP-ENV:Header><SOAP-ENV:Body><SyncOrderRelationReq xmlns='http://10.1.2.122/misc/dsmp.xsd'>"
                + "<Version>1.5.0</Version><MsgType>SyncOrderRelationReq</MsgType><Send_Address><DeviceType>0</DeviceType><DeviceID>0011</DeviceID></Send_Address>"
                + "<Dest_Address><DeviceType>400</DeviceType><DeviceID>0</DeviceID></Dest_Address><FeeUser_ID><UserIDType>2</UserIDType><MSISDN></MSISDN>"
                + "<PseudoCode>00116000000286</PseudoCode></FeeUser_ID><DestUser_ID><UserIDType>2</UserIDType><MSISDN></MSISDN><PseudoCode>00116000000286</PseudoCode>"
                + "</DestUser_ID><LinkID>SP</LinkID><ActionID>1</ActionID><ActionReasonID>1</ActionReasonID><SPID>900562</SPID><SPServiceID>04101040</SPServiceID>"
                + "<AccessMode>5</AccessMode><FeatureStr></FeatureStr></SyncOrderRelationReq></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        // return
        // "<?xml version=\"1.0\" encoding=\"utf-8\"?><SOAP-ENV:Envelope xmlns:xsi=\"http:/www.w3.org/XMLSchema-instance\" "
        // +" xmlns:xsd=\"http:/www.w3.org/XMLSchema\" xmlns:SOAP-ENV=\"http:/schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http:/schemas.xmlsoap.org/soap/encoding/\">"
        // +" <SOAP-ENV:Header><TransactionID xmlns=\"http:/www.monternet.com/dsmp/schemas/\">00110318384464</TransactionID>"
        // +" </SOAP-ENV:Header><SOAP-ENV:Body><SyncOrderRelationReq xmlns=\"http:/www.monternet.com/dsmp/schemas/\">"
        // +" <Version>1.5.0</Version><MsgType>SyncOrderRelationReq</MsgType><Send_Address><DeviceType>0</DeviceType><DeviceID>0011</DeviceID></Send_Address><Dest_Address>"
        // +" <DeviceType>400</DeviceType><DeviceID>0</DeviceID></Dest_Address><FeeUser_ID><UserIDType>1</UserIDType><MSISDN>13985046628</MSISDN><PseudoCode></PseudoCode></FeeUser_ID>"
        // +" <DestUser_ID><UserIDType>1</UserIDType><MSISDN>13985046628</MSISDN><PseudoCode></PseudoCode></DestUser_ID><LinkID>SP</LinkID><ActionID>1</ActionID>"
        // +" <ActionReasonID>1</ActionReasonID><SPID>924403</SPID><SPServiceID>业务代码</SPServiceID><AccessMode>3</AccessMode><FeatureStr>MTA2NjIxNDQgREE=</FeatureStr>"
        // +" </SyncOrderRelationReq></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    }

    /**
     * 响应XML
     * 
     * @param TransactionID
     * 消息编号
     * @param Version
     * 版本号
     * @param hRet
     * 0 为成功，1为失败
     * @return
     */
    public static String getResultXml(String TransactionID, String Version, String hRet) {
        return "<SOAP-ENV:Envelope "
                + "xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\">"
                + "<SOAP-ENV:Header>" + "<dsmp:TransactionID xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\">"
                + TransactionID + "</dsmp:TransactionID>" + "</SOAP-ENV:Header>" + "<SOAP-ENV:Body>"
                + "<dsmp:SyncOrderRelationResp xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\">"
                + "<MsgType>SyncOrderRelationResp</MsgType>" + "<Version>" + Version + "</Version>" + "<hRet>" + hRet
                + "</hRet>" + "</dsmp:SyncOrderRelationResp>" + "</SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";
    }
}
