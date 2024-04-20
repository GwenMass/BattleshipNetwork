package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener {
	
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	// Constructor for the Create Account Controller
	public CreateAccountControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
	
	// Handle button clicks
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked
		String command = ae.getActionCommand();
		
		// The cancel button takes the user back to the initial panel
		if (command == "Cancel") {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
		// The submit button creates a new account
		else if (command == "Submit") {
			// Get the text the user entered in the three fields
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
			String username = createAccountPanel.getUsername();
			String password = createAccountPanel.getPassword();
			String passwordVerify = createAccountPanel.getPasswordVerify();
			
			// Check the validity of the information locally first.
			if (username.equals("") || password.equals("")) {
				displayError("You must enter a username and password.");
				return;
			}
			else if(!password.equals(passwordVerify)) {
				displayError("The two passwords did not match.");
				return;
			}
			else if(password.length() < 6) {
				displayError("The password must be at least 6 characters.");
				return;
			}
			
			// Submit the new account information the server
			CreateAccountData data = new CreateAccountData(username, password);
			try {
				client.sendToServer(data);
			}
			catch (IOException e) {
				displayError("Error connecting to the server.");
			}			
		}
	}
	
	// After an account is created, display the MenuPanel screen
	public void createAccountSuccess() {
		// Store username on client for later use in-game
		CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		String username = createAccountPanel.getUsername();
		client.setUsername(username);
	    //clientGUI.setUser(new User(createAccountPanel.getUsername(), createAccountPanel.getPassword()));
	    // Progress to MenuPanel
	    CardLayout cardLayout = (CardLayout)container.getLayout();
	    cardLayout.show(container, "4");
	}
	
	// Method that displays a message in the error label.
	public void displayError(String error) {
	    CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
	    createAccountPanel.setError(error);
	}

}
