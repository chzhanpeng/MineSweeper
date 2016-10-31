// Use random to generate mines
import java.util.Random;


public class MineSweeper{


	// Each tile has the following fields:
	// Game board consists a matrix of tile object as mines,
    protected Tile[][] board;
	// Difficulty of the game
	protected String difficulty;
	// Total number of mine of a game
	protected int numMines;
	// Total visible tiles
	protected int numVisible;
    // Total tiles with flag
    protected int numFlags;
	// Whether the game is over
	protected boolean gameOver;

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
		return
            this.allFlagged() ||
            this.height()*this.width() - this.numVisible == this.numMines;
	}

    // Check if all mines are flagged
    public boolean allFlagged()
    {
        for(int r=0; r<this.height(); r++)
        {
            for(int c=0; c<this.width(); c++)
            {
                if(this.board[r][c].mine == true)
                {
                    if(this.board[r][c].flag == false)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // Check if a tile has a flag
    public boolean isFlagged(int row, int col)
    {
        return this.board[row][col].flag;
    }

	// Return game width
	public int width()
	{
		return this.board[0].length;
	}

	// Return game height
	public int height()
	{
		return this.board.length;
	}

    // Flag a tile and increment numFlags
    public void flag(int r, int c)
    {
        this.board[r][c].flag = true;
        this.numFlags++;
    }

    // Unflag a tile and decrement numFlags
    public void unflag(int r, int c)
    {
        this.board[r][c].flag = false;
        this.numFlags--;
    }

	// Return difficulty of the game
	public String gameDifficulty()
	{
		return difficulty;
	}

	// Generate mines randomly
	// Generate tile for each position on board
	// Randomly select random tile to be mine; if tile is already a mine,
	// select another tile, repeat until there are enough mines
	protected void randomGenerate(int n)
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				this.board[r][c] = new Tile(r,c,false);
			}
		}
		Random rd = new Random();
		int curNumMines = 0;
		while(curNumMines < this.numMines)
		{
			int r, c;
			r = rd.nextInt(this.height());
			c = rd.nextInt(this.width());
			if(this.board[r][c].mine == false)
			{
				this.board[r][c].mine = true;
				curNumMines++;
			}
		}
	}

	// Count number of mines in adjacent tiles for each tile
	// This method iteratet though the board, find tiles with mine
	// and increment numSurroundingMines of neighbor tiles
	protected void countMines()
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				if(this.board[r][c].mine == true)
				{
					try{ this.board[r-1][c-1].numSurroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Up left
					try{ this.board[r-1][c].numSurroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Up
					try{ this.board[r-1][c+1].numSurroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Up right
					try{ this.board[r][c-1].numSurroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Left
					try{ this.board[r][c+1].numSurroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Right
					try{ this.board[r+1][c-1].numSurroundingMines++; }
					catch(IndexOutOfBoundsException e){}; // Down left
					try{ this.board[r+1][c].numSurroundingMines++;   }
					catch(IndexOutOfBoundsException e){}; // Down
					try{ this.board[r+1][c+1].numSurroundingMines++; }
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
				if(this.board[r][c].numSurroundingMines == 0)
				{
					this.revealNeighbor(r,c);
				}
			}
		}
	}

	// Reveal any non-mine neighbors, recursively reveal all adjcent safe
	// tiles, stop until adjcent tile that has any surrouding mines
    // Ignore IndexOutOfBoundsException
	protected void revealNeighbor(int r, int c)
	{
		try{ this.reveal(r-1,c-1); } // Up left
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r-1,c); }   // Up
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r-1,c+1); } // Up right
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r,c-1); }   // Left
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r,c+1); }   // Right
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r+1,c-1); } // Down left
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r+1,c); }   // Down
		catch(IndexOutOfBoundsException e){};
		try{ this.reveal(r+1,c+1); } // Down right
		catch(IndexOutOfBoundsException e){};
	}

	// Reveal all tiles for debugging
	protected void revealAll()
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				this.board[r][c].visible = true;
			}
		}
	}

	// Make all tile invisible
	protected void coverAll()
	{
		for(int r = 0; r < this.height(); r++)
		{
			for(int c = 0; c < this.width(); c++)
			{
				this.board[r][c].visible = false;;
			}
		}
	}

	// A cheat for debugging only
	// Reveal all tile and then cover all tile
	protected void cheat()
	{
		this.revealAll();
		System.out.println(this);
		this.coverAll();
	}

	// String representation of game board for player
	// '?' for a unrevealed tile
	// '*' for a revealed tile with mine
	// ' ' for a revealed empty tile
	// '1-8' represen number of mines in surrounding tiles
	//    0 1 2 3
	//-------------
    // 0| ? ? ? ? |
    // 1| ? ? ? ? |
    // 2| ? ? ? ? |
	// 3| ? ? ? ? |
	//-------------
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
                if(this.board[r][c].flag == true)
                {
                    repr += "! ";
                }
                else
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
                            if(this.board[r][c].numSurroundingMines == 0)
                            {
                                repr += "  ";
                            }
                            else
                            {
                                repr += this.board[r][c].numSurroundingMines + " ";
                            }
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


	// Preset a board with mines at desired location
	protected static MineSweeper presetGame()
	{
		MineSweeper game = new MineSweeper(6,6,"easy");
		for(int r=0; r<6; r++)
		{
			for(int c=0; c<6; c++)
			{
				game.board[r][c].mine = false;
				game.board[r][c].numSurroundingMines = 0;
			}
		}
		game.board[1][1].mine = true;
		game.board[5][1].mine = true;
		game.board[1][4].mine = true;
		game.board[4][4].mine = true;
		game.countMines();
		return game;
	}


	public static void main(String[] args)
	{
        //MineSweeper game = MineSweeper.presetGame();
		//Solver.testSolver(game);
		//
		//System.out.println(game);
		//game.testSolver();
	}
}
