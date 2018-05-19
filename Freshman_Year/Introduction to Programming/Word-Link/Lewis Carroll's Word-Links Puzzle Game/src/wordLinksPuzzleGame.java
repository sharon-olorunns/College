import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

public class wordLinksPuzzleGame {
	public static void main(String[] arg) {

		System.out.println("Enter a comma separated list of words (or an empty list to quit): ");
		Scanner inputScanner = new Scanner(System.in);
		String userList = inputScanner.next();
		inputScanner.close();
		String[] dictionary = readDictionary();
		String[] arrayWord = readWordList(userList);

		for (int i = 0; i <= arrayWord.length; i++) {

			if (isEnglishWord(dictionary, arrayWord[i]))
				isUniqueList(arrayWord,userList);
		}	
	}

	public static String[] readDictionary() {

		In in = new In("words.txt");
		String s = in.readAll();
		String[] array = s.split("\r\n");
		return array;
	}

	public static String[] readWordList(String userList) {
		String[] userArray = userList.split(",\\s*");
		return userArray;
	}

	public static boolean isUniqueList(String[] arrayWord,String userList) {

		for (int index = 0; index < arrayWord.length; index++) {
			for (int indexB = index + 1; indexB < arrayWord.length; indexB++) {
				if (arrayWord[index].equals(arrayWord[indexB])){
					System.out.print("Not a valid chain of words from Lewis Carroll's word-links game.");
					return false;
				}
			}
		}
		isWordChain(arrayWord, userList);
		return true;
	}

	public static boolean isEnglishWord(String[] dictionary, String userInput) {

		int result = Arrays.binarySearch(dictionary, userInput); // positive is
																	// in the
																	// list

		if (result >= 0)
			return true;

		else
			return false;
	}

	public static boolean isDifferentByOne(String str1, String str2) {
		// compare the lengths
		int count = 0;
		boolean same = true;
		if (str1.length() != str2.length()) {
			same = false;
			System.out.print("Not a valid chain of words from Lewis Carroll's word-links game.");
			return same;
		}

		else {
			char[] first = str1.toCharArray();
			char[] second = str2.toCharArray();
			Arrays.sort(first);
			Arrays.sort(second);

			for (int index = 0; index < first.length; index++) {
				
				if (first[index] != second[index] && count == 1) {
					count++;
					same = true;
				

					if ((count > 1) || first.equals(second)) {
						System.out.print("Not a valid chain of words from Lewis Carroll's word-links game.");
						same = false;
					}
				}
				}

			return same;
			
		}
	}

	public static boolean isWordChain(String[] arrayWord, String userList) {

		readDictionary();

		String str1;
		String str2;
		for (int i = 0; i < arrayWord.length;) {

			str1 = arrayWord[i];
			str2 = arrayWord[i + 1];

			if (isDifferentByOne(str1, str2)) {
				i++;
				System.out.println("Valid chain of words from Lewis Carroll's word-links game.");
			}

			else if (!(isDifferentByOne(str1, str2))) {
				System.out.print("Not a valid chain of words from Lewis Carroll's word-links game.");
				return false;
			}
		}
		System.out.println("Valid chain of words from Lewis Carroll's word-links game.");
		return true;

	}
}
