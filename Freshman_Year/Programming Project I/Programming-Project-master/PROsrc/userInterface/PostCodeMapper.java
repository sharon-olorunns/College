package userInterface;

import dataHandlers.GeoCoder;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.core.PConstants;
public class PostCodeMapper {
	UnfoldingMap map;
	PApplet p;
	boolean canZoom = false;
	Location location;
	int zoom;
	boolean canSet = true;
	@SuppressWarnings("deprecation")
	public PostCodeMapper(PApplet parent){
		p = parent;
		map = new UnfoldingMap(p, PConstants.FX2D, new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(p, map);	
		Location UKLocation = new Location(54.580962f, -3.13827465f);
		float maxPanningDistance = 450.169f;
		map.setPanningRestriction(UKLocation, maxPanningDistance);
		map.zoomAndPanTo(UKLocation, 7);
		map.setTweening(true);
		map.setZoomRange(5f, 16f);
	}
	
	public void draw() {
		map.draw();
		zoom();
	}
	public void mousePressed(String postCode) {
		if (canSet) {
			try {
				double[] coords = GeoCoder.geoCode(postCode);
				canZoom = true;
				location = new Location(coords[1], coords[0]);
				zoom = 5;
				canSet = false;
			} catch (Exception e) {
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void zoom() {
		if (!canSet) {
			map.zoomAndPanTo(location, 7);
			canSet = true;
		}
	}
}