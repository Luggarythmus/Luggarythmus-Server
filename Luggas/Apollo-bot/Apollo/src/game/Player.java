package game;

import java.io.Serializable;

public class Player implements Serializable{
	private String name;
	private int wins;
	private int losses;
	private long timeVoice;
	
	public Player(String name) {
		this.name=name;
		this.losses=0;
		this.wins=0;
		this.timeVoice=0;
	}
	
	public String getname() {
		
		return name;
	}
	public int getWins() {
		return wins;
	}
	public int getlosses() {
		return losses;
	}
	public void setWins(int i) {
		wins=i;
	}
	public void setLosses(int i) {
		losses=i;
	}
	public void settimeVoice(long l) {
		this.timeVoice=l;
	}
	public long gettimeVoice() {
		return timeVoice;
	}
}
