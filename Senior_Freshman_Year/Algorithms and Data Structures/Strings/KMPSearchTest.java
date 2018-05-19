import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KMPSearchTest {


  @Test
  public void testEmpty(){
    assertEquals("Empty text or pattern is invalid",-1,KMPSearch.searchFirst("",""));
    assertEquals("Empty text or pattern is invalid",0,KMPSearch.searchAll("",""));
    assertFalse("Empty text or pattern is invalid",KMPSearch.contains("",""));
  }

  @Test
  public void testContains(){
    String pat = "black";
    String txt = "dajhdasgyblackblackshgashygblacksgablackblacksajhgajsygblack";

    assertEquals("Checking index if pattern is contained in text",9, KMPSearch.searchFirst(txt,pat));
    assertEquals("Checking number of pattern occurrences if it is contained in text",6, KMPSearch.searchAll(txt,pat));
    assertFalse("Checking boolean result for the pattern if it is contained in text",!KMPSearch.contains(txt,pat));
  }


  @Test
  public void testContainsA(){
    String pat = "panther";
    String txt = "apantherpantherpantherpantherpantherashdagjsdfyfupantherpantherpantherasdgaysupantherasdguypanther";

    assertEquals("Checking index if pattern is contained in text",1,KMPSearch.searchFirst(txt,pat));
    assertEquals("Checking number of pattern occurrences if it is contained in text",10,KMPSearch.searchAll(txt,pat));
    assertFalse("Checking boolean result for the pattern if it is contained in text",!KMPSearch.contains(txt,pat));
  }


  @Test
  public void testContainsB(){
    String pat = "mpc";
    String txt = "dajdgauyfuacfmpcdhaiygcaucacf";

    assertEquals("Checking index if pattern is contained in text", 13,KMPSearch.searchFirst(txt,pat));
    assertEquals("Checking number of pattern occurrences if it is contained in text",1,KMPSearch.searchAll(txt,pat));
    assertFalse("Checking boolean result for the pattern if it is contained in text",!KMPSearch.contains(txt,pat));
  }

  @Test
  public void testDoesNotContain(){
    String pat = "cantfind";
    String txt = "hguyglgiyhaystackneedlesgahsasneedlenhjujyfujaaghdhasudasdneeeedlewqhdqhwneedle";

    assertEquals("Checking index if pattern is not contained in text", -1,KMPSearch.searchFirst(txt,pat));
    assertEquals("Checking number of pattern occurrences if it is not contained in text",0,KMPSearch.searchAll(txt,pat));
    assertFalse("Checking boolean result for the pattern if it is not contained in text",KMPSearch.contains(txt,pat));
  }


  @Test
  public void testDoesNotContainA(){
    String pat = "bestMovie";
    String txt = "sdygcjcvsyyvjachvbvjdfbvduifvgkbskvzjybjkhvzd";

    assertEquals("Checking index if pattern is not contained in text", -1,KMPSearch.searchFirst(txt,pat));
    assertEquals("Checking number of pattern occurrences if it is not contained in text",0,KMPSearch.searchAll(txt,pat));
    assertFalse("Checking boolean result for the pattern if it is not contained in text",KMPSearch.contains(txt,pat));
  }


  // ----------------------------------------------------------
  /**
   *  Main Method.
   *  Use this main method to create the experiments needed to answer the experimental performance questions of this assignment.
   * @throws FileNotFoundException
   *
   */
  public static void main(String[] args)
  {
//Read in a the bus json file
//    String busFile = new String(Files.readAllBytes(Paths.get("BUSES_SERVICE_0.json")));
//
//    String totalNumOfVehicles = "VehicleNo";
//    System.out.println("The total number of vehicles in this file is -> " + searchAll(busFile, totalNumOfVehicles));
//    System.out.println();
//
//
//    String vehicleNumberA = "16555";
//    System.out.println("Is the Vehicle with the ID: " + vehicleNumberA + " present? -> " + contains(busFile, vehicleNumberA));
//    System.out.println();
//
//
//    String vehicleNumberB = "9043409";
//    System.out.println("Is the Vehicle with the ID: " + vehicleNumberB + " present? -> " + contains(busFile, vehicleNumberB));
//    System.out.println();
//
//
//    String destLocation = "HAMPTON PARK";
//    System.out.println("The first occurrence of " + destLocation + " is at index: " + searchFirst(busFile, destLocation));
//    System.out.println();
//
 }
}