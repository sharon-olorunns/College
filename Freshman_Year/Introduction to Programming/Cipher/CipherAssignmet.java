

import java.util.Scanner;


public class Cipher {
	private static final int NUMBER_OF_CHARACTERS_TO_CIPHER = 27;
	private static final String CHARACTERS_TO_CIPHER = "abcdefghijklmnopqrstuvwxyz ";

	private enum MenuOption {
		ENCRYPT, DECRYPT, QUIT
	}

	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		while (true) {
			MenuOption option = printMenu(inputScanner);
			if (option == MenuOption.QUIT) {
				break;
			}
			else if (option == MenuOption.ENCRYPT) {
				encryptOption(inputScanner);
			}
			else {
				decryptOption(inputScanner);
			}
		}
		inputScanner.close();
		System.out.println("Bye Bye!");
	}

	private static void encryptOption(Scanner inputScanner) {
		System.out.println("Input something to encrypt:");
		String inputString = inputScanner.nextLine();
		char[][] cipher = createCipher();
		String encrypted = encrypt(inputString, cipher);
		System.out.println("\nYour original string was:");
		System.out.println("\"" + inputString + "\"\n");
		System.out.println("Your encrypted string is:");
		System.out.println("\"" + encrypted + "\"\n");
		System.out.println("Here's your cipher (try not to lose it):");
		printCipher(cipher);
		System.out.println("Press enter to return to the main menu.");
		inputScanner.nextLine();
	}

	private static String getMappingInput(Scanner inputScanner) {
		return inputScanner.nextLine();
	}

	private static boolean checkValidityOfMapping(String mappingInput) {
		if (mappingInput.equals("space")) {
			return true;
		}
		if (mappingInput.length() != 1) {
			return false;
		}

		char mappingInputChar = mappingInput.toCharArray()[0];

		if (mappingInputChar >= 'a' && mappingInputChar <= 'z') {
			return true;
		}
		return false;
	}

	private static boolean checkIfAlreadyUsed(char[] charactersUnused, char mapping) {
		for (int j = 0; j < NUMBER_OF_CHARACTERS_TO_CIPHER; j++) {
			if (charactersUnused[j] == mapping) {
				charactersUnused[j] = '!';
				return false;
			}
		}
		return true;
	}

	private static char obtainPotentialMapping(String mappingInput) {
		return (mappingInput.equals("space")?' ':mappingInput.charAt(0));
	}

	private static void decryptOption(Scanner inputScanner) {
		System.out.println("Input something to decrypt:");
		String inputString = inputScanner.nextLine();
		char[] charactersToCipher = CHARACTERS_TO_CIPHER.toCharArray();
		char[][] cipher = new char[NUMBER_OF_CHARACTERS_TO_CIPHER][2];
		for (int i = 0; i < NUMBER_OF_CHARACTERS_TO_CIPHER; i++) {
			cipher[i][0] = charactersToCipher[i];
		}
		System.out.println("Okay, you're going to have to input the cipher used to encrypt this.");
		System.out.println("For each letter of the alphabet, please enter the letter it maps onto.");
		System.out.println("This presumes that upper and lowercase letters have the same relative mapping.");
		System.out.println("To map something to space, type 'space' (without quotation marks).");
		System.out.println("Example interaction:\n" +
				"a -> q\n" +
				"b -> r\n" +
				"c -> space... \n");

		char[] lettersUnused = charactersToCipher.clone();

		// Obtain cipher from input
		for (int i = 0; i < NUMBER_OF_CHARACTERS_TO_CIPHER; i++) {
			boolean validEntry = false;
			while (!validEntry) {
				System.out.print(((charactersToCipher[i] == ' ')? "space": charactersToCipher[i]) + " -> ");
				String mappingInput = getMappingInput(inputScanner);
				char potentialMapping = obtainPotentialMapping(mappingInput);
				boolean valid = checkValidityOfMapping(mappingInput);
				boolean used = false;
				if (valid) {
					used = checkIfAlreadyUsed(lettersUnused, potentialMapping);
				}
				if (!valid) {
					System.out.println("That's not a valid mapping, please enter a single lowercase letter or 'space' (without quotation marks).");
				}
				else if (used) {
					System.out.println("That mapping's already been used.");
				}
				else {
					cipher[i][1] = potentialMapping;
					validEntry = true;
				}
			}
		}
		//    inputScanner.next();

		// Allows post-input editing of cipher
		while (true) {
			System.out.println("Is this cipher correct (y/n)");
			printCipher(cipher);
			String answer = inputScanner.nextLine();
			char answerChar = answer.charAt(0);
			if (answer.length() != 1 || (answerChar != 'y' && answerChar != 'n')) {
				System.out.println("That's not a valid answer. Please enter (y/n).");
			}
			else if (answerChar == 'n') {
				swapMappings(inputScanner, cipher);
			}
			else {break;}
		}
		System.out.println("Decrypted String: \"" + decrypt(inputString, cipher) + "\"");
		System.out.println("Press enter to return to the main menu.");
		inputScanner.nextLine();
		clearConsole();
	}

	private static void swapMappings(Scanner inputScanner, char[][] cipher) {
		char mapToChange;
		char mapChangeChar;
		while (true) {
			System.out.println("What mapping you like to like to change?\n" +
					"(Input character at the top of the table)");
			String mapChange = inputScanner.nextLine();
			mapToChange = obtainPotentialMapping(mapChange);
			if ((mapChange.length() == 1 && (mapToChange >= 'a' && mapToChange <= 'z')) || mapToChange == ' ') {
				break;
			}
			else {
				System.out.println("That's not a valid answer. Please enter a lowercase character or 'space'.");
			}
		}
		while (true) {
			System.out.println("What would you like to change the mapping of '" + mapToChange + "' to?");
			String mapChange = inputScanner.nextLine();
			mapChangeChar = obtainPotentialMapping(mapChange);
			if ((mapChange.length() == 1 && (mapChangeChar >= 'a' && mapChangeChar <= 'z')) || mapChangeChar == ' ') {
				break;
			}
			else {
				System.out.println("That's not a valid answer. Please enter a lowercase character or 'space'.");
			}
		}
		char previousChar;
		for (int i = 0; i < cipher.length; i++) {						// Swap mappings so each letter only appears once.
			if (cipher[i][0] == mapToChange) {
				previousChar = cipher[i][1];
				cipher[i][1] = mapChangeChar;
				for (int j = 0; j < cipher.length; j++) {
					if (cipher[j][1] == mapChangeChar && j != i) {
						cipher[j][1] = previousChar;
						System.out.println("Swapping mappings for " + ((cipher[i][0] == ' ')?"space":cipher[i][0]) + " and " + ((cipher[i][1] == ' ')?"space":cipher[i][1]) + ".");
						break;
					}
				}
				break;
			}
		}
	}

	// Main Menu.
	private static MenuOption printMenu(Scanner inputScanner) {
		clearConsole();
		System.out.println("\nI can do three things:\n");
		while (true) {
			System.out.println("1. Encrypt something");
			System.out.println("2. Decrypt something");
			System.out.println("3. Quit\n");
			System.out.println("Please input an option number.");
			if (!inputScanner.hasNextInt()) {
				System.out.println("Sorry, you must enter a valid option number. Try again.\n");
				inputScanner.next();
			}
			else {
				int choice = inputScanner.nextInt();
				inputScanner.nextLine();
				switch (choice) {
					case 1: return MenuOption.ENCRYPT;
					case 2: return MenuOption.DECRYPT;
					case 3: return MenuOption.QUIT;
					default: System.out.println("Sorry, there are only three options. Try again.\n");
				}
			}
		}
	}

	private static void clearConsole() {
		for (int i = 0; i < 100; i++) {
			System.out.print("\n");
		}
	}

	/* All decrypt will do is to swap the rows of the cipher, and
    return the product of another encryption. */
	private static String decrypt(String input, char[][] cipher) {
		char[][] invertedCipher = new char[cipher.length][2];
		for (int i = 0; i < cipher.length; i++) {
			invertedCipher[i][0] = cipher[i][1];
			invertedCipher[i][1] = cipher[i][0];
		}
		System.out.println("Inverted Cipher:");
		printCipher(invertedCipher);
		return encrypt(input, invertedCipher);				//	Don't fail me now, GarbageCollector.
	}

	private static String encrypt(String inputString, char[][] cipher) {
		char[] input = inputString.toCharArray();
		String output = "";
		for (char i: input) {
			boolean toBeCiphered = false;
			if (i == ' ') {                              // If letter is a space
				for (int j = 0; j < cipher.length; j++) {
					if (' ' == cipher[j][0]) {
						output += (char) (cipher[j][1]);
						break;
					}
				}
				toBeCiphered = true;
			}
			else if (i - 'Z' <= 0) {                        // If letter is capital
				for (int j = 0; j < cipher.length; j++) {
					if (i - 'A' + 'a' == cipher[j][0]) {
						output += (char) (cipher[j][1] - 'a' + 'A');
						//System.out.println(i + " -> " + (char) (cipher[j][1] - 'a' + 'A'));
						toBeCiphered = true;
						break;
					}
				}
			}
			else {                                          // If letter is lowercase
				for (int j = 0; j < cipher.length; j++) {
					if (i == cipher[j][0]) {
						output += cipher[j][1];
						//System.out.println(i + " -> " + cipher[j][1]);
						toBeCiphered = true;
						break;
					}
				}
			}
			if (!toBeCiphered) {output += i;}
		}
		return output;
	}

	/* This method for creating a cipher is quite inefficient.
    I added some trace code to see how many times the while loop ran.
    I then ran the program 20 times.
    The while loop ran 100.05 times on average. */
	private static char[][] createCipher() {
		System.out.println("Generating a cipher.");
		char[][] cipher = new char[NUMBER_OF_CHARACTERS_TO_CIPHER][2];
		char[] cipherCharacters = new char[NUMBER_OF_CHARACTERS_TO_CIPHER];
		for (int i = 0; i < CHARACTERS_TO_CIPHER.length(); i++) {
			cipherCharacters[i] = CHARACTERS_TO_CIPHER.toCharArray()[i];
			cipher[i][0] = cipherCharacters[i];
		}
		int lettersUsed = 0;
		//int timesRun = 0;
		while (lettersUsed != NUMBER_OF_CHARACTERS_TO_CIPHER) {
			int randomIndex = (int) (Math.random() * (NUMBER_OF_CHARACTERS_TO_CIPHER));
			if (cipherCharacters[randomIndex] != '!') {
				if (randomIndex == NUMBER_OF_CHARACTERS_TO_CIPHER - 1) {
					cipher[lettersUsed][1] = ' ';
				}
				else {
					cipher[lettersUsed][1] = (char) (randomIndex + 'a');    // casts random ASCII value to character
				}
				cipherCharacters[randomIndex] = '!';
				lettersUsed++;
			}
			//timesRun++;
		}
		//System.out.println("Times Run: " + timesRun);
		return cipher;
	}

	/* This looks pretty because of the Unicode box drawing characters.
    https://en.wikipedia.org/wiki/Box-drawing_character */
	private static void printCipher(char[][] cipher) {
		System.out.print("╭");
		for (int i = 0; i < 2 * cipher.length - 1; i++) {
			if (i % 2 == 0) {
				System.out.print("─");
			} else {
				System.out.print("┬");
			}
		}
		System.out.print("╮");
		System.out.println();
		System.out.print("│");
		for (int i = 0; i < cipher.length; i++) {
			System.out.print(cipher[i][0] + "│");
		}
		System.out.println();
		System.out.print("├");
		for (int i = 0; i < 2 * cipher.length - 1; i++) {
			if (i % 2 == 0) {
				System.out.print("─");
			} else {
				System.out.print("┼");
			}
		}
		System.out.print("┤");
		System.out.println();
		System.out.print("│");
		for (int i = 0; i < cipher.length; i++) {
			System.out.print(cipher[i][1] + "│");
		}
		System.out.println();
		System.out.print("╰");
		for (int i = 0; i < 2 * cipher.length - 1; i++) {
			if (i % 2 == 0) {
				System.out.print("─");
			}
			else {
				System.out.print("┴");
			}
		}
		System.out.print("╯\n");
	}
}