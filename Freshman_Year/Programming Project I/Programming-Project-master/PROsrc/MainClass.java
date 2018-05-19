import processing.core.*;
import screenControls.Menu;
import userInterface.*;

import java.util.ArrayList;

import animations.LoadingScreens;
import controlP5.*;
import dataHandlers.LandData;
import dataHandlers.LandDataHandler;
import dataHandlers.SearchEngine;

public class MainClass extends PApplet {
	ArrayList<Screen> screens = new ArrayList<Screen>();
	LandData landData;
	ControlP5 cp5;
	AnimatedBarChart barChart;
	LineChart lineChart;
	PieChart pieChart;
	CColor colour;
	int Pcolour;
	int Bcolour;
	int screenColour;
	int fontColour;
	int BontColour;
	PFont font;
	SearchEngine search;
	UKMap map;
	PostCodeMapper postCodeMap;
	SearchMap searchMap;
	SearchEngine engine;
	LoadingScreens loadingScreens;
	PImage img;
	boolean canOpen = true;
	boolean canSet = false;
	int alpha;
	public static void main(String[] args) {
		PApplet.main("MainClass");
	}

	public void settings() {
		fullScreen();
	}

	public void setup() {
		img = loadImage("westminster-England_Landscape_Wallpaper_medium.jpg");
		img.resize(width, height);
		alpha = 10;
		cp5 = new ControlP5(this);
		landData = new LandData(this, "pp-50k.csv");
		font = createFont("Boulder Regular.tff", 25);
		BontColour = color(0,alpha);
		loadingScreens = new LoadingScreens(this);
		Pcolour = color(0, 206, 209);
		Bcolour = color(128, 0, 128);
		screenColour = color(255);
		fontColour = color(255);
		// Define main menu here object here
		Screen mainMenu = new Screen(this,img, new ArrayList<Drawable>(), Menu.MAIN, font);
		mainMenu.listBox.setWidth(400);
		mainMenu.listBox.setBarHeight(0);
		mainMenu.listBox.setItemHeight(60);
		mainMenu.listBox.setHeight(60);
		mainMenu.listBox.setLabel("");
		mainMenu.listBox.setColorForeground(Pcolour);
		mainMenu.listBox.setColorBackground(BontColour);
		mainMenu.listBox.setColorLabel(BontColour);
		mainMenu.label.setPosition(width * 0.26f, height * 0.418f);
		mainMenu.label.setColor(0);
		mainMenu.label.setText("View Statistical Summary of: ");
		mainMenu.listBox.setColorValue(Bcolour);
		mainMenu.listBox.setColorCaptionLabel(mainMenu.listBox.getColor().getForeground());
		searchMap = new SearchMap();
		mainMenu.listBox.addItem("PRICE", 1).setColorLabel(0).setColorCaptionLabel(Bcolour)
		.setPosition(width * 0.51f, height * 0.415f).setColorForeground(Pcolour);
		mainMenu.listBox.addItem("DATE OF SALE", 2).setColorLabel(0).setColorCaptionLabel(Bcolour)
		.setPosition(width * 0.51f, height * 0.415f).setColorForeground(Pcolour);
		mainMenu.listBox.addItem("PROPERTY TYPE", 3).setColorLabel(0).setColorCaptionLabel(Bcolour)
		.setPosition(width * 0.51f, height * 0.415f).setColorForeground(Pcolour);
		mainMenu.listBox.addItem("OLD/NEW", 5).setColorLabel(0).setColorCaptionLabel(Bcolour)
		.setPosition(width * 0.51f, height * 0.415f).setColorForeground(Pcolour);
		mainMenu.listBox.addItem("NUMBER NAME", 6).setColorLabel(0).setColorCaptionLabel(Bcolour)
		.setPosition(width * 0.51f, height * 0.415f).setColorForeground(Pcolour);
		
		cp5.addButton(Menu.MAIN.name() + "BUTTON2").setLabel("Advanced Search").setColorForeground(Pcolour)
		.setColorBackground(Pcolour).setPosition(0, 0).setWidth(340).setColorLabel(Bcolour).setHeight(28)
		.getCaptionLabel().setFont(font);
		screens.add(mainMenu);

		cp5.addButton("MAPBUTTON").setLabel("View stats using Map").setColorForeground(Pcolour).setColorBackground(Pcolour)
		.setPosition(width - 340, 0).setWidth(340).setColorLabel(Bcolour).setHeight(28).getCaptionLabel()
		.setFont(font);

		Screen priceMenu = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.PRICE, font);
		priceMenu.label.remove();
		int[] priceRange = LandDataHandler.getPriceRange(40000, landData);
		String[] years = LandDataHandler.getPriceGroups(priceRange.length, 40000);
		barChart = new AnimatedBarChart(this, priceRange, years, 150, 9000, 50, 50, width - 120, height - 120, true,
				"The Number of Houses sold for different Ranges of Price: UK", true, true);
		barChart.setValueFormat("");
		barChart.setAxisColour(0);
		barChart.setAxisValuesColour(0);
		priceMenu.addUIElement(barChart);
		priceMenu.listBox.remove();
		priceMenu.cp5.addButton(Menu.PRICE.name() + "BACKBUTTON").setLabel("BACK").setPosition(0, height - 70)
		.setColorValue(Bcolour).setColorBackground(Bcolour).setColorForeground(Bcolour).setWidth(140)
		.setHeight(50);
		screens.add(priceMenu);
		Screen dateOfSale = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.DATE_OF_SALE, font);
		dateOfSale.label.remove();
		int[] yearRange = LandDataHandler.getYearsCount(landData);
		String[] years2 = LandDataHandler.getYearsList(landData);
		barChart = new AnimatedBarChart(this, yearRange, years2, 300, 50000, 40, 40, width - 120, height - 120, false,
				"The Number of Houses sold in the Following Years: UK", false, true);
		barChart.setValueFormat("");
		barChart.setAxisLabelColour(fontColour);
		dateOfSale.addUIElement(barChart);
		dateOfSale.listBox.remove();
		dateOfSale.cp5.addButton(Menu.DATE_OF_SALE.name() + "BACKBUTTON").setLabel("BACK").setPosition(0, height - 70)
		.setColorValue(Bcolour).setColorBackground(Bcolour).setColorForeground(Bcolour).setWidth(140)
		.setHeight(50);
		screens.add(dateOfSale);
		Screen propertyType = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.PROPERTY_TYPE, font);
		propertyType.label.remove();
		int[] propertyTypes = LandDataHandler.getPropertyTypes(landData);
		String[] namesOfPropertyTypes = LandDataHandler.getPropertyTypesPerCountyString();
		pieChart = new PieChart(this, propertyTypes, namesOfPropertyTypes, "Property Type", 500, 0, font, width / 2,
				height / 2);
		propertyType.addUIElement(pieChart);
		propertyType.listBox.remove();
		propertyType.cp5.addButton(Menu.PROPERTY_TYPE.name() + "BACKBUTTON").setLabel("BACK")
		.setPosition(0, height - 70).setWidth(140).setHeight(50).setColorValue(Bcolour)
		.setColorBackground(Bcolour).setColorForeground(Bcolour);
		screens.add(propertyType);
		Screen oldNew = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.OLD_NEW, font);
		oldNew.label.remove();
		int[] oldNew1 = LandDataHandler.getCountOfOldNew(landData.getOldNew());
		String[] oldNew2 = LandDataHandler.getNewOldString();
		pieChart = new PieChart(this, oldNew1, oldNew2, "Old:New Houses:UK", 500, color(0), font, width / 2, height / 2);
		oldNew.addUIElement(pieChart);
		oldNew.listBox.remove();
		oldNew.cp5.addButton(Menu.OLD_NEW.name() + "BACKBUTTON").setLabel("BACK").setPosition(0, height - 70)
		.setColorValue(Bcolour).setColorBackground(Pcolour).setColorForeground(Pcolour).setWidth(140)
		.setHeight(50);
		screens.add(oldNew);

		Screen numName = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.NUM_NAME, font);
		numName.label.remove();

		int[] numNames = LandDataHandler.getNumNames(landData);
		String[] numNameString = LandDataHandler.getNumbers();
		pieChart = new PieChart(this, numNames, numNameString, "Proportion of numbered Houses:UK", 500, 0, font, width / 2,
				height / 2);
		numName.addUIElement(pieChart);
		numName.listBox.remove();
		numName.cp5.addButton(Menu.STREET.name() + "BACKBUTTON").setLabel("BACK").setPosition(0, height - 70)
		.setColorValue(Bcolour).setColorBackground(Pcolour).setColorForeground(Pcolour).setWidth(140)
		.setHeight(50);
		screens.add(numName);

		Screen county = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.COUNTY, font);
		county.label.remove();
		county.listBox.remove();
		county.cp5.addButton(Menu.COUNTY.name() + "BACKBUTTON").setLabel("BACK").setPosition(0, height - 70)
		.setColorValue(Bcolour).setColorBackground(Pcolour).setColorForeground(Pcolour).setWidth(140)
		.setHeight(50);
		map = new UKMap(this, width * 0.345f, .97f, width * 1f / 2, height, landData);
		county.addUIElement(map);
		screens.add(county);

		Screen searchScreen = new Screen(this, screenColour, new ArrayList<Drawable>(), Menu.SEARCH, font);
		search = new SearchEngine(this, landData, 30, 180, width - 60, height - 250, "Search By Street");
		searchScreen.listBox.remove();
		searchScreen.label.remove();
		engine = new SearchEngine(this, landData, 30, 180, width - 60, height - 250, "Search By Street");
		searchScreen.addUIElement(engine);
		screens.add(searchScreen);
		searchScreen.cp5.addButton(Menu.SEARCH.name() + "BACKBUTTON").setLabel("BACK").setPosition(0, height - 70)
		.setColorValue(Bcolour).setColorBackground(Pcolour).setColorForeground(Pcolour).setWidth(140)
		.setHeight(50);
		screens.add(searchScreen);
	}

	public void draw() {
		if (LoadingScreens.loadingScreens.get(LoadingScreens.currentLoadingScreen).canDraw){
			screens.get(Screen.currentScreen).draw();
			if (canSet)
				cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
		}
		else{
			LoadingScreens.loadingScreens.get(LoadingScreens.currentLoadingScreen).runSketch();
		}
	}
	
	public void myList(ControlEvent theEvent) {
		if (!engine.isIndexNegative()){
			searchMap.mousePressed(engine.getPostCode((int)theEvent.getValue()));
			engine.property = (int)theEvent.getValue();
		}
	}

	public void RADIOBUTTON(int a){
		if (Screen.currentScreen == 7)
			engine.setIndex(a);
	}

	public void MAPBUTTONS(int a){
		if (Screen.currentScreen == 7)
			searchMap.setToMap(a);
	}

	public void MAINLISTBOX(ControlEvent theEvent) {
		Screen.currentScreen = (int) theEvent.getValue() + 1;
		if ((int) theEvent.getValue() + 1 == 7)
			cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(width, height);
		if ((int) theEvent.getValue() + 1 == 3)
			cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(width, height);
		int lScreen = (int)theEvent.getValue() > 9 ? 0 : (int)theEvent.getValue();
		LoadingScreens.drawScreen(lScreen);
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(width, height);
		canSet = true;
	}

	public void MAINBUTTON2(ControlEvent theEvent) {
		Screen.currentScreen = 7;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(width, height);
		engine.clearList();
		if (canOpen){
			searchMap.runSketch(this, width, height, landData);
			canOpen = false;
		}
		else{
			searchMap.open();
		}
	}

	public void SEARCHBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
		searchMap.close();
	}

	public void MAPBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 6;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(width, height);
	}

	public void PRICEBUTTON(ControlEvent theEvent) {
	}

	public void PRICEBUTTON1(ControlEvent theEvent) {
		Screen.currentScreen = 7;
	}

	public void PRICEBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
	}

	public void DATE_OF_SALEBUTTON1(ControlEvent theEvent) {
		Screen.currentScreen = 15;
	}

	public void DATE_OF_SALEBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
	}


	public void PROPERTY_TYPEBUTTON1(ControlEvent theEvent) {
		Screen.currentScreen = 3;
	}

	public void PROPERTY_TYPEBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
	}

	public void OLD_NEWBUTTON1(ControlEvent theEvent) {
		Screen.currentScreen = 4;
	}

	public void OLD_NEWBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
	}

	public void NUM_NAMEBUTTON1(ControlEvent theEvent) {
		Screen.currentScreen = 5;
	}

	public void STREETBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
	}

	public void COUNTYBACKBUTTON(ControlEvent theEvent) {
		Screen.currentScreen = 0;
		cp5.get(Menu.MAIN.name() + "BUTTON2").setPosition(0, 0);
	}

	public void keyReleased() {
		if (Screen.currentScreen == 7)
			engine.search(engine.textField.getText());
	}
	public void mousePressed(){
		if (Screen.currentScreen == 6 && LoadingScreens.loadingScreens.get(LoadingScreens.currentLoadingScreen).canDraw)
			map.mousePressed();
	}
}