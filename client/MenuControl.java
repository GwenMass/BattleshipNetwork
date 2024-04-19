package client;

import javax.swing.JPanel;

public class MenuControl {
	// Private data fields for the container and game client.
	
	private JPanel container;
	private GameClient client;
	
	public MenuControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
	
}
