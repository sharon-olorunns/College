package userInterface;

import processing.core.*;
import java.util.Arrays;
import org.gicentre.utils.colour.*;
import org.gicentre.utils.stat.BarChart;

public class AnimatedBarChart extends BarChart implements Drawable {
	PApplet p;
	float[] initialYValues;
	float[] yValues;
	float numOfSteps;
	float stepsTaken = 0;
	String[] barChartLabels;
	String[] labels;
	int xPos;
	int yPos;
	float width;
	float height;
	PFont font;
	String title;
	boolean sideWays;
	boolean displayDataOnY;
	float[] data;
	public AnimatedBarChart(PApplet parent, float[] yValues, String[] barChartLabels, float numOfSteps, int maxValue,
			int xPos, int yPos, float width, float height, boolean transPoseAxes, String title, boolean sideWays, boolean displayDataOnY) {
		super(parent);
		p = parent;
		this.barChartLabels = barChartLabels;
		this.numOfSteps = numOfSteps;
		initialYValues = new float[yValues.length];
		this.yValues = yValues;
		this.xPos = xPos + 40;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		setData(initialYValues);
		setMinValue(0);
		setMaxValue(maxValue);
		p.textFont(p.createFont("Serif", 10), 10);
		showValueAxis(true);
		setValueFormat("£#0");
		setBarLabels(barChartLabels);
		showCategoryAxis(true);
		setBarGap(10);
		transposeAxes(transPoseAxes);
		setBarColour(getColourValues(), getColourTable(p.color(128,0,128), p.color(30,144,255) ));
		p.stroke(p.color(255));
		p.strokeWeight(2);
		setShowEdge(true);
		font = p.loadFont("YuGothicUI-Light-20.vlw");
		this.title = title;
		this.sideWays = sideWays;
		this.displayDataOnY = displayDataOnY;
		labels = barChartLabels.clone();
		data = yValues.clone();
	}

	public AnimatedBarChart(PApplet parent) {
		super(parent);
	}

	public AnimatedBarChart(PApplet parent, int[] yValues, String[] barChartLabels, float numOfSteps, int maxValue,
			int xPos, int yPos, int width, int height, boolean transPoseAxes, String title, boolean sideWays, boolean displayDataOnY) {
		this(parent, toFloatArray(yValues), barChartLabels, numOfSteps, maxValue, xPos, yPos, width, height, transPoseAxes, title , sideWays, displayDataOnY);
	}

	public static float[] toFloatArray(int[] intArray) {
		float[] floatArray = new float[intArray.length];
		for (int index = 0; index < intArray.length; index++)
			floatArray[index] = intArray[index];
		return floatArray;
	}

	@Override
	public void draw() {
		draw(xPos, yPos, width, height);
		if (stepsTaken != numOfSteps)
			lerp();
		displayCategory();
		p.text(title, xPos + (width / 2), yPos );
	}

	public void lerp() {
		if (stepsTaken != numOfSteps) {
			for (int index = 0; index < initialYValues.length; index++)
				initialYValues[index] += yValues[index] / numOfSteps;
			stepsTaken++;
		}
	}

	public void displayCategory() {
		PVector a = getScreenToData(new PVector(p.mouseX, p.mouseY));
		if (a != null) {
			p.fill(0);
			p.textFont(font);
			p.textAlign(PConstants.CENTER, PConstants.CENTER);
			if(displayDataOnY)
			{
				if(sideWays)
				{
					p.text(String.valueOf((int)data[data.length - (int) a.x - 1]), p.mouseX, p.mouseY - 10);
				}
				else
				{
					p.text(String.valueOf((int)data[(int) a.x]), p.mouseX, p.mouseY - 10);
				}
			}
			else
			{
				if(sideWays)
				{
					p.text((labels[labels.length - (int) a.x - 1]) + ": " + String.valueOf((int)data[data.length - (int) a.x - 1]), p.mouseX, p.mouseY - 10);
				}
				else
				{
					p.text(labels[(int) a.x] + ": " + String.valueOf((int)data[(int) a.x]), p.mouseX, p.mouseY - 10);
				}
			}
			
			
			p.stroke(p.color(255));
			p.strokeWeight(2);
		}
	}
	
	public void setClear(){
		for (int index = 0; index < barChartLabels.length; index++)
			barChartLabels[index] = "";
	}

	private ColourTable getColourTable(int colour1, int colour2) {
		ColourTable cTable = new ColourTable();
		cTable.addContinuousColourRule(0, colour1);
		cTable.addContinuousColourRule(1, colour2);
		return cTable;
	}

	private float[] getColourValues() {
		float[] sortedArray = yValues.clone();
		Arrays.sort(sortedArray);
		float[] colourArray = new float[sortedArray.length];
		for (int i = 0; i < colourArray.length; i++)
			for (int j = 0; j < colourArray.length; j++)
				if (yValues[i] == sortedArray[j]) {
					colourArray[i] = j * 1f / yValues.length;
					break;
				}
		return colourArray;
	}
}