package client;

import java.io.Serializable;

public class GameData implements Serializable {
	
	private Integer id; 
	private Grid oceanGrid;
	private boolean shipsLocked; //
	private boolean unlockingShips; //
	private Integer attackingX; //
	private Integer attackingY; //
	private boolean shotHit; //
	private boolean forfeit; //
	
	public void setId(Integer id) { 
		this.id = id;
	}
	
	public Integer getId() { 
		return id;
	}
	
	public void setOceanGrid(Grid ocean) {
		this.oceanGrid = ocean;
	}
	
	public Grid getOceanGrid() {
		return this.oceanGrid;
	}
	
	public void setShipsLocked(boolean shipsLocked) {
		this.shipsLocked = shipsLocked;
	}
	
	public boolean getShipsLocked() {
		return shipsLocked;
	}
	
	public void setUnlockingShips(boolean unlockingShips) {
		this.unlockingShips = unlockingShips;
	}
	
	public boolean getUnlockingShips() {
		return unlockingShips;
	}
	
	public void setAttackingCoords(Integer x, Integer y) {
		attackingX = x;
		attackingY = y;
	}
	
	public Integer getAttackingX() {
		return attackingX;
	}
	
	public Integer getAttackingY() {
		return attackingY;
	}
	
	public void setShotHit(boolean shotHit) {
		this.shotHit = shotHit;
	}
	
	public boolean getShotHit() {
		return shotHit;
	}
	
	
	public void setForfeit(boolean forfeit) {
		this.forfeit = forfeit;
	}
	
	public boolean getForfeit() {
		return forfeit;
	}
	
	public GameData() {
		// Set fields to default values; assume fields will be set with setters
		setId(null);
		setOceanGrid(null);
		setShipsLocked(false);
		setUnlockingShips(false);
		setAttackingCoords(null, null);
		setShotHit(false);
		setForfeit(false);
	}

}
