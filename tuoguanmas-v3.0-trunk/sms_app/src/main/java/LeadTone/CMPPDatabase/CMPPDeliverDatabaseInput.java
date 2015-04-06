package LeadTone.CMPPDatabase;

import LeadTone.Database.DatabasePool;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;


/**
 * Submit,Deliver,Query三种类型的输入进程均继承自CMPPDatabaseInput，所有的输入进程统一处理
 * 所有上行，根据不同的消息类型再分发处理，CMPPDatabaseInput中包换所有输入处理
 * 的逻辑
 */
public class CMPPDeliverDatabaseInput extends CMPPDatabaseInput
{

    public CMPPDeliverDatabaseInput(int nID, CMPPPacketQueue queue, DatabasePool pool)
    {
        super(nID, queue, pool);
    }
}