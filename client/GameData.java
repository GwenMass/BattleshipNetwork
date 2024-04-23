package client;

import java.io.Serializable;

public class GameData implements Serializable {
	private Integer id;
	private boolean readyUp;
	private boolean leavingLobby;
	private String playerOneUsername;
	private String playerTwoUsername;
	private boolean playerOneReady;
	private boolean playerTwoReady;
	private Grid playerOneOcean;
	private Grid playerTwoOcean;
	private int playerTurn;
	
	public void setPlayerTurn(int number)
	{
		this.playerTurn = number;
	}
	public int getPlayerTurn()
	{
		return this.playerTurn;
	}
	public void setPlayerOneOcean(Grid ocean)
	{
		this.playerOneOcean = ocean;
	}
	public Grid getPlayerOneOcean()
	{
		return this.playerOneOcean;
	}
	
	public void setPlayerTwoOcean(Grid ocean)
	{
		this.playerTwoOcean = ocean;
	}
	public Grid getPlayerTwoOcean()
	{
		return this.playerTwoOcean;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setReadyUp(boolean readyUp) {
		this.readyUp = readyUp;
	}
	
	public void setLeavingLobby(boolean leavingLobby) {
		this.leavingLobby = leavingLobby;
	}
	
	public boolean getReadyUp() {
		return readyUp;
	}
	
	public boolean getLeavingLobby() {
		return leavingLobby;
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
	
	public GameData() {
		// Set fields to default values; assume fields will be set with setters
				setId(null);
				setReadyUp(false);
				setLeavingLobby(false);
				setPlayerOneUsername(null);
				setPlayerTwoUsername(null);
				setPlayerOneReady(false);
				setPlayerTwoReady(false);
				setPlayerOneOcean(null);
				setPlayerTwoOcean(null);
				setPlayerTurn(0);
				
	}

}
