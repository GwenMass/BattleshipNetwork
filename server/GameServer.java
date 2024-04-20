package server;

import java.awt.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import ocsf.server.*;
import client.CreateAccountData;
import client.LoginData;
import client.MenuData;
import client.Error;

public class GameServer extends AbstractServer {
	
	// Data fields for the GameServer
	private Database database;
	private Game game;
	private JTextArea log;
	private JLabel status;
	private boolean serverRunning = false;
	
	// Constructor for initializing the server with default settings
	public GameServer() {
		super(12345);
		this.setTimeout(500);
		database = new Database();
		game = new Game();
	}
	
	// Setter for database field
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	// Getter that returns whether the server is currently running
	public boolean isRunning() {
		return serverRunning;
	}
	
	// Setters for the data fields corresponding to the GUI elements
	public void setLog(JTextArea log) {
		this.log = log;
	}
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	// When the server starts, update the GUI
	public void serverStarted() {
		serverRunning = true;
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server started\n");
	}
	
	// When the server stops listening, update the GUI
	public void serverStopped() {
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}
	
	// When the server closes completely, update the gGUI
	public void serverClosed() {
		serverRunning = false;
		status.setText("Closed");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - press Listen to restart\n");
	}
	
	// When a client connects or disconnects, display a message in the log.
	public void clientConnected(ConnectionToClient client) {
		log.append("Client " + client.getId() + " connected\n");
	}
	
	// Method that handles listening exceptions by displaying exception information
	public void listeningException(Throwable exception) {
		serverRunning = false;
		status.setText("Exception occurred while listening");
		status.setForeground(Color.RED);
		log.append("Listening exception: " + exception.getMessage() + "\n");
		log.append("Press listen to restart server\n");
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
		else if (arg0 instanceof CreateAccountData) {
			// Try to create the account
			CreateAccountData data = (CreateAccountData)arg0;
			Object result;
			String command = "SELECT username FROM user WHERE username = \'" + data.getUsername() + "\';";
			
			if (database.query(command).isEmpty()) {
				try {
		    		database.executeDML("INSERT INTO user VALUES (\'" + data.getUsername() + "\', \'" + data.getPassword() + "\');");
		    		result = "CreateAccountSuccessful";
				} 
		    	catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = new Error("Could not create new account.", "CreateAccount");
				}
			}
			else {
				result = new Error("The username is already in use.", "CreateAccount");
			}
			
			
			 // Send the result to the client.
			try {
				arg1.sendToClient(result);
			}
			catch (IOException e) {
		        return;
			}
		}
		// If we received MenuData, see if a game is in progress or start a new game
		else if (arg0 instanceof MenuData) {
			System.out.println("Server received MenuData");
			MenuData data = (MenuData)arg0;
			Object result;
			
			// If no game currently in progress, start game and get Player One details from client
			if(!game.isInProgress()) {
				game.setInProgress(true);
				game.setPlayerOneId(arg1.getId());
				game.setPlayerOneUsername(data.getUsername());
				result = "NewGameStarted";
			}
			// If game is in progress and Player Two is not yet filled, set client as Player Two
			else if (game.getPlayerTwoId() == null){
				game.setPlayerTwoId(arg1.getId());
				game.setPlayerTwoUsername(data.getUsername());
				result = "GameJoined";
			}
			else {
				result = new Error("Game already in progress.", "Menu");
			}
			
			// Send the result to the client
			try {
				arg1.sendToClient(result);
				System.out.println("Sent MenuData result to client, result: " + result);
			}
			catch (IOException e) {
				return;
			}
		}
		
	}

}
