import java.util.Random;
import java.util.Scanner;

public class CardGame 
{
	private static final int CORRECT_ANSWERS = 4;
	private static final int ZERO_CONSTANT = 0;

	public static void main(String[] args) 
	{
		Random cardGenerator = new Random();
		int randomCard = cardGenerator.nextInt(12) + 2;
		System.out.println(	"In the Hi-Lo card game the player is presented with an initial card (2 – 10, Jack, Queen, King, or Ace)"
		+ " \nYou must guess if the next card will be Higher (Hi), Lower (Lo) or Equal to the current card.\nYou must guess successfully 4 times in a row in order to win."
		+ " \n\n\nYou have card  " + randomCard + ", is the next card Higher,Lower or Equal to "+ randomCard + " ?");
		int winningGuesses = 0;

		while ((winningGuesses < CORRECT_ANSWERS) && (randomCard != ZERO_CONSTANT)) 
		{	
			int nextRandomCard = cardGenerator.nextInt(12) + 2;
			Scanner userGuess = new Scanner(System.in);

			if (userGuess.hasNextInt())
			{
				System.err.printf("Enter Higher (hi), Lower (lo) or equal\n");
				userGuess.next();
			}
			if (userGuess.hasNext("exit") || userGuess.hasNext("quit")) 
			{
				System.err.printf("Goodbye");
				userGuess.close();
				System.exit(0);
			}

			if ((nextRandomCard < 9) && (nextRandomCard > randomCard) && (userGuess.hasNext("hi"))) 
			{
				winningGuesses++;
				System.out.println("Correct card " + nextRandomCard + " is higher ");
			}
			if ((nextRandomCard > 9) && (nextRandomCard > randomCard) && (userGuess.hasNext("hi"))) 
			{
				if (nextRandomCard == 10) 
				{
					winningGuesses++;
					System.out.println("Correct Queen is higher");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 11) 
				{
					winningGuesses++;
					System.out.println("Correct King is higher");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 12) 
				{
					winningGuesses++;
					System.out.println("Correct Jack is higher");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 13) 
				{
					winningGuesses++;
					System.out.println("Correct Ace is higher");
					userGuess.hasNext();
				}
			}

			if ((nextRandomCard > randomCard) && !(userGuess.hasNext("hi"))) 
			{
				System.out.println("Incorrect, Try again");
				winningGuesses = 0;
				userGuess.hasNext();
			} 
			else if ((nextRandomCard < 9) && (nextRandomCard < randomCard) && (userGuess.hasNext("lo"))) 
			{
				winningGuesses++;
				System.out.println("Correct card " + nextRandomCard + " is lower ");
			}
			if ((nextRandomCard > 9) && (nextRandomCard < randomCard) && (userGuess.hasNext("lo")))
			{
				if (nextRandomCard == 10) 
				{
					winningGuesses++;
					System.out.println("Correct Queen is lower");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 11)
				{
					winningGuesses++;
					System.out.println("Correct King is lower");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 12) 
				{
					winningGuesses++;
					System.out.println("Correct Jack is lower");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 13) 
				{
					winningGuesses++;
					System.out.println("Correct Ace is lower");
					userGuess.hasNext();
				}
			} 
			else if ((nextRandomCard < randomCard) && !(userGuess.hasNext("lo"))) 
			{
				System.out.println("Incorrect, Try again");
				winningGuesses = 0;
				userGuess.hasNext();
			}

			if ((nextRandomCard < 9) && (nextRandomCard == randomCard) && (userGuess.hasNext("equal"))) 
			{
				winningGuesses++;
				System.out.println("Correct card " + nextRandomCard + " is equal to  ");
			}
			if ((nextRandomCard > 9) && (nextRandomCard < randomCard) && (userGuess.hasNext("equal"))) 
			{
				if (nextRandomCard == 10) 
				{
					winningGuesses++;
					System.out.println("Correct Queen is equal");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 11) 
				{
					winningGuesses++;
					System.out.println("Correct King is equal");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 12) 
				{
					winningGuesses++;
					System.out.println("Correct Jack is equal");
					userGuess.hasNext();
				} 
				else if (nextRandomCard == 13) 
				{
					winningGuesses++;
					System.out.println("Correct Ace is equal");
					userGuess.hasNext();
				}
			} 
			else if ((nextRandomCard == randomCard) && !(userGuess.hasNext("equal"))) 
			{
				System.out.println("Incorrect, Try again");
				winningGuesses = 0;
				userGuess.hasNext();
			}
		}

	}
}