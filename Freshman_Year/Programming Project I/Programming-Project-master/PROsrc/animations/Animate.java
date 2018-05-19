package animations;

import processing.core.PApplet;
import processing.core.PFont;

public abstract class Animate{
	public PApplet p;
	int secs = 0;
	int maxTime;
	boolean setSecs = true;
	public boolean canDraw = true;
	PFont font;
	public Animate(PApplet parent, int maxTime) {
		p = parent;
		this.maxTime = maxTime * 1000;
		font = p.loadFont("YuGothicUI-Light-80.vlw");
		p.textFont(font);
	}

	public abstract void draw();
	
	public void runSketch() {
		if (setSecs){
			secs = p.millis();
			setSecs = false;
		}
		draw();
		if (p.millis() - secs > maxTime){
			canDraw = true;
		}
	}
}