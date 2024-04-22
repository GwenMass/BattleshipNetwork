package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class EndGameControl implements ActionListener {
	
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	public EndGameControl(JPanel container, GameClient client){
		this.container = container;
		this.client = client;
	}
	
	// Handle button clicks
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked
		String command = ae.getActionCommand();
		
		// The "Logout" button removes the user from the Game and returns to InitialPanel
		if(command.equals("Logout")) {
			// Inform server that you are leaving lobby (can just re-use LobbyData, already has functionality)
			LobbyData data = new LobbyData();
			data.setId(client.getId());
			data.setLeavingLobby(true);
			
			// Submit EndGameData to server
			try {
				//For some reason, client connection was randomly closing
				client.setPort(8300);
				client.openConnection();
				client.sendToServer(data);
			}
			catch (IOException e) {
				System.out.println("Error logging out after game.");
			}
			
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
		
		// The "Retry" button allows the user to replay the game. Send back to Lobby Panel
		else if(command.equals("Retry")) {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");
		}
	}
	
	public void updateWinnerLabel(String message) {
		EndGamePanel endGamePanel = (EndGamePanel)container.getComponent(6);
		endGamePanel.setWinnerLabel(message);
	}
	
}
