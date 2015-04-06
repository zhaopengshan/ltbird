package com.leadtone.mas.bizplug.mms.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class MultiframeMmsXml {

	Document document;
	Element smil;
	Element head;
	Element layout;
	Element body;
	boolean imageMms = false;
	boolean textMms = false;
	boolean audioMms = false;

	public MultiframeMmsXml(){
		document = DocumentHelper.createDocument(); 
		//增加主节点
	    smil = document.addElement("smil");
		smil.addAttribute("xmlns", "http://www.w3.org/2000/SMIL20/CR/Language");
		//增加head节点
		head = smil.addElement("head");
		//增加 head/layout
		layout = head.addElement("layout");
		//增加 head/layout/root-layout
		Element rootLayout = layout.addElement("root-layout");
		rootLayout.addAttribute("width", "100%");
		rootLayout.addAttribute("height", "100%");
		rootLayout.addAttribute("font-size", "8");
		rootLayout.addAttribute("title", "My SMIL message");
		//增加body
		body = smil.addElement("body");

	}
	
	public void addFrame( String imageSrc, String musicSrc, String textSrc){
		//js 页面处理 不可以有空帧,此处不做判断
		try{
			Element par = body.addElement("par");
			par.addAttribute("dur", "10s");
			if(!imageMms){
				if( imageSrc != null && !imageSrc.equals("") ){
					addHeadImageElement();
				}
			}
			if(!textMms){
				if( textSrc != null && !textSrc.equals("") ){
					addHeadTextElement();
				}
			}
			if(!audioMms){
				if( musicSrc != null && !musicSrc.equals("") ){
					addHeadAudioElement();
				}
			}
			if( imageSrc != null && !imageSrc.equals("") ){
				Element img = par.addElement("img");
				img.addAttribute("src", imageSrc);
				img.addAttribute("region", "Image1");
			}
			
			if( musicSrc != null && !musicSrc.equals("") ){
				Element audio = par.addElement("audio");
				audio.addAttribute("src", musicSrc);
			}
			
			if( textSrc != null && !textSrc.equals("") ){
				Element text = par.addElement("text");
				text.addAttribute("src", textSrc);
				text.addAttribute("region", "Text1");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片彩信，head添加一次
	 */
	private void addHeadImageElement(){
		Element region = layout.addElement("region");
		region.addAttribute("id", "Image1");
		region.addAttribute("width", "100%");
		region.addAttribute("height", "50%");
		region.addAttribute("fit", "meet");
		region.addAttribute("left", "0");
		region.addAttribute("top", "0");
		this.imageMms = true;
	}
	/**
	 * 文本彩信，head添加一次
	 */
	private void addHeadTextElement(){
		Element region = layout.addElement("region");
		region.addAttribute("id", "Text1");
		region.addAttribute("width", "100%");
		region.addAttribute("height", "50%");
		region.addAttribute("fit", "hidden");
		region.addAttribute("left", "0");
		region.addAttribute("top", "50%");
		this.textMms = true;
	}
	/**
	 * 音频彩信
	 */
	private void addHeadAudioElement(){
		this.audioMms = true;
	}
	/* 生成报文
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if( imageMms || textMms || audioMms){
			return document.asXML();
		}else{
			return "";
		}
		
	}
}
