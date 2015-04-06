package com.leadtone.mas.connector.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public abstract class XmlUtils {
	private static final Log logger = LogFactory.getLog(XmlUtils.class);

	public static String formatXml(String xml) {
		Document xmlDoc = parseText(xml);
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("UTF-8");
			xmlWriter.write(xmlDoc);
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			if (xmlWriter != null) {
				try {
					xmlWriter.close();
				} catch (IOException e) {
					
				}
			}
		}
		return writer.toString();
	}

	public static Document parseText(String xmlstr) {
		try {
			return DocumentHelper.parseText(xmlstr);
		} catch (DocumentException ex) {
			logger.error("", ex);
		}
		return null;
	}

	
	public static Document buildDocument(InputStream is) {
		SAXReader reader = new SAXReader();
		try {
			return reader.read(is);
		} catch (DocumentException ex) {
			logger.error("", ex);
		}
		return null;
	}
	
	
	public static Document buildDocument(File file) {
		SAXReader reader = new SAXReader();
		try {
			return reader.read(file);
		} catch (DocumentException ex) {
			logger.error("", ex);
		}
		return null;
	}

	
	public static Element rootElement(InputStream is) {
		Document document = buildDocument(is);
		return document == null ? null : document.getRootElement();
	}

	
	@SuppressWarnings("rawtypes")
	public static List selectNodes(Document document, String xpath) {
		return document.selectNodes(xpath);
	}

	/**
	 * 
	 * @param <T>
	 * @param document
	 * @param xpath
	 * @param nodelet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> selectNodes(Document document, String xpath, Nodelet<T> nodelet) {
		List<T> resultList = new ArrayList<T>();
		List<Node> nodeList = document.selectNodes(xpath);
		for (Node node : nodeList) {
			resultList.add(nodelet.processNode(node));
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public static List<Node> selectNodes(Node node, String xpath) {
		return node.selectNodes(xpath);
	}

	/**
	 * 
	 * @param <T>
	 * @param node
	 * @param xpath
	 * @param nodelet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> selectNodes(Node node, String xpath, Nodelet<T> nodelet) {
		List<T> resultList = new ArrayList<T>();
		List<Node> nodeList = node.selectNodes(xpath);
		for (Node xmlNode : nodeList) {
			resultList.add(nodelet.processNode(xmlNode));
		}
		return resultList;
	}

	
	public static <T> T selectSingleNode(Document document, String xpath, Nodelet<T> nodelet) {
		Node singleNode = document.selectSingleNode(xpath);
		return nodelet.processNode(singleNode);
	}

	
	public static <T> T selectSingleNode(Node node, String xpath, Nodelet<T> nodelet) {
		Node singleNode = node.selectSingleNode(xpath);
		return nodelet.processNode(singleNode);
	}

	
	public static String getText(Document document, String xpath) {
		Node xmlNode = document.selectSingleNode(xpath);
		return xmlNode == null ? "" : xmlNode.getText();
	}

	/**
	 * 
	 * @param node
	 * @param xpath
	 * @return
	 */
	public static String getText(Node node, String xpath) {
		Node xmlNode = node.selectSingleNode(xpath);
		return xmlNode == null ? "" : xmlNode.getText();
	}

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static Map<String, String> xml2Map(Document document) {
		return xml2Map(document, false);
	}

	/**
	 * 
	 * @param document
	 * @param isProcessCommentNode
	 * @return
	 */
	public static Map<String, String> xml2Map(Document document, boolean isProcessCommentNode) {
		return xml2Map(document.getRootElement(), isProcessCommentNode);
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public static Map<String, String> xml2Map(Element element) {
		return xml2Map(element, false);
	}

	/**
	 * 
	 * @param element
	 * @param isProcessCommentNode
	 * @return
	 */
	public static Map<String, String> xml2Map(Element element, boolean isProcessCommentNode) {
		return xml2Map(element, null, isProcessCommentNode);
	}

	public static Map<String, String> xml2Map(Element element, String prefixPath,
			boolean isProcessCommentNode) {
		Map<String, String> xmlMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(prefixPath)) {
			prefixPath = element.getName();
		} else {
			prefixPath += "." + element.getName();
		}
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node childNode = element.node(i);
			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				xmlMap.putAll(xml2Map(childElement, prefixPath, isProcessCommentNode));
			} else if (childNode instanceof Comment && isProcessCommentNode) {
				xmlMap.put(prefixPath + ".comment", childNode.getText());
			}
		}
		xmlMap.put(prefixPath, element.getTextTrim());
		for (int m = 0, size = element.attributeCount(); m < size; m++) {
			Attribute attr = element.attribute(m);
			xmlMap.put(prefixPath + "." + attr.getName(), attr.getValue());
		}
		return xmlMap;
	}

	
	public static Map<String, String> xml2MapNoPath(Element element) {
		Map<String, String> dataMap = new HashMap<String, String>();
		int childrenCount = element.nodeCount();

		for (int i = 0, size = childrenCount; i < size; i++) {
			Node childNode = element.node(i);
			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				dataMap.putAll(xml2MapNoPath(childElement));
			}
		}
		
		dataMap.put(element.getName(), element.getTextTrim());
		
		return dataMap;
	}


	public static Map<String, String> xmlElement2Map(Element element, List<String> exclusionNodes) {
		Map<String, String> xmlMap = new HashMap<String, String>();
		int childrenCount = element.nodeCount();

		for (int i = 0; i < childrenCount; i++) {
			Node childNode = element.node(i);
			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				String elementName = childElement.getName();
				if (exclusionNodes == null || !exclusionNodes.contains(elementName)) {
					xmlMap.put(elementName, childElement.getTextTrim());
				}
			}
		}
		return xmlMap;
	}


	public static String map2Xml(Map<String, String> map) {
		if (map == null || map.size() == 0) {
			return null;
		}
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<properties>");
		for (Entry<String, String> entry : map.entrySet()) {
			xml.append("<property name=\"").append(entry.getKey());
			xml.append("\" value=\"").append(entry.getValue());
			xml.append("\"/>");
		}
		xml.append("</properties>");
		return xml.toString();
	}

	
	public static Map<String, String> xmlStr2Map(String xmlStr) {
		if (StringUtils.isBlank(xmlStr)) {
			return Collections.emptyMap();
		}
		final Map<String, String> xmlMap = new HashMap<String, String>();
		Document xmldoc = XmlUtils.parseText(xmlStr);
		XmlUtils.selectNodes(xmldoc, "//Body", new Nodelet<String>() {

			public String processNode(Node node) {
				Element element = (Element) node;
				xmlMap.put(element.attributeValue("name"), element.attributeValue("value"));
				return null;
			}
		});
		return xmlMap;
	}
}
