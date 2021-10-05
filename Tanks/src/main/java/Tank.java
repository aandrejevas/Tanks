
import processing.core.PConstants;
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank() {
		shape = Main.self.createShape(PShape.GEOMETRY);

		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(Main.red_tank);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, /*                 */ 0, 0);
		shape.vertex(Main.scale_x, 0, /*      */ 1, 0);
		shape.vertex(Main.scale_x, Main.scale_y, 1, 1);
		shape.vertex(0, Main.scale_y, /*      */ 0, 1);
		shape.endShape(PConstants.CLOSE);
	}

	public void pointLeft() {
		shape.setTextureUV(0, 1, 0);
		shape.setTextureUV(1, 1, 1);
		shape.setTextureUV(2, 0, 1);
		shape.setTextureUV(3, 0, 0);
	}

	public void pointRight() {
		shape.setTextureUV(0, 0, 1);
		shape.setTextureUV(1, 0, 0);
		shape.setTextureUV(2, 1, 0);
		shape.setTextureUV(3, 1, 1);
	}

	public void pointUp() {
		shape.setTextureUV(0, 0, 0);
		shape.setTextureUV(1, 1, 0);
		shape.setTextureUV(2, 1, 1);
		shape.setTextureUV(3, 0, 1);
	}

	public void pointDown() {
		shape.setTextureUV(0, 1, 1);
		shape.setTextureUV(1, 0, 1);
		shape.setTextureUV(2, 0, 0);
		shape.setTextureUV(3, 1, 0);
	}

	public void move(final int ix, final int iy) {
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

	public void turnLeft() {
		pointLeft();
		moveLeft();
	}

	public void turnRight() {
		pointRight();
		moveRight();
	}

	public void turnUp() {
		pointUp();
		moveUp();
	}

	public void turnDown() {
		pointDown();
		moveDown();
	}
}
