package dataHandlers;

import java.util.ArrayList; 

import org.codehaus.plexus.util.StringUtils;

import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.ListBox;
import controlP5.RadioButton;
import controlP5.Textfield;
import controlP5.Toggle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.data.Table;
import userInterface.Drawable;

public class SearchEngine implements Drawable {
	ControlP5 cp5;
	ListBox list;
	PApplet p;
	LandData landData;
	RadioButton radioButtons;
	public Textfield textField;
	private int xPos;
	private int yPos;
	private int height;
	private PFont font2;
	private PFont font;
	CColor colour;
	int Pcolour;
	int Bcolour;
	int screenColour;
	int fontColour;
	int BontColour;
	RadioButton mapButtons;
	public int property = -1;
	private int searchTypeIndex = 0;
	private String[] searchTypes = new String[] { LandData.COUNTY, LandData.DISTRICT, LandData.LOCALITY,
			LandData.STREET };
	
	private String price;
	private String postCode;
	private String priceRank;
	private String districtRank;
	
	public SearchEngine(PApplet parent, LandData landData, int xPos, int yPos, int width, int height, String label) {
		p = parent;
		Pcolour = parent.color(0, 206, 209);
		Bcolour = parent.color(128, 0, 128);
		font = parent.createFont("Boulder Regular.tff", 12);
		font2 = parent.createFont("Boulder Regular.tff", 12);
		this.xPos = xPos;
		this.yPos = yPos + 100;
		this.height = height - 100;
		this.landData = landData;
		cp5 = new ControlP5(p);
		cp5.setFont(font);
		cp5.setAutoDraw(false);
		
		radioButtons = cp5.addRadioButton("RADIOBUTTON").setPosition(30, 160).setSize(40, 40)
				.setColorForeground(Pcolour).setColorActive(Pcolour).setColorLabel(Pcolour)
				.setItemsPerRow(5).setSpacingColumn(((p.width / 3) - 120) / 3 - 40 / 3).addItem("County", 0)
				.addItem("District", 1).addItem("Locality", 2).addItem("Street", 3).setColorLabels(p.color(0));
		for (Toggle t : radioButtons.getItems()) {
			t.getCaptionLabel().setColorBackground(p.color(255, 30));
			t.getCaptionLabel().getStyle().moveMargin(-40, 0, 0, -46);
			t.getCaptionLabel().getStyle().movePadding(7, 0, 0, 3);
			t.getCaptionLabel().getStyle().backgroundWidth = 50;
			t.getCaptionLabel().getStyle().backgroundHeight = 13;
		}

		mapButtons = cp5.addRadioButton("MAPBUTTONS").setPosition(p.width * 2 / 3 - 300, 100).setSize(40, 40).setColorLabel(Bcolour)
				.setItemsPerRow(1).setSpacingRow(40).addItem("Geo Map", 0).addItem("Road Map", 1).addItem("Physical Map", 2);
		for (Toggle t : mapButtons.getItems()) {
			t.getCaptionLabel().setColorBackground(p.color(255, 30));
			t.getCaptionLabel().getStyle().moveMargin(-40, 0, 0, -46);
			t.getCaptionLabel().getStyle().movePadding(7, 0, 0, 3);
			t.getCaptionLabel().getStyle().backgroundWidth = 50;
			t.getCaptionLabel().getStyle().backgroundHeight = 13;
		}
		/*
		 * 1 colour when its highlited 2 base color of the thing 3 color of when
		 * the thing is selected 4 sets the colour of the scroll bar 5 colour of
		 * the text
		 */
		colour = new CColor(p.color(123), p.color(0), p.color(0), p.color(255), p.color(255));
		list = cp5.addListBox("myList");
		list.setLabel("");
		list.setPosition(this.xPos, this.yPos);
		list.setSize(p.width / 3, this.height);
		list.getCaptionLabel().setFont(font);
		list.setColorCaptionLabel(Bcolour);
		list.setItemHeight(40);// Set the height of the individual buttons
		list.setBarHeight(0);// Set the the height if the bar on top of the
		// scroll
		list.setColorActive(Pcolour);// Determines the colour of the
		// button
		// when its selected
		list.setColorForeground(Bcolour);// Set the colour of the
		// buttons
		// when I hover over them;
		textField = cp5.addTextfield(cp5, "");
		textField.setPosition(30, 30);
		textField.setHeight(30);
		textField.setWidth(p.width / 3);
		textField.setColorLabel(p.color(255)).setLabelVisible(true);
		textField.setColorCursor(p.color(255));
		textField.setColor(new CColor(p.color(255), p.color(0), p.color(0), p.color(255), p.color(255)));
	}

	public void draw() {
		cp5.draw();	
		if (property!= - 1){
			displayText(property);
		}
	}

	private String prev = "";
	private ArrayList<Table> subTables = new ArrayList<Table>();
	private int index = -1;
	
