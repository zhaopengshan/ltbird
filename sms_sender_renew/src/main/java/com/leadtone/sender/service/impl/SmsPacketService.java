package com.leadtone.sender.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.leadtone.adc.sms.core.SmsUtil;
import com.leadtone.sender.service.ISmsPacketService;
import com.leadtone.util.ByteUtil;
import com.leadtone.util.ProperUtil;

//一条正常短信正常长度是140字节，长短信会占用12个字节做标题头，050003 08xx06(08表示共8条短信，XX随机码标识这组短信，06标识这是第几条短信)；
//还要注意长短信最后一条短信需要有签名，所以长短信最后一条短信的实际允许的内容长度为：140-12-签名长度=内容长度
public class SmsPacketService implements ISmsPacketService {
	Logger logger = Logger.getLogger(SmsPacketService.class);
	private int smsLimitLength;
	private int smsGatewaySignLength;

	public SmsPacketService() {
		ProperUtil.setLocalPath("config.proterties");
		String smsLimitLengthStr = ProperUtil.readValue("sms.content.limit.length");
		smsLimitLength = (smsLimitLengthStr == null || "".equals(smsLimitLengthStr.trim())) ? 70 : Integer.parseInt(smsLimitLengthStr);
		String smsGatewaySignLengthStr = ProperUtil.readValue("sms.content.gateway.sign.length");
		smsGatewaySignLength = (smsGatewaySignLengthStr == null || "".equals(smsGatewaySignLengthStr.trim())) ? 10 : Integer.parseInt(smsGatewaySignLengthStr);
	}

	@Override
	public String[] packet(String msgContent,int signLenth) {
		if (msgContent == null || "".equals(msgContent.trim())) {
			msgContent = " ";
		}
		String[] data = null;
		if (msgContent.length() + signLenth <= smsLimitLength ) {
			try {
				byte[] dataBytes = msgContent.getBytes("UnicodeBigUnmarked");
				String contentStr = ByteUtil.byte2hex(dataBytes);
                data = new String[1];
				data[0] = contentStr;
				logger.debug("SMS Content is  " + data[0]);
			} catch (UnsupportedEncodingException e) {
				logger.error("短信内容编码转码错误！原因：[" + e+"]");
				e.printStackTrace();
			}
		}else{
			// 参照拆包策略
			int totalFragments = 1;
			boolean aliquot = msgContent.length() % (smsLimitLength - 3)==0?true:false;
			if ((msgContent.length()+signLenth)%(smsLimitLength - 3)==0) {
				totalFragments = (msgContent.length()+signLenth)/(smsLimitLength - 3);
			} else {
				totalFragments = (msgContent.length() + signLenth)/(smsLimitLength - 3) + 1;
			}
			logger.debug("分包总数：total fragment= " + totalFragments);
			data = spellSmsPackage(totalFragments, aliquot, msgContent, signLenth);

		}
		return data;
	}
	
	public String[] emppPacket(String msgContent) {
		if (msgContent == null || "".equals(msgContent.trim())) {
			msgContent = " ";
		}
		String[] data = null;
			try {
				byte[] dataBytes = msgContent.getBytes("UnicodeBigUnmarked");
				String contentStr = ByteUtil.byte2hex(dataBytes);
	            data = new String[1];
				data[0] = contentStr;
				logger.debug("SMS Content is  " + data[0]);
			} catch (UnsupportedEncodingException e) {
				logger.error("短信内容编码转码错误！原因：[" + e+"]");
				e.printStackTrace();
			}
		return data;
	}

