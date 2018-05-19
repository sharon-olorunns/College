package userInterface;
import java.util.ArrayList;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.ListBox;
import controlP5.RadioButton;
import controlP5.Textlabel;
import processing.core.*;
import screenControls.Menu;

public class Screen implements Drawable {
	private static final String RADIOBUTTON = "RADIOBUTTON";
	private static final String LISTBOX = "LISTBOX";
	private static final String LABEL = "LABEL";
	private PApplet parent;
	private ArrayList<Drawable> uiElements;
	private int backGroundColor;
	public ControlP5 cp5;
	private Menu screen;
	public static int currentScreen = 0;//the main menu screen number = -1;
	public RadioButton radioButtons;
	public ListBox listBox;
	public Button backButton;
	public Textlabel label;
	private PFont font;
	private PImage img;

	public Screen(PApplet parent, int backGroundColor,
			ArrayList<Drawable> uiElements, Menu screen, PFont font) {
		this.parent = parent;
		this.font = font;
		this.cp5 = new ControlP5(this.parent);
		this.cp5.setFont(this.font);
		this.cp5.setAutoDraw(false);
		this.label = cp5.addLabel(screen.name() + LABEL);
		this.radioButtons = new RadioButton(cp5, screen.name() + RADIOBUTTON);
		this.listBox = cp5.addListBox(screen.name() + LISTBOX);
		this.uiElements = uiElements;
		this.backGroundColor = backGroundColor;
		this.screen = screen;
	}
	
	public Screen(PApplet parent,PImage img ,
			ArrayList<Drawable> uiElements, Menu screen, PFont font) {
		this.parent = parent;
		this.font = font;
		this.cp5 = new ControlP5(this.parent);
		this.img =img;
		parent.fill(backGroundColor);
		this.cp5.setFont(this.font);
		this.cp5.setAutoDraw(false);
		this.label = cp5.addLabel(screen.name() + LABEL);
		this.radioButtons = new RadioButton(cp5, screen.name() + RADIOBUTTON);
		this.listBox = cp5.addListBox(screen.name() + LISTBOX);
		this.uiElements = uiElements;
		this.img =img;
		this.screen = screen;
	}

	public Screen(PApplet parent, int supIdentifier){
		this.parent = parent;
		this.uiElements = null;
		this.backGroundColor = 0;
	}

	public ArrayList<Drawable> getUiElements() {
		return uiElements;
	}

	public void setUiElements(ArrayList<Drawable> uiElements) {
		this.uiElements = uiElements;
	}

	public void addUIElement(Drawable uiElem) {
		uiElements.add(uiElem);
	}

	public void addUIElement(ArrayList<Drawable> uiElems) {
		uiElements.addAll(uiElems);
	}

	public void draw() {
		if (currentScreen ==0)
			parent.background(img);
		else {
		parent.background(backGroundColor);
		if(uiElements.size() != 0)
			for (Drawable tmp : uiElements) {
				tmp.draw();
			}
		}
		cp5.draw();
	}

	public Menu getScreen(){
		return screen;
	}

	public void setToChildScreen(){
		currentScreen = this.screen.getScreenNumber();
	}
}