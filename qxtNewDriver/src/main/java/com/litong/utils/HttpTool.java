package com.litong.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
public class HttpTool {
	Logger logger = Logger.getLogger(HttpTool.class);
	public String sendPostHttpRequest(String reuquestUrl, String httpData) {
		StringBuffer sResult = new StringBuffer();
		try {
			URL url = new URL(reuquestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Length", String.valueOf(httpData
					.getBytes().length));
			PrintWriter out = null;
			out = new PrintWriter(new OutputStreamWriter(
					conn.getOutputStream(), "utf-8"));
			out.print(httpData);
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			InputStream httpIS = null;
			java.io.BufferedReader http_reader = null;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				httpIS = conn.getInputStream();
				http_reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(httpIS, "GB2312"));
				String line = null;
				while ((line = http_reader.readLine()) != null) {
					if (sResult.length() > 0) {
						sResult.append("\n");
					}
					sResult.append(line);
				}
//				logger.info("ok=======http:"+sResult);
			} else {
//				logger.info("error==============http:"+responseCode);
				sResult.append("-1");
			}
		} catch (Exception ex) {
			logger.info("ex:"+ex.getMessage());
		}
		return sResult.toString();
	}
	public String sendGetHttpRequest(String reuquestUrl) {
		StringBuffer sResult = new StringBuffer();
		try {
			URL url = new URL(reuquestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			int responseCode = conn.getResponseCode();

			InputStream httpIS = null;
			java.io.BufferedReader http_reader = null;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				httpIS = conn.getInputStream();
				http_reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(httpIS, "UTF-8"));
				String line = null;
				while ((line = http_reader.readLine()) != null) {
					if (sResult.length() > 0) {
						sResult.append("\n");
					}
					sResult.append(line);
				}
			} else {
				sResult.append("-1");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sResult.toString();
	}
	public void retData(HttpServletResponse response, String data) {
		try {
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("text/plain");
			// out.println("<?xml version=1.0 encoding=UTF-8 ?>");
			out.print(data);
			out.flush();
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}