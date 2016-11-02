import java.util.Random;

// Class used to generate mines
public class Generator{

    // Generate mines randomly
    // Generate tile for each position on board
    // Randomly select random tile to be mine; if tile is already a mine,
    // select another tile, repeat until there are enough mines
    protected static MineSweeper randomGenerate(MineSweeper game, int numMines)
    {
        Random rd = new Random();
        int curNumMines = 0;
        while(curNumMines < numMines)
        {
            int r, c;
            r = rd.nextInt(game.height());
            c = rd.nextInt(game.width());
            if(game.board[r][c].mine == false)
            {
                game.board[r][c].mine = true;
                curNumMines++;
            }
        }
        return game;
    }

    // Guess-free game generator
    protected static MineSweeper smartGenerate(MineSweeper game, int numMines)
    {
        Random rd = new Random();
        int curNumMines = 0;
        while(curNumMines < numMines)
        {
            int r, c;
            r = rd.nextInt(game.height());
            c = rd.nextInt(game.width());
            if(game.)
        }
    }

}
