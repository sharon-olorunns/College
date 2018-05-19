package animations;
import processing.core.PApplet;
import processing.core.PConstants;

public class WhiteLines extends Animate{
	int N = 8, r = 150;
	float k=0, t;
	float shade = 0;
	public WhiteLines(PApplet parent, int maxTime) {
		super(parent, maxTime);
	}

	@Override
	public void draw() {
		p.background(255);
		p.strokeWeight(10);
		p.stroke(-1);
		p.fill(p.lerpColor(p.color(240,80,80),p.color(255), shade += 0.004));
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 7);
		p.translate(p.width>>1, p.height>>1);
		for (int i=0; i<N; i++) {
			float ang = 360/N;
			float l = r*PApplet.sin(PApplet.radians(ang/2));
			float x = r*PApplet.cos(PApplet.radians(ang*i));
			float y = r*PApplet.sin(PApplet.radians(ang*i));
			p.pushMatrix();
			p.translate(x, y);
			p.rotate(PConstants.PI/2+PApplet.radians(ang*i+k));
			p.line(- l, 0, l, 0);
			p.popMatrix();
		}
		k=180*PApplet.sin(PApplet.radians(t));
		if (t<90) t+=2;
		else t=0;
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}
}