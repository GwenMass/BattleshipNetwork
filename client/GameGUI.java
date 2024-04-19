package client;
import java.awt.GridLayout;

import javax.swing.*;
public class GameGUI {

	//constructor
	 private GameGUI()
	{
		 JFrame ClientGUI = new JFrame();
		 InitialPanel initialPanel = new InitialPanel();
		 LoginPanel loginPanel = new LoginPanel();
		 CreateAccountPanel createAccount = new CreateAccountPanel();
		 GameControl controller;

		 	ClientGUI.setLayout(new GridLayout(3,1));
		 	ClientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	ClientGUI.setTitle("BATTLESHIP NETWORK");
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
