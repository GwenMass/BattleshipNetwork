package client;

import java.io.Serializable;

public class MenuData implements Serializable {
	
	private String username;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public MenuData(String username) {
		setUsername(username);
	}

}
