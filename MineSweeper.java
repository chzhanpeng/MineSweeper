// Use random to generate mines
import java.util.Random;

import java.util.ArrayList;
public class MineSweeper{

	
	// Class for each tile of game
	public static class Tile{
		
		// True for tile with mine, false for no mine
		public boolean mine;
		// Number of surrounding mines
		public int surroundingMines;
		// Whether if the tile is revealed
		public boolean visible;
		// Tile can be flagged as a mine
		public boolean flag;
		// Consstructor
		
		public Tile(boolean mine)
		{
			this.mine = mine;
			this.flag = false;
			this.visible = false;
		}
		// Flag this tile
		public void flag()
		{
			this.flag = true;
		}
		// Remove flag on this tile
		public void deflag()
		{
			this.flag = false;
		}
		// Reveal this tile
		public void reveal()
		{
			this.visible = true;
		}
	}

	
    // Game board consists a matrix of integers,
	// -1 for cells with a mine,
    // 0-8 for number of mines in surrouning cell
    private Tile[][] board;
	// Difficulty of the game
	private String difficulty;
	// Number of mines
	//private int numMines;
	
	// Constructor, initialize everything
	public MineSweeper(int width, int height, String difficulty)
	{
		this.board = new Tile[width][height];
		Random random = new Random();
		// Randomly generate mines on the board
		for(int r = 0; r < this.board.length; r++)
		{
			for(int c = 0; c < this.board[0].length; c++)
			{
				if(random.nextInt(4) == 1)
				{
					this.board[r][c] = new Tile(true);
				}
				else
				{
					this.board[r][c] = new Tile(false);
				}
			}
		}
		this.difficulty = difficulty;
		this.countMines();
	}
	
	// Return difficulty of the game
	public String gameDifficulty()
	{
		return difficulty;
	}
	
	// Count number of mines in surrounding tiles for each tile
	private void countMines()
	{
		for(int r = 0; r < this.board.length; r++)
		{
			for(int c = 0; c < this.board[0].length; c++)
			{
				if(this.board[r][c].mine == true)
				{
					try{ this.board[r-1][c-1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){};  // Upper left cell
					try{ this.board[r-1][c].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){};  // Upper cell
					try{ this.board[r-1][c+1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){};  // Upper right cell
					try{ this.board[r][c-1].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){};  // Left cell
					try{ this.board[r][c+1].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){};  // Right cell
					try{ this.board[r+1][c-1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){};  // Lower left cell
					try{ this.board[r+1][c].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){};  // Lower cell
					try{ this.board[r+1][c+1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){};  // Lower right cell
				}
			}
		}
	}

	// Check if a tile is mine, if it's not mine
	// Reveal a cell, make tile visibble
	public boolean reveal(int r, int c)
	{
		if(this.board[r][c].mine == true)
		{
			this.board[r][c].visible = true;
			return false;
		}
		else
		{
			this.board[r][c].visible = true;
			return true;
		}
	}
	
	// String representation of game board for player
	// '?' for a unrevealed tile
	// 'X' for a revealed tile with mine
	// ' ' for a revealed empty tile
	// '1-8' represen number of mines in surrounding tiles
	public String toString()
	{
        String repr = "";
        for(int r = 0; r < this.board.length; r++)
		{
			for(int c = 0; c < this.board[0].length; c++)
			{
				if(this.board[r][c].visible == false)
				{
					repr += "? ";
				}
				else
				{
					if(this.board[r][c].mine == true)
					{
						repr += "X ";
					}
					else
					{
						if(this.board[r][c].surroundingMines == 0)
						{
							repr += "  ";
						}
						else
						{
							repr += this.board[r][c].surroundingMines + " ";
						}
					}
				}
			}
			repr += "\n";
		}
		return repr;
    }
	
	// Reveal alll tiles for debugging
	private void revealAll()
	{
		for(int r = 0; r < this.board.length; r++)
		{
			for(int c = 0; c < this.board[0].length; c++)
			{
				this.reveal(r,c);
			}
		}
	}
	
	public static void main(String[] args)
	{
		MineSweeper game = new MineSweeper(10,10,"easy");
		System.out.println(game);
		game.revealAll();
		System.out.println(game);
	}
}
