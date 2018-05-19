public class TriangleAndStar 
{
	public static void main(String[] arg)
	{
		System.out.println("Here are the numbers that are both triangular and star: ");
		
		int triangleNumber = 0;
		for(int index=1; triangleNumber <= Integer.MAX_VALUE && triangleNumber >= 0; index++)
		{
			triangleNumber = triangleNumber + index;
			
			if (isStarNumber(triangleNumber))
				System.out.print(triangleNumber + " ");
		}
	
	}
	
	
	public static boolean isStarNumber (int triangleNumber) 
	{
		
		int starNumber =1 ;
		for(int number=1; determineStarNumber(number) == triangleNumber || determineStarNumber(number) < triangleNumber; number++)
		{
			starNumber = determineStarNumber(number);
		}	
		
		return (triangleNumber == starNumber);
	
	}
	
	
	
	public static int determineStarNumber(int number)
	{
		int starNumber = ((6*number)*(number - 1)) + 1 ;		
		return starNumber;
	}
	
}
