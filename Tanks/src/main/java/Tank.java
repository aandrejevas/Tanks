
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank(final PApplet applet, final int ix, final int iy) {
		shape = applet.createShape(PShape.GEOMETRY);

		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(Main.red_tank);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, 0, 0);
		shape.vertex(Main.scale_x, 0, 1, 0);
		shape.vertex(Main.scale_x, Main.scale_y, 1, 1);
		shape.vertex(0, Main.scale_y, 0, 1);
		shape.endShape(PConstants.CLOSE);

		shape.translate(ix * Main.scale_x, iy * Main.scale_y);
	}

	public void moveLeft() {
		shape.translate(-Main.scale_x, 0);
	}

	public void moveRight() {
		shape.translate(Main.scale_x, 0);
	}

	public void moveUp() {
		shape.translate(0, -Main.scale_y);
	}

	public void moveDown() {
		shape.translate(0, Main.scale_y);
	}
}
