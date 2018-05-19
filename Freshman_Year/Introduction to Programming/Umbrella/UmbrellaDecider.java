import javax.swing.JOptionPane;

public class UmbrellaDecider 
{
    public static void main(String[] args) 
  {
     int answer = JOptionPane.showConfirmDialog(null, "Is it raining? ", "Umbrella Decider", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION);

     if (answer == JOptionPane.CLOSED_OPTION) 
         {
           System.exit(0);
         }

     {
       boolean raining = (answer == JOptionPane.YES_OPTION);
       if (raining) 
         {
           JOptionPane.showMessageDialog(null, "I would advise you to put up your umbrella to avoid getting soaked !","Umbrella question", JOptionPane.WARNING_MESSAGE);
         } 
       else if (!raining) 
         {
           int umbrellaUpOrDown = JOptionPane.showConfirmDialog(null, "Is there a possibility of rain ? ", "Possibilty of Rain", JOptionPane.YES_NO_OPTION, 
        		                                                JOptionPane.CLOSED_OPTION);
           if (umbrellaUpOrDown == JOptionPane.CLOSED_OPTION) 
           {
              System.exit(0);
           }
       boolean possibilityOfRain = (umbrellaUpOrDown == JOptionPane.YES_OPTION);
       if (!possibilityOfRain)
          {
             JOptionPane.showMessageDialog(null, "No need for an umbrella today !");
          }
       else 
          {
             JOptionPane.showMessageDialog(null, " There's no need to put up your umbrella, for now anyway ! ");
          }
         }
     }
  }
}