package com.leadtone.mas.register.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author ChangJun
 * 
 */
public class Xml_Bean {

	public static String java2xml(Object o) throws JAXBException,
			XMLStreamException, FactoryConfigurationError {
		JAXBContext context = JAXBContext.newInstance(o.getClass());

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
																 
		StringWriter stringWriter = new StringWriter();
		XMLStreamWriter writer = XMLOutputFactory.newInstance()
				.createXMLStreamWriter(stringWriter);

		marshaller.marshal(o, writer);

		return stringWriter.getBuffer().toString();
	}

	public static Object xml2java(Class<?> classType, String xmlStr)
			throws JAXBException, XMLStreamException, FactoryConfigurationError {
		JAXBContext context = JAXBContext.newInstance(classType);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		StringReader stringReader = new StringReader(xmlStr);
		XMLStreamReader reader = XMLInputFactory.newInstance()
				.createXMLStreamReader(stringReader);

		return unmarshaller.unmarshal(reader);
	}

	
}
