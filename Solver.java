import java.util.ArrayList;
// Solve the MineSweeper game
public class Solver
{

	public static void main(String[] args)
	{
		MineSweeper game = MineSweeper.presetGame();
		game.cheat();
		Solver solver = new Solver();
		solver.solve(game);
	}

	// Method that solve the puzzle
	public void solve(MineSweeper game)
	{
		// First move
		int f1, f2;
		f1 = 3; //rd.nextInt(game.height());
		f2 = 3; //rd.nextInt(game.width());
		game.reveal(f1,f2);
		System.out.println(game);
		// Iterate through board to find reveal tiles, and
		// check if hidden tiles euqals to numSurroundingMines
		while(!game.win())
		{
			this.search(game);
		}
		if(game.win())
		{
			System.out.println("I win, I as in Program.");
		}
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
					System.out.println(game);
					game.numFlags += mines.size();
				}
			}
		}
	}

	// Reveal all safe unflagged adjacent tiles in the resulting
	// list of safe tiles from findSafeTiles
	protected void revealTiles(MineSweeper game, ArrayList<Tile> safeTiles)
	{
		for(Tile tile : safeTiles)
		{
			try{											// Up left
				if(!game.isFlagged(tile.row-1,tile.col-1)){
					game.reveal(tile.row-1,tile.col-1);
				}
			}
			catch (IndexOutOfBoundsException e){}
			try{											// Up
				if(!game.isFlagged(tile.row-1,tile.col)){
					game.reveal(tile.row-1,tile.col);
				}
			}catch(IndexOutOfBoundsException e){}
			try{											// Up right
				if(!game.isFlagged(tile.row-1,tile.col+1)){
					game.reveal(tile.row-1,tile.col+1);
				}
			}catch(IndexOutOfBoundsException e){}
			try{											// Left
				if(!game.isFlagged(tile.row,tile.col-1)){
					game.reveal(tile.row,tile.col-1);
				}
			}catch(IndexOutOfBoundsException e){}
			try{											// Right
				if(!game.isFlagged(tile.row,tile.col+1)){
					game.reveal(tile.row,tile.col+1);
				}
			}catch(IndexOutOfBoundsException e){}
			try{											// Down left
				if(!game.isFlagged(tile.row+1,tile.col-1)){
					game.reveal(tile.row+1,tile.col-1);
				}
			}catch(IndexOutOfBoundsException e){}
			try{											// Down
				if(!game.isFlagged(tile.row+1,tile.col)){
					game.reveal(tile.row+1,tile.col);
				}
			}catch(IndexOutOfBoundsException e){}
			try{											// Down right
				if(!game.isFlagged(tile.row+1,tile.col+1)){
					game.reveal(tile.row+1,tile.col+1);
				}
			}catch(IndexOutOfBoundsException e){}
		}
	}



	// Check if numSurroundingMines equals number of surrounding flagged tiles
	// Return a list of tiles that are safe to reveal
	// Ignore IndexOutOfBoundsException
	protected ArrayList<Tile> findSafeTiles(MineSweeper game, int r, int c)
	{
		ArrayList<Tile> safeTiles = new ArrayList<Tile>();
		int count = 0;
		try{										// Up left
			if(game.isFlagged(r-1,c-1))
			{
				count++;
				safeTiles.add(game.board[r-1][c-1]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Up
			if(game.isFlagged(r-1,c))
			{
				count++;
				safeTiles.add(game.board[r-1][c]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Up right
			if(game.isFlagged(r-1,c+1))
			{
				count++;
				safeTiles.add(game.board[r-1][c+1]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Left
			if(game.isFlagged(r,c-1))
			{
				count++;
				safeTiles.add(game.board[r][c-1]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Right
			if(game.isFlagged(r,c+1))
			{
				count++;
				safeTiles.add(game.board[r][c+1]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down left
			if(game.isFlagged(r+1,c-1))
			{
				count++;
				safeTiles.add(game.board[r+1][c-1]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down
			if(game.isFlagged(r+1,c))
			{
				count++;
				safeTiles.add(game.board[r+1][c]);
			}
		}catch(IndexOutOfBoundsException e){}
		try{										// Down right
			if(game.isFlagged(r+1,c+1))
			{
				count++;
				safeTiles.add(game.board[r+1][c+1]);
			}
		}catch(IndexOutOfBoundsException e){}
		if(count > game.board[r][c].numSurroundingMines)
		{
			return null;
		}
		else
		{
			return safeTiles;
		}
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
