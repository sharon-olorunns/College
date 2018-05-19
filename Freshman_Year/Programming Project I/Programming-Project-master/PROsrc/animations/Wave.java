package animations;

import processing.core.PApplet;
import processing.core.PConstants;

public class Wave extends Animate{
	int num =20;
	float step, sz, offSet, theta, angle;
	float shade = 20f;
	public Wave(PApplet parent, int maxTime) {
		super(parent, maxTime);
		p.strokeWeight(5);
		step = 22;
	}

	@Override
	public void draw() {
		p.background(255);
		p.fill(shade+= 0.8F);
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 7);
		p.translate(p.width/2, p.height*.75f);
		angle=0;
		for (int i=0; i<num; i++) {
			p.stroke(255);
			p.noFill();
			sz = i*step;
			float offSet = PConstants.TWO_PI/num*i;
			float arcEnd = PApplet.map(PApplet.sin(theta+offSet),-1,1, PConstants.PI, PConstants.TWO_PI);
			p.arc(0, 0, sz, sz, PConstants.PI, arcEnd);
		}
		p.colorMode(PConstants.RGB);
		p.resetMatrix();
		theta += .0523;
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}
}