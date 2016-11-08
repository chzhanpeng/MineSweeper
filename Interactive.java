// Scanner to read player's inputs
import java.util.Scanner;

// This class interacts with player to play MineSweeper
// Ask user for game size and difficulty to create game
// Then ask user for next move repetitively until user win or lose
public class Interactive
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in); // Read inputs
		// Ask player for game size
		System.out.print("Enter game size (n m): ");
		int gameWidth = sc.nextInt();
		int gameHeight = sc.nextInt();
		//Force the player to choose an integer between 8 and 15
                while ((gameWidth < 8 || gameWidth > 20) || (gameHeight < 8 || gameHeight > 20) )
                {
                    System.out.println("ERROR! Invalid size");
                    System.out.print("Enter game size (n m): ");
                    gameWidth = sc.nextInt();
                    gameHeight = sc.nextInt();
                }
		// Ask player for game difficulty
		System.out.print("Enter game difficulty (easy/medium/hard): ");
		String gameDifficulty = sc.next();
		// Create new game
		MineSweeper game = new MineSweeper(gameWidth, gameHeight, gameDifficulty);
		System.out.println(game);
		Interactive.interact(game);
	}

	// Keep asking player for next move until gameOver or victory
	// Player can choose to flag, unlfag or reveal a tile
	public static void interact(MineSweeper game)
	{
		Scanner sc = new Scanner(System.in); // Read inputs
		System.out.print("Next move (flag/unflag/reveal r c): ");
		game.reveal(sc.nextInt(), sc.nextInt());
		System.out.println(game.numVisible);

		while(!game.lose() && !game.win())
		{
			System.out.print("Next move (flag/unflag/reveal r c): ");
			String nextMove = sc.next();
			if(nextMove.equals("flag"))
			{
				game.flag(sc.nextInt(), sc.nextInt());
			}
			else if(nextMove.equals("unflag"))
			{
				game.unflag(sc.nextInt(), sc.nextInt());
			}
			else
			{
				game.reveal(sc.nextInt(), sc.nextInt());
			}
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
