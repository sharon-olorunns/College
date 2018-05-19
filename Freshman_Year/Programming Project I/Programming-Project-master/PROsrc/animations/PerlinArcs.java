package animations;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class PerlinArcs extends Animate {
	float n = 0;
	float shade = 238;
	PFont font;

	public PerlinArcs(PApplet parent, int maxTime) {
		super(parent, maxTime);
		p.background(238);
		p.stroke(34, 50);
		p.noFill();
		p.strokeWeight(1);
		font = p.loadFont("YuGothicUI-Light-50.vlw");
	}

	@Override
	public void draw() {
		p.background(255);
		float d = 300 + p.frameCount % 250;
		float end = PApplet.map(p.noise(n), 0, 1, PConstants.PI, PConstants.TWO_PI);
		p.arc(p.width / 2, p.height / 2, d, d, PConstants.HALF_PI, end);
		p.fill(shade -= .8F);
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width / 2, p.height / 2);
		p.stroke(34, 50);
		p.noFill();
		p.strokeWeight(1);
		n += 0.05;
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}
}