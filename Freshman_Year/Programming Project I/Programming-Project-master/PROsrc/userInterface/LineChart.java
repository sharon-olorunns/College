package userInterface;

import org.gicentre.utils.colour.ColourTable;
import org.gicentre.utils.stat.XYChart;

import processing.core.PApplet;
import processing.core.PFont;

public class LineChart extends XYChart implements Drawable {
	float[] initialYValues;
	float[] initialXValues;
	float[] yValues;
	float[] xValues;
	float stepsTaken = 0;
	float e = 0;
	String[] labels;
	PApplet p;
	int xPos;
	int yPos;
	int width;
	int height;
	float[] xPoints = new float[1];
	float[] yPoints = new float[1];
	PFont font;

	public LineChart(PApplet parent, float[] xValues, float[] yValues, int maxValue, int xPos, int yPos, int width, int height, PFont font) {
		super(parent);
		p = parent;
		//p.textFont(font);
		initialYValues = new float[yValues.length];
		initialXValues = new float[xValues.length];
		this.yValues = yValues;
		this.xValues = xValues;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		setData(initialXValues, initialYValues);
		showXAxis(true);
		showYAxis(true);
		setMinY(0);
		setMinX(0);
		setMaxY(maxValue);
		setMaxX(maxValue);
		setPointColour(colourPoints(), getColourTable(p.color(0), p.color(255)));
		setPointSize(xValues, 20);
		setLineWidth(4);
	}

	public LineChart(PApplet parent, int[] xValues, int[] yValues, int maxValue, int xPos, int yPos, int width, int height, PFont font) {
		this(parent, toFloatArray(xValues), toFloatArray(yValues), maxValue, xPos, yPos, width, height, font);
	}

	public static float[] toFloatArray(int[] intArray) {
		float[] floatArray = new float[intArray.length];
		for (int index = 0; index < intArray.length; index++)
			floatArray[index] = intArray[index];
		return floatArray;
	}

	public void lerp() {
		if (stepsTaken != 500) {
			for (int index = 0; index < initialYValues.length; index++) {
				initialYValues[index] += yValues[index] / 500;
				initialXValues[index] += xValues[index] / 500;
			}
			stepsTaken++;
		}
	}

	@Override
	public void draw() {
		draw(xPos, yPos, width, height);
//		if (stepsTaken != 500)
//			lerp();
		 if(xPoints.length != xValues.length)
		 animate();
	}

	private ColourTable getColourTable(int colour1, int colour2) {
		ColourTable cTable = new ColourTable();
		cTable.addContinuousColourRule(0, colour1);
		cTable.addContinuousColourRule(1, colour2);
		return cTable;
	}

	private float[] colourPoints() {
		float[] colorArray = new float[yValues.length];
		for (int index = 0; index < colorArray.length; index++)
			colorArray[index] = (float)index/colorArray.length;
		return colorArray;
	}

	int index = -1;

	public void animate() {
		setData(xPoints, yPoints);
		if (e <= 1f) {
			lerp1();
		} else {
			e = 0f;
			index++;
			float[] tmpX = new float[xPoints.length + 1];
			float[] tmpY = new float[yPoints.length + 1];
			System.arraycopy(xPoints, 0, tmpX, 0, xPoints.length);
			System.arraycopy(yPoints, 0, tmpY, 0, yPoints.length);
			tmpX[tmpX.length - 1] = index == -1 ? 0f : xValues[index];
			tmpY[tmpY.length - 1] = index == -1 ? 0f : yValues[index];
			xPoints = tmpX.clone();
			yPoints = tmpY.clone();
		}
	}

	public void lerp1() {
		xPoints[index + 1] = (index == -1 ? 0f : xValues[index]) * (1 - e) + e * xValues[index + 1];
		yPoints[index + 1] = (index == -1 ? 0f : yValues[index]) * (1 - e) + e * yValues[index + 1];
		e += 0.05;
	}
}