package com.leadtone.hostmas.smsapp.domain;

public class Constants {

	public static final String ADD_DATA = "addConfig";
	
	public static final String DELETE_DATA = "deleteConfig";
	
	public static final String QUERY_DATA = "queryConfig";
	
	public static final String UPDATE_DATA = "updateConfig";
	
	public static final String MM7_PROCOTOL = "mm7";
	
	public static final int TUNNEL_MM7 = 10000;
	
	public static final int TUNNEL_CMPP = 0;
	
	public static final int LISTEN_PORT = 7000;
	
	public static String[] TELNET_COMMAND = {"telnet 192.168.5.35 5640","quit"};
	
	public static String START_SMSAPP_SERVICE= "service smsapp2 start ";
	
	public static String STOP_SMSAPP_SERVICE= "service smsapp2 stop ";
	
	public static String APP_SERVICE_RUNNING= "1";
	
	public static String APP_SERVICE_STOPPING= "0";
}
