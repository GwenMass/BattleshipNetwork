package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public class CreateAccountControl {
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	CreateAccountData accountData;
	
	public CreateAccountControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
			
	}
	
	//HANDLER FOR CREATING ACCOUNT
	public void createAccount(String username, String password) {
		//verification process
		
		//if valid,set account up
		accountData.setUsername(username);
		accountData.setPassword(password);
		
	}
}
