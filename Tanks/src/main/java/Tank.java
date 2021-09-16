
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank(final PApplet applet, final int ix, final int iy) {
		shape = applet.createShape(PConstants.RECT, ix * Main.scale_x, iy * Main.scale_y, Main.scale_x, Main.scale_y);
		shape.setStroke(false);
		shape.setFill(0xFF000000);
	}
}
