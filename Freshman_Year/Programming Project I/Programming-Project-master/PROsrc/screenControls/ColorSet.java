package screenControls;

/*
 * This class is used for passing a set colors
 */
public class ColorSet {
	/*
	 * In processing, just integer numbers can be used a color, hence the int
	 * declarations
	 */
	private int fillColor;
	private int strokeColor;
	private int fillIfClickedColor;
	private int strokeMouseOverColor;
	private int textColor;

	public ColorSet(int fillColor, int strokeColor, int fillMouseOverColor, int strokeMouseOverColor, int textColor) {
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.fillIfClickedColor = fillMouseOverColor;
		this.strokeMouseOverColor = strokeMouseOverColor;
		this.textColor = textColor;
	}

	public int getFillColor() {
		return fillColor;
	}

	public int getStrokeColor() {
		return strokeColor;
	}

	public int getFillMouseOverColor() {
		return fillIfClickedColor;
	}

	public int getStrokeMouseOverColor() {
		return strokeMouseOverColor;
	}
	
	public int getTextColor(){
		return textColor;
	}

}
