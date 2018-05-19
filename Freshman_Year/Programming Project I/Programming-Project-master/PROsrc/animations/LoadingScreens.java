package animations;

import java.util.ArrayList;

import processing.core.PApplet;

public final class LoadingScreens {
	public static int currentLoadingScreen = 0;
	public static final int LOADING_SCREEN_1 = 0;
	public static final int LOADING_SCREEN_2 = 1;
	public static final int LOADING_SCREEN_3 = 2;
	public static final int LOADING_SCREEN_4 = 3;
	public static final int LOADING_SCREEN_5 = 4;
	public static final int LOADING_SCREEN_6 = 5;
	public static final int LOADING_SCREEN_7 = 6;
	public static final int LOADING_SCREEN_8 = 7;
	public static final int LOADING_SCREEN_9 = 8;
	public static final int LOADING_SCREEN_10 = 9;
	public static final int[] SCREENS = new int[] { LOADING_SCREEN_1, LOADING_SCREEN_2, LOADING_SCREEN_3,
			LOADING_SCREEN_4, LOADING_SCREEN_5, LOADING_SCREEN_6, LOADING_SCREEN_7, LOADING_SCREEN_8, LOADING_SCREEN_9,
			LOADING_SCREEN_10 };
	public static PApplet p;
	public static ArrayList<Animate> loadingScreens = new ArrayList<Animate>();
	public LoadingScreens(PApplet parent) {
		p = parent;
		loadingScreens.add(new BoardsInALine(p, 5));
		loadingScreens.add(new ChasingEternity(p, 5));
		loadingScreens.add(new FlippingLines(p, 5));
		loadingScreens.add(new HexCellent(p, 5));
		loadingScreens.add(new MovingTriangles(p, 5));
		loadingScreens.add(new PerlinArcs(p, 5));
		loadingScreens.add(new TrinagleParticles(p, 5));
		loadingScreens.add(new Wave(p, 5));
		loadingScreens.add(new WhiteLines(p, 5));
	}
	
	public static void drawScreen(int screen) {
		currentLoadingScreen = screen;
		loadingScreens.get(screen).setSecs = true;
		loadingScreens.get(screen).canDraw = false;
	}
}