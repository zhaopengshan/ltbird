package LeadTone.Database;

import java.sql.*;


/**
 * 
 */
public class Table extends StaticSQL
{
    /**
     * 
     */
    protected String m_strName;
    /**
     * 
     */
    protected ResultSet m_rsCurrent;

    /**
     * 
     */
    public Table(String strName)
    {
        m_strName = strName;
    }

    /**
     * 
     */
    public void open()
        throws SQLException
    {
        openConnection();
        createStatement();
    }

    public boolean isOpened()
    {
        return m_rsCurrent != null;
    }

    /**
     * 
     */
    public boolean getBoolean(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getBoolean(strColumn);
    }

    /**
     * 
     */
    public byte getByte(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getByte(strColumn);
    }

    /**
     * 
     */
    public byte[] getBytes(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getBytes(strColumn);
    }

    /**
     *
     */
    public short getShort(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getShort(strColumn);
    }

    /**
     * 
     */
    public int getInteger(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getInt(strColumn);
    }

    /**
     *
     */
    public long getLong(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getLong(strColumn);
    }

    /**
     * 
     */
    public float getFloat(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getFloat(strColumn);
    }

    /**
     * 
     */
    public double getDouble(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getDouble(strColumn);
    }

    /**
     * 
     */
    public String getString(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getString(strColumn);
    }

    /**
     * 
     */
    public Time getTime(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getTime(strColumn);
    }

    /**
     * 
     */
    public Timestamp getTimestamp(String strColumn)
        throws SQLException
    {
        return m_rsCurrent.getTimestamp(strColumn);
    }

    /**
     * 
     */
    public boolean next()
        throws SQLException
    {
        return m_rsCurrent.next();
    }

    /**
     * 
     */
    public boolean wasNull()
        throws SQLException
    {
        return m_rsCurrent.wasNull();
    }

    /**
     * 
     */
    public void close()
        throws SQLException
    {
        if(m_rsCurrent != null)
            m_rsCurrent.close();
        m_rsCurrent = null;
        closeStatement();
        closeConnection();
    }

    public int insert(String strParameter, String strValue)
        throws SQLException
    {
        m_rsCurrent = null;
        setSQL("insert into " + m_strName + " (" + strParameter + ") values (" + strValue + ")");
        return executeUpdate();
    }

    public void select()
        throws SQLException
    {
        setSQL("select * from " + m_strName);
        m_rsCurrent = executeQuery();
    }

    public void select(String strCondition, String strOrder)
        throws SQLException
    {
        setSQL("select * from " + m_strName + " where " + strCondition + " " + strOrder);
        m_rsCurrent = executeQuery();
    }

    public void selectTop(int n, String strCondition, String strOrder)
        throws SQLException
    {
        setSQL("select top " + n + " * from " + m_strName + " where " + strCondition + " " + strOrder);
        m_rsCurrent = executeQuery();
    }

    public void selectLimit(String strCondition, int nLimit,String strOrder)
        throws SQLException
    {
        setSQL("select * from " + m_strName + " where " + strCondition + " " + strOrder +" limit " + nLimit);
        //System.out.println("-----  sql:  "+m_strSQL);
        m_rsCurrent = executeQuery();
    }
    
    public void selectRowNum(String strCondition, int nRowNum, String strOrder)
    throws SQLException
    {
    setSQL("select * from " + m_strName + " where " + strCondition + " and RowNum < " + nRowNum + " " + strOrder);
    m_rsCurrent = executeQuery();
    }

    public int deleteAll()
        throws SQLException
    {
        m_rsCurrent = null;
        setSQL("delete from " + m_strName);
        return executeUpdate();
    }

    public int delete(String strCondition)
        throws SQLException
    {
        m_rsCurrent = null;
        setSQL("delete from " + m_strName + " where " + strCondition);
        return executeUpdate();
    }

    public int updateAll(String strUpdate)
        throws SQLException
    {
        m_rsCurrent = null;
        setSQL("update " + m_strName + " set " + strUpdate);
        return executeUpdate();
    }

    public int update(String strUpdate, String strCondition)
        throws SQLException
    {
        m_rsCurrent = null;
        setSQL("update " + m_strName + " set " + strUpdate + " where " + strCondition);
        return executeUpdate();
    }


}