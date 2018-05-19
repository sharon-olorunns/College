import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import java.io.BufferedWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.util.Calendar;



/**
 * Resources: https://crunchify.com/how-to-read-json-object-from-file-in-java/
 *            https://examples.javacodegeeks.com/core-java/trie-tutorial-java/
 *            https://www.tutorialspoint.com/json/json_objects.htm
 *            https://stackoverflow.com/questions/33638765/how-to-read-json-data-from-txt-file-in-java
 *            https://algs4.cs.princeton.edu/52trie/TST.java
 *
 * Bus Service Questions:
 * 1. How many unique destinations is there in the file?
 *    ANS: There are 171 unique destinations
 *
 * 2. Is there a bus going to the destination "SOUTHSIDE"?
 *    ANS: No SOUTHSIDE is not present
 *
 * 3. How many records is there about the buses going to the destination beginning with "DOWN"?
 *    ANS: There are 70 records available
 *
 *
 * Google Books Common Words Questions:
 * 4. How many words is there in the file?
 *    ANS: 97565
 *
 * 5. What is the frequency of the word "ALGORITHM"?
 *    ANS: 14433021
 *
 * 6. Is the word "EMOJI" present?
 *    ANS: NO
 *
 * 7. IS the word "BLAH" present?
 *    ANS: YES
 *
 * 8. How many words are there that start with "TEST"?
 *    ANS: 39
 *
 * */


public class TST<Value> {

    /**
     * a pointer to the start of your trie
     * */
    private TrieNode<Value> root = new TrieNode<Value>(); // root of TST
    private int  size = 0;


    private static class TrieNode<Value> {
        private char c;                                 // character
        private TrieNode<Value> left, mid, right;       // left, middle, and right subtries
        private Value val;                              // value associated with string
    }


    /**
     * Initializes an empty string symbol table.
     */
    public TST(){

    }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }


    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return true if this symbol table contains key and
     *    false otherwise
     */
    public boolean contains(String key) {
        if(key == "")
            return false;

        else
            return (get(key) != null);
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and  null if the key is not in the symbol table
     */
    public Value get(String key) {
        if(key == "")
            return null;

        TrieNode<Value> x = new TrieNode<Value>();
        x = get(root, key, 0);

        if(x == null)
            return null;

        else
            return x.val;
    }


    // return subtrie corresponding to given key
    public TrieNode<Value> get(TrieNode<Value> x, String key, int d){
        if(x == null)
            return null;

        char character;
        character = key.charAt(d);

        //If character is less than node x's character, go left
        if(character < x.c)
            return get(x.left, key, d);

            //If character is more than node x's character, go right
        else if(character > x.c)
            return get(x.right, key, d);

            //Otherwise, if d is still within range, go middle
        else if(d<key.length()-1)
            return get(x.mid, key, d+1);

            //Otherwise, at end. Return x
        else
            return x;
    }


    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     */
    public void put(String key, Value val) {

        if(!contains(key))
            size++;

        root = put(root, key, val, 0);
    }

    private TrieNode<Value> put(TrieNode<Value> x, String key, Value value, int d){
        char c = key.charAt(d);

        //If null node, create new node for char c
        if(x == null){
            x = new TrieNode<Value>();
            x.c = c;
        }

        //If char is less than char of x, go left
        if(c<x.c)
            x.left = put(x.left, key, value, d);

            //If char is more than char of x, go right
        else if(c>x.c)
            x.right = put(x.right, key, value, d);

        else if(d<key.length() - 1)
            x.mid = put(x.mid, key, value, d+1);

            //Otherwise return current node
        else
            x.val = value;

        return x;
    }

    /**
     * Returns all of the keys in the set that start with prefix.
     * @param prefix the prefix
     * @return all of the keys in the set that start with prefix,
     *     as an iterable
     */
    public LinkedList<String> keysWithPrefix(String prefix) {

        LinkedList<String> linkedList = new LinkedList<String>();
        TrieNode<Value> x = get(root, prefix, 0);

        //If no keys found, return null LinkedList
        if(x == null)
            return linkedList;

        Value val = get(prefix);

        if(val != null)
            linkedList.add(prefix);

        //Get remaining keys with prefix from x.mid onwards
        getKeysWithPrefix(x.mid, new StringBuilder(prefix), linkedList);

        return linkedList;
    }

    private void getKeysWithPrefix(TrieNode<Value> x, StringBuilder prefix, LinkedList<String> list){
        if(x == null)
            return;

        //Search left for keys with prefix
        getKeysWithPrefix(x.left, prefix, list);

        //If node has a value, push prefix+c onto list
        if(x.val != null)
            list.add(prefix.toString() + x.c);

        getKeysWithPrefix(x.mid, prefix.append(x.c), list);

        prefix.deleteCharAt(prefix.length()-1);
        getKeysWithPrefix(x.right, prefix, list);
    }


    public LinkedList<String> getAllKeys(){
        LinkedList<String> list = new LinkedList<String>();
        getKeysWithPrefix(root, new StringBuilder(), list);
        return list;
    }


    private static void parseJSONFile(JSONObject route, TST<Integer> busTST){

        String destination = "";
        int value = 0;
        destination = (String) route.get("Destination");

        if(busTST.contains(destination)){
            value = busTST.get(destination);
            busTST.put(destination, value+1);
        }

        else{
            busTST.put(destination, 1);
        }
    }




    public static void main(String[] args) throws IOException, ParseException{


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
//        for (String s : googleFile.keysWithPrefix("TEST")){
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
//        for (String s : busTST.keysWithPrefix("DOWN")){
//            StdOut.println("There are " + busTST.get(s)+ " records of DOWN in the .json file");
//        }
//        StdOut.println();

    }

}