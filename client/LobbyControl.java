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
	}
}
