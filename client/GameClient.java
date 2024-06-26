package client;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient{
	
	// Private data fields
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private MenuControl menuControl;
	private LobbyControl lobbyControl;
	private GameControl gameControl;
	private EndGameControl endGameControl;
	private String username;
	private int id;
	
	// Setters for the GUI controllers
	public void setLoginControl(LoginControl loginControl) {
		this.loginControl = loginControl;
	}
	
	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
		this.createAccountControl = createAccountControl;
	}
	
	public void setMenuControl(MenuControl menuControl) {
		this.menuControl = menuControl;
	}
	
	public void setLobbyControl(LobbyControl lobbyControl) {
		this.lobbyControl = lobbyControl;
	}
	
	public void setGameControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}
	
	public void setEndGameControl(EndGameControl endGameControl) {
		this.endGameControl = endGameControl;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	// Constructor for initializing the client with default settings
	public GameClient() {
		super("localhost", 8300);
	}
	
	// Handle messages from server
	public void handleMessageFromServer(Object arg0) {
		//If string was received, figure out event
		if (arg0 instanceof String) {
			// Get the text of the message.
		    String message = (String)arg0;
		    
		    // If we successfuly logged in, tell the login controller (DEPRECATED)
		    if(message.equals("LoginSuccessful"))
		    	loginControl.loginSuccess();
		    
		    // If we successfully created an account, tell create account controller (DEPRECATED)
		    else if(message.equals("CreateAccountSuccessful"))
		    	createAccountControl.createAccountSuccess();
		    
		    // If we joined a new or existing game, tell the Menu Controller
			else if (message.equals("GameJoined")) {
		    	menuControl.joinGameSuccess();
		    }
			
			// If we are allowed to lock ships, tell the Game Controller
			else if(message.equals("LockShipsAllowed")) {
				gameControl.setLockShips(true);
			}
		    
		    // If we are allowed to unlock ships, tell the Game Controller
			else if(message.equals("UnlockShipsAllowed")) {
				gameControl.setLockShips(false);
			}
		    
		    // If AttackPhase of game has begun, tell the Game Controller
			else if(message.equals("AttackPhaseStarted")) {
				gameControl.startAttackPhase();
			}
		    
		    // If it is our turn to attack, tell the Game Controller
			else if(message.startsWith("Turn")) {
				if(message.equals("Turn" + id)) {
					gameControl.setMyTurn(true);
					gameControl.updateInstruction("It's your turn! Click a cell of your opponent's board on the right to attack!");
				}
				else {
					gameControl.setMyTurn(false);
					gameControl.updateInstruction("It's your opponent's turn. Prepare for attack!");
				}
			}
			
		}
		
		// If we received User, we successfully logged in or create account. Tell login controller by default
		else if(arg0 instanceof User) {
			// Store client's username and ID for future
			User user = (User)arg0;
			setId(user.getId());
			setUsername(user.getUsername());
			
			loginControl.loginSuccess();
		}
		
		// If we received an Error, figure out where to display it.
		else if (arg0 instanceof Error) {
			// Get the Error object.
		    Error error = (Error)arg0;
		    
		    // Display login errors using the login controller
		    if(error.getType().equals("Login")) {
		    	loginControl.displayError(error.getMessage());
		    }
		    
		    // Display account creation errors using the create account controller
		    else if(error.getType().equals("CreateAccount")) {
		    	createAccountControl.displayError(error.getMessage());
		    }
		    
		    // Display game-joining errors using the Menu Controller
		    else if(error.getType().equals("Menu")) {
		    	menuControl.displayError(error.getMessage());
		    }
		    
		    // Display Game errors using the Game controller
		    else if(error.getType().equals("Game")) {
		    	gameControl.displayError(error.getMessage());
		    }
		}
		
		// If we received LobbyData from server, inform LobbyControl about update to players in lobby
		else if (arg0 instanceof LobbyData) {
			LobbyData data = (LobbyData)arg0;
			
			// If there are two players in lobby and both are ready, inform lobbyControl
			if(data.getPlayerOneReady() == true && data.getPlayerTwoReady() == true) {
				lobbyControl.bothPlayersReady();
			}
			// If user is only player in lobby
			else if(data.getPlayerOneUsername().equals(username) && data.getPlayerTwoUsername() == null) {
				lobbyControl.updateOpponentLabel("Waiting for another player to join...");
			}
			// If user is playerOne and there are updates about playerTwo
			else if(data.getPlayerOneUsername().equals(username)) {
				String message = "Your opponent is: " + data.getPlayerTwoUsername()  + ". They are";
				if(data.getPlayerTwoReady())
					message += " ready.";
				else
					message += "n't ready.";
				
				lobbyControl.updateOpponentLabel(message);
			}
			// If user is playerTwo and there are updates about playerOne
			else if(data.getPlayerTwoUsername().equals(username)) {
				String message = "Your opponent is: " + data.getPlayerOneUsername()  + ". They are";
				if(data.getPlayerOneReady())
					message += " ready.";
				else
					message += "n't ready.";
				
				lobbyControl.updateOpponentLabel(message);
			}
			// User has just left lobby (and thus won't see this), or unexpected circumstance occurred
			else {
				lobbyControl.updateOpponentLabel("Error determining opponent username");
			}
		}
		
		else if (arg0 instanceof GameData) {
			GameData data = (GameData)arg0;
			
			// IF we got coordinates back, we are being told if a  shot hit or missed
			if(data.getAttackingX() != null && data.getAttackingY() != null) {
				// If data stores our ID, the information is about our shot
				if(data.getId() == id) {
					// Tell the game controller if our shot hit or missed
					gameControl.myShotResult(data.getAttackingX(), data.getAttackingY(), data.getShotHit());
				}
				else {
					// Tell the game controller if the opponent's shot hit or missed
					gameControl.oppShotResult(data.getAttackingX(), data.getAttackingY(), data.getShotHit());
				}
			}   
		}
		
		else if (arg0 instanceof EndGameData) {
			EndGameData data = (EndGameData)arg0;
			
			//If we received EndGameData, the game is over. Tell GameControl
			if(data.getForfeitId() != null && data.getForfeitUsername() != null)
				endGameControl.updateWinnerLabel(data.getForfeitUsername() + " forfeits; " + data.getWinnerUsername() + " won! Retry or Logout.");
			else
				endGameControl.updateWinnerLabel(data.getWinnerUsername() + " won! Retry or Logout.");
			gameControl.endGame();
		}
	}
	
}