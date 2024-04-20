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
	private JLabel errorLabel;
	private JButton loginButton;
	private JButton newAccount;
	private LoginControl controller;
	LoginPanel(){//setup the layout of the UI
		this.setLayout(new GridLayout(4,2));
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField();
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordField = new JTextField();
		
		loginButton = new JButton("Login!");
		newAccount = new JButton("Create Account?");
		
		//add created elements to the panel
		this.add(errorLabel);
		//blank component
		this.add(new JLabel());
		//username label & field
		this.add(usernameLabel);
		this.add(usernameField);
		//pasword label & field
		this.add(passwordLabel);
		this.add(passwordField);
		//buttons!
		this.add(loginButton);
		this.add(newAccount);
		
		//add actionListener to buttons
		loginButton.addActionListener((ActionEvent e) -> 
		controller.loginAttempt(usernameField.getText(), passwordField.getText()));
		
		newAccount.addActionListener((ActionEvent e) ->
		controller.setUpNewAccount());
		
	}		
}

