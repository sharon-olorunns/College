import controlP5.ControlEvent;
import dataHandlers.LandData;
import dataHandlers.SearchEngine;
import processing.core.PApplet;
import userInterface.PostCodeMapper;
public class TestClass extends PApplet {
	PostCodeMapper postCodeMap;
	SearchMap searchMap;
	LandData landData;
	SearchEngine engine;
	boolean canOpen = true;
	public static void main(String[] args) {
		PApplet.main("TestClass");
	}

	public void settings() {
		fullScreen();
	}
	public void setup() {
		landData = new LandData(this, "pp-50k.csv");
		engine = new SearchEngine(this, landData, 30, 180, width - 60, height - 250, "Search By Street");
		searchMap = new SearchMap();
	}

	public void draw() {
		background(0,112,120);
		engine.draw();
	}

	public void myList(ControlEvent theEvent) {
		if (!engine.isIndexNegative())
		searchMap.mousePressed(engine.getPostCode((int)theEvent.getValue()));
		engine.property = (int)theEvent.getValue();
	}
	
	public void RADIOBUTTON(int a){
		engine.setIndex(a);
	}
	
	public void MAPBUTTONS(int a){
		searchMap.setToMap(a);
	}
	
	public void mousePressed(){
		if (canOpen){
		searchMap.runSketch(this, width, height, landData);
		canOpen = false;
		}
	}
	
	public void keyReleased(){
		engine.search(engine.textField.getText());
	}
}