package LeadTone.Packet.SMPPPacket;

import LeadTone.*;
import java.io.UnsupportedEncodingException;


public class OptionalParameter
{
    public int tag;
    public int length;
    public byte value[];

    public OptionalParameter()
    {
        tag = 0;
        length = 0;
        value = null;
    }

    public OptionalParameter(int tag)
    {
        this.tag = 0;
        length = 0;
        value = null;
        this.tag = tag;
    }

    public boolean isValid()
    {
        if(length <= 0 || length >= 512)
        {
            Log.log("OptionalParameter.wrap : invalid length !", 0x100600000000000L);
            return false;
        }
        if(value == null || value.length != length)
        {
            Log.log("OptionalParameter.wrap : invalid valid !", 0x100600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void setByte(byte bValue)
    {
        length = 1;
        value = new byte[length];
        value[0] = bValue;
    }

    public byte getByte()
        throws OptionalParameterException
    {
        if(length != 1)
        {
            throw new OptionalParameterException("OptionalParameter.getByte : invalid length !");
        } else
        {
            byte bValue = value[0];
            return bValue;
        }
    }

    public void setShort(short sValue)
    {
        length = 2;
        value = new byte[length];
        for(int i = 0; i < length; i++)
            value[length - i - 1] = (byte)(sValue >> i * 8 & 0xff);

    }

    public short getShort()
        throws OptionalParameterException
    {
        if(length != 2)
            throw new OptionalParameterException("OptionalParameter.getShort : invalid length !");
        short sValue = 0;
        for(int i = 0; i < 2; i++)
        {
            sValue <<= 8;
            sValue |= value[i] & 0xff;
        }

        return sValue;
    }

    public void setInteger(int nValue)
    {
        length = 4;
        value = new byte[length];
        for(int i = 0; i < length; i++)
            value[length - i - 1] = (byte)(nValue >> i * 8 & 0xff);

    }

    public int getInteger()
        throws OptionalParameterException
    {
        if(length != 4)
            throw new OptionalParameterException("OptionalParameter.getInteger : invalid length !");
        int nValue = 0;
        for(int i = 0; i < 4; i++)
        {
            nValue <<= 8;
            nValue |= value[i] & 0xff;
        }

        return nValue;
    }

    public void setLong(long lValue)
    {
        length = 8;
        value = new byte[length];
        for(int i = 0; i < length; i++)
            value[length - i - 1] = (byte)(int)(lValue >> i * 8 & 255L);

    }

    public long getLong()
        throws OptionalParameterException
    {
        if(length != 8)
            throw new OptionalParameterException("OptionalParameter.getLong : invalid length !");
        long lValue = 0L;
        for(int i = 0; i < 8; i++)
        {
            lValue <<= 8;
            lValue |= value[i] & 0xff;
        }

        return lValue;
    }

    public void setBytes(byte bBytes[])
    {
        length = (short)(bBytes != null ? bBytes.length : 0);
        value = bBytes;
    }

    public byte[] getBytes()
    {
        return value;
    }

    public void setString(String strValue)
        throws UnsupportedEncodingException
    {
        value = strValue.getBytes("ISO8859-1");
        length = (short)(value != null ? value.length : 0);
    }

    public String getString()
    {
        if(value == null || value.length <= 0)
            return null;
        else
            return new String(value);
    }

    public void dump(long lMethod)
    {
        Log.log("\ttag = 0x" + Utility.toHexString(tag) + " (" + OptionalParameterTag.toString(tag) + ")", 0x100000000000000L | lMethod);
        Log.log("\tlength = " + length, 0x100000000000000L | lMethod);
        Log.log("\tvalue(binary) = 0x" + Utility.toHexString(value), 0x100000000000000L | lMethod);
    }

    public void wrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("OptionalParameter.wrap : wrap elements !", 0x100800000000000L);
        dump(0x800000000000L);
        packet.addShort((short)(tag & 0xffff));
        packet.addShort((short)(length & 0xffff));
        packet.addBytes(value);
    }

    public void unwrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("OptionalParameter.unwrap : unwrap elements !", 0x100800000000000L);
        tag = packet.getShort();
        length = packet.getShort();
        value = packet.getBytes(length);
        dump(0x800000000000L);
    }


}