package client;

import java.io.Serializable;

public class Grid implements Serializable {
	
	private int gridSize;
	private String cells[][];
	private boolean isOceanGrid;
	
	public Grid(int gridSize) {
		this.gridSize = gridSize;
		//create grid CHECK ORIENTATION
		this.cells = new String[gridSize][gridSize];
		
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells.length; y++) {
				cells[x][y] = "";
			}
		}
	}
	
	public String[][] getCells() {
		return cells;
	}
	
	//assumes borders have been checked
	//starts cell selected, the moves right or down depending on if its horizontal/vertical
	public void placeShip(int x, int y, Ship ship, int shipIndex) {
		if (ship.isHorizontal()) {
			//starts with initial cell placed, and 
			for (int i = 0; i < ship.getSize(); i++) {
				//designate 1 for cell occupied with ship
				cells[x][y] = "Ship" + shipIndex;
				x++;
			}
		}
		else {
			for (int i = 0; i < ship.getSize(); i++) {
				//designate 1 for cell occupied with ship
				cells[x][y] = "Ship" + shipIndex;
				y++;
			}
		}
	}
	
	public void removeShip(int shipIndex) {
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells.length; y++) {
				if(cells[x][y].equals("Ship" + shipIndex)) {
					cells[x][y] = "";
				}
			}
		}
	}
	
	public void registerHit(int x, int y) {
		
	}
}

/*boolean occupied = false;
		// See if any space is already occupied by another ship
		String cells[][] = oceanGrid.getCells();
		if(ship.isHorizontal()) {
			for(int i = 0; i < ship.getSize(); i++) {
				if(!cells[x][y].equals("") && !cells[x][y].equals("Ship" + shipIndex)) {
					occupied = true;
					break;
				}
				x++;
			}
		}
		else {
			for(int i = 0; i < ship.getSize(); i++) {
				if(!cells[x][y].equals("") && !cells[x][y].equals("Ship" + shipIndex)) {
					occupied = true;
					break;
				}
				y++;
			}
		}
		
		// If space is not occupied, place ship
		if(!occupied) {
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
		}*/


/*oceanGrid.placeShip(x,  y, ship, shipIndex);
		ship.setPlaced(true);
				
		if(selectedShip.isHorizontal()) {
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
		}*/
