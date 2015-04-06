package com.leadtone.mas.bizplug.mms.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.admin.common.FileUploadUtil;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMms;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachment;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrame;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrameVO;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysend;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysendVO;
import com.leadtone.mas.bizplug.mms.service.MbnMmsIService;
import com.leadtone.mas.bizplug.mms.service.MbnMmsReadysendIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;
import com.leadtone.mas.bizplug.util.WebUtils;
import org.apache.commons.lang3.StringUtils;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/mmsAction")
public class MmsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private MbnMmsReadysendIService mbnMmsReadysendIService;
	@Resource
	private ContactsService contactsService;
	@Resource
	private MbnMmsIService mbnMmsIService;
	@Resource
	private SmsMbnTunnelService smsMbnTunnelService;
	
	private File music;
	private String musicFileName;
	private File picture;
	private String pictureFileName;
	private String[] filePro;
	
	private String mmsReceiver;
	private String mmsTitle;
	private String[]  imageArray;
	private String[]  musicArray;
	private String[]  contentArray;
	private String  imageArrayString;
	private String  musicArrayString;
	private String  contentArrayString;
	private Double attachmentSize;
	private String smsIds;
	
	private static String fileDirectory =  File.separator + "mmsuploads";
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	/**
	 * 收藏收件箱短信，置标志 为 2 收藏
	 * @return
	 */
	@Action(value="cancelSendByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String cancelSendByIds(){
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		entityMap = new HashMap<String, Object>();
		if( !StringUtil.isEmpty(smsIds) && loginUser != null){
			String[] smsTemp = smsIds.split(",");
			try{
				new HashMap<String, Object>();
				List<MbnMmsReadysend> readySendList = mbnMmsReadysendIService.getByPks(smsTemp);
				if(readySendList!=null&& readySendList.size()>0){
					Iterator<MbnMmsReadysend> readySendIterator = readySendList.iterator();
					while( readySendIterator.hasNext() ){
						MbnMmsReadysend tempReady = readySendIterator.next();
						tempReady.setSendResult(ApSmsConstants.MMS_CANCEL_STATE);
					}
					mbnMmsReadysendIService.batchUpdateByList(readySendList);
					entityMap.put("resultcode", "success" );
			        entityMap.put("message", "取消发送成功！");
			        return SUCCESS;
				}
			}catch( Exception e ){
				e.printStackTrace();
			}
		}
		entityMap.put("resultcode", "error" );
        entityMap.put("message", "取消发送失败！");
		return SUCCESS;
	}
	
	@Action(value = "deleteMmsByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String deleteMmsByIds(){
		if(!StringUtil.isEmpty(smsIds)){
			entityMap = new HashMap<String, Object>();
			try{
				String[] mmsIdsArray = smsIds.split(",");
				Long[] mmsIds = ConvertUtil.arrStringToLong(mmsIdsArray);
				if(mbnMmsReadysendIService.batchDeleteByPks(mmsIds)){
					entityMap.put("resultcode", "error" );
			        entityMap.put("message", "删除失败！");
				}
			}catch( Exception e ){
				e.printStackTrace();
			}
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "删除成功！");
			
		}else{
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "删除失败！");
		}
		return SUCCESS;
	}
	
	@Action(value = "sendMms", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String sendMms() {
		entityMap = new HashMap<String, Object>();
		if( mmsTitle ==null || mmsTitle.trim().equals("")){
            entityMap.put("flag", false);
            entityMap.put("message", "彩信标题不能为空！");
            return SUCCESS;
		}
		try{
			Users loginUser = (Users) super.getSession().getAttribute(
					ApSmsConstants.SESSION_USER_INFO);
			if( loginUser ==null ){
	            entityMap.put("flag", false);
	            entityMap.put("message", "用户登录超时！");
	            return SUCCESS;
			}
			imageArray = imageArrayString.split(",");
			musicArray = musicArrayString.split(",");
			contentArray = contentArrayString.split(",");
			String[] contentFileName = new String[contentArray.length];
			for(int i = 0; i < contentArray.length; i++){
				if( !contentArray[i].trim().equals("") ){
					String tempFileName = contentToFile(contentArray[i]);
					contentFileName[i] = tempFileName;
				}else{
					contentFileName[i] = "";
				}
			}
			
			Set<Contacts> userSet = new HashSet<Contacts>();
			Long createBy = loginUser.getId();
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| loginUser.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
			}
			userSet = ContactsUtil.getContactsSet(contactsService, mmsReceiver, loginUser.getMerchantPin(),createBy);
			String userPhoneNumber = "";
			Iterator<Contacts> userIterator = userSet.iterator();
			boolean bFirst = true;
			while(userIterator.hasNext()){
				Contacts contact = userIterator.next();
				if(bFirst){
					userPhoneNumber = contact.getMobile();
					bFirst = false;
				}else{
					userPhoneNumber = userPhoneNumber+","+contact.getMobile();
				}
			}
			//mms
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			MbnMms mms = new MbnMms();
			Long mmsId = PinGen.getSerialPin();
			mms.setId(mmsId);
			mms.setCreateBy(loginUser.getId());
			mms.setCreateTime(currentTime);
			//TODO
			mms.setAttachmentSize(attachmentSize);
			mms.setTitle(mmsTitle);
			//frame 
			List<MbnMmsFrameVO> frameList = new ArrayList<MbnMmsFrameVO>();
			for(int i = 0; i < contentArray.length; i++){
				MbnMmsFrameVO tempFrame = new MbnMmsFrameVO();
				Long frameId = PinGen.getSerialPin();
				tempFrame.setId(frameId);
				tempFrame.setFrameName("");
				tempFrame.setFrameText(java.net.URLDecoder.decode(contentArray[i],"UTF-8"));
				tempFrame.setMmsId(mmsId);
				tempFrame.setPersistTime(10);
				List<MbnMmsAttachment> attachmentList = new ArrayList<MbnMmsAttachment>();
				String destDir = this.request.getSession().getServletContext().getRealPath(
						fileDirectory);
				if( i < imageArray.length && !imageArray[i].trim().equals("") ){
					MbnMmsAttachment textAttachment = new MbnMmsAttachment();
					textAttachment.setAttachmentName(imageArray[i].trim());
					textAttachment.setAttachmentSize(0d);
					textAttachment.setFrameId(frameId);
					textAttachment.setId(PinGen.getSerialPin());
					textAttachment.setStorePath(destDir);
					textAttachment.setTypeId(2);
					attachmentList.add(textAttachment);
				}
				if( i < musicArray.length && !musicArray[i].trim().equals("") ){
					MbnMmsAttachment imageAttachment = new MbnMmsAttachment();
					imageAttachment.setAttachmentName(musicArray[i].trim());
					imageAttachment.setAttachmentSize(0d);
					imageAttachment.setFrameId(frameId);
					imageAttachment.setId(PinGen.getSerialPin());
					imageAttachment.setStorePath(destDir);
					imageAttachment.setTypeId(3);
					attachmentList.add(imageAttachment);
				}
				if( i < contentFileName.length && !contentFileName[i].trim().equals("") ){
					MbnMmsAttachment musicAttachment = new MbnMmsAttachment();
					musicAttachment.setAttachmentName(contentFileName[i].trim());
					musicAttachment.setAttachmentSize(0d);
					musicAttachment.setFrameId(frameId);
					musicAttachment.setId(PinGen.getSerialPin());
					musicAttachment.setStorePath(destDir);
					musicAttachment.setTypeId(1);
					attachmentList.add(musicAttachment);
				}
				tempFrame.setMbnMmsAttachment(attachmentList);
				frameList.add(tempFrame);
			}
			mbnMmsIService.addMmsTransaction(mms,frameList);
			List<MasTunnel> mmsTunnelList = smsMbnTunnelService.getMmsTunnelByMerchantPin(loginUser.getMerchantPin());
			if(mmsTunnelList!=null && mmsTunnelList.size()>0){
				MasTunnel mmsTunnel = mmsTunnelList.get(0);
				//readysend insert to DB
				String frameXml = buildXMLData(imageArray,musicArray,contentFileName);
				MbnMmsReadysend mmsReadySend = new MbnMmsReadysend();
				mmsReadySend.setCommitTime(currentTime);
				mmsReadySend.setDescription("");
				//mark not define, add for temp
				mmsReadySend.setExpireTime(currentTime);
				mmsReadySend.setFrameXml(frameXml);
				mmsReadySend.setId(PinGen.getSerialPin());
				mmsReadySend.setMerchantPin(loginUser.getMerchantPin());
				mmsReadySend.setMmsId(mmsId);
				//mark not define, add for temp
				mmsReadySend.setOperationId(1l);
				mmsReadySend.setPriorityLevel(25);
				mmsReadySend.setReadySendTime(currentTime);
				mmsReadySend.setSelfMobile(loginUser.getMobile());
				mmsReadySend.setSendResult(0);
				//mark
				mmsReadySend.setServiceId(mmsTunnel.getServiceId());
				mmsReadySend.setTos(userPhoneNumber);
				//tunnelType 设置死 走网关0
				mmsReadySend.setTunnelType(0);
				mbnMmsReadysendIService.insert(mmsReadySend);
			}else{
				entityMap.put("flag", false);
	            entityMap.put("message", "彩信通道不存在！");
	            return SUCCESS;
			}
			
			entityMap.put("flag", true);
            entityMap.put("message", "彩信保存成功！");
		}catch(Exception e){
			e.printStackTrace();
			entityMap.put("flag", false);
            entityMap.put("message", "系统出现问题,稍后请重试！");
		}
		return SUCCESS;
	}
	
	private String buildXMLData(String[] imageArray, String[] musicArray, String[] contentFileName){
		MultiframeMmsXml mmsXml = new MultiframeMmsXml();
		for( int i = 0; i < contentFileName.length; i++ ){
			mmsXml.addFrame( i < imageArray.length?imageArray[i]:"", i<musicArray.length?musicArray[i]:"", 
					i<contentFileName.length?contentFileName[i]:"");
		}
		return mmsXml.toString();
	}
	private String contentToFile(String content){
		Long fileOrderNumber = PinGen.getSerialPin();
		String destFileName  = fileOrderNumber.toString() + ".txt";
		String destDir = this.request.getSession().getServletContext().getRealPath(
				fileDirectory);
		try{
			if (!new File(destDir).exists()) {
				new File(destDir).mkdirs();
			}
			if (!destDir.endsWith(File.separator)) {
				destDir += File.separator;
			}
			String destFilePath = destDir + destFileName;
			
			File outputFile = new File(destFilePath);
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.write(java.net.URLDecoder.decode(content,"UTF-8"));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destFileName;
	}
	
	@Action(value = "writeMms", results = {
			@Result(name = SUCCESS, location = "/mms/newspaper/jsp/mms_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeMms() {
		return SUCCESS;
	}
	/**
	 * @param page,rows,...
	 * @description 提供查询分页/模糊查询分页
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "listMms", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listMms(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
                        //设定只查询自己发送的信息
                        boolean isQuerySelf = false;
                        if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                            isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                            if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                                pageUtil.setCreateBy(u.getId());
                            }
                        }
			pageUtil.setMerchantPin(u.getMerchantPin());
			Page page = mbnMmsReadysendIService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnMmsReadysendVO> datas = (List<MbnMmsReadysendVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<MbnMmsReadysendVO>();
	            }
	            entityMap.put("rows", datas);
	            entityMap.put("totalrecords", page.getTotal());
	            entityMap.put("currpage", page.getStart());
			}
        }catch(Exception e){
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	
	/**
	 * @description 上传图片操作
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "uploadPic", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String uploadPic(){
		entityMap = new HashMap<String, Object>();
        long start = System.currentTimeMillis();
        try {
            if ( picture == null){
                    //|| (!picFileName.endsWith(".xls") && !picFileName.endsWith(".xlsx") && !picFileName.endsWith(".csv"))) {
            	entityMap.put("flag", false);
                entityMap.put("message", "上传图片文件格式错误！");
            } else {
            	Long fileId = PinGen.getSerialPin();
            	String tempFileName = fileId.toString() + pictureFileName.substring(pictureFileName.indexOf("."));
            	String destDir = this.request.getSession().getServletContext().getRealPath(
						fileDirectory);
                // 先要将文件上传至服务器，然后才能读取。因为input输入框对服务器是隐藏的
            	String[] fileItem = FileUploadUtil.upload(this.getRequest(),
            			picture, tempFileName, destDir);
    			if (fileItem == null || fileItem.length != 3) {
    				entityMap.put("flag", false);
    				entityMap.put("resultMsg", "您没上传");
    				long end = System.currentTimeMillis();
    				logger.info("parse contact file,totally use " + (end - start)
    						+ " millseconds.");
    				return SUCCESS;
    			}
                //String filePath = fileItem[0] + fileItem[1];
                //session.setAttribute("filePath", filePath);
                filePro = fileItem;
                filePro[0] = pictureFileName;
                entityMap.put("flag", true);
                entityMap.put("filePro", filePro);
                entityMap.put("message", "上传图片成功！");
            }
        } catch (Exception e) {
        	entityMap.put("flag", false);
            entityMap.put("message", "上传图片错误！");
            e.printStackTrace();
            long end = System.currentTimeMillis();
            logger.info("error when parse contact file,totally use " + (end - start) + " millseconds.");
            return SUCCESS;
        }
        return SUCCESS;
	}
	/**
	 * @description 上传图片操作
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "uploadMusic", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String uploadMusic(){
		entityMap = new HashMap<String, Object>();
        long start = System.currentTimeMillis();
        try {
            if (music == null){
                    //|| (!picFileName.endsWith(".xls") && !picFileName.endsWith(".xlsx") && !picFileName.endsWith(".csv"))) {
            	entityMap.put("flag", false);
                entityMap.put("message", "上传音频文件格式错误！");
            } else {
            	Long fileId = PinGen.getSerialPin();
            	String tempFileName = fileId.toString() + musicFileName.substring(musicFileName.indexOf("."));
            	String destDir = this.request.getSession().getServletContext().getRealPath(
						fileDirectory);
                // 先要将文件上传至服务器，然后才能读取。因为input输入框对服务器是隐藏的
            	String[] fileItem = FileUploadUtil.upload(this.getRequest(),
            			music, tempFileName, destDir);
    			if (fileItem == null || fileItem.length != 3) {
    				entityMap.put("flag", false);
    				entityMap.put("message", "您没上传");
    				long end = System.currentTimeMillis();
    				logger.info("parse contact file,totally use " + (end - start)
    						+ " millseconds.");
    				return SUCCESS;
    			}
//                String filePath = fileItem[0] + fileItem[1];
//                //session.setAttribute("filePath", filePath);
//                logger.info("==========================filePath====================================" + filePath);
//                File file = new File(filePath);
//                DataImport di = new DataImport(file);
//                String[][] content = di.getFileContent(false);
    			filePro = fileItem;
    			filePro[0] = musicFileName;
                entityMap.put("flag", true);
                entityMap.put("filePro", filePro);
                entityMap.put("message", "上传音频文件成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityMap.put("flag", false);
            entityMap.put("message", "上传音频文件错误！");
            long end = System.currentTimeMillis();
            logger.info("error when parse contact file,totally use " + (end - start) + " millseconds.");
            return SUCCESS;
        }
        return SUCCESS;
	}
	public File getMusic() {
		return music;
	}
	public void setMusic(File music) {
		this.music = music;
	}
	public String getMusicFileName() {
		return musicFileName;
	}
	public void setMusicFileName(String musicFileName) {
		this.musicFileName = musicFileName;
	}
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public String[] getFilePro() {
		return filePro;
	}
	public void setFilePro(String[] filePro) {
		this.filePro = filePro;
	}

	public String getMmsReceiver() {
		return mmsReceiver;
	}

	public void setMmsReceiver(String mmsReceiver) {
		this.mmsReceiver = mmsReceiver;
	}

	public String getMmsTitle() {
		return mmsTitle;
	}

	public void setMmsTitle(String mmsTitle) {
		this.mmsTitle = mmsTitle;
	}

	public String[] getImageArray() {
		return imageArray;
	}

	public void setImageArray(String[] imageArray) {
		this.imageArray = imageArray;
	}

	public String[] getMusicArray() {
		return musicArray;
	}

	public void setMusicArray(String[] musicArray) {
		this.musicArray = musicArray;
	}

	public String[] getContentArray() {
		return contentArray;
	}

	public void setContentArray(String[] contentArray) {
		this.contentArray = contentArray;
	}

	public String getImageArrayString() {
		return imageArrayString;
	}

	public void setImageArrayString(String imageArrayString) {
		this.imageArrayString = imageArrayString;
	}

	public String getMusicArrayString() {
		return musicArrayString;
	}

	public void setMusicArrayString(String musicArrayString) {
		this.musicArrayString = musicArrayString;
	}

	public String getContentArrayString() {
		return contentArrayString;
	}

	public void setContentArrayString(String contentArrayString) {
		this.contentArrayString = contentArrayString;
	}

	public Double getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(Double attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public String getSmsIds() {
		return smsIds;
	}

	public void setSmsIds(String smsIds) {
		this.smsIds = smsIds;
	}
	
}
