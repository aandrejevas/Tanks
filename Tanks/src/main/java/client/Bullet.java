package client;

import processing.core.PShape;
import utils.AbstractLogger;
import utils.ErrorLogger;
import utils.NullLogger;

public class Bullet extends AbstractLogger {
	public final PShape shape;

	public Bullet(final int x, final int y, final byte type) {
		super(Main.write_err ? ErrorLogger.instance : NullLogger.instance);
		shape = Facade.createShape(Main.images.getImage(type), x, y);
	}

	public void moveLeft() {
		shape.translate(-Main.scale, 0);
		log(System.identityHashCode(this) + " bullet moved left.");
	}

	public void moveRight() {
		shape.translate(Main.scale, 0);
		log(System.identityHashCode(this) + " bullet moved right.");
	}

	public void moveUp() {
		shape.translate(0, -Main.scale);
		log(System.identityHashCode(this) + " bullet moved up.");
	}

	public void moveDown() {
		shape.translate(0, Main.scale);
		log(System.identityHashCode(this) + " bullet moved down.");
	}
}
