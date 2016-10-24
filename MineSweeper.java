// Use random to generate mines
import java.util.Random;
// Scanner to read player's inputs
import java.util.Scanner;

public class MineSweeper{
	
	// Tile objects to place on the board
	public static class Tile{
		
		// True for tile with mine, false for no mine
		public boolean mine;
		// Number of surrounding mines
		public int surroundingMines;
		// Whether if the tile is revealed
		public boolean visible;
		// Tile can be flagged as a mine
		public boolean flag;
		
		// Tile class constructor, initialize mine field,
		// New tiles are always hidden with no flag
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

	
	// Each tile has the following fields:
	// 'mine': boolean whether the tile a mine
	// 'visble': whether the tile has been revealed
	// 'numSurroundingMines': number of mine in surrounding tile, min0-max8
	// Game board consists a matrix of tile object as mines,
    private Tile[][] board;
	// Difficulty of the game
	private String difficulty;
	// Total number of mine of a game
	private int numMines;
	// Whether the game is over
	private boolean gameOver;
	
	// Constructor, initialize fields and generate mines
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

	// Reveal a tile that player choose
	// First check if the tile is a mine, if so, set gameOver to true
	// Print board each time a tile is revealed safely
	public void reveal(int r, int c)
	{
		if(this.board[r][c].mine == true)
		{
			this.gameOver = true;
			this.board[r][c].visible = true;

		}
		else
		{
			this.board[r][c].visible = true;
			// Need to also reveal adjacent no-mine-surrounding tiles
		}
	}
	
	// Check if player lost a game
	public boolean gameIsOver()
	{
		return this.gameOver;
	}
	
	
	// String representation of game board for player
	// '?' for a unrevealed tile
	// '*' for a revealed tile with mine
	// ' ' for a revealed empty tile
	// '1-8' represen number of mines in surrounding tiles
	public String toString()
	{
        String repr = "" + new String(new char[this.board[0].length*2 +3]).replace("\0","-") + "\n";
        for(int r = 0; r < this.board.length; r++)
		{
			repr += "| ";
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
						repr += "* ";
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
			repr += "|\n";
		}
		repr += new String(new char[this.board[0].length*2 +3]).replace("\0","-");
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
		System.out.println(this);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in); // Read inputs
		int gameWidth, gameHeight;
		// Ask player for game size
		System.out.print("Enter game size (n m): ");
		gameWidth = sc.nextInt();
		gameHeight = sc.nextInt();
		// Ask player for game difficulty
		System.out.print("Enter game difficulty (easy/medium/hard): ");
		String gameDifficulty = sc.next();
		// Create new game accordingly
		MineSweeper game = new MineSweeper(gameWidth, gameHeight, gameDifficulty);
		System.out.println(game);
		// Keep asking player for next move until game is over
		while(!game.gameIsOver())
		{
			System.out.print("Next move (r c): ");
			game.reveal(sc.nextInt(), sc.nextInt());
			System.out.println(game);
		}
		// Reveal all tile if game is over
		game.revealAll();
		System.out.println("Game Over! :(");
		
	}
}
