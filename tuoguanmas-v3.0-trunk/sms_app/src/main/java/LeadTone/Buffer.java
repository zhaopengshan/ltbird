package LeadTone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;



public class Buffer
{
    public static final int MAX_BUFFER_SIZE = 2048;
    public static final int NETWORK_BYTE_ORDER = 0;
    public static final int PC_HOST_BYTE_ORDER = 1;
    public static final int SIZEOF_BYTE = 1;
    public static final int SIZEOF_SHORT = 2;
    public static final int SIZEOF_INTEGER = 4;
    public static final int SIZEOF_LONG = 8;
    public static final int m_nByteOrder = 1;
    

    public int m_nOffset;

    public int m_nLength;

    public byte m_bBytes[];

    public Buffer()
    {
        m_nOffset = 0;
        m_nLength = 0;
        m_bBytes = null;
        m_nOffset = 0;
        m_nLength = 0;
        m_bBytes = new byte[2048];
    }

    public Buffer(Buffer buffer)
    {
        m_nOffset = 0;
        m_nLength = 0;
        m_bBytes = null;
        m_nOffset = buffer.m_nOffset;
        m_nLength = buffer.m_nLength;
        m_bBytes = buffer.m_bBytes;
    }

    public void reset()
    {
        m_nLength = 0;
        m_nOffset = 0;
    }

    public void empty()
    {
        reset();
        m_bBytes = null;
    }

