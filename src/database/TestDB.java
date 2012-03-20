package database;

/*
 * TestDB
 * 
 * Author: Even Lislebø
 * Version: 1.0
 * 
 * Class used to test the Database.java class
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used to test the Database.java class
 * 
 * @author Even
 * @version 1.0
 */

public class TestDB {

	public static void main(String[] args) {
		
//		testInsertion();
//		testDeletion();
//		testExecute("SELECT * FROM Reservasjon;");
		
	}
	
	
	/**
	 * Tests the INSERT statement against the database
	 */
	private static void testInsertion(){
		
		System.out.println("Antall rader før: " + Database.getRowCount("Reservasjon", "Avtale_ID"));
		testExecuteUpdate("INSERT INTO Reservasjon(ID, Avtale_ID, Romnr) VALUES (3,4,5)");
		System.out.println("Antall rader etter: " + Database.getRowCount("Reservasjon", "Avtale_ID"));
		
	}

	/**
	 * Tests the DELETE statement against the database
	 */
	private static void testDeletion(){
		
		System.out.println("Antall rader før: " + Database.getRowCount("Reservasjon", "Avtale_ID"));		
		Database.executeUpdate("DELETE FROM Reservasjon WHERE ID = 3;");
		System.out.println("Antall rader etter: " + Database.getRowCount("Reservasjon", "Avtale_ID"));
		
	}
	
	/**
	 * Tests the <code>execute(String sql)</code> method in Database.java
	 * @param sql
	 */
	private static void testExecute(String sql){
		
		try {
			ResultSet rs = Database.execute(sql);
			while(rs.next()){
				
				int id = rs.getInt("ID");
				int avtaleID = rs.getInt("Avtale_ID");
				int romnr = rs.getInt("Romnr");
				System.out.println("ID: " + id);
				System.out.println("Avtale-ID: " + avtaleID);
				System.out.println("Romnr: " + romnr);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Tests the <code>executeUpdate(String sql)</code> method in Database.java
	 * @param sql
	 */
	private static void testExecuteUpdate(String sql){

		Database.executeUpdate(sql);
		
	}
	
}
