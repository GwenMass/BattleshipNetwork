package client;

import java.io.Serializable;

public class EndGameData implements Serializable {
	
	private String winnerUsername;
	private Integer winnerId;
	
	public void setWinnerUsername(String winnerUsername) {
		this.winnerUsername = winnerUsername;
	}
	
	public String getWinnerUsername() {
		return winnerUsername;
	}
	
	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
	}
	
	public Integer getWinnerId() {
		return winnerId;
	}
	
	public EndGameData(String winnerUsername, Integer winnerId) {
		setWinnerUsername(winnerUsername);
		setWinnerId(winnerId);
	}

}
