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
		// Ask player for game difficulty
		System.out.print("Enter game difficulty (easy/medium/hard): ");
		String gameDifficulty = sc.next();
		// Create new game
		MineSweeper game = new MineSweeper(gameWidth, gameHeight, gameDifficulty);
		System.out.println(game);
		game.cheat();
		// Keep asking player for next move until gameOver or victory
		while(!game.lose() && !game.win())
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
