package server;

import java.io.IOException;
import java.sql.SQLException;

import client.GameGUI;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer {
	
	// Data fields for the GameServer
	private Database database;
	
	// Constructor for initializing the server with default settings
	public GameServer() {
		super(12345);
		this.setTimeout(500);
		database = new Database();
	}
	
	// Setter for database field
	public void setDatabase(Database database) {
		this.database = database;
	}

	// When a message is received from a client, handle it
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		
		// If we received LoginData, verify the account information
		if (arg0 instanceof LoginData) {
			// Check the username and password with the database
			LoginData data = (LoginData)arg0;
			Object result;
		    String command = "SELECT username, password FROM user WHERE username = \'" + data.getUsername() + "\' AND password = \'" + data.getPassword() + "\';";
			
		    if (database.query(command).size() == 1)
		    	result = "LoginSuccessful";
		    else
		        result = new Error("The username and password are incorrect.", "Login");
		    
		    // Send the result to the client
		    try {
		    	arg1.sendToClient(result);
		    }
		    catch (IOException e) {
		    	return;
		    }
		}
		// If we received CreateAccountData, create a new account
		else if(arg0 instanceof CreateAccountData) {
			// Try to create the account
			CreateAccountData data = (CreateAccountData)arg0;
			Object result;
			String command = "SELECT username FROM user WHERE username = \'" + data.getUsername() + "\';";
			
			if (database.query(command).isEmpty()) {
				try {
		    		database.executeDML("INSERT INTO user VALUES (\'" + data.getUsername() + "\', \'" + data.getPassword() + "\');");
				} 
		    	catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				result = "CreateAccountSuccessful";
			}
			else
		        result = new Error("The username is already in use.", "CreateAccount");
			
			 // Send the result to the client.
			try {
				arg1.sendToClient(result);
			}
			catch (IOException e) {
		        return;
			}
		}
		
	}
	
	// Main function that creates the server when the program is started
	public static void main(String args[]) {
		GameServer server = new GameServer();
		server.setPort(8300);
		server.setTimeout(500);
		try {
			server.listen();
		}
		catch (IOException e) {
			System.out.println("Server failed to listen.");
			e.printStackTrace();
		}
		
	}
}
