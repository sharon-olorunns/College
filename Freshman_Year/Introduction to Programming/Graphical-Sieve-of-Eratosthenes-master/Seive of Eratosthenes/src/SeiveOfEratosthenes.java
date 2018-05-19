import java.util.Arrays;
import java.util.Scanner;
public class SeiveOfEratosthenes
{
	public static int[] sequence ;
	public static void main(String[] args)
	{
		System.out.print("Please enter a number: ");
		Scanner inputScanner =new Scanner(System.in);
		int N =inputScanner.nextInt();
		sequence=createSequence(N);

		//prints out array in createSequence function
		System.out.println(Arrays.toString(sequence).replace("[","").replace("]",""));
		
		//calls sieve function and prints out results
		sieve(N);
		
	}

	public static int[] createSequence(int N) //CREATE ARRAY WITH 2 ->> N
	{
		int firstPrime=2;
		String string ="";
		int[] array=new int[N-1];
		for(int i=0; i< array.length; i++){
			array[i]=firstPrime++;
		}	
		return array;
	}

	
	public static int[] sieve(int N) 
	{
		int count =1;
		int number=2;
		
		//marking method
		int[] mark= new int[N-1];
		for(int index=0; index< mark.length; index++){
			mark[index]=number++;
		}	

		for(int indexB=0; indexB <mark.length; indexB++){
			count++;
			if(!((mark[indexB]) == 0))
			{
			crossOutMultiples(mark, mark[indexB]); 
			if(count < Math.sqrt(N) && indexB <=Math.sqrt(N)){
				
			sequenceToString(mark);
			System.out.println();
			}
		}
	}
		nonCrossedOutSubseqToString (mark);
		System.out.println();
		return  mark;	
	}

	
	public static void crossOutMultiples(int[] mark, int n) //crosses out multiples
	{
		int[]arrayB=sequence;
		int firstNumber=2;
		if (n >= 2)
		{
			for(int indexC=0; indexC < arrayB.length; indexC++){
				arrayB[indexC]=firstNumber++;

				if((arrayB[indexC]%n == 0 && arrayB[indexC]!= n) )
				{
					mark[indexC] = 0;			
				}	
			}
		}
	}

	public static String sequenceToString (int[]mark){
		int first=2;
		int secondNumber=3;
		String string="";
		String stringB="";
		first=mark[0];
		string=Integer.toString(first);
		System.out.print(string);
		for(int newIndex=1; newIndex< mark.length; newIndex++ ,secondNumber++){
			if(mark[newIndex]==0){
				
				stringB ="["+Integer.toString(secondNumber)+"]";
				System.out.print(","+stringB);
				
			}
			else 
				System.out.print(","+mark[newIndex]);

		}

		return string;
	}

	public static String nonCrossedOutSubseqToString (int[]mark){
		int firstElement=2;
		int secondElement=3;
		String stringA="";
		String stringB="";
		firstElement=mark[0];
		stringA=Integer.toString(firstElement);
		int commaCount = 1;
		System.out.print(firstElement);
		for(int indexD=1; indexD< mark.length-1 && commaCount < mark.length; indexD++,commaCount++){
			if(!(mark[indexD]==0)){
				
				secondElement=mark[indexD];
				stringA=Integer.toString(secondElement);
				System.out.print("," +stringA);
				
			}	
		}
		return stringB;
	}
}
