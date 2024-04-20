package server;

import client.Grid;
import client.Ship;

public class Game {
	
	// Private data fields
	private Long playerOneId;
	private Long playerTwoId;
	private String playerOneUsername;
	private String playerTwoUsername;
	private Grid playerOneGrid;
	private Grid playerTwoGrid;
	private boolean inProgress = false;
	
	// Setters and getters for private data fields
	public void setPlayerOneId(Long playerOneId) {
		this.playerOneId = playerOneId;
	}
	
	public Long getPlayerOneId() {
		return playerOneId;
	}
	
	public void setPlayerTwoId(Long playerTwoId) {
		this.playerTwoId = playerTwoId;
	}
	
	public Long getPlayerTwoId() {
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

	public Game() {
		//playerOneGrid = new Grid();
		//playerTwoGrid = new Grid();
	}
	
}
