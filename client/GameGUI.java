package client;
import java.io.IOException;

import javax.swing.*;
public class GameGUI {
private InitialPanel initialPanel;
private LoginPanel loginPanel;
private CreateAccountPanel createAccount;
JFrame ClientGUI;
private GameControl controller;
	//constructor
	 public GameGUI()
	{
		//Set up game client
		GameClient client = new GameClient();
		client.setHost("localhost");
		client.setPort(8300);
		try
		{
			client.openConnection();
		}
		catch (IOException e) {
			e.printStackTrace();	
		}
		
		
		
		
	}
	 
	 public void main(String args[])
	 {
		//call & add initial panel
			ClientGUI.add(initialPanel.getInitialPanel());
			//call & add login panel
			//ClientGUI.add(loginPanel.getLoginPanel());
			ClientGUI.add(createAccount);
			
			
	 }
}
