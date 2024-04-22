package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LobbyControl implements ActionListener {
	
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	// Constructor for the lobby controller
	public LobbyControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
	
	// Handle button clicks
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked
		String command = ae.getActionCommand();
		
		// The "Leave Lobby" button removes the user from the Game and returns to Menu Panel
		if (command.equals("Leave Lobby")) {
			LobbyData data = new LobbyData();
			data.setId(client.getId());
			data.setLeavingLobby(true);
			
			// Submit LobbyData to server - informs that user is leaving Game
			try {
				//For some reason, client connection was randomly closing
				client.setPort(8300);
				client.openConnection();
				client.sendToServer(data);
			}
			catch (IOException e) {
				System.out.println("Error leaving lobby.");
			}
			
			// Remove the user from the lobby
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "4");
		}
		// The "Ready Up" button indicates to the server that the player is ready
		else if(command.equals("Ready Up")) {
			LobbyData data = new LobbyData();
			data.setId(client.getId());
			data.setReadyUp(true);
			
			// Submit LobbyData to server
			try {
				//For some reason, client connection was randomly closing
				client.setPort(8300);
				client.openConnection();
				client.sendToServer(data);
			}
			catch (IOException e) {
				System.out.println("Error readying up.");
			}
		}
	}
	
	// If both players are ready, take the user to the Game Panel
	public void bothPlayersReady() {
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "6");
	}
	
	public void updateOpponentLabel(String message) {
		LobbyPanel lobbyPanel = (LobbyPanel)container.getComponent(4);
		lobbyPanel.setOpponentLabel(message);
	}
	
}
