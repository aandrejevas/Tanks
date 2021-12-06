package client;

import processing.core.PShape;
import utils.AbstractLogger;
import utils.Drawable;

public class Tank extends AbstractLogger implements Drawable {
	public final PShape shape;

	public Tank(final int ix, final int iy, final byte type) {
		shape = Facade.createShape(Main.images.getImage(type), ix, iy);
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
		log(() -> "Tank moved left.");
	}

	public void moveRight() {
		shape.translate(Main.scale, 0);
		log(() -> "Tank moved right.");
	}

	public void moveUp() {
		shape.translate(0, -Main.scale);
		log(() -> "Tank moved up.");
	}

	public void moveDown() {
		shape.translate(0, Main.scale);
		log(() -> "Tank moved down.");
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

	@Override
	public void draw() {
		shape.draw(Main.self.g);
	}
}
