package userInterface;

import processing.core.*;

public class PieChart implements Drawable {
	PApplet p;
	float[] data;
	String[] labels;
	float num = 0;
	PFont font;
	String title;
	float[] firstAngle;
	float[] secondAngle;
	int[] colours;
	float max = 360;
	int width;
	int background;
	float range;
	int xPos;
	int yPos;
	int Bcolour;

	public PieChart(PApplet parent, float[] data, String[] labels, String title, int width, int background, PFont font,
			int xPos, int yPos) {
		p = parent;
		colours = new int[data.length];
		firstAngle = new float[data.length];
		secondAngle = new float[data.length];
		this.data = data;
		this.labels = labels;
		this.font = font;
		this.xPos = xPos;
		this.yPos = yPos;
		font = p.createFont("Boulder Regular.tff", 30);
		p.smooth();
		p.textFont(font);
		Bcolour = p.color(128, 0, 128);
		this.title = title;
		this.width = width;
		this.background = 255;
		range = width / data.length;
		createPie();
	}

	public PieChart(PApplet parent, int[] data, String[] labels, String title, int width, int background, PFont font,
			int xPos, int yPos) {
		this(parent, toFloatArray(data), labels, title, width, background, font, xPos, yPos);
	}

	public static float[] toFloatArray(int[] intArray) {
		float[] floatArray = new float[intArray.length];
		for (int index = 0; index < intArray.length; index++)
			floatArray[index] = intArray[index];
		return floatArray;
	}

	private void createPie() {
		for (int i = 0; i < data.length; i++) {
			num += data[i];
			colours[i] = p.color((int) p.random(255), (int) p.random(255), (int) p.random(255));
		}
		num = 360 / num;
		float tally = 0;
		for (int i = 0; i < data.length; i++) {
			secondAngle[i] = tally;
			tally += data[i] * num;
			firstAngle[i] = tally;
		}
	}

	@Override
	public void draw() {
		p.stroke(255);
		p.strokeWeight(2);
		int y = (yPos * 2 - width) / 2;
		for (int i = 0; i < data.length; i++) {
			p.fill(colours[i]);
			p.arc(xPos, yPos, width, width, PApplet.radians(secondAngle[i]), PApplet.radians(firstAngle[i]));
			p.fill(colours[i]);
			p.rect(xPos + 10 + width / 2, y, font.getSize(), font.getSize());
			p.fill(Bcolour);
			p.textAlign(PApplet.LEFT, PApplet.TOP);
			p.text(labels[i] + ": " + (String.valueOf((int)data[i])), xPos + 10 + width / 2 + font.getSize() + 5, y);
			y += range;
		}
		p.fill(Bcolour);
		p.textFont(font);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text(title, xPos, yPos - ((width / 2) + font.getSize() / 2 + 10));
		p.fill(0);
		p.fill(background);
		max = max == 0 ? 0 : max - 5;
		p.noStroke();
		p.arc(xPos, yPos, width + 6, width + 6, 0, PApplet.radians(max));
	}
}