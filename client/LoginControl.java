package client;

import javax.swing.JPanel;

public class LoginControl {
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	private LoginData data;
	public LoginControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
	
	
	//handlers for buttons pressed
	public void loginAttempt(String username, String password)
	{
		if (validateLogin(username,password))
				{
				data.setUsername(username);
				data.setPassword(password);
				}
		//display error message on LoginPanel
		else {}
	}
	//clear Panel & madeCreateAccountPanel the primary panel
	public void setUpNewAccount() {
	}
	
	private Boolean validateLogin(String username, String password)
	{
		return false;
	}
}
