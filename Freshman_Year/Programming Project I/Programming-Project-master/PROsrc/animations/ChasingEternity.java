package animations;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class ChasingEternity extends Animate {
	float shade = 0;
	PVector target;
	PVector[] points;
	float x, y, angle, ease = 0.5F;
	boolean easing = true;
	int num=140;
	int frames=165;
	public ChasingEternity(PApplet parent, int maxTime) {
		super(parent, maxTime);
		p = parent;
				points = new PVector[num];
		for (int i=0; i<num; i++) {	
			points[i] = new PVector(p.width/2, p.height/2);
		}
	}

	@Override
	public void draw() {
		p.background(255);
		p.colorMode(PConstants.HSB,360,100,100);
		p.noStroke();
		p.fill(0,0,shade+= 0.8F);
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 7);
		p.colorMode(PConstants.HSB,360,100,100);
		float d = 200;
		x = p.width/2+PApplet.cos(angle)*d;
		y = p.height/2+PApplet.sin(angle*2)*d;
		target = new PVector(x,y);
		PVector leader = new PVector(target.x, target.y);
		for (int i=0; i<num; i++) {
			p.fill(180.0F/num*i,90,90);
			PVector point = points[i];
			PVector distance = PVector.sub(leader, point);
			PVector velocity = PVector.mult(distance, ease);
			point.add(velocity);
			p.ellipse(point.x, point.y, 70, 70);
			leader = point;
		}
		angle += PConstants.TWO_PI/frames;
		p.colorMode(PConstants.RGB);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}
	
}