package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
	// Private data fields for the important GUI components
	JTextField usernameField;
	JPasswordField passwordField;
	JLabel errorLabel;
	
	// Getter for the text in the username field
	public String getUsername() {
		return usernameField.getText();
	}
	
	public String getPassword() {
		return new String(passwordField.getPassword());
	}
	
	public void setUsername(String username)
	{
		usernameField.setText(username);
	}
	
	public void setPassword(String password)
	{
		passwordField.setText(password);
	}
	
	// Setter for the error text
	public void setError(String error) {
		errorLabel.setText(error);
	}
	public String getError() {
		return errorLabel.getText();
	}
	
	// Constructor for the Login Panel
	public LoginPanel(LoginControl lc) {
		// Create a panel for the labels at the top of the GUI
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(Color.RED);
		JLabel instructionLabel = new JLabel("Enter your username and password to log in.", JLabel.CENTER);
		labelPanel.add(errorLabel);
		labelPanel.add(instructionLabel);
		
		// Create a panel for the login information form
		JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
	    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
	    usernameField = new JTextField(10);
	    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
	    passwordField = new JPasswordField(10);
	    loginPanel.add(usernameLabel);
	    loginPanel.add(usernameField);
	    loginPanel.add(passwordLabel);
	    loginPanel.add(passwordField);
		
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(lc);
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(lc);    
	    buttonPanel.add(submitButton);
	    buttonPanel.add(cancelButton);

	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
	    grid.add(labelPanel);
	    grid.add(loginPanel);
	    grid.add(buttonPanel);
	    this.add(grid);
	}		
	
}

