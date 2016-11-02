import java.util.ArrayList;
// Solve the MineSweeper game
import java.util.Random;
public class Solver
{

	public static void main(String[] args)
	{
		MineSweeper game = new MineSweeper(8,8,"easy");
		game.cheat();
		Solver solver = new Solver();
		solver.solve(game);
	}

	// Method that solve the puzzle
	public void solve(MineSweeper game)
	{
		Random rd = new Random();
		// First move
		int f1, f2;
		f1 = rd.nextInt(game.height());
		f2 = rd.nextInt(game.width());
		game.reveal(f1,f2);
		// Iterate through board to find reveal tiles, and
		// check if hidden tiles euqals to numSurroundingMines
		while(!game.win())
		{
			this.search(game);
			if(game.lose())
			{
				System.out.println("Sorry Master, I lost.");
				return;
			}
		}
		System.out.println("Master, I win!");
	}

	// Search through board to find mines
	// Uses findMines
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
					System.out.println(game);
				}
				if(this.foundAllAdjacentMines(game,r,c))
				{
					this.revealAdjacentSafeTiles(game,r,c);

				}
			}
		}
	}

	// Reveal all safe unflagged adjacent tiles in the resulting
	// list of safe tiles from findSafeTiles
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

	// Check if numSurroundingMines equals number of surrounding flagged tiles
	// Return a list of tiles that are safe to reveal
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


	// Flag all tile as mine in given list of tiles
	protected  void flagMines(ArrayList<Tile> mines)
	{
		for(Tile mine: mines)
		{
			mine.flag = true;
		}
	}

	// Check if number of surrouding hidden tiles equals numSurroundingMines
	// Return a list of tiles that are 100% mines
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

}
