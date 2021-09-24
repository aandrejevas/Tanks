
import processing.core.PConstants;
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank(final int ix, final int iy) {
		shape = Main.self.createShape(PShape.GEOMETRY);

		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(Main.red_tank);
		shape.translate(ix * Main.scale_x, iy * Main.scale_y);

		shape.beginShape(PConstants.POLYGON);
	}

	public Tank initLeft() {
		shape.vertex(0, 0, /*                 */ 1, 0);
		shape.vertex(Main.scale_x, 0, /*      */ 1, 1);
		shape.vertex(Main.scale_x, Main.scale_y, 0, 1);
		shape.vertex(0, Main.scale_y, /*      */ 0, 0);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public Tank initRight() {
		shape.vertex(0, 0, /*                 */ 0, 1);
		shape.vertex(Main.scale_x, 0, /*      */ 0, 0);
		shape.vertex(Main.scale_x, Main.scale_y, 1, 0);
		shape.vertex(0, Main.scale_y, /*      */ 1, 1);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public Tank initUp() {
		shape.vertex(0, 0, /*                 */ 0, 0);
		shape.vertex(Main.scale_x, 0, /*      */ 1, 0);
		shape.vertex(Main.scale_x, Main.scale_y, 1, 1);
		shape.vertex(0, Main.scale_y, /*      */ 0, 1);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public Tank initDown() {
		shape.vertex(0, 0, /*                 */ 1, 1);
		shape.vertex(Main.scale_x, 0, /*      */ 0, 1);
		shape.vertex(Main.scale_x, Main.scale_y, 0, 0);
		shape.vertex(0, Main.scale_y, /*      */ 1, 0);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public void pointLeft() {
		shape.setTextureUV(0, 1, 0);
		shape.setTextureUV(1, 1, 1);
		shape.setTextureUV(2, 0, 1);
		shape.setTextureUV(3, 0, 0);
		moveLeft();
	}

	public void pointRight() {
		shape.setTextureUV(0, 0, 1);
		shape.setTextureUV(1, 0, 0);
		shape.setTextureUV(2, 1, 0);
		shape.setTextureUV(3, 1, 1);
		moveRight();
	}

	public void pointUp() {
		shape.setTextureUV(0, 0, 0);
		shape.setTextureUV(1, 1, 0);
		shape.setTextureUV(2, 1, 1);
		shape.setTextureUV(3, 0, 1);
		moveUp();
	}

	public void pointDown() {
		shape.setTextureUV(0, 1, 1);
		shape.setTextureUV(1, 0, 1);
		shape.setTextureUV(2, 0, 0);
		shape.setTextureUV(3, 1, 0);
		moveDown();
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
