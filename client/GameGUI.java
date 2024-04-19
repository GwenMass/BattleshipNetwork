package client;
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
