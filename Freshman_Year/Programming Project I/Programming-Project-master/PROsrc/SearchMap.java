import dataHandlers.GeoCoder;
import dataHandlers.LandData;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.EsriProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.core.PConstants;
import userInterface.Drawable;

public class SearchMap extends PApplet implements Drawable{
	PApplet main;
	int width;
	int height;
	UnfoldingMap[] maps = new UnfoldingMap[3];
	PApplet p;
	Location location;
	boolean canSet = true;
	LandData landData;
	int index = 0;
	public void settings(){
		size(width / 3, height-66,FX2D);
	}

	@SuppressWarnings("deprecation")
	public void setup(){
		surface.setLocation(width * 2 / 3,0);
		maps[0] = new UnfoldingMap(this, PConstants.FX2D,new EsriProvider.WorldTopoMap()  );
		maps[1] = new UnfoldingMap(this, PConstants.FX2D,new Microsoft.RoadProvider());
		maps[2] = new UnfoldingMap(this, PConstants.FX2D, new Microsoft.HybridProvider());
		for (int index = 0; index < maps.length; index++){
			MapUtils.createDefaultEventDispatcher(this, maps[index]);	
			Location UKLocation = new Location(54.580962f, -3.13827465f);
			maps[index].zoomAndPanTo(UKLocation, 7);
			maps[index].setTweening(true);
		}
	}

	public void draw(){
		maps[index].draw();
		zoom();
	}
	
	public void setToMap(int index){
		if (index >=0)
		this.index = index;
	}

	public void runSketch(PApplet parent,int fullScreenWidth, int fullScreenHeight, LandData landData){
		this.landData = landData;
		main = parent;
		this.width = fullScreenWidth;
		this.height = fullScreenHeight;
		this.runSketch(new String[]{"SearchMap"});
		surface.setAlwaysOnTop(true);
		surface.setResizable(false);
	}

	public void close(){
		surface.setLocation(10000,1000);
	}

	public void open(){
		surface.setLocation(width * 2 / 3,0);
	}
	public void mousePressed(String postCode) {
		if (canSet) {
			try {
				double[] coords = GeoCoder.geoCode(postCode);
				location = new Location(coords[1],coords[0]);
				canSet = false;
			} catch (Exception e) {
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void zoom() {
		if (!canSet) {
			for (int index = 0; index < maps.length; index++)
				maps[index].zoomAndPanTo(location, 16);
			canSet = true;
		}
	}
}