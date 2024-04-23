package client;

public class Grid {
	private int gridSize;
	private int cells[][];
	private boolean isOceanGrid;
	
	public Grid(int gridSize)
	{
		this.gridSize = gridSize;
		//create grid CHECK ORIENTATION
		this.cells = new int[gridSize][gridSize];

	}
	int[][] getCells()
	{
		return cells;
	}
	//assumes borders have been checked
	//starts cell selected, the moves right or down depending on if its horizontal/vertical
	void placeShip(int x, int y, int size, boolean isHorizontal)
	{
		if (isHorizontal)
		{
			//starts with intial cell placed, and 
			for (int i = 0; i < size; i++)
					{
						//designate 1 for cell occupied with ship
						cells[x][y] = 1;
						x++;
					}
		}
		else if (!(isHorizontal))
		{
			for (int i = 0; i < size; i++)
			{
				//designate 1 for cell occupied with ship
				cells[x][y] = 1;
				y--;
			}
		}
	}
	public void registerHit(int x, int y)
	{
		
	}
}

