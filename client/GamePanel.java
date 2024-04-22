package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
	
	private final int gridSize = 10;
	private Grid oceanGrid;
	private Grid targetGrid;
	private JButton[][] oceanGridButtons;
	private JButton[][] targetGridButtons;
	private JLabel instructionLabel;
	
	// Constructor for the GamePanel
	public GamePanel(GameControl gc) {
		
		// Create a label to indicate whose turn it is / instruction
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		instructionLabel = new JLabel("Place your ships on your Ocean Grid!");
		labelPanel.add(instructionLabel);
		
		// Create a middle panel for the grids and buttons
		JPanel middlePanel = new JPanel(new GridLayout(1, 3, 5, 5));
		
		// Create the Ocean Grid (i.e., the grid with this user's ships)
		JPanel oceanGridPanel = new JPanel(new GridLayout(10, 10));
		oceanGrid = new Grid(gridSize);
		oceanGridButtons = new JButton[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				JButton cell = new JButton();
				cell.setPreferredSize(new Dimension(25, 25));
				cell.addActionListener(gc);
				oceanGridPanel.add(cell);
				oceanGridButtons[i][j] = cell;
			}
		}
		middlePanel.add(oceanGridPanel);
		
		// Create a panel for buttons between the grids 
		JPanel bufferPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		JButton rotateShipButton = new JButton("Rotate Ship");
		rotateShipButton.addActionListener(gc);
		JButton lockShipsButton = new JButton("Lock Ships");
		lockShipsButton.addActionListener(gc);
		JButton logOutButton = new JButton("Logout");
		logOutButton.addActionListener(gc);
		bufferPanel.add(rotateShipButton);
		bufferPanel.add(lockShipsButton);
		bufferPanel.add(logOutButton);
		middlePanel.add(bufferPanel);
		
		// Create the Target Grid (i.e., the grid representing the opponent's board)
		JPanel targetGridPanel = new JPanel(new GridLayout(10, 10));
		targetGrid = new Grid(gridSize);
		targetGridButtons = new JButton[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				JButton cell = new JButton();
				cell.setPreferredSize(new Dimension(25, 25));
				cell.addActionListener(gc);
				targetGridPanel.add(cell);
				targetGridButtons[i][j] = cell;
			}
		}
		
		// Add panel at the bottom to select a ship. When user clicks a ship from there, they then click on ocean grid and it places ship where they clicked on the grid
		
		// Add the panels to the JFrame
		//add(labelPanel);
		add(oceanGridPanel, BorderLayout.WEST);
		add(bufferPanel, BorderLayout.CENTER);
		add(targetGridPanel, BorderLayout.EAST);
	}

}
