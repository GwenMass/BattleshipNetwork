package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	//data fields
	private JTextField usernameField;
	private JTextField passwordField;
	private JButton loginButton;
	private JButton newAccount;
	private LoginControl controller;
	private LoginPanel(){//setup the layout of the UI
		this.setLayout(new GridLayout(3,2));
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField();
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordField = new JTextField();
		
		loginButton = new JButton("Login!");
		newAccount = new JButton("Create Account?");
		
		//add created elements to the panel
		this.add(usernameLabel);
		this.add(usernameField);
		this.add(passwordLabel);
		this.add(passwordField);
		this.add(loginButton);
		this.add(newAccount);
		
		//add actionListener to buttons
		
		loginButton.addActionListener((ActionEvent e) -> 
		controller.loginAttempt(usernameField.getText(), passwordField.getText()));
		
		newAccount.addActionListener((ActionEvent e) ->
		controller.setUpNewAccount());
		
	}		
}

