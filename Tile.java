// Tiles to place on game board
public class Tile
{
	// True for tile with mine, false for no mine
	protected boolean mine;
	// Number of surrounding mines
	protected int numSurroundingMines;
	// Whether if the tile is revealed
	protected boolean visible;
	// Tile can be flagged as a mine
	protected boolean flag;
	// Coordinate
	protected int row, col;

	// Tile class constructor, initialize mine field,
	// New tiles are always hidden with no flag
	protected Tile(int row, int col, boolean mine)
	{
		this.row = row;
		this.col = col;
		this.mine = mine;
		this.flag = false;
		this.visible = false;
		this.numSurroundingMines = 0;
	}
}
