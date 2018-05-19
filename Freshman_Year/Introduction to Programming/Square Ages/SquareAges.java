import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
public class SquareAges 
{
  public static final  int LATEST_YEAR = 2139;
  public static final int EARLIEST_YEAR = 2016;
  public static void main (String[] args)
  {		
	 int userAge =0;
	 for (userAge = 0; userAge <= 123; userAge++)         
	 { 		  	  
	   if (userAge<= 123)
	   {
	     int ageSquared = userAge*userAge;
	     int birthYear = ageSquared - userAge;
	     GregorianCalendar calendar = new GregorianCalendar();
	     int currentYear = calendar.get(GregorianCalendar.YEAR);
	     int currentAge = currentYear - birthYear;
	     if (birthYear < EARLIEST_YEAR &&  ageSquared > EARLIEST_YEAR ) 
	     {
	        JOptionPane.showMessageDialog(null,"Someone who is currently " + currentAge +" will turn " + userAge + 
	        		                   " in the year that "+ userAge + " is squared, which is " + ageSquared +" , CONGRATULATIONS!");
	     }  
	   }	
	 }
   }
 }
	       
	      