package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Packet.CMPPPacket.CMPPPacket;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import LeadTone.Port.CMPPXMLExchanger;

/**
 * 系统设计中将数据库表结构设计为参照CMPP标准协议实现，这样保证表结构的统一性，也避免字段的多意性，
 * 但是系统同时支持多种多种协议的发送与接收（例如SMPP、SGIP、CNGP以及不同厂商的非标准实现差异），
 * 因此消息数据在对网关处理和本地持久化之间就要进行必要的消息格式转换，此类主要就实现消息持久化，
 * 或从数据库提取进行处理的功能，具体消息格式转换工作交由子类，根据各自的协议差异完成转换
 */
public class DataExchanger extends Engine
{
    /**
     * 配合m_nID完成线程号唯一性标识
     */
    static int m_nUniqueID = 1;
    /**
     * 消息转换线程的线程号，用于多线程时
     */
    public int m_nID;
    /**
     * 数据库操作对象
     */
    CMPPDatabase m_database;
    /**
     * 用于系统和外部的基于XML的接口，实现消息对象到XML，XML到消息对象的转换，属于一种特殊的消息转换
     */
    CMPPXMLExchanger m_exchanger;
    /**
     * 消息对象映射，用于临时存放
     */
    CMPPPacket m_packet;

    /**
     * 构造方法初始化类变量
     * @param database
     * @param exchanger
     */
    public DataExchanger(CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super("DataExchanger");
        m_database = null;
        m_exchanger = null;
        m_packet = null;
        m_nID = m_nUniqueID;
        m_nUniqueID++;
        m_database = database;
        m_exchanger = exchanger;
    }

    /**
     * 将从基于XML的外部接口获取的消息取出并放入数据库操作的消息队列，等待持久化，
     * 子类中实现不同协议的消息转换完毕后放入数据库持久化的功能
     */
    public void toDatabase()
    {
        //将从基于XML的外部接口获取的消息取出
        if(m_packet == null)
            m_packet = (CMPPPacket)m_exchanger.m_input.pop();
        else
        //放入数据库操作的消息队列，等待持久化
        if(m_database.m_input.push(m_packet))
            m_packet = null;
    }



}
