import java.util.Scanner;

public class DateConversion {

	public static void main(String[] args) {
		System.out.println("Input a date in the form DD/MM/YYYY");
		int[] dates = getInput();
		if (dates[3] == -1) {
			System.out.println("That is not valid input, try consulting a keymap");
		}
		else if (checkValidity(dates)) {
			System.out.println(dayOfTheWeek(dates) + ", " + dates[0] + ordinalNumberEnding(dates[0]) + " "
					+ monthName(dates[1]) + " " + dates[2]);
		}
		else {
			System.out.println("That isn't a valid date, try consulting a calendar.");
		}
	}

	private static boolean checkValidity(int[] dates) {
		int day = dates[0];
		int month = dates[1];
		int year = dates[2];

		if (day < 1 || month < 1 || year < 0 || month > 12) {
			return false;
		}

		// 30 days has September, April, June and November
		if (month == 9 || month == 4 || month == 6 || month == 11) {
			if (day > 30) {
				return false;
			}
		}
		// Except in leap years, that's the time, when February has 29
		else if (month == 2) {
			boolean isLeapYear = leapYear(dates[2]);
			if (isLeapYear && (day > 29)) {
				return false;
			}
			else if (!isLeapYear && day > 28) {
				return false;
			}
		}
		// All the rest have 31
		else {
			if (day > 31) {
				return false;
			}
		}

		return true;
	}

	private static boolean leapYear(int year) {
		if (year % 400 == 0) {
			return true;
		}
		else if (year % 100 == 0) {
			return false;
		}
		else if (year % 4 == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private static String ordinalNumberEnding(int day) {
		if ((day > 9) && (day < 20)) {
			return "th";
		}
		else if (day % 10 == 1) {
			return "st";
		}
		else if (day % 10 == 2) {
			return "nd";
		}
		else if (day % 10 == 3) {
			return "rd";
		}
		else {
			return "th";
		}
	}

	private static String dayOfTheWeek(int[] dates) {
		int day = dates[0];
		int month = dates[1];
		int year = dates[2];

		if (month < 3) {
			year --;
		}
		int y = year % 100; // Last two digits of year
		int c = year / 100; // First two digits of year

		int dayNumber = (int) (day + Math.floor(2.6 * (((month + 9) % 12) + 1) - 0.2) + y + Math.floor(y/4) + Math.floor(c/4) - 2 * c) % 7;
		dayNumber = (dayNumber < 0)? dayNumber + 7: dayNumber;

		String[] dayArray = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		return dayArray[dayNumber];

	}

	private static String monthName(int month) {
		String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		return monthArray[month - 1];
	}

	private static int[] getInput() {
		Scanner input = new Scanner(System.in);
		int[] dates = new int[4];
		dates[3] = 0;
		input.useDelimiter("[/\n]");
		for (int i = 0; i < 3; i++) {
			if (input.hasNextInt()) {
				dates[i] = input.nextInt();
			}
			else {
				dates[3] = -1;
			}
		}
		input.close();
		// System.out.println("dates: " + dates[0] + " " + dates[1]);
		return dates;
	}
}