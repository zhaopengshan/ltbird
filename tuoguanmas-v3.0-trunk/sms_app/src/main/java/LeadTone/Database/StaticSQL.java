package LeadTone.Database;

import LeadTone.Log;
import java.sql.*;


/**
 * 充当JDBC中Statement的部分
 */
public class StaticSQL extends Database
{
    /**
     * 待执行的SQL语句
     */
    String m_strSQL;
    /**
     * 同JDBC的Statement对象
     */
    Statement m_statCreated;

    public StaticSQL()
    {
    }

    public void setSQL(String strSQL)
    {
        m_strSQL = strSQL;
    }

    public boolean isCreated()
    {
        return m_statCreated != null;
    }

    public void createStatement()
        throws SQLException
    {
        m_statCreated = m_connCurrent.createStatement();
    }

    public void closeStatement()
        throws SQLException
    {
        if(m_statCreated != null)
            m_statCreated.close();
        m_statCreated = null;
    }

    /**
     * 同JDBC的execute()方法
     */
    public boolean execute()
        throws SQLException
    {
    	boolean result;
    	try{
    		result = m_statCreated.execute(m_strSQL);
        }
        catch(SQLException e)
        {
            Log.log("execute error: " + m_strSQL, 0x2000008000000000L);
            throw e;
        }
    	
        return result;
    }

    /**
     * 同JDBC的executeUpdate()方法
     */
    public int executeUpdate()
        throws SQLException
    {
        int result;
        try
        {
        	result = m_statCreated.executeUpdate(m_strSQL);     
        }
        catch(SQLException e)
        {
            Log.log("executeUpdate error: " + m_strSQL, 0x2000008000000000L);
            throw e;
        }
        return result;
    }

    /**
     * 同JDBC的executeQuery()方法
     */
    public ResultSet executeQuery()
        throws SQLException
    {
    	ResultSet result;
    	try
    	{
    		result = m_statCreated.executeQuery(m_strSQL);
    	}
    	catch(SQLException e)
        {
            Log.log("executeQuery error: " + m_strSQL, 0x2000008000000000L);
            throw e;
        }
        return result;
    }


}