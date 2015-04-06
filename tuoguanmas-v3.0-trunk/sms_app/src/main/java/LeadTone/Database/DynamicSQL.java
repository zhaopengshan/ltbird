package LeadTone.Database;

import java.sql.*;


/**
 * 充当JDBC中PreparedStatement的部分
 */
public class DynamicSQL extends Database
{
    String m_strSQL;
    PreparedStatement m_statPrepared;

    public DynamicSQL()
    {
    }

    public void setSQL(String strSQL)
    {
        m_strSQL = strSQL;
    }

    public boolean isPrepared()
    {
        return m_statPrepared != null;
    }

    public void prepareStatement()
        throws SQLException
    {
        m_statPrepared = m_connCurrent.prepareStatement(m_strSQL);
    }

    public void closeStatement()
        throws SQLException
    {
        if(m_statPrepared != null)
            m_statPrepared.close();
        m_statPrepared = null;
    }

    public void clearParameters()
        throws SQLException
    {
        m_statPrepared.clearParameters();
    }

    public void setBoolean(int nIndex, boolean bValue)
        throws SQLException
    {
        m_statPrepared.setBoolean(nIndex, bValue);
    }

    public void setByte(int nIndex, byte bValue)
        throws SQLException
    {
        m_statPrepared.setByte(nIndex, bValue);
    }

    public void setByte(int nIndex, byte bBytes[])
        throws SQLException
    {
        m_statPrepared.setBytes(nIndex, bBytes);
    }

    public void setShort(int nIndex, short sValue)
        throws SQLException
    {
        m_statPrepared.setShort(nIndex, sValue);
    }

    public void setInteger(int nIndex, int nValue)
        throws SQLException
    {
        m_statPrepared.setInt(nIndex, nValue);
    }

    public void setLong(int nIndex, long lValue)
        throws SQLException
    {
        m_statPrepared.setLong(nIndex, lValue);
    }

    public void setFloat(int nIndex, float fValue)
        throws SQLException
    {
        m_statPrepared.setFloat(nIndex, fValue);
    }

    public void setDouble(int nIndex, double dValue)
        throws SQLException
    {
        m_statPrepared.setDouble(nIndex, dValue);
    }

    public void setString(int nIndex, String strValue)
        throws SQLException
    {
        m_statPrepared.setString(nIndex, strValue);
    }

    public void setTime(int nIndex, Time tValue)
        throws SQLException
    {
        m_statPrepared.setTime(nIndex, tValue);
    }

    public void setTimestamp(int nIndex, Timestamp tValue)
        throws SQLException
    {
        m_statPrepared.setTimestamp(nIndex, tValue);
    }

    public boolean execute()
        throws SQLException
    {
        return m_statPrepared.execute();
    }

    public int executeUpdate()
        throws SQLException
    {
        return m_statPrepared.executeUpdate();
    }

    public ResultSet executeQuery()
        throws SQLException
    {
        return m_statPrepared.executeQuery();
    }


}