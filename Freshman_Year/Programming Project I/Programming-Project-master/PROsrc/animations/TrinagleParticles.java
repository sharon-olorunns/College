package animations;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class TrinagleParticles extends Animate {
	final int NB_PARTICLES = 200;
	float shade = 50;
	ArrayList<Triangle> triangles;
	Particle[] parts = new Particle[NB_PARTICLES];
	PImage image;
	MyColor myColor = new MyColor(p);

	public TrinagleParticles(PApplet parent, int maxTime) {
		super(parent, maxTime);
		for (int i = 0; i < NB_PARTICLES; i++) {
			parts[i] = new Particle(p);
		}
	}

	@Override
	public void draw() {
		myColor.update();
		p.noStroke();
		p.background(255);
		p.textFont(font);
		p.fill(shade+= 0.8F);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 2);
		p.fill(120, 255);
		triangles = new ArrayList<Triangle>();
		Particle p1, p2;
		for (int i = 0; i < NB_PARTICLES; i++) {
			parts[i].move();
		}

		for (int i = 0; i < NB_PARTICLES; i++) {
			p1 = parts[i];
			p1.neighboors = new ArrayList<Particle>();
			p1.neighboors.add(p1);
			for (int j = i + 1; j < NB_PARTICLES; j++) {
				p2 = parts[j];
				float d = PVector.dist(p1.pos, p2.pos);
				if (d > 0 && d < Particle.DIST_MAX) {
					p1.neighboors.add(p2);
				}
			}
			if (p1.neighboors.size() > 1) {
				addTriangles(p1.neighboors);
			}
		}
		drawTriangles();
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}

	private void drawTriangles() {
		p.noStroke();
		p.fill(myColor.R, myColor.G, myColor.B,41);
		p.stroke(PApplet.max(myColor.R, 0), PApplet.max(myColor.G, 0), PApplet.max(myColor.B, 0));
		// noFill();
		p.beginShape(PConstants.TRIANGLES);
		for (int i = 0; i < triangles.size(); i++) {
			Triangle t = triangles.get(i);
			t.display();
		}
		p.endShape();
	}

	private void addTriangles(ArrayList<Particle> p_neighboors) {
		int s = p_neighboors.size();
		if (s > 2)
			for (int i = 1; i < s - 1; i++)
				for (int j = i + 1; j < s; j++)
					triangles.add(
							new Triangle(p, p_neighboors.get(0).pos, p_neighboors.get(i).pos, p_neighboors.get(j).pos));
	}

	public int getInt(int number){
		return number;
	}
}