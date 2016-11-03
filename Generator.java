import java.util.Random;

// Class used to generate mines
public class Generator{

    // Generate mines randomly
    // Generate tile for each position on board
    // Randomly select random tile to be mine; if tile is already a mine,
    // select another tile, repeat until there are enough mines
    protected void randomGenerate(MineSweeper game, int numMines)
    {
        Random rd = new Random();
        int curNumMines = 0;
        while(curNumMines < numMines)
        {
            int r, c;
            r = rd.nextInt(game.height());
            c = rd.nextInt(game.width());
            // Don't set mine on and one unti around first move
            if(r < game.fmRow+2 && r > game.fmRow-2 &&
            c < game.fmCol+2 && c > game.fmCol-2)
            {
                r = rd.nextInt(game.height());
                c = rd.nextInt(game.width());
            }
            if(game.board[r][c].mine == false)
            {
                game.board[r][c].mine = true;
                curNumMines++;
            }
        }
        this.countAdjacentMines(game);
    }

    // Guess-free game generator
    protected void smartGenerate(MineSweeper game, int numMines)
    {
        Random rd = new Random();
        int curNumMines = 0;
        while(curNumMines < numMines)
        {
            int r, c;
            r = rd.nextInt(game.height());
            c = rd.nextInt(game.width());
        }
    }


    // Count number of mines in adjacent tiles for each tile
    // This method iterate though the board, find tiles with mine
    // and increment numSurroundingMines of neighbor tiles
    protected void countAdjacentMines(MineSweeper game)
    {
        for(int r = 0; r < game.height(); r++)
        {
            for(int c = 0; c < game.width(); c++)
            {
                if(game.board[r][c].mine == true)
                {
                    try{ game.board[r-1][c-1].numSurroundingMines++; }
                    catch(IndexOutOfBoundsException e) {}; // Up left
                    try{ game.board[r-1][c].numSurroundingMines++;   }
                    catch(IndexOutOfBoundsException e) {}; // Up
                    try{ game.board[r-1][c+1].numSurroundingMines++; }
                    catch(IndexOutOfBoundsException e) {}; // Up right
                    try{ game.board[r][c-1].numSurroundingMines++;   }
                    catch(IndexOutOfBoundsException e) {}; // Left
                    try{ game.board[r][c+1].numSurroundingMines++;   }
                    catch(IndexOutOfBoundsException e) {}; // Right
                    try{ game.board[r+1][c-1].numSurroundingMines++; }
                    catch(IndexOutOfBoundsException e) {}; // Down left
                    try{ game.board[r+1][c].numSurroundingMines++;   }
                    catch(IndexOutOfBoundsException e) {}; // Down
                    try{ game.board[r+1][c+1].numSurroundingMines++; }
                    catch(IndexOutOfBoundsException e) {}; // Down right
                }
            }
        }
    }
}
