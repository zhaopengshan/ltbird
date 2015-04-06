package LeadTone.Database;

import LeadTone.Log;
import java.sql.*;


/**
 * �䵱JDBC��Statement�Ĳ���
 */
public class StaticSQL extends Database
{
    /**
     * ��ִ�е�SQL���
     */
    String m_strSQL;
    /**
     * ͬJDBC��Statement����
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
     * ͬJDBC��execute()����
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
     * ͬJDBC��executeUpdate()����
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
     * ͬJDBC��executeQuery()����
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