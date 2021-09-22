
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank(final PApplet applet, final int ix, final int iy) {
		final float x = ix * Main.scale_x, y = iy * Main.scale_y;
		shape = applet.createShape(PShape.GEOMETRY);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(Main.red_tank);
		shape.beginShape(PConstants.POLYGON);
		shape.vertex(x, y, 0, 0);
		shape.vertex(x + Main.scale_x, y, 1, 0);
		shape.vertex(x + Main.scale_x, y + Main.scale_y, 1, 1);
		shape.vertex(x, y + Main.scale_y, 0, 1);
		shape.endShape(PConstants.CLOSE);
		shape.setStroke(false);
		shape.setFill(false);
	}

	public void updateX(final int ix) {
		final float x = ix * Main.scale_x;
		shape.setVertex(0, x, shape.getVertexY(0));
		shape.setVertex(1, x + Main.scale_x, shape.getVertexY(1));
		shape.setVertex(2, x + Main.scale_x, shape.getVertexY(2));
		shape.setVertex(3, x, shape.getVertexY(3));
	}

	public void updateY(final int iy) {
		final float y = iy * Main.scale_y;
		shape.setVertex(0, shape.getVertexX(0), y);
		shape.setVertex(1, shape.getVertexX(1), y);
		shape.setVertex(2, shape.getVertexX(2), y + Main.scale_y);
		shape.setVertex(3, shape.getVertexX(3), y + Main.scale_y);
	}
}
