package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class MenuControl implements ActionListener {
	
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	// Constructor for the menu controller
	public MenuControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}

	// Handle button clicks
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked
		String command = ae.getActionCommand();
		
		// The "Logout" button takes the user to the initial panel.
		if (command.equals("Logout")) {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
		
		// The "New Game" button requests the server to start a new game
		else if (command.equals("New Game")) {
			MenuData data = new MenuData(client.getUsername());
			
			// Submit MenuData to Server -- requests to start New Game or join current game
			try {
				//For some reason, client connection was randomly closing
				client.setPort(8300);
				client.openConnection();
				client.sendToServer(data);
			}
			catch (IOException e) {
				displayError("Error connecting to the server.");
				e.printStackTrace();
			}
		}
		
	}
	
	// If starting a new game or joining a game was successful, take the user to the LobbyPanel
	public void joinGameSuccess() {
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "5");
	}
	
	public void displayError(String error) {
		MenuPanel menuPanel = (MenuPanel)container.getComponent(3);
		menuPanel.setError(error);
	}
	
	
}
