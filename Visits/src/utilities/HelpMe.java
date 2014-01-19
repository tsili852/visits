package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class HelpMe {
	public static enum PasswordState {NEW, CHANGE};
	public static int passwordUpdateStatus;
	public static String databaseName;
	public static String lastUser;
	public static String userName;
	
	public static void refreshProperties() {
		SqlConnector propConnector = new SqlConnector("dbTest1");
		ResultSet rs;
		propConnector.connectToDatabase();
		
		rs = propConnector.executeResultSetQuery("Select * from Properties");
		try {
			while(rs.next()){
				if (rs.getString("Id").equals("DATABASE_NAME")) {
					databaseName = rs.getString("Value");
				}
				
				if (rs.getString("Id").equals("LAST_USER")) {
					lastUser = rs.getString("Value");
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(propConnector.isConnectionOpen()) {
			propConnector.closeConnection();
		}
	}
		
	
	public static void updateProperty(String propertyName, String propertyValue) {
		SqlConnector propConnector = new SqlConnector(databaseName);
		propConnector.connectToDatabase();
		
		String sqlStatement = "Update Properties Set Value = '" + propertyValue + "' Where Id = '" + propertyName + "'";
		propConnector.executeUpdateQuery(sqlStatement);
	
		if(propConnector.isConnectionOpen()) {
			propConnector.closeConnection();
		}
	}
}



