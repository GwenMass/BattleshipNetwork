package server;

import java.io.Serializable;

public class User implements Serializable {
	
	private String username;
	private String password;
	private int id;
	
	public User(String username, String password, int id) {
		setUsername(username);
		setPassword(password);
		setId(id);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getId() {
		return id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
