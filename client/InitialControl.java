package client;

import javax.swing.JPanel;

public class InitialControl {
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	public InitialControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
}
