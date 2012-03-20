package database;

/*
 * Database
 * 
 * Author Even Lislebø
 * Version 1.0
 * 
 * Class used for interaction between the database and the rest of the system
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 * Class used for interaction between the database and the rest of the system
 * 
 * @author Even
 * @version 1.0
 */

public class Database {

	/**
	 * Sets up a connection to the database
	 * 
	 * @return an operative <code>Connection</code> otherwise; <code>null</code>
	 * 			if the connection setup failed
	 */
	
	public static Connection connectionSetup(){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(Config.SQL_JDBC + Config.SQL_DB,
					Config.SQL_USERNAME, Config.SQL_PASSWORD);
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Get a result from database (SELECT)
	 * 
	 * @param sql
	 * 			The SQL query you want to execute
	 * 
	 * @return	a <code>ResultSet</code> with the data selection
	 * @throws SQLException
	 */
	
	public static ResultSet execute(String sql) throws SQLException{
		//Establish database connection
		Connection conn = connectionSetup();
		
		//Create query statement
		Statement st = conn.createStatement();
		
		//Execute query and return results
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	
	/**
	 * Executes an update statement(UPDATE, INSERT or DELETE)
	 * 
	 * @param sql
	 * 			The update query you want to execute
	 * @return
	 * 			a positive <code>int</code> if the update was executed 
	 * 			without errors; otherwise <code>-1</code>
	 */
	
	public static int executeUpdate(String sql){
		
		return Database.executeUpdate(sql, false);
		
	}
	
	/**
	 * Executes an update statement(INSERT, UPDATE or DELETE) and returns
	 * a unique insertion ID if so is specified
	 * 
	 * @param sql
	 * 			The update query you want to execute
	 * @param returnInsertionID
	 * 			Specifies that the query is an INSERT statement and that a 
	 * 			unique insertion ID is to be returned
	 * @return a positive <code>int</code> if the update was executed 
	 * 			without errors, the integer may be a unique insertion ID if
	 * 			specified; otherwise <code>-1</code>
	 */
	
	public static int executeUpdate(String sql, boolean returnInsertionID){
		
		//Establish database connection
		Connection conn = connectionSetup();
		PreparedStatement pstm;
		
		int result = -1;
		
		if(sql != null && !sql.equals("")){
			
			try{
				
				//Create query statement
				Statement stm = conn.createStatement();
				
				//Return keys if desired
				if(returnInsertionID){
					
					pstm = conn.prepareStatement(sql, 
							Statement.RETURN_GENERATED_KEYS);
					
					//Execute query
					pstm.executeUpdate();
					ResultSet resset = pstm.getGeneratedKeys();
					
					//Set return result
					resset.next();
					result = resset.getInt(1);
					
					//Close
					resset.close();
					pstm.close();
					
				} else{
					
					//Execute query
					stm.executeUpdate(sql);
					
					//Set return result
					result = 1;
					
				}
				//Close
				stm.close();
				conn.close();
				
			} catch(SQLException e){
				e.printStackTrace();
				result = -1;
			}
			
		}else{
			//Empty query
			result = -1;
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * Counts the number of rows in the desired table.
	 * 
	 * @param table
	 * 			The table whose rows you wish to count
	 * @param column
	 * 			The column to count on
	 * @return an <code>int</code> representing the number of rows in the 
	 * 			specified table, counting on the specified column; 
	 * 			<code>0</code> if an error occured or the table was empty
	 */
	
	public static int getRowCount(String table, String column){
		return Database.getRowCount(table, column, null);
	}
	
	public static int getRowCount(String table, String column, String conditions){
		
		int rows = 0;
		
		//Create query statement
		String sqlCondition = (conditions != null && !conditions.equals("") ?
				" WHERE " + conditions : "");
		String sql = "SELECT " + column + " FROM " + table + sqlCondition;
		
		try{
			
			//Execute query
			ResultSet results = Database.execute(sql);
			
			//Count rows
			while(results.next()){
				rows++;
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return rows;
		
	}
	
}
