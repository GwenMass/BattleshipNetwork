package server;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import ocsf.server.*;
import client.CreateAccountData;
import client.LoginData;
import client.User;
import client.MenuData;
import client.LobbyData;
import client.GameData;
import client.Grid;
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
			String command = "SELECT username, password FROM users WHERE username = \'" + data.getUsername() + "\' AND password = \'" + data.getPassword() + "\';";
			
		    if (database.query(command).size() == 1)
		    	result = new User(data.getUsername(), data.getPassword(), database.getId(data.getUsername()));
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
			String command = "SELECT username FROM users WHERE username = \'" + data.getUsername() + "\';";
			
			if (database.query(command).isEmpty()) {
				try {
		    		//database.executeDML("INSERT INTO users VALUES (\'" + data.getUsername() + "\', \'" + data.getPassword() + "\');");
		    		database.executeDML("INSERT INTO users VALUES (\'" + data.getUsername() + "\', \'" + data.getPassword() + "\'," + database.getNextID() + ");");
		    		result = new User(data.getUsername(), data.getPassword(), database.getId(data.getUsername()));
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
			MenuData data = (MenuData)arg0;
			Object result;
			boolean broadcast = true;
			
			// If no game currently in progress, start game and get Player One details from client
			if(!game.isInProgress()) {
				game.setInProgress(true);
				//game.setPlayerOneId(arg1.getId());
				game.setPlayerOneId(database.getId(data.getUsername()));
				game.setPlayerOneUsername(data.getUsername());
				result = "GameJoined";
			}
			// If game is in progress and Player Two is not yet filled, set client as Player Two
			else if (game.getPlayerTwoId() == null){
				//game.setPlayerTwoId(arg1.getId());
				game.setPlayerTwoId(database.getId(data.getUsername()));
				game.setPlayerTwoUsername(data.getUsername());
				result = "GameJoined";
			}
			else {
				result = new Error("Game already in progress.", "Menu");
				broadcast = false;
			}
			
			// Send the result to the client
			try {
				arg1.sendToClient(result);
				
				// Broadcast to players that client has joined game
				if(broadcast) {
					LobbyData playerJoined = new LobbyData();
					playerJoined.setPlayerOneUsername(game.getPlayerOneUsername());
					playerJoined.setPlayerTwoUsername(game.getPlayerTwoUsername());
					playerJoined.setPlayerOneReady(game.getPlayerOneReady());
					playerJoined.setPlayerTwoReady(game.getPlayerTwoReady());
					sendToAllClients(playerJoined);
				}
			}
			catch (IOException e) {
				return;
			}
		}
		// If we received LobbyData, see if user is leaving game or readying up
		else if (arg0 instanceof LobbyData) {
			LobbyData data = (LobbyData)arg0;
			Object result;
			
			// If user is leaving lobby, remove them from the Game
			if(data.getLeavingLobby()) {
				game.removePlayer(data.getId());
			}
			// User is readying up
			else if(data.getReadyUp()) {
				game.setPlayerReady(data.getId(), data.getReadyUp());		
			}
			
			// Broadcast to all clients the result of player either leaving lobby or reading
			LobbyData playersUpdate = new LobbyData();
			playersUpdate.setPlayerOneReady(game.getPlayerOneReady());
			playersUpdate.setPlayerTwoReady(game.getPlayerTwoReady());
			playersUpdate.setPlayerOneUsername(game.getPlayerOneUsername());
			playersUpdate.setPlayerTwoUsername(game.getPlayerTwoUsername());
			sendToAllClients(playersUpdate);
		}
		
		// We received GameData
		else if(arg0 instanceof GameData) {
			GameData data = (GameData)arg0;
			Object result;
			
			// User is locking their ships
			if(data.getShipsLocked()) {
				// Store their grid and the fact that they are locking
				game.setPlayerShipsLocked(data.getId(), data.getShipsLocked());
				
				if(game.getPlayerOneId().equals(data.getId()))
					game.setPlayerOneGrid(data.getOceanGrid());
				else if(game.getPlayerTwoId().equals(data.getId()))
					game.setPlayerTwoGrid(data.getOceanGrid());
				
				result = "LockShipsAllowed";
				try {
					arg1.sendToClient(result);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				// If both players have locked their ships, start attackPhase and broadcast to both clients
				if(game.getPlayerOneShipsLocked() && game.getPlayerTwoShipsLocked()) {
					game.setAttackPhase(true);
					
					// Broadcast to clients that attack phase started
					result = "AttackPhaseStarted";
					sendToAllClients(result);
					
					// randomly choose first player
					Random rand = new Random();
					int randomNumber = rand.nextInt(2);
					if(randomNumber == 0) {
						result = "Turn" + game.getPlayerOneId();
					}
					else {
						result = "Turn" + game.getPlayerTwoId();
					}
					
					// Broadcast player with first turn 
					sendToAllClients(result);
				}
			}
			// User is unlocking their ships
			else if (data.getUnlockingShips()) {
				// If not in attack phase, permit unlock
				if(!game.getAttackPhase())
					game.setPlayerShipsLocked(data.getId(), false);
				
				result = "UnlockShipsAllowed";
				try {
					arg1.sendToClient(result);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// User is sending attacking coordinates
			else if (data.getAttackingX() != null && data.getAttackingY() != null) {
				String nextTurn = "";
				boolean skip = false;
				
				if(game.getPlayerOneId().equals(data.getId())){
					// Validate their transmitted ocean grid with server's stored ocean grid
					if(!game.getPlayerOneGrid().equals(data.getOceanGrid())){
						result = new Error("Synchronization Error: Ocean Grid does not match.", "Game");
					}
					
					// Check opposing player's grid at those coordinates
					String coordinatesContent = game.getPlayerTwoGrid().getCells()[data.getAttackingX()][data.getAttackingY()];
					System.out.println("CoordinatesContent: " + coordinatesContent);
					
					// Coordinate is empty -> Miss
					if(coordinatesContent.startsWith("Empty")) {
						// Update opponent's grid
						System.out.println("PLayer 1 Missed");
						Grid oppGrid = game.getPlayerTwoGrid();
						oppGrid.registerMiss(data.getAttackingX(), data.getAttackingY());
						game.setPlayerTwoGrid(oppGrid);
						
						// Inform all client that they missed
						GameData res = data;
						res.setOceanGrid(null); // data security
						res.setShotHit(false);
						System.out.println("Broadcasted?");
						sendToAllClients(res);
						System.out.println("Broadcasted.");
						
					}
					// Cordinate contains ship -> Hit
					else if(coordinatesContent.startsWith("Ship")){
						// Update opponent's grid
						Grid oppGrid = game.getPlayerTwoGrid();
						oppGrid.registerHit(data.getAttackingX(), data.getAttackingY());
						game.setPlayerTwoGrid(oppGrid);
						
						// Inform all clients that they hit
						GameData res = data;
						res.setOceanGrid(null); // data security
						res.setShotHit(true);
						sendToAllClients(res);
					}
					// Coordinate contains already-attacked coordinate
					else if(coordinatesContent.startsWith("Hit") || coordinatesContent.startsWith("Miss")) {
						skip = true;
						result = new Error("You already attacked that coordinate. Retry.", "Game");
						try {
							arg1.sendToClient(result);
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					if(!skip) {
						// Set next turn to be of the opposing player
						nextTurn = "Turn" + game.getPlayerTwoId();
						sendToAllClients(nextTurn);
					}
					
				}
				else if(game.getPlayerTwoId().equals(data.getId())) {
					// Validate their transmitted ocean grid with server's stored ocean grid
					if(!game.getPlayerTwoGrid().equals(data.getOceanGrid())){
						result = new Error("Synchronization Error: Ocean Grid does not match.", "Game");
					}
					
					// Check opposing player's grid at those coordinates
					String coordinatesContent = game.getPlayerOneGrid().getCells()[data.getAttackingX()][data.getAttackingY()];
					System.out.println("CoordinatesContent: " + coordinatesContent);
					
					// Coordinate is empty -> Miss
					if(coordinatesContent.startsWith("Empty")) {
						System.out.println("PLayer 2 Missed");
						// Update opponent's grid
						Grid oppGrid = game.getPlayerOneGrid();
						oppGrid.registerMiss(data.getAttackingX(), data.getAttackingY());
						game.setPlayerOneGrid(oppGrid);
						
						// Inform all clients that they missed
						GameData res = data;
						res.setOceanGrid(null); // data security
						res.setShotHit(false);
						System.out.println("Broadcasted?");
						sendToAllClients(res);
						System.out.println("Broadcasted.");
					}
					// Cordinate contains ship -> Hit
					else if(coordinatesContent.startsWith("Ship")){
						// Update opponent's grid
						Grid oppGrid = game.getPlayerOneGrid();
						oppGrid.registerHit(data.getAttackingX(), data.getAttackingY());
						game.setPlayerOneGrid(oppGrid);
						
						// Inform all clients that they hit
						GameData res = data;
						res.setOceanGrid(null); // data security
						res.setShotHit(true);
						sendToAllClients(res);
					}
					// Coordinate contains already-attacked coordinate
					else if(coordinatesContent.startsWith("Hit") || coordinatesContent.startsWith("Miss")) {
						skip = true;
						result = new Error("You already attacked that coordinate. Retry.", "Game");
						try {
							arg1.sendToClient(result);
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					if(!skip) {
						// Set next turn to be of the opposing player
						nextTurn = "Turn" + game.getPlayerOneId();
						sendToAllClients(nextTurn);
					}
					
				}
				else {
					sendToAllClients(new Error("Error identifying player IDs.", "Game"));
				}
			}
		}
		
	}

}
