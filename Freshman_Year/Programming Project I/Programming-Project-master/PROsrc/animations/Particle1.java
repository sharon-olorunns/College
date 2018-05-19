package animations;

import processing.core.PApplet;

public class Particle1 {
	float x, y, r;
	int c;
	int i = 1, j = 1;
	PApplet p;

	public Particle1(PApplet parent) {
		p = parent;
		x = p.random(0, p.width);
		y = p.random(0, p.height);
		r = p.random(1, 5);
		int j = (int) p.random(0, 4);
		if (j == 0) {
			c = p.color(5, 205, 229);
		}
		if (j == 1) {
			c = p.color(255, 184, 3);
		}
		if (j == 2) {
			c = p.color(255, 3, 91);
		}
		if (j == 3) {
			c = p.color(61, 62, 62);
		}
	}

	public void display(){
		p.pushStyle();
		p.noStroke();
		p.fill(c);
		p.ellipse(x, y, r, r);
		p.popStyle();
	}
	
	 void update() {
	    x = x + j*0.01f;
	    y = y + i*0.01f;
	    if (y > p.height-r) i=-1;
	    if (y < 0+r) i=1;
	    if (x > p.width-r) j=-1;
	    if (x < 0+r) j=1;
	  }
}