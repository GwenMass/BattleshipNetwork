package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameGUI extends JFrame {

	// Constructor that creates the client GUI
	public GameGUI() {
		
		// Set up the game client
		GameClient client = new GameClient();
		client.setHost("localhost");
		client.setPort(8300);
		try {
			client.openConnection();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set the title and default close operation
		this.setTitle("BATTLESHIP NETWORK");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create the card layout container
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);
		
		// Create the Controllers next
		InitialControl ic = new InitialControl(container, client);
		LoginControl lc = new LoginControl(container, client);
		CreateAccountControl cac = new CreateAccountControl(container, client);
		MenuControl mc = new MenuControl(container, client);
		LobbyControl lobbyc = new LobbyControl(container, client);
		GameControl gc = new GameControl(container, client);
		EndGameControl egc = new EndGameControl(container, client);
		
		// Set the client info
		client.setLoginControl(lc);
		client.setCreateAccountControl(cac);
		client.setMenuControl(mc);
		client.setLobbyControl(lobbyc);
		client.setGameControl(gc);
		client.setEndGameControl(egc);
		
		// Create the views 
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new CreateAccountPanel(cac);
		JPanel view4 = new MenuPanel(mc);
		JPanel view5 = new LobbyPanel(lobbyc);
		JPanel view6 = new GamePanel(gc);
		JPanel view7 = new EndGamePanel(egc);
		
		// Add the views to the card layout container
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		container.add(view5, "5");
		container.add(view6, "6");
		container.add(view7, "7");
		
		// Default to showing the InitialPanel view in the CardLayout
		cardLayout.show(container, "1");
		
		// Add the cardLayout container to the JFrame
		// GridBagLayout makes the container stay centered in the window
		this.setLayout(new GridBagLayout());
		this.add(container);
		
		// Show the JFrame
		this.setSize(550, 550);
		this.setVisible(true);
	}
	
	// Main function that creates the client's GameGUI when the program is started
	public static void main(String args[]) {
		 new GameGUI();
	}
}
