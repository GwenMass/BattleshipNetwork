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
		
		// User clicked a cell on their targetGrid during attack phase, during their turn
		else if (command.equals("targetCell") && gamePanel.getAttackPhase() && gamePanel.getMyTurn()) {
			// Determine coordinates of cell
			JButton[][] targetGridButtons = gamePanel.getTargetGridButtons();
			JButton clickedButton = (JButton)ae.getSource();
			int x = 0;
			int y = 0;
			
			for (int i = 0; i < gamePanel.getGridSize(); i++) {
				for(int j = 0; j < gamePanel.getGridSize(); j++) {
					if(clickedButton == targetGridButtons[i][j]) {
						x = i;
						y = j;
					}
				}
			}
			
			// Inform server of the coordinates they are attacking (and validate oceanGrid)
			GameData data = new GameData();
			data.setId(client.getId());
			data.setAttackingCoords(x, y);
			data.setOceanGrid(gamePanel.getOceanGrid());
			
			try {
				client.setPort(8300);
				client.openConnection();
				client.sendToServer(data);;
			}
			catch(IOException e) {
				displayError("Error attacking opponent's board.");
			}
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
					data.setOceanGrid(gamePanel.getOceanGrid());
					
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
	
	public void setLockShips(boolean lockShips) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setShipsLocked(lockShips);
	}
	
	public void setMyTurn(boolean myTurn) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setMyTurn(myTurn);
	}
	
	public void myShotResult(int attackingX, int attackingY, boolean hit) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		Grid targetGrid = gamePanel.getTargetGrid();
		JButton[][] targetGridButtons = gamePanel.getTargetGridButtons();

		if(hit) {
			targetGrid.registerHit(attackingX, attackingY);
			targetGridButtons[attackingX][attackingY].setBackground(Color.RED);
		}
		else {
			targetGrid.registerMiss(attackingX, attackingY);
			targetGridButtons[attackingX][attackingY].setBackground(Color.YELLOW);
		}
		
		gamePanel.setTargetGrid(targetGrid);
		gamePanel.setTargetGridButtons(targetGridButtons);
	}
	
	public void oppShotResult(int attackingX, int attackingY, boolean hit) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		Grid oceanGrid = gamePanel.getOceanGrid();
		JButton[][] oceanGridButtons = gamePanel.getOceanGridButtons();
		
		if(hit) {
			oceanGrid.registerHit(attackingX, attackingY);
			oceanGridButtons[attackingX][attackingY].setBackground(Color.RED);
		}
		else {
			oceanGrid.registerMiss(attackingX, attackingY);
			oceanGridButtons[attackingX][attackingY].setBackground(Color.YELLOW);
		}
		
		gamePanel.setOceanGrid(oceanGrid);
		gamePanel.setOceanGridButtons(oceanGridButtons);
	}
	
	public void updateInstruction(String message) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setInstructionLabel(message);
	}
		
	public void displayError(String message) {
		GamePanel gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.setErrorLabel(message);
	}
}
