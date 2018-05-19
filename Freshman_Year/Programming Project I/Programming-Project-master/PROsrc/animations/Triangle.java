package animations;

import processing.core.PApplet;
import processing.core.PVector;

public class Triangle {
	PApplet p;
	PVector A, B, C;

	public Triangle(PApplet parent, PVector p1, PVector p2, PVector p3) {
		p = parent;
		A = p1;
		B = p2;
		C = p3;
	}

	public void display() {
		p.vertex(A.x, A.y);
		p.vertex(B.x, B.y);
		p.vertex(C.x, C.y);
	}
}
