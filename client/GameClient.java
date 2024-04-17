package client;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient{
	//Data fields
	
	//Setters for controllers
	
	//Constructor
	public GameClient() {
		super("localhost", 8300);
	}
	
	//Handles messages from server
	public void handleMessageFromServer(Object arg0) {
		
		//If string was received, figure out event
		if (arg0 instanceof String) {
			
			// Get the text of the message.
		    String message = (String)arg0;
		    
		}
		
		else if (arg0 instanceof Error) {
			
			// Get the Error object.
		    Error error = (Error)arg0;
		    
		}
		
		
	}
}
