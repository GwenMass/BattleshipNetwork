package client;

import java.io.Serializable;

public class EndGameData implements Serializable {
	
	private String winnerUsername;
	private Integer winnerId;
	private String forfeitUsername;
	private Integer forfeitId;
	
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
	
	public void setForfeitUsername(String forfeitUsername) {
		this.forfeitUsername = forfeitUsername;
	}
	
	public String getForfeitUsername() {
		return forfeitUsername;
	}
	
	public void setForfeitId(Integer forfeitId) {
		this.forfeitId = forfeitId;
	}
	
	public Integer getForfeitId() {
		return forfeitId;
	}
	
	public EndGameData(String winnerUsername, Integer winnerId) {
		setWinnerUsername(winnerUsername);
		setWinnerId(winnerId);
		setForfeitUsername(null);
		setForfeitId(null);
	}

}
