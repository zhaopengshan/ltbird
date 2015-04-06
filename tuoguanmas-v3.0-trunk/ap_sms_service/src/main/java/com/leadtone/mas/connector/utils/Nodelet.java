package com.leadtone.mas.connector.utils;

import org.dom4j.Node;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public interface Nodelet<T> {
	T processNode(Node node);
}
