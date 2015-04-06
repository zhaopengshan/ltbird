package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import java.util.Vector;



public class UnsuccessSMEAddressList
{
    Vector m_list;

    public UnsuccessSMEAddressList()
    {
        m_list = new Vector();
    }

    public void empty()
    {
        m_list.removeAllElements();
    }

    public int size()
    {
        return m_list.size();
    }

    public void setSize(int nSize)
    {
        m_list.setSize(nSize);
    }

    public void add(UnsuccessSMEAddress address)
    {
        m_list.addElement(address);
    }

    public UnsuccessSMEAddress get(int nIndex)
    {
        return (UnsuccessSMEAddress)m_list.elementAt(nIndex);
    }

    public void delete(int nIndex)
    {
        m_list.removeElementAt(nIndex);
    }

    public boolean isValid()
    {
        for(int i = 0; i < m_list.size(); i++)
        {
            UnsuccessSMEAddress da = (UnsuccessSMEAddress)m_list.elementAt(i);
            if(!da.isValid())
            {
                Log.log("SMPPSubmitMulti.isValid : invalid destinationes[" + i + "] !", 0x100600000000000L);
                return false;
            }
        }

        return true;
    }

    public void dump(long lMethod)
    {
        for(int i = 0; i < m_list.size(); i++)
        {
            UnsuccessSMEAddress da = (UnsuccessSMEAddress)m_list.elementAt(i);
            da.dump(lMethod);
        }

    }

    public void wrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("UnsuccessSMEAddressList.wrap : wrap elements !", 0x100800000000000L);
        for(int i = 0; i < m_list.size(); i++)
        {
            UnsuccessSMEAddress da = (UnsuccessSMEAddress)m_list.elementAt(i);
            da.wrap(packet);
        }

    }

    public void unwrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("UnsuccessSMEAddressList.unwrap : unwrap elements !", 0x100800000000000L);
        for(int i = 0; i < m_list.size(); i++)
        {
            UnsuccessSMEAddress da = new UnsuccessSMEAddress();
            da.unwrap(packet);
            m_list.setElementAt(da, i);
        }

    }


}