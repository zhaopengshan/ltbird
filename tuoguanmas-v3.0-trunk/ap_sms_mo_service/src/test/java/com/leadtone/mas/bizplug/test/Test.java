package com.leadtone.mas.bizplug.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	protected static String filterPhoneNum(String phoneNum) {

		Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
		Matcher m1 = p1.matcher(phoneNum);
		if (m1.matches()) {
			Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
			Matcher m2 = p2.matcher(phoneNum);
			StringBuffer sb = new StringBuffer();
			while (m2.find()) {
				m2.appendReplacement(sb, "");
			}
			m2.appendTail(sb);
			return sb.toString();

		} else {
			System.out.println("The format of phoneNum " + phoneNum
					+ "  is not correct!Please correct it");
		}
		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "+8615911119999";
		String ii = filterPhoneNum(str);
		System.out.println(ii);

//		ClassPathResource resource = new ClassPathResource("spring-ap-sms-mo.xml");
//		System.out.println(resource.getPath() +"++++++"+resource.getFilename());
//
//		BeanFactory beanFactory = new XmlBeanFactory(resource);
//		// TODO Auto-generated method stub
//		BeanFactory beanFactory = new XmlBeanFactory(new Resource() {
//			@Override
//			public InputStream getInputStream() throws IOException {
//				FileInputStream fio = new FileInputStream(new File("E:\\Workspace\\MAS3.0\\ap_sms_mo_service\\src\\main\\resources\\spring-ap-sms.xml"));
//				return fio;
//			}
//
//			@Override
//			public long contentLength() throws IOException {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//			@Override
//			public Resource createRelative(String relativePath)
//					throws IOException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public boolean exists() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public String getDescription() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public File getFile() throws IOException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public String getFilename() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public URI getURI() throws IOException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public URL getURL() throws IOException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public boolean isOpen() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean isReadable() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public long lastModified() throws IOException {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		});
//
//		MenuService smc = (MenuService)beanFactory.getBean("menuService");
//		System.out.println("###" + smc.findTopMenus().size());

	}

}
