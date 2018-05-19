import java.util.Scanner;
public class IncrementalStatistics
{
	public static void main(String[]arg)
	{
		int numberCount =0;
		double varianceN =0;
		double averageN =0; 
		double varianceMinusOne =0;
		int numberMinusOne =0;
		double averageMinusOne =0; 

		Scanner inputScanner = new Scanner(System.in);
		System.out.print("Enter a number (or type 'exit'):");
		
		while(inputScanner.hasNext())
		{
			if(inputScanner.hasNext("exit"))
			{
				System.err.printf("Goodbye");
				inputScanner.close();
				System.exit(0);
			}
			else if(!inputScanner.hasNextInt())
			{
				System.err.printf("Enter a positive whole number (or type 'exit'):");
				inputScanner.next();
			}
			else
			{
				while(inputScanner.hasNextInt())
				{
					int numberN =inputScanner.nextInt();
				    numberCount++;
				    averageN =(averageMinusOne + (numberN - averageMinusOne) / numberCount);  
				    varianceN =(((varianceMinusOne*numberMinusOne)+(numberN - averageMinusOne)*(numberN - averageN))/numberCount);
				
			        if(numberN > 0)
			        {
			        	System.out.println("So far the average is "+ averageN +" and the variance is "+ varianceN); 
						varianceMinusOne =varianceN;
						averageMinusOne =averageN;
						numberMinusOne =numberCount;
					    System.out.print("Enter a number (or type 'exit'):");
			        }
			        else 
					{
						 System.err.printf("Enter a positive whole number (or type 'exit'):");
						 
					}
				}				
			}
		}		
	}
}
