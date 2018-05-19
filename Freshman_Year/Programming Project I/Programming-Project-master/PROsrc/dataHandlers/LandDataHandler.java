package dataHandlers;

import java.util.ArrayList;
import java.util.Arrays;

//import org.codehaus.plexus.util.StringUtils;
import processing.core.PApplet;
import processing.data.IntDict;
import processing.data.Table;

public class LandDataHandler {
	static PApplet p;

	// constructs a landDataHandler object
	public LandDataHandler(PApplet parent) {
		p = parent;
	}

	// Takes a table and sorts it by the price column
	public static void sortByPrice(Table table) {
		table.sort(LandData.PRICE);
	}

	// Takes a table and sorts it by the county column
	public static void sortByCounty(Table table) {
		table.sort(LandData.COUNTY);
	}

	// returns a string array that contains a unique list of all the counties
	public static String[] getCountyList(String[] countyArray) {
		String county = "";
		String countyList = "";
		Arrays.sort(countyArray);
		for (int index = 0; index < countyArray.length; index++) {
			if (!county.equals(countyArray[index])) {
				countyList += countyArray[index] + ",";
				county = countyArray[index];
			}
		}
		if (countyList.length() != 0)
			return countyList.substring(0, countyList.length() - 1).split(",");
		return new String[]{};
	}

	// Takes a String array, records the number of occurrences of each element
	// in the String array and returns those values as a String array
	private static int[] getCountOf(String[] countToGet) {
		IntDict dictionary = new IntDict();
		for (String tempString : countToGet)
			dictionary.increment(tempString);
		return dictionary.valueArray();
	}

	// returns the number of occurrences for each county
	public static int[] getCountPerCounty(String[] countyList) {
		return getCountOf(countyList);
	}

	// returns the number of occurences for each county as a string array
	public static float[] getFloatCountPerCounty(String[] countyList) {
		int[] countOfCounties = getCountOf(countyList);
		float[] countFloat = new float[countOfCounties.length];
		for (int index = 0; index < countOfCounties.length; index++)
			countFloat[index] = (float) countOfCounties[index];
		return countFloat;
	}

	public static float[] getCountOfProperties(String[] countyList, Table table) {
		float[] resultCount = new float[countyList.length];
		for (int index = 0; index < table.getRowCount(); index++)
			for (int i = 0; i < countyList.length; i++)
				if (countyList[i].equals(table.getString(index, LandData.COUNTY))) {
					resultCount[i]++;
					break;
				}
		return resultCount;
	}

	// returns an int array containing the number average price of houses each
	// year;
	public static int[] getAveragePricesOverTime(LandData data) {
		Table landData = data.getDateSortedTable();
		String[] dates = landData.getStringColumn(LandData.DATE_OF_SALE);
		for (int index = 0; index < dates.length; index++) {
			dates[index] = dates[index].substring(6);
		}
		int[] a = new int[0];
		int index = 0;
		String date = dates[0];
		while (index != dates.length) {
			long tally = 0;
			int num = 0;
			while (index != dates.length && date.equals(dates[index])) {
				tally += landData.getInt(index++, LandData.PRICE);
				num++;
			}
			date = dates[index == dates.length ? 0 : index];
			int[] b = new int[a.length + 1];
			System.arraycopy(a, 0, b, 0, a.length);
			b[b.length - 1] = (int) (tally / (num == 0 ? 1 : num));
			a = b.clone();
		}
		return a;
	}

	// takes the price groups and the number of groups and produces a String
	// representation of those groups e.g 0-40000, 40001 - 80000 e.t.c
	public static String[] getPriceGroups(int numOfGroups, int priceRange) {
		String[] strings = new String[numOfGroups];
		for (int index = 0; index < strings.length; index++) {
			strings[index] = "£" + ((index == 0 ? 0 : 1) + priceRange * index) + " - " + "£" + priceRange * (index + 1);
		}
		return strings;
	}

	// returns a table with houses within a given price range
	public static Table getPropertiesWithinPriceRange(int minPrice, int maxPrice, Table table) {
		Table subTable = new Table();
		for (int i = 0; i < LandData.TITLES.length; i++) {
			subTable.addColumn();
			subTable.setColumnTitle(i, LandData.TITLES[i]);
		}
		for (int index = 0; index < table.getRowCount(); index++) {
			if (table.getInt(index, LandData.PRICE) <= maxPrice) {
				if (table.getInt(index, LandData.PRICE) >= minPrice)
					subTable.addRow(table.getRow(index));
			} else
				return subTable;
		}
		return subTable;
	}

