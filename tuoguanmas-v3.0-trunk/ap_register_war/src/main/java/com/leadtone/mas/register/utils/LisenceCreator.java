package com.leadtone.mas.register.utils;

import java.io.File;
import java.util.List;

public class LisenceCreator {

	public static void main(String args[]) throws Exception {
		String source = "";
		String target = "";

		byte buffer[] = new byte[100];
		while (true) {
			System.out.print("请输入您要转换的IMEI文件：");
			int b = System.in.read(buffer);
			File sourcefile = new File(new String(buffer, 0, b).replace("\r\n",
					""));
			if (sourcefile.isDirectory() || !sourcefile.exists()) {
				System.out.println("您输入的IMEI文件不存在，请重新输入！");
			} else {
				source = sourcefile.getPath();
				break;
			}
		}

		buffer = new byte[100];
		while (true) {
			System.out.print("请输入您要生成的序列号文件(不输入路径将和jar同路径)：");
			int b = System.in.read(buffer);
			File targetfile = new File(new String(buffer, 0, b).replace("\r\n",
					""));
			if (targetfile.isDirectory()) {
				System.out.println("您输入的是个路径！");
			} else {
				target = targetfile.getPath();
				break;
			}
		}

		File sourcefile = new File(source);
		File targetfile = new File(target);

		List<String> serialList = null;
		if (targetfile.exists()) {
			serialList = FileTools.read(targetfile);
		}

		// 读取源文件
		List<String> imeiList = FileTools.read(sourcefile);

		List<String> batchSerialList = FileTools.calculateSerial(imeiList, serialList);

		FileTools.save(targetfile, batchSerialList);

		System.out.println("生成序列号文件成功");
	}

}
