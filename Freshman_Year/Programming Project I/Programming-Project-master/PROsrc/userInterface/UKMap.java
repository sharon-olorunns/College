package userInterface;

//import org.codehaus.plexus.util.StringUtils; 
import org.gicentre.geomap.*; 

import dataHandlers.LandData;
import dataHandlers.LandDataHandler;
import processing.core.*;

public class UKMap implements Drawable {
	private GeoMap geoMap;
	private final PApplet p;
	private final PFont font;
	private PieChart oldNewPie;
	private PieChart proprtyTypePie;
	private PieChart propPie;

	private String mostPopStreet;
	private String avgPrice;
	private String mostPopProprtyType;
	private String lowestPrice;
	private String highestPrice;
	private String countyName;
	public int countyID;
	public int[] colours;
	int Bcolour;
	public UKMap(PApplet parent, float xPos, float yPos, float width, float height, LandData landData) {
		p = parent;
		Bcolour = p.color(128, 0, 128);
		geoMap = new GeoMap(xPos, yPos, width, height, parent);
		geoMap.readFile("GBR_adm2");
		font = p.loadFont("YuGothicUI-Light-20.vlw");
		p.textFont(font);
		colours = new int[geoMap.getAttributeTable().getRowCount()];
		for (int index = 0; index < colours.length; index++)
			colours[index] = (int)p.random(255);
	}

	public void draw() {
		p.background(255);
		p.fill(124,252,0);
		p.stroke(0);
		p.strokeWeight(1.2f);
		geoMap.draw();
		drawMap();
		if (oldNewPie != null) {
			oldNewPie.draw();
			proprtyTypePie.draw();
			propPie.draw();
			drawText();
		}	
	}

	private void drawMap() {
		countyID = geoMap.getID(p.mouseX, p.mouseY);
		if (countyID != -1) {
			p.fill(colours[countyID]);
			geoMap.draw(countyID);
			p.fill(255, 0,200);
			p.textAlign(PConstants.CENTER, PConstants.CENTER);
			String county = geoMap.getAttributeTable().findRow(PApplet.str(countyID), 0).getString("NAME_2");
			p.text(county, p.mouseX, p.mouseY - 10);
		}
	}

	public void mousePressed() {
		if (countyID != -1) {
			p.fill(255,0,0);
			geoMap.draw(countyID);
			drawInfo(countyID);
		}
	}

	private void drawInfo(int countryID) {
		countyName = geoMap.getAttributeTable().findRow(PApplet.str(countryID), 0).getString("NAME_2");
		String county = countyName.toUpperCase();
		int[] property = LandDataHandler.getPropertyTypesPerCounty(county, LandData.getTable());
		String[] proprtyString = LandDataHandler.getPropertyTypesPerCountyString();
		proprtyTypePie = new PieChart(p, property, proprtyString, "Propert Types: " + countyName, 200, p.color(0), font,
				170, p.height/4);

		int[] oldNew = LandDataHandler.getOldNewCounties(county, LandData.getTable());
		String[] oldNewString = LandDataHandler.getOldNewCountiesString();
		oldNewPie = new PieChart(p, oldNew, oldNewString, "Old / New: " + countyName, 200, p.color(0), font,
				170, p.height - ( 2* (p.height/4)) + p.height/60);

		int[] prop = LandDataHandler.getProportionOfUkResidents(county, LandData.getTable());
		propPie = new PieChart(p, prop, new String[] { countyName, "UK" }, "UK Res. Proportion: " + countyName, 200,
				p.color(0), font, 170, p.height - 180);
		int[] highLowPrices = LandDataHandler.getHighestLowestPrices(county, LandData.getTable());
		highestPrice = highLowPrices.length != 0 ? String.valueOf(highLowPrices[1]) : "";
		lowestPrice = highLowPrices.length != 0 ? String.valueOf(highLowPrices[0]) : "";
		mostPopProprtyType = highLowPrices.length != 0 ? LandDataHandler.getMostPopularPropertyTypePerCounty(property)
				: "";
		avgPrice = highLowPrices.length != 0
				? String.valueOf(LandDataHandler.getAvgPriceOfCounty(county, LandData.getTable())) : "";
				mostPopStreet = LandDataHandler.getMostPopularStreetByCounty(county, LandData.getTable());
	}

	public void drawText() {
		
		int x = p.width - 360;
		int y = 220;
		p.fill(5);
		p.text("*ADDITIONAL INFORMATION*", x + 100,160);
		
		p.fill(Bcolour);
		p.text("County:", x, y);
		p.fill(0);
		p.text(countyName, x + 200, y );
		p.fill(Bcolour);
		p.text("Most Popular property Type:", x, y + 40);
		p.fill(0);
		p.text(mostPopProprtyType, x + 200, y + 40);
		p.fill(Bcolour);
		p.text("Highest Price:", x , y + 80);
		p.fill(0);
		p.text("£" + highestPrice, x + 200, y + 80);
		p.fill(Bcolour);
		p.text("Lowest Price:", x , y + 120);
		p.fill(0);
		p.text("£" + lowestPrice, x + 200, y + 120);
		p.fill(Bcolour);
		p.text("Average Price:", x , y + 160);
		p.fill(0);
		p.text("£" + avgPrice, x + 200, y + 160);
		p.fill(Bcolour);
		p.text("Most Popular Street:", x, y + 200);
		p.fill(0);
		p.text(mostPopStreet, x + 200, y + 200);
	}
	public void setToNull(){
		mostPopStreet = null;
		avgPrice = null;
		mostPopProprtyType = null;
		lowestPrice = null;
		highestPrice = null;
		countyName = null;
	}
}