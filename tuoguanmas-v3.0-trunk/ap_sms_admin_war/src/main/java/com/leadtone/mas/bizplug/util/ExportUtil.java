package com.leadtone.mas.bizplug.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.common.ex.export.DataExport;

public class ExportUtil {
	
	private static Logger logger = Logger.getLogger(ExportUtil.class);
	/**
	 * 导出成excel文件
	 * @param preFileName	导出类型：导出列表，导出结果
	 * @param cols			列头
	 * @param contents		每行内容
	 * @return
	 */
	public static String exportToExcel(HttpServletRequest request, String preFileName, String[] cols, String[][] contents){
		String strFileFlag = DataExport.EXCEL_XLS;
        long start = System.currentTimeMillis();
        String strFileName = null;
            // 获取应用的绝对路径
            String appPath = request.getRequestURL().toString();
            appPath = appPath.substring(0, appPath.lastIndexOf("/"));

            // 获取导出文件所在的目录的本地路径,以用户生成新的文件
            String filePath = File.separator + "downloads" + File.separator + "sms";
            String localDir = request.getSession().getServletContext().getRealPath(filePath) + File.separator;
            try {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            	 strFileName = 
            			 preFileName+"_"+sdf.format(new Date()) + strFileFlag;
            	
                new DataExport().exportToFile(localDir, strFileName, contents,
                        cols, strFileFlag);// 默认导出.xls格式的文件
            } catch (Exception e) {
                logger.error("export "+preFileName+" error," , e);
                long end = System.currentTimeMillis();
                logger.info(" export "+preFileName+",totally use " + (end - start) + " millseconds ");
            }
        long end = System.currentTimeMillis();
        logger.info("export "+preFileName+" success, fileName: "+strFileName+",totally use " + (end - start) + " millseconds ");
		return strFileName;
	}
}
