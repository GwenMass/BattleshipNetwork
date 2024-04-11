package server;

import java.sql.*;
import java.util.*;
import java.io.*;

// To start the database:
// 1. Open XAMPP Control Panel and click "Start" for MySQL

// To load the "users" table for the first time (or to reset the users table)
//    ~ Below assumes you already followed setup from class ~
// 1. Open Command Line and type: "cd c:\xampp\mysql\bin\"
// 2. Command line, type: "mysql -h localhost -u student -p" and then enter and type "hello"
// 3. Command line, type: "use student_space"
// 4. Command line, type "source" followed by filepath to battleship.sql file (e.g., "source c:\temp\battleship.sql"

public class Database {
	private Connection conn;
	
	public Database() {
		// Variable Declaration/Initialization
		Properties properties;
		FileInputStream fis = null;
		String url;
		String pass;
		String user;
		
		// Associate a File input stream with db.properties
		try {
			fis = new FileInputStream("./server/db.properties");
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Create properties object and load it with FIS
		properties = new Properties();
		try {
			properties.load(fis);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Fetch url, user, and pass from properties object
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		pass = properties.getProperty("password");
		
		// Set the connection object
		try {
			//Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, user, pass);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Connection getConnection() {
		return conn;
	}
	
	
	public boolean isUsernameTaken(String username) {
		String query = "SELECT * FROM users WHERE username = \'" + username + "\';";
		ArrayList<String> queryResult = query(query);
		if(queryResult.isEmpty())
			return false;
		return true;
	}
	
	
	public boolean verifyLogin(String username, String password) {
		String query = "SELECT * FROM users WHERE username = \'" + username + "\' AND AES_DECRYPT(password, \'key\') = \'" + password + "\';";
		ArrayList<String> queryResult = query(query);
		if(queryResult.isEmpty())
			return false;
		return true;
	}
	
	
	public int getNextID() {
		String query = "SELECT MAX(id) FROM users;";
		ArrayList<String> queryResult = query(query);
		if(queryResult == null || queryResult.get(0).equals("null,"))
			return 1;
		else {
			return Integer.parseInt(queryResult.get(0).replace(",", "")) + 1;
		}
	}
	
	
	public void addUser(String username, String password, int id) {
		String newUserDML = "INSERT INTO users VALUES(\'" + username + "\', aes_encrypt(\'" + password + "\', \'key\'), " + id + ");";
		try {
			executeDML(newUserDML);
		} 
		catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<String> query(String query) {
		//Variable Declaration/Initialization
		int i = 0; //# of columns
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		ResultSetMetaData rm = null;
		String record = "";
		Statement statement = null;
		int noColumns = 0;
		
		try {
			//1. Create an SQL Statement from the Connection object
			statement = conn.createStatement();
			
			//2. Execute the query using the execute statement
			rs = statement.executeQuery(query);
			rm = rs.getMetaData();
			noColumns = rm.getColumnCount();
			
			//3. Process each row
			while(rs.next()) {
				record = "";
				
				//Process each field in a loop
				for(i = 0; i < noColumns; i++) {
					String value = rs.getString(i+1); //column no. starts at 1, not 0
					record += value;
					record += ",";
				}
				
				list.add(record);
			}
			
		}
		catch(SQLException e) {
			return null;
		}
		
		return list;
	}
  
	
	public void executeDML(String dml) throws SQLException {
		//Create a Statement from conn
		Statement statement = conn.createStatement();
		
		//Invoke the DML statement
		statement.execute(dml);
	}
}
