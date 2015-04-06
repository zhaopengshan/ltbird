package com.leadtone.mas.register.remote;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.lang.StringUtils;

import cn.j2eebestpractice.ssiframework.util.SpringUtil;

import com.leadtone.mas.register.bean.Lisence;
import com.leadtone.mas.register.bean.LisenceVO;
import com.leadtone.mas.register.dao.LisenceDao;
import com.leadtone.mas.register.utils.MacGetterUtil;
import com.leadtone.mas.register.utils.Xml_Bean;
public class RpcRegisterImpl implements RpcRegisterIntf {
	private LisenceDao lisenceDao;
	public String productRegister(String mxlStr){
		Lisence tempLisence = null;
		String lisenceXml = null;
		LisenceVO lisenceVO = new LisenceVO();
		try {
			lisenceDao = (LisenceDao)SpringUtil.getApplicationContext().getBean("lisenceDao");
			tempLisence = (Lisence) Xml_Bean.xml2java(Lisence.class, mxlStr);
			// 
			if(!StringUtils.isEmpty(tempLisence.getUserKey()) && !StringUtils.isEmpty(tempLisence.getUserName()) && !StringUtils.isEmpty(tempLisence.getUserPwd()) ){
				//保存key。
				String userKey = tempLisence.getUserKey();
				tempLisence.setUserKey(null);
				//查询用户lisence是否存在
				List<Lisence> lisenceList = lisenceDao.getAllLisence(tempLisence);
				if(lisenceList!=null && lisenceList.size()>0){
					tempLisence = lisenceList.get(0);
					String dbLisence = tempLisence.getLisenceValue();
					if(dbLisence==null||dbLisence.trim().equals("")){
						String lisence = MacGetterUtil.getLisenceByMac(userKey);
						if(lisence.equals("error")){
							lisenceVO.setLisenceValue("您输入的Key不合法，请输入产品提供的15位Key或联系客服 wuchuanzhong@leadtone.com！");
						}else{
							tempLisence.setUserKey(userKey);
							tempLisence.setLisenceValue(lisence);
							tempLisence.setUpdateTime(new Date());
							lisenceDao.update(tempLisence);
							//resp
							lisenceVO.setLisenceId(tempLisence.getLisenceId());
							lisenceVO.setLisenceValue(tempLisence.getLisenceValue());
//							entityMap.put("message", "激活成功，如有其他问题请联系客服 wuchuanzhong@leadtone.com！");
						}
					}else{
						lisenceVO.setLisenceValue("用户已经激活过，如有其他问题请联系客服 wuchuanzhong@leadtone.com！");
					}
				}else{
					lisenceVO.setLisenceValue("此用户不存在或密码不正确！");
				}
			}else{
				lisenceVO.setLisenceValue("用户名、密码、密钥不允许为空！");
			}
			lisenceXml = Xml_Bean.java2xml(lisenceVO);
			System.out.println(lisenceXml);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lisenceXml;
	}
	public LisenceDao getLisenceDao() {
		return lisenceDao;
	}
	public void setLisenceDao(LisenceDao lisenceDao) {
		this.lisenceDao = lisenceDao;
	}
}
