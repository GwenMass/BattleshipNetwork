package client;

public class Ship {
	private int size;
	private boolean[] hits;
	int x;
	int y;
	boolean horizontal;

	// Constructor for a ship
	public Ship(int s) {
		this.size = s;
	}
	
	//return size of ship
	int getSize() {return this.size;}
	
	//rotate ship
	void rotate() 
	{
		horizontal = !horizontal;
	}
	//return hits 
	//PLACEHOLDER
	boolean getHits() {return false;}
	
	//returns ship's X co-ord
	int getX() {return this.x;}
	
	//returns ship's Y co-ord
	int getY() {return this.y;}
	
	//returns true/false on if ship is horizontal
	boolean isHorizontal() {return this.horizontal;}
	
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

