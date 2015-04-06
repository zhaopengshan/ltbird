package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;
import java.util.Vector;


public class OptionalParameterList
{
    Vector m_list;

    public OptionalParameterList()
    {
        m_list = new Vector();
    }

    public void empty()
    {
        m_list.removeAllElements();
    }

    public void add(OptionalParameter op)
    {
        m_list.addElement(op);
    }

    public OptionalParameter get(int nIndex)
    {
        return (OptionalParameter)m_list.elementAt(nIndex);
    }

    public void delete(int nIndex)
    {
        m_list.removeElementAt(nIndex);
    }

    public OptionalParameter getByTag(int tag)
    {
        for(int i = 0; i < m_list.size(); i++)
        {
            OptionalParameter op = (OptionalParameter)m_list.elementAt(i);
            if(op.tag == tag)
                return op;
        }

        return null;
    }

    public void deleteByTag(int tag)
    {
        for(int i = 0; i < m_list.size(); i++)
        {
            OptionalParameter op = (OptionalParameter)m_list.elementAt(i);
            m_list.removeElementAt(i);
        }

    }

    public void setByte(int tag, byte bValue)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
        {
            op = new OptionalParameter(tag);
            m_list.addElement(op);
        }
        op.setByte(bValue);
    }

    public byte getByte(int tag)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
            throw new OptionalParameterException("OptionalParameterList.getByte : optional parameter not found !");
        else
            return op.getByte();
    }

    public void setShort(int tag, short sValue)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
        {
            op = new OptionalParameter(tag);
            m_list.addElement(op);
        }
        op.setShort(sValue);
    }

    public short getShort(int tag)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
            throw new OptionalParameterException("OptionalParameterList.getShort : optional parameter not found !");
        else
            return op.getShort();
    }

    public void setInteger(int tag, int nValue)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
        {
            op = new OptionalParameter(tag);
            m_list.addElement(op);
        }
        op.setInteger(nValue);
    }

    public int getInteger(int tag)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
            throw new OptionalParameterException("OptionalParameterList.getInteger : optional parameter not found !");
        else
            return op.getInteger();
    }

    public void setLong(int tag, long lValue)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
        {
            op = new OptionalParameter(tag);
            m_list.addElement(op);
        }
        op.setLong(lValue);
    }

    public long getLong(int tag)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
            throw new OptionalParameterException("OptionalParameterList.getLong : optional parameter not found !");
        else
            return op.getLong();
    }

    public void setBytes(int tag, byte bBytes[])
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
        {
            op = new OptionalParameter(tag);
            m_list.addElement(op);
        }
        op.setBytes(bBytes);
    }

    public byte[] getBytes(int tag)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
            throw new OptionalParameterException("OptionalParameterList.getBytes : optional parameter not found !");
        else
            return op.getBytes();
    }

    public void setString(int tag, String strValue)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
        {
            op = new OptionalParameter(tag);
            m_list.addElement(op);
        }
        try
        {
            op.setString(strValue);
        }
        catch(Exception e)
        {
            throw new OptionalParameterException("OptionalParameterList.setString : unsupported encoding !");
        }
    }

    public String getString(int tag)
        throws OptionalParameterException
    {
        OptionalParameter op = getByTag(tag);
        if(op == null)
            throw new OptionalParameterException("OptionalParameterList.getString : optional parameter not found !");
        else
            return op.getString();
    }

    public void dump(long lMethod)
    {
        for(int i = 0; i < m_list.size(); i++)
        {
            OptionalParameter op = (OptionalParameter)m_list.elementAt(i);
            op.dump(lMethod);
        }

    }

    public void wrap(SMPPPacket packet)
    {
        Log.log("OptionalParameterList.wrap : wrap optional parameters !", 0x100800000000000L);
        try
        {
            for(int i = 0; i < m_list.size(); i++)
            {
                OptionalParameter op = (OptionalParameter)m_list.elementAt(i);
                op.wrap(packet);
            }

        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2100000000000000L);
            Log.log(e);
            Log.log("OptionalParameterList.wrap : unexpected exit !", 0x2100000000000000L);
        }
    }

    public void unwrap(SMPPPacket packet)
    {
        Log.log("OptionalParameterList.unwrap : unwrap optional parameters !", 0x100800000000000L);
        try
        {
            OptionalParameter op;
            for(; !packet.isEOB(); m_list.addElement(op))
            {
                op = new OptionalParameter();
                op.unwrap(packet);
            }

        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2100000000000000L);
            Log.log("OptionalParameterList.unwrap : unexpected exit !", 0x2100000000000000L);
        }
    }


}