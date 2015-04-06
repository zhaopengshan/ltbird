package com.leadtone.mas.admin.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

/**
 * 采用流的方式实现文件上传功能
 * 
 * 用户可以上传单个文件，也可以上传一组文件
 * 
 * 上传单个文件时用户可以调用upload(HttpServletRequest request, File src,String srcFileName,
 * String destDir)或者upload(HttpServletRequest request, File src,String
 * srcFileName)方法
 * 
 * 上传多个文件时可以调用upload(HttpServletRequest request, File src,String srcFileName,
 * String destDir)或者upload(HttpServletRequest request, File src,String
 * srcFileName, String destDir)
 * 
 * @author licb-leadtone
 * 
 */
public class FileUploadUtil {
	// 默认的缓冲区大小
	private static int BUFFER_SIZE = 16 * 1024 * 1024;
	private static String defaultDir = File.separator + "uploads";

	/**
	 * 将源文件拷贝到目的文件
	 * 
	 * @param src
	 *            源文件
	 * @param dst
	 *            目的文件
	 */
	public static int copy(File src, File dst) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		int fileSize = 0;

		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			fileSize = in.available();
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return fileSize;
	}

	/**
	 * 获取文件的扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getFileType(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 文件上传，并返回生成的新文件的目录、文件目录和文件大小
	 * 
	 * @param request
	 *            HttpServleRequest
	 * @param src
	 *            源文件，File[]类型
	 * @param srcFileName
	 *            源文件的真实名称,String[]类型，与上传文件一一对应
	 * @param destDir
	 *            目标文件的目录
	 * @return 返回一个二维数组。每行记录代表一个上传文件，其中包含了文件保存的路径；文件保存的名称和文件的大小(单位是字节，
	 *         如果需要采用KB表示自己再除以1024即可)
	 */
	public static String[][] upload(HttpServletRequest request, File[] src,
			String[] srcFileName, String destDir) {
		if (src == null) {
			return null;
		}

		String[][] fileItem = new String[src.length][3];

		/**
		 * 如果用户没有指定上传文件的目标地址，那么将文件拷贝到默认目录中
		 */
		if (destDir == null) {
			destDir = request.getSession().getServletContext().getRealPath(
					defaultDir);
			if (!new File(destDir).exists()) {
				new File(destDir).mkdirs();
			}
		}

		if (!destDir.endsWith(File.separator)) {
			destDir += File.separator;
		}

		for (int i = 0; i < src.length; i++) {
			String ext = getFileType(srcFileName[i]);

			String destFileName = srcFileName[i];
			/**
			 * 为了防止用户上传中文名称的文件，这里首先修改名称再上传
			 */
			destFileName = getNewFileName() + ext;
			String destFilePath = destDir + destFileName;
			/**
			 * 如果上传文件有重名的，那么新生成一个文件名
			 */
			while (new File(destFilePath).exists()) {
				destFileName = getNewFileName() + ext;
				destFilePath = destDir + destFileName;
			}

			File dest = new File(destFilePath);
			int fileSize = copy(src[i], dest);

			// 将文件分隔符反斜杠替换成正斜杠
			fileItem[i][0] = destDir.replaceAll("[\\\\]+", "/");
			fileItem[i][1] = destFileName;
			fileItem[i][2] = "" + fileSize;
		}

		return fileItem;
	}

	/**
	 * 文件上传工具类的人口
	 * 
	 * @param request
	 *            HttpServleRequest
	 * @param src
	 *            源文件，File类型
	 * @param srcFileName
	 *            源文件的名称
	 * @return
	 */
	public static String[][] upload(HttpServletRequest request, File[] src,
			String[] srcFileName) {
		return upload(request, src, srcFileName, null);
	}

	/**
	 * 上传单个文件工具类
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param src
	 *            File类型的上传文件
	 * @param srcFileName
	 *            String类型的上传文件真实名称
	 * @param destDir
	 *            上传文件保存的目录。可以为空，那么上传文件会保存在系统默认的目录中
	 * @return 返回一个长度为3的一维数组，内容依次为文件保存目录、文件名称和文件大小(单位为byte)
	 */
	public static String[] upload(HttpServletRequest request, File src,
			String srcFileName, String destDir) {
		if (src == null) {
			return null;
		}

		String[] fileItem = new String[3];

		/**
		 * 如果用户没有指定上传文件的目标地址，那么将文件拷贝到默认目录中
		 */
		if (destDir == null) {
			destDir = request.getSession().getServletContext().getRealPath(
					defaultDir);
		}

		if (!new File(destDir).exists()) {
			new File(destDir).mkdirs();
		}

		if (!destDir.endsWith(File.separator)) {
			destDir += File.separator;
		}

		String ext = getFileType(srcFileName);

		String destFileName = srcFileName;
		destFileName = getNewFileName() + ext;
		String destFilePath = destDir + destFileName;
		/**
		 * 如果上传文件有重名的，那么新生成一个文件名
		 */
		while (new File(destFilePath).exists()) {
			destFileName = getNewFileName() + ext;
			destFilePath = destDir + destFileName;
		}

		File dest = new File(destFilePath);
		int fileSize = copy(src, dest);

		fileItem[0] = destDir;
		fileItem[1] = destFileName;
		fileItem[2] = "" + fileSize;

		return fileItem;
	}

	/**
	 * 单个文件上传工具类的人口
	 * 
	 * @param request
	 *            HttpServleRequest
	 * @param src
	 *            源文件，File类型
	 * @param srcFileName
	 *            源文件的名称
	 * @return 返回一个长度为3的一维数组，内容依次为文件保存目录、文件名称和文件大小(单位为byte)
	 */
	public static String[] upload(HttpServletRequest request, File src,
			String srcFileName) {
		return upload(request, src, srcFileName, null);
	}

	/**
	 * 根据当前的时间获取文件名
	 * 
	 * @return
	 */
	private static String getNewFileName() {
		Calendar c = Calendar.getInstance();
		return c.getTime().getTime() + "";
	}
}
