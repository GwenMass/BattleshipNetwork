package client;

import javax.swing.JPanel;


public class EndGameControl {
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	public EndGameControl(JPanel container, GameClient client){
		this.container = container;
		this.client = client;
	}
	
}
