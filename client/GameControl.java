package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class GameControl implements ActionListener {
	
	// Private data fields for the container and game client.
	private JPanel container;
	private GameClient client;
	
	// Constructor for the menu controller
	public GameControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
	
	// Handle button clicks
	public void actionPerformed(ActionEvent ae) {		
		String command = ae.getActionCommand();
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		displayError("");	// Clear previous error
		
		// logout button removes the user from the game and returns to intial panel
		if (command.equals("Logout"))
		{
			LobbyData data = new LobbyData();
			data.setId(client.getId());
			data.setLeavingLobby(true);
				
			//submit gameData to server
			try {
				client.setPort(8300);
				client.openConnection();
				client.sendToServer(data);
			}
			catch (IOException e) {
				displayError("Error logging out of the game.");
			}
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
		
		// User clicked a cell on their oceanGrid before attack phase and without ships being locked (i.e., ship placing time)
		else if(command.equals("oceanCell") && !gamePanel.getAttackPhase() && !gamePanel.getShipsLocked()) {
			Ship selectedShip = gamePanel.getSelectedShip();
			
			// If there is no selected ship to place, display error
			if(selectedShip == null) {
				displayError("You must first select a ship to place it!");
			}
			// Otherwise, determine if ship can be placed at cell
			else {
				JButton[][] oceanGridButtons = gamePanel.getOceanGridButtons();
				JButton clickedButton = (JButton)ae.getSource();
				
				// Determine coordinates of cell
				int x = 0;
				int y = 0;
				for (int i = 0; i < gamePanel.getGridSize(); i++) {
					for(int j = 0; j < gamePanel.getGridSize(); j++) {
						if(clickedButton == oceanGridButtons[i][j]) {
							x = i;
							y = j;
						}
					}
				}
				
				// Determine if out of bounds
				if(selectedShip.isHorizontal() && selectedShip.getSize() + x > gamePanel.getGridSize()) {
					displayError("Ship out of bounds! Try again.");
				}
				else if(!selectedShip.isHorizontal() && selectedShip.getSize() + y > gamePanel.getGridSize()) {
					displayError("Ship out of bounds! Try again.");
				}
				else {
					// Not out of bounds, place ship
					gamePanel.removeShip(gamePanel.getSelectedShipIndex());
					gamePanel.placeShip(x, y, selectedShip, gamePanel.getSelectedShipIndex());
				}
			}
		}
		
		// User clicked a cell on their targetGrid during attack phase
		else if (command.equals("targetCell") && gamePanel.getAttackPhase()) {
			
		}
		
		// User clicked "Rotate Ship" when ships aren't locked
		else if (command.equals("Rotate Ship") && !gamePanel.getShipsLocked()) {
			Ship selectedShip = gamePanel.getSelectedShip();
			
			// If there is no selected ship to rotate, display error
			if(selectedShip == null) {
				displayError("You must first select a ship to rotate!");
			}
			else {
				selectedShip.rotate();
			}
		}
		
		// User clicked "Lock Ships"
		else if (command.equals("Lock Ships")) {
			Ship[] userShips = gamePanel.getUserShips();
			
			// Ships are already locked -> user wants to unlock ships (attack phase cannot have started)
			if(gamePanel.getShipsLocked() && !gamePanel.getAttackPhase()) {
				// Request server to unlock ships	
				GameData data = new GameData();
				data.setId(client.getId());
				data.setUnlockingShips(true);
				
				try {
					client.setPort(8300);
					client.openConnection();
					client.sendToServer(data);
				}
				catch (IOException e) {
					displayError("Error unlocking ships.");
				}
			}
			
			// Ships aren't locked yet --> determine if all ships are placed to see if ships *can* be locked
			else {
				boolean shipsPlaced = true;
				for(Ship ship : userShips) {
					if(ship.getPlaced() == false) {
						shipsPlaced = false;
						break;
					}
				}
				
				// If all ships are placed, request server to lock ships
				if(shipsPlaced) {					
					GameData data = new GameData();
					data.setId(client.getId());
					data.setShipsLocked(shipsPlaced);
					data.setPlayerOneOcean(gamePanel.getOceanGrid());
					
					try {
						client.setPort(8300);
						client.openConnection();
						client.sendToServer(data);
					}
					catch (IOException e) {
						displayError("Error locking ships.");
					}
				}
			}
		}
		
		// User clicked on a ship -> set SelectedShip to the ship they clicked
		else if (command.substring(0, 4).equals("Ship")) {
			int shipIndex = Integer.parseInt(command.substring(4));
			
			gamePanel.setSelectedShip(shipIndex);
		}
		
	}
	
	public void startAttackPhase() {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setAttackPhase(true);
	}
	
	public void lockShips() {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setShipsLocked(true);
	}
	
	public void unlockShips() {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setShipsLocked(false);
	}
		
	public void displayError(String message) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setErrorLabel(message);
	}
}
