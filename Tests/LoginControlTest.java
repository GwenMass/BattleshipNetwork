package Tests;

import static org.junit.Assert.*;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import org.junit.Test;

import client.*;

public class LoginControlTest {
	
	
    
   
    

	@Test
	public void testActionPerformed() 
	{
		JPanel container = new JPanel();
        CardLayout cardLayout = new CardLayout();
        container.setLayout(cardLayout);
        JPanel panel1 = new JPanel();
        container.add(panel1);
        GameClient gameClient = new GameClient();
        LoginControl loginControl = new LoginControl(container,gameClient);
        LoginPanel loginPanel = new LoginPanel(loginControl);
        container.add(loginPanel);
        LoginData loginData;
        //test these usernames & password combos
        ActionEvent ae = new ActionEvent(new Object(), 0, "Submit");
        //failing set
        //String[] usernames = {"Test1","Test2","Tes","", "       ","12345"};
        //String[] passwords = {"Test1","Test2","Tes","", "       ","12345"};
        //working set
        String[] usernames = {"Test1","Test2","Tes","12345"};
        String[] passwords = {"Test1","Test2","Tes","12345"};
        for (String username : usernames)
        {
        	for (String password : passwords)
        	{
        		loginPanel.setError("Test");
        		loginPanel.setUsername(username);
        		loginPanel.setPassword(password);
        		loginControl.actionPerformed(ae);
        		assertNotEquals(("You must enter a username and password."),loginPanel.getError());
        	}
        }
        
        
	}

	@Test
	public void testLoginSuccess() {
		
		
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
        assertEquals(visibleCard,container.getComponent(4));
		
	}

	@Test
	public void testDisplayError() {
		JPanel container = new JPanel();
        CardLayout cardLayout = new CardLayout();
        container.setLayout(cardLayout);
        container.add(new JPanel());
        LoginControl loginControl = new LoginControl(container, null); // Passing null for GameClient, not relevant for this test
        LoginPanel loginPanel = new LoginPanel(loginControl);
        container.add(loginPanel);

        loginControl.displayError("Test Error");

        // Verify that error message is set correctly on the login panel
        assertEquals("Test Error", loginPanel.getError());
	}

}
