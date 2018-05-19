package animations;
import processing.core.PApplet;
import processing.core.PConstants;
public class FlippingLines extends Animate{
	float shade = 255f;
	public FlippingLines(PApplet parent, int maxTime) {
		super(parent, maxTime);
		p.strokeWeight(4);
		p.stroke(250);
	}

	@Override
	public void draw() {
		p.background(255);
		p.rectMode(PConstants.CENTER);
		p.textFont(font);
		p.fill(shade-= 0.8F);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 7);
		p.translate(p.width/2, p.height/2);
		for(int i = -6; i <= 5; i++){
			float x = i*30;
			float y = 0;
			p.fill(PApplet.map(i, -6, 5, 0, 255), 50, 100);
			p.pushMatrix();
			p.translate(x, y);
			p.rotate(PApplet.radians(PApplet.tan(PApplet.radians(i+p.frameCount))+p.frameCount));
			p.rect(0, 0, 20, 100, 6);
			p.popMatrix();
		}
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}
}