	public void search(String user) {
		String current = user.toUpperCase();
		if (current.length() > 2) {
			if (prev.equals("") && current.length() != 0) {
				subTables.add(LandDataHandler.getSubTable(searchTypes[searchTypeIndex], current, LandData.getTable()));
				index++;
			} else if (current.length() > prev.length() && index != -1) {
				subTables.add(LandDataHandler.getSubTable(searchTypes[searchTypeIndex], current,
						(Table) subTables.get(index++)));
			} else if (current.length() < prev.length() && index != -1) {
				subTables.remove(index--);
			}
			prev = current;
		}
		if (current.length() <= 2 && !subTables.isEmpty())
			prev = "";
		displayTable();
	}

	public String getPostCode(int index) {
		return subTables.get(this.index).getString(index, LandData.POSTCODE);
	}

	public boolean isIndexNegative() {
		return index == -1;
	}
	
	//Pie Chart Ideas?

	public void setIndex(int searchTypeIndex) {
		this.index = -1;
		for (int index = subTables.size() - 1; index > -1; index--)
			subTables.remove(index);
		prev = "";
		textField.setText("");
		this.searchTypeIndex = searchTypeIndex;
		clearList();
	}

	public void clearList() {
		cp5.remove("myList");
		list = cp5.addListBox("myList");
		list.setPosition(xPos, yPos);
		list.setSize(p.width / 3, height);
		list.setItemHeight(30);
		list.setBarHeight(45);
		list.setColorActive(Pcolour);
		list.setBackgroundColor(Bcolour);
		list.setColorForeground(Pcolour);
		list.getCaptionLabel().set("PostCode | Num Name | Street | Town | County");
	}
	
	private void displayText(int index){
		p.textFont(font2);
		price = "£" +subTables.get(this.index).getString(index, LandData.PRICE);
		postCode = getPostCode(index);
		int price = subTables.get(this.index).getInt(index, LandData.PRICE);
		priceRank = LandDataHandler.getNthExpensiveProperty(price, landData);
		String district = subTables.get(this.index).getString(index, LandData.DISTRICT);
		districtRank = LandDataHandler.getNthPopularDistrict(district, landData);
		String oldNew = subTables.get(this.index).getString(index, LandData.OLD_NEW).equals("Y")? "Yes" : "No";
		String dateOfSale = subTables.get(this.index).getString(index, LandData.DATE_OF_SALE);
		p.fill(Bcolour);
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		p.fill(Bcolour);
		p.text("Date Of Sale:", p.width/2 - 120, p.height / 2 - 80);
		p.fill(0);
		p.text(dateOfSale, p.width/2 + 80, p.height / 2 - 80);
		p.fill(Bcolour);
		p.text("Old or New?:", p.width/2 - 120, p.height / 2 - 40);
		p.fill(0);
		p.text(oldNew, p.width/2 + 80, p.height / 2 - 40);
		p.fill(Bcolour);
		p.text("Price:", p.width/2 - 120, p.height / 2);
		p.fill(0);
		p.text(this.price, p.width / 2 + 80, p.height / 2 );
		p.fill(Bcolour);
		p.text("PostCode:", p.width / 2 - 120, p.height / 2 + 40 );
		p.fill(0);
		p.text(postCode, p.width / 2 + 80,  p.height / 2 + 40);
		p.fill(Bcolour);
		p.text("District:", p.width / 2 - 120 , p.height / 2 + 80);
		p.fill(0);
		p.text(StringUtils.capitalise(district.toLowerCase()), p.width / 2 + 80,  p.height / 2 + 80);
		p.fill(Bcolour);
		p.text("District Rank:", p.width / 2 - 120, p.height / 2 + 120);
		p.fill(0);
		p.text(districtRank, p.width / 2 + 80,  p.height / 2 + 120 );
		p.fill(Bcolour);
		p.text("Price Rank:", p.width / 2 - 120, p.height / 2  + 160);
		p.fill(0);
		p.text(priceRank, p.width / 2 + 80,  p.height / 2  + 160);
		p.textFont(font);
	}

	private void displayTable() {
		cp5.remove("myList");
		list = cp5.addListBox("myList");
		list.setPosition(xPos, yPos);
		list.setSize(p.width / 3, height);
		list.setItemHeight(50);
		list.setBarHeight(50);
		list.setColorActive(p.color(0));
		list.setBackgroundColor(Bcolour);
		list.setColorForeground(Pcolour);
		list.getCaptionLabel().set("PostCode | Num Name | Street | Town | County");
		if (subTables.size() != 0)
			for (int i = 0; i < subTables.get(index).getRowCount(); i++) {
				ListBox lbi = list.addItem((subTables.get(index).getString(i, LandData.POSTCODE).equals("") ? " *** "
						: subTables.get(index).getString(i, LandData.POSTCODE)) + " | "

						+ (subTables.get(index).getString(i, LandData.NUM_NAME).equals("") ? " *** "
								: subTables.get(index).getString(i, LandData.NUM_NAME))
						+ " | "
						+ (subTables.get(index).getString(i, LandData.STREET).equals("") ? " *** "
								: subTables.get(index).getString(i, LandData.STREET))
						+ " | "
						+ (subTables.get(index).getString(i, LandData.TOWN).equals("") ? " *** "
								: subTables.get(index).getString(i, LandData.TOWN))
						+ " | " + (subTables.get(index).getString(i, LandData.COUNTY).equals("") ? " *** "
								: subTables.get(index).getString(i, LandData.COUNTY)),i);
			}
	}
}