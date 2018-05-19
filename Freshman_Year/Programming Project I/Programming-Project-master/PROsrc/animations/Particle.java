package animations;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
	final static float RAD = 4;
	final static float BOUNCE = -1;
	final static float SPEED_MAX = 2.2f;
	final static float DIST_MAX = 50;
	ArrayList<Particle> neighboors;
	PVector speed;
	PVector acc;
	PVector pos;
	PApplet p;

	public Particle(PApplet parent) {
		p = parent;
		speed = new PVector(p.random(-SPEED_MAX, SPEED_MAX), p.random(-SPEED_MAX, SPEED_MAX));
		acc = new PVector(0, 0);
		pos = new PVector(p.random(p.width), p.random(p.height));
	}

	public void move() {
		pos.add(speed);
		acc.mult(0);

		if (pos.x < 0) {
			pos.x = 0;
			speed.x *= BOUNCE;
		} else if (pos.x > p.width) {
			pos.x = p.width;
			speed.x *= BOUNCE;
		}
		if (pos.y < 0) {
			pos.y = 0;
			speed.y *= BOUNCE;
		} else if (pos.y > p.height) {
			pos.y = p.height;
			speed.y *= BOUNCE;
		}
	}
	public void display() {
		p.fill(255, 14);
		p.ellipse(pos.x, pos.y, RAD, RAD);
	}
}