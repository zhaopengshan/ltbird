/**
 * Project Name: ws-server <br/>
 * File Name: Provision.java <br/>
 * Package Name: com.changyuan.misc.action <br/>
 * Date: 2015年8月16日下午4:58:55 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
//
// import org.w3c.dom.Document;
// import org.xml.sax.InputSource;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changyuan.misc.dao.TbUserDao;
import com.changyuan.misc.model.bean.ProvisionBean;
import com.changyuan.misc.utils.AnalysisXML;
import com.changyuan.misc.utils.BeanContainer;
/**
 * ClassName: ProvisionServlet <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月16日 下午4:58:55 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class ProvisionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(ProvisionServlet.class);
    /**
     * 
     */
    private static final String CONTENT_TYPE = "text/xml";

    public ProvisionServlet() {
    }

    /**
     * 订购关系同步消息接口
     * 
     * @throws IOException
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TbUserDao tbUserDao = BeanContainer.getBean("tbUserDao",TbUserDao.class);
        System.out.println("userDao.hashCode>>"+tbUserDao.hashCode());
        logger.info(">>>>>>>>>>>>接收SyncOrderRelationReq请求<<<<<<<<<<<<<");
        String resultXml = "";
        boolean resultStr = true;
        String XMLData = null;
        StringBuffer tempStringBuffer = new StringBuffer();
        String tempString = null;
        BufferedReader reader = request.getReader();// 接受MISC的订购关系同步请求包SyncOrderRelationReq
        while ((tempString = reader.readLine()) != null) {
            tempStringBuffer.append(tempString);// 读取订购关系同步请求包SyncOrderRelationReq内容
        }
        XMLData = tempStringBuffer.toString();// 转换订购关系同步请求包SyncOrderRelationReq内容为STRING，存储到XMLDAT
        logger.info("++++++++++++++++++++++++++++++++++++");
        logger.info("接收到的同步消息数据");
        logger.info(XMLData);
        logger.info("++++++++++++++++++++++++++++++++++++");
        ProvisionBean provision = null;
        if (XMLData == null || XMLData.length() < 10) {
            resultStr = false;
        } else {
            provision = new AnalysisXML().getXmlResultt(XMLData);
            if (provision == null) {
                resultStr = false;
            } else {
                // //处理同步消息
                logger.info(">>>>>>>>>>>>>>>>>>>处理同步消息<<<<<<<<<<<<<<<<<");
                logger.info(">>>>>>>>>>>>>>>>>>>处理同步消息:" + provision.toString());
            }
        }
        PrintWriter out = response.getWriter();
        if (resultStr) {
            // 返回成功
            resultXml = new AnalysisXML().getResultXml(provision.getTransactionID(), provision.getVersion(), "0");
        } else {
            // 返回失败
            resultXml = new AnalysisXML().getResultXml(provision.getTransactionID(), provision.getVersion(), "1");
        }
        // response.setContentType("text/xml");
        response.setContentType(CONTENT_TYPE);
        out.print(resultXml);
        logger.info(">>>>>>>>>>>>>>>>>>>处理响应消息:" + resultXml);
        out.flush();
        out.close();
        logger.info(">>>>>>>>>>>>返回SyncOrderRelationResp响应<<<<<<<<<<<<<");
        // DSMPServiceProv prov = this.parseReq(request);
        // if (prov != null) {
        // this.logAndSync(prov);
        // this.respReq(response, prov, 0);
        // }
    }
    // /**
    // * 将请求的SOAP包进行解析封装
    // *
    // * @param request
    // * @return
    // */
    // private DSMPServiceProv parseReq(HttpServletRequest request) {
    // DSMPServiceProv prov = null;
    // Document doc = null;
    // try {
    // BufferedReader br = request.getReader();
    // DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    // DocumentBuilder builder = factory.newDocumentBuilder();
    // doc = builder.parse(new InputSource(br));
    // DOMParsing.printNode(doc, "  ");
    // doc.normalize();
    // // 对包进行解析,得到相应的包的内容PROV
    // prov = DSMPServiceProv.ParseSoapXml(doc);
    // } catch (SAXException ex) {
    // System.out.println("## error SAXException:" + ex.toString());
    // } catch (ParserConfigurationException ex) {
    // System.out.println("## error ParserConfigurationException:" + ex.toString());
    // } catch (FactoryConfigurationError ex) {
    // System.out.println("## error FactoryConfigurationError:" + ex.toString());
    // } catch (IOException ex) {
    // System.out.println("## error IOException:" + ex.toString());
    // }
    // return prov;
    // }
    //
    // public void logAndSync(DSMPServiceProv prov) {
    // // 业务处理
    // // 状态管理命令结果 errState : 0 命令成功; -1 无效的action_id;-2 无效的service_id
    // // (其它任何非0值) 命令失败;
    // // action : 1 开通; 2 停止; 3 激活 4 暂停
    // ProvTransInterface provtrans = new ProvTransHandler();
    // provtrans.logProvTrans(prov);
    // provtrans.SyncProv(prov);
    // }
    //
    // public void respReq(HttpServletResponse response, DSMPServiceProv prov, int errState) {
    // String sb = null;
    // PrintWriter out = null;
    // try {
    // out = response.getWriter();
    // } catch (IOException ex) {
    // System.out.println(ex.toString());
    // }
    // if ("SyncOrderRelationReq".equals(prov.getMsgType())) {
    // sb = SyncOrderRelationResp.getResp(errState, prov.getTransactionID());
    // } else if ("UnSubscribeServiceReq".equals(prov.getMsgType())) {
    // sb = UnSubscribeServiceResp.getResp(errState, prov.getTransactionID());
    // }
    // out.print(sb.toString());
    // out.flush();
    // out.close();
    // }
}
