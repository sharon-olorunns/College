import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TSTTest {

  @Test
  public void testEmpty(){
    TST<Long> trie = new TST<>();
    assertEquals("empty trie should have size O",0, trie.size());
      System.out.println(trie.size());

      assertFalse("empty trees return false",trie.contains(""));
      System.out.println(trie.contains(""));

      assertNull("the get method should return null for an empty trie as there is nothing to get",trie.get(""));
      System.out.println(trie.get(""));

  }

  @Test
  public void testSingleElementTrie(){

      TST<Integer> trie = new TST<>();
      trie.put("Panther", 1);

    assertEquals("A trie with one element should return size 1",1, trie.size());
      System.out.println(trie.size());

      assertTrue("Searching if trie contains 'Panther'",trie.contains("Panther"));
      System.out.println(trie.contains("Panther"));


      assertFalse("Searching if trie contains 'Black'",trie.contains("Black"));
      System.out.println(trie.contains("Black"));

      assertFalse("Searching if trie contains 'Avengers'",trie.contains("Avengers"));
    System.out.println(trie.contains("Avengers"));

    assertFalse("Searching if trie contains 'Marvel'",trie.contains("Marvel"));
      System.out.println(trie.contains("Marvel"));


      assertEquals("Getting value when key 'Panther' exists",(Integer) 1, trie.get("Panther"));
      System.out.println(trie.get("Panther"));

      assertNull("Getting value when key 'Black' doesn't exist", trie.get("Black"));
      System.out.println(trie.get("Black"));

      assertNull("Getting value when key 'Avengers' doesn't exist", trie.get("Avengers"));
      System.out.println(trie.get("Avengers"));

      assertNull("Getting value when key 'Marvel' doesn't exist", trie.get("Marvel"));
      System.out.println(trie.get("Marvel"));

  }

  @Test
  public void testTwoElementTrie(){

      TST<Integer> trie = new TST<>();
      trie.put("Panther", 1);
      trie.put("Black", 8);

    assertEquals("The trie containing 'Panther', 'Black' should have size 2",2, trie.size());

    assertTrue("Searching if trie contains 'Panther'",trie.contains("Panther"));
    assertTrue("Searching if trie contains 'Black'",trie.contains("Black"));
    assertFalse("Searching if trie contains 'Shuri'",trie.contains("Shuri"));
    assertFalse("Searching if trie contains 'Okoye'", trie.contains("Okoye"));
    assertFalse("Searching if trie contains 'KilMonger'",trie.contains("KilMonger"));

    assertEquals("Getting value when key 'Black' exists",(Integer) 8, trie.get("Black"));
    assertNull("Getting value when key 'Okoye'", trie.get("Okoye"));
    assertNull("Getting value when key 'KilMonger'", trie.get("KilMonger"));
  }

  @Test
  public void testMultipleElements(){
    TST<Integer> trie = new TST<>();

    trie.put("Black", 1);
    trie.put("Panther", 8);
    trie.put("Billion", 12);
    trie.put("T'Challa", 19);
    trie.put("Nakia", 23);
    trie.put("Suri", 45);
    trie.put("KilMonger", 7);

    assertEquals("This trie contains 7 elements hence the size is 7",7, trie.size());

    assertTrue("Searching if trie contains 'Black' ",trie.contains("Black"));
    assertTrue("Searching if trie contains 'Panther' ",trie.contains("Panther"));
    assertFalse("Searching if trie contains 'Wakanda' ",trie.contains("Wakanda"));
    assertFalse("Searching if trie contains 'Marvel' ",trie.contains("Marvel"));
    assertFalse("Searching if trie contains 'Ryan' ",trie.contains("Ryan"));
    assertFalse("Searching if trie contains 'Coogler' ",trie.contains("Coogler"));

    assertEquals("Getting value when for key 'KilMonger'",(Integer) 7, trie.get("KilMonger"));
    assertNull("Getting value when key 'Ryan' doesn't exist", trie.get("Ryan"));
    assertNull("Getting value when key 'Wakanda' doesn't exist", trie.get("Wakanda"));
    assertNull("Getting value when key 'Marvel' doesn't exist", trie.get("Marvel"));
  }


    public static void main(String[] args)
    {

//        //Tests for google supplied .txt file
//        TST<Long> googleFile = new TST<Long>();
//        for (int i = 0; !StdIn.isEmpty(); i++) {
//            String key = StdIn.readString();
//            Long value = StdIn.readLong();
//            googleFile.put(key, value);
//        }
//
//        StdOut.println("Total words in file = " + googleFile.size());
//        StdOut.println("\nFrequency of word 'ALGORITHM' is " + googleFile.get("ALGORITHM"));
//        StdOut.println("\nIs 'EMOJI' present in the text file? " + googleFile.contains("EMOJI"));
//        StdOut.println("Is 'BLAH' present in the text file? " + googleFile.contains("BLAH"));
//
//        StdOut.println("\nWords with the prefix 'TEST'...");
//        int count = 0;
//
//        for (String s : googleFile.wordsWithPrefix("TEST")){
//            count++;
//            StdOut.println(s +"-> " + count);
//        }

//        //Tests for the .json files
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(new FileReader("C:/Users/Sharon Ollorunniwo/IdeaProjects//String Algorithms/BUSES_SERVICE_0.json"));
//        JSONArray jsonArray = (JSONArray) obj;
//        TST<Integer> busTST = new TST<Integer>();
//
//
//
//        // WRITE .JSON TO .TXT
//        jsonArray.forEach( busRoutes -> parseJSONFile( (JSONObject) busRoutes, busTST ) );
//
//        BufferedWriter buffWriter = null;
//
//        String fileName = "busTST";
//        File textFile = new File(fileName);
//
//        buffWriter = new BufferedWriter(new FileWriter(textFile));
//        LinkedList<String> keysList = busTST.getAllKeys();
//
//        int destNumbers = 1;
//        if (busTST.size() < 1000) {
//
//            StdOut.println("Here are the unique destinations: ");
//
//
//            for (String key : keysList) {
//                StdOut.println(key + " " + busTST.get(key) + " -> "+ destNumbers++);
//                buffWriter.write(key + " " + busTST.get(key) + "\n");
//            }
//            buffWriter.close();
//        }
//
//        StdOut.println("\nIs 'SOUTHSIDE' present in the text file? " + busTST.contains("SOUTHSIDE"));
//        StdOut.println();
//
//        for (String s : busTST.wordsWithPrefix("DOWN")){
//            StdOut.println("There are " + busTST.get(s)+ " records of DOWN in the .json file");
//        }
//        StdOut.println();

    }
}
