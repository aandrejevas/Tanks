
import java.util.function.Consumer;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import utils.Utils;

public abstract class Facade {
	protected Facade() {
		throw new AssertionError();
	}

	public static PShape createShape(final PImage image, final int x, final int y) {
		final PShape shape = Main.self.createShape(PShape.GEOMETRY);
		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(image);
		shape.translate(x * Main.scale, y * Main.scale);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, /*             */ 0, 0);
		shape.vertex(Main.scale, 0, /*    */ 1, 0);
		shape.vertex(Main.scale, Main.scale, 1, 1);
		shape.vertex(0, Main.scale, /*    */ 0, 1);
		shape.endShape(PConstants.CLOSE);
		return shape;
	}

	public static void handleAdd(final Consumer<Tank> func) {
		final int index = Utils.rbuf.getInt();
		final Tank tank = new Tank(Utils.rbuf.getInt(), Utils.rbuf.getInt(), (byte)Utils.rbuf.getInt());
		if (func != null) func.accept(tank);
		Main.tanks.put(index, tank);
	}

	public static void handleBulletAdd() {
		Main.bullets.put(Utils.rbuf.getInt(), new Bullet(Utils.rbuf.getInt(), Utils.rbuf.getInt(), (byte)Utils.rbuf.getInt()));
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(Main.tanks.get(Utils.rbuf.getInt()));
	}

	public static void handleBulletMove(final Consumer<Bullet> func) {
		func.accept(Main.bullets.get(Utils.rbuf.getInt()));
	}
}