    public boolean isEOB()
    {
        return m_nOffset >= m_nLength;
    }

   
    public void setLength(int nLength)
        throws BufferException
    {
        if(nLength < 0 || nLength > 2048)
        {
            throw new BufferException("Buffer.setLength : not proper length !");
        } else
        {
            m_nLength = nLength;
            return;
        }
    }

   
    public void setOffset(boolean bAbsolute, int nOffset)
        throws BufferException
    {
        if(bAbsolute)
        {
            if(nOffset < 0 || nOffset > m_nLength)
                throw new BufferException("Buffer.setOffset : not proper absolute offset !");
            m_nOffset = nOffset;
        } else
        {
            if(m_nOffset + nOffset < 0 || m_nOffset + nOffset > m_nLength)
                throw new BufferException("Buffer.setOffset : not proper relative offset !");
            m_nOffset += nOffset;
        }
    }

    
    public byte getByte(int nOffset)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 1 > m_nLength)
        {
            throw new BufferException("Buffer.getByte : not proper offset !");
        } else
        {
            byte bValue = m_bBytes[nOffset];
            return bValue;
        }
    }

    
    public void insertByte(int nOffset, byte bValue)
        throws BufferException
    {
        if(m_nLength + 1 > 2048)
            throw new BufferException("Buffer.insertByte : exceed max size !");
        m_nLength++;
        for(int i = m_nLength - 1; i >= nOffset + 1; i--)
            m_bBytes[i] = m_bBytes[i - 1];

        setByte(nOffset, bValue);
    }

   
    public void setByte(int nOffset, byte bValue)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 1 > m_nLength)
        {
            throw new BufferException("Buffer.setByte : not proper offset !");
        } else
        {
            m_bBytes[nOffset] = bValue;
            return;
        }
    }


    public short getShort(int nOffset)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 2 > m_nLength)
            throw new BufferException("Buffer.getShort : not proper offset !");
        short sValue = 0;
        for(int i = 0; i < 2; i++)
        {
            sValue <<= 8;
            sValue |= m_bBytes[nOffset + i] & 0xff;
        }

        return sValue;
    }

    public void setShort(int nOffset, short sValue)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 2 > m_nLength)
            throw new BufferException("Buffer.setShort : not proper offset !");
        for(int i = 0; i < 2; i++)
            m_bBytes[(nOffset + 2) - i - 1] = (byte)(sValue >> i * 8 & 0xff);

    }

    public void insertShort(int nOffset, short sValue)
        throws BufferException
    {
        if(m_nLength + 2 > 2048)
            throw new BufferException("Buffer.insertShort : exceed max size !");
        m_nLength += 2;
        for(int i = m_nLength - 1; i >= nOffset + 2; i--)
            m_bBytes[i] = m_bBytes[i - 2];

        setShort(nOffset, sValue);
    }

    public int getInteger(int nOffset)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 4 > m_nLength)
            throw new BufferException("Buffer.getInteger : not proper offset !");
        int nValue = 0;
        for(int i = 0; i < 4; i++)
        {
            nValue <<= 8;
            nValue |= m_bBytes[nOffset + i] & 0xff;
        }

        return nValue;
    }

    public void setInteger(int nOffset, int nValue)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 4 > m_nLength)
            throw new BufferException("Buffer.setInteger : not proper offset !");
        for(int i = 0; i < 4; i++)
            m_bBytes[(nOffset + 4) - i - 1] = (byte)(nValue >> i * 8 & 0xff);

    }

    public void insertInteger(int nOffset, int nValue)
        throws BufferException
    {
        if(m_nLength + 4 > 2048)
            throw new BufferException("Buffer.insertInteger : exceed max size !");
        m_nLength += 4;
        for(int i = m_nLength - 1; i >= nOffset + 4; i--)
            m_bBytes[i] = m_bBytes[i - 4];

        setInteger(nOffset, nValue);
    }

    public long getLong(int nOffset)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 8 > m_nLength)
            throw new BufferException("Buffer.getLong : not proper offset !");
        long lValue = 0L;
        for(int i = 0; i < 8; i++)
        {
            lValue <<= 8;
            lValue |= m_bBytes[nOffset + i] & 0xff;
        }

        return lValue;
    }

    public void setLong(int nOffset, long lValue)
        throws BufferException
    {
        if(nOffset < 0 || nOffset + 8 > m_nLength)
            throw new BufferException("Buffer.setInteger : not proper offset !");
        for(int i = 0; i < 8; i++)
            m_bBytes[(nOffset + 8) - i - 1] = (byte)(int)(lValue >> i * 8 & 255L);

    }

    public void insertLong(int nOffset, long lValue)
        throws BufferException
    {
        if(m_nLength + 8 > 2048)
            throw new BufferException("Buffer.insertLong : exceed max size !");
        m_nLength += 8;
        for(int i = m_nLength - 1; i >= nOffset + 8; i--)
            m_bBytes[i] = m_bBytes[i - 8];

        setLong(nOffset, lValue);
    }

    public void getBytes(int nOffset, byte bBytes[])
        throws BufferException
    {
        if(bBytes == null || bBytes.length <= 0)
            throw new BufferException("Buffer.getBytes : null bBytes !");
        if(nOffset < 0 || nOffset + bBytes.length > m_nLength)
            throw new BufferException("Buffer.getBytes : not proper offset !");
        for(int i = 0; i < bBytes.length; i++)
            bBytes[i] = m_bBytes[nOffset + i];

    }

    public void setBytes(int nOffset, byte bBytes[])
        throws BufferException
    {
        if(bBytes == null || bBytes.length <= 0)
            throw new BufferException("Buffer.setBytes : null bBytes !");
        if(nOffset < 0 || nOffset + bBytes.length > m_nLength)
            throw new BufferException("Buffer.setBytes : not proper offset !");
        for(int i = 0; i < bBytes.length; i++)
            m_bBytes[nOffset + i] = bBytes[i];

    }

    public void insertByte(byte bValue)
        throws BufferException
    {
        insertByte(0, bValue);
    }

    public void addByte(byte bValue)
        throws BufferException
    {
        if(m_nLength + 1 > 2048)
        {
            throw new BufferException("Buffer.addByte : buffer overflow !");
        } else
        {
            m_nLength++;
            setByte(m_nOffset, bValue);
            m_nOffset++;
            return;
        }
    }

    public void insertShort(short sValue)
        throws BufferException
    {
        insertShort(0, sValue);
    }

    public void addShort(short sValue)
        throws BufferException
    {
        if(m_nLength + 2 > 2048)
        {
            throw new BufferException("Buffer.addShort : buffer overflow !");
        } else
        {
            m_nLength += 2;
            setShort(m_nOffset, sValue);
            m_nOffset += 2;
            return;
        }
    }

    public void insertInteger(int nValue)
        throws BufferException
    {
        insertInteger(0, nValue);
    }

    public void addInteger(int nValue)
        throws BufferException
    {
        if(m_nLength + 4 > 2048)
        {
            throw new BufferException("Buffer.addInteger : buffer overflow !");
        } else
        {
            m_nLength += 4;
            setInteger(m_nOffset, nValue);
            m_nOffset += 4;
            return;
        }
    }

    public void insertLong(long lValue)
        throws BufferException
    {
        insertLong(0, lValue);
    }

    public void addLong(long lValue)
        throws BufferException
    {
        if(m_nLength + 8 > 2048)
        {
            throw new BufferException("Buffer.addLong : buffer overflow !");
        } else
        {
            m_nLength += 8;
            setLong(m_nOffset, lValue);
            m_nOffset += 8;
            return;
        }
    }

    public void addBytes(byte bytes[])
        throws BufferException
    {
        if(bytes == null || bytes.length <= 0)
            throw new BufferException("Buffer.addBytes : null bytes !");
        if(m_nLength + bytes.length > 2048)
        {
            throw new BufferException("Buffer.addBytes : buffer overflow !");
        } else
        {
            m_nLength += bytes.length;
            setBytes(m_nOffset, bytes);
            m_nOffset += bytes.length;
            return;
        }
    }

    public void addString(String string)
        throws BufferException
    {
        if(string == null || string.length() <= 0)
            throw new BufferException("Buffer.addString : null string !");
        try
        {
            byte bBytes[] = string.getBytes("ISO8859-1");
            addBytes(bBytes);
        }
        catch(UnsupportedEncodingException e)
        {
            throw new BufferException("Buffer.addString : unsupported encoding !");
        }
    }

    public void addCString(String string)
        throws BufferException
    {
        if(string != null && string.length() > 0)
            addString(string);
        addByte((byte)0);
    }

    public void addCString(String str, int nFixLength)
        throws BufferException
    {
        if(str != null && str.length() > 0)
        {
            if(str.length() < nFixLength)
                addCString(str);
            else
                addString(str, nFixLength);
        } else
        {
            addByte((byte)0);
        }
    }

    public void addString(int nFixLength, String string)
        throws BufferException
    {
        byte bBytes[] = new byte[nFixLength];
        int nLength = string != null ? string.length() : 0;
        if(nLength > nFixLength)
            throw new BufferException("Buffer.addString : exceed fix length !");
        for(int i = 0; i < nFixLength - nLength; i++)
            addByte((byte)48);

        if(string != null && string.length() > 0)
            addString(string);
    }

    public void addString(String string, int nFixLength)
        throws BufferException
    {
        byte bBytes[] = new byte[nFixLength];
        int nLength = string != null ? string.length() : 0;
        if(nLength > nFixLength)
            throw new BufferException("Buffer.addString : exceed fix length !");
        if(string != null && string.length() > 0)
            addString(string);
        for(int i = 0; i < nFixLength - nLength; i++)
            addByte((byte)0);

    }

    public byte getByte()
        throws BufferException
    {
        byte bValue = getByte(m_nOffset);
        m_nOffset++;
        return bValue;
    }

    public short getShort()
        throws BufferException
    {
        short sValue = getShort(m_nOffset);
        m_nOffset += 2;
        return sValue;
    }

    public int getInteger()
        throws BufferException
    {
        int nValue = getInteger(m_nOffset);
        m_nOffset += 4;
        return nValue;
    }

    public long getLong()
        throws BufferException
    {
        long lValue = getLong(m_nOffset);
        m_nOffset += 8;
        return lValue;
    }

    public void getBytes(byte bBytes[])
        throws BufferException
    {
        getBytes(m_nOffset, bBytes);
        m_nOffset += bBytes.length;
    }

    public byte[] getBytes(int nLength)
        throws BufferException
    {
        if(nLength <= 0)
        {
            return null;
        } else
        {
            byte bBytes[] = new byte[nLength];
            getBytes(bBytes);
            return bBytes;
        }
    }

    public byte[] getBytes(byte bEndByte)
        throws BufferException
    {
        int nSize = 0;
        for(int i = m_nOffset; i < m_nLength; i++)
        {
            if(m_bBytes[i] == bEndByte)
                break;
            nSize++;
        }

        byte bBytes[] = null;
        if(nSize > 0)
        {
            bBytes = new byte[nSize];
            getBytes(bBytes);
        }
        if(m_nOffset + 1 <= m_nLength && bEndByte != getByte())
            throw new BufferException("Buffer.getBytes : invalid end byte !");
        else
            return bBytes;
    }

    public String getString(byte bEndByte)
        throws BufferException
    {
        byte bBytes[] = getBytes(bEndByte);
        if(bBytes == null)
            return null;
        else
            return new String(bBytes, 0, bBytes.length);
    }

    public String getCString()
        throws BufferException
    {
        return getString((byte)0);
    }

    public String getCString(int nLength)
        throws BufferException
    {
        byte tBytes[] = getBytes((byte)0, nLength);
        if(tBytes != null)
            return new String(tBytes);
        else
            return null;
    }

    public byte[] getBytes(byte bEndByte, int nFixLength)
        throws BufferException
    {
        int nSize = 0;
        boolean hasEndByte = true;
        for(int i = m_nOffset; i < m_nLength; i++)
        {
            if(m_bBytes[i] == bEndByte)
                break;
            if(++nSize < nFixLength)
                continue;
            hasEndByte = false;
            break;
        }

        byte bBytes[] = (byte[])null;
        if(nSize > 0)
        {
            bBytes = new byte[nSize];
            getBytes(bBytes);
        }
        if(m_nOffset + 1 <= m_nLength && hasEndByte && bEndByte != getByte())
            throw new BufferException("Buffer.getBytes : invalid end byte !");
        else
            return bBytes;
    }

    public String getString(int nLength)
        throws BufferException
    {
        byte bBytes[] = getBytes(nLength);
        int nSize = 0;
        for(int i = 0; i < bBytes.length && bBytes[i] != 0; i++)
            nSize++;

        if(nSize <= 0)
            return null;
        else
            return new String(bBytes, 0, nSize);
    }

    public static void read(InputStream is, byte bBytes[], int nOffset, int nLength)
        throws IOException, BufferException
    {
        if(is == null)
            throw new BufferException("Buffer.read : null input stream !");
        if(bBytes == null || bBytes.length <= 0)
            throw new BufferException("Buffer.read : null buffer !");
        if(nOffset < 0 || nLength <= 0 || nOffset + nLength > bBytes.length)
            throw new BufferException("Buffer.read : operation out of range !");
        int n = 0;
        do
        {
            int nByteRead = is.read(bBytes, nOffset + n, nLength - n);
            if(nByteRead == -1)
                throw new BufferException("Buffer.read : unexpected end !");
            n += nByteRead;
        } while(n < nLength);
    }

    public static void read(InputStream is, byte bBytes[])
        throws IOException, BufferException
    {
        if(is == null)
            throw new BufferException("Buffer.read : null input stream !");
        if(bBytes == null || bBytes.length <= 0)
            throw new BufferException("Buffer.read : null buffer !");
        int nLength = bBytes.length;
        int nOffset = 0;
        do
        {
            int nByteRead = is.read(bBytes, nOffset, nLength - nOffset);
            if(nByteRead == -1)
                throw new BufferException("Buffer.read : unexpected end !");
            nOffset += nByteRead;
        } while(nOffset < nLength);
    }

    public static void write(OutputStream os, byte bBytes[], int nOffset, int nLength)
        throws IOException, BufferException
    {
        if(os == null)
            throw new BufferException("Buffer.write : null output stream !");
        if(bBytes == null || bBytes.length <= 0)
            throw new BufferException("Buffer.write : null buffer !");
        if(nOffset < 0 || nLength <= 0 || nOffset + nLength > bBytes.length)
        {
            throw new BufferException("Buffer.write : operation out of range !");
        } else
        {
            os.write(bBytes, nOffset, nLength);
            return;
        }
    }

    public static void write(OutputStream os, byte bBytes[])
        throws IOException, BufferException
    {
        if(os == null)
            throw new BufferException("Buffer.write : null output stream !");
        if(bBytes == null || bBytes.length <= 0)
        {
            throw new BufferException("Buffer.write : null buffer !");
        } else
        {
            os.write(bBytes);
            return;
        }
    }

    public void dumpBuffer(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tm_nLength = " + m_nLength, 0x800000000000000L | lMethod);
            Log.log("\tm_nOffset = " + m_nOffset, 0x800000000000000L | lMethod);
            Log.log("\tm_bBytes = 0x" + Utility.toHexString(m_bBytes, m_nLength), 0x800000000000000L | lMethod);
        }
    }

    public void inputBuffer(DataInputStream dis, int nLength)
        throws IOException, BufferException
    {
        if(m_bBytes == null || m_bBytes.length <= 0)
            throw new BufferException("Buffer.inputBuffer : null buffer !");
        if(m_nLength + nLength > 2048)
        {
            throw new BufferException("Buffer.inputBuffer : buffer overflow !");
        } else
        {
            read(dis, m_bBytes, m_nLength, nLength);
            m_nLength += nLength;
            Log.log("Buffer.inputBuffer : input data !", 0x800100000000000L);
            dumpBuffer(0x100000000000L);
            return;
        }
    }

    public void outputBuffer(DataOutputStream dos)
        throws IOException, BufferException
    {
        if(m_bBytes == null || m_bBytes.length <= 0)
        {
            throw new BufferException("Buffer.outputBuffer : null buffer !");
        } else
        {
            Log.log("Buffer.outputBuffer : output data !", 0x800100000000000L);
            dumpBuffer(0x100000000000L);
            write(dos, m_bBytes, 0, m_nLength);
            dos.flush();
            return;
        }
    }

    public void log(String strReason, long lMethod)
    {
        Log.log("Buffer.log : " + strReason, lMethod);
        dumpBuffer(lMethod);
    }



}
