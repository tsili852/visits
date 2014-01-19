package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnector {
	private Connection conn;
	private String databaseName;
	
	public SqlConnector(){
		try{
			Class.forName("org.sqlite.JDBC").newInstance();
		}
		catch (Exception e) {
			System.out.println(e.toString() + " " + e.getMessage());
		}
	}

	public SqlConnector(String dbName){
		databaseName = dbName;
		try{
			Class.forName("org.sqlite.JDBC").newInstance();
		}
		catch (Exception e) {
			System.out.println(e.toString() + " " + e.getMessage());
		}
	}
	
	public void setDbName(String dbName){
		databaseName = dbName;
	}
	
	public String getDbName(){
		return databaseName;
	}
	
	public void connectToDatabase(){
		try{
			conn = DriverManager.getConnection("jdbc:sqlite:lib/" + databaseName,"","");
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet executeResultSetQuery(String query){
		try {
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			return rs;
			
		} catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public int executeUpdateQuery(String query){
		try {
			Statement statement = conn.createStatement();
			return statement.executeUpdate(query);		
		} catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean isConnectionOpen(){
		try {
			if(conn.isClosed()){
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
