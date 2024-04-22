package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LobbyPanel extends JPanel {
	
	// Private data fields for important GUI components
	private JLabel opponentLabel;
	
	// Setter for the opponentLabel
	public void setOpponentLabel(String message) {
		opponentLabel.setText(message);
	}
	
	// Constructor for the LobbyPanel
	public LobbyPanel(LobbyControl lobbyc) {
		
		// Create a panel for the label at the top of the GUI
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		JLabel lobbyLabel = new JLabel("BATTLESHIP LOBBY", JLabel.CENTER);
		opponentLabel = new JLabel("Waiting for another player to join...", JLabel.CENTER);
		labelPanel.add(lobbyLabel);
		labelPanel.add(opponentLabel);
		
		// Create the "Ready Up" button
		JButton readyUpButton = new JButton("Ready Up");
		readyUpButton.addActionListener(lobbyc);
		JPanel readyUpButtonBuffer = new JPanel();
		readyUpButtonBuffer.add(readyUpButton);
		
		// Create the "Leave Lobby" button
		JButton leaveLobbyButton = new JButton("Leave Lobby");
		leaveLobbyButton.addActionListener(lobbyc);
		JPanel leaveLobbyButtonBuffer = new JPanel();
		leaveLobbyButtonBuffer.add(leaveLobbyButton);
		
		// Arrange the components in a grid
		JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
		grid.add(labelPanel);
		grid.add(readyUpButtonBuffer);
		grid.add(leaveLobbyButtonBuffer);
		this.add(grid);
	}

}
