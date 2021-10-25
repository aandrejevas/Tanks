
import processing.core.PShape;

public class Bullet {
	public final PShape shape;

	public Bullet(final int x, final int y, final byte type) {
		shape = Facade.createShape(Facade.getImage(type), x, y);
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
