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
		GameData gameData = new GameData();
		
		String command = ae.getActionCommand();
		//logout button removes the user from the game and returns to intial panel
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
				System.out.println("Error logging out of the game.");
			}
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
		
		//pre game stage
		if (gameData.getPlayerTurn() == 0)
		{
			//for ocean grid
			if (command.equals("\t")) {
				GamePanel gamePanel = (GamePanel)container.getComponent(6);
				//if no selected ship
				if (gamePanel.selectedShip == null)
				{
					gamePanel.setInstructionLabel("You must first select a ship to place it!");
				} else {	
					//determine clicked button
					JButton clickedButton = (JButton) ae.getSource();
					int x = 0;
					int y = 0;
					for (int i = 0; i < gamePanel.gridSize; i++)
					{
						for (int j = 0; j < gamePanel.gridSize; j++)
							if (clickedButton == gamePanel.oceanGridButtons[i][j])
							{
								x = i;
								y = j;
							}
					}
					//determine if out of bounds
					if (gamePanel.selectedShip.isHorizontal() && gamePanel.selectedShip.getSize()-1+x > gamePanel.gridSize)
					{
						//Out of bounds!
						gamePanel.setInstructionLabel("Ship is out of bounds! Try again.");
					}
					else if (!gamePanel.selectedShip.isHorizontal() && gamePanel.selectedShip.getSize()-1+y > gamePanel.gridSize)
					{
						//out of bounds! but vertical
						gamePanel.setInstructionLabel("Ship is out of bounds! Try again.");
					}
					else {
						//not out of bounds, place ship
						gamePanel.oceanGrid.placeShip(x, y, gamePanel.selectedShip.getSize(), gamePanel.selectedShip.isHorizontal());
						//set this to null 
						gamePanel.selectedShip = null;
					}
					}
				}
			//rotates the ship
			else if(command.equals("Rotate Ship"))
			{
				GamePanel gamePanel = (GamePanel)container.getComponent(6);
				//if no selected ship
				if (gamePanel.selectedShip == null)
				{
					gamePanel.setInstructionLabel("You must first select a ship to place it!");
				}
				//set the ship to rotate 
				else {gamePanel.selectedShip.rotate();}
			}
			//lock ships, acts as a ready button
			else if(command.equals("Lock Ships"))
			{
				GamePanel gamePanel = (GamePanel)container.getComponent(6);
				GameData data = new GameData();
				data.setId(client.getId());
				data.setReadyUp(true);
				//also needs to send oceanGrid to server
				// Submit LobbyData to server
							try {
								//For some reason, client connection was randomly closing
								client.setPort(8300);
								client.openConnection();
								client.sendToServer(data);
							}
							catch (IOException e) {
								System.out.println("Error readying up.");
							}
						}
			}
		//if current player's turn
		if(gameData.getPlayerTurn() == gameData.getId())
			{
				
			// for target grid
			if(command.equals("\n")){
				GamePanel gamePanel = (GamePanel)container.getComponent(6);
				//check if game is running and is activePlayer's turn
				
				
				}
			}
		//not the player's turn
		else if (gameData.getPlayerTurn() != gameData.getId())
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(6);
			gamePanel.setInstructionLabel("It is not your turn!");
		}
		}
	}
