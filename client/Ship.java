package client;

import java.io.Serializable;
import java.awt.*;
import java.util.*;

public class Ship implements Serializable {
	
	private int size;
	private boolean[] hits;
	private int x;
	private int y;
	private boolean horizontal;
	private boolean placed;
	private Color color;

	// Constructor for a ship
	public Ship(int s) {
		this.size = s;
		hits = new boolean[size];
		horizontal = true;
		placed = false;
		
		// Set a random grayscale color for this ship
		Random rand = new Random();
		int shade = rand.nextInt(100) + 50;
		color = new Color(shade, shade, shade);
	}
	
	public Color getColor() {
		return color;
	}
	
	//return size of ship
	public int getSize() {return this.size;}
	
	//rotate ship
	public void rotate() {
		horizontal = !horizontal;
	}
	//return hits 
	//PLACEHOLDER
	public boolean getHits() {return false;}
	
	//returns ship's X co-ord
	public int getX() {return this.x;}
	
	//returns ship's Y co-ord
	public int getY() {return this.y;}
	
	//returns true/false on if ship is horizontal
	public boolean isHorizontal() {return this.horizontal;}
	
	public boolean getPlaced() {
		return placed;
	}
	
	public void setPlaced(boolean placed) {
		this.placed = placed;
	}
	
	public void registerHit(int x, int y)
	{
		//find distance from original coordinate 
		//if horizontal, use x values to determine position in array of hits
		if (this.horizontal)
		{
			hits[x - this.x] = true;
		}
		//if vertical, use y values to determine position in array of hits
		else
		{
			hits[y - this.y] = true;
		}
	}
}
