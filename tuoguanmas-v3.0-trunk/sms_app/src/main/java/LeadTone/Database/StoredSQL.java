package LeadTone.Database;

import java.sql.*;


/**
 * 充当JDBC中CallableStatement的部分
 */
public class StoredSQL extends Database
{
    String m_strSQL;
    CallableStatement m_statStored;

    public StoredSQL()
    {
    }

    public void setSQL(String strSQL)
    {
        m_strSQL = strSQL;
    }

    public boolean isCalled()
    {
        return m_statStored != null;
    }

    public void prepareCall()
        throws SQLException
    {
        m_statStored = m_connCurrent.prepareCall(m_strSQL);
    }

    public void closeStatement()
        throws SQLException
    {
        if(m_statStored != null)
            m_statStored.close();
        m_statStored = null;
    }

    public void clearParameters()
        throws SQLException
    {
        m_statStored.clearParameters();
    }

    public void setBoolean(int nIndex, boolean bValue)
        throws SQLException
    {
        m_statStored.setBoolean(nIndex, bValue);
    }

    public void setByte(int nIndex, byte bValue)
        throws SQLException
    {
        m_statStored.setByte(nIndex, bValue);
    }

    public void setByte(int nIndex, byte bBytes[])
        throws SQLException
    {
        m_statStored.setBytes(nIndex, bBytes);
    }

    public void setShort(int nIndex, short sValue)
        throws SQLException
    {
        m_statStored.setShort(nIndex, sValue);
    }

    public void setInteger(int nIndex, int nValue)
        throws SQLException
    {
        m_statStored.setInt(nIndex, nValue);
    }

    public void setLong(int nIndex, long lValue)
        throws SQLException
    {
        m_statStored.setLong(nIndex, lValue);
    }

    public void setFloat(int nIndex, float fValue)
        throws SQLException
    {
        m_statStored.setFloat(nIndex, fValue);
    }

    public void setDouble(int nIndex, double dValue)
        throws SQLException
    {
        m_statStored.setDouble(nIndex, dValue);
    }

    public void setString(int nIndex, String strValue)
        throws SQLException
    {
        m_statStored.setString(nIndex, strValue);
    }

    public void setTime(int nIndex, Time tValue)
        throws SQLException
    {
        m_statStored.setTime(nIndex, tValue);
    }

    public void setTimestamp(int nIndex, Timestamp tValue)
        throws SQLException
    {
        m_statStored.setTimestamp(nIndex, tValue);
    }

    public void registerOutParameter(int nIndex, int sqlType)
        throws SQLException
    {
        m_statStored.registerOutParameter(nIndex, sqlType);
    }

    public void registerOutParameter(int nIndex, int sqlType, int nScale)
        throws SQLException
    {
        m_statStored.registerOutParameter(nIndex, sqlType, nScale);
    }

    public boolean wasNull()
        throws SQLException
    {
        return m_statStored.wasNull();
    }

    public String getString(int nIndex)
        throws SQLException
    {
        return m_statStored.getString(nIndex);
    }

    public boolean getBoolean(int nIndex)
        throws SQLException
    {
        return m_statStored.getBoolean(nIndex);
    }

    public byte getByte(int nIndex)
        throws SQLException
    {
        return m_statStored.getByte(nIndex);
    }

    public short getShort(int nIndex)
        throws SQLException
    {
        return m_statStored.getShort(nIndex);
    }

    public int getInteger(int nIndex)
        throws SQLException
    {
        return m_statStored.getInt(nIndex);
    }

    public long getLong(int nIndex)
        throws SQLException
    {
        return m_statStored.getLong(nIndex);
    }

    public float getFloat(int nIndex)
        throws SQLException
    {
        return m_statStored.getFloat(nIndex);
    }

    public double getDouble(int nIndex)
        throws SQLException
    {
        return m_statStored.getDouble(nIndex);
    }

    public Time getTime(int nIndex)
        throws SQLException
    {
        return m_statStored.getTime(nIndex);
    }

    public Timestamp getTimestamp(int nIndex)
        throws SQLException
    {
        return m_statStored.getTimestamp(nIndex);
    }

    public boolean execute()
        throws SQLException
    {
        return m_statStored.execute();
    }

    public int executeUpdate()
        throws SQLException
    {
        return m_statStored.executeUpdate();
    }

    public ResultSet executeQuery()
        throws SQLException
    {
        return m_statStored.executeQuery();
    }


}