package animations;
import processing.core.PApplet;
import processing.core.PConstants;
public class BoardsInALine extends Animate{
	float shade = 255;
	public BoardsInALine(PApplet parent, int maxTime) {
		super(parent, maxTime);
	}

	@Override
	public void draw() {
		p.background(255);
		p.rectMode(PConstants.CENTER);
		p.stroke(255, 251, 249);
		p.strokeWeight(4);
		p.fill(shade-= .8F);
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 7);
		p.fill(20);
		p.translate(p.width / 2, p.height / 2);
		int num = 10;
		float intervalX = PApplet.map(p.width, 0,p.width, 40, -40);
		float intervalY = PApplet.map(PApplet.abs(p.width - p.width / 2), 0, p.width / 2, 0, -20);
		float rectX = 100;
		float rectY = 200;
		float tilt = PApplet.map(p.width, 0, p.width, -20, 20);
		for(int i = num - 1; i > 0; i--){
			p.pushMatrix();
			float rhytm = PApplet.map(PApplet.pow(PApplet.abs(PApplet.sin(p.frameCount * 0.03f - i * 0.3f)), 50), 0, 1, 0, -50)
					* PApplet.map(PApplet.abs(p.width - p.width / 2), 0, p.width / 2, 0, 1);
			p.translate((float)(intervalX * (i - num / 2.0)),(float)( intervalY * (i - num / 2.0) + rhytm));
			p.beginShape();
			p.vertex(-rectX / 2.0f, -rectY / 2.0f + tilt);
			p.vertex(rectX / 2.0f, -rectY / 2.0f - tilt);
			p.vertex(rectX / 2.0f, rectY / 2.0f - tilt);
			p.vertex(-rectX / 2.0f, rectY / 2.0f + tilt);
			p.endShape(PConstants.CLOSE);
			p.popMatrix();
			p.rectMode(PApplet.CORNER);
			p.textAlign(PApplet.CENTER,PApplet.BASELINE);
		}
	}
}