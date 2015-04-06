package com.leadtone.mas.bizplug.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String sendRequest(String reqUrl, String reqString, Integer milliseconds) {
		logger.info("request content:" + reqString);
		java.net.URL url = null;
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null;
		InputStream inputStream = null;
		StringBuffer str = new StringBuffer();
		try {
			url = new java.net.URL(reqUrl);
			java.net.URLConnection urlcn = url.openConnection();
			if (urlcn instanceof HttpURLConnection) {
				httpURLConnection = (HttpURLConnection) urlcn;
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setDoInput(true);
				httpURLConnection.setReadTimeout(milliseconds);
				out = httpURLConnection.getOutputStream();
				out.write(reqString.getBytes("UTF-8"));
				inputStream = httpURLConnection.getInputStream();
				Reader readerStream = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader reader = new BufferedReader(readerStream);
				out.close();
				String line = null;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
				reader.close();
				httpURLConnection.disconnect();
			}
		} catch (Exception e) {
			logger.error("http client error:" + reqUrl, e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(inputStream);
			if (httpURLConnection != null){
				httpURLConnection.disconnect();
			}
		}
		String recv = str.toString();
		logger.info("reponse content:" + recv);
		return recv;
	}
}
