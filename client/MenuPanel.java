package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {
	
	// Private data fields for important GUI components
	private JLabel errorLabel;
	
	// Setter for the error text
	public void setError(String error) {
		errorLabel.setText(error);
	}
	
	// Constructor for the MenuPanel
	public MenuPanel(MenuControl mc) {
		
		// Create a panel for the labels at the top of the GUI
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(Color.RED);
		JLabel welcomeLabel = new JLabel("WELCOME TO BATTLESHIP", JLabel.CENTER);
		labelPanel.add(errorLabel);
		labelPanel.add(welcomeLabel);
		
		// Create the "New Game" button.
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(mc);
		JPanel newGameButtonBuffer = new JPanel();
		newGameButtonBuffer.add(newGameButton);
		
		// Create the "Logout" button
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(mc);
		JPanel logoutButtonBuffer = new JPanel();
		logoutButtonBuffer.add(logoutButton);
		
		// Arrange the components in a grid
		JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
		grid.add(labelPanel);
		grid.add(newGameButtonBuffer);
		grid.add(logoutButtonBuffer);
		this.add(grid);
	}
	
}
