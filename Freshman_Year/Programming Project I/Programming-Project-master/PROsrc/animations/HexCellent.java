package animations;

import processing.core.PApplet;
import processing.core.PConstants;

public class HexCellent extends Animate {
	int numBaseSides = 6;
	int sideSize = 100;
	float s = 0;
	float totalScale = 1.0f;
	float timeScale = 0.8f;
	float diff = 0.05f;
	float triangleHeight = (float) Math.sqrt(Math.pow(sideSize, 2) - Math.pow((sideSize / 2.0), 2));
	float shade = 0;
	public HexCellent(PApplet parent, int maxTime) {
		super(parent, maxTime);
	}

	@Override
	public void draw() {
		p.background(255);
		p.fill(PApplet.lerp(100, 255, shade+= .005F));
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("Pro Pro Pro's", p.width/2,p.height/ 7);
		p.fill(0);
		p.translate(p.width / 2, p.height / 2);
		p.scale(totalScale);
		p.rotate(PConstants.PI / 2);
		drawBase();
		drawExternalTriangles();
		totalScale -= 0.004f * timeScale;
		s += 0.02f * timeScale;
		if (totalScale <= 0.5) {
			totalScale = 1;
			s = 0;
		}
		p.colorMode(PConstants.RGB);
		p.rectMode(PApplet.CORNER);
		p.textAlign(PApplet.CENTER,PApplet.BASELINE);
	}

	public void drawExternalTriangles() {
		int triIndex = 0;
		for (float i = 0; i < 6; i++) {
			p.pushMatrix();
			p.rotate(i * PConstants.TWO_PI / 6f);
			p.translate(0, -triangleHeight * 1.5f);
			// left
			p.pushMatrix();
			p.translate(-sideSize / 2, 0);
			float s1 = PApplet.min(1, PApplet.max(s - (triIndex * diff), 0));
			p.scale(s1);
			p.rotate(PConstants.PI);
			p.triangle(sideSize / 2f, triangleHeight / 2.0f, -sideSize / 2f, triangleHeight / 2.0f, 0,
					-triangleHeight / 2.0f);
			p.popMatrix();
			triIndex++;
			// center
			p.pushMatrix();
			float s2 = PApplet.min(1, PApplet.max(s - (triIndex * diff), 0));
			p.scale(s2);
			p.triangle(sideSize / 2f, triangleHeight / 2.0f, -sideSize / 2f, triangleHeight / 2.0f, 0,
					-triangleHeight / 2.0f);
			p.popMatrix();
			triIndex++;
			// right
			p.pushMatrix();
			p.translate(sideSize / 2, 0);
			float s3 = PApplet.min(1, PApplet.max(s - (triIndex * diff), 0));
			p.scale(s3);
			p.rotate(PConstants.PI);
			p.triangle(sideSize / 2f, triangleHeight / 2.0f, -sideSize / 2f, triangleHeight / 2.0f, 0,
					-triangleHeight / 2.0f);
			p.popMatrix();
			triIndex++;
			p.popMatrix();
		}
	}

	public void drawBase() {
		for (float i = 0; i <= numBaseSides; i++) {
			p.pushMatrix();
			p.rotate(i * (PConstants.TWO_PI / numBaseSides));
			p.triangle(sideSize / 2, triangleHeight, -sideSize / 2, triangleHeight, 0, 0);
			p.popMatrix();
		}
	}
}