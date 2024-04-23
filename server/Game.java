package server;

import client.Grid;

public class Game {
	
	// Private data fields
	private Integer playerOneId;
	private Integer playerTwoId;
	private String playerOneUsername;
	private String playerTwoUsername;
	private Grid playerOneGrid;
	private Grid playerTwoGrid;
	private boolean playerOneReady;
	private boolean playerTwoReady;
	private boolean inProgress = false;
	private boolean playerOneShipsLocked;
	private boolean playerTwoShipsLocked;
	private boolean attackPhase;
	
	// Setters and getters for private data fields
	public void setPlayerOneId(Integer playerOneId) {
		this.playerOneId = playerOneId;
	}
	
	public Integer getPlayerOneId() {
		return playerOneId;
	}
	
	public void setPlayerTwoId(Integer playerTwoId) {
		this.playerTwoId = playerTwoId;
	}
	
	public Integer getPlayerTwoId() {
		return playerTwoId;
	}
	
	public void setPlayerOneUsername(String playerOneUsername) {
		this.playerOneUsername = playerOneUsername;
	}
	
	public String getPlayerOneUsername() {
		return playerOneUsername;
	}
	
	public void setPlayerTwoUsername(String playerTwoUsername) {
		this.playerTwoUsername = playerTwoUsername;
	}
	
	public String getPlayerTwoUsername() {
		return playerTwoUsername;
	}
	
	public void setPlayerOneGrid(Grid playerOneGrid) {
		this.playerOneGrid = playerOneGrid;
	}
	
	public Grid getPlayerOneGrid() {
		return playerOneGrid;
	}
	
	public void setPlayerTwoGrid(Grid playerTwoGrid) {
		this.playerTwoGrid = playerTwoGrid;
	}
	
	public Grid getPlayerTwoGrid() {
		return playerTwoGrid;
	}
	
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	
	public boolean isInProgress() {
		return inProgress;
	}
	
	public void setPlayerReady(Integer playerId, boolean ready) {
		if(this.playerOneId.equals(playerId))
			setPlayerOneReady(ready);
		else if(this.playerTwoId.equals(playerId))
			setPlayerTwoReady(ready);
		else
			System.out.println("Player ID " + playerId + " is not in session.");
	}
	
	public void setPlayerOneReady(boolean playerOneReady) {
		this.playerOneReady = playerOneReady;
	}
	
	public boolean getPlayerOneReady() {
		return playerOneReady;
	}
	
	public void setPlayerTwoReady(boolean playerTwoReady) {
		this.playerTwoReady = playerTwoReady;
	}
	
	public boolean getPlayerTwoReady() {
		return playerTwoReady;
	}
	
	public void setPlayerOneShipsLocked(boolean playerOneShipsLocked) {
		this.playerOneShipsLocked = playerOneShipsLocked;
	}
	
	public boolean getPlayerOneShipsLocked() {
		return playerOneShipsLocked;
	}
	
	public void setPlayerTwoShipsLocked(boolean playerTwoShipsLocked) {
		this.playerTwoShipsLocked = playerTwoShipsLocked;
	}
	
	public boolean getPlayerTwoShipsLocked() {
		return playerTwoShipsLocked;
	}
	
	public void setPlayerShipsLocked(Integer playerId, boolean shipsLocked) {
		if(this.playerOneId.equals(playerId))
			setPlayerOneShipsLocked(shipsLocked);
		else if(this.playerTwoId.equals(playerId))
			setPlayerTwoShipsLocked(shipsLocked);
		else
			System.out.println("Player ID " + playerId + " is not in session.");
	}
	
	public void setAttackPhase(boolean attackPhase) {
		this.attackPhase = attackPhase;
	}
	
	public boolean getAttackPhase() {
		return attackPhase;
	}
	
	public void removePlayer(Integer playerId) {
		if(this.playerTwoId.equals(playerId)) {
			setPlayerTwoId(null);
			setPlayerTwoUsername(null);
			setPlayerTwoReady(false);
		}
		else if(this.playerOneId.equals(playerId)) {
			setPlayerOneId(getPlayerTwoId());
			setPlayerOneUsername(getPlayerTwoUsername());
			setPlayerOneReady(getPlayerTwoReady());
			setPlayerTwoId(null);
			setPlayerTwoUsername(null);
			setPlayerTwoReady(false);
			
			// If both players left, game not in progress
			if(getPlayerOneId() == null)
				setInProgress(false);
		}
		else
			System.out.println("PlayerId does not match players in session.");
	}
	
	public void retryGame() {
		playerOneGrid = new Grid(10);
		playerTwoGrid = new Grid(10);
		playerOneReady = false;
		playerTwoReady = false;
		playerOneShipsLocked = false;
		playerTwoShipsLocked = false;
		attackPhase = false;
	}

	public Game() {
		// Set fields to default variables when no players connected
		playerOneId = null;
		playerTwoId = null;
		playerOneUsername = null;
		playerTwoUsername = null;
		playerOneGrid = new Grid(10);
		playerTwoGrid = new Grid(10);
		playerOneReady = false;
		playerTwoReady = false;
		playerOneShipsLocked = false;
		playerTwoShipsLocked = false;
		attackPhase = false;
	}
	
}