	// returns a table that contains the specified value for a given column
	public static Table getSubTable(String tableColumn, String value, Table table) {
		Table subTable = new Table();
		for (int i = 0; i < LandData.TITLES.length; i++) {
			subTable.addColumn();
			subTable.setColumnTitle(i, LandData.TITLES[i]);
		}
		for (int index = 0; index < table.getRowCount(); index++)
			if (table.getString(index, tableColumn).indexOf(value) != -1)
				subTable.addRow(table.getRow(index));
		return subTable;
	}

	// returns an int array that contains the prices of certain houses at
	// certain intervals
	public static int[] getPriceRange(int priceRange, LandData landData) {
		sortByPrice(LandData.getTable());
		int[] prices = landData.getIntPrice();
		ArrayList<Integer> intervals = new ArrayList<Integer>();
		int a = 0;
		int index = 0;
		while (index != prices.length) {
			intervals.add(0);
			int b = 0;
			while (prices[index++] <= priceRange * (a + 1)) {
				b++;
			}
			intervals.set(a, b);
			a++;
		}
		index = intervals.size() - 1;
		while (intervals.get(index) == 0) {
			intervals.remove(index--);
		}
		int[] c = new int[20];
		for (index = 0; index < c.length; index++) {
			c[index] = intervals.get(index).intValue();
		}
		return c;
	}

	// returns a string array that contains the years from 1995 - (1995 +
	// numberOfyears)
	public static String[] getYears(int numberOfyears) {
		String[] a = new String[numberOfyears];
		for (int index = 0; index < a.length; index++)
			a[index] = "" + (1995 + index);
		return a;
	}

	public static int[] getCountOfOldNew(String[] list) {
		return getCountOf(list);
	}

	public static String[] getNewOldString() {
		return new String[] { "Old", "New" };
	}

	public static int getAvgPriceOfCounty(String county, Table table) {
		Table subTable = getSubTable(LandData.COUNTY, county, table);
		long index = 0;
		long tally = 0;
		for (long price : subTable.getLongColumn(LandData.PRICE)) {
			index++;
			tally += price;
		}
		return (int) (tally / (index == 0 ? 1 : index));
	}

	// in the order D, F, O, S, T
	public static int[] getPropertyTypesPerCounty(String county, Table table) {
		Table subTable = getSubTable(LandData.COUNTY, county, table);
		int[] propertyTypes = new int[5];
		for (String a : subTable.getStringColumn(LandData.PROPERTY_TYPE))
			if (a.equals("D"))
				propertyTypes[0]++;
			else if (a.equals("F"))
				propertyTypes[1]++;
			else if (a.equals("O"))
				propertyTypes[2]++;
			else if (a.equals("S"))
				propertyTypes[3]++;
			else if (a.equals("T"))
				propertyTypes[4]++;
		return propertyTypes;
	}

	// in the order D, F, O, S, T
	public static int[] getPropertyTypes(LandData data) {
		String[] propertyTypes = data.getPropertyType();
		int[] propertyTypesInt = new int[5];
		for (String a : propertyTypes)
			if (a.equals("D"))
				propertyTypesInt[0]++;
			else if (a.equals("F"))
				propertyTypesInt[1]++;
			else if (a.equals("O"))
				propertyTypesInt[2]++;
			else if (a.equals("S"))
				propertyTypesInt[3]++;
			else if (a.equals("T"))
				propertyTypesInt[4]++;
		return propertyTypesInt;
	}

	public static String[] getPropertyTypesPerCountyString() {
		return new String[] { "Detached", "Flats/Maisonettes", "Other", "Semi-Detached", "Terraced" };
	}

	// ordered lowest to highest
	public static int[] getHighestLowestPrices(String county, Table table) {
		Table subTable = getSubTable(LandData.COUNTY, county, table);
		int prices[] = subTable.getIntColumn(LandData.PRICE);
		Arrays.sort(prices);
		if (prices.length != 0)
			return new int[] { prices[0], prices[prices.length - 1] };
		return new int[]{};
	}

	public static String[] getHighestLowestPricesString() {
		return new String[] { "Lowest Price: ", "Highest Price: " };
	}

