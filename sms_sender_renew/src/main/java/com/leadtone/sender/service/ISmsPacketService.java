package com.leadtone.sender.service;

public interface ISmsPacketService {
	public String[] packet(String msgContent,int signLenth);

	public String[] emppPacket(String content);
}
