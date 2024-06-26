package client;

import java.io.Serializable;

public class Grid implements Serializable {
	
	private String cells[][];
	
	public Grid(int gridSize) {
		//create grid CHECK ORIENTATION
		this.cells = new String[gridSize][gridSize];
		
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells.length; y++) {
				cells[x][y] = "Empty";
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
					cells[x][y] = "Empty";
				}
			}
		}
	}
	
	public void registerHit(int x, int y) {
		cells[x][y] = "Hit" + cells[x][y];
	}
	
	public void registerMiss(int x, int y) {
		cells[x][y] = "Miss";
	}
	
	public boolean checkLostGame() {
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells.length; y++) {
				if(cells[x][y].startsWith("Ship")) {
					return false;
				}
			}
		}
		
		return true;
	}
}
