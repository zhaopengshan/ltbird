/**
 * Project Name: ws-server <br/>
 * File Name: Test.java <br/>
 * Package Name: com.test.misc <br/>
 * Date: 2015年8月17日上午10:02:15 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.test.misc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.changyuan.misc.utils.AnalysisXML;

/**
 * ClassName: Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月17日 上午10:02:15 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class Test {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String request = AnalysisXML.getXml();
        // String path = "http://localhost:8080/axis/services/bxserver";
        String path = "http://localhost:8080/ws-server/servlets/provision";
//        String path = "http://114.113.234.157/ws-server/services/provision";
        java.net.URL url = new java.net.URL(path);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "text/xml");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(request);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        System.out.println("=============================");
        System.out.println("Contents of post request");
        System.out.println("=============================");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("=============================");
        System.out.println("Contents of post request ends");
        System.out.println("=============================");
        reader.close();
        connection.disconnect();
    }
}
