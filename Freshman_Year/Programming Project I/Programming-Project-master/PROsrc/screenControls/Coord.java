package screenControls;
import processing.core.PApplet;
/*
 * This class is used for passing in data to an object
 * Uses ratios instead of coordinates
 * It's variables store the X and Y position of a drawable object,
 * as well as the max ratio it can take up on the screen
 */
public class Coord {
	private float x;
	private float y;
	private float sizeX;
	private float sizeY;
	private PApplet parent;

	public Coord(PApplet parent, float x, float y, float xSize, float ySize){
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.sizeX = xSize;
		this.sizeY = ySize;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getsizeX() {
		return sizeX;
	}

	public float getsizeY() {
		return sizeY;
	}
	
	public float getAbsoluteX(){
		return x * parent.width;
	}
	
	public float getAbsoluteY(){
		return y * parent.height;
	}
	
	public float getAbsoluteSizeX(){
		return sizeX * parent.width;
	}
	
	public float getAbsoluteSizeY(){
		return sizeY * parent.height;
	}
}