	// ordered New, Old
	public static int[] getOldNewCounties(String county, Table table) {
		Table subTable = getSubTable(LandData.COUNTY, county, table);
		String[] oldNew = subTable.getStringColumn(LandData.OLD_NEW);
		int newHouse = 0;
		for (String b : oldNew)
			newHouse += b.equals("Y") ? 1 : 0;
		return new int[] { newHouse, oldNew.length - newHouse };
	}

	public static String[] getOldNewCountiesString() {
		return new String[] { "Newly Built Property", "An Established Property" };
	}

	// returns the list of districts
	public static String[] getDistrictList(LandData data) {
		return getCountyList(data.getDistrict());
	}

	// returns the number of properties per county
	public static int[] getDistrictCount(LandData data) {
		String[] district = data.getDistrict();
		Arrays.sort(district);
		return getCountOf(district);
	}

	// returns the entire street list
	public static String[] getStreetList(LandData data) {
		return getCountyList(data.getStreet());
	}

	// returns the number of properties per street
	public static int[] getStreetCount(LandData data) {
		String[] street = data.getStreet();
		Arrays.sort(street);
		return getCountOf(street);
	}

	// returns the number of properties sold for each year
	public static int[] getYearsCount(LandData data) {
		return getCountOf(data.getYearsString());
	}

	// returns the year list
	public static String[] getYearsList(LandData data) {
		return getCountyList(data.getYearsString());
	}

	public static int[] getProportionOfUkResidents(String county, Table table) {
		Table subTable = getSubTable(LandData.COUNTY, county, table);
		int proportion = subTable.getRowCount();
		int rest = table.getRowCount() - subTable.getRowCount();
		return new int[] { proportion, rest };
	}

	public static String getMostPopularPropertyTypePerCounty(int[] propertyTypes) {
		int index = 0;
		int maxValue = 0;
		for (int i = 0; i < propertyTypes.length; i++)
			if (propertyTypes[i] > maxValue) {
				maxValue = propertyTypes[i];
				index = i;
			}
		return getPropertyTypesPerCountyString()[index];
	}

	public static String getMostPopularStreetByCounty(String county, Table table) {
		Table subTable = getSubTable(LandData.COUNTY, county, table);
		String[] streetList = getCountyList(subTable.getStringColumn(LandData.STREET));
		int[] streetCount = getCountOf(streetList);
		int index = 0;
		int maxValue = 0;
		for (int i = 0; i < streetCount.length; i++)
			if (streetCount[i] > maxValue) {
				maxValue = streetCount[i];
				index = i;
			}
		if (streetList.length != 0)
			return streetList[index].toLowerCase();
		return "";
	}

	public static String[] getNumbers(){
		return new String[] {"1","2","3","4","5","6","7","8","9"};
	}

	public static String getOrdinal(int number) {
		return number > 3 && number < 21 ? "th"
				: number % 10 == 1 ? "st" : number % 10 == 2 ? "nd" : number % 10 == 3 ? "rd" : "th";
	}
	public static int[] getNumNames(LandData data){
		int[] numName = new int[9];
		String[] numNames = data.getNumName();
		for (int index = 0; index < numNames.length; index++)
			for (int i = 0; i < numName.length; i++)
				if (numNames[index].equals(String.valueOf(i+1)))
					numName[i]++;
		return numName;
	}
	
	public static String getNthExpensiveProperty(int price, LandData landData){
		int[] prices = landData.getIntPrice();
		Arrays.sort(prices);
		int index = Arrays.binarySearch(prices,price);
		if (index == prices.length - 1)
			return "The Most Expensive Property";
		else if (index == 0)
			return "The Cheapest Property";
		return "The " + (index + 1) + getOrdinal(index + 1) + " Most Expensive Property";
	}
	
	public static String getNthPopularDistrict(String district, LandData landData){
		String[] districts = getDistrictList(landData);
		int[] districtCount = getDistrictCount(landData);
		int popularity = districtCount[Arrays.binarySearch(districts, district)];
		Arrays.sort(districtCount);
		int index = Arrays.binarySearch(districtCount, popularity);
		if (index == districtCount.length - 1)
			return "The Most Popular District";
		else if (index == 0)
			return "The Least Popular District";
		return "The " + (index + 1) + getOrdinal(index + 1) + " Most Popular District";
	}
}