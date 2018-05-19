package dataHandlers;

import java.util.Arrays;

import processing.core.PApplet;
import processing.data.Table;

public class LandData {
	public static final String DICTIONARY_NAME = "dict.tsv";
	public static final String PRICE = "Price";
	public static final String DATE_OF_SALE = "DateOfSale";
	public static final String POSTCODE = "Postcode";
	public static final String PROPERTY_TYPE = "PropertyType";
	public static final String OLD_NEW = "Old/New";
	public static final String NUM_NAME = "NumName";
	public static final String STREET = "Street";
	public static final String LOCALITY = "Locality";
	public static final String TOWN = "Town";
	public static final String DISTRICT = "District";
	public static final String COUNTY = "County";
	public static final String[] TITLES = { PRICE, DATE_OF_SALE, POSTCODE, PROPERTY_TYPE, OLD_NEW, NUM_NAME, STREET,
			LOCALITY, TOWN, DISTRICT, COUNTY };
	private final PApplet p;
	private static Table regData;
	private Table regDataSortedByDate;

	public LandData(PApplet parent, String fileName) {
		p = parent;
		LandData.regData = p.loadTable(fileName, "dictionary=" + DICTIONARY_NAME);
		this.regDataSortedByDate = p.loadTable("pp-50k(Sorted By Date).csv", "dictionary=" + DICTIONARY_NAME);
		for (int i = 0; i < TITLES.length; i++) {
			LandData.regData.setColumnTitle(i, TITLES[i]);
			this.regDataSortedByDate.setColumnTitle(i, TITLES[i]);
		}
	}

	public static  Table getTable() {
		return regData;
	}

	public Table getDateSortedTable() {
		return regDataSortedByDate;
	}

	public float[] getFloatPrice() {
		return regData.getFloatColumn(PRICE);
	}

	public String[] getStringPrice() {
		return regData.getStringColumn(PRICE);
	}

	public int[] getIntPrice() {
		return regData.getIntColumn(PRICE);
	}

	public String[] getDateOfSale() {
		return regDataSortedByDate.getStringColumn(DATE_OF_SALE);
	}

	public String[] getPostcode() {
		return regData.getStringColumn(POSTCODE);
	}

	public String[] getPropertyType() {
		return regData.getStringColumn(PROPERTY_TYPE);
	}

	public String[] getOldNew() {
		return regData.getStringColumn(OLD_NEW);
	}

	public String[] getNumName() {
		return regData.getStringColumn(NUM_NAME);
	}

	public String[] getStreet() {
		return regData.getStringColumn(STREET);
	}

	public String[] getLocality() {
		return regData.getStringColumn(LOCALITY);
	}

	public String[] getTown() {
		return regData.getStringColumn(TOWN);
	}

	public String[] getDistrict() {
		return regData.getStringColumn(DISTRICT);
	}

	public static String[] getCounty() {
		return regData.getStringColumn(COUNTY);
	}
	
	public int[] getYears(){
		String[] dates = getDateOfSale();
		int[] years = new int[dates.length];
		for (int index = 0; index < years.length; index++) {
			years[index] = Integer.parseInt(dates[index].substring(6, dates[index].length() - 6));
		}
		Arrays.sort(years);
		return years;
	}
	
	public String[] getYearsString(){
		int[] years = getYears();
		String[] yearsString = new String[years.length];
		for(int index = 0; index < yearsString.length; index++)
			yearsString[index] = String.valueOf(years[index]);
		return yearsString;
	}
}