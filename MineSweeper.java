// Use random to generate mines
import java.util.Random;
// Scanner to read player's inputs
import java.util.Scanner;

public class MineSweeper{
	
	// Tile objects to place on the board
	protected static class Tile{
		
		// True for tile with mine, false for no mine
		protected boolean mine;
		// Number of surrounding mines
		protected int surroundingMines;
		// Whether if the tile is revealed
		protected boolean visible;
		// Tile can be flagged as a mine
		protected boolean flag;
		
		// Tile class constructor, initialize mine field,
		// New tiles are always hidden with no flag
		public Tile(boolean mine)
		{
			this.mine = mine;
			this.flag = false;
			this.visible = false;
		}
		// Flag this tile
		protected void flag()
		{
			this.flag = true;
		}
		// Remove flag on this tile
		protected void deflag()
		{
			this.flag = false;
		}
		// Reveal this tile
		protected void reveal()
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
	// Total visible tiles
	private int numVisible;
	// Whether the game is over
	private boolean gameOver;
	
	// Constructor, initialize fields and generate mines
	public MineSweeper(int width, int height, String difficulty)
	{
		this.board = new Tile[width][height];
		this.numVisible = 0;
		this.difficulty = difficulty;
		// Set number of mines based on game diffculty
		if(difficulty.equals("easy"))
		{
			this.numMines = width*height/6;
		}
		else if(difficulty.equals("medium"))
		{
			this.numMines = width*height/5;
		}
		else
		{
			this.numMines = width*height/4;
		}
		this.randomGenerate(this.numMines);
		this.countMines();
	}
	
	// Check if player lost a game
	public boolean lose()
	{
		return this.gameOver;
	}
	
	// Check if player wins
	public boolean win()
	{
		return (this.height()*this.width() - this.numVisible) == this.numMines;
	}
	
	// Return game size
	public int width()
	{
		return this.board[0].length;
	}
	public int height()
	{
		return this.board.length;
	}
	
	// Return difficulty of the game
	public String gameDifficulty()
	{
		return difficulty;
	}
	
	// Generate tile for each position on board
	// Randomly select random tile to be mine; if tile is already a mine,
	// select another tile, repeat until there are enough mines
	private void randomGenerate(int n)
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				this.board[r][c] = new Tile(false);
			}
		}
		Random rd = new Random();
		int curNumMines = 0;
		while(curNumMines < this.numMines)
		{
			int r, c;
			r = rd.nextInt(this.width());
			c = rd.nextInt(this.height());
			if(this.board[r][c].mine == false)
			{
				this.board[r][c].mine = true;
				curNumMines++;
			}
		}
		
		
	}
	
	// Count number of mines in surrounding tiles for each tile
	// This method iteratet though the board, find tiles with mine
	// and increment surroundingMines of neighbor tiles
	private void countMines()
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				if(this.board[r][c].mine == true)
				{
					try{ this.board[r-1][c-1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Up left
					try{ this.board[r-1][c].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Up
					try{ this.board[r-1][c+1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Up right
					try{ this.board[r][c-1].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Left
					try{ this.board[r][c+1].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Right
					try{ this.board[r+1][c-1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Down left
					try{ this.board[r+1][c].surroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Down
					try{ this.board[r+1][c+1].surroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Down right
				}
			}
		}
	}

	// Reveal a tile that player choose
	// First check if the tile is a mine, if so, set gameOver to true
	// If a tile is blank tile, reveal its neighbors
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
			if(this.board[r][c].visible == false)
			{
				this.numVisible++;
				this.board[r][c].visible = true;
				// reveal adjacent tiles when it's blank tile
				if(this.board[r][c].surroundingMines == 0)
				{
					this.revealNeighbor(r,c);
				}
			}
		}
	}
	
	// Reveal any non-mine neighbors, recursively reveal all adjcent safe
	// tiles, stop until adjcent tile that has more than one surrouding mine
	private void revealNeighbor(int r, int c)
	{
		try{this.reveal(r-1,c-1); } // Up left
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r-1,c); }   // Up
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r-1,c+1); } // Up right
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r,c-1); }   // Left
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r,c+1); }   // Right
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r+1,c-1); } // Down left
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r+1,c); }   // Down
		catch(IndexOutOfBoundsException e){};
		try{this.reveal(r+1,c+1); } // Down right
		catch(IndexOutOfBoundsException e){};
	}
	
	
	
	// Solve the puzzle
	public static void solve(MineSweeper game)
	{
		Random rd = new Random();
		// fill
	}
	
	
	// Reveal alll tiles for debugging
	private void revealAll()
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				this.board[r][c].visible = true;
			}
		}
		System.out.println(this);
	}
	
	// Make all tile invisible
	private void coverAll()
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				this.board[r][c].visible = false;;
			}
		}
	}
	
	// A cheat for debugging only!;
	private void cheat()
	{
		this.revealAll();
		this.coverAll();
	}
	
	// String representation of game board for player
	// '?' for a unrevealed tile
	// '*' for a revealed tile with mine
	// ' ' for a revealed empty tile
	// '1-8' represen number of mines in surrounding tiles
	public String toString()
	{
        String repr = "   ";
		for(int i = 0; i < this.width(); i++)
		{
			repr += String.format("%2d",i);
		}
		
		repr += "\n" + new String(new char[this.width()*2 +5]).replace("\0","-") + "\n";
        for(int r = 0; r < this.board.length; r++)
		{
			repr += String.format("%2d| ",r);
			for(int c = 0; c < this.width(); c++)
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
		repr += new String(new char[this.width()*2 +5]).replace("\0","-");
		return repr;
    }
	
	// String representation for debugging
	// Show game information such as number of mine, game difficulty
	public String toStringForDebugging()
	{
		return String.format("numMine: %d; gameDif: %s;", this.numMines, this.difficulty);
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
		game.cheat();
		// Keep asking player for next move until gameOver or victory
		while(!game.lose() &&  !game.win())
		{
			System.out.print("Next move (r c): ");
			game.reveal(sc.nextInt(), sc.nextInt());
			System.out.println(game);
		}
		// Check if player win or lose
		if(game.win())
		{
			System.out.println("You WIN!");
		}
		else
		{
			// Reveal all tile if game is over
			game.revealAll();
			System.out.println("Game Over! :(");
		}
		
	}
}
