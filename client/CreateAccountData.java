package client;

//Any changes to CreateAccountData in "client" package must be reflected in CreateAccountData in "server" package

import java.io.Serializable;

public class CreateAccountData implements Serializable {

	private String username;
	private String password;
	
	public CreateAccountData(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
