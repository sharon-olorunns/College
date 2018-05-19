public class LyricsShow 
{
	public static final int NUMBER_OF_VERSES = 12;

	public static void main(String[] args) 
	{
		for (int currentVerse = 0; currentVerse <= NUMBER_OF_VERSES; currentVerse++) 
		{
			String constantLineOne = "On the ";
			String constantLineTwo = " day of Christmas \nmy true love sent to me: ";
			String constantLineThree = "Partridge in a Pear Tree\n";
			for (int dayNumber = 1; dayNumber <= NUMBER_OF_VERSES; dayNumber++) 
			{
				int printDay = dayNumber;
				String outputString;
				if (printDay == 1) 
				{
					outputString = "st";
				} 
				else if (printDay == 2) 
				{
					outputString = "nd";
				} 
				else if (printDay == 3) 
				{
					outputString = "rd";
				} 
				else
					outputString = "th";
				{
					System.out.print("\n" + constantLineOne + printDay + outputString + constantLineTwo);
					currentVerse++;

					switch (currentVerse) 
					{

					case 12:
						System.out.print("\n12 Drummers Drumming");
					case 11:
						System.out.print("\n11 Pipers Piping");
					case 10:
						System.out.print("\n10 Lords a Leaping");
					case 9:
						System.out.print("\n9 Ladies Dancing");
					case 8:
						System.out.print("\n8 Maids a Milking");
					case 7:
						System.out.print("\n7 Swans a Swimming");
					case 6:
						System.out.print("\n6 Geese a Laying");
					case 5:
						System.out.print("\n5 Golden Rings");
					case 4:
						System.out.print("\n4 Calling Birds");
					case 3:
						System.out.print("\n3 French Hens");
					case 2:
						System.out.print("\n2 Turtle Doves");
					}

					if (printDay == 1) 
					{
						String andStatement = "\nA ";
						System.out.print(andStatement + constantLineThree);
					} 
					else
						System.out.print("\nAnd a " + constantLineThree);
				}
			}

		}

	}

}
