
import processing.core.PShape;

public class Tank {
	public final PShape shape;

	public Tank(final int ix, final int iy, final byte type) {
		shape = Facade.createShape(Main.getImage(type), ix, iy);
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