	private String[] spellSmsPackage(int totalFragments, boolean aliquot,String msgContent,int signLenth) {
		String[] data = new String[totalFragments];
		byte longMark = SmsUtil.getLongSmsMark();
		try {
			int offset = 0;
			int fragmentLen = 0;
			for (int i = 0; i < totalFragments; i++) {
				String fragment = null;
				if (totalFragments - 1 == i) {
//					if(aliquot){
//						fragmentLen = fragmentLen - 3;
//					}else{
					    if (67 - signLenth < ((msgContent.length() - offset) % 67))
						    fragmentLen = 70 - 3 - signLenth;
					    else
						    fragmentLen = msgContent.length() - offset;
//					}
				} else {
					if (totalFragments - 2 == i) {
						if (67 - signLenth< ((msgContent.length() - offset) % 67))
							fragmentLen = 70 - 3 - signLenth;
						else
							fragmentLen = 67;
					} else
						fragmentLen = 67;
				}
				fragment = msgContent.substring(offset, offset + fragmentLen);
				offset += fragmentLen;
				byte[] head = getLongSMSProtocal(totalFragments, longMark,
						i + 1);
				byte[] body = fragment.getBytes("UnicodeBigUnmarked");
				byte[] content = new byte[head.length + body.length];
				System.arraycopy(head, 0, content, 0, head.length);
				System.arraycopy(body, 0, content, head.length, body.length);
				data[i] = new String(content, "UnicodeBigUnmarked");
				try {
					byte[] tempContent = data[i].getBytes("UnicodeBigUnmarked");
					String contentStr = ByteUtil.byte2hex(tempContent);
					data[i] = contentStr;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.debug("SMS Content is  " + data[i]);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	private byte[] getLongSMSProtocal(Integer smsSize, byte longMark,
			Integer num) {
		byte[] protocal = new byte[] { 05, 00, 03, longMark,
				smsSize.byteValue(), num.byteValue() };
		return protocal;
	}
	
	
	public static void main(String[]args) throws IOException{
//		SmsPacketService packet = new SmsPacketService();
//		String msgContent = "盐城明晶滤材有限公司http://www.ycmjlc.com厂家批发直供：PPS、PTFE、玄武岩、氟美斯袋、美塔斯袋、亚克力袋、玻纤袋、防水涤纶袋、硅油/石墨玻纤袋、电镀/喷塑/不锈钢骨架及袋式收尘器、电收尘改袋收尘器。货真价实，信誉第一，服务周道！（袋口下十公分有洞要补焊脉冲阀下喷吹口，定袋时骨架长及花板孔直径要精确，定期检查气缸和脉冲阀是否工作以防止大面积损坏除尘袋。明晶滤材感谢您的来电与支持！供货热线：明晶 14705118188  18977508118，电传：0515-83414555，83419798";
//		String[] data = packet.packet(msgContent,10);
//		System.out.println("条数："+data.length);
//		for(int i=0;i<data.length;i++){
//			System.out.println("第"+(i+1)+"条数长度为："+ data[i].length()+ "\r内容为："+ data[i]);
//			if(data.length==1){
//				String aa = new String( ByteUtil.hex2byte(data[i]),"UnicodeBigUnmarked");
//				System.out.println(aa);
//			} else {
//				String aa = new String( ByteUtil.hex2byte(data[i]),"UnicodeBigUnmarked");
//				System.out.println(aa.substring(3));
//			}
//		}
//		
//		FileWriter writer = new FileWriter("D:\\result.txt", true); 
//		for(int k=0;k<=10;k++){//签名长度。0<= X <=10
//			writer.write("签名长度为："+k+"\r");
//			for(int m = 1;m +k <= 335;m ++){//内容长度,最多5条 ，含签名小于等于67*5 = 335
//				StringBuilder content = new StringBuilder();//内容
//				for(int i = 1;i<=m; i++){
//					content.append("啊");
//				}
//				String[] split = packet.packet(content.toString(),k);
//
//				writer.write("签名长度为："+k + "内容长度为:"+m+"拆分条数为："+split.length+"\r");
//				StringBuilder decode = new StringBuilder();
//				for(int i=0;i<split.length;i++){
//					
//					if(split[i].length()>280){
//						System.out.println("lenth error\r");
//					}
//					
//					String aa="";
//					if(split.length==1){
//						 aa = new String( ByteUtil.hex2byte(split[i]),"UnicodeBigUnmarked");
//						writer.write("签名长度为："+k + "内容长度为:"+m+"拆分条数为："+split.length+ "第："+(i+1)+"内容为："+aa +"\r");
//						decode.append(aa);
//					} else {
//						 aa = new String( ByteUtil.hex2byte(split[i]),"UnicodeBigUnmarked").substring(3);
//						writer.write("签名长度为："+k + "内容长度为:"+m+"拆分条数为："+split.length+ "第："+(i+1)+"内容为："+aa+"\r");
//						decode.append(aa);
//					}
//					
//					writer.write("签名长度为："+k + "内容长度为:"+m+"拆分条数为："+split.length+ "第："+(i+1)+"条长度为:"+split[i].length()+ "内容长度为："+ aa.length() +"\r");
//					
//				}
//				
//				if(m!=decode.length()){
//					System.out.println("split\r");
//				}
//			}
//			writer.flush();
//		}
		
		String code ="0051000D91685100035378F30008008c0500037E05046C3475357AD953D1753591CFFF1A00340034002E00364E07006B00570068FF0C5F5367087D2F8BA153D1753591CFFF1A0033003300394E07006B00570068FF1B4E8C7EA76C3475357AD953D1753591CF003A00340035002E00324E07006B00570068FF0C5F5367087D2F8BA153D1753591CFFF1A003300340034002E00344E07006B00570068";
		
		
		String decode =new String( ByteUtil.hex2byte(code),"UnicodeBigUnmarked");
		System.out.println(decode);
		
		code ="0051000D91685100035378F3000800320500037E0505FF1B53F051706CB367656C3491CF7EA600320032002E0032006D0033002F00733002FF08738B4F73851AFF09";
		decode =new String( ByteUtil.hex2byte(code),"UnicodeBigUnmarked");
		
		System.out.println(decode);
	}
}
