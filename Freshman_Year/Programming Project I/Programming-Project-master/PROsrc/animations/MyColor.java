package animations;

import processing.core.PApplet;

public class MyColor {
	float R, G, B, Rspeed, Gspeed, Bspeed;
	PApplet p;
	final static float minSpeed = .7f;
	final static float maxSpeed = 1.5f;
	public MyColor(PApplet parent) {
		this.p = parent;
		R = p.random(255);
		G = p.random(255);
		B = p.random(255);
		Rspeed = (p.random(1) > .5 ? 1 : -1) * p.random(minSpeed, maxSpeed);
		Gspeed = (p.random(1) > .5 ? 1 : -1) * p.random(minSpeed, maxSpeed);
		Bspeed = (p.random(1) > .5 ? 1 : -1) * p.random(minSpeed, maxSpeed);
	}

	public void update(){
		Rspeed = ((R += Rspeed) > 255 || (R < 0)) ? -Rspeed : Rspeed;
		Gspeed = ((G += Gspeed) > 255 || (G < 0)) ? -Gspeed : Gspeed;
		Bspeed = ((B += Bspeed) > 255 || (B < 0)) ? -Bspeed : Bspeed;
	}

}
