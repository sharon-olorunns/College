package animations;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class MovingTriangles extends Animate {
	ArrayList<Particle1> poop;
	boolean flag = true;
	int distance = 50;
	float shade = 255;
	public MovingTriangles(PApplet parent, int maxTime) {
		super(parent, maxTime);
		p.smooth();
		poop = new ArrayList<Particle1>();
		for (int i = 0; i < 100; i++) {
			Particle1 P = new Particle1(p);
			poop.add(P);
		}
	}

	@Override
	public void draw() {
		p.background(255);
		p.textFont(font);
		p.fill(shade-= 0.5F);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 2);
		for (int i = 0; i < poop.size(); i++) {
			Particle1 Pn1 = poop.get(i);
			Pn1.display();
			Pn1.update();
			for (int j = i + 1; j < poop.size(); j++) {
				Particle1 Pn2 = poop.get(j);
				Pn2.update();
				if (PApplet.dist(Pn1.x, Pn1.y, Pn2.x, Pn2.y) < distance) {
					for (int k = j + 1; k < poop.size(); k++) {
						Particle1 Pn3 = poop.get(k);
						if (PApplet.dist(Pn3.x, Pn3.y, Pn2.x, Pn2.y) < distance) {
							if (flag) {
								p.stroke(255, 10);
								p.fill(Pn3.c, 95); // method to access the class
													// property
							} else {
								p.noFill();
								p.strokeWeight(1);
								p.stroke(0, 20);
							}
							p.beginShape(PConstants.TRIANGLES);
							p.vertex(Pn1.x, Pn1.y);
							p.vertex(Pn2.x, Pn2.y);
							p.vertex(Pn3.x, Pn3.y);
							p.endShape();
						}
						Pn3.update();
					}
				}
			}
		}
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}
}