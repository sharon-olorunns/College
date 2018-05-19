import javax.swing.JOptionPane;          
import java.util.Scanner;
import java.text.DecimalFormat;


public class InflationCalculator
{
	public static void main(String [] args)
	  {
		 // These are the conversion rates 
		 
		final int NEW_PENNIES_PER_NEW_POUND = 100;
		 final int OLD_SHILLINGS_PER_OLD_POUND = 20;
		 final int OLD_PENNIES_PER_OLD_SHILLING = 12;
		 final int NEW_PENNIES_PER_OLD_PENNY = 67;
	
	    
		 // The user is now being asked to input Pounds, Shillings and Pence
		 
		 String inputPounds = JOptionPane.showInputDialog(
			     "Type your number of old pounds ");
	     Scanner inputScanner = new Scanner ( inputPounds );
	     int oldPounds = inputScanner.nextInt();
	     inputScanner.close(); 
	
	
	     String inputShilling = JOptionPane.showInputDialog(
			     "Type your number of old shillings ");
	     Scanner inputScanner1 = new Scanner ( inputShilling );
	     int oldShilling = inputScanner1.nextInt();
	     inputScanner1.close(); 
	
	
	     String inputPennies = JOptionPane.showInputDialog(
			     "Type your number of old pennies ");
	     Scanner inputScanner2 = new Scanner ( inputPennies );
	     int oldPennies = inputScanner2.nextInt();
	     inputScanner2.close(); 	
	
	     
	     // This is the conversion of old pounds to new pounds
	     
	     int conversion1 = oldPounds * OLD_SHILLINGS_PER_OLD_POUND; 
	     int conversion2  = conversion1 * OLD_PENNIES_PER_OLD_SHILLING; 
	     int conversion3  = conversion2 * NEW_PENNIES_PER_OLD_PENNY; 
	     int remainder = conversion3 % NEW_PENNIES_PER_NEW_POUND; 
	     int modernPounds = conversion3 / NEW_PENNIES_PER_NEW_POUND;
	
	     
	     // This the conversion of old shillings to new pounds
	     
	     int conversion4 = oldShilling * OLD_PENNIES_PER_OLD_SHILLING;
	     int conversion5 = conversion4 * NEW_PENNIES_PER_OLD_PENNY;
	     int modernPounds2 = conversion5 / NEW_PENNIES_PER_NEW_POUND ;
	     int remainder2 = conversion5 % NEW_PENNIES_PER_NEW_POUND;
	     
	     
	     // This is the conversion of old pennies to new pounds
	     
	     int conversion6 = oldPennies * NEW_PENNIES_PER_OLD_PENNY; 
	     int modernPounds3 = conversion6 / NEW_PENNIES_PER_NEW_POUND; 
	     int remainder3 = conversion6 % NEW_PENNIES_PER_NEW_POUND;
	     
	     /* Decimal Format will ensure the integer values print to decimal.
	        This means we don't have to use doubles to get our final answer
	        in decimal form*/
	     
	     int totalNewPounds = modernPounds + modernPounds2 + modernPounds3;
	     DecimalFormat poundFormatter = new DecimalFormat("0");
	     String newPounds = poundFormatter.format(totalNewPounds);
	     
	     int totalRemainder = remainder + remainder2 + remainder3;
	     DecimalFormat remainderFormatter = new DecimalFormat("00");
	     String newRemainder = remainderFormatter.format(totalRemainder);
		    
	     // This presents a dialog box with the final amount of new pounds
	     
	     JOptionPane.showMessageDialog(null,"You have " + "£" + newPounds +"." +  newRemainder);
	
	}
	
}

