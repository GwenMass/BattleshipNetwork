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
		    
		    // If we successfuly logged in, tell the login controller
		    if(message.equals("LoginSuccessful"))
		    	loginControl.loginSuccess();
		    
		    // If we successfully created an account, tell the create account controller
		    else if(message.equals("CreateAccountSuccessful"))
		    	createAccountControl.createAccountSuccess();
		    
		    // If we joined a new or existing game, tell the Menu Controller
		    else if(message.equals("NewGameStarted")) {
		    	menuControl.newGameSuccess();
			}
			else if (message.equals("GameJoined")) {
		    	menuControl.joinGameSuccess();
		    }
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
		}
	}
	
}