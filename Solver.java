import java.util.ArrayList;
// Solve the MineSweeper game
public class Solver
{

	public static void main(String[] args)
	{
		MineSweeper game = MineSweeper.presetGame();
		Solver solver = new Solver();
		solver.solve(game);
		ArrayList<Tile> mines = solver.findMines(game, 2,2);
		solver.flagMines(mines);
		game.numFlags += mines.size();
		System.out.println(game);
	}

	// Method that solve the puzzle
	public static void solve(MineSweeper game)
	{
		// First move
		int f1, f2;
		f1 = 3; //rd.nextInt(game.height());
		f2 = 3; //rd.nextInt(game.width());
		game.reveal(f1,f2);
		System.out.println(String.format("First move: %d %d",f1,f2));
		System.out.println(game);
		// Iterate through board to find reveal tiles, and
		// check if hidden tiles euqals to numSurroundingMines
		while(!game.lose() && game.win())
		{
			for(int r = 0; r < game.height(); r++)
			{
				for(int c = 0; c < game.width(); c++)
				{
					if(this.findMines(r,c) != null)
					{
						Solver.flagMines(mines);
						game.numFlags += mines.size();
					}
				}
			}
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

	// Return a list of tile are 100% mines
	// check if number of surrouding hidden tiles is same as numSurroundingMines
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
