package testClasses;

import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Test;
import client.*;
import server.*;
public class IntegrationTestClass {

	@Test
	public void testAccountCreation()
	{
		JPanel container = new JPanel();
		CardLayout cardLayout = new CardLayout();
		container.setLayout(cardLayout);
		container.add(new JPanel(),"1");
		container.add(new JPanel(),"4");
		GameClient gameClient = new GameClient();
		CreateAccountControl createAccountControl = new CreateAccountControl(container,gameClient);
		CreateAccountPanel createAccountPanel = new CreateAccountPanel(createAccountControl);
		createAccountPanel.setUsername("testuser3");
		createAccountPanel.setPassword("password");
		ActionEvent ae = new ActionEvent(new Object(),0,"Submit");
		
		//make sure logged in by panel visibilty
		 Component visibleCard = null;
	        for (Component comp : container.getComponents())
	        {
	        	if (comp.isVisible()) {
	        			visibleCard = comp;
	        	}
	        }
	        //make sure visible card is the correct one as a result of logging in
	        assertEquals(visibleCard,container.getComponent(0));
	}
	@Test
	public void testDatabase()
	{
		Database db = new Database();
		ArrayList<String> result = db.query("Select * from USERS");
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testLogin()
	{

		
		JPanel panel1 = new JPanel();
		JPanel panel4 = new JPanel();
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel();
		container.setLayout(cardLayout);
		LoginControl loginControl = new LoginControl(container, null);
		container.add(panel1,"1");
		container.add(panel4,"4");
		//show panel 1
		assertEquals(panel1, container.getComponent(0));
        assertEquals(panel4, container.getComponent(1));
        loginControl.loginSuccess();
        //now show panel 4
        //determine if panel 4 is visible 
        Component visibleCard = null;
        for (Component comp : container.getComponents())
        {
        	if (comp.isVisible()) {
        			visibleCard = comp;
        	}
        }
        assertEquals(visibleCard,container.getComponent(1));
		
        
		
	}
}
