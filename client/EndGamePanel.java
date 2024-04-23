package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EndGamePanel extends JPanel {
	
	// Private data fields for important GUI components
	private JLabel winnerLabel;
	
	// Setter for the winnerLabel
	public void setWinnerLabel(String message) {
		winnerLabel.setText(message);
	}

	// Constructor for the EndGamePanel
	public EndGamePanel(EndGameControl egc) {
		
		// Create a panel for the label at the top of the GUI
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		winnerLabel = new JLabel("", JLabel.CENTER);
		labelPanel.add(winnerLabel);
		
		// Create the "Retry" button
		JButton retryButton = new JButton("Retry");
		retryButton.addActionListener(egc);
		JPanel retryButtonBuffer = new JPanel();
		retryButtonBuffer.add(retryButton);
		
		// Create the "Logout" button
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(egc);
		JPanel logoutButtonBuffer = new JPanel();
		logoutButtonBuffer.add(logoutButton);
		
		// Arrange the components in a grid
		JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
		grid.add(labelPanel);
		grid.add(retryButtonBuffer);
		grid.add(logoutButtonBuffer);
		this.add(grid);
	}
	
}
