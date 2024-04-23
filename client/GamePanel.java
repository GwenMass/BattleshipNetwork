package client;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
	
	private JLabel instructionLabel;
	private JLabel errorLabel;
	private final int gridSize = 10;
	private Grid oceanGrid;
	private Grid targetGrid;
	private JButton[][] oceanGridButtons;
	private JButton[][] targetGridButtons;
	private JButton lockShipsButton;
	private Ship[] userShips = {new Ship(5),new Ship(4),new Ship(3),new Ship(3),new Ship(2)};
	private JButton[] shipButtons;
	private Ship selectedShip;
	private int selectedShipIndex = -1;
	private boolean shipsLocked = false;
	private boolean attackPhase = false;
	private boolean myTurn = false;
	
	public void setInstructionLabel(String message) {
		instructionLabel.setText(message);
	}
	
	public void setErrorLabel(String message) {
		errorLabel.setText(message);
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	public void setOceanGrid(Grid oceanGrid) {
		this.oceanGrid = oceanGrid;
	}
	
	public Grid getOceanGrid() {
		return oceanGrid;
	}
	
	public void setTargetGrid(Grid targetGrid) {
		this.targetGrid = targetGrid;
	}
	
	public Grid getTargetGrid() {
		return targetGrid;
	}
	
	public JButton[][] getOceanGridButtons(){
		return oceanGridButtons;
	}
	
	public void setOceanGridButtons(JButton[][] oceanGridButtons) {
		this.oceanGridButtons = oceanGridButtons;
	}
	
	public JButton[][] getTargetGridButtons(){
		return targetGridButtons;
	}
	
	public void setTargetGridButtons(JButton[][] targetGridButtons) {
		this.targetGridButtons = targetGridButtons;
	}
	
	public Ship[] getUserShips() {
		return userShips;
	}
	
	public void setUserShips(Ship[] userShips) {
		this.userShips = userShips;
	}
	
	public void setSelectedShip(int i) {
		// Remove border on previously selected ship
		if(selectedShipIndex != -1) {
			shipButtons[selectedShipIndex].setBorder(null);
		}
		// If new selectedShip is same as previous selectedShip (i.e., same ship clicked twice), unselect the ship
		if(selectedShipIndex == i || i == -1) {
			selectedShipIndex = -1;
			selectedShip = null;
			return;
		}
		// Set new selectedShip and give it a border
		selectedShipIndex = i;
		selectedShip = userShips[i];
		shipButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
	}
	
	public Ship getSelectedShip() {
		return selectedShip;
	}
	
	public int getSelectedShipIndex() {
		return selectedShipIndex;
	}
	
	public void placeShip(int x, int y, Ship ship, int shipIndex) {
		boolean occupied = false;
		int temp_x = x;
		int temp_y = y;
		
		// See if any space is already occupied by another ship
		String cells[][] = oceanGrid.getCells();
		if(ship.isHorizontal()) {
			for(int i = 0; i < ship.getSize(); i++) {
				if(!cells[x][y].equals("Empty") && !cells[x][y].equals("Ship" + shipIndex)) {
					occupied = true;
					break;
				}
				x++;
			}
		}
		else {
			for(int i = 0; i < ship.getSize(); i++) {
				if(!cells[x][y].equals("Empty") && !cells[x][y].equals("Ship" + shipIndex)) {
					occupied = true;
					break;
				}
				y++;
			}
		}
		
		// If space is not occupied, place ship
		if(!occupied) {
			x = temp_x;
			y = temp_y;
			oceanGrid.placeShip(x,  y, ship, shipIndex);
			ship.setPlaced(true);
					
			if(ship.isHorizontal()) {
				for(int i = 0; i < ship.getSize(); i++) {
					oceanGridButtons[x][y].setBackground(ship.getColor());
					x++;
				}
			}
			else {
				for(int i = 0; i < ship.getSize(); i++) {
					oceanGridButtons[x][y].setBackground(ship.getColor());
					y++;
				}
			}
		}
	}
	
	public void removeShip(int shipIndex) {
		String[][] cells = oceanGrid.getCells();
		for(int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells.length; y++) {
				if(cells[x][y].equals("Ship" + shipIndex)) {
					oceanGridButtons[x][y].setBackground(null);
				}
			}
		}
		oceanGrid.removeShip(shipIndex);
	}
	
	public void setShipsLocked(boolean shipsLocked) {
		this.shipsLocked = shipsLocked;
		
		if(shipsLocked) {
			lockShipsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		}
		else
			lockShipsButton.setBorder(null);
	}
	
	public boolean getShipsLocked() {
		return shipsLocked;
	}
	
	public void setAttackPhase(boolean attackPhase) {
		this.attackPhase = attackPhase;
	}
	
	public boolean getAttackPhase() {
		return attackPhase;
	}
	
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	
	public boolean getMyTurn() {
		return myTurn;
	}

	// Constructor for the GamePanel
	public GamePanel(GameControl gc) {
		//this.setLayout(new GridLayout(3,1,5,5));
		// Create a label to indicate whose turn it is / instruction
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		instructionLabel = new JLabel("Place your ships on your Ocean Grid!", JLabel.CENTER);
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(Color.RED);
		labelPanel.add(instructionLabel);
		labelPanel.add(errorLabel);
		
		// Create a middle panel for the grids and buttons
		JPanel middlePanel = new JPanel(new GridLayout(1, 3, 5, 5));
		
		// Create the Ocean Grid (i.e., the grid with this user's ships)
		JPanel oceanGridPanel = new JPanel(new GridLayout(10, 10));
		oceanGrid = new Grid(gridSize);
		oceanGridButtons = new JButton[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				JButton cell = new JButton();
				cell.setActionCommand("oceanCell");
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
		lockShipsButton = new JButton("Lock Ships");
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
				cell.setActionCommand("targetCell");
				cell.setPreferredSize(new Dimension(25, 25));
				cell.addActionListener(gc);
				targetGridPanel.add(cell);
				targetGridButtons[i][j] = cell;
			}
		}
		
		// Add panel at the bottom to select a ship. When user clicks a ship from there, they then click on ocean grid and it places ship where they clicked on the grid
		JPanel shipSelection = new JPanel(new GridLayout(1,5,5,5));
		JPanel shipBuffer = new JPanel();
		shipButtons = new JButton[userShips.length];
		for (int i = 0; i < userShips.length; i++) {
			JButton shipButton = new JButton("Ship, Size " + userShips[i].getSize());
			shipButton.setActionCommand("Ship" + i);
			shipButton.setPreferredSize(new Dimension(25*userShips[i].getSize(),25));
			shipButton.addActionListener(gc);
			shipSelection.add(shipButton);
			shipButtons[i] = shipButton;
		}
		shipBuffer.add(shipSelection);
		
		// Add the panels to the JFrame
		this.setLayout(new BorderLayout());
		this.add(labelPanel, BorderLayout.NORTH);
		this.add(oceanGridPanel, BorderLayout.WEST);
		this.add(bufferPanel, BorderLayout.CENTER);
		this.add(targetGridPanel, BorderLayout.EAST);
		this.add(shipBuffer, BorderLayout.SOUTH);
		
	}

}
