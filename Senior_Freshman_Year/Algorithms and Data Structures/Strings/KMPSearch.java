import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class KMPSearch {

  /**
   * Bus Service Questions:
   *
   * 1. How many total vehicles is there information on?
   *    Ans: 987 vehicles
   *
   * 2. Does the file contain information about the vehicle number 16555?
   *    Ans: Yes
   *
   * 3. Locate the first record about a bus heading to HAMPTON PARK
   *    Ans: 19774
   *
   * 4. Does the file contain information about the vehicle number 9043409?
   *    Ans: No
   */

   /**
    * Resources used: http://people.cs.pitt.edu/~aus/cs1501/KMP_algorithm.pdf
    *                 https://algs4.cs.princeton.edu/53substring/KMP.java.html
  */

    /**  To keep track of available shifts during each mismatched character we build a DFA (deterministic finite-state automata).
        constructed just from the pattern
    */
    public static int[][] dfa;


/**
 * @Param: String txt - text
 *        String pat - pattern
 *
 * @Task: The method takes in a pattern pat and checks whether it occurs at least once in the String txt. If the pattern
 *       is found at the least once it returns true otherwise it returns false.
 *
 */
    public static boolean contains(String txt, String pat) {
        if(searchFirst(txt, pat) != -1)

            return true;

        else
            return false;
    }


    /**
     *  The method returns the index of the first ocurrence of a pattern pat in String txt.
     *  It should return -1 if the pat is not present
     */
    public static int searchFirst(String txt, String pat) {

        //make sure that the text and pattern actually exist
        if(txt.length() == 0 || pat.length() == 0)
            return -1;

        dfaFormation(pat);

        int pattern = pat.length();
        int text = txt.length();

        int i, j;
        for (i = 0, j = 0; i < text && j < pattern; i++) {
            j = dfa[txt.charAt(i)][j];
        }

        if (j == pattern){
            return i - pattern;    // found an occurrence of the pattern
        }

        return -1;              // pattern does not exist
    }


    /**
     * The method returns the number of non-overlapping occurrences of a pattern pat in String txt.
     */
    public static int searchAll(String txt, String pat) {

        int counter = 0;
        int result;
     boolean continueProcess = true;
        String temp = txt;

        //make sure that the text and pattern actually exist
        if(txt.length() == 0 || pat.length() == 0)
            return 0;

        dfaFormation(pat);

        //Recursively search for string, slicing
        while(continueProcess){
            result = searchFirst(temp, pat);

            //If the specified pattern is found in the text continue the process
            if(result != -1){
                counter++;
                temp = temp.substring(result + pat.length());
            }
            else
                continueProcess = false;
        }

        if(counter != 0)
            return counter;
        else
            return counter;
    }


    /**
     * This method creates a dfa which is then used to process the pattern string.
     *
     */

    public static void dfaFormation(String pat){

        if(pat.length()==0)
            return;

        int alphabetSize = 256; //Standard alphabet length

        // build DFA from pattern
        int patternLength = pat.length();
        dfa = new int[alphabetSize][patternLength];


 /**
  *   DFA will go always (i + 1)'th state from i'th state
  *   if the character c is equal to current character of pattern
  */
        dfa[pat.charAt(0)][0] = 1;

        for (int x = 0, j = 1; j < patternLength; j++) {

            for (int c = 0; c < alphabetSize; c++) {
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            }

            dfa[pat.charAt(j)][j] = j+1;   // Set match case.
            x = dfa[pat.charAt(j)][x];     // Update restart state.
        }

    }


   public static void main(String[] args) throws IOException
   {
//       //Read in a the bus json file
//       String busFile = new String(Files.readAllBytes(Paths.get("BUSES_SERVICE_0.json")));
//
//       String totalNumOfVehicles = "VehicleNo";
//       System.out.println("The total number of vehicles in this file is -> " + searchAll(busFile, totalNumOfVehicles));
//       System.out.println();
//
//
//       String vehicleNumberA = "16555";
//       System.out.println("Is the Vehicle with the ID: " + vehicleNumberA + " present? -> " + contains(busFile, vehicleNumberA));
//       System.out.println();
//
//
//       String vehicleNumberB = "9043409";
//       System.out.println("Is the Vehicle with the ID: " + vehicleNumberB + " present? -> " + contains(busFile, vehicleNumberB));
//       System.out.println();
//
//
//       String destLocation = "HAMPTON PARK";
//       System.out.println("The first occurrence of " + destLocation + " is at index: " + searchFirst(busFile, destLocation));
//       System.out.println();

   }
}