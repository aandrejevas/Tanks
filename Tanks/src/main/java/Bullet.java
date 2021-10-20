
import processing.core.PConstants;
import processing.core.PShape;

public class Bullet {
	public final PShape shape;

	public Bullet(final int x, final int y) {
		shape = Main.self.createShape(PShape.GEOMETRY);
		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(Main.bullet_blue);
		shape.translate(x * Main.scale, y * Main.scale);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, /*             */ 0, 0);
		shape.vertex(Main.scale, 0, /*    */ 1, 0);
		shape.vertex(Main.scale, Main.scale, 1, 1);
		shape.vertex(0, Main.scale, /*    */ 0, 1);
		shape.endShape(PConstants.CLOSE);
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
}
