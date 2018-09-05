package continuum.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*********
 * Utility to insert, update, delete results to db
 * @author aditya.gaur
 *
 */

public class DBUtilities {

	private static String mySQLConnString 		= 	"com.mysql.jdbc.Driver";
	private static String sqlServerConnString	=	"com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static Connection conn = null;

		
	/**
	 * Public method to connect to Database
	 * Parameters: DB Server Name
	 */
	public static void connectDB(String DBSeverName){
		
		if(DBSeverName.equals("") || DBSeverName.equals(null)){
			System.out.println("Unknown Database Host Name.. Unable to create connection");
			return;
			}
		
		String DBserverURL = null;
		String connString = null;
		String localhost =  Utility.getMavenProperties("DBserverURL");//"40.121.209.122:3306/";
		String databaseName = Utility.getMavenProperties("DBname");//"mysql";
		String username = Utility.getMavenProperties("DBusername");//"root"; 
		String password = Utility.getMavenProperties("DBpassword");//"Q34ut0m4t10n";
		
		if(DBSeverName.equalsIgnoreCase("mysql")){
			DBserverURL = "jdbc:mysql://"+localhost;
			connString = mySQLConnString;
			
		}else if(DBSeverName.equalsIgnoreCase("sqlserver")){
			DBserverURL = "jdbc:sqlserver://" + localhost;
			connString = sqlServerConnString;
		}
		
		System.out.println("Creating connection to " + DBSeverName);
		createConnection(connString, databaseName, DBserverURL, username, password);
	}
	

	/**
	 * Method to create the connection with DB [type- MySQL, SqlSever]
	 * Parameters: DBServer Driver String, DB Name, DB URL, Username, Password
	 **/
	public static Connection createConnection(String JDBCdriverString, String databaseName, String DBServerURL,
			String username, String password) {

		try {
			Class.forName(JDBCdriverString);
			conn = DriverManager.getConnection(DBServerURL + databaseName, username, password);
			System.out.println("Connection Successfull to DB..");

		} catch (SQLException e) {
			System.out.println("Attempt to connect to Database failed.");
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			System.out.println("Attempt to connect to Database failed.");
			e.printStackTrace();
		}
			return conn;
	}

	/**
	 * Method to close the connection to DB 
	 * Parameters: Connection Object
	 **/
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Connection to Database is closed");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to perform insert
	 * Parameters: Table name, Column Name and respective value
	 **/
	public static void insertValues(String tableName, String[] ArrCoulmnNames, String[] ArrCoulmnValues) {

		if (conn == null) {
			System.out.println("Connection to Database is not present. Insert operaton failed..");
			return;
		}

		String insertQuery = "";
		Statement stmt = null;
		try {
			if (ArrCoulmnNames != null && ArrCoulmnValues != null && tableName != null) {

				String coulmnNames = ArrCoulmnNames[0];
				String coulmnValues = "'" + ArrCoulmnValues[0] + "'";

				if (ArrCoulmnNames.length > 1) {
					for (int i = 1; i < ArrCoulmnNames.length; i++) {
						coulmnNames = coulmnNames + ", " + ArrCoulmnNames[i];
						coulmnValues = coulmnValues + ",'" + ArrCoulmnValues[i] + "'";
					}
				}

				insertQuery = "INSERT INTO " + tableName + " (" + coulmnNames + " ) VALUES( " + coulmnValues + " );";

			//	System.out.println("Executing sql query: " + insertQuery);

				stmt = conn.createStatement();
				int i = stmt.executeUpdate(insertQuery);

				//System.out.println("Insert successfully executed. " + i + "records inserted.");
			}
		} catch (SQLException e) {

			System.out.println("Insert operation failed.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to perform update operation
	 * Parameters: Table name, Column Name, Where Clause Column and respective value
	 **/
	public static void updateValues(String tableName, String[] ArrCoulmnNames, String[] ArrCoulmnValues,
			String[] whereCoulmn, String[] whereValue) {

		if (conn == null) {
			System.out.println("Connection to Database is not present. Update operaton failed..");
			return;
		}

		String updateQuery = "";
		Statement stmt = null;
		try {
			String valueSet = "";
			String whereCondition = "0";

			valueSet = ArrCoulmnNames[0] + "= '" + ArrCoulmnValues[0] + "'";
			whereCondition = whereCoulmn[0] + "= '" + whereValue[0] + "'";

			if (ArrCoulmnNames.length > 1) {
				for (int i = 1; i < ArrCoulmnNames.length; i++) {
					valueSet = valueSet + ", " + ArrCoulmnNames[i] + "= '" + ArrCoulmnValues[i] + "'";
				}
			}

			if (whereCoulmn.length > 1) {
				for (int i = 1; i < whereCoulmn.length; i++) {
					whereCondition = whereCondition + " AND " + whereCoulmn[i] + "= '" + whereValue[i] + "'";
				}
			}

			updateQuery = "UPDATE " + tableName + " SET " + valueSet + " WHERE " + whereCondition;

			//System.out.println("Executing sql query: " + updateQuery);

			stmt = conn.createStatement();
			int i = stmt.executeUpdate(updateQuery);

			//System.out.println("Update successfully executed. " + i + " Records Updated.");
		} catch (SQLException e) {

			System.out.println("Update operation failed.");
			e.printStackTrace();

		}
	}
	
	/**
	 * Method to perform delete operation
	 * Parameters: Table name, Where Clause Column and respective value
	 **/
	public static void deleteValues(String tableName, String[] whereCoulmn, String[] whereValue) {

		if (conn == null) {
			System.out.println("Connection to Databse is not present. Delete operaton failed..");
			return;
		}

		String deleteQuery = "";
		Statement stmt = null;
		try {

			String whereCondition = "0";

			whereCondition = whereCoulmn[0] + "= '" + whereValue[0] + "'";

			if (whereCoulmn.length > 1) {
				for (int i = 1; i < whereCoulmn.length; i++) {
					whereCondition = whereCondition + " AND " + whereCoulmn[i] + "= '" + whereValue[i] + "'";
				}
			}

			deleteQuery = "DELETE FROM " + tableName + " WHERE " + whereCondition;

			System.out.println("Executing sql query: " + deleteQuery);

			stmt = conn.createStatement();
			int i = stmt.executeUpdate(deleteQuery);

			System.out.println("Delete successfully executed. " + i + " Records deleted.");
		} catch (SQLException e) {

			System.out.println("Delete operation failed.");
			e.printStackTrace();

		}
	}

}
