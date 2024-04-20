package client;
import java.io.IOException;

import javax.swing.*;
public class GameGUI {

	//constructor
	 private GameGUI()
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
			ClientGUI.add(initialPanel);
			//call & add login panel
			ClientGUI.add(loginPanel);
			ClientGUI.pack();
			ClientGUI.setVisible(true);
			//ClientGUI.add(createAccount);
	}
	 public static void main(String args[])
	 {
		 new GameGUI();
	 }
}
