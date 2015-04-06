package com.leadtone.mas.admin.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载ExcelDemo
 * @author wangyu
 */
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	private boolean checkFileName(String fileName){
		if(fileName.endsWith(".xls")){
			
		}
		return true;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String file = request.getParameter("fileName");
		
		if(!file.endsWith(".xls")||file.indexOf("..")>-1||file.indexOf("downloads")==-1){
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
//		if(1!=2){
//			request.getRequestDispatcher("/error.jsp").forward(request, response);
//			return;
//		}
		// 去掉文件名中的“\”分隔符和“/”分隔符,防止带有分隔符，在ie6下下载会出现问题
		String fileName = file;
		if (file.indexOf("/") != -1)
			fileName = file.substring(file.lastIndexOf("/") + 1);
		if (fileName.indexOf("\\") != -1)
			fileName = file.substring(file.lastIndexOf("\\") + 1);
		//fileName = fileName.replaceAll("/", "").replaceAll("\\\\", "");

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        if (file.indexOf("downloads/") != -1)
			fileName = file.substring(file.lastIndexOf("downloads/") + "downloads/".length());
		if (file.indexOf("downloads\\") != -1)
			fileName = file.substring(file.lastIndexOf("downloads\\") + "downloads\\".length());
		// 这才是获取真正的物理路径
		ServletContext appliaction = this.getServletContext();
		// file = file.replaceAll("/", "\\\\");
		String downFilePath = appliaction.getRealPath("/downloads")+"/" + fileName;

		// 下面的方法获取的是网络路径
		// String appPath = request.getRequestURL().toString();
		// appPath = appPath.substring(0, appPath.lastIndexOf("/"));

		OutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream(new File(downFilePath));

		int i = 0;
		byte[] arr = new byte[0124];
		while ((i = fis.read(arr)) != -1) {
			os.write(arr, 0, i);
		}

		os.close();
	}
}
