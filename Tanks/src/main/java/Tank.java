
import processing.core.PConstants;
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank(final int ix, final int iy, final int ally_or_enemy) {
		shape = Main.self.createShape(PShape.GEOMETRY);

		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		if (ally_or_enemy == 0) {
			shape.setTexture(Main.t34_tank);
		} else {
			shape.setTexture(Main.tiger_tank);
		}
		shape.translate(ix * Main.scale, iy * Main.scale);

		shape.beginShape(PConstants.POLYGON);
	}

	public Tank initLeft() {
		shape.vertex(0, 0, /*             */ 1, 0);
		shape.vertex(Main.scale, 0, /*    */ 1, 1);
		shape.vertex(Main.scale, Main.scale, 0, 1);
		shape.vertex(0, Main.scale, /*    */ 0, 0);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public Tank initRight() {
		shape.vertex(0, 0, /*             */ 0, 1);
		shape.vertex(Main.scale, 0, /*    */ 0, 0);
		shape.vertex(Main.scale, Main.scale, 1, 0);
		shape.vertex(0, Main.scale, /*    */ 1, 1);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public Tank initUp() {
		shape.vertex(0, 0, /*             */ 0, 0);
		shape.vertex(Main.scale, 0, /*    */ 1, 0);
		shape.vertex(Main.scale, Main.scale, 1, 1);
		shape.vertex(0, Main.scale, /*    */ 0, 1);
		shape.endShape(PConstants.CLOSE);
		return this;
	}

	public Tank initDown() {
		shape.vertex(0, 0, /*             */ 1, 1);
		shape.vertex(Main.scale, 0, /*    */ 0, 1);
		shape.vertex(Main.scale, Main.scale, 0, 0);
		shape.vertex(0, Main.scale, /*    */ 1, 0);
		shape.endShape(PConstants.CLOSE);
		return this;
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

	public void moveLeft() {
		shape.translate(-Main.scale, 0);
	}

	public void moveRight() {
		shape.translate(Main.scale, 0);
	}

	public void moveUp() {
		shape.translate(0, -Main.scale);
	}

	public void moveDown() {
		shape.translate(0, Main.scale);
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
