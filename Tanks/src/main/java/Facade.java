
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

	public static PImage getImage(final byte key) {
		switch (key) {
			case Utils.MAP_EMPTY: return Main.background_box;
			case Utils.MAP_BORDER: return Main.metal_box;
			case Utils.MAP_WALL: return Main.wood_box;
			case Utils.MAP_WATER: return Main.water_box;
			case Utils.MAP_LAVA: return Main.lava_box;
			case Utils.MAP_T34: return Main.t34_tank;
			case Utils.MAP_SHERMAN: return Main.sherman_tank;
			case Utils.MAP_T34H: return Main.t34_tank_highlited;
			case Utils.MAP_TIGER: return Main.tiger_tank;
			case Utils.DROP_LAMMO: return Main.drop_lammo;
			case Utils.DROP_MAMMO: return Main.drop_mammo;
			case Utils.DROP_SAMMO: return Main.drop_sammo;
			case Utils.DROP_LARMOR: return Main.drop_larmor;
			case Utils.DROP_MARMOR: return Main.drop_marmor;
			case Utils.DROP_SARMOR: return Main.drop_sarmor;
			case Utils.DROP_LHEALTH: return Main.drop_lhealth;
			case Utils.DROP_MHEALTH: return Main.drop_mhealth;
			case Utils.DROP_SHEALTH: return Main.drop_shealth;
			case Utils.SHOT_NORMAL: return Main.bullet_normal;
			case Utils.SHOT_RED: return Main.bullet_red;
			case Utils.SHOT_BLUE: return Main.bullet_blue;
			default: throw new NullPointerException();
		}
	}

	public static void handleAddNew(final byte type) {
		Main.tanks.put(Utils.rbuf.getInt(), new Tank(Utils.rbuf.getInt(), Utils.rbuf.getInt(), type));
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
