package client;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class CreateAccountPanel extends JPanel {
private JTextField usernameField;
private JTextField passwordField;
private JButton createAccount;
private CreateAccountControl controller;
private CreateAccountPanel() {
	//setup the layout of the UI
	this.setLayout(new GridLayout(3,2));
		
	JLabel usernameLabel = new JLabel("Username:");
	usernameField = new JTextField();
	
	JLabel passwordLabel = new JLabel("Password:");
	passwordField = new JTextField();
	
	createAccount = new JButton("Create Account!");
	
	//add created elements to the panel
	this.add(usernameLabel);
	this.add(usernameField);
	this.add(passwordLabel);
	this.add(passwordField);
	this.add(createAccount);
	
	//add actionListener to createButton
	createAccount.addActionListener((ActionEvent e) -> 
	controller.createAccount(usernameField.getText(), passwordField.getText()));
}
}
