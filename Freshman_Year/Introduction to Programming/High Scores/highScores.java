import java.util.Scanner;
public class highScores
{
	public static void main(String[] arg)
	{
		try
		{
		System.out.print( "How many high scores do you want to see: " );
		Scanner input = new Scanner( System.in );
		int numberOfScores = input.nextInt();
		int highScores[] = new int[ numberOfScores ];  
		initialiseHighScores(highScores);
		boolean quit = false;  	

		while(!quit)
		{	
			System.out.print( "Enter a score: " );	
			if(input.hasNextInt())
			{ 
				int score =input.nextInt();
				if(higherThan(highScores, score))
				{
					highScores[highScores.length - 1] = score;
					insertScore(highScores,score);
					printHighScores(highScores,score);
				}				
			}		
			else
			{
				quit =false;
				System.out.println("Please enter a valid number");
				input.next();
			}
			
		
		
		}	
		}
		catch (NullPointerException exception)
		{
		}
		catch (java.util.NoSuchElementException exception)
		{
			System.out.print( "No number entered.");
		}
		
		
	}
	
	
	
	public static void initialiseHighScores(int [] highScores)
	{
		for(int index= 0; index < highScores.length; index++)
			highScores[index] =0;	
	}




	public static void insertScore(int [] highScores,int score)  //this code was taken from blackboard
	{
		for (int index=0; index < highScores.length-1; index++)
		{
			int minimumIndex = index;

			for (int index2 = index+1; index2 < highScores.length; index2++)
			{
				if (highScores[index2]  > highScores[minimumIndex])
					minimumIndex = index2 ;
			}
			int temp = highScores[index];
			highScores[index] = highScores[minimumIndex];
			highScores[minimumIndex] = temp;

		}
	
	}
	


	public static void printHighScores(int[] highScores, int score)
	{

		System.out.print(  "The high scores are ");
		for ( int i = 0; i < highScores.length; i++ )
		{
			
			System.out.printf(  "%d " ,highScores[i] );		
		}		
		System.out.println();
	}


	public static boolean higherThan(int [] highScores, int score) 
	{
		int maxValue =score ;
		for (int i = 0; i < highScores.length; i++)
		{ 
			if (highScores[i] < maxValue) 
			{
				return true;
			}
		}
		return false;
	}
}