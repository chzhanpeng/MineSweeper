// Use random to generate mines
import java.util.Random;

public class MineSweeper {

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
    // Keep track of first move, this helps in generating mines
    protected int fmRow;
    protected int fmCol;
    // Whether it's brand new game/ no move taken
    protected boolean fresh;

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
        this.fresh = true;
        this.initBoard();
    }

    // Check if player lost a game
    // Player loses by revealing a mine
    public boolean lose()
    {
        return this.gameOver;
    }

    // Check if player wins
    // Player wins by flagging all mines or reveal all safe tiles
    public boolean win()
    {
        return
        this.numFlags == this.numMines ||
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
                    if(this.isFlagged(r,c))
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

    // Initialize board, creates tiles for each position on board
    public void initBoard()
    {
        for(int r = 0; r < this.height(); r++)
        {
            for(int c = 0; c < this.width(); c++)
            {
                this.board[r][c] = new Tile(r,c,false);
            }
        }
    }

    // Reveal a tile that player choose
    // Check if the move is first move, generate game if after first move
    // Then check if the tile is a mine, game is over if so
    // Then check if a tile is blank tile, reveal its neighbors also
    public void reveal(int r, int c)
    {
        if(this.fresh)
        {
            this.fresh = false;
            this.fmRow = r;
            this.fmCol = c;
            Generator gnrt = new Generator();
            gnrt.randomGenerate(this, this.numMines);
            this.numVisible++;
            this.board[r][c].visible = true;
            // reveal adjacent tiles when it's blank tile
            if(this.board[r][c].numSurroundingMines == 0)
            {
                this.revealNeighbor(r,c);
            }
        }
        else
        {
            if(this.board[r][c].mine == true)
            {
                this.gameOver = true;
                this.revealAll();
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
    }

    // Reveal safe neighboring tiles
    // Recursively revealNeighbor for the adjacent safe tiles,
    // stop until adjcent tile that has any surrouding mines
    // Ignore IndexOutOfBoundsException
    protected void revealNeighbor(int r, int c)
    {
        try{ this.reveal(r-1,c-1); } // Up left
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r-1,c); } // Up
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r-1,c+1); } // Up right
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r,c-1); } // Left
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r,c+1); } // Right
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r+1,c-1); } // Down left
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r+1,c); } // Down
        catch(IndexOutOfBoundsException e) {};
        try{ this.reveal(r+1,c+1); } // Down right
        catch(IndexOutOfBoundsException e) {};
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
        System.out.println(this);
    }

    // A cheat for testing purpose ONLY
    // Draw board with all mines marked
    protected void cheat()
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
            repr += "|\n";
        }
        repr += new String(new char[this.width()*2 +5]).replace("\0","-");
        System.out.println(repr);
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
        return String.format("numMine: %d; numFLags: %s;", this.numMines, this.numFlags);
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
                game.board[r][c].visible = false;
                game.board[r][c].numSurroundingMines = 0;
            }
        }
        game.board[1][1].mine = true;
        Generator gnrt = new Generator();
        gnrt.countAdjacentMines(game);
        return game;
    }

    public static void main(String[] args)
    {

    }


}
