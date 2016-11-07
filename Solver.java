// To store a list of tiles
import java.util.ArrayList;
// Use random to make first move
import java.util.Random;
//
import java.util.HashSet;

// Solve the MineSweeper game
// The game must be guess free after first move in order to solve puzzle completely
public class Solver
{

	protected HashSet<Coord> visited ;//Store all visted coordinates

	public Solver()
	{
		this.visited = new HashSet<Coord>();
	}

	public static void main(String[] args)
	{
		MineSweeper game = new MineSweeper(20, 20, "medium");
		Solver solver = new Solver();
		solver.solve(game);
	}

	// Method that solve the puzzle
	// First move always random
	// Iterate through the board and use foundAllAdjacentMines to find mines
	// and use findAdjacentSafeMines to find safe tiles until victory
	public void solve(MineSweeper game)
	{
		Random rd = new Random();
		// First move
		int r,c;
		r = rd.nextInt(game.height());
		c = rd.nextInt(game.width());
		System.out.println(String.format("first move: %d %d",r,c));
		game.reveal(r,c);
		System.out.println("CHEAT:");
		game.cheat();
		System.out.println(game);
		System.out.println("^\n^\n");
		// Keep searching until win
		int step = 0;
		while(!game.win() && !game.lose())
		{
			if(step%20 == 0)
			System.out.println(step);
			step++;
			if(step>300)
			{
				break;
			}
			String lastRound = game.toString();
			this.search(game);
			if(lastRound.equals(game.toString()))
			{
				System.out.println("No move can be taken without gussing.");
				break;
			}
		}
		if(game.lose())
		{
			System.out.println("Sorry Master, I lost.");
			return;
		}
		else if(game.win())
		{
			System.out.println(game);
			System.out.println("Master, I win!");
		}
		else
		{
			System.out.println("unfinished");
		}
	}

	// Search through board to findMines and find safe tile
	// uses findMines to find mines and use foundAllAdjacentMines
	// to find safe tiles
	protected void search(MineSweeper game)
	{
		for(int r = 0; r < game.height(); r++)
		{
			for(int c = 0; c < game.width(); c++)
			{
				ArrayList<Tile> mines = this.findMines(game,r,c);
				if(mines != null)
				{
					this.flagMines(mines);
					game.numFlags += mines.size();
				}
				//System.out.println(clueless);
				if(!this.visited.contains(new Coord(r,c)))
				{
					if(this.foundAllAdjacentMines(game,r,c))
					{
						this.revealAdjacentSafeTiles(game,r,c);
						this.visited.add(new Coord(r,c));
					}
				}
			}
		}
		//System.out.println(clueless);
	}

	// Reveal all safe unflagged adjacent tiles
	// Use foundAllAdjacentMines to check if tile is safe to use this method
	protected void revealAdjacentSafeTiles(MineSweeper game, int r, int c)
	{
		try{										// Up left
			if(!game.isFlagged(r-1,c-1))
			{
				game.reveal(r-1,c-1);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Up
			if(!game.isFlagged(r-1,c))
			{
				game.reveal(r-1,c);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Up right
			if(!game.isFlagged(r-1,c+1))
			{
				game.reveal(r-1,c+1);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Left
			if(!game.isFlagged(r,c-1))
			{
				game.reveal(r,c-1);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Right
			if(!game.isFlagged(r,c+1))
			{
				game.reveal(r,c+1);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down left
			if(!game.isFlagged(r+1,c-1))
			{
				game.reveal(r+1,c-1);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down
			if(!game.isFlagged(r+1,c))
			{
				game.reveal(r+1,c);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down right
			if(!game.isFlagged(r+1,c+1))
			{
				game.reveal(r+1,c+1);
			}
		}catch(IndexOutOfBoundsException e){}
	}

	// Check if a tile has adjacent mines all flagged
	// Check if numSurroundingMines equals number of surrounding flagged tiles
	// Return true if all neighbor mines have been found
	// Ignore IndexOutOfBoundsException
	protected boolean foundAllAdjacentMines(MineSweeper game, int r, int c)
	{
		int count = 0;
		try{										// Up left
			if(game.isFlagged(r-1,c-1))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Up
			if(game.isFlagged(r-1,c))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Up right
			if(game.isFlagged(r-1,c+1))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Left
			if(game.isFlagged(r,c-1))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Right
			if(game.isFlagged(r,c+1))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down left
			if(game.isFlagged(r+1,c-1))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down
			if(game.isFlagged(r+1,c))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down right
			if(game.isFlagged(r+1,c+1))
			{
				count++;
				if(count==game.board[r][c].numSurroundingMines){
					return true;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		return false;
	}

	// Check if number of surrouding hidden tiles equals numSurroundingMines
	// If condition is met, that means all hidden tiles are mines,
	// Return those hidden tiles as a list
	// Ignore IndexOutOfBoundsException
	protected ArrayList<Tile> findMines(MineSweeper game, int r, int c)
	{
		ArrayList<Tile> mines = new ArrayList<Tile>();
		int count = 0;
		try{                          							// Up left
			if(game.board[r-1][c-1].visible == false)
			{
				count++;
				mines.add(game.board[r-1][c-1]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Up
			if(game.board[r-1][c].visible == false)
			{
				count++;
				mines.add(game.board[r-1][c]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Up right
			if(game.board[r-1][c+1].visible == false)
			{
				count++;
				mines.add(game.board[r-1][c+1]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Left
			if(game.board[r][c-1].visible == false)
			{
				count++;
				mines.add(game.board[r][c-1]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Right
			if(game.board[r][c+1].visible == false)
			{
				count++;
				mines.add(game.board[r][c+1]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Down left
			if(game.board[r+1][c-1].visible == false)
			{
				count++;
				mines.add(game.board[r+1][c-1]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Down
			if(game.board[r+1][c].visible == false)
			{
				count++;
				mines.add(game.board[r+1][c]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		try{                          							// Down right
			if(game.board[r+1][c+1].visible == false)
			{
				count++;
				mines.add(game.board[r+1][c+1]);
				if(count > game.board[r][c].numSurroundingMines)
				{return null;}
			}
		}catch( IndexOutOfBoundsException e){};
		return mines;
	}

	// Flag all tile as mine in given list of tiles
	protected void flagMines(ArrayList<Tile> mines)
	{
		for(Tile mine: mines)
		{
			mine.flag = true;
		}
	}

}
